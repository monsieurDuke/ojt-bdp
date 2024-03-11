package com.google.android.material.timepicker;

import android.text.InputFilter;
import android.text.Spanned;

class MaxInputValidator
  implements InputFilter
{
  private int max;
  
  public MaxInputValidator(int paramInt)
  {
    this.max = paramInt;
  }
  
  public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>(paramSpanned);
      localStringBuilder.replace(paramInt3, paramInt4, paramCharSequence.subSequence(paramInt1, paramInt2).toString());
      paramInt2 = Integer.parseInt(localStringBuilder.toString());
      paramInt1 = this.max;
      if (paramInt2 <= paramInt1) {
        return null;
      }
    }
    catch (NumberFormatException paramCharSequence) {}
    return "";
  }
  
  public int getMax()
  {
    return this.max;
  }
  
  public void setMax(int paramInt)
  {
    this.max = paramInt;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/MaxInputValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */