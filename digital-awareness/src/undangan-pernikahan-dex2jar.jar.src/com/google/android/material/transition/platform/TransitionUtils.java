package com.google.android.material.transition.platform;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import android.transition.PathMotion;
import android.transition.PatternPathMotion;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.PathParser;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TransitionUtils
{
  static final int NO_ATTR_RES_ID = 0;
  static final int NO_DURATION = -1;
  private static final int PATH_TYPE_ARC = 1;
  private static final int PATH_TYPE_LINEAR = 0;
  private static final RectF transformAlphaRectF = new RectF();
  
  static float calculateArea(RectF paramRectF)
  {
    return paramRectF.width() * paramRectF.height();
  }
  
  static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel paramShapeAppearanceModel, RectF paramRectF)
  {
    paramShapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator()
    {
      public CornerSize apply(CornerSize paramAnonymousCornerSize)
      {
        if (!(paramAnonymousCornerSize instanceof RelativeCornerSize)) {
          paramAnonymousCornerSize = new RelativeCornerSize(paramAnonymousCornerSize.getCornerSize(TransitionUtils.this) / TransitionUtils.this.height());
        }
        return paramAnonymousCornerSize;
      }
    });
  }
  
  static Shader createColorShader(int paramInt)
  {
    return new LinearGradient(0.0F, 0.0F, 0.0F, 0.0F, paramInt, paramInt, Shader.TileMode.CLAMP);
  }
  
  static <T> T defaultIfNull(T paramT1, T paramT2)
  {
    if (paramT1 == null) {
      paramT1 = paramT2;
    }
    return paramT1;
  }
  
  static View findAncestorById(View paramView, int paramInt)
  {
    String str = paramView.getResources().getResourceName(paramInt);
    while (paramView != null)
    {
      if (paramView.getId() == paramInt) {
        return paramView;
      }
      paramView = paramView.getParent();
      if (!(paramView instanceof View)) {
        break;
      }
      paramView = (View)paramView;
    }
    throw new IllegalArgumentException(str + " is not a valid ancestor");
  }
  
  static View findDescendantOrAncestorById(View paramView, int paramInt)
  {
    View localView = paramView.findViewById(paramInt);
    if (localView != null) {
      return localView;
    }
    return findAncestorById(paramView, paramInt);
  }
  
  static RectF getLocationOnScreen(View paramView)
  {
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    int j = arrayOfInt[0];
    int i = arrayOfInt[1];
    int k = paramView.getWidth();
    int m = paramView.getHeight();
    return new RectF(j, i, k + j, m + i);
  }
  
  static RectF getRelativeBounds(View paramView)
  {
    return new RectF(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
  }
  
  static Rect getRelativeBoundsRect(View paramView)
  {
    return new Rect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
  }
  
  private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel paramShapeAppearanceModel, RectF paramRectF)
  {
    boolean bool;
    if ((paramShapeAppearanceModel.getTopLeftCornerSize().getCornerSize(paramRectF) == 0.0F) && (paramShapeAppearanceModel.getTopRightCornerSize().getCornerSize(paramRectF) == 0.0F) && (paramShapeAppearanceModel.getBottomRightCornerSize().getCornerSize(paramRectF) == 0.0F) && (paramShapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(paramRectF) == 0.0F)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat2 - paramFloat1) * paramFloat3 + paramFloat1;
  }
  
  static float lerp(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return lerp(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, false);
  }
  
  static float lerp(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, boolean paramBoolean)
  {
    if ((paramBoolean) && ((paramFloat5 < 0.0F) || (paramFloat5 > 1.0F))) {
      return lerp(paramFloat1, paramFloat2, paramFloat5);
    }
    if (paramFloat5 < paramFloat3) {
      return paramFloat1;
    }
    if (paramFloat5 > paramFloat4) {
      return paramFloat2;
    }
    return lerp(paramFloat1, paramFloat2, (paramFloat5 - paramFloat3) / (paramFloat4 - paramFloat3));
  }
  
  static int lerp(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat3 < paramFloat1) {
      return paramInt1;
    }
    if (paramFloat3 > paramFloat2) {
      return paramInt2;
    }
    return (int)lerp(paramInt1, paramInt2, (paramFloat3 - paramFloat1) / (paramFloat2 - paramFloat1));
  }
  
  static ShapeAppearanceModel lerp(ShapeAppearanceModel paramShapeAppearanceModel1, ShapeAppearanceModel paramShapeAppearanceModel2, RectF paramRectF1, final RectF paramRectF2, final float paramFloat1, final float paramFloat2, final float paramFloat3)
  {
    if (paramFloat3 < paramFloat1) {
      return paramShapeAppearanceModel1;
    }
    if (paramFloat3 > paramFloat2) {
      return paramShapeAppearanceModel2;
    }
    transformCornerSizes(paramShapeAppearanceModel1, paramShapeAppearanceModel2, paramRectF1, new CornerSizeBinaryOperator()
    {
      public CornerSize apply(CornerSize paramAnonymousCornerSize1, CornerSize paramAnonymousCornerSize2)
      {
        return new AbsoluteCornerSize(TransitionUtils.lerp(paramAnonymousCornerSize1.getCornerSize(TransitionUtils.this), paramAnonymousCornerSize2.getCornerSize(paramRectF2), paramFloat1, paramFloat2, paramFloat3));
      }
    });
  }
  
  static void maybeAddTransition(TransitionSet paramTransitionSet, Transition paramTransition)
  {
    if (paramTransition != null) {
      paramTransitionSet.addTransition(paramTransition);
    }
  }
  
  static boolean maybeApplyThemeDuration(Transition paramTransition, Context paramContext, int paramInt)
  {
    if ((paramInt != 0) && (paramTransition.getDuration() == -1L))
    {
      paramInt = MotionUtils.resolveThemeDuration(paramContext, paramInt, -1);
      if (paramInt != -1)
      {
        paramTransition.setDuration(paramInt);
        return true;
      }
    }
    return false;
  }
  
  static boolean maybeApplyThemeInterpolator(Transition paramTransition, Context paramContext, int paramInt, TimeInterpolator paramTimeInterpolator)
  {
    if ((paramInt != 0) && (paramTransition.getInterpolator() == null))
    {
      paramTransition.setInterpolator(MotionUtils.resolveThemeInterpolator(paramContext, paramInt, paramTimeInterpolator));
      return true;
    }
    return false;
  }
  
  static boolean maybeApplyThemePath(Transition paramTransition, Context paramContext, int paramInt)
  {
    if (paramInt != 0)
    {
      paramContext = resolveThemePath(paramContext, paramInt);
      if (paramContext != null)
      {
        paramTransition.setPathMotion(paramContext);
        return true;
      }
    }
    return false;
  }
  
  static void maybeRemoveTransition(TransitionSet paramTransitionSet, Transition paramTransition)
  {
    if (paramTransition != null) {
      paramTransitionSet.removeTransition(paramTransition);
    }
  }
  
  static PathMotion resolveThemePath(Context paramContext, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    if (paramContext.getTheme().resolveAttribute(paramInt, localTypedValue, true))
    {
      if (localTypedValue.type == 16)
      {
        paramInt = localTypedValue.data;
        if (paramInt == 0) {
          return null;
        }
        if (paramInt == 1) {
          return new MaterialArcMotion();
        }
        throw new IllegalArgumentException("Invalid motion path type: " + paramInt);
      }
      if (localTypedValue.type == 3)
      {
        paramContext = String.valueOf(localTypedValue.string);
        Log5ECF72.a(paramContext);
        LogE84000.a(paramContext);
        Log229316.a(paramContext);
        return new PatternPathMotion(PathParser.createPathFromPathData(paramContext));
      }
      throw new IllegalArgumentException("Motion path theme attribute must either be an enum value or path data string");
    }
    return null;
  }
  
  private static int saveLayerAlphaCompat(Canvas paramCanvas, Rect paramRect, int paramInt)
  {
    RectF localRectF = transformAlphaRectF;
    localRectF.set(paramRect);
    if (Build.VERSION.SDK_INT >= 21) {
      return paramCanvas.saveLayerAlpha(localRectF, paramInt);
    }
    return paramCanvas.saveLayerAlpha(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom, paramInt, 31);
  }
  
  static void transform(Canvas paramCanvas, Rect paramRect, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, CanvasOperation paramCanvasOperation)
  {
    if (paramInt <= 0) {
      return;
    }
    int i = paramCanvas.save();
    paramCanvas.translate(paramFloat1, paramFloat2);
    paramCanvas.scale(paramFloat3, paramFloat3);
    if (paramInt < 255) {
      saveLayerAlphaCompat(paramCanvas, paramRect, paramInt);
    }
    paramCanvasOperation.run(paramCanvas);
    paramCanvas.restoreToCount(i);
  }
  
  static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel paramShapeAppearanceModel1, ShapeAppearanceModel paramShapeAppearanceModel2, RectF paramRectF, CornerSizeBinaryOperator paramCornerSizeBinaryOperator)
  {
    if (isShapeAppearanceSignificant(paramShapeAppearanceModel1, paramRectF)) {
      paramRectF = paramShapeAppearanceModel1;
    } else {
      paramRectF = paramShapeAppearanceModel2;
    }
    return paramRectF.toBuilder().setTopLeftCornerSize(paramCornerSizeBinaryOperator.apply(paramShapeAppearanceModel1.getTopLeftCornerSize(), paramShapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(paramCornerSizeBinaryOperator.apply(paramShapeAppearanceModel1.getTopRightCornerSize(), paramShapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(paramCornerSizeBinaryOperator.apply(paramShapeAppearanceModel1.getBottomLeftCornerSize(), paramShapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(paramCornerSizeBinaryOperator.apply(paramShapeAppearanceModel1.getBottomRightCornerSize(), paramShapeAppearanceModel2.getBottomRightCornerSize())).build();
  }
  
  static abstract interface CanvasOperation
  {
    public abstract void run(Canvas paramCanvas);
  }
  
  static abstract interface CornerSizeBinaryOperator
  {
    public abstract CornerSize apply(CornerSize paramCornerSize1, CornerSize paramCornerSize2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/TransitionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */