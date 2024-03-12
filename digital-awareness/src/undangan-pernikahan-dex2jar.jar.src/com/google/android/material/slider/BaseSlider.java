package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.RangeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R.attr;
import com.google.android.material.R.color;
import com.google.android.material.R.dimen;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>>
  extends View
{
  static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;
  private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
  private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION = "minSeparation(%s) must be greater or equal to 0";
  private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE = "minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)";
  private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT = "minSeparation(%s) cannot be set as a dimension when using stepSize(%s)";
  private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
  private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
  private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
  private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo(%s) must be greater than valueFrom(%s)";
  private static final int HALO_ALPHA = 63;
  private static final long LABEL_ANIMATION_ENTER_DURATION = 83L;
  private static final long LABEL_ANIMATION_EXIT_DURATION = 117L;
  private static final String TAG = BaseSlider.class.getSimpleName();
  private static final double THRESHOLD = 1.0E-4D;
  private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
  static final int UNIT_PX = 0;
  static final int UNIT_VALUE = 1;
  private static final String WARNING_FLOATING_POINT_ERROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.";
  private BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
  private final AccessibilityHelper accessibilityHelper;
  private final AccessibilityManager accessibilityManager;
  private int activeThumbIdx = -1;
  private final Paint activeTicksPaint;
  private final Paint activeTrackPaint;
  private final List<L> changeListeners = new ArrayList();
  private Drawable customThumbDrawable;
  private List<Drawable> customThumbDrawablesForValues;
  private final MaterialShapeDrawable defaultThumbDrawable;
  private int defaultThumbRadius;
  private boolean dirtyConfig;
  private int focusedThumbIdx = -1;
  private boolean forceDrawCompatHalo;
  private LabelFormatter formatter;
  private ColorStateList haloColor;
  private final Paint haloPaint;
  private int haloRadius;
  private final Paint inactiveTicksPaint;
  private final Paint inactiveTrackPaint;
  private boolean isLongPress = false;
  private int labelBehavior;
  private final TooltipDrawableFactory labelMaker;
  private int labelPadding;
  private final List<TooltipDrawable> labels = new ArrayList();
  private boolean labelsAreAnimatedIn = false;
  private ValueAnimator labelsInAnimator;
  private ValueAnimator labelsOutAnimator;
  private MotionEvent lastEvent;
  private int minTrackSidePadding;
  private final int scaledTouchSlop;
  private int separationUnit;
  private float stepSize = 0.0F;
  private boolean thumbIsPressed = false;
  private final Paint thumbPaint;
  private int thumbRadius;
  private ColorStateList tickColorActive;
  private ColorStateList tickColorInactive;
  private boolean tickVisible = true;
  private float[] ticksCoordinates;
  private float touchDownX;
  private final List<T> touchListeners = new ArrayList();
  private float touchPosition;
  private ColorStateList trackColorActive;
  private ColorStateList trackColorInactive;
  private int trackHeight;
  private int trackSidePadding;
  private int trackTop;
  private int trackWidth;
  private float valueFrom;
  private float valueTo;
  private ArrayList<Float> values = new ArrayList();
  private int widgetHeight;
  
  public BaseSlider(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BaseSlider(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.sliderStyle);
  }
  
  public BaseSlider(Context paramContext, final AttributeSet paramAttributeSet, final int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, DEF_STYLE_RES), paramAttributeSet, paramInt);
    paramContext = new MaterialShapeDrawable();
    this.defaultThumbDrawable = paramContext;
    this.customThumbDrawablesForValues = Collections.emptyList();
    this.separationUnit = 0;
    Context localContext = getContext();
    Paint localPaint = new Paint();
    this.inactiveTrackPaint = localPaint;
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setStrokeCap(Paint.Cap.ROUND);
    localPaint = new Paint();
    this.activeTrackPaint = localPaint;
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setStrokeCap(Paint.Cap.ROUND);
    localPaint = new Paint(1);
    this.thumbPaint = localPaint;
    localPaint.setStyle(Paint.Style.FILL);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    localPaint = new Paint(1);
    this.haloPaint = localPaint;
    localPaint.setStyle(Paint.Style.FILL);
    localPaint = new Paint();
    this.inactiveTicksPaint = localPaint;
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setStrokeCap(Paint.Cap.ROUND);
    localPaint = new Paint();
    this.activeTicksPaint = localPaint;
    localPaint.setStyle(Paint.Style.STROKE);
    localPaint.setStrokeCap(Paint.Cap.ROUND);
    loadResources(localContext.getResources());
    this.labelMaker = new TooltipDrawableFactory()
    {
      public TooltipDrawable createTooltipDrawable()
      {
        TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(BaseSlider.this.getContext(), paramAttributeSet, R.styleable.Slider, paramInt, BaseSlider.DEF_STYLE_RES, new int[0]);
        TooltipDrawable localTooltipDrawable = BaseSlider.parseLabelDrawable(BaseSlider.this.getContext(), localTypedArray);
        localTypedArray.recycle();
        return localTooltipDrawable;
      }
    };
    processAttributes(localContext, paramAttributeSet, paramInt);
    setFocusable(true);
    setClickable(true);
    paramContext.setShadowCompatibilityMode(2);
    this.scaledTouchSlop = ViewConfiguration.get(localContext).getScaledTouchSlop();
    paramContext = new AccessibilityHelper(this);
    this.accessibilityHelper = paramContext;
    ViewCompat.setAccessibilityDelegate(this, paramContext);
    this.accessibilityManager = ((AccessibilityManager)getContext().getSystemService("accessibility"));
  }
  
  private void adjustCustomThumbDrawableBounds(Drawable paramDrawable)
  {
    int i = this.thumbRadius * 2;
    int j = paramDrawable.getIntrinsicWidth();
    int k = paramDrawable.getIntrinsicHeight();
    if ((j == -1) && (k == -1))
    {
      paramDrawable.setBounds(0, 0, i, i);
    }
    else
    {
      float f = i / Math.max(j, k);
      paramDrawable.setBounds(0, 0, (int)(j * f), (int)(k * f));
    }
  }
  
  private void attachLabelToContentView(TooltipDrawable paramTooltipDrawable)
  {
    paramTooltipDrawable.setRelativeToView(ViewUtils.getContentView(this));
  }
  
  private Float calculateIncrementForKey(int paramInt)
  {
    float f;
    if (this.isLongPress) {
      f = calculateStepIncrement(20);
    } else {
      f = calculateStepIncrement();
    }
    switch (paramInt)
    {
    default: 
      return null;
    case 70: 
    case 81: 
      return Float.valueOf(f);
    case 69: 
      return Float.valueOf(-f);
    case 22: 
      if (isRtl()) {
        f = -f;
      }
      return Float.valueOf(f);
    }
    if (!isRtl()) {
      f = -f;
    }
    return Float.valueOf(f);
  }
  
  private float calculateStepIncrement()
  {
    float f2 = this.stepSize;
    float f1 = f2;
    if (f2 == 0.0F) {
      f1 = 1.0F;
    }
    return f1;
  }
  
  private float calculateStepIncrement(int paramInt)
  {
    float f2 = calculateStepIncrement();
    float f1 = (this.valueTo - this.valueFrom) / f2;
    if (f1 <= paramInt) {
      return f2;
    }
    return Math.round(f1 / paramInt) * f2;
  }
  
  private int calculateTop()
  {
    int j = this.trackTop;
    int k = this.labelBehavior;
    int i = 0;
    if ((k != 1) && (!shouldAlwaysShowLabel())) {
      break label47;
    }
    i = ((TooltipDrawable)this.labels.get(0)).getIntrinsicHeight();
    label47:
    return j + i;
  }
  
  private ValueAnimator createLabelAnimator(boolean paramBoolean)
  {
    float f2 = 0.0F;
    if (paramBoolean) {
      f1 = 0.0F;
    } else {
      f1 = 1.0F;
    }
    Object localObject;
    if (paramBoolean) {
      localObject = this.labelsOutAnimator;
    } else {
      localObject = this.labelsInAnimator;
    }
    float f3 = getAnimatorCurrentValueOrDefault((ValueAnimator)localObject, f1);
    float f1 = f2;
    if (paramBoolean) {
      f1 = 1.0F;
    }
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { f3, f1 });
    long l;
    if (paramBoolean) {
      l = 83L;
    } else {
      l = 117L;
    }
    localValueAnimator.setDuration(l);
    if (paramBoolean) {
      localObject = AnimationUtils.DECELERATE_INTERPOLATOR;
    } else {
      localObject = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    }
    localValueAnimator.setInterpolator((TimeInterpolator)localObject);
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        paramAnonymousValueAnimator = BaseSlider.this.labels.iterator();
        while (paramAnonymousValueAnimator.hasNext()) {
          ((TooltipDrawable)paramAnonymousValueAnimator.next()).setRevealFraction(f);
        }
        ViewCompat.postInvalidateOnAnimation(BaseSlider.this);
      }
    });
    return localValueAnimator;
  }
  
  private void createLabelPool()
  {
    if (this.labels.size() > this.values.size())
    {
      localObject = this.labels.subList(this.values.size(), this.labels.size());
      Iterator localIterator = ((List)localObject).iterator();
      while (localIterator.hasNext())
      {
        TooltipDrawable localTooltipDrawable = (TooltipDrawable)localIterator.next();
        if (ViewCompat.isAttachedToWindow(this)) {
          detachLabelFromContentView(localTooltipDrawable);
        }
      }
      ((List)localObject).clear();
    }
    while (this.labels.size() < this.values.size())
    {
      localObject = this.labelMaker.createTooltipDrawable();
      this.labels.add(localObject);
      if (ViewCompat.isAttachedToWindow(this)) {
        attachLabelToContentView((TooltipDrawable)localObject);
      }
    }
    int j = this.labels.size();
    int i = 1;
    if (j == 1) {
      i = 0;
    }
    Object localObject = this.labels.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((TooltipDrawable)((Iterator)localObject).next()).setStrokeWidth(i);
    }
  }
  
  private void detachLabelFromContentView(TooltipDrawable paramTooltipDrawable)
  {
    ViewOverlayImpl localViewOverlayImpl = ViewUtils.getContentViewOverlay(this);
    if (localViewOverlayImpl != null)
    {
      localViewOverlayImpl.remove(paramTooltipDrawable);
      paramTooltipDrawable.detachView(ViewUtils.getContentView(this));
    }
  }
  
  private float dimenToValue(float paramFloat)
  {
    if (paramFloat == 0.0F) {
      return 0.0F;
    }
    paramFloat = (paramFloat - this.trackSidePadding) / this.trackWidth;
    float f = this.valueFrom;
    return paramFloat * (f - this.valueTo) + f;
  }
  
  private void dispatchOnChangedFromUser(int paramInt)
  {
    Object localObject = this.changeListeners.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((BaseOnChangeListener)((Iterator)localObject).next()).onValueChange(this, ((Float)this.values.get(paramInt)).floatValue(), true);
    }
    localObject = this.accessibilityManager;
    if ((localObject != null) && (((AccessibilityManager)localObject).isEnabled())) {
      scheduleAccessibilityEventSender(paramInt);
    }
  }
  
  private void dispatchOnChangedProgrammatically()
  {
    Iterator localIterator2 = this.changeListeners.iterator();
    while (localIterator2.hasNext())
    {
      BaseOnChangeListener localBaseOnChangeListener = (BaseOnChangeListener)localIterator2.next();
      Iterator localIterator1 = this.values.iterator();
      while (localIterator1.hasNext()) {
        localBaseOnChangeListener.onValueChange(this, ((Float)localIterator1.next()).floatValue(), false);
      }
    }
  }
  
  private void drawActiveTrack(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    float[] arrayOfFloat = getActiveRange();
    int i = this.trackSidePadding;
    float f2 = i;
    float f3 = arrayOfFloat[1];
    float f1 = paramInt1;
    paramCanvas.drawLine(i + arrayOfFloat[0] * paramInt1, paramInt2, f2 + f3 * f1, paramInt2, this.activeTrackPaint);
  }
  
  private void drawInactiveTrack(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    float[] arrayOfFloat = getActiveRange();
    int i = this.trackSidePadding;
    float f = i + arrayOfFloat[1] * paramInt1;
    if (f < i + paramInt1) {
      paramCanvas.drawLine(f, paramInt2, i + paramInt1, paramInt2, this.inactiveTrackPaint);
    }
    i = this.trackSidePadding;
    f = i + arrayOfFloat[0] * paramInt1;
    if (f > i) {
      paramCanvas.drawLine(i, paramInt2, f, paramInt2, this.inactiveTrackPaint);
    }
  }
  
  private void drawThumbDrawable(Canvas paramCanvas, int paramInt1, int paramInt2, float paramFloat, Drawable paramDrawable)
  {
    paramCanvas.save();
    paramCanvas.translate(this.trackSidePadding + (int)(normalizeValue(paramFloat) * paramInt1) - paramDrawable.getBounds().width() / 2.0F, paramInt2 - paramDrawable.getBounds().height() / 2.0F);
    paramDrawable.draw(paramCanvas);
    paramCanvas.restore();
  }
  
  private void drawThumbs(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.values.size(); i++)
    {
      float f = ((Float)this.values.get(i)).floatValue();
      Drawable localDrawable = this.customThumbDrawable;
      if (localDrawable != null)
      {
        drawThumbDrawable(paramCanvas, paramInt1, paramInt2, f, localDrawable);
      }
      else if (i < this.customThumbDrawablesForValues.size())
      {
        drawThumbDrawable(paramCanvas, paramInt1, paramInt2, f, (Drawable)this.customThumbDrawablesForValues.get(i));
      }
      else
      {
        if (!isEnabled()) {
          paramCanvas.drawCircle(this.trackSidePadding + normalizeValue(f) * paramInt1, paramInt2, this.thumbRadius, this.thumbPaint);
        }
        drawThumbDrawable(paramCanvas, paramInt1, paramInt2, f, this.defaultThumbDrawable);
      }
    }
  }
  
  private void ensureLabelsAdded()
  {
    if (this.labelBehavior == 2) {
      return;
    }
    if (!this.labelsAreAnimatedIn)
    {
      this.labelsAreAnimatedIn = true;
      localObject = createLabelAnimator(true);
      this.labelsInAnimator = ((ValueAnimator)localObject);
      this.labelsOutAnimator = null;
      ((ValueAnimator)localObject).start();
    }
    Object localObject = this.labels.iterator();
    for (int i = 0; (i < this.values.size()) && (((Iterator)localObject).hasNext()); i++) {
      if (i != this.focusedThumbIdx) {
        setValueForLabel((TooltipDrawable)((Iterator)localObject).next(), ((Float)this.values.get(i)).floatValue());
      }
    }
    if (((Iterator)localObject).hasNext())
    {
      setValueForLabel((TooltipDrawable)((Iterator)localObject).next(), ((Float)this.values.get(this.focusedThumbIdx)).floatValue());
      return;
    }
    localObject = String.format("Not enough labels(%d) to display all the values(%d)", new Object[] { Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size()) });
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalStateException((String)localObject);
  }
  
  private void ensureLabelsRemoved()
  {
    if (this.labelsAreAnimatedIn)
    {
      this.labelsAreAnimatedIn = false;
      ValueAnimator localValueAnimator = createLabelAnimator(false);
      this.labelsOutAnimator = localValueAnimator;
      this.labelsInAnimator = null;
      localValueAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          super.onAnimationEnd(paramAnonymousAnimator);
          Iterator localIterator = BaseSlider.this.labels.iterator();
          while (localIterator.hasNext())
          {
            paramAnonymousAnimator = (TooltipDrawable)localIterator.next();
            ViewUtils.getContentViewOverlay(BaseSlider.this).remove(paramAnonymousAnimator);
          }
        }
      });
      this.labelsOutAnimator.start();
    }
  }
  
  private void focusThumbOnFocusGained(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 66: 
      moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
      break;
    case 17: 
      moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
      break;
    case 2: 
      moveFocus(Integer.MIN_VALUE);
      break;
    case 1: 
      moveFocus(Integer.MAX_VALUE);
    }
  }
  
  private String formatValue(float paramFloat)
  {
    if (hasLabelFormatter()) {
      return this.formatter.getFormattedValue(paramFloat);
    }
    if ((int)paramFloat == paramFloat) {
      str = "%.0f";
    } else {
      str = "%.2f";
    }
    String str = String.format(str, new Object[] { Float.valueOf(paramFloat) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private float[] getActiveRange()
  {
    float f2 = ((Float)Collections.max(getValues())).floatValue();
    float f1 = ((Float)Collections.min(getValues())).floatValue();
    if (this.values.size() == 1) {
      f1 = this.valueFrom;
    }
    f1 = normalizeValue(f1);
    f2 = normalizeValue(f2);
    float[] arrayOfFloat;
    if (isRtl())
    {
      arrayOfFloat = new float[2];
      arrayOfFloat[0] = f2;
      arrayOfFloat[1] = f1;
    }
    else
    {
      arrayOfFloat = new float[2];
      arrayOfFloat[0] = f1;
      arrayOfFloat[1] = f2;
    }
    return arrayOfFloat;
  }
  
  private static float getAnimatorCurrentValueOrDefault(ValueAnimator paramValueAnimator, float paramFloat)
  {
    if ((paramValueAnimator != null) && (paramValueAnimator.isRunning()))
    {
      paramFloat = ((Float)paramValueAnimator.getAnimatedValue()).floatValue();
      paramValueAnimator.cancel();
      return paramFloat;
    }
    return paramFloat;
  }
  
  private float getClampedValue(int paramInt, float paramFloat)
  {
    float f1 = getMinSeparation();
    if (this.separationUnit == 0) {
      f1 = dimenToValue(f1);
    }
    float f2 = f1;
    f1 = f2;
    if (isRtl()) {
      f1 = -f2;
    }
    if (paramInt + 1 >= this.values.size()) {
      f2 = this.valueTo;
    } else {
      f2 = ((Float)this.values.get(paramInt + 1)).floatValue() - f1;
    }
    if (paramInt - 1 < 0) {
      f1 = this.valueFrom;
    } else {
      f1 = ((Float)this.values.get(paramInt - 1)).floatValue() + f1;
    }
    return MathUtils.clamp(paramFloat, f1, f2);
  }
  
  private int getColorForState(ColorStateList paramColorStateList)
  {
    return paramColorStateList.getColorForState(getDrawableState(), paramColorStateList.getDefaultColor());
  }
  
  private float getValueOfTouchPosition()
  {
    double d2 = snapPosition(this.touchPosition);
    double d1 = d2;
    if (isRtl()) {
      d1 = 1.0D - d2;
    }
    float f1 = this.valueTo;
    float f2 = this.valueFrom;
    return (float)((f1 - f2) * d1 + f2);
  }
  
  private float getValueOfTouchPositionAbsolute()
  {
    float f2 = this.touchPosition;
    float f1 = f2;
    if (isRtl()) {
      f1 = 1.0F - f2;
    }
    f2 = this.valueTo;
    float f3 = this.valueFrom;
    return (f2 - f3) * f1 + f3;
  }
  
  private Drawable initializeCustomThumbDrawable(Drawable paramDrawable)
  {
    paramDrawable = paramDrawable.mutate().getConstantState().newDrawable();
    adjustCustomThumbDrawableBounds(paramDrawable);
    return paramDrawable;
  }
  
  private void invalidateTrack()
  {
    this.inactiveTrackPaint.setStrokeWidth(this.trackHeight);
    this.activeTrackPaint.setStrokeWidth(this.trackHeight);
    this.inactiveTicksPaint.setStrokeWidth(this.trackHeight / 2.0F);
    this.activeTicksPaint.setStrokeWidth(this.trackHeight / 2.0F);
  }
  
  private boolean isInVerticalScrollingContainer()
  {
    for (ViewParent localViewParent = getParent();; localViewParent = localViewParent.getParent())
    {
      boolean bool = localViewParent instanceof ViewGroup;
      int i = 0;
      if (!bool) {
        break;
      }
      ViewGroup localViewGroup = (ViewGroup)localViewParent;
      if ((localViewGroup.canScrollVertically(1)) || (localViewGroup.canScrollVertically(-1))) {
        i = 1;
      }
      if ((i != 0) && (localViewGroup.shouldDelayChildPressedState())) {
        return true;
      }
    }
    return false;
  }
  
  private boolean isMultipleOfStepSize(float paramFloat)
  {
    String str = Float.toString(paramFloat);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    BigDecimal localBigDecimal = new BigDecimal(str);
    str = Float.toString(this.stepSize);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    double d = localBigDecimal.divide(new BigDecimal(str), MathContext.DECIMAL64).doubleValue();
    boolean bool;
    if (Math.abs(Math.round(d) - d) < 1.0E-4D) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void loadResources(Resources paramResources)
  {
    this.widgetHeight = paramResources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
    int i = paramResources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
    this.minTrackSidePadding = i;
    this.trackSidePadding = i;
    this.defaultThumbRadius = paramResources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
    this.trackTop = paramResources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_top);
    this.labelPadding = paramResources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
  }
  
  private void maybeCalculateTicksCoordinates()
  {
    if (this.stepSize <= 0.0F) {
      return;
    }
    validateConfigurationIfDirty();
    int j = Math.min((int)((this.valueTo - this.valueFrom) / this.stepSize + 1.0F), this.trackWidth / (this.trackHeight * 2) + 1);
    float[] arrayOfFloat = this.ticksCoordinates;
    if ((arrayOfFloat == null) || (arrayOfFloat.length != j * 2)) {
      this.ticksCoordinates = new float[j * 2];
    }
    float f = this.trackWidth / (j - 1);
    for (int i = 0; i < j * 2; i += 2)
    {
      arrayOfFloat = this.ticksCoordinates;
      arrayOfFloat[i] = (this.trackSidePadding + i / 2 * f);
      arrayOfFloat[(i + 1)] = calculateTop();
    }
  }
  
  private void maybeDrawHalo(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    if (shouldDrawCompatHalo())
    {
      int i = (int)(this.trackSidePadding + normalizeValue(((Float)this.values.get(this.focusedThumbIdx)).floatValue()) * paramInt1);
      if (Build.VERSION.SDK_INT < 28)
      {
        paramInt1 = this.haloRadius;
        paramCanvas.clipRect(i - paramInt1, paramInt2 - paramInt1, i + paramInt1, paramInt1 + paramInt2, Region.Op.UNION);
      }
      paramCanvas.drawCircle(i, paramInt2, this.haloRadius, this.haloPaint);
    }
  }
  
  private void maybeDrawTicks(Canvas paramCanvas)
  {
    if ((this.tickVisible) && (this.stepSize > 0.0F))
    {
      float[] arrayOfFloat = getActiveRange();
      int i = pivotIndex(this.ticksCoordinates, arrayOfFloat[0]);
      int j = pivotIndex(this.ticksCoordinates, arrayOfFloat[1]);
      paramCanvas.drawPoints(this.ticksCoordinates, 0, i * 2, this.inactiveTicksPaint);
      paramCanvas.drawPoints(this.ticksCoordinates, i * 2, j * 2 - i * 2, this.activeTicksPaint);
      arrayOfFloat = this.ticksCoordinates;
      paramCanvas.drawPoints(arrayOfFloat, j * 2, arrayOfFloat.length - j * 2, this.inactiveTicksPaint);
      return;
    }
  }
  
  private void maybeIncreaseTrackSidePadding()
  {
    int i = Math.max(this.thumbRadius - this.defaultThumbRadius, 0);
    this.trackSidePadding = (this.minTrackSidePadding + i);
    if (ViewCompat.isLaidOut(this)) {
      updateTrackWidth(getWidth());
    }
  }
  
  private boolean moveFocus(int paramInt)
  {
    int i = this.focusedThumbIdx;
    paramInt = (int)MathUtils.clamp(i + paramInt, 0L, this.values.size() - 1);
    this.focusedThumbIdx = paramInt;
    if (paramInt == i) {
      return false;
    }
    if (this.activeThumbIdx != -1) {
      this.activeThumbIdx = paramInt;
    }
    updateHaloHotspot();
    postInvalidate();
    return true;
  }
  
  private boolean moveFocusInAbsoluteDirection(int paramInt)
  {
    int i = paramInt;
    if (isRtl())
    {
      if (paramInt == Integer.MIN_VALUE) {
        paramInt = Integer.MAX_VALUE;
      } else {
        paramInt = -paramInt;
      }
      i = paramInt;
    }
    return moveFocus(i);
  }
  
  private float normalizeValue(float paramFloat)
  {
    float f = this.valueFrom;
    paramFloat = (paramFloat - f) / (this.valueTo - f);
    if (isRtl()) {
      return 1.0F - paramFloat;
    }
    return paramFloat;
  }
  
  private Boolean onKeyDownNoActiveThumb(int paramInt, KeyEvent paramKeyEvent)
  {
    Boolean localBoolean = Boolean.valueOf(true);
    switch (paramInt)
    {
    default: 
      return null;
    case 70: 
    case 81: 
      moveFocus(1);
      return localBoolean;
    case 69: 
      moveFocus(-1);
      return localBoolean;
    case 61: 
      if (paramKeyEvent.hasNoModifiers()) {
        return Boolean.valueOf(moveFocus(1));
      }
      if (paramKeyEvent.isShiftPressed()) {
        return Boolean.valueOf(moveFocus(-1));
      }
      return Boolean.valueOf(false);
    case 23: 
    case 66: 
      this.activeThumbIdx = this.focusedThumbIdx;
      postInvalidate();
      return localBoolean;
    case 22: 
      moveFocusInAbsoluteDirection(1);
      return localBoolean;
    }
    moveFocusInAbsoluteDirection(-1);
    return localBoolean;
  }
  
  private void onStartTrackingTouch()
  {
    Iterator localIterator = this.touchListeners.iterator();
    while (localIterator.hasNext()) {
      ((BaseOnSliderTouchListener)localIterator.next()).onStartTrackingTouch(this);
    }
  }
  
  private void onStopTrackingTouch()
  {
    Iterator localIterator = this.touchListeners.iterator();
    while (localIterator.hasNext()) {
      ((BaseOnSliderTouchListener)localIterator.next()).onStopTrackingTouch(this);
    }
  }
  
  private static TooltipDrawable parseLabelDrawable(Context paramContext, TypedArray paramTypedArray)
  {
    return TooltipDrawable.createFromAttributes(paramContext, null, 0, paramTypedArray.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip));
  }
  
  private static int pivotIndex(float[] paramArrayOfFloat, float paramFloat)
  {
    return Math.round((paramArrayOfFloat.length / 2 - 1) * paramFloat);
  }
  
  private void processAttributes(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.Slider, paramInt, DEF_STYLE_RES, new int[0]);
    this.valueFrom = localTypedArray.getFloat(R.styleable.Slider_android_valueFrom, 0.0F);
    this.valueTo = localTypedArray.getFloat(R.styleable.Slider_android_valueTo, 1.0F);
    setValues(new Float[] { Float.valueOf(this.valueFrom) });
    this.stepSize = localTypedArray.getFloat(R.styleable.Slider_android_stepSize, 0.0F);
    boolean bool = localTypedArray.hasValue(R.styleable.Slider_trackColor);
    if (bool) {
      paramInt = R.styleable.Slider_trackColor;
    } else {
      paramInt = R.styleable.Slider_trackColorInactive;
    }
    int i;
    if (bool) {
      i = R.styleable.Slider_trackColor;
    } else {
      i = R.styleable.Slider_trackColorActive;
    }
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, paramInt);
    if (paramAttributeSet == null) {
      paramAttributeSet = AppCompatResources.getColorStateList(paramContext, R.color.material_slider_inactive_track_color);
    }
    setTrackInactiveTintList(paramAttributeSet);
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, i);
    if (paramAttributeSet == null) {
      paramAttributeSet = AppCompatResources.getColorStateList(paramContext, R.color.material_slider_active_track_color);
    }
    setTrackActiveTintList(paramAttributeSet);
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.Slider_thumbColor);
    this.defaultThumbDrawable.setFillColor(paramAttributeSet);
    if (localTypedArray.hasValue(R.styleable.Slider_thumbStrokeColor)) {
      setThumbStrokeColor(MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.Slider_thumbStrokeColor));
    }
    setThumbStrokeWidth(localTypedArray.getDimension(R.styleable.Slider_thumbStrokeWidth, 0.0F));
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.Slider_haloColor);
    if (paramAttributeSet == null) {
      paramAttributeSet = AppCompatResources.getColorStateList(paramContext, R.color.material_slider_halo_color);
    }
    setHaloTintList(paramAttributeSet);
    this.tickVisible = localTypedArray.getBoolean(R.styleable.Slider_tickVisible, true);
    bool = localTypedArray.hasValue(R.styleable.Slider_tickColor);
    if (bool) {
      paramInt = R.styleable.Slider_tickColor;
    } else {
      paramInt = R.styleable.Slider_tickColorInactive;
    }
    if (bool) {
      i = R.styleable.Slider_tickColor;
    } else {
      i = R.styleable.Slider_tickColorActive;
    }
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, paramInt);
    if (paramAttributeSet == null) {
      paramAttributeSet = AppCompatResources.getColorStateList(paramContext, R.color.material_slider_inactive_tick_marks_color);
    }
    setTickInactiveTintList(paramAttributeSet);
    paramAttributeSet = MaterialResources.getColorStateList(paramContext, localTypedArray, i);
    if (paramAttributeSet != null) {
      paramContext = paramAttributeSet;
    } else {
      paramContext = AppCompatResources.getColorStateList(paramContext, R.color.material_slider_active_tick_marks_color);
    }
    setTickActiveTintList(paramContext);
    setThumbRadius(localTypedArray.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0));
    setHaloRadius(localTypedArray.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
    setThumbElevation(localTypedArray.getDimension(R.styleable.Slider_thumbElevation, 0.0F));
    setTrackHeight(localTypedArray.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
    setLabelBehavior(localTypedArray.getInt(R.styleable.Slider_labelBehavior, 0));
    if (!localTypedArray.getBoolean(R.styleable.Slider_android_enabled, true)) {
      setEnabled(false);
    }
    localTypedArray.recycle();
  }
  
  private void scheduleAccessibilityEventSender(int paramInt)
  {
    AccessibilityEventSender localAccessibilityEventSender = this.accessibilityEventSender;
    if (localAccessibilityEventSender == null) {
      this.accessibilityEventSender = new AccessibilityEventSender(null);
    } else {
      removeCallbacks(localAccessibilityEventSender);
    }
    this.accessibilityEventSender.setVirtualViewId(paramInt);
    postDelayed(this.accessibilityEventSender, 200L);
  }
  
  private void setValueForLabel(TooltipDrawable paramTooltipDrawable, float paramFloat)
  {
    paramTooltipDrawable.setText(formatValue(paramFloat));
    int i = this.trackSidePadding + (int)(normalizeValue(paramFloat) * this.trackWidth) - paramTooltipDrawable.getIntrinsicWidth() / 2;
    int j = calculateTop() - (this.labelPadding + this.thumbRadius);
    paramTooltipDrawable.setBounds(i, j - paramTooltipDrawable.getIntrinsicHeight(), paramTooltipDrawable.getIntrinsicWidth() + i, j);
    Rect localRect = new Rect(paramTooltipDrawable.getBounds());
    DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, localRect);
    paramTooltipDrawable.setBounds(localRect);
    ViewUtils.getContentViewOverlay(this).add(paramTooltipDrawable);
  }
  
  private void setValuesInternal(ArrayList<Float> paramArrayList)
  {
    if (!paramArrayList.isEmpty())
    {
      Collections.sort(paramArrayList);
      if ((this.values.size() == paramArrayList.size()) && (this.values.equals(paramArrayList))) {
        return;
      }
      this.values = paramArrayList;
      this.dirtyConfig = true;
      this.focusedThumbIdx = 0;
      updateHaloHotspot();
      createLabelPool();
      dispatchOnChangedProgrammatically();
      postInvalidate();
      return;
    }
    throw new IllegalArgumentException("At least one value must be set");
  }
  
  private boolean shouldAlwaysShowLabel()
  {
    boolean bool;
    if (this.labelBehavior == 3) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldDrawCompatHalo()
  {
    boolean bool;
    if ((!this.forceDrawCompatHalo) && (Build.VERSION.SDK_INT >= 21) && ((getBackground() instanceof RippleDrawable))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean snapActiveThumbToValue(float paramFloat)
  {
    return snapThumbToValue(this.activeThumbIdx, paramFloat);
  }
  
  private double snapPosition(float paramFloat)
  {
    float f = this.stepSize;
    if (f > 0.0F)
    {
      int i = (int)((this.valueTo - this.valueFrom) / f);
      return Math.round(i * paramFloat) / i;
    }
    return paramFloat;
  }
  
  private boolean snapThumbToValue(int paramInt, float paramFloat)
  {
    this.focusedThumbIdx = paramInt;
    if (Math.abs(paramFloat - ((Float)this.values.get(paramInt)).floatValue()) < 1.0E-4D) {
      return false;
    }
    paramFloat = getClampedValue(paramInt, paramFloat);
    this.values.set(paramInt, Float.valueOf(paramFloat));
    dispatchOnChangedFromUser(paramInt);
    return true;
  }
  
  private boolean snapTouchPosition()
  {
    return snapActiveThumbToValue(getValueOfTouchPosition());
  }
  
  private void updateHaloHotspot()
  {
    if ((!shouldDrawCompatHalo()) && (getMeasuredWidth() > 0))
    {
      Drawable localDrawable = getBackground();
      if ((localDrawable instanceof RippleDrawable))
      {
        int i = (int)(normalizeValue(((Float)this.values.get(this.focusedThumbIdx)).floatValue()) * this.trackWidth + this.trackSidePadding);
        int k = calculateTop();
        int j = this.haloRadius;
        DrawableCompat.setHotspotBounds(localDrawable, i - j, k - j, i + j, j + k);
      }
    }
  }
  
  private void updateTrackWidth(int paramInt)
  {
    this.trackWidth = Math.max(paramInt - this.trackSidePadding * 2, 0);
    maybeCalculateTicksCoordinates();
  }
  
  private void validateConfigurationIfDirty()
  {
    if (this.dirtyConfig)
    {
      validateValueFrom();
      validateValueTo();
      validateStepSize();
      validateValues();
      validateMinSeparation();
      warnAboutFloatingPointError();
      this.dirtyConfig = false;
    }
  }
  
  private void validateMinSeparation()
  {
    float f2 = getMinSeparation();
    if (f2 >= 0.0F)
    {
      float f1 = this.stepSize;
      if ((f1 > 0.0F) && (f2 > 0.0F)) {
        if (this.separationUnit == 1)
        {
          if ((f2 < f1) || (!isMultipleOfStepSize(f2)))
          {
            str = String.format("minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)", new Object[] { Float.valueOf(f2), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize) });
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            throw new IllegalStateException(str);
          }
        }
        else
        {
          str = String.format("minSeparation(%s) cannot be set as a dimension when using stepSize(%s)", new Object[] { Float.valueOf(f2), Float.valueOf(this.stepSize) });
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          throw new IllegalStateException(str);
        }
      }
      return;
    }
    String str = String.format("minSeparation(%s) must be greater or equal to 0", new Object[] { Float.valueOf(f2) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalStateException(str);
  }
  
  private void validateStepSize()
  {
    if ((this.stepSize > 0.0F) && (!valueLandsOnTick(this.valueTo)))
    {
      String str = String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", new Object[] { Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      throw new IllegalStateException(str);
    }
  }
  
  private void validateValueFrom()
  {
    if (this.valueFrom < this.valueTo) {
      return;
    }
    String str = String.format("valueFrom(%s) must be smaller than valueTo(%s)", new Object[] { Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalStateException(str);
  }
  
  private void validateValueTo()
  {
    if (this.valueTo > this.valueFrom) {
      return;
    }
    String str = String.format("valueTo(%s) must be greater than valueFrom(%s)", new Object[] { Float.valueOf(this.valueTo), Float.valueOf(this.valueFrom) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalStateException(str);
  }
  
  private void validateValues()
  {
    Iterator localIterator = this.values.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Float)localIterator.next();
      if ((((Float)localObject).floatValue() >= this.valueFrom) && (((Float)localObject).floatValue() <= this.valueTo))
      {
        if ((this.stepSize > 0.0F) && (!valueLandsOnTick(((Float)localObject).floatValue())))
        {
          localObject = String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", new Object[] { localObject, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize) });
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          throw new IllegalStateException((String)localObject);
        }
      }
      else
      {
        localObject = String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", new Object[] { localObject, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo) });
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        throw new IllegalStateException((String)localObject);
      }
    }
  }
  
  private boolean valueLandsOnTick(float paramFloat)
  {
    return isMultipleOfStepSize(paramFloat - this.valueFrom);
  }
  
  private float valueToX(float paramFloat)
  {
    return normalizeValue(paramFloat) * this.trackWidth + this.trackSidePadding;
  }
  
  private void warnAboutFloatingPointError()
  {
    float f = this.stepSize;
    if (f == 0.0F) {
      return;
    }
    String str2;
    String str1;
    if ((int)f != f)
    {
      str2 = TAG;
      str1 = String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[] { "stepSize", Float.valueOf(f) });
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      Log.w(str2, str1);
    }
    f = this.valueFrom;
    if ((int)f != f)
    {
      str2 = TAG;
      str1 = String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[] { "valueFrom", Float.valueOf(f) });
      Log5ECF72.a(str1);
      LogE84000.a(str1);
      Log229316.a(str1);
      Log.w(str2, str1);
    }
    f = this.valueTo;
    if ((int)f != f)
    {
      str1 = TAG;
      str2 = String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", new Object[] { "valueTo", Float.valueOf(f) });
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      Log.w(str1, str2);
    }
  }
  
  public void addOnChangeListener(L paramL)
  {
    this.changeListeners.add(paramL);
  }
  
  public void addOnSliderTouchListener(T paramT)
  {
    this.touchListeners.add(paramT);
  }
  
  public void clearOnChangeListeners()
  {
    this.changeListeners.clear();
  }
  
  public void clearOnSliderTouchListeners()
  {
    this.touchListeners.clear();
  }
  
  public boolean dispatchHoverEvent(MotionEvent paramMotionEvent)
  {
    boolean bool;
    if ((!this.accessibilityHelper.dispatchHoverEvent(paramMotionEvent)) && (!super.dispatchHoverEvent(paramMotionEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
    this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
    this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
    this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
    Iterator localIterator = this.labels.iterator();
    while (localIterator.hasNext())
    {
      TooltipDrawable localTooltipDrawable = (TooltipDrawable)localIterator.next();
      if (localTooltipDrawable.isStateful()) {
        localTooltipDrawable.setState(getDrawableState());
      }
    }
    if (this.defaultThumbDrawable.isStateful()) {
      this.defaultThumbDrawable.setState(getDrawableState());
    }
    this.haloPaint.setColor(getColorForState(this.haloColor));
    this.haloPaint.setAlpha(63);
  }
  
  void forceDrawCompatHalo(boolean paramBoolean)
  {
    this.forceDrawCompatHalo = paramBoolean;
  }
  
  public CharSequence getAccessibilityClassName()
  {
    return SeekBar.class.getName();
  }
  
  final int getAccessibilityFocusedVirtualViewId()
  {
    return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
  }
  
  public int getActiveThumbIndex()
  {
    return this.activeThumbIdx;
  }
  
  public int getFocusedThumbIndex()
  {
    return this.focusedThumbIdx;
  }
  
  public int getHaloRadius()
  {
    return this.haloRadius;
  }
  
  public ColorStateList getHaloTintList()
  {
    return this.haloColor;
  }
  
  public int getLabelBehavior()
  {
    return this.labelBehavior;
  }
  
  protected float getMinSeparation()
  {
    return 0.0F;
  }
  
  public float getStepSize()
  {
    return this.stepSize;
  }
  
  public float getThumbElevation()
  {
    return this.defaultThumbDrawable.getElevation();
  }
  
  public int getThumbRadius()
  {
    return this.thumbRadius;
  }
  
  public ColorStateList getThumbStrokeColor()
  {
    return this.defaultThumbDrawable.getStrokeColor();
  }
  
  public float getThumbStrokeWidth()
  {
    return this.defaultThumbDrawable.getStrokeWidth();
  }
  
  public ColorStateList getThumbTintList()
  {
    return this.defaultThumbDrawable.getFillColor();
  }
  
  public ColorStateList getTickActiveTintList()
  {
    return this.tickColorActive;
  }
  
  public ColorStateList getTickInactiveTintList()
  {
    return this.tickColorInactive;
  }
  
  public ColorStateList getTickTintList()
  {
    if (this.tickColorInactive.equals(this.tickColorActive)) {
      return this.tickColorActive;
    }
    throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
  }
  
  public ColorStateList getTrackActiveTintList()
  {
    return this.trackColorActive;
  }
  
  public int getTrackHeight()
  {
    return this.trackHeight;
  }
  
  public ColorStateList getTrackInactiveTintList()
  {
    return this.trackColorInactive;
  }
  
  public int getTrackSidePadding()
  {
    return this.trackSidePadding;
  }
  
  public ColorStateList getTrackTintList()
  {
    if (this.trackColorInactive.equals(this.trackColorActive)) {
      return this.trackColorActive;
    }
    throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
  }
  
  public int getTrackWidth()
  {
    return this.trackWidth;
  }
  
  public float getValueFrom()
  {
    return this.valueFrom;
  }
  
  public float getValueTo()
  {
    return this.valueTo;
  }
  
  List<Float> getValues()
  {
    return new ArrayList(this.values);
  }
  
  public boolean hasLabelFormatter()
  {
    boolean bool;
    if (this.formatter != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  final boolean isRtl()
  {
    int i = ViewCompat.getLayoutDirection(this);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isTickVisible()
  {
    return this.tickVisible;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Iterator localIterator = this.labels.iterator();
    while (localIterator.hasNext()) {
      attachLabelToContentView((TooltipDrawable)localIterator.next());
    }
  }
  
  protected void onDetachedFromWindow()
  {
    Object localObject = this.accessibilityEventSender;
    if (localObject != null) {
      removeCallbacks((Runnable)localObject);
    }
    this.labelsAreAnimatedIn = false;
    localObject = this.labels.iterator();
    while (((Iterator)localObject).hasNext()) {
      detachLabelFromContentView((TooltipDrawable)((Iterator)localObject).next());
    }
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.dirtyConfig)
    {
      validateConfigurationIfDirty();
      maybeCalculateTicksCoordinates();
    }
    super.onDraw(paramCanvas);
    int i = calculateTop();
    drawInactiveTrack(paramCanvas, this.trackWidth, i);
    if (((Float)Collections.max(getValues())).floatValue() > this.valueFrom) {
      drawActiveTrack(paramCanvas, this.trackWidth, i);
    }
    maybeDrawTicks(paramCanvas);
    if (((this.thumbIsPressed) || (isFocused()) || (shouldAlwaysShowLabel())) && (isEnabled()))
    {
      maybeDrawHalo(paramCanvas, this.trackWidth, i);
      if ((this.activeThumbIdx == -1) && (!shouldAlwaysShowLabel())) {
        ensureLabelsRemoved();
      } else {
        ensureLabelsAdded();
      }
    }
    else
    {
      ensureLabelsRemoved();
    }
    drawThumbs(paramCanvas, this.trackWidth, i);
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (!paramBoolean)
    {
      this.activeThumbIdx = -1;
      this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }
    else
    {
      focusThumbOnFocusGained(paramInt);
      this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!isEnabled()) {
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    if (this.values.size() == 1) {
      this.activeThumbIdx = 0;
    }
    if (this.activeThumbIdx == -1)
    {
      localObject = onKeyDownNoActiveThumb(paramInt, paramKeyEvent);
      boolean bool;
      if (localObject != null) {
        bool = ((Boolean)localObject).booleanValue();
      } else {
        bool = super.onKeyDown(paramInt, paramKeyEvent);
      }
      return bool;
    }
    this.isLongPress |= paramKeyEvent.isLongPress();
    Object localObject = calculateIncrementForKey(paramInt);
    if (localObject != null)
    {
      if (snapActiveThumbToValue(((Float)this.values.get(this.activeThumbIdx)).floatValue() + ((Float)localObject).floatValue()))
      {
        updateHaloHotspot();
        postInvalidate();
      }
      return true;
    }
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    case 61: 
      if (paramKeyEvent.hasNoModifiers()) {
        return moveFocus(1);
      }
      if (paramKeyEvent.isShiftPressed()) {
        return moveFocus(-1);
      }
      return false;
    }
    this.activeThumbIdx = -1;
    postInvalidate();
    return true;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    this.isLongPress = false;
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = this.widgetHeight;
    int j = this.labelBehavior;
    paramInt2 = 0;
    if ((j != 1) && (!shouldAlwaysShowLabel())) {
      break label49;
    }
    paramInt2 = ((TooltipDrawable)this.labels.get(0)).getIntrinsicHeight();
    label49:
    super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(i + paramInt2, 1073741824));
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (SliderState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.valueFrom = paramParcelable.valueFrom;
    this.valueTo = paramParcelable.valueTo;
    setValuesInternal(paramParcelable.values);
    this.stepSize = paramParcelable.stepSize;
    if (paramParcelable.hasFocus) {
      requestFocus();
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SliderState localSliderState = new SliderState(super.onSaveInstanceState());
    localSliderState.valueFrom = this.valueFrom;
    localSliderState.valueTo = this.valueTo;
    localSliderState.values = new ArrayList(this.values);
    localSliderState.stepSize = this.stepSize;
    localSliderState.hasFocus = hasFocus();
    return localSliderState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    updateTrackWidth(paramInt1);
    updateHaloHotspot();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!isEnabled()) {
      return false;
    }
    float f1 = paramMotionEvent.getX();
    float f2 = (f1 - this.trackSidePadding) / this.trackWidth;
    this.touchPosition = f2;
    f2 = Math.max(0.0F, f2);
    this.touchPosition = f2;
    this.touchPosition = Math.min(1.0F, f2);
    switch (paramMotionEvent.getActionMasked())
    {
    default: 
      break;
    case 2: 
      if (!this.thumbIsPressed)
      {
        if ((isInVerticalScrollingContainer()) && (Math.abs(f1 - this.touchDownX) < this.scaledTouchSlop)) {
          return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        onStartTrackingTouch();
      }
      if (pickActiveThumb())
      {
        this.thumbIsPressed = true;
        snapTouchPosition();
        updateHaloHotspot();
        invalidate();
      }
      break;
    case 1: 
      this.thumbIsPressed = false;
      MotionEvent localMotionEvent = this.lastEvent;
      if ((localMotionEvent != null) && (localMotionEvent.getActionMasked() == 0) && (Math.abs(this.lastEvent.getX() - paramMotionEvent.getX()) <= this.scaledTouchSlop) && (Math.abs(this.lastEvent.getY() - paramMotionEvent.getY()) <= this.scaledTouchSlop) && (pickActiveThumb())) {
        onStartTrackingTouch();
      }
      if (this.activeThumbIdx != -1)
      {
        snapTouchPosition();
        this.activeThumbIdx = -1;
        onStopTrackingTouch();
      }
      invalidate();
      break;
    case 0: 
      this.touchDownX = f1;
      if (!isInVerticalScrollingContainer())
      {
        getParent().requestDisallowInterceptTouchEvent(true);
        if (pickActiveThumb())
        {
          requestFocus();
          this.thumbIsPressed = true;
          snapTouchPosition();
          updateHaloHotspot();
          invalidate();
          onStartTrackingTouch();
        }
      }
      break;
    }
    setPressed(this.thumbIsPressed);
    this.lastEvent = MotionEvent.obtain(paramMotionEvent);
    return true;
  }
  
  protected boolean pickActiveThumb()
  {
    int i = this.activeThumbIdx;
    boolean bool = true;
    if (i != -1) {
      return true;
    }
    float f4 = getValueOfTouchPositionAbsolute();
    float f5 = valueToX(f4);
    this.activeThumbIdx = 0;
    float f2 = Math.abs(((Float)this.values.get(0)).floatValue() - f4);
    i = 1;
    while (i < this.values.size())
    {
      float f3 = Math.abs(((Float)this.values.get(i)).floatValue() - f4);
      float f6 = valueToX(((Float)this.values.get(i)).floatValue());
      if (Float.compare(f3, f2) > 1) {
        break;
      }
      int j;
      if (isRtl() ? f6 - f5 > 0.0F : f6 - f5 < 0.0F) {
        j = 1;
      } else {
        j = 0;
      }
      float f1;
      if (Float.compare(f3, f2) < 0)
      {
        f1 = f3;
        this.activeThumbIdx = i;
      }
      else
      {
        f1 = f2;
        if (Float.compare(f3, f2) == 0)
        {
          if (Math.abs(f6 - f5) < this.scaledTouchSlop)
          {
            this.activeThumbIdx = -1;
            return false;
          }
          f1 = f2;
          if (j != 0)
          {
            f1 = f3;
            this.activeThumbIdx = i;
          }
        }
      }
      i++;
      f2 = f1;
    }
    if (this.activeThumbIdx == -1) {
      bool = false;
    }
    return bool;
  }
  
  public void removeOnChangeListener(L paramL)
  {
    this.changeListeners.remove(paramL);
  }
  
  public void removeOnSliderTouchListener(T paramT)
  {
    this.touchListeners.remove(paramT);
  }
  
  protected void setActiveThumbIndex(int paramInt)
  {
    this.activeThumbIdx = paramInt;
  }
  
  void setCustomThumbDrawable(int paramInt)
  {
    setCustomThumbDrawable(getResources().getDrawable(paramInt));
  }
  
  void setCustomThumbDrawable(Drawable paramDrawable)
  {
    this.customThumbDrawable = initializeCustomThumbDrawable(paramDrawable);
    this.customThumbDrawablesForValues.clear();
    postInvalidate();
  }
  
  void setCustomThumbDrawablesForValues(int... paramVarArgs)
  {
    Drawable[] arrayOfDrawable = new Drawable[paramVarArgs.length];
    for (int i = 0; i < paramVarArgs.length; i++) {
      arrayOfDrawable[i] = getResources().getDrawable(paramVarArgs[i]);
    }
    setCustomThumbDrawablesForValues(arrayOfDrawable);
  }
  
  void setCustomThumbDrawablesForValues(Drawable... paramVarArgs)
  {
    this.customThumbDrawable = null;
    this.customThumbDrawablesForValues = new ArrayList();
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++)
    {
      Drawable localDrawable = paramVarArgs[i];
      this.customThumbDrawablesForValues.add(initializeCustomThumbDrawable(localDrawable));
    }
    postInvalidate();
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 2;
    }
    setLayerType(i, null);
  }
  
  public void setFocusedThumbIndex(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.values.size()))
    {
      this.focusedThumbIdx = paramInt;
      this.accessibilityHelper.requestKeyboardFocusForVirtualView(paramInt);
      postInvalidate();
      return;
    }
    throw new IllegalArgumentException("index out of range");
  }
  
  public void setHaloRadius(int paramInt)
  {
    if (paramInt == this.haloRadius) {
      return;
    }
    this.haloRadius = paramInt;
    Drawable localDrawable = getBackground();
    if ((!shouldDrawCompatHalo()) && ((localDrawable instanceof RippleDrawable)))
    {
      DrawableUtils.setRippleDrawableRadius((RippleDrawable)localDrawable, this.haloRadius);
      return;
    }
    postInvalidate();
  }
  
  public void setHaloRadiusResource(int paramInt)
  {
    setHaloRadius(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setHaloTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.haloColor)) {
      return;
    }
    this.haloColor = paramColorStateList;
    Drawable localDrawable = getBackground();
    if ((!shouldDrawCompatHalo()) && ((localDrawable instanceof RippleDrawable)))
    {
      ((RippleDrawable)localDrawable).setColor(paramColorStateList);
      return;
    }
    this.haloPaint.setColor(getColorForState(paramColorStateList));
    this.haloPaint.setAlpha(63);
    invalidate();
  }
  
  public void setLabelBehavior(int paramInt)
  {
    if (this.labelBehavior != paramInt)
    {
      this.labelBehavior = paramInt;
      requestLayout();
    }
  }
  
  public void setLabelFormatter(LabelFormatter paramLabelFormatter)
  {
    this.formatter = paramLabelFormatter;
  }
  
  protected void setSeparationUnit(int paramInt)
  {
    this.separationUnit = paramInt;
    this.dirtyConfig = true;
    postInvalidate();
  }
  
  public void setStepSize(float paramFloat)
  {
    if (paramFloat >= 0.0F)
    {
      if (this.stepSize != paramFloat)
      {
        this.stepSize = paramFloat;
        this.dirtyConfig = true;
        postInvalidate();
      }
      return;
    }
    String str = String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", new Object[] { Float.valueOf(paramFloat), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str);
  }
  
  public void setThumbElevation(float paramFloat)
  {
    this.defaultThumbDrawable.setElevation(paramFloat);
  }
  
  public void setThumbElevationResource(int paramInt)
  {
    setThumbElevation(getResources().getDimension(paramInt));
  }
  
  public void setThumbRadius(int paramInt)
  {
    if (paramInt == this.thumbRadius) {
      return;
    }
    this.thumbRadius = paramInt;
    maybeIncreaseTrackSidePadding();
    this.defaultThumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, this.thumbRadius).build());
    Object localObject = this.defaultThumbDrawable;
    paramInt = this.thumbRadius;
    ((MaterialShapeDrawable)localObject).setBounds(0, 0, paramInt * 2, paramInt * 2);
    localObject = this.customThumbDrawable;
    if (localObject != null) {
      adjustCustomThumbDrawableBounds((Drawable)localObject);
    }
    localObject = this.customThumbDrawablesForValues.iterator();
    while (((Iterator)localObject).hasNext()) {
      adjustCustomThumbDrawableBounds((Drawable)((Iterator)localObject).next());
    }
    postInvalidate();
  }
  
  public void setThumbRadiusResource(int paramInt)
  {
    setThumbRadius(getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setThumbStrokeColor(ColorStateList paramColorStateList)
  {
    this.defaultThumbDrawable.setStrokeColor(paramColorStateList);
    postInvalidate();
  }
  
  public void setThumbStrokeColorResource(int paramInt)
  {
    if (paramInt != 0) {
      setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), paramInt));
    }
  }
  
  public void setThumbStrokeWidth(float paramFloat)
  {
    this.defaultThumbDrawable.setStrokeWidth(paramFloat);
    postInvalidate();
  }
  
  public void setThumbStrokeWidthResource(int paramInt)
  {
    if (paramInt != 0) {
      setThumbStrokeWidth(getResources().getDimension(paramInt));
    }
  }
  
  public void setThumbTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.defaultThumbDrawable.getFillColor())) {
      return;
    }
    this.defaultThumbDrawable.setFillColor(paramColorStateList);
    invalidate();
  }
  
  public void setTickActiveTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.tickColorActive)) {
      return;
    }
    this.tickColorActive = paramColorStateList;
    this.activeTicksPaint.setColor(getColorForState(paramColorStateList));
    invalidate();
  }
  
  public void setTickInactiveTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.tickColorInactive)) {
      return;
    }
    this.tickColorInactive = paramColorStateList;
    this.inactiveTicksPaint.setColor(getColorForState(paramColorStateList));
    invalidate();
  }
  
  public void setTickTintList(ColorStateList paramColorStateList)
  {
    setTickInactiveTintList(paramColorStateList);
    setTickActiveTintList(paramColorStateList);
  }
  
  public void setTickVisible(boolean paramBoolean)
  {
    if (this.tickVisible != paramBoolean)
    {
      this.tickVisible = paramBoolean;
      postInvalidate();
    }
  }
  
  public void setTrackActiveTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.trackColorActive)) {
      return;
    }
    this.trackColorActive = paramColorStateList;
    this.activeTrackPaint.setColor(getColorForState(paramColorStateList));
    invalidate();
  }
  
  public void setTrackHeight(int paramInt)
  {
    if (this.trackHeight != paramInt)
    {
      this.trackHeight = paramInt;
      invalidateTrack();
      postInvalidate();
    }
  }
  
  public void setTrackInactiveTintList(ColorStateList paramColorStateList)
  {
    if (paramColorStateList.equals(this.trackColorInactive)) {
      return;
    }
    this.trackColorInactive = paramColorStateList;
    this.inactiveTrackPaint.setColor(getColorForState(paramColorStateList));
    invalidate();
  }
  
  public void setTrackTintList(ColorStateList paramColorStateList)
  {
    setTrackInactiveTintList(paramColorStateList);
    setTrackActiveTintList(paramColorStateList);
  }
  
  public void setValueFrom(float paramFloat)
  {
    this.valueFrom = paramFloat;
    this.dirtyConfig = true;
    postInvalidate();
  }
  
  public void setValueTo(float paramFloat)
  {
    this.valueTo = paramFloat;
    this.dirtyConfig = true;
    postInvalidate();
  }
  
  void setValues(List<Float> paramList)
  {
    setValuesInternal(new ArrayList(paramList));
  }
  
  void setValues(Float... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    Collections.addAll(localArrayList, paramVarArgs);
    setValuesInternal(localArrayList);
  }
  
  void updateBoundsForVirturalViewId(int paramInt, Rect paramRect)
  {
    int j = this.trackSidePadding + (int)(normalizeValue(((Float)getValues().get(paramInt)).floatValue()) * this.trackWidth);
    paramInt = calculateTop();
    int i = this.thumbRadius;
    paramRect.set(j - i, paramInt - i, j + i, i + paramInt);
  }
  
  private class AccessibilityEventSender
    implements Runnable
  {
    int virtualViewId = -1;
    
    private AccessibilityEventSender() {}
    
    public void run()
    {
      BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
    }
    
    void setVirtualViewId(int paramInt)
    {
      this.virtualViewId = paramInt;
    }
  }
  
  private static class AccessibilityHelper
    extends ExploreByTouchHelper
  {
    private final BaseSlider<?, ?, ?> slider;
    final Rect virtualViewBounds = new Rect();
    
    AccessibilityHelper(BaseSlider<?, ?, ?> paramBaseSlider)
    {
      super();
      this.slider = paramBaseSlider;
    }
    
    private String startOrEndDescription(int paramInt)
    {
      if (paramInt == this.slider.getValues().size() - 1) {
        return this.slider.getContext().getString(R.string.material_slider_range_end);
      }
      if (paramInt == 0) {
        return this.slider.getContext().getString(R.string.material_slider_range_start);
      }
      return "";
    }
    
    protected int getVirtualViewAt(float paramFloat1, float paramFloat2)
    {
      for (int i = 0; i < this.slider.getValues().size(); i++)
      {
        this.slider.updateBoundsForVirturalViewId(i, this.virtualViewBounds);
        if (this.virtualViewBounds.contains((int)paramFloat1, (int)paramFloat2)) {
          return i;
        }
      }
      return -1;
    }
    
    protected void getVisibleVirtualViews(List<Integer> paramList)
    {
      for (int i = 0; i < this.slider.getValues().size(); i++) {
        paramList.add(Integer.valueOf(i));
      }
    }
    
    protected boolean onPerformActionForVirtualView(int paramInt1, int paramInt2, Bundle paramBundle)
    {
      if (!this.slider.isEnabled()) {
        return false;
      }
      switch (paramInt2)
      {
      default: 
        return false;
      case 16908349: 
        if ((paramBundle != null) && (paramBundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")))
        {
          f1 = paramBundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
          if (this.slider.snapThumbToValue(paramInt1, f1))
          {
            this.slider.updateHaloHotspot();
            this.slider.postInvalidate();
            invalidateVirtualView(paramInt1);
            return true;
          }
          return false;
        }
        return false;
      }
      float f2 = this.slider.calculateStepIncrement(20);
      float f1 = f2;
      if (paramInt2 == 8192) {
        f1 = -f2;
      }
      f2 = f1;
      if (this.slider.isRtl()) {
        f2 = -f1;
      }
      paramBundle = this.slider.getValues();
      f1 = MathUtils.clamp(((Float)paramBundle.get(paramInt1)).floatValue() + f2, this.slider.getValueFrom(), this.slider.getValueTo());
      if (this.slider.snapThumbToValue(paramInt1, f1))
      {
        this.slider.updateHaloHotspot();
        this.slider.postInvalidate();
        invalidateVirtualView(paramInt1);
        return true;
      }
      return false;
    }
    
    protected void onPopulateNodeForVirtualView(int paramInt, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
      Object localObject = this.slider.getValues();
      float f1 = ((Float)((List)localObject).get(paramInt)).floatValue();
      float f2 = this.slider.getValueFrom();
      float f3 = this.slider.getValueTo();
      if (this.slider.isEnabled())
      {
        if (f1 > f2) {
          paramAccessibilityNodeInfoCompat.addAction(8192);
        }
        if (f1 < f3) {
          paramAccessibilityNodeInfoCompat.addAction(4096);
        }
      }
      paramAccessibilityNodeInfoCompat.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, f2, f3, f1));
      paramAccessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
      StringBuilder localStringBuilder = new StringBuilder();
      if (this.slider.getContentDescription() != null) {
        localStringBuilder.append(this.slider.getContentDescription()).append(",");
      }
      if (((List)localObject).size() > 1)
      {
        localStringBuilder.append(startOrEndDescription(paramInt));
        localObject = this.slider.formatValue(f1);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        localStringBuilder.append((String)localObject);
      }
      paramAccessibilityNodeInfoCompat.setContentDescription(localStringBuilder.toString());
      this.slider.updateBoundsForVirturalViewId(paramInt, this.virtualViewBounds);
      paramAccessibilityNodeInfoCompat.setBoundsInParent(this.virtualViewBounds);
    }
  }
  
  static class SliderState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator()
    {
      public BaseSlider.SliderState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BaseSlider.SliderState(paramAnonymousParcel, null);
      }
      
      public BaseSlider.SliderState[] newArray(int paramAnonymousInt)
      {
        return new BaseSlider.SliderState[paramAnonymousInt];
      }
    };
    boolean hasFocus;
    float stepSize;
    float valueFrom;
    float valueTo;
    ArrayList<Float> values;
    
    private SliderState(Parcel paramParcel)
    {
      super();
      this.valueFrom = paramParcel.readFloat();
      this.valueTo = paramParcel.readFloat();
      ArrayList localArrayList = new ArrayList();
      this.values = localArrayList;
      paramParcel.readList(localArrayList, Float.class.getClassLoader());
      this.stepSize = paramParcel.readFloat();
      this.hasFocus = paramParcel.createBooleanArray()[0];
    }
    
    SliderState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeFloat(this.valueFrom);
      paramParcel.writeFloat(this.valueTo);
      paramParcel.writeList(this.values);
      paramParcel.writeFloat(this.stepSize);
      paramParcel.writeBooleanArray(new boolean[] { this.hasFocus });
    }
  }
  
  private static abstract interface TooltipDrawableFactory
  {
    public abstract TooltipDrawable createTooltipDrawable();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/slider/BaseSlider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */