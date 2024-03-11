package com.google.android.material.shape;

public final class OffsetEdgeTreatment
  extends EdgeTreatment
{
  private final float offset;
  private final EdgeTreatment other;
  
  public OffsetEdgeTreatment(EdgeTreatment paramEdgeTreatment, float paramFloat)
  {
    this.other = paramEdgeTreatment;
    this.offset = paramFloat;
  }
  
  boolean forceIntersection()
  {
    return this.other.forceIntersection();
  }
  
  public void getEdgePath(float paramFloat1, float paramFloat2, float paramFloat3, ShapePath paramShapePath)
  {
    this.other.getEdgePath(paramFloat1, paramFloat2 - this.offset, paramFloat3, paramShapePath);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/OffsetEdgeTreatment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */