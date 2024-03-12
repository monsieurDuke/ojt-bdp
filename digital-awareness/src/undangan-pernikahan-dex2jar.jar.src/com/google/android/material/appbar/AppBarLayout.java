package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.integer;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppBarLayout
  extends LinearLayout
  implements CoordinatorLayout.AttachedBehavior
{
  private static final int DEF_STYLE_RES = R.style.Widget_Design_AppBarLayout;
  private static final int INVALID_SCROLL_RANGE = -1;
  static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
  static final int PENDING_ACTION_COLLAPSED = 2;
  static final int PENDING_ACTION_EXPANDED = 1;
  static final int PENDING_ACTION_FORCE = 8;
  static final int PENDING_ACTION_NONE = 0;
  private Behavior behavior;
  private int currentOffset;
  private int downPreScrollRange = -1;
  private int downScrollRange = -1;
  private ValueAnimator elevationOverlayAnimator;
  private boolean haveChildWithInterpolator;
  private WindowInsetsCompat lastInsets;
  private boolean liftOnScroll;
  private final List<LiftOnScrollListener> liftOnScrollListeners = new ArrayList();
  private WeakReference<View> liftOnScrollTargetView;
  private int liftOnScrollTargetViewId;
  private boolean liftable;
  private boolean liftableOverride;
  private boolean lifted;
  private List<BaseOnOffsetChangedListener> listeners;
  private int pendingAction = 0;
  private Drawable statusBarForeground;
  private int[] tmpStatesArray;
  private int totalScrollRange = -1;
  
  public AppBarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.appBarLayoutStyle);
  }
  
  public AppBarLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    setOrientation(1);
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (getOutlineProvider() == ViewOutlineProvider.BACKGROUND) {
        ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
      }
      ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, paramAttributeSet, paramInt, i);
    }
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.AppBarLayout, paramInt, i, new int[0]);
    ViewCompat.setBackground(this, paramAttributeSet.getDrawable(R.styleable.AppBarLayout_android_background));
    if ((getBackground() instanceof ColorDrawable))
    {
      ColorDrawable localColorDrawable = (ColorDrawable)getBackground();
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
      localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(localColorDrawable.getColor()));
      localMaterialShapeDrawable.initializeElevationOverlay(paramContext);
      ViewCompat.setBackground(this, localMaterialShapeDrawable);
    }
    if (paramAttributeSet.hasValue(R.styleable.AppBarLayout_expanded)) {
      setExpanded(paramAttributeSet.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
    }
    if ((Build.VERSION.SDK_INT >= 21) && (paramAttributeSet.hasValue(R.styleable.AppBarLayout_elevation))) {
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, paramAttributeSet.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
    }
    if (Build.VERSION.SDK_INT >= 26)
    {
      if (paramAttributeSet.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
        setKeyboardNavigationCluster(paramAttributeSet.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
      }
      if (paramAttributeSet.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
        setTouchscreenBlocksFocus(paramAttributeSet.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
      }
    }
    this.liftOnScroll = paramAttributeSet.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
    this.liftOnScrollTargetViewId = paramAttributeSet.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
    setStatusBarForeground(paramAttributeSet.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
    paramAttributeSet.recycle();
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        return AppBarLayout.this.onWindowInsetChanged(paramAnonymousWindowInsetsCompat);
      }
    });
  }
  
  private void clearLiftOnScrollTargetView()
  {
    WeakReference localWeakReference = this.liftOnScrollTargetView;
    if (localWeakReference != null) {
      localWeakReference.clear();
    }
    this.liftOnScrollTargetView = null;
  }
  
  private View findLiftOnScrollTargetView(View paramView)
  {
    if (this.liftOnScrollTargetView == null)
    {
      int i = this.liftOnScrollTargetViewId;
      if (i != -1)
      {
        View localView = null;
        if (paramView != null) {
          localView = paramView.findViewById(i);
        }
        paramView = localView;
        if (localView == null)
        {
          paramView = localView;
          if ((getParent() instanceof ViewGroup)) {
            paramView = ((ViewGroup)getParent()).findViewById(this.liftOnScrollTargetViewId);
          }
        }
        if (paramView != null) {
          this.liftOnScrollTargetView = new WeakReference(paramView);
        }
      }
    }
    paramView = this.liftOnScrollTargetView;
    if (paramView != null) {
      paramView = (View)paramView.get();
    } else {
      paramView = null;
    }
    return paramView;
  }
  
  private boolean hasCollapsibleChild()
  {
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      if (((LayoutParams)getChildAt(i).getLayoutParams()).isCollapsible()) {
        return true;
      }
      i++;
    }
    return false;
  }
  
  private void invalidateScrollRanges()
  {
    Object localObject = this.behavior;
    if ((localObject != null) && (this.totalScrollRange != -1) && (this.pendingAction == 0)) {
      localObject = ((Behavior)localObject).saveScrollState(AbsSavedState.EMPTY_STATE, this);
    } else {
      localObject = null;
    }
    this.totalScrollRange = -1;
    this.downPreScrollRange = -1;
    this.downScrollRange = -1;
    if (localObject != null) {
      this.behavior.restoreScrollState((AppBarLayout.BaseBehavior.SavedState)localObject, false);
    }
  }
  
  private void setExpanded(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    int i;
    if (paramBoolean1) {
      i = 1;
    } else {
      i = 2;
    }
    int k = 0;
    int j;
    if (paramBoolean2) {
      j = 4;
    } else {
      j = 0;
    }
    if (paramBoolean3) {
      k = 8;
    }
    this.pendingAction = (i | j | k);
    requestLayout();
  }
  
  private boolean setLiftableState(boolean paramBoolean)
  {
    if (this.liftable != paramBoolean)
    {
      this.liftable = paramBoolean;
      refreshDrawableState();
      return true;
    }
    return false;
  }
  
  private boolean shouldDrawStatusBarForeground()
  {
    boolean bool;
    if ((this.statusBarForeground != null) && (getTopInset() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldOffsetFirstChild()
  {
    int i = getChildCount();
    boolean bool2 = false;
    if (i > 0)
    {
      View localView = getChildAt(0);
      boolean bool1 = bool2;
      if (localView.getVisibility() != 8)
      {
        bool1 = bool2;
        if (!ViewCompat.getFitsSystemWindows(localView)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  private void startLiftOnScrollElevationOverlayAnimation(final MaterialShapeDrawable paramMaterialShapeDrawable, boolean paramBoolean)
  {
    float f1 = getResources().getDimension(R.dimen.design_appbar_elevation);
    float f3 = 0.0F;
    float f2;
    if (paramBoolean) {
      f2 = 0.0F;
    } else {
      f2 = f1;
    }
    if (paramBoolean) {
      f3 = f1;
    }
    ValueAnimator localValueAnimator = this.elevationOverlayAnimator;
    if (localValueAnimator != null) {
      localValueAnimator.cancel();
    }
    localValueAnimator = ValueAnimator.ofFloat(new float[] { f2, f3 });
    this.elevationOverlayAnimator = localValueAnimator;
    localValueAnimator.setDuration(getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
    this.elevationOverlayAnimator.setInterpolator(com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR);
    this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        paramMaterialShapeDrawable.setElevation(f);
        if ((AppBarLayout.this.statusBarForeground instanceof MaterialShapeDrawable)) {
          ((MaterialShapeDrawable)AppBarLayout.this.statusBarForeground).setElevation(f);
        }
        paramAnonymousValueAnimator = AppBarLayout.this.liftOnScrollListeners.iterator();
        while (paramAnonymousValueAnimator.hasNext()) {
          ((AppBarLayout.LiftOnScrollListener)paramAnonymousValueAnimator.next()).onUpdate(f, paramMaterialShapeDrawable.getResolvedTintColor());
        }
      }
    });
    this.elevationOverlayAnimator.start();
  }
  
  private void updateWillNotDraw()
  {
    setWillNotDraw(shouldDrawStatusBarForeground() ^ true);
  }
  
  public void addLiftOnScrollListener(LiftOnScrollListener paramLiftOnScrollListener)
  {
    this.liftOnScrollListeners.add(paramLiftOnScrollListener);
  }
  
  public void addOnOffsetChangedListener(BaseOnOffsetChangedListener paramBaseOnOffsetChangedListener)
  {
    if (this.listeners == null) {
      this.listeners = new ArrayList();
    }
    if ((paramBaseOnOffsetChangedListener != null) && (!this.listeners.contains(paramBaseOnOffsetChangedListener))) {
      this.listeners.add(paramBaseOnOffsetChangedListener);
    }
  }
  
  public void addOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener)
  {
    addOnOffsetChangedListener(paramOnOffsetChangedListener);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void clearLiftOnScrollListener()
  {
    this.liftOnScrollListeners.clear();
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if (shouldDrawStatusBarForeground())
    {
      int i = paramCanvas.save();
      paramCanvas.translate(0.0F, -this.currentOffset);
      this.statusBarForeground.draw(paramCanvas);
      paramCanvas.restoreToCount(i);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable localDrawable = this.statusBarForeground;
    if ((localDrawable != null) && (localDrawable.isStateful()) && (localDrawable.setState(arrayOfInt))) {
      invalidateDrawable(localDrawable);
    }
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((Build.VERSION.SDK_INT >= 19) && ((paramLayoutParams instanceof LinearLayout.LayoutParams))) {
      return new LayoutParams((LinearLayout.LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public CoordinatorLayout.Behavior<AppBarLayout> getBehavior()
  {
    Behavior localBehavior = new Behavior();
    this.behavior = localBehavior;
    return localBehavior;
  }
  
  int getDownNestedPreScrollRange()
  {
    int i = this.downPreScrollRange;
    if (i != -1) {
      return i;
    }
    int k = 0;
    int j = getChildCount() - 1;
    while (j >= 0)
    {
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int n = localView.getMeasuredHeight();
      i = localLayoutParams.scrollFlags;
      if ((i & 0x5) == 5)
      {
        int m = localLayoutParams.topMargin + localLayoutParams.bottomMargin;
        if ((i & 0x8) != 0) {
          i = m + ViewCompat.getMinimumHeight(localView);
        } else if ((i & 0x2) != 0) {
          i = m + (n - ViewCompat.getMinimumHeight(localView));
        } else {
          i = m + n;
        }
        m = i;
        if (j == 0)
        {
          m = i;
          if (ViewCompat.getFitsSystemWindows(localView)) {
            m = Math.min(i, n - getTopInset());
          }
        }
        i = k + m;
      }
      else
      {
        i = k;
        if (k > 0) {
          break;
        }
      }
      j--;
      k = i;
    }
    i = Math.max(0, k);
    this.downPreScrollRange = i;
    return i;
  }
  
  int getDownNestedScrollRange()
  {
    int i = this.downScrollRange;
    if (i != -1) {
      return i;
    }
    i = 0;
    int j = 0;
    int m = getChildCount();
    int k;
    for (;;)
    {
      k = i;
      if (j >= m) {
        break;
      }
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i2 = localView.getMeasuredHeight();
      int i3 = localLayoutParams.topMargin;
      int i1 = localLayoutParams.bottomMargin;
      int n = localLayoutParams.scrollFlags;
      k = i;
      if ((n & 0x1) == 0) {
        break;
      }
      i += i2 + (i3 + i1);
      if ((n & 0x2) != 0)
      {
        k = i - ViewCompat.getMinimumHeight(localView);
        break;
      }
      j++;
    }
    i = Math.max(0, k);
    this.downScrollRange = i;
    return i;
  }
  
  public int getLiftOnScrollTargetViewId()
  {
    return this.liftOnScrollTargetViewId;
  }
  
  public final int getMinimumHeightForVisibleOverlappingContent()
  {
    int j = getTopInset();
    int i = ViewCompat.getMinimumHeight(this);
    if (i != 0) {
      return i * 2 + j;
    }
    i = getChildCount();
    if (i >= 1) {
      i = ViewCompat.getMinimumHeight(getChildAt(i - 1));
    } else {
      i = 0;
    }
    if (i != 0) {
      return i * 2 + j;
    }
    return getHeight() / 3;
  }
  
  int getPendingAction()
  {
    return this.pendingAction;
  }
  
  public Drawable getStatusBarForeground()
  {
    return this.statusBarForeground;
  }
  
  @Deprecated
  public float getTargetElevation()
  {
    return 0.0F;
  }
  
  final int getTopInset()
  {
    WindowInsetsCompat localWindowInsetsCompat = this.lastInsets;
    int i;
    if (localWindowInsetsCompat != null) {
      i = localWindowInsetsCompat.getSystemWindowInsetTop();
    } else {
      i = 0;
    }
    return i;
  }
  
  public final int getTotalScrollRange()
  {
    int i = this.totalScrollRange;
    if (i != -1) {
      return i;
    }
    i = 0;
    int j = 0;
    int m = getChildCount();
    int k;
    for (;;)
    {
      k = i;
      if (j >= m) {
        break;
      }
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i1 = localView.getMeasuredHeight();
      int n = localLayoutParams.scrollFlags;
      k = i;
      if ((n & 0x1) == 0) {
        break;
      }
      k = i + (localLayoutParams.topMargin + i1 + localLayoutParams.bottomMargin);
      i = k;
      if (j == 0)
      {
        i = k;
        if (ViewCompat.getFitsSystemWindows(localView)) {
          i = k - getTopInset();
        }
      }
      if ((n & 0x2) != 0)
      {
        k = i - ViewCompat.getMinimumHeight(localView);
        break;
      }
      j++;
    }
    i = Math.max(0, k);
    this.totalScrollRange = i;
    return i;
  }
  
  int getUpNestedPreScrollRange()
  {
    return getTotalScrollRange();
  }
  
  boolean hasChildWithInterpolator()
  {
    return this.haveChildWithInterpolator;
  }
  
  boolean hasScrollableChildren()
  {
    boolean bool;
    if (getTotalScrollRange() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isLiftOnScroll()
  {
    return this.liftOnScroll;
  }
  
  public boolean isLifted()
  {
    return this.lifted;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this);
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    if (this.tmpStatesArray == null) {
      this.tmpStatesArray = new int[4];
    }
    int[] arrayOfInt1 = this.tmpStatesArray;
    int[] arrayOfInt2 = super.onCreateDrawableState(arrayOfInt1.length + paramInt);
    if (this.liftable) {
      paramInt = R.attr.state_liftable;
    } else {
      paramInt = -R.attr.state_liftable;
    }
    arrayOfInt1[0] = paramInt;
    if ((this.liftable) && (this.lifted)) {
      paramInt = R.attr.state_lifted;
    } else {
      paramInt = -R.attr.state_lifted;
    }
    arrayOfInt1[1] = paramInt;
    if (this.liftable) {
      paramInt = R.attr.state_collapsible;
    } else {
      paramInt = -R.attr.state_collapsible;
    }
    arrayOfInt1[2] = paramInt;
    if ((this.liftable) && (this.lifted)) {
      paramInt = R.attr.state_collapsed;
    } else {
      paramInt = -R.attr.state_collapsed;
    }
    arrayOfInt1[3] = paramInt;
    return mergeDrawableStates(arrayOfInt2, arrayOfInt1);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    clearLiftOnScrollTargetView();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    paramBoolean = ViewCompat.getFitsSystemWindows(this);
    boolean bool = true;
    if ((paramBoolean) && (shouldOffsetFirstChild()))
    {
      paramInt2 = getTopInset();
      for (paramInt1 = getChildCount() - 1; paramInt1 >= 0; paramInt1--) {
        ViewCompat.offsetTopAndBottom(getChildAt(paramInt1), paramInt2);
      }
    }
    invalidateScrollRanges();
    this.haveChildWithInterpolator = false;
    paramInt1 = 0;
    paramInt2 = getChildCount();
    while (paramInt1 < paramInt2)
    {
      if (((LayoutParams)getChildAt(paramInt1).getLayoutParams()).getScrollInterpolator() != null)
      {
        this.haveChildWithInterpolator = true;
        break;
      }
      paramInt1++;
    }
    Drawable localDrawable = this.statusBarForeground;
    if (localDrawable != null) {
      localDrawable.setBounds(0, 0, getWidth(), getTopInset());
    }
    if (!this.liftableOverride)
    {
      paramBoolean = bool;
      if (!this.liftOnScroll) {
        if (hasCollapsibleChild()) {
          paramBoolean = bool;
        } else {
          paramBoolean = false;
        }
      }
      setLiftableState(paramBoolean);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getMode(paramInt2);
    if ((i != 1073741824) && (ViewCompat.getFitsSystemWindows(this)) && (shouldOffsetFirstChild()))
    {
      paramInt1 = getMeasuredHeight();
      switch (i)
      {
      default: 
        break;
      case 0: 
        paramInt1 += getTopInset();
        break;
      case -2147483648: 
        paramInt1 = MathUtils.clamp(getMeasuredHeight() + getTopInset(), 0, View.MeasureSpec.getSize(paramInt2));
      }
      setMeasuredDimension(getMeasuredWidth(), paramInt1);
    }
    invalidateScrollRanges();
  }
  
  void onOffsetChanged(int paramInt)
  {
    this.currentOffset = paramInt;
    if (!willNotDraw()) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
    Object localObject = this.listeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (BaseOnOffsetChangedListener)this.listeners.get(i);
        if (localObject != null) {
          ((BaseOnOffsetChangedListener)localObject).onOffsetChanged(this, paramInt);
        }
        i++;
      }
    }
  }
  
  WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat paramWindowInsetsCompat)
  {
    WindowInsetsCompat localWindowInsetsCompat = null;
    if (ViewCompat.getFitsSystemWindows(this)) {
      localWindowInsetsCompat = paramWindowInsetsCompat;
    }
    if (!ObjectsCompat.equals(this.lastInsets, localWindowInsetsCompat))
    {
      this.lastInsets = localWindowInsetsCompat;
      updateWillNotDraw();
      requestLayout();
    }
    return paramWindowInsetsCompat;
  }
  
  public boolean removeLiftOnScrollListener(LiftOnScrollListener paramLiftOnScrollListener)
  {
    return this.liftOnScrollListeners.remove(paramLiftOnScrollListener);
  }
  
  public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener paramBaseOnOffsetChangedListener)
  {
    List localList = this.listeners;
    if ((localList != null) && (paramBaseOnOffsetChangedListener != null)) {
      localList.remove(paramBaseOnOffsetChangedListener);
    }
  }
  
  public void removeOnOffsetChangedListener(OnOffsetChangedListener paramOnOffsetChangedListener)
  {
    removeOnOffsetChangedListener(paramOnOffsetChangedListener);
  }
  
  void resetPendingAction()
  {
    this.pendingAction = 0;
  }
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    MaterialShapeUtils.setElevation(this, paramFloat);
  }
  
  public void setExpanded(boolean paramBoolean)
  {
    setExpanded(paramBoolean, ViewCompat.isLaidOut(this));
  }
  
  public void setExpanded(boolean paramBoolean1, boolean paramBoolean2)
  {
    setExpanded(paramBoolean1, paramBoolean2, true);
  }
  
  public void setLiftOnScroll(boolean paramBoolean)
  {
    this.liftOnScroll = paramBoolean;
  }
  
  public void setLiftOnScrollTargetViewId(int paramInt)
  {
    this.liftOnScrollTargetViewId = paramInt;
    clearLiftOnScrollTargetView();
  }
  
  public boolean setLiftable(boolean paramBoolean)
  {
    this.liftableOverride = true;
    return setLiftableState(paramBoolean);
  }
  
  public void setLiftableOverrideEnabled(boolean paramBoolean)
  {
    this.liftableOverride = paramBoolean;
  }
  
  public boolean setLifted(boolean paramBoolean)
  {
    return setLiftedState(paramBoolean, true);
  }
  
  boolean setLiftedState(boolean paramBoolean)
  {
    return setLiftedState(paramBoolean, this.liftableOverride ^ true);
  }
  
  boolean setLiftedState(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramBoolean2) && (this.lifted != paramBoolean1))
    {
      this.lifted = paramBoolean1;
      refreshDrawableState();
      if ((this.liftOnScroll) && ((getBackground() instanceof MaterialShapeDrawable))) {
        startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable)getBackground(), paramBoolean1);
      }
      return true;
    }
    return false;
  }
  
  public void setOrientation(int paramInt)
  {
    if (paramInt == 1)
    {
      super.setOrientation(paramInt);
      return;
    }
    throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
  }
  
  public void setStatusBarForeground(Drawable paramDrawable)
  {
    Drawable localDrawable2 = this.statusBarForeground;
    if (localDrawable2 != paramDrawable)
    {
      Drawable localDrawable1 = null;
      if (localDrawable2 != null) {
        localDrawable2.setCallback(null);
      }
      if (paramDrawable != null) {
        localDrawable1 = paramDrawable.mutate();
      }
      this.statusBarForeground = localDrawable1;
      if (localDrawable1 != null)
      {
        if (localDrawable1.isStateful()) {
          this.statusBarForeground.setState(getDrawableState());
        }
        DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection(this));
        paramDrawable = this.statusBarForeground;
        boolean bool;
        if (getVisibility() == 0) {
          bool = true;
        } else {
          bool = false;
        }
        paramDrawable.setVisible(bool, false);
        this.statusBarForeground.setCallback(this);
      }
      updateWillNotDraw();
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setStatusBarForegroundColor(int paramInt)
  {
    setStatusBarForeground(new ColorDrawable(paramInt));
  }
  
  public void setStatusBarForegroundResource(int paramInt)
  {
    setStatusBarForeground(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  @Deprecated
  public void setTargetElevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, paramFloat);
    }
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    Drawable localDrawable = this.statusBarForeground;
    if (localDrawable != null) {
      localDrawable.setVisible(bool, false);
    }
  }
  
  boolean shouldLift(View paramView)
  {
    View localView2 = findLiftOnScrollTargetView(paramView);
    View localView1 = localView2;
    if (localView2 == null) {
      localView1 = paramView;
    }
    boolean bool;
    if ((localView1 != null) && ((localView1.canScrollVertically(-1)) || (localView1.getScrollY() > 0))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.statusBarForeground)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  protected static class BaseBehavior<T extends AppBarLayout>
    extends HeaderBehavior<T>
  {
    private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
    private boolean coordinatorLayoutA11yScrollable;
    private WeakReference<View> lastNestedScrollingChildRef;
    private int lastStartedType;
    private ValueAnimator offsetAnimator;
    private int offsetDelta;
    private BaseDragCallback onDragCallback;
    private SavedState savedState;
    
    public BaseBehavior() {}
    
    public BaseBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    private boolean addAccessibilityScrollActions(final CoordinatorLayout paramCoordinatorLayout, final T paramT, final View paramView)
    {
      boolean bool1 = false;
      if (getTopBottomOffsetForScrollingSibling() != -paramT.getTotalScrollRange())
      {
        addActionToExpand(paramCoordinatorLayout, paramT, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
        bool1 = true;
      }
      boolean bool2 = bool1;
      if (getTopBottomOffsetForScrollingSibling() != 0) {
        if (paramView.canScrollVertically(-1))
        {
          final int i = -paramT.getDownNestedPreScrollRange();
          if (i != 0)
          {
            ViewCompat.replaceAccessibilityAction(paramCoordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand()
            {
              public boolean perform(View paramAnonymousView, AccessibilityViewCommand.CommandArguments paramAnonymousCommandArguments)
              {
                AppBarLayout.BaseBehavior.this.onNestedPreScroll(paramCoordinatorLayout, paramT, paramView, 0, i, new int[] { 0, 0 }, 1);
                return true;
              }
            });
            bool1 = true;
          }
          bool2 = bool1;
        }
        else
        {
          addActionToExpand(paramCoordinatorLayout, paramT, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
          bool2 = true;
        }
      }
      return bool2;
    }
    
    private void addActionToExpand(CoordinatorLayout paramCoordinatorLayout, final T paramT, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat, final boolean paramBoolean)
    {
      ViewCompat.replaceAccessibilityAction(paramCoordinatorLayout, paramAccessibilityActionCompat, null, new AccessibilityViewCommand()
      {
        public boolean perform(View paramAnonymousView, AccessibilityViewCommand.CommandArguments paramAnonymousCommandArguments)
        {
          paramT.setExpanded(paramBoolean);
          return true;
        }
      });
    }
    
    private void animateOffsetTo(CoordinatorLayout paramCoordinatorLayout, T paramT, int paramInt, float paramFloat)
    {
      int i = Math.abs(getTopBottomOffsetForScrollingSibling() - paramInt);
      paramFloat = Math.abs(paramFloat);
      if (paramFloat > 0.0F) {
        i = Math.round(i / paramFloat * 1000.0F) * 3;
      } else {
        i = (int)((1.0F + i / paramT.getHeight()) * 150.0F);
      }
      animateOffsetWithDuration(paramCoordinatorLayout, paramT, paramInt, i);
    }
    
    private void animateOffsetWithDuration(final CoordinatorLayout paramCoordinatorLayout, final T paramT, int paramInt1, int paramInt2)
    {
      int i = getTopBottomOffsetForScrollingSibling();
      if (i == paramInt1)
      {
        paramCoordinatorLayout = this.offsetAnimator;
        if ((paramCoordinatorLayout != null) && (paramCoordinatorLayout.isRunning())) {
          this.offsetAnimator.cancel();
        }
        return;
      }
      ValueAnimator localValueAnimator = this.offsetAnimator;
      if (localValueAnimator == null)
      {
        localValueAnimator = new ValueAnimator();
        this.offsetAnimator = localValueAnimator;
        localValueAnimator.setInterpolator(com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR);
        this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
          public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
          {
            AppBarLayout.BaseBehavior.this.setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
          }
        });
      }
      else
      {
        localValueAnimator.cancel();
      }
      this.offsetAnimator.setDuration(Math.min(paramInt2, 600));
      this.offsetAnimator.setIntValues(new int[] { i, paramInt1 });
      this.offsetAnimator.start();
    }
    
    private int calculateSnapOffset(int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramInt1 < (paramInt2 + paramInt3) / 2) {
        paramInt1 = paramInt2;
      } else {
        paramInt1 = paramInt3;
      }
      return paramInt1;
    }
    
    private boolean canScrollChildren(CoordinatorLayout paramCoordinatorLayout, T paramT, View paramView)
    {
      boolean bool;
      if ((paramT.hasScrollableChildren()) && (paramCoordinatorLayout.getHeight() - paramView.getHeight() <= paramT.getHeight())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private static boolean checkFlag(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 & paramInt2) == paramInt2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private boolean childrenHaveScrollFlags(AppBarLayout paramAppBarLayout)
    {
      int j = paramAppBarLayout.getChildCount();
      for (int i = 0; i < j; i++) {
        if (((AppBarLayout.LayoutParams)paramAppBarLayout.getChildAt(i).getLayoutParams()).scrollFlags != 0) {
          return true;
        }
      }
      return false;
    }
    
    private View findFirstScrollingChild(CoordinatorLayout paramCoordinatorLayout)
    {
      int i = 0;
      int j = paramCoordinatorLayout.getChildCount();
      while (i < j)
      {
        View localView = paramCoordinatorLayout.getChildAt(i);
        if ((!(localView instanceof NestedScrollingChild)) && (!(localView instanceof ListView)) && (!(localView instanceof ScrollView))) {
          i++;
        } else {
          return localView;
        }
      }
      return null;
    }
    
    private static View getAppBarChildOnOffset(AppBarLayout paramAppBarLayout, int paramInt)
    {
      int i = Math.abs(paramInt);
      paramInt = 0;
      int j = paramAppBarLayout.getChildCount();
      while (paramInt < j)
      {
        View localView = paramAppBarLayout.getChildAt(paramInt);
        if ((i >= localView.getTop()) && (i <= localView.getBottom())) {
          return localView;
        }
        paramInt++;
      }
      return null;
    }
    
    private int getChildIndexOnOffset(T paramT, int paramInt)
    {
      int i = 0;
      int i1 = paramT.getChildCount();
      while (i < i1)
      {
        Object localObject = paramT.getChildAt(i);
        int n = ((View)localObject).getTop();
        int m = ((View)localObject).getBottom();
        localObject = (AppBarLayout.LayoutParams)((View)localObject).getLayoutParams();
        int k = n;
        int j = m;
        if (checkFlag(((AppBarLayout.LayoutParams)localObject).getScrollFlags(), 32))
        {
          k = n - ((AppBarLayout.LayoutParams)localObject).topMargin;
          j = m + ((AppBarLayout.LayoutParams)localObject).bottomMargin;
        }
        if ((k <= -paramInt) && (j >= -paramInt)) {
          return i;
        }
        i++;
      }
      return -1;
    }
    
    private View getChildWithScrollingBehavior(CoordinatorLayout paramCoordinatorLayout)
    {
      int j = paramCoordinatorLayout.getChildCount();
      for (int i = 0; i < j; i++)
      {
        View localView = paramCoordinatorLayout.getChildAt(i);
        if ((((CoordinatorLayout.LayoutParams)localView.getLayoutParams()).getBehavior() instanceof AppBarLayout.ScrollingViewBehavior)) {
          return localView;
        }
      }
      return null;
    }
    
    private int interpolateOffset(T paramT, int paramInt)
    {
      int k = Math.abs(paramInt);
      int i = 0;
      int j = paramT.getChildCount();
      while (i < j)
      {
        View localView = paramT.getChildAt(i);
        AppBarLayout.LayoutParams localLayoutParams = (AppBarLayout.LayoutParams)localView.getLayoutParams();
        Interpolator localInterpolator = localLayoutParams.getScrollInterpolator();
        if ((k >= localView.getTop()) && (k <= localView.getBottom()))
        {
          if (localInterpolator == null) {
            break;
          }
          i = 0;
          int m = localLayoutParams.getScrollFlags();
          if ((m & 0x1) != 0)
          {
            j = 0 + (localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin);
            i = j;
            if ((m & 0x2) != 0) {
              i = j - ViewCompat.getMinimumHeight(localView);
            }
          }
          j = i;
          if (ViewCompat.getFitsSystemWindows(localView)) {
            j = i - paramT.getTopInset();
          }
          if (j > 0)
          {
            i = localView.getTop();
            i = Math.round(j * localInterpolator.getInterpolation((k - i) / j));
            return Integer.signum(paramInt) * (localView.getTop() + i);
          }
          break;
        }
        i++;
      }
      return paramInt;
    }
    
    private boolean shouldJumpElevationState(CoordinatorLayout paramCoordinatorLayout, T paramT)
    {
      paramCoordinatorLayout = paramCoordinatorLayout.getDependents(paramT);
      int i = 0;
      int j = paramCoordinatorLayout.size();
      for (;;)
      {
        boolean bool = false;
        if (i >= j) {
          break;
        }
        paramT = (View)paramCoordinatorLayout.get(i);
        paramT = ((CoordinatorLayout.LayoutParams)paramT.getLayoutParams()).getBehavior();
        if ((paramT instanceof AppBarLayout.ScrollingViewBehavior))
        {
          if (((AppBarLayout.ScrollingViewBehavior)paramT).getOverlayTop() != 0) {
            bool = true;
          }
          return bool;
        }
        i++;
      }
      return false;
    }
    
    private void snapToChildIfNeeded(CoordinatorLayout paramCoordinatorLayout, T paramT)
    {
      int n = paramT.getTopInset() + paramT.getPaddingTop();
      int i1 = getTopBottomOffsetForScrollingSibling() - n;
      int k = getChildIndexOnOffset(paramT, i1);
      if (k >= 0)
      {
        View localView = paramT.getChildAt(k);
        AppBarLayout.LayoutParams localLayoutParams = (AppBarLayout.LayoutParams)localView.getLayoutParams();
        int i2 = localLayoutParams.getScrollFlags();
        if ((i2 & 0x11) == 17)
        {
          int i = -localView.getTop();
          int m = -localView.getBottom();
          int j = i;
          if (k == 0)
          {
            j = i;
            if (ViewCompat.getFitsSystemWindows(paramT))
            {
              j = i;
              if (ViewCompat.getFitsSystemWindows(localView)) {
                j = i - paramT.getTopInset();
              }
            }
          }
          if (checkFlag(i2, 2))
          {
            i = m + ViewCompat.getMinimumHeight(localView);
            k = j;
          }
          else
          {
            k = j;
            i = m;
            if (checkFlag(i2, 5))
            {
              i = ViewCompat.getMinimumHeight(localView) + m;
              if (i1 < i)
              {
                k = i;
                i = m;
              }
              else
              {
                k = j;
              }
            }
          }
          m = k;
          j = i;
          if (checkFlag(i2, 32))
          {
            m = k + localLayoutParams.topMargin;
            j = i - localLayoutParams.bottomMargin;
          }
          i = calculateSnapOffset(i1, j, m);
          animateOffsetTo(paramCoordinatorLayout, paramT, MathUtils.clamp(i + n, -paramT.getTotalScrollRange(), 0), 0.0F);
        }
      }
    }
    
    private void updateAccessibilityActions(CoordinatorLayout paramCoordinatorLayout, T paramT)
    {
      ViewCompat.removeAccessibilityAction(paramCoordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
      ViewCompat.removeAccessibilityAction(paramCoordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
      if (paramT.getTotalScrollRange() == 0) {
        return;
      }
      View localView = getChildWithScrollingBehavior(paramCoordinatorLayout);
      if (localView == null) {
        return;
      }
      if (!childrenHaveScrollFlags(paramT)) {
        return;
      }
      if (!ViewCompat.hasAccessibilityDelegate(paramCoordinatorLayout)) {
        ViewCompat.setAccessibilityDelegate(paramCoordinatorLayout, new AccessibilityDelegateCompat()
        {
          public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
          {
            super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
            paramAnonymousAccessibilityNodeInfoCompat.setScrollable(AppBarLayout.BaseBehavior.this.coordinatorLayoutA11yScrollable);
            paramAnonymousAccessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
          }
        });
      }
      this.coordinatorLayoutA11yScrollable = addAccessibilityScrollActions(paramCoordinatorLayout, paramT, localView);
    }
    
    private void updateAppBarLayoutDrawableState(CoordinatorLayout paramCoordinatorLayout, T paramT, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      View localView = getAppBarChildOnOffset(paramT, paramInt1);
      boolean bool3 = false;
      boolean bool1 = bool3;
      if (localView != null)
      {
        int j = ((AppBarLayout.LayoutParams)localView.getLayoutParams()).getScrollFlags();
        bool1 = bool3;
        if ((j & 0x1) != 0)
        {
          int i = ViewCompat.getMinimumHeight(localView);
          boolean bool2 = false;
          bool1 = false;
          if ((paramInt2 > 0) && ((j & 0xC) != 0))
          {
            if (-paramInt1 >= localView.getBottom() - i - paramT.getTopInset()) {
              bool1 = true;
            }
          }
          else
          {
            bool1 = bool3;
            if ((j & 0x2) != 0)
            {
              bool1 = bool2;
              if (-paramInt1 >= localView.getBottom() - i - paramT.getTopInset()) {
                bool1 = true;
              }
            }
          }
        }
      }
      if (paramT.isLiftOnScroll()) {
        bool1 = paramT.shouldLift(findFirstScrollingChild(paramCoordinatorLayout));
      }
      bool1 = paramT.setLiftedState(bool1);
      if ((paramBoolean) || ((bool1) && (shouldJumpElevationState(paramCoordinatorLayout, paramT)))) {
        paramT.jumpDrawablesToCurrentState();
      }
    }
    
    boolean canDragView(T paramT)
    {
      BaseDragCallback localBaseDragCallback = this.onDragCallback;
      if (localBaseDragCallback != null) {
        return localBaseDragCallback.canDrag(paramT);
      }
      paramT = this.lastNestedScrollingChildRef;
      boolean bool = true;
      if (paramT != null)
      {
        paramT = (View)paramT.get();
        if ((paramT == null) || (!paramT.isShown()) || (paramT.canScrollVertically(-1))) {
          bool = false;
        }
        return bool;
      }
      return true;
    }
    
    int getMaxDragOffset(T paramT)
    {
      return -paramT.getDownNestedScrollRange();
    }
    
    int getScrollRangeForDragFling(T paramT)
    {
      return paramT.getTotalScrollRange();
    }
    
    int getTopBottomOffsetForScrollingSibling()
    {
      return getTopAndBottomOffset() + this.offsetDelta;
    }
    
    boolean isOffsetAnimatorRunning()
    {
      ValueAnimator localValueAnimator = this.offsetAnimator;
      boolean bool;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void onFlingFinished(CoordinatorLayout paramCoordinatorLayout, T paramT)
    {
      snapToChildIfNeeded(paramCoordinatorLayout, paramT);
      if (paramT.isLiftOnScroll()) {
        paramT.setLiftedState(paramT.shouldLift(findFirstScrollingChild(paramCoordinatorLayout)));
      }
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, T paramT, int paramInt)
    {
      boolean bool = super.onLayoutChild(paramCoordinatorLayout, paramT, paramInt);
      int i = paramT.getPendingAction();
      Object localObject = this.savedState;
      if ((localObject != null) && ((i & 0x8) == 0))
      {
        if (((SavedState)localObject).fullyScrolled)
        {
          setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, -paramT.getTotalScrollRange());
        }
        else if (this.savedState.fullyExpanded)
        {
          setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, 0);
        }
        else
        {
          localObject = paramT.getChildAt(this.savedState.firstVisibleChildIndex);
          paramInt = -((View)localObject).getBottom();
          if (this.savedState.firstVisibleChildAtMinimumHeight) {
            paramInt += ViewCompat.getMinimumHeight((View)localObject) + paramT.getTopInset();
          } else {
            paramInt += Math.round(((View)localObject).getHeight() * this.savedState.firstVisibleChildPercentageShown);
          }
          setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, paramInt);
        }
      }
      else if (i != 0)
      {
        if ((i & 0x4) != 0) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if ((i & 0x2) != 0)
        {
          i = -paramT.getUpNestedPreScrollRange();
          if (paramInt != 0) {
            animateOffsetTo(paramCoordinatorLayout, paramT, i, 0.0F);
          } else {
            setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, i);
          }
        }
        else if ((i & 0x1) != 0)
        {
          if (paramInt != 0) {
            animateOffsetTo(paramCoordinatorLayout, paramT, 0, 0.0F);
          } else {
            setHeaderTopBottomOffset(paramCoordinatorLayout, paramT, 0);
          }
        }
      }
      paramT.resetPendingAction();
      this.savedState = null;
      setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -paramT.getTotalScrollRange(), 0));
      updateAppBarLayoutDrawableState(paramCoordinatorLayout, paramT, getTopAndBottomOffset(), 0, true);
      paramT.onOffsetChanged(getTopAndBottomOffset());
      updateAccessibilityActions(paramCoordinatorLayout, paramT);
      return bool;
    }
    
    public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, T paramT, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (((CoordinatorLayout.LayoutParams)paramT.getLayoutParams()).height == -2)
      {
        paramCoordinatorLayout.onMeasureChild(paramT, paramInt1, paramInt2, View.MeasureSpec.makeMeasureSpec(0, 0), paramInt4);
        return true;
      }
      return super.onMeasureChild(paramCoordinatorLayout, paramT, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, T paramT, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
    {
      if (paramInt2 != 0)
      {
        if (paramInt2 < 0)
        {
          paramInt3 = -paramT.getTotalScrollRange();
          int i = paramT.getDownNestedPreScrollRange();
          paramInt1 = paramInt3;
          paramInt3 = i + paramInt3;
        }
        else
        {
          paramInt1 = -paramT.getUpNestedPreScrollRange();
          paramInt3 = 0;
        }
        if (paramInt1 != paramInt3) {
          paramArrayOfInt[1] = scroll(paramCoordinatorLayout, paramT, paramInt2, paramInt1, paramInt3);
        }
      }
      if (paramT.isLiftOnScroll()) {
        paramT.setLiftedState(paramT.shouldLift(paramView));
      }
    }
    
    public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, T paramT, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
    {
      if (paramInt4 < 0) {
        paramArrayOfInt[1] = scroll(paramCoordinatorLayout, paramT, paramInt4, -paramT.getDownNestedScrollRange(), 0);
      }
      if (paramInt4 == 0) {
        updateAccessibilityActions(paramCoordinatorLayout, paramT);
      }
    }
    
    public void onRestoreInstanceState(CoordinatorLayout paramCoordinatorLayout, T paramT, Parcelable paramParcelable)
    {
      if ((paramParcelable instanceof SavedState))
      {
        restoreScrollState((SavedState)paramParcelable, true);
        super.onRestoreInstanceState(paramCoordinatorLayout, paramT, this.savedState.getSuperState());
      }
      else
      {
        super.onRestoreInstanceState(paramCoordinatorLayout, paramT, paramParcelable);
        this.savedState = null;
      }
    }
    
    public Parcelable onSaveInstanceState(CoordinatorLayout paramCoordinatorLayout, T paramT)
    {
      paramCoordinatorLayout = super.onSaveInstanceState(paramCoordinatorLayout, paramT);
      paramT = saveScrollState(paramCoordinatorLayout, paramT);
      if (paramT != null) {
        paramCoordinatorLayout = paramT;
      }
      return paramCoordinatorLayout;
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, T paramT, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      boolean bool;
      if (((paramInt1 & 0x2) != 0) && ((paramT.isLiftOnScroll()) || (canScrollChildren(paramCoordinatorLayout, paramT, paramView1)))) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool)
      {
        paramCoordinatorLayout = this.offsetAnimator;
        if (paramCoordinatorLayout != null) {
          paramCoordinatorLayout.cancel();
        }
      }
      this.lastNestedScrollingChildRef = null;
      this.lastStartedType = paramInt2;
      return bool;
    }
    
    public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, T paramT, View paramView, int paramInt)
    {
      if ((this.lastStartedType == 0) || (paramInt == 1))
      {
        snapToChildIfNeeded(paramCoordinatorLayout, paramT);
        if (paramT.isLiftOnScroll()) {
          paramT.setLiftedState(paramT.shouldLift(paramView));
        }
      }
      this.lastNestedScrollingChildRef = new WeakReference(paramView);
    }
    
    void restoreScrollState(SavedState paramSavedState, boolean paramBoolean)
    {
      if ((this.savedState == null) || (paramBoolean)) {
        this.savedState = paramSavedState;
      }
    }
    
    SavedState saveScrollState(Parcelable paramParcelable, T paramT)
    {
      int k = getTopAndBottomOffset();
      int i = 0;
      int j = paramT.getChildCount();
      while (i < j)
      {
        View localView = paramT.getChildAt(i);
        int m = localView.getBottom() + k;
        if ((localView.getTop() + k <= 0) && (m >= 0))
        {
          if (paramParcelable == null) {
            paramParcelable = AbsSavedState.EMPTY_STATE;
          }
          paramParcelable = new SavedState(paramParcelable);
          boolean bool2 = false;
          if (k == 0) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          paramParcelable.fullyExpanded = bool1;
          if ((!paramParcelable.fullyExpanded) && (-k >= paramT.getTotalScrollRange())) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          paramParcelable.fullyScrolled = bool1;
          paramParcelable.firstVisibleChildIndex = i;
          boolean bool1 = bool2;
          if (m == ViewCompat.getMinimumHeight(localView) + paramT.getTopInset()) {
            bool1 = true;
          }
          paramParcelable.firstVisibleChildAtMinimumHeight = bool1;
          paramParcelable.firstVisibleChildPercentageShown = (m / localView.getHeight());
          return paramParcelable;
        }
        i++;
      }
      return null;
    }
    
    public void setDragCallback(BaseDragCallback paramBaseDragCallback)
    {
      this.onDragCallback = paramBaseDragCallback;
    }
    
    int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, T paramT, int paramInt1, int paramInt2, int paramInt3)
    {
      int j = getTopBottomOffsetForScrollingSibling();
      int i = 0;
      if ((paramInt2 != 0) && (j >= paramInt2) && (j <= paramInt3))
      {
        paramInt2 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
        paramInt1 = i;
        if (j != paramInt2)
        {
          if (paramT.hasChildWithInterpolator()) {
            paramInt1 = interpolateOffset(paramT, paramInt2);
          } else {
            paramInt1 = paramInt2;
          }
          boolean bool = setTopAndBottomOffset(paramInt1);
          paramInt3 = j - paramInt2;
          this.offsetDelta = (paramInt2 - paramInt1);
          i = 1;
          if (bool) {
            for (paramInt1 = 0; paramInt1 < paramT.getChildCount(); paramInt1++)
            {
              AppBarLayout.LayoutParams localLayoutParams = (AppBarLayout.LayoutParams)paramT.getChildAt(paramInt1).getLayoutParams();
              AppBarLayout.ChildScrollEffect localChildScrollEffect = localLayoutParams.getScrollEffect();
              if ((localChildScrollEffect != null) && ((localLayoutParams.getScrollFlags() & 0x1) != 0)) {
                localChildScrollEffect.onOffsetChanged(paramT, paramT.getChildAt(paramInt1), getTopAndBottomOffset());
              }
            }
          }
          if ((!bool) && (paramT.hasChildWithInterpolator())) {
            paramCoordinatorLayout.dispatchDependentViewsChanged(paramT);
          }
          paramT.onOffsetChanged(getTopAndBottomOffset());
          paramInt1 = i;
          if (paramInt2 < j) {
            paramInt1 = -1;
          }
          updateAppBarLayoutDrawableState(paramCoordinatorLayout, paramT, paramInt2, paramInt1, false);
          paramInt1 = paramInt3;
        }
      }
      else
      {
        this.offsetDelta = 0;
        paramInt1 = i;
      }
      updateAccessibilityActions(paramCoordinatorLayout, paramT);
      return paramInt1;
    }
    
    public static abstract class BaseDragCallback<T extends AppBarLayout>
    {
      public abstract boolean canDrag(T paramT);
    }
    
    protected static class SavedState
      extends AbsSavedState
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return new AppBarLayout.BaseBehavior.SavedState(paramAnonymousParcel, null);
        }
        
        public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return new AppBarLayout.BaseBehavior.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppBarLayout.BaseBehavior.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppBarLayout.BaseBehavior.SavedState[paramAnonymousInt];
        }
      };
      boolean firstVisibleChildAtMinimumHeight;
      int firstVisibleChildIndex;
      float firstVisibleChildPercentageShown;
      boolean fullyExpanded;
      boolean fullyScrolled;
      
      public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        super(paramClassLoader);
        int i = paramParcel.readByte();
        boolean bool2 = true;
        boolean bool1;
        if (i != 0) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.fullyScrolled = bool1;
        if (paramParcel.readByte() != 0) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.fullyExpanded = bool1;
        this.firstVisibleChildIndex = paramParcel.readInt();
        this.firstVisibleChildPercentageShown = paramParcel.readFloat();
        if (paramParcel.readByte() != 0) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
        this.firstVisibleChildAtMinimumHeight = bool1;
      }
      
      public SavedState(Parcelable paramParcelable)
      {
        super();
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        super.writeToParcel(paramParcel, paramInt);
        paramParcel.writeByte((byte)this.fullyScrolled);
        paramParcel.writeByte((byte)this.fullyExpanded);
        paramParcel.writeInt(this.firstVisibleChildIndex);
        paramParcel.writeFloat(this.firstVisibleChildPercentageShown);
        paramParcel.writeByte((byte)this.firstVisibleChildAtMinimumHeight);
      }
    }
  }
  
  public static abstract interface BaseOnOffsetChangedListener<T extends AppBarLayout>
  {
    public abstract void onOffsetChanged(T paramT, int paramInt);
  }
  
  public static class Behavior
    extends AppBarLayout.BaseBehavior<AppBarLayout>
  {
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public static abstract class DragCallback
      extends AppBarLayout.BaseBehavior.BaseDragCallback<AppBarLayout>
    {}
  }
  
  public static abstract class ChildScrollEffect
  {
    public abstract void onOffsetChanged(AppBarLayout paramAppBarLayout, View paramView, float paramFloat);
  }
  
  public static class CompressChildScrollEffect
    extends AppBarLayout.ChildScrollEffect
  {
    private static final float COMPRESS_DISTANCE_FACTOR = 0.3F;
    private final Rect ghostRect = new Rect();
    private final Rect relativeRect = new Rect();
    
    private static void updateRelativeRect(Rect paramRect, AppBarLayout paramAppBarLayout, View paramView)
    {
      paramView.getDrawingRect(paramRect);
      paramAppBarLayout.offsetDescendantRectToMyCoords(paramView, paramRect);
      paramRect.offset(0, -paramAppBarLayout.getTopInset());
    }
    
    public void onOffsetChanged(AppBarLayout paramAppBarLayout, View paramView, float paramFloat)
    {
      updateRelativeRect(this.relativeRect, paramAppBarLayout, paramView);
      paramFloat = this.relativeRect.top - Math.abs(paramFloat);
      if (paramFloat <= 0.0F)
      {
        float f = MathUtils.clamp(Math.abs(paramFloat / this.relativeRect.height()), 0.0F, 1.0F);
        paramFloat = -paramFloat - this.relativeRect.height() * 0.3F * (1.0F - (1.0F - f) * (1.0F - f));
        paramView.setTranslationY(paramFloat);
        paramView.getDrawingRect(this.ghostRect);
        this.ghostRect.offset(0, (int)-paramFloat);
        ViewCompat.setClipBounds(paramView, this.ghostRect);
      }
      else
      {
        ViewCompat.setClipBounds(paramView, null);
        paramView.setTranslationY(0.0F);
      }
    }
  }
  
  public static class LayoutParams
    extends LinearLayout.LayoutParams
  {
    static final int COLLAPSIBLE_FLAGS = 10;
    static final int FLAG_QUICK_RETURN = 5;
    static final int FLAG_SNAP = 17;
    private static final int SCROLL_EFFECT_COMPRESS = 1;
    private static final int SCROLL_EFFECT_NONE = 0;
    public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
    public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
    public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
    public static final int SCROLL_FLAG_NO_SCROLL = 0;
    public static final int SCROLL_FLAG_SCROLL = 1;
    public static final int SCROLL_FLAG_SNAP = 16;
    public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
    private AppBarLayout.ChildScrollEffect scrollEffect;
    int scrollFlags = 1;
    Interpolator scrollInterpolator;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2, paramFloat);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppBarLayout_Layout);
      this.scrollFlags = paramAttributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
      setScrollEffect(createScrollEffectFromInt(paramAttributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollEffect, 0)));
      if (paramAttributeSet.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
        this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(paramContext, paramAttributeSet.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
      }
      paramAttributeSet.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LinearLayout.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.scrollFlags = paramLayoutParams.scrollFlags;
      this.scrollInterpolator = paramLayoutParams.scrollInterpolator;
    }
    
    private AppBarLayout.ChildScrollEffect createScrollEffectFromInt(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return null;
      }
      return new AppBarLayout.CompressChildScrollEffect();
    }
    
    public AppBarLayout.ChildScrollEffect getScrollEffect()
    {
      return this.scrollEffect;
    }
    
    public int getScrollFlags()
    {
      return this.scrollFlags;
    }
    
    public Interpolator getScrollInterpolator()
    {
      return this.scrollInterpolator;
    }
    
    boolean isCollapsible()
    {
      int i = this.scrollFlags;
      boolean bool = true;
      if (((i & 0x1) != 1) || ((i & 0xA) == 0)) {
        bool = false;
      }
      return bool;
    }
    
    public void setScrollEffect(AppBarLayout.ChildScrollEffect paramChildScrollEffect)
    {
      this.scrollEffect = paramChildScrollEffect;
    }
    
    public void setScrollFlags(int paramInt)
    {
      this.scrollFlags = paramInt;
    }
    
    public void setScrollInterpolator(Interpolator paramInterpolator)
    {
      this.scrollInterpolator = paramInterpolator;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface ScrollFlags {}
  }
  
  public static abstract interface LiftOnScrollListener
  {
    public abstract void onUpdate(float paramFloat, int paramInt);
  }
  
  public static abstract interface OnOffsetChangedListener
    extends AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout>
  {
    public abstract void onOffsetChanged(AppBarLayout paramAppBarLayout, int paramInt);
  }
  
  public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior
  {
    public ScrollingViewBehavior() {}
    
    public ScrollingViewBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ScrollingViewBehavior_Layout);
      setOverlayTop(paramContext.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
      paramContext.recycle();
    }
    
    private static int getAppBarLayoutOffset(AppBarLayout paramAppBarLayout)
    {
      paramAppBarLayout = ((CoordinatorLayout.LayoutParams)paramAppBarLayout.getLayoutParams()).getBehavior();
      if ((paramAppBarLayout instanceof AppBarLayout.BaseBehavior)) {
        return ((AppBarLayout.BaseBehavior)paramAppBarLayout).getTopBottomOffsetForScrollingSibling();
      }
      return 0;
    }
    
    private void offsetChildAsNeeded(View paramView1, View paramView2)
    {
      Object localObject = ((CoordinatorLayout.LayoutParams)paramView2.getLayoutParams()).getBehavior();
      if ((localObject instanceof AppBarLayout.BaseBehavior))
      {
        localObject = (AppBarLayout.BaseBehavior)localObject;
        ViewCompat.offsetTopAndBottom(paramView1, paramView2.getBottom() - paramView1.getTop() + ((AppBarLayout.BaseBehavior)localObject).offsetDelta + getVerticalLayoutGap() - getOverlapPixelsForOffset(paramView2));
      }
    }
    
    private void updateLiftedStateIfNeeded(View paramView1, View paramView2)
    {
      if ((paramView2 instanceof AppBarLayout))
      {
        paramView2 = (AppBarLayout)paramView2;
        if (paramView2.isLiftOnScroll()) {
          paramView2.setLiftedState(paramView2.shouldLift(paramView1));
        }
      }
    }
    
    AppBarLayout findFirstDependency(List<View> paramList)
    {
      int i = 0;
      int j = paramList.size();
      while (i < j)
      {
        View localView = (View)paramList.get(i);
        if ((localView instanceof AppBarLayout)) {
          return (AppBarLayout)localView;
        }
        i++;
      }
      return null;
    }
    
    float getOverlapRatioForOffset(View paramView)
    {
      if ((paramView instanceof AppBarLayout))
      {
        paramView = (AppBarLayout)paramView;
        int j = paramView.getTotalScrollRange();
        int k = paramView.getDownNestedPreScrollRange();
        int i = getAppBarLayoutOffset(paramView);
        if ((k != 0) && (j + i <= k)) {
          return 0.0F;
        }
        j -= k;
        if (j != 0) {
          return i / j + 1.0F;
        }
      }
      return 0.0F;
    }
    
    int getScrollRange(View paramView)
    {
      if ((paramView instanceof AppBarLayout)) {
        return ((AppBarLayout)paramView).getTotalScrollRange();
      }
      return super.getScrollRange(paramView);
    }
    
    public boolean layoutDependsOn(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      return paramView2 instanceof AppBarLayout;
    }
    
    public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      offsetChildAsNeeded(paramView1, paramView2);
      updateLiftedStateIfNeeded(paramView1, paramView2);
      return false;
    }
    
    public void onDependentViewRemoved(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      if ((paramView2 instanceof AppBarLayout))
      {
        ViewCompat.removeAccessibilityAction(paramCoordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
        ViewCompat.removeAccessibilityAction(paramCoordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
        ViewCompat.setAccessibilityDelegate(paramCoordinatorLayout, null);
      }
    }
    
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout paramCoordinatorLayout, View paramView, Rect paramRect, boolean paramBoolean)
    {
      AppBarLayout localAppBarLayout = findFirstDependency(paramCoordinatorLayout.getDependencies(paramView));
      if (localAppBarLayout != null)
      {
        paramRect.offset(paramView.getLeft(), paramView.getTop());
        paramView = this.tempRect1;
        paramView.set(0, 0, paramCoordinatorLayout.getWidth(), paramCoordinatorLayout.getHeight());
        if (!paramView.contains(paramRect))
        {
          localAppBarLayout.setExpanded(false, paramBoolean ^ true);
          return true;
        }
      }
      return false;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/appbar/AppBarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */