package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class NavigationView
  extends ScrimInsetsFrameLayout
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int DEF_STYLE_RES = R.style.Widget_Design_NavigationView;
  private static final int[] DISABLED_STATE_SET = { -16842910 };
  private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
  private boolean bottomInsetScrimEnabled;
  private int drawerLayoutCornerSize;
  private int layoutGravity;
  OnNavigationItemSelectedListener listener;
  private final int maxWidth;
  private final NavigationMenu menu;
  private MenuInflater menuInflater;
  private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
  private final NavigationMenuPresenter presenter;
  private final RectF shapeClipBounds;
  private Path shapeClipPath;
  private final int[] tmpLocation;
  private boolean topInsetScrimEnabled;
  
  public NavigationView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, com.google.android.material.R.attr.navigationViewStyle);
  }
  
  public NavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    NavigationMenuPresenter localNavigationMenuPresenter = new NavigationMenuPresenter();
    this.presenter = localNavigationMenuPresenter;
    this.tmpLocation = new int[2];
    this.topInsetScrimEnabled = true;
    this.bottomInsetScrimEnabled = true;
    this.layoutGravity = 0;
    this.drawerLayoutCornerSize = 0;
    this.shapeClipBounds = new RectF();
    Context localContext = getContext();
    NavigationMenu localNavigationMenu = new NavigationMenu(localContext);
    this.menu = localNavigationMenu;
    TintTypedArray localTintTypedArray = ThemeEnforcement.obtainTintedStyledAttributes(localContext, paramAttributeSet, R.styleable.NavigationView, paramInt, i, new int[0]);
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_android_background)) {
      ViewCompat.setBackground(this, localTintTypedArray.getDrawable(R.styleable.NavigationView_android_background));
    }
    this.drawerLayoutCornerSize = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_drawerLayoutCornerSize, 0);
    this.layoutGravity = localTintTypedArray.getInt(R.styleable.NavigationView_android_layout_gravity, 0);
    if ((getBackground() == null) || ((getBackground() instanceof ColorDrawable)))
    {
      paramAttributeSet = ShapeAppearanceModel.builder(localContext, paramAttributeSet, paramInt, i).build();
      paramContext = getBackground();
      paramAttributeSet = new MaterialShapeDrawable(paramAttributeSet);
      if ((paramContext instanceof ColorDrawable)) {
        paramAttributeSet.setFillColor(ColorStateList.valueOf(((ColorDrawable)paramContext).getColor()));
      }
      paramAttributeSet.initializeElevationOverlay(localContext);
      ViewCompat.setBackground(this, paramAttributeSet);
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_elevation)) {
      setElevation(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
    }
    setFitsSystemWindows(localTintTypedArray.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
    this.maxWidth = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
    paramContext = null;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_subheaderColor)) {
      paramContext = localTintTypedArray.getColorStateList(R.styleable.NavigationView_subheaderColor);
    }
    paramInt = 0;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_subheaderTextAppearance)) {
      paramInt = localTintTypedArray.getResourceId(R.styleable.NavigationView_subheaderTextAppearance, 0);
    }
    paramAttributeSet = paramContext;
    if (paramInt == 0)
    {
      paramAttributeSet = paramContext;
      if (paramContext == null) {
        paramAttributeSet = createDefaultColorStateList(16842808);
      }
    }
    ColorStateList localColorStateList1;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemIconTint)) {
      localColorStateList1 = localTintTypedArray.getColorStateList(R.styleable.NavigationView_itemIconTint);
    } else {
      localColorStateList1 = createDefaultColorStateList(16842808);
    }
    i = 0;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
      i = localTintTypedArray.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemIconSize)) {
      setItemIconSize(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemIconSize, 0));
    }
    paramContext = null;
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemTextColor)) {
      paramContext = localTintTypedArray.getColorStateList(R.styleable.NavigationView_itemTextColor);
    }
    Object localObject = paramContext;
    if (i == 0)
    {
      localObject = paramContext;
      if (paramContext == null) {
        localObject = createDefaultColorStateList(16842806);
      }
    }
    Drawable localDrawable = localTintTypedArray.getDrawable(R.styleable.NavigationView_itemBackground);
    paramContext = localDrawable;
    if (localDrawable == null)
    {
      paramContext = localDrawable;
      if (hasShapeAppearance(localTintTypedArray))
      {
        localDrawable = createDefaultItemBackground(localTintTypedArray);
        ColorStateList localColorStateList2 = MaterialResources.getColorStateList(localContext, localTintTypedArray, R.styleable.NavigationView_itemRippleColor);
        paramContext = localDrawable;
        if (Build.VERSION.SDK_INT >= 21)
        {
          paramContext = localDrawable;
          if (localColorStateList2 != null)
          {
            paramContext = createDefaultItemDrawable(localTintTypedArray, null);
            localNavigationMenuPresenter.setItemForeground(new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(localColorStateList2), null, paramContext));
            paramContext = localDrawable;
          }
        }
      }
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemHorizontalPadding)) {
      setItemHorizontalPadding(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemHorizontalPadding, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_itemVerticalPadding)) {
      setItemVerticalPadding(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemVerticalPadding, 0));
    }
    setDividerInsetStart(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_dividerInsetStart, 0));
    setDividerInsetEnd(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_dividerInsetEnd, 0));
    setSubheaderInsetStart(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_subheaderInsetStart, 0));
    setSubheaderInsetEnd(localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_subheaderInsetEnd, 0));
    setTopInsetScrimEnabled(localTintTypedArray.getBoolean(R.styleable.NavigationView_topInsetScrimEnabled, this.topInsetScrimEnabled));
    setBottomInsetScrimEnabled(localTintTypedArray.getBoolean(R.styleable.NavigationView_bottomInsetScrimEnabled, this.bottomInsetScrimEnabled));
    int j = localTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemIconPadding, 0);
    setItemMaxLines(localTintTypedArray.getInt(R.styleable.NavigationView_itemMaxLines, 1));
    localNavigationMenu.setCallback(new MenuBuilder.Callback()
    {
      public boolean onMenuItemSelected(MenuBuilder paramAnonymousMenuBuilder, MenuItem paramAnonymousMenuItem)
      {
        boolean bool;
        if ((NavigationView.this.listener != null) && (NavigationView.this.listener.onNavigationItemSelected(paramAnonymousMenuItem))) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public void onMenuModeChange(MenuBuilder paramAnonymousMenuBuilder) {}
    });
    localNavigationMenuPresenter.setId(1);
    localNavigationMenuPresenter.initForMenu(localContext, localNavigationMenu);
    if (paramInt != 0) {
      localNavigationMenuPresenter.setSubheaderTextAppearance(paramInt);
    }
    localNavigationMenuPresenter.setSubheaderColor(paramAttributeSet);
    localNavigationMenuPresenter.setItemIconTintList(localColorStateList1);
    localNavigationMenuPresenter.setOverScrollMode(getOverScrollMode());
    if (i != 0) {
      localNavigationMenuPresenter.setItemTextAppearance(i);
    }
    localNavigationMenuPresenter.setItemTextColor((ColorStateList)localObject);
    localNavigationMenuPresenter.setItemBackground(paramContext);
    localNavigationMenuPresenter.setItemIconPadding(j);
    localNavigationMenu.addMenuPresenter(localNavigationMenuPresenter);
    addView((View)localNavigationMenuPresenter.getMenuView(this));
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_menu)) {
      inflateMenu(localTintTypedArray.getResourceId(R.styleable.NavigationView_menu, 0));
    }
    if (localTintTypedArray.hasValue(R.styleable.NavigationView_headerLayout)) {
      inflateHeaderView(localTintTypedArray.getResourceId(R.styleable.NavigationView_headerLayout, 0));
    }
    localTintTypedArray.recycle();
    setupInsetScrimsListener();
  }
  
  private ColorStateList createDefaultColorStateList(int paramInt)
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
    int[] arrayOfInt1 = DISABLED_STATE_SET;
    int[] arrayOfInt2 = CHECKED_STATE_SET;
    localObject = EMPTY_STATE_SET;
    int j = localColorStateList.getColorForState(arrayOfInt1, paramInt);
    return new ColorStateList(new int[][] { arrayOfInt1, arrayOfInt2, localObject }, new int[] { j, i, paramInt });
  }
  
  private Drawable createDefaultItemBackground(TintTypedArray paramTintTypedArray)
  {
    return createDefaultItemDrawable(paramTintTypedArray, MaterialResources.getColorStateList(getContext(), paramTintTypedArray, R.styleable.NavigationView_itemShapeFillColor));
  }
  
  private Drawable createDefaultItemDrawable(TintTypedArray paramTintTypedArray, ColorStateList paramColorStateList)
  {
    int j = paramTintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearance, 0);
    int i = paramTintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearanceOverlay, 0);
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(getContext(), j, i).build());
    localMaterialShapeDrawable.setFillColor(paramColorStateList);
    return new InsetDrawable(localMaterialShapeDrawable, paramTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetStart, 0), paramTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetTop, 0), paramTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetEnd, 0), paramTintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetBottom, 0));
  }
  
  private MenuInflater getMenuInflater()
  {
    if (this.menuInflater == null) {
      this.menuInflater = new SupportMenuInflater(getContext());
    }
    return this.menuInflater;
  }
  
  private boolean hasShapeAppearance(TintTypedArray paramTintTypedArray)
  {
    boolean bool;
    if ((!paramTintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearance)) && (!paramTintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearanceOverlay))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void maybeUpdateCornerSizeForDrawerLayout(int paramInt1, int paramInt2)
  {
    if (((getParent() instanceof DrawerLayout)) && (this.drawerLayoutCornerSize > 0) && ((getBackground() instanceof MaterialShapeDrawable)))
    {
      MaterialShapeDrawable localMaterialShapeDrawable = (MaterialShapeDrawable)getBackground();
      ShapeAppearanceModel.Builder localBuilder = localMaterialShapeDrawable.getShapeAppearanceModel().toBuilder();
      if (GravityCompat.getAbsoluteGravity(this.layoutGravity, ViewCompat.getLayoutDirection(this)) == 3)
      {
        localBuilder.setTopRightCornerSize(this.drawerLayoutCornerSize);
        localBuilder.setBottomRightCornerSize(this.drawerLayoutCornerSize);
      }
      else
      {
        localBuilder.setTopLeftCornerSize(this.drawerLayoutCornerSize);
        localBuilder.setBottomLeftCornerSize(this.drawerLayoutCornerSize);
      }
      localMaterialShapeDrawable.setShapeAppearanceModel(localBuilder.build());
      if (this.shapeClipPath == null) {
        this.shapeClipPath = new Path();
      }
      this.shapeClipPath.reset();
      this.shapeClipBounds.set(0.0F, 0.0F, paramInt1, paramInt2);
      ShapeAppearancePathProvider.getInstance().calculatePath(localMaterialShapeDrawable.getShapeAppearanceModel(), localMaterialShapeDrawable.getInterpolation(), this.shapeClipBounds, this.shapeClipPath);
      invalidate();
    }
    else
    {
      this.shapeClipPath = null;
      this.shapeClipBounds.setEmpty();
    }
  }
  
  private void setupInsetScrimsListener()
  {
    this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        Object localObject = NavigationView.this;
        ((NavigationView)localObject).getLocationOnScreen(((NavigationView)localObject).tmpLocation);
        localObject = NavigationView.this.tmpLocation;
        boolean bool2 = true;
        boolean bool1;
        if (localObject[1] == 0) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        NavigationView.this.presenter.setBehindStatusBar(bool1);
        localObject = NavigationView.this;
        if ((bool1) && (((NavigationView)localObject).isTopInsetScrimEnabled())) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        ((NavigationView)localObject).setDrawTopInsetForeground(bool1);
        localObject = ContextUtils.getActivity(NavigationView.this.getContext());
        if ((localObject != null) && (Build.VERSION.SDK_INT >= 21))
        {
          int i;
          if (((Activity)localObject).findViewById(16908290).getHeight() == NavigationView.this.getHeight()) {
            i = 1;
          } else {
            i = 0;
          }
          int j;
          if (Color.alpha(((Activity)localObject).getWindow().getNavigationBarColor()) != 0) {
            j = 1;
          } else {
            j = 0;
          }
          localObject = NavigationView.this;
          if ((i != 0) && (j != 0) && (((NavigationView)localObject).isBottomInsetScrimEnabled())) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
          ((NavigationView)localObject).setDrawBottomInsetForeground(bool1);
        }
      }
    };
    getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
  }
  
  public void addHeaderView(View paramView)
  {
    this.presenter.addHeaderView(paramView);
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    if (this.shapeClipPath == null)
    {
      super.dispatchDraw(paramCanvas);
      return;
    }
    int i = paramCanvas.save();
    paramCanvas.clipPath(this.shapeClipPath);
    super.dispatchDraw(paramCanvas);
    paramCanvas.restoreToCount(i);
  }
  
  public MenuItem getCheckedItem()
  {
    return this.presenter.getCheckedItem();
  }
  
  public int getDividerInsetEnd()
  {
    return this.presenter.getDividerInsetEnd();
  }
  
  public int getDividerInsetStart()
  {
    return this.presenter.getDividerInsetStart();
  }
  
  public int getHeaderCount()
  {
    return this.presenter.getHeaderCount();
  }
  
  public View getHeaderView(int paramInt)
  {
    return this.presenter.getHeaderView(paramInt);
  }
  
  public Drawable getItemBackground()
  {
    return this.presenter.getItemBackground();
  }
  
  public int getItemHorizontalPadding()
  {
    return this.presenter.getItemHorizontalPadding();
  }
  
  public int getItemIconPadding()
  {
    return this.presenter.getItemIconPadding();
  }
  
  public ColorStateList getItemIconTintList()
  {
    return this.presenter.getItemTintList();
  }
  
  public int getItemMaxLines()
  {
    return this.presenter.getItemMaxLines();
  }
  
  public ColorStateList getItemTextColor()
  {
    return this.presenter.getItemTextColor();
  }
  
  public int getItemVerticalPadding()
  {
    return this.presenter.getItemVerticalPadding();
  }
  
  public Menu getMenu()
  {
    return this.menu;
  }
  
  public int getSubheaderInsetEnd()
  {
    return this.presenter.getSubheaderInsetEnd();
  }
  
  public int getSubheaderInsetStart()
  {
    return this.presenter.getSubheaderInsetStart();
  }
  
  public View inflateHeaderView(int paramInt)
  {
    return this.presenter.inflateHeaderView(paramInt);
  }
  
  public void inflateMenu(int paramInt)
  {
    this.presenter.setUpdateSuspended(true);
    getMenuInflater().inflate(paramInt, this.menu);
    this.presenter.setUpdateSuspended(false);
    this.presenter.updateMenuView(false);
  }
  
  public boolean isBottomInsetScrimEnabled()
  {
    return this.bottomInsetScrimEnabled;
  }
  
  public boolean isTopInsetScrimEnabled()
  {
    return this.topInsetScrimEnabled;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (Build.VERSION.SDK_INT < 16) {
      getViewTreeObserver().removeGlobalOnLayoutListener(this.onGlobalLayoutListener);
    } else {
      getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }
  }
  
  protected void onInsetsChanged(WindowInsetsCompat paramWindowInsetsCompat)
  {
    this.presenter.dispatchApplyWindowInsets(paramWindowInsetsCompat);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    switch (View.MeasureSpec.getMode(paramInt1))
    {
    default: 
      break;
    case 1073741824: 
      break;
    case 0: 
      paramInt1 = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
      break;
    case -2147483648: 
      paramInt1 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt1), this.maxWidth), 1073741824);
    }
    super.onMeasure(paramInt1, paramInt2);
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
    this.menu.restorePresenterStates(paramParcelable.menuState);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.menuState = new Bundle();
    this.menu.savePresenterStates(localSavedState.menuState);
    return localSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    maybeUpdateCornerSizeForDrawerLayout(paramInt1, paramInt2);
  }
  
  public void removeHeaderView(View paramView)
  {
    this.presenter.removeHeaderView(paramView);
  }
  
  public void setBottomInsetScrimEnabled(boolean paramBoolean)
  {
    this.bottomInsetScrimEnabled = paramBoolean;
  }
  
  public void setCheckedItem(int paramInt)
  {
    MenuItem localMenuItem = this.menu.findItem(paramInt);
    if (localMenuItem != null) {
      this.presenter.setCheckedItem((MenuItemImpl)localMenuItem);
    }
  }
  
  public void setCheckedItem(MenuItem paramMenuItem)
  {
    paramMenuItem = this.menu.findItem(paramMenuItem.getItemId());
    if (paramMenuItem != null)
    {
      this.presenter.setCheckedItem((MenuItemImpl)paramMenuItem);
      return;
    }
    throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
  }
  
  public void setDividerInsetEnd(int paramInt)
  {
    this.presenter.setDividerInsetEnd(paramInt);
  }
  
  public void setDividerInsetStart(int paramInt)
  {
    this.presenter.setDividerInsetStart(paramInt);
  }
  
  public void setElevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      super.setElevation(paramFloat);
    }
    MaterialShapeUtils.setElevation(this, paramFloat);
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    this.presenter.setItemBackground(paramDrawable);
  }
  
  public void setItemBackgroundResource(int paramInt)
  {
    setItemBackground(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setItemHorizontalPadding(int paramInt)
  {
    this.presenter.setItemHorizontalPadding(paramInt);
  }
  
  public void setItemHorizontalPaddingResource(int paramInt)
  {
    this.presenter.setItemHorizontalPadding(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconPadding(int paramInt)
  {
    this.presenter.setItemIconPadding(paramInt);
  }
  
  public void setItemIconPaddingResource(int paramInt)
  {
    this.presenter.setItemIconPadding(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setItemIconSize(int paramInt)
  {
    this.presenter.setItemIconSize(paramInt);
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    this.presenter.setItemIconTintList(paramColorStateList);
  }
  
  public void setItemMaxLines(int paramInt)
  {
    this.presenter.setItemMaxLines(paramInt);
  }
  
  public void setItemTextAppearance(int paramInt)
  {
    this.presenter.setItemTextAppearance(paramInt);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    this.presenter.setItemTextColor(paramColorStateList);
  }
  
  public void setItemVerticalPadding(int paramInt)
  {
    this.presenter.setItemVerticalPadding(paramInt);
  }
  
  public void setItemVerticalPaddingResource(int paramInt)
  {
    this.presenter.setItemVerticalPadding(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener paramOnNavigationItemSelectedListener)
  {
    this.listener = paramOnNavigationItemSelectedListener;
  }
  
  public void setOverScrollMode(int paramInt)
  {
    super.setOverScrollMode(paramInt);
    NavigationMenuPresenter localNavigationMenuPresenter = this.presenter;
    if (localNavigationMenuPresenter != null) {
      localNavigationMenuPresenter.setOverScrollMode(paramInt);
    }
  }
  
  public void setSubheaderInsetEnd(int paramInt)
  {
    this.presenter.setSubheaderInsetStart(paramInt);
  }
  
  public void setSubheaderInsetStart(int paramInt)
  {
    this.presenter.setSubheaderInsetStart(paramInt);
  }
  
  public void setTopInsetScrimEnabled(boolean paramBoolean)
  {
    this.topInsetScrimEnabled = paramBoolean;
  }
  
  public static abstract interface OnNavigationItemSelectedListener
  {
    public abstract boolean onNavigationItemSelected(MenuItem paramMenuItem);
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public NavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NavigationView.SavedState(paramAnonymousParcel, null);
      }
      
      public NavigationView.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new NavigationView.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public NavigationView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NavigationView.SavedState[paramAnonymousInt];
      }
    };
    public Bundle menuState;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.menuState = paramParcel.readBundle(paramClassLoader);
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeBundle(this.menuState);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigation/NavigationView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */