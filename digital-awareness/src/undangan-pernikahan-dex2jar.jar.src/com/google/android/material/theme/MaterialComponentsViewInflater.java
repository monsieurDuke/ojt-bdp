package com.google.android.material.theme;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

public class MaterialComponentsViewInflater
  extends AppCompatViewInflater
{
  protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new MaterialAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatButton createButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new MaterialButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckBox createCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new MaterialCheckBox(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRadioButton createRadioButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new MaterialRadioButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatTextView createTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new MaterialTextView(paramContext, paramAttributeSet);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/theme/MaterialComponentsViewInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */