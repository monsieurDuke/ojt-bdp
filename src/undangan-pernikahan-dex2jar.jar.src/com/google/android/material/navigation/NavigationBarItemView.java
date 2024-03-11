package com.google.android.material.navigation;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView.ItemView;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.drawable;
import com.google.android.material.R.id;
import com.google.android.material.R.integer;
import com.google.android.material.R.string;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;

public abstract class NavigationBarItemView
  extends FrameLayout
  implements MenuView.ItemView
{
  private static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform(null);
  private static final ActiveIndicatorTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform(null);
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int INVALID_ITEM_POSITION = -1;
  private ValueAnimator activeIndicatorAnimator;
  private int activeIndicatorDesiredHeight = 0;
  private int activeIndicatorDesiredWidth = 0;
  private boolean activeIndicatorEnabled = false;
  private int activeIndicatorMarginHorizontal = 0;
  private float activeIndicatorProgress = 0.0F;
  private boolean activeIndicatorResizeable = false;
  private ActiveIndicatorTransform activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
  private final View activeIndicatorView;
  private BadgeDrawable badgeDrawable;
  private final ImageView icon;
  private final FrameLayout iconContainer;
  private ColorStateList iconTint;
  private boolean initialized = false;
  private boolean isShifting;
  private MenuItemImpl itemData;
  private int itemPaddingBottom;
  private int itemPaddingTop;
  private int itemPosition = -1;
  private final ViewGroup labelGroup;
  private int labelVisibilityMode;
  private final TextView largeLabel;
  private Drawable originalIconDrawable;
  private float scaleDownFactor;
  private float scaleUpFactor;
  private float shiftAmount;
  private final TextView smallLabel;
  private Drawable wrappedIconDrawable;
  
  public NavigationBarItemView(Context paramContext)
  {
    super(paramContext);
    LayoutInflater.from(paramContext).inflate(getItemLayoutResId(), this, true);
    this.iconContainer = ((FrameLayout)findViewById(R.id.navigation_bar_item_icon_container));
    this.activeIndicatorView = findViewById(R.id.navigation_bar_item_active_indicator_view);
    paramContext = (ImageView)findViewById(R.id.navigation_bar_item_icon_view);
    this.icon = paramContext;
    ViewGroup localViewGroup = (ViewGroup)findViewById(R.id.navigation_bar_item_labels_group);
    this.labelGroup = localViewGroup;
    TextView localTextView1 = (TextView)findViewById(R.id.navigation_bar_item_small_label_view);
    this.smallLabel = localTextView1;
    TextView localTextView2 = (TextView)findViewById(R.id.navigation_bar_item_large_label_view);
    this.largeLabel = localTextView2;
    setBackgroundResource(getItemBackgroundResId());
    this.itemPaddingTop = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
    this.itemPaddingBottom = localViewGroup.getPaddingBottom();
    ViewCompat.setImportantForAccessibility(localTextView1, 2);
    ViewCompat.setImportantForAccessibility(localTextView2, 2);
    setFocusable(true);
    calculateTextScaleFactors(localTextView1.getTextSize(), localTextView2.getTextSize());
    if (paramContext != null) {
      paramContext.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
      {
        public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
        {
          if (NavigationBarItemView.this.icon.getVisibility() == 0)
          {
            paramAnonymousView = NavigationBarItemView.this;
            paramAnonymousView.tryUpdateBadgeBounds(paramAnonymousView.icon);
          }
        }
      });
    }
  }
  
  private void calculateTextScaleFactors(float paramFloat1, float paramFloat2)
  {
    this.shiftAmount = (paramFloat1 - paramFloat2);
    this.scaleUpFactor = (paramFloat2 * 1.0F / paramFloat1);
    this.scaleDownFactor = (1.0F * paramFloat1 / paramFloat2);
  }
  
  private FrameLayout getCustomParentForBadge(View paramView)
  {
    ImageView localImageView = this.icon;
    Object localObject = null;
    if (paramView == localImageView)
    {
      paramView = (View)localObject;
      if (BadgeUtils.USE_COMPAT_PARENT) {
        paramView = (FrameLayout)this.icon.getParent();
      }
      return paramView;
    }
    return null;
  }
  
  private View getIconOrContainer()
  {
    Object localObject = this.iconContainer;
    if (localObject == null) {
      localObject = this.icon;
    }
    return (View)localObject;
  }
  
  private int getItemVisiblePosition()
  {
    ViewGroup localViewGroup = (ViewGroup)getParent();
    int m = localViewGroup.indexOfChild(this);
    int j = 0;
    int i = 0;
    while (i < m)
    {
      View localView = localViewGroup.getChildAt(i);
      int k = j;
      if ((localView instanceof NavigationBarItemView))
      {
        k = j;
        if (localView.getVisibility() == 0) {
          k = j + 1;
        }
      }
      i++;
      j = k;
    }
    return j;
  }
  
  private int getSuggestedIconHeight()
  {
    int i = 0;
    BadgeDrawable localBadgeDrawable = this.badgeDrawable;
    if (localBadgeDrawable != null) {
      i = localBadgeDrawable.getMinimumHeight() / 2;
    }
    return Math.max(i, ((FrameLayout.LayoutParams)getIconOrContainer().getLayoutParams()).topMargin) + this.icon.getMeasuredWidth() + i;
  }
  
  private int getSuggestedIconWidth()
  {
    Object localObject = this.badgeDrawable;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((BadgeDrawable)localObject).getMinimumWidth() - this.badgeDrawable.getHorizontalOffset();
    }
    localObject = (FrameLayout.LayoutParams)getIconOrContainer().getLayoutParams();
    return Math.max(i, ((FrameLayout.LayoutParams)localObject).leftMargin) + this.icon.getMeasuredWidth() + Math.max(i, ((FrameLayout.LayoutParams)localObject).rightMargin);
  }
  
  private boolean hasBadge()
  {
    boolean bool;
    if (this.badgeDrawable != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isActiveIndicatorResizeableAndUnlabeled()
  {
    boolean bool;
    if ((this.activeIndicatorResizeable) && (this.labelVisibilityMode == 2)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void maybeAnimateActiveIndicatorToProgress(final float paramFloat)
  {
    if ((this.activeIndicatorEnabled) && (this.initialized) && (ViewCompat.isAttachedToWindow(this)))
    {
      ValueAnimator localValueAnimator = this.activeIndicatorAnimator;
      if (localValueAnimator != null)
      {
        localValueAnimator.cancel();
        this.activeIndicatorAnimator = null;
      }
      localValueAnimator = ValueAnimator.ofFloat(new float[] { this.activeIndicatorProgress, paramFloat });
      this.activeIndicatorAnimator = localValueAnimator;
      localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
          NavigationBarItemView.this.setActiveIndicatorProgress(f, paramFloat);
        }
      });
      this.activeIndicatorAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
      this.activeIndicatorAnimator.setDuration(MotionUtils.resolveThemeDuration(getContext(), R.attr.motionDurationLong1, getResources().getInteger(R.integer.material_motion_duration_long_1)));
      this.activeIndicatorAnimator.start();
      return;
    }
    setActiveIndicatorProgress(paramFloat, paramFloat);
  }
  
  private void refreshChecked()
  {
    MenuItemImpl localMenuItemImpl = this.itemData;
    if (localMenuItemImpl != null) {
      setChecked(localMenuItemImpl.isChecked());
    }
  }
  
  private void setActiveIndicatorProgress(float paramFloat1, float paramFloat2)
  {
    View localView = this.activeIndicatorView;
    if (localView != null) {
      this.activeIndicatorTransform.updateForProgress(paramFloat1, paramFloat2, localView);
    }
    this.activeIndicatorProgress = paramFloat1;
  }
  
  private static void setTextAppearanceWithoutFontScaling(TextView paramTextView, int paramInt)
  {
    TextViewCompat.setTextAppearance(paramTextView, paramInt);
    paramInt = MaterialResources.getUnscaledTextSize(paramTextView.getContext(), paramInt, 0);
    if (paramInt != 0) {
      paramTextView.setTextSize(0, paramInt);
    }
  }
  
  private static void setViewScaleValues(View paramView, float paramFloat1, float paramFloat2, int paramInt)
  {
    paramView.setScaleX(paramFloat1);
    paramView.setScaleY(paramFloat2);
    paramView.setVisibility(paramInt);
  }
  
  private static void setViewTopMarginAndGravity(View paramView, int paramInt1, int paramInt2)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)paramView.getLayoutParams();
    localLayoutParams.topMargin = paramInt1;
    localLayoutParams.bottomMargin = paramInt1;
    localLayoutParams.gravity = paramInt2;
    paramView.setLayoutParams(localLayoutParams);
  }
  
  private void tryAttachBadgeToAnchor(View paramView)
  {
    if (!hasBadge()) {
      return;
    }
    if (paramView != null)
    {
      setClipChildren(false);
      setClipToPadding(false);
      BadgeUtils.attachBadgeDrawable(this.badgeDrawable, paramView, getCustomParentForBadge(paramView));
    }
  }
  
  private void tryRemoveBadgeFromAnchor(View paramView)
  {
    if (!hasBadge()) {
      return;
    }
    if (paramView != null)
    {
      setClipChildren(true);
      setClipToPadding(true);
      BadgeUtils.detachBadgeDrawable(this.badgeDrawable, paramView);
    }
    this.badgeDrawable = null;
  }
  
  private void tryUpdateBadgeBounds(View paramView)
  {
    if (!hasBadge()) {
      return;
    }
    BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, paramView, getCustomParentForBadge(paramView));
  }
  
  private void updateActiveIndicatorLayoutParams(int paramInt)
  {
    if (this.activeIndicatorView == null) {
      return;
    }
    int i = Math.min(this.activeIndicatorDesiredWidth, paramInt - this.activeIndicatorMarginHorizontal * 2);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.activeIndicatorView.getLayoutParams();
    if (isActiveIndicatorResizeableAndUnlabeled()) {
      paramInt = i;
    } else {
      paramInt = this.activeIndicatorDesiredHeight;
    }
    localLayoutParams.height = paramInt;
    localLayoutParams.width = i;
    this.activeIndicatorView.setLayoutParams(localLayoutParams);
  }
  
  private void updateActiveIndicatorTransform()
  {
    if (isActiveIndicatorResizeableAndUnlabeled()) {
      this.activeIndicatorTransform = ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
    } else {
      this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
    }
  }
  
  private static void updateViewPaddingBottom(View paramView, int paramInt)
  {
    paramView.setPadding(paramView.getPaddingLeft(), paramView.getPaddingTop(), paramView.getPaddingRight(), paramInt);
  }
  
  void clear()
  {
    removeBadge();
    this.itemData = null;
    this.activeIndicatorProgress = 0.0F;
    this.initialized = false;
  }
  
  public Drawable getActiveIndicatorDrawable()
  {
    View localView = this.activeIndicatorView;
    if (localView == null) {
      return null;
    }
    return localView.getBackground();
  }
  
  public BadgeDrawable getBadge()
  {
    return this.badgeDrawable;
  }
  
  protected int getItemBackgroundResId()
  {
    return R.drawable.mtrl_navigation_bar_item_background;
  }
  
  public MenuItemImpl getItemData()
  {
    return this.itemData;
  }
  
  protected int getItemDefaultMarginResId()
  {
    return R.dimen.mtrl_navigation_bar_item_default_margin;
  }
  
  protected abstract int getItemLayoutResId();
  
  public int getItemPosition()
  {
    return this.itemPosition;
  }
  
  protected int getSuggestedMinimumHeight()
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.labelGroup.getLayoutParams();
    return getSuggestedIconHeight() + localLayoutParams.topMargin + this.labelGroup.getMeasuredHeight() + localLayoutParams.bottomMargin;
  }
  
  protected int getSuggestedMinimumWidth()
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.labelGroup.getLayoutParams();
    int i = localLayoutParams.leftMargin;
    int k = this.labelGroup.getMeasuredWidth();
    int j = localLayoutParams.rightMargin;
    return Math.max(getSuggestedIconWidth(), i + k + j);
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt)
  {
    this.itemData = paramMenuItemImpl;
    setCheckable(paramMenuItemImpl.isCheckable());
    setChecked(paramMenuItemImpl.isChecked());
    setEnabled(paramMenuItemImpl.isEnabled());
    setIcon(paramMenuItemImpl.getIcon());
    setTitle(paramMenuItemImpl.getTitle());
    setId(paramMenuItemImpl.getItemId());
    if (!TextUtils.isEmpty(paramMenuItemImpl.getContentDescription())) {
      setContentDescription(paramMenuItemImpl.getContentDescription());
    }
    CharSequence localCharSequence;
    if (!TextUtils.isEmpty(paramMenuItemImpl.getTooltipText())) {
      localCharSequence = paramMenuItemImpl.getTooltipText();
    } else {
      localCharSequence = paramMenuItemImpl.getTitle();
    }
    if ((Build.VERSION.SDK_INT < 21) || (Build.VERSION.SDK_INT > 23)) {
      TooltipCompat.setTooltipText(this, localCharSequence);
    }
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    }
    setVisibility(paramInt);
    this.initialized = true;
  }
  
  public int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    MenuItemImpl localMenuItemImpl = this.itemData;
    if ((localMenuItemImpl != null) && (localMenuItemImpl.isCheckable()) && (this.itemData.isChecked())) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    Object localObject = this.badgeDrawable;
    if ((localObject != null) && (((BadgeDrawable)localObject).isVisible()))
    {
      localObject = this.itemData.getTitle();
      if (!TextUtils.isEmpty(this.itemData.getContentDescription())) {
        localObject = this.itemData.getContentDescription();
      }
      paramAccessibilityNodeInfo.setContentDescription(localObject + ", " + this.badgeDrawable.getContentDescription());
    }
    paramAccessibilityNodeInfo = AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, getItemVisiblePosition(), 1, false, isSelected()));
    if (isSelected())
    {
      paramAccessibilityNodeInfo.setClickable(false);
      paramAccessibilityNodeInfo.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
    }
    paramAccessibilityNodeInfo.setRoleDescription(getResources().getString(R.string.item_view_role_description));
  }
  
  protected void onSizeChanged(final int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    post(new Runnable()
    {
      public void run()
      {
        NavigationBarItemView.this.updateActiveIndicatorLayoutParams(paramInt1);
      }
    });
  }
  
  public boolean prefersCondensedTitle()
  {
    return false;
  }
  
  void removeBadge()
  {
    tryRemoveBadgeFromAnchor(this.icon);
  }
  
  public void setActiveIndicatorDrawable(Drawable paramDrawable)
  {
    View localView = this.activeIndicatorView;
    if (localView == null) {
      return;
    }
    localView.setBackgroundDrawable(paramDrawable);
  }
  
  public void setActiveIndicatorEnabled(boolean paramBoolean)
  {
    this.activeIndicatorEnabled = paramBoolean;
    View localView = this.activeIndicatorView;
    if (localView != null)
    {
      int i;
      if (paramBoolean) {
        i = 0;
      } else {
        i = 8;
      }
      localView.setVisibility(i);
      requestLayout();
    }
  }
  
  public void setActiveIndicatorHeight(int paramInt)
  {
    this.activeIndicatorDesiredHeight = paramInt;
    updateActiveIndicatorLayoutParams(getWidth());
  }
  
  public void setActiveIndicatorMarginHorizontal(int paramInt)
  {
    this.activeIndicatorMarginHorizontal = paramInt;
    updateActiveIndicatorLayoutParams(getWidth());
  }
  
  public void setActiveIndicatorResizeable(boolean paramBoolean)
  {
    this.activeIndicatorResizeable = paramBoolean;
  }
  
  public void setActiveIndicatorWidth(int paramInt)
  {
    this.activeIndicatorDesiredWidth = paramInt;
    updateActiveIndicatorLayoutParams(getWidth());
  }
  
  void setBadge(BadgeDrawable paramBadgeDrawable)
  {
    if (this.badgeDrawable == paramBadgeDrawable) {
      return;
    }
    if ((hasBadge()) && (this.icon != null))
    {
      Log.w("NavigationBar", "Multiple badges shouldn't be attached to one item.");
      tryRemoveBadgeFromAnchor(this.icon);
    }
    this.badgeDrawable = paramBadgeDrawable;
    paramBadgeDrawable = this.icon;
    if (paramBadgeDrawable != null) {
      tryAttachBadgeToAnchor(paramBadgeDrawable);
    }
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    refreshDrawableState();
  }
  
  public void setChecked(boolean paramBoolean)
  {
    TextView localTextView = this.largeLabel;
    localTextView.setPivotX(localTextView.getWidth() / 2);
    localTextView = this.largeLabel;
    localTextView.setPivotY(localTextView.getBaseline());
    localTextView = this.smallLabel;
    localTextView.setPivotX(localTextView.getWidth() / 2);
    localTextView = this.smallLabel;
    localTextView.setPivotY(localTextView.getBaseline());
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    maybeAnimateActiveIndicatorToProgress(f);
    switch (this.labelVisibilityMode)
    {
    default: 
      break;
    case 2: 
      setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
      this.largeLabel.setVisibility(8);
      this.smallLabel.setVisibility(8);
      break;
    case 1: 
      updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
      if (paramBoolean)
      {
        setViewTopMarginAndGravity(getIconOrContainer(), (int)(this.itemPaddingTop + this.shiftAmount), 49);
        setViewScaleValues(this.largeLabel, 1.0F, 1.0F, 0);
        localTextView = this.smallLabel;
        f = this.scaleUpFactor;
        setViewScaleValues(localTextView, f, f, 4);
      }
      else
      {
        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
        localTextView = this.largeLabel;
        f = this.scaleDownFactor;
        setViewScaleValues(localTextView, f, f, 4);
        setViewScaleValues(this.smallLabel, 1.0F, 1.0F, 0);
      }
      break;
    case 0: 
      if (paramBoolean)
      {
        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
        this.largeLabel.setVisibility(0);
      }
      else
      {
        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
        updateViewPaddingBottom(this.labelGroup, 0);
        this.largeLabel.setVisibility(4);
      }
      this.smallLabel.setVisibility(4);
      break;
    case -1: 
      if (this.isShifting)
      {
        if (paramBoolean)
        {
          setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
          updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
          this.largeLabel.setVisibility(0);
        }
        else
        {
          setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
          updateViewPaddingBottom(this.labelGroup, 0);
          this.largeLabel.setVisibility(4);
        }
        this.smallLabel.setVisibility(4);
      }
      else
      {
        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
        if (paramBoolean)
        {
          setViewTopMarginAndGravity(getIconOrContainer(), (int)(this.itemPaddingTop + this.shiftAmount), 49);
          setViewScaleValues(this.largeLabel, 1.0F, 1.0F, 0);
          localTextView = this.smallLabel;
          f = this.scaleUpFactor;
          setViewScaleValues(localTextView, f, f, 4);
        }
        else
        {
          setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
          localTextView = this.largeLabel;
          f = this.scaleDownFactor;
          setViewScaleValues(localTextView, f, f, 4);
          setViewScaleValues(this.smallLabel, 1.0F, 1.0F, 0);
        }
      }
      break;
    }
    refreshDrawableState();
    setSelected(paramBoolean);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.smallLabel.setEnabled(paramBoolean);
    this.largeLabel.setEnabled(paramBoolean);
    this.icon.setEnabled(paramBoolean);
    if (paramBoolean) {
      ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(getContext(), 1002));
    } else {
      ViewCompat.setPointerIcon(this, null);
    }
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    if (paramDrawable == this.originalIconDrawable) {
      return;
    }
    this.originalIconDrawable = paramDrawable;
    Object localObject = paramDrawable;
    if (paramDrawable != null)
    {
      localObject = paramDrawable.getConstantState();
      if (localObject != null) {
        paramDrawable = ((Drawable.ConstantState)localObject).newDrawable();
      }
      paramDrawable = DrawableCompat.wrap(paramDrawable).mutate();
      this.wrappedIconDrawable = paramDrawable;
      ColorStateList localColorStateList = this.iconTint;
      localObject = paramDrawable;
      if (localColorStateList != null)
      {
        DrawableCompat.setTintList(paramDrawable, localColorStateList);
        localObject = paramDrawable;
      }
    }
    this.icon.setImageDrawable((Drawable)localObject);
  }
  
  public void setIconSize(int paramInt)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.icon.getLayoutParams();
    localLayoutParams.width = paramInt;
    localLayoutParams.height = paramInt;
    this.icon.setLayoutParams(localLayoutParams);
  }
  
  public void setIconTintList(ColorStateList paramColorStateList)
  {
    this.iconTint = paramColorStateList;
    if (this.itemData != null)
    {
      Drawable localDrawable = this.wrappedIconDrawable;
      if (localDrawable != null)
      {
        DrawableCompat.setTintList(localDrawable, paramColorStateList);
        this.wrappedIconDrawable.invalidateSelf();
      }
    }
  }
  
  public void setItemBackground(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt == 0) {
      localDrawable = null;
    } else {
      localDrawable = ContextCompat.getDrawable(getContext(), paramInt);
    }
    setItemBackground(localDrawable);
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    Drawable localDrawable = paramDrawable;
    if (paramDrawable != null)
    {
      localDrawable = paramDrawable;
      if (paramDrawable.getConstantState() != null) {
        localDrawable = paramDrawable.getConstantState().newDrawable().mutate();
      }
    }
    ViewCompat.setBackground(this, localDrawable);
  }
  
  public void setItemPaddingBottom(int paramInt)
  {
    if (this.itemPaddingBottom != paramInt)
    {
      this.itemPaddingBottom = paramInt;
      refreshChecked();
    }
  }
  
  public void setItemPaddingTop(int paramInt)
  {
    if (this.itemPaddingTop != paramInt)
    {
      this.itemPaddingTop = paramInt;
      refreshChecked();
    }
  }
  
  public void setItemPosition(int paramInt)
  {
    this.itemPosition = paramInt;
  }
  
  public void setLabelVisibilityMode(int paramInt)
  {
    if (this.labelVisibilityMode != paramInt)
    {
      this.labelVisibilityMode = paramInt;
      updateActiveIndicatorTransform();
      updateActiveIndicatorLayoutParams(getWidth());
      refreshChecked();
    }
  }
  
  public void setShifting(boolean paramBoolean)
  {
    if (this.isShifting != paramBoolean)
    {
      this.isShifting = paramBoolean;
      refreshChecked();
    }
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTextAppearanceActive(int paramInt)
  {
    setTextAppearanceWithoutFontScaling(this.largeLabel, paramInt);
    calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
  }
  
  public void setTextAppearanceInactive(int paramInt)
  {
    setTextAppearanceWithoutFontScaling(this.smallLabel, paramInt);
    calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    if (paramColorStateList != null)
    {
      this.smallLabel.setTextColor(paramColorStateList);
      this.largeLabel.setTextColor(paramColorStateList);
    }
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.smallLabel.setText(paramCharSequence);
    this.largeLabel.setText(paramCharSequence);
    MenuItemImpl localMenuItemImpl = this.itemData;
    if ((localMenuItemImpl == null) || (TextUtils.isEmpty(localMenuItemImpl.getContentDescription()))) {
      setContentDescription(paramCharSequence);
    }
    localMenuItemImpl = this.itemData;
    if ((localMenuItemImpl != null) && (!TextUtils.isEmpty(localMenuItemImpl.getTooltipText()))) {
      paramCharSequence = this.itemData.getTooltipText();
    }
    if ((Build.VERSION.SDK_INT < 21) || (Build.VERSION.SDK_INT > 23)) {
      TooltipCompat.setTooltipText(this, paramCharSequence);
    }
  }
  
  public boolean showsIcon()
  {
    return true;
  }
  
  private static class ActiveIndicatorTransform
  {
    private static final float ALPHA_FRACTION = 0.2F;
    private static final float SCALE_X_HIDDEN = 0.4F;
    private static final float SCALE_X_SHOWN = 1.0F;
    
    protected float calculateAlpha(float paramFloat1, float paramFloat2)
    {
      float f;
      if (paramFloat2 == 0.0F) {
        f = 0.8F;
      } else {
        f = 0.0F;
      }
      if (paramFloat2 == 0.0F) {
        paramFloat2 = 1.0F;
      } else {
        paramFloat2 = 0.2F;
      }
      return AnimationUtils.lerp(0.0F, 1.0F, f, paramFloat2, paramFloat1);
    }
    
    protected float calculateScaleX(float paramFloat1, float paramFloat2)
    {
      return AnimationUtils.lerp(0.4F, 1.0F, paramFloat1);
    }
    
    protected float calculateScaleY(float paramFloat1, float paramFloat2)
    {
      return 1.0F;
    }
    
    public void updateForProgress(float paramFloat1, float paramFloat2, View paramView)
    {
      paramView.setScaleX(calculateScaleX(paramFloat1, paramFloat2));
      paramView.setScaleY(calculateScaleY(paramFloat1, paramFloat2));
      paramView.setAlpha(calculateAlpha(paramFloat1, paramFloat2));
    }
  }
  
  private static class ActiveIndicatorUnlabeledTransform
    extends NavigationBarItemView.ActiveIndicatorTransform
  {
    private ActiveIndicatorUnlabeledTransform()
    {
      super();
    }
    
    protected float calculateScaleY(float paramFloat1, float paramFloat2)
    {
      return calculateScaleX(paramFloat1, paramFloat2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigation/NavigationBarItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */