package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.appcompat.R.styleable;
import androidx.core.graphics.ColorUtils;

public class ThemeUtils
{
  static final int[] ACTIVATED_STATE_SET;
  static final int[] CHECKED_STATE_SET;
  static final int[] DISABLED_STATE_SET;
  static final int[] EMPTY_STATE_SET = new int[0];
  static final int[] FOCUSED_STATE_SET;
  static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET;
  static final int[] PRESSED_STATE_SET;
  static final int[] SELECTED_STATE_SET;
  private static final String TAG = "ThemeUtils";
  private static final int[] TEMP_ARRAY = new int[1];
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal();
  
  static
  {
    DISABLED_STATE_SET = new int[] { -16842910 };
    FOCUSED_STATE_SET = new int[] { 16842908 };
    ACTIVATED_STATE_SET = new int[] { 16843518 };
    PRESSED_STATE_SET = new int[] { 16842919 };
    CHECKED_STATE_SET = new int[] { 16842912 };
    SELECTED_STATE_SET = new int[] { 16842913 };
    NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[] { -16842919, -16842908 };
  }
  
  public static void checkAppCompatTheme(View paramView, Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    try
    {
      if (!paramContext.hasValue(R.styleable.AppCompatTheme_windowActionBar))
      {
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        Log.e("ThemeUtils", "View " + paramView.getClass() + " is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
      }
      return;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public static ColorStateList createDisabledStateList(int paramInt1, int paramInt2)
  {
    int[][] arrayOfInt1 = new int[2][];
    int[] arrayOfInt = new int[2];
    arrayOfInt1[0] = DISABLED_STATE_SET;
    arrayOfInt[0] = paramInt2;
    paramInt2 = 0 + 1;
    arrayOfInt1[paramInt2] = EMPTY_STATE_SET;
    arrayOfInt[paramInt2] = paramInt1;
    return new ColorStateList(arrayOfInt1, arrayOfInt);
  }
  
  public static int getDisabledThemeAttrColor(Context paramContext, int paramInt)
  {
    Object localObject = getThemeAttrColorStateList(paramContext, paramInt);
    if ((localObject != null) && (((ColorStateList)localObject).isStateful())) {
      return ((ColorStateList)localObject).getColorForState(DISABLED_STATE_SET, ((ColorStateList)localObject).getDefaultColor());
    }
    localObject = getTypedValue();
    paramContext.getTheme().resolveAttribute(16842803, (TypedValue)localObject, true);
    return getThemeAttrColor(paramContext, paramInt, ((TypedValue)localObject).getFloat());
  }
  
  public static int getThemeAttrColor(Context paramContext, int paramInt)
  {
    Object localObject = TEMP_ARRAY;
    localObject[0] = paramInt;
    localObject = TintTypedArray.obtainStyledAttributes(paramContext, null, (int[])localObject);
    try
    {
      paramInt = ((TintTypedArray)localObject).getColor(0, 0);
      return paramInt;
    }
    finally
    {
      ((TintTypedArray)localObject).recycle();
    }
  }
  
  static int getThemeAttrColor(Context paramContext, int paramInt, float paramFloat)
  {
    paramInt = getThemeAttrColor(paramContext, paramInt);
    return ColorUtils.setAlphaComponent(paramInt, Math.round(Color.alpha(paramInt) * paramFloat));
  }
  
  public static ColorStateList getThemeAttrColorStateList(Context paramContext, int paramInt)
  {
    Object localObject1 = TEMP_ARRAY;
    localObject1[0] = paramInt;
    paramContext = TintTypedArray.obtainStyledAttributes(paramContext, null, (int[])localObject1);
    try
    {
      localObject1 = paramContext.getColorStateList(0);
      return (ColorStateList)localObject1;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  private static TypedValue getTypedValue()
  {
    ThreadLocal localThreadLocal = TL_TYPED_VALUE;
    TypedValue localTypedValue2 = (TypedValue)localThreadLocal.get();
    TypedValue localTypedValue1 = localTypedValue2;
    if (localTypedValue2 == null)
    {
      localTypedValue1 = new TypedValue();
      localThreadLocal.set(localTypedValue1);
    }
    return localTypedValue1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/ThemeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */