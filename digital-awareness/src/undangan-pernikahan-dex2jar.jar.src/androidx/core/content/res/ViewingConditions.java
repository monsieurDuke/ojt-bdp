package androidx.core.content.res;

final class ViewingConditions
{
  static final ViewingConditions DEFAULT = make(CamUtils.WHITE_POINT_D65, (float)(CamUtils.yFromLStar(50.0F) * 63.66197723675813D / 100.0D), 50.0F, 2.0F, false);
  private final float mAw;
  private final float mC;
  private final float mFl;
  private final float mFlRoot;
  private final float mN;
  private final float mNbb;
  private final float mNc;
  private final float mNcb;
  private final float[] mRgbD;
  private final float mZ;
  
  private ViewingConditions(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float[] paramArrayOfFloat, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.mN = paramFloat1;
    this.mAw = paramFloat2;
    this.mNbb = paramFloat3;
    this.mNcb = paramFloat4;
    this.mC = paramFloat5;
    this.mNc = paramFloat6;
    this.mRgbD = paramArrayOfFloat;
    this.mFl = paramFloat7;
    this.mFlRoot = paramFloat8;
    this.mZ = paramFloat9;
  }
  
  static ViewingConditions make(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    Object localObject = CamUtils.XYZ_TO_CAM16RGB;
    float f2 = paramArrayOfFloat[0] * localObject[0][0] + paramArrayOfFloat[1] * localObject[0][1] + paramArrayOfFloat[2] * localObject[0][2];
    float f4 = paramArrayOfFloat[0] * localObject[1][0] + paramArrayOfFloat[1] * localObject[1][1] + paramArrayOfFloat[2] * localObject[1][2];
    float f3 = paramArrayOfFloat[0] * localObject[2][0] + paramArrayOfFloat[1] * localObject[2][1] + paramArrayOfFloat[2] * localObject[2][2];
    float f5 = paramFloat3 / 10.0F + 0.8F;
    float f1;
    if (f5 >= 0.9D) {
      f1 = CamUtils.lerp(0.59F, 0.69F, (f5 - 0.9F) * 10.0F);
    } else {
      f1 = CamUtils.lerp(0.525F, 0.59F, (f5 - 0.8F) * 10.0F);
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
    localObject[0] = (100.0F / f2 * paramFloat3 + 1.0F - paramFloat3);
    localObject[1] = (100.0F / f4 * paramFloat3 + 1.0F - paramFloat3);
    localObject[2] = (100.0F / f3 * paramFloat3 + 1.0F - paramFloat3);
    paramFloat3 = 1.0F / (5.0F * paramFloat1 + 1.0F);
    float f6 = paramFloat3 * paramFloat3 * paramFloat3 * paramFloat3;
    paramFloat3 = 1.0F - f6;
    paramFloat1 = f6 * paramFloat1 + 0.1F * paramFloat3 * paramFloat3 * (float)Math.cbrt(paramFloat1 * 5.0D);
    f6 = CamUtils.yFromLStar(paramFloat2) / paramArrayOfFloat[1];
    paramFloat2 = (float)Math.sqrt(f6);
    paramFloat3 = 0.725F / (float)Math.pow(f6, 0.2D);
    float[] arrayOfFloat = new float[3];
    arrayOfFloat[0] = ((float)Math.pow(localObject[0] * paramFloat1 * f2 / 100.0D, 0.42D));
    arrayOfFloat[1] = ((float)Math.pow(localObject[1] * paramFloat1 * f4 / 100.0D, 0.42D));
    arrayOfFloat[2] = ((float)Math.pow(localObject[2] * paramFloat1 * f3 / 100.0D, 0.42D));
    paramArrayOfFloat = new float[3];
    paramArrayOfFloat[0] = (arrayOfFloat[0] * 400.0F / (arrayOfFloat[0] + 27.13F));
    paramArrayOfFloat[1] = (arrayOfFloat[1] * 400.0F / (arrayOfFloat[1] + 27.13F));
    paramArrayOfFloat[2] = (arrayOfFloat[2] * 400.0F / (arrayOfFloat[2] + 27.13F));
    return new ViewingConditions(f6, (paramArrayOfFloat[0] * 2.0F + paramArrayOfFloat[1] + paramArrayOfFloat[2] * 0.05F) * paramFloat3, paramFloat3, paramFloat3, f1, f5, (float[])localObject, paramFloat1, (float)Math.pow(paramFloat1, 0.25D), paramFloat2 + 1.48F);
  }
  
  float getAw()
  {
    return this.mAw;
  }
  
  float getC()
  {
    return this.mC;
  }
  
  float getFl()
  {
    return this.mFl;
  }
  
  float getFlRoot()
  {
    return this.mFlRoot;
  }
  
  float getN()
  {
    return this.mN;
  }
  
  float getNbb()
  {
    return this.mNbb;
  }
  
  float getNc()
  {
    return this.mNc;
  }
  
  float getNcb()
  {
    return this.mNcb;
  }
  
  float[] getRgbD()
  {
    return this.mRgbD;
  }
  
  float getZ()
  {
    return this.mZ;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/ViewingConditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */