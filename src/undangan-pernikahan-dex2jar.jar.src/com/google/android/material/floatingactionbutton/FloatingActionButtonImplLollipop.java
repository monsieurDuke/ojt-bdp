package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.R.color;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;
import java.util.List;

class FloatingActionButtonImplLollipop
  extends FloatingActionButtonImpl
{
  FloatingActionButtonImplLollipop(FloatingActionButton paramFloatingActionButton, ShadowViewDelegate paramShadowViewDelegate)
  {
    super(paramFloatingActionButton, paramShadowViewDelegate);
  }
  
  private Animator createElevationAnimator(float paramFloat1, float paramFloat2)
  {
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.play(ObjectAnimator.ofFloat(this.view, "elevation", new float[] { paramFloat1 }).setDuration(0L)).with(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[] { paramFloat2 }).setDuration(100L));
    localAnimatorSet.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
    return localAnimatorSet;
  }
  
  BorderDrawable createBorderDrawable(int paramInt, ColorStateList paramColorStateList)
  {
    Context localContext = this.view.getContext();
    BorderDrawable localBorderDrawable = new BorderDrawable((ShapeAppearanceModel)Preconditions.checkNotNull(this.shapeAppearance));
    localBorderDrawable.setGradientColors(ContextCompat.getColor(localContext, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(localContext, R.color.design_fab_stroke_end_outer_color));
    localBorderDrawable.setBorderWidth(paramInt);
    localBorderDrawable.setBorderTint(paramColorStateList);
    return localBorderDrawable;
  }
  
  MaterialShapeDrawable createShapeDrawable()
  {
    return new AlwaysStatefulMaterialShapeDrawable((ShapeAppearanceModel)Preconditions.checkNotNull(this.shapeAppearance));
  }
  
  public float getElevation()
  {
    return this.view.getElevation();
  }
  
  void getPadding(Rect paramRect)
  {
    if (this.shadowViewDelegate.isCompatPaddingEnabled())
    {
      super.getPadding(paramRect);
    }
    else if (!shouldExpandBoundsForA11y())
    {
      int i = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
      paramRect.set(i, i, i, i);
    }
    else
    {
      paramRect.set(0, 0, 0, 0);
    }
  }
  
  void initializeBackgroundDrawable(ColorStateList paramColorStateList1, PorterDuff.Mode paramMode, ColorStateList paramColorStateList2, int paramInt)
  {
    this.shapeDrawable = createShapeDrawable();
    this.shapeDrawable.setTintList(paramColorStateList1);
    if (paramMode != null) {
      this.shapeDrawable.setTintMode(paramMode);
    }
    this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
    if (paramInt > 0)
    {
      this.borderDrawable = createBorderDrawable(paramInt, paramColorStateList1);
      paramColorStateList1 = new LayerDrawable(new Drawable[] { (Drawable)Preconditions.checkNotNull(this.borderDrawable), (Drawable)Preconditions.checkNotNull(this.shapeDrawable) });
    }
    else
    {
      this.borderDrawable = null;
      paramColorStateList1 = this.shapeDrawable;
    }
    this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(paramColorStateList2), paramColorStateList1, null);
    this.contentBackground = this.rippleDrawable;
  }
  
  void jumpDrawableToCurrentState() {}
  
  void onCompatShadowChanged()
  {
    updatePadding();
  }
  
  void onDrawableStateChanged(int[] paramArrayOfInt)
  {
    if (Build.VERSION.SDK_INT == 21) {
      if (this.view.isEnabled())
      {
        this.view.setElevation(this.elevation);
        if (this.view.isPressed()) {
          this.view.setTranslationZ(this.pressedTranslationZ);
        } else if ((!this.view.isFocused()) && (!this.view.isHovered())) {
          this.view.setTranslationZ(0.0F);
        } else {
          this.view.setTranslationZ(this.hoveredFocusedTranslationZ);
        }
      }
      else
      {
        this.view.setElevation(0.0F);
        this.view.setTranslationZ(0.0F);
      }
    }
  }
  
  void onElevationsChanged(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (Build.VERSION.SDK_INT == 21)
    {
      this.view.refreshDrawableState();
    }
    else
    {
      StateListAnimator localStateListAnimator = new StateListAnimator();
      localStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(paramFloat1, paramFloat3));
      localStateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(paramFloat1, paramFloat2));
      localStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(paramFloat1, paramFloat2));
      localStateListAnimator.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(paramFloat1, paramFloat2));
      AnimatorSet localAnimatorSet = new AnimatorSet();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ObjectAnimator.ofFloat(this.view, "elevation", new float[] { paramFloat1 }).setDuration(0L));
      if ((Build.VERSION.SDK_INT >= 22) && (Build.VERSION.SDK_INT <= 24)) {
        localArrayList.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[] { this.view.getTranslationZ() }).setDuration(100L));
      }
      localArrayList.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[] { 0.0F }).setDuration(100L));
      localAnimatorSet.playSequentially((Animator[])localArrayList.toArray(new Animator[0]));
      localAnimatorSet.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
      localStateListAnimator.addState(ENABLED_STATE_SET, localAnimatorSet);
      localStateListAnimator.addState(EMPTY_STATE_SET, createElevationAnimator(0.0F, 0.0F));
      this.view.setStateListAnimator(localStateListAnimator);
    }
    if (shouldAddPadding()) {
      updatePadding();
    }
  }
  
  boolean requirePreDrawListener()
  {
    return false;
  }
  
  void setRippleColor(ColorStateList paramColorStateList)
  {
    if ((this.rippleDrawable instanceof RippleDrawable)) {
      ((RippleDrawable)this.rippleDrawable).setColor(RippleUtils.sanitizeRippleDrawableColor(paramColorStateList));
    } else {
      super.setRippleColor(paramColorStateList);
    }
  }
  
  boolean shouldAddPadding()
  {
    boolean bool;
    if ((!this.shadowViewDelegate.isCompatPaddingEnabled()) && (shouldExpandBoundsForA11y())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void updateFromViewRotation() {}
  
  static class AlwaysStatefulMaterialShapeDrawable
    extends MaterialShapeDrawable
  {
    AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel paramShapeAppearanceModel)
    {
      super();
    }
    
    public boolean isStateful()
    {
      return true;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/FloatingActionButtonImplLollipop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */