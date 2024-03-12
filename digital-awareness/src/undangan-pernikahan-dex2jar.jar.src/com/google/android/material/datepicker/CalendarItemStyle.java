package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.styleable;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;

final class CalendarItemStyle
{
  private final ColorStateList backgroundColor;
  private final Rect insets;
  private final ShapeAppearanceModel itemShape;
  private final ColorStateList strokeColor;
  private final int strokeWidth;
  private final ColorStateList textColor;
  
  private CalendarItemStyle(ColorStateList paramColorStateList1, ColorStateList paramColorStateList2, ColorStateList paramColorStateList3, int paramInt, ShapeAppearanceModel paramShapeAppearanceModel, Rect paramRect)
  {
    Preconditions.checkArgumentNonnegative(paramRect.left);
    Preconditions.checkArgumentNonnegative(paramRect.top);
    Preconditions.checkArgumentNonnegative(paramRect.right);
    Preconditions.checkArgumentNonnegative(paramRect.bottom);
    this.insets = paramRect;
    this.textColor = paramColorStateList2;
    this.backgroundColor = paramColorStateList1;
    this.strokeColor = paramColorStateList3;
    this.strokeWidth = paramInt;
    this.itemShape = paramShapeAppearanceModel;
  }
  
  static CalendarItemStyle create(Context paramContext, int paramInt)
  {
    boolean bool;
    if (paramInt != 0) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool, "Cannot create a CalendarItemStyle with a styleResId of 0");
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.MaterialCalendarItem);
    Rect localRect = new Rect(localTypedArray.getDimensionPixelOffset(R.styleable.MaterialCalendarItem_android_insetLeft, 0), localTypedArray.getDimensionPixelOffset(R.styleable.MaterialCalendarItem_android_insetTop, 0), localTypedArray.getDimensionPixelOffset(R.styleable.MaterialCalendarItem_android_insetRight, 0), localTypedArray.getDimensionPixelOffset(R.styleable.MaterialCalendarItem_android_insetBottom, 0));
    ColorStateList localColorStateList2 = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.MaterialCalendarItem_itemFillColor);
    ColorStateList localColorStateList1 = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.MaterialCalendarItem_itemTextColor);
    ColorStateList localColorStateList3 = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.MaterialCalendarItem_itemStrokeColor);
    int i = localTypedArray.getDimensionPixelSize(R.styleable.MaterialCalendarItem_itemStrokeWidth, 0);
    int j = localTypedArray.getResourceId(R.styleable.MaterialCalendarItem_itemShapeAppearance, 0);
    paramInt = localTypedArray.getResourceId(R.styleable.MaterialCalendarItem_itemShapeAppearanceOverlay, 0);
    paramContext = ShapeAppearanceModel.builder(paramContext, j, paramInt).build();
    localTypedArray.recycle();
    return new CalendarItemStyle(localColorStateList2, localColorStateList1, localColorStateList3, i, paramContext, localRect);
  }
  
  int getBottomInset()
  {
    return this.insets.bottom;
  }
  
  int getLeftInset()
  {
    return this.insets.left;
  }
  
  int getRightInset()
  {
    return this.insets.right;
  }
  
  int getTopInset()
  {
    return this.insets.top;
  }
  
  void styleItem(TextView paramTextView)
  {
    Object localObject = new MaterialShapeDrawable();
    MaterialShapeDrawable localMaterialShapeDrawable = new MaterialShapeDrawable();
    ((MaterialShapeDrawable)localObject).setShapeAppearanceModel(this.itemShape);
    localMaterialShapeDrawable.setShapeAppearanceModel(this.itemShape);
    ((MaterialShapeDrawable)localObject).setFillColor(this.backgroundColor);
    ((MaterialShapeDrawable)localObject).setStroke(this.strokeWidth, this.strokeColor);
    paramTextView.setTextColor(this.textColor);
    if (Build.VERSION.SDK_INT >= 21) {
      localObject = new RippleDrawable(this.textColor.withAlpha(30), (Drawable)localObject, localMaterialShapeDrawable);
    }
    ViewCompat.setBackground(paramTextView, new InsetDrawable((Drawable)localObject, this.insets.left, this.insets.top, this.insets.right, this.insets.bottom));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/CalendarItemStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */