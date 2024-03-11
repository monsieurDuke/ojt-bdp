package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CircularProgressIndicator
  extends BaseProgressIndicator<CircularProgressIndicatorSpec>
{
  public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CircularProgressIndicator;
  public static final int INDICATOR_DIRECTION_CLOCKWISE = 0;
  public static final int INDICATOR_DIRECTION_COUNTERCLOCKWISE = 1;
  
  public CircularProgressIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CircularProgressIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.circularProgressIndicatorStyle);
  }
  
  public CircularProgressIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt, DEF_STYLE_RES);
    initializeDrawables();
  }
  
  private void initializeDrawables()
  {
    setIndeterminateDrawable(IndeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec)this.spec));
    setProgressDrawable(DeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec)this.spec));
  }
  
  CircularProgressIndicatorSpec createSpec(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new CircularProgressIndicatorSpec(paramContext, paramAttributeSet);
  }
  
  public int getIndicatorDirection()
  {
    return ((CircularProgressIndicatorSpec)this.spec).indicatorDirection;
  }
  
  public int getIndicatorInset()
  {
    return ((CircularProgressIndicatorSpec)this.spec).indicatorInset;
  }
  
  public int getIndicatorSize()
  {
    return ((CircularProgressIndicatorSpec)this.spec).indicatorSize;
  }
  
  public void setIndicatorDirection(int paramInt)
  {
    ((CircularProgressIndicatorSpec)this.spec).indicatorDirection = paramInt;
    invalidate();
  }
  
  public void setIndicatorInset(int paramInt)
  {
    if (((CircularProgressIndicatorSpec)this.spec).indicatorInset != paramInt)
    {
      ((CircularProgressIndicatorSpec)this.spec).indicatorInset = paramInt;
      invalidate();
    }
  }
  
  public void setIndicatorSize(int paramInt)
  {
    paramInt = Math.max(paramInt, getTrackThickness() * 2);
    if (((CircularProgressIndicatorSpec)this.spec).indicatorSize != paramInt)
    {
      ((CircularProgressIndicatorSpec)this.spec).indicatorSize = paramInt;
      ((CircularProgressIndicatorSpec)this.spec).validateSpec();
      invalidate();
    }
  }
  
  public void setTrackThickness(int paramInt)
  {
    super.setTrackThickness(paramInt);
    ((CircularProgressIndicatorSpec)this.spec).validateSpec();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IndicatorDirection {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/CircularProgressIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */