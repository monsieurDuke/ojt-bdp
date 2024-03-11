package com.google.android.material.textfield;

import android.content.Context;
import com.google.android.material.internal.CheckableImageButton;

abstract class EndIconDelegate
{
  Context context;
  final int customEndIcon;
  CheckableImageButton endIconView;
  TextInputLayout textInputLayout;
  
  EndIconDelegate(TextInputLayout paramTextInputLayout, int paramInt)
  {
    this.textInputLayout = paramTextInputLayout;
    this.context = paramTextInputLayout.getContext();
    this.endIconView = paramTextInputLayout.getEndIconView();
    this.customEndIcon = paramInt;
  }
  
  abstract void initialize();
  
  boolean isBoxBackgroundModeSupported(int paramInt)
  {
    return true;
  }
  
  void onSuffixVisibilityChanged(boolean paramBoolean) {}
  
  boolean shouldTintIconOnError()
  {
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/EndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */