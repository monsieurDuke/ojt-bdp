package com.google.android.material.color;

final class ViewingConditions
{
  public static final ViewingConditions DEFAULT = make(ColorUtils.whitePointD65(), (float)(ColorUtils.yFromLstar(50.0F) * 63.66197723675813D / 100.0D), 50.0F, 2.0F, false);
  private final float aw;
  private final float c;
  private final float fl;
  private final float flRoot;
  private final float n;
  private final float nbb;
  private final float nc;
  private final float ncb;
  private final float[] rgbD;
  private final float z;
  
  private ViewingConditions(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float[] paramArrayOfFloat, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.n = paramFloat1;
    this.aw = paramFloat2;
    this.nbb = paramFloat3;
    this.ncb = paramFloat4;
    this.c = paramFloat5;
    this.nc = paramFloat6;
    this.rgbD = paramArrayOfFloat;
    this.fl = paramFloat7;
    this.flRoot = paramFloat8;
    this.z = paramFloat9;
  }
  
  static ViewingConditions make(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    Object localObject = Cam16.XYZ_TO_CAM16RGB;
    float f3 = paramArrayOfFloat[0] * localObject[0][0] + paramArrayOfFloat[1] * localObject[0][1] + paramArrayOfFloat[2] * localObject[0][2];
    float f4 = paramArrayOfFloat[0] * localObject[1][0] + paramArrayOfFloat[1] * localObject[1][1] + paramArrayOfFloat[2] * localObject[1][2];
    float f2 = paramArrayOfFloat[0] * localObject[2][0] + paramArrayOfFloat[1] * localObject[2][1] + paramArrayOfFloat[2] * localObject[2][2];
    float f5 = paramFloat3 / 10.0F + 0.8F;
    float f1;
    if (f5 >= 0.9D) {
      f1 = MathUtils.lerp(0.59F, 0.69F, (f5 - 0.9F) * 10.0F);
    } else {
      f1 = MathUtils.lerp(0.525F, 0.59F, (f5 - 0.8F) * 10.0F);
    }
    if (paramBoolean) {
      paramFloat3 = 1.0F;
    } else {
      paramFloat3 = (1.0F - (float)Math.exp((-paramFloat1 - 42.0F) / 92.0F) * 0.2777778F) * f5;
    }
    if (paramFloat3 > 1.0D) {
      paramFloat3 = 1.0F;
    } else if (paramFloat3 < 0.0D) {
      paramFloat3 = 0.0F;
    }
    localObject = new float[3];
    localObject[0] = (100.0F / f3 * paramFloat3 + 1.0F - paramFloat3);
    localObject[1] = (100.0F / f4 * paramFloat3 + 1.0F - paramFloat3);
    localObject[2] = (100.0F / f2 * paramFloat3 + 1.0F - paramFloat3);
    paramFloat3 = 1.0F / (5.0F * paramFloat1 + 1.0F);
    float f6 = paramFloat3 * paramFloat3 * paramFloat3 * paramFloat3;
    paramFloat3 = 1.0F - f6;
    paramFloat1 = f6 * paramFloat1 + 0.1F * paramFloat3 * paramFloat3 * (float)Math.cbrt(paramFloat1 * 5.0D);
    paramFloat3 = ColorUtils.yFromLstar(paramFloat2) / paramArrayOfFloat[1];
    paramFloat2 = (float)Math.sqrt(paramFloat3);
    f6 = 0.725F / (float)Math.pow(paramFloat3, 0.2D);
    paramArrayOfFloat = new float[3];
    paramArrayOfFloat[0] = ((float)Math.pow(localObject[0] * paramFloat1 * f3 / 100.0D, 0.42D));
    paramArrayOfFloat[1] = ((float)Math.pow(localObject[1] * paramFloat1 * f4 / 100.0D, 0.42D));
    paramArrayOfFloat[2] = ((float)Math.pow(localObject[2] * paramFloat1 * f2 / 100.0D, 0.42D));
    float[] arrayOfFloat = new float[3];
    arrayOfFloat[0] = (paramArrayOfFloat[0] * 400.0F / (paramArrayOfFloat[0] + 27.13F));
    arrayOfFloat[1] = (paramArrayOfFloat[1] * 400.0F / (paramArrayOfFloat[1] + 27.13F));
    arrayOfFloat[2] = (paramArrayOfFloat[2] * 400.0F / (paramArrayOfFloat[2] + 27.13F));
    return new ViewingConditions(paramFloat3, (arrayOfFloat[0] * 2.0F + arrayOfFloat[1] + arrayOfFloat[2] * 0.05F) * f6, f6, f6, f1, f5, (float[])localObject, paramFloat1, (float)Math.pow(paramFloat1, 0.25D), paramFloat2 + 1.48F);
  }
  
  public float getAw()
  {
    return this.aw;
  }
  
  float getC()
  {
    return this.c;
  }
  
  float getFl()
  {
    return this.fl;
  }
  
  public float getFlRoot()
  {
    return this.flRoot;
  }
  
  public float getN()
  {
    return this.n;
  }
  
  public float getNbb()
  {
    return this.nbb;
  }
  
  float getNc()
  {
    return this.nc;
  }
  
  float getNcb()
  {
    return this.ncb;
  }
  
  public float[] getRgbD()
  {
    return this.rgbD;
  }
  
  float getZ()
  {
    return this.z;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/ViewingConditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */