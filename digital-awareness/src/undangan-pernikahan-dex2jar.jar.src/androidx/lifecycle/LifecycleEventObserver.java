package androidx.lifecycle;

public abstract interface LifecycleEventObserver
  extends LifecycleObserver
{
  public abstract void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/LifecycleEventObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */