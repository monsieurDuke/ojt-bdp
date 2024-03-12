package androidx.constraintlayout.core.parser;

public class CLNumber
  extends CLElement
{
  float value = NaN.0F;
  
  public CLNumber(float paramFloat)
  {
    super(null);
    this.value = paramFloat;
  }
  
  public CLNumber(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLNumber(paramArrayOfChar);
  }
  
  public float getFloat()
  {
    if (Float.isNaN(this.value)) {
      this.value = Float.parseFloat(content());
    }
    return this.value;
  }
  
  public int getInt()
  {
    if (Float.isNaN(this.value)) {
      this.value = Integer.parseInt(content());
    }
    return (int)this.value;
  }
  
  public boolean isInt()
  {
    float f = getFloat();
    boolean bool;
    if ((int)f == f) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void putValue(float paramFloat)
  {
    this.value = paramFloat;
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    addIndent(localStringBuilder, paramInt1);
    float f = getFloat();
    paramInt1 = (int)f;
    if (paramInt1 == f) {
      localStringBuilder.append(paramInt1);
    } else {
      localStringBuilder.append(f);
    }
    return localStringBuilder.toString();
  }
  
  protected String toJSON()
  {
    float f = getFloat();
    int i = (int)f;
    if (i == f) {
      return "" + i;
    }
    return "" + f;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */