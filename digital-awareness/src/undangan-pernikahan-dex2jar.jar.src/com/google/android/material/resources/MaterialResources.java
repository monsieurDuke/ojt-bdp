package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R.styleable;

public class MaterialResources
{
  private static final float FONT_SCALE_1_3 = 1.3F;
  private static final float FONT_SCALE_2_0 = 2.0F;
  
  public static ColorStateList getColorStateList(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    int i;
    if (paramTypedArray.hasValue(paramInt))
    {
      i = paramTypedArray.getResourceId(paramInt, 0);
      if (i != 0)
      {
        paramContext = AppCompatResources.getColorStateList(paramContext, i);
        if (paramContext != null) {
          return paramContext;
        }
      }
    }
    if (Build.VERSION.SDK_INT <= 15)
    {
      i = paramTypedArray.getColor(paramInt, -1);
      if (i != -1) {
        return ColorStateList.valueOf(i);
      }
    }
    return paramTypedArray.getColorStateList(paramInt);
  }
  
  public static ColorStateList getColorStateList(Context paramContext, TintTypedArray paramTintTypedArray, int paramInt)
  {
    int i;
    if (paramTintTypedArray.hasValue(paramInt))
    {
      i = paramTintTypedArray.getResourceId(paramInt, 0);
      if (i != 0)
      {
        paramContext = AppCompatResources.getColorStateList(paramContext, i);
        if (paramContext != null) {
          return paramContext;
        }
      }
    }
    if (Build.VERSION.SDK_INT <= 15)
    {
      i = paramTintTypedArray.getColor(paramInt, -1);
      if (i != -1) {
        return ColorStateList.valueOf(i);
      }
    }
    return paramTintTypedArray.getColorStateList(paramInt);
  }
  
  private static int getComplexUnit(TypedValue paramTypedValue)
  {
    if (Build.VERSION.SDK_INT >= 22) {
      return paramTypedValue.getComplexUnit();
    }
    return paramTypedValue.data >> 0 & 0xF;
  }
  
  public static int getDimensionPixelSize(Context paramContext, TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    TypedValue localTypedValue = new TypedValue();
    if ((paramTypedArray.getValue(paramInt1, localTypedValue)) && (localTypedValue.type == 2))
    {
      paramContext = paramContext.getTheme().obtainStyledAttributes(new int[] { localTypedValue.data });
      paramInt1 = paramContext.getDimensionPixelSize(0, paramInt2);
      paramContext.recycle();
      return paramInt1;
    }
    return paramTypedArray.getDimensionPixelSize(paramInt1, paramInt2);
  }
  
  public static Drawable getDrawable(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    if (paramTypedArray.hasValue(paramInt))
    {
      int i = paramTypedArray.getResourceId(paramInt, 0);
      if (i != 0)
      {
        paramContext = AppCompatResources.getDrawable(paramContext, i);
        if (paramContext != null) {
          return paramContext;
        }
      }
    }
    return paramTypedArray.getDrawable(paramInt);
  }
  
  static int getIndexWithValue(TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    if (paramTypedArray.hasValue(paramInt1)) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  public static TextAppearance getTextAppearance(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    if (paramTypedArray.hasValue(paramInt))
    {
      paramInt = paramTypedArray.getResourceId(paramInt, 0);
      if (paramInt != 0) {
        return new TextAppearance(paramContext, paramInt);
      }
    }
    return null;
  }
  
  public static int getUnscaledTextSize(Context paramContext, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {
      return paramInt2;
    }
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt1, R.styleable.TextAppearance);
    TypedValue localTypedValue = new TypedValue();
    boolean bool = localTypedArray.getValue(R.styleable.TextAppearance_android_textSize, localTypedValue);
    localTypedArray.recycle();
    if (!bool) {
      return paramInt2;
    }
    if (getComplexUnit(localTypedValue) == 2) {
      return Math.round(TypedValue.complexToFloat(localTypedValue.data) * paramContext.getResources().getDisplayMetrics().density);
    }
    return TypedValue.complexToDimensionPixelSize(localTypedValue.data, paramContext.getResources().getDisplayMetrics());
  }
  
  public static boolean isFontScaleAtLeast1_3(Context paramContext)
  {
    boolean bool;
    if (paramContext.getResources().getConfiguration().fontScale >= 1.3F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isFontScaleAtLeast2_0(Context paramContext)
  {
    boolean bool;
    if (paramContext.getResources().getConfiguration().fontScale >= 2.0F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/resources/MaterialResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */