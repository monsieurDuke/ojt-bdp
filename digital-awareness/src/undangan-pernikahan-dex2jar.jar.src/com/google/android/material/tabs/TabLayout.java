package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import androidx.core.util.Pools.SynchronizedPool;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.DecorView;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout
  extends HorizontalScrollView
{
  private static final int ANIMATION_DURATION = 300;
  static final int DEFAULT_GAP_TEXT_ICON = 8;
  private static final int DEFAULT_HEIGHT = 48;
  private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
  private static final int DEF_STYLE_RES = R.style.Widget_Design_TabLayout;
  static final int FIXED_WRAP_GUTTER_MIN = 16;
  public static final int GRAVITY_CENTER = 1;
  public static final int GRAVITY_FILL = 0;
  public static final int GRAVITY_START = 2;
  public static final int INDICATOR_ANIMATION_MODE_ELASTIC = 1;
  public static final int INDICATOR_ANIMATION_MODE_FADE = 2;
  public static final int INDICATOR_ANIMATION_MODE_LINEAR = 0;
  public static final int INDICATOR_GRAVITY_BOTTOM = 0;
  public static final int INDICATOR_GRAVITY_CENTER = 1;
  public static final int INDICATOR_GRAVITY_STRETCH = 3;
  public static final int INDICATOR_GRAVITY_TOP = 2;
  private static final int INVALID_WIDTH = -1;
  private static final String LOG_TAG = "TabLayout";
  public static final int MODE_AUTO = 2;
  public static final int MODE_FIXED = 1;
  public static final int MODE_SCROLLABLE = 0;
  public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
  public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
  private static final int TAB_MIN_WIDTH_MARGIN = 56;
  private static final Pools.Pool<Tab> tabPool = new Pools.SynchronizedPool(16);
  private AdapterChangeListener adapterChangeListener;
  private int contentInsetStart;
  private BaseOnTabSelectedListener currentVpSelectedListener;
  boolean inlineLabel;
  int mode;
  private TabLayoutOnPageChangeListener pageChangeListener;
  private PagerAdapter pagerAdapter;
  private DataSetObserver pagerAdapterObserver;
  private final int requestedTabMaxWidth;
  private final int requestedTabMinWidth;
  private ValueAnimator scrollAnimator;
  private final int scrollableTabMinWidth;
  private BaseOnTabSelectedListener selectedListener;
  private final ArrayList<BaseOnTabSelectedListener> selectedListeners = new ArrayList();
  private Tab selectedTab;
  private boolean setupViewPagerImplicitly;
  final SlidingTabIndicator slidingTabIndicator;
  final int tabBackgroundResId;
  int tabGravity;
  ColorStateList tabIconTint;
  PorterDuff.Mode tabIconTintMode;
  int tabIndicatorAnimationDuration;
  int tabIndicatorAnimationMode;
  boolean tabIndicatorFullWidth;
  int tabIndicatorGravity;
  int tabIndicatorHeight = -1;
  private TabIndicatorInterpolator tabIndicatorInterpolator;
  int tabMaxWidth = Integer.MAX_VALUE;
  int tabPaddingBottom;
  int tabPaddingEnd;
  int tabPaddingStart;
  int tabPaddingTop;
  ColorStateList tabRippleColorStateList;
  Drawable tabSelectedIndicator = new GradientDrawable();
  private int tabSelectedIndicatorColor = 0;
  int tabTextAppearance;
  ColorStateList tabTextColors;
  float tabTextMultiLineSize;
  float tabTextSize;
  private final Pools.Pool<TabView> tabViewPool = new Pools.SimplePool(12);
  private final ArrayList<Tab> tabs = new ArrayList();
  boolean unboundedRipple;
  ViewPager viewPager;
  
  public TabLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.tabStyle);
  }
  
  public TabLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    setHorizontalScrollBarEnabled(false);
    Object localObject = new SlidingTabIndicator(paramContext);
    this.slidingTabIndicator = ((SlidingTabIndicator)localObject);
    super.addView((View)localObject, 0, new FrameLayout.LayoutParams(-2, -1));
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout, paramInt, i, new int[] { com.google.android.material.R.styleable.TabLayout_tabTextAppearance });
    if ((getBackground() instanceof ColorDrawable))
    {
      ColorDrawable localColorDrawable = (ColorDrawable)getBackground();
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
      localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(localColorDrawable.getColor()));
      localMaterialShapeDrawable.initializeElevationOverlay(paramContext);
      localMaterialShapeDrawable.setElevation(ViewCompat.getElevation(this));
      ViewCompat.setBackground(this, localMaterialShapeDrawable);
    }
    setSelectedTabIndicator(MaterialResources.getDrawable(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout_tabIndicator));
    setSelectedTabIndicatorColor(paramAttributeSet.getColor(com.google.android.material.R.styleable.TabLayout_tabIndicatorColor, 0));
    ((SlidingTabIndicator)localObject).setSelectedIndicatorHeight(paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabIndicatorHeight, -1));
    setSelectedTabIndicatorGravity(paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabIndicatorGravity, 0));
    setTabIndicatorAnimationMode(paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabIndicatorAnimationMode, 0));
    setTabIndicatorFullWidth(paramAttributeSet.getBoolean(com.google.android.material.R.styleable.TabLayout_tabIndicatorFullWidth, true));
    paramInt = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPadding, 0);
    this.tabPaddingBottom = paramInt;
    this.tabPaddingEnd = paramInt;
    this.tabPaddingTop = paramInt;
    this.tabPaddingStart = paramInt;
    this.tabPaddingStart = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingStart, this.tabPaddingStart);
    this.tabPaddingTop = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingTop, this.tabPaddingTop);
    this.tabPaddingEnd = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingEnd, this.tabPaddingEnd);
    this.tabPaddingBottom = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabPaddingBottom, this.tabPaddingBottom);
    paramInt = paramAttributeSet.getResourceId(com.google.android.material.R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
    this.tabTextAppearance = paramInt;
    localObject = paramContext.obtainStyledAttributes(paramInt, androidx.appcompat.R.styleable.TextAppearance);
    try
    {
      this.tabTextSize = ((TypedArray)localObject).getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
      this.tabTextColors = MaterialResources.getColorStateList(paramContext, (TypedArray)localObject, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
      ((TypedArray)localObject).recycle();
      if (paramAttributeSet.hasValue(com.google.android.material.R.styleable.TabLayout_tabTextColor)) {
        this.tabTextColors = MaterialResources.getColorStateList(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout_tabTextColor);
      }
      if (paramAttributeSet.hasValue(com.google.android.material.R.styleable.TabLayout_tabSelectedTextColor))
      {
        paramInt = paramAttributeSet.getColor(com.google.android.material.R.styleable.TabLayout_tabSelectedTextColor, 0);
        this.tabTextColors = createColorStateList(this.tabTextColors.getDefaultColor(), paramInt);
      }
      this.tabIconTint = MaterialResources.getColorStateList(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout_tabIconTint);
      this.tabIconTintMode = ViewUtils.parseTintMode(paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabIconTintMode, -1), null);
      this.tabRippleColorStateList = MaterialResources.getColorStateList(paramContext, paramAttributeSet, com.google.android.material.R.styleable.TabLayout_tabRippleColor);
      this.tabIndicatorAnimationDuration = paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabIndicatorAnimationDuration, 300);
      this.requestedTabMinWidth = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabMinWidth, -1);
      this.requestedTabMaxWidth = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabMaxWidth, -1);
      this.tabBackgroundResId = paramAttributeSet.getResourceId(com.google.android.material.R.styleable.TabLayout_tabBackground, 0);
      this.contentInsetStart = paramAttributeSet.getDimensionPixelSize(com.google.android.material.R.styleable.TabLayout_tabContentStart, 0);
      this.mode = paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabMode, 1);
      this.tabGravity = paramAttributeSet.getInt(com.google.android.material.R.styleable.TabLayout_tabGravity, 0);
      this.inlineLabel = paramAttributeSet.getBoolean(com.google.android.material.R.styleable.TabLayout_tabInlineLabel, false);
      this.unboundedRipple = paramAttributeSet.getBoolean(com.google.android.material.R.styleable.TabLayout_tabUnboundedRipple, false);
      paramAttributeSet.recycle();
      paramContext = getResources();
      this.tabTextMultiLineSize = paramContext.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
      this.scrollableTabMinWidth = paramContext.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
      applyModeAndGravity();
      return;
    }
    finally
    {
      ((TypedArray)localObject).recycle();
    }
  }
  
  private void addTabFromItemView(TabItem paramTabItem)
  {
    Tab localTab = newTab();
    if (paramTabItem.text != null) {
      localTab.setText(paramTabItem.text);
    }
    if (paramTabItem.icon != null) {
      localTab.setIcon(paramTabItem.icon);
    }
    if (paramTabItem.customLayout != 0) {
      localTab.setCustomView(paramTabItem.customLayout);
    }
    if (!TextUtils.isEmpty(paramTabItem.getContentDescription())) {
      localTab.setContentDescription(paramTabItem.getContentDescription());
    }
    addTab(localTab);
  }
  
  private void addTabView(Tab paramTab)
  {
    TabView localTabView = paramTab.view;
    localTabView.setSelected(false);
    localTabView.setActivated(false);
    this.slidingTabIndicator.addView(localTabView, paramTab.getPosition(), createLayoutParamsForTabs());
  }
  
  private void addViewInternal(View paramView)
  {
    if ((paramView instanceof TabItem))
    {
      addTabFromItemView((TabItem)paramView);
      return;
    }
    throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
  }
  
  private void animateToTab(int paramInt)
  {
    if (paramInt == -1) {
      return;
    }
    if ((getWindowToken() != null) && (ViewCompat.isLaidOut(this)) && (!this.slidingTabIndicator.childrenNeedLayout()))
    {
      int j = getScrollX();
      int i = calculateScrollXForTab(paramInt, 0.0F);
      if (j != i)
      {
        ensureScrollAnimator();
        this.scrollAnimator.setIntValues(new int[] { j, i });
        this.scrollAnimator.start();
      }
      this.slidingTabIndicator.animateIndicatorToPosition(paramInt, this.tabIndicatorAnimationDuration);
      return;
    }
    setScrollPosition(paramInt, 0.0F, true);
  }
  
  private void applyGravityForModeScrollable(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 1: 
      this.slidingTabIndicator.setGravity(1);
      break;
    case 0: 
      Log.w("TabLayout", "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
    case 2: 
      this.slidingTabIndicator.setGravity(8388611);
    }
  }
  
  private void applyModeAndGravity()
  {
    int i = 0;
    int j = this.mode;
    if ((j == 0) || (j == 2)) {
      i = Math.max(0, this.contentInsetStart - this.tabPaddingStart);
    }
    ViewCompat.setPaddingRelative(this.slidingTabIndicator, i, 0, 0, 0);
    switch (this.mode)
    {
    default: 
      break;
    case 1: 
    case 2: 
      if (this.tabGravity == 2) {
        Log.w("TabLayout", "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
      }
      this.slidingTabIndicator.setGravity(1);
      break;
    case 0: 
      applyGravityForModeScrollable(this.tabGravity);
    }
    updateTabViews(true);
  }
  
  private int calculateScrollXForTab(int paramInt, float paramFloat)
  {
    int j = this.mode;
    int i = 0;
    if ((j != 0) && (j != 2)) {
      return 0;
    }
    View localView2 = this.slidingTabIndicator.getChildAt(paramInt);
    if (localView2 == null) {
      return 0;
    }
    View localView1;
    if (paramInt + 1 < this.slidingTabIndicator.getChildCount()) {
      localView1 = this.slidingTabIndicator.getChildAt(paramInt + 1);
    } else {
      localView1 = null;
    }
    j = localView2.getWidth();
    paramInt = i;
    if (localView1 != null) {
      paramInt = localView1.getWidth();
    }
    i = localView2.getLeft() + j / 2 - getWidth() / 2;
    paramInt = (int)((j + paramInt) * 0.5F * paramFloat);
    if (ViewCompat.getLayoutDirection(this) == 0) {
      paramInt = i + paramInt;
    } else {
      paramInt = i - paramInt;
    }
    return paramInt;
  }
  
  private void configureTab(Tab paramTab, int paramInt)
  {
    paramTab.setPosition(paramInt);
    this.tabs.add(paramInt, paramTab);
    int i = this.tabs.size();
    paramInt++;
    while (paramInt < i)
    {
      ((Tab)this.tabs.get(paramInt)).setPosition(paramInt);
      paramInt++;
    }
  }
  
  private static ColorStateList createColorStateList(int paramInt1, int paramInt2)
  {
    int[][] arrayOfInt1 = new int[2][];
    int[] arrayOfInt = new int[2];
    arrayOfInt1[0] = SELECTED_STATE_SET;
    arrayOfInt[0] = paramInt2;
    paramInt2 = 0 + 1;
    arrayOfInt1[paramInt2] = EMPTY_STATE_SET;
    arrayOfInt[paramInt2] = paramInt1;
    return new ColorStateList(arrayOfInt1, arrayOfInt);
  }
  
  private LinearLayout.LayoutParams createLayoutParamsForTabs()
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    updateTabViewLayoutParams(localLayoutParams);
    return localLayoutParams;
  }
  
  private TabView createTabView(Tab paramTab)
  {
    Object localObject1 = this.tabViewPool;
    if (localObject1 != null) {
      localObject1 = (TabView)((Pools.Pool)localObject1).acquire();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new TabView(getContext());
    }
    ((TabView)localObject2).setTab(paramTab);
    ((TabView)localObject2).setFocusable(true);
    ((TabView)localObject2).setMinimumWidth(getTabMinWidth());
    if (TextUtils.isEmpty(paramTab.contentDesc)) {
      ((TabView)localObject2).setContentDescription(paramTab.text);
    } else {
      ((TabView)localObject2).setContentDescription(paramTab.contentDesc);
    }
    return (TabView)localObject2;
  }
  
  private void dispatchTabReselected(Tab paramTab)
  {
    for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
      ((BaseOnTabSelectedListener)this.selectedListeners.get(i)).onTabReselected(paramTab);
    }
  }
  
  private void dispatchTabSelected(Tab paramTab)
  {
    for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
      ((BaseOnTabSelectedListener)this.selectedListeners.get(i)).onTabSelected(paramTab);
    }
  }
  
  private void dispatchTabUnselected(Tab paramTab)
  {
    for (int i = this.selectedListeners.size() - 1; i >= 0; i--) {
      ((BaseOnTabSelectedListener)this.selectedListeners.get(i)).onTabUnselected(paramTab);
    }
  }
  
  private void ensureScrollAnimator()
  {
    if (this.scrollAnimator == null)
    {
      ValueAnimator localValueAnimator = new ValueAnimator();
      this.scrollAnimator = localValueAnimator;
      localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      this.scrollAnimator.setDuration(this.tabIndicatorAnimationDuration);
      this.scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          TabLayout.this.scrollTo(((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue(), 0);
        }
      });
    }
  }
  
  private int getDefaultHeight()
  {
    int k = 0;
    int i = 0;
    int m = this.tabs.size();
    int j;
    for (;;)
    {
      j = k;
      if (i >= m) {
        break;
      }
      Tab localTab = (Tab)this.tabs.get(i);
      if ((localTab != null) && (localTab.getIcon() != null) && (!TextUtils.isEmpty(localTab.getText())))
      {
        j = 1;
        break;
      }
      i++;
    }
    if ((j != 0) && (!this.inlineLabel)) {
      i = 72;
    } else {
      i = 48;
    }
    return i;
  }
  
  private int getTabMinWidth()
  {
    int i = this.requestedTabMinWidth;
    if (i != -1) {
      return i;
    }
    i = this.mode;
    if ((i != 0) && (i != 2)) {
      i = 0;
    } else {
      i = this.scrollableTabMinWidth;
    }
    return i;
  }
  
  private int getTabScrollRange()
  {
    return Math.max(0, this.slidingTabIndicator.getWidth() - getWidth() - getPaddingLeft() - getPaddingRight());
  }
  
  private boolean isScrollingEnabled()
  {
    boolean bool;
    if ((getTabMode() != 0) && (getTabMode() != 2)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void removeTabViewAt(int paramInt)
  {
    TabView localTabView = (TabView)this.slidingTabIndicator.getChildAt(paramInt);
    this.slidingTabIndicator.removeViewAt(paramInt);
    if (localTabView != null)
    {
      localTabView.reset();
      this.tabViewPool.release(localTabView);
    }
    requestLayout();
  }
  
  private void setSelectedTabView(int paramInt)
  {
    int j = this.slidingTabIndicator.getChildCount();
    if (paramInt < j) {
      for (int i = 0; i < j; i++)
      {
        View localView = this.slidingTabIndicator.getChildAt(i);
        boolean bool2 = false;
        if (i == paramInt) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        localView.setSelected(bool1);
        boolean bool1 = bool2;
        if (i == paramInt) {
          bool1 = true;
        }
        localView.setActivated(bool1);
      }
    }
  }
  
  private void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean1, boolean paramBoolean2)
  {
    ViewPager localViewPager = this.viewPager;
    if (localViewPager != null)
    {
      localObject = this.pageChangeListener;
      if (localObject != null) {
        localViewPager.removeOnPageChangeListener((ViewPager.OnPageChangeListener)localObject);
      }
      localObject = this.adapterChangeListener;
      if (localObject != null) {
        this.viewPager.removeOnAdapterChangeListener((ViewPager.OnAdapterChangeListener)localObject);
      }
    }
    Object localObject = this.currentVpSelectedListener;
    if (localObject != null)
    {
      removeOnTabSelectedListener((BaseOnTabSelectedListener)localObject);
      this.currentVpSelectedListener = null;
    }
    if (paramViewPager != null)
    {
      this.viewPager = paramViewPager;
      if (this.pageChangeListener == null) {
        this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
      }
      this.pageChangeListener.reset();
      paramViewPager.addOnPageChangeListener(this.pageChangeListener);
      localObject = new ViewPagerOnTabSelectedListener(paramViewPager);
      this.currentVpSelectedListener = ((BaseOnTabSelectedListener)localObject);
      addOnTabSelectedListener((BaseOnTabSelectedListener)localObject);
      localObject = paramViewPager.getAdapter();
      if (localObject != null) {
        setPagerAdapter((PagerAdapter)localObject, paramBoolean1);
      }
      if (this.adapterChangeListener == null) {
        this.adapterChangeListener = new AdapterChangeListener();
      }
      this.adapterChangeListener.setAutoRefresh(paramBoolean1);
      paramViewPager.addOnAdapterChangeListener(this.adapterChangeListener);
      setScrollPosition(paramViewPager.getCurrentItem(), 0.0F, true);
    }
    else
    {
      this.viewPager = null;
      setPagerAdapter(null, false);
    }
    this.setupViewPagerImplicitly = paramBoolean2;
  }
  
  private void updateAllTabs()
  {
    int i = 0;
    int j = this.tabs.size();
    while (i < j)
    {
      ((Tab)this.tabs.get(i)).updateView();
      i++;
    }
  }
  
  private void updateTabViewLayoutParams(LinearLayout.LayoutParams paramLayoutParams)
  {
    if ((this.mode == 1) && (this.tabGravity == 0))
    {
      paramLayoutParams.width = 0;
      paramLayoutParams.weight = 1.0F;
    }
    else
    {
      paramLayoutParams.width = -2;
      paramLayoutParams.weight = 0.0F;
    }
  }
  
  @Deprecated
  public void addOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    if (!this.selectedListeners.contains(paramBaseOnTabSelectedListener)) {
      this.selectedListeners.add(paramBaseOnTabSelectedListener);
    }
  }
  
  public void addOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener)
  {
    addOnTabSelectedListener(paramOnTabSelectedListener);
  }
  
  public void addTab(Tab paramTab)
  {
    addTab(paramTab, this.tabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt)
  {
    addTab(paramTab, paramInt, this.tabs.isEmpty());
  }
  
  public void addTab(Tab paramTab, int paramInt, boolean paramBoolean)
  {
    if (paramTab.parent == this)
    {
      configureTab(paramTab, paramInt);
      addTabView(paramTab);
      if (paramBoolean) {
        paramTab.select();
      }
      return;
    }
    throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
  }
  
  public void addTab(Tab paramTab, boolean paramBoolean)
  {
    addTab(paramTab, this.tabs.size(), paramBoolean);
  }
  
  public void addView(View paramView)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    addViewInternal(paramView);
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    addViewInternal(paramView);
  }
  
  public void clearOnTabSelectedListeners()
  {
    this.selectedListeners.clear();
  }
  
  protected Tab createTabFromPool()
  {
    Tab localTab2 = (Tab)tabPool.acquire();
    Tab localTab1 = localTab2;
    if (localTab2 == null) {
      localTab1 = new Tab();
    }
    return localTab1;
  }
  
  public FrameLayout.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return generateDefaultLayoutParams();
  }
  
  public int getSelectedTabPosition()
  {
    Tab localTab = this.selectedTab;
    int i;
    if (localTab != null) {
      i = localTab.getPosition();
    } else {
      i = -1;
    }
    return i;
  }
  
  public Tab getTabAt(int paramInt)
  {
    Tab localTab;
    if ((paramInt >= 0) && (paramInt < getTabCount())) {
      localTab = (Tab)this.tabs.get(paramInt);
    } else {
      localTab = null;
    }
    return localTab;
  }
  
  public int getTabCount()
  {
    return this.tabs.size();
  }
  
  public int getTabGravity()
  {
    return this.tabGravity;
  }
  
  public ColorStateList getTabIconTint()
  {
    return this.tabIconTint;
  }
  
  public int getTabIndicatorAnimationMode()
  {
    return this.tabIndicatorAnimationMode;
  }
  
  public int getTabIndicatorGravity()
  {
    return this.tabIndicatorGravity;
  }
  
  int getTabMaxWidth()
  {
    return this.tabMaxWidth;
  }
  
  public int getTabMode()
  {
    return this.mode;
  }
  
  public ColorStateList getTabRippleColor()
  {
    return this.tabRippleColorStateList;
  }
  
  public Drawable getTabSelectedIndicator()
  {
    return this.tabSelectedIndicator;
  }
  
  public ColorStateList getTabTextColors()
  {
    return this.tabTextColors;
  }
  
  public boolean hasUnboundedRipple()
  {
    return this.unboundedRipple;
  }
  
  public boolean isInlineLabel()
  {
    return this.inlineLabel;
  }
  
  public boolean isTabIndicatorFullWidth()
  {
    return this.tabIndicatorFullWidth;
  }
  
  public Tab newTab()
  {
    Tab localTab = createTabFromPool();
    localTab.parent = this;
    localTab.view = createTabView(localTab);
    if (localTab.id != -1) {
      localTab.view.setId(localTab.id);
    }
    return localTab;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this);
    if (this.viewPager == null)
    {
      ViewParent localViewParent = getParent();
      if ((localViewParent instanceof ViewPager)) {
        setupWithViewPager((ViewPager)localViewParent, true, true);
      }
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.setupViewPagerImplicitly)
    {
      setupWithViewPager(null);
      this.setupViewPagerImplicitly = false;
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++)
    {
      View localView = this.slidingTabIndicator.getChildAt(i);
      if ((localView instanceof TabView)) {
        ((TabView)localView).drawBackground(paramCanvas);
      }
    }
    super.onDraw(paramCanvas);
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo = AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getTabCount(), false, 1));
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool;
    if ((isScrollingEnabled()) && (super.onInterceptTouchEvent(paramMotionEvent))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int m = Math.round(ViewUtils.dpToPx(getContext(), getDefaultHeight()));
    int i = View.MeasureSpec.getMode(paramInt2);
    int j = 0;
    int k = 0;
    switch (i)
    {
    default: 
      i = paramInt2;
      break;
    case 0: 
      i = View.MeasureSpec.makeMeasureSpec(getPaddingTop() + m + getPaddingBottom(), 1073741824);
      break;
    case -2147483648: 
      i = paramInt2;
      if (getChildCount() == 1)
      {
        i = paramInt2;
        if (View.MeasureSpec.getSize(paramInt2) >= m)
        {
          getChildAt(0).setMinimumHeight(m);
          i = paramInt2;
        }
      }
      break;
    }
    m = View.MeasureSpec.getSize(paramInt1);
    if (View.MeasureSpec.getMode(paramInt1) != 0)
    {
      paramInt2 = this.requestedTabMaxWidth;
      if (paramInt2 <= 0) {
        paramInt2 = (int)(m - ViewUtils.dpToPx(getContext(), 56));
      }
      this.tabMaxWidth = paramInt2;
    }
    super.onMeasure(paramInt1, i);
    if (getChildCount() == 1)
    {
      View localView = getChildAt(0);
      paramInt1 = 0;
      switch (this.mode)
      {
      default: 
        break;
      case 1: 
        paramInt1 = k;
        if (localView.getMeasuredWidth() != getMeasuredWidth()) {
          paramInt1 = 1;
        }
        break;
      case 0: 
      case 2: 
        paramInt1 = j;
        if (localView.getMeasuredWidth() < getMeasuredWidth()) {
          paramInt1 = 1;
        }
        break;
      }
      if (paramInt1 != 0)
      {
        paramInt1 = getChildMeasureSpec(i, getPaddingTop() + getPaddingBottom(), localView.getLayoutParams().height);
        localView.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), paramInt1);
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getActionMasked() == 8) && (!isScrollingEnabled())) {
      return false;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  void populateFromPagerAdapter()
  {
    removeAllTabs();
    Object localObject = this.pagerAdapter;
    if (localObject != null)
    {
      int j = ((PagerAdapter)localObject).getCount();
      for (int i = 0; i < j; i++) {
        addTab(newTab().setText(this.pagerAdapter.getPageTitle(i)), false);
      }
      localObject = this.viewPager;
      if ((localObject != null) && (j > 0))
      {
        i = ((ViewPager)localObject).getCurrentItem();
        if ((i != getSelectedTabPosition()) && (i < getTabCount())) {
          selectTab(getTabAt(i));
        }
      }
    }
  }
  
  protected boolean releaseFromTabPool(Tab paramTab)
  {
    return tabPool.release(paramTab);
  }
  
  public void removeAllTabs()
  {
    for (int i = this.slidingTabIndicator.getChildCount() - 1; i >= 0; i--) {
      removeTabViewAt(i);
    }
    Iterator localIterator = this.tabs.iterator();
    while (localIterator.hasNext())
    {
      Tab localTab = (Tab)localIterator.next();
      localIterator.remove();
      localTab.reset();
      releaseFromTabPool(localTab);
    }
    this.selectedTab = null;
  }
  
  @Deprecated
  public void removeOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    this.selectedListeners.remove(paramBaseOnTabSelectedListener);
  }
  
  public void removeOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener)
  {
    removeOnTabSelectedListener(paramOnTabSelectedListener);
  }
  
  public void removeTab(Tab paramTab)
  {
    if (paramTab.parent == this)
    {
      removeTabAt(paramTab.getPosition());
      return;
    }
    throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
  }
  
  public void removeTabAt(int paramInt)
  {
    Tab localTab = this.selectedTab;
    int i;
    if (localTab != null) {
      i = localTab.getPosition();
    } else {
      i = 0;
    }
    removeTabViewAt(paramInt);
    localTab = (Tab)this.tabs.remove(paramInt);
    if (localTab != null)
    {
      localTab.reset();
      releaseFromTabPool(localTab);
    }
    int k = this.tabs.size();
    for (int j = paramInt; j < k; j++) {
      ((Tab)this.tabs.get(j)).setPosition(j);
    }
    if (i == paramInt)
    {
      if (this.tabs.isEmpty()) {
        localTab = null;
      } else {
        localTab = (Tab)this.tabs.get(Math.max(0, paramInt - 1));
      }
      selectTab(localTab);
    }
  }
  
  public void selectTab(Tab paramTab)
  {
    selectTab(paramTab, true);
  }
  
  public void selectTab(Tab paramTab, boolean paramBoolean)
  {
    Tab localTab = this.selectedTab;
    if (localTab == paramTab)
    {
      if (localTab != null)
      {
        dispatchTabReselected(paramTab);
        animateToTab(paramTab.getPosition());
      }
    }
    else
    {
      int i;
      if (paramTab != null) {
        i = paramTab.getPosition();
      } else {
        i = -1;
      }
      if (paramBoolean)
      {
        if (((localTab == null) || (localTab.getPosition() == -1)) && (i != -1)) {
          setScrollPosition(i, 0.0F, true);
        } else {
          animateToTab(i);
        }
        if (i != -1) {
          setSelectedTabView(i);
        }
      }
      this.selectedTab = paramTab;
      if (localTab != null) {
        dispatchTabUnselected(localTab);
      }
      if (paramTab != null) {
        dispatchTabSelected(paramTab);
      }
    }
  }
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    MaterialShapeUtils.setElevation(this, paramFloat);
  }
  
  public void setInlineLabel(boolean paramBoolean)
  {
    if (this.inlineLabel != paramBoolean)
    {
      this.inlineLabel = paramBoolean;
      for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++)
      {
        View localView = this.slidingTabIndicator.getChildAt(i);
        if ((localView instanceof TabView)) {
          ((TabView)localView).updateOrientation();
        }
      }
      applyModeAndGravity();
    }
  }
  
  public void setInlineLabelResource(int paramInt)
  {
    setInlineLabel(getResources().getBoolean(paramInt));
  }
  
  @Deprecated
  public void setOnTabSelectedListener(BaseOnTabSelectedListener paramBaseOnTabSelectedListener)
  {
    BaseOnTabSelectedListener localBaseOnTabSelectedListener = this.selectedListener;
    if (localBaseOnTabSelectedListener != null) {
      removeOnTabSelectedListener(localBaseOnTabSelectedListener);
    }
    this.selectedListener = paramBaseOnTabSelectedListener;
    if (paramBaseOnTabSelectedListener != null) {
      addOnTabSelectedListener(paramBaseOnTabSelectedListener);
    }
  }
  
  @Deprecated
  public void setOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener)
  {
    setOnTabSelectedListener(paramOnTabSelectedListener);
  }
  
  void setPagerAdapter(PagerAdapter paramPagerAdapter, boolean paramBoolean)
  {
    PagerAdapter localPagerAdapter = this.pagerAdapter;
    if (localPagerAdapter != null)
    {
      DataSetObserver localDataSetObserver = this.pagerAdapterObserver;
      if (localDataSetObserver != null) {
        localPagerAdapter.unregisterDataSetObserver(localDataSetObserver);
      }
    }
    this.pagerAdapter = paramPagerAdapter;
    if ((paramBoolean) && (paramPagerAdapter != null))
    {
      if (this.pagerAdapterObserver == null) {
        this.pagerAdapterObserver = new PagerAdapterObserver();
      }
      paramPagerAdapter.registerDataSetObserver(this.pagerAdapterObserver);
    }
    populateFromPagerAdapter();
  }
  
  void setScrollAnimatorListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ensureScrollAnimator();
    this.scrollAnimator.addListener(paramAnimatorListener);
  }
  
  public void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean)
  {
    setScrollPosition(paramInt, paramFloat, paramBoolean, true);
  }
  
  public void setScrollPosition(int paramInt, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = Math.round(paramInt + paramFloat);
    if ((i >= 0) && (i < this.slidingTabIndicator.getChildCount()))
    {
      if (paramBoolean2) {
        this.slidingTabIndicator.setIndicatorPositionFromTabPosition(paramInt, paramFloat);
      }
      ValueAnimator localValueAnimator = this.scrollAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        this.scrollAnimator.cancel();
      }
      if (paramInt < 0) {
        paramInt = 0;
      } else {
        paramInt = calculateScrollXForTab(paramInt, paramFloat);
      }
      scrollTo(paramInt, 0);
      if (paramBoolean1) {
        setSelectedTabView(i);
      }
      return;
    }
  }
  
  public void setSelectedTabIndicator(int paramInt)
  {
    if (paramInt != 0) {
      setSelectedTabIndicator(AppCompatResources.getDrawable(getContext(), paramInt));
    } else {
      setSelectedTabIndicator(null);
    }
  }
  
  public void setSelectedTabIndicator(Drawable paramDrawable)
  {
    if (this.tabSelectedIndicator != paramDrawable)
    {
      if (paramDrawable == null) {
        paramDrawable = new GradientDrawable();
      }
      this.tabSelectedIndicator = paramDrawable;
      int i = this.tabIndicatorHeight;
      if (i == -1) {
        i = paramDrawable.getIntrinsicHeight();
      }
      this.slidingTabIndicator.setSelectedIndicatorHeight(i);
    }
  }
  
  public void setSelectedTabIndicatorColor(int paramInt)
  {
    this.tabSelectedIndicatorColor = paramInt;
    updateTabViews(false);
  }
  
  public void setSelectedTabIndicatorGravity(int paramInt)
  {
    if (this.tabIndicatorGravity != paramInt)
    {
      this.tabIndicatorGravity = paramInt;
      ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
    }
  }
  
  @Deprecated
  public void setSelectedTabIndicatorHeight(int paramInt)
  {
    this.tabIndicatorHeight = paramInt;
    this.slidingTabIndicator.setSelectedIndicatorHeight(paramInt);
  }
  
  public void setTabGravity(int paramInt)
  {
    if (this.tabGravity != paramInt)
    {
      this.tabGravity = paramInt;
      applyModeAndGravity();
    }
  }
  
  public void setTabIconTint(ColorStateList paramColorStateList)
  {
    if (this.tabIconTint != paramColorStateList)
    {
      this.tabIconTint = paramColorStateList;
      updateAllTabs();
    }
  }
  
  public void setTabIconTintResource(int paramInt)
  {
    setTabIconTint(AppCompatResources.getColorStateList(getContext(), paramInt));
  }
  
  public void setTabIndicatorAnimationMode(int paramInt)
  {
    this.tabIndicatorAnimationMode = paramInt;
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException(paramInt + " is not a valid TabIndicatorAnimationMode");
    case 2: 
      this.tabIndicatorInterpolator = new FadeTabIndicatorInterpolator();
      break;
    case 1: 
      this.tabIndicatorInterpolator = new ElasticTabIndicatorInterpolator();
      break;
    case 0: 
      this.tabIndicatorInterpolator = new TabIndicatorInterpolator();
    }
  }
  
  public void setTabIndicatorFullWidth(boolean paramBoolean)
  {
    this.tabIndicatorFullWidth = paramBoolean;
    this.slidingTabIndicator.jumpIndicatorToSelectedPosition();
    ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
  }
  
  public void setTabMode(int paramInt)
  {
    if (paramInt != this.mode)
    {
      this.mode = paramInt;
      applyModeAndGravity();
    }
  }
  
  public void setTabRippleColor(ColorStateList paramColorStateList)
  {
    if (this.tabRippleColorStateList != paramColorStateList)
    {
      this.tabRippleColorStateList = paramColorStateList;
      for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++)
      {
        paramColorStateList = this.slidingTabIndicator.getChildAt(i);
        if ((paramColorStateList instanceof TabView)) {
          ((TabView)paramColorStateList).updateBackgroundDrawable(getContext());
        }
      }
    }
  }
  
  public void setTabRippleColorResource(int paramInt)
  {
    setTabRippleColor(AppCompatResources.getColorStateList(getContext(), paramInt));
  }
  
  public void setTabTextColors(int paramInt1, int paramInt2)
  {
    setTabTextColors(createColorStateList(paramInt1, paramInt2));
  }
  
  public void setTabTextColors(ColorStateList paramColorStateList)
  {
    if (this.tabTextColors != paramColorStateList)
    {
      this.tabTextColors = paramColorStateList;
      updateAllTabs();
    }
  }
  
  @Deprecated
  public void setTabsFromPagerAdapter(PagerAdapter paramPagerAdapter)
  {
    setPagerAdapter(paramPagerAdapter, false);
  }
  
  public void setUnboundedRipple(boolean paramBoolean)
  {
    if (this.unboundedRipple != paramBoolean)
    {
      this.unboundedRipple = paramBoolean;
      for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++)
      {
        View localView = this.slidingTabIndicator.getChildAt(i);
        if ((localView instanceof TabView)) {
          ((TabView)localView).updateBackgroundDrawable(getContext());
        }
      }
    }
  }
  
  public void setUnboundedRippleResource(int paramInt)
  {
    setUnboundedRipple(getResources().getBoolean(paramInt));
  }
  
  public void setupWithViewPager(ViewPager paramViewPager)
  {
    setupWithViewPager(paramViewPager, true);
  }
  
  public void setupWithViewPager(ViewPager paramViewPager, boolean paramBoolean)
  {
    setupWithViewPager(paramViewPager, paramBoolean, false);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    boolean bool;
    if (getTabScrollRange() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void updateTabViews(boolean paramBoolean)
  {
    for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++)
    {
      View localView = this.slidingTabIndicator.getChildAt(i);
      localView.setMinimumWidth(getTabMinWidth());
      updateTabViewLayoutParams((LinearLayout.LayoutParams)localView.getLayoutParams());
      if (paramBoolean) {
        localView.requestLayout();
      }
    }
  }
  
  private class AdapterChangeListener
    implements ViewPager.OnAdapterChangeListener
  {
    private boolean autoRefresh;
    
    AdapterChangeListener() {}
    
    public void onAdapterChanged(ViewPager paramViewPager, PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2)
    {
      if (TabLayout.this.viewPager == paramViewPager) {
        TabLayout.this.setPagerAdapter(paramPagerAdapter2, this.autoRefresh);
      }
    }
    
    void setAutoRefresh(boolean paramBoolean)
    {
      this.autoRefresh = paramBoolean;
    }
  }
  
  @Deprecated
  public static abstract interface BaseOnTabSelectedListener<T extends TabLayout.Tab>
  {
    public abstract void onTabReselected(T paramT);
    
    public abstract void onTabSelected(T paramT);
    
    public abstract void onTabUnselected(T paramT);
  }
  
  public static @interface LabelVisibility {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  public static abstract interface OnTabSelectedListener
    extends TabLayout.BaseOnTabSelectedListener<TabLayout.Tab>
  {}
  
  private class PagerAdapterObserver
    extends DataSetObserver
  {
    PagerAdapterObserver() {}
    
    public void onChanged()
    {
      TabLayout.this.populateFromPagerAdapter();
    }
    
    public void onInvalidated()
    {
      TabLayout.this.populateFromPagerAdapter();
    }
  }
  
  class SlidingTabIndicator
    extends LinearLayout
  {
    ValueAnimator indicatorAnimator;
    private int layoutDirection = -1;
    int selectedPosition = -1;
    float selectionOffset;
    
    SlidingTabIndicator(Context paramContext)
    {
      super();
      setWillNotDraw(false);
    }
    
    private void jumpIndicatorToSelectedPosition()
    {
      View localView = getChildAt(this.selectedPosition);
      TabIndicatorInterpolator localTabIndicatorInterpolator = TabLayout.this.tabIndicatorInterpolator;
      TabLayout localTabLayout = TabLayout.this;
      localTabIndicatorInterpolator.setIndicatorBoundsForTab(localTabLayout, localView, localTabLayout.tabSelectedIndicator);
    }
    
    private void tweenIndicatorPosition(View paramView1, View paramView2, float paramFloat)
    {
      int i;
      if ((paramView1 != null) && (paramView1.getWidth() > 0)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        TabIndicatorInterpolator localTabIndicatorInterpolator = TabLayout.this.tabIndicatorInterpolator;
        TabLayout localTabLayout = TabLayout.this;
        localTabIndicatorInterpolator.updateIndicatorForOffset(localTabLayout, paramView1, paramView2, paramFloat, localTabLayout.tabSelectedIndicator);
      }
      else
      {
        TabLayout.this.tabSelectedIndicator.setBounds(-1, TabLayout.this.tabSelectedIndicator.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
    
    private void updateOrRecreateIndicatorAnimation(boolean paramBoolean, final int paramInt1, int paramInt2)
    {
      final Object localObject1 = getChildAt(this.selectedPosition);
      final Object localObject2 = getChildAt(paramInt1);
      if (localObject2 == null)
      {
        jumpIndicatorToSelectedPosition();
        return;
      }
      localObject1 = new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          TabLayout.SlidingTabIndicator.this.tweenIndicatorPosition(localObject1, localObject2, paramAnonymousValueAnimator.getAnimatedFraction());
        }
      };
      if (paramBoolean)
      {
        localObject2 = new ValueAnimator();
        this.indicatorAnimator = ((ValueAnimator)localObject2);
        ((ValueAnimator)localObject2).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        ((ValueAnimator)localObject2).setDuration(paramInt2);
        ((ValueAnimator)localObject2).setFloatValues(new float[] { 0.0F, 1.0F });
        ((ValueAnimator)localObject2).addUpdateListener((ValueAnimator.AnimatorUpdateListener)localObject1);
        ((ValueAnimator)localObject2).addListener(new AnimatorListenerAdapter()
        {
          public void onAnimationEnd(Animator paramAnonymousAnimator)
          {
            TabLayout.SlidingTabIndicator.this.selectedPosition = paramInt1;
          }
          
          public void onAnimationStart(Animator paramAnonymousAnimator)
          {
            TabLayout.SlidingTabIndicator.this.selectedPosition = paramInt1;
          }
        });
        ((ValueAnimator)localObject2).start();
      }
      else
      {
        this.indicatorAnimator.removeAllUpdateListeners();
        this.indicatorAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)localObject1);
      }
    }
    
    void animateIndicatorToPosition(int paramInt1, int paramInt2)
    {
      ValueAnimator localValueAnimator = this.indicatorAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        this.indicatorAnimator.cancel();
      }
      updateOrRecreateIndicatorAnimation(true, paramInt1, paramInt2);
    }
    
    boolean childrenNeedLayout()
    {
      int i = 0;
      int j = getChildCount();
      while (i < j)
      {
        if (getChildAt(i).getWidth() <= 0) {
          return true;
        }
        i++;
      }
      return false;
    }
    
    public void draw(Canvas paramCanvas)
    {
      int j = TabLayout.this.tabSelectedIndicator.getBounds().height();
      int i = j;
      if (j < 0) {
        i = TabLayout.this.tabSelectedIndicator.getIntrinsicHeight();
      }
      int k = 0;
      j = 0;
      switch (TabLayout.this.tabIndicatorGravity)
      {
      default: 
        i = k;
        break;
      case 3: 
        i = 0;
        j = getHeight();
        break;
      case 2: 
        k = 0;
        j = i;
        i = k;
        break;
      case 1: 
        j = (getHeight() - i) / 2;
        k = (getHeight() + i) / 2;
        i = j;
        j = k;
        break;
      case 0: 
        i = getHeight() - i;
        j = getHeight();
      }
      if (TabLayout.this.tabSelectedIndicator.getBounds().width() > 0)
      {
        Object localObject = TabLayout.this.tabSelectedIndicator.getBounds();
        TabLayout.this.tabSelectedIndicator.setBounds(((Rect)localObject).left, i, ((Rect)localObject).right, j);
        localObject = TabLayout.this.tabSelectedIndicator;
        if (TabLayout.this.tabSelectedIndicatorColor != 0)
        {
          localObject = DrawableCompat.wrap((Drawable)localObject);
          if (Build.VERSION.SDK_INT == 21) {
            ((Drawable)localObject).setColorFilter(TabLayout.this.tabSelectedIndicatorColor, PorterDuff.Mode.SRC_IN);
          } else {
            DrawableCompat.setTint((Drawable)localObject, TabLayout.this.tabSelectedIndicatorColor);
          }
        }
        else if (Build.VERSION.SDK_INT == 21)
        {
          ((Drawable)localObject).setColorFilter(null);
        }
        else
        {
          DrawableCompat.setTintList((Drawable)localObject, null);
        }
        ((Drawable)localObject).draw(paramCanvas);
      }
      super.draw(paramCanvas);
    }
    
    float getIndicatorPosition()
    {
      return this.selectedPosition + this.selectionOffset;
    }
    
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      ValueAnimator localValueAnimator = this.indicatorAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        updateOrRecreateIndicatorAnimation(false, this.selectedPosition, -1);
      } else {
        jumpIndicatorToSelectedPosition();
      }
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if (View.MeasureSpec.getMode(paramInt1) != 1073741824) {
        return;
      }
      if ((TabLayout.this.tabGravity == 1) || (TabLayout.this.mode == 2))
      {
        int m = getChildCount();
        int j = 0;
        int i = 0;
        Object localObject;
        while (i < m)
        {
          localObject = getChildAt(i);
          k = j;
          if (((View)localObject).getVisibility() == 0) {
            k = Math.max(j, ((View)localObject).getMeasuredWidth());
          }
          i++;
          j = k;
        }
        if (j <= 0) {
          return;
        }
        int k = (int)ViewUtils.dpToPx(getContext(), 16);
        i = 0;
        if (j * m <= getMeasuredWidth() - k * 2)
        {
          for (k = 0; k < m; k++)
          {
            localObject = (LinearLayout.LayoutParams)getChildAt(k).getLayoutParams();
            if ((((LinearLayout.LayoutParams)localObject).width != j) || (((LinearLayout.LayoutParams)localObject).weight != 0.0F))
            {
              ((LinearLayout.LayoutParams)localObject).width = j;
              ((LinearLayout.LayoutParams)localObject).weight = 0.0F;
              i = 1;
            }
          }
        }
        else
        {
          TabLayout.this.tabGravity = 0;
          TabLayout.this.updateTabViews(false);
          i = 1;
        }
        if (i != 0) {
          super.onMeasure(paramInt1, paramInt2);
        }
      }
    }
    
    public void onRtlPropertiesChanged(int paramInt)
    {
      super.onRtlPropertiesChanged(paramInt);
      if ((Build.VERSION.SDK_INT < 23) && (this.layoutDirection != paramInt))
      {
        requestLayout();
        this.layoutDirection = paramInt;
      }
    }
    
    void setIndicatorPositionFromTabPosition(int paramInt, float paramFloat)
    {
      ValueAnimator localValueAnimator = this.indicatorAnimator;
      if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
        this.indicatorAnimator.cancel();
      }
      this.selectedPosition = paramInt;
      this.selectionOffset = paramFloat;
      tweenIndicatorPosition(getChildAt(paramInt), getChildAt(this.selectedPosition + 1), this.selectionOffset);
    }
    
    void setSelectedIndicatorHeight(int paramInt)
    {
      Rect localRect = TabLayout.this.tabSelectedIndicator.getBounds();
      TabLayout.this.tabSelectedIndicator.setBounds(localRect.left, 0, localRect.right, paramInt);
      requestLayout();
    }
  }
  
  public static class Tab
  {
    public static final int INVALID_POSITION = -1;
    private CharSequence contentDesc;
    private View customView;
    private Drawable icon;
    private int id = -1;
    private int labelVisibilityMode = 1;
    public TabLayout parent;
    private int position = -1;
    private Object tag;
    private CharSequence text;
    public TabLayout.TabView view;
    
    public BadgeDrawable getBadge()
    {
      return TabLayout.TabView.access$1000(this.view);
    }
    
    public CharSequence getContentDescription()
    {
      Object localObject = this.view;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((TabLayout.TabView)localObject).getContentDescription();
      }
      return (CharSequence)localObject;
    }
    
    public View getCustomView()
    {
      return this.customView;
    }
    
    public Drawable getIcon()
    {
      return this.icon;
    }
    
    public int getId()
    {
      return this.id;
    }
    
    public BadgeDrawable getOrCreateBadge()
    {
      return TabLayout.TabView.access$800(this.view);
    }
    
    public int getPosition()
    {
      return this.position;
    }
    
    public int getTabLabelVisibility()
    {
      return this.labelVisibilityMode;
    }
    
    public Object getTag()
    {
      return this.tag;
    }
    
    public CharSequence getText()
    {
      return this.text;
    }
    
    public boolean isSelected()
    {
      TabLayout localTabLayout = this.parent;
      if (localTabLayout != null)
      {
        int i = localTabLayout.getSelectedTabPosition();
        boolean bool;
        if ((i != -1) && (i == this.position)) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public void removeBadge()
    {
      TabLayout.TabView.access$900(this.view);
    }
    
    void reset()
    {
      this.parent = null;
      this.view = null;
      this.tag = null;
      this.icon = null;
      this.id = -1;
      this.text = null;
      this.contentDesc = null;
      this.position = -1;
      this.customView = null;
    }
    
    public void select()
    {
      TabLayout localTabLayout = this.parent;
      if (localTabLayout != null)
      {
        localTabLayout.selectTab(this);
        return;
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(int paramInt)
    {
      TabLayout localTabLayout = this.parent;
      if (localTabLayout != null) {
        return setContentDescription(localTabLayout.getResources().getText(paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setContentDescription(CharSequence paramCharSequence)
    {
      this.contentDesc = paramCharSequence;
      updateView();
      return this;
    }
    
    public Tab setCustomView(int paramInt)
    {
      return setCustomView(LayoutInflater.from(this.view.getContext()).inflate(paramInt, this.view, false));
    }
    
    public Tab setCustomView(View paramView)
    {
      this.customView = paramView;
      updateView();
      return this;
    }
    
    public Tab setIcon(int paramInt)
    {
      TabLayout localTabLayout = this.parent;
      if (localTabLayout != null) {
        return setIcon(AppCompatResources.getDrawable(localTabLayout.getContext(), paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setIcon(Drawable paramDrawable)
    {
      this.icon = paramDrawable;
      if ((this.parent.tabGravity == 1) || (this.parent.mode == 2)) {
        this.parent.updateTabViews(true);
      }
      updateView();
      if ((BadgeUtils.USE_COMPAT_PARENT) && (TabLayout.TabView.access$600(this.view)) && (TabLayout.TabView.access$700(this.view).isVisible())) {
        this.view.invalidate();
      }
      return this;
    }
    
    public Tab setId(int paramInt)
    {
      this.id = paramInt;
      TabLayout.TabView localTabView = this.view;
      if (localTabView != null) {
        localTabView.setId(paramInt);
      }
      return this;
    }
    
    void setPosition(int paramInt)
    {
      this.position = paramInt;
    }
    
    public Tab setTabLabelVisibility(int paramInt)
    {
      this.labelVisibilityMode = paramInt;
      if ((this.parent.tabGravity == 1) || (this.parent.mode == 2)) {
        this.parent.updateTabViews(true);
      }
      updateView();
      if ((BadgeUtils.USE_COMPAT_PARENT) && (TabLayout.TabView.access$600(this.view)) && (TabLayout.TabView.access$700(this.view).isVisible())) {
        this.view.invalidate();
      }
      return this;
    }
    
    public Tab setTag(Object paramObject)
    {
      this.tag = paramObject;
      return this;
    }
    
    public Tab setText(int paramInt)
    {
      TabLayout localTabLayout = this.parent;
      if (localTabLayout != null) {
        return setText(localTabLayout.getResources().getText(paramInt));
      }
      throw new IllegalArgumentException("Tab not attached to a TabLayout");
    }
    
    public Tab setText(CharSequence paramCharSequence)
    {
      if ((TextUtils.isEmpty(this.contentDesc)) && (!TextUtils.isEmpty(paramCharSequence))) {
        this.view.setContentDescription(paramCharSequence);
      }
      this.text = paramCharSequence;
      updateView();
      return this;
    }
    
    void updateView()
    {
      TabLayout.TabView localTabView = this.view;
      if (localTabView != null) {
        localTabView.update();
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabGravity {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabIndicatorAnimationMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TabIndicatorGravity {}
  
  public static class TabLayoutOnPageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    private int previousScrollState;
    private int scrollState;
    private final WeakReference<TabLayout> tabLayoutRef;
    
    public TabLayoutOnPageChangeListener(TabLayout paramTabLayout)
    {
      this.tabLayoutRef = new WeakReference(paramTabLayout);
    }
    
    public void onPageScrollStateChanged(int paramInt)
    {
      this.previousScrollState = this.scrollState;
      this.scrollState = paramInt;
    }
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
    {
      TabLayout localTabLayout = (TabLayout)this.tabLayoutRef.get();
      if (localTabLayout != null)
      {
        paramInt2 = this.scrollState;
        boolean bool2 = false;
        boolean bool1;
        if ((paramInt2 == 2) && (this.previousScrollState != 1)) {
          bool1 = false;
        } else {
          bool1 = true;
        }
        if ((paramInt2 != 2) || (this.previousScrollState != 0)) {
          bool2 = true;
        }
        localTabLayout.setScrollPosition(paramInt1, paramFloat, bool1, bool2);
      }
    }
    
    public void onPageSelected(int paramInt)
    {
      TabLayout localTabLayout = (TabLayout)this.tabLayoutRef.get();
      if ((localTabLayout != null) && (localTabLayout.getSelectedTabPosition() != paramInt) && (paramInt < localTabLayout.getTabCount()))
      {
        int i = this.scrollState;
        boolean bool;
        if ((i != 0) && ((i != 2) || (this.previousScrollState != 0))) {
          bool = false;
        } else {
          bool = true;
        }
        localTabLayout.selectTab(localTabLayout.getTabAt(paramInt), bool);
      }
    }
    
    void reset()
    {
      this.scrollState = 0;
      this.previousScrollState = 0;
    }
  }
  
  public final class TabView
    extends LinearLayout
  {
    private View badgeAnchorView;
    private BadgeDrawable badgeDrawable;
    private Drawable baseBackgroundDrawable;
    private ImageView customIconView;
    private TextView customTextView;
    private View customView;
    private int defaultMaxLines = 2;
    private ImageView iconView;
    private TabLayout.Tab tab;
    private TextView textView;
    
    public TabView(Context paramContext)
    {
      super();
      updateBackgroundDrawable(paramContext);
      ViewCompat.setPaddingRelative(this, TabLayout.this.tabPaddingStart, TabLayout.this.tabPaddingTop, TabLayout.this.tabPaddingEnd, TabLayout.this.tabPaddingBottom);
      setGravity(17);
      setOrientation(TabLayout.this.inlineLabel ^ true);
      setClickable(true);
      ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
    }
    
    private void addOnLayoutChangeListener(final View paramView)
    {
      if (paramView == null) {
        return;
      }
      paramView.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
      {
        public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
        {
          if (paramView.getVisibility() == 0) {
            TabLayout.TabView.this.tryUpdateBadgeDrawableBounds(paramView);
          }
        }
      });
    }
    
    private float approximateLineWidth(Layout paramLayout, int paramInt, float paramFloat)
    {
      return paramLayout.getLineWidth(paramInt) * (paramFloat / paramLayout.getPaint().getTextSize());
    }
    
    private void clipViewToPaddingForBadge(boolean paramBoolean)
    {
      setClipChildren(paramBoolean);
      setClipToPadding(paramBoolean);
      ViewGroup localViewGroup = (ViewGroup)getParent();
      if (localViewGroup != null)
      {
        localViewGroup.setClipChildren(paramBoolean);
        localViewGroup.setClipToPadding(paramBoolean);
      }
    }
    
    private FrameLayout createPreApi18BadgeAnchorRoot()
    {
      FrameLayout localFrameLayout = new FrameLayout(getContext());
      localFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
      return localFrameLayout;
    }
    
    private void drawBackground(Canvas paramCanvas)
    {
      Drawable localDrawable = this.baseBackgroundDrawable;
      if (localDrawable != null)
      {
        localDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
        this.baseBackgroundDrawable.draw(paramCanvas);
      }
    }
    
    private BadgeDrawable getBadge()
    {
      return this.badgeDrawable;
    }
    
    private FrameLayout getCustomParentForBadge(View paramView)
    {
      ImageView localImageView = this.iconView;
      FrameLayout localFrameLayout = null;
      if ((paramView != localImageView) && (paramView != this.textView)) {
        return null;
      }
      if (BadgeUtils.USE_COMPAT_PARENT) {
        localFrameLayout = (FrameLayout)paramView.getParent();
      }
      return localFrameLayout;
    }
    
    private BadgeDrawable getOrCreateBadge()
    {
      if (this.badgeDrawable == null) {
        this.badgeDrawable = BadgeDrawable.create(getContext());
      }
      tryUpdateBadgeAnchor();
      BadgeDrawable localBadgeDrawable = this.badgeDrawable;
      if (localBadgeDrawable != null) {
        return localBadgeDrawable;
      }
      throw new IllegalStateException("Unable to create badge");
    }
    
    private boolean hasBadgeDrawable()
    {
      boolean bool;
      if (this.badgeDrawable != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private void inflateAndAddDefaultIconView()
    {
      Object localObject = this;
      if (BadgeUtils.USE_COMPAT_PARENT)
      {
        localObject = createPreApi18BadgeAnchorRoot();
        addView((View)localObject, 0);
      }
      ImageView localImageView = (ImageView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)localObject, false);
      this.iconView = localImageView;
      ((ViewGroup)localObject).addView(localImageView, 0);
    }
    
    private void inflateAndAddDefaultTextView()
    {
      Object localObject = this;
      if (BadgeUtils.USE_COMPAT_PARENT)
      {
        localObject = createPreApi18BadgeAnchorRoot();
        addView((View)localObject);
      }
      TextView localTextView = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)localObject, false);
      this.textView = localTextView;
      ((ViewGroup)localObject).addView(localTextView);
    }
    
    private void removeBadge()
    {
      if (this.badgeAnchorView != null) {
        tryRemoveBadgeFromAnchor();
      }
      this.badgeDrawable = null;
    }
    
    private void tryAttachBadgeToAnchor(View paramView)
    {
      if (!hasBadgeDrawable()) {
        return;
      }
      if (paramView != null)
      {
        clipViewToPaddingForBadge(false);
        BadgeUtils.attachBadgeDrawable(this.badgeDrawable, paramView, getCustomParentForBadge(paramView));
        this.badgeAnchorView = paramView;
      }
    }
    
    private void tryRemoveBadgeFromAnchor()
    {
      if (!hasBadgeDrawable()) {
        return;
      }
      clipViewToPaddingForBadge(true);
      View localView = this.badgeAnchorView;
      if (localView != null)
      {
        BadgeUtils.detachBadgeDrawable(this.badgeDrawable, localView);
        this.badgeAnchorView = null;
      }
    }
    
    private void tryUpdateBadgeAnchor()
    {
      if (!hasBadgeDrawable()) {
        return;
      }
      if (this.customView != null)
      {
        tryRemoveBadgeFromAnchor();
      }
      else
      {
        Object localObject1;
        Object localObject2;
        if (this.iconView != null)
        {
          localObject1 = this.tab;
          if ((localObject1 != null) && (((TabLayout.Tab)localObject1).getIcon() != null))
          {
            localObject2 = this.badgeAnchorView;
            localObject1 = this.iconView;
            if (localObject2 != localObject1)
            {
              tryRemoveBadgeFromAnchor();
              tryAttachBadgeToAnchor(this.iconView);
              return;
            }
            tryUpdateBadgeDrawableBounds((View)localObject1);
            return;
          }
        }
        if (this.textView != null)
        {
          localObject1 = this.tab;
          if ((localObject1 != null) && (((TabLayout.Tab)localObject1).getTabLabelVisibility() == 1))
          {
            localObject1 = this.badgeAnchorView;
            localObject2 = this.textView;
            if (localObject1 != localObject2)
            {
              tryRemoveBadgeFromAnchor();
              tryAttachBadgeToAnchor(this.textView);
              return;
            }
            tryUpdateBadgeDrawableBounds((View)localObject2);
            return;
          }
        }
        tryRemoveBadgeFromAnchor();
      }
    }
    
    private void tryUpdateBadgeDrawableBounds(View paramView)
    {
      if ((hasBadgeDrawable()) && (paramView == this.badgeAnchorView)) {
        BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, paramView, getCustomParentForBadge(paramView));
      }
    }
    
    private void updateBackgroundDrawable(Context paramContext)
    {
      int i = TabLayout.this.tabBackgroundResId;
      Object localObject = null;
      if (i != 0)
      {
        paramContext = AppCompatResources.getDrawable(paramContext, TabLayout.this.tabBackgroundResId);
        this.baseBackgroundDrawable = paramContext;
        if ((paramContext != null) && (paramContext.isStateful())) {
          this.baseBackgroundDrawable.setState(getDrawableState());
        }
      }
      else
      {
        this.baseBackgroundDrawable = null;
      }
      paramContext = new GradientDrawable();
      ((GradientDrawable)paramContext).setColor(0);
      if (TabLayout.this.tabRippleColorStateList != null)
      {
        GradientDrawable localGradientDrawable = new GradientDrawable();
        localGradientDrawable.setCornerRadius(1.0E-5F);
        localGradientDrawable.setColor(-1);
        ColorStateList localColorStateList = RippleUtils.convertToRippleDrawableColor(TabLayout.this.tabRippleColorStateList);
        if (Build.VERSION.SDK_INT >= 21)
        {
          if (TabLayout.this.unboundedRipple) {
            paramContext = null;
          }
          if (!TabLayout.this.unboundedRipple) {
            localObject = localGradientDrawable;
          }
          paramContext = new RippleDrawable(localColorStateList, paramContext, (Drawable)localObject);
        }
        else
        {
          localObject = DrawableCompat.wrap(localGradientDrawable);
          DrawableCompat.setTintList((Drawable)localObject, localColorStateList);
          paramContext = new LayerDrawable(new Drawable[] { paramContext, localObject });
        }
      }
      ViewCompat.setBackground(this, paramContext);
      TabLayout.this.invalidate();
    }
    
    private void updateTextAndIcon(TextView paramTextView, ImageView paramImageView)
    {
      Object localObject1 = this.tab;
      Object localObject2 = null;
      Drawable localDrawable;
      if ((localObject1 != null) && (((TabLayout.Tab)localObject1).getIcon() != null)) {
        localDrawable = DrawableCompat.wrap(this.tab.getIcon()).mutate();
      } else {
        localDrawable = null;
      }
      if (localDrawable != null)
      {
        DrawableCompat.setTintList(localDrawable, TabLayout.this.tabIconTint);
        if (TabLayout.this.tabIconTintMode != null) {
          DrawableCompat.setTintMode(localDrawable, TabLayout.this.tabIconTintMode);
        }
      }
      localObject1 = this.tab;
      if (localObject1 != null) {
        localObject1 = ((TabLayout.Tab)localObject1).getText();
      } else {
        localObject1 = null;
      }
      if (paramImageView != null) {
        if (localDrawable != null)
        {
          paramImageView.setImageDrawable(localDrawable);
          paramImageView.setVisibility(0);
          setVisibility(0);
        }
        else
        {
          paramImageView.setVisibility(8);
          paramImageView.setImageDrawable(null);
        }
      }
      boolean bool = TextUtils.isEmpty((CharSequence)localObject1) ^ true;
      if (paramTextView != null) {
        if (bool)
        {
          paramTextView.setText((CharSequence)localObject1);
          if (this.tab.labelVisibilityMode == 1) {
            paramTextView.setVisibility(0);
          } else {
            paramTextView.setVisibility(8);
          }
          setVisibility(0);
        }
        else
        {
          paramTextView.setVisibility(8);
          paramTextView.setText(null);
        }
      }
      if (paramImageView != null)
      {
        paramTextView = (ViewGroup.MarginLayoutParams)paramImageView.getLayoutParams();
        int j = 0;
        int i = j;
        if (bool)
        {
          i = j;
          if (paramImageView.getVisibility() == 0) {
            i = (int)ViewUtils.dpToPx(getContext(), 8);
          }
        }
        if (TabLayout.this.inlineLabel)
        {
          if (i != MarginLayoutParamsCompat.getMarginEnd(paramTextView))
          {
            MarginLayoutParamsCompat.setMarginEnd(paramTextView, i);
            paramTextView.bottomMargin = 0;
            paramImageView.setLayoutParams(paramTextView);
            paramImageView.requestLayout();
          }
        }
        else if (i != paramTextView.bottomMargin)
        {
          paramTextView.bottomMargin = i;
          MarginLayoutParamsCompat.setMarginEnd(paramTextView, 0);
          paramImageView.setLayoutParams(paramTextView);
          paramImageView.requestLayout();
        }
      }
      paramImageView = this.tab;
      paramTextView = (TextView)localObject2;
      if (paramImageView != null) {
        paramTextView = paramImageView.contentDesc;
      }
      if ((Build.VERSION.SDK_INT < 21) || (Build.VERSION.SDK_INT > 23))
      {
        if (bool) {
          paramTextView = (TextView)localObject1;
        }
        TooltipCompat.setTooltipText(this, paramTextView);
      }
    }
    
    protected void drawableStateChanged()
    {
      super.drawableStateChanged();
      boolean bool2 = false;
      int[] arrayOfInt = getDrawableState();
      Drawable localDrawable = this.baseBackgroundDrawable;
      boolean bool1 = bool2;
      if (localDrawable != null)
      {
        bool1 = bool2;
        if (localDrawable.isStateful()) {
          bool1 = false | this.baseBackgroundDrawable.setState(arrayOfInt);
        }
      }
      if (bool1)
      {
        invalidate();
        TabLayout.this.invalidate();
      }
    }
    
    int getContentHeight()
    {
      int m = 0;
      int n = 0;
      int k = 0;
      TextView localTextView = this.textView;
      int j = 0;
      ImageView localImageView = this.iconView;
      View localView2 = this.customView;
      while (j < 3)
      {
        View localView1 = new View[] { localTextView, localImageView, localView2 }[j];
        int i2 = m;
        int i1 = n;
        int i = k;
        if (localView1 != null)
        {
          i2 = m;
          i1 = n;
          i = k;
          if (localView1.getVisibility() == 0)
          {
            i1 = localView1.getTop();
            i = i1;
            if (m != 0) {
              i = Math.min(n, i1);
            }
            n = i;
            i1 = localView1.getBottom();
            i = i1;
            if (m != 0) {
              i = Math.max(k, i1);
            }
            i2 = 1;
            i1 = n;
          }
        }
        j++;
        m = i2;
        n = i1;
        k = i;
      }
      return k - n;
    }
    
    int getContentWidth()
    {
      int m = 0;
      int n = 0;
      int k = 0;
      TextView localTextView = this.textView;
      int j = 0;
      ImageView localImageView = this.iconView;
      View localView2 = this.customView;
      while (j < 3)
      {
        View localView1 = new View[] { localTextView, localImageView, localView2 }[j];
        int i2 = m;
        int i1 = n;
        int i = k;
        if (localView1 != null)
        {
          i2 = m;
          i1 = n;
          i = k;
          if (localView1.getVisibility() == 0)
          {
            i1 = localView1.getLeft();
            i = i1;
            if (m != 0) {
              i = Math.min(n, i1);
            }
            n = i;
            i1 = localView1.getRight();
            i = i1;
            if (m != 0) {
              i = Math.max(k, i1);
            }
            i2 = 1;
            i1 = n;
          }
        }
        j++;
        m = i2;
        n = i1;
        k = i;
      }
      return k - n;
    }
    
    public TabLayout.Tab getTab()
    {
      return this.tab;
    }
    
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
    {
      super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
      Object localObject = this.badgeDrawable;
      if ((localObject != null) && (((BadgeDrawable)localObject).isVisible()))
      {
        localObject = getContentDescription();
        paramAccessibilityNodeInfo.setContentDescription(localObject + ", " + this.badgeDrawable.getContentDescription());
      }
      paramAccessibilityNodeInfo = AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo);
      paramAccessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.tab.getPosition(), 1, false, isSelected()));
      if (isSelected())
      {
        paramAccessibilityNodeInfo.setClickable(false);
        paramAccessibilityNodeInfo.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
      }
      paramAccessibilityNodeInfo.setRoleDescription(getResources().getString(R.string.item_view_role_description));
    }
    
    public void onMeasure(int paramInt1, int paramInt2)
    {
      int i = View.MeasureSpec.getSize(paramInt1);
      int k = View.MeasureSpec.getMode(paramInt1);
      int j = TabLayout.this.getTabMaxWidth();
      if ((j > 0) && ((k == 0) || (i > j))) {
        paramInt1 = View.MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, Integer.MIN_VALUE);
      }
      super.onMeasure(paramInt1, paramInt2);
      if (this.textView != null)
      {
        float f2 = TabLayout.this.tabTextSize;
        j = this.defaultMaxLines;
        Object localObject = this.iconView;
        float f1;
        if ((localObject != null) && (((ImageView)localObject).getVisibility() == 0))
        {
          i = 1;
          f1 = f2;
        }
        else
        {
          localObject = this.textView;
          f1 = f2;
          i = j;
          if (localObject != null)
          {
            f1 = f2;
            i = j;
            if (((TextView)localObject).getLineCount() > 1)
            {
              f1 = TabLayout.this.tabTextMultiLineSize;
              i = j;
            }
          }
        }
        f2 = this.textView.getTextSize();
        int m = this.textView.getLineCount();
        j = TextViewCompat.getMaxLines(this.textView);
        if ((f1 != f2) || ((j >= 0) && (i != j)))
        {
          k = 1;
          j = k;
          if (TabLayout.this.mode == 1)
          {
            j = k;
            if (f1 > f2)
            {
              j = k;
              if (m == 1)
              {
                localObject = this.textView.getLayout();
                if (localObject != null)
                {
                  j = k;
                  if (approximateLineWidth((Layout)localObject, 0, f1) <= getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) {}
                }
                else
                {
                  j = 0;
                }
              }
            }
          }
          if (j != 0)
          {
            this.textView.setTextSize(0, f1);
            this.textView.setMaxLines(i);
            super.onMeasure(paramInt1, paramInt2);
          }
        }
      }
    }
    
    public boolean performClick()
    {
      boolean bool = super.performClick();
      if (this.tab != null)
      {
        if (!bool) {
          playSoundEffect(0);
        }
        this.tab.select();
        return true;
      }
      return bool;
    }
    
    void reset()
    {
      setTab(null);
      setSelected(false);
    }
    
    public void setSelected(boolean paramBoolean)
    {
      int i;
      if (isSelected() != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      super.setSelected(paramBoolean);
      if ((i != 0) && (paramBoolean) && (Build.VERSION.SDK_INT < 16)) {
        sendAccessibilityEvent(4);
      }
      Object localObject = this.textView;
      if (localObject != null) {
        ((TextView)localObject).setSelected(paramBoolean);
      }
      localObject = this.iconView;
      if (localObject != null) {
        ((ImageView)localObject).setSelected(paramBoolean);
      }
      localObject = this.customView;
      if (localObject != null) {
        ((View)localObject).setSelected(paramBoolean);
      }
    }
    
    void setTab(TabLayout.Tab paramTab)
    {
      if (paramTab != this.tab)
      {
        this.tab = paramTab;
        update();
      }
    }
    
    final void update()
    {
      TabLayout.Tab localTab = this.tab;
      Object localObject1;
      if (localTab != null) {
        localObject1 = localTab.getCustomView();
      } else {
        localObject1 = null;
      }
      if (localObject1 != null)
      {
        Object localObject2 = ((View)localObject1).getParent();
        if (localObject2 != this)
        {
          if (localObject2 != null) {
            ((ViewGroup)localObject2).removeView((View)localObject1);
          }
          addView((View)localObject1);
        }
        this.customView = ((View)localObject1);
        localObject2 = this.textView;
        if (localObject2 != null) {
          ((TextView)localObject2).setVisibility(8);
        }
        localObject2 = this.iconView;
        if (localObject2 != null)
        {
          ((ImageView)localObject2).setVisibility(8);
          this.iconView.setImageDrawable(null);
        }
        localObject2 = (TextView)((View)localObject1).findViewById(16908308);
        this.customTextView = ((TextView)localObject2);
        if (localObject2 != null) {
          this.defaultMaxLines = TextViewCompat.getMaxLines((TextView)localObject2);
        }
        this.customIconView = ((ImageView)((View)localObject1).findViewById(16908294));
      }
      else
      {
        localObject1 = this.customView;
        if (localObject1 != null)
        {
          removeView((View)localObject1);
          this.customView = null;
        }
        this.customTextView = null;
        this.customIconView = null;
      }
      if (this.customView == null)
      {
        if (this.iconView == null) {
          inflateAndAddDefaultIconView();
        }
        if (this.textView == null)
        {
          inflateAndAddDefaultTextView();
          this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
        }
        TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
        if (TabLayout.this.tabTextColors != null) {
          this.textView.setTextColor(TabLayout.this.tabTextColors);
        }
        updateTextAndIcon(this.textView, this.iconView);
        tryUpdateBadgeAnchor();
        addOnLayoutChangeListener(this.iconView);
        addOnLayoutChangeListener(this.textView);
      }
      else
      {
        localObject1 = this.customTextView;
        if ((localObject1 != null) || (this.customIconView != null)) {
          updateTextAndIcon((TextView)localObject1, this.customIconView);
        }
      }
      if ((localTab != null) && (!TextUtils.isEmpty(localTab.contentDesc))) {
        setContentDescription(localTab.contentDesc);
      }
      boolean bool;
      if ((localTab != null) && (localTab.isSelected())) {
        bool = true;
      } else {
        bool = false;
      }
      setSelected(bool);
    }
    
    final void updateOrientation()
    {
      setOrientation(TabLayout.this.inlineLabel ^ true);
      TextView localTextView = this.customTextView;
      if ((localTextView == null) && (this.customIconView == null)) {
        updateTextAndIcon(this.textView, this.iconView);
      } else {
        updateTextAndIcon(localTextView, this.customIconView);
      }
    }
  }
  
  public static class ViewPagerOnTabSelectedListener
    implements TabLayout.OnTabSelectedListener
  {
    private final ViewPager viewPager;
    
    public ViewPagerOnTabSelectedListener(ViewPager paramViewPager)
    {
      this.viewPager = paramViewPager;
    }
    
    public void onTabReselected(TabLayout.Tab paramTab) {}
    
    public void onTabSelected(TabLayout.Tab paramTab)
    {
      this.viewPager.setCurrentItem(paramTab.getPosition());
    }
    
    public void onTabUnselected(TabLayout.Tab paramTab) {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/tabs/TabLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */