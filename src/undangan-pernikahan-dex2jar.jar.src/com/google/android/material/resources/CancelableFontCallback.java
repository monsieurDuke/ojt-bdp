package com.google.android.material.resources;

import android.graphics.Typeface;

public final class CancelableFontCallback
  extends TextAppearanceFontCallback
{
  private final ApplyFont applyFont;
  private boolean cancelled;
  private final Typeface fallbackFont;
  
  public CancelableFontCallback(ApplyFont paramApplyFont, Typeface paramTypeface)
  {
    this.fallbackFont = paramTypeface;
    this.applyFont = paramApplyFont;
  }
  
  private void updateIfNotCancelled(Typeface paramTypeface)
  {
    if (!this.cancelled) {
      this.applyFont.apply(paramTypeface);
    }
  }
  
  public void cancel()
  {
    this.cancelled = true;
  }
  
  public void onFontRetrievalFailed(int paramInt)
  {
    updateIfNotCancelled(this.fallbackFont);
  }
  
  public void onFontRetrieved(Typeface paramTypeface, boolean paramBoolean)
  {
    updateIfNotCancelled(paramTypeface);
  }
  
  public static abstract interface ApplyFont
  {
    public abstract void apply(Typeface paramTypeface);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/resources/CancelableFontCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */