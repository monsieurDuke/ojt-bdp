package androidx.work.impl.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class WakeLocks
{
  private static final String TAG;
  private static final WeakHashMap<PowerManager.WakeLock, String> sWakeLocks = new WeakHashMap();
  
  static
  {
    String str = Logger.tagWithPrefix("WakeLocks");
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    TAG = str;
  }
  
  public static void checkWakeLocks()
  {
    HashMap localHashMap = new HashMap();
    synchronized (sWakeLocks)
    {
      localHashMap.putAll((Map)???);
      ??? = localHashMap.keySet().iterator();
      while (((Iterator)???).hasNext())
      {
        Object localObject3 = (PowerManager.WakeLock)((Iterator)???).next();
        if ((localObject3 != null) && (((PowerManager.WakeLock)localObject3).isHeld()))
        {
          localObject3 = String.format("WakeLock held for %s", new Object[] { localHashMap.get(localObject3) });
          Log5ECF72.a(localObject3);
          LogE84000.a(localObject3);
          Log229316.a(localObject3);
          Logger.get().warning(TAG, (String)localObject3, new Throwable[0]);
        }
      }
      return;
    }
  }
  
  public static PowerManager.WakeLock newWakeLock(Context paramContext, String arg1)
  {
    Object localObject = (PowerManager)paramContext.getApplicationContext().getSystemService("power");
    paramContext = "WorkManager: " + ???;
    localObject = ((PowerManager)localObject).newWakeLock(1, paramContext);
    synchronized (sWakeLocks)
    {
      ???.put(localObject, paramContext);
      return (PowerManager.WakeLock)localObject;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/WakeLocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */