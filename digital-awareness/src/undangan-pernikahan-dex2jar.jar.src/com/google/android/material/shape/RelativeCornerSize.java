package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class RelativeCornerSize
  implements CornerSize
{
  private final float percent;
  
  public RelativeCornerSize(float paramFloat)
  {
    this.percent = paramFloat;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof RelativeCornerSize)) {
      return false;
    }
    paramObject = (RelativeCornerSize)paramObject;
    if (this.percent != ((RelativeCornerSize)paramObject).percent) {
      bool = false;
    }
    return bool;
  }
  
  public float getCornerSize(RectF paramRectF)
  {
    return this.percent * paramRectF.height();
  }
  
  public float getRelativePercent()
  {
    return this.percent;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(new Object[] { Float.valueOf(this.percent) });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/RelativeCornerSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */