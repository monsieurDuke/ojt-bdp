package androidx.core.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

public final class ViewConfigurationCompat
{
  private static final String TAG = "ViewConfigCompat";
  private static Method sGetScaledScrollFactorMethod;
  
  static
  {
    if (Build.VERSION.SDK_INT == 25) {
      try
      {
        sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
      }
      catch (Exception localException)
      {
        Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
      }
    }
  }
  
  private static float getLegacyScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      Method localMethod = sGetScaledScrollFactorMethod;
      if (localMethod != null) {
        try
        {
          int i = ((Integer)localMethod.invoke(paramViewConfiguration, new Object[0])).intValue();
          return i;
        }
        catch (Exception paramViewConfiguration)
        {
          Log.i("ViewConfigCompat", "Could not find method getScaledScrollFactor() on ViewConfiguration");
        }
      }
    }
    paramViewConfiguration = new TypedValue();
    if (paramContext.getTheme().resolveAttribute(16842829, paramViewConfiguration, true)) {
      return paramViewConfiguration.getDimension(paramContext.getResources().getDisplayMetrics());
    }
    return 0.0F;
  }
  
  public static float getScaledHorizontalScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getScaledHorizontalScrollFactor(paramViewConfiguration);
    }
    return getLegacyScrollFactor(paramViewConfiguration, paramContext);
  }
  
  public static int getScaledHoverSlop(ViewConfiguration paramViewConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getScaledHoverSlop(paramViewConfiguration);
    }
    return paramViewConfiguration.getScaledTouchSlop() / 2;
  }
  
  @Deprecated
  public static int getScaledPagingTouchSlop(ViewConfiguration paramViewConfiguration)
  {
    return paramViewConfiguration.getScaledPagingTouchSlop();
  }
  
  public static float getScaledVerticalScrollFactor(ViewConfiguration paramViewConfiguration, Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getScaledVerticalScrollFactor(paramViewConfiguration);
    }
    return getLegacyScrollFactor(paramViewConfiguration, paramContext);
  }
  
  @Deprecated
  public static boolean hasPermanentMenuKey(ViewConfiguration paramViewConfiguration)
  {
    return paramViewConfiguration.hasPermanentMenuKey();
  }
  
  public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration paramViewConfiguration, Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.shouldShowMenuShortcutsWhenKeyboardPresent(paramViewConfiguration);
    }
    paramViewConfiguration = paramContext.getResources();
    int i = paramViewConfiguration.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
    boolean bool;
    if ((i != 0) && (paramViewConfiguration.getBoolean(i))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class Api26Impl
  {
    static float getScaledHorizontalScrollFactor(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.getScaledHorizontalScrollFactor();
    }
    
    static float getScaledVerticalScrollFactor(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.getScaledVerticalScrollFactor();
    }
  }
  
  static class Api28Impl
  {
    static int getScaledHoverSlop(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.getScaledHoverSlop();
    }
    
    static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration paramViewConfiguration)
    {
      return paramViewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ViewConfigurationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */