package androidx.core.telephony;

import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class TelephonyManagerCompat
{
  private static Method sGetDeviceIdMethod;
  private static Method sGetSubIdMethod;
  
  public static String getImei(TelephonyManager paramTelephonyManager)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      paramTelephonyManager = Api26Impl.getImei(paramTelephonyManager);
      Log5ECF72.a(paramTelephonyManager);
      LogE84000.a(paramTelephonyManager);
      Log229316.a(paramTelephonyManager);
      return paramTelephonyManager;
    }
    if (Build.VERSION.SDK_INT >= 22)
    {
      int i = getSubscriptionId(paramTelephonyManager);
      if ((i != Integer.MAX_VALUE) && (i != -1))
      {
        i = SubscriptionManagerCompat.getSlotIndex(i);
        if (Build.VERSION.SDK_INT >= 23)
        {
          paramTelephonyManager = Api23Impl.getDeviceId(paramTelephonyManager, i);
          Log5ECF72.a(paramTelephonyManager);
          LogE84000.a(paramTelephonyManager);
          Log229316.a(paramTelephonyManager);
          return paramTelephonyManager;
        }
        try
        {
          if (sGetDeviceIdMethod == null)
          {
            Method localMethod = TelephonyManager.class.getDeclaredMethod("getDeviceId", new Class[] { Integer.TYPE });
            sGetDeviceIdMethod = localMethod;
            localMethod.setAccessible(true);
          }
          paramTelephonyManager = (String)sGetDeviceIdMethod.invoke(paramTelephonyManager, new Object[] { Integer.valueOf(i) });
          return paramTelephonyManager;
        }
        catch (InvocationTargetException paramTelephonyManager) {}catch (IllegalAccessException paramTelephonyManager) {}catch (NoSuchMethodException paramTelephonyManager) {}
        return null;
      }
    }
    return paramTelephonyManager.getDeviceId();
  }
  
  public static int getSubscriptionId(TelephonyManager paramTelephonyManager)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getSubscriptionId(paramTelephonyManager);
    }
    if (Build.VERSION.SDK_INT >= 22) {
      try
      {
        if (sGetSubIdMethod == null)
        {
          Method localMethod = TelephonyManager.class.getDeclaredMethod("getSubId", new Class[0]);
          sGetSubIdMethod = localMethod;
          localMethod.setAccessible(true);
        }
        paramTelephonyManager = (Integer)sGetSubIdMethod.invoke(paramTelephonyManager, new Object[0]);
        if ((paramTelephonyManager != null) && (paramTelephonyManager.intValue() != -1))
        {
          int i = paramTelephonyManager.intValue();
          return i;
        }
      }
      catch (NoSuchMethodException paramTelephonyManager) {}catch (IllegalAccessException paramTelephonyManager) {}catch (InvocationTargetException paramTelephonyManager) {}
    }
    return Integer.MAX_VALUE;
  }
  
  private static class Api23Impl
  {
    static String getDeviceId(TelephonyManager paramTelephonyManager, int paramInt)
    {
      return paramTelephonyManager.getDeviceId(paramInt);
    }
  }
  
  private static class Api26Impl
  {
    static String getImei(TelephonyManager paramTelephonyManager)
    {
      return paramTelephonyManager.getImei();
    }
  }
  
  private static class Api30Impl
  {
    static int getSubscriptionId(TelephonyManager paramTelephonyManager)
    {
      return paramTelephonyManager.getSubscriptionId();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/telephony/TelephonyManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */