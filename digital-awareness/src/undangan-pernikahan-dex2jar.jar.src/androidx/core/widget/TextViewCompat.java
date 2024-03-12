package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.PrecomputedText.Params;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.text.PrecomputedTextCompat.Params;
import androidx.core.text.PrecomputedTextCompat.Params.Builder;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class TextViewCompat
{
  public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
  public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
  private static final int LINES = 1;
  private static final String LOG_TAG = "TextViewCompat";
  private static Field sMaxModeField;
  private static boolean sMaxModeFieldFetched;
  private static Field sMaximumField;
  private static boolean sMaximumFieldFetched;
  private static Field sMinModeField;
  private static boolean sMinModeFieldFetched;
  private static Field sMinimumField;
  private static boolean sMinimumFieldFetched;
  
  public static int getAutoSizeMaxTextSize(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      return Api26Impl.getAutoSizeMaxTextSize(paramTextView);
    }
    if ((paramTextView instanceof AutoSizeableTextView)) {
      return ((AutoSizeableTextView)paramTextView).getAutoSizeMaxTextSize();
    }
    return -1;
  }
  
  public static int getAutoSizeMinTextSize(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      return Api26Impl.getAutoSizeMinTextSize(paramTextView);
    }
    if ((paramTextView instanceof AutoSizeableTextView)) {
      return ((AutoSizeableTextView)paramTextView).getAutoSizeMinTextSize();
    }
    return -1;
  }
  
  public static int getAutoSizeStepGranularity(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      return Api26Impl.getAutoSizeStepGranularity(paramTextView);
    }
    if ((paramTextView instanceof AutoSizeableTextView)) {
      return ((AutoSizeableTextView)paramTextView).getAutoSizeStepGranularity();
    }
    return -1;
  }
  
  public static int[] getAutoSizeTextAvailableSizes(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      return Api26Impl.getAutoSizeTextAvailableSizes(paramTextView);
    }
    if ((paramTextView instanceof AutoSizeableTextView)) {
      return ((AutoSizeableTextView)paramTextView).getAutoSizeTextAvailableSizes();
    }
    return new int[0];
  }
  
  public static int getAutoSizeTextType(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      return Api26Impl.getAutoSizeTextType(paramTextView);
    }
    if ((paramTextView instanceof AutoSizeableTextView)) {
      return ((AutoSizeableTextView)paramTextView).getAutoSizeTextType();
    }
    return 0;
  }
  
  public static ColorStateList getCompoundDrawableTintList(TextView paramTextView)
  {
    Preconditions.checkNotNull(paramTextView);
    if (Build.VERSION.SDK_INT >= 24) {
      return Api23Impl.getCompoundDrawableTintList(paramTextView);
    }
    if ((paramTextView instanceof TintableCompoundDrawablesView)) {
      return ((TintableCompoundDrawablesView)paramTextView).getSupportCompoundDrawablesTintList();
    }
    return null;
  }
  
  public static PorterDuff.Mode getCompoundDrawableTintMode(TextView paramTextView)
  {
    Preconditions.checkNotNull(paramTextView);
    if (Build.VERSION.SDK_INT >= 24) {
      return Api23Impl.getCompoundDrawableTintMode(paramTextView);
    }
    if ((paramTextView instanceof TintableCompoundDrawablesView)) {
      return ((TintableCompoundDrawablesView)paramTextView).getSupportCompoundDrawablesTintMode();
    }
    return null;
  }
  
  public static Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api17Impl.getCompoundDrawablesRelative(paramTextView);
    }
    if (Build.VERSION.SDK_INT >= 17)
    {
      int j = Api17Impl.getLayoutDirection(paramTextView);
      int i = 1;
      if (j != 1) {
        i = 0;
      }
      paramTextView = paramTextView.getCompoundDrawables();
      if (i != 0)
      {
        Object localObject1 = paramTextView[2];
        Object localObject2 = paramTextView[0];
        paramTextView[0] = localObject1;
        paramTextView[2] = localObject2;
      }
      return paramTextView;
    }
    return paramTextView.getCompoundDrawables();
  }
  
  public static int getFirstBaselineToTopHeight(TextView paramTextView)
  {
    return paramTextView.getPaddingTop() - paramTextView.getPaint().getFontMetricsInt().top;
  }
  
  public static int getLastBaselineToBottomHeight(TextView paramTextView)
  {
    return paramTextView.getPaddingBottom() + paramTextView.getPaint().getFontMetricsInt().bottom;
  }
  
  public static int getMaxLines(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getMaxLines(paramTextView);
    }
    if (!sMaxModeFieldFetched)
    {
      sMaxModeField = retrieveField("mMaxMode");
      sMaxModeFieldFetched = true;
    }
    Field localField = sMaxModeField;
    if ((localField != null) && (retrieveIntFromField(localField, paramTextView) == 1))
    {
      if (!sMaximumFieldFetched)
      {
        sMaximumField = retrieveField("mMaximum");
        sMaximumFieldFetched = true;
      }
      localField = sMaximumField;
      if (localField != null) {
        return retrieveIntFromField(localField, paramTextView);
      }
    }
    return -1;
  }
  
  public static int getMinLines(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getMinLines(paramTextView);
    }
    if (!sMinModeFieldFetched)
    {
      sMinModeField = retrieveField("mMinMode");
      sMinModeFieldFetched = true;
    }
    Field localField = sMinModeField;
    if ((localField != null) && (retrieveIntFromField(localField, paramTextView) == 1))
    {
      if (!sMinimumFieldFetched)
      {
        sMinimumField = retrieveField("mMinimum");
        sMinimumFieldFetched = true;
      }
      localField = sMinimumField;
      if (localField != null) {
        return retrieveIntFromField(localField, paramTextView);
      }
    }
    return -1;
  }
  
  private static int getTextDirection(TextDirectionHeuristic paramTextDirectionHeuristic)
  {
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
      return 1;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
      return 1;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
      return 2;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.LTR) {
      return 3;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.RTL) {
      return 4;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
      return 5;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
      return 6;
    }
    if (paramTextDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
      return 7;
    }
    return 1;
  }
  
  private static TextDirectionHeuristic getTextDirectionHeuristic(TextView paramTextView)
  {
    if ((paramTextView.getTransformationMethod() instanceof PasswordTransformationMethod)) {
      return TextDirectionHeuristics.LTR;
    }
    int j = Build.VERSION.SDK_INT;
    int i = 0;
    if ((j >= 28) && ((paramTextView.getInputType() & 0xF) == 3))
    {
      i = Character.getDirectionality(Api28Impl.getDigitStrings(Api24Impl.getInstance(Api17Impl.getTextLocale(paramTextView)))[0].codePointAt(0));
      if ((i != 1) && (i != 2)) {
        return TextDirectionHeuristics.LTR;
      }
      return TextDirectionHeuristics.RTL;
    }
    if (Api17Impl.getLayoutDirection(paramTextView) == 1) {
      i = 1;
    }
    switch (Api17Impl.getTextDirection(paramTextView))
    {
    default: 
      if (i != 0) {
        paramTextView = TextDirectionHeuristics.FIRSTSTRONG_RTL;
      }
      break;
    case 7: 
      return TextDirectionHeuristics.FIRSTSTRONG_RTL;
    case 6: 
      return TextDirectionHeuristics.FIRSTSTRONG_LTR;
    case 5: 
      return TextDirectionHeuristics.LOCALE;
    case 4: 
      return TextDirectionHeuristics.RTL;
    case 3: 
      return TextDirectionHeuristics.LTR;
    case 2: 
      return TextDirectionHeuristics.ANYRTL_LTR;
    }
    paramTextView = TextDirectionHeuristics.FIRSTSTRONG_LTR;
    return paramTextView;
  }
  
  public static PrecomputedTextCompat.Params getTextMetricsParams(TextView paramTextView)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return new PrecomputedTextCompat.Params(Api28Impl.getTextMetricsParams(paramTextView));
    }
    PrecomputedTextCompat.Params.Builder localBuilder = new PrecomputedTextCompat.Params.Builder(new TextPaint(paramTextView.getPaint()));
    if (Build.VERSION.SDK_INT >= 23)
    {
      localBuilder.setBreakStrategy(Api23Impl.getBreakStrategy(paramTextView));
      localBuilder.setHyphenationFrequency(Api23Impl.getHyphenationFrequency(paramTextView));
    }
    if (Build.VERSION.SDK_INT >= 18) {
      localBuilder.setTextDirection(getTextDirectionHeuristic(paramTextView));
    }
    return localBuilder.build();
  }
  
  private static Field retrieveField(String paramString)
  {
    Object localObject = null;
    try
    {
      Field localField = TextView.class.getDeclaredField(paramString);
      localObject = localField;
      localField.setAccessible(true);
      localObject = localField;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.e("TextViewCompat", "Could not retrieve " + paramString + " field.");
    }
    return (Field)localObject;
  }
  
  private static int retrieveIntFromField(Field paramField, TextView paramTextView)
  {
    try
    {
      int i = paramField.getInt(paramTextView);
      return i;
    }
    catch (IllegalAccessException paramTextView)
    {
      Log.d("TextViewCompat", "Could not retrieve value of " + paramField.getName() + " field.");
    }
    return -1;
  }
  
  public static void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IllegalArgumentException
  {
    if (Build.VERSION.SDK_INT >= 27) {
      Api26Impl.setAutoSizeTextTypeUniformWithConfiguration(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
    } else if ((paramTextView instanceof AutoSizeableTextView)) {
      ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
    throws IllegalArgumentException
  {
    if (Build.VERSION.SDK_INT >= 27) {
      Api26Impl.setAutoSizeTextTypeUniformWithPresetSizes(paramTextView, paramArrayOfInt, paramInt);
    } else if ((paramTextView instanceof AutoSizeableTextView)) {
      ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
  }
  
  public static void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 27) {
      Api26Impl.setAutoSizeTextTypeWithDefaults(paramTextView, paramInt);
    } else if ((paramTextView instanceof AutoSizeableTextView)) {
      ((AutoSizeableTextView)paramTextView).setAutoSizeTextTypeWithDefaults(paramInt);
    }
  }
  
  public static void setCompoundDrawableTintList(TextView paramTextView, ColorStateList paramColorStateList)
  {
    Preconditions.checkNotNull(paramTextView);
    if (Build.VERSION.SDK_INT >= 24) {
      Api23Impl.setCompoundDrawableTintList(paramTextView, paramColorStateList);
    } else if ((paramTextView instanceof TintableCompoundDrawablesView)) {
      ((TintableCompoundDrawablesView)paramTextView).setSupportCompoundDrawablesTintList(paramColorStateList);
    }
  }
  
  public static void setCompoundDrawableTintMode(TextView paramTextView, PorterDuff.Mode paramMode)
  {
    Preconditions.checkNotNull(paramTextView);
    if (Build.VERSION.SDK_INT >= 24) {
      Api23Impl.setCompoundDrawableTintMode(paramTextView, paramMode);
    } else if ((paramTextView instanceof TintableCompoundDrawablesView)) {
      ((TintableCompoundDrawablesView)paramTextView).setSupportCompoundDrawablesTintMode(paramMode);
    }
  }
  
  public static void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      Api17Impl.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    else if (Build.VERSION.SDK_INT >= 17)
    {
      int j = Api17Impl.getLayoutDirection(paramTextView);
      int i = 1;
      if (j != 1) {
        i = 0;
      }
      Drawable localDrawable;
      if (i != 0) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (i == 0) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawables(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
    }
    else
    {
      paramTextView.setCompoundDrawables(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else if (Build.VERSION.SDK_INT >= 17)
    {
      int i = Api17Impl.getLayoutDirection(paramTextView);
      int j = 1;
      if (i != 1) {
        j = 0;
      }
      if (j != 0) {
        i = paramInt3;
      } else {
        i = paramInt1;
      }
      if (j == 0) {
        paramInt1 = paramInt3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(i, paramInt2, paramInt1, paramInt4);
    }
    else
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
  {
    if (Build.VERSION.SDK_INT >= 18)
    {
      Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    else if (Build.VERSION.SDK_INT >= 17)
    {
      int j = Api17Impl.getLayoutDirection(paramTextView);
      int i = 1;
      if (j != 1) {
        i = 0;
      }
      Drawable localDrawable;
      if (i != 0) {
        localDrawable = paramDrawable3;
      } else {
        localDrawable = paramDrawable1;
      }
      if (i == 0) {
        paramDrawable1 = paramDrawable3;
      }
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(localDrawable, paramDrawable2, paramDrawable1, paramDrawable4);
    }
    else
    {
      paramTextView.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
  }
  
  public static void setCustomSelectionActionModeCallback(TextView paramTextView, ActionMode.Callback paramCallback)
  {
    paramTextView.setCustomSelectionActionModeCallback(wrapCustomSelectionActionModeCallback(paramTextView, paramCallback));
  }
  
  public static void setFirstBaselineToTopHeight(TextView paramTextView, int paramInt)
  {
    Preconditions.checkArgumentNonnegative(paramInt);
    if (Build.VERSION.SDK_INT >= 28)
    {
      Api28Impl.setFirstBaselineToTopHeight(paramTextView, paramInt);
      return;
    }
    Paint.FontMetricsInt localFontMetricsInt = paramTextView.getPaint().getFontMetricsInt();
    int i;
    if ((Build.VERSION.SDK_INT >= 16) && (!Api16Impl.getIncludeFontPadding(paramTextView))) {
      i = localFontMetricsInt.ascent;
    } else {
      i = localFontMetricsInt.top;
    }
    if (paramInt > Math.abs(i)) {
      paramTextView.setPadding(paramTextView.getPaddingLeft(), paramInt + i, paramTextView.getPaddingRight(), paramTextView.getPaddingBottom());
    }
  }
  
  public static void setLastBaselineToBottomHeight(TextView paramTextView, int paramInt)
  {
    Preconditions.checkArgumentNonnegative(paramInt);
    Paint.FontMetricsInt localFontMetricsInt = paramTextView.getPaint().getFontMetricsInt();
    int i;
    if ((Build.VERSION.SDK_INT >= 16) && (!Api16Impl.getIncludeFontPadding(paramTextView))) {
      i = localFontMetricsInt.descent;
    } else {
      i = localFontMetricsInt.bottom;
    }
    if (paramInt > Math.abs(i)) {
      paramTextView.setPadding(paramTextView.getPaddingLeft(), paramTextView.getPaddingTop(), paramTextView.getPaddingRight(), paramInt - i);
    }
  }
  
  public static void setLineHeight(TextView paramTextView, int paramInt)
  {
    Preconditions.checkArgumentNonnegative(paramInt);
    int i = paramTextView.getPaint().getFontMetricsInt(null);
    if (paramInt != i) {
      paramTextView.setLineSpacing(paramInt - i, 1.0F);
    }
  }
  
  public static void setPrecomputedText(TextView paramTextView, PrecomputedTextCompat paramPrecomputedTextCompat)
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      paramTextView.setText(paramPrecomputedTextCompat.getPrecomputedText());
    }
    else
    {
      if (!getTextMetricsParams(paramTextView).equalsWithoutTextDirection(paramPrecomputedTextCompat.getParams())) {
        break label39;
      }
      paramTextView.setText(paramPrecomputedTextCompat);
    }
    return;
    label39:
    throw new IllegalArgumentException("Given text can not be applied to TextView.");
  }
  
  public static void setTextAppearance(TextView paramTextView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      paramTextView.setTextAppearance(paramInt);
    } else {
      paramTextView.setTextAppearance(paramTextView.getContext(), paramInt);
    }
  }
  
  public static void setTextMetricsParams(TextView paramTextView, PrecomputedTextCompat.Params paramParams)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      Api17Impl.setTextDirection(paramTextView, getTextDirection(paramParams.getTextDirection()));
    }
    if (Build.VERSION.SDK_INT < 23)
    {
      float f = paramParams.getTextPaint().getTextScaleX();
      paramTextView.getPaint().set(paramParams.getTextPaint());
      if (f == paramTextView.getTextScaleX()) {
        paramTextView.setTextScaleX(f / 2.0F + 1.0F);
      }
      paramTextView.setTextScaleX(f);
    }
    else
    {
      paramTextView.getPaint().set(paramParams.getTextPaint());
      Api23Impl.setBreakStrategy(paramTextView, paramParams.getBreakStrategy());
      Api23Impl.setHyphenationFrequency(paramTextView, paramParams.getHyphenationFrequency());
    }
  }
  
  public static ActionMode.Callback unwrapCustomSelectionActionModeCallback(ActionMode.Callback paramCallback)
  {
    if (((paramCallback instanceof OreoCallback)) && (Build.VERSION.SDK_INT >= 26)) {
      return ((OreoCallback)paramCallback).getWrappedCallback();
    }
    return paramCallback;
  }
  
  public static ActionMode.Callback wrapCustomSelectionActionModeCallback(TextView paramTextView, ActionMode.Callback paramCallback)
  {
    if ((Build.VERSION.SDK_INT >= 26) && (Build.VERSION.SDK_INT <= 27) && (!(paramCallback instanceof OreoCallback)) && (paramCallback != null)) {
      return new OreoCallback(paramCallback, paramTextView);
    }
    return paramCallback;
  }
  
  static class Api16Impl
  {
    static boolean getIncludeFontPadding(TextView paramTextView)
    {
      return paramTextView.getIncludeFontPadding();
    }
    
    static int getMaxLines(TextView paramTextView)
    {
      return paramTextView.getMaxLines();
    }
    
    static int getMinLines(TextView paramTextView)
    {
      return paramTextView.getMinLines();
    }
  }
  
  static class Api17Impl
  {
    static Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawablesRelative();
    }
    
    static int getLayoutDirection(View paramView)
    {
      return paramView.getLayoutDirection();
    }
    
    static int getTextDirection(View paramView)
    {
      return paramView.getTextDirection();
    }
    
    static Locale getTextLocale(TextView paramTextView)
    {
      return paramTextView.getTextLocale();
    }
    
    static void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelative(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    static void setTextDirection(View paramView, int paramInt)
    {
      paramView.setTextDirection(paramInt);
    }
  }
  
  static class Api23Impl
  {
    static int getBreakStrategy(TextView paramTextView)
    {
      return paramTextView.getBreakStrategy();
    }
    
    static ColorStateList getCompoundDrawableTintList(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawableTintList();
    }
    
    static PorterDuff.Mode getCompoundDrawableTintMode(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawableTintMode();
    }
    
    static int getHyphenationFrequency(TextView paramTextView)
    {
      return paramTextView.getHyphenationFrequency();
    }
    
    static void setBreakStrategy(TextView paramTextView, int paramInt)
    {
      paramTextView.setBreakStrategy(paramInt);
    }
    
    static void setCompoundDrawableTintList(TextView paramTextView, ColorStateList paramColorStateList)
    {
      paramTextView.setCompoundDrawableTintList(paramColorStateList);
    }
    
    static void setCompoundDrawableTintMode(TextView paramTextView, PorterDuff.Mode paramMode)
    {
      paramTextView.setCompoundDrawableTintMode(paramMode);
    }
    
    static void setHyphenationFrequency(TextView paramTextView, int paramInt)
    {
      paramTextView.setHyphenationFrequency(paramInt);
    }
  }
  
  static class Api24Impl
  {
    static DecimalFormatSymbols getInstance(Locale paramLocale)
    {
      return DecimalFormatSymbols.getInstance(paramLocale);
    }
  }
  
  static class Api26Impl
  {
    static int getAutoSizeMaxTextSize(TextView paramTextView)
    {
      return paramTextView.getAutoSizeMaxTextSize();
    }
    
    static int getAutoSizeMinTextSize(TextView paramTextView)
    {
      return paramTextView.getAutoSizeMinTextSize();
    }
    
    static int getAutoSizeStepGranularity(TextView paramTextView)
    {
      return paramTextView.getAutoSizeStepGranularity();
    }
    
    static int[] getAutoSizeTextAvailableSizes(TextView paramTextView)
    {
      return paramTextView.getAutoSizeTextAvailableSizes();
    }
    
    static int getAutoSizeTextType(TextView paramTextView)
    {
      return paramTextView.getAutoSizeTextType();
    }
    
    static void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
    {
      paramTextView.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
    
    static void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt)
    {
      paramTextView.setAutoSizeTextTypeWithDefaults(paramInt);
    }
  }
  
  static class Api28Impl
  {
    static String[] getDigitStrings(DecimalFormatSymbols paramDecimalFormatSymbols)
    {
      return paramDecimalFormatSymbols.getDigitStrings();
    }
    
    static PrecomputedText.Params getTextMetricsParams(TextView paramTextView)
    {
      return paramTextView.getTextMetricsParams();
    }
    
    static void setFirstBaselineToTopHeight(TextView paramTextView, int paramInt)
    {
      paramTextView.setFirstBaselineToTopHeight(paramInt);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AutoSizeTextType {}
  
  private static class OreoCallback
    implements ActionMode.Callback
  {
    private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
    private final ActionMode.Callback mCallback;
    private boolean mCanUseMenuBuilderReferences;
    private boolean mInitializedMenuBuilderReferences;
    private Class<?> mMenuBuilderClass;
    private Method mMenuBuilderRemoveItemAtMethod;
    private final TextView mTextView;
    
    OreoCallback(ActionMode.Callback paramCallback, TextView paramTextView)
    {
      this.mCallback = paramCallback;
      this.mTextView = paramTextView;
      this.mInitializedMenuBuilderReferences = false;
    }
    
    private Intent createProcessTextIntent()
    {
      return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
    }
    
    private Intent createProcessTextIntentForResolveInfo(ResolveInfo paramResolveInfo, TextView paramTextView)
    {
      return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", isEditable(paramTextView) ^ true).setClassName(paramResolveInfo.activityInfo.packageName, paramResolveInfo.activityInfo.name);
    }
    
    private List<ResolveInfo> getSupportedActivities(Context paramContext, PackageManager paramPackageManager)
    {
      ArrayList localArrayList = new ArrayList();
      if (!(paramContext instanceof Activity)) {
        return localArrayList;
      }
      paramPackageManager = paramPackageManager.queryIntentActivities(createProcessTextIntent(), 0).iterator();
      while (paramPackageManager.hasNext())
      {
        ResolveInfo localResolveInfo = (ResolveInfo)paramPackageManager.next();
        if (isSupportedActivity(localResolveInfo, paramContext)) {
          localArrayList.add(localResolveInfo);
        }
      }
      return localArrayList;
    }
    
    private boolean isEditable(TextView paramTextView)
    {
      boolean bool;
      if (((paramTextView instanceof Editable)) && (paramTextView.onCheckIsTextEditor()) && (paramTextView.isEnabled())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private boolean isSupportedActivity(ResolveInfo paramResolveInfo, Context paramContext)
    {
      boolean bool2 = paramContext.getPackageName().equals(paramResolveInfo.activityInfo.packageName);
      boolean bool1 = true;
      if (bool2) {
        return true;
      }
      if (!paramResolveInfo.activityInfo.exported) {
        return false;
      }
      if ((paramResolveInfo.activityInfo.permission != null) && (paramContext.checkSelfPermission(paramResolveInfo.activityInfo.permission) != 0)) {
        bool1 = false;
      }
      return bool1;
    }
    
    private void recomputeProcessTextMenuItems(Menu paramMenu)
    {
      Object localObject2 = this.mTextView.getContext();
      PackageManager localPackageManager = ((Context)localObject2).getPackageManager();
      if (!this.mInitializedMenuBuilderReferences)
      {
        this.mInitializedMenuBuilderReferences = true;
        try
        {
          Class localClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
          this.mMenuBuilderClass = localClass;
          this.mMenuBuilderRemoveItemAtMethod = localClass.getDeclaredMethod("removeItemAt", new Class[] { Integer.TYPE });
          this.mCanUseMenuBuilderReferences = true;
        }
        catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
        this.mMenuBuilderClass = null;
        this.mMenuBuilderRemoveItemAtMethod = null;
        this.mCanUseMenuBuilderReferences = false;
      }
      try
      {
        if ((this.mCanUseMenuBuilderReferences) && (this.mMenuBuilderClass.isInstance(paramMenu))) {
          localObject1 = this.mMenuBuilderRemoveItemAtMethod;
        } else {
          localObject1 = paramMenu.getClass().getDeclaredMethod("removeItemAt", new Class[] { Integer.TYPE });
        }
        for (int i = paramMenu.size() - 1; i >= 0; i--)
        {
          MenuItem localMenuItem = paramMenu.getItem(i);
          if ((localMenuItem.getIntent() != null) && ("android.intent.action.PROCESS_TEXT".equals(localMenuItem.getIntent().getAction()))) {
            ((Method)localObject1).invoke(paramMenu, new Object[] { Integer.valueOf(i) });
          }
        }
        Object localObject1 = getSupportedActivities((Context)localObject2, localPackageManager);
        for (i = 0; i < ((List)localObject1).size(); i++)
        {
          localObject2 = (ResolveInfo)((List)localObject1).get(i);
          paramMenu.add(0, 0, i + 100, ((ResolveInfo)localObject2).loadLabel(localPackageManager)).setIntent(createProcessTextIntentForResolveInfo((ResolveInfo)localObject2, this.mTextView)).setShowAsAction(1);
        }
        return;
      }
      catch (InvocationTargetException paramMenu) {}catch (IllegalAccessException paramMenu) {}catch (NoSuchMethodException paramMenu) {}
    }
    
    ActionMode.Callback getWrappedCallback()
    {
      return this.mCallback;
    }
    
    public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mCallback.onActionItemClicked(paramActionMode, paramMenuItem);
    }
    
    public boolean onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mCallback.onCreateActionMode(paramActionMode, paramMenu);
    }
    
    public void onDestroyActionMode(ActionMode paramActionMode)
    {
      this.mCallback.onDestroyActionMode(paramActionMode);
    }
    
    public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
    {
      recomputeProcessTextMenuItems(paramMenu);
      return this.mCallback.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/TextViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */