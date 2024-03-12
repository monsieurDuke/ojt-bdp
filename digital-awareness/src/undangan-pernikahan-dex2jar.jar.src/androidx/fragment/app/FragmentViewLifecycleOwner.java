package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

class FragmentViewLifecycleOwner
  implements HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ViewModelStoreOwner
{
  private ViewModelProvider.Factory mDefaultFactory;
  private final Fragment mFragment;
  private LifecycleRegistry mLifecycleRegistry = null;
  private SavedStateRegistryController mSavedStateRegistryController = null;
  private final ViewModelStore mViewModelStore;
  
  FragmentViewLifecycleOwner(Fragment paramFragment, ViewModelStore paramViewModelStore)
  {
    this.mFragment = paramFragment;
    this.mViewModelStore = paramViewModelStore;
  }
  
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory()
  {
    Object localObject1 = this.mFragment.getDefaultViewModelProviderFactory();
    if (!localObject1.equals(this.mFragment.mDefaultFactory))
    {
      this.mDefaultFactory = ((ViewModelProvider.Factory)localObject1);
      return (ViewModelProvider.Factory)localObject1;
    }
    if (this.mDefaultFactory == null)
    {
      Object localObject2 = null;
      for (Context localContext = this.mFragment.requireContext().getApplicationContext();; localContext = ((ContextWrapper)localContext).getBaseContext())
      {
        localObject1 = localObject2;
        if (!(localContext instanceof ContextWrapper)) {
          break;
        }
        if ((localContext instanceof Application))
        {
          localObject1 = (Application)localContext;
          break;
        }
      }
      this.mDefaultFactory = new SavedStateViewModelFactory((Application)localObject1, this, this.mFragment.getArguments());
    }
    return this.mDefaultFactory;
  }
  
  public Lifecycle getLifecycle()
  {
    initialize();
    return this.mLifecycleRegistry;
  }
  
  public SavedStateRegistry getSavedStateRegistry()
  {
    initialize();
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public ViewModelStore getViewModelStore()
  {
    initialize();
    return this.mViewModelStore;
  }
  
  void handleLifecycleEvent(Lifecycle.Event paramEvent)
  {
    this.mLifecycleRegistry.handleLifecycleEvent(paramEvent);
  }
  
  void initialize()
  {
    if (this.mLifecycleRegistry == null)
    {
      this.mLifecycleRegistry = new LifecycleRegistry(this);
      this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
    }
  }
  
  boolean isInitialized()
  {
    boolean bool;
    if (this.mLifecycleRegistry != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void performRestore(Bundle paramBundle)
  {
    this.mSavedStateRegistryController.performRestore(paramBundle);
  }
  
  void performSave(Bundle paramBundle)
  {
    this.mSavedStateRegistryController.performSave(paramBundle);
  }
  
  void setCurrentState(Lifecycle.State paramState)
  {
    this.mLifecycleRegistry.setCurrentState(paramState);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentViewLifecycleOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */