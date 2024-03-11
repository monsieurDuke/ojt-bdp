package com.google.android.material.button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MaterialButtonToggleGroup
  extends LinearLayout
{
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialButtonToggleGroup;
  private static final String LOG_TAG = MaterialButtonToggleGroup.class.getSimpleName();
  private Set<Integer> checkedIds = new HashSet();
  private Integer[] childOrder;
  private final Comparator<MaterialButton> childOrderComparator = new Comparator()
  {
    public int compare(MaterialButton paramAnonymousMaterialButton1, MaterialButton paramAnonymousMaterialButton2)
    {
      int i = Boolean.valueOf(paramAnonymousMaterialButton1.isChecked()).compareTo(Boolean.valueOf(paramAnonymousMaterialButton2.isChecked()));
      if (i != 0) {
        return i;
      }
      i = Boolean.valueOf(paramAnonymousMaterialButton1.isPressed()).compareTo(Boolean.valueOf(paramAnonymousMaterialButton2.isPressed()));
      if (i != 0) {
        return i;
      }
      return Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(paramAnonymousMaterialButton1)).compareTo(Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(paramAnonymousMaterialButton2)));
    }
  };
  private final int defaultCheckId;
  private final LinkedHashSet<OnButtonCheckedListener> onButtonCheckedListeners = new LinkedHashSet();
  private final List<CornerData> originalCornerData = new ArrayList();
  private final PressedStateTracker pressedStateTracker = new PressedStateTracker(null);
  private boolean selectionRequired;
  private boolean singleSelection;
  private boolean skipCheckedStateTracker = false;
  
  public MaterialButtonToggleGroup(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialButtonToggleGroup(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.materialButtonToggleGroupStyle);
  }
  
  public MaterialButtonToggleGroup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = ThemeEnforcement.obtainStyledAttributes(getContext(), paramAttributeSet, R.styleable.MaterialButtonToggleGroup, paramInt, i, new int[0]);
    setSingleSelection(paramContext.getBoolean(R.styleable.MaterialButtonToggleGroup_singleSelection, false));
    this.defaultCheckId = paramContext.getResourceId(R.styleable.MaterialButtonToggleGroup_checkedButton, -1);
    this.selectionRequired = paramContext.getBoolean(R.styleable.MaterialButtonToggleGroup_selectionRequired, false);
    setChildrenDrawingOrderEnabled(true);
    paramContext.recycle();
    ViewCompat.setImportantForAccessibility(this, 1);
  }
  
  private void adjustChildMarginsAndUpdateLayout()
  {
    int j = getFirstVisibleChildIndex();
    if (j == -1) {
      return;
    }
    for (int i = j + 1; i < getChildCount(); i++)
    {
      MaterialButton localMaterialButton = getChildButton(i);
      Object localObject = getChildButton(i - 1);
      int k = Math.min(localMaterialButton.getStrokeWidth(), ((MaterialButton)localObject).getStrokeWidth());
      localObject = buildLayoutParams(localMaterialButton);
      if (getOrientation() == 0)
      {
        MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams)localObject, 0);
        MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)localObject, -k);
        ((LinearLayout.LayoutParams)localObject).topMargin = 0;
      }
      else
      {
        ((LinearLayout.LayoutParams)localObject).bottomMargin = 0;
        ((LinearLayout.LayoutParams)localObject).topMargin = (-k);
        MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)localObject, 0);
      }
      localMaterialButton.setLayoutParams((ViewGroup.LayoutParams)localObject);
    }
    resetChildMargins(j);
  }
  
  private LinearLayout.LayoutParams buildLayoutParams(View paramView)
  {
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof LinearLayout.LayoutParams)) {
      return (LinearLayout.LayoutParams)paramView;
    }
    return new LinearLayout.LayoutParams(paramView.width, paramView.height);
  }
  
  private void checkInternal(int paramInt, boolean paramBoolean)
  {
    if (paramInt == -1)
    {
      Log.e(LOG_TAG, "Button ID is not valid: " + paramInt);
      return;
    }
    HashSet localHashSet = new HashSet(this.checkedIds);
    if ((paramBoolean) && (!localHashSet.contains(Integer.valueOf(paramInt))))
    {
      if ((this.singleSelection) && (!localHashSet.isEmpty())) {
        localHashSet.clear();
      }
      localHashSet.add(Integer.valueOf(paramInt));
    }
    else
    {
      if ((paramBoolean) || (!localHashSet.contains(Integer.valueOf(paramInt)))) {
        return;
      }
      if ((!this.selectionRequired) || (localHashSet.size() > 1)) {
        localHashSet.remove(Integer.valueOf(paramInt));
      }
    }
    updateCheckedIds(localHashSet);
    return;
  }
  
  private void dispatchOnButtonChecked(int paramInt, boolean paramBoolean)
  {
    Iterator localIterator = this.onButtonCheckedListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnButtonCheckedListener)localIterator.next()).onButtonChecked(this, paramInt, paramBoolean);
    }
  }
  
  private MaterialButton getChildButton(int paramInt)
  {
    return (MaterialButton)getChildAt(paramInt);
  }
  
  private int getFirstVisibleChildIndex()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      if (isChildVisible(i)) {
        return i;
      }
    }
    return -1;
  }
  
  private int getIndexWithinVisibleButtons(View paramView)
  {
    if (!(paramView instanceof MaterialButton)) {
      return -1;
    }
    int j = 0;
    int i = 0;
    while (i < getChildCount())
    {
      if (getChildAt(i) == paramView) {
        return j;
      }
      int k = j;
      if ((getChildAt(i) instanceof MaterialButton))
      {
        k = j;
        if (isChildVisible(i)) {
          k = j + 1;
        }
      }
      i++;
      j = k;
    }
    return -1;
  }
  
  private int getLastVisibleChildIndex()
  {
    for (int i = getChildCount() - 1; i >= 0; i--) {
      if (isChildVisible(i)) {
        return i;
      }
    }
    return -1;
  }
  
  private CornerData getNewCornerData(int paramInt1, int paramInt2, int paramInt3)
  {
    CornerData localCornerData = (CornerData)this.originalCornerData.get(paramInt1);
    if (paramInt2 == paramInt3) {
      return localCornerData;
    }
    int i;
    if (getOrientation() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (paramInt1 == paramInt2)
    {
      if (i != 0) {
        localCornerData = CornerData.start(localCornerData, this);
      } else {
        localCornerData = CornerData.top(localCornerData);
      }
      return localCornerData;
    }
    if (paramInt1 == paramInt3)
    {
      if (i != 0) {
        localCornerData = CornerData.end(localCornerData, this);
      } else {
        localCornerData = CornerData.bottom(localCornerData);
      }
      return localCornerData;
    }
    return null;
  }
  
  private int getVisibleButtonCount()
  {
    int k = 0;
    int i = 0;
    while (i < getChildCount())
    {
      int j = k;
      if ((getChildAt(i) instanceof MaterialButton))
      {
        j = k;
        if (isChildVisible(i)) {
          j = k + 1;
        }
      }
      i++;
      k = j;
    }
    return k;
  }
  
  private boolean isChildVisible(int paramInt)
  {
    boolean bool;
    if (getChildAt(paramInt).getVisibility() != 8) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void resetChildMargins(int paramInt)
  {
    if ((getChildCount() != 0) && (paramInt != -1))
    {
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)getChildButton(paramInt).getLayoutParams();
      if (getOrientation() == 1)
      {
        localLayoutParams.topMargin = 0;
        localLayoutParams.bottomMargin = 0;
        return;
      }
      MarginLayoutParamsCompat.setMarginEnd(localLayoutParams, 0);
      MarginLayoutParamsCompat.setMarginStart(localLayoutParams, 0);
      localLayoutParams.leftMargin = 0;
      localLayoutParams.rightMargin = 0;
      return;
    }
  }
  
  private void setCheckedStateForView(int paramInt, boolean paramBoolean)
  {
    View localView = findViewById(paramInt);
    if ((localView instanceof MaterialButton))
    {
      this.skipCheckedStateTracker = true;
      ((MaterialButton)localView).setChecked(paramBoolean);
      this.skipCheckedStateTracker = false;
    }
  }
  
  private void setGeneratedIdIfNeeded(MaterialButton paramMaterialButton)
  {
    if (paramMaterialButton.getId() == -1) {
      paramMaterialButton.setId(ViewCompat.generateViewId());
    }
  }
  
  private void setupButtonChild(MaterialButton paramMaterialButton)
  {
    paramMaterialButton.setMaxLines(1);
    paramMaterialButton.setEllipsize(TextUtils.TruncateAt.END);
    paramMaterialButton.setCheckable(true);
    paramMaterialButton.setOnPressedChangeListenerInternal(this.pressedStateTracker);
    paramMaterialButton.setShouldDrawSurfaceColorStroke(true);
  }
  
  private static void updateBuilderWithCornerData(ShapeAppearanceModel.Builder paramBuilder, CornerData paramCornerData)
  {
    if (paramCornerData == null)
    {
      paramBuilder.setAllCornerSizes(0.0F);
      return;
    }
    paramBuilder.setTopLeftCornerSize(paramCornerData.topLeft).setBottomLeftCornerSize(paramCornerData.bottomLeft).setTopRightCornerSize(paramCornerData.topRight).setBottomRightCornerSize(paramCornerData.bottomRight);
  }
  
  private void updateCheckedIds(Set<Integer> paramSet)
  {
    Set localSet = this.checkedIds;
    this.checkedIds = new HashSet(paramSet);
    for (int i = 0; i < getChildCount(); i++)
    {
      int j = getChildButton(i).getId();
      setCheckedStateForView(j, paramSet.contains(Integer.valueOf(j)));
      if (localSet.contains(Integer.valueOf(j)) != paramSet.contains(Integer.valueOf(j))) {
        dispatchOnButtonChecked(j, paramSet.contains(Integer.valueOf(j)));
      }
    }
    invalidate();
  }
  
  private void updateChildOrder()
  {
    TreeMap localTreeMap = new TreeMap(this.childOrderComparator);
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      localTreeMap.put(getChildButton(i), Integer.valueOf(i));
    }
    this.childOrder = ((Integer[])localTreeMap.values().toArray(new Integer[0]));
  }
  
  public void addOnButtonCheckedListener(OnButtonCheckedListener paramOnButtonCheckedListener)
  {
    this.onButtonCheckedListeners.add(paramOnButtonCheckedListener);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (!(paramView instanceof MaterialButton))
    {
      Log.e(LOG_TAG, "Child views must be of type MaterialButton.");
      return;
    }
    super.addView(paramView, paramInt, paramLayoutParams);
    paramLayoutParams = (MaterialButton)paramView;
    setGeneratedIdIfNeeded(paramLayoutParams);
    setupButtonChild(paramLayoutParams);
    checkInternal(paramLayoutParams.getId(), paramLayoutParams.isChecked());
    paramView = paramLayoutParams.getShapeAppearanceModel();
    this.originalCornerData.add(new CornerData(paramView.getTopLeftCornerSize(), paramView.getBottomLeftCornerSize(), paramView.getTopRightCornerSize(), paramView.getBottomRightCornerSize()));
    ViewCompat.setAccessibilityDelegate(paramLayoutParams, new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, MaterialButtonToggleGroup.this.getIndexWithinVisibleButtons(paramAnonymousView), 1, false, ((MaterialButton)paramAnonymousView).isChecked()));
      }
    });
  }
  
  public void check(int paramInt)
  {
    checkInternal(paramInt, true);
  }
  
  public void clearChecked()
  {
    updateCheckedIds(new HashSet());
  }
  
  public void clearOnButtonCheckedListeners()
  {
    this.onButtonCheckedListeners.clear();
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    updateChildOrder();
    super.dispatchDraw(paramCanvas);
  }
  
  public int getCheckedButtonId()
  {
    int i;
    if ((this.singleSelection) && (!this.checkedIds.isEmpty())) {
      i = ((Integer)this.checkedIds.iterator().next()).intValue();
    } else {
      i = -1;
    }
    return i;
  }
  
  public List<Integer> getCheckedButtonIds()
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++)
    {
      int j = getChildButton(i).getId();
      if (this.checkedIds.contains(Integer.valueOf(j))) {
        localArrayList.add(Integer.valueOf(j));
      }
    }
    return localArrayList;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    Integer[] arrayOfInteger = this.childOrder;
    if ((arrayOfInteger != null) && (paramInt2 < arrayOfInteger.length)) {
      return arrayOfInteger[paramInt2].intValue();
    }
    Log.w(LOG_TAG, "Child order wasn't updated");
    return paramInt2;
  }
  
  public boolean isSelectionRequired()
  {
    return this.selectionRequired;
  }
  
  public boolean isSingleSelection()
  {
    return this.singleSelection;
  }
  
  void onButtonCheckedStateChanged(MaterialButton paramMaterialButton, boolean paramBoolean)
  {
    if (this.skipCheckedStateTracker) {
      return;
    }
    checkInternal(paramMaterialButton.getId(), paramBoolean);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    int i = this.defaultCheckId;
    if (i != -1) {
      updateCheckedIds(Collections.singleton(Integer.valueOf(i)));
    }
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo = AccessibilityNodeInfoCompat.wrap(paramAccessibilityNodeInfo);
    int j = getVisibleButtonCount();
    int i;
    if (isSingleSelection()) {
      i = 1;
    } else {
      i = 2;
    }
    paramAccessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, j, false, i));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    updateChildShapes();
    adjustChildMarginsAndUpdateLayout();
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void onViewRemoved(View paramView)
  {
    super.onViewRemoved(paramView);
    if ((paramView instanceof MaterialButton)) {
      ((MaterialButton)paramView).setOnPressedChangeListenerInternal(null);
    }
    int i = indexOfChild(paramView);
    if (i >= 0) {
      this.originalCornerData.remove(i);
    }
    updateChildShapes();
    adjustChildMarginsAndUpdateLayout();
  }
  
  public void removeOnButtonCheckedListener(OnButtonCheckedListener paramOnButtonCheckedListener)
  {
    this.onButtonCheckedListeners.remove(paramOnButtonCheckedListener);
  }
  
  public void setSelectionRequired(boolean paramBoolean)
  {
    this.selectionRequired = paramBoolean;
  }
  
  public void setSingleSelection(int paramInt)
  {
    setSingleSelection(getResources().getBoolean(paramInt));
  }
  
  public void setSingleSelection(boolean paramBoolean)
  {
    if (this.singleSelection != paramBoolean)
    {
      this.singleSelection = paramBoolean;
      clearChecked();
    }
  }
  
  public void uncheck(int paramInt)
  {
    checkInternal(paramInt, false);
  }
  
  void updateChildShapes()
  {
    int m = getChildCount();
    int k = getFirstVisibleChildIndex();
    int j = getLastVisibleChildIndex();
    for (int i = 0; i < m; i++)
    {
      MaterialButton localMaterialButton = getChildButton(i);
      if (localMaterialButton.getVisibility() != 8)
      {
        ShapeAppearanceModel.Builder localBuilder = localMaterialButton.getShapeAppearanceModel().toBuilder();
        updateBuilderWithCornerData(localBuilder, getNewCornerData(i, k, j));
        localMaterialButton.setShapeAppearanceModel(localBuilder.build());
      }
    }
  }
  
  private static class CornerData
  {
    private static final CornerSize noCorner = new AbsoluteCornerSize(0.0F);
    CornerSize bottomLeft;
    CornerSize bottomRight;
    CornerSize topLeft;
    CornerSize topRight;
    
    CornerData(CornerSize paramCornerSize1, CornerSize paramCornerSize2, CornerSize paramCornerSize3, CornerSize paramCornerSize4)
    {
      this.topLeft = paramCornerSize1;
      this.topRight = paramCornerSize3;
      this.bottomRight = paramCornerSize4;
      this.bottomLeft = paramCornerSize2;
    }
    
    public static CornerData bottom(CornerData paramCornerData)
    {
      CornerSize localCornerSize = noCorner;
      return new CornerData(localCornerSize, paramCornerData.bottomLeft, localCornerSize, paramCornerData.bottomRight);
    }
    
    public static CornerData end(CornerData paramCornerData, View paramView)
    {
      if (ViewUtils.isLayoutRtl(paramView)) {
        paramCornerData = left(paramCornerData);
      } else {
        paramCornerData = right(paramCornerData);
      }
      return paramCornerData;
    }
    
    public static CornerData left(CornerData paramCornerData)
    {
      CornerSize localCornerSize1 = paramCornerData.topLeft;
      paramCornerData = paramCornerData.bottomLeft;
      CornerSize localCornerSize2 = noCorner;
      return new CornerData(localCornerSize1, paramCornerData, localCornerSize2, localCornerSize2);
    }
    
    public static CornerData right(CornerData paramCornerData)
    {
      CornerSize localCornerSize = noCorner;
      return new CornerData(localCornerSize, localCornerSize, paramCornerData.topRight, paramCornerData.bottomRight);
    }
    
    public static CornerData start(CornerData paramCornerData, View paramView)
    {
      if (ViewUtils.isLayoutRtl(paramView)) {
        paramCornerData = right(paramCornerData);
      } else {
        paramCornerData = left(paramCornerData);
      }
      return paramCornerData;
    }
    
    public static CornerData top(CornerData paramCornerData)
    {
      CornerSize localCornerSize1 = paramCornerData.topLeft;
      CornerSize localCornerSize2 = noCorner;
      return new CornerData(localCornerSize1, localCornerSize2, paramCornerData.topRight, localCornerSize2);
    }
  }
  
  public static abstract interface OnButtonCheckedListener
  {
    public abstract void onButtonChecked(MaterialButtonToggleGroup paramMaterialButtonToggleGroup, int paramInt, boolean paramBoolean);
  }
  
  private class PressedStateTracker
    implements MaterialButton.OnPressedChangeListener
  {
    private PressedStateTracker() {}
    
    public void onPressedChanged(MaterialButton paramMaterialButton, boolean paramBoolean)
    {
      MaterialButtonToggleGroup.this.invalidate();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/button/MaterialButtonToggleGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */