package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;

public abstract interface TintableCompoundDrawablesView
{
  public abstract ColorStateList getSupportCompoundDrawablesTintList();
  
  public abstract PorterDuff.Mode getSupportCompoundDrawablesTintMode();
  
  public abstract void setSupportCompoundDrawablesTintList(ColorStateList paramColorStateList);
  
  public abstract void setSupportCompoundDrawablesTintMode(PorterDuff.Mode paramMode);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/TintableCompoundDrawablesView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */