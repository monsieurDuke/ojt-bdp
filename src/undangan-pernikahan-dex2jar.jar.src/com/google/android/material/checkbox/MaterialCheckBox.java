package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialCheckBox
  extends AppCompatCheckBox
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_CheckBox;
  private static final int[][] ENABLED_CHECKED_STATES;
  private boolean centerIfNoTextEnabled;
  private ColorStateList materialThemeColorsTintList;
  private boolean useMaterialThemeColors;
  
  static
  {
    int[] arrayOfInt3 = { 16842910, 16842912 };
    int[] arrayOfInt1 = { 16842910, -16842912 };
    int[] arrayOfInt2 = { -16842910, -16842912 };
    ENABLED_CHECKED_STATES = new int[][] { arrayOfInt3, arrayOfInt1, { -16842910, 16842912 }, arrayOfInt2 };
  }
  
  public MaterialCheckBox(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.checkboxStyle);
  }
  
  public MaterialCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialCheckBox, paramInt, i, new int[0]);
    if (paramAttributeSet.hasValue(R.styleable.MaterialCheckBox_buttonTint)) {
      CompoundButtonCompat.setButtonTintList(this, MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.MaterialCheckBox_buttonTint));
    }
    this.useMaterialThemeColors = paramAttributeSet.getBoolean(R.styleable.MaterialCheckBox_useMaterialThemeColors, false);
    this.centerIfNoTextEnabled = paramAttributeSet.getBoolean(R.styleable.MaterialCheckBox_centerIfNoTextEnabled, true);
    paramAttributeSet.recycle();
  }
  
  private ColorStateList getMaterialThemeColorsTintList()
  {
    if (this.materialThemeColorsTintList == null)
    {
      int[][] arrayOfInt1 = ENABLED_CHECKED_STATES;
      int[] arrayOfInt = new int[arrayOfInt1.length];
      int j = MaterialColors.getColor(this, R.attr.colorControlActivated);
      int k = MaterialColors.getColor(this, R.attr.colorSurface);
      int i = MaterialColors.getColor(this, R.attr.colorOnSurface);
      arrayOfInt[0] = MaterialColors.layer(k, j, 1.0F);
      arrayOfInt[1] = MaterialColors.layer(k, i, 0.54F);
      arrayOfInt[2] = MaterialColors.layer(k, i, 0.38F);
      arrayOfInt[3] = MaterialColors.layer(k, i, 0.38F);
      this.materialThemeColorsTintList = new ColorStateList(arrayOfInt1, arrayOfInt);
    }
    return this.materialThemeColorsTintList;
  }
  
  public boolean isCenterIfNoTextEnabled()
  {
    return this.centerIfNoTextEnabled;
  }
  
  public boolean isUseMaterialThemeColors()
  {
    return this.useMaterialThemeColors;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((this.useMaterialThemeColors) && (CompoundButtonCompat.getButtonTintList(this) == null)) {
      setUseMaterialThemeColors(true);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.centerIfNoTextEnabled) && (TextUtils.isEmpty(getText())))
    {
      Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this);
      if (localDrawable != null)
      {
        if (ViewUtils.isLayoutRtl(this)) {
          i = -1;
        } else {
          i = 1;
        }
        int j = (getWidth() - localDrawable.getIntrinsicWidth()) / 2 * i;
        int i = paramCanvas.save();
        paramCanvas.translate(j, 0.0F);
        super.onDraw(paramCanvas);
        paramCanvas.restoreToCount(i);
        if (getBackground() != null)
        {
          paramCanvas = localDrawable.getBounds();
          DrawableCompat.setHotspotBounds(getBackground(), paramCanvas.left + j, paramCanvas.top, paramCanvas.right + j, paramCanvas.bottom);
        }
        return;
      }
    }
    super.onDraw(paramCanvas);
  }
  
  public void setCenterIfNoTextEnabled(boolean paramBoolean)
  {
    this.centerIfNoTextEnabled = paramBoolean;
  }
  
  public void setUseMaterialThemeColors(boolean paramBoolean)
  {
    this.useMaterialThemeColors = paramBoolean;
    if (paramBoolean) {
      CompoundButtonCompat.setButtonTintList(this, getMaterialThemeColorsTintList());
    } else {
      CompoundButtonCompat.setButtonTintList(this, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/checkbox/MaterialCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */