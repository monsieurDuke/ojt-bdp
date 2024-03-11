package com.google.android.material.textfield;

class CustomEndIconDelegate
  extends EndIconDelegate
{
  CustomEndIconDelegate(TextInputLayout paramTextInputLayout, int paramInt)
  {
    super(paramTextInputLayout, paramInt);
  }
  
  void initialize()
  {
    this.textInputLayout.setEndIconDrawable(this.customEndIcon);
    this.textInputLayout.setEndIconOnClickListener(null);
    this.textInputLayout.setEndIconOnLongClickListener(null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/CustomEndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */