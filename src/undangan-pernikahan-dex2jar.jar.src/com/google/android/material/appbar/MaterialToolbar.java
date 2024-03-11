package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialToolbar
  extends Toolbar
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Toolbar;
  private static final ImageView.ScaleType[] LOGO_SCALE_TYPE_ARRAY = { ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE };
  private Boolean logoAdjustViewBounds;
  private ImageView.ScaleType logoScaleType;
  private Integer navigationIconTint;
  private boolean subtitleCentered;
  private boolean titleCentered;
  
  public MaterialToolbar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialToolbar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.toolbarStyle);
  }
  
  public MaterialToolbar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = getContext();
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialToolbar, paramInt, i, new int[0]);
    if (localTypedArray.hasValue(R.styleable.MaterialToolbar_navigationIconTint)) {
      setNavigationIconTint(localTypedArray.getColor(R.styleable.MaterialToolbar_navigationIconTint, -1));
    }
    this.titleCentered = localTypedArray.getBoolean(R.styleable.MaterialToolbar_titleCentered, false);
    this.subtitleCentered = localTypedArray.getBoolean(R.styleable.MaterialToolbar_subtitleCentered, false);
    paramInt = localTypedArray.getInt(R.styleable.MaterialToolbar_logoScaleType, -1);
    if (paramInt >= 0)
    {
      paramAttributeSet = LOGO_SCALE_TYPE_ARRAY;
      if (paramInt < paramAttributeSet.length) {
        this.logoScaleType = paramAttributeSet[paramInt];
      }
    }
    if (localTypedArray.hasValue(R.styleable.MaterialToolbar_logoAdjustViewBounds)) {
      this.logoAdjustViewBounds = Boolean.valueOf(localTypedArray.getBoolean(R.styleable.MaterialToolbar_logoAdjustViewBounds, false));
    }
    localTypedArray.recycle();
    initBackground(paramContext);
  }
  
  private Pair<Integer, Integer> calculateTitleBoundLimits(TextView paramTextView1, TextView paramTextView2)
  {
    int j = getMeasuredWidth();
    int i2 = j / 2;
    int i = getPaddingLeft();
    int m = j - getPaddingRight();
    int k = 0;
    while (k < getChildCount())
    {
      View localView = getChildAt(k);
      int n = i;
      int i1 = m;
      if (localView.getVisibility() != 8)
      {
        n = i;
        i1 = m;
        if (localView != paramTextView1)
        {
          n = i;
          i1 = m;
          if (localView != paramTextView2)
          {
            j = i;
            if (localView.getRight() < i2)
            {
              j = i;
              if (localView.getRight() > i) {
                j = localView.getRight();
              }
            }
            n = j;
            i1 = m;
            if (localView.getLeft() > i2)
            {
              n = j;
              i1 = m;
              if (localView.getLeft() < m)
              {
                i1 = localView.getLeft();
                n = j;
              }
            }
          }
        }
      }
      k++;
      i = n;
      m = i1;
    }
    return new Pair(Integer.valueOf(i), Integer.valueOf(m));
  }
  
  private void initBackground(Context paramContext)
  {
    Drawable localDrawable = getBackground();
    if ((localDrawable != null) && (!(localDrawable instanceof ColorDrawable))) {
      return;
    }
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
    int i;
    if (localDrawable != null) {
      i = ((ColorDrawable)localDrawable).getColor();
    } else {
      i = 0;
    }
    localMaterialShapeDrawable.setFillColor(ColorStateList.valueOf(i));
    localMaterialShapeDrawable.initializeElevationOverlay(paramContext);
    localMaterialShapeDrawable.setElevation(ViewCompat.getElevation(this));
    ViewCompat.setBackground(this, localMaterialShapeDrawable);
  }
  
  private void layoutTitleCenteredHorizontally(View paramView, Pair<Integer, Integer> paramPair)
  {
    int j = getMeasuredWidth();
    int i = paramView.getMeasuredWidth();
    int k = j / 2 - i / 2;
    int m = k + i;
    int n = Math.max(Math.max(((Integer)paramPair.first).intValue() - k, 0), Math.max(m - ((Integer)paramPair.second).intValue(), 0));
    j = k;
    i = m;
    if (n > 0)
    {
      j = k + n;
      i = m - n;
      paramView.measure(View.MeasureSpec.makeMeasureSpec(i - j, 1073741824), paramView.getMeasuredHeightAndState());
    }
    paramView.layout(j, paramView.getTop(), i, paramView.getBottom());
  }
  
  private void maybeCenterTitleViews()
  {
    if ((!this.titleCentered) && (!this.subtitleCentered)) {
      return;
    }
    TextView localTextView2 = ToolbarUtils.getTitleTextView(this);
    TextView localTextView1 = ToolbarUtils.getSubtitleTextView(this);
    if ((localTextView2 == null) && (localTextView1 == null)) {
      return;
    }
    Pair localPair = calculateTitleBoundLimits(localTextView2, localTextView1);
    if ((this.titleCentered) && (localTextView2 != null)) {
      layoutTitleCenteredHorizontally(localTextView2, localPair);
    }
    if ((this.subtitleCentered) && (localTextView1 != null)) {
      layoutTitleCenteredHorizontally(localTextView1, localPair);
    }
  }
  
  private Drawable maybeTintNavigationIcon(Drawable paramDrawable)
  {
    if ((paramDrawable != null) && (this.navigationIconTint != null))
    {
      paramDrawable = DrawableCompat.wrap(paramDrawable.mutate());
      DrawableCompat.setTint(paramDrawable, this.navigationIconTint.intValue());
      return paramDrawable;
    }
    return paramDrawable;
  }
  
  private void updateLogoImageView()
  {
    ImageView localImageView = ToolbarUtils.getLogoImageView(this);
    if (localImageView != null)
    {
      Object localObject = this.logoAdjustViewBounds;
      if (localObject != null) {
        localImageView.setAdjustViewBounds(((Boolean)localObject).booleanValue());
      }
      localObject = this.logoScaleType;
      if (localObject != null) {
        localImageView.setScaleType((ImageView.ScaleType)localObject);
      }
    }
  }
  
  public ImageView.ScaleType getLogoScaleType()
  {
    return this.logoScaleType;
  }
  
  public Integer getNavigationIconTint()
  {
    return this.navigationIconTint;
  }
  
  public boolean isLogoAdjustViewBounds()
  {
    Boolean localBoolean = this.logoAdjustViewBounds;
    boolean bool;
    if ((localBoolean != null) && (localBoolean.booleanValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isSubtitleCentered()
  {
    return this.subtitleCentered;
  }
  
  public boolean isTitleCentered()
  {
    return this.titleCentered;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    maybeCenterTitleViews();
    updateLogoImageView();
  }
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    MaterialShapeUtils.setElevation(this, paramFloat);
  }
  
  public void setLogoAdjustViewBounds(boolean paramBoolean)
  {
    Boolean localBoolean = this.logoAdjustViewBounds;
    if ((localBoolean == null) || (localBoolean.booleanValue() != paramBoolean))
    {
      this.logoAdjustViewBounds = Boolean.valueOf(paramBoolean);
      requestLayout();
    }
  }
  
  public void setLogoScaleType(ImageView.ScaleType paramScaleType)
  {
    if (this.logoScaleType != paramScaleType)
    {
      this.logoScaleType = paramScaleType;
      requestLayout();
    }
  }
  
  public void setNavigationIcon(Drawable paramDrawable)
  {
    super.setNavigationIcon(maybeTintNavigationIcon(paramDrawable));
  }
  
  public void setNavigationIconTint(int paramInt)
  {
    this.navigationIconTint = Integer.valueOf(paramInt);
    Drawable localDrawable = getNavigationIcon();
    if (localDrawable != null) {
      setNavigationIcon(localDrawable);
    }
  }
  
  public void setSubtitleCentered(boolean paramBoolean)
  {
    if (this.subtitleCentered != paramBoolean)
    {
      this.subtitleCentered = paramBoolean;
      requestLayout();
    }
  }
  
  public void setTitleCentered(boolean paramBoolean)
  {
    if (this.titleCentered != paramBoolean)
    {
      this.titleCentered = paramBoolean;
      requestLayout();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/appbar/MaterialToolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */