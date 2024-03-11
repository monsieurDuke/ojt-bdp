package androidx.constraintlayout.core.motion;

public class CustomAttribute
{
  private static final String TAG = "TransitionLayout";
  boolean mBooleanValue;
  private int mColorValue;
  private float mFloatValue;
  private int mIntegerValue;
  private boolean mMethod = false;
  String mName;
  private String mStringValue;
  private AttributeType mType;
  
  public CustomAttribute(CustomAttribute paramCustomAttribute, Object paramObject)
  {
    this.mName = paramCustomAttribute.mName;
    this.mType = paramCustomAttribute.mType;
    setValue(paramObject);
  }
  
  public CustomAttribute(String paramString, AttributeType paramAttributeType)
  {
    this.mName = paramString;
    this.mType = paramAttributeType;
  }
  
  public CustomAttribute(String paramString, AttributeType paramAttributeType, Object paramObject, boolean paramBoolean)
  {
    this.mName = paramString;
    this.mType = paramAttributeType;
    this.mMethod = paramBoolean;
    setValue(paramObject);
  }
  
  private static int clamp(int paramInt)
  {
    paramInt = (paramInt & (paramInt >> 31 ^ 0xFFFFFFFF)) - 255;
    return (paramInt & paramInt >> 31) + 255;
  }
  
  public static int hsvToRgb(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int k = (int)(paramFloat1 * 6.0F);
    paramFloat1 = 6.0F * paramFloat1 - k;
    int i = (int)(paramFloat3 * 255.0F * (1.0F - paramFloat2) + 0.5F);
    int n = (int)(paramFloat3 * 255.0F * (1.0F - paramFloat1 * paramFloat2) + 0.5F);
    int j = (int)(paramFloat3 * 255.0F * (1.0F - (1.0F - paramFloat1) * paramFloat2) + 0.5F);
    int m = (int)(255.0F * paramFloat3 + 0.5F);
    switch (k)
    {
    default: 
      return 0;
    case 5: 
      return 0xFF000000 | (m << 16) + (i << 8) + n;
    case 4: 
      return 0xFF000000 | (j << 16) + (i << 8) + m;
    case 3: 
      return 0xFF000000 | (i << 16) + (n << 8) + m;
    case 2: 
      return 0xFF000000 | (i << 16) + (m << 8) + j;
    case 1: 
      return 0xFF000000 | (n << 16) + (m << 8) + i;
    }
    return 0xFF000000 | (m << 16) + (j << 8) + i;
  }
  
  public boolean diff(CustomAttribute paramCustomAttribute)
  {
    boolean bool3 = false;
    boolean bool6 = false;
    boolean bool5 = false;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool4 = false;
    if ((paramCustomAttribute != null) && (this.mType == paramCustomAttribute.mType))
    {
      switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
      {
      default: 
        return false;
      case 8: 
        bool1 = bool4;
        if (this.mFloatValue == paramCustomAttribute.mFloatValue) {
          bool1 = true;
        }
        return bool1;
      case 7: 
        bool1 = bool3;
        if (this.mFloatValue == paramCustomAttribute.mFloatValue) {
          bool1 = true;
        }
        return bool1;
      case 4: 
      case 5: 
        bool1 = bool6;
        if (this.mColorValue == paramCustomAttribute.mColorValue) {
          bool1 = true;
        }
        return bool1;
      case 3: 
        bool1 = bool5;
        if (this.mIntegerValue == paramCustomAttribute.mIntegerValue) {
          bool1 = true;
        }
        return bool1;
      case 2: 
        if (this.mBooleanValue == paramCustomAttribute.mBooleanValue) {
          bool1 = true;
        }
        return bool1;
      }
      bool1 = bool2;
      if (this.mIntegerValue == paramCustomAttribute.mIntegerValue) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  public AttributeType getType()
  {
    return this.mType;
  }
  
  public float getValueToInterpolate()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return NaN.0F;
    case 8: 
      return this.mFloatValue;
    case 7: 
      return this.mFloatValue;
    case 6: 
      return this.mIntegerValue;
    case 4: 
    case 5: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 3: 
      throw new RuntimeException("Cannot interpolate String");
    }
    float f;
    if (this.mBooleanValue) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    return f;
  }
  
  public void getValuesToInterpolate(float[] paramArrayOfFloat)
  {
    float f1;
    switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      break;
    case 8: 
      paramArrayOfFloat[0] = this.mFloatValue;
      break;
    case 7: 
      paramArrayOfFloat[0] = this.mFloatValue;
      break;
    case 6: 
      paramArrayOfFloat[0] = this.mIntegerValue;
      break;
    case 4: 
    case 5: 
      int i = this.mColorValue;
      f1 = (float)Math.pow((i >> 16 & 0xFF) / 255.0F, 2.2D);
      float f2 = (float)Math.pow((i >> 8 & 0xFF) / 255.0F, 2.2D);
      float f3 = (float)Math.pow((i & 0xFF) / 255.0F, 2.2D);
      paramArrayOfFloat[0] = f1;
      paramArrayOfFloat[1] = f2;
      paramArrayOfFloat[2] = f3;
      paramArrayOfFloat[3] = ((i >> 24 & 0xFF) / 255.0F);
      break;
    case 3: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 2: 
      if (this.mBooleanValue) {
        f1 = 1.0F;
      } else {
        f1 = 0.0F;
      }
      paramArrayOfFloat[0] = f1;
    }
  }
  
  public boolean isContinuous()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return true;
    }
    return false;
  }
  
  public int numberOfInterpolatedValues()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return 1;
    }
    return 4;
  }
  
  public void setColorValue(int paramInt)
  {
    this.mColorValue = paramInt;
  }
  
  public void setFloatValue(float paramFloat)
  {
    this.mFloatValue = paramFloat;
  }
  
  public void setIntValue(int paramInt)
  {
    this.mIntegerValue = paramInt;
  }
  
  public void setStringValue(String paramString)
  {
    this.mStringValue = paramString;
  }
  
  public void setValue(Object paramObject)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      break;
    case 8: 
      this.mFloatValue = ((Float)paramObject).floatValue();
      break;
    case 7: 
      this.mFloatValue = ((Float)paramObject).floatValue();
      break;
    case 4: 
    case 5: 
      this.mColorValue = ((Integer)paramObject).intValue();
      break;
    case 3: 
      this.mStringValue = ((String)paramObject);
      break;
    case 2: 
      this.mBooleanValue = ((Boolean)paramObject).booleanValue();
      break;
    case 1: 
    case 6: 
      this.mIntegerValue = ((Integer)paramObject).intValue();
    }
  }
  
  public void setValue(float[] paramArrayOfFloat)
  {
    int i = 1.$SwitchMap$androidx$constraintlayout$core$motion$CustomAttribute$AttributeType[this.mType.ordinal()];
    boolean bool = true;
    switch (i)
    {
    default: 
      break;
    case 8: 
      this.mFloatValue = paramArrayOfFloat[0];
      break;
    case 7: 
      this.mFloatValue = paramArrayOfFloat[0];
      break;
    case 4: 
    case 5: 
      i = hsvToRgb(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2]);
      this.mColorValue = i;
      this.mColorValue = (i & 0xFFFFFF | clamp((int)(paramArrayOfFloat[3] * 255.0F)) << 24);
      break;
    case 3: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 2: 
      if (paramArrayOfFloat[0] <= 0.5D) {
        bool = false;
      }
      this.mBooleanValue = bool;
      break;
    case 1: 
    case 6: 
      this.mIntegerValue = ((int)paramArrayOfFloat[0]);
    }
  }
  
  public static enum AttributeType
  {
    private static final AttributeType[] $VALUES;
    
    static
    {
      AttributeType localAttributeType4 = new AttributeType("INT_TYPE", 0);
      INT_TYPE = localAttributeType4;
      AttributeType localAttributeType1 = new AttributeType("FLOAT_TYPE", 1);
      FLOAT_TYPE = localAttributeType1;
      AttributeType localAttributeType7 = new AttributeType("COLOR_TYPE", 2);
      COLOR_TYPE = localAttributeType7;
      AttributeType localAttributeType6 = new AttributeType("COLOR_DRAWABLE_TYPE", 3);
      COLOR_DRAWABLE_TYPE = localAttributeType6;
      AttributeType localAttributeType5 = new AttributeType("STRING_TYPE", 4);
      STRING_TYPE = localAttributeType5;
      AttributeType localAttributeType3 = new AttributeType("BOOLEAN_TYPE", 5);
      BOOLEAN_TYPE = localAttributeType3;
      AttributeType localAttributeType8 = new AttributeType("DIMENSION_TYPE", 6);
      DIMENSION_TYPE = localAttributeType8;
      AttributeType localAttributeType2 = new AttributeType("REFERENCE_TYPE", 7);
      REFERENCE_TYPE = localAttributeType2;
      $VALUES = new AttributeType[] { localAttributeType4, localAttributeType1, localAttributeType7, localAttributeType6, localAttributeType5, localAttributeType3, localAttributeType8, localAttributeType2 };
    }
    
    private AttributeType() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/CustomAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */