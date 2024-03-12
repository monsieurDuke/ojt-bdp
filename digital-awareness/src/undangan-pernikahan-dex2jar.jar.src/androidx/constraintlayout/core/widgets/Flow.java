package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Flow
  extends VirtualLayout
{
  public static final int HORIZONTAL_ALIGN_CENTER = 2;
  public static final int HORIZONTAL_ALIGN_END = 1;
  public static final int HORIZONTAL_ALIGN_START = 0;
  public static final int VERTICAL_ALIGN_BASELINE = 3;
  public static final int VERTICAL_ALIGN_BOTTOM = 1;
  public static final int VERTICAL_ALIGN_CENTER = 2;
  public static final int VERTICAL_ALIGN_TOP = 0;
  public static final int WRAP_ALIGNED = 2;
  public static final int WRAP_CHAIN = 1;
  public static final int WRAP_CHAIN_NEW = 3;
  public static final int WRAP_NONE = 0;
  private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
  private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
  private int[] mAlignedDimensions = null;
  private ArrayList<WidgetsList> mChainList = new ArrayList();
  private ConstraintWidget[] mDisplayedWidgets;
  private int mDisplayedWidgetsCount = 0;
  private float mFirstHorizontalBias = 0.5F;
  private int mFirstHorizontalStyle = -1;
  private float mFirstVerticalBias = 0.5F;
  private int mFirstVerticalStyle = -1;
  private int mHorizontalAlign = 2;
  private float mHorizontalBias = 0.5F;
  private int mHorizontalGap = 0;
  private int mHorizontalStyle = -1;
  private float mLastHorizontalBias = 0.5F;
  private int mLastHorizontalStyle = -1;
  private float mLastVerticalBias = 0.5F;
  private int mLastVerticalStyle = -1;
  private int mMaxElementsWrap = -1;
  private int mOrientation = 0;
  private int mVerticalAlign = 2;
  private float mVerticalBias = 0.5F;
  private int mVerticalGap = 0;
  private int mVerticalStyle = -1;
  private int mWrapMode = 0;
  
  private void createAlignedConstraints(boolean paramBoolean)
  {
    if ((this.mAlignedDimensions != null) && (this.mAlignedBiggestElementsInCols != null) && (this.mAlignedBiggestElementsInRows != null))
    {
      for (int i = 0; i < this.mDisplayedWidgetsCount; i++) {
        this.mDisplayedWidgets[i].resetAnchors();
      }
      Object localObject1 = this.mAlignedDimensions;
      int m = localObject1[0];
      int n = localObject1[1];
      localObject1 = null;
      float f = this.mHorizontalBias;
      i = 0;
      int j;
      ConstraintWidget localConstraintWidget;
      while (i < m)
      {
        j = i;
        if (paramBoolean)
        {
          j = m - i - 1;
          f = 1.0F - this.mHorizontalBias;
        }
        localConstraintWidget = this.mAlignedBiggestElementsInCols[j];
        localObject2 = localObject1;
        if (localConstraintWidget != null) {
          if (localConstraintWidget.getVisibility() == 8)
          {
            localObject2 = localObject1;
          }
          else
          {
            if (i == 0)
            {
              localConstraintWidget.connect(localConstraintWidget.mLeft, this.mLeft, getPaddingLeft());
              localConstraintWidget.setHorizontalChainStyle(this.mHorizontalStyle);
              localConstraintWidget.setHorizontalBiasPercent(f);
            }
            if (i == m - 1) {
              localConstraintWidget.connect(localConstraintWidget.mRight, this.mRight, getPaddingRight());
            }
            if ((i > 0) && (localObject1 != null))
            {
              localConstraintWidget.connect(localConstraintWidget.mLeft, ((ConstraintWidget)localObject1).mRight, this.mHorizontalGap);
              ((ConstraintWidget)localObject1).connect(((ConstraintWidget)localObject1).mRight, localConstraintWidget.mLeft, 0);
            }
            localObject2 = localConstraintWidget;
          }
        }
        i++;
        localObject1 = localObject2;
      }
      i = 0;
      for (Object localObject2 = localObject1; i < n; localObject2 = localObject1)
      {
        localConstraintWidget = this.mAlignedBiggestElementsInRows[i];
        localObject1 = localObject2;
        if (localConstraintWidget != null) {
          if (localConstraintWidget.getVisibility() == 8)
          {
            localObject1 = localObject2;
          }
          else
          {
            if (i == 0)
            {
              localConstraintWidget.connect(localConstraintWidget.mTop, this.mTop, getPaddingTop());
              localConstraintWidget.setVerticalChainStyle(this.mVerticalStyle);
              localConstraintWidget.setVerticalBiasPercent(this.mVerticalBias);
            }
            if (i == n - 1) {
              localConstraintWidget.connect(localConstraintWidget.mBottom, this.mBottom, getPaddingBottom());
            }
            if ((i > 0) && (localObject2 != null))
            {
              localConstraintWidget.connect(localConstraintWidget.mTop, ((ConstraintWidget)localObject2).mBottom, this.mVerticalGap);
              ((ConstraintWidget)localObject2).connect(((ConstraintWidget)localObject2).mBottom, localConstraintWidget.mTop, 0);
            }
            localObject1 = localConstraintWidget;
          }
        }
        i++;
      }
      for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++)
        {
          int k = j * m + i;
          if (this.mOrientation == 1) {
            k = i * n + j;
          }
          localObject1 = this.mDisplayedWidgets;
          if (k < localObject1.length)
          {
            localObject1 = localObject1[k];
            if ((localObject1 != null) && (((ConstraintWidget)localObject1).getVisibility() != 8))
            {
              localConstraintWidget = this.mAlignedBiggestElementsInCols[i];
              localObject2 = this.mAlignedBiggestElementsInRows[j];
              if (localObject1 != localConstraintWidget)
              {
                ((ConstraintWidget)localObject1).connect(((ConstraintWidget)localObject1).mLeft, localConstraintWidget.mLeft, 0);
                ((ConstraintWidget)localObject1).connect(((ConstraintWidget)localObject1).mRight, localConstraintWidget.mRight, 0);
              }
              if (localObject1 != localObject2)
              {
                ((ConstraintWidget)localObject1).connect(((ConstraintWidget)localObject1).mTop, ((ConstraintWidget)localObject2).mTop, 0);
                ((ConstraintWidget)localObject1).connect(((ConstraintWidget)localObject1).mBottom, ((ConstraintWidget)localObject2).mBottom, 0);
              }
            }
          }
        }
      }
      return;
    }
  }
  
  private final int getWidgetHeight(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    if (paramConstraintWidget == null) {
      return 0;
    }
    if (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 0) {
        return 0;
      }
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 2)
      {
        paramInt = (int)(paramConstraintWidget.mMatchConstraintPercentHeight * paramInt);
        if (paramInt != paramConstraintWidget.getHeight())
        {
          paramConstraintWidget.setMeasureRequested(true);
          measure(paramConstraintWidget, paramConstraintWidget.getHorizontalDimensionBehaviour(), paramConstraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, paramInt);
        }
        return paramInt;
      }
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 1) {
        return paramConstraintWidget.getHeight();
      }
      if (paramConstraintWidget.mMatchConstraintDefaultHeight == 3) {
        return (int)(paramConstraintWidget.getWidth() * paramConstraintWidget.mDimensionRatio + 0.5F);
      }
    }
    return paramConstraintWidget.getHeight();
  }
  
  private final int getWidgetWidth(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    if (paramConstraintWidget == null) {
      return 0;
    }
    if (paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 0) {
        return 0;
      }
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 2)
      {
        paramInt = (int)(paramConstraintWidget.mMatchConstraintPercentWidth * paramInt);
        if (paramInt != paramConstraintWidget.getWidth())
        {
          paramConstraintWidget.setMeasureRequested(true);
          measure(paramConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, paramInt, paramConstraintWidget.getVerticalDimensionBehaviour(), paramConstraintWidget.getHeight());
        }
        return paramInt;
      }
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 1) {
        return paramConstraintWidget.getWidth();
      }
      if (paramConstraintWidget.mMatchConstraintDefaultWidth == 3) {
        return (int)(paramConstraintWidget.getHeight() * paramConstraintWidget.mDimensionRatio + 0.5F);
      }
    }
    return paramConstraintWidget.getWidth();
  }
  
  private void measureAligned(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    int i1 = 0;
    int n = 0;
    int i2 = 0;
    int k;
    int i;
    int j;
    int m;
    Object localObject;
    if (paramInt2 == 0)
    {
      k = this.mMaxElementsWrap;
      i = n;
      j = k;
      if (k <= 0)
      {
        i = 0;
        j = 0;
        for (m = 0; m < paramInt1; m++)
        {
          k = i;
          if (m > 0) {
            k = i + this.mHorizontalGap;
          }
          localObject = paramArrayOfConstraintWidget[m];
          if (localObject == null)
          {
            i = k;
          }
          else
          {
            i = k + getWidgetWidth((ConstraintWidget)localObject, paramInt3);
            if (i > paramInt3) {
              break;
            }
            j++;
          }
        }
        i = n;
      }
    }
    else
    {
      k = this.mMaxElementsWrap;
      i = k;
      j = i2;
      if (k <= 0)
      {
        m = 0;
        k = 0;
        for (n = 0;; n++)
        {
          i = k;
          j = i2;
          if (n >= paramInt1) {
            break;
          }
          i = m;
          if (n > 0) {
            i = m + this.mVerticalGap;
          }
          localObject = paramArrayOfConstraintWidget[n];
          if (localObject == null)
          {
            m = i;
          }
          else
          {
            m = i + getWidgetHeight((ConstraintWidget)localObject, paramInt3);
            if (m > paramInt3)
            {
              i = k;
              j = i2;
              break;
            }
            k++;
          }
        }
      }
    }
    if (this.mAlignedDimensions == null) {
      this.mAlignedDimensions = new int[2];
    }
    if ((i != 0) || (paramInt2 != 1))
    {
      k = i1;
      m = i;
      n = j;
      if (j == 0)
      {
        k = i1;
        m = i;
        n = j;
        if (paramInt2 != 0) {}
      }
    }
    else
    {
      k = 1;
      n = j;
      m = i;
    }
    while (k == 0)
    {
      if (paramInt2 == 0)
      {
        m = (int)Math.ceil(paramInt1 / n);
        j = n;
      }
      else
      {
        j = (int)Math.ceil(paramInt1 / m);
      }
      localObject = this.mAlignedBiggestElementsInCols;
      if ((localObject != null) && (localObject.length >= j)) {
        Arrays.fill((Object[])localObject, null);
      } else {
        this.mAlignedBiggestElementsInCols = new ConstraintWidget[j];
      }
      localObject = this.mAlignedBiggestElementsInRows;
      if ((localObject != null) && (localObject.length >= m)) {
        Arrays.fill((Object[])localObject, null);
      } else {
        this.mAlignedBiggestElementsInRows = new ConstraintWidget[m];
      }
      for (i = 0; i < j; i++) {
        for (n = 0; n < m; n++)
        {
          i1 = n * j + i;
          if (paramInt2 == 1) {
            i1 = i * m + n;
          }
          if (i1 < paramArrayOfConstraintWidget.length)
          {
            localObject = paramArrayOfConstraintWidget[i1];
            if (localObject != null)
            {
              i1 = getWidgetWidth((ConstraintWidget)localObject, paramInt3);
              ConstraintWidget localConstraintWidget = this.mAlignedBiggestElementsInCols[i];
              if ((localConstraintWidget == null) || (localConstraintWidget.getWidth() < i1)) {
                this.mAlignedBiggestElementsInCols[i] = localObject;
              }
              i1 = getWidgetHeight((ConstraintWidget)localObject, paramInt3);
              localConstraintWidget = this.mAlignedBiggestElementsInRows[n];
              if ((localConstraintWidget == null) || (localConstraintWidget.getHeight() < i1)) {
                this.mAlignedBiggestElementsInRows[n] = localObject;
              }
            }
          }
        }
      }
      i = 0;
      n = 0;
      while (n < j)
      {
        localObject = this.mAlignedBiggestElementsInCols[n];
        i1 = i;
        if (localObject != null)
        {
          i1 = i;
          if (n > 0) {
            i1 = i + this.mHorizontalGap;
          }
          i1 += getWidgetWidth((ConstraintWidget)localObject, paramInt3);
        }
        n++;
        i = i1;
      }
      n = 0;
      i1 = 0;
      while (i1 < m)
      {
        localObject = this.mAlignedBiggestElementsInRows[i1];
        i2 = n;
        if (localObject != null)
        {
          i2 = n;
          if (i1 > 0) {
            i2 = n + this.mVerticalGap;
          }
          i2 += getWidgetHeight((ConstraintWidget)localObject, paramInt3);
        }
        i1++;
        n = i2;
      }
      paramArrayOfInt[0] = i;
      paramArrayOfInt[1] = n;
      if (paramInt2 == 0)
      {
        if (i > paramInt3)
        {
          if (j > 1)
          {
            j--;
            i = k;
          }
          else
          {
            i = 1;
          }
        }
        else {
          i = 1;
        }
      }
      else if (n > paramInt3)
      {
        if (m > 1)
        {
          m--;
          i = k;
        }
        else
        {
          i = 1;
        }
      }
      else {
        i = 1;
      }
      k = i;
      n = j;
    }
    paramArrayOfConstraintWidget = this.mAlignedDimensions;
    paramArrayOfConstraintWidget[0] = n;
    paramArrayOfConstraintWidget[1] = m;
  }
  
  private void measureChainWrap(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (paramInt1 == 0) {
      return;
    }
    this.mChainList.clear();
    Object localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
    this.mChainList.add(localObject1);
    int i = 0;
    int j = 0;
    if (paramInt2 == 0)
    {
      m = 0;
      k = 0;
      i = j;
      while (k < paramInt1)
      {
        localObject2 = paramArrayOfConstraintWidget[k];
        n = getWidgetWidth((ConstraintWidget)localObject2, paramInt3);
        if (((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          i++;
        }
        if (((m == paramInt3) || (this.mHorizontalGap + m + n > paramInt3)) && (((WidgetsList)localObject1).biggest != null)) {
          j = 1;
        } else {
          j = 0;
        }
        if ((j == 0) && (k > 0))
        {
          i1 = this.mMaxElementsWrap;
          if ((i1 > 0) && (k % i1 == 0)) {
            j = 1;
          }
        }
        if (j != 0)
        {
          localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
          ((WidgetsList)localObject1).setStartIndex(k);
          this.mChainList.add(localObject1);
          j = n;
        }
        else if (k > 0)
        {
          j = m + (this.mHorizontalGap + n);
        }
        else
        {
          j = n;
        }
        ((WidgetsList)localObject1).add((ConstraintWidget)localObject2);
        k++;
        m = j;
      }
    }
    else
    {
      m = 0;
      k = 0;
      while (k < paramInt1)
      {
        localObject2 = paramArrayOfConstraintWidget[k];
        n = getWidgetHeight((ConstraintWidget)localObject2, paramInt3);
        if (((ConstraintWidget)localObject2).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          i++;
        }
        if (((m == paramInt3) || (this.mVerticalGap + m + n > paramInt3)) && (((WidgetsList)localObject1).biggest != null)) {
          j = 1;
        } else {
          j = 0;
        }
        if ((j == 0) && (k > 0))
        {
          i1 = this.mMaxElementsWrap;
          if ((i1 > 0) && (k % i1 == 0)) {
            j = 1;
          }
        }
        if (j != 0)
        {
          localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
          ((WidgetsList)localObject1).setStartIndex(k);
          this.mChainList.add(localObject1);
          j = n;
        }
        else if (k > 0)
        {
          j = m + (this.mVerticalGap + n);
        }
        else
        {
          j = n;
        }
        ((WidgetsList)localObject1).add((ConstraintWidget)localObject2);
        k++;
        m = j;
      }
    }
    int i3 = this.mChainList.size();
    Object localObject3 = this.mLeft;
    paramArrayOfConstraintWidget = this.mTop;
    Object localObject4 = this.mRight;
    Object localObject2 = this.mBottom;
    int n = getPaddingLeft();
    int i2 = getPaddingTop();
    int i1 = getPaddingRight();
    int m = getPaddingBottom();
    if ((getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      paramInt1 = 0;
    } else {
      paramInt1 = 1;
    }
    if ((i > 0) && (paramInt1 != 0)) {
      for (j = 0; j < i3; j++)
      {
        localObject1 = (WidgetsList)this.mChainList.get(j);
        if (paramInt2 == 0) {
          ((WidgetsList)localObject1).measureMatchConstraints(paramInt3 - ((WidgetsList)localObject1).getWidth());
        } else {
          ((WidgetsList)localObject1).measureMatchConstraints(paramInt3 - ((WidgetsList)localObject1).getHeight());
        }
      }
    }
    int k = 0;
    paramInt1 = 0;
    i = 0;
    while (k < i3)
    {
      WidgetsList localWidgetsList = (WidgetsList)this.mChainList.get(k);
      if (paramInt2 == 0)
      {
        if (k < i3 - 1)
        {
          localObject1 = ((WidgetsList)this.mChainList.get(k + 1)).biggest.mTop;
          j = 0;
        }
        else
        {
          localObject1 = this.mBottom;
          j = getPaddingBottom();
        }
        localObject2 = localWidgetsList.biggest.mBottom;
        localWidgetsList.setup(paramInt2, (ConstraintAnchor)localObject3, paramArrayOfConstraintWidget, (ConstraintAnchor)localObject4, (ConstraintAnchor)localObject1, n, i2, i1, j, paramInt3);
        i2 = 0;
        paramInt1 = Math.max(paramInt1, localWidgetsList.getWidth());
        m = i + localWidgetsList.getHeight();
        i = m;
        if (k > 0) {
          i = m + this.mVerticalGap;
        }
        paramArrayOfConstraintWidget = (ConstraintWidget[])localObject2;
        localObject2 = localObject1;
        m = j;
      }
      else
      {
        if (k < i3 - 1)
        {
          localObject1 = ((WidgetsList)this.mChainList.get(k + 1)).biggest.mLeft;
          j = 0;
        }
        else
        {
          localObject1 = this.mRight;
          j = getPaddingRight();
        }
        localObject4 = localWidgetsList.biggest.mRight;
        localWidgetsList.setup(paramInt2, (ConstraintAnchor)localObject3, paramArrayOfConstraintWidget, (ConstraintAnchor)localObject1, (ConstraintAnchor)localObject2, n, i2, j, m, paramInt3);
        localObject3 = localObject4;
        n = 0;
        paramInt1 += localWidgetsList.getWidth();
        i = Math.max(i, localWidgetsList.getHeight());
        if (k > 0)
        {
          paramInt1 += this.mHorizontalGap;
          localObject4 = localObject1;
          i1 = j;
        }
        else
        {
          i1 = j;
          localObject4 = localObject1;
        }
      }
      k++;
    }
    paramArrayOfInt[0] = paramInt1;
    paramArrayOfInt[1] = i;
  }
  
  private void measureChainWrap_new(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (paramInt1 == 0) {
      return;
    }
    this.mChainList.clear();
    Object localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
    this.mChainList.add(localObject1);
    int j = 0;
    int n = 0;
    if (paramInt2 == 0)
    {
      k = 0;
      i = 0;
      m = 0;
      j = n;
      while (m < paramInt1)
      {
        i1 = i + 1;
        localObject2 = paramArrayOfConstraintWidget[m];
        n = getWidgetWidth((ConstraintWidget)localObject2, paramInt3);
        if (((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          j++;
        }
        if (((k == paramInt3) || (this.mHorizontalGap + k + n > paramInt3)) && (((WidgetsList)localObject1).biggest != null)) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i == 0) && (m > 0))
        {
          i2 = this.mMaxElementsWrap;
          if ((i2 > 0) && (i1 > i2)) {
            i = 1;
          }
        }
        if (i != 0)
        {
          localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
          ((WidgetsList)localObject1).setStartIndex(m);
          this.mChainList.add(localObject1);
          i = i1;
          k = n;
        }
        else if (m > 0)
        {
          k += this.mHorizontalGap + n;
          i = 0;
        }
        else
        {
          i = 0;
          k = n;
        }
        ((WidgetsList)localObject1).add((ConstraintWidget)localObject2);
        m++;
      }
    }
    else
    {
      n = 0;
      k = 0;
      m = 0;
      while (m < paramInt1)
      {
        localObject2 = paramArrayOfConstraintWidget[m];
        i1 = getWidgetHeight((ConstraintWidget)localObject2, paramInt3);
        if (((ConstraintWidget)localObject2).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          j++;
        }
        if (((n == paramInt3) || (this.mVerticalGap + n + i1 > paramInt3)) && (((WidgetsList)localObject1).biggest != null)) {
          i = 1;
        } else {
          i = 0;
        }
        if ((i == 0) && (m > 0))
        {
          i2 = this.mMaxElementsWrap;
          if ((i2 > 0) && (k > i2)) {
            i = 1;
          }
        }
        if (i != 0)
        {
          localObject1 = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
          ((WidgetsList)localObject1).setStartIndex(m);
          this.mChainList.add(localObject1);
          i = i1;
        }
        else if (m > 0)
        {
          i = n + (this.mVerticalGap + i1);
          k = 0;
        }
        else
        {
          k = 0;
          i = i1;
        }
        ((WidgetsList)localObject1).add((ConstraintWidget)localObject2);
        m++;
        n = i;
      }
    }
    int i3 = this.mChainList.size();
    Object localObject3 = this.mLeft;
    paramArrayOfConstraintWidget = this.mTop;
    Object localObject4 = this.mRight;
    Object localObject2 = this.mBottom;
    n = getPaddingLeft();
    int i2 = getPaddingTop();
    int i1 = getPaddingRight();
    int m = getPaddingBottom();
    if ((getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      paramInt1 = 0;
    } else {
      paramInt1 = 1;
    }
    if ((j > 0) && (paramInt1 != 0)) {
      for (i = 0; i < i3; i++)
      {
        localObject1 = (WidgetsList)this.mChainList.get(i);
        if (paramInt2 == 0) {
          ((WidgetsList)localObject1).measureMatchConstraints(paramInt3 - ((WidgetsList)localObject1).getWidth());
        } else {
          ((WidgetsList)localObject1).measureMatchConstraints(paramInt3 - ((WidgetsList)localObject1).getHeight());
        }
      }
    }
    int k = 0;
    int i = 0;
    paramInt1 = 0;
    while (k < i3)
    {
      WidgetsList localWidgetsList = (WidgetsList)this.mChainList.get(k);
      if (paramInt2 == 0)
      {
        if (k < i3 - 1)
        {
          localObject1 = ((WidgetsList)this.mChainList.get(k + 1)).biggest.mTop;
          j = 0;
        }
        else
        {
          localObject1 = this.mBottom;
          j = getPaddingBottom();
        }
        localObject2 = localWidgetsList.biggest.mBottom;
        localWidgetsList.setup(paramInt2, (ConstraintAnchor)localObject3, paramArrayOfConstraintWidget, (ConstraintAnchor)localObject4, (ConstraintAnchor)localObject1, n, i2, i1, j, paramInt3);
        i2 = 0;
        i = Math.max(i, localWidgetsList.getWidth());
        m = paramInt1 + localWidgetsList.getHeight();
        paramInt1 = m;
        if (k > 0) {
          paramInt1 = m + this.mVerticalGap;
        }
        paramArrayOfConstraintWidget = (ConstraintWidget[])localObject2;
        localObject2 = localObject1;
        m = j;
      }
      else
      {
        if (k < i3 - 1)
        {
          localObject1 = ((WidgetsList)this.mChainList.get(k + 1)).biggest.mLeft;
          j = 0;
        }
        else
        {
          localObject1 = this.mRight;
          j = getPaddingRight();
        }
        localObject4 = localWidgetsList.biggest.mRight;
        localWidgetsList.setup(paramInt2, (ConstraintAnchor)localObject3, paramArrayOfConstraintWidget, (ConstraintAnchor)localObject1, (ConstraintAnchor)localObject2, n, i2, j, m, paramInt3);
        localObject3 = localObject4;
        n = 0;
        i += localWidgetsList.getWidth();
        paramInt1 = Math.max(paramInt1, localWidgetsList.getHeight());
        if (k > 0)
        {
          i += this.mHorizontalGap;
          localObject4 = localObject1;
          i1 = j;
        }
        else
        {
          i1 = j;
          localObject4 = localObject1;
        }
      }
      k++;
    }
    paramArrayOfInt[0] = i;
    paramArrayOfInt[1] = paramInt1;
  }
  
  private void measureNoWrap(ConstraintWidget[] paramArrayOfConstraintWidget, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (paramInt1 == 0) {
      return;
    }
    WidgetsList localWidgetsList;
    if (this.mChainList.size() == 0)
    {
      localWidgetsList = new WidgetsList(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, paramInt3);
      this.mChainList.add(localWidgetsList);
    }
    else
    {
      localWidgetsList = (WidgetsList)this.mChainList.get(0);
      localWidgetsList.clear();
      localWidgetsList.setup(paramInt2, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), paramInt3);
    }
    for (paramInt2 = 0; paramInt2 < paramInt1; paramInt2++) {
      localWidgetsList.add(paramArrayOfConstraintWidget[paramInt2]);
    }
    paramArrayOfInt[0] = localWidgetsList.getWidth();
    paramArrayOfInt[1] = localWidgetsList.getHeight();
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    super.addToSolver(paramLinearSystem, paramBoolean);
    if ((getParent() != null) && (((ConstraintWidgetContainer)getParent()).isRtl())) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    int j;
    int i;
    switch (this.mWrapMode)
    {
    default: 
      break;
    case 3: 
      j = this.mChainList.size();
      i = 0;
    case 2: 
    case 1: 
    case 0: 
      while (i < j)
      {
        paramLinearSystem = (WidgetsList)this.mChainList.get(i);
        boolean bool;
        if (i == j - 1) {
          bool = true;
        } else {
          bool = false;
        }
        paramLinearSystem.createConstraints(paramBoolean, i, bool);
        i++;
        continue;
        createAlignedConstraints(paramBoolean);
        break;
        j = this.mChainList.size();
        for (i = 0; i < j; i++)
        {
          paramLinearSystem = (WidgetsList)this.mChainList.get(i);
          if (i == j - 1) {
            bool = true;
          } else {
            bool = false;
          }
          paramLinearSystem.createConstraints(paramBoolean, i, bool);
        }
        break;
        if (this.mChainList.size() > 0) {
          ((WidgetsList)this.mChainList.get(0)).createConstraints(paramBoolean, 0, true);
        }
      }
    }
    needsCallbackFromSolver(false);
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = (Flow)paramConstraintWidget;
    this.mHorizontalStyle = paramConstraintWidget.mHorizontalStyle;
    this.mVerticalStyle = paramConstraintWidget.mVerticalStyle;
    this.mFirstHorizontalStyle = paramConstraintWidget.mFirstHorizontalStyle;
    this.mFirstVerticalStyle = paramConstraintWidget.mFirstVerticalStyle;
    this.mLastHorizontalStyle = paramConstraintWidget.mLastHorizontalStyle;
    this.mLastVerticalStyle = paramConstraintWidget.mLastVerticalStyle;
    this.mHorizontalBias = paramConstraintWidget.mHorizontalBias;
    this.mVerticalBias = paramConstraintWidget.mVerticalBias;
    this.mFirstHorizontalBias = paramConstraintWidget.mFirstHorizontalBias;
    this.mFirstVerticalBias = paramConstraintWidget.mFirstVerticalBias;
    this.mLastHorizontalBias = paramConstraintWidget.mLastHorizontalBias;
    this.mLastVerticalBias = paramConstraintWidget.mLastVerticalBias;
    this.mHorizontalGap = paramConstraintWidget.mHorizontalGap;
    this.mVerticalGap = paramConstraintWidget.mVerticalGap;
    this.mHorizontalAlign = paramConstraintWidget.mHorizontalAlign;
    this.mVerticalAlign = paramConstraintWidget.mVerticalAlign;
    this.mWrapMode = paramConstraintWidget.mWrapMode;
    this.mMaxElementsWrap = paramConstraintWidget.mMaxElementsWrap;
    this.mOrientation = paramConstraintWidget.mOrientation;
  }
  
  public float getMaxElementsWrap()
  {
    return this.mMaxElementsWrap;
  }
  
  public void measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.mWidgetsCount > 0) && (!measureChildren()))
    {
      setMeasure(0, 0);
      needsCallbackFromSolver(false);
      return;
    }
    int i4 = getPaddingLeft();
    int i3 = getPaddingRight();
    int i2 = getPaddingTop();
    int i1 = getPaddingBottom();
    int[] arrayOfInt = new int[2];
    int i = this.mOrientation;
    if (i == 1) {
      j = paramInt4 - i2 - i1;
    } else {
      j = paramInt2 - i4 - i3;
    }
    if (i == 0)
    {
      if (this.mHorizontalStyle == -1) {
        this.mHorizontalStyle = 0;
      }
      if (this.mVerticalStyle == -1) {
        this.mVerticalStyle = 0;
      }
    }
    else
    {
      if (this.mHorizontalStyle == -1) {
        this.mHorizontalStyle = 0;
      }
      if (this.mVerticalStyle == -1) {
        this.mVerticalStyle = 0;
      }
    }
    ConstraintWidget[] arrayOfConstraintWidget = this.mWidgets;
    int m = 0;
    for (i = 0; m < this.mWidgetsCount; i = k)
    {
      k = i;
      if (this.mWidgets[m].getVisibility() == 8) {
        k = i + 1;
      }
      m++;
    }
    m = this.mWidgetsCount;
    if (i > 0)
    {
      arrayOfConstraintWidget = new ConstraintWidget[this.mWidgetsCount - i];
      i = 0;
      k = 0;
      while (k < this.mWidgetsCount)
      {
        ConstraintWidget localConstraintWidget = this.mWidgets[k];
        int n = i;
        if (localConstraintWidget.getVisibility() != 8)
        {
          arrayOfConstraintWidget[i] = localConstraintWidget;
          n = i + 1;
        }
        k++;
        i = n;
      }
      m = i;
    }
    this.mDisplayedWidgets = arrayOfConstraintWidget;
    this.mDisplayedWidgetsCount = m;
    switch (this.mWrapMode)
    {
    default: 
      break;
    case 3: 
      measureChainWrap_new(arrayOfConstraintWidget, m, this.mOrientation, j, arrayOfInt);
      break;
    case 2: 
      measureAligned(arrayOfConstraintWidget, m, this.mOrientation, j, arrayOfInt);
      break;
    case 1: 
      measureChainWrap(arrayOfConstraintWidget, m, this.mOrientation, j, arrayOfInt);
      break;
    case 0: 
      measureNoWrap(arrayOfConstraintWidget, m, this.mOrientation, j, arrayOfInt);
    }
    boolean bool = false;
    int k = arrayOfInt[0] + i4 + i3;
    i = arrayOfInt[1] + i2 + i1;
    m = 0;
    int j = 0;
    if (paramInt1 != 1073741824) {
      if (paramInt1 == Integer.MIN_VALUE)
      {
        paramInt2 = Math.min(k, paramInt2);
      }
      else
      {
        paramInt2 = m;
        if (paramInt1 == 0) {
          paramInt2 = k;
        }
      }
    }
    if (paramInt3 == 1073741824)
    {
      paramInt1 = paramInt4;
    }
    else if (paramInt3 == Integer.MIN_VALUE)
    {
      paramInt1 = Math.min(i, paramInt4);
    }
    else
    {
      paramInt1 = j;
      if (paramInt3 == 0) {
        paramInt1 = i;
      }
    }
    setMeasure(paramInt2, paramInt1);
    setWidth(paramInt2);
    setHeight(paramInt1);
    if (this.mWidgetsCount > 0) {
      bool = true;
    }
    needsCallbackFromSolver(bool);
  }
  
  public void setFirstHorizontalBias(float paramFloat)
  {
    this.mFirstHorizontalBias = paramFloat;
  }
  
  public void setFirstHorizontalStyle(int paramInt)
  {
    this.mFirstHorizontalStyle = paramInt;
  }
  
  public void setFirstVerticalBias(float paramFloat)
  {
    this.mFirstVerticalBias = paramFloat;
  }
  
  public void setFirstVerticalStyle(int paramInt)
  {
    this.mFirstVerticalStyle = paramInt;
  }
  
  public void setHorizontalAlign(int paramInt)
  {
    this.mHorizontalAlign = paramInt;
  }
  
  public void setHorizontalBias(float paramFloat)
  {
    this.mHorizontalBias = paramFloat;
  }
  
  public void setHorizontalGap(int paramInt)
  {
    this.mHorizontalGap = paramInt;
  }
  
  public void setHorizontalStyle(int paramInt)
  {
    this.mHorizontalStyle = paramInt;
  }
  
  public void setLastHorizontalBias(float paramFloat)
  {
    this.mLastHorizontalBias = paramFloat;
  }
  
  public void setLastHorizontalStyle(int paramInt)
  {
    this.mLastHorizontalStyle = paramInt;
  }
  
  public void setLastVerticalBias(float paramFloat)
  {
    this.mLastVerticalBias = paramFloat;
  }
  
  public void setLastVerticalStyle(int paramInt)
  {
    this.mLastVerticalStyle = paramInt;
  }
  
  public void setMaxElementsWrap(int paramInt)
  {
    this.mMaxElementsWrap = paramInt;
  }
  
  public void setOrientation(int paramInt)
  {
    this.mOrientation = paramInt;
  }
  
  public void setVerticalAlign(int paramInt)
  {
    this.mVerticalAlign = paramInt;
  }
  
  public void setVerticalBias(float paramFloat)
  {
    this.mVerticalBias = paramFloat;
  }
  
  public void setVerticalGap(int paramInt)
  {
    this.mVerticalGap = paramInt;
  }
  
  public void setVerticalStyle(int paramInt)
  {
    this.mVerticalStyle = paramInt;
  }
  
  public void setWrapMode(int paramInt)
  {
    this.mWrapMode = paramInt;
  }
  
  private class WidgetsList
  {
    private ConstraintWidget biggest = null;
    int biggestDimension = 0;
    private ConstraintAnchor mBottom;
    private int mCount = 0;
    private int mHeight = 0;
    private ConstraintAnchor mLeft;
    private int mMax = 0;
    private int mNbMatchConstraintsWidgets = 0;
    private int mOrientation = 0;
    private int mPaddingBottom = 0;
    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;
    private int mPaddingTop = 0;
    private ConstraintAnchor mRight;
    private int mStartIndex = 0;
    private ConstraintAnchor mTop;
    private int mWidth = 0;
    
    public WidgetsList(int paramInt1, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, ConstraintAnchor paramConstraintAnchor3, ConstraintAnchor paramConstraintAnchor4, int paramInt2)
    {
      this.mOrientation = paramInt1;
      this.mLeft = paramConstraintAnchor1;
      this.mTop = paramConstraintAnchor2;
      this.mRight = paramConstraintAnchor3;
      this.mBottom = paramConstraintAnchor4;
      this.mPaddingLeft = Flow.this.getPaddingLeft();
      this.mPaddingTop = Flow.this.getPaddingTop();
      this.mPaddingRight = Flow.this.getPaddingRight();
      this.mPaddingBottom = Flow.this.getPaddingBottom();
      this.mMax = paramInt2;
    }
    
    private void recomputeDimensions()
    {
      this.mWidth = 0;
      this.mHeight = 0;
      this.biggest = null;
      this.biggestDimension = 0;
      int k = this.mCount;
      for (int i = 0; (i < k) && (this.mStartIndex + i < Flow.this.mDisplayedWidgetsCount); i++)
      {
        ConstraintWidget localConstraintWidget = Flow.this.mDisplayedWidgets[(this.mStartIndex + i)];
        int m;
        int j;
        if (this.mOrientation == 0)
        {
          m = localConstraintWidget.getWidth();
          j = Flow.this.mHorizontalGap;
          if (localConstraintWidget.getVisibility() == 8) {
            j = 0;
          }
          this.mWidth += m + j;
          j = Flow.this.getWidgetHeight(localConstraintWidget, this.mMax);
          if ((this.biggest == null) || (this.biggestDimension < j))
          {
            this.biggest = localConstraintWidget;
            this.biggestDimension = j;
            this.mHeight = j;
          }
        }
        else
        {
          m = Flow.this.getWidgetWidth(localConstraintWidget, this.mMax);
          int n = Flow.this.getWidgetHeight(localConstraintWidget, this.mMax);
          j = Flow.this.mVerticalGap;
          if (localConstraintWidget.getVisibility() == 8) {
            j = 0;
          }
          this.mHeight += n + j;
          if ((this.biggest == null) || (this.biggestDimension < m))
          {
            this.biggest = localConstraintWidget;
            this.biggestDimension = m;
            this.mWidth = m;
          }
        }
      }
    }
    
    public void add(ConstraintWidget paramConstraintWidget)
    {
      int i;
      int j;
      if (this.mOrientation == 0)
      {
        i = Flow.this.getWidgetWidth(paramConstraintWidget, this.mMax);
        if (paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        {
          this.mNbMatchConstraintsWidgets += 1;
          i = 0;
        }
        j = Flow.this.mHorizontalGap;
        if (paramConstraintWidget.getVisibility() == 8) {
          j = 0;
        }
        this.mWidth += i + j;
        i = Flow.this.getWidgetHeight(paramConstraintWidget, this.mMax);
        if ((this.biggest == null) || (this.biggestDimension < i))
        {
          this.biggest = paramConstraintWidget;
          this.biggestDimension = i;
          this.mHeight = i;
        }
      }
      else
      {
        int k = Flow.this.getWidgetWidth(paramConstraintWidget, this.mMax);
        i = Flow.this.getWidgetHeight(paramConstraintWidget, this.mMax);
        if (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        {
          this.mNbMatchConstraintsWidgets += 1;
          i = 0;
        }
        j = Flow.this.mVerticalGap;
        if (paramConstraintWidget.getVisibility() == 8) {
          j = 0;
        }
        this.mHeight += i + j;
        if ((this.biggest == null) || (this.biggestDimension < k))
        {
          this.biggest = paramConstraintWidget;
          this.biggestDimension = k;
          this.mWidth = k;
        }
      }
      this.mCount += 1;
    }
    
    public void clear()
    {
      this.biggestDimension = 0;
      this.biggest = null;
      this.mWidth = 0;
      this.mHeight = 0;
      this.mStartIndex = 0;
      this.mCount = 0;
      this.mNbMatchConstraintsWidgets = 0;
    }
    
    public void createConstraints(boolean paramBoolean1, int paramInt, boolean paramBoolean2)
    {
      int i2 = this.mCount;
      Object localObject;
      for (int i = 0; (i < i2) && (this.mStartIndex + i < Flow.this.mDisplayedWidgetsCount); i++)
      {
        localObject = Flow.this.mDisplayedWidgets[(this.mStartIndex + i)];
        if (localObject != null) {
          ((ConstraintWidget)localObject).resetAnchors();
        }
      }
      if ((i2 != 0) && (this.biggest != null))
      {
        int m;
        if ((paramBoolean2) && (paramInt == 0)) {
          m = 1;
        } else {
          m = 0;
        }
        int j = -1;
        int k = -1;
        i = 0;
        int n;
        while (i < i2)
        {
          n = i;
          if (paramBoolean1) {
            n = i2 - 1 - i;
          }
          if (this.mStartIndex + n >= Flow.this.mDisplayedWidgetsCount) {
            break;
          }
          localObject = Flow.this.mDisplayedWidgets[(this.mStartIndex + n)];
          int i1 = j;
          n = k;
          if (localObject != null)
          {
            i1 = j;
            n = k;
            if (((ConstraintWidget)localObject).getVisibility() == 0)
            {
              k = j;
              if (j == -1) {
                k = i;
              }
              n = i;
              i1 = k;
            }
          }
          i++;
          j = i1;
          k = n;
        }
        localObject = null;
        ConstraintWidget localConstraintWidget3 = null;
        ConstraintWidget localConstraintWidget2;
        ConstraintWidget localConstraintWidget1;
        float f2;
        float f1;
        if (this.mOrientation == 0)
        {
          localConstraintWidget2 = this.biggest;
          localConstraintWidget2.setVerticalChainStyle(Flow.this.mVerticalStyle);
          n = this.mPaddingTop;
          i = n;
          if (paramInt > 0) {
            i = n + Flow.this.mVerticalGap;
          }
          localConstraintWidget2.mTop.connect(this.mTop, i);
          if (paramBoolean2) {
            localConstraintWidget2.mBottom.connect(this.mBottom, this.mPaddingBottom);
          }
          if (paramInt > 0) {
            this.mTop.mOwner.mBottom.connect(localConstraintWidget2.mTop, 0);
          }
          localConstraintWidget1 = localConstraintWidget2;
          localObject = localConstraintWidget1;
          if (Flow.this.mVerticalAlign == 3)
          {
            localObject = localConstraintWidget1;
            if (!localConstraintWidget2.hasBaseline()) {
              for (paramInt = 0;; paramInt++)
              {
                localObject = localConstraintWidget1;
                if (paramInt >= i2) {
                  break;
                }
                i = paramInt;
                if (paramBoolean1) {
                  i = i2 - 1 - paramInt;
                }
                if (this.mStartIndex + i >= Flow.this.mDisplayedWidgetsCount)
                {
                  localObject = localConstraintWidget1;
                }
                else
                {
                  localObject = Flow.this.mDisplayedWidgets[(this.mStartIndex + i)];
                  if (((ConstraintWidget)localObject).hasBaseline()) {
                    break;
                  }
                }
              }
            }
          }
          paramInt = 0;
          localConstraintWidget1 = localConstraintWidget3;
          while (paramInt < i2)
          {
            i = paramInt;
            if (paramBoolean1) {
              i = i2 - 1 - paramInt;
            }
            if (this.mStartIndex + i >= Flow.this.mDisplayedWidgetsCount) {
              break;
            }
            localConstraintWidget3 = Flow.this.mDisplayedWidgets[(this.mStartIndex + i)];
            if (localConstraintWidget3 != null)
            {
              if (paramInt == 0) {
                localConstraintWidget3.connect(localConstraintWidget3.mLeft, this.mLeft, this.mPaddingLeft);
              }
              if (i == 0)
              {
                n = Flow.this.mHorizontalStyle;
                f2 = Flow.this.mHorizontalBias;
                f1 = f2;
                if (paramBoolean1) {
                  f1 = 1.0F - f2;
                }
                if ((this.mStartIndex == 0) && (Flow.this.mFirstHorizontalStyle != -1))
                {
                  i = Flow.this.mFirstHorizontalStyle;
                  f2 = Flow.this.mFirstHorizontalBias;
                  f1 = f2;
                  if (paramBoolean1) {
                    f1 = 1.0F - f2;
                  }
                  f2 = f1;
                }
                else
                {
                  i = n;
                  f2 = f1;
                  if (paramBoolean2)
                  {
                    i = n;
                    f2 = f1;
                    if (Flow.this.mLastHorizontalStyle != -1)
                    {
                      i = Flow.this.mLastHorizontalStyle;
                      f2 = Flow.this.mLastHorizontalBias;
                      f1 = f2;
                      if (paramBoolean1) {
                        f1 = 1.0F - f2;
                      }
                      f2 = f1;
                    }
                  }
                }
                localConstraintWidget3.setHorizontalChainStyle(i);
                localConstraintWidget3.setHorizontalBiasPercent(f2);
              }
              if (paramInt == i2 - 1) {
                localConstraintWidget3.connect(localConstraintWidget3.mRight, this.mRight, this.mPaddingRight);
              }
              if (localConstraintWidget1 != null)
              {
                localConstraintWidget3.mLeft.connect(localConstraintWidget1.mRight, Flow.this.mHorizontalGap);
                if (paramInt == j) {
                  localConstraintWidget3.mLeft.setGoneMargin(this.mPaddingLeft);
                }
                localConstraintWidget1.mRight.connect(localConstraintWidget3.mLeft, 0);
                if (paramInt == k + 1) {
                  localConstraintWidget1.mRight.setGoneMargin(this.mPaddingRight);
                }
              }
              if (localConstraintWidget3 != localConstraintWidget2) {
                if ((Flow.this.mVerticalAlign == 3) && (((ConstraintWidget)localObject).hasBaseline()) && (localConstraintWidget3 != localObject) && (localConstraintWidget3.hasBaseline()))
                {
                  localConstraintWidget3.mBaseline.connect(((ConstraintWidget)localObject).mBaseline, 0);
                }
                else
                {
                  switch (Flow.this.mVerticalAlign)
                  {
                  default: 
                    if (m != 0)
                    {
                      localConstraintWidget3.mTop.connect(this.mTop, this.mPaddingTop);
                      localConstraintWidget3.mBottom.connect(this.mBottom, this.mPaddingBottom);
                    }
                    break;
                  case 1: 
                    localConstraintWidget3.mBottom.connect(localConstraintWidget2.mBottom, 0);
                    break;
                  case 0: 
                    localConstraintWidget3.mTop.connect(localConstraintWidget2.mTop, 0);
                    break;
                  }
                  localConstraintWidget3.mTop.connect(localConstraintWidget2.mTop, 0);
                  localConstraintWidget3.mBottom.connect(localConstraintWidget2.mBottom, 0);
                }
              }
              localConstraintWidget1 = localConstraintWidget3;
            }
            paramInt++;
          }
        }
        else
        {
          localConstraintWidget2 = this.biggest;
          localConstraintWidget2.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
          n = this.mPaddingLeft;
          i = n;
          if (paramInt > 0) {
            i = n + Flow.this.mHorizontalGap;
          }
          if (paramBoolean1)
          {
            localConstraintWidget2.mRight.connect(this.mRight, i);
            if (paramBoolean2) {
              localConstraintWidget2.mLeft.connect(this.mLeft, this.mPaddingRight);
            }
            if (paramInt > 0) {
              this.mRight.mOwner.mLeft.connect(localConstraintWidget2.mRight, 0);
            }
          }
          else
          {
            localConstraintWidget2.mLeft.connect(this.mLeft, i);
            if (paramBoolean2) {
              localConstraintWidget2.mRight.connect(this.mRight, this.mPaddingRight);
            }
            if (paramInt > 0) {
              this.mLeft.mOwner.mRight.connect(localConstraintWidget2.mLeft, 0);
            }
          }
          for (i = 0; (i < i2) && (this.mStartIndex + i < Flow.this.mDisplayedWidgetsCount); i++)
          {
            localConstraintWidget1 = Flow.this.mDisplayedWidgets[(this.mStartIndex + i)];
            if (localConstraintWidget1 != null)
            {
              if (i == 0)
              {
                localConstraintWidget1.connect(localConstraintWidget1.mTop, this.mTop, this.mPaddingTop);
                n = Flow.this.mVerticalStyle;
                f2 = Flow.this.mVerticalBias;
                if ((this.mStartIndex == 0) && (Flow.this.mFirstVerticalStyle != -1))
                {
                  paramInt = Flow.this.mFirstVerticalStyle;
                  f1 = Flow.this.mFirstVerticalBias;
                }
                else
                {
                  paramInt = n;
                  f1 = f2;
                  if (paramBoolean2)
                  {
                    paramInt = n;
                    f1 = f2;
                    if (Flow.this.mLastVerticalStyle != -1)
                    {
                      paramInt = Flow.this.mLastVerticalStyle;
                      f1 = Flow.this.mLastVerticalBias;
                    }
                  }
                }
                localConstraintWidget1.setVerticalChainStyle(paramInt);
                localConstraintWidget1.setVerticalBiasPercent(f1);
              }
              if (i == i2 - 1) {
                localConstraintWidget1.connect(localConstraintWidget1.mBottom, this.mBottom, this.mPaddingBottom);
              }
              if (localObject != null)
              {
                localConstraintWidget1.mTop.connect(((ConstraintWidget)localObject).mBottom, Flow.this.mVerticalGap);
                if (i == j) {
                  localConstraintWidget1.mTop.setGoneMargin(this.mPaddingTop);
                }
                ((ConstraintWidget)localObject).mBottom.connect(localConstraintWidget1.mTop, 0);
                if (i == k + 1) {
                  ((ConstraintWidget)localObject).mBottom.setGoneMargin(this.mPaddingBottom);
                }
              }
              if (localConstraintWidget1 != localConstraintWidget2) {
                if (paramBoolean1) {
                  switch (Flow.this.mHorizontalAlign)
                  {
                  default: 
                    break;
                  case 2: 
                    localConstraintWidget1.mLeft.connect(localConstraintWidget2.mLeft, 0);
                    localConstraintWidget1.mRight.connect(localConstraintWidget2.mRight, 0);
                    break;
                  case 1: 
                    localConstraintWidget1.mLeft.connect(localConstraintWidget2.mLeft, 0);
                    break;
                  case 0: 
                    localConstraintWidget1.mRight.connect(localConstraintWidget2.mRight, 0);
                  }
                } else {
                  switch (Flow.this.mHorizontalAlign)
                  {
                  default: 
                    break;
                  case 2: 
                    if (m != 0)
                    {
                      localConstraintWidget1.mLeft.connect(this.mLeft, this.mPaddingLeft);
                      localConstraintWidget1.mRight.connect(this.mRight, this.mPaddingRight);
                    }
                    else
                    {
                      localConstraintWidget1.mLeft.connect(localConstraintWidget2.mLeft, 0);
                      localConstraintWidget1.mRight.connect(localConstraintWidget2.mRight, 0);
                    }
                    break;
                  case 1: 
                    localConstraintWidget1.mRight.connect(localConstraintWidget2.mRight, 0);
                    break;
                  case 0: 
                    localConstraintWidget1.mLeft.connect(localConstraintWidget2.mLeft, 0);
                    break;
                  }
                }
              }
              localObject = localConstraintWidget1;
            }
          }
        }
        return;
      }
    }
    
    public int getHeight()
    {
      if (this.mOrientation == 1) {
        return this.mHeight - Flow.this.mVerticalGap;
      }
      return this.mHeight;
    }
    
    public int getWidth()
    {
      if (this.mOrientation == 0) {
        return this.mWidth - Flow.this.mHorizontalGap;
      }
      return this.mWidth;
    }
    
    public void measureMatchConstraints(int paramInt)
    {
      int j = this.mNbMatchConstraintsWidgets;
      if (j == 0) {
        return;
      }
      int i = this.mCount;
      j = paramInt / j;
      for (paramInt = 0; (paramInt < i) && (this.mStartIndex + paramInt < Flow.this.mDisplayedWidgetsCount); paramInt++)
      {
        ConstraintWidget localConstraintWidget = Flow.this.mDisplayedWidgets[(this.mStartIndex + paramInt)];
        if (this.mOrientation == 0)
        {
          if ((localConstraintWidget != null) && (localConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mMatchConstraintDefaultWidth == 0)) {
            Flow.this.measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, j, localConstraintWidget.getVerticalDimensionBehaviour(), localConstraintWidget.getHeight());
          }
        }
        else if ((localConstraintWidget != null) && (localConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mMatchConstraintDefaultHeight == 0)) {
          Flow.this.measure(localConstraintWidget, localConstraintWidget.getHorizontalDimensionBehaviour(), localConstraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, j);
        }
      }
      recomputeDimensions();
    }
    
    public void setStartIndex(int paramInt)
    {
      this.mStartIndex = paramInt;
    }
    
    public void setup(int paramInt1, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, ConstraintAnchor paramConstraintAnchor3, ConstraintAnchor paramConstraintAnchor4, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      this.mOrientation = paramInt1;
      this.mLeft = paramConstraintAnchor1;
      this.mTop = paramConstraintAnchor2;
      this.mRight = paramConstraintAnchor3;
      this.mBottom = paramConstraintAnchor4;
      this.mPaddingLeft = paramInt2;
      this.mPaddingTop = paramInt3;
      this.mPaddingRight = paramInt4;
      this.mPaddingBottom = paramInt5;
      this.mMax = paramInt6;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Flow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */