package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuBuilder.ItemInvoker;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;

public class ActionMenuView
  extends LinearLayoutCompat
  implements MenuBuilder.ItemInvoker, MenuView
{
  static final int GENERATED_ITEM_PADDING = 4;
  static final int MIN_CELL_SIZE = 56;
  private static final String TAG = "ActionMenuView";
  private MenuPresenter.Callback mActionMenuPresenterCallback;
  private boolean mFormatItems;
  private int mFormatItemsWidth;
  private int mGeneratedItemPadding;
  private MenuBuilder mMenu;
  MenuBuilder.Callback mMenuBuilderCallback;
  private int mMinCellSize;
  OnMenuItemClickListener mOnMenuItemClickListener;
  private Context mPopupContext;
  private int mPopupTheme;
  private ActionMenuPresenter mPresenter;
  private boolean mReserveOverflow;
  
  public ActionMenuView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionMenuView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBaselineAligned(false);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mMinCellSize = ((int)(56.0F * f));
    this.mGeneratedItemPadding = ((int)(4.0F * f));
    this.mPopupContext = paramContext;
    this.mPopupTheme = 0;
  }
  
  static int measureChildForCells(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int j = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt3) - paramInt4, View.MeasureSpec.getMode(paramInt3));
    ActionMenuItemView localActionMenuItemView;
    if ((paramView instanceof ActionMenuItemView)) {
      localActionMenuItemView = (ActionMenuItemView)paramView;
    } else {
      localActionMenuItemView = null;
    }
    boolean bool2 = false;
    if ((localActionMenuItemView != null) && (localActionMenuItemView.hasText())) {
      paramInt4 = 1;
    } else {
      paramInt4 = 0;
    }
    int i = 0;
    paramInt3 = i;
    if (paramInt2 > 0) {
      if (paramInt4 != 0)
      {
        paramInt3 = i;
        if (paramInt2 < 2) {}
      }
      else
      {
        paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1 * paramInt2, Integer.MIN_VALUE), j);
        i = paramView.getMeasuredWidth();
        paramInt3 = i / paramInt1;
        paramInt2 = paramInt3;
        if (i % paramInt1 != 0) {
          paramInt2 = paramInt3 + 1;
        }
        paramInt3 = paramInt2;
        if (paramInt4 != 0)
        {
          paramInt3 = paramInt2;
          if (paramInt2 < 2) {
            paramInt3 = 2;
          }
        }
      }
    }
    boolean bool1 = bool2;
    if (!localLayoutParams.isOverflowButton)
    {
      bool1 = bool2;
      if (paramInt4 != 0) {
        bool1 = true;
      }
    }
    localLayoutParams.expandable = bool1;
    localLayoutParams.cellsUsed = paramInt3;
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt3 * paramInt1, 1073741824), j);
    return paramInt3;
  }
  
  private void onMeasureExactFormat(int paramInt1, int paramInt2)
  {
    int i7 = View.MeasureSpec.getMode(paramInt2);
    int j = View.MeasureSpec.getSize(paramInt1);
    int i10 = View.MeasureSpec.getSize(paramInt2);
    paramInt1 = getPaddingLeft();
    int i = getPaddingRight();
    int i5 = getPaddingTop() + getPaddingBottom();
    int i11 = getChildMeasureSpec(paramInt2, i5, -2);
    int i8 = j - (paramInt1 + i);
    paramInt1 = this.mMinCellSize;
    int i2 = i8 / paramInt1;
    int n = i8 % paramInt1;
    if (i2 == 0)
    {
      setMeasuredDimension(i8, 0);
      return;
    }
    int i13 = paramInt1 + n / i2;
    paramInt1 = i2;
    i = 0;
    int i1 = 0;
    paramInt2 = 0;
    int k = 0;
    long l1 = 0L;
    int i12 = getChildCount();
    j = 0;
    int i3 = 0;
    Object localObject2;
    Object localObject1;
    int i4;
    long l2;
    while (i3 < i12)
    {
      localObject2 = getChildAt(i3);
      if (((View)localObject2).getVisibility() == 8)
      {
        m = j;
      }
      else
      {
        boolean bool = localObject2 instanceof ActionMenuItemView;
        m = j + 1;
        if (bool)
        {
          j = this.mGeneratedItemPadding;
          ((View)localObject2).setPadding(j, 0, j, 0);
        }
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        ((LayoutParams)localObject1).expanded = false;
        ((LayoutParams)localObject1).extraPixels = 0;
        ((LayoutParams)localObject1).cellsUsed = 0;
        ((LayoutParams)localObject1).expandable = false;
        ((LayoutParams)localObject1).leftMargin = 0;
        ((LayoutParams)localObject1).rightMargin = 0;
        if ((bool) && (((ActionMenuItemView)localObject2).hasText())) {
          bool = true;
        } else {
          bool = false;
        }
        ((LayoutParams)localObject1).preventEdgeOffset = bool;
        if (((LayoutParams)localObject1).isOverflowButton) {
          j = 1;
        } else {
          j = paramInt1;
        }
        i4 = measureChildForCells((View)localObject2, i13, j, i11, i5);
        i1 = Math.max(i1, i4);
        j = paramInt2;
        if (((LayoutParams)localObject1).expandable) {
          j = paramInt2 + 1;
        }
        if (((LayoutParams)localObject1).isOverflowButton) {
          k = 1;
        }
        paramInt1 -= i4;
        i = Math.max(i, ((View)localObject2).getMeasuredHeight());
        if (i4 == 1)
        {
          l2 = 1 << i3;
          l1 |= l2;
          paramInt2 = j;
        }
        else
        {
          paramInt2 = j;
        }
      }
      i3++;
      j = m;
    }
    if ((k != 0) && (j == 2)) {
      i4 = 1;
    } else {
      i4 = 0;
    }
    int m = 0;
    int i6 = paramInt1;
    paramInt1 = m;
    i2 = i8;
    i3 = i7;
    while ((paramInt2 > 0) && (i6 > 0))
    {
      i7 = Integer.MAX_VALUE;
      long l3 = 0L;
      m = 0;
      i8 = 0;
      while (i8 < i12)
      {
        localObject1 = (LayoutParams)getChildAt(i8).getLayoutParams();
        int i9;
        if (!((LayoutParams)localObject1).expandable)
        {
          n = m;
          i9 = i7;
          l2 = l3;
        }
        else if (((LayoutParams)localObject1).cellsUsed < i7)
        {
          i9 = ((LayoutParams)localObject1).cellsUsed;
          l2 = 1L << i8;
          n = 1;
        }
        else
        {
          n = m;
          i9 = i7;
          l2 = l3;
          if (((LayoutParams)localObject1).cellsUsed == i7)
          {
            l2 = l3 | 1L << i8;
            n = m + 1;
            i9 = i7;
          }
        }
        i8++;
        m = n;
        i7 = i9;
        l3 = l2;
      }
      l1 |= l3;
      if (m > i6) {
        break;
      }
      paramInt1 = 0;
      while (paramInt1 < i12)
      {
        localObject2 = getChildAt(paramInt1);
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        if ((l3 & 1 << paramInt1) == 0L)
        {
          n = i6;
          l2 = l1;
          if (((LayoutParams)localObject1).cellsUsed == i7 + 1)
          {
            l2 = l1 | 1 << paramInt1;
            n = i6;
          }
        }
        else
        {
          if ((i4 != 0) && (((LayoutParams)localObject1).preventEdgeOffset) && (i6 == 1))
          {
            n = this.mGeneratedItemPadding;
            ((View)localObject2).setPadding(n + i13, 0, n, 0);
          }
          ((LayoutParams)localObject1).cellsUsed += 1;
          ((LayoutParams)localObject1).expanded = true;
          n = i6 - 1;
          l2 = l1;
        }
        paramInt1++;
        i6 = n;
        l1 = l2;
      }
      paramInt1 = 1;
    }
    if ((k == 0) && (j == 1)) {
      paramInt2 = 1;
    } else {
      paramInt2 = 0;
    }
    if ((i6 > 0) && (l1 != 0L))
    {
      if ((i6 >= j - 1) && (paramInt2 == 0) && (i1 <= 1)) {
        break label1131;
      }
      float f1 = Long.bitCount(l1);
      if (paramInt2 == 0)
      {
        float f2;
        if ((l1 & 1L) != 0L)
        {
          f2 = f1;
          if (!((LayoutParams)getChildAt(0).getLayoutParams()).preventEdgeOffset) {
            f2 = f1 - 0.5F;
          }
        }
        else
        {
          f2 = f1;
        }
        f1 = f2;
        if ((l1 & 1 << i12 - 1) != 0L)
        {
          f1 = f2;
          if (!((LayoutParams)getChildAt(i12 - 1).getLayoutParams()).preventEdgeOffset) {
            f1 = f2 - 0.5F;
          }
        }
      }
      m = 0;
      if (f1 > 0.0F) {
        m = (int)(i6 * i13 / f1);
      }
      n = 0;
      for (k = paramInt1; n < i12; k = paramInt1)
      {
        if ((l1 & 1 << n) == 0L)
        {
          paramInt1 = k;
        }
        else
        {
          localObject2 = getChildAt(n);
          localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
          if ((localObject2 instanceof ActionMenuItemView))
          {
            ((LayoutParams)localObject1).extraPixels = m;
            ((LayoutParams)localObject1).expanded = true;
            if ((n == 0) && (!((LayoutParams)localObject1).preventEdgeOffset)) {
              ((LayoutParams)localObject1).leftMargin = (-m / 2);
            }
            paramInt1 = 1;
          }
          else if (((LayoutParams)localObject1).isOverflowButton)
          {
            ((LayoutParams)localObject1).extraPixels = m;
            ((LayoutParams)localObject1).expanded = true;
            ((LayoutParams)localObject1).rightMargin = (-m / 2);
            paramInt1 = 1;
          }
          else
          {
            if (n != 0) {
              ((LayoutParams)localObject1).leftMargin = (m / 2);
            }
            paramInt1 = k;
            if (n != i12 - 1)
            {
              ((LayoutParams)localObject1).rightMargin = (m / 2);
              paramInt1 = k;
            }
          }
        }
        n++;
      }
      break label1134;
    }
    label1131:
    k = paramInt1;
    label1134:
    if (k != 0) {
      for (paramInt1 = 0; paramInt1 < i12; paramInt1++)
      {
        localObject1 = getChildAt(paramInt1);
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (((LayoutParams)localObject2).expanded) {
          ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec(((LayoutParams)localObject2).cellsUsed * i13 + ((LayoutParams)localObject2).extraPixels, 1073741824), i11);
        }
      }
    }
    if (i3 != 1073741824) {
      paramInt1 = i;
    } else {
      paramInt1 = i10;
    }
    setMeasuredDimension(i2, paramInt1);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void dismissPopupMenus()
  {
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    if (localActionMenuPresenter != null) {
      localActionMenuPresenter.dismissPopupMenus();
    }
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    return false;
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    LayoutParams localLayoutParams = new LayoutParams(-2, -2);
    localLayoutParams.gravity = 16;
    return localLayoutParams;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (paramLayoutParams != null)
    {
      if ((paramLayoutParams instanceof LayoutParams)) {
        paramLayoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
      } else {
        paramLayoutParams = new LayoutParams(paramLayoutParams);
      }
      if (paramLayoutParams.gravity <= 0) {
        paramLayoutParams.gravity = 16;
      }
      return paramLayoutParams;
    }
    return generateDefaultLayoutParams();
  }
  
  public LayoutParams generateOverflowButtonLayoutParams()
  {
    LayoutParams localLayoutParams = generateDefaultLayoutParams();
    localLayoutParams.isOverflowButton = true;
    return localLayoutParams;
  }
  
  public Menu getMenu()
  {
    if (this.mMenu == null)
    {
      Object localObject1 = getContext();
      Object localObject2 = new MenuBuilder((Context)localObject1);
      this.mMenu = ((MenuBuilder)localObject2);
      ((MenuBuilder)localObject2).setCallback(new MenuBuilderCallback());
      localObject1 = new ActionMenuPresenter((Context)localObject1);
      this.mPresenter = ((ActionMenuPresenter)localObject1);
      ((ActionMenuPresenter)localObject1).setReserveOverflow(true);
      localObject2 = this.mPresenter;
      localObject1 = this.mActionMenuPresenterCallback;
      if (localObject1 == null) {
        localObject1 = new ActionMenuPresenterCallback();
      }
      ((ActionMenuPresenter)localObject2).setCallback((MenuPresenter.Callback)localObject1);
      this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
      this.mPresenter.setMenuView(this);
    }
    return this.mMenu;
  }
  
  public Drawable getOverflowIcon()
  {
    getMenu();
    return this.mPresenter.getOverflowIcon();
  }
  
  public int getPopupTheme()
  {
    return this.mPopupTheme;
  }
  
  public int getWindowAnimations()
  {
    return 0;
  }
  
  protected boolean hasSupportDividerBeforeChildAt(int paramInt)
  {
    if (paramInt == 0) {
      return false;
    }
    View localView2 = getChildAt(paramInt - 1);
    View localView1 = getChildAt(paramInt);
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramInt < getChildCount())
    {
      bool1 = bool2;
      if ((localView2 instanceof ActionMenuChildView)) {
        bool1 = false | ((ActionMenuChildView)localView2).needsDividerAfter();
      }
    }
    bool2 = bool1;
    if (paramInt > 0)
    {
      bool2 = bool1;
      if ((localView1 instanceof ActionMenuChildView)) {
        bool2 = bool1 | ((ActionMenuChildView)localView1).needsDividerBefore();
      }
    }
    return bool2;
  }
  
  public boolean hideOverflowMenu()
  {
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    boolean bool;
    if ((localActionMenuPresenter != null) && (localActionMenuPresenter.hideOverflowMenu())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder)
  {
    this.mMenu = paramMenuBuilder;
  }
  
  public boolean invokeItem(MenuItemImpl paramMenuItemImpl)
  {
    return this.mMenu.performItemAction(paramMenuItemImpl, 0);
  }
  
  public boolean isOverflowMenuShowPending()
  {
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    boolean bool;
    if ((localActionMenuPresenter != null) && (localActionMenuPresenter.isOverflowMenuShowPending())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOverflowMenuShowing()
  {
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    boolean bool;
    if ((localActionMenuPresenter != null) && (localActionMenuPresenter.isOverflowMenuShowing())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOverflowReserved()
  {
    return this.mReserveOverflow;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    paramConfiguration = this.mPresenter;
    if (paramConfiguration != null)
    {
      paramConfiguration.updateMenuView(false);
      if (this.mPresenter.isOverflowMenuShowing())
      {
        this.mPresenter.hideOverflowMenu();
        this.mPresenter.showOverflowMenu();
      }
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    dismissPopupMenus();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mFormatItems)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    int i2 = getChildCount();
    int i1 = (paramInt4 - paramInt2) / 2;
    int k = getDividerWidth();
    paramInt4 = 0;
    paramInt2 = 0;
    int i = paramInt3 - paramInt1 - getPaddingRight() - getPaddingLeft();
    int m = 0;
    paramBoolean = ViewUtils.isLayoutRtl(this);
    View localView;
    Object localObject;
    for (int j = 0; j < i2; j++)
    {
      localView = getChildAt(j);
      if (localView.getVisibility() != 8)
      {
        localObject = (LayoutParams)localView.getLayoutParams();
        if (((LayoutParams)localObject).isOverflowButton)
        {
          m = localView.getMeasuredWidth();
          paramInt4 = m;
          if (hasSupportDividerBeforeChildAt(j)) {
            paramInt4 = m + k;
          }
          int i3 = localView.getMeasuredHeight();
          int n;
          if (paramBoolean)
          {
            n = getPaddingLeft() + ((LayoutParams)localObject).leftMargin;
            m = n + paramInt4;
          }
          else
          {
            m = getWidth() - getPaddingRight() - ((LayoutParams)localObject).rightMargin;
            n = m - paramInt4;
          }
          int i4 = i1 - i3 / 2;
          localView.layout(n, i4, m, i4 + i3);
          i -= paramInt4;
          m = 1;
        }
        else
        {
          i -= localView.getMeasuredWidth() + ((LayoutParams)localObject).leftMargin + ((LayoutParams)localObject).rightMargin;
          hasSupportDividerBeforeChildAt(j);
          paramInt2++;
        }
      }
    }
    if ((i2 == 1) && (m == 0))
    {
      localObject = getChildAt(0);
      paramInt2 = ((View)localObject).getMeasuredWidth();
      paramInt4 = ((View)localObject).getMeasuredHeight();
      paramInt1 = (paramInt3 - paramInt1) / 2 - paramInt2 / 2;
      paramInt3 = i1 - paramInt4 / 2;
      ((View)localObject).layout(paramInt1, paramInt3, paramInt1 + paramInt2, paramInt3 + paramInt4);
      return;
    }
    paramInt1 = paramInt2 - (m ^ 0x1);
    if (paramInt1 > 0) {
      paramInt1 = i / paramInt1;
    } else {
      paramInt1 = 0;
    }
    i = Math.max(0, paramInt1);
    if (paramBoolean)
    {
      paramInt3 = getWidth() - getPaddingRight();
      for (paramInt1 = 0; paramInt1 < i2; paramInt1++)
      {
        localView = getChildAt(paramInt1);
        localObject = (LayoutParams)localView.getLayoutParams();
        if ((localView.getVisibility() != 8) && (!((LayoutParams)localObject).isOverflowButton))
        {
          j = paramInt3 - ((LayoutParams)localObject).rightMargin;
          paramInt3 = localView.getMeasuredWidth();
          m = localView.getMeasuredHeight();
          k = i1 - m / 2;
          localView.layout(j - paramInt3, k, j, k + m);
          paramInt3 = j - (((LayoutParams)localObject).leftMargin + paramInt3 + i);
        }
      }
    }
    else
    {
      paramInt2 = getPaddingLeft();
      paramInt1 = 0;
      while (paramInt1 < i2)
      {
        localView = getChildAt(paramInt1);
        localObject = (LayoutParams)localView.getLayoutParams();
        paramInt3 = paramInt2;
        if (localView.getVisibility() != 8) {
          if (((LayoutParams)localObject).isOverflowButton)
          {
            paramInt3 = paramInt2;
          }
          else
          {
            paramInt4 = paramInt2 + ((LayoutParams)localObject).leftMargin;
            paramInt3 = localView.getMeasuredWidth();
            j = localView.getMeasuredHeight();
            paramInt2 = i1 - j / 2;
            localView.layout(paramInt4, paramInt2, paramInt4 + paramInt3, paramInt2 + j);
            paramInt3 = paramInt4 + (((LayoutParams)localObject).rightMargin + paramInt3 + i);
          }
        }
        paramInt1++;
        paramInt2 = paramInt3;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool2 = this.mFormatItems;
    boolean bool1;
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mFormatItems = bool1;
    if (bool2 != bool1) {
      this.mFormatItemsWidth = 0;
    }
    int i = View.MeasureSpec.getSize(paramInt1);
    Object localObject;
    if (this.mFormatItems)
    {
      localObject = this.mMenu;
      if ((localObject != null) && (i != this.mFormatItemsWidth))
      {
        this.mFormatItemsWidth = i;
        ((MenuBuilder)localObject).onItemsChanged(true);
      }
    }
    int j = getChildCount();
    if ((this.mFormatItems) && (j > 0))
    {
      onMeasureExactFormat(paramInt1, paramInt2);
    }
    else
    {
      for (i = 0; i < j; i++)
      {
        localObject = (LayoutParams)getChildAt(i).getLayoutParams();
        ((LayoutParams)localObject).rightMargin = 0;
        ((LayoutParams)localObject).leftMargin = 0;
      }
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public MenuBuilder peekMenu()
  {
    return this.mMenu;
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean)
  {
    this.mPresenter.setExpandedActionViewsExclusive(paramBoolean);
  }
  
  public void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1)
  {
    this.mActionMenuPresenterCallback = paramCallback;
    this.mMenuBuilderCallback = paramCallback1;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable)
  {
    getMenu();
    this.mPresenter.setOverflowIcon(paramDrawable);
  }
  
  public void setOverflowReserved(boolean paramBoolean)
  {
    this.mReserveOverflow = paramBoolean;
  }
  
  public void setPopupTheme(int paramInt)
  {
    if (this.mPopupTheme != paramInt)
    {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      } else {
        this.mPopupContext = new ContextThemeWrapper(getContext(), paramInt);
      }
    }
  }
  
  public void setPresenter(ActionMenuPresenter paramActionMenuPresenter)
  {
    this.mPresenter = paramActionMenuPresenter;
    paramActionMenuPresenter.setMenuView(this);
  }
  
  public boolean showOverflowMenu()
  {
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    boolean bool;
    if ((localActionMenuPresenter != null) && (localActionMenuPresenter.showOverflowMenu())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static abstract interface ActionMenuChildView
  {
    public abstract boolean needsDividerAfter();
    
    public abstract boolean needsDividerBefore();
  }
  
  private static class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      return false;
    }
  }
  
  public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams
  {
    @ViewDebug.ExportedProperty
    public int cellsUsed;
    @ViewDebug.ExportedProperty
    public boolean expandable;
    boolean expanded;
    @ViewDebug.ExportedProperty
    public int extraPixels;
    @ViewDebug.ExportedProperty
    public boolean isOverflowButton;
    @ViewDebug.ExportedProperty
    public boolean preventEdgeOffset;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
      this.isOverflowButton = false;
    }
    
    LayoutParams(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      super(paramInt2);
      this.isOverflowButton = paramBoolean;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.isOverflowButton = paramLayoutParams.isOverflowButton;
    }
  }
  
  private class MenuBuilderCallback
    implements MenuBuilder.Callback
  {
    MenuBuilderCallback() {}
    
    public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      boolean bool;
      if ((ActionMenuView.this.mOnMenuItemClickListener != null) && (ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(paramMenuItem))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (ActionMenuView.this.mMenuBuilderCallback != null) {
        ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(paramMenuBuilder);
      }
    }
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/ActionMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */