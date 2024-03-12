package com.google.android.material.color;

final class Blend
{
  private static final float HARMONIZE_MAX_DEGREES = 15.0F;
  private static final float HARMONIZE_PERCENTAGE = 0.5F;
  
  public static int blendCam16Ucs(int paramInt1, int paramInt2, float paramFloat)
  {
    Cam16 localCam162 = Cam16.fromInt(paramInt1);
    Cam16 localCam161 = Cam16.fromInt(paramInt2);
    float f2 = localCam162.getJStar();
    float f1 = localCam162.getAStar();
    float f3 = localCam162.getBStar();
    return Cam16.fromUcs((localCam161.getJStar() - f2) * paramFloat + f2, (localCam161.getAStar() - f1) * paramFloat + f1, (localCam161.getBStar() - f3) * paramFloat + f3).getInt();
  }
  
  public static int blendHctHue(int paramInt1, int paramInt2, float paramFloat)
  {
    Cam16 localCam162 = Cam16.fromInt(blendCam16Ucs(paramInt1, paramInt2, paramFloat));
    Cam16 localCam161 = Cam16.fromInt(paramInt1);
    return Hct.from(localCam162.getHue(), localCam161.getChroma(), ColorUtils.lstarFromInt(paramInt1)).toInt();
  }
  
  public static int harmonize(int paramInt1, int paramInt2)
  {
    Hct localHct2 = Hct.fromInt(paramInt1);
    Hct localHct1 = Hct.fromInt(paramInt2);
    float f = Math.min(0.5F * MathUtils.differenceDegrees(localHct2.getHue(), localHct1.getHue()), 15.0F);
    return Hct.from(MathUtils.sanitizeDegrees(localHct2.getHue() + rotationDirection(localHct2.getHue(), localHct1.getHue()) * f), localHct2.getChroma(), localHct2.getTone()).toInt();
  }
  
  private static float rotationDirection(float paramFloat1, float paramFloat2)
  {
    float f2 = paramFloat2 - paramFloat1;
    float f1 = paramFloat2 - paramFloat1 + 360.0F;
    float f3 = paramFloat2 - paramFloat1 - 360.0F;
    paramFloat2 = Math.abs(f2);
    float f4 = Math.abs(f1);
    float f5 = Math.abs(f3);
    paramFloat1 = 1.0F;
    if ((paramFloat2 <= f4) && (paramFloat2 <= f5))
    {
      if (f2 < 0.0D) {
        paramFloat1 = -1.0F;
      }
      return paramFloat1;
    }
    if ((f4 <= paramFloat2) && (f4 <= f5))
    {
      if (f1 < 0.0D) {
        paramFloat1 = -1.0F;
      }
      return paramFloat1;
    }
    if (f3 < 0.0D) {
      paramFloat1 = -1.0F;
    }
    return paramFloat1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/Blend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */