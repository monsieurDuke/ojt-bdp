package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;

public abstract interface TintAwareDrawable
{
  public abstract void setTint(int paramInt);
  
  public abstract void setTintList(ColorStateList paramColorStateList);
  
  public abstract void setTintMode(PorterDuff.Mode paramMode);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/TintAwareDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */