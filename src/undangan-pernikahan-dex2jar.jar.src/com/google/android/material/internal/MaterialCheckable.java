package com.google.android.material.internal;

import android.widget.Checkable;

public abstract interface MaterialCheckable<T extends MaterialCheckable<T>>
  extends Checkable
{
  public abstract int getId();
  
  public abstract void setInternalOnCheckedChangeListener(OnCheckedChangeListener<T> paramOnCheckedChangeListener);
  
  public static abstract interface OnCheckedChangeListener<C>
  {
    public abstract void onCheckedChanged(C paramC, boolean paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/MaterialCheckable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */