package com.google.android.material.resources;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.util.TypedValue;
import android.view.View;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MaterialAttributes
{
  public static TypedValue resolve(Context paramContext, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    if (paramContext.getTheme().resolveAttribute(paramInt, localTypedValue, true)) {
      return localTypedValue;
    }
    return null;
  }
  
  public static boolean resolveBoolean(Context paramContext, int paramInt, boolean paramBoolean)
  {
    paramContext = resolve(paramContext, paramInt);
    if ((paramContext != null) && (paramContext.type == 18)) {
      if (paramContext.data != 0) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
    }
    return paramBoolean;
  }
  
  public static boolean resolveBooleanOrThrow(Context paramContext, int paramInt, String paramString)
  {
    boolean bool;
    if (resolveOrThrow(paramContext, paramInt, paramString) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static int resolveDimension(Context paramContext, int paramInt1, int paramInt2)
  {
    TypedValue localTypedValue = resolve(paramContext, paramInt1);
    if ((localTypedValue != null) && (localTypedValue.type == 5)) {
      return (int)localTypedValue.getDimension(paramContext.getResources().getDisplayMetrics());
    }
    return (int)paramContext.getResources().getDimension(paramInt2);
  }
  
  public static int resolveInteger(Context paramContext, int paramInt1, int paramInt2)
  {
    paramContext = resolve(paramContext, paramInt1);
    if ((paramContext != null) && (paramContext.type == 16)) {
      paramInt2 = paramContext.data;
    }
    return paramInt2;
  }
  
  public static int resolveMinimumAccessibleTouchTarget(Context paramContext)
  {
    return resolveDimension(paramContext, R.attr.minTouchTargetSize, R.dimen.mtrl_min_touch_target_size);
  }
  
  public static int resolveOrThrow(Context paramContext, int paramInt, String paramString)
  {
    TypedValue localTypedValue = resolve(paramContext, paramInt);
    if (localTypedValue != null) {
      return localTypedValue.data;
    }
    paramContext = String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", new Object[] { paramString, paramContext.getResources().getResourceName(paramInt) });
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    throw new IllegalArgumentException(paramContext);
  }
  
  public static int resolveOrThrow(View paramView, int paramInt)
  {
    return resolveOrThrow(paramView.getContext(), paramInt, paramView.getClass().getCanonicalName());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/resources/MaterialAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */