package androidx.core.telephony;

import android.os.Build.VERSION;
import android.telephony.SubscriptionManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SubscriptionManagerCompat
{
  private static Method sGetSlotIndexMethod;
  
  public static int getSlotIndex(int paramInt)
  {
    if (paramInt == -1) {
      return -1;
    }
    if (Build.VERSION.SDK_INT >= 29) {
      return Api29Impl.getSlotIndex(paramInt);
    }
    try
    {
      if (sGetSlotIndexMethod == null)
      {
        if (Build.VERSION.SDK_INT >= 26) {
          sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotIndex", new Class[] { Integer.TYPE });
        } else {
          sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotId", new Class[] { Integer.TYPE });
        }
        sGetSlotIndexMethod.setAccessible(true);
      }
      Integer localInteger = (Integer)sGetSlotIndexMethod.invoke(null, new Object[] { Integer.valueOf(paramInt) });
      if (localInteger != null)
      {
        paramInt = localInteger.intValue();
        return paramInt;
      }
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (IllegalAccessException localIllegalAccessException) {}catch (NoSuchMethodException localNoSuchMethodException) {}
    return -1;
  }
  
  private static class Api29Impl
  {
    static int getSlotIndex(int paramInt)
    {
      return SubscriptionManager.getSlotIndex(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/telephony/SubscriptionManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */