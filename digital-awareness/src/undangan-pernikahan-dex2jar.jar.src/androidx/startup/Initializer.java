package androidx.startup;

import android.content.Context;
import java.util.List;

public abstract interface Initializer<T>
{
  public abstract T create(Context paramContext);
  
  public abstract List<Class<? extends Initializer<?>>> dependencies();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/startup/Initializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */