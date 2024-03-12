package androidx.core.app;

import android.content.Intent;
import androidx.core.util.Consumer;

public abstract interface OnNewIntentProvider
{
  public abstract void addOnNewIntentListener(Consumer<Intent> paramConsumer);
  
  public abstract void removeOnNewIntentListener(Consumer<Intent> paramConsumer);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/OnNewIntentProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */