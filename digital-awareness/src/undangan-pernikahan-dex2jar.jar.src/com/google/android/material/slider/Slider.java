package com.google.android.material.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.R.attr;
import java.util.List;

public class Slider
  extends BaseSlider<Slider, OnChangeListener, OnSliderTouchListener>
{
  public Slider(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public Slider(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.sliderStyle);
  }
  
  public Slider(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16842788 });
    if (paramContext.hasValue(0)) {
      setValue(paramContext.getFloat(0, 0.0F));
    }
    paramContext.recycle();
  }
  
  public float getValue()
  {
    return ((Float)getValues().get(0)).floatValue();
  }
  
  protected boolean pickActiveThumb()
  {
    if (getActiveThumbIndex() != -1) {
      return true;
    }
    setActiveThumbIndex(0);
    return true;
  }
  
  public void setCustomThumbDrawable(int paramInt)
  {
    super.setCustomThumbDrawable(paramInt);
  }
  
  public void setCustomThumbDrawable(Drawable paramDrawable)
  {
    super.setCustomThumbDrawable(paramDrawable);
  }
  
  public void setValue(float paramFloat)
  {
    setValues(new Float[] { Float.valueOf(paramFloat) });
  }
  
  public static abstract interface OnChangeListener
    extends BaseOnChangeListener<Slider>
  {
    public abstract void onValueChange(Slider paramSlider, float paramFloat, boolean paramBoolean);
  }
  
  public static abstract interface OnSliderTouchListener
    extends BaseOnSliderTouchListener<Slider>
  {
    public abstract void onStartTrackingTouch(Slider paramSlider);
    
    public abstract void onStopTrackingTouch(Slider paramSlider);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/slider/Slider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */