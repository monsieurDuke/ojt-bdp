package androidx.lifecycle;

public abstract interface DefaultLifecycleObserver
  extends FullLifecycleObserver
{
  public void onCreate(LifecycleOwner paramLifecycleOwner) {}
  
  public void onDestroy(LifecycleOwner paramLifecycleOwner) {}
  
  public void onPause(LifecycleOwner paramLifecycleOwner) {}
  
  public void onResume(LifecycleOwner paramLifecycleOwner) {}
  
  public void onStart(LifecycleOwner paramLifecycleOwner) {}
  
  public void onStop(LifecycleOwner paramLifecycleOwner) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/DefaultLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */