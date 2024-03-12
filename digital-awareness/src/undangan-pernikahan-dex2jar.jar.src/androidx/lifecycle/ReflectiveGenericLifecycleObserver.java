package androidx.lifecycle;

@Deprecated
class ReflectiveGenericLifecycleObserver
  implements LifecycleEventObserver
{
  private final ClassesInfoCache.CallbackInfo mInfo;
  private final Object mWrapped;
  
  ReflectiveGenericLifecycleObserver(Object paramObject)
  {
    this.mWrapped = paramObject;
    this.mInfo = ClassesInfoCache.sInstance.getInfo(paramObject.getClass());
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
  {
    this.mInfo.invokeCallbacks(paramLifecycleOwner, paramEvent, this.mWrapped);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ReflectiveGenericLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */