package com.google.android.material.shape;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.internal.ViewUtils;

public class MaterialShapeUtils
{
  static CornerTreatment createCornerTreatment(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return createDefaultCornerTreatment();
    case 1: 
      return new CutCornerTreatment();
    }
    return new RoundedCornerTreatment();
  }
  
  static CornerTreatment createDefaultCornerTreatment()
  {
    return new RoundedCornerTreatment();
  }
  
  static EdgeTreatment createDefaultEdgeTreatment()
  {
    return new EdgeTreatment();
  }
  
  public static void setElevation(View paramView, float paramFloat)
  {
    paramView = paramView.getBackground();
    if ((paramView instanceof MaterialShapeDrawable)) {
      ((MaterialShapeDrawable)paramView).setElevation(paramFloat);
    }
  }
  
  public static void setParentAbsoluteElevation(View paramView)
  {
    Drawable localDrawable = paramView.getBackground();
    if ((localDrawable instanceof MaterialShapeDrawable)) {
      setParentAbsoluteElevation(paramView, (MaterialShapeDrawable)localDrawable);
    }
  }
  
  public static void setParentAbsoluteElevation(View paramView, MaterialShapeDrawable paramMaterialShapeDrawable)
  {
    if (paramMaterialShapeDrawable.isElevationOverlayEnabled()) {
      paramMaterialShapeDrawable.setParentAbsoluteElevation(ViewUtils.getParentAbsoluteElevation(paramView));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/shape/MaterialShapeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */