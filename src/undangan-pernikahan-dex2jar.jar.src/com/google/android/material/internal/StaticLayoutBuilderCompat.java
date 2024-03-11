package com.google.android.material.internal;

import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

final class StaticLayoutBuilderCompat
{
  static final int DEFAULT_HYPHENATION_FREQUENCY;
  static final float DEFAULT_LINE_SPACING_ADD = 0.0F;
  static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0F;
  private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
  private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
  private static final String TEXT_DIR_CLASS_LTR = "LTR";
  private static final String TEXT_DIR_CLASS_RTL = "RTL";
  private static Constructor<StaticLayout> constructor;
  private static boolean initialized;
  private static Object textDirection;
  private Layout.Alignment alignment;
  private TextUtils.TruncateAt ellipsize;
  private int end;
  private int hyphenationFrequency;
  private boolean includePad;
  private boolean isRtl;
  private float lineSpacingAdd;
  private float lineSpacingMultiplier;
  private int maxLines;
  private final TextPaint paint;
  private CharSequence source;
  private int start;
  private final int width;
  
  static
  {
    int i;
    if (Build.VERSION.SDK_INT >= 23) {
      i = 1;
    } else {
      i = 0;
    }
    DEFAULT_HYPHENATION_FREQUENCY = i;
  }
  
  private StaticLayoutBuilderCompat(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt)
  {
    this.source = paramCharSequence;
    this.paint = paramTextPaint;
    this.width = paramInt;
    this.start = 0;
    this.end = paramCharSequence.length();
    this.alignment = Layout.Alignment.ALIGN_NORMAL;
    this.maxLines = Integer.MAX_VALUE;
    this.lineSpacingAdd = 0.0F;
    this.lineSpacingMultiplier = 1.0F;
    this.hyphenationFrequency = DEFAULT_HYPHENATION_FREQUENCY;
    this.includePad = true;
    this.ellipsize = null;
  }
  
  private void createConstructorWithReflection()
    throws StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException
  {
    if (initialized) {
      return;
    }
    try
    {
      int i;
      if ((this.isRtl) && (Build.VERSION.SDK_INT >= 23)) {
        i = 1;
      } else {
        i = 0;
      }
      Class localClass;
      if (Build.VERSION.SDK_INT >= 18)
      {
        localClass = TextDirectionHeuristic.class;
        if (i != 0) {
          localObject1 = TextDirectionHeuristics.RTL;
        } else {
          localObject1 = TextDirectionHeuristics.LTR;
        }
        textDirection = localObject1;
        localObject1 = localClass;
      }
      else
      {
        Object localObject2 = StaticLayoutBuilderCompat.class.getClassLoader();
        if (this.isRtl) {
          localObject1 = "RTL";
        } else {
          localObject1 = "LTR";
        }
        localClass = ((ClassLoader)localObject2).loadClass("android.text.TextDirectionHeuristic");
        localObject2 = ((ClassLoader)localObject2).loadClass("android.text.TextDirectionHeuristics");
        textDirection = ((Class)localObject2).getField((String)localObject1).get(localObject2);
        localObject1 = localClass;
      }
      Object localObject1 = StaticLayout.class.getDeclaredConstructor(new Class[] { CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Layout.Alignment.class, localObject1, Float.TYPE, Float.TYPE, Boolean.TYPE, TextUtils.TruncateAt.class, Integer.TYPE, Integer.TYPE });
      constructor = (Constructor)localObject1;
      ((Constructor)localObject1).setAccessible(true);
      initialized = true;
      return;
    }
    catch (Exception localException)
    {
      throw new StaticLayoutBuilderCompatException(localException);
    }
  }
  
  public static StaticLayoutBuilderCompat obtain(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt)
  {
    return new StaticLayoutBuilderCompat(paramCharSequence, paramTextPaint, paramInt);
  }
  
  public StaticLayout build()
    throws StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException
  {
    if (this.source == null) {
      this.source = "";
    }
    int i = Math.max(0, this.width);
    Object localObject = this.source;
    if (this.maxLines == 1) {
      localObject = TextUtils.ellipsize(this.source, this.paint, i, this.ellipsize);
    }
    this.end = Math.min(((CharSequence)localObject).length(), this.end);
    if (Build.VERSION.SDK_INT >= 23)
    {
      if ((this.isRtl) && (this.maxLines == 1)) {
        this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
      }
      StaticLayout.Builder localBuilder = StaticLayout.Builder.obtain((CharSequence)localObject, this.start, this.end, this.paint, i);
      localBuilder.setAlignment(this.alignment);
      localBuilder.setIncludePad(this.includePad);
      if (this.isRtl) {
        localObject = TextDirectionHeuristics.RTL;
      } else {
        localObject = TextDirectionHeuristics.LTR;
      }
      localBuilder.setTextDirection((TextDirectionHeuristic)localObject);
      localObject = this.ellipsize;
      if (localObject != null) {
        localBuilder.setEllipsize((TextUtils.TruncateAt)localObject);
      }
      localBuilder.setMaxLines(this.maxLines);
      float f = this.lineSpacingAdd;
      if ((f != 0.0F) || (this.lineSpacingMultiplier != 1.0F)) {
        localBuilder.setLineSpacing(f, this.lineSpacingMultiplier);
      }
      if (this.maxLines > 1) {
        localBuilder.setHyphenationFrequency(this.hyphenationFrequency);
      }
      return localBuilder.build();
    }
    createConstructorWithReflection();
    try
    {
      localObject = (StaticLayout)((Constructor)Preconditions.checkNotNull(constructor)).newInstance(new Object[] { localObject, Integer.valueOf(this.start), Integer.valueOf(this.end), this.paint, Integer.valueOf(i), this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0F), Float.valueOf(0.0F), Boolean.valueOf(this.includePad), null, Integer.valueOf(i), Integer.valueOf(this.maxLines) });
      return (StaticLayout)localObject;
    }
    catch (Exception localException)
    {
      throw new StaticLayoutBuilderCompatException(localException);
    }
  }
  
  public StaticLayoutBuilderCompat setAlignment(Layout.Alignment paramAlignment)
  {
    this.alignment = paramAlignment;
    return this;
  }
  
  public StaticLayoutBuilderCompat setEllipsize(TextUtils.TruncateAt paramTruncateAt)
  {
    this.ellipsize = paramTruncateAt;
    return this;
  }
  
  public StaticLayoutBuilderCompat setEnd(int paramInt)
  {
    this.end = paramInt;
    return this;
  }
  
  public StaticLayoutBuilderCompat setHyphenationFrequency(int paramInt)
  {
    this.hyphenationFrequency = paramInt;
    return this;
  }
  
  public StaticLayoutBuilderCompat setIncludePad(boolean paramBoolean)
  {
    this.includePad = paramBoolean;
    return this;
  }
  
  public StaticLayoutBuilderCompat setIsRtl(boolean paramBoolean)
  {
    this.isRtl = paramBoolean;
    return this;
  }
  
  public StaticLayoutBuilderCompat setLineSpacing(float paramFloat1, float paramFloat2)
  {
    this.lineSpacingAdd = paramFloat1;
    this.lineSpacingMultiplier = paramFloat2;
    return this;
  }
  
  public StaticLayoutBuilderCompat setMaxLines(int paramInt)
  {
    this.maxLines = paramInt;
    return this;
  }
  
  public StaticLayoutBuilderCompat setStart(int paramInt)
  {
    this.start = paramInt;
    return this;
  }
  
  static class StaticLayoutBuilderCompatException
    extends Exception
  {
    StaticLayoutBuilderCompatException(Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/StaticLayoutBuilderCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */