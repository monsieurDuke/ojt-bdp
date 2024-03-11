package androidx.core.graphics;

import android.graphics.Color;
import java.util.Objects;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ColorUtils
{
  private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
  private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
  private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
  private static final double XYZ_EPSILON = 0.008856D;
  private static final double XYZ_KAPPA = 903.3D;
  private static final double XYZ_WHITE_REFERENCE_X = 95.047D;
  private static final double XYZ_WHITE_REFERENCE_Y = 100.0D;
  private static final double XYZ_WHITE_REFERENCE_Z = 108.883D;
  
  public static int HSLToColor(float[] paramArrayOfFloat)
  {
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    float f3 = paramArrayOfFloat[2];
    f2 = (1.0F - Math.abs(f3 * 2.0F - 1.0F)) * f2;
    float f4 = f3 - 0.5F * f2;
    f3 = (1.0F - Math.abs(f1 / 60.0F % 2.0F - 1.0F)) * f2;
    int m = (int)f1 / 60;
    int i = 0;
    int j = 0;
    int k = 0;
    switch (m)
    {
    default: 
      break;
    case 5: 
    case 6: 
      i = Math.round((f2 + f4) * 255.0F);
      j = Math.round(f4 * 255.0F);
      k = Math.round((f3 + f4) * 255.0F);
      break;
    case 4: 
      i = Math.round((f3 + f4) * 255.0F);
      j = Math.round(f4 * 255.0F);
      k = Math.round((f2 + f4) * 255.0F);
      break;
    case 3: 
      i = Math.round(f4 * 255.0F);
      j = Math.round((f3 + f4) * 255.0F);
      k = Math.round((f2 + f4) * 255.0F);
      break;
    case 2: 
      i = Math.round(f4 * 255.0F);
      j = Math.round((f2 + f4) * 255.0F);
      k = Math.round((f3 + f4) * 255.0F);
      break;
    case 1: 
      i = Math.round((f3 + f4) * 255.0F);
      j = Math.round((f2 + f4) * 255.0F);
      k = Math.round(255.0F * f4);
      break;
    case 0: 
      i = Math.round((f2 + f4) * 255.0F);
      j = Math.round((f3 + f4) * 255.0F);
      k = Math.round(255.0F * f4);
    }
    return Color.rgb(constrain(i, 0, 255), constrain(j, 0, 255), constrain(k, 0, 255));
  }
  
  public static int LABToColor(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double[] arrayOfDouble = getTempDouble3Array();
    LABToXYZ(paramDouble1, paramDouble2, paramDouble3, arrayOfDouble);
    return XYZToColor(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2]);
  }
  
  public static void LABToXYZ(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble)
  {
    double d2 = (paramDouble1 + 16.0D) / 116.0D;
    double d3 = paramDouble2 / 500.0D + d2;
    double d1 = d2 - paramDouble3 / 200.0D;
    paramDouble2 = Math.pow(d3, 3.0D);
    if (paramDouble2 <= 0.008856D) {
      paramDouble2 = (d3 * 116.0D - 16.0D) / 903.3D;
    }
    if (paramDouble1 > 7.9996247999999985D) {
      paramDouble1 = Math.pow(d2, 3.0D);
    } else {
      paramDouble1 /= 903.3D;
    }
    paramDouble3 = Math.pow(d1, 3.0D);
    if (paramDouble3 <= 0.008856D) {
      paramDouble3 = (116.0D * d1 - 16.0D) / 903.3D;
    }
    paramArrayOfDouble[0] = (95.047D * paramDouble2);
    paramArrayOfDouble[1] = (100.0D * paramDouble1);
    paramArrayOfDouble[2] = (108.883D * paramDouble3);
  }
  
  public static void RGBToHSL(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat)
  {
    float f1 = paramInt1 / 255.0F;
    float f5 = paramInt2 / 255.0F;
    float f6 = paramInt3 / 255.0F;
    float f3 = Math.max(f1, Math.max(f5, f6));
    float f7 = Math.min(f1, Math.min(f5, f6));
    float f2 = f3 - f7;
    float f4 = (f3 + f7) / 2.0F;
    if (f3 == f7)
    {
      f1 = 0.0F;
      f2 = 0.0F;
    }
    else
    {
      if (f3 == f1) {
        f1 = (f5 - f6) / f2 % 6.0F;
      } else if (f3 == f5) {
        f1 = (f6 - f1) / f2 + 2.0F;
      } else {
        f1 = (f1 - f5) / f2 + 4.0F;
      }
      f3 = f2 / (1.0F - Math.abs(2.0F * f4 - 1.0F));
      f2 = f1;
      f1 = f3;
    }
    f3 = 60.0F * f2 % 360.0F;
    f2 = f3;
    if (f3 < 0.0F) {
      f2 = f3 + 360.0F;
    }
    paramArrayOfFloat[0] = constrain(f2, 0.0F, 360.0F);
    paramArrayOfFloat[1] = constrain(f1, 0.0F, 1.0F);
    paramArrayOfFloat[2] = constrain(f4, 0.0F, 1.0F);
  }
  
  public static void RGBToLAB(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble)
  {
    RGBToXYZ(paramInt1, paramInt2, paramInt3, paramArrayOfDouble);
    XYZToLAB(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2], paramArrayOfDouble);
  }
  
  public static void RGBToXYZ(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length == 3)
    {
      double d1 = paramInt1 / 255.0D;
      if (d1 < 0.04045D) {
        d1 /= 12.92D;
      } else {
        d1 = Math.pow((d1 + 0.055D) / 1.055D, 2.4D);
      }
      double d2 = paramInt2 / 255.0D;
      if (d2 < 0.04045D) {
        d2 /= 12.92D;
      } else {
        d2 = Math.pow((d2 + 0.055D) / 1.055D, 2.4D);
      }
      double d3 = paramInt3 / 255.0D;
      if (d3 < 0.04045D) {
        d3 /= 12.92D;
      } else {
        d3 = Math.pow((0.055D + d3) / 1.055D, 2.4D);
      }
      paramArrayOfDouble[0] = ((0.4124D * d1 + 0.3576D * d2 + 0.1805D * d3) * 100.0D);
      paramArrayOfDouble[1] = ((0.2126D * d1 + 0.7152D * d2 + 0.0722D * d3) * 100.0D);
      paramArrayOfDouble[2] = ((0.0193D * d1 + 0.1192D * d2 + 0.9505D * d3) * 100.0D);
      return;
    }
    throw new IllegalArgumentException("outXyz must have a length of 3.");
  }
  
  public static int XYZToColor(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d2 = (3.2406D * paramDouble1 + -1.5372D * paramDouble2 + -0.4986D * paramDouble3) / 100.0D;
    double d1 = (-0.9689D * paramDouble1 + 1.8758D * paramDouble2 + 0.0415D * paramDouble3) / 100.0D;
    paramDouble3 = (0.0557D * paramDouble1 + -0.204D * paramDouble2 + 1.057D * paramDouble3) / 100.0D;
    if (d2 > 0.0031308D) {
      paramDouble1 = Math.pow(d2, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble1 = d2 * 12.92D;
    }
    if (d1 > 0.0031308D) {
      paramDouble2 = Math.pow(d1, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble2 = d1 * 12.92D;
    }
    if (paramDouble3 > 0.0031308D) {
      paramDouble3 = Math.pow(paramDouble3, 0.4166666666666667D) * 1.055D - 0.055D;
    } else {
      paramDouble3 *= 12.92D;
    }
    return Color.rgb(constrain((int)Math.round(paramDouble1 * 255.0D), 0, 255), constrain((int)Math.round(paramDouble2 * 255.0D), 0, 255), constrain((int)Math.round(255.0D * paramDouble3), 0, 255));
  }
  
  public static void XYZToLAB(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length == 3)
    {
      paramDouble1 = pivotXyzComponent(paramDouble1 / 95.047D);
      paramDouble2 = pivotXyzComponent(paramDouble2 / 100.0D);
      paramDouble3 = pivotXyzComponent(paramDouble3 / 108.883D);
      paramArrayOfDouble[0] = Math.max(0.0D, 116.0D * paramDouble2 - 16.0D);
      paramArrayOfDouble[1] = ((paramDouble1 - paramDouble2) * 500.0D);
      paramArrayOfDouble[2] = ((paramDouble2 - paramDouble3) * 200.0D);
      return;
    }
    throw new IllegalArgumentException("outLab must have a length of 3.");
  }
  
  public static int blendARGB(int paramInt1, int paramInt2, float paramFloat)
  {
    float f2 = 1.0F - paramFloat;
    float f4 = Color.alpha(paramInt1);
    float f7 = Color.alpha(paramInt2);
    float f6 = Color.red(paramInt1);
    float f8 = Color.red(paramInt2);
    float f3 = Color.green(paramInt1);
    float f5 = Color.green(paramInt2);
    float f1 = Color.blue(paramInt1);
    float f9 = Color.blue(paramInt2);
    return Color.argb((int)(f4 * f2 + f7 * paramFloat), (int)(f6 * f2 + f8 * paramFloat), (int)(f3 * f2 + f5 * paramFloat), (int)(f1 * f2 + f9 * paramFloat));
  }
  
  public static void blendHSL(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float paramFloat, float[] paramArrayOfFloat3)
  {
    if (paramArrayOfFloat3.length == 3)
    {
      float f = 1.0F - paramFloat;
      paramArrayOfFloat3[0] = circularInterpolate(paramArrayOfFloat1[0], paramArrayOfFloat2[0], paramFloat);
      paramArrayOfFloat3[1] = (paramArrayOfFloat1[1] * f + paramArrayOfFloat2[1] * paramFloat);
      paramArrayOfFloat3[2] = (paramArrayOfFloat1[2] * f + paramArrayOfFloat2[2] * paramFloat);
      return;
    }
    throw new IllegalArgumentException("result must have a length of 3.");
  }
  
  public static void blendLAB(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double paramDouble, double[] paramArrayOfDouble3)
  {
    if (paramArrayOfDouble3.length == 3)
    {
      double d = 1.0D - paramDouble;
      paramArrayOfDouble3[0] = (paramArrayOfDouble1[0] * d + paramArrayOfDouble2[0] * paramDouble);
      paramArrayOfDouble3[1] = (paramArrayOfDouble1[1] * d + paramArrayOfDouble2[1] * paramDouble);
      paramArrayOfDouble3[2] = (paramArrayOfDouble1[2] * d + paramArrayOfDouble2[2] * paramDouble);
      return;
    }
    throw new IllegalArgumentException("outResult must have a length of 3.");
  }
  
  public static double calculateContrast(int paramInt1, int paramInt2)
  {
    if (Color.alpha(paramInt2) == 255)
    {
      int i = paramInt1;
      if (Color.alpha(paramInt1) < 255) {
        i = compositeColors(paramInt1, paramInt2);
      }
      double d1 = calculateLuminance(i) + 0.05D;
      double d2 = calculateLuminance(paramInt2) + 0.05D;
      return Math.max(d1, d2) / Math.min(d1, d2);
    }
    StringBuilder localStringBuilder = new StringBuilder().append("background can not be translucent: #");
    String str = Integer.toHexString(paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str);
  }
  
  public static double calculateLuminance(int paramInt)
  {
    double[] arrayOfDouble = getTempDouble3Array();
    colorToXYZ(paramInt, arrayOfDouble);
    return arrayOfDouble[1] / 100.0D;
  }
  
  public static int calculateMinimumAlpha(int paramInt1, int paramInt2, float paramFloat)
  {
    if (Color.alpha(paramInt2) == 255)
    {
      if (calculateContrast(setAlphaComponent(paramInt1, 255), paramInt2) < paramFloat) {
        return -1;
      }
      int j = 0;
      int k = 0;
      int i = 255;
      while ((j <= 10) && (i - k > 1))
      {
        int m = (k + i) / 2;
        if (calculateContrast(setAlphaComponent(paramInt1, m), paramInt2) < paramFloat) {
          k = m;
        } else {
          i = m;
        }
        j++;
      }
      return i;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("background can not be translucent: #");
    String str = Integer.toHexString(paramInt2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str);
  }
  
  static float circularInterpolate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    if (Math.abs(paramFloat2 - paramFloat1) > 180.0F) {
      if (paramFloat2 > paramFloat1)
      {
        f1 = paramFloat1 + 360.0F;
        f2 = paramFloat2;
      }
      else
      {
        f2 = paramFloat2 + 360.0F;
        f1 = paramFloat1;
      }
    }
    return ((f2 - f1) * paramFloat3 + f1) % 360.0F;
  }
  
  public static void colorToHSL(int paramInt, float[] paramArrayOfFloat)
  {
    RGBToHSL(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfFloat);
  }
  
  public static void colorToLAB(int paramInt, double[] paramArrayOfDouble)
  {
    RGBToLAB(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfDouble);
  }
  
  public static void colorToXYZ(int paramInt, double[] paramArrayOfDouble)
  {
    RGBToXYZ(Color.red(paramInt), Color.green(paramInt), Color.blue(paramInt), paramArrayOfDouble);
  }
  
  private static int compositeAlpha(int paramInt1, int paramInt2)
  {
    return 255 - (255 - paramInt2) * (255 - paramInt1) / 255;
  }
  
  public static int compositeColors(int paramInt1, int paramInt2)
  {
    int i = Color.alpha(paramInt2);
    int j = Color.alpha(paramInt1);
    int k = compositeAlpha(j, i);
    return Color.argb(k, compositeComponent(Color.red(paramInt1), j, Color.red(paramInt2), i, k), compositeComponent(Color.green(paramInt1), j, Color.green(paramInt2), i, k), compositeComponent(Color.blue(paramInt1), j, Color.blue(paramInt2), i, k));
  }
  
  public static Color compositeColors(Color paramColor1, Color paramColor2)
  {
    return Api26Impl.compositeColors(paramColor1, paramColor2);
  }
  
  private static int compositeComponent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt5 == 0) {
      return 0;
    }
    return (paramInt1 * 255 * paramInt2 + paramInt3 * paramInt4 * (255 - paramInt2)) / (paramInt5 * 255);
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2) {
      paramFloat1 = paramFloat2;
    } else {
      paramFloat1 = Math.min(paramFloat1, paramFloat3);
    }
    return paramFloat1;
  }
  
  private static int constrain(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= paramInt2) {
      paramInt2 = Math.min(paramInt1, paramInt3);
    }
    return paramInt2;
  }
  
  public static double distanceEuclidean(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    return Math.sqrt(Math.pow(paramArrayOfDouble1[0] - paramArrayOfDouble2[0], 2.0D) + Math.pow(paramArrayOfDouble1[1] - paramArrayOfDouble2[1], 2.0D) + Math.pow(paramArrayOfDouble1[2] - paramArrayOfDouble2[2], 2.0D));
  }
  
  private static double[] getTempDouble3Array()
  {
    ThreadLocal localThreadLocal = TEMP_ARRAY;
    double[] arrayOfDouble2 = (double[])localThreadLocal.get();
    double[] arrayOfDouble1 = arrayOfDouble2;
    if (arrayOfDouble2 == null)
    {
      arrayOfDouble1 = new double[3];
      localThreadLocal.set(arrayOfDouble1);
    }
    return arrayOfDouble1;
  }
  
  private static double pivotXyzComponent(double paramDouble)
  {
    if (paramDouble > 0.008856D) {
      paramDouble = Math.pow(paramDouble, 0.3333333333333333D);
    } else {
      paramDouble = (903.3D * paramDouble + 16.0D) / 116.0D;
    }
    return paramDouble;
  }
  
  public static int setAlphaComponent(int paramInt1, int paramInt2)
  {
    if ((paramInt2 >= 0) && (paramInt2 <= 255)) {
      return 0xFFFFFF & paramInt1 | paramInt2 << 24;
    }
    throw new IllegalArgumentException("alpha must be between 0 and 255.");
  }
  
  static class Api26Impl
  {
    static Color compositeColors(Color paramColor1, Color paramColor2)
    {
      if (Objects.equals(paramColor1.getModel(), paramColor2.getModel()))
      {
        if (!Objects.equals(paramColor2.getColorSpace(), paramColor1.getColorSpace())) {
          paramColor1 = paramColor1.convert(paramColor2.getColorSpace());
        }
        float[] arrayOfFloat2 = paramColor1.getComponents();
        float[] arrayOfFloat1 = paramColor2.getComponents();
        float f4 = paramColor1.alpha();
        float f3 = paramColor2.alpha() * (1.0F - f4);
        int j = paramColor2.getComponentCount() - 1;
        arrayOfFloat1[j] = (f4 + f3);
        float f2 = f4;
        float f1 = f3;
        if (arrayOfFloat1[j] > 0.0F)
        {
          f2 = f4 / arrayOfFloat1[j];
          f1 = f3 / arrayOfFloat1[j];
        }
        for (int i = 0; i < j; i++) {
          arrayOfFloat1[i] = (arrayOfFloat2[i] * f2 + arrayOfFloat1[i] * f1);
        }
        return Color.valueOf(arrayOfFloat1, paramColor2.getColorSpace());
      }
      throw new IllegalArgumentException("Color models must match (" + paramColor1.getModel() + " vs. " + paramColor2.getModel() + ")");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/ColorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */