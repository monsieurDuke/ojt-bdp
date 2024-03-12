package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.Map;

public class ChangeBounds
  extends Transition
{
  private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
  private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
  private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
  private static final Property<View, PointF> POSITION_PROPERTY = new Property(PointF.class, "position")
  {
    public PointF get(View paramAnonymousView)
    {
      return null;
    }
    
    public void set(View paramAnonymousView, PointF paramAnonymousPointF)
    {
      int i = Math.round(paramAnonymousPointF.x);
      int j = Math.round(paramAnonymousPointF.y);
      ViewUtils.setLeftTopRightBottom(paramAnonymousView, i, j, paramAnonymousView.getWidth() + i, paramAnonymousView.getHeight() + j);
    }
  };
  private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
  private static final String PROPNAME_CLIP = "android:changeBounds:clip";
  private static final String PROPNAME_PARENT = "android:changeBounds:parent";
  private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
  private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
  private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
  private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
  private static RectEvaluator sRectEvaluator = new RectEvaluator();
  private static final String[] sTransitionProperties = { "android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY" };
  private boolean mReparent = false;
  private boolean mResizeClip = false;
  private int[] mTempLocation = new int[2];
  
  static
  {
    DRAWABLE_ORIGIN_PROPERTY = new Property(PointF.class, "boundsOrigin")
    {
      private Rect mBounds = new Rect();
      
      public PointF get(Drawable paramAnonymousDrawable)
      {
        paramAnonymousDrawable.copyBounds(this.mBounds);
        return new PointF(this.mBounds.left, this.mBounds.top);
      }
      
      public void set(Drawable paramAnonymousDrawable, PointF paramAnonymousPointF)
      {
        paramAnonymousDrawable.copyBounds(this.mBounds);
        this.mBounds.offsetTo(Math.round(paramAnonymousPointF.x), Math.round(paramAnonymousPointF.y));
        paramAnonymousDrawable.setBounds(this.mBounds);
      }
    };
    TOP_LEFT_PROPERTY = new Property(PointF.class, "topLeft")
    {
      public PointF get(ChangeBounds.ViewBounds paramAnonymousViewBounds)
      {
        return null;
      }
      
      public void set(ChangeBounds.ViewBounds paramAnonymousViewBounds, PointF paramAnonymousPointF)
      {
        paramAnonymousViewBounds.setTopLeft(paramAnonymousPointF);
      }
    };
    BOTTOM_RIGHT_PROPERTY = new Property(PointF.class, "bottomRight")
    {
      public PointF get(ChangeBounds.ViewBounds paramAnonymousViewBounds)
      {
        return null;
      }
      
      public void set(ChangeBounds.ViewBounds paramAnonymousViewBounds, PointF paramAnonymousPointF)
      {
        paramAnonymousViewBounds.setBottomRight(paramAnonymousPointF);
      }
    };
    BOTTOM_RIGHT_ONLY_PROPERTY = new Property(PointF.class, "bottomRight")
    {
      public PointF get(View paramAnonymousView)
      {
        return null;
      }
      
      public void set(View paramAnonymousView, PointF paramAnonymousPointF)
      {
        ViewUtils.setLeftTopRightBottom(paramAnonymousView, paramAnonymousView.getLeft(), paramAnonymousView.getTop(), Math.round(paramAnonymousPointF.x), Math.round(paramAnonymousPointF.y));
      }
    };
    TOP_LEFT_ONLY_PROPERTY = new Property(PointF.class, "topLeft")
    {
      public PointF get(View paramAnonymousView)
      {
        return null;
      }
      
      public void set(View paramAnonymousView, PointF paramAnonymousPointF)
      {
        ViewUtils.setLeftTopRightBottom(paramAnonymousView, Math.round(paramAnonymousPointF.x), Math.round(paramAnonymousPointF.y), paramAnonymousView.getRight(), paramAnonymousView.getBottom());
      }
    };
  }
  
  public ChangeBounds() {}
  
  public ChangeBounds(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.CHANGE_BOUNDS);
    boolean bool = TypedArrayUtils.getNamedBoolean(paramContext, (XmlResourceParser)paramAttributeSet, "resizeClip", 0, false);
    paramContext.recycle();
    setResizeClip(bool);
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    if ((ViewCompat.isLaidOut(localView)) || (localView.getWidth() != 0) || (localView.getHeight() != 0))
    {
      paramTransitionValues.values.put("android:changeBounds:bounds", new Rect(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom()));
      paramTransitionValues.values.put("android:changeBounds:parent", paramTransitionValues.view.getParent());
      if (this.mReparent)
      {
        paramTransitionValues.view.getLocationInWindow(this.mTempLocation);
        paramTransitionValues.values.put("android:changeBounds:windowX", Integer.valueOf(this.mTempLocation[0]));
        paramTransitionValues.values.put("android:changeBounds:windowY", Integer.valueOf(this.mTempLocation[1]));
      }
      if (this.mResizeClip) {
        paramTransitionValues.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(localView));
      }
    }
  }
  
  private boolean parentMatches(View paramView1, View paramView2)
  {
    boolean bool1 = true;
    if (this.mReparent)
    {
      bool1 = true;
      boolean bool2 = true;
      TransitionValues localTransitionValues = getMatchedTransitionValues(paramView1, true);
      if (localTransitionValues == null)
      {
        if (paramView1 == paramView2) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
      }
      else if (paramView2 != localTransitionValues.view) {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(final ViewGroup paramViewGroup, final TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      final Object localObject1 = paramTransitionValues1.values;
      Object localObject2 = paramTransitionValues2.values;
      localObject1 = (ViewGroup)((Map)localObject1).get("android:changeBounds:parent");
      localObject2 = (ViewGroup)((Map)localObject2).get("android:changeBounds:parent");
      if ((localObject1 != null) && (localObject2 != null))
      {
        final View localView = paramTransitionValues2.view;
        int m;
        int k;
        int i;
        if (parentMatches((View)localObject1, (View)localObject2))
        {
          localObject1 = (Rect)paramTransitionValues1.values.get("android:changeBounds:bounds");
          paramViewGroup = (Rect)paramTransitionValues2.values.get("android:changeBounds:bounds");
          int i5 = ((Rect)localObject1).left;
          final int i7 = paramViewGroup.left;
          int i8 = ((Rect)localObject1).top;
          n = paramViewGroup.top;
          int i9 = ((Rect)localObject1).right;
          final int i2 = paramViewGroup.right;
          int i10 = ((Rect)localObject1).bottom;
          final int i6 = paramViewGroup.bottom;
          int i3 = i9 - i5;
          m = i10 - i8;
          int i4 = i2 - i7;
          int i1 = i6 - n;
          paramTransitionValues1 = (Rect)paramTransitionValues1.values.get("android:changeBounds:clip");
          localObject1 = (Rect)paramTransitionValues2.values.get("android:changeBounds:clip");
          j = 0;
          k = 0;
          if ((i3 == 0) || (m == 0))
          {
            i = j;
            if (i4 != 0)
            {
              i = j;
              if (i1 == 0) {}
            }
          }
          else
          {
            if (i5 == i7)
            {
              j = k;
              if (i8 == n) {}
            }
            else
            {
              j = 0 + 1;
            }
            if (i9 == i2)
            {
              i = j;
              if (i10 == i6) {}
            }
            else
            {
              i = j + 1;
            }
          }
          if ((paramTransitionValues1 == null) || (paramTransitionValues1.equals(localObject1)))
          {
            j = i;
            if (paramTransitionValues1 == null)
            {
              j = i;
              if (localObject1 == null) {}
            }
          }
          else
          {
            j = i + 1;
          }
          if (j > 0)
          {
            if (!this.mResizeClip)
            {
              ViewUtils.setLeftTopRightBottom(localView, i5, i8, i9, i10);
              if (j == 2)
              {
                if ((i3 == i4) && (m == i1))
                {
                  paramViewGroup = getPathMotion().getPath(i5, i8, i7, n);
                  paramViewGroup = ObjectAnimatorUtils.ofPointF(localView, POSITION_PROPERTY, paramViewGroup);
                }
                else
                {
                  paramTransitionValues1 = new ViewBounds(localView);
                  paramViewGroup = getPathMotion().getPath(i5, i8, i7, n);
                  paramTransitionValues2 = ObjectAnimatorUtils.ofPointF(paramTransitionValues1, TOP_LEFT_PROPERTY, paramViewGroup);
                  paramViewGroup = getPathMotion().getPath(i9, i10, i2, i6);
                  localObject1 = ObjectAnimatorUtils.ofPointF(paramTransitionValues1, BOTTOM_RIGHT_PROPERTY, paramViewGroup);
                  paramViewGroup = new AnimatorSet();
                  paramViewGroup.playTogether(new Animator[] { paramTransitionValues2, localObject1 });
                  paramViewGroup.addListener(new AnimatorListenerAdapter()
                  {
                    private ChangeBounds.ViewBounds mViewBounds;
                  });
                }
              }
              else if ((i5 == i7) && (i8 == n))
              {
                paramViewGroup = getPathMotion().getPath(i9, i10, i2, i6);
                paramViewGroup = ObjectAnimatorUtils.ofPointF(localView, BOTTOM_RIGHT_ONLY_PROPERTY, paramViewGroup);
              }
              else
              {
                paramViewGroup = getPathMotion().getPath(i5, i8, i7, n);
                paramViewGroup = ObjectAnimatorUtils.ofPointF(localView, TOP_LEFT_ONLY_PROPERTY, paramViewGroup);
              }
            }
            else
            {
              i = Math.max(i3, i4);
              ViewUtils.setLeftTopRightBottom(localView, i5, i8, i5 + i, i8 + Math.max(m, i1));
              if ((i5 == i7) && (i8 == n))
              {
                paramViewGroup = null;
              }
              else
              {
                paramViewGroup = getPathMotion().getPath(i5, i8, i7, n);
                paramViewGroup = ObjectAnimatorUtils.ofPointF(localView, POSITION_PROPERTY, paramViewGroup);
              }
              if (paramTransitionValues1 == null) {
                paramTransitionValues1 = new Rect(0, 0, i3, m);
              }
              if (localObject1 == null) {
                paramTransitionValues2 = new Rect(0, 0, i4, i1);
              } else {
                paramTransitionValues2 = (TransitionValues)localObject1;
              }
              localObject2 = null;
              if (!paramTransitionValues1.equals(paramTransitionValues2))
              {
                ViewCompat.setClipBounds(localView, paramTransitionValues1);
                paramTransitionValues1 = ObjectAnimator.ofObject(localView, "clipBounds", sRectEvaluator, new Object[] { paramTransitionValues1, paramTransitionValues2 });
                paramTransitionValues1.addListener(new AnimatorListenerAdapter()
                {
                  private boolean mIsCanceled;
                  
                  public void onAnimationCancel(Animator paramAnonymousAnimator)
                  {
                    this.mIsCanceled = true;
                  }
                  
                  public void onAnimationEnd(Animator paramAnonymousAnimator)
                  {
                    if (!this.mIsCanceled)
                    {
                      ViewCompat.setClipBounds(localView, localObject1);
                      ViewUtils.setLeftTopRightBottom(localView, i7, n, i2, i6);
                    }
                  }
                });
              }
              else
              {
                paramTransitionValues1 = (TransitionValues)localObject2;
              }
              paramViewGroup = TransitionUtils.mergeAnimators(paramViewGroup, paramTransitionValues1);
            }
            if ((localView.getParent() instanceof ViewGroup))
            {
              paramTransitionValues1 = (ViewGroup)localView.getParent();
              ViewGroupUtils.suppressLayout(paramTransitionValues1, true);
              addListener(new TransitionListenerAdapter()
              {
                boolean mCanceled = false;
                
                public void onTransitionCancel(Transition paramAnonymousTransition)
                {
                  ViewGroupUtils.suppressLayout(paramTransitionValues1, false);
                  this.mCanceled = true;
                }
                
                public void onTransitionEnd(Transition paramAnonymousTransition)
                {
                  if (!this.mCanceled) {
                    ViewGroupUtils.suppressLayout(paramTransitionValues1, false);
                  }
                  paramAnonymousTransition.removeListener(this);
                }
                
                public void onTransitionPause(Transition paramAnonymousTransition)
                {
                  ViewGroupUtils.suppressLayout(paramTransitionValues1, false);
                }
                
                public void onTransitionResume(Transition paramAnonymousTransition)
                {
                  ViewGroupUtils.suppressLayout(paramTransitionValues1, true);
                }
              });
            }
            return paramViewGroup;
          }
        }
        else
        {
          n = ((Integer)paramTransitionValues1.values.get("android:changeBounds:windowX")).intValue();
          m = ((Integer)paramTransitionValues1.values.get("android:changeBounds:windowY")).intValue();
          k = ((Integer)paramTransitionValues2.values.get("android:changeBounds:windowX")).intValue();
          i = ((Integer)paramTransitionValues2.values.get("android:changeBounds:windowY")).intValue();
          if ((n != k) || (m != i)) {
            break label958;
          }
        }
        return null;
        label958:
        paramViewGroup.getLocationInWindow(this.mTempLocation);
        paramTransitionValues1 = Bitmap.createBitmap(localView.getWidth(), localView.getHeight(), Bitmap.Config.ARGB_8888);
        localView.draw(new Canvas(paramTransitionValues1));
        paramTransitionValues1 = new BitmapDrawable(paramTransitionValues1);
        final float f2 = ViewUtils.getTransitionAlpha(localView);
        ViewUtils.setTransitionAlpha(localView, 0.0F);
        ViewUtils.getOverlay(paramViewGroup).add(paramTransitionValues1);
        localObject1 = getPathMotion();
        paramTransitionValues2 = this.mTempLocation;
        int j = paramTransitionValues2[0];
        float f1 = n - j;
        final int n = paramTransitionValues2[1];
        paramTransitionValues2 = ((PathMotion)localObject1).getPath(f1, m - n, k - j, i - n);
        paramTransitionValues2 = ObjectAnimator.ofPropertyValuesHolder(paramTransitionValues1, new PropertyValuesHolder[] { PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, paramTransitionValues2) });
        paramTransitionValues2.addListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            ViewUtils.getOverlay(paramViewGroup).remove(paramTransitionValues1);
            ViewUtils.setTransitionAlpha(localView, f2);
          }
        });
        return paramTransitionValues2;
      }
      return null;
    }
    return null;
  }
  
  public boolean getResizeClip()
  {
    return this.mResizeClip;
  }
  
  public String[] getTransitionProperties()
  {
    return sTransitionProperties;
  }
  
  public void setResizeClip(boolean paramBoolean)
  {
    this.mResizeClip = paramBoolean;
  }
  
  private static class ViewBounds
  {
    private int mBottom;
    private int mBottomRightCalls;
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mTopLeftCalls;
    private View mView;
    
    ViewBounds(View paramView)
    {
      this.mView = paramView;
    }
    
    private void setLeftTopRightBottom()
    {
      ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
      this.mTopLeftCalls = 0;
      this.mBottomRightCalls = 0;
    }
    
    void setBottomRight(PointF paramPointF)
    {
      this.mRight = Math.round(paramPointF.x);
      this.mBottom = Math.round(paramPointF.y);
      int i = this.mBottomRightCalls + 1;
      this.mBottomRightCalls = i;
      if (this.mTopLeftCalls == i) {
        setLeftTopRightBottom();
      }
    }
    
    void setTopLeft(PointF paramPointF)
    {
      this.mLeft = Math.round(paramPointF.x);
      this.mTop = Math.round(paramPointF.y);
      int i = this.mTopLeftCalls + 1;
      this.mTopLeftCalls = i;
      if (i == this.mBottomRightCalls) {
        setLeftTopRightBottom();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ChangeBounds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */