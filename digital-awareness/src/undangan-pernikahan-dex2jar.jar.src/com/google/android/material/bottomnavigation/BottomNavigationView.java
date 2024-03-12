package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener;
import com.google.android.material.internal.ViewUtils.RelativePadding;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemReselectedListener;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.shape.MaterialShapeDrawable;

public class BottomNavigationView
  extends NavigationBarView
{
  static final int MAX_ITEM_COUNT = 5;
  
  public BottomNavigationView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.bottomNavigationStyle);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, R.style.Widget_Design_BottomNavigationView);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext = getContext();
    paramAttributeSet = ThemeEnforcement.obtainTintedStyledAttributes(paramContext, paramAttributeSet, R.styleable.BottomNavigationView, paramInt1, paramInt2, new int[0]);
    setItemHorizontalTranslationEnabled(paramAttributeSet.getBoolean(R.styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
    if (paramAttributeSet.hasValue(R.styleable.BottomNavigationView_android_minHeight)) {
      setMinimumHeight(paramAttributeSet.getDimensionPixelSize(R.styleable.BottomNavigationView_android_minHeight, 0));
    }
    paramAttributeSet.recycle();
    if (shouldDrawCompatibilityTopDivider()) {
      addCompatibilityTopDivider(paramContext);
    }
    applyWindowInsets();
  }
  
  private void addCompatibilityTopDivider(Context paramContext)
  {
    View localView = new View(paramContext);
    localView.setBackgroundColor(ContextCompat.getColor(paramContext, R.color.design_bottom_navigation_shadow_color));
    localView.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
    addView(localView);
  }
  
  private void applyWindowInsets()
  {
    ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat, ViewUtils.RelativePadding paramAnonymousRelativePadding)
      {
        paramAnonymousRelativePadding.bottom += paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom();
        int i = ViewCompat.getLayoutDirection(paramAnonymousView);
        int k = 1;
        if (i != 1) {
          k = 0;
        }
        int j = paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft();
        i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight();
        int n = paramAnonymousRelativePadding.start;
        if (k != 0) {
          m = i;
        } else {
          m = j;
        }
        paramAnonymousRelativePadding.start = (n + m);
        int m = paramAnonymousRelativePadding.end;
        if (k != 0) {
          i = j;
        }
        paramAnonymousRelativePadding.end = (m + i);
        paramAnonymousRelativePadding.applyToView(paramAnonymousView);
        return paramAnonymousWindowInsetsCompat;
      }
    });
  }
  
  private int makeMinHeightSpec(int paramInt)
  {
    int k = getSuggestedMinimumHeight();
    if ((View.MeasureSpec.getMode(paramInt) != 1073741824) && (k > 0))
    {
      int j = getPaddingTop();
      int i = getPaddingBottom();
      return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(paramInt), k + (j + i)), 1073741824);
    }
    return paramInt;
  }
  
  private boolean shouldDrawCompatibilityTopDivider()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT < 21) && (!(getBackground() instanceof MaterialShapeDrawable))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected NavigationBarMenuView createNavigationBarMenuView(Context paramContext)
  {
    return new BottomNavigationMenuView(paramContext);
  }
  
  public int getMaxItemCount()
  {
    return 5;
  }
  
  public boolean isItemHorizontalTranslationEnabled()
  {
    return ((BottomNavigationMenuView)getMenuView()).isItemHorizontalTranslationEnabled();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, makeMinHeightSpec(paramInt2));
  }
  
  public void setItemHorizontalTranslationEnabled(boolean paramBoolean)
  {
    BottomNavigationMenuView localBottomNavigationMenuView = (BottomNavigationMenuView)getMenuView();
    if (localBottomNavigationMenuView.isItemHorizontalTranslationEnabled() != paramBoolean)
    {
      localBottomNavigationMenuView.setItemHorizontalTranslationEnabled(paramBoolean);
      getPresenter().updateMenuView(false);
    }
  }
  
  @Deprecated
  public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener paramOnNavigationItemReselectedListener)
  {
    setOnItemReselectedListener(paramOnNavigationItemReselectedListener);
  }
  
  @Deprecated
  public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener paramOnNavigationItemSelectedListener)
  {
    setOnItemSelectedListener(paramOnNavigationItemSelectedListener);
  }
  
  @Deprecated
  public static abstract interface OnNavigationItemReselectedListener
    extends NavigationBarView.OnItemReselectedListener
  {}
  
  @Deprecated
  public static abstract interface OnNavigationItemSelectedListener
    extends NavigationBarView.OnItemSelectedListener
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomnavigation/BottomNavigationView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */