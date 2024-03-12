package androidx.core.content;

import androidx.core.util.Consumer;

public abstract interface OnTrimMemoryProvider
{
  public abstract void addOnTrimMemoryListener(Consumer<Integer> paramConsumer);
  
  public abstract void removeOnTrimMemoryListener(Consumer<Integer> paramConsumer);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/OnTrimMemoryProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */