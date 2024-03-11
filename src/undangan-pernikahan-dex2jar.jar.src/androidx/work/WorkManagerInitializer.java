package androidx.work;

import android.content.Context;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class WorkManagerInitializer
  implements Initializer<WorkManager>
{
  private static final String TAG;
  
  static
  {
    String str = Logger.tagWithPrefix("WrkMgrInitializer");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public WorkManager create(Context paramContext)
  {
    Logger.get().debug(TAG, "Initializing WorkManager with default configuration.", new Throwable[0]);
    WorkManager.initialize(paramContext, new Configuration.Builder().build());
    return WorkManager.getInstance(paramContext);
  }
  
  public List<Class<? extends Initializer<?>>> dependencies()
  {
    return Collections.emptyList();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/WorkManagerInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */