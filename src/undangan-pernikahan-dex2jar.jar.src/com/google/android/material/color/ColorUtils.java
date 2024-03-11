package com.google.android.material.color;

import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class ColorUtils
{
  private static final float[] WHITE_POINT_D65 = { 95.047F, 100.0F, 108.883F };
  
  public static int blueFromInt(int paramInt)
  {
    return paramInt & 0xFF;
  }
  
  public static float delinearized(float paramFloat)
  {
    if (paramFloat <= 0.0031308F) {
      return 12.92F * paramFloat;
    }
    return (float)Math.pow(paramFloat, 0.4166666567325592D) * 1.055F - 0.055F;
  }
  
  public static int greenFromInt(int paramInt)
  {
    return (0xFF00 & paramInt) >> 8;
  }
  
  public static String hexFromInt(int paramInt)
  {
    int j = redFromInt(paramInt);
    int i = blueFromInt(paramInt);
    String str = String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(j), Integer.valueOf(greenFromInt(paramInt)), Integer.valueOf(i) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  public static int intFromLab(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d2 = (paramDouble1 + 16.0D) / 116.0D;
    double d3 = paramDouble2 / 500.0D + d2;
    double d1 = d2 - paramDouble3 / 200.0D;
    paramDouble2 = d3 * d3 * d3;
    if (paramDouble2 <= 0.008856451679035631D) {
      paramDouble2 = (d3 * 116.0D - 16.0D) / 903.2962962962963D;
    }
    if (paramDouble1 > 8.0D) {
      paramDouble1 = d2 * d2 * d2;
    } else {
      paramDouble1 /= 903.2962962962963D;
    }
    paramDouble3 = d1 * d1 * d1;
    if (paramDouble3 <= 0.008856451679035631D) {
      paramDouble3 = (116.0D * d1 - 16.0D) / 903.2962962962963D;
    }
    float[] arrayOfFloat = WHITE_POINT_D65;
    d1 = arrayOfFloat[0];
    d3 = arrayOfFloat[1];
    d2 = arrayOfFloat[2];
    return intFromXyzComponents((float)(d1 * paramDouble2), (float)(d3 * paramDouble1), (float)(d2 * paramDouble3));
  }
  
  public static int intFromLstar(float paramFloat)
  {
    float f2 = (paramFloat + 16.0F) / 116.0F;
    int i;
    if (f2 * f2 * f2 > 0.008856452F) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (paramFloat > 8.0F) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0) {
      paramFloat = f2 * f2 * f2;
    } else {
      paramFloat /= 903.2963F;
    }
    float f1;
    if (i != 0) {
      f1 = f2 * f2 * f2;
    } else {
      f1 = (f2 * 116.0F - 16.0F) / 903.2963F;
    }
    if (i != 0) {
      f2 = f2 * f2 * f2;
    } else {
      f2 = (116.0F * f2 - 16.0F) / 903.2963F;
    }
    float[] arrayOfFloat = WHITE_POINT_D65;
    return intFromXyz(new float[] { arrayOfFloat[0] * f1, arrayOfFloat[1] * paramFloat, arrayOfFloat[2] * f2 });
  }
  
  public static int intFromRgb(int paramInt1, int paramInt2, int paramInt3)
  {
    return ((paramInt1 & 0xFF) << 16 | 0xFF000000 | (paramInt2 & 0xFF) << 8 | paramInt3 & 0xFF) >>> 0;
  }
  
  public static int intFromXyz(float[] paramArrayOfFloat)
  {
    return intFromXyzComponents(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
  }
  
  public static int intFromXyzComponents(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramFloat1 /= 100.0F;
    float f1 = paramFloat2 / 100.0F;
    float f2 = paramFloat3 / 100.0F;
    paramFloat2 = delinearized(3.2406F * paramFloat1 + -1.5372F * f1 + -0.4986F * f2);
    paramFloat3 = delinearized(-0.9689F * paramFloat1 + 1.8758F * f1 + 0.0415F * f2);
    paramFloat1 = delinearized(0.0557F * paramFloat1 + -0.204F * f1 + 1.057F * f2);
    return intFromRgb(Math.max(Math.min(255, Math.round(paramFloat2 * 255.0F)), 0), Math.max(Math.min(255, Math.round(paramFloat3 * 255.0F)), 0), Math.max(Math.min(255, Math.round(255.0F * paramFloat1)), 0));
  }
  
  public static double[] labFromInt(int paramInt)
  {
    float[] arrayOfFloat2 = xyzFromInt(paramInt);
    float f = arrayOfFloat2[1];
    float[] arrayOfFloat1 = WHITE_POINT_D65;
    double d1 = f / arrayOfFloat1[1];
    if (d1 > 0.008856451679035631D) {
      d1 = Math.cbrt(d1);
    } else {
      d1 = (d1 * 903.2962962962963D + 16.0D) / 116.0D;
    }
    double d2 = arrayOfFloat2[0] / arrayOfFloat1[0];
    if (d2 > 0.008856451679035631D) {
      d2 = Math.cbrt(d2);
    } else {
      d2 = (d2 * 903.2962962962963D + 16.0D) / 116.0D;
    }
    double d3 = arrayOfFloat2[2] / arrayOfFloat1[2];
    if (d3 > 0.008856451679035631D) {
      d3 = Math.cbrt(d3);
    } else {
      d3 = (903.2962962962963D * d3 + 16.0D) / 116.0D;
    }
    return new double[] { 116.0D * d1 - 16.0D, (d2 - d1) * 500.0D, (d1 - d3) * 200.0D };
  }
  
  public static float linearized(float paramFloat)
  {
    if (paramFloat <= 0.04045F) {
      return paramFloat / 12.92F;
    }
    return (float)Math.pow((0.055F + paramFloat) / 1.055F, 2.4000000953674316D);
  }
  
  public static float lstarFromInt(int paramInt)
  {
    return (float)labFromInt(paramInt)[0];
  }
  
  public static int redFromInt(int paramInt)
  {
    return (0xFF0000 & paramInt) >> 16;
  }
  
  public static final float[] whitePointD65()
  {
    return Arrays.copyOf(WHITE_POINT_D65, 3);
  }
  
  public static float[] xyzFromInt(int paramInt)
  {
    float f2 = linearized(redFromInt(paramInt) / 255.0F) * 100.0F;
    float f1 = linearized(greenFromInt(paramInt) / 255.0F) * 100.0F;
    float f3 = linearized(blueFromInt(paramInt) / 255.0F) * 100.0F;
    return new float[] { 0.41233894F * f2 + 0.35762063F * f1 + 0.18051042F * f3, 0.2126F * f2 + 0.7152F * f1 + 0.0722F * f3, 0.01932141F * f2 + 0.11916382F * f1 + 0.9503448F * f3 };
  }
  
  public static float yFromLstar(float paramFloat)
  {
    if (paramFloat > 8.0F) {
      return (float)Math.pow((paramFloat + 16.0D) / 116.0D, 3.0D) * 100.0F;
    }
    return paramFloat / 903.2963F * 100.0F;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/ColorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */