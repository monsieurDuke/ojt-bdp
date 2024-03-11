package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R.integer;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class NavigationBarMenuView
  extends ViewGroup
  implements MenuView
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int[] DISABLED_STATE_SET = { -16842910 };
  private static final int ITEM_POOL_SIZE = 5;
  private static final int NO_PADDING = -1;
  private final SparseArray<BadgeDrawable> badgeDrawables = new SparseArray(5);
  private NavigationBarItemView[] buttons;
  private ColorStateList itemActiveIndicatorColor;
  private boolean itemActiveIndicatorEnabled;
  private int itemActiveIndicatorHeight;
  private int itemActiveIndicatorMarginHorizontal;
  private boolean itemActiveIndicatorResizeable = false;
  private ShapeAppearanceModel itemActiveIndicatorShapeAppearance;
  private int itemActiveIndicatorWidth;
  private Drawable itemBackground;
  private int itemBackgroundRes;
  private int itemIconSize;
  private ColorStateList itemIconTint;
  private int itemPaddingBottom = -1;
  private int itemPaddingTop = -1;
  private final Pools.Pool<NavigationBarItemView> itemPool = new Pools.SynchronizedPool(5);
  private int itemTextAppearanceActive;
  private int itemTextAppearanceInactive;
  private final ColorStateList itemTextColorDefault = createDefaultColorStateList(16842808);
  private ColorStateList itemTextColorFromUser;
  private int labelVisibilityMode;
  private MenuBuilder menu;
  private final View.OnClickListener onClickListener;
  private final SparseArray<View.OnTouchListener> onTouchListeners = new SparseArray(5);
  private NavigationBarPresenter presenter;
  private int selectedItemId = 0;
  private int selectedItemPosition = 0;
  private final TransitionSet set;
  
  public NavigationBarMenuView(Context paramContext)
  {
    super(paramContext);
    if (isInEditMode())
    {
      this.set = null;
    }
    else
    {
      paramContext = new AutoTransition();
      this.set = paramContext;
      paramContext.setOrdering(0);
      paramContext.setDuration(MotionUtils.resolveThemeDuration(getContext(), com.google.android.material.R.attr.motionDurationLong1, getResources().getInteger(R.integer.material_motion_duration_long_1)));
      paramContext.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.google.android.material.R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
      paramContext.addTransition(new TextScale());
    }
    this.onClickListener = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = ((NavigationBarItemView)paramAnonymousView).getItemData();
        if (!NavigationBarMenuView.this.menu.performItemAction(paramAnonymousView, NavigationBarMenuView.this.presenter, 0)) {
          paramAnonymousView.setChecked(true);
        }
      }
    };
    ViewCompat.setImportantForAccessibility(this, 1);
  }
  
  private Drawable createItemActiveIndicatorDrawable()
  {
    if ((this.itemActiveIndicatorShapeAppearance != null) && (this.itemActiveIndicatorColor != null))
    {
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable(this.itemActiveIndicatorShapeAppearance);
      localMaterialShapeDrawable.setFillColor(this.itemActiveIndicatorColor);
      return localMaterialShapeDrawable;
    }
    return null;
  }
  
  private NavigationBarItemView getNewItem()
  {
    NavigationBarItemView localNavigationBarItemView2 = (NavigationBarItemView)this.itemPool.acquire();
    NavigationBarItemView localNavigationBarItemView1 = localNavigationBarItemView2;
    if (localNavigationBarItemView2 == null) {
      localNavigationBarItemView1 = createNavigationBarItemView(getContext());
    }
    return localNavigationBarItemView1;
  }
  
  private boolean isValidId(int paramInt)
  {
    boolean bool;
    if (paramInt != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void removeUnusedBadges()
  {
    HashSet localHashSet = new HashSet();
    for (int i = 0; i < this.menu.size(); i++) {
      localHashSet.add(Integer.valueOf(this.menu.getItem(i).getItemId()));
    }
    for (i = 0; i < this.badgeDrawables.size(); i++)
    {
      int j = this.badgeDrawables.keyAt(i);
      if (!localHashSet.contains(Integer.valueOf(j))) {
        this.badgeDrawables.delete(j);
      }
    }
  }
  
  private void setBadgeIfNeeded(NavigationBarItemView paramNavigationBarItemView)
  {
    int i = paramNavigationBarItemView.getId();
    if (!isValidId(i)) {
      return;
    }
    BadgeDrawable localBadgeDrawable = (BadgeDrawable)this.badgeDrawables.get(i);
    if (localBadgeDrawable != null) {
      paramNavigationBarItemView.setBadge(localBadgeDrawable);
    }
  }
  
  private void validateMenuItemId(int paramInt)
  {
    if (isValidId(paramInt)) {
      return;
    }
    throw new IllegalArgumentException(paramInt + " is not a valid view id");
  }
  
  public void buildMenuView()
  {
    removeAllViews();
    Object localObject2 = this.buttons;
    int j;
    Object localObject1;
    if (localObject2 != null)
    {
      j = localObject2.length;
      for (i = 0; i < j; i++)
      {
        localObject1 = localObject2[i];
        if (localObject1 != null)
        {
          this.itemPool.release(localObject1);
          ((NavigationBarItemView)localObject1).clear();
        }
      }
    }
    if (this.menu.size() == 0)
    {
      this.selectedItemId = 0;
      this.selectedItemPosition = 0;
      this.buttons = null;
      return;
    }
    removeUnusedBadges();
    this.buttons = new NavigationBarItemView[this.menu.size()];
    boolean bool = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
    for (int i = 0; i < this.menu.size(); i++)
    {
      this.presenter.setUpdateSuspended(true);
      this.menu.getItem(i).setCheckable(true);
      this.presenter.setUpdateSuspended(false);
      localObject1 = getNewItem();
      this.buttons[i] = localObject1;
      ((NavigationBarItemView)localObject1).setIconTintList(this.itemIconTint);
      ((NavigationBarItemView)localObject1).setIconSize(this.itemIconSize);
      ((NavigationBarItemView)localObject1).setTextColor(this.itemTextColorDefault);
      ((NavigationBarItemView)localObject1).setTextAppearanceInactive(this.itemTextAppearanceInactive);
      ((NavigationBarItemView)localObject1).setTextAppearanceActive(this.itemTextAppearanceActive);
      ((NavigationBarItemView)localObject1).setTextColor(this.itemTextColorFromUser);
      j = this.itemPaddingTop;
      if (j != -1) {
        ((NavigationBarItemView)localObject1).setItemPaddingTop(j);
      }
      j = this.itemPaddingBottom;
      if (j != -1) {
        ((NavigationBarItemView)localObject1).setItemPaddingBottom(j);
      }
      ((NavigationBarItemView)localObject1).setActiveIndicatorWidth(this.itemActiveIndicatorWidth);
      ((NavigationBarItemView)localObject1).setActiveIndicatorHeight(this.itemActiveIndicatorHeight);
      ((NavigationBarItemView)localObject1).setActiveIndicatorMarginHorizontal(this.itemActiveIndicatorMarginHorizontal);
      ((NavigationBarItemView)localObject1).setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
      ((NavigationBarItemView)localObject1).setActiveIndicatorResizeable(this.itemActiveIndicatorResizeable);
      ((NavigationBarItemView)localObject1).setActiveIndicatorEnabled(this.itemActiveIndicatorEnabled);
      localObject2 = this.itemBackground;
      if (localObject2 != null) {
        ((NavigationBarItemView)localObject1).setItemBackground((Drawable)localObject2);
      } else {
        ((NavigationBarItemView)localObject1).setItemBackground(this.itemBackgroundRes);
      }
      ((NavigationBarItemView)localObject1).setShifting(bool);
      ((NavigationBarItemView)localObject1).setLabelVisibilityMode(this.labelVisibilityMode);
      localObject2 = (MenuItemImpl)this.menu.getItem(i);
      ((NavigationBarItemView)localObject1).initialize((MenuItemImpl)localObject2, 0);
      ((NavigationBarItemView)localObject1).setItemPosition(i);
      j = ((MenuItemImpl)localObject2).getItemId();
      ((NavigationBarItemView)localObject1).setOnTouchListener((View.OnTouchListener)this.onTouchListeners.get(j));
      ((NavigationBarItemView)localObject1).setOnClickListener(this.onClickListener);
      int k = this.selectedItemId;
      if ((k != 0) && (j == k)) {
        this.selectedItemPosition = i;
      }
      setBadgeIfNeeded((NavigationBarItemView)localObject1);
      addView((View)localObject1);
    }
    i = Math.min(this.menu.size() - 1, this.selectedItemPosition);
    this.selectedItemPosition = i;
    this.menu.getItem(i).setChecked(true);
  }
  
  public ColorStateList createDefaultColorStateList(int paramInt)
  {
    Object localObject = new TypedValue();
    if (!getContext().getTheme().resolveAttribute(paramInt, (TypedValue)localObject, true)) {
      return null;
    }
    ColorStateList localColorStateList = AppCompatResources.getColorStateList(getContext(), ((TypedValue)localObject).resourceId);
    if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, (TypedValue)localObject, true)) {
      return null;
    }
    int i = ((TypedValue)localObject).data;
    paramInt = localColorStateList.getDefaultColor();
    int[] arrayOfInt2 = DISABLED_STATE_SET;
    localObject = CHECKED_STATE_SET;
    int[] arrayOfInt1 = EMPTY_STATE_SET;
    int j = localColorStateList.getColorForState(arrayOfInt2, paramInt);
    return new ColorStateList(new int[][] { arrayOfInt2, localObject, arrayOfInt1 }, new int[] { j, i, paramInt });
  }
  
  protected abstract NavigationBarItemView createNavigationBarItemView(Context paramContext);
  
  public NavigationBarItemView findItemView(int paramInt)
  {
    validateMenuItemId(paramInt);
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++)
      {
        NavigationBarItemView localNavigationBarItemView = arrayOfNavigationBarItemView[i];
        if (localNavigationBarItemView.getId() == paramInt) {
          return localNavigationBarItemView;
        }
      }
    }
    return null;
  }
  
  public BadgeDrawable getBadge(int paramInt)
  {
    return (BadgeDrawable)this.badgeDrawables.get(paramInt);
  }
  
  SparseArray<BadgeDrawable> getBadgeDrawables()
  {
    return this.badgeDrawables;
  }
  
  public ColorStateList getIconTintList()
  {
    return this.itemIconTint;
  }
  
  public ColorStateList getItemActiveIndicatorColor()
  {
    return this.itemActiveIndicatorColor;
  }
  
  public boolean getItemActiveIndicatorEnabled()
  {
    return this.itemActiveIndicatorEnabled;
  }
  
  public int getItemActiveIndicatorHeight()
  {
    return this.itemActiveIndicatorHeight;
  }
  
  public int getItemActiveIndicatorMarginHorizontal()
  {
    return this.itemActiveIndicatorMarginHorizontal;
  }
  
  public ShapeAppearanceModel getItemActiveIndicatorShapeAppearance()
  {
    return this.itemActiveIndicatorShapeAppearance;
  }
  
  public int getItemActiveIndicatorWidth()
  {
    return this.itemActiveIndicatorWidth;
  }
  
  public Drawable getItemBackground()
  {
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if ((arrayOfNavigationBarItemView != null) && (arrayOfNavigationBarItemView.length > 0)) {
      return arrayOfNavigationBarItemView[0].getBackground();
    }
    return this.itemBackground;
  }
  
  @Deprecated
  public int getItemBackgroundRes()
  {
    return this.itemBackgroundRes;
  }
  
  public int getItemIconSize()
  {
    return this.itemIconSize;
  }
  
  public int getItemPaddingBottom()
  {
    return this.itemPaddingBottom;
  }
  
  public int getItemPaddingTop()
  {
    return this.itemPaddingTop;
  }
  
  public int getItemTextAppearanceActive()
  {
    return this.itemTextAppearanceActive;
  }
  
  public int getItemTextAppearanceInactive()
  {
    return this.itemTextAppearanceInactive;
  }
  
  public ColorStateList getItemTextColor()
  {
    return this.itemTextColorFromUser;
  }
  
  public int getLabelVisibilityMode()
  {
    return this.labelVisibilityMode;
  }
  
  protected MenuBuilder getMenu()
  {
    return this.menu;
  }
  
  BadgeDrawable getOrCreateBadge(int paramInt)
  {
    validateMenuItemId(paramInt);
    Object localObject2 = (BadgeDrawable)this.badgeDrawables.get(paramInt);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = BadgeDrawable.create(getContext());
      this.badgeDrawables.put(paramInt, localObject1);
    }
    localObject2 = findItemView(paramInt);
    if (localObject2 != null) {
      ((NavigationBarItemView)localObject2).setBadge((BadgeDrawable)localObject1);
    }
    return (BadgeDrawable)localObject1;
  }
  
  public int getSelectedItemId()
  {
    return this.selectedItemId;
  }
  
  protected int getSelectedItemPosition()
  {
    return this.selectedItemPosition;
  }
  
  public int getWindowAnimations()
  {
    return 0;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder)
  {
    this.menu = paramMenuBuilder;
  }
  
  protected boolean isItemActiveIndicatorResizeable()
  {
    return this.itemActiveIndicatorResizeable;
  }
  
  protected boolean isShifting(int paramInt1, int paramInt2)
  {
    boolean bool = true;
    if (paramInt1 == -1)
    {
      if (paramInt2 <= 3) {
        bool = false;
      }
    }
    else if (paramInt1 != 0) {
      bool = false;
    }
    return bool;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.menu.getVisibleItems().size(), false, 1));
  }
  
  void removeBadge(int paramInt)
  {
    validateMenuItemId(paramInt);
    BadgeDrawable localBadgeDrawable = (BadgeDrawable)this.badgeDrawables.get(paramInt);
    NavigationBarItemView localNavigationBarItemView = findItemView(paramInt);
    if (localNavigationBarItemView != null) {
      localNavigationBarItemView.removeBadge();
    }
    if (localBadgeDrawable != null) {
      this.badgeDrawables.remove(paramInt);
    }
  }
  
  void restoreBadgeDrawables(SparseArray<BadgeDrawable> paramSparseArray)
  {
    int j;
    for (int i = 0; i < paramSparseArray.size(); i++)
    {
      j = paramSparseArray.keyAt(i);
      if (this.badgeDrawables.indexOfKey(j) < 0) {
        this.badgeDrawables.append(j, paramSparseArray.get(j));
      }
    }
    paramSparseArray = this.buttons;
    if (paramSparseArray != null)
    {
      j = paramSparseArray.length;
      for (i = 0; i < j; i++)
      {
        Object localObject = paramSparseArray[i];
        ((NavigationBarItemView)localObject).setBadge((BadgeDrawable)this.badgeDrawables.get(((NavigationBarItemView)localObject).getId()));
      }
    }
  }
  
  public void setIconTintList(ColorStateList paramColorStateList)
  {
    this.itemIconTint = paramColorStateList;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setIconTintList(paramColorStateList);
      }
    }
  }
  
  public void setItemActiveIndicatorColor(ColorStateList paramColorStateList)
  {
    this.itemActiveIndicatorColor = paramColorStateList;
    paramColorStateList = this.buttons;
    if (paramColorStateList != null)
    {
      int j = paramColorStateList.length;
      for (int i = 0; i < j; i++) {
        paramColorStateList[i].setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
      }
    }
  }
  
  public void setItemActiveIndicatorEnabled(boolean paramBoolean)
  {
    this.itemActiveIndicatorEnabled = paramBoolean;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setActiveIndicatorEnabled(paramBoolean);
      }
    }
  }
  
  public void setItemActiveIndicatorHeight(int paramInt)
  {
    this.itemActiveIndicatorHeight = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setActiveIndicatorHeight(paramInt);
      }
    }
  }
  
  public void setItemActiveIndicatorMarginHorizontal(int paramInt)
  {
    this.itemActiveIndicatorMarginHorizontal = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setActiveIndicatorMarginHorizontal(paramInt);
      }
    }
  }
  
  protected void setItemActiveIndicatorResizeable(boolean paramBoolean)
  {
    this.itemActiveIndicatorResizeable = paramBoolean;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setActiveIndicatorResizeable(paramBoolean);
      }
    }
  }
  
  public void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.itemActiveIndicatorShapeAppearance = paramShapeAppearanceModel;
    paramShapeAppearanceModel = this.buttons;
    if (paramShapeAppearanceModel != null)
    {
      int j = paramShapeAppearanceModel.length;
      for (int i = 0; i < j; i++) {
        paramShapeAppearanceModel[i].setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
      }
    }
  }
  
  public void setItemActiveIndicatorWidth(int paramInt)
  {
    this.itemActiveIndicatorWidth = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setActiveIndicatorWidth(paramInt);
      }
    }
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    this.itemBackground = paramDrawable;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setItemBackground(paramDrawable);
      }
    }
  }
  
  public void setItemBackgroundRes(int paramInt)
  {
    this.itemBackgroundRes = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setItemBackground(paramInt);
      }
    }
  }
  
  public void setItemIconSize(int paramInt)
  {
    this.itemIconSize = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setIconSize(paramInt);
      }
    }
  }
  
  public void setItemOnTouchListener(int paramInt, View.OnTouchListener paramOnTouchListener)
  {
    if (paramOnTouchListener == null) {
      this.onTouchListeners.remove(paramInt);
    } else {
      this.onTouchListeners.put(paramInt, paramOnTouchListener);
    }
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++)
      {
        NavigationBarItemView localNavigationBarItemView = arrayOfNavigationBarItemView[i];
        if (localNavigationBarItemView.getItemData().getItemId() == paramInt) {
          localNavigationBarItemView.setOnTouchListener(paramOnTouchListener);
        }
      }
    }
  }
  
  public void setItemPaddingBottom(int paramInt)
  {
    this.itemPaddingBottom = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setItemPaddingBottom(paramInt);
      }
    }
  }
  
  public void setItemPaddingTop(int paramInt)
  {
    this.itemPaddingTop = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setItemPaddingTop(paramInt);
      }
    }
  }
  
  public void setItemTextAppearanceActive(int paramInt)
  {
    this.itemTextAppearanceActive = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++)
      {
        NavigationBarItemView localNavigationBarItemView = arrayOfNavigationBarItemView[i];
        localNavigationBarItemView.setTextAppearanceActive(paramInt);
        ColorStateList localColorStateList = this.itemTextColorFromUser;
        if (localColorStateList != null) {
          localNavigationBarItemView.setTextColor(localColorStateList);
        }
      }
    }
  }
  
  public void setItemTextAppearanceInactive(int paramInt)
  {
    this.itemTextAppearanceInactive = paramInt;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++)
      {
        NavigationBarItemView localNavigationBarItemView = arrayOfNavigationBarItemView[i];
        localNavigationBarItemView.setTextAppearanceInactive(paramInt);
        ColorStateList localColorStateList = this.itemTextColorFromUser;
        if (localColorStateList != null) {
          localNavigationBarItemView.setTextColor(localColorStateList);
        }
      }
    }
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    this.itemTextColorFromUser = paramColorStateList;
    NavigationBarItemView[] arrayOfNavigationBarItemView = this.buttons;
    if (arrayOfNavigationBarItemView != null)
    {
      int j = arrayOfNavigationBarItemView.length;
      for (int i = 0; i < j; i++) {
        arrayOfNavigationBarItemView[i].setTextColor(paramColorStateList);
      }
    }
  }
  
  public void setLabelVisibilityMode(int paramInt)
  {
    this.labelVisibilityMode = paramInt;
  }
  
  public void setPresenter(NavigationBarPresenter paramNavigationBarPresenter)
  {
    this.presenter = paramNavigationBarPresenter;
  }
  
  void tryRestoreSelectedItemId(int paramInt)
  {
    int j = this.menu.size();
    for (int i = 0; i < j; i++)
    {
      MenuItem localMenuItem = this.menu.getItem(i);
      if (paramInt == localMenuItem.getItemId())
      {
        this.selectedItemId = paramInt;
        this.selectedItemPosition = i;
        localMenuItem.setChecked(true);
        break;
      }
    }
  }
  
  public void updateMenuView()
  {
    Object localObject = this.menu;
    if ((localObject != null) && (this.buttons != null))
    {
      int j = ((MenuBuilder)localObject).size();
      if (j != this.buttons.length)
      {
        buildMenuView();
        return;
      }
      int k = this.selectedItemId;
      for (int i = 0; i < j; i++)
      {
        localObject = this.menu.getItem(i);
        if (((MenuItem)localObject).isChecked())
        {
          this.selectedItemId = ((MenuItem)localObject).getItemId();
          this.selectedItemPosition = i;
        }
      }
      if (k != this.selectedItemId)
      {
        localObject = this.set;
        if (localObject != null) {
          TransitionManager.beginDelayedTransition(this, (Transition)localObject);
        }
      }
      boolean bool = isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
      for (i = 0; i < j; i++)
      {
        this.presenter.setUpdateSuspended(true);
        this.buttons[i].setLabelVisibilityMode(this.labelVisibilityMode);
        this.buttons[i].setShifting(bool);
        this.buttons[i].initialize((MenuItemImpl)this.menu.getItem(i), 0);
        this.presenter.setUpdateSuspended(false);
      }
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigation/NavigationBarMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */