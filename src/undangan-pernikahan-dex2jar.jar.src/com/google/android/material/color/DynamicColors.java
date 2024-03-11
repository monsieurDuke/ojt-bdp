package com.google.android.material.color;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import com.google.android.material.R.attr;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DynamicColors
{
  private static final DeviceSupportCondition DEFAULT_DEVICE_SUPPORT_CONDITION;
  private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_BRANDS;
  private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS;
  private static final int[] DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE = { R.attr.dynamicColorThemeOverlay };
  private static final DeviceSupportCondition SAMSUNG_DEVICE_SUPPORT_CONDITION;
  private static final int USE_DEFAULT_THEME_OVERLAY = 0;
  
  static
  {
    DeviceSupportCondition local1 = new DeviceSupportCondition()
    {
      public boolean isSupported()
      {
        return true;
      }
    };
    DEFAULT_DEVICE_SUPPORT_CONDITION = local1;
    Object localObject = new DeviceSupportCondition()
    {
      private Long version;
      
      public boolean isSupported()
      {
        Object localObject = this.version;
        boolean bool = false;
        if (localObject == null) {
          try
          {
            localObject = Build.class.getDeclaredMethod("getLong", new Class[] { String.class });
            ((Method)localObject).setAccessible(true);
            this.version = Long.valueOf(((Long)((Method)localObject).invoke(null, new Object[] { "ro.build.version.oneui" })).longValue());
          }
          catch (Exception localException)
          {
            this.version = Long.valueOf(-1L);
          }
        }
        if (this.version.longValue() >= 40100L) {
          bool = true;
        }
        return bool;
      }
    };
    SAMSUNG_DEVICE_SUPPORT_CONDITION = (DeviceSupportCondition)localObject;
    HashMap localHashMap = new HashMap();
    localHashMap.put("google", local1);
    localHashMap.put("hmd global", local1);
    localHashMap.put("infinix", local1);
    localHashMap.put("infinix mobility limited", local1);
    localHashMap.put("itel", local1);
    localHashMap.put("kyocera", local1);
    localHashMap.put("lenovo", local1);
    localHashMap.put("lge", local1);
    localHashMap.put("motorola", local1);
    localHashMap.put("nothing", local1);
    localHashMap.put("oneplus", local1);
    localHashMap.put("oppo", local1);
    localHashMap.put("realme", local1);
    localHashMap.put("robolectric", local1);
    localHashMap.put("samsung", localObject);
    localHashMap.put("sharp", local1);
    localHashMap.put("sony", local1);
    localHashMap.put("tcl", local1);
    localHashMap.put("tecno", local1);
    localHashMap.put("tecno mobile limited", local1);
    localHashMap.put("vivo", local1);
    localHashMap.put("xiaomi", local1);
    DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(localHashMap);
    localObject = new HashMap();
    ((Map)localObject).put("asus", local1);
    ((Map)localObject).put("jio", local1);
    DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap((Map)localObject);
  }
  
  @Deprecated
  public static void applyIfAvailable(Activity paramActivity)
  {
    applyToActivityIfAvailable(paramActivity);
  }
  
  @Deprecated
  public static void applyIfAvailable(Activity paramActivity, int paramInt)
  {
    applyToActivityIfAvailable(paramActivity, new DynamicColorsOptions.Builder().setThemeOverlay(paramInt).build());
  }
  
  @Deprecated
  public static void applyIfAvailable(Activity paramActivity, Precondition paramPrecondition)
  {
    applyToActivityIfAvailable(paramActivity, new DynamicColorsOptions.Builder().setPrecondition(paramPrecondition).build());
  }
  
  public static void applyToActivitiesIfAvailable(Application paramApplication)
  {
    applyToActivitiesIfAvailable(paramApplication, new DynamicColorsOptions.Builder().build());
  }
  
  @Deprecated
  public static void applyToActivitiesIfAvailable(Application paramApplication, int paramInt)
  {
    applyToActivitiesIfAvailable(paramApplication, new DynamicColorsOptions.Builder().setThemeOverlay(paramInt).build());
  }
  
  @Deprecated
  public static void applyToActivitiesIfAvailable(Application paramApplication, int paramInt, Precondition paramPrecondition)
  {
    applyToActivitiesIfAvailable(paramApplication, new DynamicColorsOptions.Builder().setThemeOverlay(paramInt).setPrecondition(paramPrecondition).build());
  }
  
  @Deprecated
  public static void applyToActivitiesIfAvailable(Application paramApplication, Precondition paramPrecondition)
  {
    applyToActivitiesIfAvailable(paramApplication, new DynamicColorsOptions.Builder().setPrecondition(paramPrecondition).build());
  }
  
  public static void applyToActivitiesIfAvailable(Application paramApplication, DynamicColorsOptions paramDynamicColorsOptions)
  {
    paramApplication.registerActivityLifecycleCallbacks(new DynamicColorsActivityLifecycleCallbacks(paramDynamicColorsOptions));
  }
  
  public static void applyToActivityIfAvailable(Activity paramActivity)
  {
    applyToActivityIfAvailable(paramActivity, new DynamicColorsOptions.Builder().build());
  }
  
  private static void applyToActivityIfAvailable(Activity paramActivity, int paramInt, Precondition paramPrecondition, OnAppliedCallback paramOnAppliedCallback)
  {
    if (!isDynamicColorAvailable()) {
      return;
    }
    int i = paramInt;
    if (paramInt == 0) {
      i = getDefaultThemeOverlay(paramActivity);
    }
    if ((i != 0) && (paramPrecondition.shouldApplyDynamicColors(paramActivity, i)))
    {
      ThemeUtils.applyThemeOverlay(paramActivity, i);
      paramOnAppliedCallback.onApplied(paramActivity);
    }
  }
  
  public static void applyToActivityIfAvailable(Activity paramActivity, DynamicColorsOptions paramDynamicColorsOptions)
  {
    applyToActivityIfAvailable(paramActivity, paramDynamicColorsOptions.getThemeOverlay(), paramDynamicColorsOptions.getPrecondition(), paramDynamicColorsOptions.getOnAppliedCallback());
  }
  
  private static int getDefaultThemeOverlay(Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE);
    int i = paramContext.getResourceId(0, 0);
    paramContext.recycle();
    return i;
  }
  
  public static boolean isDynamicColorAvailable()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i < 31) {
      return false;
    }
    DeviceSupportCondition localDeviceSupportCondition2 = (DeviceSupportCondition)DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS.get(Build.MANUFACTURER.toLowerCase());
    DeviceSupportCondition localDeviceSupportCondition1 = localDeviceSupportCondition2;
    if (localDeviceSupportCondition2 == null) {
      localDeviceSupportCondition1 = (DeviceSupportCondition)DYNAMIC_COLOR_SUPPORTED_BRANDS.get(Build.BRAND.toLowerCase());
    }
    boolean bool1 = bool2;
    if (localDeviceSupportCondition1 != null)
    {
      bool1 = bool2;
      if (localDeviceSupportCondition1.isSupported()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static Context wrapContextIfAvailable(Context paramContext)
  {
    return wrapContextIfAvailable(paramContext, 0);
  }
  
  public static Context wrapContextIfAvailable(Context paramContext, int paramInt)
  {
    if (!isDynamicColorAvailable()) {
      return paramContext;
    }
    int i = paramInt;
    if (paramInt == 0) {
      i = getDefaultThemeOverlay(paramContext);
    }
    if (i != 0) {
      paramContext = new ContextThemeWrapper(paramContext, i);
    }
    return paramContext;
  }
  
  private static abstract interface DeviceSupportCondition
  {
    public abstract boolean isSupported();
  }
  
  private static class DynamicColorsActivityLifecycleCallbacks
    implements Application.ActivityLifecycleCallbacks
  {
    private final DynamicColorsOptions dynamicColorsOptions;
    
    DynamicColorsActivityLifecycleCallbacks(DynamicColorsOptions paramDynamicColorsOptions)
    {
      this.dynamicColorsOptions = paramDynamicColorsOptions;
    }
    
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityDestroyed(Activity paramActivity) {}
    
    public void onActivityPaused(Activity paramActivity) {}
    
    public void onActivityPreCreated(Activity paramActivity, Bundle paramBundle)
    {
      DynamicColors.applyToActivityIfAvailable(paramActivity, this.dynamicColorsOptions.getThemeOverlay(), this.dynamicColorsOptions.getPrecondition(), this.dynamicColorsOptions.getOnAppliedCallback());
    }
    
    public void onActivityResumed(Activity paramActivity) {}
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity) {}
    
    public void onActivityStopped(Activity paramActivity) {}
  }
  
  public static abstract interface OnAppliedCallback
  {
    public abstract void onApplied(Activity paramActivity);
  }
  
  public static abstract interface Precondition
  {
    public abstract boolean shouldApplyDynamicColors(Activity paramActivity, int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/DynamicColors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */