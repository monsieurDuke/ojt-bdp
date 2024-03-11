package com.google.android.material.shape;

public class CutCornerTreatment
  extends CornerTreatment
{
  float size = -1.0F;
  
  public CutCornerTreatment() {}
  
  @Deprecated
  public CutCornerTreatment(float paramFloat)
  {
    this.size = paramFloat;
  }
  
  public void getCornerPath(ShapePath paramShapePath, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramShapePath.reset(0.0F, paramFloat3 * paramFloat2, 180.0F, 180.0F - paramFloat1);
    paramShapePath.lineTo((float)(Math.sin(Math.toRadians(paramFloat1)) * paramFloat3 * paramFloat2), (float)(Math.sin(Math.toRadians(90.0F - paramFloat1)) * paramFloat3 * paramFloat2));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/CutCornerTreatment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */