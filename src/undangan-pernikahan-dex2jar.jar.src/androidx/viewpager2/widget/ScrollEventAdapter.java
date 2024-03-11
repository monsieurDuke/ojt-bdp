package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class ScrollEventAdapter
  extends RecyclerView.OnScrollListener
{
  private static final int NO_POSITION = -1;
  private static final int STATE_IDLE = 0;
  private static final int STATE_IN_PROGRESS_FAKE_DRAG = 4;
  private static final int STATE_IN_PROGRESS_IMMEDIATE_SCROLL = 3;
  private static final int STATE_IN_PROGRESS_MANUAL_DRAG = 1;
  private static final int STATE_IN_PROGRESS_SMOOTH_SCROLL = 2;
  private int mAdapterState;
  private ViewPager2.OnPageChangeCallback mCallback;
  private boolean mDataSetChangeHappened;
  private boolean mDispatchSelected;
  private int mDragStartPosition;
  private boolean mFakeDragging;
  private final LinearLayoutManager mLayoutManager;
  private final RecyclerView mRecyclerView;
  private boolean mScrollHappened;
  private int mScrollState;
  private ScrollEventValues mScrollValues;
  private int mTarget;
  private final ViewPager2 mViewPager;
  
  ScrollEventAdapter(ViewPager2 paramViewPager2)
  {
    this.mViewPager = paramViewPager2;
    paramViewPager2 = paramViewPager2.mRecyclerView;
    this.mRecyclerView = paramViewPager2;
    this.mLayoutManager = ((LinearLayoutManager)paramViewPager2.getLayoutManager());
    this.mScrollValues = new ScrollEventValues();
    resetState();
  }
  
  private void dispatchScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    ViewPager2.OnPageChangeCallback localOnPageChangeCallback = this.mCallback;
    if (localOnPageChangeCallback != null) {
      localOnPageChangeCallback.onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  private void dispatchSelected(int paramInt)
  {
    ViewPager2.OnPageChangeCallback localOnPageChangeCallback = this.mCallback;
    if (localOnPageChangeCallback != null) {
      localOnPageChangeCallback.onPageSelected(paramInt);
    }
  }
  
  private void dispatchStateChanged(int paramInt)
  {
    if ((this.mAdapterState == 3) && (this.mScrollState == 0)) {
      return;
    }
    if (this.mScrollState == paramInt) {
      return;
    }
    this.mScrollState = paramInt;
    ViewPager2.OnPageChangeCallback localOnPageChangeCallback = this.mCallback;
    if (localOnPageChangeCallback != null) {
      localOnPageChangeCallback.onPageScrollStateChanged(paramInt);
    }
  }
  
  private int getPosition()
  {
    return this.mLayoutManager.findFirstVisibleItemPosition();
  }
  
  private boolean isInAnyDraggingState()
  {
    int i = this.mAdapterState;
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (i != 1) {
      if (i == 4) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  private void resetState()
  {
    this.mAdapterState = 0;
    this.mScrollState = 0;
    this.mScrollValues.reset();
    this.mDragStartPosition = -1;
    this.mTarget = -1;
    this.mDispatchSelected = false;
    this.mScrollHappened = false;
    this.mFakeDragging = false;
    this.mDataSetChangeHappened = false;
  }
  
  private void startDrag(boolean paramBoolean)
  {
    this.mFakeDragging = paramBoolean;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 1;
    }
    this.mAdapterState = i;
    int i = this.mTarget;
    if (i != -1)
    {
      this.mDragStartPosition = i;
      this.mTarget = -1;
    }
    else if (this.mDragStartPosition == -1)
    {
      this.mDragStartPosition = getPosition();
    }
    dispatchStateChanged(1);
  }
  
  private void updateScrollEventValues()
  {
    Object localObject1 = this.mScrollValues;
    ((ScrollEventValues)localObject1).mPosition = this.mLayoutManager.findFirstVisibleItemPosition();
    if (((ScrollEventValues)localObject1).mPosition == -1)
    {
      ((ScrollEventValues)localObject1).reset();
      return;
    }
    View localView = this.mLayoutManager.findViewByPosition(((ScrollEventValues)localObject1).mPosition);
    if (localView == null)
    {
      ((ScrollEventValues)localObject1).reset();
      return;
    }
    int i3 = this.mLayoutManager.getLeftDecorationWidth(localView);
    int i2 = this.mLayoutManager.getRightDecorationWidth(localView);
    int i1 = this.mLayoutManager.getTopDecorationHeight(localView);
    int n = this.mLayoutManager.getBottomDecorationHeight(localView);
    Object localObject2 = localView.getLayoutParams();
    int m = i3;
    int k = i2;
    int i = i1;
    int j = n;
    if ((localObject2 instanceof ViewGroup.MarginLayoutParams))
    {
      localObject2 = (ViewGroup.MarginLayoutParams)localObject2;
      m = i3 + ((ViewGroup.MarginLayoutParams)localObject2).leftMargin;
      k = i2 + ((ViewGroup.MarginLayoutParams)localObject2).rightMargin;
      i = i1 + ((ViewGroup.MarginLayoutParams)localObject2).topMargin;
      j = n + ((ViewGroup.MarginLayoutParams)localObject2).bottomMargin;
    }
    i2 = localView.getHeight();
    i1 = localView.getWidth();
    if (this.mLayoutManager.getOrientation() == 0) {
      n = 1;
    } else {
      n = 0;
    }
    if (n != 0)
    {
      k = i1 + m + k;
      m = localView.getLeft() - m - this.mRecyclerView.getPaddingLeft();
      j = k;
      i = m;
      if (this.mViewPager.isRtl())
      {
        i = -m;
        j = k;
      }
    }
    else
    {
      j = i2 + i + j;
      i = localView.getTop() - i - this.mRecyclerView.getPaddingTop();
    }
    ((ScrollEventValues)localObject1).mOffsetPx = (-i);
    if (((ScrollEventValues)localObject1).mOffsetPx < 0)
    {
      if (new AnimateLayoutChangeDetector(this.mLayoutManager).mayHaveInterferingAnimations()) {
        throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
      }
      localObject1 = String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", new Object[] { Integer.valueOf(((ScrollEventValues)localObject1).mOffsetPx) });
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      throw new IllegalStateException((String)localObject1);
    }
    float f;
    if (j == 0) {
      f = 0.0F;
    } else {
      f = ((ScrollEventValues)localObject1).mOffsetPx / j;
    }
    ((ScrollEventValues)localObject1).mOffset = f;
  }
  
  double getRelativeScrollPosition()
  {
    updateScrollEventValues();
    return this.mScrollValues.mPosition + this.mScrollValues.mOffset;
  }
  
  int getScrollState()
  {
    return this.mScrollState;
  }
  
  boolean isDragging()
  {
    int i = this.mScrollState;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  boolean isFakeDragging()
  {
    return this.mFakeDragging;
  }
  
  boolean isIdle()
  {
    boolean bool;
    if (this.mScrollState == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void notifyBeginFakeDrag()
  {
    this.mAdapterState = 4;
    startDrag(true);
  }
  
  void notifyDataSetChangeHappened()
  {
    this.mDataSetChangeHappened = true;
  }
  
  void notifyEndFakeDrag()
  {
    if ((isDragging()) && (!this.mFakeDragging)) {
      return;
    }
    this.mFakeDragging = false;
    updateScrollEventValues();
    if (this.mScrollValues.mOffsetPx == 0)
    {
      if (this.mScrollValues.mPosition != this.mDragStartPosition) {
        dispatchSelected(this.mScrollValues.mPosition);
      }
      dispatchStateChanged(0);
      resetState();
    }
    else
    {
      dispatchStateChanged(2);
    }
  }
  
  void notifyProgrammaticScroll(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      i = 2;
    } else {
      i = 3;
    }
    this.mAdapterState = i;
    int i = 0;
    this.mFakeDragging = false;
    if (this.mTarget != paramInt) {
      i = 1;
    }
    this.mTarget = paramInt;
    dispatchStateChanged(2);
    if (i != 0) {
      dispatchSelected(paramInt);
    }
  }
  
  public void onScrollStateChanged(RecyclerView paramRecyclerView, int paramInt)
  {
    if (((this.mAdapterState != 1) || (this.mScrollState != 1)) && (paramInt == 1))
    {
      startDrag(false);
      return;
    }
    if ((isInAnyDraggingState()) && (paramInt == 2))
    {
      if (this.mScrollHappened)
      {
        dispatchStateChanged(2);
        this.mDispatchSelected = true;
      }
      return;
    }
    if ((isInAnyDraggingState()) && (paramInt == 0))
    {
      int i = 0;
      updateScrollEventValues();
      if (!this.mScrollHappened)
      {
        if (this.mScrollValues.mPosition != -1) {
          dispatchScrolled(this.mScrollValues.mPosition, 0.0F, 0);
        }
        i = 1;
      }
      else if (this.mScrollValues.mOffsetPx == 0)
      {
        int j = 1;
        i = j;
        if (this.mDragStartPosition != this.mScrollValues.mPosition)
        {
          dispatchSelected(this.mScrollValues.mPosition);
          i = j;
        }
      }
      if (i != 0)
      {
        dispatchStateChanged(0);
        resetState();
      }
    }
    if ((this.mAdapterState == 2) && (paramInt == 0) && (this.mDataSetChangeHappened))
    {
      updateScrollEventValues();
      if (this.mScrollValues.mOffsetPx == 0)
      {
        if (this.mTarget != this.mScrollValues.mPosition)
        {
          if (this.mScrollValues.mPosition == -1) {
            paramInt = 0;
          } else {
            paramInt = this.mScrollValues.mPosition;
          }
          dispatchSelected(paramInt);
        }
        dispatchStateChanged(0);
        resetState();
      }
    }
  }
  
  public void onScrolled(RecyclerView paramRecyclerView, int paramInt1, int paramInt2)
  {
    this.mScrollHappened = true;
    updateScrollEventValues();
    if (this.mDispatchSelected)
    {
      this.mDispatchSelected = false;
      if (paramInt2 <= 0) {
        if (paramInt2 == 0)
        {
          int i;
          if (paramInt1 < 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == this.mViewPager.isRtl()) {}
        }
        else
        {
          paramInt1 = 0;
          break label64;
        }
      }
      paramInt1 = 1;
      label64:
      if ((paramInt1 != 0) && (this.mScrollValues.mOffsetPx != 0)) {
        paramInt1 = this.mScrollValues.mPosition + 1;
      } else {
        paramInt1 = this.mScrollValues.mPosition;
      }
      this.mTarget = paramInt1;
      if (this.mDragStartPosition != paramInt1) {
        dispatchSelected(paramInt1);
      }
    }
    else if (this.mAdapterState == 0)
    {
      paramInt1 = this.mScrollValues.mPosition;
      if (paramInt1 == -1) {
        paramInt1 = 0;
      }
      dispatchSelected(paramInt1);
    }
    if (this.mScrollValues.mPosition == -1) {
      paramInt1 = 0;
    } else {
      paramInt1 = this.mScrollValues.mPosition;
    }
    dispatchScrolled(paramInt1, this.mScrollValues.mOffset, this.mScrollValues.mOffsetPx);
    paramInt1 = this.mScrollValues.mPosition;
    paramInt2 = this.mTarget;
    if (((paramInt1 == paramInt2) || (paramInt2 == -1)) && (this.mScrollValues.mOffsetPx == 0) && (this.mScrollState != 1))
    {
      dispatchStateChanged(0);
      resetState();
    }
  }
  
  void setOnPageChangeCallback(ViewPager2.OnPageChangeCallback paramOnPageChangeCallback)
  {
    this.mCallback = paramOnPageChangeCallback;
  }
  
  private static final class ScrollEventValues
  {
    float mOffset;
    int mOffsetPx;
    int mPosition;
    
    void reset()
    {
      this.mPosition = -1;
      this.mOffset = 0.0F;
      this.mOffsetPx = 0;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager2/widget/ScrollEventAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */