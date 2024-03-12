package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.State;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public class MaterialDividerItemDecoration
  extends RecyclerView.ItemDecoration
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialDivider;
  public static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;
  private int color;
  private Drawable dividerDrawable;
  private int insetEnd;
  private int insetStart;
  private boolean lastItemDecorated;
  private int orientation;
  private final Rect tempRect = new Rect();
  private int thickness;
  
  public MaterialDividerItemDecoration(Context paramContext, int paramInt)
  {
    this(paramContext, null, paramInt);
  }
  
  public MaterialDividerItemDecoration(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, R.attr.materialDividerStyle, paramInt);
  }
  
  public MaterialDividerItemDecoration(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.MaterialDivider, paramInt1, DEF_STYLE_RES, new int[0]);
    this.color = MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.MaterialDivider_dividerColor).getDefaultColor();
    this.thickness = paramAttributeSet.getDimensionPixelSize(R.styleable.MaterialDivider_dividerThickness, paramContext.getResources().getDimensionPixelSize(R.dimen.material_divider_thickness));
    this.insetStart = paramAttributeSet.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetStart, 0);
    this.insetEnd = paramAttributeSet.getDimensionPixelOffset(R.styleable.MaterialDivider_dividerInsetEnd, 0);
    this.lastItemDecorated = paramAttributeSet.getBoolean(R.styleable.MaterialDivider_lastItemDecorated, true);
    paramAttributeSet.recycle();
    this.dividerDrawable = new ShapeDrawable();
    setDividerColor(this.color);
    setOrientation(paramInt2);
  }
  
  private void drawForHorizontalOrientation(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    paramCanvas.save();
    int i;
    int j;
    if (paramRecyclerView.getClipToPadding())
    {
      i = paramRecyclerView.getPaddingTop();
      j = paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom();
      paramCanvas.clipRect(paramRecyclerView.getPaddingLeft(), i, paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight(), j);
    }
    else
    {
      i = 0;
      j = paramRecyclerView.getHeight();
    }
    int n = this.insetStart;
    int i1 = this.insetEnd;
    int m = paramRecyclerView.getChildCount();
    for (int k = 0; k < m; k++)
    {
      View localView = paramRecyclerView.getChildAt(k);
      paramRecyclerView.getLayoutManager().getDecoratedBoundsWithMargins(localView, this.tempRect);
      int i2 = this.tempRect.right + Math.round(localView.getTranslationX());
      int i3 = this.dividerDrawable.getIntrinsicWidth();
      int i4 = this.thickness;
      this.dividerDrawable.setBounds(i2 - i3 - i4, i + n, i2, j - i1);
      this.dividerDrawable.draw(paramCanvas);
    }
    paramCanvas.restore();
  }
  
  private void drawForVerticalOrientation(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    paramCanvas.save();
    int i;
    int j;
    if (paramRecyclerView.getClipToPadding())
    {
      i = paramRecyclerView.getPaddingLeft();
      j = paramRecyclerView.getWidth() - paramRecyclerView.getPaddingRight();
      paramCanvas.clipRect(i, paramRecyclerView.getPaddingTop(), j, paramRecyclerView.getHeight() - paramRecyclerView.getPaddingBottom());
    }
    else
    {
      i = 0;
      j = paramRecyclerView.getWidth();
    }
    int k = ViewCompat.getLayoutDirection(paramRecyclerView);
    int m = 1;
    if (k != 1) {
      m = 0;
    }
    if (m != 0) {
      k = this.insetEnd;
    } else {
      k = this.insetStart;
    }
    if (m != 0) {
      m = this.insetStart;
    } else {
      m = this.insetEnd;
    }
    int n = paramRecyclerView.getChildCount();
    if (!this.lastItemDecorated) {
      n--;
    }
    for (int i1 = 0; i1 < n; i1++)
    {
      View localView = paramRecyclerView.getChildAt(i1);
      paramRecyclerView.getDecoratedBoundsWithMargins(localView, this.tempRect);
      int i3 = this.tempRect.bottom + Math.round(localView.getTranslationY());
      int i4 = this.dividerDrawable.getIntrinsicHeight();
      int i2 = this.thickness;
      this.dividerDrawable.setBounds(i + k, i3 - i4 - i2, j - m, i3);
      this.dividerDrawable.draw(paramCanvas);
    }
    paramCanvas.restore();
  }
  
  public int getDividerColor()
  {
    return this.color;
  }
  
  public int getDividerInsetEnd()
  {
    return this.insetEnd;
  }
  
  public int getDividerInsetStart()
  {
    return this.insetStart;
  }
  
  public int getDividerThickness()
  {
    return this.thickness;
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    paramRect.set(0, 0, 0, 0);
    if (this.orientation == 1) {
      paramRect.bottom = (this.dividerDrawable.getIntrinsicHeight() + this.thickness);
    } else {
      paramRect.right = (this.dividerDrawable.getIntrinsicWidth() + this.thickness);
    }
  }
  
  public int getOrientation()
  {
    return this.orientation;
  }
  
  public boolean isLastItemDecorated()
  {
    return this.lastItemDecorated;
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState)
  {
    if (paramRecyclerView.getLayoutManager() == null) {
      return;
    }
    if (this.orientation == 1) {
      drawForVerticalOrientation(paramCanvas, paramRecyclerView);
    } else {
      drawForHorizontalOrientation(paramCanvas, paramRecyclerView);
    }
  }
  
  public void setDividerColor(int paramInt)
  {
    this.color = paramInt;
    Drawable localDrawable = DrawableCompat.wrap(this.dividerDrawable);
    this.dividerDrawable = localDrawable;
    DrawableCompat.setTint(localDrawable, paramInt);
  }
  
  public void setDividerColorResource(Context paramContext, int paramInt)
  {
    setDividerColor(ContextCompat.getColor(paramContext, paramInt));
  }
  
  public void setDividerInsetEnd(int paramInt)
  {
    this.insetEnd = paramInt;
  }
  
  public void setDividerInsetEndResource(Context paramContext, int paramInt)
  {
    setDividerInsetEnd(paramContext.getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setDividerInsetStart(int paramInt)
  {
    this.insetStart = paramInt;
  }
  
  public void setDividerInsetStartResource(Context paramContext, int paramInt)
  {
    setDividerInsetStart(paramContext.getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setDividerThickness(int paramInt)
  {
    this.thickness = paramInt;
  }
  
  public void setDividerThicknessResource(Context paramContext, int paramInt)
  {
    setDividerThickness(paramContext.getResources().getDimensionPixelSize(paramInt));
  }
  
  public void setLastItemDecorated(boolean paramBoolean)
  {
    this.lastItemDecorated = paramBoolean;
  }
  
  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("Invalid orientation: " + paramInt + ". It should be either HORIZONTAL or VERTICAL");
    }
    this.orientation = paramInt;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/divider/MaterialDividerItemDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */