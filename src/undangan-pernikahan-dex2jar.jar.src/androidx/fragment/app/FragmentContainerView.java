package androidx.fragment.app;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.R.styleable;
import java.util.ArrayList;

public final class FragmentContainerView
  extends FrameLayout
{
  private View.OnApplyWindowInsetsListener mApplyWindowInsetsListener;
  private ArrayList<View> mDisappearingFragmentChildren;
  private boolean mDrawDisappearingViewsFirst = true;
  private ArrayList<View> mTransitioningFragmentViews;
  
  public FragmentContainerView(Context paramContext)
  {
    super(paramContext);
  }
  
  public FragmentContainerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public FragmentContainerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (paramAttributeSet != null)
    {
      String str1 = paramAttributeSet.getClassAttribute();
      String str2 = "class";
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FragmentContainerView);
      paramAttributeSet = str1;
      paramContext = str2;
      if (str1 == null)
      {
        paramAttributeSet = localTypedArray.getString(R.styleable.FragmentContainerView_android_name);
        paramContext = "android:name";
      }
      localTypedArray.recycle();
      if ((paramAttributeSet != null) && (!isInEditMode())) {
        throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to use " + paramContext + "=\"" + paramAttributeSet + "\"");
      }
    }
  }
  
  FragmentContainerView(Context paramContext, AttributeSet paramAttributeSet, FragmentManager paramFragmentManager)
  {
    super(paramContext, paramAttributeSet);
    String str = paramAttributeSet.getClassAttribute();
    Object localObject2 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.FragmentContainerView);
    Object localObject1 = str;
    if (str == null) {
      localObject1 = ((TypedArray)localObject2).getString(R.styleable.FragmentContainerView_android_name);
    }
    str = ((TypedArray)localObject2).getString(R.styleable.FragmentContainerView_android_tag);
    ((TypedArray)localObject2).recycle();
    int i = getId();
    localObject2 = paramFragmentManager.findFragmentById(i);
    if ((localObject1 != null) && (localObject2 == null))
    {
      if (i <= 0)
      {
        if (str != null) {
          paramContext = " with tag " + str;
        } else {
          paramContext = "";
        }
        throw new IllegalStateException("FragmentContainerView must have an android:id to add Fragment " + (String)localObject1 + paramContext);
      }
      localObject1 = paramFragmentManager.getFragmentFactory().instantiate(paramContext.getClassLoader(), (String)localObject1);
      ((Fragment)localObject1).onInflate(paramContext, paramAttributeSet, null);
      paramFragmentManager.beginTransaction().setReorderingAllowed(true).add(this, (Fragment)localObject1, str).commitNowAllowingStateLoss();
    }
    paramFragmentManager.onContainerAvailable(this);
  }
  
  private void addDisappearingFragmentView(View paramView)
  {
    ArrayList localArrayList = this.mTransitioningFragmentViews;
    if ((localArrayList != null) && (localArrayList.contains(paramView)))
    {
      if (this.mDisappearingFragmentChildren == null) {
        this.mDisappearingFragmentChildren = new ArrayList();
      }
      this.mDisappearingFragmentChildren.add(paramView);
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (FragmentManager.getViewFragment(paramView) != null)
    {
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    }
    throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + paramView + " is not associated with a Fragment.");
  }
  
  protected boolean addViewInLayout(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams, boolean paramBoolean)
  {
    if (FragmentManager.getViewFragment(paramView) != null) {
      return super.addViewInLayout(paramView, paramInt, paramLayoutParams, paramBoolean);
    }
    throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + paramView + " is not associated with a Fragment.");
  }
  
  public WindowInsets dispatchApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    WindowInsetsCompat localWindowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(paramWindowInsets);
    View.OnApplyWindowInsetsListener localOnApplyWindowInsetsListener = this.mApplyWindowInsetsListener;
    if (localOnApplyWindowInsetsListener != null) {
      localWindowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(localOnApplyWindowInsetsListener.onApplyWindowInsets(this, paramWindowInsets));
    } else {
      localWindowInsetsCompat = ViewCompat.onApplyWindowInsets(this, localWindowInsetsCompat);
    }
    if (!localWindowInsetsCompat.isConsumed())
    {
      int j = getChildCount();
      for (int i = 0; i < j; i++) {
        ViewCompat.dispatchApplyWindowInsets(getChildAt(i), localWindowInsetsCompat);
      }
    }
    return paramWindowInsets;
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    if ((this.mDrawDisappearingViewsFirst) && (this.mDisappearingFragmentChildren != null)) {
      for (int i = 0; i < this.mDisappearingFragmentChildren.size(); i++) {
        super.drawChild(paramCanvas, (View)this.mDisappearingFragmentChildren.get(i), getDrawingTime());
      }
    }
    super.dispatchDraw(paramCanvas);
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    if (this.mDrawDisappearingViewsFirst)
    {
      ArrayList localArrayList = this.mDisappearingFragmentChildren;
      if ((localArrayList != null) && (localArrayList.size() > 0) && (this.mDisappearingFragmentChildren.contains(paramView))) {
        return false;
      }
    }
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  public void endViewTransition(View paramView)
  {
    ArrayList localArrayList = this.mTransitioningFragmentViews;
    if (localArrayList != null)
    {
      localArrayList.remove(paramView);
      localArrayList = this.mDisappearingFragmentChildren;
      if ((localArrayList != null) && (localArrayList.remove(paramView))) {
        this.mDrawDisappearingViewsFirst = true;
      }
    }
    super.endViewTransition(paramView);
  }
  
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    return paramWindowInsets;
  }
  
  public void removeAllViewsInLayout()
  {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      addDisappearingFragmentView(getChildAt(i));
    }
    super.removeAllViewsInLayout();
  }
  
  protected void removeDetachedView(View paramView, boolean paramBoolean)
  {
    if (paramBoolean) {
      addDisappearingFragmentView(paramView);
    }
    super.removeDetachedView(paramView, paramBoolean);
  }
  
  public void removeView(View paramView)
  {
    addDisappearingFragmentView(paramView);
    super.removeView(paramView);
  }
  
  public void removeViewAt(int paramInt)
  {
    addDisappearingFragmentView(getChildAt(paramInt));
    super.removeViewAt(paramInt);
  }
  
  public void removeViewInLayout(View paramView)
  {
    addDisappearingFragmentView(paramView);
    super.removeViewInLayout(paramView);
  }
  
  public void removeViews(int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
      addDisappearingFragmentView(getChildAt(i));
    }
    super.removeViews(paramInt1, paramInt2);
  }
  
  public void removeViewsInLayout(int paramInt1, int paramInt2)
  {
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
      addDisappearingFragmentView(getChildAt(i));
    }
    super.removeViewsInLayout(paramInt1, paramInt2);
  }
  
  void setDrawDisappearingViewsLast(boolean paramBoolean)
  {
    this.mDrawDisappearingViewsFirst = paramBoolean;
  }
  
  public void setLayoutTransition(LayoutTransition paramLayoutTransition)
  {
    if (Build.VERSION.SDK_INT < 18)
    {
      super.setLayoutTransition(paramLayoutTransition);
      return;
    }
    throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
  }
  
  public void setOnApplyWindowInsetsListener(View.OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
  {
    this.mApplyWindowInsetsListener = paramOnApplyWindowInsetsListener;
  }
  
  public void startViewTransition(View paramView)
  {
    if (paramView.getParent() == this)
    {
      if (this.mTransitioningFragmentViews == null) {
        this.mTransitioningFragmentViews = new ArrayList();
      }
      this.mTransitioningFragmentViews.add(paramView);
    }
    super.startViewTransition(paramView);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentContainerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */