package com.google.android.material.slider;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import java.util.ArrayList;
import java.util.List;

public class RangeSlider
  extends BaseSlider<RangeSlider, OnChangeListener, OnSliderTouchListener>
{
  private float minSeparation;
  private int separationUnit;
  
  public RangeSlider(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RangeSlider(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.sliderStyle);
  }
  
  public RangeSlider(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.RangeSlider, paramInt, DEF_STYLE_RES, new int[0]);
    if (paramContext.hasValue(R.styleable.RangeSlider_values))
    {
      paramInt = paramContext.getResourceId(R.styleable.RangeSlider_values, 0);
      setValues(convertToFloat(paramContext.getResources().obtainTypedArray(paramInt)));
    }
    this.minSeparation = paramContext.getDimension(R.styleable.RangeSlider_minSeparation, 0.0F);
    paramContext.recycle();
  }
  
  private static List<Float> convertToFloat(TypedArray paramTypedArray)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramTypedArray.length(); i++) {
      localArrayList.add(Float.valueOf(paramTypedArray.getFloat(i, -1.0F)));
    }
    return localArrayList;
  }
  
  public float getMinSeparation()
  {
    return this.minSeparation;
  }
  
  public List<Float> getValues()
  {
    return super.getValues();
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (RangeSliderState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.minSeparation = paramParcelable.minSeparation;
    int i = paramParcelable.separationUnit;
    this.separationUnit = i;
    setSeparationUnit(i);
  }
  
  public Parcelable onSaveInstanceState()
  {
    RangeSliderState localRangeSliderState = new RangeSliderState(super.onSaveInstanceState());
    RangeSliderState.access$002(localRangeSliderState, this.minSeparation);
    RangeSliderState.access$102(localRangeSliderState, this.separationUnit);
    return localRangeSliderState;
  }
  
  public void setCustomThumbDrawable(int paramInt)
  {
    super.setCustomThumbDrawable(paramInt);
  }
  
  public void setCustomThumbDrawable(Drawable paramDrawable)
  {
    super.setCustomThumbDrawable(paramDrawable);
  }
  
  public void setCustomThumbDrawablesForValues(int... paramVarArgs)
  {
    super.setCustomThumbDrawablesForValues(paramVarArgs);
  }
  
  public void setCustomThumbDrawablesForValues(Drawable... paramVarArgs)
  {
    super.setCustomThumbDrawablesForValues(paramVarArgs);
  }
  
  public void setMinSeparation(float paramFloat)
  {
    this.minSeparation = paramFloat;
    this.separationUnit = 0;
    setSeparationUnit(0);
  }
  
  public void setMinSeparationValue(float paramFloat)
  {
    this.minSeparation = paramFloat;
    this.separationUnit = 1;
    setSeparationUnit(1);
  }
  
  public void setValues(List<Float> paramList)
  {
    super.setValues(paramList);
  }
  
  public void setValues(Float... paramVarArgs)
  {
    super.setValues(paramVarArgs);
  }
  
  public static abstract interface OnChangeListener
    extends BaseOnChangeListener<RangeSlider>
  {
    public abstract void onValueChange(RangeSlider paramRangeSlider, float paramFloat, boolean paramBoolean);
  }
  
  public static abstract interface OnSliderTouchListener
    extends BaseOnSliderTouchListener<RangeSlider>
  {
    public abstract void onStartTrackingTouch(RangeSlider paramRangeSlider);
    
    public abstract void onStopTrackingTouch(RangeSlider paramRangeSlider);
  }
  
  static class RangeSliderState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<RangeSliderState> CREATOR = new Parcelable.Creator()
    {
      public RangeSlider.RangeSliderState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new RangeSlider.RangeSliderState(paramAnonymousParcel, null);
      }
      
      public RangeSlider.RangeSliderState[] newArray(int paramAnonymousInt)
      {
        return new RangeSlider.RangeSliderState[paramAnonymousInt];
      }
    };
    private float minSeparation;
    private int separationUnit;
    
    private RangeSliderState(Parcel paramParcel)
    {
      super();
      this.minSeparation = paramParcel.readFloat();
      this.separationUnit = paramParcel.readInt();
    }
    
    RangeSliderState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeFloat(this.minSeparation);
      paramParcel.writeInt(this.separationUnit);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/slider/RangeSlider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */