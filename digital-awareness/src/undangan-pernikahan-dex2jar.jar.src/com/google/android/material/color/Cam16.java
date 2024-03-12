package com.google.android.material.color;

final class Cam16
{
  static final float[][] CAM16RGB_TO_XYZ;
  static final float[][] XYZ_TO_CAM16RGB;
  private final float astar;
  private final float bstar;
  private final float chroma;
  private final float hue;
  private final float j;
  private final float jstar;
  private final float m;
  private final float q;
  private final float s;
  
  static
  {
    float[] arrayOfFloat2 = { -0.250268F, 1.204414F, 0.045854F };
    float[] arrayOfFloat1 = { -0.002079F, 0.048952F, 0.953127F };
    XYZ_TO_CAM16RGB = new float[][] { { 0.401288F, 0.650173F, -0.051461F }, arrayOfFloat2, arrayOfFloat1 };
    arrayOfFloat1 = new float[] { 0.38752654F, 0.62144744F, -0.00897398F };
    CAM16RGB_TO_XYZ = new float[][] { { 1.8620678F, -1.0112547F, 0.14918678F }, arrayOfFloat1, { -0.0158415F, -0.03412294F, 1.0499644F } };
  }
  
  private Cam16(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.hue = paramFloat1;
    this.chroma = paramFloat2;
    this.j = paramFloat3;
    this.q = paramFloat4;
    this.m = paramFloat5;
    this.s = paramFloat6;
    this.jstar = paramFloat7;
    this.astar = paramFloat8;
    this.bstar = paramFloat9;
  }
  
  public static Cam16 fromInt(int paramInt)
  {
    return fromIntInViewingConditions(paramInt, ViewingConditions.DEFAULT);
  }
  
  static Cam16 fromIntInViewingConditions(int paramInt, ViewingConditions paramViewingConditions)
  {
    float f3 = ColorUtils.linearized(((0xFF0000 & paramInt) >> 16) / 255.0F) * 100.0F;
    float f4 = ColorUtils.linearized(((0xFF00 & paramInt) >> 8) / 255.0F) * 100.0F;
    float f5 = ColorUtils.linearized((paramInt & 0xFF) / 255.0F) * 100.0F;
    float f2 = 0.41233894F * f3 + 0.35762063F * f4 + 0.18051042F * f5;
    float f1 = 0.2126F * f3 + 0.7152F * f4 + 0.0722F * f5;
    float f6 = 0.01932141F * f3 + 0.11916382F * f4 + 0.9503448F * f5;
    float[][] arrayOfFloat = XYZ_TO_CAM16RGB;
    f3 = arrayOfFloat[0][0];
    float f11 = arrayOfFloat[0][1];
    float f12 = arrayOfFloat[0][2];
    float f10 = arrayOfFloat[1][0];
    float f8 = arrayOfFloat[1][1];
    float f9 = arrayOfFloat[1][2];
    f4 = arrayOfFloat[2][0];
    float f7 = arrayOfFloat[2][1];
    f5 = arrayOfFloat[2][2];
    f3 = paramViewingConditions.getRgbD()[0] * (f3 * f2 + f11 * f1 + f12 * f6);
    f8 = paramViewingConditions.getRgbD()[1] * (f10 * f2 + f8 * f1 + f9 * f6);
    f2 = paramViewingConditions.getRgbD()[2] * (f4 * f2 + f7 * f1 + f5 * f6);
    f1 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f3) / 100.0D, 0.42D);
    f5 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f8) / 100.0D, 0.42D);
    f4 = (float)Math.pow(paramViewingConditions.getFl() * Math.abs(f2) / 100.0D, 0.42D);
    f1 = Math.signum(f3) * 400.0F * f1 / (f1 + 27.13F);
    f5 = Math.signum(f8) * 400.0F * f5 / (f5 + 27.13F);
    f2 = Math.signum(f2) * 400.0F * f4 / (27.13F + f4);
    f4 = (float)(f1 * 11.0D + f5 * -12.0D + f2) / 11.0F;
    f3 = (float)(f1 + f5 - f2 * 2.0D) / 9.0F;
    f6 = (f1 * 20.0F + f5 * 20.0F + 21.0F * f2) / 20.0F;
    f2 = (40.0F * f1 + f5 * 20.0F + f2) / 20.0F;
    f1 = (float)Math.atan2(f3, f4) * 180.0F / 3.1415927F;
    if (f1 < 0.0F) {
      f1 += 360.0F;
    } else if (f1 >= 360.0F) {
      f1 -= 360.0F;
    }
    f5 = f1 * 3.1415927F / 180.0F;
    f7 = paramViewingConditions.getNbb();
    f8 = (float)Math.pow(f7 * f2 / paramViewingConditions.getAw(), paramViewingConditions.getC() * paramViewingConditions.getZ()) * 100.0F;
    f7 = 4.0F / paramViewingConditions.getC();
    f9 = (float)Math.sqrt(f8 / 100.0F);
    f10 = paramViewingConditions.getAw();
    f11 = paramViewingConditions.getFlRoot();
    if (f1 < 20.14D) {
      f2 = f1 + 360.0F;
    } else {
      f2 = f1;
    }
    f12 = (float)(Math.cos(Math.toRadians(f2) + 2.0D) + 3.8D);
    float f13 = paramViewingConditions.getNc();
    f2 = paramViewingConditions.getNcb();
    f2 = (float)Math.hypot(f4, f3) * (3846.1538F * (f12 * 0.25F) * f13 * f2) / (0.305F + f6);
    f4 = (float)Math.pow(1.64D - Math.pow(0.29D, paramViewingConditions.getN()), 0.73D) * (float)Math.pow(f2, 0.9D);
    f2 = (float)Math.sqrt(f8 / 100.0D) * f4;
    f3 = paramViewingConditions.getFlRoot() * f2;
    f4 = (float)Math.sqrt(paramViewingConditions.getC() * f4 / (paramViewingConditions.getAw() + 4.0F));
    f6 = 1.7F * f8 / (0.007F * f8 + 1.0F);
    f12 = (float)Math.log1p(f3 * 0.0228F) * 43.85965F;
    return new Cam16(f1, f2, f8, f7 * f9 * (f10 + 4.0F) * f11, f3, f4 * 50.0F, f6, (float)Math.cos(f5) * f12, (float)Math.sin(f5) * f12);
  }
  
  static Cam16 fromJch(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return fromJchInViewingConditions(paramFloat1, paramFloat2, paramFloat3, ViewingConditions.DEFAULT);
  }
  
  private static Cam16 fromJchInViewingConditions(float paramFloat1, float paramFloat2, float paramFloat3, ViewingConditions paramViewingConditions)
  {
    float f5 = 4.0F / paramViewingConditions.getC();
    float f1 = (float)Math.sqrt(paramFloat1 / 100.0D);
    float f3 = paramViewingConditions.getAw();
    float f4 = paramViewingConditions.getFlRoot();
    float f2 = paramFloat2 * paramViewingConditions.getFlRoot();
    float f6 = paramFloat2 / (float)Math.sqrt(paramFloat1 / 100.0D);
    f6 = (float)Math.sqrt(paramViewingConditions.getC() * f6 / (paramViewingConditions.getAw() + 4.0F));
    float f7 = 3.1415927F * paramFloat3 / 180.0F;
    float f8 = 1.7F * paramFloat1 / (0.007F * paramFloat1 + 1.0F);
    float f9 = (float)Math.log1p(f2 * 0.0228D) * 43.85965F;
    return new Cam16(paramFloat3, paramFloat2, paramFloat1, f5 * f1 * (f3 + 4.0F) * f4, f2, f6 * 50.0F, f8, f9 * (float)Math.cos(f7), f9 * (float)Math.sin(f7));
  }
  
  public static Cam16 fromUcs(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return fromUcsInViewingConditions(paramFloat1, paramFloat2, paramFloat3, ViewingConditions.DEFAULT);
  }
  
  public static Cam16 fromUcsInViewingConditions(float paramFloat1, float paramFloat2, float paramFloat3, ViewingConditions paramViewingConditions)
  {
    double d3 = Math.expm1(Math.hypot(paramFloat2, paramFloat3) * 0.02280000038444996D) / 0.02280000038444996D / paramViewingConditions.getFlRoot();
    double d2 = Math.atan2(paramFloat3, paramFloat2) * 57.29577951308232D;
    double d1 = d2;
    if (d2 < 0.0D) {
      d1 = d2 + 360.0D;
    }
    return fromJchInViewingConditions(paramFloat1 / (1.0F - (paramFloat1 - 100.0F) * 0.007F), (float)d3, (float)d1, paramViewingConditions);
  }
  
  float distance(Cam16 paramCam16)
  {
    float f2 = getJStar() - paramCam16.getJStar();
    float f1 = getAStar() - paramCam16.getAStar();
    float f3 = getBStar() - paramCam16.getBStar();
    return (float)(Math.pow(Math.sqrt(f2 * f2 + f1 * f1 + f3 * f3), 0.63D) * 1.41D);
  }
  
  public float getAStar()
  {
    return this.astar;
  }
  
  public float getBStar()
  {
    return this.bstar;
  }
  
  public float getChroma()
  {
    return this.chroma;
  }
  
  public float getHue()
  {
    return this.hue;
  }
  
  public int getInt()
  {
    return viewed(ViewingConditions.DEFAULT);
  }
  
  public float getJ()
  {
    return this.j;
  }
  
  public float getJStar()
  {
    return this.jstar;
  }
  
  public float getM()
  {
    return this.m;
  }
  
  public float getQ()
  {
    return this.q;
  }
  
  public float getS()
  {
    return this.s;
  }
  
  int viewed(ViewingConditions paramViewingConditions)
  {
    if ((getChroma() != 0.0D) && (getJ() != 0.0D)) {
      f1 = getChroma() / (float)Math.sqrt(getJ() / 100.0D);
    } else {
      f1 = 0.0F;
    }
    float f2 = (float)Math.pow(f1 / Math.pow(1.64D - Math.pow(0.29D, paramViewingConditions.getN()), 0.73D), 1.1111111111111112D);
    float f7 = getHue() * 3.1415927F / 180.0F;
    float f6 = (float)(Math.cos(f7 + 2.0D) + 3.8D);
    float f1 = paramViewingConditions.getAw();
    float f5 = (float)Math.pow(getJ() / 100.0D, 1.0D / paramViewingConditions.getC() / paramViewingConditions.getZ());
    float f3 = paramViewingConditions.getNc();
    float f4 = paramViewingConditions.getNcb();
    f1 = f1 * f5 / paramViewingConditions.getNbb();
    f5 = (float)Math.sin(f7);
    f7 = (float)Math.cos(f7);
    f2 = (0.305F + f1) * 23.0F * f2 / (23.0F * (3846.1538F * (f6 * 0.25F) * f3 * f4) + 11.0F * f2 * f7 + 108.0F * f2 * f5);
    f3 = f2 * f7;
    f5 = f2 * f5;
    f2 = (f1 * 460.0F + 451.0F * f3 + 288.0F * f5) / 1403.0F;
    f4 = (f1 * 460.0F - 891.0F * f3 - 261.0F * f5) / 1403.0F;
    f6 = (460.0F * f1 - 220.0F * f3 - 6300.0F * f5) / 1403.0F;
    f3 = (float)Math.max(0.0D, Math.abs(f2) * 27.13D / (400.0D - Math.abs(f2)));
    f1 = Math.signum(f2);
    f2 = 100.0F / paramViewingConditions.getFl();
    f3 = (float)Math.pow(f3, 2.380952380952381D);
    f7 = (float)Math.max(0.0D, Math.abs(f4) * 27.13D / (400.0D - Math.abs(f4)));
    f5 = Math.signum(f4);
    f4 = 100.0F / paramViewingConditions.getFl();
    float f8 = (float)Math.pow(f7, 2.380952380952381D);
    float f9 = (float)Math.max(0.0D, Math.abs(f6) * 27.13D / (400.0D - Math.abs(f6)));
    f6 = Math.signum(f6);
    f7 = 100.0F / paramViewingConditions.getFl();
    f9 = (float)Math.pow(f9, 2.380952380952381D);
    f1 = f1 * f2 * f3 / paramViewingConditions.getRgbD()[0];
    f2 = f5 * f4 * f8 / paramViewingConditions.getRgbD()[1];
    f3 = f6 * f7 * f9 / paramViewingConditions.getRgbD()[2];
    paramViewingConditions = CAM16RGB_TO_XYZ;
    return ColorUtils.intFromXyzComponents(paramViewingConditions[0][0] * f1 + paramViewingConditions[0][1] * f2 + paramViewingConditions[0][2] * f3, paramViewingConditions[1][0] * f1 + paramViewingConditions[1][1] * f2 + paramViewingConditions[1][2] * f3, paramViewingConditions[2][0] * f1 + paramViewingConditions[2][1] * f2 + paramViewingConditions[2][2] * f3);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/Cam16.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */