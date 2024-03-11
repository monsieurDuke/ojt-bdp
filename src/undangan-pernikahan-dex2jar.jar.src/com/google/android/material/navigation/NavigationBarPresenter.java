package com.google.android.material.navigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ParcelableSparseArray;

public class NavigationBarPresenter
  implements MenuPresenter
{
  private int id;
  private MenuBuilder menu;
  private NavigationBarMenuView menuView;
  private boolean updateSuspended = false;
  
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
  
  public int getId()
  {
    return this.id;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    return this.menuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    this.menu = paramMenuBuilder;
    this.menuView.initialize(paramMenuBuilder);
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      this.menuView.tryRestoreSelectedItemId(((SavedState)paramParcelable).selectedItemId);
      paramParcelable = BadgeUtils.createBadgeDrawablesFromSavedStates(this.menuView.getContext(), ((SavedState)paramParcelable).badgeSavedStates);
      this.menuView.restoreBadgeDrawables(paramParcelable);
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState();
    localSavedState.selectedItemId = this.menuView.getSelectedItemId();
    localSavedState.badgeSavedStates = BadgeUtils.createParcelableBadgeStates(this.menuView.getBadgeDrawables());
    return localSavedState;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    return false;
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {}
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setMenuView(NavigationBarMenuView paramNavigationBarMenuView)
  {
    this.menuView = paramNavigationBarMenuView;
  }
  
  public void setUpdateSuspended(boolean paramBoolean)
  {
    this.updateSuspended = paramBoolean;
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    if (this.updateSuspended) {
      return;
    }
    if (paramBoolean) {
      this.menuView.buildMenuView();
    } else {
      this.menuView.updateMenuView();
    }
  }
  
  static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public NavigationBarPresenter.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NavigationBarPresenter.SavedState(paramAnonymousParcel);
      }
      
      public NavigationBarPresenter.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NavigationBarPresenter.SavedState[paramAnonymousInt];
      }
    };
    ParcelableSparseArray badgeSavedStates;
    int selectedItemId;
    
    SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      this.selectedItemId = paramParcel.readInt();
      this.badgeSavedStates = ((ParcelableSparseArray)paramParcel.readParcelable(getClass().getClassLoader()));
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.selectedItemId);
      paramParcel.writeParcelable(this.badgeSavedStates, 0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/navigation/NavigationBarPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */