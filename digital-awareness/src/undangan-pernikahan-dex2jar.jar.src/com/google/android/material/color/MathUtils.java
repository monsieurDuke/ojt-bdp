package com.google.android.material.color;

final class MathUtils
{
  static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(paramFloat3, paramFloat1), paramFloat2);
  }
  
  public static float differenceDegrees(float paramFloat1, float paramFloat2)
  {
    return 180.0F - Math.abs(Math.abs(paramFloat1 - paramFloat2) - 180.0F);
  }
  
  public static float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (1.0F - paramFloat3) * paramFloat1 + paramFloat3 * paramFloat2;
  }
  
  public static float sanitizeDegrees(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return paramFloat % 360.0F + 360.0F;
    }
    if (paramFloat >= 360.0F) {
      return paramFloat % 360.0F;
    }
    return paramFloat;
  }
  
  public static int sanitizeDegrees(int paramInt)
  {
    if (paramInt < 0) {
      return paramInt % 360 + 360;
    }
    if (paramInt >= 360) {
      return paramInt % 360;
    }
    return paramInt;
  }
  
  static float toDegrees(float paramFloat)
  {
    return 180.0F * paramFloat / 3.1415927F;
  }
  
  static float toRadians(float paramFloat)
  {
    return paramFloat / 180.0F * 3.1415927F;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */