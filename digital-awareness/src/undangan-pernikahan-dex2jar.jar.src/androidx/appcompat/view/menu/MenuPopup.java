package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;

abstract class MenuPopup
  implements ShowableListMenu, MenuPresenter, AdapterView.OnItemClickListener
{
  private Rect mEpicenterBounds;
  
  protected static int measureIndividualMenuWidth(ListAdapter paramListAdapter, ViewGroup paramViewGroup, Context paramContext, int paramInt)
  {
    int j = 0;
    Object localObject2 = null;
    int m = 0;
    int i3 = View.MeasureSpec.makeMeasureSpec(0, 0);
    int i2 = View.MeasureSpec.makeMeasureSpec(0, 0);
    int i1 = paramListAdapter.getCount();
    int i = 0;
    Object localObject1 = paramViewGroup;
    paramViewGroup = (ViewGroup)localObject2;
    while (i < i1)
    {
      int n = paramListAdapter.getItemViewType(i);
      int k = m;
      if (n != m)
      {
        k = n;
        paramViewGroup = null;
      }
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new FrameLayout(paramContext);
      }
      paramViewGroup = paramListAdapter.getView(i, paramViewGroup, (ViewGroup)localObject2);
      paramViewGroup.measure(i3, i2);
      n = paramViewGroup.getMeasuredWidth();
      if (n >= paramInt) {
        return paramInt;
      }
      m = j;
      if (n > j) {
        m = n;
      }
      i++;
      j = m;
      m = k;
      localObject1 = localObject2;
    }
    return j;
  }
  
  protected static boolean shouldPreserveIconSpacing(MenuBuilder paramMenuBuilder)
  {
    boolean bool2 = false;
    int j = paramMenuBuilder.size();
    boolean bool1;
    for (int i = 0;; i++)
    {
      bool1 = bool2;
      if (i >= j) {
        break;
      }
      MenuItem localMenuItem = paramMenuBuilder.getItem(i);
      if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null))
      {
        bool1 = true;
        break;
      }
    }
    return bool1;
  }
  
  protected static MenuAdapter toMenuAdapter(ListAdapter paramListAdapter)
  {
    if ((paramListAdapter instanceof HeaderViewListAdapter)) {
      return (MenuAdapter)((HeaderViewListAdapter)paramListAdapter).getWrappedAdapter();
    }
    return (MenuAdapter)paramListAdapter;
  }
  
  public abstract void addMenu(MenuBuilder paramMenuBuilder);
  
  protected boolean closeMenuOnSubMenuOpened()
  {
    return true;
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public Rect getEpicenterBounds()
  {
    return this.mEpicenterBounds;
  }
  
  public int getId()
  {
    return 0;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    throw new UnsupportedOperationException("MenuPopups manage their own views");
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {}
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramView = (ListAdapter)paramAdapterView.getAdapter();
    paramAdapterView = toMenuAdapter(paramView).mAdapterMenu;
    paramView = (MenuItem)paramView.getItem(paramInt);
    if (closeMenuOnSubMenuOpened()) {
      paramInt = 0;
    } else {
      paramInt = 4;
    }
    paramAdapterView.performItemAction(paramView, this, paramInt);
  }
  
  public abstract void setAnchorView(View paramView);
  
  public void setEpicenterBounds(Rect paramRect)
  {
    this.mEpicenterBounds = paramRect;
  }
  
  public abstract void setForceShowIcon(boolean paramBoolean);
  
  public abstract void setGravity(int paramInt);
  
  public abstract void setHorizontalOffset(int paramInt);
  
  public abstract void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener);
  
  public abstract void setShowTitle(boolean paramBoolean);
  
  public abstract void setVerticalOffset(int paramInt);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/view/menu/MenuPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */