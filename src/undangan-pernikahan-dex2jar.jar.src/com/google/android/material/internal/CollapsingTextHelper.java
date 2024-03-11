package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.CancelableFontCallback.ApplyFont;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TypefaceUtils;

public final class CollapsingTextHelper
{
  private static final boolean DEBUG_DRAW = false;
  private static final Paint DEBUG_DRAW_PAINT = null;
  private static final String ELLIPSIS_NORMAL = "…";
  private static final float FADE_MODE_THRESHOLD_FRACTION_RELATIVE = 0.5F;
  private static final String TAG = "CollapsingTextHelper";
  private static final boolean USE_SCALING_TEXTURE;
  private boolean boundsChanged;
  private final Rect collapsedBounds;
  private float collapsedDrawX;
  private float collapsedDrawY;
  private CancelableFontCallback collapsedFontCallback;
  private float collapsedLetterSpacing;
  private ColorStateList collapsedShadowColor;
  private float collapsedShadowDx;
  private float collapsedShadowDy;
  private float collapsedShadowRadius;
  private float collapsedTextBlend;
  private ColorStateList collapsedTextColor;
  private int collapsedTextGravity = 16;
  private float collapsedTextSize = 15.0F;
  private float collapsedTextWidth;
  private Typeface collapsedTypeface;
  private Typeface collapsedTypefaceBold;
  private Typeface collapsedTypefaceDefault;
  private final RectF currentBounds;
  private float currentDrawX;
  private float currentDrawY;
  private float currentLetterSpacing;
  private int currentOffsetY;
  private int currentShadowColor;
  private float currentShadowDx;
  private float currentShadowDy;
  private float currentShadowRadius;
  private float currentTextSize;
  private Typeface currentTypeface;
  private boolean drawTitle;
  private final Rect expandedBounds;
  private float expandedDrawX;
  private float expandedDrawY;
  private CancelableFontCallback expandedFontCallback;
  private float expandedFraction;
  private float expandedLetterSpacing;
  private int expandedLineCount;
  private ColorStateList expandedShadowColor;
  private float expandedShadowDx;
  private float expandedShadowDy;
  private float expandedShadowRadius;
  private float expandedTextBlend;
  private ColorStateList expandedTextColor;
  private int expandedTextGravity = 16;
  private float expandedTextSize = 15.0F;
  private Bitmap expandedTitleTexture;
  private Typeface expandedTypeface;
  private Typeface expandedTypefaceBold;
  private Typeface expandedTypefaceDefault;
  private boolean fadeModeEnabled;
  private float fadeModeStartFraction;
  private float fadeModeThresholdFraction;
  private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;
  private boolean isRtl;
  private boolean isRtlTextDirectionHeuristicsEnabled = true;
  private float lineSpacingAdd = 0.0F;
  private float lineSpacingMultiplier = 1.0F;
  private int maxLines = 1;
  private TimeInterpolator positionInterpolator;
  private float scale;
  private int[] state;
  private CharSequence text;
  private StaticLayout textLayout;
  private final TextPaint textPaint;
  private TimeInterpolator textSizeInterpolator;
  private CharSequence textToDraw;
  private CharSequence textToDrawCollapsed;
  private Paint texturePaint;
  private final TextPaint tmpPaint;
  private boolean useTexture;
  private final View view;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 18) {
      bool = true;
    } else {
      bool = false;
    }
    USE_SCALING_TEXTURE = bool;
  }
  
  public CollapsingTextHelper(View paramView)
  {
    this.view = paramView;
    TextPaint localTextPaint = new TextPaint(129);
    this.textPaint = localTextPaint;
    this.tmpPaint = new TextPaint(localTextPaint);
    this.collapsedBounds = new Rect();
    this.expandedBounds = new Rect();
    this.currentBounds = new RectF();
    this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    maybeUpdateFontWeightAdjustment(paramView.getContext().getResources().getConfiguration());
  }
  
  private static int blendARGB(int paramInt1, int paramInt2, float paramFloat)
  {
    float f7 = 1.0F - paramFloat;
    float f1 = Color.alpha(paramInt1);
    float f4 = Color.alpha(paramInt2);
    float f8 = Color.red(paramInt1);
    float f5 = Color.red(paramInt2);
    float f9 = Color.green(paramInt1);
    float f6 = Color.green(paramInt2);
    float f3 = Color.blue(paramInt1);
    float f2 = Color.blue(paramInt2);
    return Color.argb(Math.round(f1 * f7 + f4 * paramFloat), Math.round(f8 * f7 + f5 * paramFloat), Math.round(f9 * f7 + f6 * paramFloat), Math.round(f3 * f7 + f2 * paramFloat));
  }
  
  private void calculateBaseOffsets(boolean paramBoolean)
  {
    calculateUsingTextSize(1.0F, paramBoolean);
    Object localObject = this.textToDraw;
    if (localObject != null)
    {
      StaticLayout localStaticLayout = this.textLayout;
      if (localStaticLayout != null) {
        this.textToDrawCollapsed = TextUtils.ellipsize((CharSequence)localObject, this.textPaint, localStaticLayout.getWidth(), TextUtils.TruncateAt.END);
      }
    }
    localObject = this.textToDrawCollapsed;
    float f2 = 0.0F;
    if (localObject != null) {
      this.collapsedTextWidth = measureTextWidth(this.textPaint, (CharSequence)localObject);
    } else {
      this.collapsedTextWidth = 0.0F;
    }
    int i = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl);
    switch (i & 0x70)
    {
    default: 
      f1 = (this.textPaint.descent() - this.textPaint.ascent()) / 2.0F;
      this.collapsedDrawY = (this.collapsedBounds.centerY() - f1);
      break;
    case 80: 
      this.collapsedDrawY = (this.collapsedBounds.bottom + this.textPaint.ascent());
      break;
    case 48: 
      this.collapsedDrawY = this.collapsedBounds.top;
    }
    switch (i & 0x800007)
    {
    default: 
      this.collapsedDrawX = this.collapsedBounds.left;
      break;
    case 5: 
      this.collapsedDrawX = (this.collapsedBounds.right - this.collapsedTextWidth);
      break;
    case 1: 
      this.collapsedDrawX = (this.collapsedBounds.centerX() - this.collapsedTextWidth / 2.0F);
    }
    calculateUsingTextSize(0.0F, paramBoolean);
    localObject = this.textLayout;
    if (localObject != null) {
      f2 = ((StaticLayout)localObject).getHeight();
    }
    float f1 = 0.0F;
    localObject = this.textLayout;
    if ((localObject != null) && (this.maxLines > 1))
    {
      f1 = ((StaticLayout)localObject).getWidth();
    }
    else
    {
      localObject = this.textToDraw;
      if (localObject != null) {
        f1 = measureTextWidth(this.textPaint, (CharSequence)localObject);
      }
    }
    localObject = this.textLayout;
    if (localObject != null) {
      i = ((StaticLayout)localObject).getLineCount();
    } else {
      i = 0;
    }
    this.expandedLineCount = i;
    i = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl);
    switch (i & 0x70)
    {
    default: 
      f2 /= 2.0F;
      this.expandedDrawY = (this.expandedBounds.centerY() - f2);
      break;
    case 80: 
      this.expandedDrawY = (this.expandedBounds.bottom - f2 + this.textPaint.descent());
      break;
    case 48: 
      this.expandedDrawY = this.expandedBounds.top;
    }
    switch (0x800007 & i)
    {
    default: 
      this.expandedDrawX = this.expandedBounds.left;
      break;
    case 5: 
      this.expandedDrawX = (this.expandedBounds.right - f1);
      break;
    case 1: 
      this.expandedDrawX = (this.expandedBounds.centerX() - f1 / 2.0F);
    }
    clearTexture();
    setInterpolatedTextSize(this.expandedFraction);
  }
  
  private void calculateCurrentOffsets()
  {
    calculateOffsets(this.expandedFraction);
  }
  
  private float calculateFadeModeTextAlpha(float paramFloat)
  {
    float f = this.fadeModeThresholdFraction;
    if (paramFloat <= f) {
      return AnimationUtils.lerp(1.0F, 0.0F, this.fadeModeStartFraction, f, paramFloat);
    }
    return AnimationUtils.lerp(0.0F, 1.0F, f, 1.0F, paramFloat);
  }
  
  private float calculateFadeModeThresholdFraction()
  {
    float f = this.fadeModeStartFraction;
    return f + (1.0F - f) * 0.5F;
  }
  
  private boolean calculateIsRtl(CharSequence paramCharSequence)
  {
    boolean bool = isDefaultIsRtl();
    if (this.isRtlTextDirectionHeuristicsEnabled) {
      bool = isTextDirectionHeuristicsIsRtl(paramCharSequence, bool);
    }
    return bool;
  }
  
  private void calculateOffsets(float paramFloat)
  {
    interpolateBounds(paramFloat);
    float f1;
    if (this.fadeModeEnabled)
    {
      if (paramFloat < this.fadeModeThresholdFraction)
      {
        f1 = 0.0F;
        this.currentDrawX = this.expandedDrawX;
        this.currentDrawY = this.expandedDrawY;
        setInterpolatedTextSize(0.0F);
      }
      else
      {
        f1 = 1.0F;
        this.currentDrawX = this.collapsedDrawX;
        this.currentDrawY = (this.collapsedDrawY - Math.max(0, this.currentOffsetY));
        setInterpolatedTextSize(1.0F);
      }
    }
    else
    {
      f1 = paramFloat;
      this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, paramFloat, this.positionInterpolator);
      this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, paramFloat, this.positionInterpolator);
      setInterpolatedTextSize(paramFloat);
    }
    setCollapsedTextBlend(1.0F - lerp(0.0F, 1.0F, 1.0F - paramFloat, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
    setExpandedTextBlend(lerp(1.0F, 0.0F, paramFloat, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
    if (this.collapsedTextColor != this.expandedTextColor) {
      this.textPaint.setColor(blendARGB(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f1));
    } else {
      this.textPaint.setColor(getCurrentCollapsedTextColor());
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      float f2 = this.collapsedLetterSpacing;
      f1 = this.expandedLetterSpacing;
      if (f2 != f1) {
        this.textPaint.setLetterSpacing(lerp(f1, f2, paramFloat, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
      } else {
        this.textPaint.setLetterSpacing(f2);
      }
    }
    this.currentShadowRadius = lerp(this.expandedShadowRadius, this.collapsedShadowRadius, paramFloat, null);
    this.currentShadowDx = lerp(this.expandedShadowDx, this.collapsedShadowDx, paramFloat, null);
    this.currentShadowDy = lerp(this.expandedShadowDy, this.collapsedShadowDy, paramFloat, null);
    int i = blendARGB(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), paramFloat);
    this.currentShadowColor = i;
    this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, i);
    if (this.fadeModeEnabled)
    {
      i = this.textPaint.getAlpha();
      i = (int)(calculateFadeModeTextAlpha(paramFloat) * i);
      this.textPaint.setAlpha(i);
    }
    ViewCompat.postInvalidateOnAnimation(this.view);
  }
  
  private void calculateUsingTextSize(float paramFloat)
  {
    calculateUsingTextSize(paramFloat, false);
  }
  
  private void calculateUsingTextSize(float paramFloat, boolean paramBoolean)
  {
    if (this.text == null) {
      return;
    }
    float f4 = this.collapsedBounds.width();
    float f1 = this.expandedBounds.width();
    int i = 0;
    int j = 0;
    float f2;
    Typeface localTypeface;
    Object localObject;
    if (isClose(paramFloat, 1.0F))
    {
      f1 = this.collapsedTextSize;
      f2 = this.collapsedLetterSpacing;
      this.scale = 1.0F;
      localTypeface = this.currentTypeface;
      localObject = this.collapsedTypeface;
      i = j;
      if (localTypeface != localObject)
      {
        this.currentTypeface = ((Typeface)localObject);
        i = 1;
      }
      paramFloat = f4;
    }
    else
    {
      float f3 = this.expandedTextSize;
      f2 = this.expandedLetterSpacing;
      localObject = this.currentTypeface;
      localTypeface = this.expandedTypeface;
      if (localObject != localTypeface)
      {
        this.currentTypeface = localTypeface;
        i = 1;
      }
      if (isClose(paramFloat, 0.0F)) {
        this.scale = 1.0F;
      } else {
        this.scale = (lerp(this.expandedTextSize, this.collapsedTextSize, paramFloat, this.textSizeInterpolator) / this.expandedTextSize);
      }
      paramFloat = this.collapsedTextSize / this.expandedTextSize;
      if (paramBoolean)
      {
        paramFloat = f1;
        f1 = f3;
      }
      else
      {
        if (f1 * paramFloat > f4) {
          paramFloat = Math.min(f4 / paramFloat, f1);
        } else {
          paramFloat = f1;
        }
        f1 = f3;
      }
    }
    int m = 1;
    paramBoolean = false;
    j = i;
    if (paramFloat > 0.0F)
    {
      if (this.currentTextSize != f1) {
        j = 1;
      } else {
        j = 0;
      }
      int k;
      if (this.currentLetterSpacing != f2) {
        k = 1;
      } else {
        k = 0;
      }
      if ((j == 0) && (k == 0) && (!this.boundsChanged) && (i == 0)) {
        i = 0;
      } else {
        i = 1;
      }
      this.currentTextSize = f1;
      this.currentLetterSpacing = f2;
      this.boundsChanged = false;
      j = i;
    }
    if ((this.textToDraw == null) || (j != 0))
    {
      this.textPaint.setTextSize(this.currentTextSize);
      this.textPaint.setTypeface(this.currentTypeface);
      if (Build.VERSION.SDK_INT >= 21) {
        this.textPaint.setLetterSpacing(this.currentLetterSpacing);
      }
      localObject = this.textPaint;
      if (this.scale != 1.0F) {
        paramBoolean = true;
      }
      ((TextPaint)localObject).setLinearText(paramBoolean);
      this.isRtl = calculateIsRtl(this.text);
      i = m;
      if (shouldDrawMultiline()) {
        i = this.maxLines;
      }
      localObject = createStaticLayout(i, paramFloat, this.isRtl);
      this.textLayout = ((StaticLayout)localObject);
      this.textToDraw = ((StaticLayout)localObject).getText();
    }
  }
  
  private void clearTexture()
  {
    Bitmap localBitmap = this.expandedTitleTexture;
    if (localBitmap != null)
    {
      localBitmap.recycle();
      this.expandedTitleTexture = null;
    }
  }
  
  private StaticLayout createStaticLayout(int paramInt, float paramFloat, boolean paramBoolean)
  {
    Object localObject3 = null;
    if (paramInt == 1) {}
    label22:
    Object localObject2;
    try
    {
      Object localObject1 = Layout.Alignment.ALIGN_NORMAL;
      break label22;
      localObject1 = getMultilineTextLayoutAlignment();
      localObject1 = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int)paramFloat).setEllipsize(TextUtils.TruncateAt.END).setIsRtl(paramBoolean).setAlignment((Layout.Alignment)localObject1).setIncludePad(false).setMaxLines(paramInt).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).build();
    }
    catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException localStaticLayoutBuilderCompatException)
    {
      Log.e("CollapsingTextHelper", localStaticLayoutBuilderCompatException.getCause().getMessage(), localStaticLayoutBuilderCompatException);
      localObject2 = localObject3;
    }
    return (StaticLayout)Preconditions.checkNotNull(localObject2);
  }
  
  private void drawMultilineTransition(Canvas paramCanvas, float paramFloat1, float paramFloat2)
  {
    int i = this.textPaint.getAlpha();
    paramCanvas.translate(paramFloat1, paramFloat2);
    this.textPaint.setAlpha((int)(this.expandedTextBlend * i));
    if (Build.VERSION.SDK_INT >= 31)
    {
      localObject = this.textPaint;
      ((TextPaint)localObject).setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, ((TextPaint)localObject).getAlpha()));
    }
    this.textLayout.draw(paramCanvas);
    this.textPaint.setAlpha((int)(this.collapsedTextBlend * i));
    if (Build.VERSION.SDK_INT >= 31)
    {
      localObject = this.textPaint;
      ((TextPaint)localObject).setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, ((TextPaint)localObject).getAlpha()));
    }
    int j = this.textLayout.getLineBaseline(0);
    Object localObject = this.textToDrawCollapsed;
    paramCanvas.drawText((CharSequence)localObject, 0, ((CharSequence)localObject).length(), 0.0F, j, this.textPaint);
    if (Build.VERSION.SDK_INT >= 31) {
      this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, this.currentShadowColor);
    }
    if (!this.fadeModeEnabled)
    {
      String str = this.textToDrawCollapsed.toString().trim();
      localObject = str;
      if (str.endsWith("…")) {
        localObject = str.substring(0, str.length() - 1);
      }
      this.textPaint.setAlpha(i);
      paramCanvas.drawText((String)localObject, 0, Math.min(this.textLayout.getLineEnd(0), ((String)localObject).length()), 0.0F, j, this.textPaint);
    }
  }
  
  private void ensureExpandedTexture()
  {
    if ((this.expandedTitleTexture == null) && (!this.expandedBounds.isEmpty()) && (!TextUtils.isEmpty(this.textToDraw)))
    {
      calculateOffsets(0.0F);
      int i = this.textLayout.getWidth();
      int j = this.textLayout.getHeight();
      if ((i > 0) && (j > 0))
      {
        this.expandedTitleTexture = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(this.expandedTitleTexture);
        this.textLayout.draw(localCanvas);
        if (this.texturePaint == null) {
          this.texturePaint = new Paint(3);
        }
        return;
      }
      return;
    }
  }
  
  private float getCollapsedTextLeftBound(int paramInt1, int paramInt2)
  {
    if ((paramInt2 != 17) && ((paramInt2 & 0x7) != 1))
    {
      float f;
      if (((paramInt2 & 0x800005) != 8388613) && ((paramInt2 & 0x5) != 5))
      {
        if (this.isRtl) {
          f = this.collapsedBounds.right - this.collapsedTextWidth;
        } else {
          f = this.collapsedBounds.left;
        }
        return f;
      }
      if (this.isRtl) {
        f = this.collapsedBounds.left;
      } else {
        f = this.collapsedBounds.right - this.collapsedTextWidth;
      }
      return f;
    }
    return paramInt1 / 2.0F - this.collapsedTextWidth / 2.0F;
  }
  
  private float getCollapsedTextRightBound(RectF paramRectF, int paramInt1, int paramInt2)
  {
    if ((paramInt2 != 17) && ((paramInt2 & 0x7) != 1))
    {
      float f;
      if (((paramInt2 & 0x800005) != 8388613) && ((paramInt2 & 0x5) != 5))
      {
        if (this.isRtl) {
          f = this.collapsedBounds.right;
        } else {
          f = paramRectF.left + this.collapsedTextWidth;
        }
        return f;
      }
      if (this.isRtl) {
        f = paramRectF.left + this.collapsedTextWidth;
      } else {
        f = this.collapsedBounds.right;
      }
      return f;
    }
    return paramInt1 / 2.0F + this.collapsedTextWidth / 2.0F;
  }
  
  private int getCurrentColor(ColorStateList paramColorStateList)
  {
    if (paramColorStateList == null) {
      return 0;
    }
    int[] arrayOfInt = this.state;
    if (arrayOfInt != null) {
      return paramColorStateList.getColorForState(arrayOfInt, 0);
    }
    return paramColorStateList.getDefaultColor();
  }
  
  private int getCurrentExpandedTextColor()
  {
    return getCurrentColor(this.expandedTextColor);
  }
  
  private Layout.Alignment getMultilineTextLayoutAlignment()
  {
    switch (GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl) & 0x7)
    {
    default: 
      if (this.isRtl) {
        localAlignment = Layout.Alignment.ALIGN_OPPOSITE;
      }
      break;
    case 5: 
      if (this.isRtl) {
        localAlignment = Layout.Alignment.ALIGN_NORMAL;
      } else {
        localAlignment = Layout.Alignment.ALIGN_OPPOSITE;
      }
      return localAlignment;
    case 1: 
      return Layout.Alignment.ALIGN_CENTER;
    }
    Layout.Alignment localAlignment = Layout.Alignment.ALIGN_NORMAL;
    return localAlignment;
  }
  
  private void getTextPaintCollapsed(TextPaint paramTextPaint)
  {
    paramTextPaint.setTextSize(this.collapsedTextSize);
    paramTextPaint.setTypeface(this.collapsedTypeface);
    if (Build.VERSION.SDK_INT >= 21) {
      paramTextPaint.setLetterSpacing(this.collapsedLetterSpacing);
    }
  }
  
  private void getTextPaintExpanded(TextPaint paramTextPaint)
  {
    paramTextPaint.setTextSize(this.expandedTextSize);
    paramTextPaint.setTypeface(this.expandedTypeface);
    if (Build.VERSION.SDK_INT >= 21) {
      paramTextPaint.setLetterSpacing(this.expandedLetterSpacing);
    }
  }
  
  private void interpolateBounds(float paramFloat)
  {
    if (this.fadeModeEnabled)
    {
      RectF localRectF = this.currentBounds;
      Rect localRect;
      if (paramFloat < this.fadeModeThresholdFraction) {
        localRect = this.expandedBounds;
      } else {
        localRect = this.collapsedBounds;
      }
      localRectF.set(localRect);
    }
    else
    {
      this.currentBounds.left = lerp(this.expandedBounds.left, this.collapsedBounds.left, paramFloat, this.positionInterpolator);
      this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, paramFloat, this.positionInterpolator);
      this.currentBounds.right = lerp(this.expandedBounds.right, this.collapsedBounds.right, paramFloat, this.positionInterpolator);
      this.currentBounds.bottom = lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, paramFloat, this.positionInterpolator);
    }
  }
  
  private static boolean isClose(float paramFloat1, float paramFloat2)
  {
    boolean bool;
    if (Math.abs(paramFloat1 - paramFloat2) < 1.0E-5F) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isDefaultIsRtl()
  {
    int i = ViewCompat.getLayoutDirection(this.view);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private boolean isTextDirectionHeuristicsIsRtl(CharSequence paramCharSequence, boolean paramBoolean)
  {
    TextDirectionHeuristicCompat localTextDirectionHeuristicCompat;
    if (paramBoolean) {
      localTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
    } else {
      localTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    }
    return localTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3, TimeInterpolator paramTimeInterpolator)
  {
    float f = paramFloat3;
    if (paramTimeInterpolator != null) {
      f = paramTimeInterpolator.getInterpolation(paramFloat3);
    }
    return AnimationUtils.lerp(paramFloat1, paramFloat2, f);
  }
  
  private float measureTextWidth(TextPaint paramTextPaint, CharSequence paramCharSequence)
  {
    return paramTextPaint.measureText(paramCharSequence, 0, paramCharSequence.length());
  }
  
  private static boolean rectEquals(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool;
    if ((paramRect.left == paramInt1) && (paramRect.top == paramInt2) && (paramRect.right == paramInt3) && (paramRect.bottom == paramInt4)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void setCollapsedTextBlend(float paramFloat)
  {
    this.collapsedTextBlend = paramFloat;
    ViewCompat.postInvalidateOnAnimation(this.view);
  }
  
  private boolean setCollapsedTypefaceInternal(Typeface paramTypeface)
  {
    Object localObject = this.collapsedFontCallback;
    if (localObject != null) {
      ((CancelableFontCallback)localObject).cancel();
    }
    if (this.collapsedTypefaceDefault != paramTypeface)
    {
      this.collapsedTypefaceDefault = paramTypeface;
      localObject = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), paramTypeface);
      this.collapsedTypefaceBold = ((Typeface)localObject);
      paramTypeface = (Typeface)localObject;
      if (localObject == null) {
        paramTypeface = this.collapsedTypefaceDefault;
      }
      this.collapsedTypeface = paramTypeface;
      return true;
    }
    return false;
  }
  
  private void setExpandedTextBlend(float paramFloat)
  {
    this.expandedTextBlend = paramFloat;
    ViewCompat.postInvalidateOnAnimation(this.view);
  }
  
  private boolean setExpandedTypefaceInternal(Typeface paramTypeface)
  {
    Object localObject = this.expandedFontCallback;
    if (localObject != null) {
      ((CancelableFontCallback)localObject).cancel();
    }
    if (this.expandedTypefaceDefault != paramTypeface)
    {
      this.expandedTypefaceDefault = paramTypeface;
      localObject = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), paramTypeface);
      this.expandedTypefaceBold = ((Typeface)localObject);
      paramTypeface = (Typeface)localObject;
      if (localObject == null) {
        paramTypeface = this.expandedTypefaceDefault;
      }
      this.expandedTypeface = paramTypeface;
      return true;
    }
    return false;
  }
  
  private void setInterpolatedTextSize(float paramFloat)
  {
    calculateUsingTextSize(paramFloat);
    boolean bool;
    if ((USE_SCALING_TEXTURE) && (this.scale != 1.0F)) {
      bool = true;
    } else {
      bool = false;
    }
    this.useTexture = bool;
    if (bool) {
      ensureExpandedTexture();
    }
    ViewCompat.postInvalidateOnAnimation(this.view);
  }
  
  private boolean shouldDrawMultiline()
  {
    int i = this.maxLines;
    boolean bool = true;
    if ((i <= 1) || ((this.isRtl) && (!this.fadeModeEnabled)) || (this.useTexture)) {
      bool = false;
    }
    return bool;
  }
  
  public void draw(Canvas paramCanvas)
  {
    int j = paramCanvas.save();
    if ((this.textToDraw != null) && (this.drawTitle))
    {
      this.textPaint.setTextSize(this.currentTextSize);
      float f3 = this.currentDrawX;
      float f2 = this.currentDrawY;
      int i;
      if ((this.useTexture) && (this.expandedTitleTexture != null)) {
        i = 1;
      } else {
        i = 0;
      }
      float f1 = this.scale;
      if ((f1 != 1.0F) && (!this.fadeModeEnabled)) {
        paramCanvas.scale(f1, f1, f3, f2);
      }
      if (i != 0)
      {
        paramCanvas.drawBitmap(this.expandedTitleTexture, f3, f2, this.texturePaint);
        paramCanvas.restoreToCount(j);
        return;
      }
      if ((shouldDrawMultiline()) && ((!this.fadeModeEnabled) || (this.expandedFraction > this.fadeModeThresholdFraction)))
      {
        drawMultilineTransition(paramCanvas, this.currentDrawX - this.textLayout.getLineStart(0), f2);
      }
      else
      {
        paramCanvas.translate(f3, f2);
        this.textLayout.draw(paramCanvas);
      }
      paramCanvas.restoreToCount(j);
    }
  }
  
  public void getCollapsedTextActualBounds(RectF paramRectF, int paramInt1, int paramInt2)
  {
    this.isRtl = calculateIsRtl(this.text);
    paramRectF.left = getCollapsedTextLeftBound(paramInt1, paramInt2);
    paramRectF.top = this.collapsedBounds.top;
    paramRectF.right = getCollapsedTextRightBound(paramRectF, paramInt1, paramInt2);
    paramRectF.bottom = (this.collapsedBounds.top + getCollapsedTextHeight());
  }
  
  public ColorStateList getCollapsedTextColor()
  {
    return this.collapsedTextColor;
  }
  
  public int getCollapsedTextGravity()
  {
    return this.collapsedTextGravity;
  }
  
  public float getCollapsedTextHeight()
  {
    getTextPaintCollapsed(this.tmpPaint);
    return -this.tmpPaint.ascent();
  }
  
  public float getCollapsedTextSize()
  {
    return this.collapsedTextSize;
  }
  
  public Typeface getCollapsedTypeface()
  {
    Typeface localTypeface = this.collapsedTypeface;
    if (localTypeface == null) {
      localTypeface = Typeface.DEFAULT;
    }
    return localTypeface;
  }
  
  public int getCurrentCollapsedTextColor()
  {
    return getCurrentColor(this.collapsedTextColor);
  }
  
  public int getExpandedLineCount()
  {
    return this.expandedLineCount;
  }
  
  public ColorStateList getExpandedTextColor()
  {
    return this.expandedTextColor;
  }
  
  public float getExpandedTextFullHeight()
  {
    getTextPaintExpanded(this.tmpPaint);
    return -this.tmpPaint.ascent() + this.tmpPaint.descent();
  }
  
  public int getExpandedTextGravity()
  {
    return this.expandedTextGravity;
  }
  
  public float getExpandedTextHeight()
  {
    getTextPaintExpanded(this.tmpPaint);
    return -this.tmpPaint.ascent();
  }
  
  public float getExpandedTextSize()
  {
    return this.expandedTextSize;
  }
  
  public Typeface getExpandedTypeface()
  {
    Typeface localTypeface = this.expandedTypeface;
    if (localTypeface == null) {
      localTypeface = Typeface.DEFAULT;
    }
    return localTypeface;
  }
  
  public float getExpansionFraction()
  {
    return this.expandedFraction;
  }
  
  public float getFadeModeThresholdFraction()
  {
    return this.fadeModeThresholdFraction;
  }
  
  public int getHyphenationFrequency()
  {
    return this.hyphenationFrequency;
  }
  
  public int getLineCount()
  {
    StaticLayout localStaticLayout = this.textLayout;
    int i;
    if (localStaticLayout != null) {
      i = localStaticLayout.getLineCount();
    } else {
      i = 0;
    }
    return i;
  }
  
  public float getLineSpacingAdd()
  {
    return this.textLayout.getSpacingAdd();
  }
  
  public float getLineSpacingMultiplier()
  {
    return this.textLayout.getSpacingMultiplier();
  }
  
  public int getMaxLines()
  {
    return this.maxLines;
  }
  
  public TimeInterpolator getPositionInterpolator()
  {
    return this.positionInterpolator;
  }
  
  public CharSequence getText()
  {
    return this.text;
  }
  
  public boolean isRtlTextDirectionHeuristicsEnabled()
  {
    return this.isRtlTextDirectionHeuristicsEnabled;
  }
  
  public final boolean isStateful()
  {
    ColorStateList localColorStateList = this.collapsedTextColor;
    if ((localColorStateList == null) || (!localColorStateList.isStateful()))
    {
      localColorStateList = this.expandedTextColor;
      if ((localColorStateList == null) || (!localColorStateList.isStateful())) {}
    }
    else
    {
      return true;
    }
    boolean bool = false;
    return bool;
  }
  
  public void maybeUpdateFontWeightAdjustment(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 31)
    {
      Typeface localTypeface = this.collapsedTypefaceDefault;
      if (localTypeface != null) {
        this.collapsedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(paramConfiguration, localTypeface);
      }
      localTypeface = this.expandedTypefaceDefault;
      if (localTypeface != null) {
        this.expandedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(paramConfiguration, localTypeface);
      }
      paramConfiguration = this.collapsedTypefaceBold;
      if (paramConfiguration == null) {
        paramConfiguration = this.collapsedTypefaceDefault;
      }
      this.collapsedTypeface = paramConfiguration;
      paramConfiguration = this.expandedTypefaceBold;
      if (paramConfiguration == null) {
        paramConfiguration = this.expandedTypefaceDefault;
      }
      this.expandedTypeface = paramConfiguration;
      recalculate(true);
    }
  }
  
  void onBoundsChanged()
  {
    boolean bool;
    if ((this.collapsedBounds.width() > 0) && (this.collapsedBounds.height() > 0) && (this.expandedBounds.width() > 0) && (this.expandedBounds.height() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    this.drawTitle = bool;
  }
  
  public void recalculate()
  {
    recalculate(false);
  }
  
  public void recalculate(boolean paramBoolean)
  {
    if (((this.view.getHeight() > 0) && (this.view.getWidth() > 0)) || (paramBoolean))
    {
      calculateBaseOffsets(paramBoolean);
      calculateCurrentOffsets();
    }
  }
  
  public void setCollapsedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!rectEquals(this.collapsedBounds, paramInt1, paramInt2, paramInt3, paramInt4))
    {
      this.collapsedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      this.boundsChanged = true;
      onBoundsChanged();
    }
  }
  
  public void setCollapsedBounds(Rect paramRect)
  {
    setCollapsedBounds(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
  }
  
  public void setCollapsedTextAppearance(int paramInt)
  {
    TextAppearance localTextAppearance = new TextAppearance(this.view.getContext(), paramInt);
    if (localTextAppearance.getTextColor() != null) {
      this.collapsedTextColor = localTextAppearance.getTextColor();
    }
    if (localTextAppearance.getTextSize() != 0.0F) {
      this.collapsedTextSize = localTextAppearance.getTextSize();
    }
    if (localTextAppearance.shadowColor != null) {
      this.collapsedShadowColor = localTextAppearance.shadowColor;
    }
    this.collapsedShadowDx = localTextAppearance.shadowDx;
    this.collapsedShadowDy = localTextAppearance.shadowDy;
    this.collapsedShadowRadius = localTextAppearance.shadowRadius;
    this.collapsedLetterSpacing = localTextAppearance.letterSpacing;
    CancelableFontCallback localCancelableFontCallback = this.collapsedFontCallback;
    if (localCancelableFontCallback != null) {
      localCancelableFontCallback.cancel();
    }
    this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont()
    {
      public void apply(Typeface paramAnonymousTypeface)
      {
        CollapsingTextHelper.this.setCollapsedTypeface(paramAnonymousTypeface);
      }
    }, localTextAppearance.getFallbackFont());
    localTextAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
    recalculate();
  }
  
  public void setCollapsedTextColor(ColorStateList paramColorStateList)
  {
    if (this.collapsedTextColor != paramColorStateList)
    {
      this.collapsedTextColor = paramColorStateList;
      recalculate();
    }
  }
  
  public void setCollapsedTextGravity(int paramInt)
  {
    if (this.collapsedTextGravity != paramInt)
    {
      this.collapsedTextGravity = paramInt;
      recalculate();
    }
  }
  
  public void setCollapsedTextSize(float paramFloat)
  {
    if (this.collapsedTextSize != paramFloat)
    {
      this.collapsedTextSize = paramFloat;
      recalculate();
    }
  }
  
  public void setCollapsedTypeface(Typeface paramTypeface)
  {
    if (setCollapsedTypefaceInternal(paramTypeface)) {
      recalculate();
    }
  }
  
  public void setCurrentOffsetY(int paramInt)
  {
    this.currentOffsetY = paramInt;
  }
  
  public void setExpandedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!rectEquals(this.expandedBounds, paramInt1, paramInt2, paramInt3, paramInt4))
    {
      this.expandedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      this.boundsChanged = true;
      onBoundsChanged();
    }
  }
  
  public void setExpandedBounds(Rect paramRect)
  {
    setExpandedBounds(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
  }
  
  public void setExpandedLetterSpacing(float paramFloat)
  {
    if (this.expandedLetterSpacing != paramFloat)
    {
      this.expandedLetterSpacing = paramFloat;
      recalculate();
    }
  }
  
  public void setExpandedTextAppearance(int paramInt)
  {
    TextAppearance localTextAppearance = new TextAppearance(this.view.getContext(), paramInt);
    if (localTextAppearance.getTextColor() != null) {
      this.expandedTextColor = localTextAppearance.getTextColor();
    }
    if (localTextAppearance.getTextSize() != 0.0F) {
      this.expandedTextSize = localTextAppearance.getTextSize();
    }
    if (localTextAppearance.shadowColor != null) {
      this.expandedShadowColor = localTextAppearance.shadowColor;
    }
    this.expandedShadowDx = localTextAppearance.shadowDx;
    this.expandedShadowDy = localTextAppearance.shadowDy;
    this.expandedShadowRadius = localTextAppearance.shadowRadius;
    this.expandedLetterSpacing = localTextAppearance.letterSpacing;
    CancelableFontCallback localCancelableFontCallback = this.expandedFontCallback;
    if (localCancelableFontCallback != null) {
      localCancelableFontCallback.cancel();
    }
    this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont()
    {
      public void apply(Typeface paramAnonymousTypeface)
      {
        CollapsingTextHelper.this.setExpandedTypeface(paramAnonymousTypeface);
      }
    }, localTextAppearance.getFallbackFont());
    localTextAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
    recalculate();
  }
  
  public void setExpandedTextColor(ColorStateList paramColorStateList)
  {
    if (this.expandedTextColor != paramColorStateList)
    {
      this.expandedTextColor = paramColorStateList;
      recalculate();
    }
  }
  
  public void setExpandedTextGravity(int paramInt)
  {
    if (this.expandedTextGravity != paramInt)
    {
      this.expandedTextGravity = paramInt;
      recalculate();
    }
  }
  
  public void setExpandedTextSize(float paramFloat)
  {
    if (this.expandedTextSize != paramFloat)
    {
      this.expandedTextSize = paramFloat;
      recalculate();
    }
  }
  
  public void setExpandedTypeface(Typeface paramTypeface)
  {
    if (setExpandedTypefaceInternal(paramTypeface)) {
      recalculate();
    }
  }
  
  public void setExpansionFraction(float paramFloat)
  {
    paramFloat = MathUtils.clamp(paramFloat, 0.0F, 1.0F);
    if (paramFloat != this.expandedFraction)
    {
      this.expandedFraction = paramFloat;
      calculateCurrentOffsets();
    }
  }
  
  public void setFadeModeEnabled(boolean paramBoolean)
  {
    this.fadeModeEnabled = paramBoolean;
  }
  
  public void setFadeModeStartFraction(float paramFloat)
  {
    this.fadeModeStartFraction = paramFloat;
    this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
  }
  
  public void setHyphenationFrequency(int paramInt)
  {
    this.hyphenationFrequency = paramInt;
  }
  
  public void setLineSpacingAdd(float paramFloat)
  {
    this.lineSpacingAdd = paramFloat;
  }
  
  public void setLineSpacingMultiplier(float paramFloat)
  {
    this.lineSpacingMultiplier = paramFloat;
  }
  
  public void setMaxLines(int paramInt)
  {
    if (paramInt != this.maxLines)
    {
      this.maxLines = paramInt;
      clearTexture();
      recalculate();
    }
  }
  
  public void setPositionInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.positionInterpolator = paramTimeInterpolator;
    recalculate();
  }
  
  public void setRtlTextDirectionHeuristicsEnabled(boolean paramBoolean)
  {
    this.isRtlTextDirectionHeuristicsEnabled = paramBoolean;
  }
  
  public final boolean setState(int[] paramArrayOfInt)
  {
    this.state = paramArrayOfInt;
    if (isStateful())
    {
      recalculate();
      return true;
    }
    return false;
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    if ((paramCharSequence == null) || (!TextUtils.equals(this.text, paramCharSequence)))
    {
      this.text = paramCharSequence;
      this.textToDraw = null;
      clearTexture();
      recalculate();
    }
  }
  
  public void setTextSizeInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    this.textSizeInterpolator = paramTimeInterpolator;
    recalculate();
  }
  
  public void setTypefaces(Typeface paramTypeface)
  {
    boolean bool1 = setCollapsedTypefaceInternal(paramTypeface);
    boolean bool2 = setExpandedTypefaceInternal(paramTypeface);
    if ((bool1) || (bool2)) {
      recalculate();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/CollapsingTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */