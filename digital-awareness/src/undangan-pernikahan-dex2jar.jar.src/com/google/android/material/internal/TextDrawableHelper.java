package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import java.lang.ref.WeakReference;

public class TextDrawableHelper
{
  private WeakReference<TextDrawableDelegate> delegate = new WeakReference(null);
  private final TextAppearanceFontCallback fontCallback = new TextAppearanceFontCallback()
  {
    public void onFontRetrievalFailed(int paramAnonymousInt)
    {
      TextDrawableHelper.access$002(TextDrawableHelper.this, true);
      TextDrawableHelper.TextDrawableDelegate localTextDrawableDelegate = (TextDrawableHelper.TextDrawableDelegate)TextDrawableHelper.this.delegate.get();
      if (localTextDrawableDelegate != null) {
        localTextDrawableDelegate.onTextSizeChange();
      }
    }
    
    public void onFontRetrieved(Typeface paramAnonymousTypeface, boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean) {
        return;
      }
      TextDrawableHelper.access$002(TextDrawableHelper.this, true);
      paramAnonymousTypeface = (TextDrawableHelper.TextDrawableDelegate)TextDrawableHelper.this.delegate.get();
      if (paramAnonymousTypeface != null) {
        paramAnonymousTypeface.onTextSizeChange();
      }
    }
  };
  private TextAppearance textAppearance;
  private final TextPaint textPaint = new TextPaint(1);
  private float textWidth;
  private boolean textWidthDirty = true;
  
  public TextDrawableHelper(TextDrawableDelegate paramTextDrawableDelegate)
  {
    setDelegate(paramTextDrawableDelegate);
  }
  
  private float calculateTextWidth(CharSequence paramCharSequence)
  {
    if (paramCharSequence == null) {
      return 0.0F;
    }
    return this.textPaint.measureText(paramCharSequence, 0, paramCharSequence.length());
  }
  
  public TextAppearance getTextAppearance()
  {
    return this.textAppearance;
  }
  
  public TextPaint getTextPaint()
  {
    return this.textPaint;
  }
  
  public float getTextWidth(String paramString)
  {
    if (!this.textWidthDirty) {
      return this.textWidth;
    }
    float f = calculateTextWidth(paramString);
    this.textWidth = f;
    this.textWidthDirty = false;
    return f;
  }
  
  public boolean isTextWidthDirty()
  {
    return this.textWidthDirty;
  }
  
  public void setDelegate(TextDrawableDelegate paramTextDrawableDelegate)
  {
    this.delegate = new WeakReference(paramTextDrawableDelegate);
  }
  
  public void setTextAppearance(TextAppearance paramTextAppearance, Context paramContext)
  {
    if (this.textAppearance != paramTextAppearance)
    {
      this.textAppearance = paramTextAppearance;
      if (paramTextAppearance != null)
      {
        paramTextAppearance.updateMeasureState(paramContext, this.textPaint, this.fontCallback);
        TextDrawableDelegate localTextDrawableDelegate = (TextDrawableDelegate)this.delegate.get();
        if (localTextDrawableDelegate != null) {
          this.textPaint.drawableState = localTextDrawableDelegate.getState();
        }
        paramTextAppearance.updateDrawState(paramContext, this.textPaint, this.fontCallback);
        this.textWidthDirty = true;
      }
      paramTextAppearance = (TextDrawableDelegate)this.delegate.get();
      if (paramTextAppearance != null)
      {
        paramTextAppearance.onTextSizeChange();
        paramTextAppearance.onStateChange(paramTextAppearance.getState());
      }
    }
  }
  
  public void setTextWidthDirty(boolean paramBoolean)
  {
    this.textWidthDirty = paramBoolean;
  }
  
  public void updateTextPaintDrawState(Context paramContext)
  {
    this.textAppearance.updateDrawState(paramContext, this.textPaint, this.fontCallback);
  }
  
  public static abstract interface TextDrawableDelegate
  {
    public abstract int[] getState();
    
    public abstract boolean onStateChange(int[] paramArrayOfInt);
    
    public abstract void onTextSizeChange();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/TextDrawableHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */