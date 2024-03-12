package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import java.util.Arrays;

public class GridLayoutManager
  extends LinearLayoutManager
{
  private static final boolean DEBUG = false;
  public static final int DEFAULT_SPAN_COUNT = -1;
  private static final String TAG = "GridLayoutManager";
  int[] mCachedBorders;
  final Rect mDecorInsets = new Rect();
  boolean mPendingSpanCountChange = false;
  final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
  final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
  View[] mSet;
  int mSpanCount = -1;
  SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
  private boolean mUsingSpansToEstimateScrollBarDimensions;
  
  public GridLayoutManager(Context paramContext, int paramInt)
  {
    super(paramContext);
    setSpanCount(paramInt);
  }
  
  public GridLayoutManager(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    super(paramContext, paramInt2, paramBoolean);
    setSpanCount(paramInt1);
  }
  
  public GridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setSpanCount(getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2).spanCount);
  }
  
  private void assignSpans(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt, boolean paramBoolean)
  {
    int i;
    int j;
    if (paramBoolean)
    {
      k = 0;
      i = paramInt;
      j = 1;
      paramInt = k;
    }
    else
    {
      paramInt--;
      i = -1;
      j = -1;
    }
    int k = 0;
    while (paramInt != i)
    {
      View localView = this.mSet[paramInt];
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      localLayoutParams.mSpanSize = getSpanSize(paramRecycler, paramState, getPosition(localView));
      localLayoutParams.mSpanIndex = k;
      k += localLayoutParams.mSpanSize;
      paramInt += j;
    }
  }
  
  private void cachePreLayoutSpanMapping()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
      int k = localLayoutParams.getViewLayoutPosition();
      this.mPreLayoutSpanSizeCache.put(k, localLayoutParams.getSpanSize());
      this.mPreLayoutSpanIndexCache.put(k, localLayoutParams.getSpanIndex());
    }
  }
  
  private void calculateItemBorders(int paramInt)
  {
    this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, paramInt);
  }
  
  static int[] calculateItemBorders(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt;
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length == paramInt1 + 1))
    {
      arrayOfInt = paramArrayOfInt;
      if (paramArrayOfInt[(paramArrayOfInt.length - 1)] == paramInt2) {}
    }
    else
    {
      arrayOfInt = new int[paramInt1 + 1];
    }
    arrayOfInt[0] = 0;
    int n = paramInt2 / paramInt1;
    int i2 = paramInt2 % paramInt1;
    int j = 0;
    paramInt2 = 0;
    for (int i = 1; i <= paramInt1; i++)
    {
      int m = n;
      int i1 = paramInt2 + i2;
      paramInt2 = i1;
      int k = m;
      if (i1 > 0)
      {
        paramInt2 = i1;
        k = m;
        if (paramInt1 - i1 < i2)
        {
          k = m + 1;
          paramInt2 = i1 - paramInt1;
        }
      }
      j += k;
      arrayOfInt[i] = j;
    }
    return arrayOfInt;
  }
  
  private void clearPreLayoutSpanMappingCache()
  {
    this.mPreLayoutSpanSizeCache.clear();
    this.mPreLayoutSpanIndexCache.clear();
  }
  
  private int computeScrollOffsetWithSpanInfo(RecyclerView.State paramState)
  {
    if ((getChildCount() != 0) && (paramState.getItemCount() != 0))
    {
      ensureLayoutState();
      boolean bool = isSmoothScrollbarEnabled();
      View localView2 = findFirstVisibleChildClosestToStart(bool ^ true, true);
      View localView1 = findFirstVisibleChildClosestToEnd(bool ^ true, true);
      if ((localView2 != null) && (localView1 != null))
      {
        int k = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView2), this.mSpanCount);
        int j = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView1), this.mSpanCount);
        int i = Math.min(k, j);
        k = Math.max(k, j);
        j = this.mSpanSizeLookup.getCachedSpanGroupIndex(paramState.getItemCount() - 1, this.mSpanCount);
        if (this.mShouldReverseLayout) {
          i = Math.max(0, j + 1 - k - 1);
        } else {
          i = Math.max(0, i);
        }
        if (!bool) {
          return i;
        }
        int m = Math.abs(this.mOrientationHelper.getDecoratedEnd(localView1) - this.mOrientationHelper.getDecoratedStart(localView2));
        k = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView2), this.mSpanCount);
        j = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView1), this.mSpanCount);
        float f = m / (j - k + 1);
        return Math.round(i * f + (this.mOrientationHelper.getStartAfterPadding() - this.mOrientationHelper.getDecoratedStart(localView2)));
      }
      return 0;
    }
    return 0;
  }
  
  private int computeScrollRangeWithSpanInfo(RecyclerView.State paramState)
  {
    if ((getChildCount() != 0) && (paramState.getItemCount() != 0))
    {
      ensureLayoutState();
      View localView2 = findFirstVisibleChildClosestToStart(isSmoothScrollbarEnabled() ^ true, true);
      View localView1 = findFirstVisibleChildClosestToEnd(isSmoothScrollbarEnabled() ^ true, true);
      if ((localView2 != null) && (localView1 != null))
      {
        if (!isSmoothScrollbarEnabled()) {
          return this.mSpanSizeLookup.getCachedSpanGroupIndex(paramState.getItemCount() - 1, this.mSpanCount) + 1;
        }
        int j = this.mOrientationHelper.getDecoratedEnd(localView1);
        int i = this.mOrientationHelper.getDecoratedStart(localView2);
        int k = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView2), this.mSpanCount);
        int m = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(localView1), this.mSpanCount);
        int n = this.mSpanSizeLookup.getCachedSpanGroupIndex(paramState.getItemCount() - 1, this.mSpanCount);
        return (int)((j - i) / (m - k + 1) * (n + 1));
      }
      return 0;
    }
    return 0;
  }
  
  private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo, int paramInt)
  {
    if (paramInt == 1) {
      i = 1;
    } else {
      i = 0;
    }
    paramInt = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition);
    if (i != 0) {
      while ((paramInt > 0) && (paramAnchorInfo.mPosition > 0))
      {
        paramAnchorInfo.mPosition -= 1;
        paramInt = getSpanIndex(paramRecycler, paramState, paramAnchorInfo.mPosition);
      }
    }
    int k = paramState.getItemCount();
    int i = paramAnchorInfo.mPosition;
    while (i < k - 1)
    {
      int j = getSpanIndex(paramRecycler, paramState, i + 1);
      if (j <= paramInt) {
        break;
      }
      i++;
      paramInt = j;
    }
    paramAnchorInfo.mPosition = i;
  }
  
  private void ensureViewSet()
  {
    View[] arrayOfView = this.mSet;
    if ((arrayOfView == null) || (arrayOfView.length != this.mSpanCount)) {
      this.mSet = new View[this.mSpanCount];
    }
  }
  
  private int getSpanGroupIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getCachedSpanGroupIndex(paramInt, this.mSpanCount);
    }
    int i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanGroupIndex(i, this.mSpanCount);
  }
  
  private int getSpanIndex(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getCachedSpanIndex(paramInt, this.mSpanCount);
    }
    int i = this.mPreLayoutSpanIndexCache.get(paramInt, -1);
    if (i != -1) {
      return i;
    }
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 0;
    }
    return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
  }
  
  private int getSpanSize(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt)
  {
    if (!paramState.isPreLayout()) {
      return this.mSpanSizeLookup.getSpanSize(paramInt);
    }
    int i = this.mPreLayoutSpanSizeCache.get(paramInt, -1);
    if (i != -1) {
      return i;
    }
    i = paramRecycler.convertPreLayoutPositionToPostLayout(paramInt);
    if (i == -1)
    {
      Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + paramInt);
      return 1;
    }
    return this.mSpanSizeLookup.getSpanSize(i);
  }
  
  private void guessMeasurement(float paramFloat, int paramInt)
  {
    calculateItemBorders(Math.max(Math.round(this.mSpanCount * paramFloat), paramInt));
  }
  
  private void measureChild(View paramView, int paramInt, boolean paramBoolean)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect localRect = localLayoutParams.mDecorInsets;
    int i = localRect.top + localRect.bottom + localLayoutParams.topMargin + localLayoutParams.bottomMargin;
    int j = localRect.left + localRect.right + localLayoutParams.leftMargin + localLayoutParams.rightMargin;
    int k = getSpaceForSpanRange(localLayoutParams.mSpanIndex, localLayoutParams.mSpanSize);
    if (this.mOrientation == 1)
    {
      paramInt = getChildMeasureSpec(k, paramInt, j, localLayoutParams.width, false);
      i = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i, localLayoutParams.height, true);
    }
    else
    {
      i = getChildMeasureSpec(k, paramInt, i, localLayoutParams.height, false);
      paramInt = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), j, localLayoutParams.width, true);
    }
    measureChildWithDecorationsAndMargin(paramView, paramInt, i, paramBoolean);
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    RecyclerView.LayoutParams localLayoutParams = (RecyclerView.LayoutParams)paramView.getLayoutParams();
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    }
    if (paramBoolean) {
      paramView.measure(paramInt1, paramInt2);
    }
  }
  
  private void updateMeasurements()
  {
    int i;
    if (getOrientation() == 1) {
      i = getWidth() - getPaddingRight() - getPaddingLeft();
    } else {
      i = getHeight() - getPaddingBottom() - getPaddingTop();
    }
    calculateItemBorders(i);
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void collectPrefetchPositionsForLayoutState(RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    int j = this.mSpanCount;
    for (int i = 0; (i < this.mSpanCount) && (paramLayoutState.hasMore(paramState)) && (j > 0); i++)
    {
      int k = paramLayoutState.mCurrentPosition;
      paramLayoutPrefetchRegistry.addPosition(k, Math.max(0, paramLayoutState.mScrollingOffset));
      j -= this.mSpanSizeLookup.getSpanSize(k);
      paramLayoutState.mCurrentPosition += paramLayoutState.mItemDirection;
    }
  }
  
  public int computeHorizontalScrollOffset(RecyclerView.State paramState)
  {
    if (this.mUsingSpansToEstimateScrollBarDimensions) {
      return computeScrollOffsetWithSpanInfo(paramState);
    }
    return super.computeHorizontalScrollOffset(paramState);
  }
  
  public int computeHorizontalScrollRange(RecyclerView.State paramState)
  {
    if (this.mUsingSpansToEstimateScrollBarDimensions) {
      return computeScrollRangeWithSpanInfo(paramState);
    }
    return super.computeHorizontalScrollRange(paramState);
  }
  
  public int computeVerticalScrollOffset(RecyclerView.State paramState)
  {
    if (this.mUsingSpansToEstimateScrollBarDimensions) {
      return computeScrollOffsetWithSpanInfo(paramState);
    }
    return super.computeVerticalScrollOffset(paramState);
  }
  
  public int computeVerticalScrollRange(RecyclerView.State paramState)
  {
    if (this.mUsingSpansToEstimateScrollBarDimensions) {
      return computeScrollRangeWithSpanInfo(paramState);
    }
    return super.computeVerticalScrollRange(paramState);
  }
  
  View findReferenceChild(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureLayoutState();
    Object localObject2 = null;
    Object localObject1 = null;
    int k = this.mOrientationHelper.getStartAfterPadding();
    int j = this.mOrientationHelper.getEndAfterPadding();
    int i;
    if (paramInt2 > paramInt1) {
      i = 1;
    } else {
      i = -1;
    }
    while (paramInt1 != paramInt2)
    {
      View localView = getChildAt(paramInt1);
      int m = getPosition(localView);
      Object localObject4 = localObject2;
      Object localObject3 = localObject1;
      if (m >= 0)
      {
        localObject4 = localObject2;
        localObject3 = localObject1;
        if (m < paramInt3) {
          if (getSpanIndex(paramRecycler, paramState, m) != 0)
          {
            localObject4 = localObject2;
            localObject3 = localObject1;
          }
          else if (((RecyclerView.LayoutParams)localView.getLayoutParams()).isItemRemoved())
          {
            localObject4 = localObject2;
            localObject3 = localObject1;
            if (localObject2 == null)
            {
              localObject4 = localView;
              localObject3 = localObject1;
            }
          }
          else
          {
            if ((this.mOrientationHelper.getDecoratedStart(localView) < j) && (this.mOrientationHelper.getDecoratedEnd(localView) >= k)) {
              return localView;
            }
            localObject4 = localObject2;
            localObject3 = localObject1;
            if (localObject1 == null)
            {
              localObject3 = localView;
              localObject4 = localObject2;
            }
          }
        }
      }
      paramInt1 += i;
      localObject2 = localObject4;
      localObject1 = localObject3;
    }
    if (localObject1 == null) {
      localObject1 = localObject2;
    }
    return (View)localObject1;
  }
  
  public RecyclerView.LayoutParams generateDefaultLayoutParams()
  {
    if (this.mOrientation == 0) {
      return new LayoutParams(-2, -1);
    }
    return new LayoutParams(-1, -2);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LayoutParams(paramContext, paramAttributeSet);
  }
  
  public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getColumnCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 1) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1;
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    if (paramState.getItemCount() < 1) {
      return 0;
    }
    return getSpanGroupIndex(paramRecycler, paramState, paramState.getItemCount() - 1) + 1;
  }
  
  int getSpaceForSpanRange(int paramInt1, int paramInt2)
  {
    if ((this.mOrientation == 1) && (isLayoutRTL()))
    {
      arrayOfInt = this.mCachedBorders;
      int i = this.mSpanCount;
      return arrayOfInt[(i - paramInt1)] - arrayOfInt[(i - paramInt1 - paramInt2)];
    }
    int[] arrayOfInt = this.mCachedBorders;
    return arrayOfInt[(paramInt1 + paramInt2)] - arrayOfInt[paramInt1];
  }
  
  public int getSpanCount()
  {
    return this.mSpanCount;
  }
  
  public SpanSizeLookup getSpanSizeLookup()
  {
    return this.mSpanSizeLookup;
  }
  
  public boolean isUsingSpansToEstimateScrollbarDimensions()
  {
    return this.mUsingSpansToEstimateScrollBarDimensions;
  }
  
  void layoutChunk(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.LayoutState paramLayoutState, LinearLayoutManager.LayoutChunkResult paramLayoutChunkResult)
  {
    int i2 = this.mOrientationHelper.getModeInOther();
    if (i2 != 1073741824) {
      i = 1;
    } else {
      i = 0;
    }
    int m = i;
    if (getChildCount() > 0) {
      n = this.mCachedBorders[this.mSpanCount];
    } else {
      n = 0;
    }
    if (m != 0) {
      updateMeasurements();
    }
    boolean bool;
    if (paramLayoutState.mItemDirection == 1) {
      bool = true;
    } else {
      bool = false;
    }
    int j = this.mSpanCount;
    if (!bool)
    {
      j = getSpanIndex(paramRecycler, paramState, paramLayoutState.mCurrentPosition) + getSpanSize(paramRecycler, paramState, paramLayoutState.mCurrentPosition);
      i = 0;
      k = 0;
    }
    else
    {
      i = 0;
      k = 0;
    }
    int i3;
    Object localObject;
    for (;;)
    {
      i1 = j;
      if (i >= this.mSpanCount) {
        break label307;
      }
      i1 = j;
      if (!paramLayoutState.hasMore(paramState)) {
        break label307;
      }
      i1 = j;
      if (j <= 0) {
        break label307;
      }
      i3 = paramLayoutState.mCurrentPosition;
      i1 = getSpanSize(paramRecycler, paramState, i3);
      if (i1 > this.mSpanCount) {
        break;
      }
      j -= i1;
      if (j < 0)
      {
        i1 = j;
        break label307;
      }
      localObject = paramLayoutState.next(paramRecycler);
      if (localObject == null)
      {
        i1 = j;
        break label307;
      }
      k += i1;
      this.mSet[i] = localObject;
      i++;
    }
    throw new IllegalArgumentException("Item at position " + i3 + " requires " + i1 + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
    label307:
    if (i == 0)
    {
      paramLayoutChunkResult.mFinished = true;
      return;
    }
    j = 0;
    assignSpans(paramRecycler, paramState, i, bool);
    int i1 = 0;
    float f2;
    for (float f1 = 0.0F; i1 < i; f1 = f2)
    {
      paramRecycler = this.mSet[i1];
      if (paramLayoutState.mScrapList == null)
      {
        if (bool) {
          addView(paramRecycler);
        } else {
          addView(paramRecycler, 0);
        }
      }
      else if (bool) {
        addDisappearingView(paramRecycler);
      } else {
        addDisappearingView(paramRecycler, 0);
      }
      calculateItemDecorationsForChild(paramRecycler, this.mDecorInsets);
      measureChild(paramRecycler, i2, false);
      i3 = this.mOrientationHelper.getDecoratedMeasurement(paramRecycler);
      k = j;
      if (i3 > j) {
        k = i3;
      }
      paramState = (LayoutParams)paramRecycler.getLayoutParams();
      float f3 = this.mOrientationHelper.getDecoratedMeasurementInOther(paramRecycler) * 1.0F / paramState.mSpanSize;
      f2 = f1;
      if (f3 > f1) {
        f2 = f3;
      }
      i1++;
      j = k;
    }
    if (m != 0)
    {
      guessMeasurement(f1, n);
      j = 0;
      k = 0;
      while (k < i)
      {
        paramRecycler = this.mSet[k];
        measureChild(paramRecycler, 1073741824, true);
        i1 = this.mOrientationHelper.getDecoratedMeasurement(paramRecycler);
        n = j;
        if (i1 > j) {
          n = i1;
        }
        k++;
        j = n;
      }
    }
    int n = 0;
    int k = i2;
    while (n < i)
    {
      paramState = this.mSet[n];
      if (this.mOrientationHelper.getDecoratedMeasurement(paramState) != j)
      {
        localObject = (LayoutParams)paramState.getLayoutParams();
        paramRecycler = ((LayoutParams)localObject).mDecorInsets;
        i1 = paramRecycler.top + paramRecycler.bottom + ((LayoutParams)localObject).topMargin + ((LayoutParams)localObject).bottomMargin;
        i2 = paramRecycler.left + paramRecycler.right + ((LayoutParams)localObject).leftMargin + ((LayoutParams)localObject).rightMargin;
        i3 = getSpaceForSpanRange(((LayoutParams)localObject).mSpanIndex, ((LayoutParams)localObject).mSpanSize);
        if (this.mOrientation == 1)
        {
          i2 = getChildMeasureSpec(i3, 1073741824, i2, ((LayoutParams)localObject).width, false);
          i1 = View.MeasureSpec.makeMeasureSpec(j - i1, 1073741824);
        }
        else
        {
          i2 = View.MeasureSpec.makeMeasureSpec(j - i2, 1073741824);
          i1 = getChildMeasureSpec(i3, 1073741824, i1, ((LayoutParams)localObject).height, false);
        }
        measureChildWithDecorationsAndMargin(paramState, i2, i1, true);
      }
      n++;
    }
    paramLayoutChunkResult.mConsumed = j;
    i1 = 0;
    m = 0;
    k = 0;
    n = 0;
    if (this.mOrientation == 1)
    {
      if (paramLayoutState.mLayoutDirection == -1)
      {
        n = paramLayoutState.mOffset;
        k = n - j;
        j = i1;
      }
      else
      {
        k = paramLayoutState.mOffset;
        n = k + j;
        j = i1;
      }
    }
    else if (paramLayoutState.mLayoutDirection == -1)
    {
      m = paramLayoutState.mOffset;
      j = m - j;
    }
    else
    {
      i1 = paramLayoutState.mOffset;
      m = i1 + j;
      j = i1;
    }
    i2 = 0;
    i1 = i;
    int i = n;
    while (i2 < i1)
    {
      paramState = this.mSet[i2];
      paramRecycler = (LayoutParams)paramState.getLayoutParams();
      if (this.mOrientation == 1)
      {
        if (isLayoutRTL())
        {
          m = getPaddingLeft() + this.mCachedBorders[(this.mSpanCount - paramRecycler.mSpanIndex)];
          n = m - this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
          j = i;
          i = n;
        }
        else
        {
          m = getPaddingLeft() + this.mCachedBorders[paramRecycler.mSpanIndex];
          n = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
          j = m;
          m = n + m;
          n = i;
          i = j;
          j = n;
        }
      }
      else
      {
        i = j;
        j = getPaddingTop() + this.mCachedBorders[paramRecycler.mSpanIndex];
        n = this.mOrientationHelper.getDecoratedMeasurementInOther(paramState);
        k = j;
        j = n + j;
      }
      layoutDecoratedWithMargins(paramState, i, k, m, j);
      if ((!paramRecycler.isItemRemoved()) && (!paramRecycler.isItemChanged())) {
        break label1127;
      }
      paramLayoutChunkResult.mIgnoreConsumed = true;
      label1127:
      paramLayoutChunkResult.mFocusable |= paramState.hasFocusable();
      i2++;
      n = i;
      i = j;
      j = n;
    }
    Arrays.fill(this.mSet, null);
  }
  
  void onAnchorReady(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, LinearLayoutManager.AnchorInfo paramAnchorInfo, int paramInt)
  {
    super.onAnchorReady(paramRecycler, paramState, paramAnchorInfo, paramInt);
    updateMeasurements();
    if ((paramState.getItemCount() > 0) && (!paramState.isPreLayout())) {
      ensureAnchorIsInCorrectSpan(paramRecycler, paramState, paramAnchorInfo, paramInt);
    }
    ensureViewSet();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    View localView1 = findContainingItemView(paramView);
    if (localView1 == null) {
      return null;
    }
    Object localObject = (LayoutParams)localView1.getLayoutParams();
    int i7 = ((LayoutParams)localObject).mSpanIndex;
    int i6 = ((LayoutParams)localObject).mSpanIndex + ((LayoutParams)localObject).mSpanSize;
    if (super.onFocusSearchFailed(paramView, paramInt, paramRecycler, paramState) == null) {
      return null;
    }
    int i11;
    if (convertFocusDirectionToLayoutDirection(paramInt) == 1) {
      i11 = 1;
    } else {
      i11 = 0;
    }
    if (i11 != this.mShouldReverseLayout) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int m;
    int k;
    if (paramInt != 0)
    {
      paramInt = getChildCount() - 1;
      m = -1;
      k = -1;
    }
    else
    {
      paramInt = 0;
      m = 1;
      k = getChildCount();
    }
    int n;
    if ((this.mOrientation == 1) && (isLayoutRTL())) {
      n = 1;
    } else {
      n = 0;
    }
    localObject = null;
    paramView = null;
    int i3 = getSpanGroupIndex(paramRecycler, paramState, paramInt);
    int j = -1;
    int i = 0;
    int i5 = -1;
    int i4 = 0;
    int i2 = paramInt;
    int i1 = paramInt;
    while (i2 != k)
    {
      paramInt = getSpanGroupIndex(paramRecycler, paramState, i2);
      View localView2 = getChildAt(i2);
      if (localView2 == localView1) {
        break;
      }
      if ((localView2.hasFocusable()) && (paramInt != i3))
      {
        if (localObject != null) {
          break;
        }
      }
      else
      {
        LayoutParams localLayoutParams = (LayoutParams)localView2.getLayoutParams();
        int i8 = localLayoutParams.mSpanIndex;
        int i9 = localLayoutParams.mSpanIndex + localLayoutParams.mSpanSize;
        if ((localView2.hasFocusable()) && (i8 == i7) && (i9 == i6)) {
          return localView2;
        }
        if (((localView2.hasFocusable()) && (localObject == null)) || ((!localView2.hasFocusable()) && (paramView == null)))
        {
          paramInt = 1;
        }
        else
        {
          paramInt = Math.max(i8, i7);
          int i10 = Math.min(i9, i6) - paramInt;
          if (localView2.hasFocusable())
          {
            if (i10 > i)
            {
              paramInt = 1;
              break label458;
            }
            if (i10 == i)
            {
              if (i8 > j) {
                paramInt = 1;
              } else {
                paramInt = 0;
              }
              if (n == paramInt)
              {
                paramInt = 1;
                break label458;
              }
            }
          }
          else if (localObject == null)
          {
            paramInt = 0;
            if (isViewPartiallyVisible(localView2, false, true))
            {
              if (i10 > i4)
              {
                paramInt = 1;
                break label458;
              }
              if (i10 == i4)
              {
                if (i8 > i5) {
                  paramInt = 1;
                }
                if (n == paramInt)
                {
                  paramInt = 1;
                  break label458;
                }
              }
            }
          }
          paramInt = 0;
        }
        label458:
        if (paramInt != 0) {
          if (localView2.hasFocusable())
          {
            j = localLayoutParams.mSpanIndex;
            paramInt = Math.min(i9, i6);
            i = Math.max(i8, i7);
            localObject = localView2;
            i = paramInt - i;
          }
          else
          {
            i5 = localLayoutParams.mSpanIndex;
            paramInt = Math.min(i9, i6);
            i4 = Math.max(i8, i7);
            paramView = localView2;
            i4 = paramInt - i4;
          }
        }
      }
      i2 += m;
    }
    if (localObject == null) {
      localObject = paramView;
    }
    return (View)localObject;
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (!(localLayoutParams instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    paramView = (LayoutParams)localLayoutParams;
    int i = getSpanGroupIndex(paramRecycler, paramState, paramView.getViewLayoutPosition());
    if (this.mOrientation == 0) {
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(paramView.getSpanIndex(), paramView.getSpanSize(), i, 1, false, false));
    } else {
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(i, 1, paramView.getSpanIndex(), paramView.getSpanSize(), false, false));
    }
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
    this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
    this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
    this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
    this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
  {
    this.mSpanSizeLookup.invalidateSpanIndexCache();
    this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (paramState.isPreLayout()) {
      cachePreLayoutSpanMapping();
    }
    super.onLayoutChildren(paramRecycler, paramState);
    clearPreLayoutSpanMappingCache();
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState)
  {
    super.onLayoutCompleted(paramState);
    this.mPendingSpanCountChange = false;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollHorizontallyBy(paramInt, paramRecycler, paramState);
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    updateMeasurements();
    ensureViewSet();
    return super.scrollVerticallyBy(paramInt, paramRecycler, paramState);
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
  {
    if (this.mCachedBorders == null) {
      super.setMeasuredDimension(paramRect, paramInt1, paramInt2);
    }
    int j = getPaddingLeft() + getPaddingRight();
    int i = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1)
    {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + i, getMinimumHeight());
      paramRect = this.mCachedBorders;
      paramInt1 = chooseSize(paramInt1, paramRect[(paramRect.length - 1)] + j, getMinimumWidth());
    }
    else
    {
      paramInt1 = chooseSize(paramInt1, paramRect.width() + j, getMinimumWidth());
      paramRect = this.mCachedBorders;
      paramInt2 = chooseSize(paramInt2, paramRect[(paramRect.length - 1)] + i, getMinimumHeight());
    }
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  public void setSpanCount(int paramInt)
  {
    if (paramInt == this.mSpanCount) {
      return;
    }
    this.mPendingSpanCountChange = true;
    if (paramInt >= 1)
    {
      this.mSpanCount = paramInt;
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      requestLayout();
      return;
    }
    throw new IllegalArgumentException("Span count should be at least 1. Provided " + paramInt);
  }
  
  public void setSpanSizeLookup(SpanSizeLookup paramSpanSizeLookup)
  {
    this.mSpanSizeLookup = paramSpanSizeLookup;
  }
  
  public void setStackFromEnd(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      super.setStackFromEnd(false);
      return;
    }
    throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
  }
  
  public void setUsingSpansToEstimateScrollbarDimensions(boolean paramBoolean)
  {
    this.mUsingSpansToEstimateScrollBarDimensions = paramBoolean;
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    boolean bool;
    if ((this.mPendingSavedState == null) && (!this.mPendingSpanCountChange)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final class DefaultSpanSizeLookup
    extends GridLayoutManager.SpanSizeLookup
  {
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      return paramInt1 % paramInt2;
    }
    
    public int getSpanSize(int paramInt)
    {
      return 1;
    }
  }
  
  public static class LayoutParams
    extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    int mSpanIndex = -1;
    int mSpanSize = 0;
    
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
    
    public LayoutParams(RecyclerView.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public int getSpanIndex()
    {
      return this.mSpanIndex;
    }
    
    public int getSpanSize()
    {
      return this.mSpanSize;
    }
  }
  
  public static abstract class SpanSizeLookup
  {
    private boolean mCacheSpanGroupIndices = false;
    private boolean mCacheSpanIndices = false;
    final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
    final SparseIntArray mSpanIndexCache = new SparseIntArray();
    
    static int findFirstKeyLessThan(SparseIntArray paramSparseIntArray, int paramInt)
    {
      int j = 0;
      int i = paramSparseIntArray.size() - 1;
      while (j <= i)
      {
        int k = j + i >>> 1;
        if (paramSparseIntArray.keyAt(k) < paramInt) {
          j = k + 1;
        } else {
          i = k - 1;
        }
      }
      paramInt = j - 1;
      if ((paramInt >= 0) && (paramInt < paramSparseIntArray.size())) {
        return paramSparseIntArray.keyAt(paramInt);
      }
      return -1;
    }
    
    int getCachedSpanGroupIndex(int paramInt1, int paramInt2)
    {
      if (!this.mCacheSpanGroupIndices) {
        return getSpanGroupIndex(paramInt1, paramInt2);
      }
      int i = this.mSpanGroupIndexCache.get(paramInt1, -1);
      if (i != -1) {
        return i;
      }
      paramInt2 = getSpanGroupIndex(paramInt1, paramInt2);
      this.mSpanGroupIndexCache.put(paramInt1, paramInt2);
      return paramInt2;
    }
    
    int getCachedSpanIndex(int paramInt1, int paramInt2)
    {
      if (!this.mCacheSpanIndices) {
        return getSpanIndex(paramInt1, paramInt2);
      }
      int i = this.mSpanIndexCache.get(paramInt1, -1);
      if (i != -1) {
        return i;
      }
      paramInt2 = getSpanIndex(paramInt1, paramInt2);
      this.mSpanIndexCache.put(paramInt1, paramInt2);
      return paramInt2;
    }
    
    public int getSpanGroupIndex(int paramInt1, int paramInt2)
    {
      int i1 = 0;
      int m = 0;
      int n = 0;
      int i = i1;
      int j = m;
      int k = n;
      if (this.mCacheSpanGroupIndices)
      {
        i2 = findFirstKeyLessThan(this.mSpanGroupIndexCache, paramInt1);
        i = i1;
        j = m;
        k = n;
        if (i2 != -1)
        {
          n = this.mSpanGroupIndexCache.get(i2);
          m = i2 + 1;
          i1 = getCachedSpanIndex(i2, paramInt2) + getSpanSize(i2);
          i = i1;
          j = n;
          k = m;
          if (i1 == paramInt2)
          {
            i = 0;
            j = n + 1;
            k = m;
          }
        }
      }
      int i2 = getSpanSize(paramInt1);
      m = k;
      while (m < paramInt1)
      {
        n = getSpanSize(m);
        i1 = i + n;
        if (i1 == paramInt2)
        {
          i = 0;
          k = j + 1;
        }
        else
        {
          i = i1;
          k = j;
          if (i1 > paramInt2)
          {
            i = n;
            k = j + 1;
          }
        }
        m++;
        j = k;
      }
      paramInt1 = j;
      if (i + i2 > paramInt2) {
        paramInt1 = j + 1;
      }
      return paramInt1;
    }
    
    public int getSpanIndex(int paramInt1, int paramInt2)
    {
      int n = getSpanSize(paramInt1);
      if (n == paramInt2) {
        return 0;
      }
      int k = 0;
      int m = 0;
      int i = k;
      int j = m;
      int i1;
      if (this.mCacheSpanIndices)
      {
        i1 = findFirstKeyLessThan(this.mSpanIndexCache, paramInt1);
        i = k;
        j = m;
        if (i1 >= 0) {
          i = this.mSpanIndexCache.get(i1) + getSpanSize(i1);
        }
      }
      for (j = i1 + 1; j < paramInt1; j++)
      {
        k = getSpanSize(j);
        m = i + k;
        if (m == paramInt2)
        {
          i = 0;
        }
        else
        {
          i = m;
          if (m > paramInt2) {
            i = k;
          }
        }
      }
      if (i + n <= paramInt2) {
        return i;
      }
      return 0;
    }
    
    public abstract int getSpanSize(int paramInt);
    
    public void invalidateSpanGroupIndexCache()
    {
      this.mSpanGroupIndexCache.clear();
    }
    
    public void invalidateSpanIndexCache()
    {
      this.mSpanIndexCache.clear();
    }
    
    public boolean isSpanGroupIndexCacheEnabled()
    {
      return this.mCacheSpanGroupIndices;
    }
    
    public boolean isSpanIndexCacheEnabled()
    {
      return this.mCacheSpanIndices;
    }
    
    public void setSpanGroupIndexCacheEnabled(boolean paramBoolean)
    {
      if (!paramBoolean) {
        this.mSpanGroupIndexCache.clear();
      }
      this.mCacheSpanGroupIndices = paramBoolean;
    }
    
    public void setSpanIndexCacheEnabled(boolean paramBoolean)
    {
      if (!paramBoolean) {
        this.mSpanGroupIndexCache.clear();
      }
      this.mCacheSpanIndices = paramBoolean;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/GridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */