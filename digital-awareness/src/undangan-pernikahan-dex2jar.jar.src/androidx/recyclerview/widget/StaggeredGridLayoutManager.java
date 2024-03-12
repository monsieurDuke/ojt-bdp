package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class StaggeredGridLayoutManager
  extends RecyclerView.LayoutManager
  implements RecyclerView.SmoothScroller.ScrollVectorProvider
{
  static final boolean DEBUG = false;
  @Deprecated
  public static final int GAP_HANDLING_LAZY = 1;
  public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
  public static final int GAP_HANDLING_NONE = 0;
  public static final int HORIZONTAL = 0;
  static final int INVALID_OFFSET = Integer.MIN_VALUE;
  private static final float MAX_SCROLL_FACTOR = 0.33333334F;
  private static final String TAG = "StaggeredGridLManager";
  public static final int VERTICAL = 1;
  private final AnchorInfo mAnchorInfo = new AnchorInfo();
  private final Runnable mCheckForGapsRunnable = new Runnable()
  {
    public void run()
    {
      StaggeredGridLayoutManager.this.checkForGaps();
    }
  };
  private int mFullSizeSpec;
  private int mGapStrategy = 2;
  private boolean mLaidOutInvalidFullSpan = false;
  private boolean mLastLayoutFromEnd;
  private boolean mLastLayoutRTL;
  private final LayoutState mLayoutState;
  LazySpanLookup mLazySpanLookup = new LazySpanLookup();
  private int mOrientation;
  private SavedState mPendingSavedState;
  int mPendingScrollPosition = -1;
  int mPendingScrollPositionOffset = Integer.MIN_VALUE;
  private int[] mPrefetchDistances;
  OrientationHelper mPrimaryOrientation;
  private BitSet mRemainingSpans;
  boolean mReverseLayout = false;
  OrientationHelper mSecondaryOrientation;
  boolean mShouldReverseLayout = false;
  private int mSizePerSpan;
  private boolean mSmoothScrollbarEnabled = true;
  private int mSpanCount = -1;
  Span[] mSpans;
  private final Rect mTmpRect = new Rect();
  
  public StaggeredGridLayoutManager(int paramInt1, int paramInt2)
  {
    this.mOrientation = paramInt2;
    setSpanCount(paramInt1);
    this.mLayoutState = new LayoutState();
    createOrientationHelpers();
  }
  
  public StaggeredGridLayoutManager(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    paramContext = getProperties(paramContext, paramAttributeSet, paramInt1, paramInt2);
    setOrientation(paramContext.orientation);
    setSpanCount(paramContext.spanCount);
    setReverseLayout(paramContext.reverseLayout);
    this.mLayoutState = new LayoutState();
    createOrientationHelpers();
  }
  
  private void appendViewToAllSpans(View paramView)
  {
    for (int i = this.mSpanCount - 1; i >= 0; i--) {
      this.mSpans[i].appendToSpan(paramView);
    }
  }
  
  private void applyPendingSavedState(AnchorInfo paramAnchorInfo)
  {
    if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
      if (this.mPendingSavedState.mSpanOffsetsSize == this.mSpanCount)
      {
        for (int j = 0; j < this.mSpanCount; j++)
        {
          this.mSpans[j].clear();
          int k = this.mPendingSavedState.mSpanOffsets[j];
          int i = k;
          if (k != Integer.MIN_VALUE) {
            if (this.mPendingSavedState.mAnchorLayoutFromEnd) {
              i = k + this.mPrimaryOrientation.getEndAfterPadding();
            } else {
              i = k + this.mPrimaryOrientation.getStartAfterPadding();
            }
          }
          this.mSpans[j].setLine(i);
        }
      }
      else
      {
        this.mPendingSavedState.invalidateSpanInfo();
        SavedState localSavedState = this.mPendingSavedState;
        localSavedState.mAnchorPosition = localSavedState.mVisibleAnchorPosition;
      }
    }
    this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
    setReverseLayout(this.mPendingSavedState.mReverseLayout);
    resolveShouldLayoutReverse();
    if (this.mPendingSavedState.mAnchorPosition != -1)
    {
      this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
      paramAnchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
    }
    else
    {
      paramAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
    }
    if (this.mPendingSavedState.mSpanLookupSize > 1)
    {
      this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
      this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
    }
  }
  
  private void attachViewToSpans(View paramView, LayoutParams paramLayoutParams, LayoutState paramLayoutState)
  {
    if (paramLayoutState.mLayoutDirection == 1)
    {
      if (paramLayoutParams.mFullSpan) {
        appendViewToAllSpans(paramView);
      } else {
        paramLayoutParams.mSpan.appendToSpan(paramView);
      }
    }
    else if (paramLayoutParams.mFullSpan) {
      prependViewToAllSpans(paramView);
    } else {
      paramLayoutParams.mSpan.prependToSpan(paramView);
    }
  }
  
  private int calculateScrollDirectionForPosition(int paramInt)
  {
    int j = getChildCount();
    int i = -1;
    if (j == 0)
    {
      if (this.mShouldReverseLayout) {
        i = 1;
      }
      return i;
    }
    int k;
    if (paramInt < getFirstChildPosition()) {
      k = 1;
    } else {
      k = 0;
    }
    if (k == this.mShouldReverseLayout) {
      i = 1;
    }
    return i;
  }
  
  private boolean checkSpanForGap(Span paramSpan)
  {
    if (this.mShouldReverseLayout)
    {
      if (paramSpan.getEndLine() < this.mPrimaryOrientation.getEndAfterPadding()) {
        return paramSpan.getLayoutParams((View)paramSpan.mViews.get(paramSpan.mViews.size() - 1)).mFullSpan ^ true;
      }
    }
    else if (paramSpan.getStartLine() > this.mPrimaryOrientation.getStartAfterPadding()) {
      return paramSpan.getLayoutParams((View)paramSpan.mViews.get(0)).mFullSpan ^ true;
    }
    return false;
  }
  
  private int computeScrollExtent(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ScrollbarHelper.computeScrollExtent(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int computeScrollOffset(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ScrollbarHelper.computeScrollOffset(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
  }
  
  private int computeScrollRange(RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    return ScrollbarHelper.computeScrollRange(paramState, this.mPrimaryOrientation, findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
  }
  
  private int convertFocusDirectionToLayoutDirection(int paramInt)
  {
    int i = -1;
    int j = Integer.MIN_VALUE;
    switch (paramInt)
    {
    default: 
      return Integer.MIN_VALUE;
    case 130: 
      if (this.mOrientation == 1) {
        j = 1;
      }
      return j;
    case 66: 
      if (this.mOrientation == 0) {
        j = 1;
      }
      return j;
    case 33: 
      if (this.mOrientation != 1) {
        i = Integer.MIN_VALUE;
      }
      return i;
    case 17: 
      if (this.mOrientation != 0) {
        i = Integer.MIN_VALUE;
      }
      return i;
    case 2: 
      if (this.mOrientation == 1) {
        return 1;
      }
      if (isLayoutRTL()) {
        return -1;
      }
      return 1;
    }
    if (this.mOrientation == 1) {
      return -1;
    }
    if (isLayoutRTL()) {
      return 1;
    }
    return -1;
  }
  
  private StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int paramInt)
  {
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem();
    localFullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++) {
      localFullSpanItem.mGapPerSpan[i] = (paramInt - this.mSpans[i].getEndLine(paramInt));
    }
    return localFullSpanItem;
  }
  
  private StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int paramInt)
  {
    StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem = new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem();
    localFullSpanItem.mGapPerSpan = new int[this.mSpanCount];
    for (int i = 0; i < this.mSpanCount; i++) {
      localFullSpanItem.mGapPerSpan[i] = (this.mSpans[i].getStartLine(paramInt) - paramInt);
    }
    return localFullSpanItem;
  }
  
  private void createOrientationHelpers()
  {
    this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
    this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
  }
  
  private int fill(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState, RecyclerView.State paramState)
  {
    Object localObject = this.mRemainingSpans;
    int i = this.mSpanCount;
    ((BitSet)localObject).set(0, i, true);
    if (this.mLayoutState.mInfinite)
    {
      if (paramLayoutState.mLayoutDirection == 1) {
        i = Integer.MAX_VALUE;
      } else {
        i = Integer.MIN_VALUE;
      }
    }
    else if (paramLayoutState.mLayoutDirection == 1) {
      i = paramLayoutState.mEndLine + paramLayoutState.mAvailable;
    } else {
      i = paramLayoutState.mStartLine - paramLayoutState.mAvailable;
    }
    updateAllRemainingSpans(paramLayoutState.mLayoutDirection, i);
    int m;
    if (this.mShouldReverseLayout) {
      m = this.mPrimaryOrientation.getEndAfterPadding();
    } else {
      m = this.mPrimaryOrientation.getStartAfterPadding();
    }
    int k;
    for (int j = 0; paramLayoutState.hasMore(paramState); k = 1)
    {
      if ((!this.mLayoutState.mInfinite) && (this.mRemainingSpans.isEmpty())) {
        break;
      }
      View localView = paramLayoutState.next(paramRecycler);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      int i4 = localLayoutParams.getViewLayoutPosition();
      j = this.mLazySpanLookup.getSpan(i4);
      int i2;
      if (j == -1) {
        i2 = 1;
      } else {
        i2 = 0;
      }
      if (i2 != 0)
      {
        if (localLayoutParams.mFullSpan) {
          localObject = this.mSpans[0];
        } else {
          localObject = getNextSpan(paramLayoutState);
        }
        this.mLazySpanLookup.setSpan(i4, (Span)localObject);
      }
      else
      {
        localObject = this.mSpans[j];
      }
      localLayoutParams.mSpan = ((Span)localObject);
      if (paramLayoutState.mLayoutDirection == 1) {
        addView(localView);
      } else {
        addView(localView, 0);
      }
      measureChildWithDecorationsAndMargin(localView, localLayoutParams, false);
      int i3;
      int i1;
      int n;
      StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem;
      if (paramLayoutState.mLayoutDirection == 1)
      {
        if (localLayoutParams.mFullSpan) {
          j = getMaxEnd(m);
        } else {
          j = ((Span)localObject).getEndLine(m);
        }
        i3 = this.mPrimaryOrientation.getDecoratedMeasurement(localView) + j;
        i1 = j;
        n = i3;
        if (i2 != 0)
        {
          i1 = j;
          n = i3;
          if (localLayoutParams.mFullSpan)
          {
            localFullSpanItem = createFullSpanItemFromEnd(j);
            localFullSpanItem.mGapDir = -1;
            localFullSpanItem.mPosition = i4;
            this.mLazySpanLookup.addFullSpanItem(localFullSpanItem);
            i1 = j;
            n = i3;
          }
        }
      }
      else
      {
        if (localLayoutParams.mFullSpan) {
          j = getMinStart(m);
        } else {
          j = ((Span)localObject).getStartLine(m);
        }
        i3 = j - this.mPrimaryOrientation.getDecoratedMeasurement(localView);
        i1 = i3;
        n = j;
        if (i2 != 0)
        {
          i1 = i3;
          n = j;
          if (localLayoutParams.mFullSpan)
          {
            localFullSpanItem = createFullSpanItemFromStart(j);
            localFullSpanItem.mGapDir = 1;
            localFullSpanItem.mPosition = i4;
            this.mLazySpanLookup.addFullSpanItem(localFullSpanItem);
            n = j;
            i1 = i3;
          }
        }
      }
      if ((localLayoutParams.mFullSpan) && (paramLayoutState.mItemDirection == -1)) {
        if (i2 != 0)
        {
          this.mLaidOutInvalidFullSpan = true;
        }
        else
        {
          boolean bool;
          if (paramLayoutState.mLayoutDirection == 1) {
            bool = areAllEndsEqual() ^ true;
          } else {
            bool = areAllStartsEqual() ^ true;
          }
          if (bool)
          {
            localFullSpanItem = this.mLazySpanLookup.getFullSpanItem(i4);
            if (localFullSpanItem != null) {
              localFullSpanItem.mHasUnwantedGapAfter = true;
            }
            this.mLaidOutInvalidFullSpan = true;
          }
        }
      }
      attachViewToSpans(localView, localLayoutParams, paramLayoutState);
      if ((isLayoutRTL()) && (this.mOrientation == 1))
      {
        if (localLayoutParams.mFullSpan) {
          k = this.mSecondaryOrientation.getEndAfterPadding();
        } else {
          k = this.mSecondaryOrientation.getEndAfterPadding() - (this.mSpanCount - 1 - ((Span)localObject).mIndex) * this.mSizePerSpan;
        }
        i3 = this.mSecondaryOrientation.getDecoratedMeasurement(localView);
        i2 = k;
        k -= i3;
      }
      else
      {
        if (localLayoutParams.mFullSpan) {
          k = this.mSecondaryOrientation.getStartAfterPadding();
        } else {
          k = ((Span)localObject).mIndex * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
        }
        i2 = this.mSecondaryOrientation.getDecoratedMeasurement(localView) + k;
      }
      if (this.mOrientation == 1) {
        layoutDecoratedWithMargins(localView, k, i1, i2, n);
      } else {
        layoutDecoratedWithMargins(localView, i1, k, n, i2);
      }
      if (localLayoutParams.mFullSpan) {
        updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, i);
      } else {
        updateRemainingSpans((Span)localObject, this.mLayoutState.mLayoutDirection, i);
      }
      recycle(paramRecycler, this.mLayoutState);
      if ((this.mLayoutState.mStopInFocusable) && (localView.hasFocusable())) {
        if (localLayoutParams.mFullSpan) {
          this.mRemainingSpans.clear();
        } else {
          this.mRemainingSpans.set(((Span)localObject).mIndex, false);
        }
      }
    }
    if (k == 0) {
      recycle(paramRecycler, this.mLayoutState);
    }
    if (this.mLayoutState.mLayoutDirection == -1)
    {
      i = getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
      i = this.mPrimaryOrientation.getStartAfterPadding() - i;
    }
    else
    {
      i = getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
    }
    if (i > 0) {
      i = Math.min(paramLayoutState.mAvailable, i);
    } else {
      i = 0;
    }
    return i;
  }
  
  private int findFirstReferenceChildPosition(int paramInt)
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      int k = getPosition(getChildAt(i));
      if ((k >= 0) && (k < paramInt)) {
        return k;
      }
    }
    return 0;
  }
  
  private int findLastReferenceChildPosition(int paramInt)
  {
    for (int i = getChildCount() - 1; i >= 0; i--)
    {
      int j = getPosition(getChildAt(i));
      if ((j >= 0) && (j < paramInt)) {
        return j;
      }
    }
    return 0;
  }
  
  private void fixEndGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMaxEnd(Integer.MIN_VALUE);
    if (i == Integer.MIN_VALUE) {
      return;
    }
    i = this.mPrimaryOrientation.getEndAfterPadding() - i;
    if (i > 0)
    {
      i -= -scrollBy(-i, paramRecycler, paramState);
      if ((paramBoolean) && (i > 0)) {
        this.mPrimaryOrientation.offsetChildren(i);
      }
      return;
    }
  }
  
  private void fixStartGap(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    int i = getMinStart(Integer.MAX_VALUE);
    if (i == Integer.MAX_VALUE) {
      return;
    }
    i -= this.mPrimaryOrientation.getStartAfterPadding();
    if (i > 0)
    {
      i -= scrollBy(i, paramRecycler, paramState);
      if ((paramBoolean) && (i > 0)) {
        this.mPrimaryOrientation.offsetChildren(-i);
      }
      return;
    }
  }
  
  private int getMaxEnd(int paramInt)
  {
    int k = this.mSpans[0].getEndLine(paramInt);
    int i = 1;
    while (i < this.mSpanCount)
    {
      int m = this.mSpans[i].getEndLine(paramInt);
      int j = k;
      if (m > k) {
        j = m;
      }
      i++;
      k = j;
    }
    return k;
  }
  
  private int getMaxStart(int paramInt)
  {
    int j = this.mSpans[0].getStartLine(paramInt);
    int i = 1;
    while (i < this.mSpanCount)
    {
      int m = this.mSpans[i].getStartLine(paramInt);
      int k = j;
      if (m > j) {
        k = m;
      }
      i++;
      j = k;
    }
    return j;
  }
  
  private int getMinEnd(int paramInt)
  {
    int k = this.mSpans[0].getEndLine(paramInt);
    int i = 1;
    while (i < this.mSpanCount)
    {
      int m = this.mSpans[i].getEndLine(paramInt);
      int j = k;
      if (m < k) {
        j = m;
      }
      i++;
      k = j;
    }
    return k;
  }
  
  private int getMinStart(int paramInt)
  {
    int k = this.mSpans[0].getStartLine(paramInt);
    int i = 1;
    while (i < this.mSpanCount)
    {
      int m = this.mSpans[i].getStartLine(paramInt);
      int j = k;
      if (m < k) {
        j = m;
      }
      i++;
      k = j;
    }
    return k;
  }
  
  private Span getNextSpan(LayoutState paramLayoutState)
  {
    int i;
    int k;
    int j;
    if (preferLastSpan(paramLayoutState.mLayoutDirection))
    {
      i = this.mSpanCount - 1;
      k = -1;
      j = -1;
    }
    else
    {
      i = 0;
      k = this.mSpanCount;
      j = 1;
    }
    Span localSpan;
    int i1;
    int m;
    if (paramLayoutState.mLayoutDirection == 1)
    {
      paramLayoutState = null;
      n = Integer.MAX_VALUE;
      i2 = this.mPrimaryOrientation.getStartAfterPadding();
      while (i != k)
      {
        localSpan = this.mSpans[i];
        i1 = localSpan.getEndLine(i2);
        m = n;
        if (i1 < n)
        {
          paramLayoutState = localSpan;
          m = i1;
        }
        i += j;
        n = m;
      }
      return paramLayoutState;
    }
    paramLayoutState = null;
    int n = Integer.MIN_VALUE;
    int i2 = this.mPrimaryOrientation.getEndAfterPadding();
    while (i != k)
    {
      localSpan = this.mSpans[i];
      i1 = localSpan.getStartLine(i2);
      m = n;
      if (i1 > n)
      {
        paramLayoutState = localSpan;
        m = i1;
      }
      i += j;
      n = m;
    }
    return paramLayoutState;
  }
  
  private void handleUpdate(int paramInt1, int paramInt2, int paramInt3)
  {
    int k;
    if (this.mShouldReverseLayout) {
      k = getLastChildPosition();
    } else {
      k = getFirstChildPosition();
    }
    int j;
    int i;
    if (paramInt3 == 8)
    {
      if (paramInt1 < paramInt2)
      {
        j = paramInt2 + 1;
        i = paramInt1;
      }
      else
      {
        j = paramInt1 + 1;
        i = paramInt2;
      }
    }
    else
    {
      i = paramInt1;
      j = paramInt1 + paramInt2;
    }
    this.mLazySpanLookup.invalidateAfter(i);
    switch (paramInt3)
    {
    default: 
      break;
    case 8: 
      this.mLazySpanLookup.offsetForRemoval(paramInt1, 1);
      this.mLazySpanLookup.offsetForAddition(paramInt2, 1);
      break;
    case 2: 
      this.mLazySpanLookup.offsetForRemoval(paramInt1, paramInt2);
      break;
    case 1: 
      this.mLazySpanLookup.offsetForAddition(paramInt1, paramInt2);
    }
    if (j <= k) {
      return;
    }
    if (this.mShouldReverseLayout) {
      paramInt1 = getFirstChildPosition();
    } else {
      paramInt1 = getLastChildPosition();
    }
    if (i <= paramInt1) {
      requestLayout();
    }
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    calculateItemDecorationsForChild(paramView, this.mTmpRect);
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    paramInt1 = updateSpecWithExtra(paramInt1, localLayoutParams.leftMargin + this.mTmpRect.left, localLayoutParams.rightMargin + this.mTmpRect.right);
    paramInt2 = updateSpecWithExtra(paramInt2, localLayoutParams.topMargin + this.mTmpRect.top, localLayoutParams.bottomMargin + this.mTmpRect.bottom);
    if (paramBoolean) {
      paramBoolean = shouldReMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    } else {
      paramBoolean = shouldMeasureChild(paramView, paramInt1, paramInt2, localLayoutParams);
    }
    if (paramBoolean) {
      paramView.measure(paramInt1, paramInt2);
    }
  }
  
  private void measureChildWithDecorationsAndMargin(View paramView, LayoutParams paramLayoutParams, boolean paramBoolean)
  {
    if (paramLayoutParams.mFullSpan)
    {
      if (this.mOrientation == 1) {
        measureChildWithDecorationsAndMargin(paramView, this.mFullSizeSpec, getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), paramLayoutParams.height, true), paramBoolean);
      } else {
        measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), paramLayoutParams.width, true), this.mFullSizeSpec, paramBoolean);
      }
    }
    else if (this.mOrientation == 1) {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, paramLayoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), paramLayoutParams.height, true), paramBoolean);
    } else {
      measureChildWithDecorationsAndMargin(paramView, getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), paramLayoutParams.width, true), getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, paramLayoutParams.height, false), paramBoolean);
    }
  }
  
  private void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, boolean paramBoolean)
  {
    AnchorInfo localAnchorInfo = this.mAnchorInfo;
    if (((this.mPendingSavedState != null) || (this.mPendingScrollPosition != -1)) && (paramState.getItemCount() == 0))
    {
      removeAndRecycleAllViews(paramRecycler);
      localAnchorInfo.reset();
      return;
    }
    boolean bool = localAnchorInfo.mValid;
    int j = 1;
    if ((bool) && (this.mPendingScrollPosition == -1) && (this.mPendingSavedState == null)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      localAnchorInfo.reset();
      if (this.mPendingSavedState != null)
      {
        applyPendingSavedState(localAnchorInfo);
      }
      else
      {
        resolveShouldLayoutReverse();
        localAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
      }
      updateAnchorInfoForLayout(paramState, localAnchorInfo);
      localAnchorInfo.mValid = true;
    }
    if ((this.mPendingSavedState == null) && (this.mPendingScrollPosition == -1) && ((localAnchorInfo.mLayoutFromEnd != this.mLastLayoutFromEnd) || (isLayoutRTL() != this.mLastLayoutRTL)))
    {
      this.mLazySpanLookup.clear();
      localAnchorInfo.mInvalidateOffsets = true;
    }
    if (getChildCount() > 0)
    {
      Object localObject = this.mPendingSavedState;
      if ((localObject == null) || (((SavedState)localObject).mSpanOffsetsSize < 1)) {
        if (localAnchorInfo.mInvalidateOffsets)
        {
          for (i = 0; i < this.mSpanCount; i++)
          {
            this.mSpans[i].clear();
            if (localAnchorInfo.mOffset != Integer.MIN_VALUE) {
              this.mSpans[i].setLine(localAnchorInfo.mOffset);
            }
          }
        }
        else
        {
          if ((i == 0) && (this.mAnchorInfo.mSpanReferenceLines != null)) {
            i = 0;
          }
          while (i < this.mSpanCount)
          {
            localObject = this.mSpans[i];
            ((Span)localObject).clear();
            ((Span)localObject).setLine(this.mAnchorInfo.mSpanReferenceLines[i]);
            i++;
            continue;
            for (i = 0; i < this.mSpanCount; i++) {
              this.mSpans[i].cacheReferenceLineAndClear(this.mShouldReverseLayout, localAnchorInfo.mOffset);
            }
            this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
          }
        }
      }
    }
    detachAndScrapAttachedViews(paramRecycler);
    this.mLayoutState.mRecycle = false;
    this.mLaidOutInvalidFullSpan = false;
    updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
    updateLayoutState(localAnchorInfo.mPosition, paramState);
    if (localAnchorInfo.mLayoutFromEnd)
    {
      setLayoutStateDirection(-1);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(1);
      this.mLayoutState.mCurrentPosition = (localAnchorInfo.mPosition + this.mLayoutState.mItemDirection);
      fill(paramRecycler, this.mLayoutState, paramState);
    }
    else
    {
      setLayoutStateDirection(1);
      fill(paramRecycler, this.mLayoutState, paramState);
      setLayoutStateDirection(-1);
      this.mLayoutState.mCurrentPosition = (localAnchorInfo.mPosition + this.mLayoutState.mItemDirection);
      fill(paramRecycler, this.mLayoutState, paramState);
    }
    repositionToWrapContentIfNecessary();
    if (getChildCount() > 0) {
      if (this.mShouldReverseLayout)
      {
        fixEndGap(paramRecycler, paramState, true);
        fixStartGap(paramRecycler, paramState, false);
      }
      else
      {
        fixStartGap(paramRecycler, paramState, true);
        fixEndGap(paramRecycler, paramState, false);
      }
    }
    int k = 0;
    int i = k;
    if (paramBoolean)
    {
      i = k;
      if (!paramState.isPreLayout())
      {
        if ((this.mGapStrategy == 0) || (getChildCount() <= 0) || ((this.mLaidOutInvalidFullSpan) || (hasGapsToFix() == null))) {
          j = 0;
        }
        i = k;
        if (j != 0)
        {
          removeCallbacks(this.mCheckForGapsRunnable);
          i = k;
          if (checkForGaps()) {
            i = 1;
          }
        }
      }
    }
    if (paramState.isPreLayout()) {
      this.mAnchorInfo.reset();
    }
    this.mLastLayoutFromEnd = localAnchorInfo.mLayoutFromEnd;
    this.mLastLayoutRTL = isLayoutRTL();
    if (i != 0)
    {
      this.mAnchorInfo.reset();
      onLayoutChildren(paramRecycler, paramState, false);
    }
  }
  
  private boolean preferLastSpan(int paramInt)
  {
    int i = this.mOrientation;
    boolean bool2 = true;
    boolean bool3 = true;
    boolean bool1;
    if (i == 0)
    {
      if (paramInt == -1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if (bool1 != this.mShouldReverseLayout) {
        bool1 = bool3;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    if (paramInt == -1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (bool1 == this.mShouldReverseLayout) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (bool1 == isLayoutRTL()) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    return bool1;
  }
  
  private void prependViewToAllSpans(View paramView)
  {
    for (int i = this.mSpanCount - 1; i >= 0; i--) {
      this.mSpans[i].prependToSpan(paramView);
    }
  }
  
  private void recycle(RecyclerView.Recycler paramRecycler, LayoutState paramLayoutState)
  {
    if ((paramLayoutState.mRecycle) && (!paramLayoutState.mInfinite))
    {
      if (paramLayoutState.mAvailable == 0)
      {
        if (paramLayoutState.mLayoutDirection == -1) {
          recycleFromEnd(paramRecycler, paramLayoutState.mEndLine);
        } else {
          recycleFromStart(paramRecycler, paramLayoutState.mStartLine);
        }
      }
      else
      {
        int i;
        if (paramLayoutState.mLayoutDirection == -1)
        {
          i = paramLayoutState.mStartLine - getMaxStart(paramLayoutState.mStartLine);
          if (i < 0) {
            i = paramLayoutState.mEndLine;
          } else {
            i = paramLayoutState.mEndLine - Math.min(i, paramLayoutState.mAvailable);
          }
          recycleFromEnd(paramRecycler, i);
        }
        else
        {
          i = getMinEnd(paramLayoutState.mEndLine) - paramLayoutState.mEndLine;
          if (i < 0) {
            i = paramLayoutState.mStartLine;
          } else {
            i = paramLayoutState.mStartLine + Math.min(i, paramLayoutState.mAvailable);
          }
          recycleFromStart(paramRecycler, i);
        }
      }
      return;
    }
  }
  
  private void recycleFromEnd(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      View localView = getChildAt(i);
      if ((this.mPrimaryOrientation.getDecoratedStart(localView) >= paramInt) && (this.mPrimaryOrientation.getTransformedStartWithDecoration(localView) >= paramInt))
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.mFullSpan)
        {
          for (int j = 0; j < this.mSpanCount; j++) {
            if (this.mSpans[j].mViews.size() == 1) {
              return;
            }
          }
          for (j = 0; j < this.mSpanCount; j++) {
            this.mSpans[j].popEnd();
          }
        }
        else
        {
          if (localLayoutParams.mSpan.mViews.size() == 1) {
            return;
          }
          localLayoutParams.mSpan.popEnd();
        }
        removeAndRecycleView(localView, paramRecycler);
        i--;
      }
      else {}
    }
  }
  
  private void recycleFromStart(RecyclerView.Recycler paramRecycler, int paramInt)
  {
    while (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      if ((this.mPrimaryOrientation.getDecoratedEnd(localView) <= paramInt) && (this.mPrimaryOrientation.getTransformedEndWithDecoration(localView) <= paramInt))
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.mFullSpan)
        {
          for (int i = 0; i < this.mSpanCount; i++) {
            if (this.mSpans[i].mViews.size() == 1) {
              return;
            }
          }
          for (i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].popStart();
          }
        }
        else
        {
          if (localLayoutParams.mSpan.mViews.size() == 1) {
            return;
          }
          localLayoutParams.mSpan.popStart();
        }
        removeAndRecycleView(localView, paramRecycler);
      }
      else {}
    }
  }
  
  private void repositionToWrapContentIfNecessary()
  {
    if (this.mSecondaryOrientation.getMode() == 1073741824) {
      return;
    }
    float f1 = 0.0F;
    int k = getChildCount();
    View localView;
    for (int i = 0; i < k; i++)
    {
      localView = getChildAt(i);
      float f3 = this.mSecondaryOrientation.getDecoratedMeasurement(localView);
      if (f3 >= f1)
      {
        float f2 = f3;
        if (((LayoutParams)localView.getLayoutParams()).isFullSpan()) {
          f2 = 1.0F * f3 / this.mSpanCount;
        }
        f1 = Math.max(f1, f2);
      }
    }
    int m = this.mSizePerSpan;
    int j = Math.round(this.mSpanCount * f1);
    i = j;
    if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
      i = Math.min(j, this.mSecondaryOrientation.getTotalSpace());
    }
    updateMeasureSpecs(i);
    if (this.mSizePerSpan == m) {
      return;
    }
    for (i = 0; i < k; i++)
    {
      localView = getChildAt(i);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (!localLayoutParams.mFullSpan) {
        if ((isLayoutRTL()) && (this.mOrientation == 1))
        {
          localView.offsetLeftAndRight(-(this.mSpanCount - 1 - localLayoutParams.mSpan.mIndex) * this.mSizePerSpan - -(this.mSpanCount - 1 - localLayoutParams.mSpan.mIndex) * m);
        }
        else
        {
          int n = localLayoutParams.mSpan.mIndex * this.mSizePerSpan;
          j = localLayoutParams.mSpan.mIndex * m;
          if (this.mOrientation == 1) {
            localView.offsetLeftAndRight(n - j);
          } else {
            localView.offsetTopAndBottom(n - j);
          }
        }
      }
    }
  }
  
  private void resolveShouldLayoutReverse()
  {
    if ((this.mOrientation != 1) && (isLayoutRTL())) {
      this.mShouldReverseLayout = (this.mReverseLayout ^ true);
    } else {
      this.mShouldReverseLayout = this.mReverseLayout;
    }
  }
  
  private void setLayoutStateDirection(int paramInt)
  {
    this.mLayoutState.mLayoutDirection = paramInt;
    LayoutState localLayoutState = this.mLayoutState;
    boolean bool2 = this.mShouldReverseLayout;
    int i = 1;
    boolean bool1;
    if (paramInt == -1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (bool2 == bool1) {
      paramInt = i;
    } else {
      paramInt = -1;
    }
    localLayoutState.mItemDirection = paramInt;
  }
  
  private void updateAllRemainingSpans(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < this.mSpanCount; i++) {
      if (!this.mSpans[i].mViews.isEmpty()) {
        updateRemainingSpans(this.mSpans[i], paramInt1, paramInt2);
      }
    }
  }
  
  private boolean updateAnchorFromChildren(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    int i;
    if (this.mLastLayoutFromEnd) {
      i = findLastReferenceChildPosition(paramState.getItemCount());
    } else {
      i = findFirstReferenceChildPosition(paramState.getItemCount());
    }
    paramAnchorInfo.mPosition = i;
    paramAnchorInfo.mOffset = Integer.MIN_VALUE;
    return true;
  }
  
  private void updateLayoutState(int paramInt, RecyclerView.State paramState)
  {
    LayoutState localLayoutState = this.mLayoutState;
    boolean bool2 = false;
    localLayoutState.mAvailable = 0;
    this.mLayoutState.mCurrentPosition = paramInt;
    int k = 0;
    int m = 0;
    int j = k;
    int i = m;
    boolean bool1;
    if (isSmoothScrolling())
    {
      int n = paramState.getTargetScrollPosition();
      j = k;
      i = m;
      if (n != -1)
      {
        boolean bool3 = this.mShouldReverseLayout;
        if (n < paramInt) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        if (bool3 == bool1)
        {
          i = this.mPrimaryOrientation.getTotalSpace();
          j = k;
        }
        else
        {
          j = this.mPrimaryOrientation.getTotalSpace();
          i = m;
        }
      }
    }
    if (getClipToPadding())
    {
      this.mLayoutState.mStartLine = (this.mPrimaryOrientation.getStartAfterPadding() - j);
      this.mLayoutState.mEndLine = (this.mPrimaryOrientation.getEndAfterPadding() + i);
    }
    else
    {
      this.mLayoutState.mEndLine = (this.mPrimaryOrientation.getEnd() + i);
      this.mLayoutState.mStartLine = (-j);
    }
    this.mLayoutState.mStopInFocusable = false;
    this.mLayoutState.mRecycle = true;
    paramState = this.mLayoutState;
    if ((this.mPrimaryOrientation.getMode() == 0) && (this.mPrimaryOrientation.getEnd() == 0)) {
      bool1 = true;
    } else {
      bool1 = bool2;
    }
    paramState.mInfinite = bool1;
  }
  
  private void updateRemainingSpans(Span paramSpan, int paramInt1, int paramInt2)
  {
    int i = paramSpan.getDeletedSize();
    if (paramInt1 == -1)
    {
      if (paramSpan.getStartLine() + i <= paramInt2) {
        this.mRemainingSpans.set(paramSpan.mIndex, false);
      }
    }
    else if (paramSpan.getEndLine() - i >= paramInt2) {
      this.mRemainingSpans.set(paramSpan.mIndex, false);
    }
  }
  
  private int updateSpecWithExtra(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 == 0) && (paramInt3 == 0)) {
      return paramInt1;
    }
    int i = View.MeasureSpec.getMode(paramInt1);
    if ((i != Integer.MIN_VALUE) && (i != 1073741824)) {
      return paramInt1;
    }
    return View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(paramInt1) - paramInt2 - paramInt3), i);
  }
  
  boolean areAllEndsEqual()
  {
    int j = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
    for (int i = 1; i < this.mSpanCount; i++) {
      if (this.mSpans[i].getEndLine(Integer.MIN_VALUE) != j) {
        return false;
      }
    }
    return true;
  }
  
  boolean areAllStartsEqual()
  {
    int j = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
    for (int i = 1; i < this.mSpanCount; i++) {
      if (this.mSpans[i].getStartLine(Integer.MIN_VALUE) != j) {
        return false;
      }
    }
    return true;
  }
  
  public void assertNotInLayoutOrScroll(String paramString)
  {
    if (this.mPendingSavedState == null) {
      super.assertNotInLayoutOrScroll(paramString);
    }
  }
  
  public boolean canScrollHorizontally()
  {
    boolean bool;
    if (this.mOrientation == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean canScrollVertically()
  {
    int i = this.mOrientation;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  boolean checkForGaps()
  {
    if ((getChildCount() != 0) && (this.mGapStrategy != 0) && (isAttachedToWindow()))
    {
      int j;
      int i;
      if (this.mShouldReverseLayout)
      {
        j = getLastChildPosition();
        i = getFirstChildPosition();
      }
      else
      {
        j = getFirstChildPosition();
        i = getLastChildPosition();
      }
      if ((j == 0) && (hasGapsToFix() != null))
      {
        this.mLazySpanLookup.clear();
        requestSimpleAnimationsInNextLayout();
        requestLayout();
        return true;
      }
      if (!this.mLaidOutInvalidFullSpan) {
        return false;
      }
      int k;
      if (this.mShouldReverseLayout) {
        k = -1;
      } else {
        k = 1;
      }
      StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem1 = this.mLazySpanLookup.getFirstFullSpanItemInRange(j, i + 1, k, true);
      if (localFullSpanItem1 == null)
      {
        this.mLaidOutInvalidFullSpan = false;
        this.mLazySpanLookup.forceInvalidateAfter(i + 1);
        return false;
      }
      StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem localFullSpanItem2 = this.mLazySpanLookup.getFirstFullSpanItemInRange(j, localFullSpanItem1.mPosition, k * -1, true);
      if (localFullSpanItem2 == null) {
        this.mLazySpanLookup.forceInvalidateAfter(localFullSpanItem1.mPosition);
      } else {
        this.mLazySpanLookup.forceInvalidateAfter(localFullSpanItem2.mPosition + 1);
      }
      requestSimpleAnimationsInNextLayout();
      requestLayout();
      return true;
    }
    return false;
  }
  
  public boolean checkLayoutParams(RecyclerView.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void collectAdjacentPrefetchPositions(int paramInt1, int paramInt2, RecyclerView.State paramState, RecyclerView.LayoutManager.LayoutPrefetchRegistry paramLayoutPrefetchRegistry)
  {
    if (this.mOrientation != 0) {
      paramInt1 = paramInt2;
    }
    if ((getChildCount() != 0) && (paramInt1 != 0))
    {
      prepareLayoutStateForDelta(paramInt1, paramState);
      Object localObject = this.mPrefetchDistances;
      if ((localObject == null) || (localObject.length < this.mSpanCount)) {
        this.mPrefetchDistances = new int[this.mSpanCount];
      }
      paramInt1 = 0;
      paramInt2 = 0;
      while (paramInt2 < this.mSpanCount)
      {
        int j;
        if (this.mLayoutState.mItemDirection == -1) {
          j = this.mLayoutState.mStartLine - this.mSpans[paramInt2].getStartLine(this.mLayoutState.mStartLine);
        } else {
          j = this.mSpans[paramInt2].getEndLine(this.mLayoutState.mEndLine) - this.mLayoutState.mEndLine;
        }
        int i = paramInt1;
        if (j >= 0)
        {
          this.mPrefetchDistances[paramInt1] = j;
          i = paramInt1 + 1;
        }
        paramInt2++;
        paramInt1 = i;
      }
      Arrays.sort(this.mPrefetchDistances, 0, paramInt1);
      for (paramInt2 = 0; (paramInt2 < paramInt1) && (this.mLayoutState.hasMore(paramState)); paramInt2++)
      {
        paramLayoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[paramInt2]);
        localObject = this.mLayoutState;
        ((LayoutState)localObject).mCurrentPosition += this.mLayoutState.mItemDirection;
      }
      return;
    }
  }
  
  public int computeHorizontalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public int computeHorizontalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public int computeHorizontalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  public PointF computeScrollVectorForPosition(int paramInt)
  {
    paramInt = calculateScrollDirectionForPosition(paramInt);
    PointF localPointF = new PointF();
    if (paramInt == 0) {
      return null;
    }
    if (this.mOrientation == 0)
    {
      localPointF.x = paramInt;
      localPointF.y = 0.0F;
    }
    else
    {
      localPointF.x = 0.0F;
      localPointF.y = paramInt;
    }
    return localPointF;
  }
  
  public int computeVerticalScrollExtent(RecyclerView.State paramState)
  {
    return computeScrollExtent(paramState);
  }
  
  public int computeVerticalScrollOffset(RecyclerView.State paramState)
  {
    return computeScrollOffset(paramState);
  }
  
  public int computeVerticalScrollRange(RecyclerView.State paramState)
  {
    return computeScrollRange(paramState);
  }
  
  public int[] findFirstCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].findFirstCompletelyVisibleItemPosition();
    }
    return paramArrayOfInt;
    label53:
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
  }
  
  View findFirstVisibleItemClosestToEnd(boolean paramBoolean)
  {
    int k = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    Object localObject1 = null;
    int i = getChildCount() - 1;
    while (i >= 0)
    {
      View localView = getChildAt(i);
      int m = this.mPrimaryOrientation.getDecoratedStart(localView);
      int n = this.mPrimaryOrientation.getDecoratedEnd(localView);
      Object localObject2 = localObject1;
      if (n > k) {
        if (m >= j)
        {
          localObject2 = localObject1;
        }
        else if ((n > j) && (paramBoolean))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = localView;
          }
        }
        else
        {
          return localView;
        }
      }
      i--;
      localObject1 = localObject2;
    }
    return (View)localObject1;
  }
  
  View findFirstVisibleItemClosestToStart(boolean paramBoolean)
  {
    int k = this.mPrimaryOrientation.getStartAfterPadding();
    int j = this.mPrimaryOrientation.getEndAfterPadding();
    int m = getChildCount();
    Object localObject1 = null;
    int i = 0;
    while (i < m)
    {
      View localView = getChildAt(i);
      int n = this.mPrimaryOrientation.getDecoratedStart(localView);
      Object localObject2 = localObject1;
      if (this.mPrimaryOrientation.getDecoratedEnd(localView) > k) {
        if (n >= j)
        {
          localObject2 = localObject1;
        }
        else if ((n < k) && (paramBoolean))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = localView;
          }
        }
        else
        {
          return localView;
        }
      }
      i++;
      localObject1 = localObject2;
    }
    return (View)localObject1;
  }
  
  int findFirstVisibleItemPositionInt()
  {
    View localView;
    if (this.mShouldReverseLayout) {
      localView = findFirstVisibleItemClosestToEnd(true);
    } else {
      localView = findFirstVisibleItemClosestToStart(true);
    }
    int i;
    if (localView == null) {
      i = -1;
    } else {
      i = getPosition(localView);
    }
    return i;
  }
  
  public int[] findFirstVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].findFirstVisibleItemPosition();
    }
    return paramArrayOfInt;
    label53:
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
  }
  
  public int[] findLastCompletelyVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].findLastCompletelyVisibleItemPosition();
    }
    return paramArrayOfInt;
    label53:
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
  }
  
  public int[] findLastVisibleItemPositions(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      paramArrayOfInt = new int[this.mSpanCount];
    } else {
      if (paramArrayOfInt.length < this.mSpanCount) {
        break label53;
      }
    }
    for (int i = 0; i < this.mSpanCount; i++) {
      paramArrayOfInt[i] = this.mSpans[i].findLastVisibleItemPosition();
    }
    return paramArrayOfInt;
    label53:
    throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.mSpanCount + ", array size:" + paramArrayOfInt.length);
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
    return super.getColumnCountForAccessibility(paramRecycler, paramState);
  }
  
  int getFirstChildPosition()
  {
    int j = getChildCount();
    int i = 0;
    if (j != 0) {
      i = getPosition(getChildAt(0));
    }
    return i;
  }
  
  public int getGapStrategy()
  {
    return this.mGapStrategy;
  }
  
  int getLastChildPosition()
  {
    int i = getChildCount();
    if (i == 0) {
      i = 0;
    } else {
      i = getPosition(getChildAt(i - 1));
    }
    return i;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public boolean getReverseLayout()
  {
    return this.mReverseLayout;
  }
  
  public int getRowCountForAccessibility(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (this.mOrientation == 0) {
      return this.mSpanCount;
    }
    return super.getRowCountForAccessibility(paramRecycler, paramState);
  }
  
  public int getSpanCount()
  {
    return this.mSpanCount;
  }
  
  View hasGapsToFix()
  {
    int i = getChildCount() - 1;
    BitSet localBitSet = new BitSet(this.mSpanCount);
    localBitSet.set(0, this.mSpanCount, true);
    int j = this.mOrientation;
    int m = -1;
    if ((j == 1) && (isLayoutRTL())) {
      j = 1;
    } else {
      j = -1;
    }
    int k;
    if (this.mShouldReverseLayout)
    {
      k = 0 - 1;
    }
    else
    {
      n = 0;
      k = i + 1;
      i = n;
    }
    if (i < k) {
      m = 1;
    }
    int n = i;
    while (n != k)
    {
      View localView = getChildAt(n);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (localBitSet.get(localLayoutParams.mSpan.mIndex))
      {
        if (checkSpanForGap(localLayoutParams.mSpan)) {
          return localView;
        }
        localBitSet.clear(localLayoutParams.mSpan.mIndex);
      }
      if ((!localLayoutParams.mFullSpan) && (n + m != k))
      {
        Object localObject = getChildAt(n + m);
        i = 0;
        int i1 = 0;
        int i2;
        if (this.mShouldReverseLayout)
        {
          int i3 = this.mPrimaryOrientation.getDecoratedEnd(localView);
          i2 = this.mPrimaryOrientation.getDecoratedEnd((View)localObject);
          if (i3 < i2) {
            return localView;
          }
          i = i1;
          if (i3 == i2) {
            i = 1;
          }
        }
        else
        {
          i1 = this.mPrimaryOrientation.getDecoratedStart(localView);
          i2 = this.mPrimaryOrientation.getDecoratedStart((View)localObject);
          if (i1 > i2) {
            return localView;
          }
          if (i1 == i2) {
            i = 1;
          }
        }
        if (i != 0)
        {
          localObject = (LayoutParams)((View)localObject).getLayoutParams();
          if (localLayoutParams.mSpan.mIndex - ((LayoutParams)localObject).mSpan.mIndex < 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (j < 0) {
            i1 = 1;
          } else {
            i1 = 0;
          }
          if (i != i1) {
            return localView;
          }
        }
      }
      n += m;
    }
    return null;
  }
  
  public void invalidateSpanAssignments()
  {
    this.mLazySpanLookup.clear();
    requestLayout();
  }
  
  public boolean isAutoMeasureEnabled()
  {
    boolean bool;
    if (this.mGapStrategy != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isLayoutRTL()
  {
    int i = getLayoutDirection();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  public void offsetChildrenHorizontal(int paramInt)
  {
    super.offsetChildrenHorizontal(paramInt);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].onOffset(paramInt);
    }
  }
  
  public void offsetChildrenVertical(int paramInt)
  {
    super.offsetChildrenVertical(paramInt);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].onOffset(paramInt);
    }
  }
  
  public void onDetachedFromWindow(RecyclerView paramRecyclerView, RecyclerView.Recycler paramRecycler)
  {
    super.onDetachedFromWindow(paramRecyclerView, paramRecycler);
    removeCallbacks(this.mCheckForGapsRunnable);
    for (int i = 0; i < this.mSpanCount; i++) {
      this.mSpans[i].clear();
    }
    paramRecyclerView.requestLayout();
  }
  
  public View onFocusSearchFailed(View paramView, int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if (getChildCount() == 0) {
      return null;
    }
    paramView = findContainingItemView(paramView);
    if (paramView == null) {
      return null;
    }
    resolveShouldLayoutReverse();
    int k = convertFocusDirectionToLayoutDirection(paramInt);
    if (k == Integer.MIN_VALUE) {
      return null;
    }
    Object localObject = (LayoutParams)paramView.getLayoutParams();
    boolean bool1 = ((LayoutParams)localObject).mFullSpan;
    localObject = ((LayoutParams)localObject).mSpan;
    if (k == 1) {
      paramInt = getLastChildPosition();
    } else {
      paramInt = getFirstChildPosition();
    }
    updateLayoutState(paramInt, paramState);
    setLayoutStateDirection(k);
    LayoutState localLayoutState = this.mLayoutState;
    localLayoutState.mCurrentPosition = (localLayoutState.mItemDirection + paramInt);
    this.mLayoutState.mAvailable = ((int)(this.mPrimaryOrientation.getTotalSpace() * 0.33333334F));
    this.mLayoutState.mStopInFocusable = true;
    localLayoutState = this.mLayoutState;
    int j = 0;
    localLayoutState.mRecycle = false;
    fill(paramRecycler, this.mLayoutState, paramState);
    this.mLastLayoutFromEnd = this.mShouldReverseLayout;
    if (!bool1)
    {
      paramRecycler = ((Span)localObject).getFocusableViewAfter(paramInt, k);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
    }
    int i;
    if (preferLastSpan(k)) {
      for (i = this.mSpanCount - 1; i >= 0; i--)
      {
        paramRecycler = this.mSpans[i].getFocusableViewAfter(paramInt, k);
        if ((paramRecycler != null) && (paramRecycler != paramView)) {
          return paramRecycler;
        }
      }
    } else {
      for (i = 0; i < this.mSpanCount; i++)
      {
        paramRecycler = this.mSpans[i].getFocusableViewAfter(paramInt, k);
        if ((paramRecycler != null) && (paramRecycler != paramView)) {
          return paramRecycler;
        }
      }
    }
    boolean bool2 = this.mReverseLayout;
    if (k == -1) {
      i = 1;
    } else {
      i = 0;
    }
    paramInt = j;
    if ((bool2 ^ true) == i) {
      paramInt = 1;
    }
    if (!bool1)
    {
      if (paramInt != 0) {
        i = ((Span)localObject).findFirstPartiallyVisibleItemPosition();
      } else {
        i = ((Span)localObject).findLastPartiallyVisibleItemPosition();
      }
      paramRecycler = findViewByPosition(i);
      if ((paramRecycler != null) && (paramRecycler != paramView)) {
        return paramRecycler;
      }
    }
    if (preferLastSpan(k)) {
      for (i = this.mSpanCount - 1; i >= 0; i--) {
        if (i != ((Span)localObject).mIndex)
        {
          if (paramInt != 0) {
            j = this.mSpans[i].findFirstPartiallyVisibleItemPosition();
          } else {
            j = this.mSpans[i].findLastPartiallyVisibleItemPosition();
          }
          paramRecycler = findViewByPosition(j);
          if ((paramRecycler != null) && (paramRecycler != paramView)) {
            return paramRecycler;
          }
        }
      }
    } else {
      for (i = 0; i < this.mSpanCount; i++)
      {
        if (paramInt != 0) {
          j = this.mSpans[i].findFirstPartiallyVisibleItemPosition();
        } else {
          j = this.mSpans[i].findLastPartiallyVisibleItemPosition();
        }
        paramRecycler = findViewByPosition(j);
        if ((paramRecycler != null) && (paramRecycler != paramView)) {
          return paramRecycler;
        }
      }
    }
    return null;
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    if (getChildCount() > 0)
    {
      View localView1 = findFirstVisibleItemClosestToStart(false);
      View localView2 = findFirstVisibleItemClosestToEnd(false);
      if ((localView1 != null) && (localView2 != null))
      {
        int i = getPosition(localView1);
        int j = getPosition(localView2);
        if (i < j)
        {
          paramAccessibilityEvent.setFromIndex(i);
          paramAccessibilityEvent.setToIndex(j);
        }
        else
        {
          paramAccessibilityEvent.setFromIndex(j);
          paramAccessibilityEvent.setToIndex(i);
        }
      }
      else {}
    }
  }
  
  public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState, View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    paramRecycler = paramView.getLayoutParams();
    if (!(paramRecycler instanceof LayoutParams))
    {
      super.onInitializeAccessibilityNodeInfoForItem(paramView, paramAccessibilityNodeInfoCompat);
      return;
    }
    paramRecycler = (LayoutParams)paramRecycler;
    int k = this.mOrientation;
    int j = 1;
    int i = 1;
    if (k == 0)
    {
      j = paramRecycler.getSpanIndex();
      if (paramRecycler.mFullSpan) {
        i = this.mSpanCount;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(j, i, -1, -1, false, false));
    }
    else
    {
      k = paramRecycler.getSpanIndex();
      i = j;
      if (paramRecycler.mFullSpan) {
        i = this.mSpanCount;
      }
      paramAccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, k, i, false, false));
    }
  }
  
  public void onItemsAdded(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 1);
  }
  
  public void onItemsChanged(RecyclerView paramRecyclerView)
  {
    this.mLazySpanLookup.clear();
    requestLayout();
  }
  
  public void onItemsMoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, int paramInt3)
  {
    handleUpdate(paramInt1, paramInt2, 8);
  }
  
  public void onItemsRemoved(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    handleUpdate(paramInt1, paramInt2, 2);
  }
  
  public void onItemsUpdated(RecyclerView paramRecyclerView, int paramInt1, int paramInt2, Object paramObject)
  {
    handleUpdate(paramInt1, paramInt2, 4);
  }
  
  public void onLayoutChildren(RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    onLayoutChildren(paramRecycler, paramState, true);
  }
  
  public void onLayoutCompleted(RecyclerView.State paramState)
  {
    super.onLayoutCompleted(paramState);
    this.mPendingScrollPosition = -1;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    this.mPendingSavedState = null;
    this.mAnchorInfo.reset();
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      this.mPendingSavedState = ((SavedState)paramParcelable);
      requestLayout();
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    if (this.mPendingSavedState != null) {
      return new SavedState(this.mPendingSavedState);
    }
    SavedState localSavedState = new SavedState();
    localSavedState.mReverseLayout = this.mReverseLayout;
    localSavedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
    localSavedState.mLastLayoutRTL = this.mLastLayoutRTL;
    LazySpanLookup localLazySpanLookup = this.mLazySpanLookup;
    if ((localLazySpanLookup != null) && (localLazySpanLookup.mData != null))
    {
      localSavedState.mSpanLookup = this.mLazySpanLookup.mData;
      localSavedState.mSpanLookupSize = localSavedState.mSpanLookup.length;
      localSavedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
    }
    else
    {
      localSavedState.mSpanLookupSize = 0;
    }
    if (getChildCount() > 0)
    {
      int i;
      if (this.mLastLayoutFromEnd) {
        i = getLastChildPosition();
      } else {
        i = getFirstChildPosition();
      }
      localSavedState.mAnchorPosition = i;
      localSavedState.mVisibleAnchorPosition = findFirstVisibleItemPositionInt();
      localSavedState.mSpanOffsetsSize = this.mSpanCount;
      localSavedState.mSpanOffsets = new int[this.mSpanCount];
      for (int j = 0; j < this.mSpanCount; j++)
      {
        int k;
        if (this.mLastLayoutFromEnd)
        {
          k = this.mSpans[j].getEndLine(Integer.MIN_VALUE);
          i = k;
          if (k != Integer.MIN_VALUE) {
            i = k - this.mPrimaryOrientation.getEndAfterPadding();
          }
        }
        else
        {
          k = this.mSpans[j].getStartLine(Integer.MIN_VALUE);
          i = k;
          if (k != Integer.MIN_VALUE) {
            i = k - this.mPrimaryOrientation.getStartAfterPadding();
          }
        }
        localSavedState.mSpanOffsets[j] = i;
      }
    }
    else
    {
      localSavedState.mAnchorPosition = -1;
      localSavedState.mVisibleAnchorPosition = -1;
      localSavedState.mSpanOffsetsSize = 0;
    }
    return localSavedState;
  }
  
  public void onScrollStateChanged(int paramInt)
  {
    if (paramInt == 0) {
      checkForGaps();
    }
  }
  
  void prepareLayoutStateForDelta(int paramInt, RecyclerView.State paramState)
  {
    int j;
    int i;
    if (paramInt > 0)
    {
      j = 1;
      i = getLastChildPosition();
    }
    else
    {
      j = -1;
      i = getFirstChildPosition();
    }
    this.mLayoutState.mRecycle = true;
    updateLayoutState(i, paramState);
    setLayoutStateDirection(j);
    paramState = this.mLayoutState;
    paramState.mCurrentPosition = (paramState.mItemDirection + i);
    this.mLayoutState.mAvailable = Math.abs(paramInt);
  }
  
  int scrollBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    if ((getChildCount() != 0) && (paramInt != 0))
    {
      prepareLayoutStateForDelta(paramInt, paramState);
      int i = fill(paramRecycler, this.mLayoutState, paramState);
      if (this.mLayoutState.mAvailable >= i) {
        if (paramInt < 0) {
          paramInt = -i;
        } else {
          paramInt = i;
        }
      }
      this.mPrimaryOrientation.offsetChildren(-paramInt);
      this.mLastLayoutFromEnd = this.mShouldReverseLayout;
      this.mLayoutState.mAvailable = 0;
      recycle(paramRecycler, this.mLayoutState);
      return paramInt;
    }
    return 0;
  }
  
  public int scrollHorizontallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void scrollToPosition(int paramInt)
  {
    SavedState localSavedState = this.mPendingSavedState;
    if ((localSavedState != null) && (localSavedState.mAnchorPosition != paramInt)) {
      this.mPendingSavedState.invalidateAnchorPositionInfo();
    }
    this.mPendingScrollPosition = paramInt;
    this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
    requestLayout();
  }
  
  public void scrollToPositionWithOffset(int paramInt1, int paramInt2)
  {
    SavedState localSavedState = this.mPendingSavedState;
    if (localSavedState != null) {
      localSavedState.invalidateAnchorPositionInfo();
    }
    this.mPendingScrollPosition = paramInt1;
    this.mPendingScrollPositionOffset = paramInt2;
    requestLayout();
  }
  
  public int scrollVerticallyBy(int paramInt, RecyclerView.Recycler paramRecycler, RecyclerView.State paramState)
  {
    return scrollBy(paramInt, paramRecycler, paramState);
  }
  
  public void setGapStrategy(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mGapStrategy) {
      return;
    }
    if ((paramInt != 0) && (paramInt != 2)) {
      throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
    }
    this.mGapStrategy = paramInt;
    requestLayout();
  }
  
  public void setMeasuredDimension(Rect paramRect, int paramInt1, int paramInt2)
  {
    int j = getPaddingLeft() + getPaddingRight();
    int i = getPaddingTop() + getPaddingBottom();
    if (this.mOrientation == 1)
    {
      paramInt2 = chooseSize(paramInt2, paramRect.height() + i, getMinimumHeight());
      paramInt1 = chooseSize(paramInt1, this.mSizePerSpan * this.mSpanCount + j, getMinimumWidth());
    }
    else
    {
      paramInt1 = chooseSize(paramInt1, paramRect.width() + j, getMinimumWidth());
      paramInt2 = chooseSize(paramInt2, this.mSizePerSpan * this.mSpanCount + i, getMinimumHeight());
    }
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  public void setOrientation(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("invalid orientation.");
    }
    assertNotInLayoutOrScroll(null);
    if (paramInt == this.mOrientation) {
      return;
    }
    this.mOrientation = paramInt;
    OrientationHelper localOrientationHelper = this.mPrimaryOrientation;
    this.mPrimaryOrientation = this.mSecondaryOrientation;
    this.mSecondaryOrientation = localOrientationHelper;
    requestLayout();
  }
  
  public void setReverseLayout(boolean paramBoolean)
  {
    assertNotInLayoutOrScroll(null);
    SavedState localSavedState = this.mPendingSavedState;
    if ((localSavedState != null) && (localSavedState.mReverseLayout != paramBoolean)) {
      this.mPendingSavedState.mReverseLayout = paramBoolean;
    }
    this.mReverseLayout = paramBoolean;
    requestLayout();
  }
  
  public void setSpanCount(int paramInt)
  {
    assertNotInLayoutOrScroll(null);
    if (paramInt != this.mSpanCount)
    {
      invalidateSpanAssignments();
      this.mSpanCount = paramInt;
      this.mRemainingSpans = new BitSet(this.mSpanCount);
      this.mSpans = new Span[this.mSpanCount];
      for (paramInt = 0; paramInt < this.mSpanCount; paramInt++) {
        this.mSpans[paramInt] = new Span(paramInt);
      }
      requestLayout();
    }
  }
  
  public void smoothScrollToPosition(RecyclerView paramRecyclerView, RecyclerView.State paramState, int paramInt)
  {
    paramRecyclerView = new LinearSmoothScroller(paramRecyclerView.getContext());
    paramRecyclerView.setTargetPosition(paramInt);
    startSmoothScroll(paramRecyclerView);
  }
  
  public boolean supportsPredictiveItemAnimations()
  {
    boolean bool;
    if (this.mPendingSavedState == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean updateAnchorFromPendingData(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    boolean bool2 = paramState.isPreLayout();
    boolean bool1 = false;
    if (!bool2)
    {
      int i = this.mPendingScrollPosition;
      if (i != -1)
      {
        if ((i >= 0) && (i < paramState.getItemCount()))
        {
          paramState = this.mPendingSavedState;
          if ((paramState != null) && (paramState.mAnchorPosition != -1) && (this.mPendingSavedState.mSpanOffsetsSize >= 1))
          {
            paramAnchorInfo.mOffset = Integer.MIN_VALUE;
            paramAnchorInfo.mPosition = this.mPendingScrollPosition;
          }
          else
          {
            paramState = findViewByPosition(this.mPendingScrollPosition);
            if (paramState != null)
            {
              if (this.mShouldReverseLayout) {
                i = getLastChildPosition();
              } else {
                i = getFirstChildPosition();
              }
              paramAnchorInfo.mPosition = i;
              if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE)
              {
                if (paramAnchorInfo.mLayoutFromEnd) {
                  paramAnchorInfo.mOffset = (this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd(paramState));
                } else {
                  paramAnchorInfo.mOffset = (this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart(paramState));
                }
                return true;
              }
              if (this.mPrimaryOrientation.getDecoratedMeasurement(paramState) > this.mPrimaryOrientation.getTotalSpace())
              {
                if (paramAnchorInfo.mLayoutFromEnd) {
                  i = this.mPrimaryOrientation.getEndAfterPadding();
                } else {
                  i = this.mPrimaryOrientation.getStartAfterPadding();
                }
                paramAnchorInfo.mOffset = i;
                return true;
              }
              i = this.mPrimaryOrientation.getDecoratedStart(paramState) - this.mPrimaryOrientation.getStartAfterPadding();
              if (i < 0)
              {
                paramAnchorInfo.mOffset = (-i);
                return true;
              }
              i = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd(paramState);
              if (i < 0)
              {
                paramAnchorInfo.mOffset = i;
                return true;
              }
              paramAnchorInfo.mOffset = Integer.MIN_VALUE;
            }
            else
            {
              paramAnchorInfo.mPosition = this.mPendingScrollPosition;
              i = this.mPendingScrollPositionOffset;
              if (i == Integer.MIN_VALUE)
              {
                if (calculateScrollDirectionForPosition(paramAnchorInfo.mPosition) == 1) {
                  bool1 = true;
                }
                paramAnchorInfo.mLayoutFromEnd = bool1;
                paramAnchorInfo.assignCoordinateFromPadding();
              }
              else
              {
                paramAnchorInfo.assignCoordinateFromPadding(i);
              }
              paramAnchorInfo.mInvalidateOffsets = true;
            }
          }
          return true;
        }
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
      }
    }
    return false;
  }
  
  void updateAnchorInfoForLayout(RecyclerView.State paramState, AnchorInfo paramAnchorInfo)
  {
    if (updateAnchorFromPendingData(paramState, paramAnchorInfo)) {
      return;
    }
    if (updateAnchorFromChildren(paramState, paramAnchorInfo)) {
      return;
    }
    paramAnchorInfo.assignCoordinateFromPadding();
    paramAnchorInfo.mPosition = 0;
  }
  
  void updateMeasureSpecs(int paramInt)
  {
    this.mSizePerSpan = (paramInt / this.mSpanCount);
    this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec(paramInt, this.mSecondaryOrientation.getMode());
  }
  
  class AnchorInfo
  {
    boolean mInvalidateOffsets;
    boolean mLayoutFromEnd;
    int mOffset;
    int mPosition;
    int[] mSpanReferenceLines;
    boolean mValid;
    
    AnchorInfo()
    {
      reset();
    }
    
    void assignCoordinateFromPadding()
    {
      int i;
      if (this.mLayoutFromEnd) {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      } else {
        i = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      }
      this.mOffset = i;
    }
    
    void assignCoordinateFromPadding(int paramInt)
    {
      if (this.mLayoutFromEnd) {
        this.mOffset = (StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - paramInt);
      } else {
        this.mOffset = (StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + paramInt);
      }
    }
    
    void reset()
    {
      this.mPosition = -1;
      this.mOffset = Integer.MIN_VALUE;
      this.mLayoutFromEnd = false;
      this.mInvalidateOffsets = false;
      this.mValid = false;
      int[] arrayOfInt = this.mSpanReferenceLines;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
    }
    
    void saveSpanReferenceLines(StaggeredGridLayoutManager.Span[] paramArrayOfSpan)
    {
      int j = paramArrayOfSpan.length;
      int[] arrayOfInt = this.mSpanReferenceLines;
      if ((arrayOfInt == null) || (arrayOfInt.length < j)) {
        this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
      }
      for (int i = 0; i < j; i++) {
        this.mSpanReferenceLines[i] = paramArrayOfSpan[i].getStartLine(Integer.MIN_VALUE);
      }
    }
  }
  
  public static class LayoutParams
    extends RecyclerView.LayoutParams
  {
    public static final int INVALID_SPAN_ID = -1;
    boolean mFullSpan;
    StaggeredGridLayoutManager.Span mSpan;
    
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
    
    public final int getSpanIndex()
    {
      StaggeredGridLayoutManager.Span localSpan = this.mSpan;
      if (localSpan == null) {
        return -1;
      }
      return localSpan.mIndex;
    }
    
    public boolean isFullSpan()
    {
      return this.mFullSpan;
    }
    
    public void setFullSpan(boolean paramBoolean)
    {
      this.mFullSpan = paramBoolean;
    }
  }
  
  static class LazySpanLookup
  {
    private static final int MIN_SIZE = 10;
    int[] mData;
    List<FullSpanItem> mFullSpanItems;
    
    private int invalidateFullSpansAfter(int paramInt)
    {
      if (this.mFullSpanItems == null) {
        return -1;
      }
      FullSpanItem localFullSpanItem = getFullSpanItem(paramInt);
      if (localFullSpanItem != null) {
        this.mFullSpanItems.remove(localFullSpanItem);
      }
      int k = -1;
      int m = this.mFullSpanItems.size();
      int j;
      for (int i = 0;; i++)
      {
        j = k;
        if (i >= m) {
          break;
        }
        if (((FullSpanItem)this.mFullSpanItems.get(i)).mPosition >= paramInt)
        {
          j = i;
          break;
        }
      }
      if (j != -1)
      {
        localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(j);
        this.mFullSpanItems.remove(j);
        return localFullSpanItem.mPosition;
      }
      return -1;
    }
    
    private void offsetFullSpansForAddition(int paramInt1, int paramInt2)
    {
      Object localObject = this.mFullSpanItems;
      if (localObject == null) {
        return;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (FullSpanItem)this.mFullSpanItems.get(i);
        if (((FullSpanItem)localObject).mPosition >= paramInt1) {
          ((FullSpanItem)localObject).mPosition += paramInt2;
        }
      }
    }
    
    private void offsetFullSpansForRemoval(int paramInt1, int paramInt2)
    {
      Object localObject = this.mFullSpanItems;
      if (localObject == null) {
        return;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (FullSpanItem)this.mFullSpanItems.get(i);
        if (((FullSpanItem)localObject).mPosition >= paramInt1) {
          if (((FullSpanItem)localObject).mPosition < paramInt1 + paramInt2) {
            this.mFullSpanItems.remove(i);
          } else {
            ((FullSpanItem)localObject).mPosition -= paramInt2;
          }
        }
      }
    }
    
    public void addFullSpanItem(FullSpanItem paramFullSpanItem)
    {
      if (this.mFullSpanItems == null) {
        this.mFullSpanItems = new ArrayList();
      }
      int j = this.mFullSpanItems.size();
      for (int i = 0; i < j; i++)
      {
        FullSpanItem localFullSpanItem = (FullSpanItem)this.mFullSpanItems.get(i);
        if (localFullSpanItem.mPosition == paramFullSpanItem.mPosition) {
          this.mFullSpanItems.remove(i);
        }
        if (localFullSpanItem.mPosition >= paramFullSpanItem.mPosition)
        {
          this.mFullSpanItems.add(i, paramFullSpanItem);
          return;
        }
      }
      this.mFullSpanItems.add(paramFullSpanItem);
    }
    
    void clear()
    {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt != null) {
        Arrays.fill(arrayOfInt, -1);
      }
      this.mFullSpanItems = null;
    }
    
    void ensureSize(int paramInt)
    {
      int[] arrayOfInt1 = this.mData;
      if (arrayOfInt1 == null)
      {
        arrayOfInt1 = new int[Math.max(paramInt, 10) + 1];
        this.mData = arrayOfInt1;
        Arrays.fill(arrayOfInt1, -1);
      }
      else if (paramInt >= arrayOfInt1.length)
      {
        arrayOfInt1 = this.mData;
        int[] arrayOfInt2 = new int[sizeForPosition(paramInt)];
        this.mData = arrayOfInt2;
        System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
        arrayOfInt2 = this.mData;
        Arrays.fill(arrayOfInt2, arrayOfInt1.length, arrayOfInt2.length, -1);
      }
    }
    
    int forceInvalidateAfter(int paramInt)
    {
      List localList = this.mFullSpanItems;
      if (localList != null) {
        for (int i = localList.size() - 1; i >= 0; i--) {
          if (((FullSpanItem)this.mFullSpanItems.get(i)).mPosition >= paramInt) {
            this.mFullSpanItems.remove(i);
          }
        }
      }
      return invalidateAfter(paramInt);
    }
    
    public FullSpanItem getFirstFullSpanItemInRange(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      Object localObject = this.mFullSpanItems;
      if (localObject == null) {
        return null;
      }
      int j = ((List)localObject).size();
      for (int i = 0; i < j; i++)
      {
        localObject = (FullSpanItem)this.mFullSpanItems.get(i);
        if (((FullSpanItem)localObject).mPosition >= paramInt2) {
          return null;
        }
        if ((((FullSpanItem)localObject).mPosition >= paramInt1) && ((paramInt3 == 0) || (((FullSpanItem)localObject).mGapDir == paramInt3) || ((paramBoolean) && (((FullSpanItem)localObject).mHasUnwantedGapAfter)))) {
          return (FullSpanItem)localObject;
        }
      }
      return null;
    }
    
    public FullSpanItem getFullSpanItem(int paramInt)
    {
      Object localObject = this.mFullSpanItems;
      if (localObject == null) {
        return null;
      }
      for (int i = ((List)localObject).size() - 1; i >= 0; i--)
      {
        localObject = (FullSpanItem)this.mFullSpanItems.get(i);
        if (((FullSpanItem)localObject).mPosition == paramInt) {
          return (FullSpanItem)localObject;
        }
      }
      return null;
    }
    
    int getSpan(int paramInt)
    {
      int[] arrayOfInt = this.mData;
      if ((arrayOfInt != null) && (paramInt < arrayOfInt.length)) {
        return arrayOfInt[paramInt];
      }
      return -1;
    }
    
    int invalidateAfter(int paramInt)
    {
      int[] arrayOfInt = this.mData;
      if (arrayOfInt == null) {
        return -1;
      }
      if (paramInt >= arrayOfInt.length) {
        return -1;
      }
      int i = invalidateFullSpansAfter(paramInt);
      if (i == -1)
      {
        arrayOfInt = this.mData;
        Arrays.fill(arrayOfInt, paramInt, arrayOfInt.length, -1);
        return this.mData.length;
      }
      Arrays.fill(this.mData, paramInt, i + 1, -1);
      return i + 1;
    }
    
    void offsetForAddition(int paramInt1, int paramInt2)
    {
      int[] arrayOfInt = this.mData;
      if ((arrayOfInt != null) && (paramInt1 < arrayOfInt.length))
      {
        ensureSize(paramInt1 + paramInt2);
        arrayOfInt = this.mData;
        System.arraycopy(arrayOfInt, paramInt1, arrayOfInt, paramInt1 + paramInt2, arrayOfInt.length - paramInt1 - paramInt2);
        Arrays.fill(this.mData, paramInt1, paramInt1 + paramInt2, -1);
        offsetFullSpansForAddition(paramInt1, paramInt2);
        return;
      }
    }
    
    void offsetForRemoval(int paramInt1, int paramInt2)
    {
      int[] arrayOfInt = this.mData;
      if ((arrayOfInt != null) && (paramInt1 < arrayOfInt.length))
      {
        ensureSize(paramInt1 + paramInt2);
        arrayOfInt = this.mData;
        System.arraycopy(arrayOfInt, paramInt1 + paramInt2, arrayOfInt, paramInt1, arrayOfInt.length - paramInt1 - paramInt2);
        arrayOfInt = this.mData;
        Arrays.fill(arrayOfInt, arrayOfInt.length - paramInt2, arrayOfInt.length, -1);
        offsetFullSpansForRemoval(paramInt1, paramInt2);
        return;
      }
    }
    
    void setSpan(int paramInt, StaggeredGridLayoutManager.Span paramSpan)
    {
      ensureSize(paramInt);
      this.mData[paramInt] = paramSpan.mIndex;
    }
    
    int sizeForPosition(int paramInt)
    {
      int i = this.mData.length;
      while (i <= paramInt) {
        i *= 2;
      }
      return i;
    }
    
    static class FullSpanItem
      implements Parcelable
    {
      public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator()
      {
        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem createFromParcel(Parcel paramAnonymousParcel)
        {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem(paramAnonymousParcel);
        }
        
        public StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[] newArray(int paramAnonymousInt)
        {
          return new StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem[paramAnonymousInt];
        }
      };
      int mGapDir;
      int[] mGapPerSpan;
      boolean mHasUnwantedGapAfter;
      int mPosition;
      
      FullSpanItem() {}
      
      FullSpanItem(Parcel paramParcel)
      {
        this.mPosition = paramParcel.readInt();
        this.mGapDir = paramParcel.readInt();
        int i = paramParcel.readInt();
        boolean bool = true;
        if (i != 1) {
          bool = false;
        }
        this.mHasUnwantedGapAfter = bool;
        i = paramParcel.readInt();
        if (i > 0)
        {
          int[] arrayOfInt = new int[i];
          this.mGapPerSpan = arrayOfInt;
          paramParcel.readIntArray(arrayOfInt);
        }
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      int getGapForSpan(int paramInt)
      {
        int[] arrayOfInt = this.mGapPerSpan;
        if (arrayOfInt == null) {
          paramInt = 0;
        } else {
          paramInt = arrayOfInt[paramInt];
        }
        return paramInt;
      }
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder().append("FullSpanItem{mPosition=").append(this.mPosition).append(", mGapDir=").append(this.mGapDir).append(", mHasUnwantedGapAfter=").append(this.mHasUnwantedGapAfter).append(", mGapPerSpan=");
        String str = Arrays.toString(this.mGapPerSpan);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str + '}';
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.mPosition);
        paramParcel.writeInt(this.mGapDir);
        paramParcel.writeInt(this.mHasUnwantedGapAfter);
        int[] arrayOfInt = this.mGapPerSpan;
        if ((arrayOfInt != null) && (arrayOfInt.length > 0))
        {
          paramParcel.writeInt(arrayOfInt.length);
          paramParcel.writeIntArray(this.mGapPerSpan);
        }
        else
        {
          paramParcel.writeInt(0);
        }
      }
    }
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public StaggeredGridLayoutManager.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new StaggeredGridLayoutManager.SavedState(paramAnonymousParcel);
      }
      
      public StaggeredGridLayoutManager.SavedState[] newArray(int paramAnonymousInt)
      {
        return new StaggeredGridLayoutManager.SavedState[paramAnonymousInt];
      }
    };
    boolean mAnchorLayoutFromEnd;
    int mAnchorPosition;
    List<StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem> mFullSpanItems;
    boolean mLastLayoutRTL;
    boolean mReverseLayout;
    int[] mSpanLookup;
    int mSpanLookupSize;
    int[] mSpanOffsets;
    int mSpanOffsetsSize;
    int mVisibleAnchorPosition;
    
    public SavedState() {}
    
    SavedState(Parcel paramParcel)
    {
      this.mAnchorPosition = paramParcel.readInt();
      this.mVisibleAnchorPosition = paramParcel.readInt();
      int i = paramParcel.readInt();
      this.mSpanOffsetsSize = i;
      int[] arrayOfInt;
      if (i > 0)
      {
        arrayOfInt = new int[i];
        this.mSpanOffsets = arrayOfInt;
        paramParcel.readIntArray(arrayOfInt);
      }
      i = paramParcel.readInt();
      this.mSpanLookupSize = i;
      if (i > 0)
      {
        arrayOfInt = new int[i];
        this.mSpanLookup = arrayOfInt;
        paramParcel.readIntArray(arrayOfInt);
      }
      i = paramParcel.readInt();
      boolean bool2 = false;
      if (i == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.mReverseLayout = bool1;
      if (paramParcel.readInt() == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.mAnchorLayoutFromEnd = bool1;
      boolean bool1 = bool2;
      if (paramParcel.readInt() == 1) {
        bool1 = true;
      }
      this.mLastLayoutRTL = bool1;
      this.mFullSpanItems = paramParcel.readArrayList(StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.class.getClassLoader());
    }
    
    public SavedState(SavedState paramSavedState)
    {
      this.mSpanOffsetsSize = paramSavedState.mSpanOffsetsSize;
      this.mAnchorPosition = paramSavedState.mAnchorPosition;
      this.mVisibleAnchorPosition = paramSavedState.mVisibleAnchorPosition;
      this.mSpanOffsets = paramSavedState.mSpanOffsets;
      this.mSpanLookupSize = paramSavedState.mSpanLookupSize;
      this.mSpanLookup = paramSavedState.mSpanLookup;
      this.mReverseLayout = paramSavedState.mReverseLayout;
      this.mAnchorLayoutFromEnd = paramSavedState.mAnchorLayoutFromEnd;
      this.mLastLayoutRTL = paramSavedState.mLastLayoutRTL;
      this.mFullSpanItems = paramSavedState.mFullSpanItems;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    void invalidateAnchorPositionInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mAnchorPosition = -1;
      this.mVisibleAnchorPosition = -1;
    }
    
    void invalidateSpanInfo()
    {
      this.mSpanOffsets = null;
      this.mSpanOffsetsSize = 0;
      this.mSpanLookupSize = 0;
      this.mSpanLookup = null;
      this.mFullSpanItems = null;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.mAnchorPosition);
      paramParcel.writeInt(this.mVisibleAnchorPosition);
      paramParcel.writeInt(this.mSpanOffsetsSize);
      if (this.mSpanOffsetsSize > 0) {
        paramParcel.writeIntArray(this.mSpanOffsets);
      }
      paramParcel.writeInt(this.mSpanLookupSize);
      if (this.mSpanLookupSize > 0) {
        paramParcel.writeIntArray(this.mSpanLookup);
      }
      paramParcel.writeInt(this.mReverseLayout);
      paramParcel.writeInt(this.mAnchorLayoutFromEnd);
      paramParcel.writeInt(this.mLastLayoutRTL);
      paramParcel.writeList(this.mFullSpanItems);
    }
  }
  
  class Span
  {
    static final int INVALID_LINE = Integer.MIN_VALUE;
    int mCachedEnd = Integer.MIN_VALUE;
    int mCachedStart = Integer.MIN_VALUE;
    int mDeletedSize = 0;
    final int mIndex;
    ArrayList<View> mViews = new ArrayList();
    
    Span(int paramInt)
    {
      this.mIndex = paramInt;
    }
    
    void appendToSpan(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(paramView);
      localLayoutParams.mSpan = this;
      this.mViews.add(paramView);
      this.mCachedEnd = Integer.MIN_VALUE;
      if (this.mViews.size() == 1) {
        this.mCachedStart = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
      }
    }
    
    void cacheReferenceLineAndClear(boolean paramBoolean, int paramInt)
    {
      int i;
      if (paramBoolean) {
        i = getEndLine(Integer.MIN_VALUE);
      } else {
        i = getStartLine(Integer.MIN_VALUE);
      }
      clear();
      if (i == Integer.MIN_VALUE) {
        return;
      }
      if (((paramBoolean) && (i < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding())) || ((!paramBoolean) && (i > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()))) {
        return;
      }
      int j = i;
      if (paramInt != Integer.MIN_VALUE) {
        j = i + paramInt;
      }
      this.mCachedEnd = j;
      this.mCachedStart = j;
    }
    
    void calculateCachedEnd()
    {
      Object localObject = this.mViews;
      View localView = (View)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
      localObject = getLayoutParams(localView);
      this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
      if (((StaggeredGridLayoutManager.LayoutParams)localObject).mFullSpan)
      {
        localObject = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(((StaggeredGridLayoutManager.LayoutParams)localObject).getViewLayoutPosition());
        if ((localObject != null) && (((StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem)localObject).mGapDir == 1)) {
          this.mCachedEnd += ((StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem)localObject).getGapForSpan(this.mIndex);
        }
      }
    }
    
    void calculateCachedStart()
    {
      View localView = (View)this.mViews.get(0);
      Object localObject = getLayoutParams(localView);
      this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
      if (((StaggeredGridLayoutManager.LayoutParams)localObject).mFullSpan)
      {
        localObject = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(((StaggeredGridLayoutManager.LayoutParams)localObject).getViewLayoutPosition());
        if ((localObject != null) && (((StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem)localObject).mGapDir == -1)) {
          this.mCachedStart -= ((StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem)localObject).getGapForSpan(this.mIndex);
        }
      }
    }
    
    void clear()
    {
      this.mViews.clear();
      invalidateCache();
      this.mDeletedSize = 0;
    }
    
    public int findFirstCompletelyVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, true);
      } else {
        i = findOneVisibleChild(0, this.mViews.size(), true);
      }
      return i;
    }
    
    public int findFirstPartiallyVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
      } else {
        i = findOnePartiallyVisibleChild(0, this.mViews.size(), true);
      }
      return i;
    }
    
    public int findFirstVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, false);
      } else {
        i = findOneVisibleChild(0, this.mViews.size(), false);
      }
      return i;
    }
    
    public int findLastCompletelyVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(0, this.mViews.size(), true);
      } else {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, true);
      }
      return i;
    }
    
    public int findLastPartiallyVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOnePartiallyVisibleChild(0, this.mViews.size(), true);
      } else {
        i = findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
      }
      return i;
    }
    
    public int findLastVisibleItemPosition()
    {
      int i;
      if (StaggeredGridLayoutManager.this.mReverseLayout) {
        i = findOneVisibleChild(0, this.mViews.size(), false);
      } else {
        i = findOneVisibleChild(this.mViews.size() - 1, -1, false);
      }
      return i;
    }
    
    int findOnePartiallyOrCompletelyVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      int n = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
      int m = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
      int i;
      if (paramInt2 > paramInt1) {
        i = 1;
      } else {
        i = -1;
      }
      while (paramInt1 != paramInt2)
      {
        View localView = (View)this.mViews.get(paramInt1);
        int i2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(localView);
        int i1 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(localView);
        int k = 0;
        int j;
        if (paramBoolean3 ? i2 <= m : i2 < m) {
          j = 1;
        } else {
          j = 0;
        }
        if (paramBoolean3 ? i1 >= n : i1 > n) {
          k = 1;
        }
        if ((j != 0) && (k != 0)) {
          if ((paramBoolean1) && (paramBoolean2))
          {
            if ((i2 >= n) && (i1 <= m)) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
          }
          else
          {
            if (paramBoolean2) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
            if ((i2 < n) || (i1 > m)) {
              return StaggeredGridLayoutManager.this.getPosition(localView);
            }
          }
        }
        paramInt1 += i;
      }
      return -1;
    }
    
    int findOnePartiallyVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      return findOnePartiallyOrCompletelyVisibleChild(paramInt1, paramInt2, false, false, paramBoolean);
    }
    
    int findOneVisibleChild(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      return findOnePartiallyOrCompletelyVisibleChild(paramInt1, paramInt2, paramBoolean, true, false);
    }
    
    public int getDeletedSize()
    {
      return this.mDeletedSize;
    }
    
    int getEndLine()
    {
      int i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      calculateCachedEnd();
      return this.mCachedEnd;
    }
    
    int getEndLine(int paramInt)
    {
      int i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      if (this.mViews.size() == 0) {
        return paramInt;
      }
      calculateCachedEnd();
      return this.mCachedEnd;
    }
    
    public View getFocusableViewAfter(int paramInt1, int paramInt2)
    {
      Object localObject1 = null;
      Object localObject2 = null;
      if (paramInt2 == -1)
      {
        int i = this.mViews.size();
        paramInt2 = 0;
        localObject1 = localObject2;
        while (paramInt2 < i)
        {
          localObject2 = (View)this.mViews.get(paramInt2);
          if (((StaggeredGridLayoutManager.this.mReverseLayout) && (StaggeredGridLayoutManager.this.getPosition((View)localObject2) <= paramInt1)) || ((!StaggeredGridLayoutManager.this.mReverseLayout) && (StaggeredGridLayoutManager.this.getPosition((View)localObject2) >= paramInt1)) || (!((View)localObject2).hasFocusable())) {
            break;
          }
          localObject1 = localObject2;
          paramInt2++;
        }
        localObject2 = localObject1;
      }
      else
      {
        for (paramInt2 = this.mViews.size() - 1;; paramInt2--)
        {
          localObject2 = localObject1;
          if (paramInt2 < 0) {
            break;
          }
          View localView = (View)this.mViews.get(paramInt2);
          if (StaggeredGridLayoutManager.this.mReverseLayout)
          {
            localObject2 = localObject1;
            if (StaggeredGridLayoutManager.this.getPosition(localView) >= paramInt1) {
              break;
            }
          }
          if ((!StaggeredGridLayoutManager.this.mReverseLayout) && (StaggeredGridLayoutManager.this.getPosition(localView) <= paramInt1))
          {
            localObject2 = localObject1;
            break;
          }
          localObject2 = localObject1;
          if (!localView.hasFocusable()) {
            break;
          }
          localObject1 = localView;
        }
      }
      return (View)localObject2;
    }
    
    StaggeredGridLayoutManager.LayoutParams getLayoutParams(View paramView)
    {
      return (StaggeredGridLayoutManager.LayoutParams)paramView.getLayoutParams();
    }
    
    int getStartLine()
    {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      calculateCachedStart();
      return this.mCachedStart;
    }
    
    int getStartLine(int paramInt)
    {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE) {
        return i;
      }
      if (this.mViews.size() == 0) {
        return paramInt;
      }
      calculateCachedStart();
      return this.mCachedStart;
    }
    
    void invalidateCache()
    {
      this.mCachedStart = Integer.MIN_VALUE;
      this.mCachedEnd = Integer.MIN_VALUE;
    }
    
    void onOffset(int paramInt)
    {
      int i = this.mCachedStart;
      if (i != Integer.MIN_VALUE) {
        this.mCachedStart = (i + paramInt);
      }
      i = this.mCachedEnd;
      if (i != Integer.MIN_VALUE) {
        this.mCachedEnd = (i + paramInt);
      }
    }
    
    void popEnd()
    {
      int i = this.mViews.size();
      View localView = (View)this.mViews.remove(i - 1);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      localLayoutParams.mSpan = null;
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      }
      if (i == 1) {
        this.mCachedStart = Integer.MIN_VALUE;
      }
      this.mCachedEnd = Integer.MIN_VALUE;
    }
    
    void popStart()
    {
      View localView = (View)this.mViews.remove(0);
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(localView);
      localLayoutParams.mSpan = null;
      if (this.mViews.size() == 0) {
        this.mCachedEnd = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(localView);
      }
      this.mCachedStart = Integer.MIN_VALUE;
    }
    
    void prependToSpan(View paramView)
    {
      StaggeredGridLayoutManager.LayoutParams localLayoutParams = getLayoutParams(paramView);
      localLayoutParams.mSpan = this;
      this.mViews.add(0, paramView);
      this.mCachedStart = Integer.MIN_VALUE;
      if (this.mViews.size() == 1) {
        this.mCachedEnd = Integer.MIN_VALUE;
      }
      if ((localLayoutParams.isItemRemoved()) || (localLayoutParams.isItemChanged())) {
        this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(paramView);
      }
    }
    
    void setLine(int paramInt)
    {
      this.mCachedStart = paramInt;
      this.mCachedEnd = paramInt;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/recyclerview/widget/StaggeredGridLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */