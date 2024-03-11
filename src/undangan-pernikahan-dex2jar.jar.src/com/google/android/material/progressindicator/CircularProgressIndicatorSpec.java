package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public final class CircularProgressIndicatorSpec
  extends BaseProgressIndicatorSpec
{
  public int indicatorDirection;
  public int indicatorInset;
  public int indicatorSize;
  
  public CircularProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.circularProgressIndicatorStyle);
  }
  
  public CircularProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, CircularProgressIndicator.DEF_STYLE_RES);
  }
  
  public CircularProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    int j = paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_circular_size_medium);
    int i = paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_circular_inset_medium);
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.CircularProgressIndicator, paramInt1, paramInt2, new int[0]);
    this.indicatorSize = Math.max(MaterialResources.getDimensionPixelSize(paramContext, paramAttributeSet, R.styleable.CircularProgressIndicator_indicatorSize, j), this.trackThickness * 2);
    this.indicatorInset = MaterialResources.getDimensionPixelSize(paramContext, paramAttributeSet, R.styleable.CircularProgressIndicator_indicatorInset, i);
    this.indicatorDirection = paramAttributeSet.getInt(R.styleable.CircularProgressIndicator_indicatorDirectionCircular, 0);
    paramAttributeSet.recycle();
    validateSpec();
  }
  
  void validateSpec() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/CircularProgressIndicatorSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */