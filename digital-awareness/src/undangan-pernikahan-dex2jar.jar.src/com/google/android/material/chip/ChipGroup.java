package com.google.android.material.chip;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.CheckableGroup.OnCheckedStateChangeListener;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;
import java.util.Set;

public class ChipGroup
  extends FlowLayout
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ChipGroup;
  private final CheckableGroup<Chip> checkableGroup;
  private int chipSpacingHorizontal;
  private int chipSpacingVertical;
  private final int defaultCheckedId;
  private OnCheckedStateChangeListener onCheckedStateChangeListener;
  private final PassThroughHierarchyChangeListener passThroughListener;
  
  public ChipGroup(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ChipGroup(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.chipGroupStyle);
  }
  
  public ChipGroup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = new CheckableGroup();
    this.checkableGroup = paramContext;
    PassThroughHierarchyChangeListener localPassThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener(null);
    this.passThroughListener = localPassThroughHierarchyChangeListener;
    paramAttributeSet = ThemeEnforcement.obtainStyledAttributes(getContext(), paramAttributeSet, R.styleable.ChipGroup, paramInt, i, new int[0]);
    paramInt = paramAttributeSet.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
    setChipSpacingHorizontal(paramAttributeSet.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, paramInt));
    setChipSpacingVertical(paramAttributeSet.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, paramInt));
    setSingleLine(paramAttributeSet.getBoolean(R.styleable.ChipGroup_singleLine, false));
    setSingleSelection(paramAttributeSet.getBoolean(R.styleable.ChipGroup_singleSelection, false));
    setSelectionRequired(paramAttributeSet.getBoolean(R.styleable.ChipGroup_selectionRequired, false));
    this.defaultCheckedId = paramAttributeSet.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
    paramAttributeSet.recycle();
    paramContext.setOnCheckedStateChangeListener(new CheckableGroup.OnCheckedStateChangeListener()
    {
      public void onCheckedStateChanged(Set<Integer> paramAnonymousSet)
      {
        if (ChipGroup.this.onCheckedStateChangeListener != null)
        {
          paramAnonymousSet = ChipGroup.this.onCheckedStateChangeListener;
          ChipGroup localChipGroup = ChipGroup.this;
          paramAnonymousSet.onCheckedChanged(localChipGroup, localChipGroup.checkableGroup.getCheckedIdsSortedByChildOrder(ChipGroup.this));
        }
      }
    });
    super.setOnHierarchyChangeListener(localPassThroughHierarchyChangeListener);
    ViewCompat.setImportantForAccessibility(this, 1);
  }
  
  private int getChipCount()
  {
    int j = 0;
    int i = 0;
    while (i < getChildCount())
    {
      int k = j;
      if ((getChildAt(i) instanceof Chip)) {
        k = j + 1;
      }
      i++;
      j = k;
    }
    return j;
  }
  
  public void check(int paramInt)
  {
    this.checkableGroup.check(paramInt);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if ((super.checkLayoutParams(paramLayoutParams)) && ((paramLayoutParams instanceof LayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void clearCheck()
  {
    this.checkableGroup.clearCheck();
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getCheckedChipId()
  {
    return this.checkableGroup.getSingleCheckedId();
  }
  
  public List<Integer> getCheckedChipIds()
  {
    return this.checkableGroup.getCheckedIdsSortedByChildOrder(this);
  }
  
  public int getChipSpacingHorizontal()
  {
    return this.chipSpacingHorizontal;
  }
  
  public int getChipSpacingVertical()
  {
    return this.chipSpacingVertical;
  }
  
  int getIndexOfChip(View paramView)
  {
    if (!(paramView instanceof Chip)) {
      return -1;
    }
    int j = 0;
    int i = 0;
    while (i < getChildCount())
    {
      int k = j;
      if ((getChildAt(i) instanceof Chip))
      {
        if ((Chip)getChildAt(i) == paramView) {
          return j;
        }
        k = j + 1;
      }
      i++;
      j = k;
    }
    return -1;
  }
  
  public boolean isSelectionRequired()
  {
    return this.checkableGroup.isSelectionRequired();
  }
  
  public boolean isSingleLine()
  {
    return super.isSingleLine();
  }
  
  public boolean isSingleSelection()
  {
    return this.checkableGroup.isSingleSelection();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    int i = this.defaultCheckedId;
    if (i != -1) {
      this.checkableGroup.check(i);
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo = AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo);
    int i;
    if (isSingleLine()) {
      i = getChipCount();
    } else {
      i = -1;
    }
    int k = getRowCount();
    int j;
    if (isSingleSelection()) {
      j = 1;
    } else {
      j = 2;
    }
    paramAccessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(k, i, false, j));
  }
  
  public void setChipSpacing(int paramInt)
  {
    setChipSpacingHorizontal(paramInt);
    setChipSpacingVertical(paramInt);
  }
  
  public void setChipSpacingHorizontal(int paramInt)
  {
    if (this.chipSpacingHorizontal != paramInt)
    {
      this.chipSpacingHorizontal = paramInt;
      setItemSpacing(paramInt);
      requestLayout();
    }
  }
  
  public void setChipSpacingHorizontalResource(int paramInt)
  {
    setChipSpacingHorizontal(getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setChipSpacingResource(int paramInt)
  {
    setChipSpacing(getResources().getDimensionPixelOffset(paramInt));
  }
  
  public void setChipSpacingVertical(int paramInt)
  {
    if (this.chipSpacingVertical != paramInt)
    {
      this.chipSpacingVertical = paramInt;
      setLineSpacing(paramInt);
      requestLayout();
    }
  }
  
  public void setChipSpacingVerticalResource(int paramInt)
  {
    setChipSpacingVertical(getResources().getDimensionPixelOffset(paramInt));
  }
  
  @Deprecated
  public void setDividerDrawableHorizontal(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  @Deprecated
  public void setDividerDrawableVertical(Drawable paramDrawable)
  {
    throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  @Deprecated
  public void setFlexWrap(int paramInt)
  {
    throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
  }
  
  @Deprecated
  public void setOnCheckedChangeListener(final OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    if (paramOnCheckedChangeListener == null)
    {
      setOnCheckedStateChangeListener(null);
      return;
    }
    setOnCheckedStateChangeListener(new OnCheckedStateChangeListener()
    {
      public void onCheckedChanged(ChipGroup paramAnonymousChipGroup, List<Integer> paramAnonymousList)
      {
        if (!ChipGroup.this.checkableGroup.isSingleSelection()) {
          return;
        }
        paramOnCheckedChangeListener.onCheckedChanged(paramAnonymousChipGroup, ChipGroup.this.getCheckedChipId());
      }
    });
  }
  
  public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener paramOnCheckedStateChangeListener)
  {
    this.onCheckedStateChangeListener = paramOnCheckedStateChangeListener;
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    PassThroughHierarchyChangeListener.access$302(this.passThroughListener, paramOnHierarchyChangeListener);
  }
  
  public void setSelectionRequired(boolean paramBoolean)
  {
    this.checkableGroup.setSelectionRequired(paramBoolean);
  }
  
  @Deprecated
  public void setShowDividerHorizontal(int paramInt)
  {
    throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  @Deprecated
  public void setShowDividerVertical(int paramInt)
  {
    throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
  }
  
  public void setSingleLine(int paramInt)
  {
    setSingleLine(getResources().getBoolean(paramInt));
  }
  
  public void setSingleLine(boolean paramBoolean)
  {
    super.setSingleLine(paramBoolean);
  }
  
  public void setSingleSelection(int paramInt)
  {
    setSingleSelection(getResources().getBoolean(paramInt));
  }
  
  public void setSingleSelection(boolean paramBoolean)
  {
    this.checkableGroup.setSingleSelection(paramBoolean);
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  @Deprecated
  public static abstract interface OnCheckedChangeListener
  {
    public abstract void onCheckedChanged(ChipGroup paramChipGroup, int paramInt);
  }
  
  public static abstract interface OnCheckedStateChangeListener
  {
    public abstract void onCheckedChanged(ChipGroup paramChipGroup, List<Integer> paramList);
  }
  
  private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener
  {
    private ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
    
    private PassThroughHierarchyChangeListener() {}
    
    public void onChildViewAdded(View paramView1, View paramView2)
    {
      if ((paramView1 == ChipGroup.this) && ((paramView2 instanceof Chip)))
      {
        if (paramView2.getId() == -1) {
          paramView2.setId(ViewCompat.generateViewId());
        }
        ChipGroup.this.checkableGroup.addCheckable((Chip)paramView2);
      }
      ViewGroup.OnHierarchyChangeListener localOnHierarchyChangeListener = this.onHierarchyChangeListener;
      if (localOnHierarchyChangeListener != null) {
        localOnHierarchyChangeListener.onChildViewAdded(paramView1, paramView2);
      }
    }
    
    public void onChildViewRemoved(View paramView1, View paramView2)
    {
      Object localObject = ChipGroup.this;
      if ((paramView1 == localObject) && ((paramView2 instanceof Chip))) {
        ((ChipGroup)localObject).checkableGroup.removeCheckable((Chip)paramView2);
      }
      localObject = this.onHierarchyChangeListener;
      if (localObject != null) {
        ((ViewGroup.OnHierarchyChangeListener)localObject).onChildViewRemoved(paramView1, paramView2);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/chip/ChipGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */