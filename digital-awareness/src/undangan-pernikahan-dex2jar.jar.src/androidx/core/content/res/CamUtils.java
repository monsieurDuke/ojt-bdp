package androidx.core.content.res;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;

final class CamUtils
{
  static final float[][] CAM16RGB_TO_XYZ;
  static final float[][] SRGB_TO_XYZ = { { 0.41233894F, 0.35762063F, 0.18051042F }, { 0.2126F, 0.7152F, 0.0722F }, { 0.01932141F, 0.11916382F, 0.9503448F } };
  static final float[] WHITE_POINT_D65;
  static final float[][] XYZ_TO_CAM16RGB;
  
  static
  {
    float[] arrayOfFloat = { -0.250268F, 1.204414F, 0.045854F };
    XYZ_TO_CAM16RGB = new float[][] { { 0.401288F, 0.650173F, -0.051461F }, arrayOfFloat, { -0.002079F, 0.048952F, 0.953127F } };
    CAM16RGB_TO_XYZ = new float[][] { { 1.8620678F, -1.0112547F, 0.14918678F }, { 0.38752654F, 0.62144744F, -0.00897398F }, { -0.0158415F, -0.03412294F, 1.0499644F } };
    WHITE_POINT_D65 = new float[] { 95.047F, 100.0F, 108.883F };
  }
  
  static int intFromLStar(float paramFloat)
  {
    if (paramFloat < 1.0F) {
      return -16777216;
    }
    if (paramFloat > 99.0F) {
      return -1;
    }
    float f2 = (paramFloat + 16.0F) / 116.0F;
    int i;
    if (paramFloat > 8.0F) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramFloat = f2 * f2 * f2;
    } else {
      paramFloat /= 903.2963F;
    }
    if (f2 * f2 * f2 > 0.008856452F) {
      i = 1;
    } else {
      i = 0;
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
    return ColorUtils.XYZToColor(arrayOfFloat[0] * f1, arrayOfFloat[1] * paramFloat, arrayOfFloat[2] * f2);
  }
  
  static float lStarFromInt(int paramInt)
  {
    return lStarFromY(yFromInt(paramInt));
  }
  
  static float lStarFromY(float paramFloat)
  {
    paramFloat /= 100.0F;
    if (paramFloat <= 0.008856452F) {
      return 903.2963F * paramFloat;
    }
    return 116.0F * (float)Math.cbrt(paramFloat) - 16.0F;
  }
  
  static float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat2 - paramFloat1) * paramFloat3 + paramFloat1;
  }
  
  static float linearized(int paramInt)
  {
    float f = paramInt / 255.0F;
    if (f <= 0.04045F) {
      return f / 12.92F * 100.0F;
    }
    return (float)Math.pow((0.055F + f) / 1.055F, 2.4000000953674316D) * 100.0F;
  }
  
  static float[] xyzFromInt(int paramInt)
  {
    float f2 = linearized(Color.red(paramInt));
    float f3 = linearized(Color.green(paramInt));
    float f1 = linearized(Color.blue(paramInt));
    float[][] arrayOfFloat = SRGB_TO_XYZ;
    return new float[] { arrayOfFloat[0][0] * f2 + arrayOfFloat[0][1] * f3 + arrayOfFloat[0][2] * f1, arrayOfFloat[1][0] * f2 + arrayOfFloat[1][1] * f3 + arrayOfFloat[1][2] * f1, arrayOfFloat[2][0] * f2 + arrayOfFloat[2][1] * f3 + arrayOfFloat[2][2] * f1 };
  }
  
  static float yFromInt(int paramInt)
  {
    float f3 = linearized(Color.red(paramInt));
    float f1 = linearized(Color.green(paramInt));
    float f2 = linearized(Color.blue(paramInt));
    float[][] arrayOfFloat = SRGB_TO_XYZ;
    return arrayOfFloat[1][0] * f3 + arrayOfFloat[1][1] * f1 + arrayOfFloat[1][2] * f2;
  }
  
  static float yFromLStar(float paramFloat)
  {
    if (paramFloat > 8.0F) {
      return (float)Math.pow((paramFloat + 16.0D) / 116.0D, 3.0D) * 100.0F;
    }
    return paramFloat / 903.2963F * 100.0F;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/CamUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */