package com.google.android.material.navigationrail;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener;
import com.google.android.material.internal.ViewUtils.RelativePadding;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationRailView
  extends NavigationBarView
{
  private static final int DEFAULT_HEADER_GRAVITY = 49;
  static final int DEFAULT_MENU_GRAVITY = 49;
  static final int MAX_ITEM_COUNT = 7;
  static final int NO_ITEM_MINIMUM_HEIGHT = -1;
  private View headerView;
  private Boolean paddingBottomSystemWindowInsets = null;
  private Boolean paddingTopSystemWindowInsets = null;
  private final int topMargin = getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_rail_margin);
  
  public NavigationRailView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NavigationRailView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.navigationRailStyle);
  }
  
  public NavigationRailView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, R.style.Widget_MaterialComponents_NavigationRailView);
  }
  
  public NavigationRailView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), paramAttributeSet, R.styleable.NavigationRailView, paramInt1, paramInt2, new int[0]);
    paramInt1 = paramContext.getResourceId(R.styleable.NavigationRailView_headerLayout, 0);
    if (paramInt1 != 0) {
      addHeaderView(paramInt1);
    }
    setMenuGravity(paramContext.getInt(R.styleable.NavigationRailView_menuGravity, 49));
    if (paramContext.hasValue(R.styleable.NavigationRailView_itemMinHeight)) {
      setItemMinimumHeight(paramContext.getDimensionPixelSize(R.styleable.NavigationRailView_itemMinHeight, -1));
    }
    if (paramContext.hasValue(R.styleable.NavigationRailView_paddingTopSystemWindowInsets)) {
      this.paddingTopSystemWindowInsets = Boolean.valueOf(paramContext.getBoolean(R.styleable.NavigationRailView_paddingTopSystemWindowInsets, false));
    }
    if (paramContext.hasValue(R.styleable.NavigationRailView_paddingBottomSystemWindowInsets)) {
      this.paddingBottomSystemWindowInsets = Boolean.valueOf(paramContext.getBoolean(R.styleable.NavigationRailView_paddingBottomSystemWindowInsets, false));
    }
    paramContext.recycle();
    applyWindowInsets();
  }
  
  private void applyWindowInsets()
  {
    ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat, ViewUtils.RelativePadding paramAnonymousRelativePadding)
      {
        NavigationRailView localNavigationRailView = NavigationRailView.this;
        if (localNavigationRailView.shouldApplyWindowInsetPadding(localNavigationRailView.paddingTopSystemWindowInsets)) {
          paramAnonymousRelativePadding.top += paramAnonymousWindowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top;
        }
        localNavigationRailView = NavigationRailView.this;
        if (localNavigationRailView.shouldApplyWindowInsetPadding(localNavigationRailView.paddingBottomSystemWindowInsets)) {
          paramAnonymousRelativePadding.bottom += paramAnonymousWindowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
        }
        int j = ViewCompat.getLayoutDirection(paramAnonymousView);
        int i = 1;
        if (j != 1) {
          i = 0;
        }
        j = paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft();
        int k = paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight();
        int m = paramAnonymousRelativePadding.start;
        if (i != 0) {
          i = k;
        } else {
          i = j;
        }
        paramAnonymousRelativePadding.start = (m + i);
        paramAnonymousRelativePadding.applyToView(paramAnonymousView);
        return paramAnonymousWindowInsetsCompat;
      }
    });
  }
  
  private NavigationRailMenuView getNavigationRailMenuView()
  {
    return (NavigationRailMenuView)getMenuView();
  }
  
  private boolean isHeaderViewVisible()
  {
    View localView = this.headerView;
    boolean bool;
    if ((localView != null) && (localView.getVisibility() != 8)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private int makeMinWidthSpec(int paramInt)
  {
    int j = getSuggestedMinimumWidth();
    if ((View.MeasureSpec.getMode(paramInt) != 1073741824) && (j > 0))
    {
      int i = getPaddingLeft();
      int k = getPaddingRight();
      return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt), j + (i + k)), 1073741824);
    }
    return paramInt;
  }
  
  private boolean shouldApplyWindowInsetPadding(Boolean paramBoolean)
  {
    boolean bool;
    if (paramBoolean != null) {
      bool = paramBoolean.booleanValue();
    } else {
      bool = ViewCompat.getFitsSystemWindows(this);
    }
    return bool;
  }
  
  public void addHeaderView(int paramInt)
  {
    addHeaderView(LayoutInflater.from(getContext()).inflate(paramInt, this, false));
  }
  
  public void addHeaderView(View paramView)
  {
    removeHeaderView();
    this.headerView = paramView;
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
    localLayoutParams.gravity = 49;
    localLayoutParams.topMargin = this.topMargin;
    addView(paramView, 0, localLayoutParams);
  }
  
  protected NavigationRailMenuView createNavigationBarMenuView(Context paramContext)
  {
    return new NavigationRailMenuView(paramContext);
  }
  
  public View getHeaderView()
  {
    return this.headerView;
  }
  
  public int getItemMinimumHeight()
  {
    return ((NavigationRailMenuView)getMenuView()).getItemMinimumHeight();
  }
  
  public int getMaxItemCount()
  {
    return 7;
  }
  
  public int getMenuGravity()
  {
    return getNavigationRailMenuView().getMenuGravity();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    NavigationRailMenuView localNavigationRailMenuView = getNavigationRailMenuView();
    paramInt1 = 0;
    if (isHeaderViewVisible())
    {
      paramInt2 = this.headerView.getBottom() + this.topMargin;
      paramInt3 = localNavigationRailMenuView.getTop();
      if (paramInt3 < paramInt2) {
        paramInt1 = paramInt2 - paramInt3;
      }
    }
    else if (localNavigationRailMenuView.isTopGravity())
    {
      paramInt1 = this.topMargin;
    }
    if (paramInt1 > 0) {
      localNavigationRailMenuView.layout(localNavigationRailMenuView.getLeft(), localNavigationRailMenuView.getTop() + paramInt1, localNavigationRailMenuView.getRight(), localNavigationRailMenuView.getBottom() + paramInt1);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    paramInt1 = makeMinWidthSpec(paramInt1);
    super.onMeasure(paramInt1, paramInt2);
    if (isHeaderViewVisible())
    {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() - this.headerView.getMeasuredHeight() - this.topMargin, Integer.MIN_VALUE);
      measureChild(getNavigationRailMenuView(), paramInt1, paramInt2);
    }
  }
  
  public void removeHeaderView()
  {
    View localView = this.headerView;
    if (localView != null)
    {
      removeView(localView);
      this.headerView = null;
    }
  }
  
  public void setItemMinimumHeight(int paramInt)
  {
    ((NavigationRailMenuView)getMenuView()).setItemMinimumHeight(paramInt);
  }
  
  public void setMenuGravity(int paramInt)
  {
    getNavigationRailMenuView().setMenuGravity(paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigationrail/NavigationRailView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */