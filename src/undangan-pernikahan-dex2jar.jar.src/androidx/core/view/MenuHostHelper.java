package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuHostHelper
{
  private final CopyOnWriteArrayList<MenuProvider> mMenuProviders = new CopyOnWriteArrayList();
  private final Runnable mOnInvalidateMenuCallback;
  private final Map<MenuProvider, LifecycleContainer> mProviderToLifecycleContainers = new HashMap();
  
  public MenuHostHelper(Runnable paramRunnable)
  {
    this.mOnInvalidateMenuCallback = paramRunnable;
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider)
  {
    this.mMenuProviders.add(paramMenuProvider);
    this.mOnInvalidateMenuCallback.run();
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner)
  {
    addMenuProvider(paramMenuProvider);
    paramLifecycleOwner = paramLifecycleOwner.getLifecycle();
    Object localObject = (LifecycleContainer)this.mProviderToLifecycleContainers.remove(paramMenuProvider);
    if (localObject != null) {
      ((LifecycleContainer)localObject).clearObservers();
    }
    localObject = new MenuHostHelper..ExternalSyntheticLambda0(this, paramMenuProvider);
    this.mProviderToLifecycleContainers.put(paramMenuProvider, new LifecycleContainer(paramLifecycleOwner, (LifecycleEventObserver)localObject));
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner, Lifecycle.State paramState)
  {
    paramLifecycleOwner = paramLifecycleOwner.getLifecycle();
    LifecycleContainer localLifecycleContainer = (LifecycleContainer)this.mProviderToLifecycleContainers.remove(paramMenuProvider);
    if (localLifecycleContainer != null) {
      localLifecycleContainer.clearObservers();
    }
    paramState = new MenuHostHelper..ExternalSyntheticLambda1(this, paramState, paramMenuProvider);
    this.mProviderToLifecycleContainers.put(paramMenuProvider, new LifecycleContainer(paramLifecycleOwner, paramState));
  }
  
  public void onCreateMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    Iterator localIterator = this.mMenuProviders.iterator();
    while (localIterator.hasNext()) {
      ((MenuProvider)localIterator.next()).onCreateMenu(paramMenu, paramMenuInflater);
    }
  }
  
  public void onMenuClosed(Menu paramMenu)
  {
    Iterator localIterator = this.mMenuProviders.iterator();
    while (localIterator.hasNext()) {
      ((MenuProvider)localIterator.next()).onMenuClosed(paramMenu);
    }
  }
  
  public boolean onMenuItemSelected(MenuItem paramMenuItem)
  {
    Iterator localIterator = this.mMenuProviders.iterator();
    while (localIterator.hasNext()) {
      if (((MenuProvider)localIterator.next()).onMenuItemSelected(paramMenuItem)) {
        return true;
      }
    }
    return false;
  }
  
  public void onPrepareMenu(Menu paramMenu)
  {
    Iterator localIterator = this.mMenuProviders.iterator();
    while (localIterator.hasNext()) {
      ((MenuProvider)localIterator.next()).onPrepareMenu(paramMenu);
    }
  }
  
  public void removeMenuProvider(MenuProvider paramMenuProvider)
  {
    this.mMenuProviders.remove(paramMenuProvider);
    paramMenuProvider = (LifecycleContainer)this.mProviderToLifecycleContainers.remove(paramMenuProvider);
    if (paramMenuProvider != null) {
      paramMenuProvider.clearObservers();
    }
    this.mOnInvalidateMenuCallback.run();
  }
  
  private static class LifecycleContainer
  {
    final Lifecycle mLifecycle;
    private LifecycleEventObserver mObserver;
    
    LifecycleContainer(Lifecycle paramLifecycle, LifecycleEventObserver paramLifecycleEventObserver)
    {
      this.mLifecycle = paramLifecycle;
      this.mObserver = paramLifecycleEventObserver;
      paramLifecycle.addObserver(paramLifecycleEventObserver);
    }
    
    void clearObservers()
    {
      this.mLifecycle.removeObserver(this.mObserver);
      this.mObserver = null;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/MenuHostHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */