package androidx.core.text;

import android.icu.util.ULocale;
import android.os.Build.VERSION;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ICUCompat
{
  private static final String TAG = "ICUCompat";
  private static Method sAddLikelySubtagsMethod;
  private static Method sGetScriptMethod;
  
  static
  {
    if (Build.VERSION.SDK_INT < 21) {
      try
      {
        Class localClass = Class.forName("libcore.icu.ICU");
        sGetScriptMethod = localClass.getMethod("getScript", new Class[] { String.class });
        sAddLikelySubtagsMethod = localClass.getMethod("addLikelySubtags", new Class[] { String.class });
      }
      catch (Exception localException1)
      {
        sGetScriptMethod = null;
        sAddLikelySubtagsMethod = null;
        Log.w("ICUCompat", localException1);
      }
    } else if (Build.VERSION.SDK_INT < 24) {
      try
      {
        sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[] { Locale.class });
      }
      catch (Exception localException2)
      {
        throw new IllegalStateException(localException2);
      }
    }
  }
  
  private static String addLikelySubtagsBelowApi21(Locale paramLocale)
  {
    paramLocale = paramLocale.toString();
    try
    {
      Object localObject = sAddLikelySubtagsMethod;
      if (localObject != null)
      {
        localObject = (String)((Method)localObject).invoke(null, new Object[] { paramLocale });
        return (String)localObject;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Log.w("ICUCompat", localInvocationTargetException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      Log.w("ICUCompat", localIllegalAccessException);
    }
    return paramLocale;
  }
  
  private static String getScriptBelowApi21(String paramString)
  {
    try
    {
      Method localMethod = sGetScriptMethod;
      if (localMethod != null)
      {
        paramString = (String)localMethod.invoke(null, new Object[] { paramString });
        return paramString;
      }
    }
    catch (InvocationTargetException paramString)
    {
      Log.w("ICUCompat", paramString);
    }
    catch (IllegalAccessException paramString)
    {
      Log.w("ICUCompat", paramString);
    }
    return null;
  }
  
  public static String maximizeAndGetScript(Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      paramLocale = Api24Impl.getScript(Api24Impl.addLikelySubtags(Api24Impl.forLocale(paramLocale)));
      Log5ECF72.a(paramLocale);
      LogE84000.a(paramLocale);
      Log229316.a(paramLocale);
      return paramLocale;
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      try
      {
        String str = Api21Impl.getScript((Locale)sAddLikelySubtagsMethod.invoke(null, new Object[] { paramLocale }));
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.w("ICUCompat", localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        Log.w("ICUCompat", localInvocationTargetException);
      }
      paramLocale = Api21Impl.getScript(paramLocale);
      Log5ECF72.a(paramLocale);
      LogE84000.a(paramLocale);
      Log229316.a(paramLocale);
      return paramLocale;
    }
    paramLocale = addLikelySubtagsBelowApi21(paramLocale);
    Log5ECF72.a(paramLocale);
    LogE84000.a(paramLocale);
    Log229316.a(paramLocale);
    if (paramLocale != null)
    {
      paramLocale = getScriptBelowApi21(paramLocale);
      Log5ECF72.a(paramLocale);
      LogE84000.a(paramLocale);
      Log229316.a(paramLocale);
      return paramLocale;
    }
    return null;
  }
  
  static class Api21Impl
  {
    static String getScript(Locale paramLocale)
    {
      return paramLocale.getScript();
    }
  }
  
  static class Api24Impl
  {
    static ULocale addLikelySubtags(Object paramObject)
    {
      return ULocale.addLikelySubtags((ULocale)paramObject);
    }
    
    static ULocale forLocale(Locale paramLocale)
    {
      return ULocale.forLocale(paramLocale);
    }
    
    static String getScript(Object paramObject)
    {
      return ((ULocale)paramObject).getScript();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/ICUCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */