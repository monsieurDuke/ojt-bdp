package androidx.activity.contextaware;

import android.content.Context;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ContextAwareHelper
{
  private volatile Context mContext;
  private final Set<OnContextAvailableListener> mListeners = new CopyOnWriteArraySet();
  
  public void addOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener)
  {
    if (this.mContext != null) {
      paramOnContextAvailableListener.onContextAvailable(this.mContext);
    }
    this.mListeners.add(paramOnContextAvailableListener);
  }
  
  public void clearAvailableContext()
  {
    this.mContext = null;
  }
  
  public void dispatchOnContextAvailable(Context paramContext)
  {
    this.mContext = paramContext;
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnContextAvailableListener)localIterator.next()).onContextAvailable(paramContext);
    }
  }
  
  public Context peekAvailableContext()
  {
    return this.mContext;
  }
  
  public void removeOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener)
  {
    this.mListeners.remove(paramOnContextAvailableListener);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/contextaware/ContextAwareHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */