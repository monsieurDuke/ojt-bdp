package androidx.core.content;

import android.content.res.Configuration;
import androidx.core.util.Consumer;

public abstract interface OnConfigurationChangedProvider
{
  public abstract void addOnConfigurationChangedListener(Consumer<Configuration> paramConsumer);
  
  public abstract void removeOnConfigurationChangedListener(Consumer<Configuration> paramConsumer);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/OnConfigurationChangedProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */