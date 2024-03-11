package com.google.android.material.shape;

import android.graphics.RectF;

public class CornerTreatment
{
  @Deprecated
  public void getCornerPath(float paramFloat1, float paramFloat2, ShapePath paramShapePath) {}
  
  public void getCornerPath(ShapePath paramShapePath, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    getCornerPath(paramFloat1, paramFloat2, paramShapePath);
  }
  
  public void getCornerPath(ShapePath paramShapePath, float paramFloat1, float paramFloat2, RectF paramRectF, CornerSize paramCornerSize)
  {
    getCornerPath(paramShapePath, paramFloat1, paramFloat2, paramCornerSize.getCornerSize(paramRectF));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/CornerTreatment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */