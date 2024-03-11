package com.google.android.material.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;

class MaterialCardViewHelper
{
  private static final float CARD_VIEW_SHADOW_MULTIPLIER = 1.5F;
  private static final int CHECKED_ICON_LAYER_INDEX = 2;
  private static final Drawable CHECKED_ICON_NONE;
  private static final double COS_45 = Math.cos(Math.toRadians(45.0D));
  private static final int DEFAULT_STROKE_VALUE = -1;
  private final MaterialShapeDrawable bgDrawable;
  private boolean checkable;
  private Drawable checkedIcon;
  private int checkedIconGravity;
  private int checkedIconMargin;
  private int checkedIconSize;
  private ColorStateList checkedIconTint;
  private LayerDrawable clickableForegroundDrawable;
  private MaterialShapeDrawable compatRippleDrawable;
  private Drawable fgDrawable;
  private final MaterialShapeDrawable foregroundContentDrawable;
  private MaterialShapeDrawable foregroundShapeDrawable;
  private boolean isBackgroundOverwritten = false;
  private final MaterialCardView materialCardView;
  private ColorStateList rippleColor;
  private Drawable rippleDrawable;
  private ShapeAppearanceModel shapeAppearanceModel;
  private ColorStateList strokeColor;
  private int strokeWidth;
  private final Rect userContentPadding = new Rect();
  
  static
  {
    ColorDrawable localColorDrawable;
    if (Build.VERSION.SDK_INT <= 28) {
      localColorDrawable = new ColorDrawable();
    } else {
      localColorDrawable = null;
    }
    CHECKED_ICON_NONE = localColorDrawable;
  }
  
  public MaterialCardViewHelper(MaterialCardView paramMaterialCardView, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this.materialCardView = paramMaterialCardView;
    Object localObject = new MaterialShapeDrawable(paramMaterialCardView.getContext(), paramAttributeSet, paramInt1, paramInt2);
    this.bgDrawable = ((MaterialShapeDrawable)localObject);
    ((MaterialShapeDrawable)localObject).initializeElevationOverlay(paramMaterialCardView.getContext());
    ((MaterialShapeDrawable)localObject).setShadowColor(-12303292);
    localObject = ((MaterialShapeDrawable)localObject).getShapeAppearanceModel().toBuilder();
    paramMaterialCardView = paramMaterialCardView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.CardView, paramInt1, R.style.CardView);
    if (paramMaterialCardView.hasValue(R.styleable.CardView_cardCornerRadius)) {
      ((ShapeAppearanceModel.Builder)localObject).setAllCornerSizes(paramMaterialCardView.getDimension(R.styleable.CardView_cardCornerRadius, 0.0F));
    }
    this.foregroundContentDrawable = new MaterialShapeDrawable();
    setShapeAppearanceModel(((ShapeAppearanceModel.Builder)localObject).build());
    paramMaterialCardView.recycle();
  }
  
  private float calculateActualCornerPadding()
  {
    return Math.max(Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopLeftCorner(), this.bgDrawable.getTopLeftCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getTopRightCorner(), this.bgDrawable.getTopRightCornerResolvedSize())), Math.max(calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomRightCorner(), this.bgDrawable.getBottomRightCornerResolvedSize()), calculateCornerPaddingForCornerTreatment(this.shapeAppearanceModel.getBottomLeftCorner(), this.bgDrawable.getBottomLeftCornerResolvedSize())));
  }
  
  private float calculateCornerPaddingForCornerTreatment(CornerTreatment paramCornerTreatment, float paramFloat)
  {
    if ((paramCornerTreatment instanceof RoundedCornerTreatment)) {
      return (float)((1.0D - COS_45) * paramFloat);
    }
    if ((paramCornerTreatment instanceof CutCornerTreatment)) {
      return paramFloat / 2.0F;
    }
    return 0.0F;
  }
  
  private float calculateHorizontalBackgroundPadding()
  {
    float f2 = this.materialCardView.getMaxCardElevation();
    float f1;
    if (shouldAddCornerPaddingOutsideCardBackground()) {
      f1 = calculateActualCornerPadding();
    } else {
      f1 = 0.0F;
    }
    return f2 + f1;
  }
  
  private float calculateVerticalBackgroundPadding()
  {
    float f2 = this.materialCardView.getMaxCardElevation();
    float f1;
    if (shouldAddCornerPaddingOutsideCardBackground()) {
      f1 = calculateActualCornerPadding();
    } else {
      f1 = 0.0F;
    }
    return f2 * 1.5F + f1;
  }
  
  private boolean canClipToOutline()
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 21) && (this.bgDrawable.isRoundRect())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private Drawable createCompatRippleDrawable()
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    MaterialShapeDrawable localMaterialShapeDrawable = createForegroundShapeDrawable();
    this.compatRippleDrawable = localMaterialShapeDrawable;
    localMaterialShapeDrawable.setFillColor(this.rippleColor);
    localMaterialShapeDrawable = this.compatRippleDrawable;
    localStateListDrawable.addState(new int[] { 16842919 }, localMaterialShapeDrawable);
    return localStateListDrawable;
  }
  
  private Drawable createForegroundRippleDrawable()
  {
    if (RippleUtils.USE_FRAMEWORK_RIPPLE)
    {
      this.foregroundShapeDrawable = createForegroundShapeDrawable();
      return new RippleDrawable(this.rippleColor, null, this.foregroundShapeDrawable);
    }
    return createCompatRippleDrawable();
  }
  
  private MaterialShapeDrawable createForegroundShapeDrawable()
  {
    return new MaterialShapeDrawable(this.shapeAppearanceModel);
  }
  
  private Drawable getClickableForeground()
  {
    if (this.rippleDrawable == null) {
      this.rippleDrawable = createForegroundRippleDrawable();
    }
    if (this.clickableForegroundDrawable == null)
    {
      LayerDrawable localLayerDrawable = new LayerDrawable(new Drawable[] { this.rippleDrawable, this.foregroundContentDrawable, this.checkedIcon });
      this.clickableForegroundDrawable = localLayerDrawable;
      localLayerDrawable.setId(2, R.id.mtrl_card_checked_layer_id);
    }
    return this.clickableForegroundDrawable;
  }
  
  private float getParentCardViewCalculatedCornerPadding()
  {
    if ((this.materialCardView.getPreventCornerOverlap()) && ((Build.VERSION.SDK_INT < 21) || (this.materialCardView.getUseCompatPadding()))) {
      return (float)((1.0D - COS_45) * this.materialCardView.getCardViewRadius());
    }
    return 0.0F;
  }
  
  private Drawable insetDrawable(Drawable paramDrawable)
  {
    int j = 0;
    int k = 0;
    int i;
    if (Build.VERSION.SDK_INT < 21) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      i = k;
      if (!this.materialCardView.getUseCompatPadding()) {}
    }
    else
    {
      j = (int)Math.ceil(calculateVerticalBackgroundPadding());
      i = (int)Math.ceil(calculateHorizontalBackgroundPadding());
    }
    new InsetDrawable(paramDrawable, i, j, i, j)
    {
      public int getMinimumHeight()
      {
        return -1;
      }
      
      public int getMinimumWidth()
      {
        return -1;
      }
      
      public boolean getPadding(Rect paramAnonymousRect)
      {
        return false;
      }
    };
  }
  
  private boolean isCheckedIconBottom()
  {
    boolean bool;
    if ((this.checkedIconGravity & 0x50) == 80) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isCheckedIconEnd()
  {
    boolean bool;
    if ((this.checkedIconGravity & 0x800005) == 8388613) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldAddCornerPaddingInsideCardBackground()
  {
    boolean bool;
    if ((this.materialCardView.getPreventCornerOverlap()) && (!canClipToOutline())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean shouldAddCornerPaddingOutsideCardBackground()
  {
    boolean bool;
    if ((this.materialCardView.getPreventCornerOverlap()) && (canClipToOutline()) && (this.materialCardView.getUseCompatPadding())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void updateInsetForeground(Drawable paramDrawable)
  {
    if ((Build.VERSION.SDK_INT >= 23) && ((this.materialCardView.getForeground() instanceof InsetDrawable))) {
      ((InsetDrawable)this.materialCardView.getForeground()).setDrawable(paramDrawable);
    } else {
      this.materialCardView.setForeground(insetDrawable(paramDrawable));
    }
  }
  
  private void updateRippleColor()
  {
    if (RippleUtils.USE_FRAMEWORK_RIPPLE)
    {
      localObject = this.rippleDrawable;
      if (localObject != null)
      {
        ((RippleDrawable)localObject).setColor(this.rippleColor);
        return;
      }
    }
    Object localObject = this.compatRippleDrawable;
    if (localObject != null) {
      ((MaterialShapeDrawable)localObject).setFillColor(this.rippleColor);
    }
  }
  
  void forceRippleRedraw()
  {
    Object localObject = this.rippleDrawable;
    if (localObject != null)
    {
      localObject = ((Drawable)localObject).getBounds();
      int i = ((Rect)localObject).bottom;
      this.rippleDrawable.setBounds(((Rect)localObject).left, ((Rect)localObject).top, ((Rect)localObject).right, i - 1);
      this.rippleDrawable.setBounds(((Rect)localObject).left, ((Rect)localObject).top, ((Rect)localObject).right, i);
    }
  }
  
  MaterialShapeDrawable getBackground()
  {
    return this.bgDrawable;
  }
  
  ColorStateList getCardBackgroundColor()
  {
    return this.bgDrawable.getFillColor();
  }
  
  ColorStateList getCardForegroundColor()
  {
    return this.foregroundContentDrawable.getFillColor();
  }
  
  Drawable getCheckedIcon()
  {
    return this.checkedIcon;
  }
  
  int getCheckedIconGravity()
  {
    return this.checkedIconGravity;
  }
  
  int getCheckedIconMargin()
  {
    return this.checkedIconMargin;
  }
  
  int getCheckedIconSize()
  {
    return this.checkedIconSize;
  }
  
  ColorStateList getCheckedIconTint()
  {
    return this.checkedIconTint;
  }
  
  float getCornerRadius()
  {
    return this.bgDrawable.getTopLeftCornerResolvedSize();
  }
  
  float getProgress()
  {
    return this.bgDrawable.getInterpolation();
  }
  
  ColorStateList getRippleColor()
  {
    return this.rippleColor;
  }
  
  ShapeAppearanceModel getShapeAppearanceModel()
  {
    return this.shapeAppearanceModel;
  }
  
  int getStrokeColor()
  {
    ColorStateList localColorStateList = this.strokeColor;
    int i;
    if (localColorStateList == null) {
      i = -1;
    } else {
      i = localColorStateList.getDefaultColor();
    }
    return i;
  }
  
  ColorStateList getStrokeColorStateList()
  {
    return this.strokeColor;
  }
  
  int getStrokeWidth()
  {
    return this.strokeWidth;
  }
  
  Rect getUserContentPadding()
  {
    return this.userContentPadding;
  }
  
  boolean isBackgroundOverwritten()
  {
    return this.isBackgroundOverwritten;
  }
  
  boolean isCheckable()
  {
    return this.checkable;
  }
  
  void loadFromAttributes(TypedArray paramTypedArray)
  {
    ColorStateList localColorStateList = MaterialResources.getColorStateList(this.materialCardView.getContext(), paramTypedArray, R.styleable.MaterialCardView_strokeColor);
    this.strokeColor = localColorStateList;
    if (localColorStateList == null) {
      this.strokeColor = ColorStateList.valueOf(-1);
    }
    this.strokeWidth = paramTypedArray.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
    boolean bool = paramTypedArray.getBoolean(R.styleable.MaterialCardView_android_checkable, false);
    this.checkable = bool;
    this.materialCardView.setLongClickable(bool);
    this.checkedIconTint = MaterialResources.getColorStateList(this.materialCardView.getContext(), paramTypedArray, R.styleable.MaterialCardView_checkedIconTint);
    setCheckedIcon(MaterialResources.getDrawable(this.materialCardView.getContext(), paramTypedArray, R.styleable.MaterialCardView_checkedIcon));
    setCheckedIconSize(paramTypedArray.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconSize, 0));
    setCheckedIconMargin(paramTypedArray.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconMargin, 0));
    this.checkedIconGravity = paramTypedArray.getInteger(R.styleable.MaterialCardView_checkedIconGravity, 8388661);
    localColorStateList = MaterialResources.getColorStateList(this.materialCardView.getContext(), paramTypedArray, R.styleable.MaterialCardView_rippleColor);
    this.rippleColor = localColorStateList;
    if (localColorStateList == null) {
      this.rippleColor = ColorStateList.valueOf(MaterialColors.getColor(this.materialCardView, R.attr.colorControlHighlight));
    }
    setCardForegroundColor(MaterialResources.getColorStateList(this.materialCardView.getContext(), paramTypedArray, R.styleable.MaterialCardView_cardForegroundColor));
    updateRippleColor();
    updateElevation();
    updateStroke();
    this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
    if (this.materialCardView.isClickable()) {
      paramTypedArray = getClickableForeground();
    } else {
      paramTypedArray = this.foregroundContentDrawable;
    }
    this.fgDrawable = paramTypedArray;
    this.materialCardView.setForeground(insetDrawable(paramTypedArray));
  }
  
  void recalculateCheckedIconPosition(int paramInt1, int paramInt2)
  {
    if (this.clickableForegroundDrawable != null)
    {
      int i;
      if (Build.VERSION.SDK_INT < 21) {
        i = 1;
      } else {
        i = 0;
      }
      int k = 0;
      int m = 0;
      if ((i != 0) || (this.materialCardView.getUseCompatPadding()))
      {
        k = (int)Math.ceil(calculateVerticalBackgroundPadding() * 2.0F);
        m = (int)Math.ceil(calculateHorizontalBackgroundPadding() * 2.0F);
      }
      if (isCheckedIconEnd()) {
        i = paramInt1 - this.checkedIconMargin - this.checkedIconSize - m;
      } else {
        i = this.checkedIconMargin;
      }
      int j;
      if (isCheckedIconBottom()) {
        j = this.checkedIconMargin;
      } else {
        j = paramInt2 - this.checkedIconMargin - this.checkedIconSize - k;
      }
      if (isCheckedIconEnd()) {
        paramInt1 = this.checkedIconMargin;
      } else {
        paramInt1 = paramInt1 - this.checkedIconMargin - this.checkedIconSize - m;
      }
      if (isCheckedIconBottom()) {
        paramInt2 = paramInt2 - this.checkedIconMargin - this.checkedIconSize - k;
      } else {
        paramInt2 = this.checkedIconMargin;
      }
      m = i;
      k = paramInt1;
      if (ViewCompat.getLayoutDirection(this.materialCardView) == 1)
      {
        k = i;
        m = paramInt1;
      }
      this.clickableForegroundDrawable.setLayerInset(2, m, paramInt2, k, j);
    }
  }
  
  void setBackgroundOverwritten(boolean paramBoolean)
  {
    this.isBackgroundOverwritten = paramBoolean;
  }
  
  void setCardBackgroundColor(ColorStateList paramColorStateList)
  {
    this.bgDrawable.setFillColor(paramColorStateList);
  }
  
  void setCardForegroundColor(ColorStateList paramColorStateList)
  {
    MaterialShapeDrawable localMaterialShapeDrawable = this.foregroundContentDrawable;
    if (paramColorStateList == null) {
      paramColorStateList = ColorStateList.valueOf(0);
    }
    localMaterialShapeDrawable.setFillColor(paramColorStateList);
  }
  
  void setCheckable(boolean paramBoolean)
  {
    this.checkable = paramBoolean;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    Drawable localDrawable = this.checkedIcon;
    if (localDrawable != null)
    {
      int i;
      if (paramBoolean) {
        i = 255;
      } else {
        i = 0;
      }
      localDrawable.setAlpha(i);
    }
  }
  
  void setCheckedIcon(Drawable paramDrawable)
  {
    if (paramDrawable != null)
    {
      paramDrawable = DrawableCompat.wrap(paramDrawable).mutate();
      this.checkedIcon = paramDrawable;
      DrawableCompat.setTintList(paramDrawable, this.checkedIconTint);
      setChecked(this.materialCardView.isChecked());
    }
    else
    {
      this.checkedIcon = CHECKED_ICON_NONE;
    }
    paramDrawable = this.clickableForegroundDrawable;
    if (paramDrawable != null) {
      paramDrawable.setDrawableByLayerId(R.id.mtrl_card_checked_layer_id, this.checkedIcon);
    }
  }
  
  void setCheckedIconGravity(int paramInt)
  {
    this.checkedIconGravity = paramInt;
    recalculateCheckedIconPosition(this.materialCardView.getMeasuredWidth(), this.materialCardView.getMeasuredHeight());
  }
  
  void setCheckedIconMargin(int paramInt)
  {
    this.checkedIconMargin = paramInt;
  }
  
  void setCheckedIconSize(int paramInt)
  {
    this.checkedIconSize = paramInt;
  }
  
  void setCheckedIconTint(ColorStateList paramColorStateList)
  {
    this.checkedIconTint = paramColorStateList;
    Drawable localDrawable = this.checkedIcon;
    if (localDrawable != null) {
      DrawableCompat.setTintList(localDrawable, paramColorStateList);
    }
  }
  
  void setCornerRadius(float paramFloat)
  {
    setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(paramFloat));
    this.fgDrawable.invalidateSelf();
    if ((shouldAddCornerPaddingOutsideCardBackground()) || (shouldAddCornerPaddingInsideCardBackground())) {
      updateContentPadding();
    }
    if (shouldAddCornerPaddingOutsideCardBackground()) {
      updateInsets();
    }
  }
  
  void setProgress(float paramFloat)
  {
    this.bgDrawable.setInterpolation(paramFloat);
    MaterialShapeDrawable localMaterialShapeDrawable = this.foregroundContentDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setInterpolation(paramFloat);
    }
    localMaterialShapeDrawable = this.foregroundShapeDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setInterpolation(paramFloat);
    }
  }
  
  void setRippleColor(ColorStateList paramColorStateList)
  {
    this.rippleColor = paramColorStateList;
    updateRippleColor();
  }
  
  void setShapeAppearanceModel(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    this.shapeAppearanceModel = paramShapeAppearanceModel;
    this.bgDrawable.setShapeAppearanceModel(paramShapeAppearanceModel);
    MaterialShapeDrawable localMaterialShapeDrawable = this.bgDrawable;
    localMaterialShapeDrawable.setShadowBitmapDrawingEnable(localMaterialShapeDrawable.isRoundRect() ^ true);
    localMaterialShapeDrawable = this.foregroundContentDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setShapeAppearanceModel(paramShapeAppearanceModel);
    }
    localMaterialShapeDrawable = this.foregroundShapeDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setShapeAppearanceModel(paramShapeAppearanceModel);
    }
    localMaterialShapeDrawable = this.compatRippleDrawable;
    if (localMaterialShapeDrawable != null) {
      localMaterialShapeDrawable.setShapeAppearanceModel(paramShapeAppearanceModel);
    }
  }
  
  void setStrokeColor(ColorStateList paramColorStateList)
  {
    if (this.strokeColor == paramColorStateList) {
      return;
    }
    this.strokeColor = paramColorStateList;
    updateStroke();
  }
  
  void setStrokeWidth(int paramInt)
  {
    if (paramInt == this.strokeWidth) {
      return;
    }
    this.strokeWidth = paramInt;
    updateStroke();
  }
  
  void setUserContentPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.userContentPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    updateContentPadding();
  }
  
  void updateClickable()
  {
    Drawable localDrawable = this.fgDrawable;
    Object localObject;
    if (this.materialCardView.isClickable()) {
      localObject = getClickableForeground();
    } else {
      localObject = this.foregroundContentDrawable;
    }
    this.fgDrawable = ((Drawable)localObject);
    if (localDrawable != localObject) {
      updateInsetForeground((Drawable)localObject);
    }
  }
  
  void updateContentPadding()
  {
    if ((!shouldAddCornerPaddingInsideCardBackground()) && (!shouldAddCornerPaddingOutsideCardBackground())) {
      i = 0;
    } else {
      i = 1;
    }
    float f;
    if (i != 0) {
      f = calculateActualCornerPadding();
    } else {
      f = 0.0F;
    }
    int i = (int)(f - getParentCardViewCalculatedCornerPadding());
    this.materialCardView.setAncestorContentPadding(this.userContentPadding.left + i, this.userContentPadding.top + i, this.userContentPadding.right + i, this.userContentPadding.bottom + i);
  }
  
  void updateElevation()
  {
    this.bgDrawable.setElevation(this.materialCardView.getCardElevation());
  }
  
  void updateInsets()
  {
    if (!isBackgroundOverwritten()) {
      this.materialCardView.setBackgroundInternal(insetDrawable(this.bgDrawable));
    }
    this.materialCardView.setForeground(insetDrawable(this.fgDrawable));
  }
  
  void updateStroke()
  {
    this.foregroundContentDrawable.setStroke(this.strokeWidth, this.strokeColor);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/card/MaterialCardViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */