package androidx.activity;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class OnBackPressedCallback
{
  private CopyOnWriteArrayList<Cancellable> mCancellables = new CopyOnWriteArrayList();
  private boolean mEnabled;
  
  public OnBackPressedCallback(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
  }
  
  void addCancellable(Cancellable paramCancellable)
  {
    this.mCancellables.add(paramCancellable);
  }
  
  public abstract void handleOnBackPressed();
  
  public final boolean isEnabled()
  {
    return this.mEnabled;
  }
  
  public final void remove()
  {
    Iterator localIterator = this.mCancellables.iterator();
    while (localIterator.hasNext()) {
      ((Cancellable)localIterator.next()).cancel();
    }
  }
  
  void removeCancellable(Cancellable paramCancellable)
  {
    this.mCancellables.remove(paramCancellable);
  }
  
  public final void setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/OnBackPressedCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */