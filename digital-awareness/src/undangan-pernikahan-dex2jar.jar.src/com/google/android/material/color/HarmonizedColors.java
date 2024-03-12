package com.google.android.material.color;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.loader.ResourcesLoader;
import android.os.Build.VERSION;
import android.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R.style;
import java.util.HashMap;
import java.util.Map;

public class HarmonizedColors
{
  private static final String TAG = HarmonizedColors.class.getSimpleName();
  
  private static void addHarmonizedColorAttributesToReplacementMap(Map<Integer, Integer> paramMap, TypedArray paramTypedArray1, TypedArray paramTypedArray2, int paramInt)
  {
    if (paramTypedArray2 == null) {
      paramTypedArray2 = paramTypedArray1;
    }
    for (int i = 0; i < paramTypedArray1.getIndexCount(); i++)
    {
      int k = paramTypedArray2.getResourceId(i, 0);
      if ((k != 0) && (paramTypedArray1.hasValue(i)) && (isColorResource(paramTypedArray1.getType(i))))
      {
        int j = paramTypedArray1.getColor(i, 0);
        paramMap.put(Integer.valueOf(k), Integer.valueOf(MaterialColors.harmonize(j, paramInt)));
      }
    }
  }
  
  private static boolean addResourcesLoaderToContext(Context paramContext, Map<Integer, Integer> paramMap)
  {
    paramMap = ColorResourcesLoaderCreator.create(paramContext, paramMap);
    if (paramMap != null)
    {
      paramContext.getResources().addLoaders(new ResourcesLoader[] { paramMap });
      return true;
    }
    return false;
  }
  
  public static void applyToContextIfAvailable(Context paramContext, HarmonizedColorsOptions paramHarmonizedColorsOptions)
  {
    if (!isHarmonizedColorAvailable()) {
      return;
    }
    Map localMap = createHarmonizedColorReplacementMap(paramContext, paramHarmonizedColorsOptions);
    int i = paramHarmonizedColorsOptions.getThemeOverlayResourceId(0);
    if ((addResourcesLoaderToContext(paramContext, localMap)) && (i != 0)) {
      ThemeUtils.applyThemeOverlay(paramContext, i);
    }
  }
  
  private static Map<Integer, Integer> createHarmonizedColorReplacementMap(Context paramContext, HarmonizedColorsOptions paramHarmonizedColorsOptions)
  {
    HashMap localHashMap = new HashMap();
    int j = MaterialColors.getColor(paramContext, paramHarmonizedColorsOptions.getColorAttributeToHarmonizeWith(), TAG);
    for (int m : paramHarmonizedColorsOptions.getColorResourceIds()) {
      localHashMap.put(Integer.valueOf(m), Integer.valueOf(MaterialColors.harmonize(ContextCompat.getColor(paramContext, m), j)));
    }
    paramHarmonizedColorsOptions = paramHarmonizedColorsOptions.getColorAttributes();
    if (paramHarmonizedColorsOptions != null)
    {
      ??? = paramHarmonizedColorsOptions.getAttributes();
      if (???.length > 0)
      {
        ??? = paramHarmonizedColorsOptions.getThemeOverlay();
        paramHarmonizedColorsOptions = paramContext.obtainStyledAttributes(???);
        if (??? != 0) {
          paramContext = new ContextThemeWrapper(paramContext, ???).obtainStyledAttributes(???);
        } else {
          paramContext = null;
        }
        addHarmonizedColorAttributesToReplacementMap(localHashMap, paramHarmonizedColorsOptions, paramContext, j);
        paramHarmonizedColorsOptions.recycle();
        if (paramContext != null) {
          paramContext.recycle();
        }
      }
    }
    return localHashMap;
  }
  
  private static boolean isColorResource(int paramInt)
  {
    boolean bool;
    if ((28 <= paramInt) && (paramInt <= 31)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isHarmonizedColorAvailable()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 30) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static Context wrapContextIfAvailable(Context paramContext, HarmonizedColorsOptions paramHarmonizedColorsOptions)
  {
    if (!isHarmonizedColorAvailable()) {
      return paramContext;
    }
    Map localMap = createHarmonizedColorReplacementMap(paramContext, paramHarmonizedColorsOptions);
    paramHarmonizedColorsOptions = new ContextThemeWrapper(paramContext, paramHarmonizedColorsOptions.getThemeOverlayResourceId(R.style.ThemeOverlay_Material3_HarmonizedColors_Empty));
    paramHarmonizedColorsOptions.applyOverrideConfiguration(new Configuration());
    if (addResourcesLoaderToContext(paramHarmonizedColorsOptions, localMap)) {
      paramContext = paramHarmonizedColorsOptions;
    }
    return paramContext;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/HarmonizedColors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */