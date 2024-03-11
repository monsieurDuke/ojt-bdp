package com.google.android.material.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import com.google.android.material.R.attr;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;

public class MaterialShapeDrawable
  extends Drawable
  implements TintAwareDrawable, Shapeable
{
  public static final int SHADOW_COMPAT_MODE_ALWAYS = 2;
  public static final int SHADOW_COMPAT_MODE_DEFAULT = 0;
  public static final int SHADOW_COMPAT_MODE_NEVER = 1;
  private static final float SHADOW_OFFSET_MULTIPLIER = 0.25F;
  private static final float SHADOW_RADIUS_MULTIPLIER = 0.75F;
  private static final String TAG = MaterialShapeDrawable.class.getSimpleName();
  private static final Paint clearPaint;
  private final BitSet containsIncompatibleShadowOp = new BitSet(8);
  private final ShapePath.ShadowCompatOperation[] cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
  private MaterialShapeDrawableState drawableState;
  private final ShapePath.ShadowCompatOperation[] edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
  private final Paint fillPaint;
  private final RectF insetRectF = new RectF();
  private final Matrix matrix = new Matrix();
  private final Path path = new Path();
  private final RectF pathBounds;
  private boolean pathDirty;
  private final Path pathInsetByStroke = new Path();
  private final ShapeAppearancePathProvider pathProvider;
  private final ShapeAppearancePathProvider.PathListener pathShadowListener;
  private final RectF rectF = new RectF();
  private int resolvedTintColor;
  private final Region scratchRegion = new Region();
  private boolean shadowBitmapDrawingEnable;
  private final ShadowRenderer shadowRenderer;
  private final Paint strokePaint;
  private ShapeAppearanceModel strokeShapeAppearance;
  private PorterDuffColorFilter strokeTintFilter;
  private PorterDuffColorFilter tintFilter;
  private final Region transparentRegion = new Region();
  
  static
  {
    Paint localPaint = new Paint(1);
    clearPaint = localPaint;
    localPaint.setColor(-1);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
  }
  
  public MaterialShapeDrawable()
  {
    this(new ShapeAppearanceModel());
  }
  
  public MaterialShapeDrawable(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(ShapeAppearanceModel.builder(paramContext, paramAttributeSet, paramInt1, paramInt2).build());
  }
  
  private MaterialShapeDrawable(MaterialShapeDrawableState paramMaterialShapeDrawableState)
  {
    Paint localPaint1 = new Paint(1);
    this.fillPaint = localPaint1;
    Paint localPaint2 = new Paint(1);
    this.strokePaint = localPaint2;
    this.shadowRenderer = new ShadowRenderer();
    ShapeAppearancePathProvider localShapeAppearancePathProvider;
    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
      localShapeAppearancePathProvider = ShapeAppearancePathProvider.getInstance();
    } else {
      localShapeAppearancePathProvider = new ShapeAppearancePathProvider();
    }
    this.pathProvider = localShapeAppearancePathProvider;
    this.pathBounds = new RectF();
    this.shadowBitmapDrawingEnable = true;
    this.drawableState = paramMaterialShapeDrawableState;
    localPaint2.setStyle(Paint.Style.STROKE);
    localPaint1.setStyle(Paint.Style.FILL);
    updateTintFilter();
    updateColorsForState(getState());
    this.pathShadowListener = new ShapeAppearancePathProvider.PathListener()
    {
      public void onCornerPathCreated(ShapePath paramAnonymousShapePath, Matrix paramAnonymousMatrix, int paramAnonymousInt)
      {
        MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(paramAnonymousInt, paramAnonymousShapePath.containsIncompatibleShadowOp());
        MaterialShapeDrawable.this.cornerShadowOperation[paramAnonymousInt] = paramAnonymousShapePath.createShadowCompatOperation(paramAnonymousMatrix);
      }
      
      public void onEdgePathCreated(ShapePath paramAnonymousShapePath, Matrix paramAnonymousMatrix, int paramAnonymousInt)
      {
        MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(paramAnonymousInt + 4, paramAnonymousShapePath.containsIncompatibleShadowOp());
        MaterialShapeDrawable.this.edgeShadowOperation[paramAnonymousInt] = paramAnonymousShapePath.createShadowCompatOperation(paramAnonymousMatrix);
      }
    };
  }
  
  public MaterialShapeDrawable(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this(new MaterialShapeDrawableState(paramShapeAppearanceModel, null));
  }
  
  @Deprecated
  public MaterialShapeDrawable(ShapePathModel paramShapePathModel)
  {
    this(paramShapePathModel);
  }
  
  private PorterDuffColorFilter calculatePaintColorTintFilter(Paint paramPaint, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      int i = paramPaint.getColor();
      int j = compositeElevationOverlayIfNeeded(i);
      this.resolvedTintColor = j;
      if (j != i) {
        return new PorterDuffColorFilter(j, PorterDuff.Mode.SRC_IN);
      }
    }
    return null;
  }
  
  private void calculatePath(RectF paramRectF, Path paramPath)
  {
    calculatePathForSize(paramRectF, paramPath);
    if (this.drawableState.scale != 1.0F)
    {
      this.matrix.reset();
      this.matrix.setScale(this.drawableState.scale, this.drawableState.scale, paramRectF.width() / 2.0F, paramRectF.height() / 2.0F);
      paramPath.transform(this.matrix);
    }
    paramPath.computeBounds(this.pathBounds, true);
  }
  
  private void calculateStrokePath()
  {
    final float f = -getStrokeInsetLength();
    ShapeAppearanceModel localShapeAppearanceModel = getShapeAppearanceModel().withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator()
    {
      public CornerSize apply(CornerSize paramAnonymousCornerSize)
      {
        if (!(paramAnonymousCornerSize instanceof RelativeCornerSize)) {
          paramAnonymousCornerSize = new AdjustedCornerSize(f, paramAnonymousCornerSize);
        }
        return paramAnonymousCornerSize;
      }
    });
    this.strokeShapeAppearance = localShapeAppearanceModel;
    this.pathProvider.calculatePath(localShapeAppearanceModel, this.drawableState.interpolation, getBoundsInsetByStroke(), this.pathInsetByStroke);
  }
  
  private PorterDuffColorFilter calculateTintColorTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, boolean paramBoolean)
  {
    int j = paramColorStateList.getColorForState(getState(), 0);
    int i = j;
    if (paramBoolean) {
      i = compositeElevationOverlayIfNeeded(j);
    }
    this.resolvedTintColor = i;
    return new PorterDuffColorFilter(i, paramMode);
  }
  
  private PorterDuffColorFilter calculateTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, Paint paramPaint, boolean paramBoolean)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      paramColorStateList = calculateTintColorTintFilter(paramColorStateList, paramMode, paramBoolean);
    } else {
      paramColorStateList = calculatePaintColorTintFilter(paramPaint, paramBoolean);
    }
    return paramColorStateList;
  }
  
  public static MaterialShapeDrawable createWithElevationOverlay(Context paramContext)
  {
    return createWithElevationOverlay(paramContext, 0.0F);
  }
  
  public static MaterialShapeDrawable createWithElevationOverlay(Context paramContext, float paramFloat)
  {
    int i = MaterialColors.getColor(paramContext, R.attr.colorSurface, MaterialShapeDrawable.class.getSimpleName());
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
    localMaterialShapeDrawable.initializeElevationOverlay(paramContext);
    localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(i));
    localMaterialShapeDrawable.setElevation(paramFloat);
    return localMaterialShapeDrawable;
  }
  
  private void drawCompatShadow(Canvas paramCanvas)
  {
    if (this.containsIncompatibleShadowOp.cardinality() > 0) {
      Log.w(TAG, "Compatibility shadow requested but can't be drawn for all operations in this shape.");
    }
    if (this.drawableState.shadowCompatOffset != 0) {
      paramCanvas.drawPath(this.path, this.shadowRenderer.getShadowPaint());
    }
    for (int i = 0; i < 4; i++)
    {
      this.cornerShadowOperation[i].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, paramCanvas);
      this.edgeShadowOperation[i].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, paramCanvas);
    }
    if (this.shadowBitmapDrawingEnable)
    {
      i = getShadowOffsetX();
      int j = getShadowOffsetY();
      paramCanvas.translate(-i, -j);
      paramCanvas.drawPath(this.path, clearPaint);
      paramCanvas.translate(i, j);
    }
  }
  
  private void drawFillShape(Canvas paramCanvas)
  {
    drawShape(paramCanvas, this.fillPaint, this.path, this.drawableState.shapeAppearanceModel, getBoundsAsRectF());
  }
  
  private void drawShape(Canvas paramCanvas, Paint paramPaint, Path paramPath, ShapeAppearanceModel paramShapeAppearanceModel, RectF paramRectF)
  {
    if (paramShapeAppearanceModel.isRoundRect(paramRectF))
    {
      float f = paramShapeAppearanceModel.getTopRightCornerSize().getCornerSize(paramRectF) * this.drawableState.interpolation;
      paramCanvas.drawRoundRect(paramRectF, f, f, paramPaint);
    }
    else
    {
      paramCanvas.drawPath(paramPath, paramPaint);
    }
  }
  
  private RectF getBoundsInsetByStroke()
  {
    this.insetRectF.set(getBoundsAsRectF());
    float f = getStrokeInsetLength();
    this.insetRectF.inset(f, f);
    return this.insetRectF;
  }
  
  private float getStrokeInsetLength()
  {
    if (hasStroke()) {
      return this.strokePaint.getStrokeWidth() / 2.0F;
    }
    return 0.0F;
  }
  
  private boolean hasCompatShadow()
  {
    int i = this.drawableState.shadowCompatMode;
    boolean bool = true;
    if ((i == 1) || (this.drawableState.shadowCompatRadius <= 0) || ((this.drawableState.shadowCompatMode == 2) || (!requiresCompatShadow()))) {
      bool = false;
    }
    return bool;
  }
  
  private boolean hasFill()
  {
    boolean bool;
    if ((this.drawableState.paintStyle != Paint.Style.FILL_AND_STROKE) && (this.drawableState.paintStyle != Paint.Style.FILL)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean hasStroke()
  {
    boolean bool;
    if (((this.drawableState.paintStyle == Paint.Style.FILL_AND_STROKE) || (this.drawableState.paintStyle == Paint.Style.STROKE)) && (this.strokePaint.getStrokeWidth() > 0.0F)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void invalidateSelfIgnoreShape()
  {
    super.invalidateSelf();
  }
  
  private void maybeDrawCompatShadow(Canvas paramCanvas)
  {
    if (!hasCompatShadow()) {
      return;
    }
    paramCanvas.save();
    prepareCanvasForShadow(paramCanvas);
    if (!this.shadowBitmapDrawingEnable)
    {
      drawCompatShadow(paramCanvas);
      paramCanvas.restore();
      return;
    }
    int i = (int)(this.pathBounds.width() - getBounds().width());
    int j = (int)(this.pathBounds.height() - getBounds().height());
    if ((i >= 0) && (j >= 0))
    {
      Bitmap localBitmap = Bitmap.createBitmap((int)this.pathBounds.width() + this.drawableState.shadowCompatRadius * 2 + i, (int)this.pathBounds.height() + this.drawableState.shadowCompatRadius * 2 + j, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      float f2 = getBounds().left - this.drawableState.shadowCompatRadius - i;
      float f1 = getBounds().top - this.drawableState.shadowCompatRadius - j;
      localCanvas.translate(-f2, -f1);
      drawCompatShadow(localCanvas);
      paramCanvas.drawBitmap(localBitmap, f2, f1, null);
      localBitmap.recycle();
      paramCanvas.restore();
      return;
    }
    throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
  }
  
  private static int modulateAlpha(int paramInt1, int paramInt2)
  {
    return paramInt1 * ((paramInt2 >>> 7) + paramInt2) >>> 8;
  }
  
  private void prepareCanvasForShadow(Canvas paramCanvas)
  {
    int j = getShadowOffsetX();
    int i = getShadowOffsetY();
    if ((Build.VERSION.SDK_INT < 21) && (this.shadowBitmapDrawingEnable))
    {
      Rect localRect = paramCanvas.getClipBounds();
      localRect.inset(-this.drawableState.shadowCompatRadius, -this.drawableState.shadowCompatRadius);
      localRect.offset(j, i);
      paramCanvas.clipRect(localRect, Region.Op.REPLACE);
    }
    paramCanvas.translate(j, i);
  }
  
  private boolean updateColorsForState(int[] paramArrayOfInt)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    int i;
    int j;
    if (this.drawableState.fillColor != null)
    {
      i = this.fillPaint.getColor();
      j = this.drawableState.fillColor.getColorForState(paramArrayOfInt, i);
      bool1 = bool2;
      if (i != j)
      {
        this.fillPaint.setColor(j);
        bool1 = true;
      }
    }
    bool2 = bool1;
    if (this.drawableState.strokeColor != null)
    {
      i = this.strokePaint.getColor();
      j = this.drawableState.strokeColor.getColorForState(paramArrayOfInt, i);
      bool2 = bool1;
      if (i != j)
      {
        this.strokePaint.setColor(j);
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private boolean updateTintFilter()
  {
    PorterDuffColorFilter localPorterDuffColorFilter1 = this.tintFilter;
    PorterDuffColorFilter localPorterDuffColorFilter2 = this.strokeTintFilter;
    ColorStateList localColorStateList = this.drawableState.tintList;
    PorterDuff.Mode localMode = this.drawableState.tintMode;
    Paint localPaint = this.fillPaint;
    boolean bool = true;
    this.tintFilter = calculateTintFilter(localColorStateList, localMode, localPaint, true);
    this.strokeTintFilter = calculateTintFilter(this.drawableState.strokeTintList, this.drawableState.tintMode, this.strokePaint, false);
    if (this.drawableState.useTintColorForShadow) {
      this.shadowRenderer.setShadowColor(this.drawableState.tintList.getColorForState(getState(), 0));
    }
    if ((ObjectsCompat.equals(localPorterDuffColorFilter1, this.tintFilter)) && (ObjectsCompat.equals(localPorterDuffColorFilter2, this.strokeTintFilter))) {
      bool = false;
    }
    return bool;
  }
  
  private void updateZ()
  {
    float f = getZ();
    this.drawableState.shadowCompatRadius = ((int)Math.ceil(0.75F * f));
    this.drawableState.shadowCompatOffset = ((int)Math.ceil(0.25F * f));
    updateTintFilter();
    invalidateSelfIgnoreShape();
  }
  
  protected final void calculatePathForSize(RectF paramRectF, Path paramPath)
  {
    this.pathProvider.calculatePath(this.drawableState.shapeAppearanceModel, this.drawableState.interpolation, paramRectF, this.pathShadowListener, paramPath);
  }
  
  protected int compositeElevationOverlayIfNeeded(int paramInt)
  {
    float f2 = getZ();
    float f1 = getParentAbsoluteElevation();
    if (this.drawableState.elevationOverlayProvider != null) {
      paramInt = this.drawableState.elevationOverlayProvider.compositeOverlayIfNeeded(paramInt, f2 + f1);
    }
    return paramInt;
  }
  
  public void draw(Canvas paramCanvas)
  {
    this.fillPaint.setColorFilter(this.tintFilter);
    int i = this.fillPaint.getAlpha();
    this.fillPaint.setAlpha(modulateAlpha(i, this.drawableState.alpha));
    this.strokePaint.setColorFilter(this.strokeTintFilter);
    this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
    int j = this.strokePaint.getAlpha();
    this.strokePaint.setAlpha(modulateAlpha(j, this.drawableState.alpha));
    if (this.pathDirty)
    {
      calculateStrokePath();
      calculatePath(getBoundsAsRectF(), this.path);
      this.pathDirty = false;
    }
    maybeDrawCompatShadow(paramCanvas);
    if (hasFill()) {
      drawFillShape(paramCanvas);
    }
    if (hasStroke()) {
      drawStrokeShape(paramCanvas);
    }
    this.fillPaint.setAlpha(i);
    this.strokePaint.setAlpha(j);
  }
  
  protected void drawShape(Canvas paramCanvas, Paint paramPaint, Path paramPath, RectF paramRectF)
  {
    drawShape(paramCanvas, paramPaint, paramPath, this.drawableState.shapeAppearanceModel, paramRectF);
  }
  
  protected void drawStrokeShape(Canvas paramCanvas)
  {
    drawShape(paramCanvas, this.strokePaint, this.pathInsetByStroke, this.strokeShapeAppearance, getBoundsInsetByStroke());
  }
  
  public int getAlpha()
  {
    return this.drawableState.alpha;
  }
  
  public float getBottomLeftCornerResolvedSize()
  {
    return this.drawableState.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(getBoundsAsRectF());
  }
  
  public float getBottomRightCornerResolvedSize()
  {
    return this.drawableState.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(getBoundsAsRectF());
  }
  
  protected RectF getBoundsAsRectF()
  {
    this.rectF.set(getBounds());
    return this.rectF;
  }
  
  public Drawable.ConstantState getConstantState()
  {
    return this.drawableState;
  }
  
  public float getElevation()
  {
    return this.drawableState.elevation;
  }
  
  public ColorStateList getFillColor()
  {
    return this.drawableState.fillColor;
  }
  
  public float getInterpolation()
  {
    return this.drawableState.interpolation;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  public void getOutline(Outline paramOutline)
  {
    if (this.drawableState.shadowCompatMode == 2) {
      return;
    }
    if (isRoundRect())
    {
      float f1 = getTopLeftCornerResolvedSize();
      float f2 = this.drawableState.interpolation;
      paramOutline.setRoundRect(getBounds(), f1 * f2);
      return;
    }
    calculatePath(getBoundsAsRectF(), this.path);
    if ((this.path.isConvex()) || (Build.VERSION.SDK_INT >= 29)) {
      try
      {
        paramOutline.setConvexPath(this.path);
      }
      catch (IllegalArgumentException paramOutline) {}
    }
  }
  
  public boolean getPadding(Rect paramRect)
  {
    if (this.drawableState.padding != null)
    {
      paramRect.set(this.drawableState.padding);
      return true;
    }
    return super.getPadding(paramRect);
  }
  
  public Paint.Style getPaintStyle()
  {
    return this.drawableState.paintStyle;
  }
  
  public float getParentAbsoluteElevation()
  {
    return this.drawableState.parentAbsoluteElevation;
  }
  
  @Deprecated
  public void getPathForSize(int paramInt1, int paramInt2, Path paramPath)
  {
    calculatePathForSize(new RectF(0.0F, 0.0F, paramInt1, paramInt2), paramPath);
  }
  
  public int getResolvedTintColor()
  {
    return this.resolvedTintColor;
  }
  
  public float getScale()
  {
    return this.drawableState.scale;
  }
  
  public int getShadowCompatRotation()
  {
    return this.drawableState.shadowCompatRotation;
  }
  
  public int getShadowCompatibilityMode()
  {
    return this.drawableState.shadowCompatMode;
  }
  
  @Deprecated
  public int getShadowElevation()
  {
    return (int)getElevation();
  }
  
  public int getShadowOffsetX()
  {
    return (int)(this.drawableState.shadowCompatOffset * Math.sin(Math.toRadians(this.drawableState.shadowCompatRotation)));
  }
  
  public int getShadowOffsetY()
  {
    return (int)(this.drawableState.shadowCompatOffset * Math.cos(Math.toRadians(this.drawableState.shadowCompatRotation)));
  }
  
  public int getShadowRadius()
  {
    return this.drawableState.shadowCompatRadius;
  }
  
  public int getShadowVerticalOffset()
  {
    return this.drawableState.shadowCompatOffset;
  }
  
  public ShapeAppearanceModel getShapeAppearanceModel()
  {
    return this.drawableState.shapeAppearanceModel;
  }
  
  @Deprecated
  public ShapePathModel getShapedViewModel()
  {
    Object localObject = getShapeAppearanceModel();
    if ((localObject instanceof ShapePathModel)) {
      localObject = (ShapePathModel)localObject;
    } else {
      localObject = null;
    }
    return (ShapePathModel)localObject;
  }
  
  public ColorStateList getStrokeColor()
  {
    return this.drawableState.strokeColor;
  }
  
  public ColorStateList getStrokeTintList()
  {
    return this.drawableState.strokeTintList;
  }
  
  public float getStrokeWidth()
  {
    return this.drawableState.strokeWidth;
  }
  
  public ColorStateList getTintList()
  {
    return this.drawableState.tintList;
  }
  
  public float getTopLeftCornerResolvedSize()
  {
    return this.drawableState.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
  }
  
  public float getTopRightCornerResolvedSize()
  {
    return this.drawableState.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(getBoundsAsRectF());
  }
  
  public float getTranslationZ()
  {
    return this.drawableState.translationZ;
  }
  
  public Region getTransparentRegion()
  {
    Rect localRect = getBounds();
    this.transparentRegion.set(localRect);
    calculatePath(getBoundsAsRectF(), this.path);
    this.scratchRegion.setPath(this.path, this.transparentRegion);
    this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
    return this.transparentRegion;
  }
  
  public float getZ()
  {
    return getElevation() + getTranslationZ();
  }
  
  public void initializeElevationOverlay(Context paramContext)
  {
    this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(paramContext);
    updateZ();
  }
  
  public void invalidateSelf()
  {
    this.pathDirty = true;
    super.invalidateSelf();
  }
  
  public boolean isElevationOverlayEnabled()
  {
    boolean bool;
    if ((this.drawableState.elevationOverlayProvider != null) && (this.drawableState.elevationOverlayProvider.isThemeElevationOverlayEnabled())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isElevationOverlayInitialized()
  {
    boolean bool;
    if (this.drawableState.elevationOverlayProvider != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isPointInTransparentRegion(int paramInt1, int paramInt2)
  {
    return getTransparentRegion().contains(paramInt1, paramInt2);
  }
  
  public boolean isRoundRect()
  {
    return this.drawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF());
  }
  
  @Deprecated
  public boolean isShadowEnabled()
  {
    boolean bool;
    if ((this.drawableState.shadowCompatMode != 0) && (this.drawableState.shadowCompatMode != 2)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isStateful()
  {
    boolean bool;
    if ((!super.isStateful()) && ((this.drawableState.tintList == null) || (!this.drawableState.tintList.isStateful())) && ((this.drawableState.strokeTintList == null) || (!this.drawableState.strokeTintList.isStateful())) && ((this.drawableState.strokeColor == null) || (!this.drawableState.strokeColor.isStateful())) && ((this.drawableState.fillColor == null) || (!this.drawableState.fillColor.isStateful()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public Drawable mutate()
  {
    this.drawableState = new MaterialShapeDrawableState(this.drawableState);
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    this.pathDirty = true;
    super.onBoundsChange(paramRect);
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    boolean bool2 = updateColorsForState(paramArrayOfInt);
    boolean bool1 = updateTintFilter();
    if ((!bool2) && (!bool1)) {
      bool1 = false;
    } else {
      bool1 = true;
    }
    if (bool1) {
      invalidateSelf();
    }
    return bool1;
  }
  
  public boolean requiresCompatShadow()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 21) && ((isRoundRect()) || (this.path.isConvex()) || (Build.VERSION.SDK_INT >= 29))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void setAlpha(int paramInt)
  {
    if (this.drawableState.alpha != paramInt)
    {
      this.drawableState.alpha = paramInt;
      invalidateSelfIgnoreShape();
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.drawableState.colorFilter = paramColorFilter;
    invalidateSelfIgnoreShape();
  }
  
  public void setCornerSize(float paramFloat)
  {
    setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(paramFloat));
  }
  
  public void setCornerSize(CornerSize paramCornerSize)
  {
    setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(paramCornerSize));
  }
  
  public void setEdgeIntersectionCheckEnable(boolean paramBoolean)
  {
    this.pathProvider.setEdgeIntersectionCheckEnable(paramBoolean);
  }
  
  public void setElevation(float paramFloat)
  {
    if (this.drawableState.elevation != paramFloat)
    {
      this.drawableState.elevation = paramFloat;
      updateZ();
    }
  }
  
  public void setFillColor(ColorStateList paramColorStateList)
  {
    if (this.drawableState.fillColor != paramColorStateList)
    {
      this.drawableState.fillColor = paramColorStateList;
      onStateChange(getState());
    }
  }
  
  public void setInterpolation(float paramFloat)
  {
    if (this.drawableState.interpolation != paramFloat)
    {
      this.drawableState.interpolation = paramFloat;
      this.pathDirty = true;
      invalidateSelf();
    }
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.drawableState.padding == null) {
      this.drawableState.padding = new Rect();
    }
    this.drawableState.padding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    invalidateSelf();
  }
  
  public void setPaintStyle(Paint.Style paramStyle)
  {
    this.drawableState.paintStyle = paramStyle;
    invalidateSelfIgnoreShape();
  }
  
  public void setParentAbsoluteElevation(float paramFloat)
  {
    if (this.drawableState.parentAbsoluteElevation != paramFloat)
    {
      this.drawableState.parentAbsoluteElevation = paramFloat;
      updateZ();
    }
  }
  
  public void setScale(float paramFloat)
  {
    if (this.drawableState.scale != paramFloat)
    {
      this.drawableState.scale = paramFloat;
      invalidateSelf();
    }
  }
  
  public void setShadowBitmapDrawingEnable(boolean paramBoolean)
  {
    this.shadowBitmapDrawingEnable = paramBoolean;
  }
  
  public void setShadowColor(int paramInt)
  {
    this.shadowRenderer.setShadowColor(paramInt);
    this.drawableState.useTintColorForShadow = false;
    invalidateSelfIgnoreShape();
  }
  
  public void setShadowCompatRotation(int paramInt)
  {
    if (this.drawableState.shadowCompatRotation != paramInt)
    {
      this.drawableState.shadowCompatRotation = paramInt;
      invalidateSelfIgnoreShape();
    }
  }
  
  public void setShadowCompatibilityMode(int paramInt)
  {
    if (this.drawableState.shadowCompatMode != paramInt)
    {
      this.drawableState.shadowCompatMode = paramInt;
      invalidateSelfIgnoreShape();
    }
  }
  
  @Deprecated
  public void setShadowElevation(int paramInt)
  {
    setElevation(paramInt);
  }
  
  @Deprecated
  public void setShadowEnabled(boolean paramBoolean)
  {
    setShadowCompatibilityMode(paramBoolean ^ true);
  }
  
  @Deprecated
  public void setShadowRadius(int paramInt)
  {
    this.drawableState.shadowCompatRadius = paramInt;
  }
  
  public void setShadowVerticalOffset(int paramInt)
  {
    if (this.drawableState.shadowCompatOffset != paramInt)
    {
      this.drawableState.shadowCompatOffset = paramInt;
      invalidateSelfIgnoreShape();
    }
  }
  
  public void setShapeAppearanceModel(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.drawableState.shapeAppearanceModel = paramShapeAppearanceModel;
    invalidateSelf();
  }
  
  @Deprecated
  public void setShapedViewModel(ShapePathModel paramShapePathModel)
  {
    setShapeAppearanceModel(paramShapePathModel);
  }
  
  public void setStroke(float paramFloat, int paramInt)
  {
    setStrokeWidth(paramFloat);
    setStrokeColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setStroke(float paramFloat, ColorStateList paramColorStateList)
  {
    setStrokeWidth(paramFloat);
    setStrokeColor(paramColorStateList);
  }
  
  public void setStrokeColor(ColorStateList paramColorStateList)
  {
    if (this.drawableState.strokeColor != paramColorStateList)
    {
      this.drawableState.strokeColor = paramColorStateList;
      onStateChange(getState());
    }
  }
  
  public void setStrokeTint(int paramInt)
  {
    setStrokeTint(ColorStateList.valueOf(paramInt));
  }
  
  public void setStrokeTint(ColorStateList paramColorStateList)
  {
    this.drawableState.strokeTintList = paramColorStateList;
    updateTintFilter();
    invalidateSelfIgnoreShape();
  }
  
  public void setStrokeWidth(float paramFloat)
  {
    this.drawableState.strokeWidth = paramFloat;
    invalidateSelf();
  }
  
  public void setTint(int paramInt)
  {
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList)
  {
    this.drawableState.tintList = paramColorStateList;
    updateTintFilter();
    invalidateSelfIgnoreShape();
  }
  
  public void setTintMode(PorterDuff.Mode paramMode)
  {
    if (this.drawableState.tintMode != paramMode)
    {
      this.drawableState.tintMode = paramMode;
      updateTintFilter();
      invalidateSelfIgnoreShape();
    }
  }
  
  public void setTranslationZ(float paramFloat)
  {
    if (this.drawableState.translationZ != paramFloat)
    {
      this.drawableState.translationZ = paramFloat;
      updateZ();
    }
  }
  
  public void setUseTintColorForShadow(boolean paramBoolean)
  {
    if (this.drawableState.useTintColorForShadow != paramBoolean)
    {
      this.drawableState.useTintColorForShadow = paramBoolean;
      invalidateSelf();
    }
  }
  
  public void setZ(float paramFloat)
  {
    setTranslationZ(paramFloat - getElevation());
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface CompatibilityShadowMode {}
  
  static final class MaterialShapeDrawableState
    extends Drawable.ConstantState
  {
    public int alpha = 255;
    public ColorFilter colorFilter;
    public float elevation = 0.0F;
    public ElevationOverlayProvider elevationOverlayProvider;
    public ColorStateList fillColor = null;
    public float interpolation = 1.0F;
    public Rect padding = null;
    public Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;
    public float parentAbsoluteElevation = 0.0F;
    public float scale = 1.0F;
    public int shadowCompatMode = 0;
    public int shadowCompatOffset = 0;
    public int shadowCompatRadius = 0;
    public int shadowCompatRotation = 0;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorStateList strokeColor = null;
    public ColorStateList strokeTintList = null;
    public float strokeWidth;
    public ColorStateList tintList = null;
    public PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
    public float translationZ = 0.0F;
    public boolean useTintColorForShadow = false;
    
    public MaterialShapeDrawableState(MaterialShapeDrawableState paramMaterialShapeDrawableState)
    {
      this.shapeAppearanceModel = paramMaterialShapeDrawableState.shapeAppearanceModel;
      this.elevationOverlayProvider = paramMaterialShapeDrawableState.elevationOverlayProvider;
      this.strokeWidth = paramMaterialShapeDrawableState.strokeWidth;
      this.colorFilter = paramMaterialShapeDrawableState.colorFilter;
      this.fillColor = paramMaterialShapeDrawableState.fillColor;
      this.strokeColor = paramMaterialShapeDrawableState.strokeColor;
      this.tintMode = paramMaterialShapeDrawableState.tintMode;
      this.tintList = paramMaterialShapeDrawableState.tintList;
      this.alpha = paramMaterialShapeDrawableState.alpha;
      this.scale = paramMaterialShapeDrawableState.scale;
      this.shadowCompatOffset = paramMaterialShapeDrawableState.shadowCompatOffset;
      this.shadowCompatMode = paramMaterialShapeDrawableState.shadowCompatMode;
      this.useTintColorForShadow = paramMaterialShapeDrawableState.useTintColorForShadow;
      this.interpolation = paramMaterialShapeDrawableState.interpolation;
      this.parentAbsoluteElevation = paramMaterialShapeDrawableState.parentAbsoluteElevation;
      this.elevation = paramMaterialShapeDrawableState.elevation;
      this.translationZ = paramMaterialShapeDrawableState.translationZ;
      this.shadowCompatRadius = paramMaterialShapeDrawableState.shadowCompatRadius;
      this.shadowCompatRotation = paramMaterialShapeDrawableState.shadowCompatRotation;
      this.strokeTintList = paramMaterialShapeDrawableState.strokeTintList;
      this.paintStyle = paramMaterialShapeDrawableState.paintStyle;
      if (paramMaterialShapeDrawableState.padding != null) {
        this.padding = new Rect(paramMaterialShapeDrawableState.padding);
      }
    }
    
    public MaterialShapeDrawableState(ShapeAppearanceModel paramShapeAppearanceModel, ElevationOverlayProvider paramElevationOverlayProvider)
    {
      this.shapeAppearanceModel = paramShapeAppearanceModel;
      this.elevationOverlayProvider = paramElevationOverlayProvider;
    }
    
    public int getChangingConfigurations()
    {
      return 0;
    }
    
    public Drawable newDrawable()
    {
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable(this, null);
      MaterialShapeDrawable.access$402(localMaterialShapeDrawable, true);
      return localMaterialShapeDrawable;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/MaterialShapeDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */