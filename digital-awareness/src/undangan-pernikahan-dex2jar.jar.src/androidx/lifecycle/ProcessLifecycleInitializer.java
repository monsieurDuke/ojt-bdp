package androidx.lifecycle;

import android.content.Context;
import androidx.startup.AppInitializer;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;

public final class ProcessLifecycleInitializer
  implements Initializer<LifecycleOwner>
{
  public LifecycleOwner create(Context paramContext)
  {
    if (AppInitializer.getInstance(paramContext).isEagerlyInitialized(getClass()))
    {
      LifecycleDispatcher.init(paramContext);
      ProcessLifecycleOwner.init(paramContext);
      return ProcessLifecycleOwner.get();
    }
    throw new IllegalStateException("ProcessLifecycleInitializer cannot be initialized lazily. \nPlease ensure that you have: \n<meta-data\n    android:name='androidx.lifecycle.ProcessLifecycleInitializer' \n    android:value='androidx.startup' /> \nunder InitializationProvider in your AndroidManifest.xml");
  }
  
  public List<Class<? extends Initializer<?>>> dependencies()
  {
    return Collections.emptyList();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ProcessLifecycleInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */