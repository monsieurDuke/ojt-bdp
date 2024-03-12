package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.text.BidiFormatter;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ChipDrawable
  extends MaterialShapeDrawable
  implements TintAwareDrawable, Drawable.Callback, TextDrawableHelper.TextDrawableDelegate
{
  private static final boolean DEBUG = false;
  private static final int[] DEFAULT_STATE = { 16842910 };
  private static final int MAX_CHIP_ICON_HEIGHT = 24;
  private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
  private static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
  private int alpha = 255;
  private boolean checkable;
  private Drawable checkedIcon;
  private ColorStateList checkedIconTint;
  private boolean checkedIconVisible;
  private ColorStateList chipBackgroundColor;
  private float chipCornerRadius = -1.0F;
  private float chipEndPadding;
  private Drawable chipIcon;
  private float chipIconSize;
  private ColorStateList chipIconTint;
  private boolean chipIconVisible;
  private float chipMinHeight;
  private final Paint chipPaint = new Paint(1);
  private float chipStartPadding;
  private ColorStateList chipStrokeColor;
  private float chipStrokeWidth;
  private ColorStateList chipSurfaceColor;
  private Drawable closeIcon;
  private CharSequence closeIconContentDescription;
  private float closeIconEndPadding;
  private Drawable closeIconRipple;
  private float closeIconSize;
  private float closeIconStartPadding;
  private int[] closeIconStateSet;
  private ColorStateList closeIconTint;
  private boolean closeIconVisible;
  private ColorFilter colorFilter;
  private ColorStateList compatRippleColor;
  private final Context context;
  private boolean currentChecked;
  private int currentChipBackgroundColor;
  private int currentChipStrokeColor;
  private int currentChipSurfaceColor;
  private int currentCompatRippleColor;
  private int currentCompositeSurfaceBackgroundColor;
  private int currentTextColor;
  private int currentTint;
  private final Paint debugPaint;
  private WeakReference<Delegate> delegate = new WeakReference(null);
  private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
  private boolean hasChipIconTint;
  private MotionSpec hideMotionSpec;
  private float iconEndPadding;
  private float iconStartPadding;
  private boolean isShapeThemingEnabled;
  private int maxWidth;
  private final PointF pointF = new PointF();
  private final RectF rectF = new RectF();
  private ColorStateList rippleColor;
  private final Path shapePath = new Path();
  private boolean shouldDrawText;
  private MotionSpec showMotionSpec;
  private CharSequence text;
  private final TextDrawableHelper textDrawableHelper;
  private float textEndPadding;
  private float textStartPadding;
  private ColorStateList tint;
  private PorterDuffColorFilter tintFilter;
  private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
  private TextUtils.TruncateAt truncateAt;
  private boolean useCompatRipple;
  
  private ChipDrawable(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    initializeElevationOverlay(paramContext);
    this.context = paramContext;
    paramAttributeSet = new TextDrawableHelper(this);
    this.textDrawableHelper = paramAttributeSet;
    this.text = "";
    paramAttributeSet.getTextPaint().density = paramContext.getResources().getDisplayMetrics().density;
    this.debugPaint = null;
    paramContext = DEFAULT_STATE;
    setState(paramContext);
    setCloseIconState(paramContext);
    this.shouldDrawText = true;
    if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
      closeIconRippleMask.setTint(-1);
    }
  }
  
  private void applyChildDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == null) {
      return;
    }
    paramDrawable.setCallback(this);
    DrawableCompat.setLayoutDirection(paramDrawable, DrawableCompat.getLayoutDirection(this));
    paramDrawable.setLevel(getLevel());
    paramDrawable.setVisible(isVisible(), false);
    if (paramDrawable == this.closeIcon)
    {
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(getCloseIconState());
      }
      DrawableCompat.setTintList(paramDrawable, this.closeIconTint);
      return;
    }
    Drawable localDrawable = this.chipIcon;
    if ((paramDrawable == localDrawable) && (this.hasChipIconTint)) {
      DrawableCompat.setTintList(localDrawable, this.chipIconTint);
    }
    if (paramDrawable.isStateful()) {
      paramDrawable.setState(getState());
    }
  }
  
  private void calculateChipIconBounds(Rect paramRect, RectF paramRectF)
  {
    paramRectF.setEmpty();
    if ((showsChipIcon()) || (showsCheckedIcon()))
    {
      float f2 = this.chipStartPadding + this.iconStartPadding;
      float f1 = getCurrentChipIconWidth();
      if (DrawableCompat.getLayoutDirection(this) == 0)
      {
        paramRectF.left = (paramRect.left + f2);
        paramRectF.right = (paramRectF.left + f1);
      }
      else
      {
        paramRectF.right = (paramRect.right - f2);
        paramRectF.left = (paramRectF.right - f1);
      }
      f1 = getCurrentChipIconHeight();
      paramRectF.top = (paramRect.exactCenterY() - f1 / 2.0F);
      paramRectF.bottom = (paramRectF.top + f1);
    }
  }
  
  private void calculateChipTouchBounds(Rect paramRect, RectF paramRectF)
  {
    paramRectF.set(paramRect);
    if (showsCloseIcon())
    {
      float f = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
      if (DrawableCompat.getLayoutDirection(this) == 0) {
        paramRectF.right = (paramRect.right - f);
      } else {
        paramRectF.left = (paramRect.left + f);
      }
    }
  }
  
  private void calculateCloseIconBounds(Rect paramRect, RectF paramRectF)
  {
    paramRectF.setEmpty();
    if (showsCloseIcon())
    {
      float f = this.chipEndPadding + this.closeIconEndPadding;
      if (DrawableCompat.getLayoutDirection(this) == 0)
      {
        paramRectF.right = (paramRect.right - f);
        paramRectF.left = (paramRectF.right - this.closeIconSize);
      }
      else
      {
        paramRectF.left = (paramRect.left + f);
        paramRectF.right = (paramRectF.left + this.closeIconSize);
      }
      paramRectF.top = (paramRect.exactCenterY() - this.closeIconSize / 2.0F);
      paramRectF.bottom = (paramRectF.top + this.closeIconSize);
    }
  }
  
  private void calculateCloseIconTouchBounds(Rect paramRect, RectF paramRectF)
  {
    paramRectF.setEmpty();
    if (showsCloseIcon())
    {
      float f = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
      if (DrawableCompat.getLayoutDirection(this) == 0)
      {
        paramRectF.right = paramRect.right;
        paramRectF.left = (paramRectF.right - f);
      }
      else
      {
        paramRectF.left = paramRect.left;
        paramRectF.right = (paramRect.left + f);
      }
      paramRectF.top = paramRect.top;
      paramRectF.bottom = paramRect.bottom;
    }
  }
  
  private void calculateTextBounds(Rect paramRect, RectF paramRectF)
  {
    paramRectF.setEmpty();
    if (this.text != null)
    {
      float f2 = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
      float f1 = this.chipEndPadding + calculateCloseIconWidth() + this.textEndPadding;
      if (DrawableCompat.getLayoutDirection(this) == 0)
      {
        paramRectF.left = (paramRect.left + f2);
        paramRectF.right = (paramRect.right - f1);
      }
      else
      {
        paramRectF.left = (paramRect.left + f1);
        paramRectF.right = (paramRect.right - f2);
      }
      paramRectF.top = paramRect.top;
      paramRectF.bottom = paramRect.bottom;
    }
  }
  
  private float calculateTextCenterFromBaseline()
  {
    this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
    return (this.fontMetrics.descent + this.fontMetrics.ascent) / 2.0F;
  }
  
  private boolean canShowCheckedIcon()
  {
    boolean bool;
    if ((this.checkedIconVisible) && (this.checkedIcon != null) && (this.checkable)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static ChipDrawable createFromAttributes(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramContext = new ChipDrawable(paramContext, paramAttributeSet, paramInt1, paramInt2);
    paramContext.loadFromAttributes(paramAttributeSet, paramInt1, paramInt2);
    return paramContext;
  }
  
  public static ChipDrawable createFromResource(Context paramContext, int paramInt)
  {
    AttributeSet localAttributeSet = DrawableUtils.parseDrawableXml(paramContext, paramInt, "chip");
    int i = localAttributeSet.getStyleAttribute();
    paramInt = i;
    if (i == 0) {
      paramInt = R.style.Widget_MaterialComponents_Chip_Entry;
    }
    return createFromAttributes(paramContext, localAttributeSet, R.attr.chipStandaloneStyle, paramInt);
  }
  
  private void drawCheckedIcon(Canvas paramCanvas, Rect paramRect)
  {
    if (showsCheckedIcon())
    {
      calculateChipIconBounds(paramRect, this.rectF);
      float f2 = this.rectF.left;
      float f1 = this.rectF.top;
      paramCanvas.translate(f2, f1);
      this.checkedIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
      this.checkedIcon.draw(paramCanvas);
      paramCanvas.translate(-f2, -f1);
    }
  }
  
  private void drawChipBackground(Canvas paramCanvas, Rect paramRect)
  {
    if (!this.isShapeThemingEnabled)
    {
      this.chipPaint.setColor(this.currentChipBackgroundColor);
      this.chipPaint.setStyle(Paint.Style.FILL);
      this.chipPaint.setColorFilter(getTintColorFilter());
      this.rectF.set(paramRect);
      paramCanvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
    }
  }
  
  private void drawChipIcon(Canvas paramCanvas, Rect paramRect)
  {
    if (showsChipIcon())
    {
      calculateChipIconBounds(paramRect, this.rectF);
      float f1 = this.rectF.left;
      float f2 = this.rectF.top;
      paramCanvas.translate(f1, f2);
      this.chipIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
      this.chipIcon.draw(paramCanvas);
      paramCanvas.translate(-f1, -f2);
    }
  }
  
  private void drawChipStroke(Canvas paramCanvas, Rect paramRect)
  {
    if ((this.chipStrokeWidth > 0.0F) && (!this.isShapeThemingEnabled))
    {
      this.chipPaint.setColor(this.currentChipStrokeColor);
      this.chipPaint.setStyle(Paint.Style.STROKE);
      if (!this.isShapeThemingEnabled) {
        this.chipPaint.setColorFilter(getTintColorFilter());
      }
      this.rectF.set(paramRect.left + this.chipStrokeWidth / 2.0F, paramRect.top + this.chipStrokeWidth / 2.0F, paramRect.right - this.chipStrokeWidth / 2.0F, paramRect.bottom - this.chipStrokeWidth / 2.0F);
      float f = this.chipCornerRadius - this.chipStrokeWidth / 2.0F;
      paramCanvas.drawRoundRect(this.rectF, f, f, this.chipPaint);
    }
  }
  
  private void drawChipSurface(Canvas paramCanvas, Rect paramRect)
  {
    if (!this.isShapeThemingEnabled)
    {
      this.chipPaint.setColor(this.currentChipSurfaceColor);
      this.chipPaint.setStyle(Paint.Style.FILL);
      this.rectF.set(paramRect);
      paramCanvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
    }
  }
  
  private void drawCloseIcon(Canvas paramCanvas, Rect paramRect)
  {
    if (showsCloseIcon())
    {
      calculateCloseIconBounds(paramRect, this.rectF);
      float f1 = this.rectF.left;
      float f2 = this.rectF.top;
      paramCanvas.translate(f1, f2);
      this.closeIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
      if (RippleUtils.USE_FRAMEWORK_RIPPLE)
      {
        this.closeIconRipple.setBounds(this.closeIcon.getBounds());
        this.closeIconRipple.jumpToCurrentState();
        this.closeIconRipple.draw(paramCanvas);
      }
      else
      {
        this.closeIcon.draw(paramCanvas);
      }
      paramCanvas.translate(-f1, -f2);
    }
  }
  
  private void drawCompatRipple(Canvas paramCanvas, Rect paramRect)
  {
    this.chipPaint.setColor(this.currentCompatRippleColor);
    this.chipPaint.setStyle(Paint.Style.FILL);
    this.rectF.set(paramRect);
    if (!this.isShapeThemingEnabled)
    {
      paramCanvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
    }
    else
    {
      calculatePathForSize(new RectF(paramRect), this.shapePath);
      super.drawShape(paramCanvas, this.chipPaint, this.shapePath, getBoundsAsRectF());
    }
  }
  
  private void drawDebug(Canvas paramCanvas, Rect paramRect)
  {
    Paint localPaint = this.debugPaint;
    if (localPaint != null)
    {
      localPaint.setColor(ColorUtils.setAlphaComponent(-16777216, 127));
      paramCanvas.drawRect(paramRect, this.debugPaint);
      if ((showsChipIcon()) || (showsCheckedIcon()))
      {
        calculateChipIconBounds(paramRect, this.rectF);
        paramCanvas.drawRect(this.rectF, this.debugPaint);
      }
      if (this.text != null) {
        paramCanvas.drawLine(paramRect.left, paramRect.exactCenterY(), paramRect.right, paramRect.exactCenterY(), this.debugPaint);
      }
      if (showsCloseIcon())
      {
        calculateCloseIconBounds(paramRect, this.rectF);
        paramCanvas.drawRect(this.rectF, this.debugPaint);
      }
      this.debugPaint.setColor(ColorUtils.setAlphaComponent(-65536, 127));
      calculateChipTouchBounds(paramRect, this.rectF);
      paramCanvas.drawRect(this.rectF, this.debugPaint);
      this.debugPaint.setColor(ColorUtils.setAlphaComponent(-16711936, 127));
      calculateCloseIconTouchBounds(paramRect, this.rectF);
      paramCanvas.drawRect(this.rectF, this.debugPaint);
    }
  }
  
  private void drawText(Canvas paramCanvas, Rect paramRect)
  {
    if (this.text != null)
    {
      Object localObject = calculateTextOriginAndAlignment(paramRect, this.pointF);
      calculateTextBounds(paramRect, this.rectF);
      if (this.textDrawableHelper.getTextAppearance() != null)
      {
        this.textDrawableHelper.getTextPaint().drawableState = getState();
        this.textDrawableHelper.updateTextPaintDrawState(this.context);
      }
      this.textDrawableHelper.getTextPaint().setTextAlign((Paint.Align)localObject);
      int i;
      if (Math.round(this.textDrawableHelper.getTextWidth(getText().toString())) > Math.round(this.rectF.width())) {
        i = 1;
      } else {
        i = 0;
      }
      int j = 0;
      if (i != 0)
      {
        j = paramCanvas.save();
        paramCanvas.clipRect(this.rectF);
      }
      localObject = this.text;
      paramRect = (Rect)localObject;
      if (i != 0)
      {
        paramRect = (Rect)localObject;
        if (this.truncateAt != null) {
          paramRect = TextUtils.ellipsize(this.text, this.textDrawableHelper.getTextPaint(), this.rectF.width(), this.truncateAt);
        }
      }
      paramCanvas.drawText(paramRect, 0, paramRect.length(), this.pointF.x, this.pointF.y, this.textDrawableHelper.getTextPaint());
      if (i != 0) {
        paramCanvas.restoreToCount(j);
      }
    }
  }
  
  private float getCurrentChipIconHeight()
  {
    Drawable localDrawable;
    if (this.currentChecked) {
      localDrawable = this.checkedIcon;
    } else {
      localDrawable = this.chipIcon;
    }
    float f = this.chipIconSize;
    if ((f <= 0.0F) && (localDrawable != null))
    {
      f = (float)Math.ceil(ViewUtils.dpToPx(this.context, 24));
      if (localDrawable.getIntrinsicHeight() <= f) {
        return localDrawable.getIntrinsicHeight();
      }
      return f;
    }
    return f;
  }
  
  private float getCurrentChipIconWidth()
  {
    Drawable localDrawable;
    if (this.currentChecked) {
      localDrawable = this.checkedIcon;
    } else {
      localDrawable = this.chipIcon;
    }
    float f = this.chipIconSize;
    if ((f <= 0.0F) && (localDrawable != null)) {
      return localDrawable.getIntrinsicWidth();
    }
    return f;
  }
  
  private ColorFilter getTintColorFilter()
  {
    Object localObject = this.colorFilter;
    if (localObject == null) {
      localObject = this.tintFilter;
    }
    return (ColorFilter)localObject;
  }
  
  private static boolean hasState(int[] paramArrayOfInt, int paramInt)
  {
    if (paramArrayOfInt == null) {
      return false;
    }
    int j = paramArrayOfInt.length;
    for (int i = 0; i < j; i++) {
      if (paramArrayOfInt[i] == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  private static boolean isStateful(ColorStateList paramColorStateList)
  {
    boolean bool;
    if ((paramColorStateList != null) && (paramColorStateList.isStateful())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isStateful(Drawable paramDrawable)
  {
    boolean bool;
    if ((paramDrawable != null) && (paramDrawable.isStateful())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isStateful(TextAppearance paramTextAppearance)
  {
    boolean bool;
    if ((paramTextAppearance != null) && (paramTextAppearance.getTextColor() != null) && (paramTextAppearance.getTextColor().isStateful())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(this.context, paramAttributeSet, R.styleable.Chip, paramInt1, paramInt2, new int[0]);
    this.isShapeThemingEnabled = localTypedArray.hasValue(R.styleable.Chip_shapeAppearance);
    setChipSurfaceColor(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_chipSurfaceColor));
    setChipBackgroundColor(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_chipBackgroundColor));
    setChipMinHeight(localTypedArray.getDimension(R.styleable.Chip_chipMinHeight, 0.0F));
    if (localTypedArray.hasValue(R.styleable.Chip_chipCornerRadius)) {
      setChipCornerRadius(localTypedArray.getDimension(R.styleable.Chip_chipCornerRadius, 0.0F));
    }
    setChipStrokeColor(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_chipStrokeColor));
    setChipStrokeWidth(localTypedArray.getDimension(R.styleable.Chip_chipStrokeWidth, 0.0F));
    setRippleColor(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_rippleColor));
    setText(localTypedArray.getText(R.styleable.Chip_android_text));
    TextAppearance localTextAppearance = MaterialResources.getTextAppearance(this.context, localTypedArray, R.styleable.Chip_android_textAppearance);
    localTextAppearance.setTextSize(localTypedArray.getDimension(R.styleable.Chip_android_textSize, localTextAppearance.getTextSize()));
    if (Build.VERSION.SDK_INT < 23) {
      localTextAppearance.setTextColor(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_android_textColor));
    }
    setTextAppearance(localTextAppearance);
    switch (localTypedArray.getInt(R.styleable.Chip_android_ellipsize, 0))
    {
    default: 
      break;
    case 3: 
      setEllipsize(TextUtils.TruncateAt.END);
      break;
    case 2: 
      setEllipsize(TextUtils.TruncateAt.MIDDLE);
      break;
    case 1: 
      setEllipsize(TextUtils.TruncateAt.START);
    }
    setChipIconVisible(localTypedArray.getBoolean(R.styleable.Chip_chipIconVisible, false));
    if ((paramAttributeSet != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") == null)) {
      setChipIconVisible(localTypedArray.getBoolean(R.styleable.Chip_chipIconEnabled, false));
    }
    setChipIcon(MaterialResources.getDrawable(this.context, localTypedArray, R.styleable.Chip_chipIcon));
    if (localTypedArray.hasValue(R.styleable.Chip_chipIconTint)) {
      setChipIconTint(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_chipIconTint));
    }
    setChipIconSize(localTypedArray.getDimension(R.styleable.Chip_chipIconSize, -1.0F));
    setCloseIconVisible(localTypedArray.getBoolean(R.styleable.Chip_closeIconVisible, false));
    if ((paramAttributeSet != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") == null)) {
      setCloseIconVisible(localTypedArray.getBoolean(R.styleable.Chip_closeIconEnabled, false));
    }
    setCloseIcon(MaterialResources.getDrawable(this.context, localTypedArray, R.styleable.Chip_closeIcon));
    setCloseIconTint(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_closeIconTint));
    setCloseIconSize(localTypedArray.getDimension(R.styleable.Chip_closeIconSize, 0.0F));
    setCheckable(localTypedArray.getBoolean(R.styleable.Chip_android_checkable, false));
    setCheckedIconVisible(localTypedArray.getBoolean(R.styleable.Chip_checkedIconVisible, false));
    if ((paramAttributeSet != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") != null) && (paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") == null)) {
      setCheckedIconVisible(localTypedArray.getBoolean(R.styleable.Chip_checkedIconEnabled, false));
    }
    setCheckedIcon(MaterialResources.getDrawable(this.context, localTypedArray, R.styleable.Chip_checkedIcon));
    if (localTypedArray.hasValue(R.styleable.Chip_checkedIconTint)) {
      setCheckedIconTint(MaterialResources.getColorStateList(this.context, localTypedArray, R.styleable.Chip_checkedIconTint));
    }
    setShowMotionSpec(MotionSpec.createFromAttribute(this.context, localTypedArray, R.styleable.Chip_showMotionSpec));
    setHideMotionSpec(MotionSpec.createFromAttribute(this.context, localTypedArray, R.styleable.Chip_hideMotionSpec));
    setChipStartPadding(localTypedArray.getDimension(R.styleable.Chip_chipStartPadding, 0.0F));
    setIconStartPadding(localTypedArray.getDimension(R.styleable.Chip_iconStartPadding, 0.0F));
    setIconEndPadding(localTypedArray.getDimension(R.styleable.Chip_iconEndPadding, 0.0F));
    setTextStartPadding(localTypedArray.getDimension(R.styleable.Chip_textStartPadding, 0.0F));
    setTextEndPadding(localTypedArray.getDimension(R.styleable.Chip_textEndPadding, 0.0F));
    setCloseIconStartPadding(localTypedArray.getDimension(R.styleable.Chip_closeIconStartPadding, 0.0F));
    setCloseIconEndPadding(localTypedArray.getDimension(R.styleable.Chip_closeIconEndPadding, 0.0F));
    setChipEndPadding(localTypedArray.getDimension(R.styleable.Chip_chipEndPadding, 0.0F));
    setMaxWidth(localTypedArray.getDimensionPixelSize(R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
    localTypedArray.recycle();
  }
  
  private boolean onStateChange(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    boolean bool2 = super.onStateChange(paramArrayOfInt1);
    int k = 0;
    Object localObject = this.chipSurfaceColor;
    if (localObject != null) {
      i = ((ColorStateList)localObject).getColorForState(paramArrayOfInt1, this.currentChipSurfaceColor);
    } else {
      i = 0;
    }
    int j = compositeElevationOverlayIfNeeded(i);
    if (this.currentChipSurfaceColor != j)
    {
      this.currentChipSurfaceColor = j;
      bool2 = true;
    }
    localObject = this.chipBackgroundColor;
    if (localObject != null) {
      i = ((ColorStateList)localObject).getColorForState(paramArrayOfInt1, this.currentChipBackgroundColor);
    } else {
      i = 0;
    }
    int i = compositeElevationOverlayIfNeeded(i);
    if (this.currentChipBackgroundColor != i)
    {
      this.currentChipBackgroundColor = i;
      bool2 = true;
    }
    int m = MaterialColors.layer(j, i);
    i = this.currentCompositeSurfaceBackgroundColor;
    boolean bool3 = true;
    if (i != m) {
      i = 1;
    } else {
      i = 0;
    }
    if (getFillColor() == null) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i | j) != 0)
    {
      this.currentCompositeSurfaceBackgroundColor = m;
      setFillColor(ColorStateList.valueOf(m));
      bool2 = true;
    }
    localObject = this.chipStrokeColor;
    if (localObject != null) {
      i = ((ColorStateList)localObject).getColorForState(paramArrayOfInt1, this.currentChipStrokeColor);
    } else {
      i = 0;
    }
    if (this.currentChipStrokeColor != i)
    {
      this.currentChipStrokeColor = i;
      bool2 = true;
    }
    if ((this.compatRippleColor != null) && (RippleUtils.shouldDrawRippleCompat(paramArrayOfInt1))) {
      i = this.compatRippleColor.getColorForState(paramArrayOfInt1, this.currentCompatRippleColor);
    } else {
      i = 0;
    }
    boolean bool1 = bool2;
    if (this.currentCompatRippleColor != i)
    {
      this.currentCompatRippleColor = i;
      bool1 = bool2;
      if (this.useCompatRipple) {
        bool1 = true;
      }
    }
    if ((this.textDrawableHelper.getTextAppearance() != null) && (this.textDrawableHelper.getTextAppearance().getTextColor() != null)) {
      i = this.textDrawableHelper.getTextAppearance().getTextColor().getColorForState(paramArrayOfInt1, this.currentTextColor);
    } else {
      i = 0;
    }
    if (this.currentTextColor != i)
    {
      this.currentTextColor = i;
      bool1 = true;
    }
    if ((!hasState(getState(), 16842912)) || (!this.checkable)) {
      bool3 = false;
    }
    bool2 = bool1;
    i = k;
    if (this.currentChecked != bool3)
    {
      bool2 = bool1;
      i = k;
      if (this.checkedIcon != null)
      {
        float f1 = calculateChipIconWidth();
        this.currentChecked = bool3;
        float f2 = calculateChipIconWidth();
        bool1 = true;
        bool2 = bool1;
        i = k;
        if (f1 != f2)
        {
          i = 1;
          bool2 = bool1;
        }
      }
    }
    localObject = this.tint;
    if (localObject != null) {
      j = ((ColorStateList)localObject).getColorForState(paramArrayOfInt1, this.currentTint);
    } else {
      j = 0;
    }
    if (this.currentTint != j)
    {
      this.currentTint = j;
      this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, this.tintMode);
      bool2 = true;
    }
    bool1 = bool2;
    if (isStateful(this.chipIcon)) {
      bool1 = bool2 | this.chipIcon.setState(paramArrayOfInt1);
    }
    bool2 = bool1;
    if (isStateful(this.checkedIcon)) {
      bool2 = bool1 | this.checkedIcon.setState(paramArrayOfInt1);
    }
    if (isStateful(this.closeIcon))
    {
      localObject = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
      System.arraycopy(paramArrayOfInt1, 0, localObject, 0, paramArrayOfInt1.length);
      System.arraycopy(paramArrayOfInt2, 0, localObject, paramArrayOfInt1.length, paramArrayOfInt2.length);
      bool1 = bool2 | this.closeIcon.setState((int[])localObject);
    }
    else
    {
      bool1 = bool2;
    }
    bool2 = bool1;
    if (RippleUtils.USE_FRAMEWORK_RIPPLE)
    {
      bool2 = bool1;
      if (isStateful(this.closeIconRipple)) {
        bool2 = bool1 | this.closeIconRipple.setState(paramArrayOfInt2);
      }
    }
    if (bool2) {
      invalidateSelf();
    }
    if (i != 0) {
      onSizeChange();
    }
    return bool2;
  }
  
  private void setChipSurfaceColor(ColorStateList paramColorStateList)
  {
    if (this.chipSurfaceColor != paramColorStateList)
    {
      this.chipSurfaceColor = paramColorStateList;
      onStateChange(getState());
    }
  }
  
  private boolean showsCheckedIcon()
  {
    boolean bool;
    if ((this.checkedIconVisible) && (this.checkedIcon != null) && (this.currentChecked)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean showsChipIcon()
  {
    boolean bool;
    if ((this.chipIconVisible) && (this.chipIcon != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean showsCloseIcon()
  {
    boolean bool;
    if ((this.closeIconVisible) && (this.closeIcon != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void unapplyChildDrawable(Drawable paramDrawable)
  {
    if (paramDrawable != null) {
      paramDrawable.setCallback(null);
    }
  }
  
  private void updateCompatRippleColor()
  {
    ColorStateList localColorStateList;
    if (this.useCompatRipple) {
      localColorStateList = RippleUtils.sanitizeRippleDrawableColor(this.rippleColor);
    } else {
      localColorStateList = null;
    }
    this.compatRippleColor = localColorStateList;
  }
  
  private void updateFrameworkCloseIconRipple()
  {
    this.closeIconRipple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(getRippleColor()), this.closeIcon, closeIconRippleMask);
  }
  
  float calculateChipIconWidth()
  {
    if ((!showsChipIcon()) && (!showsCheckedIcon())) {
      return 0.0F;
    }
    return this.iconStartPadding + getCurrentChipIconWidth() + this.iconEndPadding;
  }
  
  float calculateCloseIconWidth()
  {
    if (showsCloseIcon()) {
      return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
    }
    return 0.0F;
  }
  
  Paint.Align calculateTextOriginAndAlignment(Rect paramRect, PointF paramPointF)
  {
    paramPointF.set(0.0F, 0.0F);
    Paint.Align localAlign = Paint.Align.LEFT;
    if (this.text != null)
    {
      float f = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
      if (DrawableCompat.getLayoutDirection(this) == 0)
      {
        paramPointF.x = (paramRect.left + f);
        localAlign = Paint.Align.LEFT;
      }
      else
      {
        paramPointF.x = (paramRect.right - f);
        localAlign = Paint.Align.RIGHT;
      }
      paramPointF.y = (paramRect.centerY() - calculateTextCenterFromBaseline());
    }
    return localAlign;
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    if ((!localRect.isEmpty()) && (getAlpha() != 0))
    {
      int i = 0;
      if (this.alpha < 255) {
        i = CanvasCompat.saveLayerAlpha(paramCanvas, localRect.left, localRect.top, localRect.right, localRect.bottom, this.alpha);
      }
      drawChipSurface(paramCanvas, localRect);
      drawChipBackground(paramCanvas, localRect);
      if (this.isShapeThemingEnabled) {
        super.draw(paramCanvas);
      }
      drawChipStroke(paramCanvas, localRect);
      drawCompatRipple(paramCanvas, localRect);
      drawChipIcon(paramCanvas, localRect);
      drawCheckedIcon(paramCanvas, localRect);
      if (this.shouldDrawText) {
        drawText(paramCanvas, localRect);
      }
      drawCloseIcon(paramCanvas, localRect);
      drawDebug(paramCanvas, localRect);
      if (this.alpha < 255) {
        paramCanvas.restoreToCount(i);
      }
      return;
    }
  }
  
  public int getAlpha()
  {
    return this.alpha;
  }
  
  public Drawable getCheckedIcon()
  {
    return this.checkedIcon;
  }
  
  public ColorStateList getCheckedIconTint()
  {
    return this.checkedIconTint;
  }
  
  public ColorStateList getChipBackgroundColor()
  {
    return this.chipBackgroundColor;
  }
  
  public float getChipCornerRadius()
  {
    float f;
    if (this.isShapeThemingEnabled) {
      f = getTopLeftCornerResolvedSize();
    } else {
      f = this.chipCornerRadius;
    }
    return f;
  }
  
  public float getChipEndPadding()
  {
    return this.chipEndPadding;
  }
  
  public Drawable getChipIcon()
  {
    Drawable localDrawable = this.chipIcon;
    if (localDrawable != null) {
      localDrawable = DrawableCompat.unwrap(localDrawable);
    } else {
      localDrawable = null;
    }
    return localDrawable;
  }
  
  public float getChipIconSize()
  {
    return this.chipIconSize;
  }
  
  public ColorStateList getChipIconTint()
  {
    return this.chipIconTint;
  }
  
  public float getChipMinHeight()
  {
    return this.chipMinHeight;
  }
  
  public float getChipStartPadding()
  {
    return this.chipStartPadding;
  }
  
  public ColorStateList getChipStrokeColor()
  {
    return this.chipStrokeColor;
  }
  
  public float getChipStrokeWidth()
  {
    return this.chipStrokeWidth;
  }
  
  public void getChipTouchBounds(RectF paramRectF)
  {
    calculateChipTouchBounds(getBounds(), paramRectF);
  }
  
  public Drawable getCloseIcon()
  {
    Drawable localDrawable = this.closeIcon;
    if (localDrawable != null) {
      localDrawable = DrawableCompat.unwrap(localDrawable);
    } else {
      localDrawable = null;
    }
    return localDrawable;
  }
  
  public CharSequence getCloseIconContentDescription()
  {
    return this.closeIconContentDescription;
  }
  
  public float getCloseIconEndPadding()
  {
    return this.closeIconEndPadding;
  }
  
  public float getCloseIconSize()
  {
    return this.closeIconSize;
  }
  
  public float getCloseIconStartPadding()
  {
    return this.closeIconStartPadding;
  }
  
  public int[] getCloseIconState()
  {
    return this.closeIconStateSet;
  }
  
  public ColorStateList getCloseIconTint()
  {
    return this.closeIconTint;
  }
  
  public void getCloseIconTouchBounds(RectF paramRectF)
  {
    calculateCloseIconTouchBounds(getBounds(), paramRectF);
  }
  
  public ColorFilter getColorFilter()
  {
    return this.colorFilter;
  }
  
  public TextUtils.TruncateAt getEllipsize()
  {
    return this.truncateAt;
  }
  
  public MotionSpec getHideMotionSpec()
  {
    return this.hideMotionSpec;
  }
  
  public float getIconEndPadding()
  {
    return this.iconEndPadding;
  }
  
  public float getIconStartPadding()
  {
    return this.iconStartPadding;
  }
  
  public int getIntrinsicHeight()
  {
    return (int)this.chipMinHeight;
  }
  
  public int getIntrinsicWidth()
  {
    return Math.min(Math.round(this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding + this.textDrawableHelper.getTextWidth(getText().toString()) + this.textEndPadding + calculateCloseIconWidth() + this.chipEndPadding), this.maxWidth);
  }
  
  public int getMaxWidth()
  {
    return this.maxWidth;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void getOutline(Outline paramOutline)
  {
    if (this.isShapeThemingEnabled)
    {
      super.getOutline(paramOutline);
      return;
    }
    Rect localRect = getBounds();
    if (!localRect.isEmpty()) {
      paramOutline.setRoundRect(localRect, this.chipCornerRadius);
    } else {
      paramOutline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.chipCornerRadius);
    }
    paramOutline.setAlpha(getAlpha() / 255.0F);
  }
  
  public ColorStateList getRippleColor()
  {
    return this.rippleColor;
  }
  
  public MotionSpec getShowMotionSpec()
  {
    return this.showMotionSpec;
  }
  
  public CharSequence getText()
  {
    return this.text;
  }
  
  public TextAppearance getTextAppearance()
  {
    return this.textDrawableHelper.getTextAppearance();
  }
  
  public float getTextEndPadding()
  {
    return this.textEndPadding;
  }
  
  public float getTextStartPadding()
  {
    return this.textStartPadding;
  }
  
  public boolean getUseCompatRipple()
  {
    return this.useCompatRipple;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    paramDrawable = getCallback();
    if (paramDrawable != null) {
      paramDrawable.invalidateDrawable(this);
    }
  }
  
  public boolean isCheckable()
  {
    return this.checkable;
  }
  
  @Deprecated
  public boolean isCheckedIconEnabled()
  {
    return isCheckedIconVisible();
  }
  
  public boolean isCheckedIconVisible()
  {
    return this.checkedIconVisible;
  }
  
  @Deprecated
  public boolean isChipIconEnabled()
  {
    return isChipIconVisible();
  }
  
  public boolean isChipIconVisible()
  {
    return this.chipIconVisible;
  }
  
  @Deprecated
  public boolean isCloseIconEnabled()
  {
    return isCloseIconVisible();
  }
  
  public boolean isCloseIconStateful()
  {
    return isStateful(this.closeIcon);
  }
  
  public boolean isCloseIconVisible()
  {
    return this.closeIconVisible;
  }
  
  boolean isShapeThemingEnabled()
  {
    return this.isShapeThemingEnabled;
  }
  
  public boolean isStateful()
  {
    boolean bool;
    if ((!isStateful(this.chipSurfaceColor)) && (!isStateful(this.chipBackgroundColor)) && (!isStateful(this.chipStrokeColor)) && ((!this.useCompatRipple) || (!isStateful(this.compatRippleColor))) && (!isStateful(this.textDrawableHelper.getTextAppearance())) && (!canShowCheckedIcon()) && (!isStateful(this.chipIcon)) && (!isStateful(this.checkedIcon)) && (!isStateful(this.tint))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean onLayoutDirectionChanged(int paramInt)
  {
    boolean bool2 = super.onLayoutDirectionChanged(paramInt);
    boolean bool1 = bool2;
    if (showsChipIcon()) {
      bool1 = bool2 | DrawableCompat.setLayoutDirection(this.chipIcon, paramInt);
    }
    bool2 = bool1;
    if (showsCheckedIcon()) {
      bool2 = bool1 | DrawableCompat.setLayoutDirection(this.checkedIcon, paramInt);
    }
    bool1 = bool2;
    if (showsCloseIcon()) {
      bool1 = bool2 | DrawableCompat.setLayoutDirection(this.closeIcon, paramInt);
    }
    if (bool1) {
      invalidateSelf();
    }
    return true;
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    boolean bool1 = super.onLevelChange(paramInt);
    boolean bool2 = bool1;
    if (showsChipIcon()) {
      bool2 = bool1 | this.chipIcon.setLevel(paramInt);
    }
    bool1 = bool2;
    if (showsCheckedIcon()) {
      bool1 = bool2 | this.checkedIcon.setLevel(paramInt);
    }
    bool2 = bool1;
    if (showsCloseIcon()) {
      bool2 = bool1 | this.closeIcon.setLevel(paramInt);
    }
    if (bool2) {
      invalidateSelf();
    }
    return bool2;
  }
  
  protected void onSizeChange()
  {
    Delegate localDelegate = (Delegate)this.delegate.get();
    if (localDelegate != null) {
      localDelegate.onChipDrawableSizeChange();
    }
  }
  
  public boolean onStateChange(int[] paramArrayOfInt)
  {
    if (this.isShapeThemingEnabled) {
      super.onStateChange(paramArrayOfInt);
    }
    return onStateChange(paramArrayOfInt, getCloseIconState());
  }
  
  public void onTextSizeChange()
  {
    onSizeChange();
    invalidateSelf();
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    paramDrawable = getCallback();
    if (paramDrawable != null) {
      paramDrawable.scheduleDrawable(this, paramRunnable, paramLong);
    }
  }
  
  public void setAlpha(int paramInt)
  {
    if (this.alpha != paramInt)
    {
      this.alpha = paramInt;
      invalidateSelf();
    }
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    if (this.checkable != paramBoolean)
    {
      this.checkable = paramBoolean;
      float f1 = calculateChipIconWidth();
      if ((!paramBoolean) && (this.currentChecked)) {
        this.currentChecked = false;
      }
      float f2 = calculateChipIconWidth();
      invalidateSelf();
      if (f1 != f2) {
        onSizeChange();
      }
    }
  }
  
  public void setCheckableResource(int paramInt)
  {
    setCheckable(this.context.getResources().getBoolean(paramInt));
  }
  
  public void setCheckedIcon(Drawable paramDrawable)
  {
    if (this.checkedIcon != paramDrawable)
    {
      float f1 = calculateChipIconWidth();
      this.checkedIcon = paramDrawable;
      float f2 = calculateChipIconWidth();
      unapplyChildDrawable(this.checkedIcon);
      applyChildDrawable(this.checkedIcon);
      invalidateSelf();
      if (f1 != f2) {
        onSizeChange();
      }
    }
  }
  
  @Deprecated
  public void setCheckedIconEnabled(boolean paramBoolean)
  {
    setCheckedIconVisible(paramBoolean);
  }
  
  @Deprecated
  public void setCheckedIconEnabledResource(int paramInt)
  {
    setCheckedIconVisible(this.context.getResources().getBoolean(paramInt));
  }
  
  public void setCheckedIconResource(int paramInt)
  {
    setCheckedIcon(AppCompatResources.getDrawable(this.context, paramInt));
  }
  
  public void setCheckedIconTint(ColorStateList paramColorStateList)
  {
    if (this.checkedIconTint != paramColorStateList)
    {
      this.checkedIconTint = paramColorStateList;
      if (canShowCheckedIcon()) {
        DrawableCompat.setTintList(this.checkedIcon, paramColorStateList);
      }
      onStateChange(getState());
    }
  }
  
  public void setCheckedIconTintResource(int paramInt)
  {
    setCheckedIconTint(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  public void setCheckedIconVisible(int paramInt)
  {
    setCheckedIconVisible(this.context.getResources().getBoolean(paramInt));
  }
  
  public void setCheckedIconVisible(boolean paramBoolean)
  {
    if (this.checkedIconVisible != paramBoolean)
    {
      boolean bool = showsCheckedIcon();
      this.checkedIconVisible = paramBoolean;
      paramBoolean = showsCheckedIcon();
      int i;
      if (bool != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramBoolean) {
          applyChildDrawable(this.checkedIcon);
        } else {
          unapplyChildDrawable(this.checkedIcon);
        }
        invalidateSelf();
        onSizeChange();
      }
    }
  }
  
  public void setChipBackgroundColor(ColorStateList paramColorStateList)
  {
    if (this.chipBackgroundColor != paramColorStateList)
    {
      this.chipBackgroundColor = paramColorStateList;
      onStateChange(getState());
    }
  }
  
  public void setChipBackgroundColorResource(int paramInt)
  {
    setChipBackgroundColor(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  @Deprecated
  public void setChipCornerRadius(float paramFloat)
  {
    if (this.chipCornerRadius != paramFloat)
    {
      this.chipCornerRadius = paramFloat;
      setShapeAppearanceModel(getShapeAppearanceModel().withCornerSize(paramFloat));
    }
  }
  
  @Deprecated
  public void setChipCornerRadiusResource(int paramInt)
  {
    setChipCornerRadius(this.context.getResources().getDimension(paramInt));
  }
  
  public void setChipEndPadding(float paramFloat)
  {
    if (this.chipEndPadding != paramFloat)
    {
      this.chipEndPadding = paramFloat;
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setChipEndPaddingResource(int paramInt)
  {
    setChipEndPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setChipIcon(Drawable paramDrawable)
  {
    Drawable localDrawable = getChipIcon();
    if (localDrawable != paramDrawable)
    {
      float f2 = calculateChipIconWidth();
      if (paramDrawable != null) {
        paramDrawable = DrawableCompat.wrap(paramDrawable).mutate();
      } else {
        paramDrawable = null;
      }
      this.chipIcon = paramDrawable;
      float f1 = calculateChipIconWidth();
      unapplyChildDrawable(localDrawable);
      if (showsChipIcon()) {
        applyChildDrawable(this.chipIcon);
      }
      invalidateSelf();
      if (f2 != f1) {
        onSizeChange();
      }
    }
  }
  
  @Deprecated
  public void setChipIconEnabled(boolean paramBoolean)
  {
    setChipIconVisible(paramBoolean);
  }
  
  @Deprecated
  public void setChipIconEnabledResource(int paramInt)
  {
    setChipIconVisible(paramInt);
  }
  
  public void setChipIconResource(int paramInt)
  {
    setChipIcon(AppCompatResources.getDrawable(this.context, paramInt));
  }
  
  public void setChipIconSize(float paramFloat)
  {
    if (this.chipIconSize != paramFloat)
    {
      float f = calculateChipIconWidth();
      this.chipIconSize = paramFloat;
      paramFloat = calculateChipIconWidth();
      invalidateSelf();
      if (f != paramFloat) {
        onSizeChange();
      }
    }
  }
  
  public void setChipIconSizeResource(int paramInt)
  {
    setChipIconSize(this.context.getResources().getDimension(paramInt));
  }
  
  public void setChipIconTint(ColorStateList paramColorStateList)
  {
    this.hasChipIconTint = true;
    if (this.chipIconTint != paramColorStateList)
    {
      this.chipIconTint = paramColorStateList;
      if (showsChipIcon()) {
        DrawableCompat.setTintList(this.chipIcon, paramColorStateList);
      }
      onStateChange(getState());
    }
  }
  
  public void setChipIconTintResource(int paramInt)
  {
    setChipIconTint(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  public void setChipIconVisible(int paramInt)
  {
    setChipIconVisible(this.context.getResources().getBoolean(paramInt));
  }
  
  public void setChipIconVisible(boolean paramBoolean)
  {
    if (this.chipIconVisible != paramBoolean)
    {
      boolean bool = showsChipIcon();
      this.chipIconVisible = paramBoolean;
      paramBoolean = showsChipIcon();
      int i;
      if (bool != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramBoolean) {
          applyChildDrawable(this.chipIcon);
        } else {
          unapplyChildDrawable(this.chipIcon);
        }
        invalidateSelf();
        onSizeChange();
      }
    }
  }
  
  public void setChipMinHeight(float paramFloat)
  {
    if (this.chipMinHeight != paramFloat)
    {
      this.chipMinHeight = paramFloat;
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setChipMinHeightResource(int paramInt)
  {
    setChipMinHeight(this.context.getResources().getDimension(paramInt));
  }
  
  public void setChipStartPadding(float paramFloat)
  {
    if (this.chipStartPadding != paramFloat)
    {
      this.chipStartPadding = paramFloat;
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setChipStartPaddingResource(int paramInt)
  {
    setChipStartPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setChipStrokeColor(ColorStateList paramColorStateList)
  {
    if (this.chipStrokeColor != paramColorStateList)
    {
      this.chipStrokeColor = paramColorStateList;
      if (this.isShapeThemingEnabled) {
        setStrokeColor(paramColorStateList);
      }
      onStateChange(getState());
    }
  }
  
  public void setChipStrokeColorResource(int paramInt)
  {
    setChipStrokeColor(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  public void setChipStrokeWidth(float paramFloat)
  {
    if (this.chipStrokeWidth != paramFloat)
    {
      this.chipStrokeWidth = paramFloat;
      this.chipPaint.setStrokeWidth(paramFloat);
      if (this.isShapeThemingEnabled) {
        super.setStrokeWidth(paramFloat);
      }
      invalidateSelf();
    }
  }
  
  public void setChipStrokeWidthResource(int paramInt)
  {
    setChipStrokeWidth(this.context.getResources().getDimension(paramInt));
  }
  
  public void setCloseIcon(Drawable paramDrawable)
  {
    Drawable localDrawable = getCloseIcon();
    if (localDrawable != paramDrawable)
    {
      float f2 = calculateCloseIconWidth();
      if (paramDrawable != null) {
        paramDrawable = DrawableCompat.wrap(paramDrawable).mutate();
      } else {
        paramDrawable = null;
      }
      this.closeIcon = paramDrawable;
      if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
        updateFrameworkCloseIconRipple();
      }
      float f1 = calculateCloseIconWidth();
      unapplyChildDrawable(localDrawable);
      if (showsCloseIcon()) {
        applyChildDrawable(this.closeIcon);
      }
      invalidateSelf();
      if (f2 != f1) {
        onSizeChange();
      }
    }
  }
  
  public void setCloseIconContentDescription(CharSequence paramCharSequence)
  {
    if (this.closeIconContentDescription != paramCharSequence)
    {
      this.closeIconContentDescription = BidiFormatter.getInstance().unicodeWrap(paramCharSequence);
      invalidateSelf();
    }
  }
  
  @Deprecated
  public void setCloseIconEnabled(boolean paramBoolean)
  {
    setCloseIconVisible(paramBoolean);
  }
  
  @Deprecated
  public void setCloseIconEnabledResource(int paramInt)
  {
    setCloseIconVisible(paramInt);
  }
  
  public void setCloseIconEndPadding(float paramFloat)
  {
    if (this.closeIconEndPadding != paramFloat)
    {
      this.closeIconEndPadding = paramFloat;
      invalidateSelf();
      if (showsCloseIcon()) {
        onSizeChange();
      }
    }
  }
  
  public void setCloseIconEndPaddingResource(int paramInt)
  {
    setCloseIconEndPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setCloseIconResource(int paramInt)
  {
    setCloseIcon(AppCompatResources.getDrawable(this.context, paramInt));
  }
  
  public void setCloseIconSize(float paramFloat)
  {
    if (this.closeIconSize != paramFloat)
    {
      this.closeIconSize = paramFloat;
      invalidateSelf();
      if (showsCloseIcon()) {
        onSizeChange();
      }
    }
  }
  
  public void setCloseIconSizeResource(int paramInt)
  {
    setCloseIconSize(this.context.getResources().getDimension(paramInt));
  }
  
  public void setCloseIconStartPadding(float paramFloat)
  {
    if (this.closeIconStartPadding != paramFloat)
    {
      this.closeIconStartPadding = paramFloat;
      invalidateSelf();
      if (showsCloseIcon()) {
        onSizeChange();
      }
    }
  }
  
  public void setCloseIconStartPaddingResource(int paramInt)
  {
    setCloseIconStartPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public boolean setCloseIconState(int[] paramArrayOfInt)
  {
    if (!Arrays.equals(this.closeIconStateSet, paramArrayOfInt))
    {
      this.closeIconStateSet = paramArrayOfInt;
      if (showsCloseIcon()) {
        return onStateChange(getState(), paramArrayOfInt);
      }
    }
    return false;
  }
  
  public void setCloseIconTint(ColorStateList paramColorStateList)
  {
    if (this.closeIconTint != paramColorStateList)
    {
      this.closeIconTint = paramColorStateList;
      if (showsCloseIcon()) {
        DrawableCompat.setTintList(this.closeIcon, paramColorStateList);
      }
      onStateChange(getState());
    }
  }
  
  public void setCloseIconTintResource(int paramInt)
  {
    setCloseIconTint(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  public void setCloseIconVisible(int paramInt)
  {
    setCloseIconVisible(this.context.getResources().getBoolean(paramInt));
  }
  
  public void setCloseIconVisible(boolean paramBoolean)
  {
    if (this.closeIconVisible != paramBoolean)
    {
      boolean bool = showsCloseIcon();
      this.closeIconVisible = paramBoolean;
      paramBoolean = showsCloseIcon();
      int i;
      if (bool != paramBoolean) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramBoolean) {
          applyChildDrawable(this.closeIcon);
        } else {
          unapplyChildDrawable(this.closeIcon);
        }
        invalidateSelf();
        onSizeChange();
      }
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    if (this.colorFilter != paramColorFilter)
    {
      this.colorFilter = paramColorFilter;
      invalidateSelf();
    }
  }
  
  public void setDelegate(Delegate paramDelegate)
  {
    this.delegate = new WeakReference(paramDelegate);
  }
  
  public void setEllipsize(TextUtils.TruncateAt paramTruncateAt)
  {
    this.truncateAt = paramTruncateAt;
  }
  
  public void setHideMotionSpec(MotionSpec paramMotionSpec)
  {
    this.hideMotionSpec = paramMotionSpec;
  }
  
  public void setHideMotionSpecResource(int paramInt)
  {
    setHideMotionSpec(MotionSpec.createFromResource(this.context, paramInt));
  }
  
  public void setIconEndPadding(float paramFloat)
  {
    if (this.iconEndPadding != paramFloat)
    {
      float f = calculateChipIconWidth();
      this.iconEndPadding = paramFloat;
      paramFloat = calculateChipIconWidth();
      invalidateSelf();
      if (f != paramFloat) {
        onSizeChange();
      }
    }
  }
  
  public void setIconEndPaddingResource(int paramInt)
  {
    setIconEndPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setIconStartPadding(float paramFloat)
  {
    if (this.iconStartPadding != paramFloat)
    {
      float f = calculateChipIconWidth();
      this.iconStartPadding = paramFloat;
      paramFloat = calculateChipIconWidth();
      invalidateSelf();
      if (f != paramFloat) {
        onSizeChange();
      }
    }
  }
  
  public void setIconStartPaddingResource(int paramInt)
  {
    setIconStartPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setMaxWidth(int paramInt)
  {
    this.maxWidth = paramInt;
  }
  
  public void setRippleColor(ColorStateList paramColorStateList)
  {
    if (this.rippleColor != paramColorStateList)
    {
      this.rippleColor = paramColorStateList;
      updateCompatRippleColor();
      onStateChange(getState());
    }
  }
  
  public void setRippleColorResource(int paramInt)
  {
    setRippleColor(AppCompatResources.getColorStateList(this.context, paramInt));
  }
  
  void setShouldDrawText(boolean paramBoolean)
  {
    this.shouldDrawText = paramBoolean;
  }
  
  public void setShowMotionSpec(MotionSpec paramMotionSpec)
  {
    this.showMotionSpec = paramMotionSpec;
  }
  
  public void setShowMotionSpecResource(int paramInt)
  {
    setShowMotionSpec(MotionSpec.createFromResource(this.context, paramInt));
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    Object localObject = paramCharSequence;
    if (paramCharSequence == null) {
      localObject = "";
    }
    if (!TextUtils.equals(this.text, (CharSequence)localObject))
    {
      this.text = ((CharSequence)localObject);
      this.textDrawableHelper.setTextWidthDirty(true);
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setTextAppearance(TextAppearance paramTextAppearance)
  {
    this.textDrawableHelper.setTextAppearance(paramTextAppearance, this.context);
  }
  
  public void setTextAppearanceResource(int paramInt)
  {
    setTextAppearance(new TextAppearance(this.context, paramInt));
  }
  
  public void setTextColor(int paramInt)
  {
    setTextColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    TextAppearance localTextAppearance = getTextAppearance();
    if (localTextAppearance != null)
    {
      localTextAppearance.setTextColor(paramColorStateList);
      invalidateSelf();
    }
  }
  
  public void setTextEndPadding(float paramFloat)
  {
    if (this.textEndPadding != paramFloat)
    {
      this.textEndPadding = paramFloat;
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setTextEndPaddingResource(int paramInt)
  {
    setTextEndPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setTextResource(int paramInt)
  {
    setText(this.context.getResources().getString(paramInt));
  }
  
  public void setTextSize(float paramFloat)
  {
    TextAppearance localTextAppearance = getTextAppearance();
    if (localTextAppearance != null)
    {
      localTextAppearance.setTextSize(paramFloat);
      this.textDrawableHelper.getTextPaint().setTextSize(paramFloat);
      onTextSizeChange();
    }
  }
  
  public void setTextStartPadding(float paramFloat)
  {
    if (this.textStartPadding != paramFloat)
    {
      this.textStartPadding = paramFloat;
      invalidateSelf();
      onSizeChange();
    }
  }
  
  public void setTextStartPaddingResource(int paramInt)
  {
    setTextStartPadding(this.context.getResources().getDimension(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    if (this.tint != paramColorStateList)
    {
      this.tint = paramColorStateList;
      onStateChange(getState());
    }
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    if (this.tintMode != paramMode)
    {
      this.tintMode = paramMode;
      this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, paramMode);
      invalidateSelf();
    }
  }
  
  public void setUseCompatRipple(boolean paramBoolean)
  {
    if (this.useCompatRipple != paramBoolean)
    {
      this.useCompatRipple = paramBoolean;
      updateCompatRippleColor();
      onStateChange(getState());
    }
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool2 = super.setVisible(paramBoolean1, paramBoolean2);
    boolean bool1 = bool2;
    if (showsChipIcon()) {
      bool1 = bool2 | this.chipIcon.setVisible(paramBoolean1, paramBoolean2);
    }
    bool2 = bool1;
    if (showsCheckedIcon()) {
      bool2 = bool1 | this.checkedIcon.setVisible(paramBoolean1, paramBoolean2);
    }
    bool1 = bool2;
    if (showsCloseIcon()) {
      bool1 = bool2 | this.closeIcon.setVisible(paramBoolean1, paramBoolean2);
    }
    if (bool1) {
      invalidateSelf();
    }
    return bool1;
  }
  
  boolean shouldDrawText()
  {
    return this.shouldDrawText;
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    paramDrawable = getCallback();
    if (paramDrawable != null) {
      paramDrawable.unscheduleDrawable(this, paramRunnable);
    }
  }
  
  public static abstract interface Delegate
  {
    public abstract void onChipDrawableSizeChange();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/chip/ChipDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */