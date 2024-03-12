package com.google.android.material.radiobutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.CompoundButtonCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialRadioButton
  extends AppCompatRadioButton
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_RadioButton;
  private static final int[][] ENABLED_CHECKED_STATES;
  private ColorStateList materialThemeColorsTintList;
  private boolean useMaterialThemeColors;
  
  static
  {
    int[] arrayOfInt1 = { 16842910, 16842912 };
    int[] arrayOfInt2 = { -16842910, 16842912 };
    ENABLED_CHECKED_STATES = new int[][] { arrayOfInt1, { 16842910, -16842912 }, arrayOfInt2, { -16842910, -16842912 } };
  }
  
  public MaterialRadioButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialRadioButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.radioButtonStyle);
  }
  
  public MaterialRadioButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialRadioButton, paramInt, i, new int[0]);
    if (paramAttributeSet.hasValue(R.styleable.MaterialRadioButton_buttonTint)) {
      CompoundButtonCompat.setButtonTintList(this, MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.MaterialRadioButton_buttonTint));
    }
    this.useMaterialThemeColors = paramAttributeSet.getBoolean(R.styleable.MaterialRadioButton_useMaterialThemeColors, false);
    paramAttributeSet.recycle();
  }
  
  private ColorStateList getMaterialThemeColorsTintList()
  {
    if (this.materialThemeColorsTintList == null)
    {
      int k = MaterialColors.getColor(this, R.attr.colorControlActivated);
      int j = MaterialColors.getColor(this, R.attr.colorOnSurface);
      int i = MaterialColors.getColor(this, R.attr.colorSurface);
      int[][] arrayOfInt1 = ENABLED_CHECKED_STATES;
      int[] arrayOfInt = new int[arrayOfInt1.length];
      arrayOfInt[0] = MaterialColors.layer(i, k, 1.0F);
      arrayOfInt[1] = MaterialColors.layer(i, j, 0.54F);
      arrayOfInt[2] = MaterialColors.layer(i, j, 0.38F);
      arrayOfInt[3] = MaterialColors.layer(i, j, 0.38F);
      this.materialThemeColorsTintList = new ColorStateList(arrayOfInt1, arrayOfInt);
    }
    return this.materialThemeColorsTintList;
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/radiobutton/MaterialRadioButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */