package androidx.activity.contextaware;

import android.content.Context;

public abstract interface ContextAware
{
  public abstract void addOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener);
  
  public abstract Context peekAvailableContext();
  
  public abstract void removeOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/contextaware/ContextAware.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */