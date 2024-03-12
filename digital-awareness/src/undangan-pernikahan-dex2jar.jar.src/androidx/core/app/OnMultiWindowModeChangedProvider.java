package androidx.core.app;

import androidx.core.util.Consumer;

public abstract interface OnMultiWindowModeChangedProvider
{
  public abstract void addOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> paramConsumer);
  
  public abstract void removeOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> paramConsumer);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/OnMultiWindowModeChangedProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */