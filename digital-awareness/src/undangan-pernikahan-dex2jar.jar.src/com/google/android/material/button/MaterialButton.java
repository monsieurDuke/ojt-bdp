package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton
  extends AppCompatButton
  implements Checkable, Shapeable
{
  private static final int[] CHECKABLE_STATE_SET = { 16842911 };
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Button;
  public static final int ICON_GRAVITY_END = 3;
  public static final int ICON_GRAVITY_START = 1;
  public static final int ICON_GRAVITY_TEXT_END = 4;
  public static final int ICON_GRAVITY_TEXT_START = 2;
  public static final int ICON_GRAVITY_TEXT_TOP = 32;
  public static final int ICON_GRAVITY_TOP = 16;
  private static final String LOG_TAG = "MaterialButton";
  private boolean broadcasting;
  private boolean checked;
  private Drawable icon;
  private int iconGravity;
  private int iconLeft;
  private int iconPadding;
  private int iconSize;
  private ColorStateList iconTint;
  private PorterDuff.Mode iconTintMode;
  private int iconTop;
  private final MaterialButtonHelper materialButtonHelper;
  private final LinkedHashSet<OnCheckedChangeListener> onCheckedChangeListeners = new LinkedHashSet();
  private OnPressedChangeListener onPressedChangeListenerInternal;
  
  public MaterialButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialButtonStyle);
  }
  
  public MaterialButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    boolean bool = false;
    this.checked = false;
    this.broadcasting = false;
    Context localContext = getContext();
    paramContext = ThemeEnforcement.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.MaterialButton, paramInt, i, new int[0]);
    this.iconPadding = paramContext.getDimensionPixelSize(R.styleable.MaterialButton_iconPadding, 0);
    this.iconTintMode = ViewUtils.parseTintMode(paramContext.getInt(R.styleable.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
    this.iconTint = MaterialResources.getColorStateList(getContext(), paramContext, R.styleable.MaterialButton_iconTint);
    this.icon = MaterialResources.getDrawable(getContext(), paramContext, R.styleable.MaterialButton_icon);
    this.iconGravity = paramContext.getInteger(R.styleable.MaterialButton_iconGravity, 1);
    this.iconSize = paramContext.getDimensionPixelSize(R.styleable.MaterialButton_iconSize, 0);
    paramAttributeSet = new MaterialButtonHelper(this, ShapeAppearanceModel.builder(localContext, paramAttributeSet, paramInt, i).build());
    this.materialButtonHelper = paramAttributeSet;
    paramAttributeSet.loadFromAttributes(paramContext);
    paramContext.recycle();
    setCompoundDrawablePadding(this.iconPadding);
    if (this.icon != null) {
      bool = true;
    }
    updateIcon(bool);
  }
  
  private String getA11yClassName()
  {
    Class localClass;
    if (isCheckable()) {
      localClass = CompoundButton.class;
    } else {
      localClass = Button.class;
    }
    return localClass.getName();
  }
  
  private Layout.Alignment getActualTextAlignment()
  {
    if (Build.VERSION.SDK_INT < 17) {
      return getGravityTextAlignment();
    }
    switch (getTextAlignment())
    {
    case 2: 
    case 5: 
    default: 
      return Layout.Alignment.ALIGN_NORMAL;
    case 4: 
      return Layout.Alignment.ALIGN_CENTER;
    case 3: 
    case 6: 
      return Layout.Alignment.ALIGN_OPPOSITE;
    }
    return getGravityTextAlignment();
  }
  
  private Layout.Alignment getGravityTextAlignment()
  {
    switch (getGravity() & 0x800007)
    {
    default: 
      return Layout.Alignment.ALIGN_NORMAL;
    case 5: 
    case 8388613: 
      return Layout.Alignment.ALIGN_OPPOSITE;
    }
    return Layout.Alignment.ALIGN_CENTER;
  }
  
  private int getTextHeight()
  {
    TextPaint localTextPaint = getPaint();
    Object localObject2 = getText().toString();
    Object localObject1 = localObject2;
    if (getTransformationMethod() != null) {
      localObject1 = getTransformationMethod().getTransformation((CharSequence)localObject2, this).toString();
    }
    localObject2 = new Rect();
    localTextPaint.getTextBounds((String)localObject1, 0, ((String)localObject1).length(), (Rect)localObject2);
    return Math.min(((Rect)localObject2).height(), getLayout().getHeight());
  }
  
  private int getTextWidth()
  {
    TextPaint localTextPaint = getPaint();
    String str2 = getText().toString();
    String str1 = str2;
    if (getTransformationMethod() != null) {
      str1 = getTransformationMethod().getTransformation(str2, this).toString();
    }
    return Math.min((int)localTextPaint.measureText(str1), getLayout().getEllipsizedWidth());
  }
  
  private boolean isIconEnd()
  {
    int i = this.iconGravity;
    boolean bool;
    if ((i != 3) && (i != 4)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean isIconStart()
  {
    int i = this.iconGravity;
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (i != 1) {
      if (i == 2) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  private boolean isIconTop()
  {
    int i = this.iconGravity;
    boolean bool;
    if ((i != 16) && (i != 32)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean isLayoutRTL()
  {
    int i = ViewCompat.getLayoutDirection(this);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private boolean isUsingOriginalBackground()
  {
    MaterialButtonHelper localMaterialButtonHelper = this.materialButtonHelper;
    boolean bool;
    if ((localMaterialButtonHelper != null) && (!localMaterialButtonHelper.isBackgroundOverwritten())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void resetIconDrawable()
  {
    if (isIconStart()) {
      TextViewCompat.setCompoundDrawablesRelative(this, this.icon, null, null, null);
    } else if (isIconEnd()) {
      TextViewCompat.setCompoundDrawablesRelative(this, null, null, this.icon, null);
    } else if (isIconTop()) {
      TextViewCompat.setCompoundDrawablesRelative(this, null, this.icon, null, null);
    }
  }
  
  private void updateIcon(boolean paramBoolean)
  {
    Object localObject1 = this.icon;
    int k = 1;
    int i;
    if (localObject1 != null)
    {
      localObject1 = DrawableCompat.wrap((Drawable)localObject1).mutate();
      this.icon = ((Drawable)localObject1);
      DrawableCompat.setTintList((Drawable)localObject1, this.iconTint);
      localObject1 = this.iconTintMode;
      if (localObject1 != null) {
        DrawableCompat.setTintMode(this.icon, (PorterDuff.Mode)localObject1);
      }
      i = this.iconSize;
      if (i == 0) {
        i = this.icon.getIntrinsicWidth();
      }
      int j = this.iconSize;
      if (j == 0) {
        j = this.icon.getIntrinsicHeight();
      }
      localObject1 = this.icon;
      int n = this.iconLeft;
      int m = this.iconTop;
      ((Drawable)localObject1).setBounds(n, m, n + i, m + j);
      this.icon.setVisible(true, paramBoolean);
    }
    if (paramBoolean)
    {
      resetIconDrawable();
      return;
    }
    Object localObject3 = TextViewCompat.getCompoundDrawablesRelative(this);
    localObject1 = localObject3[0];
    Object localObject2 = localObject3[1];
    localObject3 = localObject3[2];
    if (((isIconStart()) && (localObject1 != this.icon)) || ((isIconEnd()) && (localObject3 != this.icon)) || ((isIconTop()) && (localObject2 != this.icon))) {
      i = k;
    } else {
      i = 0;
    }
    if (i != 0) {
      resetIconDrawable();
    }
  }
  
  private void updateIconPosition(int paramInt1, int paramInt2)
  {
    if ((this.icon != null) && (getLayout() != null))
    {
      int i;
      if ((!isIconStart()) && (!isIconEnd())) {
        if (isIconTop())
        {
          this.iconLeft = 0;
          if (this.iconGravity == 16)
          {
            this.iconTop = 0;
            updateIcon(false);
            return;
          }
          i = this.iconSize;
          paramInt1 = i;
          if (i == 0) {
            paramInt1 = this.icon.getIntrinsicHeight();
          }
          paramInt1 = (paramInt2 - getTextHeight() - getPaddingTop() - paramInt1 - this.iconPadding - getPaddingBottom()) / 2;
          if (this.iconTop == paramInt1) {
            break label307;
          }
          this.iconTop = paramInt1;
          updateIcon(false);
          break label307;
        }
      }
      for (;;)
      {
        break;
        this.iconTop = 0;
        Layout.Alignment localAlignment = getActualTextAlignment();
        paramInt2 = this.iconGravity;
        boolean bool1 = true;
        if ((paramInt2 == 1) || (paramInt2 == 3) || ((paramInt2 == 2) && (localAlignment == Layout.Alignment.ALIGN_NORMAL)) || ((this.iconGravity == 4) && (localAlignment == Layout.Alignment.ALIGN_OPPOSITE))) {
          break label308;
        }
        i = this.iconSize;
        paramInt2 = i;
        if (i == 0) {
          paramInt2 = this.icon.getIntrinsicWidth();
        }
        paramInt1 = paramInt1 - getTextWidth() - ViewCompat.getPaddingEnd(this) - paramInt2 - this.iconPadding - ViewCompat.getPaddingStart(this);
        if (localAlignment == Layout.Alignment.ALIGN_CENTER) {
          paramInt1 /= 2;
        }
        boolean bool2 = isLayoutRTL();
        if (this.iconGravity != 4) {
          bool1 = false;
        }
        paramInt2 = paramInt1;
        if (bool2 != bool1) {
          paramInt2 = -paramInt1;
        }
        if (this.iconLeft != paramInt2)
        {
          this.iconLeft = paramInt2;
          updateIcon(false);
        }
      }
      label307:
      return;
      label308:
      this.iconLeft = 0;
      updateIcon(false);
      return;
    }
  }
  
  public void addOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    this.onCheckedChangeListeners.add(paramOnCheckedChangeListener);
  }
  
  public void clearOnCheckedChangeListeners()
  {
    this.onCheckedChangeListeners.clear();
  }
  
  public ColorStateList getBackgroundTintList()
  {
    return getSupportBackgroundTintList();
  }
  
  public PorterDuff.Mode getBackgroundTintMode()
  {
    return getSupportBackgroundTintMode();
  }
  
  public int getCornerRadius()
  {
    int i;
    if (isUsingOriginalBackground()) {
      i = this.materialButtonHelper.getCornerRadius();
    } else {
      i = 0;
    }
    return i;
  }
  
  public Drawable getIcon()
  {
    return this.icon;
  }
  
  public int getIconGravity()
  {
    return this.iconGravity;
  }
  
  public int getIconPadding()
  {
    return this.iconPadding;
  }
  
  public int getIconSize()
  {
    return this.iconSize;
  }
  
  public ColorStateList getIconTint()
  {
    return this.iconTint;
  }
  
  public PorterDuff.Mode getIconTintMode()
  {
    return this.iconTintMode;
  }
  
  public int getInsetBottom()
  {
    return this.materialButtonHelper.getInsetBottom();
  }
  
  public int getInsetTop()
  {
    return this.materialButtonHelper.getInsetTop();
  }
  
  public ColorStateList getRippleColor()
  {
    ColorStateList localColorStateList;
    if (isUsingOriginalBackground()) {
      localColorStateList = this.materialButtonHelper.getRippleColor();
    } else {
      localColorStateList = null;
    }
    return localColorStateList;
  }
  
  public ShapeAppearanceModel getShapeAppearanceModel()
  {
    if (isUsingOriginalBackground()) {
      return this.materialButtonHelper.getShapeAppearanceModel();
    }
    throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
  }
  
  public ColorStateList getStrokeColor()
  {
    ColorStateList localColorStateList;
    if (isUsingOriginalBackground()) {
      localColorStateList = this.materialButtonHelper.getStrokeColor();
    } else {
      localColorStateList = null;
    }
    return localColorStateList;
  }
  
  public int getStrokeWidth()
  {
    int i;
    if (isUsingOriginalBackground()) {
      i = this.materialButtonHelper.getStrokeWidth();
    } else {
      i = 0;
    }
    return i;
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    if (isUsingOriginalBackground()) {
      return this.materialButtonHelper.getSupportBackgroundTintList();
    }
    return super.getSupportBackgroundTintList();
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    if (isUsingOriginalBackground()) {
      return this.materialButtonHelper.getSupportBackgroundTintMode();
    }
    return super.getSupportBackgroundTintMode();
  }
  
  public boolean isCheckable()
  {
    MaterialButtonHelper localMaterialButtonHelper = this.materialButtonHelper;
    boolean bool;
    if ((localMaterialButtonHelper != null) && (localMaterialButtonHelper.isCheckable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (isUsingOriginalBackground()) {
      MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable());
    }
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 2);
    if (isCheckable()) {
      mergeDrawableStates(arrayOfInt, CHECKABLE_STATE_SET);
    }
    if (isChecked()) {
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    }
    return arrayOfInt;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(getA11yClassName());
    paramAccessibilityEvent.setChecked(isChecked());
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(getA11yClassName());
    paramAccessibilityNodeInfo.setCheckable(isCheckable());
    paramAccessibilityNodeInfo.setChecked(isChecked());
    paramAccessibilityNodeInfo.setClickable(isClickable());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (Build.VERSION.SDK_INT == 21)
    {
      MaterialButtonHelper localMaterialButtonHelper = this.materialButtonHelper;
      if (localMaterialButtonHelper != null) {
        localMaterialButtonHelper.updateMaskBounds(paramInt4 - paramInt2, paramInt3 - paramInt1);
      }
    }
    updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    setChecked(paramParcelable.checked);
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.checked = this.checked;
    return localSavedState;
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
    updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
  }
  
  public boolean performClick()
  {
    toggle();
    return super.performClick();
  }
  
  public void refreshDrawableState()
  {
    super.refreshDrawableState();
    if (this.icon != null)
    {
      int[] arrayOfInt = getDrawableState();
      if (this.icon.setState(arrayOfInt)) {
        invalidate();
      }
    }
  }
  
  public void removeOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    this.onCheckedChangeListeners.remove(paramOnCheckedChangeListener);
  }
  
  public void setBackground(Drawable paramDrawable)
  {
    setBackgroundDrawable(paramDrawable);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setBackgroundColor(paramInt);
    } else {
      super.setBackgroundColor(paramInt);
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    if (isUsingOriginalBackground())
    {
      if (paramDrawable != getBackground())
      {
        Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
        this.materialButtonHelper.setBackgroundOverwritten();
        super.setBackgroundDrawable(paramDrawable);
      }
      else
      {
        getBackground().setState(paramDrawable.getState());
      }
    }
    else {
      super.setBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    Drawable localDrawable = null;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    }
    setBackgroundDrawable(localDrawable);
  }
  
  public void setBackgroundTintList(ColorStateList paramColorStateList)
  {
    setSupportBackgroundTintList(paramColorStateList);
  }
  
  public void setBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    setSupportBackgroundTintMode(paramMode);
  }
  
  public void setCheckable(boolean paramBoolean)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setCheckable(paramBoolean);
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    if ((isCheckable()) && (isEnabled()) && (this.checked != paramBoolean))
    {
      this.checked = paramBoolean;
      refreshDrawableState();
      if ((getParent() instanceof MaterialButtonToggleGroup)) {
        ((MaterialButtonToggleGroup)getParent()).onButtonCheckedStateChanged(this, this.checked);
      }
      if (this.broadcasting) {
        return;
      }
      this.broadcasting = true;
      Iterator localIterator = this.onCheckedChangeListeners.iterator();
      while (localIterator.hasNext()) {
        ((OnCheckedChangeListener)localIterator.next()).onCheckedChanged(this, this.checked);
      }
      this.broadcasting = false;
    }
  }
  
  public void setCornerRadius(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setCornerRadius(paramInt);
    }
  }
  
  public void setCornerRadiusResource(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      setCornerRadius(getResources().getDimensionPixelSize(paramInt));
    }
  }
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.getMaterialShapeDrawable().setElevation(paramFloat);
    }
  }
  
  public void setIcon(Drawable paramDrawable)
  {
    if (this.icon != paramDrawable)
    {
      this.icon = paramDrawable;
      updateIcon(true);
      updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }
  }
  
  public void setIconGravity(int paramInt)
  {
    if (this.iconGravity != paramInt)
    {
      this.iconGravity = paramInt;
      updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }
  }
  
  public void setIconPadding(int paramInt)
  {
    if (this.iconPadding != paramInt)
    {
      this.iconPadding = paramInt;
      setCompoundDrawablePadding(paramInt);
    }
  }
  
  public void setIconResource(int paramInt)
  {
    Drawable localDrawable = null;
    if (paramInt != 0) {
      localDrawable = AppCompatResources.getDrawable(getContext(), paramInt);
    }
    setIcon(localDrawable);
  }
  
  public void setIconSize(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (this.iconSize != paramInt)
      {
        this.iconSize = paramInt;
        updateIcon(true);
      }
      return;
    }
    throw new IllegalArgumentException("iconSize cannot be less than 0");
  }
  
  public void setIconTint(ColorStateList paramColorStateList)
  {
    if (this.iconTint != paramColorStateList)
    {
      this.iconTint = paramColorStateList;
      updateIcon(false);
    }
  }
  
  public void setIconTintMode(PorterDuff.Mode paramMode)
  {
    if (this.iconTintMode != paramMode)
    {
      this.iconTintMode = paramMode;
      updateIcon(false);
    }
  }
  
  public void setIconTintResource(int paramInt)
  {
    setIconTint(AppCompatResources.getColorStateList(getContext(), paramInt));
  }
  
  public void setInsetBottom(int paramInt)
  {
    this.materialButtonHelper.setInsetBottom(paramInt);
  }
  
  public void setInsetTop(int paramInt)
  {
    this.materialButtonHelper.setInsetTop(paramInt);
  }
  
  void setInternalBackground(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
  }
  
  void setOnPressedChangeListenerInternal(OnPressedChangeListener paramOnPressedChangeListener)
  {
    this.onPressedChangeListenerInternal = paramOnPressedChangeListener;
  }
  
  public void setPressed(boolean paramBoolean)
  {
    OnPressedChangeListener localOnPressedChangeListener = this.onPressedChangeListenerInternal;
    if (localOnPressedChangeListener != null) {
      localOnPressedChangeListener.onPressedChanged(this, paramBoolean);
    }
    super.setPressed(paramBoolean);
  }
  
  public void setRippleColor(ColorStateList paramColorStateList)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setRippleColor(paramColorStateList);
    }
  }
  
  public void setRippleColorResource(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      setRippleColor(AppCompatResources.getColorStateList(getContext(), paramInt));
    }
  }
  
  public void setShapeAppearanceModel(ShapeAppearanceModel paramShapeAppearanceModel)
  {
    if (isUsingOriginalBackground())
    {
      this.materialButtonHelper.setShapeAppearanceModel(paramShapeAppearanceModel);
      return;
    }
    throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
  }
  
  void setShouldDrawSurfaceColorStroke(boolean paramBoolean)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setShouldDrawSurfaceColorStroke(paramBoolean);
    }
  }
  
  public void setStrokeColor(ColorStateList paramColorStateList)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setStrokeColor(paramColorStateList);
    }
  }
  
  public void setStrokeColorResource(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      setStrokeColor(AppCompatResources.getColorStateList(getContext(), paramInt));
    }
  }
  
  public void setStrokeWidth(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setStrokeWidth(paramInt);
    }
  }
  
  public void setStrokeWidthResource(int paramInt)
  {
    if (isUsingOriginalBackground()) {
      setStrokeWidth(getResources().getDimensionPixelSize(paramInt));
    }
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setSupportBackgroundTintList(paramColorStateList);
    } else {
      super.setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    if (isUsingOriginalBackground()) {
      this.materialButtonHelper.setSupportBackgroundTintMode(paramMode);
    } else {
      super.setSupportBackgroundTintMode(paramMode);
    }
  }
  
  public void setTextAlignment(int paramInt)
  {
    super.setTextAlignment(paramInt);
    updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
  }
  
  public void toggle()
  {
    setChecked(this.checked ^ true);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IconGravity {}
  
  public static abstract interface OnCheckedChangeListener
  {
    public abstract void onCheckedChanged(MaterialButton paramMaterialButton, boolean paramBoolean);
  }
  
  static abstract interface OnPressedChangeListener
  {
    public abstract void onPressedChanged(MaterialButton paramMaterialButton, boolean paramBoolean);
  }
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public MaterialButton.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new MaterialButton.SavedState(paramAnonymousParcel, null);
      }
      
      public MaterialButton.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new MaterialButton.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public MaterialButton.SavedState[] newArray(int paramAnonymousInt)
      {
        return new MaterialButton.SavedState[paramAnonymousInt];
      }
    };
    boolean checked;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      if (paramClassLoader == null) {
        getClass().getClassLoader();
      }
      readFromParcel(paramParcel);
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    private void readFromParcel(Parcel paramParcel)
    {
      int i = paramParcel.readInt();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      this.checked = bool;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.checked);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/button/MaterialButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */