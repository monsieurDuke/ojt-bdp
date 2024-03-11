package androidx.core.content.res;

import androidx.core.graphics.ColorUtils;

class CamColor
{
  private static final float CHROMA_SEARCH_ENDPOINT = 0.4F;
  private static final float DE_MAX = 1.0F;
  private static final float DL_MAX = 0.2F;
  private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01F;
  private final float mAstar;
  private final float mBstar;
  private final float mChroma;
  private final float mHue;
  private final float mJ;
  private final float mJstar;
  private final float mM;
  private final float mQ;
  private final float mS;
  
  CamColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.mHue = paramFloat1;
    this.mChroma = paramFloat2;
    this.mJ = paramFloat3;
    this.mQ = paramFloat4;
    this.mM = paramFloat5;
    this.mS = paramFloat6;
    this.mJstar = paramFloat7;
    this.mAstar = paramFloat8;
    this.mBstar = paramFloat9;
  }
  
  private static CamColor findCamByJ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f3 = 0.0F;
    float f1 = 100.0F;
    float f7 = 1000.0F;
    float f5 = 1000.0F;
    Object localObject2;
    for (Object localObject1 = null;; localObject1 = localObject2)
    {
      localObject2 = localObject1;
      if (Math.abs(f3 - f1) <= 0.01F) {
        break;
      }
      float f6 = f3 + (f1 - f3) / 2.0F;
      int i = fromJch(f6, paramFloat2, paramFloat1).viewedInSrgb();
      float f10 = CamUtils.lStarFromInt(i);
      float f9 = Math.abs(paramFloat3 - f10);
      float f4 = f7;
      float f2 = f5;
      localObject2 = localObject1;
      if (f9 < 0.2F)
      {
        CamColor localCamColor = fromColor(i);
        float f8 = localCamColor.distance(fromJch(localCamColor.getJ(), localCamColor.getChroma(), paramFloat1));
        f4 = f7;
        f2 = f5;
        localObject2 = localObject1;
        if (f8 <= 1.0F)
        {
          f4 = f9;
          f2 = f8;
          localObject2 = localCamColor;
        }
      }
      if ((f4 == 0.0F) && (f2 == 0.0F)) {
        break;
      }
      if (f10 < paramFloat3) {
        f3 = f6;
      } else {
        f1 = f6;
      }
      f7 = f4;
      f5 = f2;
    }
    return (CamColor)localObject2;
  }
  
  static CamColor fromColor(int paramInt)
  {
    return fromColorInViewingConditions(paramInt, ViewingConditions.DEFAULT);
  }
  
  static CamColor fromColorInViewingConditions(int paramInt, ViewingConditions paramViewingConditions)
  {
    float[] arrayOfFloat = CamUtils.xyzFromInt(paramInt);
    float[][] arrayOfFloat1 = CamUtils.XYZ_TO_CAM16RGB;
    float f17 = arrayOfFloat[0];
    float f18 = arrayOfFloat1[0][0];
    float f14 = arrayOfFloat[1];
    float f15 = arrayOfFloat1[0][1];
    float f13 = arrayOfFloat[2];
    float f16 = arrayOfFloat1[0][2];
    float f9 = arrayOfFloat[0];
    float f7 = arrayOfFloat1[1][0];
    float f8 = arrayOfFloat[1];
    float f11 = arrayOfFloat1[1][1];
    float f10 = arrayOfFloat[2];
    float f12 = arrayOfFloat1[1][2];
    float f6 = arrayOfFloat[0];
    float f5 = arrayOfFloat1[2][0];
    float f4 = arrayOfFloat[1];
    float f2 = arrayOfFloat1[2][1];
    float f3 = arrayOfFloat[2];
    float f1 = arrayOfFloat1[2][2];
    f13 = paramViewingConditions.getRgbD()[0] * (f17 * f18 + f14 * f15 + f13 * f16);
    f7 = paramViewingConditions.getRgbD()[1] * (f9 * f7 + f8 * f11 + f10 * f12);
    f2 = paramViewingConditions.getRgbD()[2] * (f6 * f5 + f4 * f2 + f3 * f1);
    f1 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f13) / 100.0D, 0.42D);
    f4 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f7) / 100.0D, 0.42D);
    f3 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f2) / 100.0D, 0.42D);
    f1 = Math.signum(f13) * 400.0F * f1 / (f1 + 27.13F);
    f4 = Math.signum(f7) * 400.0F * f4 / (f4 + 27.13F);
    f2 = Math.signum(f2) * 400.0F * f3 / (27.13F + f3);
    f6 = (float)(f1 * 11.0D + f4 * -12.0D + f2) / 11.0F;
    f5 = (float)(f1 + f4 - f2 * 2.0D) / 9.0F;
    f3 = (f1 * 20.0F + f4 * 20.0F + 21.0F * f2) / 20.0F;
    f2 = (40.0F * f1 + f4 * 20.0F + f2) / 20.0F;
    f1 = (float)Math.atan2(f5, f6) * 180.0F / 3.1415927F;
    if (f1 < 0.0F) {
      f1 += 360.0F;
    } else if (f1 >= 360.0F) {
      f1 -= 360.0F;
    }
    f4 = f1 * 3.1415927F / 180.0F;
    f7 = (float)Math.pow(paramViewingConditions.getNbb() * f2 / paramViewingConditions.getAw(), paramViewingConditions.getC() * paramViewingConditions.getZ()) * 100.0F;
    f10 = 4.0F / paramViewingConditions.getC();
    f9 = (float)Math.sqrt(f7 / 100.0F);
    f8 = paramViewingConditions.getAw();
    f11 = paramViewingConditions.getFlRoot();
    if (f1 < 20.14D) {
      f2 = f1 + 360.0F;
    } else {
      f2 = f1;
    }
    f13 = (float)(Math.cos(f2 * 3.141592653589793D / 180.0D + 2.0D) + 3.8D);
    f12 = paramViewingConditions.getNc();
    f2 = paramViewingConditions.getNcb();
    f2 = (float)Math.sqrt(f6 * f6 + f5 * f5) * (3846.1538F * (f13 * 0.25F) * f12 * f2) / (0.305F + f3);
    f5 = (float)Math.pow(1.64D - Math.pow(0.29D, paramViewingConditions.getN()), 0.73D) * (float)Math.pow(f2, 0.9D);
    f3 = (float)Math.sqrt(f7 / 100.0D) * f5;
    f2 = paramViewingConditions.getFlRoot() * f3;
    f6 = (float)Math.sqrt(paramViewingConditions.getC() * f5 / (paramViewingConditions.getAw() + 4.0F));
    f12 = 1.7F * f7 / (0.007F * f7 + 1.0F);
    f5 = (float)Math.log(0.0228F * f2 + 1.0F) * 43.85965F;
    return new CamColor(f1, f3, f7, f10 * f9 * (f8 + 4.0F) * f11, f2, f6 * 50.0F, f12, (float)Math.cos(f4) * f5, (float)Math.sin(f4) * f5);
  }
  
  private static CamColor fromJch(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return fromJchInFrame(paramFloat1, paramFloat2, paramFloat3, ViewingConditions.DEFAULT);
  }
  
  private static CamColor fromJchInFrame(float paramFloat1, float paramFloat2, float paramFloat3, ViewingConditions paramViewingConditions)
  {
    float f4 = 4.0F / paramViewingConditions.getC();
    float f2 = (float)Math.sqrt(paramFloat1 / 100.0D);
    float f5 = paramViewingConditions.getAw();
    float f3 = paramViewingConditions.getFlRoot();
    float f1 = paramFloat2 * paramViewingConditions.getFlRoot();
    float f6 = paramFloat2 / (float)Math.sqrt(paramFloat1 / 100.0D);
    float f8 = (float)Math.sqrt(paramViewingConditions.getC() * f6 / (paramViewingConditions.getAw() + 4.0F));
    float f9 = 3.1415927F * paramFloat3 / 180.0F;
    float f7 = 1.7F * paramFloat1 / (0.007F * paramFloat1 + 1.0F);
    f6 = (float)Math.log(f1 * 0.0228D + 1.0D) * 43.85965F;
    return new CamColor(paramFloat3, paramFloat2, paramFloat1, f4 * f2 * (f5 + 4.0F) * f3, f1, f8 * 50.0F, f7, f6 * (float)Math.cos(f9), f6 * (float)Math.sin(f9));
  }
  
  static int toColor(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return toColor(paramFloat1, paramFloat2, paramFloat3, ViewingConditions.DEFAULT);
  }
  
  static int toColor(float paramFloat1, float paramFloat2, float paramFloat3, ViewingConditions paramViewingConditions)
  {
    if ((paramFloat2 >= 1.0D) && (Math.round(paramFloat3) > 0.0D) && (Math.round(paramFloat3) < 100.0D))
    {
      float f1 = 0.0F;
      if (paramFloat1 >= 0.0F) {
        f1 = Math.min(360.0F, paramFloat1);
      }
      float f2 = paramFloat2;
      paramFloat1 = paramFloat2;
      paramFloat2 = 0.0F;
      int i = 1;
      Object localObject = null;
      while (Math.abs(paramFloat2 - f2) >= 0.4F)
      {
        CamColor localCamColor = findCamByJ(f1, paramFloat1, paramFloat3);
        if (i != 0)
        {
          if (localCamColor != null) {
            return localCamColor.viewed(paramViewingConditions);
          }
          i = 0;
          paramFloat1 = paramFloat2 + (f2 - paramFloat2) / 2.0F;
        }
        else
        {
          if (localCamColor == null)
          {
            f2 = paramFloat1;
          }
          else
          {
            localObject = localCamColor;
            paramFloat2 = paramFloat1;
          }
          paramFloat1 = paramFloat2 + (f2 - paramFloat2) / 2.0F;
        }
      }
      if (localObject == null) {
        return CamUtils.intFromLStar(paramFloat3);
      }
      return ((CamColor)localObject).viewed(paramViewingConditions);
    }
    return CamUtils.intFromLStar(paramFloat3);
  }
  
  float distance(CamColor paramCamColor)
  {
    float f2 = getJStar() - paramCamColor.getJStar();
    float f1 = getAStar() - paramCamColor.getAStar();
    float f3 = getBStar() - paramCamColor.getBStar();
    return (float)(Math.pow(Math.sqrt(f2 * f2 + f1 * f1 + f3 * f3), 0.63D) * 1.41D);
  }
  
  float getAStar()
  {
    return this.mAstar;
  }
  
  float getBStar()
  {
    return this.mBstar;
  }
  
  float getChroma()
  {
    return this.mChroma;
  }
  
  float getHue()
  {
    return this.mHue;
  }
  
  float getJ()
  {
    return this.mJ;
  }
  
  float getJStar()
  {
    return this.mJstar;
  }
  
  float getM()
  {
    return this.mM;
  }
  
  float getQ()
  {
    return this.mQ;
  }
  
  float getS()
  {
    return this.mS;
  }
  
  int viewed(ViewingConditions paramViewingConditions)
  {
    if ((getChroma() != 0.0D) && (getJ() != 0.0D)) {
      f1 = getChroma() / (float)Math.sqrt(getJ() / 100.0D);
    } else {
      f1 = 0.0F;
    }
    float f5 = (float)Math.pow(f1 / Math.pow(1.64D - Math.pow(0.29D, paramViewingConditions.getN()), 0.73D), 1.1111111111111112D);
    float f7 = getHue() * 3.1415927F / 180.0F;
    float f3 = (float)(Math.cos(f7 + 2.0D) + 3.8D);
    float f1 = paramViewingConditions.getAw();
    float f4 = (float)Math.pow(getJ() / 100.0D, 1.0D / paramViewingConditions.getC() / paramViewingConditions.getZ());
    float f6 = paramViewingConditions.getNc();
    float f2 = paramViewingConditions.getNcb();
    f1 = f1 * f4 / paramViewingConditions.getNbb();
    f4 = (float)Math.sin(f7);
    f7 = (float)Math.cos(f7);
    f3 = (0.305F + f1) * 23.0F * f5 / (23.0F * (3846.1538F * (f3 * 0.25F) * f6 * f2) + 11.0F * f5 * f7 + 108.0F * f5 * f4);
    f2 = f3 * f7;
    f5 = f3 * f4;
    f4 = (f1 * 460.0F + 451.0F * f2 + 288.0F * f5) / 1403.0F;
    f3 = (f1 * 460.0F - 891.0F * f2 - 261.0F * f5) / 1403.0F;
    f6 = (460.0F * f1 - 220.0F * f2 - 6300.0F * f5) / 1403.0F;
    f5 = (float)Math.max(0.0D, Math.abs(f4) * 27.13D / (400.0D - Math.abs(f4)));
    f2 = Math.signum(f4);
    f1 = 100.0F / paramViewingConditions.getFl();
    f4 = (float)Math.pow(f5, 2.380952380952381D);
    f7 = (float)Math.max(0.0D, Math.abs(f3) * 27.13D / (400.0D - Math.abs(f3)));
    f5 = Math.signum(f3);
    f3 = 100.0F / paramViewingConditions.getFl();
    f7 = (float)Math.pow(f7, 2.380952380952381D);
    float f9 = (float)Math.max(0.0D, Math.abs(f6) * 27.13D / (400.0D - Math.abs(f6)));
    f6 = Math.signum(f6);
    float f8 = 100.0F / paramViewingConditions.getFl();
    f9 = (float)Math.pow(f9, 2.380952380952381D);
    f1 = f2 * f1 * f4 / paramViewingConditions.getRgbD()[0];
    f2 = f5 * f3 * f7 / paramViewingConditions.getRgbD()[1];
    f4 = f6 * f8 * f9 / paramViewingConditions.getRgbD()[2];
    paramViewingConditions = CamUtils.CAM16RGB_TO_XYZ;
    f7 = paramViewingConditions[0][0];
    f9 = paramViewingConditions[0][1];
    float f11 = paramViewingConditions[0][2];
    f5 = paramViewingConditions[1][0];
    f8 = paramViewingConditions[1][1];
    f6 = paramViewingConditions[1][2];
    f3 = paramViewingConditions[2][0];
    float f12 = paramViewingConditions[2][1];
    float f10 = paramViewingConditions[2][2];
    return ColorUtils.XYZToColor(f7 * f1 + f9 * f2 + f11 * f4, f5 * f1 + f8 * f2 + f6 * f4, f3 * f1 + f12 * f2 + f10 * f4);
  }
  
  int viewedInSrgb()
  {
    return viewed(ViewingConditions.DEFAULT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/CamColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */