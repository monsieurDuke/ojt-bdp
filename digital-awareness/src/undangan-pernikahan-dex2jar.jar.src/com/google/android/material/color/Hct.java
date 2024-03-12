package com.google.android.material.color;

final class Hct
{
  private static final float CHROMA_SEARCH_ENDPOINT = 0.4F;
  private static final float DE_MAX = 1.0F;
  private static final float DE_MAX_ERROR = 1.0E-9F;
  private static final float DL_MAX = 0.2F;
  private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01F;
  private float chroma;
  private float hue;
  private float tone;
  
  private Hct(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    setInternalState(gamutMap(paramFloat1, paramFloat2, paramFloat3));
  }
  
  private static Cam16 findCamByJ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f3 = 0.0F;
    float f1 = 100.0F;
    float f6 = 1000.0F;
    float f5 = 1000.0F;
    Object localObject2;
    for (Object localObject1 = null;; localObject1 = localObject2)
    {
      localObject2 = localObject1;
      if (Math.abs(f3 - f1) <= 0.01F) {
        break;
      }
      float f7 = f3 + (f1 - f3) / 2.0F;
      int i = Cam16.fromJch(f7, paramFloat2, paramFloat1).getInt();
      float f10 = ColorUtils.lstarFromInt(i);
      float f9 = Math.abs(paramFloat3 - f10);
      float f4 = f6;
      float f2 = f5;
      localObject2 = localObject1;
      if (f9 < 0.2F)
      {
        Cam16 localCam16 = Cam16.fromInt(i);
        float f8 = localCam16.distance(Cam16.fromJch(localCam16.getJ(), localCam16.getChroma(), paramFloat1));
        f4 = f6;
        f2 = f5;
        localObject2 = localObject1;
        if (f8 <= 1.0F)
        {
          f4 = f6;
          f2 = f5;
          localObject2 = localObject1;
          if (f8 <= f5)
          {
            f4 = f9;
            f2 = f8;
            localObject2 = localCam16;
          }
        }
      }
      if ((f4 == 0.0F) && (f2 < 1.0E-9F)) {
        break;
      }
      if (f10 < paramFloat3) {
        f3 = f7;
      } else {
        f1 = f7;
      }
      f6 = f4;
      f5 = f2;
    }
    return (Cam16)localObject2;
  }
  
  public static Hct from(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return new Hct(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public static Hct fromInt(int paramInt)
  {
    Cam16 localCam16 = Cam16.fromInt(paramInt);
    return new Hct(localCam16.getHue(), localCam16.getChroma(), ColorUtils.lstarFromInt(paramInt));
  }
  
  private static int gamutMap(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return gamutMapInViewingConditions(paramFloat1, paramFloat2, paramFloat3, ViewingConditions.DEFAULT);
  }
  
  static int gamutMapInViewingConditions(float paramFloat1, float paramFloat2, float paramFloat3, ViewingConditions paramViewingConditions)
  {
    if ((paramFloat2 >= 1.0D) && (Math.round(paramFloat3) > 0.0D) && (Math.round(paramFloat3) < 100.0D))
    {
      float f2 = MathUtils.sanitizeDegrees(paramFloat1);
      float f1 = paramFloat2;
      paramFloat1 = paramFloat2;
      paramFloat2 = 0.0F;
      int i = 1;
      Object localObject = null;
      while (Math.abs(paramFloat2 - f1) >= 0.4F)
      {
        Cam16 localCam16 = findCamByJ(f2, paramFloat1, paramFloat3);
        if (i != 0)
        {
          if (localCam16 != null) {
            return localCam16.viewed(paramViewingConditions);
          }
          i = 0;
          paramFloat1 = paramFloat2 + (f1 - paramFloat2) / 2.0F;
        }
        else
        {
          if (localCam16 == null)
          {
            f1 = paramFloat1;
          }
          else
          {
            localObject = localCam16;
            paramFloat2 = paramFloat1;
          }
          paramFloat1 = paramFloat2 + (f1 - paramFloat2) / 2.0F;
        }
      }
      if (localObject == null) {
        return ColorUtils.intFromLstar(paramFloat3);
      }
      return ((Cam16)localObject).viewed(paramViewingConditions);
    }
    return ColorUtils.intFromLstar(paramFloat3);
  }
  
  private void setInternalState(int paramInt)
  {
    Cam16 localCam16 = Cam16.fromInt(paramInt);
    float f = ColorUtils.lstarFromInt(paramInt);
    this.hue = localCam16.getHue();
    this.chroma = localCam16.getChroma();
    this.tone = f;
  }
  
  public float getChroma()
  {
    return this.chroma;
  }
  
  public float getHue()
  {
    return this.hue;
  }
  
  public float getTone()
  {
    return this.tone;
  }
  
  public void setChroma(float paramFloat)
  {
    setInternalState(gamutMap(this.hue, paramFloat, this.tone));
  }
  
  public void setHue(float paramFloat)
  {
    setInternalState(gamutMap(MathUtils.sanitizeDegrees(paramFloat), this.chroma, this.tone));
  }
  
  public void setTone(float paramFloat)
  {
    setInternalState(gamutMap(this.hue, this.chroma, paramFloat));
  }
  
  public int toInt()
  {
    return gamutMap(this.hue, this.chroma, this.tone);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/Hct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */