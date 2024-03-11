package com.google.android.material.textfield;

class NoEndIconDelegate
  extends EndIconDelegate
{
  NoEndIconDelegate(TextInputLayout paramTextInputLayout)
  {
    super(paramTextInputLayout, 0);
  }
  
  void initialize()
  {
    this.textInputLayout.setEndIconOnClickListener(null);
    this.textInputLayout.setEndIconDrawable(null);
    this.textInputLayout.setEndIconContentDescription(null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/NoEndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */