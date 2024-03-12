package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class Visibility
  extends Transition
{
  public static final int MODE_IN = 1;
  public static final int MODE_OUT = 2;
  private static final String PROPNAME_PARENT = "android:visibility:parent";
  private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
  static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
  private static final String[] sTransitionProperties = { "android:visibility:visibility", "android:visibility:parent" };
  private int mMode = 3;
  
  public Visibility() {}
  
  public Visibility(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.VISIBILITY_TRANSITION);
    int i = TypedArrayUtils.getNamedInt(paramContext, (XmlResourceParser)paramAttributeSet, "transitionVisibilityMode", 0, 0);
    paramContext.recycle();
    if (i != 0) {
      setMode(i);
    }
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    int i = paramTransitionValues.view.getVisibility();
    paramTransitionValues.values.put("android:visibility:visibility", Integer.valueOf(i));
    paramTransitionValues.values.put("android:visibility:parent", paramTransitionValues.view.getParent());
    int[] arrayOfInt = new int[2];
    paramTransitionValues.view.getLocationOnScreen(arrayOfInt);
    paramTransitionValues.values.put("android:visibility:screenLocation", arrayOfInt);
  }
  
  private VisibilityInfo getVisibilityChangeInfo(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    VisibilityInfo localVisibilityInfo = new VisibilityInfo();
    localVisibilityInfo.mVisibilityChange = false;
    localVisibilityInfo.mFadeIn = false;
    if ((paramTransitionValues1 != null) && (paramTransitionValues1.values.containsKey("android:visibility:visibility")))
    {
      localVisibilityInfo.mStartVisibility = ((Integer)paramTransitionValues1.values.get("android:visibility:visibility")).intValue();
      localVisibilityInfo.mStartParent = ((ViewGroup)paramTransitionValues1.values.get("android:visibility:parent"));
    }
    else
    {
      localVisibilityInfo.mStartVisibility = -1;
      localVisibilityInfo.mStartParent = null;
    }
    if ((paramTransitionValues2 != null) && (paramTransitionValues2.values.containsKey("android:visibility:visibility")))
    {
      localVisibilityInfo.mEndVisibility = ((Integer)paramTransitionValues2.values.get("android:visibility:visibility")).intValue();
      localVisibilityInfo.mEndParent = ((ViewGroup)paramTransitionValues2.values.get("android:visibility:parent"));
    }
    else
    {
      localVisibilityInfo.mEndVisibility = -1;
      localVisibilityInfo.mEndParent = null;
    }
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      if ((localVisibilityInfo.mStartVisibility == localVisibilityInfo.mEndVisibility) && (localVisibilityInfo.mStartParent == localVisibilityInfo.mEndParent)) {
        return localVisibilityInfo;
      }
      if (localVisibilityInfo.mStartVisibility != localVisibilityInfo.mEndVisibility)
      {
        if (localVisibilityInfo.mStartVisibility == 0)
        {
          localVisibilityInfo.mFadeIn = false;
          localVisibilityInfo.mVisibilityChange = true;
        }
        else if (localVisibilityInfo.mEndVisibility == 0)
        {
          localVisibilityInfo.mFadeIn = true;
          localVisibilityInfo.mVisibilityChange = true;
        }
      }
      else if (localVisibilityInfo.mEndParent == null)
      {
        localVisibilityInfo.mFadeIn = false;
        localVisibilityInfo.mVisibilityChange = true;
      }
      else if (localVisibilityInfo.mStartParent == null)
      {
        localVisibilityInfo.mFadeIn = true;
        localVisibilityInfo.mVisibilityChange = true;
      }
    }
    else if ((paramTransitionValues1 == null) && (localVisibilityInfo.mEndVisibility == 0))
    {
      localVisibilityInfo.mFadeIn = true;
      localVisibilityInfo.mVisibilityChange = true;
    }
    else if ((paramTransitionValues2 == null) && (localVisibilityInfo.mStartVisibility == 0))
    {
      localVisibilityInfo.mFadeIn = false;
      localVisibilityInfo.mVisibilityChange = true;
    }
    return localVisibilityInfo;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    VisibilityInfo localVisibilityInfo = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    if ((localVisibilityInfo.mVisibilityChange) && ((localVisibilityInfo.mStartParent != null) || (localVisibilityInfo.mEndParent != null)))
    {
      if (localVisibilityInfo.mFadeIn) {
        return onAppear(paramViewGroup, paramTransitionValues1, localVisibilityInfo.mStartVisibility, paramTransitionValues2, localVisibilityInfo.mEndVisibility);
      }
      return onDisappear(paramViewGroup, paramTransitionValues1, localVisibilityInfo.mStartVisibility, paramTransitionValues2, localVisibilityInfo.mEndVisibility);
    }
    return null;
  }
  
  public int getMode()
  {
    return this.mMode;
  }
  
  public String[] getTransitionProperties()
  {
    return sTransitionProperties;
  }
  
  public boolean isTransitionRequired(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    boolean bool2 = false;
    if ((paramTransitionValues1 == null) && (paramTransitionValues2 == null)) {
      return false;
    }
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null) && (paramTransitionValues2.values.containsKey("android:visibility:visibility") != paramTransitionValues1.values.containsKey("android:visibility:visibility"))) {
      return false;
    }
    paramTransitionValues1 = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    boolean bool1 = bool2;
    if (paramTransitionValues1.mVisibilityChange) {
      if (paramTransitionValues1.mStartVisibility != 0)
      {
        bool1 = bool2;
        if (paramTransitionValues1.mEndVisibility != 0) {}
      }
      else
      {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public boolean isVisible(TransitionValues paramTransitionValues)
  {
    boolean bool2 = false;
    if (paramTransitionValues == null) {
      return false;
    }
    int i = ((Integer)paramTransitionValues.values.get("android:visibility:visibility")).intValue();
    paramTransitionValues = (View)paramTransitionValues.values.get("android:visibility:parent");
    boolean bool1 = bool2;
    if (i == 0)
    {
      bool1 = bool2;
      if (paramTransitionValues != null) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2)
  {
    if (((this.mMode & 0x1) == 1) && (paramTransitionValues2 != null))
    {
      if (paramTransitionValues1 == null)
      {
        Object localObject = (View)paramTransitionValues2.view.getParent();
        TransitionValues localTransitionValues = getMatchedTransitionValues((View)localObject, false);
        localObject = getTransitionValues((View)localObject, false);
        if (getVisibilityChangeInfo(localTransitionValues, (TransitionValues)localObject).mVisibilityChange) {
          return null;
        }
      }
      return onAppear(paramViewGroup, paramTransitionValues2.view, paramTransitionValues1, paramTransitionValues2);
    }
    return null;
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    return null;
  }
  
  public Animator onDisappear(final ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2)
  {
    if ((this.mMode & 0x2) != 2) {
      return null;
    }
    if (paramTransitionValues1 == null) {
      return null;
    }
    final View localView1 = paramTransitionValues1.view;
    final Object localObject1;
    if (paramTransitionValues2 != null) {
      localObject1 = paramTransitionValues2.view;
    } else {
      localObject1 = null;
    }
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject2 = null;
    int j = 0;
    View localView2 = (View)localView1.getTag(R.id.save_overlay_view);
    int i;
    if (localView2 != null)
    {
      localObject1 = localView2;
      i = 1;
    }
    else
    {
      paramInt1 = 0;
      if ((localObject1 != null) && (((View)localObject1).getParent() != null))
      {
        if (paramInt2 == 4) {
          localObject2 = localObject1;
        } else if (localView1 == localObject1) {
          localObject2 = localObject1;
        } else {
          paramInt1 = 1;
        }
      }
      else if (localObject1 != null) {
        localObject3 = localObject1;
      } else {
        paramInt1 = 1;
      }
      localObject1 = localObject3;
      localObject4 = localObject2;
      i = j;
      if (paramInt1 != 0) {
        if (localView1.getParent() == null)
        {
          localObject1 = localView1;
          localObject4 = localObject2;
          i = j;
        }
        else
        {
          localObject1 = localObject3;
          localObject4 = localObject2;
          i = j;
          if ((localView1.getParent() instanceof View))
          {
            localView2 = (View)localView1.getParent();
            localObject1 = getTransitionValues(localView2, true);
            localObject4 = getMatchedTransitionValues(localView2, true);
            if (!getVisibilityChangeInfo((TransitionValues)localObject1, (TransitionValues)localObject4).mVisibilityChange)
            {
              localObject1 = TransitionUtils.copyViewImage(paramViewGroup, localView1, localView2);
              localObject4 = localObject2;
              i = j;
            }
            else
            {
              paramInt1 = localView2.getId();
              if (localView2.getParent() == null)
              {
                localObject1 = localObject3;
                localObject4 = localObject2;
                i = j;
                if (paramInt1 != -1)
                {
                  localObject1 = localObject3;
                  localObject4 = localObject2;
                  i = j;
                  if (paramViewGroup.findViewById(paramInt1) != null)
                  {
                    localObject1 = localObject3;
                    localObject4 = localObject2;
                    i = j;
                    if (this.mCanRemoveViews)
                    {
                      localObject1 = localView1;
                      localObject4 = localObject2;
                      i = j;
                    }
                  }
                }
              }
              else
              {
                i = j;
                localObject4 = localObject2;
                localObject1 = localObject3;
              }
            }
          }
        }
      }
    }
    if (localObject1 != null)
    {
      if (i == 0)
      {
        localObject2 = (int[])paramTransitionValues1.values.get("android:visibility:screenLocation");
        paramInt1 = localObject2[0];
        paramInt2 = localObject2[1];
        localObject2 = new int[2];
        paramViewGroup.getLocationOnScreen((int[])localObject2);
        ((View)localObject1).offsetLeftAndRight(paramInt1 - localObject2[0] - ((View)localObject1).getLeft());
        ((View)localObject1).offsetTopAndBottom(paramInt2 - localObject2[1] - ((View)localObject1).getTop());
        ViewGroupUtils.getOverlay(paramViewGroup).add((View)localObject1);
      }
      paramTransitionValues1 = onDisappear(paramViewGroup, (View)localObject1, paramTransitionValues1, paramTransitionValues2);
      if (i == 0) {
        if (paramTransitionValues1 == null)
        {
          ViewGroupUtils.getOverlay(paramViewGroup).remove((View)localObject1);
        }
        else
        {
          localView1.setTag(R.id.save_overlay_view, localObject1);
          addListener(new TransitionListenerAdapter()
          {
            public void onTransitionEnd(Transition paramAnonymousTransition)
            {
              localView1.setTag(R.id.save_overlay_view, null);
              ViewGroupUtils.getOverlay(paramViewGroup).remove(localObject1);
              paramAnonymousTransition.removeListener(this);
            }
            
            public void onTransitionPause(Transition paramAnonymousTransition)
            {
              ViewGroupUtils.getOverlay(paramViewGroup).remove(localObject1);
            }
            
            public void onTransitionResume(Transition paramAnonymousTransition)
            {
              if (localObject1.getParent() == null) {
                ViewGroupUtils.getOverlay(paramViewGroup).add(localObject1);
              } else {
                Visibility.this.cancel();
              }
            }
          });
        }
      }
      return paramTransitionValues1;
    }
    if (localObject4 != null)
    {
      paramInt1 = ((View)localObject4).getVisibility();
      ViewUtils.setTransitionVisibility((View)localObject4, 0);
      paramViewGroup = onDisappear(paramViewGroup, (View)localObject4, paramTransitionValues1, paramTransitionValues2);
      if (paramViewGroup != null)
      {
        paramTransitionValues1 = new DisappearListener((View)localObject4, paramInt2, true);
        paramViewGroup.addListener(paramTransitionValues1);
        AnimatorUtils.addPauseListener(paramViewGroup, paramTransitionValues1);
        addListener(paramTransitionValues1);
      }
      else
      {
        ViewUtils.setTransitionVisibility((View)localObject4, paramInt1);
      }
      return paramViewGroup;
    }
    return null;
  }
  
  public void setMode(int paramInt)
  {
    if ((paramInt & 0xFFFFFFFC) == 0)
    {
      this.mMode = paramInt;
      return;
    }
    throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
  }
  
  private static class DisappearListener
    extends AnimatorListenerAdapter
    implements Transition.TransitionListener, AnimatorUtils.AnimatorPauseListenerCompat
  {
    boolean mCanceled = false;
    private final int mFinalVisibility;
    private boolean mLayoutSuppressed;
    private final ViewGroup mParent;
    private final boolean mSuppressLayout;
    private final View mView;
    
    DisappearListener(View paramView, int paramInt, boolean paramBoolean)
    {
      this.mView = paramView;
      this.mFinalVisibility = paramInt;
      this.mParent = ((ViewGroup)paramView.getParent());
      this.mSuppressLayout = paramBoolean;
      suppressLayout(true);
    }
    
    private void hideViewWhenNotCanceled()
    {
      if (!this.mCanceled)
      {
        ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        ViewGroup localViewGroup = this.mParent;
        if (localViewGroup != null) {
          localViewGroup.invalidate();
        }
      }
      suppressLayout(false);
    }
    
    private void suppressLayout(boolean paramBoolean)
    {
      if ((this.mSuppressLayout) && (this.mLayoutSuppressed != paramBoolean))
      {
        ViewGroup localViewGroup = this.mParent;
        if (localViewGroup != null)
        {
          this.mLayoutSuppressed = paramBoolean;
          ViewGroupUtils.suppressLayout(localViewGroup, paramBoolean);
        }
      }
    }
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      this.mCanceled = true;
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      hideViewWhenNotCanceled();
    }
    
    public void onAnimationPause(Animator paramAnimator)
    {
      if (!this.mCanceled) {
        ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
      }
    }
    
    public void onAnimationRepeat(Animator paramAnimator) {}
    
    public void onAnimationResume(Animator paramAnimator)
    {
      if (!this.mCanceled) {
        ViewUtils.setTransitionVisibility(this.mView, 0);
      }
    }
    
    public void onAnimationStart(Animator paramAnimator) {}
    
    public void onTransitionCancel(Transition paramTransition) {}
    
    public void onTransitionEnd(Transition paramTransition)
    {
      hideViewWhenNotCanceled();
      paramTransition.removeListener(this);
    }
    
    public void onTransitionPause(Transition paramTransition)
    {
      suppressLayout(false);
    }
    
    public void onTransitionResume(Transition paramTransition)
    {
      suppressLayout(true);
    }
    
    public void onTransitionStart(Transition paramTransition) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  private static class VisibilityInfo
  {
    ViewGroup mEndParent;
    int mEndVisibility;
    boolean mFadeIn;
    ViewGroup mStartParent;
    int mStartVisibility;
    boolean mVisibilityChange;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/Visibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */