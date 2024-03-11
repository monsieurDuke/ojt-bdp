package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;

public class Dimension
{
  public static final Object FIXED_DIMENSION = new Object();
  public static final Object PARENT_DIMENSION = new Object();
  public static final Object PERCENT_DIMENSION = new Object();
  public static final Object RATIO_DIMENSION = new Object();
  public static final Object SPREAD_DIMENSION;
  public static final Object WRAP_DIMENSION = new Object();
  private final int WRAP_CONTENT = -2;
  Object mInitialValue = WRAP_DIMENSION;
  boolean mIsSuggested = false;
  int mMax = Integer.MAX_VALUE;
  int mMin = 0;
  float mPercent = 1.0F;
  String mRatioString = null;
  int mValue = 0;
  
  static
  {
    SPREAD_DIMENSION = new Object();
  }
  
  private Dimension() {}
  
  private Dimension(Object paramObject)
  {
    this.mInitialValue = paramObject;
  }
  
  public static Dimension Fixed(int paramInt)
  {
    Dimension localDimension = new Dimension(FIXED_DIMENSION);
    localDimension.fixed(paramInt);
    return localDimension;
  }
  
  public static Dimension Fixed(Object paramObject)
  {
    Dimension localDimension = new Dimension(FIXED_DIMENSION);
    localDimension.fixed(paramObject);
    return localDimension;
  }
  
  public static Dimension Parent()
  {
    return new Dimension(PARENT_DIMENSION);
  }
  
  public static Dimension Percent(Object paramObject, float paramFloat)
  {
    Dimension localDimension = new Dimension(PERCENT_DIMENSION);
    localDimension.percent(paramObject, paramFloat);
    return localDimension;
  }
  
  public static Dimension Ratio(String paramString)
  {
    Dimension localDimension = new Dimension(RATIO_DIMENSION);
    localDimension.ratio(paramString);
    return localDimension;
  }
  
  public static Dimension Spread()
  {
    return new Dimension(SPREAD_DIMENSION);
  }
  
  public static Dimension Suggested(int paramInt)
  {
    Dimension localDimension = new Dimension();
    localDimension.suggested(paramInt);
    return localDimension;
  }
  
  public static Dimension Suggested(Object paramObject)
  {
    Dimension localDimension = new Dimension();
    localDimension.suggested(paramObject);
    return localDimension;
  }
  
  public static Dimension Wrap()
  {
    return new Dimension(WRAP_DIMENSION);
  }
  
  public void apply(State paramState, ConstraintWidget paramConstraintWidget, int paramInt)
  {
    paramState = this.mRatioString;
    if (paramState != null) {
      paramConstraintWidget.setDimensionRatio(paramState);
    }
    if (paramInt == 0)
    {
      if (this.mIsSuggested)
      {
        paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
        paramInt = 0;
        paramState = this.mInitialValue;
        if (paramState == WRAP_DIMENSION) {
          paramInt = 1;
        } else if (paramState == PERCENT_DIMENSION) {
          paramInt = 2;
        }
        paramConstraintWidget.setHorizontalMatchStyle(paramInt, this.mMin, this.mMax, this.mPercent);
      }
      else
      {
        paramInt = this.mMin;
        if (paramInt > 0) {
          paramConstraintWidget.setMinWidth(paramInt);
        }
        paramInt = this.mMax;
        if (paramInt < Integer.MAX_VALUE) {
          paramConstraintWidget.setMaxWidth(paramInt);
        }
        paramState = this.mInitialValue;
        if (paramState == WRAP_DIMENSION)
        {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }
        else if (paramState == PARENT_DIMENSION)
        {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
        }
        else if (paramState == null)
        {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
          paramConstraintWidget.setWidth(this.mValue);
        }
      }
    }
    else if (this.mIsSuggested)
    {
      paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
      paramInt = 0;
      paramState = this.mInitialValue;
      if (paramState == WRAP_DIMENSION) {
        paramInt = 1;
      } else if (paramState == PERCENT_DIMENSION) {
        paramInt = 2;
      }
      paramConstraintWidget.setVerticalMatchStyle(paramInt, this.mMin, this.mMax, this.mPercent);
    }
    else
    {
      paramInt = this.mMin;
      if (paramInt > 0) {
        paramConstraintWidget.setMinHeight(paramInt);
      }
      paramInt = this.mMax;
      if (paramInt < Integer.MAX_VALUE) {
        paramConstraintWidget.setMaxHeight(paramInt);
      }
      paramState = this.mInitialValue;
      if (paramState == WRAP_DIMENSION)
      {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
      }
      else if (paramState == PARENT_DIMENSION)
      {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
      }
      else if (paramState == null)
      {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setHeight(this.mValue);
      }
    }
  }
  
  public boolean equalsFixedValue(int paramInt)
  {
    return (this.mInitialValue == null) && (this.mValue == paramInt);
  }
  
  public Dimension fixed(int paramInt)
  {
    this.mInitialValue = null;
    this.mValue = paramInt;
    return this;
  }
  
  public Dimension fixed(Object paramObject)
  {
    this.mInitialValue = paramObject;
    if ((paramObject instanceof Integer))
    {
      this.mValue = ((Integer)paramObject).intValue();
      this.mInitialValue = null;
    }
    return this;
  }
  
  int getValue()
  {
    return this.mValue;
  }
  
  public Dimension max(int paramInt)
  {
    if (this.mMax >= 0) {
      this.mMax = paramInt;
    }
    return this;
  }
  
  public Dimension max(Object paramObject)
  {
    Object localObject = WRAP_DIMENSION;
    if ((paramObject == localObject) && (this.mIsSuggested))
    {
      this.mInitialValue = localObject;
      this.mMax = Integer.MAX_VALUE;
    }
    return this;
  }
  
  public Dimension min(int paramInt)
  {
    if (paramInt >= 0) {
      this.mMin = paramInt;
    }
    return this;
  }
  
  public Dimension min(Object paramObject)
  {
    if (paramObject == WRAP_DIMENSION) {
      this.mMin = -2;
    }
    return this;
  }
  
  public Dimension percent(Object paramObject, float paramFloat)
  {
    this.mPercent = paramFloat;
    return this;
  }
  
  public Dimension ratio(String paramString)
  {
    this.mRatioString = paramString;
    return this;
  }
  
  void setValue(int paramInt)
  {
    this.mIsSuggested = false;
    this.mInitialValue = null;
    this.mValue = paramInt;
  }
  
  public Dimension suggested(int paramInt)
  {
    this.mIsSuggested = true;
    if (paramInt >= 0) {
      this.mMax = paramInt;
    }
    return this;
  }
  
  public Dimension suggested(Object paramObject)
  {
    this.mInitialValue = paramObject;
    this.mIsSuggested = true;
    return this;
  }
  
  public static enum Type
  {
    private static final Type[] $VALUES;
    
    static
    {
      Type localType2 = new Type("FIXED", 0);
      FIXED = localType2;
      Type localType3 = new Type("WRAP", 1);
      WRAP = localType3;
      Type localType4 = new Type("MATCH_PARENT", 2);
      MATCH_PARENT = localType4;
      Type localType1 = new Type("MATCH_CONSTRAINT", 3);
      MATCH_CONSTRAINT = localType1;
      $VALUES = new Type[] { localType2, localType3, localType4, localType1 };
    }
    
    private Type() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/Dimension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */