package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.internal.CheckableImageButton;
import java.util.Arrays;

class IconHelper
{
  static void applyIconTint(TextInputLayout paramTextInputLayout, CheckableImageButton paramCheckableImageButton, ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    Drawable localDrawable2 = paramCheckableImageButton.getDrawable();
    Drawable localDrawable1 = localDrawable2;
    if (localDrawable2 != null)
    {
      localDrawable2 = DrawableCompat.wrap(localDrawable2).mutate();
      if ((paramColorStateList != null) && (paramColorStateList.isStateful())) {
        DrawableCompat.setTintList(localDrawable2, ColorStateList.valueOf(paramColorStateList.getColorForState(mergeIconState(paramTextInputLayout, paramCheckableImageButton), paramColorStateList.getDefaultColor())));
      } else {
        DrawableCompat.setTintList(localDrawable2, paramColorStateList);
      }
      localDrawable1 = localDrawable2;
      if (paramMode != null)
      {
        DrawableCompat.setTintMode(localDrawable2, paramMode);
        localDrawable1 = localDrawable2;
      }
    }
    if (paramCheckableImageButton.getDrawable() != localDrawable1) {
      paramCheckableImageButton.setImageDrawable(localDrawable1);
    }
  }
  
  private static int[] mergeIconState(TextInputLayout paramTextInputLayout, CheckableImageButton paramCheckableImageButton)
  {
    paramTextInputLayout = paramTextInputLayout.getDrawableState();
    paramCheckableImageButton = paramCheckableImageButton.getDrawableState();
    int i = paramTextInputLayout.length;
    paramTextInputLayout = Arrays.copyOf(paramTextInputLayout, paramTextInputLayout.length + paramCheckableImageButton.length);
    System.arraycopy(paramCheckableImageButton, 0, paramTextInputLayout, i, paramCheckableImageButton.length);
    return paramTextInputLayout;
  }
  
  static void refreshIconDrawableState(TextInputLayout paramTextInputLayout, CheckableImageButton paramCheckableImageButton, ColorStateList paramColorStateList)
  {
    Drawable localDrawable = paramCheckableImageButton.getDrawable();
    if ((paramCheckableImageButton.getDrawable() != null) && (paramColorStateList != null) && (paramColorStateList.isStateful()))
    {
      int i = paramColorStateList.getColorForState(mergeIconState(paramTextInputLayout, paramCheckableImageButton), paramColorStateList.getDefaultColor());
      paramTextInputLayout = DrawableCompat.wrap(localDrawable).mutate();
      DrawableCompat.setTintList(paramTextInputLayout, ColorStateList.valueOf(i));
      paramCheckableImageButton.setImageDrawable(paramTextInputLayout);
      return;
    }
  }
  
  private static void setIconClickable(CheckableImageButton paramCheckableImageButton, View.OnLongClickListener paramOnLongClickListener)
  {
    boolean bool3 = ViewCompat.hasOnClickListeners(paramCheckableImageButton);
    boolean bool2 = false;
    int i = 1;
    boolean bool1;
    if (paramOnLongClickListener != null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((bool3) || (bool1)) {
      bool2 = true;
    }
    paramCheckableImageButton.setFocusable(bool2);
    paramCheckableImageButton.setClickable(bool3);
    paramCheckableImageButton.setPressable(bool3);
    paramCheckableImageButton.setLongClickable(bool1);
    if (!bool2) {
      i = 2;
    }
    ViewCompat.setImportantForAccessibility(paramCheckableImageButton, i);
  }
  
  static void setIconOnClickListener(CheckableImageButton paramCheckableImageButton, View.OnClickListener paramOnClickListener, View.OnLongClickListener paramOnLongClickListener)
  {
    paramCheckableImageButton.setOnClickListener(paramOnClickListener);
    setIconClickable(paramCheckableImageButton, paramOnLongClickListener);
  }
  
  static void setIconOnLongClickListener(CheckableImageButton paramCheckableImageButton, View.OnLongClickListener paramOnLongClickListener)
  {
    paramCheckableImageButton.setOnLongClickListener(paramOnLongClickListener);
    setIconClickable(paramCheckableImageButton, paramOnLongClickListener);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/IconHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */