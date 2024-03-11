package androidx.work;

import android.content.Context;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DelegatingWorkerFactory
  extends WorkerFactory
{
  private static final String TAG;
  private final List<WorkerFactory> mFactories = new CopyOnWriteArrayList();
  
  static
  {
    String str = Logger.tagWithPrefix("DelegatingWkrFctry");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public final void addFactory(WorkerFactory paramWorkerFactory)
  {
    this.mFactories.add(paramWorkerFactory);
  }
  
  public final ListenableWorker createWorker(Context paramContext, String paramString, WorkerParameters paramWorkerParameters)
  {
    Iterator localIterator = this.mFactories.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (WorkerFactory)localIterator.next();
      try
      {
        return (ListenableWorker)localObject;
      }
      finally
      {
        paramString = String.format("Unable to instantiate a ListenableWorker (%s)", new Object[] { paramString });
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        Logger.get().error(TAG, paramString, new Throwable[] { paramContext });
      }
    }
    return null;
  }
  
  List<WorkerFactory> getFactories()
  {
    return this.mFactories;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/DelegatingWorkerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */