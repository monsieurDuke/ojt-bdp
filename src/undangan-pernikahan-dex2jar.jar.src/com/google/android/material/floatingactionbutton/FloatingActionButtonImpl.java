package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.integer;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FloatingActionButtonImpl
{
  static final int ANIM_STATE_HIDING = 1;
  static final int ANIM_STATE_NONE = 0;
  static final int ANIM_STATE_SHOWING = 2;
  static final long ELEVATION_ANIM_DELAY = 100L;
  static final long ELEVATION_ANIM_DURATION = 100L;
  static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
  static final int[] EMPTY_STATE_SET = new int[0];
  static final int[] ENABLED_STATE_SET;
  static final int[] FOCUSED_ENABLED_STATE_SET;
  private static final float HIDE_ICON_SCALE = 0.4F;
  private static final float HIDE_OPACITY = 0.0F;
  private static final float HIDE_SCALE = 0.4F;
  static final int[] HOVERED_ENABLED_STATE_SET;
  static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET;
  static final int[] PRESSED_ENABLED_STATE_SET = { 16842919, 16842910 };
  static final float SHADOW_MULTIPLIER = 1.5F;
  private static final float SHOW_ICON_SCALE = 1.0F;
  private static final float SHOW_OPACITY = 1.0F;
  private static final float SHOW_SCALE = 1.0F;
  private static final float SPEC_HIDE_ICON_SCALE = 0.0F;
  private static final float SPEC_HIDE_SCALE = 0.0F;
  private int animState = 0;
  BorderDrawable borderDrawable;
  Drawable contentBackground;
  private Animator currentAnimator;
  float elevation;
  boolean ensureMinTouchTargetSize;
  private ArrayList<Animator.AnimatorListener> hideListeners;
  private MotionSpec hideMotionSpec;
  float hoveredFocusedTranslationZ;
  private float imageMatrixScale = 1.0F;
  private int maxImageSize;
  int minTouchTargetSize;
  private ViewTreeObserver.OnPreDrawListener preDrawListener;
  float pressedTranslationZ;
  Drawable rippleDrawable;
  private float rotation;
  boolean shadowPaddingEnabled = true;
  final ShadowViewDelegate shadowViewDelegate;
  ShapeAppearanceModel shapeAppearance;
  MaterialShapeDrawable shapeDrawable;
  private ArrayList<Animator.AnimatorListener> showListeners;
  private MotionSpec showMotionSpec;
  private final StateListAnimator stateListAnimator;
  private final Matrix tmpMatrix = new Matrix();
  private final Rect tmpRect = new Rect();
  private final RectF tmpRectF1 = new RectF();
  private final RectF tmpRectF2 = new RectF();
  private ArrayList<InternalTransformationCallback> transformationCallbacks;
  final FloatingActionButton view;
  
  static
  {
    HOVERED_FOCUSED_ENABLED_STATE_SET = new int[] { 16843623, 16842908, 16842910 };
    FOCUSED_ENABLED_STATE_SET = new int[] { 16842908, 16842910 };
    HOVERED_ENABLED_STATE_SET = new int[] { 16843623, 16842910 };
    ENABLED_STATE_SET = new int[] { 16842910 };
  }
  
  FloatingActionButtonImpl(FloatingActionButton paramFloatingActionButton, ShadowViewDelegate paramShadowViewDelegate)
  {
    this.view = paramFloatingActionButton;
    this.shadowViewDelegate = paramShadowViewDelegate;
    paramShadowViewDelegate = new StateListAnimator();
    this.stateListAnimator = paramShadowViewDelegate;
    paramShadowViewDelegate.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToPressedTranslationZAnimation()));
    paramShadowViewDelegate.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    paramShadowViewDelegate.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    paramShadowViewDelegate.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(new ElevateToHoveredFocusedTranslationZAnimation()));
    paramShadowViewDelegate.addState(ENABLED_STATE_SET, createElevationAnimator(new ResetElevationAnimation()));
    paramShadowViewDelegate.addState(EMPTY_STATE_SET, createElevationAnimator(new DisabledElevationAnimation()));
    this.rotation = paramFloatingActionButton.getRotation();
  }
  
  private void calculateImageMatrixFromScale(float paramFloat, Matrix paramMatrix)
  {
    paramMatrix.reset();
    Drawable localDrawable = this.view.getDrawable();
    if ((localDrawable != null) && (this.maxImageSize != 0))
    {
      RectF localRectF1 = this.tmpRectF1;
      RectF localRectF2 = this.tmpRectF2;
      localRectF1.set(0.0F, 0.0F, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
      int i = this.maxImageSize;
      localRectF2.set(0.0F, 0.0F, i, i);
      paramMatrix.setRectToRect(localRectF1, localRectF2, Matrix.ScaleToFit.CENTER);
      i = this.maxImageSize;
      paramMatrix.postScale(paramFloat, paramFloat, i / 2.0F, i / 2.0F);
    }
  }
  
  private AnimatorSet createAnimator(MotionSpec paramMotionSpec, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    ArrayList localArrayList = new ArrayList();
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[] { paramFloat1 });
    paramMotionSpec.getTiming("opacity").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    localObjectAnimator = ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[] { paramFloat2 });
    paramMotionSpec.getTiming("scale").apply(localObjectAnimator);
    workAroundOreoBug(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    localObjectAnimator = ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[] { paramFloat2 });
    paramMotionSpec.getTiming("scale").apply(localObjectAnimator);
    workAroundOreoBug(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    calculateImageMatrixFromScale(paramFloat3, this.tmpMatrix);
    localObjectAnimator = ObjectAnimator.ofObject(this.view, new ImageMatrixProperty(), new MatrixEvaluator()new Matrix
    {
      public Matrix evaluate(float paramAnonymousFloat, Matrix paramAnonymousMatrix1, Matrix paramAnonymousMatrix2)
      {
        FloatingActionButtonImpl.access$202(FloatingActionButtonImpl.this, paramAnonymousFloat);
        return super.evaluate(paramAnonymousFloat, paramAnonymousMatrix1, paramAnonymousMatrix2);
      }
    }, new Matrix[] { new Matrix(this.tmpMatrix) });
    paramMotionSpec.getTiming("iconScale").apply(localObjectAnimator);
    localArrayList.add(localObjectAnimator);
    paramMotionSpec = new AnimatorSet();
    AnimatorSetCompat.playTogether(paramMotionSpec, localArrayList);
    return paramMotionSpec;
  }
  
  private AnimatorSet createDefaultAnimator(final float paramFloat1, final float paramFloat2, final float paramFloat3)
  {
    AnimatorSet localAnimatorSet = new AnimatorSet();
    ArrayList localArrayList = new ArrayList();
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        FloatingActionButtonImpl.this.view.setAlpha(AnimationUtils.lerp(this.val$startAlpha, paramFloat1, 0.0F, 0.2F, f));
        FloatingActionButtonImpl.this.view.setScaleX(AnimationUtils.lerp(this.val$startScaleX, paramFloat2, f));
        FloatingActionButtonImpl.this.view.setScaleY(AnimationUtils.lerp(this.val$startScaleY, paramFloat2, f));
        FloatingActionButtonImpl.access$202(FloatingActionButtonImpl.this, AnimationUtils.lerp(this.val$startImageMatrixScale, paramFloat3, f));
        FloatingActionButtonImpl.this.calculateImageMatrixFromScale(AnimationUtils.lerp(this.val$startImageMatrixScale, paramFloat3, f), this.val$matrix);
        FloatingActionButtonImpl.this.view.setImageMatrix(this.val$matrix);
      }
    });
    localArrayList.add(localValueAnimator);
    AnimatorSetCompat.playTogether(localAnimatorSet, localArrayList);
    localAnimatorSet.setDuration(MotionUtils.resolveThemeDuration(this.view.getContext(), R.attr.motionDurationLong1, this.view.getContext().getResources().getInteger(R.integer.material_motion_duration_long_1)));
    localAnimatorSet.setInterpolator(MotionUtils.resolveThemeInterpolator(this.view.getContext(), R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
    return localAnimatorSet;
  }
  
  private ValueAnimator createElevationAnimator(ShadowAnimatorImpl paramShadowAnimatorImpl)
  {
    ValueAnimator localValueAnimator = new ValueAnimator();
    localValueAnimator.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
    localValueAnimator.setDuration(100L);
    localValueAnimator.addListener(paramShadowAnimatorImpl);
    localValueAnimator.addUpdateListener(paramShadowAnimatorImpl);
    localValueAnimator.setFloatValues(new float[] { 0.0F, 1.0F });
    return localValueAnimator;
  }
  
  private ViewTreeObserver.OnPreDrawListener getOrCreatePreDrawListener()
  {
    if (this.preDrawListener == null) {
      this.preDrawListener = new ViewTreeObserver.OnPreDrawListener()
      {
        public boolean onPreDraw()
        {
          FloatingActionButtonImpl.this.onPreDraw();
          return true;
        }
      };
    }
    return this.preDrawListener;
  }
  
  private boolean shouldAnimateVisibilityChange()
  {
    boolean bool;
    if ((ViewCompat.isLaidOut(this.view)) && (!this.view.isInEditMode())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void workAroundOreoBug(ObjectAnimator paramObjectAnimator)
  {
    if (Build.VERSION.SDK_INT != 26) {
      return;
    }
    paramObjectAnimator.setEvaluator(new TypeEvaluator()
    {
      FloatEvaluator floatEvaluator = new FloatEvaluator();
      
      public Float evaluate(float paramAnonymousFloat, Float paramAnonymousFloat1, Float paramAnonymousFloat2)
      {
        paramAnonymousFloat = this.floatEvaluator.evaluate(paramAnonymousFloat, paramAnonymousFloat1, paramAnonymousFloat2).floatValue();
        if (paramAnonymousFloat < 0.1F) {
          paramAnonymousFloat = 0.0F;
        }
        return Float.valueOf(paramAnonymousFloat);
      }
    });
  }
  
  public void addOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    if (this.hideListeners == null) {
      this.hideListeners = new ArrayList();
    }
    this.hideListeners.add(paramAnimatorListener);
  }
  
  void addOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    if (this.showListeners == null) {
      this.showListeners = new ArrayList();
    }
    this.showListeners.add(paramAnimatorListener);
  }
  
  void addTransformationCallback(InternalTransformationCallback paramInternalTransformationCallback)
  {
    if (this.transformationCallbacks == null) {
      this.transformationCallbacks = new ArrayList();
    }
    this.transformationCallbacks.add(paramInternalTransformationCallback);
  }
  
  MaterialShapeDrawable createShapeDrawable()
  {
    return new MaterialShapeDrawable((ShapeAppearanceModel)Preconditions.checkNotNull(this.shapeAppearance));
  }
  
  final Drawable getContentBackground()
  {
    return this.contentBackground;
  }
  
  float getElevation()
  {
    return this.elevation;
  }
  
  boolean getEnsureMinTouchTargetSize()
  {
    return this.ensureMinTouchTargetSize;
  }
  
  final MotionSpec getHideMotionSpec()
  {
    return this.hideMotionSpec;
  }
  
  float getHoveredFocusedTranslationZ()
  {
    return this.hoveredFocusedTranslationZ;
  }
  
  void getPadding(Rect paramRect)
  {
    if (this.ensureMinTouchTargetSize) {
      i = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
    } else {
      i = 0;
    }
    float f;
    if (this.shadowPaddingEnabled) {
      f = getElevation() + this.pressedTranslationZ;
    } else {
      f = 0.0F;
    }
    int j = Math.max(i, (int)Math.ceil(f));
    int i = Math.max(i, (int)Math.ceil(1.5F * f));
    paramRect.set(j, i, j, i);
  }
  
  float getPressedTranslationZ()
  {
    return this.pressedTranslationZ;
  }
  
  final ShapeAppearanceModel getShapeAppearance()
  {
    return this.shapeAppearance;
  }
  
  final MotionSpec getShowMotionSpec()
  {
    return this.showMotionSpec;
  }
  
  void hide(final InternalVisibilityChangedListener paramInternalVisibilityChangedListener, final boolean paramBoolean)
  {
    if (isOrWillBeHidden()) {
      return;
    }
    Object localObject = this.currentAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    if (shouldAnimateVisibilityChange())
    {
      localObject = this.hideMotionSpec;
      if (localObject != null) {
        localObject = createAnimator((MotionSpec)localObject, 0.0F, 0.0F, 0.0F);
      } else {
        localObject = createDefaultAnimator(0.0F, 0.4F, 0.4F);
      }
      ((AnimatorSet)localObject).addListener(new AnimatorListenerAdapter()
      {
        private boolean cancelled;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          this.cancelled = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          FloatingActionButtonImpl.access$002(FloatingActionButtonImpl.this, 0);
          FloatingActionButtonImpl.access$102(FloatingActionButtonImpl.this, null);
          if (!this.cancelled)
          {
            paramAnonymousAnimator = FloatingActionButtonImpl.this.view;
            boolean bool = paramBoolean;
            int i;
            if (bool) {
              i = 8;
            } else {
              i = 4;
            }
            paramAnonymousAnimator.internalSetVisibility(i, bool);
            paramAnonymousAnimator = paramInternalVisibilityChangedListener;
            if (paramAnonymousAnimator != null) {
              paramAnonymousAnimator.onHidden();
            }
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          FloatingActionButtonImpl.this.view.internalSetVisibility(0, paramBoolean);
          FloatingActionButtonImpl.access$002(FloatingActionButtonImpl.this, 1);
          FloatingActionButtonImpl.access$102(FloatingActionButtonImpl.this, paramAnonymousAnimator);
          this.cancelled = false;
        }
      });
      paramInternalVisibilityChangedListener = this.hideListeners;
      if (paramInternalVisibilityChangedListener != null)
      {
        paramInternalVisibilityChangedListener = paramInternalVisibilityChangedListener.iterator();
        while (paramInternalVisibilityChangedListener.hasNext()) {
          ((AnimatorSet)localObject).addListener((Animator.AnimatorListener)paramInternalVisibilityChangedListener.next());
        }
      }
      ((AnimatorSet)localObject).start();
    }
    else
    {
      localObject = this.view;
      int i;
      if (paramBoolean) {
        i = 8;
      } else {
        i = 4;
      }
      ((FloatingActionButton)localObject).internalSetVisibility(i, paramBoolean);
      if (paramInternalVisibilityChangedListener != null) {
        paramInternalVisibilityChangedListener.onHidden();
      }
    }
  }
  
  void initializeBackgroundDrawable(ColorStateList paramColorStateList1, PorterDuff.Mode paramMode, ColorStateList paramColorStateList2, int paramInt)
  {
    MaterialShapeDrawable localMaterialShapeDrawable = createShapeDrawable();
    this.shapeDrawable = localMaterialShapeDrawable;
    localMaterialShapeDrawable.setTintList(paramColorStateList1);
    if (paramMode != null) {
      this.shapeDrawable.setTintMode(paramMode);
    }
    this.shapeDrawable.setShadowColor(-12303292);
    this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
    paramColorStateList1 = new RippleDrawableCompat(this.shapeDrawable.getShapeAppearanceModel());
    paramColorStateList1.setTintList(RippleUtils.sanitizeRippleDrawableColor(paramColorStateList2));
    this.rippleDrawable = paramColorStateList1;
    this.contentBackground = new LayerDrawable(new Drawable[] { (Drawable)Preconditions.checkNotNull(this.shapeDrawable), paramColorStateList1 });
  }
  
  boolean isOrWillBeHidden()
  {
    int i = this.view.getVisibility();
    boolean bool2 = false;
    boolean bool1 = false;
    if (i == 0)
    {
      if (this.animState == 1) {
        bool1 = true;
      }
      return bool1;
    }
    bool1 = bool2;
    if (this.animState != 2) {
      bool1 = true;
    }
    return bool1;
  }
  
  boolean isOrWillBeShown()
  {
    int i = this.view.getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i != 0)
    {
      bool1 = bool2;
      if (this.animState == 2) {
        bool1 = true;
      }
      return bool1;
    }
    if (this.animState != 1) {
      bool1 = true;
    }
    return bool1;
  }
  
  void jumpDrawableToCurrentState()
  {
    this.stateListAnimator.jumpToCurrentState();
  }
  
  void onAttachedToWindow()
  {
    MaterialShapeDrawable localMaterialShapeDrawable = this.shapeDrawable;
    if (localMaterialShapeDrawable != null) {
      MaterialShapeUtils.setParentAbsoluteElevation(this.view, localMaterialShapeDrawable);
    }
    if (requirePreDrawListener()) {
      this.view.getViewTreeObserver().addOnPreDrawListener(getOrCreatePreDrawListener());
    }
  }
  
  void onCompatShadowChanged() {}
  
  void onDetachedFromWindow()
  {
    ViewTreeObserver localViewTreeObserver = this.view.getViewTreeObserver();
    ViewTreeObserver.OnPreDrawListener localOnPreDrawListener = this.preDrawListener;
    if (localOnPreDrawListener != null)
    {
      localViewTreeObserver.removeOnPreDrawListener(localOnPreDrawListener);
      this.preDrawListener = null;
    }
  }
  
  void onDrawableStateChanged(int[] paramArrayOfInt)
  {
    this.stateListAnimator.setState(paramArrayOfInt);
  }
  
  void onElevationsChanged(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    updatePadding();
    updateShapeElevation(paramFloat1);
  }
  
  void onPaddingUpdated(Rect paramRect)
  {
    Preconditions.checkNotNull(this.contentBackground, "Didn't initialize content background");
    if (shouldAddPadding())
    {
      paramRect = new InsetDrawable(this.contentBackground, paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
      this.shadowViewDelegate.setBackgroundDrawable(paramRect);
    }
    else
    {
      this.shadowViewDelegate.setBackgroundDrawable(this.contentBackground);
    }
  }
  
  void onPreDraw()
  {
    float f = this.view.getRotation();
    if (this.rotation != f)
    {
      this.rotation = f;
      updateFromViewRotation();
    }
  }
  
  void onScaleChanged()
  {
    Object localObject = this.transformationCallbacks;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((InternalTransformationCallback)((Iterator)localObject).next()).onScaleChanged();
      }
    }
  }
  
  void onTranslationChanged()
  {
    Object localObject = this.transformationCallbacks;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((InternalTransformationCallback)((Iterator)localObject).next()).onTranslationChanged();
      }
    }
  }
  
  public void removeOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ArrayList localArrayList = this.hideListeners;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramAnimatorListener);
  }
  
  void removeOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    ArrayList localArrayList = this.showListeners;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramAnimatorListener);
  }
  
  void removeTransformationCallback(InternalTransformationCallback paramInternalTransformationCallback)
  {
    ArrayList localArrayList = this.transformationCallbacks;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramInternalTransformationCallback);
  }
  
  boolean requirePreDrawListener()
  {
    return true;
  }
  
  void setBackgroundTintList(ColorStateList paramColorStateList)
  {
    Object localObject = this.shapeDrawable;
    if (localObject != null) {
      ((MaterialShapeDrawable)localObject).setTintList(paramColorStateList);
    }
    localObject = this.borderDrawable;
    if (localObject != null) {
      ((BorderDrawable)localObject).setBorderTint(paramColorStateList);
    }
  }
  
  void setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    MaterialShapeDrawable localMaterialShapeDrawable = this.shapeDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setTintMode(paramMode);
    }
  }
  
  final void setElevation(float paramFloat)
  {
    if (this.elevation != paramFloat)
    {
      this.elevation = paramFloat;
      onElevationsChanged(paramFloat, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
    }
  }
  
  void setEnsureMinTouchTargetSize(boolean paramBoolean)
  {
    this.ensureMinTouchTargetSize = paramBoolean;
  }
  
  final void setHideMotionSpec(MotionSpec paramMotionSpec)
  {
    this.hideMotionSpec = paramMotionSpec;
  }
  
  final void setHoveredFocusedTranslationZ(float paramFloat)
  {
    if (this.hoveredFocusedTranslationZ != paramFloat)
    {
      this.hoveredFocusedTranslationZ = paramFloat;
      onElevationsChanged(this.elevation, paramFloat, this.pressedTranslationZ);
    }
  }
  
  final void setImageMatrixScale(float paramFloat)
  {
    this.imageMatrixScale = paramFloat;
    Matrix localMatrix = this.tmpMatrix;
    calculateImageMatrixFromScale(paramFloat, localMatrix);
    this.view.setImageMatrix(localMatrix);
  }
  
  final void setMaxImageSize(int paramInt)
  {
    if (this.maxImageSize != paramInt)
    {
      this.maxImageSize = paramInt;
      updateImageMatrixScale();
    }
  }
  
  void setMinTouchTargetSize(int paramInt)
  {
    this.minTouchTargetSize = paramInt;
  }
  
  final void setPressedTranslationZ(float paramFloat)
  {
    if (this.pressedTranslationZ != paramFloat)
    {
      this.pressedTranslationZ = paramFloat;
      onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, paramFloat);
    }
  }
  
  void setRippleColor(ColorStateList paramColorStateList)
  {
    Drawable localDrawable = this.rippleDrawable;
    if (localDrawable != null) {
      DrawableCompat.setTintList(localDrawable, RippleUtils.sanitizeRippleDrawableColor(paramColorStateList));
    }
  }
  
  void setShadowPaddingEnabled(boolean paramBoolean)
  {
    this.shadowPaddingEnabled = paramBoolean;
    updatePadding();
  }
  
  final void setShapeAppearance(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.shapeAppearance = paramShapeAppearanceModel;
    Object localObject = this.shapeDrawable;
    if (localObject != null) {
      ((MaterialShapeDrawable)localObject).setShapeAppearanceModel(paramShapeAppearanceModel);
    }
    localObject = this.rippleDrawable;
    if ((localObject instanceof Shapeable)) {
      ((Shapeable)localObject).setShapeAppearanceModel(paramShapeAppearanceModel);
    }
    localObject = this.borderDrawable;
    if (localObject != null) {
      ((BorderDrawable)localObject).setShapeAppearanceModel(paramShapeAppearanceModel);
    }
  }
  
  final void setShowMotionSpec(MotionSpec paramMotionSpec)
  {
    this.showMotionSpec = paramMotionSpec;
  }
  
  boolean shouldAddPadding()
  {
    return true;
  }
  
  final boolean shouldExpandBoundsForA11y()
  {
    boolean bool;
    if ((this.ensureMinTouchTargetSize) && (this.view.getSizeDimension() < this.minTouchTargetSize)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void show(final InternalVisibilityChangedListener paramInternalVisibilityChangedListener, final boolean paramBoolean)
  {
    if (isOrWillBeShown()) {
      return;
    }
    Object localObject = this.currentAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    int i;
    if (this.showMotionSpec == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (shouldAnimateVisibilityChange())
    {
      if (this.view.getVisibility() != 0)
      {
        localObject = this.view;
        float f2 = 0.0F;
        ((FloatingActionButton)localObject).setAlpha(0.0F);
        localObject = this.view;
        if (i != 0) {
          f1 = 0.4F;
        } else {
          f1 = 0.0F;
        }
        ((FloatingActionButton)localObject).setScaleY(f1);
        localObject = this.view;
        if (i != 0) {
          f1 = 0.4F;
        } else {
          f1 = 0.0F;
        }
        ((FloatingActionButton)localObject).setScaleX(f1);
        float f1 = f2;
        if (i != 0) {
          f1 = 0.4F;
        }
        setImageMatrixScale(f1);
      }
      localObject = this.showMotionSpec;
      if (localObject != null) {
        localObject = createAnimator((MotionSpec)localObject, 1.0F, 1.0F, 1.0F);
      } else {
        localObject = createDefaultAnimator(1.0F, 1.0F, 1.0F);
      }
      ((AnimatorSet)localObject).addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          FloatingActionButtonImpl.access$002(FloatingActionButtonImpl.this, 0);
          FloatingActionButtonImpl.access$102(FloatingActionButtonImpl.this, null);
          paramAnonymousAnimator = paramInternalVisibilityChangedListener;
          if (paramAnonymousAnimator != null) {
            paramAnonymousAnimator.onShown();
          }
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          FloatingActionButtonImpl.this.view.internalSetVisibility(0, paramBoolean);
          FloatingActionButtonImpl.access$002(FloatingActionButtonImpl.this, 2);
          FloatingActionButtonImpl.access$102(FloatingActionButtonImpl.this, paramAnonymousAnimator);
        }
      });
      paramInternalVisibilityChangedListener = this.showListeners;
      if (paramInternalVisibilityChangedListener != null)
      {
        paramInternalVisibilityChangedListener = paramInternalVisibilityChangedListener.iterator();
        while (paramInternalVisibilityChangedListener.hasNext()) {
          ((AnimatorSet)localObject).addListener((Animator.AnimatorListener)paramInternalVisibilityChangedListener.next());
        }
      }
      ((AnimatorSet)localObject).start();
    }
    else
    {
      this.view.internalSetVisibility(0, paramBoolean);
      this.view.setAlpha(1.0F);
      this.view.setScaleY(1.0F);
      this.view.setScaleX(1.0F);
      setImageMatrixScale(1.0F);
      if (paramInternalVisibilityChangedListener != null) {
        paramInternalVisibilityChangedListener.onShown();
      }
    }
  }
  
  void updateFromViewRotation()
  {
    if (Build.VERSION.SDK_INT == 19) {
      if (this.rotation % 90.0F != 0.0F)
      {
        if (this.view.getLayerType() != 1) {
          this.view.setLayerType(1, null);
        }
      }
      else if (this.view.getLayerType() != 0) {
        this.view.setLayerType(0, null);
      }
    }
    MaterialShapeDrawable localMaterialShapeDrawable = this.shapeDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setShadowCompatRotation((int)this.rotation);
    }
  }
  
  final void updateImageMatrixScale()
  {
    setImageMatrixScale(this.imageMatrixScale);
  }
  
  final void updatePadding()
  {
    Rect localRect = this.tmpRect;
    getPadding(localRect);
    onPaddingUpdated(localRect);
    this.shadowViewDelegate.setShadowPadding(localRect.left, localRect.top, localRect.right, localRect.bottom);
  }
  
  void updateShapeElevation(float paramFloat)
  {
    MaterialShapeDrawable localMaterialShapeDrawable = this.shapeDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setElevation(paramFloat);
    }
  }
  
  private class DisabledElevationAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    DisabledElevationAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return 0.0F;
    }
  }
  
  private class ElevateToHoveredFocusedTranslationZAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ElevateToHoveredFocusedTranslationZAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.hoveredFocusedTranslationZ;
    }
  }
  
  private class ElevateToPressedTranslationZAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ElevateToPressedTranslationZAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return FloatingActionButtonImpl.this.elevation + FloatingActionButtonImpl.this.pressedTranslationZ;
    }
  }
  
  static abstract interface InternalTransformationCallback
  {
    public abstract void onScaleChanged();
    
    public abstract void onTranslationChanged();
  }
  
  static abstract interface InternalVisibilityChangedListener
  {
    public abstract void onHidden();
    
    public abstract void onShown();
  }
  
  private class ResetElevationAnimation
    extends FloatingActionButtonImpl.ShadowAnimatorImpl
  {
    ResetElevationAnimation()
    {
      super(null);
    }
    
    protected float getTargetShadowSize()
    {
      return FloatingActionButtonImpl.this.elevation;
    }
  }
  
  private abstract class ShadowAnimatorImpl
    extends AnimatorListenerAdapter
    implements ValueAnimator.AnimatorUpdateListener
  {
    private float shadowSizeEnd;
    private float shadowSizeStart;
    private boolean validValues;
    
    private ShadowAnimatorImpl() {}
    
    protected abstract float getTargetShadowSize();
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      FloatingActionButtonImpl.this.updateShapeElevation((int)this.shadowSizeEnd);
      this.validValues = false;
    }
    
    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      if (!this.validValues)
      {
        if (FloatingActionButtonImpl.this.shapeDrawable == null) {
          f = 0.0F;
        } else {
          f = FloatingActionButtonImpl.this.shapeDrawable.getElevation();
        }
        this.shadowSizeStart = f;
        this.shadowSizeEnd = getTargetShadowSize();
        this.validValues = true;
      }
      FloatingActionButtonImpl localFloatingActionButtonImpl = FloatingActionButtonImpl.this;
      float f = this.shadowSizeStart;
      localFloatingActionButtonImpl.updateShapeElevation((int)(f + (this.shadowSizeEnd - f) * paramValueAnimator.getAnimatedFraction()));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/FloatingActionButtonImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */