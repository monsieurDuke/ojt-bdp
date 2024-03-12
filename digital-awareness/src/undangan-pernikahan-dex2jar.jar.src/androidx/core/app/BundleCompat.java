package androidx.core.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BundleCompat
{
  public static IBinder getBinder(Bundle paramBundle, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.getBinder(paramBundle, paramString);
    }
    return BeforeApi18Impl.getBinder(paramBundle, paramString);
  }
  
  public static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      Api18Impl.putBinder(paramBundle, paramString, paramIBinder);
    } else {
      BeforeApi18Impl.putBinder(paramBundle, paramString, paramIBinder);
    }
  }
  
  static class Api18Impl
  {
    static IBinder getBinder(Bundle paramBundle, String paramString)
    {
      return paramBundle.getBinder(paramString);
    }
    
    static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder)
    {
      paramBundle.putBinder(paramString, paramIBinder);
    }
  }
  
  static class BeforeApi18Impl
  {
    private static final String TAG = "BundleCompatBaseImpl";
    private static Method sGetIBinderMethod;
    private static boolean sGetIBinderMethodFetched;
    private static Method sPutIBinderMethod;
    private static boolean sPutIBinderMethodFetched;
    
    public static IBinder getBinder(Bundle paramBundle, String paramString)
    {
      if (!sGetIBinderMethodFetched)
      {
        try
        {
          Method localMethod1 = Bundle.class.getMethod("getIBinder", new Class[] { String.class });
          sGetIBinderMethod = localMethod1;
          localMethod1.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("BundleCompatBaseImpl", "Failed to retrieve getIBinder method", localNoSuchMethodException);
        }
        sGetIBinderMethodFetched = true;
      }
      Method localMethod2 = sGetIBinderMethod;
      if (localMethod2 != null)
      {
        try
        {
          paramBundle = (IBinder)localMethod2.invoke(paramBundle, new Object[] { paramString });
          return paramBundle;
        }
        catch (IllegalArgumentException paramBundle) {}catch (IllegalAccessException paramBundle) {}catch (InvocationTargetException paramBundle) {}
        Log.i("BundleCompatBaseImpl", "Failed to invoke getIBinder via reflection", paramBundle);
        sGetIBinderMethod = null;
      }
      return null;
    }
    
    public static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder)
    {
      if (!sPutIBinderMethodFetched)
      {
        try
        {
          Method localMethod1 = Bundle.class.getMethod("putIBinder", new Class[] { String.class, IBinder.class });
          sPutIBinderMethod = localMethod1;
          localMethod1.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("BundleCompatBaseImpl", "Failed to retrieve putIBinder method", localNoSuchMethodException);
        }
        sPutIBinderMethodFetched = true;
      }
      Method localMethod2 = sPutIBinderMethod;
      if (localMethod2 != null)
      {
        try
        {
          localMethod2.invoke(paramBundle, new Object[] { paramString, paramIBinder });
        }
        catch (IllegalArgumentException paramBundle) {}catch (IllegalAccessException paramBundle) {}catch (InvocationTargetException paramBundle) {}
        Log.i("BundleCompatBaseImpl", "Failed to invoke putIBinder via reflection", paramBundle);
        sPutIBinderMethod = null;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/BundleCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */