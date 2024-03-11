package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;

class ViewGroupOverlayApi18
  implements ViewGroupOverlayImpl
{
  private final ViewGroupOverlay viewGroupOverlay;
  
  ViewGroupOverlayApi18(ViewGroup paramViewGroup)
  {
    this.viewGroupOverlay = paramViewGroup.getOverlay();
  }
  
  public void add(Drawable paramDrawable)
  {
    this.viewGroupOverlay.add(paramDrawable);
  }
  
  public void add(View paramView)
  {
    this.viewGroupOverlay.add(paramView);
  }
  
  public void remove(Drawable paramDrawable)
  {
    this.viewGroupOverlay.remove(paramDrawable);
  }
  
  public void remove(View paramView)
  {
    this.viewGroupOverlay.remove(paramView);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/ViewGroupOverlayApi18.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */