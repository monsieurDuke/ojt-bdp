package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.styleable;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CollapsingToolbarLayout
  extends FrameLayout
{
  private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
  private static final int DEF_STYLE_RES = com.google.android.material.R.style.Widget_Design_CollapsingToolbar;
  public static final int TITLE_COLLAPSE_MODE_FADE = 1;
  public static final int TITLE_COLLAPSE_MODE_SCALE = 0;
  final CollapsingTextHelper collapsingTextHelper;
  private boolean collapsingTitleEnabled;
  private Drawable contentScrim;
  int currentOffset;
  private boolean drawCollapsingTitle;
  private View dummyView;
  final ElevationOverlayProvider elevationOverlayProvider;
  private int expandedMarginBottom;
  private int expandedMarginEnd;
  private int expandedMarginStart;
  private int expandedMarginTop;
  private int extraMultilineHeight = 0;
  private boolean extraMultilineHeightEnabled;
  private boolean forceApplySystemWindowInsetTop;
  WindowInsetsCompat lastInsets;
  private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
  private boolean refreshToolbar = true;
  private int scrimAlpha;
  private long scrimAnimationDuration;
  private ValueAnimator scrimAnimator;
  private int scrimVisibleHeightTrigger = -1;
  private boolean scrimsAreShown;
  Drawable statusBarScrim;
  private int titleCollapseMode;
  private final Rect tmpRect = new Rect();
  private ViewGroup toolbar;
  private View toolbarDirectChild;
  private int toolbarId;
  private int topInsetApplied = 0;
  
  public CollapsingToolbarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CollapsingToolbarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.collapsingToolbarLayoutStyle);
  }
  
  public CollapsingToolbarLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    Context localContext = getContext();
    paramContext = new CollapsingTextHelper(this);
    this.collapsingTextHelper = paramContext;
    paramContext.setTextSizeInterpolator(com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR);
    paramContext.setRtlTextDirectionHeuristicsEnabled(false);
    this.elevationOverlayProvider = new ElevationOverlayProvider(localContext);
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.CollapsingToolbarLayout, paramInt, i, new int[0]);
    paramContext.setExpandedTextGravity(paramAttributeSet.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
    paramContext.setCollapsedTextGravity(paramAttributeSet.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
    paramInt = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
    this.expandedMarginBottom = paramInt;
    this.expandedMarginEnd = paramInt;
    this.expandedMarginTop = paramInt;
    this.expandedMarginStart = paramInt;
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
      this.expandedMarginStart = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
      this.expandedMarginEnd = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
      this.expandedMarginTop = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
      this.expandedMarginBottom = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
    }
    this.collapsingTitleEnabled = paramAttributeSet.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
    setTitle(paramAttributeSet.getText(R.styleable.CollapsingToolbarLayout_title));
    paramContext.setExpandedTextAppearance(com.google.android.material.R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
    paramContext.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
      paramContext.setExpandedTextAppearance(paramAttributeSet.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
      paramContext.setCollapsedTextAppearance(paramAttributeSet.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextColor)) {
      paramContext.setExpandedTextColor(MaterialResources.getColorStateList(localContext, paramAttributeSet, R.styleable.CollapsingToolbarLayout_expandedTitleTextColor));
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextColor)) {
      paramContext.setCollapsedTextColor(MaterialResources.getColorStateList(localContext, paramAttributeSet, R.styleable.CollapsingToolbarLayout_collapsedTitleTextColor));
    }
    this.scrimVisibleHeightTrigger = paramAttributeSet.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_maxLines)) {
      paramContext.setMaxLines(paramAttributeSet.getInt(R.styleable.CollapsingToolbarLayout_maxLines, 1));
    }
    if (paramAttributeSet.hasValue(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator)) {
      paramContext.setPositionInterpolator(android.view.animation.AnimationUtils.loadInterpolator(localContext, paramAttributeSet.getResourceId(R.styleable.CollapsingToolbarLayout_titlePositionInterpolator, 0)));
    }
    this.scrimAnimationDuration = paramAttributeSet.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
    setContentScrim(paramAttributeSet.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
    setStatusBarScrim(paramAttributeSet.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
    setTitleCollapseMode(paramAttributeSet.getInt(R.styleable.CollapsingToolbarLayout_titleCollapseMode, 0));
    this.toolbarId = paramAttributeSet.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
    this.forceApplySystemWindowInsetTop = paramAttributeSet.getBoolean(R.styleable.CollapsingToolbarLayout_forceApplySystemWindowInsetTop, false);
    this.extraMultilineHeightEnabled = paramAttributeSet.getBoolean(R.styleable.CollapsingToolbarLayout_extraMultilineHeightEnabled, false);
    paramAttributeSet.recycle();
    setWillNotDraw(false);
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        return CollapsingToolbarLayout.this.onWindowInsetChanged(paramAnonymousWindowInsetsCompat);
      }
    });
  }
  
  private void animateScrim(int paramInt)
  {
    ensureToolbar();
    Object localObject = this.scrimAnimator;
    if (localObject == null)
    {
      ValueAnimator localValueAnimator = new ValueAnimator();
      this.scrimAnimator = localValueAnimator;
      if (paramInt > this.scrimAlpha) {
        localObject = com.google.android.material.animation.AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
      } else {
        localObject = com.google.android.material.animation.AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
      }
      localValueAnimator.setInterpolator((TimeInterpolator)localObject);
      this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          CollapsingToolbarLayout.this.setScrimAlpha(((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue());
        }
      });
    }
    else if (((ValueAnimator)localObject).isRunning())
    {
      this.scrimAnimator.cancel();
    }
    this.scrimAnimator.setDuration(this.scrimAnimationDuration);
    this.scrimAnimator.setIntValues(new int[] { this.scrimAlpha, paramInt });
    this.scrimAnimator.start();
  }
  
  private void disableLiftOnScrollIfNeeded(AppBarLayout paramAppBarLayout)
  {
    if (isTitleCollapseFadeMode()) {
      paramAppBarLayout.setLiftOnScroll(false);
    }
  }
  
  private void ensureToolbar()
  {
    if (!this.refreshToolbar) {
      return;
    }
    this.toolbar = null;
    this.toolbarDirectChild = null;
    int i = this.toolbarId;
    Object localObject1;
    if (i != -1)
    {
      localObject1 = (ViewGroup)findViewById(i);
      this.toolbar = ((ViewGroup)localObject1);
      if (localObject1 != null) {
        this.toolbarDirectChild = findDirectChild((View)localObject1);
      }
    }
    if (this.toolbar == null)
    {
      Object localObject2 = null;
      i = 0;
      int j = getChildCount();
      for (;;)
      {
        localObject1 = localObject2;
        if (i >= j) {
          break;
        }
        localObject1 = getChildAt(i);
        if (isToolbar((View)localObject1))
        {
          localObject1 = (ViewGroup)localObject1;
          break;
        }
        i++;
      }
      this.toolbar = ((ViewGroup)localObject1);
    }
    updateDummyView();
    this.refreshToolbar = false;
  }
  
  private View findDirectChild(View paramView)
  {
    View localView = paramView;
    for (paramView = paramView.getParent(); (paramView != this) && (paramView != null); paramView = paramView.getParent()) {
      if ((paramView instanceof View)) {
        localView = (View)paramView;
      }
    }
    return localView;
  }
  
  private static int getHeightWithMargins(View paramView)
  {
    Object localObject = paramView.getLayoutParams();
    if ((localObject instanceof ViewGroup.MarginLayoutParams))
    {
      localObject = (ViewGroup.MarginLayoutParams)localObject;
      return paramView.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams)localObject).topMargin + ((ViewGroup.MarginLayoutParams)localObject).bottomMargin;
    }
    return paramView.getMeasuredHeight();
  }
  
  private static CharSequence getToolbarTitle(View paramView)
  {
    if ((paramView instanceof androidx.appcompat.widget.Toolbar)) {
      return ((androidx.appcompat.widget.Toolbar)paramView).getTitle();
    }
    if ((Build.VERSION.SDK_INT >= 21) && ((paramView instanceof android.widget.Toolbar))) {
      return ((android.widget.Toolbar)paramView).getTitle();
    }
    return null;
  }
  
  static ViewOffsetHelper getViewOffsetHelper(View paramView)
  {
    ViewOffsetHelper localViewOffsetHelper2 = (ViewOffsetHelper)paramView.getTag(R.id.view_offset_helper);
    ViewOffsetHelper localViewOffsetHelper1 = localViewOffsetHelper2;
    if (localViewOffsetHelper2 == null)
    {
      localViewOffsetHelper1 = new ViewOffsetHelper(paramView);
      paramView.setTag(R.id.view_offset_helper, localViewOffsetHelper1);
    }
    return localViewOffsetHelper1;
  }
  
  private boolean isTitleCollapseFadeMode()
  {
    int i = this.titleCollapseMode;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isToolbar(View paramView)
  {
    boolean bool;
    if ((!(paramView instanceof androidx.appcompat.widget.Toolbar)) && ((Build.VERSION.SDK_INT < 21) || (!(paramView instanceof android.widget.Toolbar)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean isToolbarChild(View paramView)
  {
    View localView = this.toolbarDirectChild;
    boolean bool = true;
    if ((localView != null) && (localView != this))
    {
      if (paramView != localView) {
        bool = false;
      }
    }
    else if (paramView != this.toolbar) {
      bool = false;
    }
    return bool;
  }
  
  private void updateCollapsedBounds(boolean paramBoolean)
  {
    Object localObject = this.toolbarDirectChild;
    if (localObject == null) {
      localObject = this.toolbar;
    }
    int i1 = getMaxOffsetForPinChild((View)localObject);
    DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
    localObject = this.toolbar;
    int i;
    int j;
    int m;
    int k;
    if ((localObject instanceof androidx.appcompat.widget.Toolbar))
    {
      localObject = (androidx.appcompat.widget.Toolbar)localObject;
      i = ((androidx.appcompat.widget.Toolbar)localObject).getTitleMarginStart();
      j = ((androidx.appcompat.widget.Toolbar)localObject).getTitleMarginEnd();
      m = ((androidx.appcompat.widget.Toolbar)localObject).getTitleMarginTop();
      k = ((androidx.appcompat.widget.Toolbar)localObject).getTitleMarginBottom();
    }
    else
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        localObject = this.toolbar;
        if ((localObject instanceof android.widget.Toolbar))
        {
          localObject = (android.widget.Toolbar)localObject;
          i = ((android.widget.Toolbar)localObject).getTitleMarginStart();
          j = ((android.widget.Toolbar)localObject).getTitleMarginEnd();
          m = ((android.widget.Toolbar)localObject).getTitleMarginTop();
          k = ((android.widget.Toolbar)localObject).getTitleMarginBottom();
          break label158;
        }
      }
      i = 0;
      j = 0;
      m = 0;
      k = 0;
    }
    label158:
    localObject = this.collapsingTextHelper;
    int i2 = this.tmpRect.left;
    int n;
    if (paramBoolean) {
      n = j;
    } else {
      n = i;
    }
    int i4 = this.tmpRect.top;
    int i3 = this.tmpRect.right;
    if (paramBoolean) {
      j = i;
    }
    ((CollapsingTextHelper)localObject).setCollapsedBounds(i2 + n, i4 + i1 + m, i3 - j, this.tmpRect.bottom + i1 - k);
  }
  
  private void updateContentDescriptionFromTitle()
  {
    setContentDescription(getTitle());
  }
  
  private void updateContentScrimBounds(Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    updateContentScrimBounds(paramDrawable, this.toolbar, paramInt1, paramInt2);
  }
  
  private void updateContentScrimBounds(Drawable paramDrawable, View paramView, int paramInt1, int paramInt2)
  {
    if ((isTitleCollapseFadeMode()) && (paramView != null) && (this.collapsingTitleEnabled)) {
      paramInt2 = paramView.getBottom();
    }
    paramDrawable.setBounds(0, 0, paramInt1, paramInt2);
  }
  
  private void updateDummyView()
  {
    if (!this.collapsingTitleEnabled)
    {
      Object localObject = this.dummyView;
      if (localObject != null)
      {
        localObject = ((View)localObject).getParent();
        if ((localObject instanceof ViewGroup)) {
          ((ViewGroup)localObject).removeView(this.dummyView);
        }
      }
    }
    if ((this.collapsingTitleEnabled) && (this.toolbar != null))
    {
      if (this.dummyView == null) {
        this.dummyView = new View(getContext());
      }
      if (this.dummyView.getParent() == null) {
        this.toolbar.addView(this.dummyView, -1, -1);
      }
    }
  }
  
  private void updateTextBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (this.collapsingTitleEnabled)
    {
      Object localObject = this.dummyView;
      if (localObject != null)
      {
        boolean bool1 = ViewCompat.isAttachedToWindow((View)localObject);
        boolean bool2 = false;
        if ((bool1) && (this.dummyView.getVisibility() == 0)) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.drawCollapsingTitle = bool1;
        if ((bool1) || (paramBoolean))
        {
          bool1 = bool2;
          if (ViewCompat.getLayoutDirection(this) == 1) {
            bool1 = true;
          }
          updateCollapsedBounds(bool1);
          localObject = this.collapsingTextHelper;
          int i;
          if (bool1) {
            i = this.expandedMarginEnd;
          } else {
            i = this.expandedMarginStart;
          }
          int m = this.tmpRect.top;
          int k = this.expandedMarginTop;
          int j;
          if (bool1) {
            j = this.expandedMarginStart;
          } else {
            j = this.expandedMarginEnd;
          }
          ((CollapsingTextHelper)localObject).setExpandedBounds(i, m + k, paramInt3 - paramInt1 - j, paramInt4 - paramInt2 - this.expandedMarginBottom);
          this.collapsingTextHelper.recalculate(paramBoolean);
        }
      }
    }
  }
  
  private void updateTitleFromToolbarIfNeeded()
  {
    if ((this.toolbar != null) && (this.collapsingTitleEnabled) && (TextUtils.isEmpty(this.collapsingTextHelper.getText()))) {
      setTitle(getToolbarTitle(this.toolbar));
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    ensureToolbar();
    Object localObject;
    if (this.toolbar == null)
    {
      localObject = this.contentScrim;
      if ((localObject != null) && (this.scrimAlpha > 0))
      {
        ((Drawable)localObject).mutate().setAlpha(this.scrimAlpha);
        this.contentScrim.draw(paramCanvas);
      }
    }
    int i;
    if ((this.collapsingTitleEnabled) && (this.drawCollapsingTitle)) {
      if ((this.toolbar != null) && (this.contentScrim != null) && (this.scrimAlpha > 0) && (isTitleCollapseFadeMode()) && (this.collapsingTextHelper.getExpansionFraction() < this.collapsingTextHelper.getFadeModeThresholdFraction()))
      {
        i = paramCanvas.save();
        paramCanvas.clipRect(this.contentScrim.getBounds(), Region.Op.DIFFERENCE);
        this.collapsingTextHelper.draw(paramCanvas);
        paramCanvas.restoreToCount(i);
      }
      else
      {
        this.collapsingTextHelper.draw(paramCanvas);
      }
    }
    if ((this.statusBarScrim != null) && (this.scrimAlpha > 0))
    {
      localObject = this.lastInsets;
      if (localObject != null) {
        i = ((WindowInsetsCompat)localObject).getSystemWindowInsetTop();
      } else {
        i = 0;
      }
      if (i > 0)
      {
        this.statusBarScrim.setBounds(0, -this.currentOffset, getWidth(), i - this.currentOffset);
        this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
        this.statusBarScrim.draw(paramCanvas);
      }
    }
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    int j = 0;
    int i = j;
    if (this.contentScrim != null)
    {
      i = j;
      if (this.scrimAlpha > 0)
      {
        i = j;
        if (isToolbarChild(paramView))
        {
          updateContentScrimBounds(this.contentScrim, paramView, getWidth(), getHeight());
          this.contentScrim.mutate().setAlpha(this.scrimAlpha);
          this.contentScrim.draw(paramCanvas);
          i = 1;
        }
      }
    }
    boolean bool;
    if ((!super.drawChild(paramCanvas, paramView, paramLong)) && (i == 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    boolean bool1 = false;
    Object localObject = this.statusBarScrim;
    boolean bool2 = bool1;
    if (localObject != null)
    {
      bool2 = bool1;
      if (((Drawable)localObject).isStateful()) {
        bool2 = false | ((Drawable)localObject).setState(arrayOfInt);
      }
    }
    localObject = this.contentScrim;
    bool1 = bool2;
    if (localObject != null)
    {
      bool1 = bool2;
      if (((Drawable)localObject).isStateful()) {
        bool1 = bool2 | ((Drawable)localObject).setState(arrayOfInt);
      }
    }
    localObject = this.collapsingTextHelper;
    bool2 = bool1;
    if (localObject != null) {
      bool2 = bool1 | ((CollapsingTextHelper)localObject).setState(arrayOfInt);
    }
    if (bool2) {
      invalidate();
    }
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -1);
  }
  
  public FrameLayout.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getCollapsedTitleGravity()
  {
    return this.collapsingTextHelper.getCollapsedTextGravity();
  }
  
  public Typeface getCollapsedTitleTypeface()
  {
    return this.collapsingTextHelper.getCollapsedTypeface();
  }
  
  public Drawable getContentScrim()
  {
    return this.contentScrim;
  }
  
  public int getExpandedTitleGravity()
  {
    return this.collapsingTextHelper.getExpandedTextGravity();
  }
  
  public int getExpandedTitleMarginBottom()
  {
    return this.expandedMarginBottom;
  }
  
  public int getExpandedTitleMarginEnd()
  {
    return this.expandedMarginEnd;
  }
  
  public int getExpandedTitleMarginStart()
  {
    return this.expandedMarginStart;
  }
  
  public int getExpandedTitleMarginTop()
  {
    return this.expandedMarginTop;
  }
  
  public Typeface getExpandedTitleTypeface()
  {
    return this.collapsingTextHelper.getExpandedTypeface();
  }
  
  public int getHyphenationFrequency()
  {
    return this.collapsingTextHelper.getHyphenationFrequency();
  }
  
  public int getLineCount()
  {
    return this.collapsingTextHelper.getLineCount();
  }
  
  public float getLineSpacingAdd()
  {
    return this.collapsingTextHelper.getLineSpacingAdd();
  }
  
  public float getLineSpacingMultiplier()
  {
    return this.collapsingTextHelper.getLineSpacingMultiplier();
  }
  
  public int getMaxLines()
  {
    return this.collapsingTextHelper.getMaxLines();
  }
  
  final int getMaxOffsetForPinChild(View paramView)
  {
    ViewOffsetHelper localViewOffsetHelper = getViewOffsetHelper(paramView);
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    return getHeight() - localViewOffsetHelper.getLayoutTop() - paramView.getHeight() - localLayoutParams.bottomMargin;
  }
  
  int getScrimAlpha()
  {
    return this.scrimAlpha;
  }
  
  public long getScrimAnimationDuration()
  {
    return this.scrimAnimationDuration;
  }
  
  public int getScrimVisibleHeightTrigger()
  {
    int i = this.scrimVisibleHeightTrigger;
    if (i >= 0) {
      return i + this.topInsetApplied + this.extraMultilineHeight;
    }
    WindowInsetsCompat localWindowInsetsCompat = this.lastInsets;
    if (localWindowInsetsCompat != null) {
      i = localWindowInsetsCompat.getSystemWindowInsetTop();
    } else {
      i = 0;
    }
    int j = ViewCompat.getMinimumHeight(this);
    if (j > 0) {
      return Math.min(j * 2 + i, getHeight());
    }
    return getHeight() / 3;
  }
  
  public Drawable getStatusBarScrim()
  {
    return this.statusBarScrim;
  }
  
  public CharSequence getTitle()
  {
    CharSequence localCharSequence;
    if (this.collapsingTitleEnabled) {
      localCharSequence = this.collapsingTextHelper.getText();
    } else {
      localCharSequence = null;
    }
    return localCharSequence;
  }
  
  public int getTitleCollapseMode()
  {
    return this.titleCollapseMode;
  }
  
  public TimeInterpolator getTitlePositionInterpolator()
  {
    return this.collapsingTextHelper.getPositionInterpolator();
  }
  
  public boolean isExtraMultilineHeightEnabled()
  {
    return this.extraMultilineHeightEnabled;
  }
  
  public boolean isForceApplySystemWindowInsetTop()
  {
    return this.forceApplySystemWindowInsetTop;
  }
  
  public boolean isRtlTextDirectionHeuristicsEnabled()
  {
    return this.collapsingTextHelper.isRtlTextDirectionHeuristicsEnabled();
  }
  
  public boolean isTitleEnabled()
  {
    return this.collapsingTitleEnabled;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Object localObject = getParent();
    if ((localObject instanceof AppBarLayout))
    {
      localObject = (AppBarLayout)localObject;
      disableLiftOnScrollIfNeeded((AppBarLayout)localObject);
      ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View)localObject));
      if (this.onOffsetChangedListener == null) {
        this.onOffsetChangedListener = new OffsetUpdateListener();
      }
      ((AppBarLayout)localObject).addOnOffsetChangedListener(this.onOffsetChangedListener);
      ViewCompat.requestApplyInsets(this);
    }
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(paramConfiguration);
  }
  
  protected void onDetachedFromWindow()
  {
    ViewParent localViewParent = getParent();
    AppBarLayout.OnOffsetChangedListener localOnOffsetChangedListener = this.onOffsetChangedListener;
    if ((localOnOffsetChangedListener != null) && ((localViewParent instanceof AppBarLayout))) {
      ((AppBarLayout)localViewParent).removeOnOffsetChangedListener(localOnOffsetChangedListener);
    }
    super.onDetachedFromWindow();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Object localObject = this.lastInsets;
    if (localObject != null)
    {
      int k = ((WindowInsetsCompat)localObject).getSystemWindowInsetTop();
      i = 0;
      j = getChildCount();
      while (i < j)
      {
        localObject = getChildAt(i);
        if ((!ViewCompat.getFitsSystemWindows((View)localObject)) && (((View)localObject).getTop() < k)) {
          ViewCompat.offsetTopAndBottom((View)localObject, k);
        }
        i++;
      }
    }
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      getViewOffsetHelper(getChildAt(i)).onViewLayout();
      i++;
    }
    updateTextBounds(paramInt1, paramInt2, paramInt3, paramInt4, false);
    updateTitleFromToolbarIfNeeded();
    updateScrimVisibility();
    paramInt1 = 0;
    paramInt2 = getChildCount();
    while (paramInt1 < paramInt2)
    {
      getViewOffsetHelper(getChildAt(paramInt1)).applyOffsets();
      paramInt1++;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    ensureToolbar();
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getMode(paramInt2);
    Object localObject = this.lastInsets;
    if (localObject != null) {
      paramInt2 = ((WindowInsetsCompat)localObject).getSystemWindowInsetTop();
    } else {
      paramInt2 = 0;
    }
    if (((i == 0) || (this.forceApplySystemWindowInsetTop)) && (paramInt2 > 0))
    {
      this.topInsetApplied = paramInt2;
      super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + paramInt2, 1073741824));
    }
    if ((this.extraMultilineHeightEnabled) && (this.collapsingTextHelper.getMaxLines() > 1))
    {
      updateTitleFromToolbarIfNeeded();
      updateTextBounds(0, 0, getMeasuredWidth(), getMeasuredHeight(), true);
      paramInt2 = this.collapsingTextHelper.getExpandedLineCount();
      if (paramInt2 > 1)
      {
        this.extraMultilineHeight = ((paramInt2 - 1) * Math.round(this.collapsingTextHelper.getExpandedTextFullHeight()));
        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + this.extraMultilineHeight, 1073741824));
      }
    }
    ViewGroup localViewGroup = this.toolbar;
    if (localViewGroup != null)
    {
      localObject = this.toolbarDirectChild;
      if ((localObject != null) && (localObject != this)) {
        setMinimumHeight(getHeightWithMargins((View)localObject));
      } else {
        setMinimumHeight(getHeightWithMargins(localViewGroup));
      }
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    Drawable localDrawable = this.contentScrim;
    if (localDrawable != null) {
      updateContentScrimBounds(localDrawable, paramInt1, paramInt2);
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
      requestLayout();
    }
    return paramWindowInsetsCompat.consumeSystemWindowInsets();
  }
  
  public void setCollapsedTitleGravity(int paramInt)
  {
    this.collapsingTextHelper.setCollapsedTextGravity(paramInt);
  }
  
  public void setCollapsedTitleTextAppearance(int paramInt)
  {
    this.collapsingTextHelper.setCollapsedTextAppearance(paramInt);
  }
  
  public void setCollapsedTitleTextColor(int paramInt)
  {
    setCollapsedTitleTextColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setCollapsedTitleTextColor(ColorStateList paramColorStateList)
  {
    this.collapsingTextHelper.setCollapsedTextColor(paramColorStateList);
  }
  
  public void setCollapsedTitleTypeface(Typeface paramTypeface)
  {
    this.collapsingTextHelper.setCollapsedTypeface(paramTypeface);
  }
  
  public void setContentScrim(Drawable paramDrawable)
  {
    Drawable localDrawable2 = this.contentScrim;
    if (localDrawable2 != paramDrawable)
    {
      Drawable localDrawable1 = null;
      if (localDrawable2 != null) {
        localDrawable2.setCallback(null);
      }
      if (paramDrawable != null) {
        localDrawable1 = paramDrawable.mutate();
      }
      this.contentScrim = localDrawable1;
      if (localDrawable1 != null)
      {
        updateContentScrimBounds(localDrawable1, getWidth(), getHeight());
        this.contentScrim.setCallback(this);
        this.contentScrim.setAlpha(this.scrimAlpha);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setContentScrimColor(int paramInt)
  {
    setContentScrim(new ColorDrawable(paramInt));
  }
  
  public void setContentScrimResource(int paramInt)
  {
    setContentScrim(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setExpandedTitleColor(int paramInt)
  {
    setExpandedTitleTextColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setExpandedTitleGravity(int paramInt)
  {
    this.collapsingTextHelper.setExpandedTextGravity(paramInt);
  }
  
  public void setExpandedTitleMargin(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.expandedMarginStart = paramInt1;
    this.expandedMarginTop = paramInt2;
    this.expandedMarginEnd = paramInt3;
    this.expandedMarginBottom = paramInt4;
    requestLayout();
  }
  
  public void setExpandedTitleMarginBottom(int paramInt)
  {
    this.expandedMarginBottom = paramInt;
    requestLayout();
  }
  
  public void setExpandedTitleMarginEnd(int paramInt)
  {
    this.expandedMarginEnd = paramInt;
    requestLayout();
  }
  
  public void setExpandedTitleMarginStart(int paramInt)
  {
    this.expandedMarginStart = paramInt;
    requestLayout();
  }
  
  public void setExpandedTitleMarginTop(int paramInt)
  {
    this.expandedMarginTop = paramInt;
    requestLayout();
  }
  
  public void setExpandedTitleTextAppearance(int paramInt)
  {
    this.collapsingTextHelper.setExpandedTextAppearance(paramInt);
  }
  
  public void setExpandedTitleTextColor(ColorStateList paramColorStateList)
  {
    this.collapsingTextHelper.setExpandedTextColor(paramColorStateList);
  }
  
  public void setExpandedTitleTypeface(Typeface paramTypeface)
  {
    this.collapsingTextHelper.setExpandedTypeface(paramTypeface);
  }
  
  public void setExtraMultilineHeightEnabled(boolean paramBoolean)
  {
    this.extraMultilineHeightEnabled = paramBoolean;
  }
  
  public void setForceApplySystemWindowInsetTop(boolean paramBoolean)
  {
    this.forceApplySystemWindowInsetTop = paramBoolean;
  }
  
  public void setHyphenationFrequency(int paramInt)
  {
    this.collapsingTextHelper.setHyphenationFrequency(paramInt);
  }
  
  public void setLineSpacingAdd(float paramFloat)
  {
    this.collapsingTextHelper.setLineSpacingAdd(paramFloat);
  }
  
  public void setLineSpacingMultiplier(float paramFloat)
  {
    this.collapsingTextHelper.setLineSpacingMultiplier(paramFloat);
  }
  
  public void setMaxLines(int paramInt)
  {
    this.collapsingTextHelper.setMaxLines(paramInt);
  }
  
  public void setRtlTextDirectionHeuristicsEnabled(boolean paramBoolean)
  {
    this.collapsingTextHelper.setRtlTextDirectionHeuristicsEnabled(paramBoolean);
  }
  
  void setScrimAlpha(int paramInt)
  {
    if (paramInt != this.scrimAlpha)
    {
      if (this.contentScrim != null)
      {
        ViewGroup localViewGroup = this.toolbar;
        if (localViewGroup != null) {
          ViewCompat.postInvalidateOnAnimation(localViewGroup);
        }
      }
      this.scrimAlpha = paramInt;
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setScrimAnimationDuration(long paramLong)
  {
    this.scrimAnimationDuration = paramLong;
  }
  
  public void setScrimVisibleHeightTrigger(int paramInt)
  {
    if (this.scrimVisibleHeightTrigger != paramInt)
    {
      this.scrimVisibleHeightTrigger = paramInt;
      updateScrimVisibility();
    }
  }
  
  public void setScrimsShown(boolean paramBoolean)
  {
    boolean bool;
    if ((ViewCompat.isLaidOut(this)) && (!isInEditMode())) {
      bool = true;
    } else {
      bool = false;
    }
    setScrimsShown(paramBoolean, bool);
  }
  
  public void setScrimsShown(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.scrimsAreShown != paramBoolean1)
    {
      int i = 255;
      if (paramBoolean2)
      {
        if (!paramBoolean1) {
          i = 0;
        }
        animateScrim(i);
      }
      else
      {
        if (!paramBoolean1) {
          i = 0;
        }
        setScrimAlpha(i);
      }
      this.scrimsAreShown = paramBoolean1;
    }
  }
  
  public void setStatusBarScrim(Drawable paramDrawable)
  {
    Drawable localDrawable2 = this.statusBarScrim;
    if (localDrawable2 != paramDrawable)
    {
      Drawable localDrawable1 = null;
      if (localDrawable2 != null) {
        localDrawable2.setCallback(null);
      }
      if (paramDrawable != null) {
        localDrawable1 = paramDrawable.mutate();
      }
      this.statusBarScrim = localDrawable1;
      if (localDrawable1 != null)
      {
        if (localDrawable1.isStateful()) {
          this.statusBarScrim.setState(getDrawableState());
        }
        DrawableCompat.setLayoutDirection(this.statusBarScrim, ViewCompat.getLayoutDirection(this));
        paramDrawable = this.statusBarScrim;
        boolean bool;
        if (getVisibility() == 0) {
          bool = true;
        } else {
          bool = false;
        }
        paramDrawable.setVisible(bool, false);
        this.statusBarScrim.setCallback(this);
        this.statusBarScrim.setAlpha(this.scrimAlpha);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setStatusBarScrimColor(int paramInt)
  {
    setStatusBarScrim(new ColorDrawable(paramInt));
  }
  
  public void setStatusBarScrimResource(int paramInt)
  {
    setStatusBarScrim(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.collapsingTextHelper.setText(paramCharSequence);
    updateContentDescriptionFromTitle();
  }
  
  public void setTitleCollapseMode(int paramInt)
  {
    this.titleCollapseMode = paramInt;
    boolean bool = isTitleCollapseFadeMode();
    this.collapsingTextHelper.setFadeModeEnabled(bool);
    ViewParent localViewParent = getParent();
    if ((localViewParent instanceof AppBarLayout)) {
      disableLiftOnScrollIfNeeded((AppBarLayout)localViewParent);
    }
    if ((bool) && (this.contentScrim == null))
    {
      float f = getResources().getDimension(R.dimen.design_appbar_elevation);
      setContentScrimColor(this.elevationOverlayProvider.compositeOverlayWithThemeSurfaceColorIfNeeded(f));
    }
  }
  
  public void setTitleEnabled(boolean paramBoolean)
  {
    if (paramBoolean != this.collapsingTitleEnabled)
    {
      this.collapsingTitleEnabled = paramBoolean;
      updateContentDescriptionFromTitle();
      updateDummyView();
      requestLayout();
    }
  }
  
  public void setTitlePositionInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.collapsingTextHelper.setPositionInterpolator(paramTimeInterpolator);
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
    Drawable localDrawable = this.statusBarScrim;
    if ((localDrawable != null) && (localDrawable.isVisible() != bool)) {
      this.statusBarScrim.setVisible(bool, false);
    }
    localDrawable = this.contentScrim;
    if ((localDrawable != null) && (localDrawable.isVisible() != bool)) {
      this.contentScrim.setVisible(bool, false);
    }
  }
  
  final void updateScrimVisibility()
  {
    if ((this.contentScrim != null) || (this.statusBarScrim != null))
    {
      boolean bool;
      if (getHeight() + this.currentOffset < getScrimVisibleHeightTrigger()) {
        bool = true;
      } else {
        bool = false;
      }
      setScrimsShown(bool);
    }
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.contentScrim) && (paramDrawable != this.statusBarScrim)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static class LayoutParams
    extends FrameLayout.LayoutParams
  {
    public static final int COLLAPSE_MODE_OFF = 0;
    public static final int COLLAPSE_MODE_PARALLAX = 2;
    public static final int COLLAPSE_MODE_PIN = 1;
    private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5F;
    int collapseMode = 0;
    float parallaxMult = 0.5F;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt2, paramInt3);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CollapsingToolbarLayout_Layout);
      this.collapseMode = paramContext.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
      setParallaxMultiplier(paramContext.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5F));
      paramContext.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public LayoutParams(FrameLayout.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public int getCollapseMode()
    {
      return this.collapseMode;
    }
    
    public float getParallaxMultiplier()
    {
      return this.parallaxMult;
    }
    
    public void setCollapseMode(int paramInt)
    {
      this.collapseMode = paramInt;
    }
    
    public void setParallaxMultiplier(float paramFloat)
    {
      this.parallaxMult = paramFloat;
    }
  }
  
  private class OffsetUpdateListener
    implements AppBarLayout.OnOffsetChangedListener
  {
    OffsetUpdateListener() {}
    
    public void onOffsetChanged(AppBarLayout paramAppBarLayout, int paramInt)
    {
      CollapsingToolbarLayout.this.currentOffset = paramInt;
      if (CollapsingToolbarLayout.this.lastInsets != null) {
        i = CollapsingToolbarLayout.this.lastInsets.getSystemWindowInsetTop();
      } else {
        i = 0;
      }
      int j = 0;
      int k = CollapsingToolbarLayout.this.getChildCount();
      while (j < k)
      {
        paramAppBarLayout = CollapsingToolbarLayout.this.getChildAt(j);
        CollapsingToolbarLayout.LayoutParams localLayoutParams = (CollapsingToolbarLayout.LayoutParams)paramAppBarLayout.getLayoutParams();
        ViewOffsetHelper localViewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(paramAppBarLayout);
        switch (localLayoutParams.collapseMode)
        {
        default: 
          break;
        case 2: 
          localViewOffsetHelper.setTopAndBottomOffset(Math.round(-paramInt * localLayoutParams.parallaxMult));
          break;
        case 1: 
          localViewOffsetHelper.setTopAndBottomOffset(MathUtils.clamp(-paramInt, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild(paramAppBarLayout)));
        }
        j++;
      }
      CollapsingToolbarLayout.this.updateScrimVisibility();
      if ((CollapsingToolbarLayout.this.statusBarScrim != null) && (i > 0)) {
        ViewCompat.postInvalidateOnAnimation(CollapsingToolbarLayout.this);
      }
      j = CollapsingToolbarLayout.this.getHeight();
      int i = j - ViewCompat.getMinimumHeight(CollapsingToolbarLayout.this) - i;
      k = CollapsingToolbarLayout.this.getScrimVisibleHeightTrigger();
      CollapsingToolbarLayout.this.collapsingTextHelper.setFadeModeStartFraction(Math.min(1.0F, (j - k) / i));
      CollapsingToolbarLayout.this.collapsingTextHelper.setCurrentOffsetY(CollapsingToolbarLayout.this.currentOffset + i);
      CollapsingToolbarLayout.this.collapsingTextHelper.setExpansionFraction(Math.abs(paramInt) / i);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TitleCollapseMode {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/appbar/CollapsingToolbarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */