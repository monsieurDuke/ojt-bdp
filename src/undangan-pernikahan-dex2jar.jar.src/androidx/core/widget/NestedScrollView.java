package androidx.core.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.R.attr;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import java.util.ArrayList;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class NestedScrollView
  extends FrameLayout
  implements NestedScrollingParent3, NestedScrollingChild3, ScrollingView
{
  private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
  static final int ANIMATED_SCROLL_GAP = 250;
  private static final int DEFAULT_SMOOTH_SCROLL_DURATION = 250;
  private static final int INVALID_POINTER = -1;
  static final float MAX_SCROLL_FACTOR = 0.5F;
  private static final int[] SCROLLVIEW_STYLEABLE = { 16843130 };
  private static final String TAG = "NestedScrollView";
  private int mActivePointerId = -1;
  private final NestedScrollingChildHelper mChildHelper;
  private View mChildToScrollTo = null;
  public EdgeEffect mEdgeGlowBottom;
  public EdgeEffect mEdgeGlowTop;
  private boolean mFillViewport;
  private boolean mIsBeingDragged = false;
  private boolean mIsLaidOut = false;
  private boolean mIsLayoutDirty = true;
  private int mLastMotionY;
  private long mLastScroll;
  private int mLastScrollerY;
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private int mNestedYOffset;
  private OnScrollChangeListener mOnScrollChangeListener;
  private final NestedScrollingParentHelper mParentHelper;
  private SavedState mSavedState;
  private final int[] mScrollConsumed = new int[2];
  private final int[] mScrollOffset = new int[2];
  private OverScroller mScroller;
  private boolean mSmoothScrollingEnabled = true;
  private final Rect mTempRect = new Rect();
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;
  private float mVerticalScrollFactor;
  
  public NestedScrollView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.nestedScrollViewStyle);
  }
  
  public NestedScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mEdgeGlowTop = EdgeEffectCompat.create(paramContext, paramAttributeSet);
    this.mEdgeGlowBottom = EdgeEffectCompat.create(paramContext, paramAttributeSet);
    initScrollView();
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, SCROLLVIEW_STYLEABLE, paramInt, 0);
    setFillViewport(paramContext.getBoolean(0, false));
    paramContext.recycle();
    this.mParentHelper = new NestedScrollingParentHelper(this);
    this.mChildHelper = new NestedScrollingChildHelper(this);
    setNestedScrollingEnabled(true);
    ViewCompat.setAccessibilityDelegate(this, ACCESSIBILITY_DELEGATE);
  }
  
  private void abortAnimatedScroll()
  {
    this.mScroller.abortAnimation();
    stopNestedScroll(1);
  }
  
  private boolean canOverScroll()
  {
    int i = getOverScrollMode();
    boolean bool = true;
    if ((i != 0) && ((i != 1) || (getScrollRange() <= 0))) {
      bool = false;
    }
    return bool;
  }
  
  private boolean canScroll()
  {
    int i = getChildCount();
    boolean bool = false;
    if (i > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      if (localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin > getHeight() - getPaddingTop() - getPaddingBottom()) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  private static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 < paramInt3) && (paramInt1 >= 0))
    {
      if (paramInt2 + paramInt1 > paramInt3) {
        return paramInt3 - paramInt2;
      }
      return paramInt1;
    }
    return 0;
  }
  
  private void doScrollY(int paramInt)
  {
    if (paramInt != 0) {
      if (this.mSmoothScrollingEnabled) {
        smoothScrollBy(0, paramInt);
      } else {
        scrollBy(0, paramInt);
      }
    }
  }
  
  private boolean edgeEffectFling(int paramInt)
  {
    boolean bool = true;
    if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0F) {
      this.mEdgeGlowTop.onAbsorb(paramInt);
    } else if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) != 0.0F) {
      this.mEdgeGlowBottom.onAbsorb(-paramInt);
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void endDrag()
  {
    this.mIsBeingDragged = false;
    recycleVelocityTracker();
    stopNestedScroll(0);
    this.mEdgeGlowTop.onRelease();
    this.mEdgeGlowBottom.onRelease();
  }
  
  private View findFocusableViewInBounds(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = getFocusables(2);
    Object localObject2 = null;
    int m = 0;
    int i1 = localArrayList.size();
    int k = 0;
    while (k < i1)
    {
      View localView = (View)localArrayList.get(k);
      int i2 = localView.getTop();
      int i3 = localView.getBottom();
      Object localObject1 = localObject2;
      int j = m;
      if (paramInt1 < i3)
      {
        localObject1 = localObject2;
        j = m;
        if (i2 < paramInt2)
        {
          int n = 0;
          int i;
          if ((paramInt1 < i2) && (i3 < paramInt2)) {
            i = 1;
          } else {
            i = 0;
          }
          if (localObject2 == null)
          {
            localObject1 = localView;
            j = i;
          }
          else
          {
            if (((paramBoolean) && (i2 < ((View)localObject2).getTop())) || ((!paramBoolean) && (i3 > ((View)localObject2).getBottom()))) {
              n = 1;
            }
            if (m != 0)
            {
              localObject1 = localObject2;
              j = m;
              if (i != 0)
              {
                localObject1 = localObject2;
                j = m;
                if (n != 0)
                {
                  localObject1 = localView;
                  j = m;
                }
              }
            }
            else if (i != 0)
            {
              localObject1 = localView;
              j = 1;
            }
            else
            {
              localObject1 = localObject2;
              j = m;
              if (n != 0)
              {
                localObject1 = localView;
                j = m;
              }
            }
          }
        }
      }
      k++;
      localObject2 = localObject1;
      m = j;
    }
    return (View)localObject2;
  }
  
  private float getVerticalScrollFactorCompat()
  {
    if (this.mVerticalScrollFactor == 0.0F)
    {
      TypedValue localTypedValue = new TypedValue();
      Context localContext = getContext();
      if (localContext.getTheme().resolveAttribute(16842829, localTypedValue, true)) {
        this.mVerticalScrollFactor = localTypedValue.getDimension(localContext.getResources().getDisplayMetrics());
      } else {
        throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
      }
    }
    return this.mVerticalScrollFactor;
  }
  
  private boolean inChild(int paramInt1, int paramInt2)
  {
    int i = getChildCount();
    boolean bool = false;
    if (i > 0)
    {
      i = getScrollY();
      View localView = getChildAt(0);
      if ((paramInt2 >= localView.getTop() - i) && (paramInt2 < localView.getBottom() - i) && (paramInt1 >= localView.getLeft()) && (paramInt1 < localView.getRight())) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  private void initOrResetVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      localVelocityTracker.clear();
    }
  }
  
  private void initScrollView()
  {
    this.mScroller = new OverScroller(getContext());
    setFocusable(true);
    setDescendantFocusability(262144);
    setWillNotDraw(false);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
  }
  
  private void initVelocityTrackerIfNotExists()
  {
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
  }
  
  private boolean isOffScreen(View paramView)
  {
    return isWithinDeltaOfScreen(paramView, 0, getHeight()) ^ true;
  }
  
  private static boolean isViewDescendantOf(View paramView1, View paramView2)
  {
    boolean bool = true;
    if (paramView1 == paramView2) {
      return true;
    }
    paramView1 = paramView1.getParent();
    if ((!(paramView1 instanceof ViewGroup)) || (!isViewDescendantOf((View)paramView1, paramView2))) {
      bool = false;
    }
    return bool;
  }
  
  private boolean isWithinDeltaOfScreen(View paramView, int paramInt1, int paramInt2)
  {
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    boolean bool;
    if ((this.mTempRect.bottom + paramInt1 >= getScrollY()) && (this.mTempRect.top - paramInt1 <= getScrollY() + paramInt2)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void onNestedScrollInternal(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = getScrollY();
    scrollBy(0, paramInt1);
    i = getScrollY() - i;
    if (paramArrayOfInt != null) {
      paramArrayOfInt[1] += i;
    }
    this.mChildHelper.dispatchNestedScroll(0, i, 0, paramInt1 - i, null, paramInt2, paramArrayOfInt);
  }
  
  private void onSecondaryPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.mLastMotionY = ((int)paramMotionEvent.getY(i));
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      paramMotionEvent = this.mVelocityTracker;
      if (paramMotionEvent != null) {
        paramMotionEvent.clear();
      }
    }
  }
  
  private void recycleVelocityTracker()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private int releaseVerticalGlow(int paramInt, float paramFloat)
  {
    float f1 = 0.0F;
    float f2 = paramFloat / getWidth();
    float f3 = paramInt / getHeight();
    if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0F)
    {
      f1 = -EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, -f3, f2);
      paramFloat = f1;
      if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) == 0.0F)
      {
        this.mEdgeGlowTop.onRelease();
        paramFloat = f1;
      }
    }
    else
    {
      paramFloat = f1;
      if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) != 0.0F)
      {
        f1 = EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, f3, 1.0F - f2);
        paramFloat = f1;
        if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) == 0.0F)
        {
          this.mEdgeGlowBottom.onRelease();
          paramFloat = f1;
        }
      }
    }
    paramInt = Math.round(getHeight() * paramFloat);
    if (paramInt != 0) {
      invalidate();
    }
    return paramInt;
  }
  
  private void runAnimatedScroll(boolean paramBoolean)
  {
    if (paramBoolean) {
      startNestedScroll(2, 1);
    } else {
      stopNestedScroll(1);
    }
    this.mLastScrollerY = getScrollY();
    ViewCompat.postInvalidateOnAnimation(this);
  }
  
  private boolean scrollAndFocus(int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool2 = true;
    int j = getHeight();
    int i = getScrollY();
    j = i + j;
    boolean bool1;
    if (paramInt1 == 33) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    View localView = findFocusableViewInBounds(bool1, paramInt2, paramInt3);
    Object localObject = localView;
    if (localView == null) {
      localObject = this;
    }
    if ((paramInt2 >= i) && (paramInt3 <= j))
    {
      bool1 = false;
    }
    else
    {
      if (bool1) {
        paramInt2 -= i;
      } else {
        paramInt2 = paramInt3 - j;
      }
      doScrollY(paramInt2);
      bool1 = bool2;
    }
    if (localObject != findFocus()) {
      ((View)localObject).requestFocus(paramInt1);
    }
    return bool1;
  }
  
  private void scrollToChild(View paramView)
  {
    paramView.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(paramView, this.mTempRect);
    int i = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
    if (i != 0) {
      scrollBy(0, i);
    }
  }
  
  private boolean scrollToChildRect(Rect paramRect, boolean paramBoolean)
  {
    int i = computeScrollDeltaToGetChildRectOnScreen(paramRect);
    boolean bool;
    if (i != 0) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      if (paramBoolean) {
        scrollBy(0, i);
      } else {
        smoothScrollBy(0, i);
      }
    }
    return bool;
  }
  
  private void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (getChildCount() == 0) {
      return;
    }
    if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      int k = localView.getHeight();
      int i1 = localLayoutParams.topMargin;
      int i = localLayoutParams.bottomMargin;
      int m = getHeight();
      int n = getPaddingTop();
      int j = getPaddingBottom();
      paramInt1 = getScrollY();
      paramInt2 = Math.max(0, Math.min(paramInt1 + paramInt2, Math.max(0, k + i1 + i - (m - n - j))));
      this.mScroller.startScroll(getScrollX(), paramInt1, 0, paramInt2 - paramInt1, paramInt3);
      runAnimatedScroll(paramBoolean);
    }
    else
    {
      if (!this.mScroller.isFinished()) {
        abortAnimatedScroll();
      }
      scrollBy(paramInt1, paramInt2);
    }
    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
  }
  
  private boolean stopGlowAnimations(MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    if (EdgeEffectCompat.getDistance(this.mEdgeGlowTop) != 0.0F)
    {
      EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, 0.0F, paramMotionEvent.getX() / getWidth());
      bool = true;
    }
    if (EdgeEffectCompat.getDistance(this.mEdgeGlowBottom) != 0.0F)
    {
      EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, 0.0F, 1.0F - paramMotionEvent.getX() / getWidth());
      bool = true;
    }
    return bool;
  }
  
  public void addView(View paramView)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramInt);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (getChildCount() <= 0)
    {
      super.addView(paramView, paramLayoutParams);
      return;
    }
    throw new IllegalStateException("ScrollView can host only one direct child");
  }
  
  public boolean arrowScroll(int paramInt)
  {
    Object localObject2 = findFocus();
    Object localObject1 = localObject2;
    if (localObject2 == this) {
      localObject1 = null;
    }
    localObject2 = FocusFinder.getInstance().findNextFocus(this, (View)localObject1, paramInt);
    int k = getMaxScrollAmount();
    if ((localObject2 != null) && (isWithinDeltaOfScreen((View)localObject2, k, getHeight())))
    {
      ((View)localObject2).getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords((View)localObject2, this.mTempRect);
      doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
      ((View)localObject2).requestFocus(paramInt);
    }
    else
    {
      int j = k;
      int i;
      if ((paramInt == 33) && (getScrollY() < j))
      {
        i = getScrollY();
      }
      else
      {
        i = j;
        if (paramInt == 130)
        {
          i = j;
          if (getChildCount() > 0)
          {
            View localView = getChildAt(0);
            localObject2 = (FrameLayout.LayoutParams)localView.getLayoutParams();
            i = Math.min(localView.getBottom() + ((FrameLayout.LayoutParams)localObject2).bottomMargin - (getScrollY() + getHeight() - getPaddingBottom()), k);
          }
        }
      }
      if (i == 0) {
        return false;
      }
      if (paramInt != 130) {
        i = -i;
      }
      doScrollY(i);
    }
    if ((localObject1 != null) && (((View)localObject1).isFocused()) && (isOffScreen((View)localObject1)))
    {
      paramInt = getDescendantFocusability();
      setDescendantFocusability(131072);
      requestFocus();
      setDescendantFocusability(paramInt);
    }
    return true;
  }
  
  public int computeHorizontalScrollExtent()
  {
    return super.computeHorizontalScrollExtent();
  }
  
  public int computeHorizontalScrollOffset()
  {
    return super.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange()
  {
    return super.computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    if (this.mScroller.isFinished()) {
      return;
    }
    this.mScroller.computeScrollOffset();
    int j = this.mScroller.getCurrY();
    int i = j - this.mLastScrollerY;
    this.mLastScrollerY = j;
    int[] arrayOfInt = this.mScrollConsumed;
    int k = 0;
    arrayOfInt[1] = 0;
    dispatchNestedPreScroll(0, i, arrayOfInt, null, 1);
    j = i - this.mScrollConsumed[1];
    int m = getScrollRange();
    i = j;
    if (j != 0)
    {
      i = getScrollY();
      overScrollByCompat(0, j, getScrollX(), i, 0, m, 0, 0, false);
      i = getScrollY() - i;
      j -= i;
      arrayOfInt = this.mScrollConsumed;
      arrayOfInt[1] = 0;
      dispatchNestedScroll(0, i, 0, j, this.mScrollOffset, 1, arrayOfInt);
      i = j - this.mScrollConsumed[1];
    }
    if (i != 0)
    {
      int n = getOverScrollMode();
      if (n != 0)
      {
        j = k;
        if (n == 1)
        {
          j = k;
          if (m <= 0) {}
        }
      }
      else
      {
        j = 1;
      }
      if (j != 0) {
        if (i < 0)
        {
          if (this.mEdgeGlowTop.isFinished()) {
            this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
          }
        }
        else if (this.mEdgeGlowBottom.isFinished()) {
          this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
        }
      }
      abortAnimatedScroll();
    }
    if (!this.mScroller.isFinished()) {
      ViewCompat.postInvalidateOnAnimation(this);
    } else {
      stopNestedScroll(1);
    }
  }
  
  protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect)
  {
    if (getChildCount() == 0) {
      return 0;
    }
    int i1 = getHeight();
    int i = getScrollY();
    int k = i + i1;
    int m = getVerticalFadingEdgeLength();
    int j = i;
    if (paramRect.top > 0) {
      j = i + m;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    i = k;
    if (paramRect.bottom < localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin) {
      i = k - m;
    }
    m = i;
    int n = 0;
    if ((paramRect.bottom > m) && (paramRect.top > j))
    {
      if (paramRect.height() > i1) {
        i = 0 + (paramRect.top - j);
      } else {
        i = 0 + (paramRect.bottom - m);
      }
      i = Math.min(i, localView.getBottom() + localLayoutParams.bottomMargin - k);
    }
    for (;;)
    {
      break;
      i = n;
      if (paramRect.top < j)
      {
        i = n;
        if (paramRect.bottom < m)
        {
          if (paramRect.height() > i1) {
            i = 0 - (m - paramRect.bottom);
          } else {
            i = 0 - (j - paramRect.top);
          }
          i = Math.max(i, -getScrollY());
        }
      }
    }
    return i;
  }
  
  public int computeVerticalScrollExtent()
  {
    return super.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset()
  {
    return Math.max(0, super.computeVerticalScrollOffset());
  }
  
  public int computeVerticalScrollRange()
  {
    int j = getChildCount();
    int i = getHeight() - getPaddingBottom() - getPaddingTop();
    if (j == 0) {
      return i;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    j = localView.getBottom() + localLayoutParams.bottomMargin;
    int k = getScrollY();
    int m = Math.max(0, j - i);
    if (k < 0)
    {
      i = j - k;
    }
    else
    {
      i = j;
      if (k > m) {
        i = j + (k - m);
      }
    }
    return i;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool;
    if ((!super.dispatchKeyEvent(paramKeyEvent)) && (!executeKeyEvent(paramKeyEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return this.mChildHelper.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2)
  {
    return this.mChildHelper.dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, 0);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3)
  {
    return this.mChildHelper.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramInt3);
  }
  
  public void dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int paramInt5, int[] paramArrayOfInt2)
  {
    this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramInt5, paramArrayOfInt2);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5)
  {
    return this.mChildHelper.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int i2 = getScrollY();
    int i3;
    int k;
    int i1;
    int i;
    int n;
    int j;
    int m;
    if (!this.mEdgeGlowTop.isFinished())
    {
      i3 = paramCanvas.save();
      k = getWidth();
      i1 = getHeight();
      i = 0;
      n = Math.min(0, i2);
      if (Build.VERSION.SDK_INT >= 21)
      {
        j = k;
        if (!Api21Impl.getClipToPadding(this)) {}
      }
      else
      {
        j = k - (getPaddingLeft() + getPaddingRight());
        i = 0 + getPaddingLeft();
      }
      m = i1;
      k = n;
      if (Build.VERSION.SDK_INT >= 21)
      {
        m = i1;
        k = n;
        if (Api21Impl.getClipToPadding(this))
        {
          m = i1 - (getPaddingTop() + getPaddingBottom());
          k = n + getPaddingTop();
        }
      }
      paramCanvas.translate(i, k);
      this.mEdgeGlowTop.setSize(j, m);
      if (this.mEdgeGlowTop.draw(paramCanvas)) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      paramCanvas.restoreToCount(i3);
    }
    if (!this.mEdgeGlowBottom.isFinished())
    {
      i3 = paramCanvas.save();
      k = getWidth();
      i1 = getHeight();
      j = 0;
      n = Math.max(getScrollRange(), i2) + i1;
      if (Build.VERSION.SDK_INT >= 21)
      {
        i = k;
        if (!Api21Impl.getClipToPadding(this)) {}
      }
      else
      {
        i = k - (getPaddingLeft() + getPaddingRight());
        j = 0 + getPaddingLeft();
      }
      m = i1;
      k = n;
      if (Build.VERSION.SDK_INT >= 21)
      {
        m = i1;
        k = n;
        if (Api21Impl.getClipToPadding(this))
        {
          m = i1 - (getPaddingTop() + getPaddingBottom());
          k = n - getPaddingBottom();
        }
      }
      paramCanvas.translate(j - i, k);
      paramCanvas.rotate(180.0F, i, 0.0F);
      this.mEdgeGlowBottom.setSize(i, m);
      if (this.mEdgeGlowBottom.draw(paramCanvas)) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      paramCanvas.restoreToCount(i3);
    }
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent)
  {
    this.mTempRect.setEmpty();
    boolean bool1 = canScroll();
    int i = 130;
    if (!bool1)
    {
      bool2 = isFocused();
      bool1 = false;
      if ((bool2) && (paramKeyEvent.getKeyCode() != 4))
      {
        View localView = findFocus();
        paramKeyEvent = localView;
        if (localView == this) {
          paramKeyEvent = null;
        }
        paramKeyEvent = FocusFinder.getInstance().findNextFocus(this, paramKeyEvent, 130);
        if ((paramKeyEvent != null) && (paramKeyEvent != this) && (paramKeyEvent.requestFocus(130))) {
          bool1 = true;
        }
        return bool1;
      }
      return false;
    }
    boolean bool2 = false;
    bool1 = bool2;
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode())
      {
      default: 
        bool1 = bool2;
        break;
      case 62: 
        if (paramKeyEvent.isShiftPressed()) {
          i = 33;
        }
        pageScroll(i);
        bool1 = bool2;
        break;
      case 20: 
        if (!paramKeyEvent.isAltPressed()) {
          bool1 = arrowScroll(130);
        } else {
          bool1 = fullScroll(130);
        }
        break;
      case 19: 
        if (!paramKeyEvent.isAltPressed()) {
          bool1 = arrowScroll(33);
        } else {
          bool1 = fullScroll(33);
        }
        break;
      }
    }
    return bool1;
  }
  
  public void fling(int paramInt)
  {
    if (getChildCount() > 0)
    {
      this.mScroller.fling(getScrollX(), getScrollY(), 0, paramInt, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
      runAnimatedScroll(true);
    }
  }
  
  public boolean fullScroll(int paramInt)
  {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    }
    int j = getHeight();
    this.mTempRect.top = 0;
    this.mTempRect.bottom = j;
    if (i != 0)
    {
      i = getChildCount();
      if (i > 0)
      {
        View localView = getChildAt(i - 1);
        Object localObject = (FrameLayout.LayoutParams)localView.getLayoutParams();
        this.mTempRect.bottom = (localView.getBottom() + ((FrameLayout.LayoutParams)localObject).bottomMargin + getPaddingBottom());
        localObject = this.mTempRect;
        ((Rect)localObject).top = (((Rect)localObject).bottom - j);
      }
    }
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  protected float getBottomFadingEdgeStrength()
  {
    if (getChildCount() == 0) {
      return 0.0F;
    }
    View localView = getChildAt(0);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
    int i = getVerticalFadingEdgeLength();
    int k = getHeight();
    int j = getPaddingBottom();
    j = localView.getBottom() + localLayoutParams.bottomMargin - getScrollY() - (k - j);
    if (j < i) {
      return j / i;
    }
    return 1.0F;
  }
  
  public int getMaxScrollAmount()
  {
    return (int)(getHeight() * 0.5F);
  }
  
  public int getNestedScrollAxes()
  {
    return this.mParentHelper.getNestedScrollAxes();
  }
  
  int getScrollRange()
  {
    int i = 0;
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      i = Math.max(0, localView.getHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin - (getHeight() - getPaddingTop() - getPaddingBottom()));
    }
    return i;
  }
  
  protected float getTopFadingEdgeStrength()
  {
    if (getChildCount() == 0) {
      return 0.0F;
    }
    int j = getVerticalFadingEdgeLength();
    int i = getScrollY();
    if (i < j) {
      return i / j;
    }
    return 1.0F;
  }
  
  public boolean hasNestedScrollingParent()
  {
    return hasNestedScrollingParent(0);
  }
  
  public boolean hasNestedScrollingParent(int paramInt)
  {
    return this.mChildHelper.hasNestedScrollingParent(paramInt);
  }
  
  public boolean isFillViewport()
  {
    return this.mFillViewport;
  }
  
  public boolean isNestedScrollingEnabled()
  {
    return this.mChildHelper.isNestedScrollingEnabled();
  }
  
  public boolean isSmoothScrollingEnabled()
  {
    return this.mSmoothScrollingEnabled;
  }
  
  protected void measureChild(View paramView, int paramInt1, int paramInt2)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight(), localLayoutParams.width), View.MeasureSpec.makeMeasureSpec(0, 0));
  }
  
  protected void measureChildWithMargins(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    paramView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin + paramInt2, localMarginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin, 0));
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mIsLaidOut = false;
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getAction();
    int m = 0;
    int i = 0;
    if ((j == 8) && (!this.mIsBeingDragged))
    {
      float f;
      if (MotionEventCompat.isFromSource(paramMotionEvent, 2)) {
        f = paramMotionEvent.getAxisValue(9);
      } else if (MotionEventCompat.isFromSource(paramMotionEvent, 4194304)) {
        f = paramMotionEvent.getAxisValue(26);
      } else {
        f = 0.0F;
      }
      if (f != 0.0F)
      {
        j = (int)(getVerticalScrollFactorCompat() * f);
        int k = getScrollRange();
        int n = getScrollY();
        j = n - j;
        boolean bool2 = false;
        boolean bool3 = false;
        boolean bool1 = false;
        if (j < 0)
        {
          if ((canOverScroll()) && (!MotionEventCompat.isFromSource(paramMotionEvent, 8194))) {
            i = 1;
          }
          if (i != 0)
          {
            EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, -j / getHeight(), 0.5F);
            this.mEdgeGlowTop.onRelease();
            invalidate();
            bool1 = true;
          }
          i = 0;
        }
        else
        {
          i = j;
          bool1 = bool3;
          if (j > k)
          {
            if ((canOverScroll()) && (!MotionEventCompat.isFromSource(paramMotionEvent, 8194))) {
              i = 1;
            } else {
              i = m;
            }
            bool1 = bool2;
            if (i != 0)
            {
              EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, (j - k) / getHeight(), 0.5F);
              this.mEdgeGlowBottom.onRelease();
              invalidate();
              bool1 = true;
            }
            i = k;
          }
        }
        if (i != n)
        {
          super.scrollTo(getScrollX(), i);
          return true;
        }
        return bool1;
      }
    }
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    boolean bool2 = true;
    boolean bool3 = true;
    if ((i == 2) && (this.mIsBeingDragged)) {
      return true;
    }
    switch (i & 0xFF)
    {
    case 4: 
    case 5: 
    default: 
      break;
    case 6: 
      onSecondaryPointerUp(paramMotionEvent);
      break;
    case 2: 
      i = this.mActivePointerId;
      if (i != -1)
      {
        int j = paramMotionEvent.findPointerIndex(i);
        if (j == -1)
        {
          Log.e("NestedScrollView", "Invalid pointerId=" + i + " in onInterceptTouchEvent");
        }
        else
        {
          i = (int)paramMotionEvent.getY(j);
          if ((Math.abs(i - this.mLastMotionY) > this.mTouchSlop) && ((0x2 & getNestedScrollAxes()) == 0))
          {
            this.mIsBeingDragged = true;
            this.mLastMotionY = i;
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(paramMotionEvent);
            this.mNestedYOffset = 0;
            paramMotionEvent = getParent();
            if (paramMotionEvent != null) {
              paramMotionEvent.requestDisallowInterceptTouchEvent(true);
            }
          }
        }
      }
      break;
    case 1: 
    case 3: 
      this.mIsBeingDragged = false;
      this.mActivePointerId = -1;
      recycleVelocityTracker();
      if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      stopNestedScroll(0);
      break;
    case 0: 
      i = (int)paramMotionEvent.getY();
      boolean bool1;
      if (!inChild((int)paramMotionEvent.getX(), i))
      {
        bool1 = bool3;
        if (!stopGlowAnimations(paramMotionEvent)) {
          if (!this.mScroller.isFinished()) {
            bool1 = bool3;
          } else {
            bool1 = false;
          }
        }
        this.mIsBeingDragged = bool1;
        recycleVelocityTracker();
      }
      else
      {
        this.mLastMotionY = i;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        initOrResetVelocityTracker();
        this.mVelocityTracker.addMovement(paramMotionEvent);
        this.mScroller.computeScrollOffset();
        bool1 = bool2;
        if (!stopGlowAnimations(paramMotionEvent)) {
          if (!this.mScroller.isFinished()) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
        this.mIsBeingDragged = bool1;
        startNestedScroll(2, 0);
      }
      break;
    }
    return this.mIsBeingDragged;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsLayoutDirty = false;
    Object localObject = this.mChildToScrollTo;
    if ((localObject != null) && (isViewDescendantOf((View)localObject, this))) {
      scrollToChild(this.mChildToScrollTo);
    }
    this.mChildToScrollTo = null;
    if (!this.mIsLaidOut)
    {
      if (this.mSavedState != null)
      {
        scrollTo(getScrollX(), this.mSavedState.scrollPosition);
        this.mSavedState = null;
      }
      paramInt1 = 0;
      if (getChildCount() > 0)
      {
        View localView = getChildAt(0);
        localObject = (FrameLayout.LayoutParams)localView.getLayoutParams();
        paramInt1 = localView.getMeasuredHeight() + ((FrameLayout.LayoutParams)localObject).topMargin + ((FrameLayout.LayoutParams)localObject).bottomMargin;
      }
      int i = getPaddingTop();
      int j = getPaddingBottom();
      paramInt3 = getScrollY();
      paramInt1 = clamp(paramInt3, paramInt4 - paramInt2 - i - j, paramInt1);
      if (paramInt1 != paramInt3) {
        scrollTo(getScrollX(), paramInt1);
      }
    }
    scrollTo(getScrollX(), getScrollY());
    this.mIsLaidOut = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (!this.mFillViewport) {
      return;
    }
    if (View.MeasureSpec.getMode(paramInt2) == 0) {
      return;
    }
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      paramInt2 = localView.getMeasuredHeight();
      int i = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - localLayoutParams.topMargin - localLayoutParams.bottomMargin;
      if (paramInt2 < i) {
        localView.measure(getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localLayoutParams.leftMargin + localLayoutParams.rightMargin, localLayoutParams.width), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
      }
    }
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      dispatchNestedFling(0.0F, paramFloat2, true);
      fling((int)paramFloat2);
      return true;
    }
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    return dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt, 0);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt, null, paramInt3);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    onNestedScrollInternal(paramInt4, 0, null);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    onNestedScrollInternal(paramInt4, paramInt5, null);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    onNestedScrollInternal(paramInt4, paramInt5, paramArrayOfInt);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    onNestedScrollAccepted(paramView1, paramView2, paramInt, 0);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    this.mParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    startNestedScroll(2, paramInt2);
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.scrollTo(paramInt1, paramInt2);
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    int i;
    if (paramInt == 2)
    {
      i = 130;
    }
    else
    {
      i = paramInt;
      if (paramInt == 1) {
        i = 33;
      }
    }
    View localView;
    if (paramRect == null) {
      localView = FocusFinder.getInstance().findNextFocus(this, null, i);
    } else {
      localView = FocusFinder.getInstance().findNextFocusFromRect(this, paramRect, i);
    }
    if (localView == null) {
      return false;
    }
    if (isOffScreen(localView)) {
      return false;
    }
    return localView.requestFocus(i, paramRect);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.mSavedState = paramParcelable;
    requestLayout();
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.scrollPosition = getScrollY();
    return localSavedState;
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    OnScrollChangeListener localOnScrollChangeListener = this.mOnScrollChangeListener;
    if (localOnScrollChangeListener != null) {
      localOnScrollChangeListener.onScrollChange(this, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    View localView = findFocus();
    if ((localView != null) && (this != localView))
    {
      if (isWithinDeltaOfScreen(localView, 0, paramInt4))
      {
        localView.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(localView, this.mTempRect);
        doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
      }
      return;
    }
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    return onStartNestedScroll(paramView1, paramView2, paramInt, 0);
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((paramInt1 & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onStopNestedScroll(View paramView)
  {
    onStopNestedScroll(paramView, 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt)
  {
    this.mParentHelper.onStopNestedScroll(paramView, paramInt);
    stopNestedScroll(paramInt);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    initVelocityTrackerIfNotExists();
    int i = paramMotionEvent.getActionMasked();
    if (i == 0) {
      this.mNestedYOffset = 0;
    }
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    localMotionEvent.offsetLocation(0.0F, this.mNestedYOffset);
    Object localObject;
    switch (i)
    {
    case 4: 
    default: 
      break;
    case 6: 
      onSecondaryPointerUp(paramMotionEvent);
      this.mLastMotionY = ((int)paramMotionEvent.getY(paramMotionEvent.findPointerIndex(this.mActivePointerId)));
      break;
    case 5: 
      i = paramMotionEvent.getActionIndex();
      this.mLastMotionY = ((int)paramMotionEvent.getY(i));
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      break;
    case 3: 
      if ((this.mIsBeingDragged) && (getChildCount() > 0) && (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      this.mActivePointerId = -1;
      endDrag();
      break;
    case 2: 
      int m = paramMotionEvent.findPointerIndex(this.mActivePointerId);
      if (m == -1)
      {
        Log.e("NestedScrollView", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
      }
      else
      {
        int k = (int)paramMotionEvent.getY(m);
        i = this.mLastMotionY - k;
        i -= releaseVerticalGlow(i, paramMotionEvent.getX(m));
        if ((!this.mIsBeingDragged) && (Math.abs(i) > this.mTouchSlop))
        {
          localObject = getParent();
          if (localObject != null) {
            ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
          }
          this.mIsBeingDragged = true;
          if (i > 0) {
            i -= this.mTouchSlop;
          } else {
            i += this.mTouchSlop;
          }
        }
        if (this.mIsBeingDragged)
        {
          int j;
          if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, this.mScrollOffset, 0))
          {
            j = this.mScrollConsumed[1];
            this.mNestedYOffset += this.mScrollOffset[1];
            j = i - j;
          }
          else
          {
            j = i;
          }
          this.mLastMotionY = (k - this.mScrollOffset[1]);
          int i1 = getScrollY();
          int n = getScrollRange();
          i = getOverScrollMode();
          if ((i != 0) && ((i != 1) || (n <= 0))) {
            k = 0;
          } else {
            k = 1;
          }
          if ((overScrollByCompat(0, j, 0, getScrollY(), 0, n, 0, 0, true)) && (!hasNestedScrollingParent(0))) {
            i = 1;
          } else {
            i = 0;
          }
          int i2 = getScrollY() - i1;
          localObject = this.mScrollConsumed;
          localObject[1] = 0;
          dispatchNestedScroll(0, i2, 0, j - i2, this.mScrollOffset, 0, (int[])localObject);
          i2 = this.mLastMotionY;
          int i3 = this.mScrollOffset[1];
          this.mLastMotionY = (i2 - i3);
          this.mNestedYOffset += i3;
          if (k != 0)
          {
            j -= this.mScrollConsumed[1];
            k = i1 + j;
            if (k < 0)
            {
              EdgeEffectCompat.onPullDistance(this.mEdgeGlowTop, -j / getHeight(), paramMotionEvent.getX(m) / getWidth());
              if (!this.mEdgeGlowBottom.isFinished()) {
                this.mEdgeGlowBottom.onRelease();
              }
            }
            else if (k > n)
            {
              EdgeEffectCompat.onPullDistance(this.mEdgeGlowBottom, j / getHeight(), 1.0F - paramMotionEvent.getX(m) / getWidth());
              if (!this.mEdgeGlowTop.isFinished()) {
                this.mEdgeGlowTop.onRelease();
              }
            }
            if ((this.mEdgeGlowTop.isFinished()) && (this.mEdgeGlowBottom.isFinished())) {
              break label727;
            }
            ViewCompat.postInvalidateOnAnimation(this);
            i = 0;
          }
          if (i != 0) {
            this.mVelocityTracker.clear();
          }
        }
      }
      break;
    case 1: 
      paramMotionEvent = this.mVelocityTracker;
      paramMotionEvent.computeCurrentVelocity(1000, this.mMaximumVelocity);
      i = (int)paramMotionEvent.getYVelocity(this.mActivePointerId);
      if (Math.abs(i) >= this.mMinimumVelocity)
      {
        if ((!edgeEffectFling(i)) && (!dispatchNestedPreFling(0.0F, -i)))
        {
          dispatchNestedFling(0.0F, -i, true);
          fling(-i);
        }
      }
      else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      this.mActivePointerId = -1;
      endDrag();
      break;
    case 0: 
      label727:
      if (getChildCount() == 0) {
        return false;
      }
      if (this.mIsBeingDragged)
      {
        localObject = getParent();
        if (localObject != null) {
          ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
        }
      }
      if (!this.mScroller.isFinished()) {
        abortAnimatedScroll();
      }
      this.mLastMotionY = ((int)paramMotionEvent.getY());
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      startNestedScroll(2, 0);
    }
    paramMotionEvent = this.mVelocityTracker;
    if (paramMotionEvent != null) {
      paramMotionEvent.addMovement(localMotionEvent);
    }
    localMotionEvent.recycle();
    return true;
  }
  
  boolean overScrollByCompat(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    int k = getOverScrollMode();
    int i;
    if (computeHorizontalScrollRange() > computeHorizontalScrollExtent()) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (computeVerticalScrollRange() > computeVerticalScrollExtent()) {
      j = 1;
    } else {
      j = 0;
    }
    if ((k != 0) && ((k != 1) || (i == 0))) {
      i = 0;
    } else {
      i = 1;
    }
    if ((k != 0) && ((k != 1) || (j == 0))) {
      j = 0;
    } else {
      j = 1;
    }
    paramInt3 += paramInt1;
    if (i == 0) {
      paramInt1 = 0;
    } else {
      paramInt1 = paramInt7;
    }
    paramInt4 += paramInt2;
    if (j == 0) {
      paramInt2 = 0;
    } else {
      paramInt2 = paramInt8;
    }
    paramInt7 = -paramInt1;
    paramInt1 += paramInt5;
    paramInt5 = -paramInt2;
    paramInt2 += paramInt6;
    if (paramInt3 > paramInt1)
    {
      paramBoolean = true;
    }
    else if (paramInt3 < paramInt7)
    {
      paramInt1 = paramInt7;
      paramBoolean = true;
    }
    else
    {
      paramBoolean = false;
      paramInt1 = paramInt3;
    }
    boolean bool;
    if (paramInt4 > paramInt2)
    {
      bool = true;
    }
    else if (paramInt4 < paramInt5)
    {
      paramInt2 = paramInt5;
      bool = true;
    }
    else
    {
      bool = false;
      paramInt2 = paramInt4;
    }
    if ((bool) && (!hasNestedScrollingParent(1))) {
      this.mScroller.springBack(paramInt1, paramInt2, 0, 0, 0, getScrollRange());
    }
    onOverScrolled(paramInt1, paramInt2, paramBoolean, bool);
    if ((!paramBoolean) && (!bool)) {
      paramBoolean = false;
    } else {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public boolean pageScroll(int paramInt)
  {
    int i;
    if (paramInt == 130) {
      i = 1;
    } else {
      i = 0;
    }
    int j = getHeight();
    if (i != 0)
    {
      this.mTempRect.top = (getScrollY() + j);
      i = getChildCount();
      if (i > 0)
      {
        View localView = getChildAt(i - 1);
        localObject = (FrameLayout.LayoutParams)localView.getLayoutParams();
        i = localView.getBottom() + ((FrameLayout.LayoutParams)localObject).bottomMargin + getPaddingBottom();
        if (this.mTempRect.top + j > i) {
          this.mTempRect.top = (i - j);
        }
      }
    }
    else
    {
      this.mTempRect.top = (getScrollY() - j);
      if (this.mTempRect.top < 0) {
        this.mTempRect.top = 0;
      }
    }
    Object localObject = this.mTempRect;
    ((Rect)localObject).bottom = (((Rect)localObject).top + j);
    return scrollAndFocus(paramInt, this.mTempRect.top, this.mTempRect.bottom);
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    if (!this.mIsLayoutDirty) {
      scrollToChild(paramView2);
    } else {
      this.mChildToScrollTo = paramView2;
    }
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    paramRect.offset(paramView.getLeft() - paramView.getScrollX(), paramView.getTop() - paramView.getScrollY());
    return scrollToChildRect(paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    if (paramBoolean) {
      recycleVelocityTracker();
    }
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout()
  {
    this.mIsLayoutDirty = true;
    super.requestLayout();
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    if (getChildCount() > 0)
    {
      View localView = getChildAt(0);
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
      int i7 = getWidth();
      int i6 = getPaddingLeft();
      int i3 = getPaddingRight();
      int i2 = localView.getWidth();
      int i5 = localLayoutParams.leftMargin;
      int i4 = localLayoutParams.rightMargin;
      int n = getHeight();
      int i1 = getPaddingTop();
      int k = getPaddingBottom();
      int m = localView.getHeight();
      int j = localLayoutParams.topMargin;
      int i = localLayoutParams.bottomMargin;
      paramInt1 = clamp(paramInt1, i7 - i6 - i3, i2 + i5 + i4);
      paramInt2 = clamp(paramInt2, n - i1 - k, m + j + i);
      if ((paramInt1 != getScrollX()) || (paramInt2 != getScrollY())) {
        super.scrollTo(paramInt1, paramInt2);
      }
    }
  }
  
  public void setFillViewport(boolean paramBoolean)
  {
    if (paramBoolean != this.mFillViewport)
    {
      this.mFillViewport = paramBoolean;
      requestLayout();
    }
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean)
  {
    this.mChildHelper.setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnScrollChangeListener(OnScrollChangeListener paramOnScrollChangeListener)
  {
    this.mOnScrollChangeListener = paramOnScrollChangeListener;
  }
  
  public void setSmoothScrollingEnabled(boolean paramBoolean)
  {
    this.mSmoothScrollingEnabled = paramBoolean;
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return true;
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2)
  {
    smoothScrollBy(paramInt1, paramInt2, 250, false);
  }
  
  public final void smoothScrollBy(int paramInt1, int paramInt2, int paramInt3)
  {
    smoothScrollBy(paramInt1, paramInt2, paramInt3, false);
  }
  
  public final void smoothScrollTo(int paramInt1, int paramInt2)
  {
    smoothScrollTo(paramInt1, paramInt2, 250, false);
  }
  
  public final void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3)
  {
    smoothScrollTo(paramInt1, paramInt2, paramInt3, false);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    smoothScrollBy(paramInt1 - getScrollX(), paramInt2 - getScrollY(), paramInt3, paramBoolean);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    smoothScrollTo(paramInt1, paramInt2, 250, paramBoolean);
  }
  
  public boolean startNestedScroll(int paramInt)
  {
    return startNestedScroll(paramInt, 0);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2)
  {
    return this.mChildHelper.startNestedScroll(paramInt1, paramInt2);
  }
  
  public void stopNestedScroll()
  {
    stopNestedScroll(0);
  }
  
  public void stopNestedScroll(int paramInt)
  {
    this.mChildHelper.stopNestedScroll(paramInt);
  }
  
  static class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramView = (NestedScrollView)paramView;
      paramAccessibilityEvent.setClassName(ScrollView.class.getName());
      boolean bool;
      if (paramView.getScrollRange() > 0) {
        bool = true;
      } else {
        bool = false;
      }
      paramAccessibilityEvent.setScrollable(bool);
      paramAccessibilityEvent.setScrollX(paramView.getScrollX());
      paramAccessibilityEvent.setScrollY(paramView.getScrollY());
      AccessibilityRecordCompat.setMaxScrollX(paramAccessibilityEvent, paramView.getScrollX());
      AccessibilityRecordCompat.setMaxScrollY(paramAccessibilityEvent, paramView.getScrollRange());
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramView = (NestedScrollView)paramView;
      paramAccessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
      if (paramView.isEnabled())
      {
        int i = paramView.getScrollRange();
        if (i > 0)
        {
          paramAccessibilityNodeInfoCompat.setScrollable(true);
          if (paramView.getScrollY() > 0)
          {
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
          }
          if (paramView.getScrollY() < i)
          {
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
            paramAccessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
          }
        }
      }
    }
    
    public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      if (super.performAccessibilityAction(paramView, paramInt, paramBundle)) {
        return true;
      }
      paramView = (NestedScrollView)paramView;
      if (!paramView.isEnabled()) {
        return false;
      }
      int j = paramView.getHeight();
      paramBundle = new Rect();
      int i = j;
      if (paramView.getMatrix().isIdentity())
      {
        i = j;
        if (paramView.getGlobalVisibleRect(paramBundle)) {
          i = paramBundle.height();
        }
      }
      switch (paramInt)
      {
      default: 
        return false;
      case 8192: 
      case 16908344: 
        j = paramView.getPaddingBottom();
        paramInt = paramView.getPaddingTop();
        paramInt = Math.max(paramView.getScrollY() - (i - j - paramInt), 0);
        if (paramInt != paramView.getScrollY())
        {
          paramView.smoothScrollTo(0, paramInt, true);
          return true;
        }
        return false;
      }
      paramInt = paramView.getPaddingBottom();
      j = paramView.getPaddingTop();
      paramInt = Math.min(paramView.getScrollY() + (i - paramInt - j), paramView.getScrollRange());
      if (paramInt != paramView.getScrollY())
      {
        paramView.smoothScrollTo(0, paramInt, true);
        return true;
      }
      return false;
    }
  }
  
  static class Api21Impl
  {
    static boolean getClipToPadding(ViewGroup paramViewGroup)
    {
      return paramViewGroup.getClipToPadding();
    }
  }
  
  public static abstract interface OnScrollChangeListener
  {
    public abstract void onScrollChange(NestedScrollView paramNestedScrollView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public NestedScrollView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new NestedScrollView.SavedState(paramAnonymousParcel);
      }
      
      public NestedScrollView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new NestedScrollView.SavedState[paramAnonymousInt];
      }
    };
    public int scrollPosition;
    
    SavedState(Parcel paramParcel)
    {
      super();
      this.scrollPosition = paramParcel.readInt();
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("HorizontalScrollView.SavedState{");
      String str = Integer.toHexString(System.identityHashCode(this));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + " scrollPosition=" + this.scrollPosition + "}";
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.scrollPosition);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/NestedScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */