package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

public abstract class AbstractSavedStateViewModelFactory
  extends ViewModelProvider.OnRequeryFactory
  implements ViewModelProvider.Factory
{
  static final String TAG_SAVED_STATE_HANDLE_CONTROLLER = "androidx.lifecycle.savedstate.vm.tag";
  private Bundle mDefaultArgs;
  private Lifecycle mLifecycle;
  private SavedStateRegistry mSavedStateRegistry;
  
  public AbstractSavedStateViewModelFactory() {}
  
  public AbstractSavedStateViewModelFactory(SavedStateRegistryOwner paramSavedStateRegistryOwner, Bundle paramBundle)
  {
    this.mSavedStateRegistry = paramSavedStateRegistryOwner.getSavedStateRegistry();
    this.mLifecycle = paramSavedStateRegistryOwner.getLifecycle();
    this.mDefaultArgs = paramBundle;
  }
  
  private <T extends ViewModel> T create(String paramString, Class<T> paramClass)
  {
    SavedStateHandleController localSavedStateHandleController = LegacySavedStateHandleController.create(this.mSavedStateRegistry, this.mLifecycle, paramString, this.mDefaultArgs);
    paramString = create(paramString, paramClass, localSavedStateHandleController.getHandle());
    paramString.setTagIfAbsent("androidx.lifecycle.savedstate.vm.tag", localSavedStateHandleController);
    return paramString;
  }
  
  public final <T extends ViewModel> T create(Class<T> paramClass)
  {
    String str = paramClass.getCanonicalName();
    if (str != null)
    {
      if (this.mLifecycle != null) {
        return create(str, paramClass);
      }
      throw new UnsupportedOperationException("AbstractSavedStateViewModelFactory constructed with empty constructor supports only calls to create(modelClass: Class<T>, extras: CreationExtras).");
    }
    throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
  }
  
  public final <T extends ViewModel> T create(Class<T> paramClass, CreationExtras paramCreationExtras)
  {
    String str = (String)paramCreationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
    if (str != null)
    {
      if (this.mSavedStateRegistry != null) {
        return create(str, paramClass);
      }
      return create(str, paramClass, SavedStateHandleSupport.createSavedStateHandle(paramCreationExtras));
    }
    throw new IllegalStateException("VIEW_MODEL_KEY must always be provided by ViewModelProvider");
  }
  
  protected abstract <T extends ViewModel> T create(String paramString, Class<T> paramClass, SavedStateHandle paramSavedStateHandle);
  
  public void onRequery(ViewModel paramViewModel)
  {
    SavedStateRegistry localSavedStateRegistry = this.mSavedStateRegistry;
    if (localSavedStateRegistry != null) {
      LegacySavedStateHandleController.attachHandleIfNeeded(paramViewModel, localSavedStateRegistry, this.mLifecycle);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/AbstractSavedStateViewModelFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */