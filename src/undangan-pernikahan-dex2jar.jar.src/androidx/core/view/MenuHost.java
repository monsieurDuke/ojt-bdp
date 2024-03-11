package androidx.core.view;

import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;

public abstract interface MenuHost
{
  public abstract void addMenuProvider(MenuProvider paramMenuProvider);
  
  public abstract void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner);
  
  public abstract void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner, Lifecycle.State paramState);
  
  public abstract void invalidateMenu();
  
  public abstract void removeMenuProvider(MenuProvider paramMenuProvider);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/MenuHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */