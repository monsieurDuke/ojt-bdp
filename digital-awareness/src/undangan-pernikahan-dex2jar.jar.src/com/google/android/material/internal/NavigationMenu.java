package com.google.android.material.internal;

import android.content.Context;
import android.view.SubMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

public class NavigationMenu
  extends MenuBuilder
{
  public NavigationMenu(Context paramContext)
  {
    super(paramContext);
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    paramCharSequence = (MenuItemImpl)addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    NavigationSubMenu localNavigationSubMenu = new NavigationSubMenu(getContext(), this, paramCharSequence);
    paramCharSequence.setSubMenu(localNavigationSubMenu);
    return localNavigationSubMenu;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/NavigationMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */