package androidx.core.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

public final class MarginLayoutParamsCompat
{
  public static int getLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    int i;
    if (Build.VERSION.SDK_INT >= 17) {
      i = Api17Impl.getLayoutDirection(paramMarginLayoutParams);
    } else {
      i = 0;
    }
    int j = i;
    if (i != 0)
    {
      j = i;
      if (i != 1) {
        j = 0;
      }
    }
    return j;
  }
  
  public static int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getMarginEnd(paramMarginLayoutParams);
    }
    return paramMarginLayoutParams.rightMargin;
  }
  
  public static int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getMarginStart(paramMarginLayoutParams);
    }
    return paramMarginLayoutParams.leftMargin;
  }
  
  public static boolean isMarginRelative(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.isMarginRelative(paramMarginLayoutParams);
    }
    return false;
  }
  
  public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.resolveLayoutDirection(paramMarginLayoutParams, paramInt);
    }
  }
  
  public static void setLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setLayoutDirection(paramMarginLayoutParams, paramInt);
    }
  }
  
  public static void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setMarginEnd(paramMarginLayoutParams, paramInt);
    } else {
      paramMarginLayoutParams.rightMargin = paramInt;
    }
  }
  
  public static void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setMarginStart(paramMarginLayoutParams, paramInt);
    } else {
      paramMarginLayoutParams.leftMargin = paramInt;
    }
  }
  
  static class Api17Impl
  {
    static int getLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.getLayoutDirection();
    }
    
    static int getMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.getMarginEnd();
    }
    
    static int getMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.getMarginStart();
    }
    
    static boolean isMarginRelative(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      return paramMarginLayoutParams.isMarginRelative();
    }
    
    static void resolveLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.resolveLayoutDirection(paramInt);
    }
    
    static void setLayoutDirection(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.setLayoutDirection(paramInt);
    }
    
    static void setMarginEnd(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.setMarginEnd(paramInt);
    }
    
    static void setMarginStart(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
    {
      paramMarginLayoutParams.setMarginStart(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/MarginLayoutParamsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */