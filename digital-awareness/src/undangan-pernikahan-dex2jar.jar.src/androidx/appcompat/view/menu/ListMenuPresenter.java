package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.R.layout;
import java.util.ArrayList;

public class ListMenuPresenter
  implements MenuPresenter, AdapterView.OnItemClickListener
{
  private static final String TAG = "ListMenuPresenter";
  public static final String VIEWS_TAG = "android:menu:list";
  MenuAdapter mAdapter;
  private MenuPresenter.Callback mCallback;
  Context mContext;
  private int mId;
  LayoutInflater mInflater;
  int mItemIndexOffset;
  int mItemLayoutRes;
  MenuBuilder mMenu;
  ExpandedMenuView mMenuView;
  int mThemeRes;
  
  public ListMenuPresenter(int paramInt1, int paramInt2)
  {
    this.mItemLayoutRes = paramInt1;
    this.mThemeRes = paramInt2;
  }
  
  public ListMenuPresenter(Context paramContext, int paramInt)
  {
    this(paramInt, 0);
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public ListAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new MenuAdapter();
    }
    return this.mAdapter;
  }
  
  public int getId()
  {
    return this.mId;
  }
  
  int getItemIndexOffset()
  {
    return this.mItemIndexOffset;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    if (this.mMenuView == null)
    {
      this.mMenuView = ((ExpandedMenuView)this.mInflater.inflate(R.layout.abc_expanded_menu_layout, paramViewGroup, false));
      if (this.mAdapter == null) {
        this.mAdapter = new MenuAdapter();
      }
      this.mMenuView.setAdapter(this.mAdapter);
      this.mMenuView.setOnItemClickListener(this);
    }
    return this.mMenuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    if (this.mThemeRes != 0)
    {
      paramContext = new ContextThemeWrapper(paramContext, this.mThemeRes);
      this.mContext = paramContext;
      this.mInflater = LayoutInflater.from(paramContext);
    }
    else if (this.mContext != null)
    {
      this.mContext = paramContext;
      if (this.mInflater == null) {
        this.mInflater = LayoutInflater.from(paramContext);
      }
    }
    this.mMenu = paramMenuBuilder;
    paramContext = this.mAdapter;
    if (paramContext != null) {
      paramContext.notifyDataSetChanged();
    }
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    MenuPresenter.Callback localCallback = this.mCallback;
    if (localCallback != null) {
      localCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mMenu.performItemAction(this.mAdapter.getItem(paramInt), this, 0);
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    restoreHierarchyState((Bundle)paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    if (this.mMenuView == null) {
      return null;
    }
    Bundle localBundle = new Bundle();
    saveHierarchyState(localBundle);
    return localBundle;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (!paramSubMenuBuilder.hasVisibleItems()) {
      return false;
    }
    new MenuDialogHelper(paramSubMenuBuilder).show(null);
    MenuPresenter.Callback localCallback = this.mCallback;
    if (localCallback != null) {
      localCallback.onOpenSubMenu(paramSubMenuBuilder);
    }
    return true;
  }
  
  public void restoreHierarchyState(Bundle paramBundle)
  {
    paramBundle = paramBundle.getSparseParcelableArray("android:menu:list");
    if (paramBundle != null) {
      this.mMenuView.restoreHierarchyState(paramBundle);
    }
  }
  
  public void saveHierarchyState(Bundle paramBundle)
  {
    SparseArray localSparseArray = new SparseArray();
    ExpandedMenuView localExpandedMenuView = this.mMenuView;
    if (localExpandedMenuView != null) {
      localExpandedMenuView.saveHierarchyState(localSparseArray);
    }
    paramBundle.putSparseParcelableArray("android:menu:list", localSparseArray);
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public void setId(int paramInt)
  {
    this.mId = paramInt;
  }
  
  public void setItemIndexOffset(int paramInt)
  {
    this.mItemIndexOffset = paramInt;
    if (this.mMenuView != null) {
      updateMenuView(false);
    }
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    MenuAdapter localMenuAdapter = this.mAdapter;
    if (localMenuAdapter != null) {
      localMenuAdapter.notifyDataSetChanged();
    }
  }
  
  private class MenuAdapter
    extends BaseAdapter
  {
    private int mExpandedIndex = -1;
    
    public MenuAdapter()
    {
      findExpandedIndex();
    }
    
    void findExpandedIndex()
    {
      MenuItemImpl localMenuItemImpl = ListMenuPresenter.this.mMenu.getExpandedItem();
      if (localMenuItemImpl != null)
      {
        ArrayList localArrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
        int j = localArrayList.size();
        for (int i = 0; i < j; i++) {
          if ((MenuItemImpl)localArrayList.get(i) == localMenuItemImpl)
          {
            this.mExpandedIndex = i;
            return;
          }
        }
      }
      this.mExpandedIndex = -1;
    }
    
    public int getCount()
    {
      int i = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset;
      if (this.mExpandedIndex < 0) {
        return i;
      }
      return i - 1;
    }
    
    public MenuItemImpl getItem(int paramInt)
    {
      ArrayList localArrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
      int i = paramInt + ListMenuPresenter.this.mItemIndexOffset;
      int j = this.mExpandedIndex;
      paramInt = i;
      if (j >= 0)
      {
        paramInt = i;
        if (i >= j) {
          paramInt = i + 1;
        }
      }
      return (MenuItemImpl)localArrayList.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      if (paramView == null) {
        localView = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, paramViewGroup, false);
      }
      ((MenuView.ItemView)localView).initialize(getItem(paramInt), 0);
      return localView;
    }
    
    public void notifyDataSetChanged()
    {
      findExpandedIndex();
      super.notifyDataSetChanged();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/view/menu/ListMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */