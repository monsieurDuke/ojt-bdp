package androidx.lifecycle;

import androidx.savedstate.SavedStateRegistry;

final class SavedStateHandleController
  implements LifecycleEventObserver
{
  private final SavedStateHandle mHandle;
  private boolean mIsAttached = false;
  private final String mKey;
  
  SavedStateHandleController(String paramString, SavedStateHandle paramSavedStateHandle)
  {
    this.mKey = paramString;
    this.mHandle = paramSavedStateHandle;
  }
  
  void attachToLifecycle(SavedStateRegistry paramSavedStateRegistry, Lifecycle paramLifecycle)
  {
    if (!this.mIsAttached)
    {
      this.mIsAttached = true;
      paramLifecycle.addObserver(this);
      paramSavedStateRegistry.registerSavedStateProvider(this.mKey, this.mHandle.savedStateProvider());
      return;
    }
    throw new IllegalStateException("Already attached to lifecycleOwner");
  }
  
  SavedStateHandle getHandle()
  {
    return this.mHandle;
  }
  
  boolean isAttached()
  {
    return this.mIsAttached;
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
  {
    if (paramEvent == Lifecycle.Event.ON_DESTROY)
    {
      this.mIsAttached = false;
      paramLifecycleOwner.getLifecycle().removeObserver(this);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/SavedStateHandleController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */