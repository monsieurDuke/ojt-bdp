package androidx.core.util;

import android.util.SizeF;

public final class SizeFCompat
{
  private final float mHeight;
  private final float mWidth;
  
  public SizeFCompat(float paramFloat1, float paramFloat2)
  {
    this.mWidth = Preconditions.checkArgumentFinite(paramFloat1, "width");
    this.mHeight = Preconditions.checkArgumentFinite(paramFloat2, "height");
  }
  
  public static SizeFCompat toSizeFCompat(SizeF paramSizeF)
  {
    return Api21Impl.toSizeFCompat(paramSizeF);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof SizeFCompat)) {
      return false;
    }
    paramObject = (SizeFCompat)paramObject;
    if ((((SizeFCompat)paramObject).mWidth != this.mWidth) || (((SizeFCompat)paramObject).mHeight != this.mHeight)) {
      bool = false;
    }
    return bool;
  }
  
  public float getHeight()
  {
    return this.mHeight;
  }
  
  public float getWidth()
  {
    return this.mWidth;
  }
  
  public int hashCode()
  {
    return Float.floatToIntBits(this.mWidth) ^ Float.floatToIntBits(this.mHeight);
  }
  
  public SizeF toSizeF()
  {
    return Api21Impl.toSizeF(this);
  }
  
  public String toString()
  {
    return this.mWidth + "x" + this.mHeight;
  }
  
  private static final class Api21Impl
  {
    static SizeF toSizeF(SizeFCompat paramSizeFCompat)
    {
      Preconditions.checkNotNull(paramSizeFCompat);
      return new SizeF(paramSizeFCompat.getWidth(), paramSizeFCompat.getHeight());
    }
    
    static SizeFCompat toSizeFCompat(SizeF paramSizeF)
    {
      Preconditions.checkNotNull(paramSizeF);
      return new SizeFCompat(paramSizeF.getWidth(), paramSizeF.getHeight());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/SizeFCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */