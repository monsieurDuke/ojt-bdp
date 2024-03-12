package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewStructure;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class TextInputLayout
  extends LinearLayout
{
  public static final int BOX_BACKGROUND_FILLED = 1;
  public static final int BOX_BACKGROUND_NONE = 0;
  public static final int BOX_BACKGROUND_OUTLINE = 2;
  private static final int DEF_STYLE_RES = R.style.Widget_Design_TextInputLayout;
  public static final int END_ICON_CLEAR_TEXT = 2;
  public static final int END_ICON_CUSTOM = -1;
  public static final int END_ICON_DROPDOWN_MENU = 3;
  public static final int END_ICON_NONE = 0;
  public static final int END_ICON_PASSWORD_TOGGLE = 1;
  private static final int INVALID_MAX_LENGTH = -1;
  private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
  private static final String LOG_TAG = "TextInputLayout";
  private static final int NO_WIDTH = -1;
  private static final long PLACEHOLDER_FADE_DURATION = 87L;
  private static final long PLACEHOLDER_START_DELAY = 67L;
  private ValueAnimator animator;
  private boolean areCornerRadiiRtl;
  private MaterialShapeDrawable boxBackground;
  private int boxBackgroundColor;
  private int boxBackgroundMode;
  private int boxCollapsedPaddingTopPx;
  private final int boxLabelCutoutPaddingPx;
  private int boxStrokeColor;
  private int boxStrokeWidthDefaultPx;
  private int boxStrokeWidthFocusedPx;
  private int boxStrokeWidthPx;
  private MaterialShapeDrawable boxUnderlineDefault;
  private MaterialShapeDrawable boxUnderlineFocused;
  final CollapsingTextHelper collapsingTextHelper;
  boolean counterEnabled;
  private int counterMaxLength;
  private int counterOverflowTextAppearance;
  private ColorStateList counterOverflowTextColor;
  private boolean counterOverflowed;
  private int counterTextAppearance;
  private ColorStateList counterTextColor;
  private TextView counterView;
  private int defaultFilledBackgroundColor;
  private ColorStateList defaultHintTextColor;
  private int defaultStrokeColor;
  private int disabledColor;
  private int disabledFilledBackgroundColor;
  EditText editText;
  private final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners = new LinkedHashSet();
  private Drawable endDummyDrawable;
  private int endDummyDrawableWidth;
  private final LinkedHashSet<OnEndIconChangedListener> endIconChangedListeners;
  private final SparseArray<EndIconDelegate> endIconDelegates;
  private final FrameLayout endIconFrame;
  private int endIconMode = 0;
  private View.OnLongClickListener endIconOnLongClickListener;
  private ColorStateList endIconTintList;
  private PorterDuff.Mode endIconTintMode;
  private final CheckableImageButton endIconView;
  private final LinearLayout endLayout;
  private View.OnLongClickListener errorIconOnLongClickListener;
  private ColorStateList errorIconTintList;
  private PorterDuff.Mode errorIconTintMode;
  private final CheckableImageButton errorIconView;
  private boolean expandedHintEnabled;
  private int focusedFilledBackgroundColor;
  private int focusedStrokeColor;
  private ColorStateList focusedTextColor;
  private CharSequence hint;
  private boolean hintAnimationEnabled;
  private boolean hintEnabled;
  private boolean hintExpanded;
  private int hoveredFilledBackgroundColor;
  private int hoveredStrokeColor;
  private boolean inDrawableStateChanged;
  private final IndicatorViewController indicatorViewController = new IndicatorViewController(this);
  private final FrameLayout inputFrame;
  private boolean isProvidingHint;
  private int maxEms = -1;
  private int maxWidth = -1;
  private int minEms = -1;
  private int minWidth = -1;
  private Drawable originalEditTextEndDrawable;
  private CharSequence originalHint;
  private boolean placeholderEnabled;
  private Fade placeholderFadeIn;
  private Fade placeholderFadeOut;
  private CharSequence placeholderText;
  private int placeholderTextAppearance;
  private ColorStateList placeholderTextColor;
  private TextView placeholderTextView;
  private boolean restoringSavedState;
  private ShapeAppearanceModel shapeAppearanceModel;
  private Drawable startDummyDrawable;
  private int startDummyDrawableWidth;
  private final StartCompoundLayout startLayout;
  private ColorStateList strokeErrorColor;
  private CharSequence suffixText;
  private final TextView suffixTextView;
  private final Rect tmpBoundsRect = new Rect();
  private final Rect tmpRect = new Rect();
  private final RectF tmpRectF = new RectF();
  private Typeface typeface;
  
  public TextInputLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.textInputStyle);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    SparseArray localSparseArray = new SparseArray();
    this.endIconDelegates = localSparseArray;
    this.endIconChangedListeners = new LinkedHashSet();
    Object localObject2 = new CollapsingTextHelper(this);
    this.collapsingTextHelper = ((CollapsingTextHelper)localObject2);
    Context localContext = getContext();
    setOrientation(1);
    setWillNotDraw(false);
    setAddStatesFromChildren(true);
    FrameLayout localFrameLayout1 = new FrameLayout(localContext);
    this.inputFrame = localFrameLayout1;
    FrameLayout localFrameLayout2 = new FrameLayout(localContext);
    this.endIconFrame = localFrameLayout2;
    LinearLayout localLinearLayout = new LinearLayout(localContext);
    this.endLayout = localLinearLayout;
    AppCompatTextView localAppCompatTextView = new AppCompatTextView(localContext);
    this.suffixTextView = localAppCompatTextView;
    localLinearLayout.setVisibility(8);
    localFrameLayout2.setVisibility(8);
    localAppCompatTextView.setVisibility(8);
    Object localObject1 = LayoutInflater.from(localContext);
    paramContext = (CheckableImageButton)((LayoutInflater)localObject1).inflate(R.layout.design_text_input_end_icon, localLinearLayout, false);
    this.errorIconView = paramContext;
    localObject1 = (CheckableImageButton)((LayoutInflater)localObject1).inflate(R.layout.design_text_input_end_icon, localFrameLayout2, false);
    this.endIconView = ((CheckableImageButton)localObject1);
    localFrameLayout1.setAddStatesFromChildren(true);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
    localFrameLayout2.setLayoutParams(new FrameLayout.LayoutParams(-2, -1));
    ((CollapsingTextHelper)localObject2).setTextSizeInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    ((CollapsingTextHelper)localObject2).setPositionInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    ((CollapsingTextHelper)localObject2).setCollapsedTextGravity(8388659);
    localObject2 = ThemeEnforcement.obtainTintedStyledAttributes(localContext, paramAttributeSet, R.styleable.TextInputLayout, paramInt, i, new int[] { R.styleable.TextInputLayout_counterTextAppearance, R.styleable.TextInputLayout_counterOverflowTextAppearance, R.styleable.TextInputLayout_errorTextAppearance, R.styleable.TextInputLayout_helperTextTextAppearance, R.styleable.TextInputLayout_hintTextAppearance });
    StartCompoundLayout localStartCompoundLayout = new StartCompoundLayout(this, (TintTypedArray)localObject2);
    this.startLayout = localStartCompoundLayout;
    this.hintEnabled = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
    setHint(((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_android_hint));
    this.hintAnimationEnabled = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
    this.expandedHintEnabled = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_expandedHintEnabled, true);
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_android_minEms)) {
      setMinEms(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_android_minEms, -1));
    } else if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_android_minWidth)) {
      setMinWidth(((TintTypedArray)localObject2).getDimensionPixelSize(R.styleable.TextInputLayout_android_minWidth, -1));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_android_maxEms)) {
      setMaxEms(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_android_maxEms, -1));
    } else if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_android_maxWidth)) {
      setMaxWidth(((TintTypedArray)localObject2).getDimensionPixelSize(R.styleable.TextInputLayout_android_maxWidth, -1));
    }
    this.shapeAppearanceModel = ShapeAppearanceModel.builder(localContext, paramAttributeSet, paramInt, i).build();
    this.boxLabelCutoutPaddingPx = localContext.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
    this.boxCollapsedPaddingTopPx = ((TintTypedArray)localObject2).getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
    this.boxStrokeWidthDefaultPx = ((TintTypedArray)localObject2).getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidth, localContext.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default));
    this.boxStrokeWidthFocusedPx = ((TintTypedArray)localObject2).getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidthFocused, localContext.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused));
    this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
    float f2 = ((TintTypedArray)localObject2).getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, -1.0F);
    float f1 = ((TintTypedArray)localObject2).getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, -1.0F);
    float f3 = ((TintTypedArray)localObject2).getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, -1.0F);
    float f4 = ((TintTypedArray)localObject2).getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, -1.0F);
    paramAttributeSet = this.shapeAppearanceModel.toBuilder();
    if (f2 >= 0.0F) {
      paramAttributeSet.setTopLeftCornerSize(f2);
    }
    if (f1 >= 0.0F) {
      paramAttributeSet.setTopRightCornerSize(f1);
    }
    if (f3 >= 0.0F) {
      paramAttributeSet.setBottomRightCornerSize(f3);
    }
    if (f4 >= 0.0F) {
      paramAttributeSet.setBottomLeftCornerSize(f4);
    }
    this.shapeAppearanceModel = paramAttributeSet.build();
    paramAttributeSet = MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_boxBackgroundColor);
    if (paramAttributeSet != null)
    {
      paramInt = paramAttributeSet.getDefaultColor();
      this.defaultFilledBackgroundColor = paramInt;
      this.boxBackgroundColor = paramInt;
      if (paramAttributeSet.isStateful())
      {
        this.disabledFilledBackgroundColor = paramAttributeSet.getColorForState(new int[] { -16842910 }, -1);
        this.focusedFilledBackgroundColor = paramAttributeSet.getColorForState(new int[] { 16842908, 16842910 }, -1);
        this.hoveredFilledBackgroundColor = paramAttributeSet.getColorForState(new int[] { 16843623, 16842910 }, -1);
      }
      else
      {
        this.focusedFilledBackgroundColor = this.defaultFilledBackgroundColor;
        paramAttributeSet = AppCompatResources.getColorStateList(localContext, R.color.mtrl_filled_background_color);
        this.disabledFilledBackgroundColor = paramAttributeSet.getColorForState(new int[] { -16842910 }, -1);
        this.hoveredFilledBackgroundColor = paramAttributeSet.getColorForState(new int[] { 16843623 }, -1);
      }
    }
    else
    {
      this.boxBackgroundColor = 0;
      this.defaultFilledBackgroundColor = 0;
      this.disabledFilledBackgroundColor = 0;
      this.focusedFilledBackgroundColor = 0;
      this.hoveredFilledBackgroundColor = 0;
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_android_textColorHint))
    {
      paramAttributeSet = ((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
      this.focusedTextColor = paramAttributeSet;
      this.defaultHintTextColor = paramAttributeSet;
    }
    paramAttributeSet = MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_boxStrokeColor);
    this.focusedStrokeColor = ((TintTypedArray)localObject2).getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
    this.defaultStrokeColor = ContextCompat.getColor(localContext, R.color.mtrl_textinput_default_box_stroke_color);
    this.disabledColor = ContextCompat.getColor(localContext, R.color.mtrl_textinput_disabled_color);
    this.hoveredStrokeColor = ContextCompat.getColor(localContext, R.color.mtrl_textinput_hovered_box_stroke_color);
    if (paramAttributeSet != null) {
      setBoxStrokeColorStateList(paramAttributeSet);
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_boxStrokeErrorColor)) {
      setBoxStrokeErrorColor(MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_boxStrokeErrorColor));
    }
    if (((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
      setHintTextAppearance(((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
    }
    int n = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
    CharSequence localCharSequence1 = ((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_errorContentDescription);
    boolean bool3 = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
    paramContext.setId(R.id.text_input_error_icon);
    if (MaterialResources.isFontScaleAtLeast1_3(localContext)) {
      MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)paramContext.getLayoutParams(), 0);
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_errorIconTint)) {
      this.errorIconTintList = MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_errorIconTint);
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_errorIconTintMode)) {
      this.errorIconTintMode = ViewUtils.parseTintMode(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_errorIconTintMode, -1), null);
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_errorIconDrawable)) {
      setErrorIconDrawable(((TintTypedArray)localObject2).getDrawable(R.styleable.TextInputLayout_errorIconDrawable));
    }
    paramContext.setContentDescription(getResources().getText(R.string.error_icon_content_description));
    ViewCompat.setImportantForAccessibility(paramContext, 2);
    paramContext.setClickable(false);
    paramContext.setPressable(false);
    paramContext.setFocusable(false);
    int j = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
    boolean bool2 = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
    paramAttributeSet = ((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_helperText);
    int m = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_placeholderTextAppearance, 0);
    CharSequence localCharSequence3 = ((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_placeholderText);
    int k = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_suffixTextAppearance, 0);
    CharSequence localCharSequence2 = ((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_suffixText);
    boolean bool1 = ((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
    setCounterMaxLength(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
    this.counterTextAppearance = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
    this.counterOverflowTextAppearance = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
    setBoxBackgroundMode(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
    if (MaterialResources.isFontScaleAtLeast1_3(localContext)) {
      MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)((CheckableImageButton)localObject1).getLayoutParams(), 0);
    }
    i = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_endIconDrawable, 0);
    localSparseArray.append(-1, new CustomEndIconDelegate(this, i));
    localSparseArray.append(0, new NoEndIconDelegate(this));
    if (i == 0) {
      paramInt = ((TintTypedArray)localObject2).getResourceId(R.styleable.TextInputLayout_passwordToggleDrawable, 0);
    } else {
      paramInt = i;
    }
    localSparseArray.append(1, new PasswordToggleEndIconDelegate(this, paramInt));
    localSparseArray.append(2, new ClearTextEndIconDelegate(this, i));
    localSparseArray.append(3, new DropdownMenuEndIconDelegate(this, i));
    if (!((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_passwordToggleEnabled))
    {
      if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_endIconTint)) {
        this.endIconTintList = MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_endIconTint);
      }
      if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_endIconTintMode)) {
        this.endIconTintMode = ViewUtils.parseTintMode(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_endIconTintMode, -1), null);
      }
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_endIconMode))
    {
      setEndIconMode(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_endIconMode, 0));
      if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_endIconContentDescription)) {
        setEndIconContentDescription(((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_endIconContentDescription));
      }
      setEndIconCheckable(((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_endIconCheckable, true));
    }
    else if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_passwordToggleEnabled))
    {
      if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
        this.endIconTintList = MaterialResources.getColorStateList(localContext, (TintTypedArray)localObject2, R.styleable.TextInputLayout_passwordToggleTint);
      }
      if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
        this.endIconTintMode = ViewUtils.parseTintMode(((TintTypedArray)localObject2).getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
      }
      setEndIconMode(((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false));
      setEndIconContentDescription(((TintTypedArray)localObject2).getText(R.styleable.TextInputLayout_passwordToggleContentDescription));
    }
    localAppCompatTextView.setId(R.id.textinput_suffix_text);
    localAppCompatTextView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 80));
    ViewCompat.setAccessibilityLiveRegion(localAppCompatTextView, 1);
    setErrorContentDescription(localCharSequence1);
    setCounterOverflowTextAppearance(this.counterOverflowTextAppearance);
    setHelperTextTextAppearance(j);
    setErrorTextAppearance(n);
    setCounterTextAppearance(this.counterTextAppearance);
    setPlaceholderText(localCharSequence3);
    setPlaceholderTextAppearance(m);
    setSuffixTextAppearance(k);
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_errorTextColor)) {
      setErrorTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_errorTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_helperTextTextColor)) {
      setHelperTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_helperTextTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_hintTextColor)) {
      setHintTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_hintTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_counterTextColor)) {
      setCounterTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_counterTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_counterOverflowTextColor)) {
      setCounterOverflowTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_counterOverflowTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_placeholderTextColor)) {
      setPlaceholderTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_placeholderTextColor));
    }
    if (((TintTypedArray)localObject2).hasValue(R.styleable.TextInputLayout_suffixTextColor)) {
      setSuffixTextColor(((TintTypedArray)localObject2).getColorStateList(R.styleable.TextInputLayout_suffixTextColor));
    }
    setEnabled(((TintTypedArray)localObject2).getBoolean(R.styleable.TextInputLayout_android_enabled, true));
    ((TintTypedArray)localObject2).recycle();
    ViewCompat.setImportantForAccessibility(this, 2);
    if (Build.VERSION.SDK_INT >= 26) {
      ViewCompat.setImportantForAutofill(this, 1);
    }
    localFrameLayout2.addView((View)localObject1);
    localLinearLayout.addView(localAppCompatTextView);
    localLinearLayout.addView(paramContext);
    localLinearLayout.addView(localFrameLayout2);
    localFrameLayout1.addView(localStartCompoundLayout);
    localFrameLayout1.addView(localLinearLayout);
    addView(localFrameLayout1);
    setHelperTextEnabled(bool2);
    setErrorEnabled(bool3);
    setCounterEnabled(bool1);
    setHelperText(paramAttributeSet);
    setSuffixText(localCharSequence2);
  }
  
  private void addPlaceholderTextView()
  {
    TextView localTextView = this.placeholderTextView;
    if (localTextView != null)
    {
      this.inputFrame.addView(localTextView);
      this.placeholderTextView.setVisibility(0);
    }
  }
  
  private void adjustFilledEditTextPaddingForLargeFont()
  {
    if ((this.editText != null) && (this.boxBackgroundMode == 1))
    {
      EditText localEditText;
      if (MaterialResources.isFontScaleAtLeast2_0(getContext()))
      {
        localEditText = this.editText;
        ViewCompat.setPaddingRelative(localEditText, ViewCompat.getPaddingStart(localEditText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_2_0_padding_bottom));
      }
      else if (MaterialResources.isFontScaleAtLeast1_3(getContext()))
      {
        localEditText = this.editText;
        ViewCompat.setPaddingRelative(localEditText, ViewCompat.getPaddingStart(localEditText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(R.dimen.material_filled_edittext_font_1_3_padding_bottom));
      }
      return;
    }
  }
  
  private void applyBoxAttributes()
  {
    Object localObject = this.boxBackground;
    if (localObject == null) {
      return;
    }
    localObject = ((MaterialShapeDrawable)localObject).getShapeAppearanceModel();
    ShapeAppearanceModel localShapeAppearanceModel = this.shapeAppearanceModel;
    if (localObject != localShapeAppearanceModel)
    {
      this.boxBackground.setShapeAppearanceModel(localShapeAppearanceModel);
      updateDropdownMenuBackground();
    }
    if (canDrawOutlineStroke()) {
      this.boxBackground.setStroke(this.boxStrokeWidthPx, this.boxStrokeColor);
    }
    int i = calculateBoxBackgroundColor();
    this.boxBackgroundColor = i;
    this.boxBackground.setFillColor(ColorStateList.valueOf(i));
    if (this.endIconMode == 3) {
      this.editText.getBackground().invalidateSelf();
    }
    applyBoxUnderlineAttributes();
    invalidate();
  }
  
  private void applyBoxUnderlineAttributes()
  {
    if ((this.boxUnderlineDefault != null) && (this.boxUnderlineFocused != null))
    {
      if (canDrawStroke())
      {
        MaterialShapeDrawable localMaterialShapeDrawable = this.boxUnderlineDefault;
        ColorStateList localColorStateList;
        if (this.editText.isFocused()) {
          localColorStateList = ColorStateList.valueOf(this.defaultStrokeColor);
        } else {
          localColorStateList = ColorStateList.valueOf(this.boxStrokeColor);
        }
        localMaterialShapeDrawable.setFillColor(localColorStateList);
        this.boxUnderlineFocused.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
      }
      invalidate();
      return;
    }
  }
  
  private void applyCutoutPadding(RectF paramRectF)
  {
    paramRectF.left -= this.boxLabelCutoutPaddingPx;
    paramRectF.right += this.boxLabelCutoutPaddingPx;
  }
  
  private void assignBoxBackgroundByMode()
  {
    switch (this.boxBackgroundMode)
    {
    default: 
      throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
    case 2: 
      if ((this.hintEnabled) && (!(this.boxBackground instanceof CutoutDrawable))) {
        this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
      } else {
        this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
      }
      this.boxUnderlineDefault = null;
      this.boxUnderlineFocused = null;
      break;
    case 1: 
      this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
      this.boxUnderlineDefault = new MaterialShapeDrawable();
      this.boxUnderlineFocused = new MaterialShapeDrawable();
      break;
    case 0: 
      this.boxBackground = null;
      this.boxUnderlineDefault = null;
      this.boxUnderlineFocused = null;
    }
  }
  
  private int calculateBoxBackgroundColor()
  {
    int i = this.boxBackgroundColor;
    if (this.boxBackgroundMode == 1) {
      i = MaterialColors.layer(MaterialColors.getColor(this, R.attr.colorSurface, 0), this.boxBackgroundColor);
    }
    return i;
  }
  
  private Rect calculateCollapsedTextBounds(Rect paramRect)
  {
    if (this.editText != null)
    {
      Rect localRect = this.tmpBoundsRect;
      boolean bool = ViewUtils.isLayoutRtl(this);
      localRect.bottom = paramRect.bottom;
      switch (this.boxBackgroundMode)
      {
      default: 
        localRect.left = getLabelLeftBoundAlightWithPrefix(paramRect.left, bool);
        localRect.top = getPaddingTop();
        localRect.right = getLabelRightBoundAlignedWithSuffix(paramRect.right, bool);
        return localRect;
      case 2: 
        paramRect.left += this.editText.getPaddingLeft();
        paramRect.top -= calculateLabelMarginTop();
        paramRect.right -= this.editText.getPaddingRight();
        return localRect;
      }
      localRect.left = getLabelLeftBoundAlightWithPrefix(paramRect.left, bool);
      paramRect.top += this.boxCollapsedPaddingTopPx;
      localRect.right = getLabelRightBoundAlignedWithSuffix(paramRect.right, bool);
      return localRect;
    }
    throw new IllegalStateException();
  }
  
  private int calculateExpandedLabelBottom(Rect paramRect1, Rect paramRect2, float paramFloat)
  {
    if (isSingleLineFilledTextField()) {
      return (int)(paramRect2.top + paramFloat);
    }
    return paramRect1.bottom - this.editText.getCompoundPaddingBottom();
  }
  
  private int calculateExpandedLabelTop(Rect paramRect, float paramFloat)
  {
    if (isSingleLineFilledTextField()) {
      return (int)(paramRect.centerY() - paramFloat / 2.0F);
    }
    return paramRect.top + this.editText.getCompoundPaddingTop();
  }
  
  private Rect calculateExpandedTextBounds(Rect paramRect)
  {
    if (this.editText != null)
    {
      Rect localRect = this.tmpBoundsRect;
      float f = this.collapsingTextHelper.getExpandedTextHeight();
      paramRect.left += this.editText.getCompoundPaddingLeft();
      localRect.top = calculateExpandedLabelTop(paramRect, f);
      paramRect.right -= this.editText.getCompoundPaddingRight();
      localRect.bottom = calculateExpandedLabelBottom(paramRect, localRect, f);
      return localRect;
    }
    throw new IllegalStateException();
  }
  
  private int calculateLabelMarginTop()
  {
    if (!this.hintEnabled) {
      return 0;
    }
    switch (this.boxBackgroundMode)
    {
    case 1: 
    default: 
      return 0;
    case 2: 
      return (int)(this.collapsingTextHelper.getCollapsedTextHeight() / 2.0F);
    }
    return (int)this.collapsingTextHelper.getCollapsedTextHeight();
  }
  
  private boolean canDrawOutlineStroke()
  {
    boolean bool;
    if ((this.boxBackgroundMode == 2) && (canDrawStroke())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean canDrawStroke()
  {
    boolean bool;
    if ((this.boxStrokeWidthPx > -1) && (this.boxStrokeColor != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void closeCutout()
  {
    if (cutoutEnabled()) {
      ((CutoutDrawable)this.boxBackground).removeCutout();
    }
  }
  
  private void collapseHint(boolean paramBoolean)
  {
    ValueAnimator localValueAnimator = this.animator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      this.animator.cancel();
    }
    if ((paramBoolean) && (this.hintAnimationEnabled)) {
      animateToExpansionFraction(1.0F);
    } else {
      this.collapsingTextHelper.setExpansionFraction(1.0F);
    }
    this.hintExpanded = false;
    if (cutoutEnabled()) {
      openCutout();
    }
    updatePlaceholderText();
    this.startLayout.onHintStateChanged(false);
    updateSuffixTextVisibility();
  }
  
  private Fade createPlaceholderFadeTransition()
  {
    Fade localFade = new Fade();
    localFade.setDuration(87L);
    localFade.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    return localFade;
  }
  
  private boolean cutoutEnabled()
  {
    boolean bool;
    if ((this.hintEnabled) && (!TextUtils.isEmpty(this.hint)) && ((this.boxBackground instanceof CutoutDrawable))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void dispatchOnEditTextAttached()
  {
    Iterator localIterator = this.editTextAttachedListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnEditTextAttachedListener)localIterator.next()).onEditTextAttached(this);
    }
  }
  
  private void dispatchOnEndIconChanged(int paramInt)
  {
    Iterator localIterator = this.endIconChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnEndIconChangedListener)localIterator.next()).onEndIconChanged(this, paramInt);
    }
  }
  
  private void drawBoxUnderline(Canvas paramCanvas)
  {
    if (this.boxUnderlineFocused != null)
    {
      Object localObject = this.boxUnderlineDefault;
      if (localObject != null)
      {
        ((MaterialShapeDrawable)localObject).draw(paramCanvas);
        if (this.editText.isFocused())
        {
          localObject = this.boxUnderlineFocused.getBounds();
          Rect localRect = this.boxUnderlineDefault.getBounds();
          float f = this.collapsingTextHelper.getExpansionFraction();
          int i = localRect.centerX();
          ((Rect)localObject).left = AnimationUtils.lerp(i, localRect.left, f);
          ((Rect)localObject).right = AnimationUtils.lerp(i, localRect.right, f);
          this.boxUnderlineFocused.draw(paramCanvas);
        }
      }
    }
  }
  
  private void drawHint(Canvas paramCanvas)
  {
    if (this.hintEnabled) {
      this.collapsingTextHelper.draw(paramCanvas);
    }
  }
  
  private void expandHint(boolean paramBoolean)
  {
    ValueAnimator localValueAnimator = this.animator;
    if ((localValueAnimator != null) && (localValueAnimator.isRunning())) {
      this.animator.cancel();
    }
    if ((paramBoolean) && (this.hintAnimationEnabled)) {
      animateToExpansionFraction(0.0F);
    } else {
      this.collapsingTextHelper.setExpansionFraction(0.0F);
    }
    if ((cutoutEnabled()) && (((CutoutDrawable)this.boxBackground).hasCutout())) {
      closeCutout();
    }
    this.hintExpanded = true;
    hidePlaceholderText();
    this.startLayout.onHintStateChanged(true);
    updateSuffixTextVisibility();
  }
  
  private EndIconDelegate getEndIconDelegate()
  {
    EndIconDelegate localEndIconDelegate = (EndIconDelegate)this.endIconDelegates.get(this.endIconMode);
    if (localEndIconDelegate == null) {
      localEndIconDelegate = (EndIconDelegate)this.endIconDelegates.get(0);
    }
    return localEndIconDelegate;
  }
  
  private CheckableImageButton getEndIconToUpdateDummyDrawable()
  {
    if (this.errorIconView.getVisibility() == 0) {
      return this.errorIconView;
    }
    if ((hasEndIcon()) && (isEndIconVisible())) {
      return this.endIconView;
    }
    return null;
  }
  
  private int getLabelLeftBoundAlightWithPrefix(int paramInt, boolean paramBoolean)
  {
    int i = this.editText.getCompoundPaddingLeft() + paramInt;
    paramInt = i;
    if (getPrefixText() != null)
    {
      paramInt = i;
      if (!paramBoolean) {
        paramInt = i - getPrefixTextView().getMeasuredWidth() + getPrefixTextView().getPaddingLeft();
      }
    }
    return paramInt;
  }
  
  private int getLabelRightBoundAlignedWithSuffix(int paramInt, boolean paramBoolean)
  {
    int i = paramInt - this.editText.getCompoundPaddingRight();
    paramInt = i;
    if (getPrefixText() != null)
    {
      paramInt = i;
      if (paramBoolean) {
        paramInt = i + (getPrefixTextView().getMeasuredWidth() - getPrefixTextView().getPaddingRight());
      }
    }
    return paramInt;
  }
  
  private boolean hasEndIcon()
  {
    boolean bool;
    if (this.endIconMode != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void hidePlaceholderText()
  {
    TextView localTextView = this.placeholderTextView;
    if ((localTextView != null) && (this.placeholderEnabled))
    {
      localTextView.setText(null);
      TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeOut);
      this.placeholderTextView.setVisibility(4);
    }
  }
  
  private boolean isErrorIconVisible()
  {
    boolean bool;
    if (this.errorIconView.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isSingleLineFilledTextField()
  {
    int i = this.boxBackgroundMode;
    boolean bool = true;
    if ((i != 1) || ((Build.VERSION.SDK_INT < 16) || (this.editText.getMinLines() > 1))) {
      bool = false;
    }
    return bool;
  }
  
  private void onApplyBoxBackgroundMode()
  {
    assignBoxBackgroundByMode();
    setEditTextBoxBackground();
    updateTextInputBoxState();
    updateBoxCollapsedPaddingTop();
    adjustFilledEditTextPaddingForLargeFont();
    if (this.boxBackgroundMode != 0) {
      updateInputLayoutMargins();
    }
  }
  
  private void openCutout()
  {
    if (!cutoutEnabled()) {
      return;
    }
    RectF localRectF = this.tmpRectF;
    this.collapsingTextHelper.getCollapsedTextActualBounds(localRectF, this.editText.getWidth(), this.editText.getGravity());
    applyCutoutPadding(localRectF);
    localRectF.offset(-getPaddingLeft(), -getPaddingTop() - localRectF.height() / 2.0F + this.boxStrokeWidthPx);
    ((CutoutDrawable)this.boxBackground).setCutout(localRectF);
  }
  
  private void recalculateCutout()
  {
    if ((cutoutEnabled()) && (!this.hintExpanded))
    {
      closeCutout();
      openCutout();
    }
  }
  
  private static void recursiveSetEnabled(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    int i = 0;
    int j = paramViewGroup.getChildCount();
    while (i < j)
    {
      View localView = paramViewGroup.getChildAt(i);
      localView.setEnabled(paramBoolean);
      if ((localView instanceof ViewGroup)) {
        recursiveSetEnabled((ViewGroup)localView, paramBoolean);
      }
      i++;
    }
  }
  
  private void removePlaceholderTextView()
  {
    TextView localTextView = this.placeholderTextView;
    if (localTextView != null) {
      localTextView.setVisibility(8);
    }
  }
  
  private void setEditText(EditText paramEditText)
  {
    if (this.editText == null)
    {
      if ((this.endIconMode != 3) && (!(paramEditText instanceof TextInputEditText))) {
        Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
      }
      this.editText = paramEditText;
      int i = this.minEms;
      if (i != -1) {
        setMinEms(i);
      } else {
        setMinWidth(this.minWidth);
      }
      i = this.maxEms;
      if (i != -1) {
        setMaxEms(i);
      } else {
        setMaxWidth(this.maxWidth);
      }
      onApplyBoxBackgroundMode();
      setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
      this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
      this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
      if (Build.VERSION.SDK_INT >= 21) {
        this.collapsingTextHelper.setExpandedLetterSpacing(this.editText.getLetterSpacing());
      }
      i = this.editText.getGravity();
      this.collapsingTextHelper.setCollapsedTextGravity(i & 0xFFFFFF8F | 0x30);
      this.collapsingTextHelper.setExpandedTextGravity(i);
      this.editText.addTextChangedListener(new TextWatcher()
      {
        public void afterTextChanged(Editable paramAnonymousEditable)
        {
          TextInputLayout localTextInputLayout = TextInputLayout.this;
          localTextInputLayout.updateLabelState(localTextInputLayout.restoringSavedState ^ true);
          if (TextInputLayout.this.counterEnabled) {
            TextInputLayout.this.updateCounter(paramAnonymousEditable.length());
          }
          if (TextInputLayout.this.placeholderEnabled) {
            TextInputLayout.this.updatePlaceholderText(paramAnonymousEditable.length());
          }
        }
        
        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      if (this.defaultHintTextColor == null) {
        this.defaultHintTextColor = this.editText.getHintTextColors();
      }
      if (this.hintEnabled)
      {
        if (TextUtils.isEmpty(this.hint))
        {
          CharSequence localCharSequence = this.editText.getHint();
          this.originalHint = localCharSequence;
          setHint(localCharSequence);
          this.editText.setHint(null);
        }
        this.isProvidingHint = true;
      }
      if (this.counterView != null) {
        updateCounter(this.editText.getText().length());
      }
      updateEditTextBackground();
      this.indicatorViewController.adjustIndicatorPadding();
      this.startLayout.bringToFront();
      this.endLayout.bringToFront();
      this.endIconFrame.bringToFront();
      this.errorIconView.bringToFront();
      dispatchOnEditTextAttached();
      updateSuffixTextViewPadding();
      if (!isEnabled()) {
        paramEditText.setEnabled(false);
      }
      updateLabelState(false, true);
      return;
    }
    throw new IllegalArgumentException("We already have an EditText, can only have one");
  }
  
  private void setEditTextBoxBackground()
  {
    if (shouldUseEditTextBackgroundForBoxBackground()) {
      ViewCompat.setBackground(this.editText, this.boxBackground);
    }
  }
  
  private void setHintInternal(CharSequence paramCharSequence)
  {
    if (!TextUtils.equals(paramCharSequence, this.hint))
    {
      this.hint = paramCharSequence;
      this.collapsingTextHelper.setText(paramCharSequence);
      if (!this.hintExpanded) {
        openCutout();
      }
    }
  }
  
  private static void setIconClickable(CheckableImageButton paramCheckableImageButton, View.OnLongClickListener paramOnLongClickListener)
  {
    boolean bool3 = ViewCompat.hasOnClickListeners(paramCheckableImageButton);
    boolean bool2 = false;
    int i = 1;
    boolean bool1;
    if (paramOnLongClickListener != null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((bool3) || (bool1)) {
      bool2 = true;
    }
    paramCheckableImageButton.setFocusable(bool2);
    paramCheckableImageButton.setClickable(bool3);
    paramCheckableImageButton.setPressable(bool3);
    paramCheckableImageButton.setLongClickable(bool1);
    if (!bool2) {
      i = 2;
    }
    ViewCompat.setImportantForAccessibility(paramCheckableImageButton, i);
  }
  
  private static void setIconOnClickListener(CheckableImageButton paramCheckableImageButton, View.OnClickListener paramOnClickListener, View.OnLongClickListener paramOnLongClickListener)
  {
    paramCheckableImageButton.setOnClickListener(paramOnClickListener);
    setIconClickable(paramCheckableImageButton, paramOnLongClickListener);
  }
  
  private static void setIconOnLongClickListener(CheckableImageButton paramCheckableImageButton, View.OnLongClickListener paramOnLongClickListener)
  {
    paramCheckableImageButton.setOnLongClickListener(paramOnLongClickListener);
    setIconClickable(paramCheckableImageButton, paramOnLongClickListener);
  }
  
  private void setPlaceholderTextEnabled(boolean paramBoolean)
  {
    if (this.placeholderEnabled == paramBoolean) {
      return;
    }
    if (paramBoolean)
    {
      addPlaceholderTextView();
    }
    else
    {
      removePlaceholderTextView();
      this.placeholderTextView = null;
    }
    this.placeholderEnabled = paramBoolean;
  }
  
  private boolean shouldUpdateEndDummyDrawable()
  {
    boolean bool;
    if (((this.errorIconView.getVisibility() == 0) || ((hasEndIcon()) && (isEndIconVisible())) || (this.suffixText != null)) && (this.endLayout.getMeasuredWidth() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldUpdateStartDummyDrawable()
  {
    boolean bool;
    if (((getStartIconDrawable() != null) || ((getPrefixText() != null) && (getPrefixTextView().getVisibility() == 0))) && (this.startLayout.getMeasuredWidth() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldUseEditTextBackgroundForBoxBackground()
  {
    EditText localEditText = this.editText;
    boolean bool;
    if ((localEditText != null) && (this.boxBackground != null) && (localEditText.getBackground() == null) && (this.boxBackgroundMode != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void showPlaceholderText()
  {
    if ((this.placeholderTextView != null) && (this.placeholderEnabled) && (!TextUtils.isEmpty(this.placeholderText)))
    {
      this.placeholderTextView.setText(this.placeholderText);
      TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeIn);
      this.placeholderTextView.setVisibility(0);
      this.placeholderTextView.bringToFront();
      if (Build.VERSION.SDK_INT >= 16) {
        announceForAccessibility(this.placeholderText);
      }
    }
  }
  
  private void tintEndIconOnError(boolean paramBoolean)
  {
    if ((paramBoolean) && (getEndIconDrawable() != null))
    {
      Drawable localDrawable = DrawableCompat.wrap(getEndIconDrawable()).mutate();
      DrawableCompat.setTint(localDrawable, this.indicatorViewController.getErrorViewCurrentTextColor());
      this.endIconView.setImageDrawable(localDrawable);
    }
    else
    {
      IconHelper.applyIconTint(this, this.endIconView, this.endIconTintList, this.endIconTintMode);
    }
  }
  
  private void updateBoxCollapsedPaddingTop()
  {
    if (this.boxBackgroundMode == 1) {
      if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
        this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R.dimen.material_font_2_0_box_collapsed_padding_top);
      } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
        this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R.dimen.material_font_1_3_box_collapsed_padding_top);
      }
    }
  }
  
  private void updateBoxUnderlineBounds(Rect paramRect)
  {
    int j;
    int i;
    if (this.boxUnderlineDefault != null)
    {
      j = paramRect.bottom;
      i = this.boxStrokeWidthDefaultPx;
      this.boxUnderlineDefault.setBounds(paramRect.left, j - i, paramRect.right, paramRect.bottom);
    }
    if (this.boxUnderlineFocused != null)
    {
      i = paramRect.bottom;
      j = this.boxStrokeWidthFocusedPx;
      this.boxUnderlineFocused.setBounds(paramRect.left, i - j, paramRect.right, paramRect.bottom);
    }
  }
  
  private void updateCounter()
  {
    if (this.counterView != null)
    {
      EditText localEditText = this.editText;
      int i;
      if (localEditText == null) {
        i = 0;
      } else {
        i = localEditText.getText().length();
      }
      updateCounter(i);
    }
  }
  
  private static void updateCounterContentDescription(Context paramContext, TextView paramTextView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = R.string.character_counter_overflowed_content_description;
    } else {
      i = R.string.character_counter_content_description;
    }
    paramTextView.setContentDescription(paramContext.getString(i, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
  }
  
  private void updateCounterTextAppearanceAndColor()
  {
    Object localObject = this.counterView;
    if (localObject != null)
    {
      int i;
      if (this.counterOverflowed) {
        i = this.counterOverflowTextAppearance;
      } else {
        i = this.counterTextAppearance;
      }
      setTextAppearanceCompatWithErrorFallback((TextView)localObject, i);
      if (!this.counterOverflowed)
      {
        localObject = this.counterTextColor;
        if (localObject != null) {
          this.counterView.setTextColor((ColorStateList)localObject);
        }
      }
      if (this.counterOverflowed)
      {
        localObject = this.counterOverflowTextColor;
        if (localObject != null) {
          this.counterView.setTextColor((ColorStateList)localObject);
        }
      }
    }
  }
  
  private void updateDropdownMenuBackground()
  {
    if ((this.endIconMode == 3) && (this.boxBackgroundMode == 2)) {
      ((DropdownMenuEndIconDelegate)this.endIconDelegates.get(3)).updateOutlinedRippleEffect((AutoCompleteTextView)this.editText);
    }
  }
  
  private boolean updateEditTextHeightBasedOnIcon()
  {
    if (this.editText == null) {
      return false;
    }
    int i = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight());
    if (this.editText.getMeasuredHeight() < i)
    {
      this.editText.setMinimumHeight(i);
      return true;
    }
    return false;
  }
  
  private void updateEndLayoutVisibility()
  {
    Object localObject = this.endIconFrame;
    int i = this.endIconView.getVisibility();
    int j = 8;
    if ((i == 0) && (!isErrorIconVisible())) {
      i = 0;
    } else {
      i = 8;
    }
    ((FrameLayout)localObject).setVisibility(i);
    if ((this.suffixText != null) && (!isHintExpanded())) {
      i = 0;
    } else {
      i = 8;
    }
    if ((!isEndIconVisible()) && (!isErrorIconVisible()) && (i != 0)) {
      i = 0;
    } else {
      i = 1;
    }
    localObject = this.endLayout;
    if (i != 0) {
      j = 0;
    }
    ((LinearLayout)localObject).setVisibility(j);
  }
  
  private void updateErrorIconVisibility()
  {
    Object localObject = getErrorIconDrawable();
    int j = 0;
    int i;
    if ((localObject != null) && (this.indicatorViewController.isErrorEnabled()) && (this.indicatorViewController.errorShouldBeShown())) {
      i = 1;
    } else {
      i = 0;
    }
    localObject = this.errorIconView;
    if (i != 0) {
      i = j;
    } else {
      i = 8;
    }
    ((CheckableImageButton)localObject).setVisibility(i);
    updateEndLayoutVisibility();
    updateSuffixTextViewPadding();
    if (!hasEndIcon()) {
      updateDummyDrawables();
    }
  }
  
  private void updateInputLayoutMargins()
  {
    if (this.boxBackgroundMode != 1)
    {
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.inputFrame.getLayoutParams();
      int i = calculateLabelMarginTop();
      if (i != localLayoutParams.topMargin)
      {
        localLayoutParams.topMargin = i;
        this.inputFrame.requestLayout();
      }
    }
  }
  
  private void updateLabelState(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool2 = isEnabled();
    Object localObject = this.editText;
    int i;
    if ((localObject != null) && (!TextUtils.isEmpty(((EditText)localObject).getText()))) {
      i = 1;
    } else {
      i = 0;
    }
    localObject = this.editText;
    int j;
    if ((localObject != null) && (((EditText)localObject).hasFocus())) {
      j = 1;
    } else {
      j = 0;
    }
    boolean bool1 = this.indicatorViewController.errorShouldBeShown();
    localObject = this.defaultHintTextColor;
    if (localObject != null)
    {
      this.collapsingTextHelper.setCollapsedTextColor((ColorStateList)localObject);
      this.collapsingTextHelper.setExpandedTextColor(this.defaultHintTextColor);
    }
    if (!bool2)
    {
      localObject = this.defaultHintTextColor;
      int k;
      if (localObject != null)
      {
        k = this.disabledColor;
        k = ((ColorStateList)localObject).getColorForState(new int[] { -16842910 }, k);
      }
      else
      {
        k = this.disabledColor;
      }
      this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(k));
      this.collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf(k));
    }
    else if (bool1)
    {
      this.collapsingTextHelper.setCollapsedTextColor(this.indicatorViewController.getErrorViewTextColors());
    }
    else
    {
      if (this.counterOverflowed)
      {
        localObject = this.counterView;
        if (localObject != null)
        {
          this.collapsingTextHelper.setCollapsedTextColor(((TextView)localObject).getTextColors());
          break label259;
        }
      }
      if (j != 0)
      {
        localObject = this.focusedTextColor;
        if (localObject != null) {
          this.collapsingTextHelper.setCollapsedTextColor((ColorStateList)localObject);
        }
      }
    }
    label259:
    if ((i == 0) && (this.expandedHintEnabled) && ((!isEnabled()) || (j == 0)))
    {
      if ((paramBoolean2) || (!this.hintExpanded)) {
        expandHint(paramBoolean1);
      }
    }
    else if ((paramBoolean2) || (this.hintExpanded)) {
      collapseHint(paramBoolean1);
    }
  }
  
  private void updatePlaceholderMeasurementsBasedOnEditText()
  {
    if (this.placeholderTextView != null)
    {
      EditText localEditText = this.editText;
      if (localEditText != null)
      {
        int i = localEditText.getGravity();
        this.placeholderTextView.setGravity(i);
        this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
      }
    }
  }
  
  private void updatePlaceholderText()
  {
    EditText localEditText = this.editText;
    int i;
    if (localEditText == null) {
      i = 0;
    } else {
      i = localEditText.getText().length();
    }
    updatePlaceholderText(i);
  }
  
  private void updatePlaceholderText(int paramInt)
  {
    if ((paramInt == 0) && (!this.hintExpanded)) {
      showPlaceholderText();
    } else {
      hidePlaceholderText();
    }
  }
  
  private void updateStrokeErrorColor(boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.strokeErrorColor.getDefaultColor();
    int k = this.strokeErrorColor.getColorForState(new int[] { 16843623, 16842910 }, i);
    int j = this.strokeErrorColor.getColorForState(new int[] { 16843518, 16842910 }, i);
    if (paramBoolean1) {
      this.boxStrokeColor = j;
    } else if (paramBoolean2) {
      this.boxStrokeColor = k;
    } else {
      this.boxStrokeColor = i;
    }
  }
  
  private void updateSuffixTextViewPadding()
  {
    if (this.editText == null) {
      return;
    }
    int i;
    if ((!isEndIconVisible()) && (!isErrorIconVisible())) {
      i = ViewCompat.getPaddingEnd(this.editText);
    } else {
      i = 0;
    }
    ViewCompat.setPaddingRelative(this.suffixTextView, getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), this.editText.getPaddingTop(), i, this.editText.getPaddingBottom());
  }
  
  private void updateSuffixTextVisibility()
  {
    int j = this.suffixTextView.getVisibility();
    Object localObject = this.suffixText;
    boolean bool = false;
    int i;
    if ((localObject != null) && (!isHintExpanded())) {
      i = 0;
    } else {
      i = 8;
    }
    if (j != i)
    {
      localObject = getEndIconDelegate();
      if (i == 0) {
        bool = true;
      }
      ((EndIconDelegate)localObject).onSuffixVisibilityChanged(bool);
    }
    updateEndLayoutVisibility();
    this.suffixTextView.setVisibility(i);
    updateDummyDrawables();
  }
  
  public void addOnEditTextAttachedListener(OnEditTextAttachedListener paramOnEditTextAttachedListener)
  {
    this.editTextAttachedListeners.add(paramOnEditTextAttachedListener);
    if (this.editText != null) {
      paramOnEditTextAttachedListener.onEditTextAttached(this);
    }
  }
  
  public void addOnEndIconChangedListener(OnEndIconChangedListener paramOnEndIconChangedListener)
  {
    this.endIconChangedListeners.add(paramOnEndIconChangedListener);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramView instanceof EditText))
    {
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(paramLayoutParams);
      localLayoutParams.gravity = (localLayoutParams.gravity & 0xFFFFFF8F | 0x10);
      this.inputFrame.addView(paramView, localLayoutParams);
      this.inputFrame.setLayoutParams(paramLayoutParams);
      updateInputLayoutMargins();
      setEditText((EditText)paramView);
    }
    else
    {
      super.addView(paramView, paramInt, paramLayoutParams);
    }
  }
  
  void animateToExpansionFraction(float paramFloat)
  {
    if (this.collapsingTextHelper.getExpansionFraction() == paramFloat) {
      return;
    }
    if (this.animator == null)
    {
      ValueAnimator localValueAnimator = new ValueAnimator();
      this.animator = localValueAnimator;
      localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      this.animator.setDuration(167L);
      this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
    }
    this.animator.setFloatValues(new float[] { this.collapsingTextHelper.getExpansionFraction(), paramFloat });
    this.animator.start();
  }
  
  public void clearOnEditTextAttachedListeners()
  {
    this.editTextAttachedListeners.clear();
  }
  
  public void clearOnEndIconChangedListeners()
  {
    this.endIconChangedListeners.clear();
  }
  
  boolean cutoutIsOpen()
  {
    boolean bool;
    if ((cutoutEnabled()) && (((CutoutDrawable)this.boxBackground).hasCutout())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void dispatchProvideAutofillStructure(ViewStructure paramViewStructure, int paramInt)
  {
    Object localObject = this.editText;
    if (localObject == null)
    {
      super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
      return;
    }
    boolean bool;
    if (this.originalHint != null)
    {
      bool = this.isProvidingHint;
      this.isProvidingHint = false;
      localObject = ((EditText)localObject).getHint();
      this.editText.setHint(this.originalHint);
    }
    try
    {
      super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
      this.editText.setHint((CharSequence)localObject);
      this.isProvidingHint = bool;
    }
    finally
    {
      this.editText.setHint((CharSequence)localObject);
      this.isProvidingHint = bool;
    }
    onProvideAutofillVirtualStructure(paramViewStructure, paramInt);
    paramViewStructure.setChildCount(this.inputFrame.getChildCount());
    for (int i = 0; i < this.inputFrame.getChildCount(); i++)
    {
      View localView = this.inputFrame.getChildAt(i);
      localObject = paramViewStructure.newChild(i);
      localView.dispatchProvideAutofillStructure((ViewStructure)localObject, paramInt);
      if (localView == this.editText) {
        ((ViewStructure)localObject).setHint(getHint());
      }
    }
  }
  
  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray)
  {
    this.restoringSavedState = true;
    super.dispatchRestoreInstanceState(paramSparseArray);
    this.restoringSavedState = false;
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    drawHint(paramCanvas);
    drawBoxUnderline(paramCanvas);
  }
  
  protected void drawableStateChanged()
  {
    if (this.inDrawableStateChanged) {
      return;
    }
    boolean bool2 = true;
    this.inDrawableStateChanged = true;
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    boolean bool1 = false;
    CollapsingTextHelper localCollapsingTextHelper = this.collapsingTextHelper;
    if (localCollapsingTextHelper != null) {
      bool1 = false | localCollapsingTextHelper.setState(arrayOfInt);
    }
    if (this.editText != null)
    {
      if ((!ViewCompat.isLaidOut(this)) || (!isEnabled())) {
        bool2 = false;
      }
      updateLabelState(bool2);
    }
    updateEditTextBackground();
    updateTextInputBoxState();
    if (bool1) {
      invalidate();
    }
    this.inDrawableStateChanged = false;
  }
  
  public int getBaseline()
  {
    EditText localEditText = this.editText;
    if (localEditText != null) {
      return localEditText.getBaseline() + getPaddingTop() + calculateLabelMarginTop();
    }
    return super.getBaseline();
  }
  
  MaterialShapeDrawable getBoxBackground()
  {
    int i = this.boxBackgroundMode;
    if ((i != 1) && (i != 2)) {
      throw new IllegalStateException();
    }
    return this.boxBackground;
  }
  
  public int getBoxBackgroundColor()
  {
    return this.boxBackgroundColor;
  }
  
  public int getBoxBackgroundMode()
  {
    return this.boxBackgroundMode;
  }
  
  public int getBoxCollapsedPaddingTop()
  {
    return this.boxCollapsedPaddingTopPx;
  }
  
  public float getBoxCornerRadiusBottomEnd()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this)) {
      f = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
    } else {
      f = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
    }
    return f;
  }
  
  public float getBoxCornerRadiusBottomStart()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this)) {
      f = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
    } else {
      f = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
    }
    return f;
  }
  
  public float getBoxCornerRadiusTopEnd()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this)) {
      f = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
    } else {
      f = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
    }
    return f;
  }
  
  public float getBoxCornerRadiusTopStart()
  {
    float f;
    if (ViewUtils.isLayoutRtl(this)) {
      f = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
    } else {
      f = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
    }
    return f;
  }
  
  public int getBoxStrokeColor()
  {
    return this.focusedStrokeColor;
  }
  
  public ColorStateList getBoxStrokeErrorColor()
  {
    return this.strokeErrorColor;
  }
  
  public int getBoxStrokeWidth()
  {
    return this.boxStrokeWidthDefaultPx;
  }
  
  public int getBoxStrokeWidthFocused()
  {
    return this.boxStrokeWidthFocusedPx;
  }
  
  public int getCounterMaxLength()
  {
    return this.counterMaxLength;
  }
  
  CharSequence getCounterOverflowDescription()
  {
    if ((this.counterEnabled) && (this.counterOverflowed))
    {
      TextView localTextView = this.counterView;
      if (localTextView != null) {
        return localTextView.getContentDescription();
      }
    }
    return null;
  }
  
  public ColorStateList getCounterOverflowTextColor()
  {
    return this.counterTextColor;
  }
  
  public ColorStateList getCounterTextColor()
  {
    return this.counterTextColor;
  }
  
  public ColorStateList getDefaultHintTextColor()
  {
    return this.defaultHintTextColor;
  }
  
  public EditText getEditText()
  {
    return this.editText;
  }
  
  public CharSequence getEndIconContentDescription()
  {
    return this.endIconView.getContentDescription();
  }
  
  public Drawable getEndIconDrawable()
  {
    return this.endIconView.getDrawable();
  }
  
  public int getEndIconMode()
  {
    return this.endIconMode;
  }
  
  CheckableImageButton getEndIconView()
  {
    return this.endIconView;
  }
  
  public CharSequence getError()
  {
    CharSequence localCharSequence;
    if (this.indicatorViewController.isErrorEnabled()) {
      localCharSequence = this.indicatorViewController.getErrorText();
    } else {
      localCharSequence = null;
    }
    return localCharSequence;
  }
  
  public CharSequence getErrorContentDescription()
  {
    return this.indicatorViewController.getErrorContentDescription();
  }
  
  public int getErrorCurrentTextColors()
  {
    return this.indicatorViewController.getErrorViewCurrentTextColor();
  }
  
  public Drawable getErrorIconDrawable()
  {
    return this.errorIconView.getDrawable();
  }
  
  final int getErrorTextCurrentColor()
  {
    return this.indicatorViewController.getErrorViewCurrentTextColor();
  }
  
  public CharSequence getHelperText()
  {
    CharSequence localCharSequence;
    if (this.indicatorViewController.isHelperTextEnabled()) {
      localCharSequence = this.indicatorViewController.getHelperText();
    } else {
      localCharSequence = null;
    }
    return localCharSequence;
  }
  
  public int getHelperTextCurrentTextColor()
  {
    return this.indicatorViewController.getHelperTextViewCurrentTextColor();
  }
  
  public CharSequence getHint()
  {
    CharSequence localCharSequence;
    if (this.hintEnabled) {
      localCharSequence = this.hint;
    } else {
      localCharSequence = null;
    }
    return localCharSequence;
  }
  
  final float getHintCollapsedTextHeight()
  {
    return this.collapsingTextHelper.getCollapsedTextHeight();
  }
  
  final int getHintCurrentCollapsedTextColor()
  {
    return this.collapsingTextHelper.getCurrentCollapsedTextColor();
  }
  
  public ColorStateList getHintTextColor()
  {
    return this.focusedTextColor;
  }
  
  public int getMaxEms()
  {
    return this.maxEms;
  }
  
  public int getMaxWidth()
  {
    return this.maxWidth;
  }
  
  public int getMinEms()
  {
    return this.minEms;
  }
  
  public int getMinWidth()
  {
    return this.minWidth;
  }
  
  @Deprecated
  public CharSequence getPasswordVisibilityToggleContentDescription()
  {
    return this.endIconView.getContentDescription();
  }
  
  @Deprecated
  public Drawable getPasswordVisibilityToggleDrawable()
  {
    return this.endIconView.getDrawable();
  }
  
  public CharSequence getPlaceholderText()
  {
    CharSequence localCharSequence;
    if (this.placeholderEnabled) {
      localCharSequence = this.placeholderText;
    } else {
      localCharSequence = null;
    }
    return localCharSequence;
  }
  
  public int getPlaceholderTextAppearance()
  {
    return this.placeholderTextAppearance;
  }
  
  public ColorStateList getPlaceholderTextColor()
  {
    return this.placeholderTextColor;
  }
  
  public CharSequence getPrefixText()
  {
    return this.startLayout.getPrefixText();
  }
  
  public ColorStateList getPrefixTextColor()
  {
    return this.startLayout.getPrefixTextColor();
  }
  
  public TextView getPrefixTextView()
  {
    return this.startLayout.getPrefixTextView();
  }
  
  public CharSequence getStartIconContentDescription()
  {
    return this.startLayout.getStartIconContentDescription();
  }
  
  public Drawable getStartIconDrawable()
  {
    return this.startLayout.getStartIconDrawable();
  }
  
  public CharSequence getSuffixText()
  {
    return this.suffixText;
  }
  
  public ColorStateList getSuffixTextColor()
  {
    return this.suffixTextView.getTextColors();
  }
  
  public TextView getSuffixTextView()
  {
    return this.suffixTextView;
  }
  
  public Typeface getTypeface()
  {
    return this.typeface;
  }
  
  public boolean isCounterEnabled()
  {
    return this.counterEnabled;
  }
  
  public boolean isEndIconCheckable()
  {
    return this.endIconView.isCheckable();
  }
  
  public boolean isEndIconVisible()
  {
    boolean bool;
    if ((this.endIconFrame.getVisibility() == 0) && (this.endIconView.getVisibility() == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isErrorEnabled()
  {
    return this.indicatorViewController.isErrorEnabled();
  }
  
  public boolean isExpandedHintEnabled()
  {
    return this.expandedHintEnabled;
  }
  
  final boolean isHelperTextDisplayed()
  {
    return this.indicatorViewController.helperTextIsDisplayed();
  }
  
  public boolean isHelperTextEnabled()
  {
    return this.indicatorViewController.isHelperTextEnabled();
  }
  
  public boolean isHintAnimationEnabled()
  {
    return this.hintAnimationEnabled;
  }
  
  public boolean isHintEnabled()
  {
    return this.hintEnabled;
  }
  
  final boolean isHintExpanded()
  {
    return this.hintExpanded;
  }
  
  @Deprecated
  public boolean isPasswordVisibilityToggleEnabled()
  {
    int i = this.endIconMode;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isProvidingHint()
  {
    return this.isProvidingHint;
  }
  
  public boolean isStartIconCheckable()
  {
    return this.startLayout.isStartIconCheckable();
  }
  
  public boolean isStartIconVisible()
  {
    return this.startLayout.isStartIconVisible();
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(paramConfiguration);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    EditText localEditText = this.editText;
    if (localEditText != null)
    {
      Rect localRect = this.tmpRect;
      DescendantOffsetUtils.getDescendantRect(this, localEditText, localRect);
      updateBoxUnderlineBounds(localRect);
      if (this.hintEnabled)
      {
        this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
        paramInt1 = this.editText.getGravity();
        this.collapsingTextHelper.setCollapsedTextGravity(paramInt1 & 0xFFFFFF8F | 0x30);
        this.collapsingTextHelper.setExpandedTextGravity(paramInt1);
        this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(localRect));
        this.collapsingTextHelper.setExpandedBounds(calculateExpandedTextBounds(localRect));
        this.collapsingTextHelper.recalculate();
        if ((cutoutEnabled()) && (!this.hintExpanded)) {
          openCutout();
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    boolean bool1 = updateEditTextHeightBasedOnIcon();
    boolean bool2 = updateDummyDrawables();
    if ((bool1) || (bool2)) {
      this.editText.post(new Runnable()
      {
        public void run()
        {
          TextInputLayout.this.editText.requestLayout();
        }
      });
    }
    updatePlaceholderMeasurementsBasedOnEditText();
    updateSuffixTextViewPadding();
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    setError(paramParcelable.error);
    if (paramParcelable.isEndIconChecked) {
      this.endIconView.post(new Runnable()
      {
        public void run()
        {
          TextInputLayout.this.endIconView.performClick();
          TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
        }
      });
    }
    setHint(paramParcelable.hintText);
    setHelperText(paramParcelable.helperText);
    setPlaceholderText(paramParcelable.placeholderText);
    requestLayout();
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    super.onRtlPropertiesChanged(paramInt);
    int i = 0;
    boolean bool1;
    if (paramInt == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2 = this.areCornerRadiiRtl;
    if (bool1 != bool2)
    {
      paramInt = i;
      if (bool1)
      {
        paramInt = i;
        if (!bool2) {
          paramInt = 1;
        }
      }
      float f4 = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
      float f5 = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
      float f3 = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
      float f2 = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
      float f1;
      if (paramInt != 0) {
        f1 = f4;
      } else {
        f1 = f5;
      }
      if (paramInt != 0) {
        f4 = f5;
      }
      if (paramInt != 0) {
        f5 = f3;
      } else {
        f5 = f2;
      }
      if (paramInt == 0) {
        f2 = f3;
      }
      setBoxCornerRadii(f1, f4, f5, f2);
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (this.indicatorViewController.errorShouldBeShown()) {
      localSavedState.error = getError();
    }
    boolean bool;
    if ((hasEndIcon()) && (this.endIconView.isChecked())) {
      bool = true;
    } else {
      bool = false;
    }
    localSavedState.isEndIconChecked = bool;
    localSavedState.hintText = getHint();
    localSavedState.helperText = getHelperText();
    localSavedState.placeholderText = getPlaceholderText();
    return localSavedState;
  }
  
  @Deprecated
  public void passwordVisibilityToggleRequested(boolean paramBoolean)
  {
    if (this.endIconMode == 1)
    {
      this.endIconView.performClick();
      if (paramBoolean) {
        this.endIconView.jumpDrawablesToCurrentState();
      }
    }
  }
  
  public void refreshEndIconDrawableState()
  {
    IconHelper.refreshIconDrawableState(this, this.endIconView, this.endIconTintList);
  }
  
  public void refreshErrorIconDrawableState()
  {
    IconHelper.refreshIconDrawableState(this, this.errorIconView, this.errorIconTintList);
  }
  
  public void refreshStartIconDrawableState()
  {
    this.startLayout.refreshStartIconDrawableState();
  }
  
  public void removeOnEditTextAttachedListener(OnEditTextAttachedListener paramOnEditTextAttachedListener)
  {
    this.editTextAttachedListeners.remove(paramOnEditTextAttachedListener);
  }
  
  public void removeOnEndIconChangedListener(OnEndIconChangedListener paramOnEndIconChangedListener)
  {
    this.endIconChangedListeners.remove(paramOnEndIconChangedListener);
  }
  
  public void setBoxBackgroundColor(int paramInt)
  {
    if (this.boxBackgroundColor != paramInt)
    {
      this.boxBackgroundColor = paramInt;
      this.defaultFilledBackgroundColor = paramInt;
      this.focusedFilledBackgroundColor = paramInt;
      this.hoveredFilledBackgroundColor = paramInt;
      applyBoxAttributes();
    }
  }
  
  public void setBoxBackgroundColorResource(int paramInt)
  {
    setBoxBackgroundColor(ContextCompat.getColor(getContext(), paramInt));
  }
  
  public void setBoxBackgroundColorStateList(ColorStateList paramColorStateList)
  {
    int i = paramColorStateList.getDefaultColor();
    this.defaultFilledBackgroundColor = i;
    this.boxBackgroundColor = i;
    this.disabledFilledBackgroundColor = paramColorStateList.getColorForState(new int[] { -16842910 }, -1);
    this.focusedFilledBackgroundColor = paramColorStateList.getColorForState(new int[] { 16842908, 16842910 }, -1);
    this.hoveredFilledBackgroundColor = paramColorStateList.getColorForState(new int[] { 16843623, 16842910 }, -1);
    applyBoxAttributes();
  }
  
  public void setBoxBackgroundMode(int paramInt)
  {
    if (paramInt == this.boxBackgroundMode) {
      return;
    }
    this.boxBackgroundMode = paramInt;
    if (this.editText != null) {
      onApplyBoxBackgroundMode();
    }
  }
  
  public void setBoxCollapsedPaddingTop(int paramInt)
  {
    this.boxCollapsedPaddingTopPx = paramInt;
  }
  
  public void setBoxCornerRadii(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool = ViewUtils.isLayoutRtl(this);
    this.areCornerRadiiRtl = bool;
    float f;
    if (bool) {
      f = paramFloat2;
    } else {
      f = paramFloat1;
    }
    if (!bool) {
      paramFloat1 = paramFloat2;
    }
    if (bool) {
      paramFloat2 = paramFloat4;
    } else {
      paramFloat2 = paramFloat3;
    }
    if (bool) {
      paramFloat4 = paramFloat3;
    }
    MaterialShapeDrawable localMaterialShapeDrawable = this.boxBackground;
    if ((localMaterialShapeDrawable == null) || (localMaterialShapeDrawable.getTopLeftCornerResolvedSize() != f) || (this.boxBackground.getTopRightCornerResolvedSize() != paramFloat1) || (this.boxBackground.getBottomLeftCornerResolvedSize() != paramFloat2) || (this.boxBackground.getBottomRightCornerResolvedSize() != paramFloat4))
    {
      this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(f).setTopRightCornerSize(paramFloat1).setBottomLeftCornerSize(paramFloat2).setBottomRightCornerSize(paramFloat4).build();
      applyBoxAttributes();
    }
  }
  
  public void setBoxCornerRadiiResources(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setBoxCornerRadii(getContext().getResources().getDimension(paramInt1), getContext().getResources().getDimension(paramInt2), getContext().getResources().getDimension(paramInt4), getContext().getResources().getDimension(paramInt3));
  }
  
  public void setBoxStrokeColor(int paramInt)
  {
    if (this.focusedStrokeColor != paramInt)
    {
      this.focusedStrokeColor = paramInt;
      updateTextInputBoxState();
    }
  }
  
  public void setBoxStrokeColorStateList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.isStateful())
    {
      this.defaultStrokeColor = paramColorStateList.getDefaultColor();
      this.disabledColor = paramColorStateList.getColorForState(new int[] { -16842910 }, -1);
      this.hoveredStrokeColor = paramColorStateList.getColorForState(new int[] { 16843623, 16842910 }, -1);
      this.focusedStrokeColor = paramColorStateList.getColorForState(new int[] { 16842908, 16842910 }, -1);
    }
    else if (this.focusedStrokeColor != paramColorStateList.getDefaultColor())
    {
      this.focusedStrokeColor = paramColorStateList.getDefaultColor();
    }
    updateTextInputBoxState();
  }
  
  public void setBoxStrokeErrorColor(ColorStateList paramColorStateList)
  {
    if (this.strokeErrorColor != paramColorStateList)
    {
      this.strokeErrorColor = paramColorStateList;
      updateTextInputBoxState();
    }
  }
  
  public void setBoxStrokeWidth(int paramInt)
  {
    this.boxStrokeWidthDefaultPx = paramInt;
    updateTextInputBoxState();
  }
  
  public void setBoxStrokeWidthFocused(int paramInt)
  {
    this.boxStrokeWidthFocusedPx = paramInt;
    updateTextInputBoxState();
  }
  
  public void setBoxStrokeWidthFocusedResource(int paramInt)
  {
    setBoxStrokeWidthFocused(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setBoxStrokeWidthResource(int paramInt)
  {
    setBoxStrokeWidth(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setCounterEnabled(boolean paramBoolean)
  {
    if (this.counterEnabled != paramBoolean)
    {
      if (paramBoolean)
      {
        Object localObject = new AppCompatTextView(getContext());
        this.counterView = ((TextView)localObject);
        ((TextView)localObject).setId(R.id.textinput_counter);
        localObject = this.typeface;
        if (localObject != null) {
          this.counterView.setTypeface((Typeface)localObject);
        }
        this.counterView.setMaxLines(1);
        this.indicatorViewController.addIndicator(this.counterView, 2);
        MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)this.counterView.getLayoutParams(), getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_counter_margin_start));
        updateCounterTextAppearanceAndColor();
        updateCounter();
      }
      else
      {
        this.indicatorViewController.removeIndicator(this.counterView, 2);
        this.counterView = null;
      }
      this.counterEnabled = paramBoolean;
    }
  }
  
  public void setCounterMaxLength(int paramInt)
  {
    if (this.counterMaxLength != paramInt)
    {
      if (paramInt > 0) {
        this.counterMaxLength = paramInt;
      } else {
        this.counterMaxLength = -1;
      }
      if (this.counterEnabled) {
        updateCounter();
      }
    }
  }
  
  public void setCounterOverflowTextAppearance(int paramInt)
  {
    if (this.counterOverflowTextAppearance != paramInt)
    {
      this.counterOverflowTextAppearance = paramInt;
      updateCounterTextAppearanceAndColor();
    }
  }
  
  public void setCounterOverflowTextColor(ColorStateList paramColorStateList)
  {
    if (this.counterOverflowTextColor != paramColorStateList)
    {
      this.counterOverflowTextColor = paramColorStateList;
      updateCounterTextAppearanceAndColor();
    }
  }
  
  public void setCounterTextAppearance(int paramInt)
  {
    if (this.counterTextAppearance != paramInt)
    {
      this.counterTextAppearance = paramInt;
      updateCounterTextAppearanceAndColor();
    }
  }
  
  public void setCounterTextColor(ColorStateList paramColorStateList)
  {
    if (this.counterTextColor != paramColorStateList)
    {
      this.counterTextColor = paramColorStateList;
      updateCounterTextAppearanceAndColor();
    }
  }
  
  public void setDefaultHintTextColor(ColorStateList paramColorStateList)
  {
    this.defaultHintTextColor = paramColorStateList;
    this.focusedTextColor = paramColorStateList;
    if (this.editText != null) {
      updateLabelState(false);
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    recursiveSetEnabled(this, paramBoolean);
    super.setEnabled(paramBoolean);
  }
  
  public void setEndIconActivated(boolean paramBoolean)
  {
    this.endIconView.setActivated(paramBoolean);
  }
  
  public void setEndIconCheckable(boolean paramBoolean)
  {
    this.endIconView.setCheckable(paramBoolean);
  }
  
  public void setEndIconContentDescription(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getResources().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setEndIconContentDescription(localCharSequence);
  }
  
  public void setEndIconContentDescription(CharSequence paramCharSequence)
  {
    if (getEndIconContentDescription() != paramCharSequence) {
      this.endIconView.setContentDescription(paramCharSequence);
    }
  }
  
  public void setEndIconDrawable(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setEndIconDrawable(localDrawable);
  }
  
  public void setEndIconDrawable(Drawable paramDrawable)
  {
    this.endIconView.setImageDrawable(paramDrawable);
    if (paramDrawable != null)
    {
      IconHelper.applyIconTint(this, this.endIconView, this.endIconTintList, this.endIconTintMode);
      refreshEndIconDrawableState();
    }
  }
  
  public void setEndIconMode(int paramInt)
  {
    if (this.endIconMode == paramInt) {
      return;
    }
    int i = this.endIconMode;
    this.endIconMode = paramInt;
    dispatchOnEndIconChanged(i);
    boolean bool;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    }
    setEndIconVisible(bool);
    if (getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode))
    {
      getEndIconDelegate().initialize();
      IconHelper.applyIconTint(this, this.endIconView, this.endIconTintList, this.endIconTintMode);
      return;
    }
    throw new IllegalStateException("The current box background mode " + this.boxBackgroundMode + " is not supported by the end icon mode " + paramInt);
  }
  
  public void setEndIconOnClickListener(View.OnClickListener paramOnClickListener)
  {
    setIconOnClickListener(this.endIconView, paramOnClickListener, this.endIconOnLongClickListener);
  }
  
  public void setEndIconOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.endIconOnLongClickListener = paramOnLongClickListener;
    setIconOnLongClickListener(this.endIconView, paramOnLongClickListener);
  }
  
  public void setEndIconTintList(ColorStateList paramColorStateList)
  {
    if (this.endIconTintList != paramColorStateList)
    {
      this.endIconTintList = paramColorStateList;
      IconHelper.applyIconTint(this, this.endIconView, paramColorStateList, this.endIconTintMode);
    }
  }
  
  public void setEndIconTintMode(PorterDuff.Mode paramMode)
  {
    if (this.endIconTintMode != paramMode)
    {
      this.endIconTintMode = paramMode;
      IconHelper.applyIconTint(this, this.endIconView, this.endIconTintList, paramMode);
    }
  }
  
  public void setEndIconVisible(boolean paramBoolean)
  {
    if (isEndIconVisible() != paramBoolean)
    {
      CheckableImageButton localCheckableImageButton = this.endIconView;
      int i;
      if (paramBoolean) {
        i = 0;
      } else {
        i = 8;
      }
      localCheckableImageButton.setVisibility(i);
      updateEndLayoutVisibility();
      updateSuffixTextViewPadding();
      updateDummyDrawables();
    }
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    if (!this.indicatorViewController.isErrorEnabled())
    {
      if (TextUtils.isEmpty(paramCharSequence)) {
        return;
      }
      setErrorEnabled(true);
    }
    if (!TextUtils.isEmpty(paramCharSequence)) {
      this.indicatorViewController.showError(paramCharSequence);
    } else {
      this.indicatorViewController.hideError();
    }
  }
  
  public void setErrorContentDescription(CharSequence paramCharSequence)
  {
    this.indicatorViewController.setErrorContentDescription(paramCharSequence);
  }
  
  public void setErrorEnabled(boolean paramBoolean)
  {
    this.indicatorViewController.setErrorEnabled(paramBoolean);
  }
  
  public void setErrorIconDrawable(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setErrorIconDrawable(localDrawable);
    refreshErrorIconDrawableState();
  }
  
  public void setErrorIconDrawable(Drawable paramDrawable)
  {
    this.errorIconView.setImageDrawable(paramDrawable);
    updateErrorIconVisibility();
    IconHelper.applyIconTint(this, this.errorIconView, this.errorIconTintList, this.errorIconTintMode);
  }
  
  public void setErrorIconOnClickListener(View.OnClickListener paramOnClickListener)
  {
    setIconOnClickListener(this.errorIconView, paramOnClickListener, this.errorIconOnLongClickListener);
  }
  
  public void setErrorIconOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.errorIconOnLongClickListener = paramOnLongClickListener;
    setIconOnLongClickListener(this.errorIconView, paramOnLongClickListener);
  }
  
  public void setErrorIconTintList(ColorStateList paramColorStateList)
  {
    if (this.errorIconTintList != paramColorStateList)
    {
      this.errorIconTintList = paramColorStateList;
      IconHelper.applyIconTint(this, this.errorIconView, paramColorStateList, this.errorIconTintMode);
    }
  }
  
  public void setErrorIconTintMode(PorterDuff.Mode paramMode)
  {
    if (this.errorIconTintMode != paramMode)
    {
      this.errorIconTintMode = paramMode;
      IconHelper.applyIconTint(this, this.errorIconView, this.errorIconTintList, paramMode);
    }
  }
  
  public void setErrorTextAppearance(int paramInt)
  {
    this.indicatorViewController.setErrorTextAppearance(paramInt);
  }
  
  public void setErrorTextColor(ColorStateList paramColorStateList)
  {
    this.indicatorViewController.setErrorViewTextColor(paramColorStateList);
  }
  
  public void setExpandedHintEnabled(boolean paramBoolean)
  {
    if (this.expandedHintEnabled != paramBoolean)
    {
      this.expandedHintEnabled = paramBoolean;
      updateLabelState(false);
    }
  }
  
  public void setHelperText(CharSequence paramCharSequence)
  {
    if (TextUtils.isEmpty(paramCharSequence))
    {
      if (isHelperTextEnabled()) {
        setHelperTextEnabled(false);
      }
    }
    else
    {
      if (!isHelperTextEnabled()) {
        setHelperTextEnabled(true);
      }
      this.indicatorViewController.showHelper(paramCharSequence);
    }
  }
  
  public void setHelperTextColor(ColorStateList paramColorStateList)
  {
    this.indicatorViewController.setHelperTextViewTextColor(paramColorStateList);
  }
  
  public void setHelperTextEnabled(boolean paramBoolean)
  {
    this.indicatorViewController.setHelperTextEnabled(paramBoolean);
  }
  
  public void setHelperTextTextAppearance(int paramInt)
  {
    this.indicatorViewController.setHelperTextAppearance(paramInt);
  }
  
  public void setHint(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getResources().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setHint(localCharSequence);
  }
  
  public void setHint(CharSequence paramCharSequence)
  {
    if (this.hintEnabled)
    {
      setHintInternal(paramCharSequence);
      sendAccessibilityEvent(2048);
    }
  }
  
  public void setHintAnimationEnabled(boolean paramBoolean)
  {
    this.hintAnimationEnabled = paramBoolean;
  }
  
  public void setHintEnabled(boolean paramBoolean)
  {
    if (paramBoolean != this.hintEnabled)
    {
      this.hintEnabled = paramBoolean;
      if (!paramBoolean)
      {
        this.isProvidingHint = false;
        if ((!TextUtils.isEmpty(this.hint)) && (TextUtils.isEmpty(this.editText.getHint()))) {
          this.editText.setHint(this.hint);
        }
        setHintInternal(null);
      }
      else
      {
        CharSequence localCharSequence = this.editText.getHint();
        if (!TextUtils.isEmpty(localCharSequence))
        {
          if (TextUtils.isEmpty(this.hint)) {
            setHint(localCharSequence);
          }
          this.editText.setHint(null);
        }
        this.isProvidingHint = true;
      }
      if (this.editText != null) {
        updateInputLayoutMargins();
      }
    }
  }
  
  public void setHintTextAppearance(int paramInt)
  {
    this.collapsingTextHelper.setCollapsedTextAppearance(paramInt);
    this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
    if (this.editText != null)
    {
      updateLabelState(false);
      updateInputLayoutMargins();
    }
  }
  
  public void setHintTextColor(ColorStateList paramColorStateList)
  {
    if (this.focusedTextColor != paramColorStateList)
    {
      if (this.defaultHintTextColor == null) {
        this.collapsingTextHelper.setCollapsedTextColor(paramColorStateList);
      }
      this.focusedTextColor = paramColorStateList;
      if (this.editText != null) {
        updateLabelState(false);
      }
    }
  }
  
  public void setMaxEms(int paramInt)
  {
    this.maxEms = paramInt;
    EditText localEditText = this.editText;
    if ((localEditText != null) && (paramInt != -1)) {
      localEditText.setMaxEms(paramInt);
    }
  }
  
  public void setMaxWidth(int paramInt)
  {
    this.maxWidth = paramInt;
    EditText localEditText = this.editText;
    if ((localEditText != null) && (paramInt != -1)) {
      localEditText.setMaxWidth(paramInt);
    }
  }
  
  public void setMaxWidthResource(int paramInt)
  {
    setMaxWidth(getContext().getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setMinEms(int paramInt)
  {
    this.minEms = paramInt;
    EditText localEditText = this.editText;
    if ((localEditText != null) && (paramInt != -1)) {
      localEditText.setMinEms(paramInt);
    }
  }
  
  public void setMinWidth(int paramInt)
  {
    this.minWidth = paramInt;
    EditText localEditText = this.editText;
    if ((localEditText != null) && (paramInt != -1)) {
      localEditText.setMinWidth(paramInt);
    }
  }
  
  public void setMinWidthResource(int paramInt)
  {
    setMinWidth(getContext().getResources().getDimensionPixelSize(paramInt));
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleContentDescription(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getResources().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setPasswordVisibilityToggleContentDescription(localCharSequence);
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleContentDescription(CharSequence paramCharSequence)
  {
    this.endIconView.setContentDescription(paramCharSequence);
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleDrawable(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setPasswordVisibilityToggleDrawable(localDrawable);
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleDrawable(Drawable paramDrawable)
  {
    this.endIconView.setImageDrawable(paramDrawable);
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleEnabled(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.endIconMode != 1)) {
      setEndIconMode(1);
    } else if (!paramBoolean) {
      setEndIconMode(0);
    }
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleTintList(ColorStateList paramColorStateList)
  {
    this.endIconTintList = paramColorStateList;
    IconHelper.applyIconTint(this, this.endIconView, paramColorStateList, this.endIconTintMode);
  }
  
  @Deprecated
  public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode paramMode)
  {
    this.endIconTintMode = paramMode;
    IconHelper.applyIconTint(this, this.endIconView, this.endIconTintList, paramMode);
  }
  
  public void setPlaceholderText(CharSequence paramCharSequence)
  {
    if (this.placeholderTextView == null)
    {
      Object localObject = new AppCompatTextView(getContext());
      this.placeholderTextView = ((TextView)localObject);
      ((TextView)localObject).setId(R.id.textinput_placeholder);
      ViewCompat.setImportantForAccessibility(this.placeholderTextView, 2);
      localObject = createPlaceholderFadeTransition();
      this.placeholderFadeIn = ((Fade)localObject);
      ((Fade)localObject).setStartDelay(67L);
      this.placeholderFadeOut = createPlaceholderFadeTransition();
      setPlaceholderTextAppearance(this.placeholderTextAppearance);
      setPlaceholderTextColor(this.placeholderTextColor);
    }
    if (TextUtils.isEmpty(paramCharSequence))
    {
      setPlaceholderTextEnabled(false);
    }
    else
    {
      if (!this.placeholderEnabled) {
        setPlaceholderTextEnabled(true);
      }
      this.placeholderText = paramCharSequence;
    }
    updatePlaceholderText();
  }
  
  public void setPlaceholderTextAppearance(int paramInt)
  {
    this.placeholderTextAppearance = paramInt;
    TextView localTextView = this.placeholderTextView;
    if (localTextView != null) {
      TextViewCompat.setTextAppearance(localTextView, paramInt);
    }
  }
  
  public void setPlaceholderTextColor(ColorStateList paramColorStateList)
  {
    if (this.placeholderTextColor != paramColorStateList)
    {
      this.placeholderTextColor = paramColorStateList;
      TextView localTextView = this.placeholderTextView;
      if ((localTextView != null) && (paramColorStateList != null)) {
        localTextView.setTextColor(paramColorStateList);
      }
    }
  }
  
  public void setPrefixText(CharSequence paramCharSequence)
  {
    this.startLayout.setPrefixText(paramCharSequence);
  }
  
  public void setPrefixTextAppearance(int paramInt)
  {
    this.startLayout.setPrefixTextAppearance(paramInt);
  }
  
  public void setPrefixTextColor(ColorStateList paramColorStateList)
  {
    this.startLayout.setPrefixTextColor(paramColorStateList);
  }
  
  public void setStartIconCheckable(boolean paramBoolean)
  {
    this.startLayout.setStartIconCheckable(paramBoolean);
  }
  
  public void setStartIconContentDescription(int paramInt)
  {
    CharSequence localCharSequence;
    if (paramInt != 0) {
      localCharSequence = getResources().getText(paramInt);
    } else {
      localCharSequence = null;
    }
    setStartIconContentDescription(localCharSequence);
  }
  
  public void setStartIconContentDescription(CharSequence paramCharSequence)
  {
    this.startLayout.setStartIconContentDescription(paramCharSequence);
  }
  
  public void setStartIconDrawable(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setStartIconDrawable(localDrawable);
  }
  
  public void setStartIconDrawable(Drawable paramDrawable)
  {
    this.startLayout.setStartIconDrawable(paramDrawable);
  }
  
  public void setStartIconOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.startLayout.setStartIconOnClickListener(paramOnClickListener);
  }
  
  public void setStartIconOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.startLayout.setStartIconOnLongClickListener(paramOnLongClickListener);
  }
  
  public void setStartIconTintList(ColorStateList paramColorStateList)
  {
    this.startLayout.setStartIconTintList(paramColorStateList);
  }
  
  public void setStartIconTintMode(PorterDuff.Mode paramMode)
  {
    this.startLayout.setStartIconTintMode(paramMode);
  }
  
  public void setStartIconVisible(boolean paramBoolean)
  {
    this.startLayout.setStartIconVisible(paramBoolean);
  }
  
  public void setSuffixText(CharSequence paramCharSequence)
  {
    CharSequence localCharSequence;
    if (TextUtils.isEmpty(paramCharSequence)) {
      localCharSequence = null;
    } else {
      localCharSequence = paramCharSequence;
    }
    this.suffixText = localCharSequence;
    this.suffixTextView.setText(paramCharSequence);
    updateSuffixTextVisibility();
  }
  
  public void setSuffixTextAppearance(int paramInt)
  {
    TextViewCompat.setTextAppearance(this.suffixTextView, paramInt);
  }
  
  public void setSuffixTextColor(ColorStateList paramColorStateList)
  {
    this.suffixTextView.setTextColor(paramColorStateList);
  }
  
  void setTextAppearanceCompatWithErrorFallback(TextView paramTextView, int paramInt)
  {
    int i = 0;
    try
    {
      TextViewCompat.setTextAppearance(paramTextView, paramInt);
      paramInt = i;
      if (Build.VERSION.SDK_INT >= 23)
      {
        int j = paramTextView.getTextColors().getDefaultColor();
        paramInt = i;
        if (j == -65281) {
          paramInt = 1;
        }
      }
    }
    catch (Exception localException)
    {
      paramInt = 1;
    }
    if (paramInt != 0)
    {
      TextViewCompat.setTextAppearance(paramTextView, R.style.TextAppearance_AppCompat_Caption);
      paramTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.design_error));
    }
  }
  
  public void setTextInputAccessibilityDelegate(AccessibilityDelegate paramAccessibilityDelegate)
  {
    EditText localEditText = this.editText;
    if (localEditText != null) {
      ViewCompat.setAccessibilityDelegate(localEditText, paramAccessibilityDelegate);
    }
  }
  
  public void setTypeface(Typeface paramTypeface)
  {
    if (paramTypeface != this.typeface)
    {
      this.typeface = paramTypeface;
      this.collapsingTextHelper.setTypefaces(paramTypeface);
      this.indicatorViewController.setTypefaces(paramTypeface);
      TextView localTextView = this.counterView;
      if (localTextView != null) {
        localTextView.setTypeface(paramTypeface);
      }
    }
  }
  
  void updateCounter(int paramInt)
  {
    boolean bool2 = this.counterOverflowed;
    int i = this.counterMaxLength;
    Object localObject;
    if (i == -1)
    {
      TextView localTextView = this.counterView;
      localObject = String.valueOf(paramInt);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localTextView.setText((CharSequence)localObject);
      this.counterView.setContentDescription(null);
      this.counterOverflowed = false;
    }
    else
    {
      boolean bool1;
      if (paramInt > i) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.counterOverflowed = bool1;
      updateCounterContentDescription(getContext(), this.counterView, paramInt, this.counterMaxLength, this.counterOverflowed);
      if (bool2 != this.counterOverflowed) {
        updateCounterTextAppearanceAndColor();
      }
      localObject = BidiFormatter.getInstance();
      this.counterView.setText(((BidiFormatter)localObject).unicodeWrap(getContext().getString(R.string.character_counter_pattern, new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.counterMaxLength) })));
    }
    if ((this.editText != null) && (bool2 != this.counterOverflowed))
    {
      updateLabelState(false);
      updateTextInputBoxState();
      updateEditTextBackground();
    }
  }
  
  boolean updateDummyDrawables()
  {
    if (this.editText == null) {
      return false;
    }
    boolean bool1 = false;
    int i;
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if (shouldUpdateStartDummyDrawable())
    {
      i = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
      if ((this.startDummyDrawable == null) || (this.startDummyDrawableWidth != i))
      {
        localObject1 = new ColorDrawable();
        this.startDummyDrawable = ((Drawable)localObject1);
        this.startDummyDrawableWidth = i;
        ((Drawable)localObject1).setBounds(0, 0, i, 1);
      }
      localObject2 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
      localObject3 = localObject2[0];
      localObject1 = this.startDummyDrawable;
      if (localObject3 != localObject1)
      {
        TextViewCompat.setCompoundDrawablesRelative(this.editText, (Drawable)localObject1, localObject2[1], localObject2[2], localObject2[3]);
        bool1 = true;
      }
    }
    else if (this.startDummyDrawable != null)
    {
      localObject1 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
      TextViewCompat.setCompoundDrawablesRelative(this.editText, null, localObject1[1], localObject1[2], localObject1[3]);
      this.startDummyDrawable = null;
      bool1 = true;
    }
    boolean bool2;
    if (shouldUpdateEndDummyDrawable())
    {
      int j = this.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
      localObject1 = getEndIconToUpdateDummyDrawable();
      i = j;
      if (localObject1 != null) {
        i = ((View)localObject1).getMeasuredWidth() + j + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)((View)localObject1).getLayoutParams());
      }
      localObject1 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
      localObject2 = this.endDummyDrawable;
      if ((localObject2 != null) && (this.endDummyDrawableWidth != i))
      {
        this.endDummyDrawableWidth = i;
        ((Drawable)localObject2).setBounds(0, 0, i, 1);
        TextViewCompat.setCompoundDrawablesRelative(this.editText, localObject1[0], localObject1[1], this.endDummyDrawable, localObject1[3]);
        bool2 = true;
      }
      else
      {
        if (localObject2 == null)
        {
          localObject2 = new ColorDrawable();
          this.endDummyDrawable = ((Drawable)localObject2);
          this.endDummyDrawableWidth = i;
          ((Drawable)localObject2).setBounds(0, 0, i, 1);
        }
        localObject3 = localObject1[2];
        localObject2 = this.endDummyDrawable;
        bool2 = bool1;
        if (localObject3 != localObject2)
        {
          this.originalEditTextEndDrawable = localObject1[2];
          TextViewCompat.setCompoundDrawablesRelative(this.editText, localObject1[0], localObject1[1], (Drawable)localObject2, localObject1[3]);
          bool2 = true;
        }
      }
    }
    else
    {
      bool2 = bool1;
      if (this.endDummyDrawable != null)
      {
        localObject1 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
        if (localObject1[2] == this.endDummyDrawable)
        {
          TextViewCompat.setCompoundDrawablesRelative(this.editText, localObject1[0], localObject1[1], this.originalEditTextEndDrawable, localObject1[3]);
          bool1 = true;
        }
        this.endDummyDrawable = null;
        return bool1;
      }
    }
    bool1 = bool2;
    return bool1;
  }
  
  void updateEditTextBackground()
  {
    Object localObject1 = this.editText;
    if ((localObject1 != null) && (this.boxBackgroundMode == 0))
    {
      Object localObject2 = ((EditText)localObject1).getBackground();
      if (localObject2 == null) {
        return;
      }
      localObject1 = localObject2;
      if (DrawableUtils.canSafelyMutateDrawable((Drawable)localObject2)) {
        localObject1 = ((Drawable)localObject2).mutate();
      }
      if (this.indicatorViewController.errorShouldBeShown())
      {
        ((Drawable)localObject1).setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.getErrorViewCurrentTextColor(), PorterDuff.Mode.SRC_IN));
      }
      else
      {
        if (this.counterOverflowed)
        {
          localObject2 = this.counterView;
          if (localObject2 != null)
          {
            ((Drawable)localObject1).setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(((TextView)localObject2).getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            break label117;
          }
        }
        DrawableCompat.clearColorFilter((Drawable)localObject1);
        this.editText.refreshDrawableState();
      }
      label117:
      return;
    }
  }
  
  void updateLabelState(boolean paramBoolean)
  {
    updateLabelState(paramBoolean, false);
  }
  
  void updateTextInputBoxState()
  {
    if ((this.boxBackground != null) && (this.boxBackgroundMode != 0))
    {
      boolean bool1 = isFocused();
      boolean bool3 = false;
      Object localObject;
      if (!bool1)
      {
        localObject = this.editText;
        if ((localObject == null) || (!((EditText)localObject).hasFocus()))
        {
          bool1 = false;
          break label58;
        }
      }
      bool1 = true;
      label58:
      boolean bool2;
      if (!isHovered())
      {
        localObject = this.editText;
        bool2 = bool3;
        if (localObject != null)
        {
          bool2 = bool3;
          if (!((EditText)localObject).isHovered()) {}
        }
      }
      else
      {
        bool2 = true;
      }
      if (!isEnabled())
      {
        this.boxStrokeColor = this.disabledColor;
      }
      else if (this.indicatorViewController.errorShouldBeShown())
      {
        if (this.strokeErrorColor != null) {
          updateStrokeErrorColor(bool1, bool2);
        } else {
          this.boxStrokeColor = this.indicatorViewController.getErrorViewCurrentTextColor();
        }
      }
      else
      {
        if (this.counterOverflowed)
        {
          localObject = this.counterView;
          if (localObject != null)
          {
            if (this.strokeErrorColor != null)
            {
              updateStrokeErrorColor(bool1, bool2);
              break label234;
            }
            this.boxStrokeColor = ((TextView)localObject).getCurrentTextColor();
            break label234;
          }
        }
        if (bool1) {
          this.boxStrokeColor = this.focusedStrokeColor;
        } else if (bool2) {
          this.boxStrokeColor = this.hoveredStrokeColor;
        } else {
          this.boxStrokeColor = this.defaultStrokeColor;
        }
      }
      label234:
      updateErrorIconVisibility();
      refreshErrorIconDrawableState();
      refreshStartIconDrawableState();
      refreshEndIconDrawableState();
      if (getEndIconDelegate().shouldTintIconOnError()) {
        tintEndIconOnError(this.indicatorViewController.errorShouldBeShown());
      }
      if (this.boxBackgroundMode == 2)
      {
        int i = this.boxStrokeWidthPx;
        if ((bool1) && (isEnabled())) {
          this.boxStrokeWidthPx = this.boxStrokeWidthFocusedPx;
        } else {
          this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
        }
        if (this.boxStrokeWidthPx != i) {
          recalculateCutout();
        }
      }
      if (this.boxBackgroundMode == 1) {
        if (!isEnabled()) {
          this.boxBackgroundColor = this.disabledFilledBackgroundColor;
        } else if ((bool2) && (!bool1)) {
          this.boxBackgroundColor = this.hoveredFilledBackgroundColor;
        } else if (bool1) {
          this.boxBackgroundColor = this.focusedFilledBackgroundColor;
        } else {
          this.boxBackgroundColor = this.defaultFilledBackgroundColor;
        }
      }
      applyBoxAttributes();
      return;
    }
  }
  
  public static class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final TextInputLayout layout;
    
    public AccessibilityDelegate(TextInputLayout paramTextInputLayout)
    {
      this.layout = paramTextInputLayout;
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramView = this.layout.getEditText();
      Editable localEditable;
      if (paramView != null) {
        localEditable = paramView.getText();
      } else {
        localEditable = null;
      }
      paramView = this.layout.getHint();
      CharSequence localCharSequence1 = this.layout.getError();
      CharSequence localCharSequence3 = this.layout.getPlaceholderText();
      int j = this.layout.getCounterMaxLength();
      CharSequence localCharSequence2 = this.layout.getCounterOverflowDescription();
      boolean bool2 = TextUtils.isEmpty(localEditable) ^ true;
      boolean bool3 = TextUtils.isEmpty(paramView);
      boolean bool4 = this.layout.isHintExpanded();
      boolean bool1 = TextUtils.isEmpty(localCharSequence1) ^ true;
      int i;
      if ((!bool1) && (TextUtils.isEmpty(localCharSequence2))) {
        i = 0;
      } else {
        i = 1;
      }
      if ((bool3 ^ true)) {
        paramView = paramView.toString();
      } else {
        paramView = "";
      }
      this.layout.startLayout.setupAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat);
      if (bool2)
      {
        paramAccessibilityNodeInfoCompat.setText(localEditable);
      }
      else if (!TextUtils.isEmpty(paramView))
      {
        paramAccessibilityNodeInfoCompat.setText(paramView);
        if (((bool4 ^ true)) && (localCharSequence3 != null)) {
          paramAccessibilityNodeInfoCompat.setText(paramView + ", " + localCharSequence3);
        }
      }
      else if (localCharSequence3 != null)
      {
        paramAccessibilityNodeInfoCompat.setText(localCharSequence3);
      }
      if (!TextUtils.isEmpty(paramView))
      {
        if (Build.VERSION.SDK_INT >= 26)
        {
          paramAccessibilityNodeInfoCompat.setHintText(paramView);
        }
        else
        {
          if (bool2) {
            paramView = localEditable + ", " + paramView;
          }
          paramAccessibilityNodeInfoCompat.setText(paramView);
        }
        if (!bool2) {
          bool3 = true;
        } else {
          bool3 = false;
        }
        paramAccessibilityNodeInfoCompat.setShowingHintText(bool3);
      }
      if ((localEditable == null) || (localEditable.length() != j)) {
        j = -1;
      }
      paramAccessibilityNodeInfoCompat.setMaxTextLength(j);
      if (i != 0)
      {
        if (bool1) {
          paramView = localCharSequence1;
        } else {
          paramView = localCharSequence2;
        }
        paramAccessibilityNodeInfoCompat.setError(paramView);
      }
      if (Build.VERSION.SDK_INT >= 17)
      {
        paramView = this.layout.indicatorViewController.getHelperTextView();
        if (paramView != null) {
          paramAccessibilityNodeInfoCompat.setLabelFor(paramView);
        }
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface BoxBackgroundMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface EndIconMode {}
  
  public static abstract interface OnEditTextAttachedListener
  {
    public abstract void onEditTextAttached(TextInputLayout paramTextInputLayout);
  }
  
  public static abstract interface OnEndIconChangedListener
  {
    public abstract void onEndIconChanged(TextInputLayout paramTextInputLayout, int paramInt);
  }
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public TextInputLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new TextInputLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public TextInputLayout.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new TextInputLayout.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public TextInputLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new TextInputLayout.SavedState[paramAnonymousInt];
      }
    };
    CharSequence error;
    CharSequence helperText;
    CharSequence hintText;
    boolean isEndIconChecked;
    CharSequence placeholderText;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.error = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
      int i = paramParcel.readInt();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      this.isEndIconChecked = bool;
      this.hintText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
      this.helperText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
      this.placeholderText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("TextInputLayout.SavedState{");
      String str = Integer.toHexString(System.identityHashCode(this));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + " error=" + this.error + " hint=" + this.hintText + " helperText=" + this.helperText + " placeholderText=" + this.placeholderText + "}";
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      TextUtils.writeToParcel(this.error, paramParcel, paramInt);
      paramParcel.writeInt(this.isEndIconChecked);
      TextUtils.writeToParcel(this.hintText, paramParcel, paramInt);
      TextUtils.writeToParcel(this.helperText, paramParcel, paramInt);
      TextUtils.writeToParcel(this.placeholderText, paramParcel, paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/TextInputLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */