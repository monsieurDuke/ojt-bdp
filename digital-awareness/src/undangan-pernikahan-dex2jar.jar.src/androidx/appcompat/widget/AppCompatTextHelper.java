package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.appcompat.R.styleable;
import androidx.core.content.res.ResourcesCompat.FontCallback;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

class AppCompatTextHelper
{
  private static final int MONOSPACE = 3;
  private static final int SANS = 1;
  private static final int SERIF = 2;
  private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
  private boolean mAsyncFontPending;
  private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
  private TintInfo mDrawableBottomTint;
  private TintInfo mDrawableEndTint;
  private TintInfo mDrawableLeftTint;
  private TintInfo mDrawableRightTint;
  private TintInfo mDrawableStartTint;
  private TintInfo mDrawableTint;
  private TintInfo mDrawableTopTint;
  private Typeface mFontTypeface;
  private int mFontWeight = -1;
  private int mStyle = 0;
  private final TextView mView;
  
  AppCompatTextHelper(TextView paramTextView)
  {
    this.mView = paramTextView;
    this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(paramTextView);
  }
  
  private void applyCompoundDrawableTint(Drawable paramDrawable, TintInfo paramTintInfo)
  {
    if ((paramDrawable != null) && (paramTintInfo != null)) {
      AppCompatDrawableManager.tintDrawable(paramDrawable, paramTintInfo, this.mView.getDrawableState());
    }
  }
  
  private static TintInfo createTintInfo(Context paramContext, AppCompatDrawableManager paramAppCompatDrawableManager, int paramInt)
  {
    paramContext = paramAppCompatDrawableManager.getTintList(paramContext, paramInt);
    if (paramContext != null)
    {
      paramAppCompatDrawableManager = new TintInfo();
      paramAppCompatDrawableManager.mHasTintList = true;
      paramAppCompatDrawableManager.mTintList = paramContext;
      return paramAppCompatDrawableManager;
    }
    return null;
  }
  
  private void setCompoundDrawables(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4, Drawable paramDrawable5, Drawable paramDrawable6)
  {
    if ((Build.VERSION.SDK_INT >= 17) && ((paramDrawable5 != null) || (paramDrawable6 != null)))
    {
      Drawable[] arrayOfDrawable = Api17Impl.getCompoundDrawablesRelative(this.mView);
      TextView localTextView = this.mView;
      if (paramDrawable5 != null) {
        paramDrawable1 = paramDrawable5;
      } else {
        paramDrawable1 = arrayOfDrawable[0];
      }
      if (paramDrawable2 == null) {
        paramDrawable2 = arrayOfDrawable[1];
      }
      if (paramDrawable6 != null) {
        paramDrawable3 = paramDrawable6;
      } else {
        paramDrawable3 = arrayOfDrawable[2];
      }
      if (paramDrawable4 == null) {
        paramDrawable4 = arrayOfDrawable[3];
      }
      Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(localTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    else
    {
      if ((paramDrawable1 != null) || (paramDrawable2 != null) || (paramDrawable3 != null) || (paramDrawable4 != null)) {
        break label127;
      }
    }
    return;
    label127:
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramDrawable6 = Api17Impl.getCompoundDrawablesRelative(this.mView);
      if ((paramDrawable6[0] != null) || (paramDrawable6[2] != null))
      {
        paramDrawable3 = this.mView;
        paramDrawable5 = paramDrawable6[0];
        if (paramDrawable2 != null) {
          paramDrawable1 = paramDrawable2;
        } else {
          paramDrawable1 = paramDrawable6[1];
        }
        paramDrawable2 = paramDrawable6[2];
        if (paramDrawable4 == null) {
          paramDrawable4 = paramDrawable6[3];
        }
        Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable3, paramDrawable5, paramDrawable1, paramDrawable2, paramDrawable4);
        return;
      }
    }
    paramDrawable6 = this.mView.getCompoundDrawables();
    paramDrawable5 = this.mView;
    if (paramDrawable1 == null) {
      paramDrawable1 = paramDrawable6[0];
    }
    if (paramDrawable2 == null) {
      paramDrawable2 = paramDrawable6[1];
    }
    if (paramDrawable3 == null) {
      paramDrawable3 = paramDrawable6[2];
    }
    if (paramDrawable4 == null) {
      paramDrawable4 = paramDrawable6[3];
    }
    paramDrawable5.setCompoundDrawablesWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  private void setCompoundTints()
  {
    TintInfo localTintInfo = this.mDrawableTint;
    this.mDrawableLeftTint = localTintInfo;
    this.mDrawableTopTint = localTintInfo;
    this.mDrawableRightTint = localTintInfo;
    this.mDrawableBottomTint = localTintInfo;
    this.mDrawableStartTint = localTintInfo;
    this.mDrawableEndTint = localTintInfo;
  }
  
  private void setTextSizeInternal(int paramInt, float paramFloat)
  {
    this.mAutoSizeTextHelper.setTextSizeInternal(paramInt, paramFloat);
  }
  
  private void updateTypefaceAndStyle(Context paramContext, TintTypedArray paramTintTypedArray)
  {
    this.mStyle = paramTintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i >= 28)
    {
      i = paramTintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
      this.mFontWeight = i;
      if (i != -1) {
        this.mStyle = (this.mStyle & 0x2 | 0x0);
      }
    }
    if ((!paramTintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily)) && (!paramTintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)))
    {
      if (paramTintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface))
      {
        this.mAsyncFontPending = false;
        switch (paramTintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1))
        {
        default: 
          break;
        case 3: 
          this.mFontTypeface = Typeface.MONOSPACE;
          break;
        case 2: 
          this.mFontTypeface = Typeface.SERIF;
          break;
        case 1: 
          this.mFontTypeface = Typeface.SANS_SERIF;
        }
      }
      return;
    }
    this.mFontTypeface = null;
    if (paramTintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
      i = R.styleable.TextAppearance_fontFamily;
    } else {
      i = R.styleable.TextAppearance_android_fontFamily;
    }
    final int k = this.mFontWeight;
    final int j = this.mStyle;
    boolean bool1;
    if (!paramContext.isRestricted())
    {
      paramContext = new ResourcesCompat.FontCallback()
      {
        public void onFontRetrievalFailed(int paramAnonymousInt) {}
        
        public void onFontRetrieved(Typeface paramAnonymousTypeface)
        {
          Typeface localTypeface = paramAnonymousTypeface;
          if (Build.VERSION.SDK_INT >= 28)
          {
            int i = k;
            localTypeface = paramAnonymousTypeface;
            if (i != -1)
            {
              boolean bool;
              if ((j & 0x2) != 0) {
                bool = true;
              } else {
                bool = false;
              }
              localTypeface = AppCompatTextHelper.Api28Impl.create(paramAnonymousTypeface, i, bool);
            }
          }
          AppCompatTextHelper.this.onAsyncTypefaceReceived(this.val$textViewWeak, localTypeface);
        }
      };
      try
      {
        paramContext = paramTintTypedArray.getFont(i, this.mStyle, paramContext);
        if (paramContext != null) {
          if ((Build.VERSION.SDK_INT >= 28) && (this.mFontWeight != -1))
          {
            paramContext = Typeface.create(paramContext, 0);
            j = this.mFontWeight;
            if ((this.mStyle & 0x2) != 0) {
              bool1 = true;
            } else {
              bool1 = false;
            }
            this.mFontTypeface = Api28Impl.create(paramContext, j, bool1);
          }
          else
          {
            this.mFontTypeface = paramContext;
          }
        }
        if (this.mFontTypeface == null) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.mAsyncFontPending = bool1;
      }
      catch (Resources.NotFoundException paramContext) {}catch (UnsupportedOperationException paramContext) {}
    }
    if (this.mFontTypeface == null)
    {
      paramContext = paramTintTypedArray.getString(i);
      if (paramContext != null) {
        if ((Build.VERSION.SDK_INT >= 28) && (this.mFontWeight != -1))
        {
          paramContext = Typeface.create(paramContext, 0);
          i = this.mFontWeight;
          bool1 = bool2;
          if ((this.mStyle & 0x2) != 0) {
            bool1 = true;
          }
          this.mFontTypeface = Api28Impl.create(paramContext, i, bool1);
        }
        else
        {
          this.mFontTypeface = Typeface.create(paramContext, this.mStyle);
        }
      }
    }
  }
  
  void applyCompoundDrawablesTints()
  {
    Drawable[] arrayOfDrawable;
    if ((this.mDrawableLeftTint != null) || (this.mDrawableTopTint != null) || (this.mDrawableRightTint != null) || (this.mDrawableBottomTint != null))
    {
      arrayOfDrawable = this.mView.getCompoundDrawables();
      applyCompoundDrawableTint(arrayOfDrawable[0], this.mDrawableLeftTint);
      applyCompoundDrawableTint(arrayOfDrawable[1], this.mDrawableTopTint);
      applyCompoundDrawableTint(arrayOfDrawable[2], this.mDrawableRightTint);
      applyCompoundDrawableTint(arrayOfDrawable[3], this.mDrawableBottomTint);
    }
    if ((Build.VERSION.SDK_INT >= 17) && ((this.mDrawableStartTint != null) || (this.mDrawableEndTint != null)))
    {
      arrayOfDrawable = Api17Impl.getCompoundDrawablesRelative(this.mView);
      applyCompoundDrawableTint(arrayOfDrawable[0], this.mDrawableStartTint);
      applyCompoundDrawableTint(arrayOfDrawable[2], this.mDrawableEndTint);
    }
  }
  
  void autoSizeText()
  {
    this.mAutoSizeTextHelper.autoSizeText();
  }
  
  int getAutoSizeMaxTextSize()
  {
    return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
  }
  
  int getAutoSizeMinTextSize()
  {
    return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
  }
  
  int getAutoSizeStepGranularity()
  {
    return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
  }
  
  int[] getAutoSizeTextAvailableSizes()
  {
    return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
  }
  
  int getAutoSizeTextType()
  {
    return this.mAutoSizeTextHelper.getAutoSizeTextType();
  }
  
  ColorStateList getCompoundDrawableTintList()
  {
    Object localObject = this.mDrawableTint;
    if (localObject != null) {
      localObject = ((TintInfo)localObject).mTintList;
    } else {
      localObject = null;
    }
    return (ColorStateList)localObject;
  }
  
  PorterDuff.Mode getCompoundDrawableTintMode()
  {
    Object localObject = this.mDrawableTint;
    if (localObject != null) {
      localObject = ((TintInfo)localObject).mTintMode;
    } else {
      localObject = null;
    }
    return (PorterDuff.Mode)localObject;
  }
  
  boolean isAutoSizeEnabled()
  {
    return this.mAutoSizeTextHelper.isAutoSizeEnabled();
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    Context localContext = this.mView.getContext();
    AppCompatDrawableManager localAppCompatDrawableManager = AppCompatDrawableManager.get();
    Object localObject1 = TintTypedArray.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.AppCompatTextHelper, paramInt, 0);
    Object localObject2 = this.mView;
    ViewCompat.saveAttributeDataForStyleable((View)localObject2, ((TextView)localObject2).getContext(), R.styleable.AppCompatTextHelper, paramAttributeSet, ((TintTypedArray)localObject1).getWrappedTypeArray(), paramInt, 0);
    int k = ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
    if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
      this.mDrawableLeftTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
    }
    if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
      this.mDrawableTopTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
    }
    if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
      this.mDrawableRightTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
    }
    if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
      this.mDrawableBottomTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
    }
    if (Build.VERSION.SDK_INT >= 17)
    {
      if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
        this.mDrawableStartTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
      }
      if (((TintTypedArray)localObject1).hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
        this.mDrawableEndTint = createTintInfo(localContext, localAppCompatDrawableManager, ((TintTypedArray)localObject1).getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
      }
    }
    ((TintTypedArray)localObject1).recycle();
    boolean bool3 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
    boolean bool1 = false;
    boolean bool2 = false;
    int i = 0;
    int j = 0;
    Object localObject11 = null;
    Object localObject3 = null;
    Object localObject9 = null;
    Object localObject10 = null;
    localObject2 = null;
    Object localObject8 = null;
    localObject1 = null;
    Object localObject7 = null;
    Object localObject4 = null;
    TintTypedArray localTintTypedArray1 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    if (k != -1)
    {
      TintTypedArray localTintTypedArray2 = TintTypedArray.obtainStyledAttributes(localContext, k, R.styleable.TextAppearance);
      bool1 = bool2;
      i = j;
      if (!bool3)
      {
        bool1 = bool2;
        i = j;
        if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_textAllCaps))
        {
          i = 1;
          bool1 = localTintTypedArray2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
        }
      }
      updateTypefaceAndStyle(localContext, localTintTypedArray2);
      localObject3 = localObject11;
      localObject2 = localObject10;
      localObject1 = localObject7;
      if (Build.VERSION.SDK_INT < 23)
      {
        localObject4 = localObject9;
        if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColor)) {
          localObject4 = localTintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColor);
        }
        localObject5 = localObject8;
        if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
          localObject5 = localTintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
        }
        localObject3 = localObject4;
        localObject2 = localObject5;
        localObject1 = localObject7;
        if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_android_textColorLink))
        {
          localObject1 = localTintTypedArray2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
          localObject2 = localObject5;
          localObject3 = localObject4;
        }
      }
      localObject5 = localObject6;
      if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_textLocale)) {
        localObject5 = localTintTypedArray2.getString(R.styleable.TextAppearance_textLocale);
      }
      localObject4 = localTintTypedArray1;
      if (Build.VERSION.SDK_INT >= 26)
      {
        localObject4 = localTintTypedArray1;
        if (localTintTypedArray2.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
          localObject4 = localTintTypedArray2.getString(R.styleable.TextAppearance_fontVariationSettings);
        }
      }
      localTintTypedArray2.recycle();
    }
    localTintTypedArray1 = TintTypedArray.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.TextAppearance, paramInt, 0);
    if ((!bool3) && (localTintTypedArray1.hasValue(R.styleable.TextAppearance_textAllCaps)))
    {
      bool1 = localTintTypedArray1.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
      i = 1;
    }
    if (Build.VERSION.SDK_INT < 23)
    {
      if (localTintTypedArray1.hasValue(R.styleable.TextAppearance_android_textColor)) {
        localObject3 = localTintTypedArray1.getColorStateList(R.styleable.TextAppearance_android_textColor);
      }
      if (localTintTypedArray1.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
        localObject2 = localTintTypedArray1.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
      }
      if (localTintTypedArray1.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
        localObject1 = localTintTypedArray1.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
      } else {}
    }
    if (localTintTypedArray1.hasValue(R.styleable.TextAppearance_textLocale)) {
      localObject5 = localTintTypedArray1.getString(R.styleable.TextAppearance_textLocale);
    }
    if ((Build.VERSION.SDK_INT >= 26) && (localTintTypedArray1.hasValue(R.styleable.TextAppearance_fontVariationSettings))) {
      localObject4 = localTintTypedArray1.getString(R.styleable.TextAppearance_fontVariationSettings);
    }
    if (Build.VERSION.SDK_INT >= 28) {
      if (localTintTypedArray1.hasValue(R.styleable.TextAppearance_android_textSize))
      {
        if (localTintTypedArray1.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
          this.mView.setTextSize(0, 0.0F);
        }
      }
      else {}
    }
    updateTypefaceAndStyle(localContext, localTintTypedArray1);
    localTintTypedArray1.recycle();
    if (localObject3 != null) {
      this.mView.setTextColor((ColorStateList)localObject3);
    }
    if (localObject2 != null) {
      this.mView.setHintTextColor((ColorStateList)localObject2);
    }
    if (localObject1 != null) {
      this.mView.setLinkTextColor((ColorStateList)localObject1);
    }
    if ((!bool3) && (i != 0)) {
      setAllCaps(bool1);
    }
    localObject1 = this.mFontTypeface;
    if (localObject1 != null) {
      if (this.mFontWeight == -1) {
        this.mView.setTypeface((Typeface)localObject1, this.mStyle);
      } else {
        this.mView.setTypeface((Typeface)localObject1);
      }
    }
    if (localObject4 != null) {
      Api26Impl.setFontVariationSettings(this.mView, (String)localObject4);
    }
    if (localObject5 != null) {
      if (Build.VERSION.SDK_INT >= 24)
      {
        Api24Impl.setTextLocales(this.mView, Api24Impl.forLanguageTags((String)localObject5));
      }
      else if (Build.VERSION.SDK_INT >= 21)
      {
        localObject1 = localObject5.split(",")[0];
        Api17Impl.setTextLocale(this.mView, Api21Impl.forLanguageTag((String)localObject1));
      }
    }
    this.mAutoSizeTextHelper.loadFromAttributes(paramAttributeSet, paramInt);
    if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      if (this.mAutoSizeTextHelper.getAutoSizeTextType() != 0)
      {
        localObject1 = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
        if (localObject1.length > 0) {
          if (Api26Impl.getAutoSizeStepGranularity(this.mView) != -1.0F) {
            Api26Impl.setAutoSizeTextTypeUniformWithConfiguration(this.mView, this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
          } else {
            Api26Impl.setAutoSizeTextTypeUniformWithPresetSizes(this.mView, (int[])localObject1, 0);
          }
        }
      }
      else {}
    }
    localTintTypedArray1 = TintTypedArray.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.AppCompatTextView);
    localObject4 = null;
    localObject5 = null;
    paramAttributeSet = null;
    paramInt = R.styleable.AppCompatTextView_drawableLeftCompat;
    localObject1 = null;
    paramInt = localTintTypedArray1.getResourceId(paramInt, -1);
    if (paramInt != -1) {
      paramAttributeSet = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    }
    paramInt = R.styleable.AppCompatTextView_drawableTopCompat;
    localObject2 = null;
    paramInt = localTintTypedArray1.getResourceId(paramInt, -1);
    if (paramInt != -1) {
      localObject1 = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    }
    paramInt = localTintTypedArray1.getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
    if (paramInt != -1) {
      localObject2 = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    }
    paramInt = localTintTypedArray1.getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
    if (paramInt != -1) {
      localObject3 = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    } else {
      localObject3 = null;
    }
    paramInt = localTintTypedArray1.getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
    if (paramInt != -1) {
      localObject4 = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    }
    paramInt = localTintTypedArray1.getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
    if (paramInt != -1) {
      localObject5 = localAppCompatDrawableManager.getDrawable(localContext, paramInt);
    }
    setCompoundDrawables(paramAttributeSet, (Drawable)localObject1, (Drawable)localObject2, (Drawable)localObject3, (Drawable)localObject4, (Drawable)localObject5);
    if (localTintTypedArray1.hasValue(R.styleable.AppCompatTextView_drawableTint))
    {
      paramAttributeSet = localTintTypedArray1.getColorStateList(R.styleable.AppCompatTextView_drawableTint);
      TextViewCompat.setCompoundDrawableTintList(this.mView, paramAttributeSet);
    }
    if (localTintTypedArray1.hasValue(R.styleable.AppCompatTextView_drawableTintMode))
    {
      paramAttributeSet = DrawableUtils.parseTintMode(localTintTypedArray1.getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), null);
      TextViewCompat.setCompoundDrawableTintMode(this.mView, paramAttributeSet);
    }
    i = localTintTypedArray1.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
    j = localTintTypedArray1.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
    paramInt = localTintTypedArray1.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, -1);
    localTintTypedArray1.recycle();
    if (i != -1) {
      TextViewCompat.setFirstBaselineToTopHeight(this.mView, i);
    }
    if (j != -1) {
      TextViewCompat.setLastBaselineToBottomHeight(this.mView, j);
    }
    if (paramInt != -1) {
      TextViewCompat.setLineHeight(this.mView, paramInt);
    }
  }
  
  void onAsyncTypefaceReceived(final WeakReference<TextView> paramWeakReference, final Typeface paramTypeface)
  {
    if (this.mAsyncFontPending)
    {
      this.mFontTypeface = paramTypeface;
      paramWeakReference = (TextView)paramWeakReference.get();
      if (paramWeakReference != null) {
        if (ViewCompat.isAttachedToWindow(paramWeakReference)) {
          paramWeakReference.post(new Runnable()
          {
            public void run()
            {
              paramWeakReference.setTypeface(paramTypeface, this.val$style);
            }
          });
        } else {
          paramWeakReference.setTypeface(paramTypeface, this.mStyle);
        }
      }
    }
  }
  
  void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
      autoSizeText();
    }
  }
  
  void onSetCompoundDrawables()
  {
    applyCompoundDrawablesTints();
  }
  
  void onSetTextAppearance(Context paramContext, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, R.styleable.TextAppearance);
    if (localTintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
      setAllCaps(localTintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
    }
    if (Build.VERSION.SDK_INT < 23)
    {
      ColorStateList localColorStateList;
      if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor))
      {
        localColorStateList = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
        if (localColorStateList != null) {
          this.mView.setTextColor(localColorStateList);
        }
      }
      if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorLink))
      {
        localColorStateList = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
        if (localColorStateList != null) {
          this.mView.setLinkTextColor(localColorStateList);
        }
      }
      if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorHint))
      {
        localColorStateList = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
        if (localColorStateList != null) {
          this.mView.setHintTextColor(localColorStateList);
        }
      }
    }
    if ((localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize)) && (localTintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0)) {
      this.mView.setTextSize(0, 0.0F);
    }
    updateTypefaceAndStyle(paramContext, localTintTypedArray);
    if ((Build.VERSION.SDK_INT >= 26) && (localTintTypedArray.hasValue(R.styleable.TextAppearance_fontVariationSettings)))
    {
      paramContext = localTintTypedArray.getString(R.styleable.TextAppearance_fontVariationSettings);
      if (paramContext != null) {
        Api26Impl.setFontVariationSettings(this.mView, paramContext);
      }
    }
    localTintTypedArray.recycle();
    paramContext = this.mFontTypeface;
    if (paramContext != null) {
      this.mView.setTypeface(paramContext, this.mStyle);
    }
  }
  
  void populateSurroundingTextIfNeeded(TextView paramTextView, InputConnection paramInputConnection, EditorInfo paramEditorInfo)
  {
    if ((Build.VERSION.SDK_INT < 30) && (paramInputConnection != null)) {
      EditorInfoCompat.setInitialSurroundingText(paramEditorInfo, paramTextView.getText());
    }
  }
  
  void setAllCaps(boolean paramBoolean)
  {
    this.mView.setAllCaps(paramBoolean);
  }
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IllegalArgumentException
  {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfInt, int paramInt)
    throws IllegalArgumentException
  {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
  }
  
  void setAutoSizeTextTypeWithDefaults(int paramInt)
  {
    this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(paramInt);
  }
  
  void setCompoundDrawableTintList(ColorStateList paramColorStateList)
  {
    if (this.mDrawableTint == null) {
      this.mDrawableTint = new TintInfo();
    }
    this.mDrawableTint.mTintList = paramColorStateList;
    TintInfo localTintInfo = this.mDrawableTint;
    boolean bool;
    if (paramColorStateList != null) {
      bool = true;
    } else {
      bool = false;
    }
    localTintInfo.mHasTintList = bool;
    setCompoundTints();
  }
  
  void setCompoundDrawableTintMode(PorterDuff.Mode paramMode)
  {
    if (this.mDrawableTint == null) {
      this.mDrawableTint = new TintInfo();
    }
    this.mDrawableTint.mTintMode = paramMode;
    TintInfo localTintInfo = this.mDrawableTint;
    boolean bool;
    if (paramMode != null) {
      bool = true;
    } else {
      bool = false;
    }
    localTintInfo.mHasTintMode = bool;
    setCompoundTints();
  }
  
  void setTextSize(int paramInt, float paramFloat)
  {
    if ((!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) && (!isAutoSizeEnabled())) {
      setTextSizeInternal(paramInt, paramFloat);
    }
  }
  
  static class Api17Impl
  {
    static Drawable[] getCompoundDrawablesRelative(TextView paramTextView)
    {
      return paramTextView.getCompoundDrawablesRelative();
    }
    
    static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4)
    {
      paramTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
    }
    
    static void setTextLocale(TextView paramTextView, Locale paramLocale)
    {
      paramTextView.setTextLocale(paramLocale);
    }
  }
  
  static class Api21Impl
  {
    static Locale forLanguageTag(String paramString)
    {
      return Locale.forLanguageTag(paramString);
    }
  }
  
  static class Api24Impl
  {
    static LocaleList forLanguageTags(String paramString)
    {
      return LocaleList.forLanguageTags(paramString);
    }
    
    static void setTextLocales(TextView paramTextView, LocaleList paramLocaleList)
    {
      paramTextView.setTextLocales(paramLocaleList);
    }
  }
  
  static class Api26Impl
  {
    static int getAutoSizeStepGranularity(TextView paramTextView)
    {
      return paramTextView.getAutoSizeStepGranularity();
    }
    
    static void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramTextView.setAutoSizeTextTypeUniformWithConfiguration(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfInt, int paramInt)
    {
      paramTextView.setAutoSizeTextTypeUniformWithPresetSizes(paramArrayOfInt, paramInt);
    }
    
    static boolean setFontVariationSettings(TextView paramTextView, String paramString)
    {
      return paramTextView.setFontVariationSettings(paramString);
    }
  }
  
  static class Api28Impl
  {
    static Typeface create(Typeface paramTypeface, int paramInt, boolean paramBoolean)
    {
      return Typeface.create(paramTypeface, paramInt, paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */