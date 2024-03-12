package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import java.util.ArrayList;

public class NavigationMenuPresenter
  implements MenuPresenter
{
  public static final int NO_TEXT_APPEARANCE_SET = 0;
  private static final String STATE_ADAPTER = "android:menu:adapter";
  private static final String STATE_HEADER = "android:menu:header";
  private static final String STATE_HIERARCHY = "android:menu:list";
  NavigationMenuAdapter adapter;
  private MenuPresenter.Callback callback;
  int dividerInsetEnd;
  int dividerInsetStart;
  boolean hasCustomItemIconSize;
  LinearLayout headerLayout;
  ColorStateList iconTintList;
  private int id;
  boolean isBehindStatusBar = true;
  Drawable itemBackground;
  RippleDrawable itemForeground;
  int itemHorizontalPadding;
  int itemIconPadding;
  int itemIconSize;
  private int itemMaxLines;
  int itemVerticalPadding;
  LayoutInflater layoutInflater;
  MenuBuilder menu;
  private NavigationMenuView menuView;
  final View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      paramAnonymousView = (NavigationMenuItemView)paramAnonymousView;
      NavigationMenuPresenter.this.setUpdateSuspended(true);
      paramAnonymousView = paramAnonymousView.getItemData();
      boolean bool = NavigationMenuPresenter.this.menu.performItemAction(paramAnonymousView, NavigationMenuPresenter.this, 0);
      int j = 0;
      int i = j;
      if (paramAnonymousView != null)
      {
        i = j;
        if (paramAnonymousView.isCheckable())
        {
          i = j;
          if (bool)
          {
            NavigationMenuPresenter.this.adapter.setCheckedItem(paramAnonymousView);
            i = 1;
          }
        }
      }
      NavigationMenuPresenter.this.setUpdateSuspended(false);
      if (i != 0) {
        NavigationMenuPresenter.this.updateMenuView(false);
      }
    }
  };
  private int overScrollMode = -1;
  int paddingSeparator;
  private int paddingTopDefault;
  ColorStateList subheaderColor;
  int subheaderInsetEnd;
  int subheaderInsetStart;
  int subheaderTextAppearance = 0;
  int textAppearance = 0;
  ColorStateList textColor;
  
  private void updateTopPadding()
  {
    int j = 0;
    int i = j;
    if (this.headerLayout.getChildCount() == 0)
    {
      i = j;
      if (this.isBehindStatusBar) {
        i = this.paddingTopDefault;
      }
    }
    NavigationMenuView localNavigationMenuView = this.menuView;
    localNavigationMenuView.setPadding(0, i, 0, localNavigationMenuView.getPaddingBottom());
  }
  
  public void addHeaderView(View paramView)
  {
    this.headerLayout.addView(paramView);
    paramView = this.menuView;
    paramView.setPadding(0, 0, 0, paramView.getPaddingBottom());
  }
  
  public boolean collapseItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public void dispatchApplyWindowInsets(WindowInsetsCompat paramWindowInsetsCompat)
  {
    int i = paramWindowInsetsCompat.getSystemWindowInsetTop();
    if (this.paddingTopDefault != i)
    {
      this.paddingTopDefault = i;
      updateTopPadding();
    }
    NavigationMenuView localNavigationMenuView = this.menuView;
    localNavigationMenuView.setPadding(0, localNavigationMenuView.getPaddingTop(), 0, paramWindowInsetsCompat.getSystemWindowInsetBottom());
    ViewCompat.dispatchApplyWindowInsets(this.headerLayout, paramWindowInsetsCompat);
  }
  
  public boolean expandItemActionView(MenuBuilder paramMenuBuilder, MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public MenuItemImpl getCheckedItem()
  {
    return this.adapter.getCheckedItem();
  }
  
  public int getDividerInsetEnd()
  {
    return this.dividerInsetEnd;
  }
  
  public int getDividerInsetStart()
  {
    return this.dividerInsetStart;
  }
  
  public int getHeaderCount()
  {
    return this.headerLayout.getChildCount();
  }
  
  public View getHeaderView(int paramInt)
  {
    return this.headerLayout.getChildAt(paramInt);
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public Drawable getItemBackground()
  {
    return this.itemBackground;
  }
  
  public int getItemHorizontalPadding()
  {
    return this.itemHorizontalPadding;
  }
  
  public int getItemIconPadding()
  {
    return this.itemIconPadding;
  }
  
  public int getItemMaxLines()
  {
    return this.itemMaxLines;
  }
  
  public ColorStateList getItemTextColor()
  {
    return this.textColor;
  }
  
  public ColorStateList getItemTintList()
  {
    return this.iconTintList;
  }
  
  public int getItemVerticalPadding()
  {
    return this.itemVerticalPadding;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    if (this.menuView == null)
    {
      paramViewGroup = (NavigationMenuView)this.layoutInflater.inflate(R.layout.design_navigation_menu, paramViewGroup, false);
      this.menuView = paramViewGroup;
      paramViewGroup.setAccessibilityDelegateCompat(new NavigationMenuViewAccessibilityDelegate(this.menuView));
      if (this.adapter == null) {
        this.adapter = new NavigationMenuAdapter();
      }
      int i = this.overScrollMode;
      if (i != -1) {
        this.menuView.setOverScrollMode(i);
      }
      this.headerLayout = ((LinearLayout)this.layoutInflater.inflate(R.layout.design_navigation_item_header, this.menuView, false));
      this.menuView.setAdapter(this.adapter);
    }
    return this.menuView;
  }
  
  public int getSubheaderInsetEnd()
  {
    return this.subheaderInsetEnd;
  }
  
  public int getSubheaderInsetStart()
  {
    return this.subheaderInsetStart;
  }
  
  public View inflateHeaderView(int paramInt)
  {
    View localView = this.layoutInflater.inflate(paramInt, this.headerLayout, false);
    addHeaderView(localView);
    return localView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    this.layoutInflater = LayoutInflater.from(paramContext);
    this.menu = paramMenuBuilder;
    this.paddingSeparator = paramContext.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
  }
  
  public boolean isBehindStatusBar()
  {
    return this.isBehindStatusBar;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    MenuPresenter.Callback localCallback = this.callback;
    if (localCallback != null) {
      localCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof Bundle))
    {
      paramParcelable = (Bundle)paramParcelable;
      Object localObject = paramParcelable.getSparseParcelableArray("android:menu:list");
      if (localObject != null) {
        this.menuView.restoreHierarchyState((SparseArray)localObject);
      }
      localObject = paramParcelable.getBundle("android:menu:adapter");
      if (localObject != null) {
        this.adapter.restoreInstanceState((Bundle)localObject);
      }
      paramParcelable = paramParcelable.getSparseParcelableArray("android:menu:header");
      if (paramParcelable != null) {
        this.headerLayout.restoreHierarchyState(paramParcelable);
      }
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    if (this.menuView != null)
    {
      localObject = new SparseArray();
      this.menuView.saveHierarchyState((SparseArray)localObject);
      localBundle.putSparseParcelableArray("android:menu:list", (SparseArray)localObject);
    }
    Object localObject = this.adapter;
    if (localObject != null) {
      localBundle.putBundle("android:menu:adapter", ((NavigationMenuAdapter)localObject).createInstanceState());
    }
    if (this.headerLayout != null)
    {
      localObject = new SparseArray();
      this.headerLayout.saveHierarchyState((SparseArray)localObject);
      localBundle.putSparseParcelableArray("android:menu:header", (SparseArray)localObject);
    }
    return localBundle;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    return false;
  }
  
  public void removeHeaderView(View paramView)
  {
    this.headerLayout.removeView(paramView);
    if (this.headerLayout.getChildCount() == 0)
    {
      paramView = this.menuView;
      paramView.setPadding(0, this.paddingTopDefault, 0, paramView.getPaddingBottom());
    }
  }
  
  public void setBehindStatusBar(boolean paramBoolean)
  {
    if (this.isBehindStatusBar != paramBoolean)
    {
      this.isBehindStatusBar = paramBoolean;
      updateTopPadding();
    }
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback)
  {
    this.callback = paramCallback;
  }
  
  public void setCheckedItem(MenuItemImpl paramMenuItemImpl)
  {
    this.adapter.setCheckedItem(paramMenuItemImpl);
  }
  
  public void setDividerInsetEnd(int paramInt)
  {
    this.dividerInsetEnd = paramInt;
    updateMenuView(false);
  }
  
  public void setDividerInsetStart(int paramInt)
  {
    this.dividerInsetStart = paramInt;
    updateMenuView(false);
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setItemBackground(Drawable paramDrawable)
  {
    this.itemBackground = paramDrawable;
    updateMenuView(false);
  }
  
  public void setItemForeground(RippleDrawable paramRippleDrawable)
  {
    this.itemForeground = paramRippleDrawable;
    updateMenuView(false);
  }
  
  public void setItemHorizontalPadding(int paramInt)
  {
    this.itemHorizontalPadding = paramInt;
    updateMenuView(false);
  }
  
  public void setItemIconPadding(int paramInt)
  {
    this.itemIconPadding = paramInt;
    updateMenuView(false);
  }
  
  public void setItemIconSize(int paramInt)
  {
    if (this.itemIconSize != paramInt)
    {
      this.itemIconSize = paramInt;
      this.hasCustomItemIconSize = true;
      updateMenuView(false);
    }
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList)
  {
    this.iconTintList = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setItemMaxLines(int paramInt)
  {
    this.itemMaxLines = paramInt;
    updateMenuView(false);
  }
  
  public void setItemTextAppearance(int paramInt)
  {
    this.textAppearance = paramInt;
    updateMenuView(false);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList)
  {
    this.textColor = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setItemVerticalPadding(int paramInt)
  {
    this.itemVerticalPadding = paramInt;
    updateMenuView(false);
  }
  
  public void setOverScrollMode(int paramInt)
  {
    this.overScrollMode = paramInt;
    NavigationMenuView localNavigationMenuView = this.menuView;
    if (localNavigationMenuView != null) {
      localNavigationMenuView.setOverScrollMode(paramInt);
    }
  }
  
  public void setSubheaderColor(ColorStateList paramColorStateList)
  {
    this.subheaderColor = paramColorStateList;
    updateMenuView(false);
  }
  
  public void setSubheaderInsetEnd(int paramInt)
  {
    this.subheaderInsetEnd = paramInt;
    updateMenuView(false);
  }
  
  public void setSubheaderInsetStart(int paramInt)
  {
    this.subheaderInsetStart = paramInt;
    updateMenuView(false);
  }
  
  public void setSubheaderTextAppearance(int paramInt)
  {
    this.subheaderTextAppearance = paramInt;
    updateMenuView(false);
  }
  
  public void setUpdateSuspended(boolean paramBoolean)
  {
    NavigationMenuAdapter localNavigationMenuAdapter = this.adapter;
    if (localNavigationMenuAdapter != null) {
      localNavigationMenuAdapter.setUpdateSuspended(paramBoolean);
    }
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    NavigationMenuAdapter localNavigationMenuAdapter = this.adapter;
    if (localNavigationMenuAdapter != null) {
      localNavigationMenuAdapter.update();
    }
  }
  
  private static class HeaderViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public HeaderViewHolder(View paramView)
    {
      super();
    }
  }
  
  private class NavigationMenuAdapter
    extends RecyclerView.Adapter<NavigationMenuPresenter.ViewHolder>
  {
    private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
    private static final String STATE_CHECKED_ITEM = "android:menu:checked";
    private static final int VIEW_TYPE_HEADER = 3;
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_SEPARATOR = 2;
    private static final int VIEW_TYPE_SUBHEADER = 1;
    private MenuItemImpl checkedItem;
    private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> items = new ArrayList();
    private boolean updateSuspended;
    
    NavigationMenuAdapter()
    {
      prepareMenuItems();
    }
    
    private void appendTransparentIconIfMissing(int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        ((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(paramInt1)).needsEmptyIcon = true;
        paramInt1++;
      }
    }
    
    private void prepareMenuItems()
    {
      if (this.updateSuspended) {
        return;
      }
      this.updateSuspended = true;
      this.items.clear();
      this.items.add(new NavigationMenuPresenter.NavigationMenuHeaderItem());
      int n = -1;
      int j = 0;
      boolean bool2 = false;
      int m = 0;
      int i2 = NavigationMenuPresenter.this.menu.getVisibleItems().size();
      for (;;)
      {
        boolean bool1 = false;
        if (m >= i2) {
          break;
        }
        Object localObject = (MenuItemImpl)NavigationMenuPresenter.this.menu.getVisibleItems().get(m);
        if (((MenuItemImpl)localObject).isChecked()) {
          setCheckedItem((MenuItemImpl)localObject);
        }
        if (((MenuItemImpl)localObject).isCheckable()) {
          ((MenuItemImpl)localObject).setExclusiveCheckable(false);
        }
        int i;
        int k;
        if (((MenuItemImpl)localObject).hasSubMenu())
        {
          SubMenu localSubMenu = ((MenuItemImpl)localObject).getSubMenu();
          if (localSubMenu.hasVisibleItems())
          {
            if (m != 0) {
              this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
            }
            this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem((MenuItemImpl)localObject));
            i = 0;
            int i4 = this.items.size();
            int i1 = 0;
            int i3 = localSubMenu.size();
            while (i1 < i3)
            {
              MenuItemImpl localMenuItemImpl = (MenuItemImpl)localSubMenu.getItem(i1);
              k = i;
              if (localMenuItemImpl.isVisible())
              {
                k = i;
                if (i == 0)
                {
                  k = i;
                  if (localMenuItemImpl.getIcon() != null) {
                    k = 1;
                  }
                }
                if (localMenuItemImpl.isCheckable()) {
                  localMenuItemImpl.setExclusiveCheckable(false);
                }
                if (((MenuItemImpl)localObject).isChecked()) {
                  setCheckedItem((MenuItemImpl)localObject);
                }
                this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem(localMenuItemImpl));
              }
              i1++;
              i = k;
            }
            if (i != 0) {
              appendTransparentIconIfMissing(i4, this.items.size());
            }
          }
          i = j;
          bool1 = bool2;
        }
        else
        {
          k = ((MenuItemImpl)localObject).getGroupId();
          if (k != n)
          {
            j = this.items.size();
            if (((MenuItemImpl)localObject).getIcon() != null) {
              bool1 = true;
            }
            bool2 = bool1;
            i = j;
            bool1 = bool2;
            if (m != 0)
            {
              i = j + 1;
              this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, NavigationMenuPresenter.this.paddingSeparator));
              bool1 = bool2;
            }
          }
          else
          {
            i = j;
            bool1 = bool2;
            if (!bool2)
            {
              i = j;
              bool1 = bool2;
              if (((MenuItemImpl)localObject).getIcon() != null)
              {
                bool1 = true;
                appendTransparentIconIfMissing(j, this.items.size());
                i = j;
              }
            }
          }
          localObject = new NavigationMenuPresenter.NavigationMenuTextItem((MenuItemImpl)localObject);
          ((NavigationMenuPresenter.NavigationMenuTextItem)localObject).needsEmptyIcon = bool1;
          this.items.add(localObject);
          n = k;
        }
        m++;
        j = i;
        bool2 = bool1;
      }
      this.updateSuspended = false;
    }
    
    public Bundle createInstanceState()
    {
      Bundle localBundle = new Bundle();
      Object localObject = this.checkedItem;
      if (localObject != null) {
        localBundle.putInt("android:menu:checked", ((MenuItemImpl)localObject).getItemId());
      }
      SparseArray localSparseArray = new SparseArray();
      int i = 0;
      int j = this.items.size();
      while (i < j)
      {
        localObject = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(i);
        if ((localObject instanceof NavigationMenuPresenter.NavigationMenuTextItem))
        {
          MenuItemImpl localMenuItemImpl = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject).getMenuItem();
          if (localMenuItemImpl != null) {
            localObject = localMenuItemImpl.getActionView();
          } else {
            localObject = null;
          }
          if (localObject != null)
          {
            ParcelableSparseArray localParcelableSparseArray = new ParcelableSparseArray();
            ((View)localObject).saveHierarchyState(localParcelableSparseArray);
            localSparseArray.put(localMenuItemImpl.getItemId(), localParcelableSparseArray);
          }
        }
        i++;
      }
      localBundle.putSparseParcelableArray("android:menu:action_views", localSparseArray);
      return localBundle;
    }
    
    public MenuItemImpl getCheckedItem()
    {
      return this.checkedItem;
    }
    
    public int getItemCount()
    {
      return this.items.size();
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public int getItemViewType(int paramInt)
    {
      NavigationMenuPresenter.NavigationMenuItem localNavigationMenuItem = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(paramInt);
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem)) {
        return 2;
      }
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuHeaderItem)) {
        return 3;
      }
      if ((localNavigationMenuItem instanceof NavigationMenuPresenter.NavigationMenuTextItem))
      {
        if (((NavigationMenuPresenter.NavigationMenuTextItem)localNavigationMenuItem).getMenuItem().hasSubMenu()) {
          return 1;
        }
        return 0;
      }
      throw new RuntimeException("Unknown item type.");
    }
    
    int getRowCount()
    {
      int i;
      if (NavigationMenuPresenter.this.headerLayout.getChildCount() == 0) {
        i = 0;
      } else {
        i = 1;
      }
      int j = 0;
      for (int k = i; j < NavigationMenuPresenter.this.adapter.getItemCount(); k = i)
      {
        i = k;
        if (NavigationMenuPresenter.this.adapter.getItemViewType(j) == 0) {
          i = k + 1;
        }
        j++;
      }
      return k;
    }
    
    public void onBindViewHolder(NavigationMenuPresenter.ViewHolder paramViewHolder, int paramInt)
    {
      Object localObject;
      switch (getItemViewType(paramInt))
      {
      default: 
        break;
      case 2: 
        localObject = (NavigationMenuPresenter.NavigationMenuSeparatorItem)this.items.get(paramInt);
        paramViewHolder.itemView.setPadding(NavigationMenuPresenter.this.dividerInsetStart, ((NavigationMenuPresenter.NavigationMenuSeparatorItem)localObject).getPaddingTop(), NavigationMenuPresenter.this.dividerInsetEnd, ((NavigationMenuPresenter.NavigationMenuSeparatorItem)localObject).getPaddingBottom());
        break;
      case 1: 
        paramViewHolder = (TextView)paramViewHolder.itemView;
        paramViewHolder.setText(((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(paramInt)).getMenuItem().getTitle());
        if (NavigationMenuPresenter.this.subheaderTextAppearance != 0) {
          TextViewCompat.setTextAppearance(paramViewHolder, NavigationMenuPresenter.this.subheaderTextAppearance);
        }
        paramViewHolder.setPadding(NavigationMenuPresenter.this.subheaderInsetStart, paramViewHolder.getPaddingTop(), NavigationMenuPresenter.this.subheaderInsetEnd, paramViewHolder.getPaddingBottom());
        if (NavigationMenuPresenter.this.subheaderColor != null) {
          paramViewHolder.setTextColor(NavigationMenuPresenter.this.subheaderColor);
        }
        break;
      case 0: 
        localObject = (NavigationMenuItemView)paramViewHolder.itemView;
        ((NavigationMenuItemView)localObject).setIconTintList(NavigationMenuPresenter.this.iconTintList);
        if (NavigationMenuPresenter.this.textAppearance != 0) {
          ((NavigationMenuItemView)localObject).setTextAppearance(NavigationMenuPresenter.this.textAppearance);
        }
        if (NavigationMenuPresenter.this.textColor != null) {
          ((NavigationMenuItemView)localObject).setTextColor(NavigationMenuPresenter.this.textColor);
        }
        if (NavigationMenuPresenter.this.itemBackground != null) {
          paramViewHolder = NavigationMenuPresenter.this.itemBackground.getConstantState().newDrawable();
        } else {
          paramViewHolder = null;
        }
        ViewCompat.setBackground((View)localObject, paramViewHolder);
        if (NavigationMenuPresenter.this.itemForeground != null) {
          ((NavigationMenuItemView)localObject).setForeground(NavigationMenuPresenter.this.itemForeground.getConstantState().newDrawable());
        }
        paramViewHolder = (NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(paramInt);
        ((NavigationMenuItemView)localObject).setNeedsEmptyIcon(paramViewHolder.needsEmptyIcon);
        ((NavigationMenuItemView)localObject).setPadding(NavigationMenuPresenter.this.itemHorizontalPadding, NavigationMenuPresenter.this.itemVerticalPadding, NavigationMenuPresenter.this.itemHorizontalPadding, NavigationMenuPresenter.this.itemVerticalPadding);
        ((NavigationMenuItemView)localObject).setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
        if (NavigationMenuPresenter.this.hasCustomItemIconSize) {
          ((NavigationMenuItemView)localObject).setIconSize(NavigationMenuPresenter.this.itemIconSize);
        }
        ((NavigationMenuItemView)localObject).setMaxLines(NavigationMenuPresenter.this.itemMaxLines);
        ((NavigationMenuItemView)localObject).initialize(paramViewHolder.getMenuItem(), 0);
      }
    }
    
    public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return null;
      case 3: 
        return new NavigationMenuPresenter.HeaderViewHolder(NavigationMenuPresenter.this.headerLayout);
      case 2: 
        return new NavigationMenuPresenter.SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, paramViewGroup);
      case 1: 
        return new NavigationMenuPresenter.SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, paramViewGroup);
      }
      return new NavigationMenuPresenter.NormalViewHolder(NavigationMenuPresenter.this.layoutInflater, paramViewGroup, NavigationMenuPresenter.this.onClickListener);
    }
    
    public void onViewRecycled(NavigationMenuPresenter.ViewHolder paramViewHolder)
    {
      if ((paramViewHolder instanceof NavigationMenuPresenter.NormalViewHolder)) {
        ((NavigationMenuItemView)paramViewHolder.itemView).recycle();
      }
    }
    
    public void restoreInstanceState(Bundle paramBundle)
    {
      int k = paramBundle.getInt("android:menu:checked", 0);
      int i;
      int j;
      Object localObject1;
      if (k != 0)
      {
        this.updateSuspended = true;
        i = 0;
        j = this.items.size();
        while (i < j)
        {
          localObject1 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(i);
          if ((localObject1 instanceof NavigationMenuPresenter.NavigationMenuTextItem))
          {
            localObject1 = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject1).getMenuItem();
            if ((localObject1 != null) && (((MenuItemImpl)localObject1).getItemId() == k))
            {
              setCheckedItem((MenuItemImpl)localObject1);
              break;
            }
          }
          i++;
        }
        this.updateSuspended = false;
        prepareMenuItems();
      }
      paramBundle = paramBundle.getSparseParcelableArray("android:menu:action_views");
      if (paramBundle != null)
      {
        i = 0;
        j = this.items.size();
        while (i < j)
        {
          localObject1 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(i);
          if ((localObject1 instanceof NavigationMenuPresenter.NavigationMenuTextItem))
          {
            Object localObject2 = ((NavigationMenuPresenter.NavigationMenuTextItem)localObject1).getMenuItem();
            if (localObject2 != null)
            {
              localObject1 = ((MenuItemImpl)localObject2).getActionView();
              if (localObject1 != null)
              {
                localObject2 = (ParcelableSparseArray)paramBundle.get(((MenuItemImpl)localObject2).getItemId());
                if (localObject2 != null) {
                  ((View)localObject1).restoreHierarchyState((SparseArray)localObject2);
                }
              }
            }
          }
          i++;
        }
      }
    }
    
    public void setCheckedItem(MenuItemImpl paramMenuItemImpl)
    {
      if ((this.checkedItem != paramMenuItemImpl) && (paramMenuItemImpl.isCheckable()))
      {
        MenuItemImpl localMenuItemImpl = this.checkedItem;
        if (localMenuItemImpl != null) {
          localMenuItemImpl.setChecked(false);
        }
        this.checkedItem = paramMenuItemImpl;
        paramMenuItemImpl.setChecked(true);
        return;
      }
    }
    
    public void setUpdateSuspended(boolean paramBoolean)
    {
      this.updateSuspended = paramBoolean;
    }
    
    public void update()
    {
      prepareMenuItems();
      notifyDataSetChanged();
    }
  }
  
  private static class NavigationMenuHeaderItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {}
  
  private static abstract interface NavigationMenuItem {}
  
  private static class NavigationMenuSeparatorItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {
    private final int paddingBottom;
    private final int paddingTop;
    
    public NavigationMenuSeparatorItem(int paramInt1, int paramInt2)
    {
      this.paddingTop = paramInt1;
      this.paddingBottom = paramInt2;
    }
    
    public int getPaddingBottom()
    {
      return this.paddingBottom;
    }
    
    public int getPaddingTop()
    {
      return this.paddingTop;
    }
  }
  
  private static class NavigationMenuTextItem
    implements NavigationMenuPresenter.NavigationMenuItem
  {
    private final MenuItemImpl menuItem;
    boolean needsEmptyIcon;
    
    NavigationMenuTextItem(MenuItemImpl paramMenuItemImpl)
    {
      this.menuItem = paramMenuItemImpl;
    }
    
    public MenuItemImpl getMenuItem()
    {
      return this.menuItem;
    }
  }
  
  private class NavigationMenuViewAccessibilityDelegate
    extends RecyclerViewAccessibilityDelegate
  {
    NavigationMenuViewAccessibilityDelegate(RecyclerView paramRecyclerView)
    {
      super();
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramAccessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.adapter.getRowCount(), 0, false));
    }
  }
  
  private static class NormalViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public NormalViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, View.OnClickListener paramOnClickListener)
    {
      super();
      this.itemView.setOnClickListener(paramOnClickListener);
    }
  }
  
  private static class SeparatorViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public SeparatorViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static class SubheaderViewHolder
    extends NavigationMenuPresenter.ViewHolder
  {
    public SubheaderViewHolder(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      super();
    }
  }
  
  private static abstract class ViewHolder
    extends RecyclerView.ViewHolder
  {
    public ViewHolder(View paramView)
    {
      super();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/NavigationMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */