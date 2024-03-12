package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.dimen;
import com.google.android.material.R.styleable;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class NavigationBarView
  extends FrameLayout
{
  public static final int LABEL_VISIBILITY_AUTO = -1;
  public static final int LABEL_VISIBILITY_LABELED = 1;
  public static final int LABEL_VISIBILITY_SELECTED = 0;
  public static final int LABEL_VISIBILITY_UNLABELED = 2;
  private static final int MENU_PRESENTER_ID = 1;
  private ColorStateList itemRippleColor;
  private final NavigationBarMenu menu;
  private MenuInflater menuInflater;
  private final NavigationBarMenuView menuView;
  private final NavigationBarPresenter presenter;
  private OnItemReselectedListener reselectedListener;
  private OnItemSelectedListener selectedListener;
  
  public NavigationBarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt1, paramInt2), paramAttributeSet, paramInt1);
    Object localObject = new NavigationBarPresenter();
    this.presenter = ((NavigationBarPresenter)localObject);
    paramContext = getContext();
    TintTypedArray localTintTypedArray = ThemeEnforcement.obtainTintedStyledAttributes(paramContext, paramAttributeSet, R.styleable.NavigationBarView, paramInt1, paramInt2, new int[] { R.styleable.NavigationBarView_itemTextAppearanceInactive, R.styleable.NavigationBarView_itemTextAppearanceActive });
    paramAttributeSet = new NavigationBarMenu(paramContext, getClass(), getMaxItemCount());
    this.menu = paramAttributeSet;
    NavigationBarMenuView localNavigationBarMenuView = createNavigationBarMenuView(paramContext);
    this.menuView = localNavigationBarMenuView;
    ((NavigationBarPresenter)localObject).setMenuView(localNavigationBarMenuView);
    ((NavigationBarPresenter)localObject).setId(1);
    localNavigationBarMenuView.setPresenter((NavigationBarPresenter)localObject);
    paramAttributeSet.addMenuPresenter((MenuPresenter)localObject);
    ((NavigationBarPresenter)localObject).initForMenu(getContext(), paramAttributeSet);
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemIconTint)) {
      localNavigationBarMenuView.setIconTintList(localTintTypedArray.getColorStateList(R.styleable.NavigationBarView_itemIconTint));
    } else {
      localNavigationBarMenuView.setIconTintList(localNavigationBarMenuView.createDefaultColorStateList(16842808));
    }
    setItemIconSize(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationBarView_itemIconSize, getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_bar_item_default_icon_size)));
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemTextAppearanceInactive)) {
      setItemTextAppearanceInactive(localTintTypedArray.getResourceId(R.styleable.NavigationBarView_itemTextAppearanceInactive, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemTextAppearanceActive)) {
      setItemTextAppearanceActive(localTintTypedArray.getResourceId(R.styleable.NavigationBarView_itemTextAppearanceActive, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemTextColor)) {
      setItemTextColor(localTintTypedArray.getColorStateList(R.styleable.NavigationBarView_itemTextColor));
    }
    if ((getBackground() == null) || ((getBackground() instanceof ColorDrawable))) {
      ViewCompat.setBackground(this, createMaterialShapeDrawableBackground(paramContext));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemPaddingTop)) {
      setItemPaddingTop(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationBarView_itemPaddingTop, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_itemPaddingBottom)) {
      setItemPaddingBottom(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationBarView_itemPaddingBottom, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_elevation)) {
      setElevation(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationBarView_elevation, 0));
    }
    localObject = MaterialResources.getColorStateList(paramContext, localTintTypedArray, R.styleable.NavigationBarView_backgroundTint);
    DrawableCompat.setTintList(getBackground().mutate(), (ColorStateList)localObject);
    setLabelVisibilityMode(localTintTypedArray.getInteger(R.styleable.NavigationBarView_labelVisibilityMode, -1));
    paramInt1 = localTintTypedArray.getResourceId(R.styleable.NavigationBarView_itemBackground, 0);
    if (paramInt1 != 0) {
      localNavigationBarMenuView.setItemBackgroundRes(paramInt1);
    } else {
      setItemRippleColor(MaterialResources.getColorStateList(paramContext, localTintTypedArray, R.styleable.NavigationBarView_itemRippleColor));
    }
    paramInt1 = localTintTypedArray.getResourceId(R.styleable.NavigationBarView_itemActiveIndicatorStyle, 0);
    if (paramInt1 != 0)
    {
      setItemActiveIndicatorEnabled(true);
      localObject = paramContext.obtainStyledAttributes(paramInt1, R.styleable.NavigationBarActiveIndicator);
      setItemActiveIndicatorWidth(((TypedArray)localObject).getDimensionPixelSize(R.styleable.NavigationBarActiveIndicator_android_width, 0));
      setItemActiveIndicatorHeight(((TypedArray)localObject).getDimensionPixelSize(R.styleable.NavigationBarActiveIndicator_android_height, 0));
      setItemActiveIndicatorMarginHorizontal(((TypedArray)localObject).getDimensionPixelOffset(R.styleable.NavigationBarActiveIndicator_marginHorizontal, 0));
      setItemActiveIndicatorColor(MaterialResources.getColorStateList(paramContext, (TypedArray)localObject, R.styleable.NavigationBarActiveIndicator_android_color));
      paramInt1 = ((TypedArray)localObject).getResourceId(R.styleable.NavigationBarActiveIndicator_shapeAppearance, 0);
      setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel.builder(paramContext, paramInt1, 0).build());
      ((TypedArray)localObject).recycle();
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationBarView_menu)) {
      inflateMenu(localTintTypedArray.getResourceId(R.styleable.NavigationBarView_menu, 0));
    }
    localTintTypedArray.recycle();
    addView(localNavigationBarMenuView);
    paramAttributeSet.setCallback(new MenuBuilder.Callback()
    {
      public boolean onMenuItemSelected(MenuBuilder paramAnonymousMenuBuilder, MenuItem paramAnonymousMenuItem)
      {
        paramAnonymousMenuBuilder = NavigationBarView.this.reselectedListener;
        boolean bool = true;
        if ((paramAnonymousMenuBuilder != null) && (paramAnonymousMenuItem.getItemId() == NavigationBarView.this.getSelectedItemId()))
        {
          NavigationBarView.this.reselectedListener.onNavigationItemReselected(paramAnonymousMenuItem);
          return true;
        }
        if ((NavigationBarView.this.selectedListener == null) || (NavigationBarView.this.selectedListener.onNavigationItemSelected(paramAnonymousMenuItem))) {
          bool = false;
        }
        return bool;
      }
      
      public void onMenuModeChange(MenuBuilder paramAnonymousMenuBuilder) {}
    });
  }
  
  private MaterialShapeDrawable createMaterialShapeDrawableBackground(Context paramContext)
  {
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
    Drawable localDrawable = getBackground();
    if ((localDrawable instanceof ColorDrawable)) {
      localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable)localDrawable).getColor()));
    }
    localMaterialShapeDrawable.initializeElevationOverlay(paramContext);
    return localMaterialShapeDrawable;
  }
  
  private MenuInflater getMenuInflater()
  {
    if (this.menuInflater == null) {
      this.menuInflater = new SupportMenuInflater(getContext());
    }
    return this.menuInflater;
  }
  
  protected abstract NavigationBarMenuView createNavigationBarMenuView(Context paramContext);
  
  public BadgeDrawable getBadge(int paramInt)
  {
    return this.menuView.getBadge(paramInt);
  }
  
  public ColorStateList getItemActiveIndicatorColor()
  {
    return this.menuView.getItemActiveIndicatorColor();
  }
  
  public int getItemActiveIndicatorHeight()
  {
    return this.menuView.getItemActiveIndicatorHeight();
  }
  
  public int getItemActiveIndicatorMarginHorizontal()
  {
    return this.menuView.getItemActiveIndicatorMarginHorizontal();
  }
  
  public ShapeAppearanceModel getItemActiveIndicatorShapeAppearance()
  {
    return this.menuView.getItemActiveIndicatorShapeAppearance();
  }
  
  public int getItemActiveIndicatorWidth()
  {
    return this.menuView.getItemActiveIndicatorWidth();
  }
  
  public Drawable getItemBackground()
  {
    return this.menuView.getItemBackground();
  }
  
  @Deprecated
  public int getItemBackgroundResource()
  {
    return this.menuView.getItemBackgroundRes();
  }
  
  public int getItemIconSize()
  {
    return this.menuView.getItemIconSize();
  }
  
  public ColorStateList getItemIconTintList()
  {
    return this.menuView.getIconTintList();
  }
  
  public int getItemPaddingBottom()
  {
    return this.menuView.getItemPaddingBottom();
  }
  
  public int getItemPaddingTop()
  {
    return this.menuView.getItemPaddingTop();
  }
  
  public ColorStateList getItemRippleColor()
  {
    return this.itemRippleColor;
  }
  
  public int getItemTextAppearanceActive()
  {
    return this.menuView.getItemTextAppearanceActive();
  }
  
  public int getItemTextAppearanceInactive()
  {
    return this.menuView.getItemTextAppearanceInactive();
  }
  
  public ColorStateList getItemTextColor()
  {
    return this.menuView.getItemTextColor();
  }
  
  public int getLabelVisibilityMode()
  {
    return this.menuView.getLabelVisibilityMode();
  }
  
  public abstract int getMaxItemCount();
  
  public Menu getMenu()
  {
    return this.menu;
  }
  
  public MenuView getMenuView()
  {
    return this.menuView;
  }
  
  public BadgeDrawable getOrCreateBadge(int paramInt)
  {
    return this.menuView.getOrCreateBadge(paramInt);
  }
  
  public NavigationBarPresenter getPresenter()
  {
    return this.presenter;
  }
  
  public int getSelectedItemId()
  {
    return this.menuView.getSelectedItemId();
  }
  
  public void inflateMenu(int paramInt)
  {
    this.presenter.setUpdateSuspended(true);
    getMenuInflater().inflate(paramInt, this.menu);
    this.presenter.setUpdateSuspended(false);
    this.presenter.updateMenuView(true);
  }
  
  public boolean isItemActiveIndicatorEnabled()
  {
    return this.menuView.getItemActiveIndicatorEnabled();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.menu.restorePresenterStates(paramParcelable.menuPresenterState);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.menuPresenterState = new Bundle();
    this.menu.savePresenterStates(localSavedState.menuPresenterState);
    return localSavedState;
  }
  
  public void removeBadge(int paramInt)
  {
    this.menuView.removeBadge(paramInt);
  }
  
  public void setElevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      super.setElevation(paramFloat);
    }
    MaterialShapeUtils.setElevation(this, paramFloat);
  }
  
  public void setItemActiveIndicatorColor(ColorStateList paramColorStateList)
  {
    this.menuView.setItemActiveIndicatorColor(paramColorStateList);
  }
  
  public void setItemActiveIndicatorEnabled(boolean paramBoolean)
  {
    this.menuView.setItemActiveIndicatorEnabled(paramBoolean);
  }
  
  public void setItemActiveIndicatorHeight(int paramInt)
  {
    this.menuView.setItemActiveIndicatorHeight(paramInt);
  }
  
  public void setItemActiveIndicatorMarginHorizontal(int paramInt)
  {
    this.menuView.setItemActiveIndicatorMarginHorizontal(paramInt);
  }
  
  public void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.menuView.setItemActiveIndicatorShapeAppearance(paramShapeAppearanceModel);
  }
  
  public void setItemActiveIndicatorWidth(int paramInt)
  {
    this.menuView.setItemActiveIndicatorWidth(paramInt);
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    this.menuView.setItemBackground(paramDrawable);
    this.itemRippleColor = null;
  }
  
  public void setItemBackgroundResource(int paramInt)
  {
    this.menuView.setItemBackgroundRes(paramInt);
    this.itemRippleColor = null;
  }
  
  public void setItemIconSize(int paramInt)
  {
    this.menuView.setItemIconSize(paramInt);
  }
  
  public void setItemIconSizeRes(int paramInt)
  {
    setItemIconSize(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    this.menuView.setIconTintList(paramColorStateList);
  }
  
  public void setItemOnTouchListener(int paramInt, View.OnTouchListener paramOnTouchListener)
  {
    this.menuView.setItemOnTouchListener(paramInt, paramOnTouchListener);
  }
  
  public void setItemPaddingBottom(int paramInt)
  {
    this.menuView.setItemPaddingBottom(paramInt);
  }
  
  public void setItemPaddingTop(int paramInt)
  {
    this.menuView.setItemPaddingTop(paramInt);
  }
  
  public void setItemRippleColor(ColorStateList paramColorStateList)
  {
    if (this.itemRippleColor == paramColorStateList)
    {
      if ((paramColorStateList == null) && (this.menuView.getItemBackground() != null)) {
        this.menuView.setItemBackground(null);
      }
      return;
    }
    this.itemRippleColor = paramColorStateList;
    if (paramColorStateList == null)
    {
      this.menuView.setItemBackground(null);
    }
    else
    {
      paramColorStateList = RippleUtils.convertToRippleDrawableColor(paramColorStateList);
      if (Build.VERSION.SDK_INT >= 21)
      {
        this.menuView.setItemBackground(new RippleDrawable(paramColorStateList, null, null));
      }
      else
      {
        Object localObject = new GradientDrawable();
        ((GradientDrawable)localObject).setCornerRadius(1.0E-5F);
        localObject = DrawableCompat.wrap((Drawable)localObject);
        DrawableCompat.setTintList((Drawable)localObject, paramColorStateList);
        this.menuView.setItemBackground((Drawable)localObject);
      }
    }
  }
  
  public void setItemTextAppearanceActive(int paramInt)
  {
    this.menuView.setItemTextAppearanceActive(paramInt);
  }
  
  public void setItemTextAppearanceInactive(int paramInt)
  {
    this.menuView.setItemTextAppearanceInactive(paramInt);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    this.menuView.setItemTextColor(paramColorStateList);
  }
  
  public void setLabelVisibilityMode(int paramInt)
  {
    if (this.menuView.getLabelVisibilityMode() != paramInt)
    {
      this.menuView.setLabelVisibilityMode(paramInt);
      this.presenter.updateMenuView(false);
    }
  }
  
  public void setOnItemReselectedListener(OnItemReselectedListener paramOnItemReselectedListener)
  {
    this.reselectedListener = paramOnItemReselectedListener;
  }
  
  public void setOnItemSelectedListener(OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.selectedListener = paramOnItemSelectedListener;
  }
  
  public void setSelectedItemId(int paramInt)
  {
    MenuItem localMenuItem = this.menu.findItem(paramInt);
    if ((localMenuItem != null) && (!this.menu.performItemAction(localMenuItem, this.presenter, 0))) {
      localMenuItem.setChecked(true);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LabelVisibility {}
  
  public static abstract interface OnItemReselectedListener
  {
    public abstract void onNavigationItemReselected(MenuItem paramMenuItem);
  }
  
  public static abstract interface OnItemSelectedListener
  {
    public abstract boolean onNavigationItemSelected(MenuItem paramMenuItem);
  }
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public NavigationBarView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NavigationBarView.SavedState(paramAnonymousParcel, null);
      }
      
      public NavigationBarView.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new NavigationBarView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public NavigationBarView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NavigationBarView.SavedState[paramAnonymousInt];
      }
    };
    Bundle menuPresenterState;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      ClassLoader localClassLoader = paramClassLoader;
      if (paramClassLoader == null) {
        localClassLoader = getClass().getClassLoader();
      }
      readFromParcel(paramParcel, localClassLoader);
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    private void readFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      this.menuPresenterState = paramParcel.readBundle(paramClassLoader);
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeBundle(this.menuPresenterState);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigation/NavigationBarView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */