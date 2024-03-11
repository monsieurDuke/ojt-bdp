package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.content.res.ResourcesCompat.FontCallback;
import com.google.android.material.R.styleable;

public class TextAppearance
{
  private static final String TAG = "TextAppearance";
  private static final int TYPEFACE_MONOSPACE = 3;
  private static final int TYPEFACE_SANS = 1;
  private static final int TYPEFACE_SERIF = 2;
  private Typeface font;
  public final String fontFamily;
  private final int fontFamilyResourceId;
  private boolean fontResolved = false;
  public final boolean hasLetterSpacing;
  public final float letterSpacing;
  public final ColorStateList shadowColor;
  public final float shadowDx;
  public final float shadowDy;
  public final float shadowRadius;
  public final boolean textAllCaps;
  private ColorStateList textColor;
  public final ColorStateList textColorHint;
  public final ColorStateList textColorLink;
  private float textSize;
  public final int textStyle;
  public final int typeface;
  
  public TextAppearance(Context paramContext, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.TextAppearance);
    setTextSize(localTypedArray.getDimension(R.styleable.TextAppearance_android_textSize, 0.0F));
    setTextColor(MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.TextAppearance_android_textColor));
    this.textColorHint = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.TextAppearance_android_textColorHint);
    this.textColorLink = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.TextAppearance_android_textColorLink);
    this.textStyle = localTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, 0);
    this.typeface = localTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
    int i = MaterialResources.getIndexWithValue(localTypedArray, R.styleable.TextAppearance_fontFamily, R.styleable.TextAppearance_android_fontFamily);
    this.fontFamilyResourceId = localTypedArray.getResourceId(i, 0);
    this.fontFamily = localTypedArray.getString(i);
    this.textAllCaps = localTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
    this.shadowColor = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.TextAppearance_android_shadowColor);
    this.shadowDx = localTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
    this.shadowDy = localTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
    this.shadowRadius = localTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
    localTypedArray.recycle();
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramContext = paramContext.obtainStyledAttributes(paramInt, R.styleable.MaterialTextAppearance);
      this.hasLetterSpacing = paramContext.hasValue(R.styleable.MaterialTextAppearance_android_letterSpacing);
      this.letterSpacing = paramContext.getFloat(R.styleable.MaterialTextAppearance_android_letterSpacing, 0.0F);
      paramContext.recycle();
    }
    else
    {
      this.hasLetterSpacing = false;
      this.letterSpacing = 0.0F;
    }
  }
  
  private void createFallbackFont()
  {
    if (this.font == null)
    {
      String str = this.fontFamily;
      if (str != null) {
        this.font = Typeface.create(str, this.textStyle);
      }
    }
    if (this.font == null)
    {
      switch (this.typeface)
      {
      default: 
        this.font = Typeface.DEFAULT;
        break;
      case 3: 
        this.font = Typeface.MONOSPACE;
        break;
      case 2: 
        this.font = Typeface.SERIF;
        break;
      case 1: 
        this.font = Typeface.SANS_SERIF;
      }
      this.font = Typeface.create(this.font, this.textStyle);
    }
  }
  
  private boolean shouldLoadFontSynchronously(Context paramContext)
  {
    boolean bool2 = TextAppearanceConfig.shouldLoadFontSynchronously();
    boolean bool1 = true;
    if (bool2) {
      return true;
    }
    int i = this.fontFamilyResourceId;
    if (i != 0) {
      paramContext = ResourcesCompat.getCachedFont(paramContext, i);
    } else {
      paramContext = null;
    }
    if (paramContext == null) {
      bool1 = false;
    }
    return bool1;
  }
  
  public Typeface getFallbackFont()
  {
    createFallbackFont();
    return this.font;
  }
  
  public Typeface getFont(Context paramContext)
  {
    if (this.fontResolved) {
      return this.font;
    }
    if (!paramContext.isRestricted()) {
      try
      {
        paramContext = ResourcesCompat.getFont(paramContext, this.fontFamilyResourceId);
        this.font = paramContext;
        if (paramContext != null) {
          this.font = Typeface.create(paramContext, this.textStyle);
        }
      }
      catch (Exception paramContext)
      {
        Log.d("TextAppearance", "Error loading font " + this.fontFamily, paramContext);
      }
      catch (Resources.NotFoundException paramContext) {}catch (UnsupportedOperationException paramContext) {}
    }
    createFallbackFont();
    this.fontResolved = true;
    return this.font;
  }
  
  public void getFontAsync(final Context paramContext, final TextPaint paramTextPaint, final TextAppearanceFontCallback paramTextAppearanceFontCallback)
  {
    updateTextPaintMeasureState(paramContext, paramTextPaint, getFallbackFont());
    getFontAsync(paramContext, new TextAppearanceFontCallback()
    {
      public void onFontRetrievalFailed(int paramAnonymousInt)
      {
        paramTextAppearanceFontCallback.onFontRetrievalFailed(paramAnonymousInt);
      }
      
      public void onFontRetrieved(Typeface paramAnonymousTypeface, boolean paramAnonymousBoolean)
      {
        TextAppearance.this.updateTextPaintMeasureState(paramContext, paramTextPaint, paramAnonymousTypeface);
        paramTextAppearanceFontCallback.onFontRetrieved(paramAnonymousTypeface, paramAnonymousBoolean);
      }
    });
  }
  
  public void getFontAsync(Context paramContext, TextAppearanceFontCallback paramTextAppearanceFontCallback)
  {
    if (shouldLoadFontSynchronously(paramContext)) {
      getFont(paramContext);
    } else {
      createFallbackFont();
    }
    int i = this.fontFamilyResourceId;
    if (i == 0) {
      this.fontResolved = true;
    }
    if (this.fontResolved)
    {
      paramTextAppearanceFontCallback.onFontRetrieved(this.font, true);
      return;
    }
    try
    {
      ResourcesCompat.FontCallback local1 = new com/google/android/material/resources/TextAppearance$1;
      local1.<init>(this, paramTextAppearanceFontCallback);
      ResourcesCompat.getFont(paramContext, i, local1, null);
    }
    catch (Exception paramContext)
    {
      Log.d("TextAppearance", "Error loading font " + this.fontFamily, paramContext);
      this.fontResolved = true;
      paramTextAppearanceFontCallback.onFontRetrievalFailed(-3);
    }
    catch (Resources.NotFoundException paramContext)
    {
      this.fontResolved = true;
      paramTextAppearanceFontCallback.onFontRetrievalFailed(1);
    }
  }
  
  public ColorStateList getTextColor()
  {
    return this.textColor;
  }
  
  public float getTextSize()
  {
    return this.textSize;
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    this.textColor = paramColorStateList;
  }
  
  public void setTextSize(float paramFloat)
  {
    this.textSize = paramFloat;
  }
  
  public void updateDrawState(Context paramContext, TextPaint paramTextPaint, TextAppearanceFontCallback paramTextAppearanceFontCallback)
  {
    updateMeasureState(paramContext, paramTextPaint, paramTextAppearanceFontCallback);
    paramContext = this.textColor;
    int i;
    if (paramContext != null) {
      i = paramContext.getColorForState(paramTextPaint.drawableState, this.textColor.getDefaultColor());
    } else {
      i = -16777216;
    }
    paramTextPaint.setColor(i);
    float f2 = this.shadowRadius;
    float f3 = this.shadowDx;
    float f1 = this.shadowDy;
    paramContext = this.shadowColor;
    if (paramContext != null) {
      i = paramContext.getColorForState(paramTextPaint.drawableState, this.shadowColor.getDefaultColor());
    } else {
      i = 0;
    }
    paramTextPaint.setShadowLayer(f2, f3, f1, i);
  }
  
  public void updateMeasureState(Context paramContext, TextPaint paramTextPaint, TextAppearanceFontCallback paramTextAppearanceFontCallback)
  {
    if (shouldLoadFontSynchronously(paramContext)) {
      updateTextPaintMeasureState(paramContext, paramTextPaint, getFont(paramContext));
    } else {
      getFontAsync(paramContext, paramTextPaint, paramTextAppearanceFontCallback);
    }
  }
  
  public void updateTextPaintMeasureState(Context paramContext, TextPaint paramTextPaint, Typeface paramTypeface)
  {
    paramContext = TypefaceUtils.maybeCopyWithFontWeightAdjustment(paramContext, paramTypeface);
    if (paramContext != null) {
      paramTypeface = paramContext;
    }
    paramTextPaint.setTypeface(paramTypeface);
    int i = this.textStyle & (paramTypeface.getStyle() ^ 0xFFFFFFFF);
    boolean bool;
    if ((i & 0x1) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    paramTextPaint.setFakeBoldText(bool);
    float f;
    if ((i & 0x2) != 0) {
      f = -0.25F;
    } else {
      f = 0.0F;
    }
    paramTextPaint.setTextSkewX(f);
    paramTextPaint.setTextSize(this.textSize);
    if ((Build.VERSION.SDK_INT >= 21) && (this.hasLetterSpacing)) {
      paramTextPaint.setLetterSpacing(this.letterSpacing);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/resources/TextAppearance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */