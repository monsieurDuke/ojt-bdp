package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AbsoluteCornerSize
  implements CornerSize
{
  private final float size;
  
  public AbsoluteCornerSize(float paramFloat)
  {
    this.size = paramFloat;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof AbsoluteCornerSize)) {
      return false;
    }
    paramObject = (AbsoluteCornerSize)paramObject;
    if (this.size != ((AbsoluteCornerSize)paramObject).size) {
      bool = false;
    }
    return bool;
  }
  
  public float getCornerSize()
  {
    return this.size;
  }
  
  public float getCornerSize(RectF paramRectF)
  {
    return this.size;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(new Object[] { Float.valueOf(this.size) });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/AbsoluteCornerSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */