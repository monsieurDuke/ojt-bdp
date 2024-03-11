package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.transition.ArcMotion;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.shape.Shapeable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public final class MaterialContainerTransform
  extends Transition
{
  private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS;
  private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS_ARC = new ProgressThresholdsGroup(new ProgressThresholds(0.1F, 0.4F), new ProgressThresholds(0.1F, 1.0F), new ProgressThresholds(0.1F, 1.0F), new ProgressThresholds(0.1F, 0.9F), null);
  private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS;
  private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS_ARC = new ProgressThresholdsGroup(new ProgressThresholds(0.6F, 0.9F), new ProgressThresholds(0.0F, 0.9F), new ProgressThresholds(0.0F, 0.9F), new ProgressThresholds(0.2F, 0.9F), null);
  private static final float ELEVATION_NOT_SET = -1.0F;
  public static final int FADE_MODE_CROSS = 2;
  public static final int FADE_MODE_IN = 0;
  public static final int FADE_MODE_OUT = 1;
  public static final int FADE_MODE_THROUGH = 3;
  public static final int FIT_MODE_AUTO = 0;
  public static final int FIT_MODE_HEIGHT = 2;
  public static final int FIT_MODE_WIDTH = 1;
  private static final String PROP_BOUNDS = "materialContainerTransition:bounds";
  private static final String PROP_SHAPE_APPEARANCE = "materialContainerTransition:shapeAppearance";
  private static final String TAG = MaterialContainerTransform.class.getSimpleName();
  public static final int TRANSITION_DIRECTION_AUTO = 0;
  public static final int TRANSITION_DIRECTION_ENTER = 1;
  public static final int TRANSITION_DIRECTION_RETURN = 2;
  private static final String[] TRANSITION_PROPS = { "materialContainerTransition:bounds", "materialContainerTransition:shapeAppearance" };
  private boolean appliedThemeValues;
  private int containerColor;
  private boolean drawDebugEnabled;
  private int drawingViewId;
  private boolean elevationShadowEnabled;
  private int endContainerColor;
  private float endElevation;
  private ShapeAppearanceModel endShapeAppearanceModel;
  private View endView;
  private int endViewId;
  private int fadeMode;
  private ProgressThresholds fadeProgressThresholds;
  private int fitMode;
  private boolean holdAtEndEnabled;
  private boolean pathMotionCustom;
  private ProgressThresholds scaleMaskProgressThresholds;
  private ProgressThresholds scaleProgressThresholds;
  private int scrimColor;
  private ProgressThresholds shapeMaskProgressThresholds;
  private int startContainerColor;
  private float startElevation;
  private ShapeAppearanceModel startShapeAppearanceModel;
  private View startView;
  private int startViewId;
  private int transitionDirection;
  
  static
  {
    DEFAULT_ENTER_THRESHOLDS = new ProgressThresholdsGroup(new ProgressThresholds(0.0F, 0.25F), new ProgressThresholds(0.0F, 1.0F), new ProgressThresholds(0.0F, 1.0F), new ProgressThresholds(0.0F, 0.75F), null);
    DEFAULT_RETURN_THRESHOLDS = new ProgressThresholdsGroup(new ProgressThresholds(0.6F, 0.9F), new ProgressThresholds(0.0F, 1.0F), new ProgressThresholds(0.0F, 0.9F), new ProgressThresholds(0.3F, 0.9F), null);
  }
  
  public MaterialContainerTransform()
  {
    boolean bool = false;
    this.drawDebugEnabled = false;
    this.holdAtEndEnabled = false;
    this.pathMotionCustom = false;
    this.appliedThemeValues = false;
    this.drawingViewId = 16908290;
    this.startViewId = -1;
    this.endViewId = -1;
    this.containerColor = 0;
    this.startContainerColor = 0;
    this.endContainerColor = 0;
    this.scrimColor = 1375731712;
    this.transitionDirection = 0;
    this.fadeMode = 0;
    this.fitMode = 0;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    }
    this.elevationShadowEnabled = bool;
    this.startElevation = -1.0F;
    this.endElevation = -1.0F;
  }
  
  public MaterialContainerTransform(Context paramContext, boolean paramBoolean)
  {
    boolean bool = false;
    this.drawDebugEnabled = false;
    this.holdAtEndEnabled = false;
    this.pathMotionCustom = false;
    this.appliedThemeValues = false;
    this.drawingViewId = 16908290;
    this.startViewId = -1;
    this.endViewId = -1;
    this.containerColor = 0;
    this.startContainerColor = 0;
    this.endContainerColor = 0;
    this.scrimColor = 1375731712;
    this.transitionDirection = 0;
    this.fadeMode = 0;
    this.fitMode = 0;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    }
    this.elevationShadowEnabled = bool;
    this.startElevation = -1.0F;
    this.endElevation = -1.0F;
    maybeApplyThemeValues(paramContext, paramBoolean);
    this.appliedThemeValues = true;
  }
  
  private ProgressThresholdsGroup buildThresholdsGroup(boolean paramBoolean)
  {
    PathMotion localPathMotion = getPathMotion();
    if ((!(localPathMotion instanceof ArcMotion)) && (!(localPathMotion instanceof MaterialArcMotion))) {
      return getThresholdsOrDefault(paramBoolean, DEFAULT_ENTER_THRESHOLDS, DEFAULT_RETURN_THRESHOLDS);
    }
    return getThresholdsOrDefault(paramBoolean, DEFAULT_ENTER_THRESHOLDS_ARC, DEFAULT_RETURN_THRESHOLDS_ARC);
  }
  
  private static RectF calculateDrawableBounds(View paramView1, View paramView2, float paramFloat1, float paramFloat2)
  {
    if (paramView2 != null)
    {
      paramView1 = TransitionUtils.getLocationOnScreen(paramView2);
      paramView1.offset(paramFloat1, paramFloat2);
      return paramView1;
    }
    return new RectF(0.0F, 0.0F, paramView1.getWidth(), paramView1.getHeight());
  }
  
  private static ShapeAppearanceModel captureShapeAppearance(View paramView, RectF paramRectF, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    return TransitionUtils.convertToRelativeCornerSizes(getShapeAppearance(paramView, paramShapeAppearanceModel), paramRectF);
  }
  
  private static void captureValues(TransitionValues paramTransitionValues, View paramView, int paramInt, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    if (paramInt != -1)
    {
      paramTransitionValues.view = TransitionUtils.findDescendantOrAncestorById(paramTransitionValues.view, paramInt);
    }
    else if (paramView != null)
    {
      paramTransitionValues.view = paramView;
    }
    else if ((paramTransitionValues.view.getTag(R.id.mtrl_motion_snapshot_view) instanceof View))
    {
      paramView = (View)paramTransitionValues.view.getTag(R.id.mtrl_motion_snapshot_view);
      paramTransitionValues.view.setTag(R.id.mtrl_motion_snapshot_view, null);
      paramTransitionValues.view = paramView;
    }
    View localView = paramTransitionValues.view;
    if ((ViewCompat.isLaidOut(localView)) || (localView.getWidth() != 0) || (localView.getHeight() != 0))
    {
      if (localView.getParent() == null) {
        paramView = TransitionUtils.getRelativeBounds(localView);
      } else {
        paramView = TransitionUtils.getLocationOnScreen(localView);
      }
      paramTransitionValues.values.put("materialContainerTransition:bounds", paramView);
      paramTransitionValues.values.put("materialContainerTransition:shapeAppearance", captureShapeAppearance(localView, paramView, paramShapeAppearanceModel));
    }
  }
  
  private static float getElevationOrDefault(float paramFloat, View paramView)
  {
    if (paramFloat == -1.0F) {
      paramFloat = ViewCompat.getElevation(paramView);
    }
    return paramFloat;
  }
  
  private static ShapeAppearanceModel getShapeAppearance(View paramView, ShapeAppearanceModel paramShapeAppearanceModel)
  {
    if (paramShapeAppearanceModel != null) {
      return paramShapeAppearanceModel;
    }
    if ((paramView.getTag(R.id.mtrl_motion_snapshot_view) instanceof ShapeAppearanceModel)) {
      return (ShapeAppearanceModel)paramView.getTag(R.id.mtrl_motion_snapshot_view);
    }
    paramShapeAppearanceModel = paramView.getContext();
    int i = getTransitionShapeAppearanceResId(paramShapeAppearanceModel);
    if (i != -1) {
      return ShapeAppearanceModel.builder(paramShapeAppearanceModel, i, 0).build();
    }
    if ((paramView instanceof Shapeable)) {
      return ((Shapeable)paramView).getShapeAppearanceModel();
    }
    return ShapeAppearanceModel.builder().build();
  }
  
  private ProgressThresholdsGroup getThresholdsOrDefault(boolean paramBoolean, ProgressThresholdsGroup paramProgressThresholdsGroup1, ProgressThresholdsGroup paramProgressThresholdsGroup2)
  {
    if (!paramBoolean) {
      paramProgressThresholdsGroup1 = paramProgressThresholdsGroup2;
    }
    return new ProgressThresholdsGroup((ProgressThresholds)TransitionUtils.defaultIfNull(this.fadeProgressThresholds, paramProgressThresholdsGroup1.fade), (ProgressThresholds)TransitionUtils.defaultIfNull(this.scaleProgressThresholds, paramProgressThresholdsGroup1.scale), (ProgressThresholds)TransitionUtils.defaultIfNull(this.scaleMaskProgressThresholds, paramProgressThresholdsGroup1.scaleMask), (ProgressThresholds)TransitionUtils.defaultIfNull(this.shapeMaskProgressThresholds, paramProgressThresholdsGroup1.shapeMask), null);
  }
  
  private static int getTransitionShapeAppearanceResId(Context paramContext)
  {
    paramContext = paramContext.obtainStyledAttributes(new int[] { R.attr.transitionShapeAppearance });
    int i = paramContext.getResourceId(0, -1);
    paramContext.recycle();
    return i;
  }
  
  private boolean isEntering(RectF paramRectF1, RectF paramRectF2)
  {
    int i = this.transitionDirection;
    boolean bool = false;
    switch (i)
    {
    default: 
      throw new IllegalArgumentException("Invalid transition direction: " + this.transitionDirection);
    case 2: 
      return false;
    case 1: 
      return true;
    }
    if (TransitionUtils.calculateArea(paramRectF2) > TransitionUtils.calculateArea(paramRectF1)) {
      bool = true;
    }
    return bool;
  }
  
  private void maybeApplyThemeValues(Context paramContext, boolean paramBoolean)
  {
    TransitionUtils.maybeApplyThemeInterpolator(this, paramContext, R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    int i;
    if (paramBoolean) {
      i = R.attr.motionDurationLong1;
    } else {
      i = R.attr.motionDurationMedium2;
    }
    TransitionUtils.maybeApplyThemeDuration(this, paramContext, i);
    if (!this.pathMotionCustom) {
      TransitionUtils.maybeApplyThemePath(this, paramContext, R.attr.motionPath);
    }
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues, this.endView, this.endViewId, this.endShapeAppearanceModel);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    captureValues(paramTransitionValues, this.startView, this.startViewId, this.startShapeAppearanceModel);
  }
  
  public Animator createAnimator(final ViewGroup paramViewGroup, final TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 != null) && (paramTransitionValues2 != null))
    {
      RectF localRectF1 = (RectF)paramTransitionValues1.values.get("materialContainerTransition:bounds");
      ShapeAppearanceModel localShapeAppearanceModel2 = (ShapeAppearanceModel)paramTransitionValues1.values.get("materialContainerTransition:shapeAppearance");
      if ((localRectF1 != null) && (localShapeAppearanceModel2 != null))
      {
        RectF localRectF2 = (RectF)paramTransitionValues2.values.get("materialContainerTransition:bounds");
        ShapeAppearanceModel localShapeAppearanceModel1 = (ShapeAppearanceModel)paramTransitionValues2.values.get("materialContainerTransition:shapeAppearance");
        if ((localRectF2 != null) && (localShapeAppearanceModel1 != null))
        {
          final View localView1 = paramTransitionValues1.view;
          final View localView2 = paramTransitionValues2.view;
          if (localView2.getParent() != null) {
            paramViewGroup = localView2;
          } else {
            paramViewGroup = localView1;
          }
          if (this.drawingViewId == paramViewGroup.getId())
          {
            paramTransitionValues1 = (View)paramViewGroup.getParent();
            paramTransitionValues2 = paramViewGroup;
          }
          else
          {
            paramTransitionValues1 = TransitionUtils.findAncestorById(paramViewGroup, this.drawingViewId);
            paramTransitionValues2 = null;
          }
          RectF localRectF3 = TransitionUtils.getLocationOnScreen(paramTransitionValues1);
          float f2 = -localRectF3.left;
          float f1 = -localRectF3.top;
          paramTransitionValues2 = calculateDrawableBounds(paramTransitionValues1, paramTransitionValues2, f2, f1);
          localRectF1.offset(f2, f1);
          localRectF2.offset(f2, f1);
          boolean bool = isEntering(localRectF1, localRectF2);
          if (!this.appliedThemeValues) {
            maybeApplyThemeValues(paramViewGroup.getContext(), bool);
          }
          paramViewGroup = new TransitionDrawable(getPathMotion(), localView1, localRectF1, localShapeAppearanceModel2, getElevationOrDefault(this.startElevation, localView1), localView2, localRectF2, localShapeAppearanceModel1, getElevationOrDefault(this.endElevation, localView2), this.containerColor, this.startContainerColor, this.endContainerColor, this.scrimColor, bool, this.elevationShadowEnabled, FadeModeEvaluators.get(this.fadeMode, bool), FitModeEvaluators.get(this.fitMode, bool, localRectF1, localRectF2), buildThresholdsGroup(bool), this.drawDebugEnabled, null);
          paramViewGroup.setBounds(Math.round(paramTransitionValues2.left), Math.round(paramTransitionValues2.top), Math.round(paramTransitionValues2.right), Math.round(paramTransitionValues2.bottom));
          paramTransitionValues2 = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
          paramTransitionValues2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
          {
            public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
            {
              MaterialContainerTransform.TransitionDrawable.access$200(paramViewGroup, paramAnonymousValueAnimator.getAnimatedFraction());
            }
          });
          addListener(new TransitionListenerAdapter()
          {
            public void onTransitionEnd(Transition paramAnonymousTransition)
            {
              MaterialContainerTransform.this.removeListener(this);
              if (MaterialContainerTransform.this.holdAtEndEnabled) {
                return;
              }
              localView1.setAlpha(1.0F);
              localView2.setAlpha(1.0F);
              ViewUtils.getOverlay(paramTransitionValues1).remove(paramViewGroup);
            }
            
            public void onTransitionStart(Transition paramAnonymousTransition)
            {
              ViewUtils.getOverlay(paramTransitionValues1).add(paramViewGroup);
              localView1.setAlpha(0.0F);
              localView2.setAlpha(0.0F);
            }
          });
          return paramTransitionValues2;
        }
        Log.w(TAG, "Skipping due to null end bounds. Ensure end view is laid out and measured.");
        return null;
      }
      Log.w(TAG, "Skipping due to null start bounds. Ensure start view is laid out and measured.");
      return null;
    }
    return null;
  }
  
  public int getContainerColor()
  {
    return this.containerColor;
  }
  
  public int getDrawingViewId()
  {
    return this.drawingViewId;
  }
  
  public int getEndContainerColor()
  {
    return this.endContainerColor;
  }
  
  public float getEndElevation()
  {
    return this.endElevation;
  }
  
  public ShapeAppearanceModel getEndShapeAppearanceModel()
  {
    return this.endShapeAppearanceModel;
  }
  
  public View getEndView()
  {
    return this.endView;
  }
  
  public int getEndViewId()
  {
    return this.endViewId;
  }
  
  public int getFadeMode()
  {
    return this.fadeMode;
  }
  
  public ProgressThresholds getFadeProgressThresholds()
  {
    return this.fadeProgressThresholds;
  }
  
  public int getFitMode()
  {
    return this.fitMode;
  }
  
  public ProgressThresholds getScaleMaskProgressThresholds()
  {
    return this.scaleMaskProgressThresholds;
  }
  
  public ProgressThresholds getScaleProgressThresholds()
  {
    return this.scaleProgressThresholds;
  }
  
  public int getScrimColor()
  {
    return this.scrimColor;
  }
  
  public ProgressThresholds getShapeMaskProgressThresholds()
  {
    return this.shapeMaskProgressThresholds;
  }
  
  public int getStartContainerColor()
  {
    return this.startContainerColor;
  }
  
  public float getStartElevation()
  {
    return this.startElevation;
  }
  
  public ShapeAppearanceModel getStartShapeAppearanceModel()
  {
    return this.startShapeAppearanceModel;
  }
  
  public View getStartView()
  {
    return this.startView;
  }
  
  public int getStartViewId()
  {
    return this.startViewId;
  }
  
  public int getTransitionDirection()
  {
    return this.transitionDirection;
  }
  
  public String[] getTransitionProperties()
  {
    return TRANSITION_PROPS;
  }
  
  public boolean isDrawDebugEnabled()
  {
    return this.drawDebugEnabled;
  }
  
  public boolean isElevationShadowEnabled()
  {
    return this.elevationShadowEnabled;
  }
  
  public boolean isHoldAtEndEnabled()
  {
    return this.holdAtEndEnabled;
  }
  
  public void setAllContainerColors(int paramInt)
  {
    this.containerColor = paramInt;
    this.startContainerColor = paramInt;
    this.endContainerColor = paramInt;
  }
  
  public void setContainerColor(int paramInt)
  {
    this.containerColor = paramInt;
  }
  
  public void setDrawDebugEnabled(boolean paramBoolean)
  {
    this.drawDebugEnabled = paramBoolean;
  }
  
  public void setDrawingViewId(int paramInt)
  {
    this.drawingViewId = paramInt;
  }
  
  public void setElevationShadowEnabled(boolean paramBoolean)
  {
    this.elevationShadowEnabled = paramBoolean;
  }
  
  public void setEndContainerColor(int paramInt)
  {
    this.endContainerColor = paramInt;
  }
  
  public void setEndElevation(float paramFloat)
  {
    this.endElevation = paramFloat;
  }
  
  public void setEndShapeAppearanceModel(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.endShapeAppearanceModel = paramShapeAppearanceModel;
  }
  
  public void setEndView(View paramView)
  {
    this.endView = paramView;
  }
  
  public void setEndViewId(int paramInt)
  {
    this.endViewId = paramInt;
  }
  
  public void setFadeMode(int paramInt)
  {
    this.fadeMode = paramInt;
  }
  
  public void setFadeProgressThresholds(ProgressThresholds paramProgressThresholds)
  {
    this.fadeProgressThresholds = paramProgressThresholds;
  }
  
  public void setFitMode(int paramInt)
  {
    this.fitMode = paramInt;
  }
  
  public void setHoldAtEndEnabled(boolean paramBoolean)
  {
    this.holdAtEndEnabled = paramBoolean;
  }
  
  public void setPathMotion(PathMotion paramPathMotion)
  {
    super.setPathMotion(paramPathMotion);
    this.pathMotionCustom = true;
  }
  
  public void setScaleMaskProgressThresholds(ProgressThresholds paramProgressThresholds)
  {
    this.scaleMaskProgressThresholds = paramProgressThresholds;
  }
  
  public void setScaleProgressThresholds(ProgressThresholds paramProgressThresholds)
  {
    this.scaleProgressThresholds = paramProgressThresholds;
  }
  
  public void setScrimColor(int paramInt)
  {
    this.scrimColor = paramInt;
  }
  
  public void setShapeMaskProgressThresholds(ProgressThresholds paramProgressThresholds)
  {
    this.shapeMaskProgressThresholds = paramProgressThresholds;
  }
  
  public void setStartContainerColor(int paramInt)
  {
    this.startContainerColor = paramInt;
  }
  
  public void setStartElevation(float paramFloat)
  {
    this.startElevation = paramFloat;
  }
  
  public void setStartShapeAppearanceModel(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.startShapeAppearanceModel = paramShapeAppearanceModel;
  }
  
  public void setStartView(View paramView)
  {
    this.startView = paramView;
  }
  
  public void setStartViewId(int paramInt)
  {
    this.startViewId = paramInt;
  }
  
  public void setTransitionDirection(int paramInt)
  {
    this.transitionDirection = paramInt;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FadeMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FitMode {}
  
  public static class ProgressThresholds
  {
    private final float end;
    private final float start;
    
    public ProgressThresholds(float paramFloat1, float paramFloat2)
    {
      this.start = paramFloat1;
      this.end = paramFloat2;
    }
    
    public float getEnd()
    {
      return this.end;
    }
    
    public float getStart()
    {
      return this.start;
    }
  }
  
  private static class ProgressThresholdsGroup
  {
    private final MaterialContainerTransform.ProgressThresholds fade;
    private final MaterialContainerTransform.ProgressThresholds scale;
    private final MaterialContainerTransform.ProgressThresholds scaleMask;
    private final MaterialContainerTransform.ProgressThresholds shapeMask;
    
    private ProgressThresholdsGroup(MaterialContainerTransform.ProgressThresholds paramProgressThresholds1, MaterialContainerTransform.ProgressThresholds paramProgressThresholds2, MaterialContainerTransform.ProgressThresholds paramProgressThresholds3, MaterialContainerTransform.ProgressThresholds paramProgressThresholds4)
    {
      this.fade = paramProgressThresholds1;
      this.scale = paramProgressThresholds2;
      this.scaleMask = paramProgressThresholds3;
      this.shapeMask = paramProgressThresholds4;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TransitionDirection {}
  
  private static final class TransitionDrawable
    extends Drawable
  {
    private static final int COMPAT_SHADOW_COLOR = -7829368;
    private static final int SHADOW_COLOR = 754974720;
    private static final float SHADOW_DX_MULTIPLIER_ADJUSTMENT = 0.3F;
    private static final float SHADOW_DY_MULTIPLIER_ADJUSTMENT = 1.5F;
    private final MaterialShapeDrawable compatShadowDrawable;
    private final Paint containerPaint;
    private float currentElevation;
    private float currentElevationDy;
    private final RectF currentEndBounds;
    private final RectF currentEndBoundsMasked;
    private RectF currentMaskBounds;
    private final RectF currentStartBounds;
    private final RectF currentStartBoundsMasked;
    private final Paint debugPaint;
    private final Path debugPath;
    private final float displayHeight;
    private final float displayWidth;
    private final boolean drawDebugEnabled;
    private final boolean elevationShadowEnabled;
    private final RectF endBounds;
    private final Paint endContainerPaint;
    private final float endElevation;
    private final ShapeAppearanceModel endShapeAppearanceModel;
    private final View endView;
    private final boolean entering;
    private final FadeModeEvaluator fadeModeEvaluator;
    private FadeModeResult fadeModeResult;
    private final FitModeEvaluator fitModeEvaluator;
    private FitModeResult fitModeResult;
    private final MaskEvaluator maskEvaluator;
    private final float motionPathLength;
    private final PathMeasure motionPathMeasure;
    private final float[] motionPathPosition;
    private float progress;
    private final MaterialContainerTransform.ProgressThresholdsGroup progressThresholds;
    private final Paint scrimPaint;
    private final Paint shadowPaint;
    private final RectF startBounds;
    private final Paint startContainerPaint;
    private final float startElevation;
    private final ShapeAppearanceModel startShapeAppearanceModel;
    private final View startView;
    
    private TransitionDrawable(PathMotion paramPathMotion, View paramView1, RectF paramRectF1, ShapeAppearanceModel paramShapeAppearanceModel1, float paramFloat1, View paramView2, RectF paramRectF2, ShapeAppearanceModel paramShapeAppearanceModel2, float paramFloat2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, FadeModeEvaluator paramFadeModeEvaluator, FitModeEvaluator paramFitModeEvaluator, MaterialContainerTransform.ProgressThresholdsGroup paramProgressThresholdsGroup, boolean paramBoolean3)
    {
      Paint localPaint5 = new Paint();
      this.containerPaint = localPaint5;
      Paint localPaint4 = new Paint();
      this.startContainerPaint = localPaint4;
      Paint localPaint3 = new Paint();
      this.endContainerPaint = localPaint3;
      this.shadowPaint = new Paint();
      Paint localPaint2 = new Paint();
      this.scrimPaint = localPaint2;
      this.maskEvaluator = new MaskEvaluator();
      float[] arrayOfFloat = new float[2];
      this.motionPathPosition = arrayOfFloat;
      MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
      this.compatShadowDrawable = localMaterialShapeDrawable;
      Paint localPaint1 = new Paint();
      this.debugPaint = localPaint1;
      this.debugPath = new Path();
      this.startView = paramView1;
      this.startBounds = paramRectF1;
      this.startShapeAppearanceModel = paramShapeAppearanceModel1;
      this.startElevation = paramFloat1;
      this.endView = paramView2;
      this.endBounds = paramRectF2;
      this.endShapeAppearanceModel = paramShapeAppearanceModel2;
      this.endElevation = paramFloat2;
      this.entering = paramBoolean1;
      this.elevationShadowEnabled = paramBoolean2;
      this.fadeModeEvaluator = paramFadeModeEvaluator;
      this.fitModeEvaluator = paramFitModeEvaluator;
      this.progressThresholds = paramProgressThresholdsGroup;
      this.drawDebugEnabled = paramBoolean3;
      paramView1 = (WindowManager)paramView1.getContext().getSystemService("window");
      paramShapeAppearanceModel1 = new DisplayMetrics();
      paramView1.getDefaultDisplay().getMetrics(paramShapeAppearanceModel1);
      this.displayWidth = paramShapeAppearanceModel1.widthPixels;
      this.displayHeight = paramShapeAppearanceModel1.heightPixels;
      localPaint5.setColor(paramInt1);
      localPaint4.setColor(paramInt2);
      localPaint3.setColor(paramInt3);
      localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(0));
      localMaterialShapeDrawable.setShadowCompatibilityMode(2);
      localMaterialShapeDrawable.setShadowBitmapDrawingEnable(false);
      localMaterialShapeDrawable.setShadowColor(-7829368);
      paramView1 = new RectF(paramRectF1);
      this.currentStartBounds = paramView1;
      this.currentStartBoundsMasked = new RectF(paramView1);
      paramView1 = new RectF(paramView1);
      this.currentEndBounds = paramView1;
      this.currentEndBoundsMasked = new RectF(paramView1);
      paramView1 = getMotionPathPoint(paramRectF1);
      paramShapeAppearanceModel1 = getMotionPathPoint(paramRectF2);
      paramPathMotion = new PathMeasure(paramPathMotion.getPath(paramView1.x, paramView1.y, paramShapeAppearanceModel1.x, paramShapeAppearanceModel1.y), false);
      this.motionPathMeasure = paramPathMotion;
      this.motionPathLength = paramPathMotion.getLength();
      arrayOfFloat[0] = paramRectF1.centerX();
      arrayOfFloat[1] = paramRectF1.top;
      localPaint2.setStyle(Paint.Style.FILL);
      localPaint2.setShader(TransitionUtils.createColorShader(paramInt4));
      localPaint1.setStyle(Paint.Style.STROKE);
      localPaint1.setStrokeWidth(10.0F);
      updateProgress(0.0F);
    }
    
    private static float calculateElevationDxMultiplier(RectF paramRectF, float paramFloat)
    {
      return (paramRectF.centerX() / (paramFloat / 2.0F) - 1.0F) * 0.3F;
    }
    
    private static float calculateElevationDyMultiplier(RectF paramRectF, float paramFloat)
    {
      return paramRectF.centerY() / paramFloat * 1.5F;
    }
    
    private void drawDebugCumulativePath(Canvas paramCanvas, RectF paramRectF, Path paramPath, int paramInt)
    {
      paramRectF = getMotionPathPoint(paramRectF);
      if (this.progress == 0.0F)
      {
        paramPath.reset();
        paramPath.moveTo(paramRectF.x, paramRectF.y);
      }
      else
      {
        paramPath.lineTo(paramRectF.x, paramRectF.y);
        this.debugPaint.setColor(paramInt);
        paramCanvas.drawPath(paramPath, this.debugPaint);
      }
    }
    
    private void drawDebugRect(Canvas paramCanvas, RectF paramRectF, int paramInt)
    {
      this.debugPaint.setColor(paramInt);
      paramCanvas.drawRect(paramRectF, this.debugPaint);
    }
    
    private void drawElevationShadow(Canvas paramCanvas)
    {
      paramCanvas.save();
      paramCanvas.clipPath(this.maskEvaluator.getPath(), Region.Op.DIFFERENCE);
      if (Build.VERSION.SDK_INT > 28) {
        drawElevationShadowWithPaintShadowLayer(paramCanvas);
      } else {
        drawElevationShadowWithMaterialShapeDrawable(paramCanvas);
      }
      paramCanvas.restore();
    }
    
    private void drawElevationShadowWithMaterialShapeDrawable(Canvas paramCanvas)
    {
      this.compatShadowDrawable.setBounds((int)this.currentMaskBounds.left, (int)this.currentMaskBounds.top, (int)this.currentMaskBounds.right, (int)this.currentMaskBounds.bottom);
      this.compatShadowDrawable.setElevation(this.currentElevation);
      this.compatShadowDrawable.setShadowVerticalOffset((int)this.currentElevationDy);
      this.compatShadowDrawable.setShapeAppearanceModel(this.maskEvaluator.getCurrentShapeAppearanceModel());
      this.compatShadowDrawable.draw(paramCanvas);
    }
    
    private void drawElevationShadowWithPaintShadowLayer(Canvas paramCanvas)
    {
      ShapeAppearanceModel localShapeAppearanceModel = this.maskEvaluator.getCurrentShapeAppearanceModel();
      if (localShapeAppearanceModel.isRoundRect(this.currentMaskBounds))
      {
        float f = localShapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.currentMaskBounds);
        paramCanvas.drawRoundRect(this.currentMaskBounds, f, f, this.shadowPaint);
      }
      else
      {
        paramCanvas.drawPath(this.maskEvaluator.getPath(), this.shadowPaint);
      }
    }
    
    private void drawEndView(Canvas paramCanvas)
    {
      maybeDrawContainerColor(paramCanvas, this.endContainerPaint);
      TransitionUtils.transform(paramCanvas, getBounds(), this.currentEndBounds.left, this.currentEndBounds.top, this.fitModeResult.endScale, this.fadeModeResult.endAlpha, new TransitionUtils.CanvasOperation()
      {
        public void run(Canvas paramAnonymousCanvas)
        {
          MaterialContainerTransform.TransitionDrawable.this.endView.draw(paramAnonymousCanvas);
        }
      });
    }
    
    private void drawStartView(Canvas paramCanvas)
    {
      maybeDrawContainerColor(paramCanvas, this.startContainerPaint);
      TransitionUtils.transform(paramCanvas, getBounds(), this.currentStartBounds.left, this.currentStartBounds.top, this.fitModeResult.startScale, this.fadeModeResult.startAlpha, new TransitionUtils.CanvasOperation()
      {
        public void run(Canvas paramAnonymousCanvas)
        {
          MaterialContainerTransform.TransitionDrawable.this.startView.draw(paramAnonymousCanvas);
        }
      });
    }
    
    private static PointF getMotionPathPoint(RectF paramRectF)
    {
      return new PointF(paramRectF.centerX(), paramRectF.top);
    }
    
    private void maybeDrawContainerColor(Canvas paramCanvas, Paint paramPaint)
    {
      if ((paramPaint.getColor() != 0) && (paramPaint.getAlpha() > 0)) {
        paramCanvas.drawRect(getBounds(), paramPaint);
      }
    }
    
    private void setProgress(float paramFloat)
    {
      if (this.progress != paramFloat) {
        updateProgress(paramFloat);
      }
    }
    
    private void updateProgress(float paramFloat)
    {
      this.progress = paramFloat;
      Object localObject = this.scrimPaint;
      if (this.entering) {
        f1 = TransitionUtils.lerp(0.0F, 255.0F, paramFloat);
      } else {
        f1 = TransitionUtils.lerp(255.0F, 0.0F, paramFloat);
      }
      ((Paint)localObject).setAlpha((int)f1);
      this.motionPathMeasure.getPosTan(this.motionPathLength * paramFloat, this.motionPathPosition, null);
      localObject = this.motionPathPosition;
      float f4 = localObject[0];
      float f3 = localObject[1];
      if ((paramFloat <= 1.0F) && (paramFloat >= 0.0F))
      {
        f2 = f4;
        f1 = f3;
      }
      else
      {
        if (paramFloat > 1.0F)
        {
          f2 = 0.99F;
          f1 = (paramFloat - 1.0F) / (1.0F - 0.99F);
        }
        else
        {
          f2 = 0.01F;
          f1 = paramFloat / 0.01F * -1.0F;
        }
        this.motionPathMeasure.getPosTan(this.motionPathLength * f2, (float[])localObject, null);
        localObject = this.motionPathPosition;
        f2 = localObject[0];
        float f5 = localObject[1];
        f2 = f4 + (f4 - f2) * f1;
        f1 = f3 + (f3 - f5) * f1;
      }
      f3 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$500(this.progressThresholds).start))).floatValue();
      f4 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$500(this.progressThresholds).end))).floatValue();
      localObject = this.fitModeEvaluator.evaluate(paramFloat, f3, f4, this.startBounds.width(), this.startBounds.height(), this.endBounds.width(), this.endBounds.height());
      this.fitModeResult = ((FitModeResult)localObject);
      this.currentStartBounds.set(f2 - ((FitModeResult)localObject).currentStartWidth / 2.0F, f1, this.fitModeResult.currentStartWidth / 2.0F + f2, this.fitModeResult.currentStartHeight + f1);
      this.currentEndBounds.set(f2 - this.fitModeResult.currentEndWidth / 2.0F, f1, this.fitModeResult.currentEndWidth / 2.0F + f2, this.fitModeResult.currentEndHeight + f1);
      this.currentStartBoundsMasked.set(this.currentStartBounds);
      this.currentEndBoundsMasked.set(this.currentEndBounds);
      float f2 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$600(this.progressThresholds).start))).floatValue();
      float f1 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$600(this.progressThresholds).end))).floatValue();
      boolean bool = this.fitModeEvaluator.shouldMaskStartBounds(this.fitModeResult);
      if (bool) {
        localObject = this.currentStartBoundsMasked;
      } else {
        localObject = this.currentEndBoundsMasked;
      }
      f1 = TransitionUtils.lerp(0.0F, 1.0F, f2, f1, paramFloat);
      if (!bool) {
        f1 = 1.0F - f1;
      }
      this.fitModeEvaluator.applyMask((RectF)localObject, f1, this.fitModeResult);
      this.currentMaskBounds = new RectF(Math.min(this.currentStartBoundsMasked.left, this.currentEndBoundsMasked.left), Math.min(this.currentStartBoundsMasked.top, this.currentEndBoundsMasked.top), Math.max(this.currentStartBoundsMasked.right, this.currentEndBoundsMasked.right), Math.max(this.currentStartBoundsMasked.bottom, this.currentEndBoundsMasked.bottom));
      this.maskEvaluator.evaluate(paramFloat, this.startShapeAppearanceModel, this.endShapeAppearanceModel, this.currentStartBounds, this.currentStartBoundsMasked, this.currentEndBoundsMasked, this.progressThresholds.shapeMask);
      this.currentElevation = TransitionUtils.lerp(this.startElevation, this.endElevation, paramFloat);
      f2 = calculateElevationDxMultiplier(this.currentMaskBounds, this.displayWidth);
      f3 = calculateElevationDyMultiplier(this.currentMaskBounds, this.displayHeight);
      f1 = this.currentElevation;
      f2 = (int)(f1 * f2);
      f3 = (int)(f1 * f3);
      this.currentElevationDy = f3;
      this.shadowPaint.setShadowLayer(f1, f2, f3, 754974720);
      f1 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$400(this.progressThresholds).start))).floatValue();
      f2 = ((Float)Preconditions.checkNotNull(Float.valueOf(MaterialContainerTransform.ProgressThresholdsGroup.access$400(this.progressThresholds).end))).floatValue();
      this.fadeModeResult = this.fadeModeEvaluator.evaluate(paramFloat, f1, f2, 0.35F);
      if (this.startContainerPaint.getColor() != 0) {
        this.startContainerPaint.setAlpha(this.fadeModeResult.startAlpha);
      }
      if (this.endContainerPaint.getColor() != 0) {
        this.endContainerPaint.setAlpha(this.fadeModeResult.endAlpha);
      }
      invalidateSelf();
    }
    
    public void draw(Canvas paramCanvas)
    {
      if (this.scrimPaint.getAlpha() > 0) {
        paramCanvas.drawRect(getBounds(), this.scrimPaint);
      }
      int i;
      if (this.drawDebugEnabled) {
        i = paramCanvas.save();
      } else {
        i = -1;
      }
      if ((this.elevationShadowEnabled) && (this.currentElevation > 0.0F)) {
        drawElevationShadow(paramCanvas);
      }
      this.maskEvaluator.clip(paramCanvas);
      maybeDrawContainerColor(paramCanvas, this.containerPaint);
      if (this.fadeModeResult.endOnTop)
      {
        drawStartView(paramCanvas);
        drawEndView(paramCanvas);
      }
      else
      {
        drawEndView(paramCanvas);
        drawStartView(paramCanvas);
      }
      if (this.drawDebugEnabled)
      {
        paramCanvas.restoreToCount(i);
        drawDebugCumulativePath(paramCanvas, this.currentStartBounds, this.debugPath, -65281);
        drawDebugRect(paramCanvas, this.currentStartBoundsMasked, 65280);
        drawDebugRect(paramCanvas, this.currentStartBounds, -16711936);
        drawDebugRect(paramCanvas, this.currentEndBoundsMasked, -16711681);
        drawDebugRect(paramCanvas, this.currentEndBounds, -16776961);
      }
    }
    
    public int getOpacity()
    {
      return -3;
    }
    
    public void setAlpha(int paramInt)
    {
      throw new UnsupportedOperationException("Setting alpha on is not supported");
    }
    
    public void setColorFilter(ColorFilter paramColorFilter)
    {
      throw new UnsupportedOperationException("Setting a color filter is not supported");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/MaterialContainerTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */