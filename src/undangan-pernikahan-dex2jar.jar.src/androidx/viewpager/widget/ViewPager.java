package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ViewPager
  extends ViewGroup
{
  private static final int CLOSE_ENOUGH = 2;
  private static final Comparator<ItemInfo> COMPARATOR = new Comparator()
  {
    public int compare(ViewPager.ItemInfo paramAnonymousItemInfo1, ViewPager.ItemInfo paramAnonymousItemInfo2)
    {
      return paramAnonymousItemInfo1.position - paramAnonymousItemInfo2.position;
    }
  };
  private static final boolean DEBUG = false;
  private static final int DEFAULT_GUTTER_SIZE = 16;
  private static final int DEFAULT_OFFSCREEN_PAGES = 1;
  private static final int DRAW_ORDER_DEFAULT = 0;
  private static final int DRAW_ORDER_FORWARD = 1;
  private static final int DRAW_ORDER_REVERSE = 2;
  private static final int INVALID_POINTER = -1;
  static final int[] LAYOUT_ATTRS = { 16842931 };
  private static final int MAX_SETTLE_DURATION = 600;
  private static final int MIN_DISTANCE_FOR_FLING = 25;
  private static final int MIN_FLING_VELOCITY = 400;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  private static final String TAG = "ViewPager";
  private static final boolean USE_CACHE = false;
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public float getInterpolation(float paramAnonymousFloat)
    {
      paramAnonymousFloat -= 1.0F;
      return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
    }
  };
  private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();
  private int mActivePointerId = -1;
  PagerAdapter mAdapter;
  private List<OnAdapterChangeListener> mAdapterChangeListeners;
  private int mBottomPageBounds;
  private boolean mCalledSuper;
  private int mChildHeightMeasureSpec;
  private int mChildWidthMeasureSpec;
  private int mCloseEnough;
  int mCurItem;
  private int mDecorChildCount;
  private int mDefaultGutterSize;
  private int mDrawingOrder;
  private ArrayList<View> mDrawingOrderedChildren;
  private final Runnable mEndScrollRunnable = new Runnable()
  {
    public void run()
    {
      ViewPager.this.setScrollState(0);
      ViewPager.this.populate();
    }
  };
  private int mExpectedAdapterCount;
  private long mFakeDragBeginTime;
  private boolean mFakeDragging;
  private boolean mFirstLayout = true;
  private float mFirstOffset = -3.4028235E38F;
  private int mFlingDistance;
  private int mGutterSize;
  private boolean mInLayout;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private OnPageChangeListener mInternalPageChangeListener;
  private boolean mIsBeingDragged;
  private boolean mIsScrollStarted;
  private boolean mIsUnableToDrag;
  private final ArrayList<ItemInfo> mItems = new ArrayList();
  private float mLastMotionX;
  private float mLastMotionY;
  private float mLastOffset = Float.MAX_VALUE;
  private EdgeEffect mLeftEdge;
  private Drawable mMarginDrawable;
  private int mMaximumVelocity;
  private int mMinimumVelocity;
  private boolean mNeedCalculatePageOffsets = false;
  private PagerObserver mObserver;
  private int mOffscreenPageLimit = 1;
  private OnPageChangeListener mOnPageChangeListener;
  private List<OnPageChangeListener> mOnPageChangeListeners;
  private int mPageMargin;
  private PageTransformer mPageTransformer;
  private int mPageTransformerLayerType;
  private boolean mPopulatePending;
  private Parcelable mRestoredAdapterState = null;
  private ClassLoader mRestoredClassLoader = null;
  private int mRestoredCurItem = -1;
  private EdgeEffect mRightEdge;
  private int mScrollState = 0;
  private Scroller mScroller;
  private boolean mScrollingCacheEnabled;
  private final ItemInfo mTempItem = new ItemInfo();
  private final Rect mTempRect = new Rect();
  private int mTopPageBounds;
  private int mTouchSlop;
  private VelocityTracker mVelocityTracker;
  
  public ViewPager(Context paramContext)
  {
    super(paramContext);
    initViewPager();
  }
  
  public ViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initViewPager();
  }
  
  private void calculatePageOffsets(ItemInfo paramItemInfo1, int paramInt, ItemInfo paramItemInfo2)
  {
    int m = this.mAdapter.getCount();
    int i = getClientWidth();
    float f2;
    if (i > 0) {
      f2 = this.mPageMargin / i;
    } else {
      f2 = 0.0F;
    }
    if (paramItemInfo2 != null)
    {
      i = paramItemInfo2.position;
      if (i < paramItemInfo1.position)
      {
        j = 0;
        f1 = paramItemInfo2.offset + paramItemInfo2.widthFactor + f2;
        i++;
        while ((i <= paramItemInfo1.position) && (j < this.mItems.size()))
        {
          for (paramItemInfo2 = (ItemInfo)this.mItems.get(j);; paramItemInfo2 = (ItemInfo)this.mItems.get(j))
          {
            f3 = f1;
            k = i;
            if (i <= paramItemInfo2.position) {
              break;
            }
            f3 = f1;
            k = i;
            if (j >= this.mItems.size() - 1) {
              break;
            }
            j++;
          }
          while (k < paramItemInfo2.position)
          {
            f3 += this.mAdapter.getPageWidth(k) + f2;
            k++;
          }
          paramItemInfo2.offset = f3;
          f1 = f3 + (paramItemInfo2.widthFactor + f2);
          i = k + 1;
        }
      }
      if (i > paramItemInfo1.position)
      {
        j = this.mItems.size() - 1;
        f1 = paramItemInfo2.offset;
        i--;
        while ((i >= paramItemInfo1.position) && (j >= 0))
        {
          for (paramItemInfo2 = (ItemInfo)this.mItems.get(j);; paramItemInfo2 = (ItemInfo)this.mItems.get(j))
          {
            f3 = f1;
            k = i;
            if (i >= paramItemInfo2.position) {
              break;
            }
            f3 = f1;
            k = i;
            if (j <= 0) {
              break;
            }
            j--;
          }
          while (k > paramItemInfo2.position)
          {
            f3 -= this.mAdapter.getPageWidth(k) + f2;
            k--;
          }
          f1 = f3 - (paramItemInfo2.widthFactor + f2);
          paramItemInfo2.offset = f1;
          i = k - 1;
        }
      }
    }
    int k = this.mItems.size();
    float f3 = paramItemInfo1.offset;
    i = paramItemInfo1.position - 1;
    if (paramItemInfo1.position == 0) {
      f1 = paramItemInfo1.offset;
    } else {
      f1 = -3.4028235E38F;
    }
    this.mFirstOffset = f1;
    if (paramItemInfo1.position == m - 1) {
      f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor - 1.0F;
    } else {
      f1 = Float.MAX_VALUE;
    }
    this.mLastOffset = f1;
    int j = paramInt - 1;
    float f1 = f3;
    while (j >= 0)
    {
      paramItemInfo2 = (ItemInfo)this.mItems.get(j);
      while (i > paramItemInfo2.position)
      {
        f1 -= this.mAdapter.getPageWidth(i) + f2;
        i--;
      }
      f1 -= paramItemInfo2.widthFactor + f2;
      paramItemInfo2.offset = f1;
      if (paramItemInfo2.position == 0) {
        this.mFirstOffset = f1;
      }
      j--;
      i--;
    }
    f1 = paramItemInfo1.offset + paramItemInfo1.widthFactor + f2;
    j = paramItemInfo1.position + 1;
    i = paramInt + 1;
    for (paramInt = j; i < k; paramInt++)
    {
      paramItemInfo1 = (ItemInfo)this.mItems.get(i);
      while (paramInt < paramItemInfo1.position)
      {
        f1 += this.mAdapter.getPageWidth(paramInt) + f2;
        paramInt++;
      }
      if (paramItemInfo1.position == m - 1) {
        this.mLastOffset = (paramItemInfo1.widthFactor + f1 - 1.0F);
      }
      paramItemInfo1.offset = f1;
      f1 += paramItemInfo1.widthFactor + f2;
      i++;
    }
    this.mNeedCalculatePageOffsets = false;
  }
  
  private void completeScroll(boolean paramBoolean)
  {
    int i;
    if (this.mScrollState == 2) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      setScrollingCacheEnabled(false);
      if ((true ^ this.mScroller.isFinished()))
      {
        this.mScroller.abortAnimation();
        int n = getScrollX();
        int k = getScrollY();
        j = this.mScroller.getCurrX();
        int m = this.mScroller.getCurrY();
        if ((n != j) || (k != m))
        {
          scrollTo(j, m);
          if (j != n) {
            pageScrolled(j);
          }
        }
      }
    }
    this.mPopulatePending = false;
    for (int j = 0; j < this.mItems.size(); j++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(j);
      if (localItemInfo.scrolling)
      {
        i = 1;
        localItemInfo.scrolling = false;
      }
    }
    if (i != 0) {
      if (paramBoolean) {
        ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
      } else {
        this.mEndScrollRunnable.run();
      }
    }
  }
  
  private int determineTargetPage(int paramInt1, float paramFloat, int paramInt2, int paramInt3)
  {
    if ((Math.abs(paramInt3) > this.mFlingDistance) && (Math.abs(paramInt2) > this.mMinimumVelocity))
    {
      if (paramInt2 <= 0) {
        paramInt1++;
      }
    }
    else
    {
      float f;
      if (paramInt1 >= this.mCurItem) {
        f = 0.4F;
      } else {
        f = 0.6F;
      }
      paramInt1 = (int)(paramFloat + f) + paramInt1;
    }
    paramInt2 = paramInt1;
    if (this.mItems.size() > 0)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(0);
      Object localObject = this.mItems;
      localObject = (ItemInfo)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
      paramInt2 = Math.max(localItemInfo.position, Math.min(paramInt1, ((ItemInfo)localObject).position));
    }
    return paramInt2;
  }
  
  private void dispatchOnPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrolled(paramInt1, paramFloat, paramInt2);
    }
  }
  
  private void dispatchOnPageSelected(int paramInt)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageSelected(paramInt);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageSelected(paramInt);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageSelected(paramInt);
    }
  }
  
  private void dispatchOnScrollStateChanged(int paramInt)
  {
    Object localObject = this.mOnPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
    }
    localObject = this.mOnPageChangeListeners;
    if (localObject != null)
    {
      int i = 0;
      int j = ((List)localObject).size();
      while (i < j)
      {
        localObject = (OnPageChangeListener)this.mOnPageChangeListeners.get(i);
        if (localObject != null) {
          ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
        }
        i++;
      }
    }
    localObject = this.mInternalPageChangeListener;
    if (localObject != null) {
      ((OnPageChangeListener)localObject).onPageScrollStateChanged(paramInt);
    }
  }
  
  private void enableLayers(boolean paramBoolean)
  {
    int k = getChildCount();
    for (int i = 0; i < k; i++)
    {
      int j;
      if (paramBoolean) {
        j = this.mPageTransformerLayerType;
      } else {
        j = 0;
      }
      getChildAt(i).setLayerType(j, null);
    }
  }
  
  private void endDrag()
  {
    this.mIsBeingDragged = false;
    this.mIsUnableToDrag = false;
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  private Rect getChildRectInPagerCoordinates(Rect paramRect, View paramView)
  {
    Rect localRect = paramRect;
    if (paramRect == null) {
      localRect = new Rect();
    }
    if (paramView == null)
    {
      localRect.set(0, 0, 0, 0);
      return localRect;
    }
    localRect.left = paramView.getLeft();
    localRect.right = paramView.getRight();
    localRect.top = paramView.getTop();
    localRect.bottom = paramView.getBottom();
    for (paramRect = paramView.getParent(); ((paramRect instanceof ViewGroup)) && (paramRect != this); paramRect = paramRect.getParent())
    {
      paramRect = (ViewGroup)paramRect;
      localRect.left += paramRect.getLeft();
      localRect.right += paramRect.getRight();
      localRect.top += paramRect.getTop();
      localRect.bottom += paramRect.getBottom();
    }
    return localRect;
  }
  
  private int getClientWidth()
  {
    return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
  }
  
  private ItemInfo infoForCurrentScrollPosition()
  {
    int i = getClientWidth();
    float f2 = 0.0F;
    float f1;
    if (i > 0) {
      f1 = getScrollX() / i;
    } else {
      f1 = 0.0F;
    }
    if (i > 0) {
      f2 = this.mPageMargin / i;
    }
    int m = -1;
    float f4 = 0.0F;
    float f3 = 0.0F;
    int j = 1;
    Object localObject = null;
    i = 0;
    while (i < this.mItems.size())
    {
      ItemInfo localItemInfo2 = (ItemInfo)this.mItems.get(i);
      int k = i;
      ItemInfo localItemInfo1 = localItemInfo2;
      if (j == 0)
      {
        k = i;
        localItemInfo1 = localItemInfo2;
        if (localItemInfo2.position != m + 1)
        {
          localItemInfo1 = this.mTempItem;
          localItemInfo1.offset = (f4 + f3 + f2);
          localItemInfo1.position = (m + 1);
          localItemInfo1.widthFactor = this.mAdapter.getPageWidth(localItemInfo1.position);
          k = i - 1;
        }
      }
      f4 = localItemInfo1.offset;
      f3 = localItemInfo1.widthFactor;
      if ((j == 0) && (f1 < f4)) {
        return (ItemInfo)localObject;
      }
      if ((f1 >= f3 + f4 + f2) && (k != this.mItems.size() - 1))
      {
        j = 0;
        m = localItemInfo1.position;
        f3 = localItemInfo1.widthFactor;
        i = k + 1;
        localObject = localItemInfo1;
      }
      else
      {
        return localItemInfo1;
      }
    }
    return (ItemInfo)localObject;
  }
  
  private static boolean isDecorView(View paramView)
  {
    boolean bool;
    if (paramView.getClass().getAnnotation(DecorView.class) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isGutterDrag(float paramFloat1, float paramFloat2)
  {
    boolean bool;
    if (((paramFloat1 < this.mGutterSize) && (paramFloat2 > 0.0F)) || ((paramFloat1 > getWidth() - this.mGutterSize) && (paramFloat2 < 0.0F))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
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
      this.mLastMotionX = paramMotionEvent.getX(i);
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      paramMotionEvent = this.mVelocityTracker;
      if (paramMotionEvent != null) {
        paramMotionEvent.clear();
      }
    }
  }
  
  private boolean pageScrolled(int paramInt)
  {
    if (this.mItems.size() == 0)
    {
      if (this.mFirstLayout) {
        return false;
      }
      this.mCalledSuper = false;
      onPageScrolled(0, 0.0F, 0);
      if (this.mCalledSuper) {
        return false;
      }
      throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }
    ItemInfo localItemInfo = infoForCurrentScrollPosition();
    int k = getClientWidth();
    int j = this.mPageMargin;
    float f = j / k;
    int i = localItemInfo.position;
    f = (paramInt / k - localItemInfo.offset) / (localItemInfo.widthFactor + f);
    paramInt = (int)((k + j) * f);
    this.mCalledSuper = false;
    onPageScrolled(i, f, paramInt);
    if (this.mCalledSuper) {
      return true;
    }
    throw new IllegalStateException("onPageScrolled did not call superclass implementation");
  }
  
  private boolean performDrag(float paramFloat)
  {
    boolean bool2 = false;
    boolean bool1 = false;
    boolean bool3 = false;
    float f1 = this.mLastMotionX;
    this.mLastMotionX = paramFloat;
    float f2 = getScrollX() + (f1 - paramFloat);
    int k = getClientWidth();
    paramFloat = k * this.mFirstOffset;
    f1 = k * this.mLastOffset;
    int i = 1;
    int j = 1;
    ItemInfo localItemInfo = (ItemInfo)this.mItems.get(0);
    Object localObject = this.mItems;
    localObject = (ItemInfo)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
    if (localItemInfo.position != 0)
    {
      i = 0;
      paramFloat = localItemInfo.offset * k;
    }
    if (((ItemInfo)localObject).position != this.mAdapter.getCount() - 1)
    {
      j = 0;
      f1 = ((ItemInfo)localObject).offset * k;
    }
    if (f2 < paramFloat)
    {
      bool1 = bool3;
      if (i != 0)
      {
        this.mLeftEdge.onPull(Math.abs(paramFloat - f2) / k);
        bool1 = true;
      }
    }
    else
    {
      paramFloat = f2;
      if (f2 > f1)
      {
        bool1 = bool2;
        if (j != 0)
        {
          this.mRightEdge.onPull(Math.abs(f2 - f1) / k);
          bool1 = true;
        }
        paramFloat = f1;
      }
    }
    this.mLastMotionX += paramFloat - (int)paramFloat;
    scrollTo((int)paramFloat, getScrollY());
    pageScrolled((int)paramFloat);
    return bool1;
  }
  
  private void recomputeScrollPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    float f;
    if ((paramInt2 > 0) && (!this.mItems.isEmpty()))
    {
      if (!this.mScroller.isFinished())
      {
        this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
      }
      else
      {
        int m = getPaddingLeft();
        int i = getPaddingRight();
        int j = getPaddingLeft();
        int k = getPaddingRight();
        f = getScrollX() / (paramInt2 - j - k + paramInt4);
        scrollTo((int)((paramInt1 - m - i + paramInt3) * f), getScrollY());
      }
    }
    else
    {
      ItemInfo localItemInfo = infoForPosition(this.mCurItem);
      if (localItemInfo != null) {
        f = Math.min(localItemInfo.offset, this.mLastOffset);
      } else {
        f = 0.0F;
      }
      paramInt1 = (int)((paramInt1 - getPaddingLeft() - getPaddingRight()) * f);
      if (paramInt1 != getScrollX())
      {
        completeScroll(false);
        scrollTo(paramInt1, getScrollY());
      }
    }
  }
  
  private void removeNonDecorViews()
  {
    int j;
    for (int i = 0; i < getChildCount(); i = j + 1)
    {
      j = i;
      if (!((LayoutParams)getChildAt(i).getLayoutParams()).isDecor)
      {
        removeViewAt(i);
        j = i - 1;
      }
    }
  }
  
  private void requestParentDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    ViewParent localViewParent = getParent();
    if (localViewParent != null) {
      localViewParent.requestDisallowInterceptTouchEvent(paramBoolean);
    }
  }
  
  private boolean resetTouch()
  {
    this.mActivePointerId = -1;
    endDrag();
    this.mLeftEdge.onRelease();
    this.mRightEdge.onRelease();
    boolean bool;
    if ((!this.mLeftEdge.isFinished()) && (!this.mRightEdge.isFinished())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private void scrollToItem(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2)
  {
    ItemInfo localItemInfo = infoForPosition(paramInt1);
    int i = 0;
    if (localItemInfo != null) {
      i = (int)(getClientWidth() * Math.max(this.mFirstOffset, Math.min(localItemInfo.offset, this.mLastOffset)));
    }
    if (paramBoolean1)
    {
      smoothScrollTo(i, 0, paramInt2);
      if (paramBoolean2) {
        dispatchOnPageSelected(paramInt1);
      }
    }
    else
    {
      if (paramBoolean2) {
        dispatchOnPageSelected(paramInt1);
      }
      completeScroll(false);
      scrollTo(i, 0);
      pageScrolled(i);
    }
  }
  
  private void setScrollingCacheEnabled(boolean paramBoolean)
  {
    if (this.mScrollingCacheEnabled != paramBoolean) {
      this.mScrollingCacheEnabled = paramBoolean;
    }
  }
  
  private void sortChildDrawingOrder()
  {
    if (this.mDrawingOrder != 0)
    {
      Object localObject = this.mDrawingOrderedChildren;
      if (localObject == null) {
        this.mDrawingOrderedChildren = new ArrayList();
      } else {
        ((ArrayList)localObject).clear();
      }
      int j = getChildCount();
      for (int i = 0; i < j; i++)
      {
        localObject = getChildAt(i);
        this.mDrawingOrderedChildren.add(localObject);
      }
      Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
    }
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    int k = paramArrayList.size();
    int j = getDescendantFocusability();
    if (j != 393216) {
      for (int i = 0; i < getChildCount(); i++)
      {
        View localView = getChildAt(i);
        if (localView.getVisibility() == 0)
        {
          ItemInfo localItemInfo = infoForChild(localView);
          if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem)) {
            localView.addFocusables(paramArrayList, paramInt1, paramInt2);
          }
        }
      }
    }
    if ((j != 262144) || (k == paramArrayList.size()))
    {
      if (!isFocusable()) {
        return;
      }
      if (((paramInt2 & 0x1) == 1) && (isInTouchMode()) && (!isFocusableInTouchMode())) {
        return;
      }
      if (paramArrayList != null) {
        paramArrayList.add(this);
      }
    }
  }
  
  ItemInfo addNewItem(int paramInt1, int paramInt2)
  {
    ItemInfo localItemInfo = new ItemInfo();
    localItemInfo.position = paramInt1;
    localItemInfo.object = this.mAdapter.instantiateItem(this, paramInt1);
    localItemInfo.widthFactor = this.mAdapter.getPageWidth(paramInt1);
    if ((paramInt2 >= 0) && (paramInt2 < this.mItems.size())) {
      this.mItems.add(paramInt2, localItemInfo);
    } else {
      this.mItems.add(localItemInfo);
    }
    return localItemInfo;
  }
  
  public void addOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener)
  {
    if (this.mAdapterChangeListeners == null) {
      this.mAdapterChangeListeners = new ArrayList();
    }
    this.mAdapterChangeListeners.add(paramOnAdapterChangeListener);
  }
  
  public void addOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    if (this.mOnPageChangeListeners == null) {
      this.mOnPageChangeListeners = new ArrayList();
    }
    this.mOnPageChangeListeners.add(paramOnPageChangeListener);
  }
  
  public void addTouchables(ArrayList<View> paramArrayList)
  {
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem)) {
          localView.addTouchables(paramArrayList);
        }
      }
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    ViewGroup.LayoutParams localLayoutParams = paramLayoutParams;
    if (!checkLayoutParams(paramLayoutParams)) {
      localLayoutParams = generateLayoutParams(paramLayoutParams);
    }
    paramLayoutParams = (LayoutParams)localLayoutParams;
    paramLayoutParams.isDecor |= isDecorView(paramView);
    if (this.mInLayout)
    {
      if ((paramLayoutParams != null) && (paramLayoutParams.isDecor)) {
        throw new IllegalStateException("Cannot add pager decor view during layout");
      }
      paramLayoutParams.needsMeasure = true;
      addViewInLayout(paramView, paramInt, localLayoutParams);
    }
    else
    {
      super.addView(paramView, paramInt, localLayoutParams);
    }
  }
  
  public boolean arrowScroll(int paramInt)
  {
    View localView = findFocus();
    Object localObject;
    int j;
    int i;
    if (localView == this)
    {
      localObject = null;
    }
    else
    {
      localObject = localView;
      if (localView != null)
      {
        j = 0;
        for (localObject = localView.getParent();; localObject = ((ViewParent)localObject).getParent())
        {
          i = j;
          if (!(localObject instanceof ViewGroup)) {
            break;
          }
          if (localObject == this)
          {
            i = 1;
            break;
          }
        }
        localObject = localView;
        if (i == 0)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(localView.getClass().getSimpleName());
          for (localObject = localView.getParent(); (localObject instanceof ViewGroup); localObject = ((ViewParent)localObject).getParent()) {
            localStringBuilder.append(" => ").append(localObject.getClass().getSimpleName());
          }
          Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + localStringBuilder.toString());
          localObject = null;
        }
      }
    }
    boolean bool2 = false;
    boolean bool1 = false;
    localView = FocusFinder.getInstance().findNextFocus(this, (View)localObject, paramInt);
    if ((localView != null) && (localView != localObject)) {
      if (paramInt == 17)
      {
        j = getChildRectInPagerCoordinates(this.mTempRect, localView).left;
        i = getChildRectInPagerCoordinates(this.mTempRect, (View)localObject).left;
        if ((localObject != null) && (j >= i)) {
          bool1 = pageLeft();
        } else {
          bool1 = localView.requestFocus();
        }
      }
    }
    for (;;)
    {
      break;
      if (paramInt == 66)
      {
        j = getChildRectInPagerCoordinates(this.mTempRect, localView).left;
        i = getChildRectInPagerCoordinates(this.mTempRect, (View)localObject).left;
        if ((localObject != null) && (j <= i)) {
          bool1 = pageRight();
        } else {
          bool1 = localView.requestFocus();
        }
        break;
        if ((paramInt != 17) && (paramInt != 1))
        {
          if (paramInt != 66)
          {
            bool1 = bool2;
            if (paramInt != 2) {
              break;
            }
          }
          else
          {
            bool1 = pageRight();
          }
        }
        else {
          bool1 = pageLeft();
        }
      }
    }
    if (bool1) {
      playSoundEffect(SoundEffectConstants.getContantForFocusDirection(paramInt));
    }
    return bool1;
  }
  
  public boolean beginFakeDrag()
  {
    if (this.mIsBeingDragged) {
      return false;
    }
    this.mFakeDragging = true;
    setScrollState(1);
    this.mLastMotionX = 0.0F;
    this.mInitialMotionX = 0.0F;
    Object localObject = this.mVelocityTracker;
    if (localObject == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    } else {
      ((VelocityTracker)localObject).clear();
    }
    long l = SystemClock.uptimeMillis();
    localObject = MotionEvent.obtain(l, l, 0, 0.0F, 0.0F, 0);
    this.mVelocityTracker.addMovement((MotionEvent)localObject);
    ((MotionEvent)localObject).recycle();
    this.mFakeDragBeginTime = l;
    return true;
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool2 = paramView instanceof ViewGroup;
    boolean bool1 = true;
    if (bool2)
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int j = paramView.getScrollX();
      int k = paramView.getScrollY();
      for (int i = localViewGroup.getChildCount() - 1; i >= 0; i--)
      {
        View localView = localViewGroup.getChildAt(i);
        if ((paramInt2 + j >= localView.getLeft()) && (paramInt2 + j < localView.getRight()) && (paramInt3 + k >= localView.getTop()) && (paramInt3 + k < localView.getBottom()) && (canScroll(localView, true, paramInt1, paramInt2 + j - localView.getLeft(), paramInt3 + k - localView.getTop()))) {
          return true;
        }
      }
    }
    if ((paramBoolean) && (paramView.canScrollHorizontally(-paramInt1))) {
      paramBoolean = bool1;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public boolean canScrollHorizontally(int paramInt)
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    boolean bool1 = false;
    boolean bool2 = false;
    if (localPagerAdapter == null) {
      return false;
    }
    int i = getClientWidth();
    int j = getScrollX();
    if (paramInt < 0)
    {
      bool1 = bool2;
      if (j > (int)(i * this.mFirstOffset)) {
        bool1 = true;
      }
      return bool1;
    }
    if (paramInt > 0)
    {
      if (j < (int)(i * this.mLastOffset)) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if (((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void clearOnPageChangeListeners()
  {
    List localList = this.mOnPageChangeListeners;
    if (localList != null) {
      localList.clear();
    }
  }
  
  public void computeScroll()
  {
    this.mIsScrollStarted = true;
    if ((!this.mScroller.isFinished()) && (this.mScroller.computeScrollOffset()))
    {
      int m = getScrollX();
      int j = getScrollY();
      int i = this.mScroller.getCurrX();
      int k = this.mScroller.getCurrY();
      if ((m != i) || (j != k))
      {
        scrollTo(i, k);
        if (!pageScrolled(i))
        {
          this.mScroller.abortAnimation();
          scrollTo(0, k);
        }
      }
      ViewCompat.postInvalidateOnAnimation(this);
      return;
    }
    completeScroll(true);
  }
  
  void dataSetChanged()
  {
    int i4 = this.mAdapter.getCount();
    this.mExpectedAdapterCount = i4;
    int i;
    if ((this.mItems.size() < this.mOffscreenPageLimit * 2 + 1) && (this.mItems.size() < i4)) {
      i = 1;
    } else {
      i = 0;
    }
    int j = this.mCurItem;
    int k = 0;
    int m = 0;
    Object localObject;
    while (m < this.mItems.size())
    {
      localObject = (ItemInfo)this.mItems.get(m);
      int i3 = this.mAdapter.getItemPosition(((ItemInfo)localObject).object);
      int n;
      int i1;
      int i2;
      if (i3 == -1)
      {
        n = j;
        i1 = k;
        i2 = m;
      }
      else if (i3 == -2)
      {
        this.mItems.remove(m);
        i3 = m - 1;
        m = k;
        if (k == 0)
        {
          this.mAdapter.startUpdate(this);
          m = 1;
        }
        this.mAdapter.destroyItem(this, ((ItemInfo)localObject).position, ((ItemInfo)localObject).object);
        i = 1;
        n = j;
        i1 = m;
        i2 = i3;
        if (this.mCurItem == ((ItemInfo)localObject).position)
        {
          n = Math.max(0, Math.min(this.mCurItem, i4 - 1));
          i = 1;
          i1 = m;
          i2 = i3;
        }
      }
      else
      {
        n = j;
        i1 = k;
        i2 = m;
        if (((ItemInfo)localObject).position != i3)
        {
          if (((ItemInfo)localObject).position == this.mCurItem) {
            j = i3;
          }
          ((ItemInfo)localObject).position = i3;
          i = 1;
          i2 = m;
          i1 = k;
          n = j;
        }
      }
      m = i2 + 1;
      j = n;
      k = i1;
    }
    if (k != 0) {
      this.mAdapter.finishUpdate(this);
    }
    Collections.sort(this.mItems, COMPARATOR);
    if (i != 0)
    {
      k = getChildCount();
      for (i = 0; i < k; i++)
      {
        localObject = (LayoutParams)getChildAt(i).getLayoutParams();
        if (!((LayoutParams)localObject).isDecor) {
          ((LayoutParams)localObject).widthFactor = 0.0F;
        }
      }
      setCurrentItemInternal(j, false, true);
      requestLayout();
    }
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
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (paramAccessibilityEvent.getEventType() == 4096) {
      return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    }
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem) && (localView.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent))) {
          return true;
        }
      }
    }
    return false;
  }
  
  float distanceInfluenceForSnapDuration(float paramFloat)
  {
    return (float)Math.sin((paramFloat - 0.5F) * 0.47123894F);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    int k = 0;
    int i = 0;
    int m = getOverScrollMode();
    if (m != 0) {
      if (m == 1)
      {
        PagerAdapter localPagerAdapter = this.mAdapter;
        if ((localPagerAdapter != null) && (localPagerAdapter.getCount() > 1)) {}
      }
      else
      {
        this.mLeftEdge.finish();
        this.mRightEdge.finish();
        break label257;
      }
    }
    int j;
    if (!this.mLeftEdge.isFinished())
    {
      k = paramCanvas.save();
      i = getHeight() - getPaddingTop() - getPaddingBottom();
      m = getWidth();
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-i + getPaddingTop(), this.mFirstOffset * m);
      this.mLeftEdge.setSize(i, m);
      j = false | this.mLeftEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(k);
    }
    k = j;
    boolean bool;
    if (!this.mRightEdge.isFinished())
    {
      m = paramCanvas.save();
      int i1 = getWidth();
      int i2 = getHeight();
      int n = getPaddingTop();
      k = getPaddingBottom();
      paramCanvas.rotate(90.0F);
      paramCanvas.translate(-getPaddingTop(), -(this.mLastOffset + 1.0F) * i1);
      this.mRightEdge.setSize(i2 - n - k, i1);
      bool = j | this.mRightEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(m);
    }
    label257:
    if (bool) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    Drawable localDrawable = this.mMarginDrawable;
    if ((localDrawable != null) && (localDrawable.isStateful())) {
      localDrawable.setState(getDrawableState());
    }
  }
  
  public void endFakeDrag()
  {
    if (this.mFakeDragging)
    {
      if (this.mAdapter != null)
      {
        Object localObject = this.mVelocityTracker;
        ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumVelocity);
        int j = (int)((VelocityTracker)localObject).getXVelocity(this.mActivePointerId);
        this.mPopulatePending = true;
        int i = getClientWidth();
        int k = getScrollX();
        localObject = infoForCurrentScrollPosition();
        setCurrentItemInternal(determineTargetPage(((ItemInfo)localObject).position, (k / i - ((ItemInfo)localObject).offset) / ((ItemInfo)localObject).widthFactor, j, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, j);
      }
      endDrag();
      this.mFakeDragging = false;
      return;
    }
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  public boolean executeKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode())
      {
      default: 
        bool1 = bool2;
        break;
      case 61: 
        if (paramKeyEvent.hasNoModifiers())
        {
          bool1 = arrowScroll(2);
        }
        else
        {
          bool1 = bool2;
          if (paramKeyEvent.hasModifiers(1)) {
            bool1 = arrowScroll(1);
          }
        }
        break;
      case 22: 
        if (paramKeyEvent.hasModifiers(2)) {
          bool1 = pageRight();
        } else {
          bool1 = arrowScroll(66);
        }
        break;
      case 21: 
        if (paramKeyEvent.hasModifiers(2)) {
          bool1 = pageLeft();
        } else {
          bool1 = arrowScroll(17);
        }
        break;
      }
    }
    return bool1;
  }
  
  public void fakeDragBy(float paramFloat)
  {
    if (this.mFakeDragging)
    {
      if (this.mAdapter == null) {
        return;
      }
      this.mLastMotionX += paramFloat;
      float f2 = getScrollX() - paramFloat;
      int i = getClientWidth();
      paramFloat = i * this.mFirstOffset;
      float f1 = i * this.mLastOffset;
      Object localObject1 = (ItemInfo)this.mItems.get(0);
      Object localObject2 = this.mItems;
      localObject2 = (ItemInfo)((ArrayList)localObject2).get(((ArrayList)localObject2).size() - 1);
      if (((ItemInfo)localObject1).position != 0) {
        paramFloat = ((ItemInfo)localObject1).offset * i;
      }
      if (((ItemInfo)localObject2).position != this.mAdapter.getCount() - 1) {
        f1 = ((ItemInfo)localObject2).offset * i;
      }
      if (f2 >= paramFloat)
      {
        paramFloat = f2;
        if (f2 > f1) {
          paramFloat = f1;
        }
      }
      this.mLastMotionX += paramFloat - (int)paramFloat;
      scrollTo((int)paramFloat, getScrollY());
      pageScrolled((int)paramFloat);
      long l = SystemClock.uptimeMillis();
      localObject1 = MotionEvent.obtain(this.mFakeDragBeginTime, l, 2, this.mLastMotionX, 0.0F, 0);
      this.mVelocityTracker.addMovement((MotionEvent)localObject1);
      ((MotionEvent)localObject1).recycle();
      return;
    }
    throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return generateDefaultLayoutParams();
  }
  
  public PagerAdapter getAdapter()
  {
    return this.mAdapter;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2)
  {
    if (this.mDrawingOrder == 2) {
      paramInt2 = paramInt1 - 1 - paramInt2;
    }
    return ((LayoutParams)((View)this.mDrawingOrderedChildren.get(paramInt2)).getLayoutParams()).childIndex;
  }
  
  public int getCurrentItem()
  {
    return this.mCurItem;
  }
  
  public int getOffscreenPageLimit()
  {
    return this.mOffscreenPageLimit;
  }
  
  public int getPageMargin()
  {
    return this.mPageMargin;
  }
  
  ItemInfo infoForAnyChild(View paramView)
  {
    for (;;)
    {
      ViewParent localViewParent = paramView.getParent();
      if (localViewParent == this) {
        break label34;
      }
      if ((localViewParent == null) || (!(localViewParent instanceof View))) {
        break;
      }
      paramView = (View)localViewParent;
    }
    return null;
    label34:
    return infoForChild(paramView);
  }
  
  ItemInfo infoForChild(View paramView)
  {
    for (int i = 0; i < this.mItems.size(); i++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
      if (this.mAdapter.isViewFromObject(paramView, localItemInfo.object)) {
        return localItemInfo;
      }
    }
    return null;
  }
  
  ItemInfo infoForPosition(int paramInt)
  {
    for (int i = 0; i < this.mItems.size(); i++)
    {
      ItemInfo localItemInfo = (ItemInfo)this.mItems.get(i);
      if (localItemInfo.position == paramInt) {
        return localItemInfo;
      }
    }
    return null;
  }
  
  void initViewPager()
  {
    setWillNotDraw(false);
    setDescendantFocusability(262144);
    setFocusable(true);
    Context localContext = getContext();
    this.mScroller = new Scroller(localContext, sInterpolator);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(localContext);
    float f = localContext.getResources().getDisplayMetrics().density;
    this.mTouchSlop = localViewConfiguration.getScaledPagingTouchSlop();
    this.mMinimumVelocity = ((int)(400.0F * f));
    this.mMaximumVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
    this.mLeftEdge = new EdgeEffect(localContext);
    this.mRightEdge = new EdgeEffect(localContext);
    this.mFlingDistance = ((int)(25.0F * f));
    this.mCloseEnough = ((int)(2.0F * f));
    this.mDefaultGutterSize = ((int)(16.0F * f));
    ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
    if (ViewCompat.getImportantForAccessibility(this) == 0) {
      ViewCompat.setImportantForAccessibility(this, 1);
    }
    ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener()
    {
      private final Rect mTempRect = new Rect();
      
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
      {
        WindowInsetsCompat localWindowInsetsCompat = ViewCompat.onApplyWindowInsets(paramAnonymousView, paramAnonymousWindowInsetsCompat);
        if (localWindowInsetsCompat.isConsumed()) {
          return localWindowInsetsCompat;
        }
        paramAnonymousWindowInsetsCompat = this.mTempRect;
        paramAnonymousWindowInsetsCompat.left = localWindowInsetsCompat.getSystemWindowInsetLeft();
        paramAnonymousWindowInsetsCompat.top = localWindowInsetsCompat.getSystemWindowInsetTop();
        paramAnonymousWindowInsetsCompat.right = localWindowInsetsCompat.getSystemWindowInsetRight();
        paramAnonymousWindowInsetsCompat.bottom = localWindowInsetsCompat.getSystemWindowInsetBottom();
        int i = 0;
        int j = ViewPager.this.getChildCount();
        while (i < j)
        {
          paramAnonymousView = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), localWindowInsetsCompat);
          paramAnonymousWindowInsetsCompat.left = Math.min(paramAnonymousView.getSystemWindowInsetLeft(), paramAnonymousWindowInsetsCompat.left);
          paramAnonymousWindowInsetsCompat.top = Math.min(paramAnonymousView.getSystemWindowInsetTop(), paramAnonymousWindowInsetsCompat.top);
          paramAnonymousWindowInsetsCompat.right = Math.min(paramAnonymousView.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.right);
          paramAnonymousWindowInsetsCompat.bottom = Math.min(paramAnonymousView.getSystemWindowInsetBottom(), paramAnonymousWindowInsetsCompat.bottom);
          i++;
        }
        return localWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.left, paramAnonymousWindowInsetsCompat.top, paramAnonymousWindowInsetsCompat.right, paramAnonymousWindowInsetsCompat.bottom);
      }
    });
  }
  
  public boolean isFakeDragging()
  {
    return this.mFakeDragging;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow()
  {
    removeCallbacks(this.mEndScrollRunnable);
    Scroller localScroller = this.mScroller;
    if ((localScroller != null) && (!localScroller.isFinished())) {
      this.mScroller.abortAnimation();
    }
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mPageMargin > 0) && (this.mMarginDrawable != null) && (this.mItems.size() > 0) && (this.mAdapter != null))
    {
      int n = getScrollX();
      int m = getWidth();
      float f2 = this.mPageMargin / m;
      int j = 0;
      Object localObject = (ItemInfo)this.mItems.get(0);
      float f1 = ((ItemInfo)localObject).offset;
      int k = this.mItems.size();
      int i = ((ItemInfo)localObject).position;
      int i1 = ((ItemInfo)this.mItems.get(k - 1)).position;
      while (i < i1)
      {
        while ((i > ((ItemInfo)localObject).position) && (j < k))
        {
          localObject = this.mItems;
          j++;
          localObject = (ItemInfo)((ArrayList)localObject).get(j);
        }
        float f3;
        if (i == ((ItemInfo)localObject).position)
        {
          f3 = (((ItemInfo)localObject).offset + ((ItemInfo)localObject).widthFactor) * m;
          f1 = ((ItemInfo)localObject).offset + ((ItemInfo)localObject).widthFactor + f2;
        }
        else
        {
          f3 = this.mAdapter.getPageWidth(i);
          float f5 = m;
          float f4 = f1 + (f3 + f2);
          f3 = (f1 + f3) * f5;
          f1 = f4;
        }
        if (this.mPageMargin + f3 > n)
        {
          this.mMarginDrawable.setBounds(Math.round(f3), this.mTopPageBounds, Math.round(this.mPageMargin + f3), this.mBottomPageBounds);
          this.mMarginDrawable.draw(paramCanvas);
        }
        if (f3 > n + m) {
          break;
        }
        i++;
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction() & 0xFF;
    if ((i != 3) && (i != 1))
    {
      if (i != 0)
      {
        if (this.mIsBeingDragged) {
          return true;
        }
        if (this.mIsUnableToDrag) {
          return false;
        }
      }
      float f1;
      switch (i)
      {
      default: 
        break;
      case 6: 
        onSecondaryPointerUp(paramMotionEvent);
        break;
      case 2: 
        i = this.mActivePointerId;
        if (i != -1)
        {
          i = paramMotionEvent.findPointerIndex(i);
          float f2 = paramMotionEvent.getX(i);
          float f4 = f2 - this.mLastMotionX;
          float f5 = Math.abs(f4);
          float f3 = paramMotionEvent.getY(i);
          f1 = Math.abs(f3 - this.mInitialMotionY);
          if ((f4 != 0.0F) && (!isGutterDrag(this.mLastMotionX, f4)) && (canScroll(this, false, (int)f4, (int)f2, (int)f3)))
          {
            this.mLastMotionX = f2;
            this.mLastMotionY = f3;
            this.mIsUnableToDrag = true;
            return false;
          }
          i = this.mTouchSlop;
          if ((f5 > i) && (0.5F * f5 > f1))
          {
            this.mIsBeingDragged = true;
            requestParentDisallowInterceptTouchEvent(true);
            setScrollState(1);
            if (f4 > 0.0F) {
              f1 = this.mInitialMotionX + this.mTouchSlop;
            } else {
              f1 = this.mInitialMotionX - this.mTouchSlop;
            }
            this.mLastMotionX = f1;
            this.mLastMotionY = f3;
            setScrollingCacheEnabled(true);
          }
          else if (f1 > i)
          {
            this.mIsUnableToDrag = true;
          }
          if ((this.mIsBeingDragged) && (performDrag(f2))) {
            ViewCompat.postInvalidateOnAnimation(this);
          }
        }
        break;
      case 0: 
        f1 = paramMotionEvent.getX();
        this.mInitialMotionX = f1;
        this.mLastMotionX = f1;
        f1 = paramMotionEvent.getY();
        this.mInitialMotionY = f1;
        this.mLastMotionY = f1;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
        this.mIsUnableToDrag = false;
        this.mIsScrollStarted = true;
        this.mScroller.computeScrollOffset();
        if ((this.mScrollState == 2) && (Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough))
        {
          this.mScroller.abortAnimation();
          this.mPopulatePending = false;
          populate();
          this.mIsBeingDragged = true;
          requestParentDisallowInterceptTouchEvent(true);
          setScrollState(1);
        }
        else
        {
          completeScroll(false);
          this.mIsBeingDragged = false;
        }
        break;
      }
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      return this.mIsBeingDragged;
    }
    resetTouch();
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i3 = getChildCount();
    int i4 = paramInt3 - paramInt1;
    int i5 = paramInt4 - paramInt2;
    paramInt1 = getPaddingLeft();
    paramInt2 = getPaddingTop();
    int i = getPaddingRight();
    paramInt4 = getPaddingBottom();
    int i6 = getScrollX();
    int k = 0;
    int m = 0;
    Object localObject;
    int n;
    LayoutParams localLayoutParams;
    while (m < i3)
    {
      localObject = getChildAt(m);
      paramInt3 = paramInt1;
      j = paramInt2;
      n = i;
      int i1 = paramInt4;
      int i2 = k;
      if (((View)localObject).getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)((View)localObject).getLayoutParams();
        if (localLayoutParams.isDecor)
        {
          paramInt3 = localLayoutParams.gravity;
          n = localLayoutParams.gravity;
          switch (paramInt3 & 0x7)
          {
          case 2: 
          case 4: 
          default: 
            paramInt3 = paramInt1;
            j = paramInt1;
            break;
          case 5: 
            paramInt3 = i4 - i - ((View)localObject).getMeasuredWidth();
            i += ((View)localObject).getMeasuredWidth();
            j = paramInt1;
            break;
          case 3: 
            paramInt3 = paramInt1;
            j = paramInt1 + ((View)localObject).getMeasuredWidth();
            break;
          case 1: 
            paramInt3 = Math.max((i4 - ((View)localObject).getMeasuredWidth()) / 2, paramInt1);
            j = paramInt1;
          }
          switch (n & 0x70)
          {
          default: 
            paramInt1 = paramInt2;
            break;
          case 80: 
            paramInt1 = i5 - paramInt4 - ((View)localObject).getMeasuredHeight();
            paramInt4 += ((View)localObject).getMeasuredHeight();
            break;
          case 48: 
            paramInt1 = paramInt2;
            paramInt2 += ((View)localObject).getMeasuredHeight();
            break;
          case 16: 
            paramInt1 = Math.max((i5 - ((View)localObject).getMeasuredHeight()) / 2, paramInt2);
          }
          paramInt3 += i6;
          ((View)localObject).layout(paramInt3, paramInt1, paramInt3 + ((View)localObject).getMeasuredWidth(), paramInt1 + ((View)localObject).getMeasuredHeight());
          i2 = k + 1;
          paramInt3 = j;
          j = paramInt2;
          n = i;
          i1 = paramInt4;
        }
        else
        {
          i2 = k;
          i1 = paramInt4;
          n = i;
          j = paramInt2;
          paramInt3 = paramInt1;
        }
      }
      m++;
      paramInt1 = paramInt3;
      paramInt2 = j;
      i = n;
      paramInt4 = i1;
      k = i2;
    }
    m = i4 - paramInt1 - i;
    i = 0;
    paramInt3 = i4;
    int j = i3;
    while (i < j)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (!localLayoutParams.isDecor)
        {
          localObject = infoForChild(localView);
          if (localObject != null)
          {
            n = paramInt1 + (int)(m * ((ItemInfo)localObject).offset);
            if (localLayoutParams.needsMeasure)
            {
              localLayoutParams.needsMeasure = false;
              localView.measure(View.MeasureSpec.makeMeasureSpec((int)(m * localLayoutParams.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec(i5 - paramInt2 - paramInt4, 1073741824));
            }
            localView.layout(n, paramInt2, localView.getMeasuredWidth() + n, localView.getMeasuredHeight() + paramInt2);
          }
        }
        else {}
      }
      i++;
    }
    this.mTopPageBounds = paramInt2;
    this.mBottomPageBounds = (i5 - paramInt4);
    this.mDecorChildCount = k;
    if (this.mFirstLayout) {
      scrollToItem(this.mCurItem, false, 0, false);
    }
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(getDefaultSize(0, paramInt1), getDefaultSize(0, paramInt2));
    int n = getMeasuredWidth();
    int i1 = n / 10;
    this.mGutterSize = Math.min(i1, this.mDefaultGutterSize);
    paramInt1 = n - getPaddingLeft() - getPaddingRight();
    paramInt2 = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    int i5 = getChildCount();
    int m = 0;
    Object localObject2;
    Object localObject1;
    while (m < i5)
    {
      localObject2 = getChildAt(m);
      int j;
      if (((View)localObject2).getVisibility() != 8)
      {
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        if ((localObject1 != null) && (((LayoutParams)localObject1).isDecor))
        {
          i = ((LayoutParams)localObject1).gravity & 0x7;
          int i2 = ((LayoutParams)localObject1).gravity & 0x70;
          int k = Integer.MIN_VALUE;
          j = Integer.MIN_VALUE;
          if ((i2 != 48) && (i2 != 80)) {
            i2 = 0;
          } else {
            i2 = 1;
          }
          int i3;
          if ((i != 3) && (i != 5)) {
            i3 = 0;
          } else {
            i3 = 1;
          }
          if (i2 != 0)
          {
            i = 1073741824;
          }
          else
          {
            i = k;
            if (i3 != 0)
            {
              j = 1073741824;
              i = k;
            }
          }
          int i4;
          if (((LayoutParams)localObject1).width != -2)
          {
            i4 = 1073741824;
            if (((LayoutParams)localObject1).width != -1) {
              i = ((LayoutParams)localObject1).width;
            } else {
              i = paramInt1;
            }
          }
          else
          {
            k = paramInt1;
            i4 = i;
            i = k;
          }
          if (((LayoutParams)localObject1).height != -2)
          {
            if (((LayoutParams)localObject1).height != -1)
            {
              k = ((LayoutParams)localObject1).height;
              j = 1073741824;
            }
            else
            {
              j = 1073741824;
              k = paramInt2;
            }
          }
          else {
            k = paramInt2;
          }
          ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(i, i4), View.MeasureSpec.makeMeasureSpec(k, j));
          if (i2 != 0)
          {
            j = paramInt2 - ((View)localObject2).getMeasuredHeight();
            i = paramInt1;
          }
          else
          {
            i = paramInt1;
            j = paramInt2;
            if (i3 != 0)
            {
              i = paramInt1 - ((View)localObject2).getMeasuredWidth();
              j = paramInt2;
            }
          }
        }
        else
        {
          i = paramInt1;
          j = paramInt2;
        }
      }
      else
      {
        j = paramInt2;
        i = paramInt1;
      }
      m++;
      paramInt1 = i;
      paramInt2 = j;
    }
    this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824);
    this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824);
    this.mInLayout = true;
    populate();
    this.mInLayout = false;
    int i = getChildCount();
    for (paramInt2 = 0; paramInt2 < i; paramInt2++)
    {
      localObject1 = getChildAt(paramInt2);
      if (((View)localObject1).getVisibility() != 8)
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if ((localObject2 == null) || (!((LayoutParams)localObject2).isDecor)) {
          ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec((int)(paramInt1 * ((LayoutParams)localObject2).widthFactor), 1073741824), this.mChildHeightMeasureSpec);
        }
      }
    }
  }
  
  protected void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
    int i;
    Object localObject;
    if (this.mDecorChildCount > 0)
    {
      int i3 = getScrollX();
      i = getPaddingLeft();
      int k = getPaddingRight();
      int i1 = getWidth();
      int i2 = getChildCount();
      int m = 0;
      while (m < i2)
      {
        View localView = getChildAt(m);
        localObject = (LayoutParams)localView.getLayoutParams();
        int n;
        int j;
        if (!((LayoutParams)localObject).isDecor)
        {
          n = i;
          j = k;
        }
        else
        {
          switch (((LayoutParams)localObject).gravity & 0x7)
          {
          case 2: 
          case 4: 
          default: 
            j = i;
            break;
          case 5: 
            j = i1 - k - localView.getMeasuredWidth();
            k += localView.getMeasuredWidth();
            break;
          case 3: 
            j = i;
            i += localView.getWidth();
            break;
          case 1: 
            j = Math.max((i1 - localView.getMeasuredWidth()) / 2, i);
          }
          int i4 = j + i3 - localView.getLeft();
          n = i;
          j = k;
          if (i4 != 0)
          {
            localView.offsetLeftAndRight(i4);
            j = k;
            n = i;
          }
        }
        m++;
        i = n;
        k = j;
      }
    }
    dispatchOnPageScrolled(paramInt1, paramFloat, paramInt2);
    if (this.mPageTransformer != null)
    {
      i = getScrollX();
      paramInt2 = getChildCount();
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      {
        localObject = getChildAt(paramInt1);
        if (!((LayoutParams)((View)localObject).getLayoutParams()).isDecor)
        {
          paramFloat = (((View)localObject).getLeft() - i) / getClientWidth();
          this.mPageTransformer.transformPage((View)localObject, paramFloat);
        }
      }
    }
    this.mCalledSuper = true;
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect)
  {
    int k = getChildCount();
    int i;
    int j;
    if ((paramInt & 0x2) != 0)
    {
      i = 0;
      j = 1;
    }
    else
    {
      i = k - 1;
      j = -1;
      k = -1;
    }
    while (i != k)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 0)
      {
        ItemInfo localItemInfo = infoForChild(localView);
        if ((localItemInfo != null) && (localItemInfo.position == this.mCurItem) && (localView.requestFocus(paramInt, paramRect))) {
          return true;
        }
      }
      i += j;
    }
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    PagerAdapter localPagerAdapter = this.mAdapter;
    if (localPagerAdapter != null)
    {
      localPagerAdapter.restoreState(paramParcelable.adapterState, paramParcelable.loader);
      setCurrentItemInternal(paramParcelable.position, false, true);
    }
    else
    {
      this.mRestoredCurItem = paramParcelable.position;
      this.mRestoredAdapterState = paramParcelable.adapterState;
      this.mRestoredClassLoader = paramParcelable.loader;
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.position = this.mCurItem;
    PagerAdapter localPagerAdapter = this.mAdapter;
    if (localPagerAdapter != null) {
      localSavedState.adapterState = localPagerAdapter.saveState();
    }
    return localSavedState;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3)
    {
      paramInt2 = this.mPageMargin;
      recomputeScrollPosition(paramInt1, paramInt3, paramInt2, paramInt2);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mFakeDragging) {
      return true;
    }
    if ((paramMotionEvent.getAction() == 0) && (paramMotionEvent.getEdgeFlags() != 0)) {
      return false;
    }
    Object localObject = this.mAdapter;
    if ((localObject != null) && (((PagerAdapter)localObject).getCount() != 0))
    {
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int i = paramMotionEvent.getAction();
      boolean bool = false;
      float f1;
      switch (i & 0xFF)
      {
      case 4: 
      default: 
        break;
      case 6: 
        onSecondaryPointerUp(paramMotionEvent);
        this.mLastMotionX = paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId));
        break;
      case 5: 
        i = paramMotionEvent.getActionIndex();
        this.mLastMotionX = paramMotionEvent.getX(i);
        this.mActivePointerId = paramMotionEvent.getPointerId(i);
        break;
      case 3: 
        if (this.mIsBeingDragged)
        {
          scrollToItem(this.mCurItem, true, 0, false);
          bool = resetTouch();
        }
        break;
      case 2: 
        if (!this.mIsBeingDragged)
        {
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          if (i == -1)
          {
            bool = resetTouch();
            break;
          }
          f1 = paramMotionEvent.getX(i);
          float f4 = Math.abs(f1 - this.mLastMotionX);
          float f2 = paramMotionEvent.getY(i);
          float f3 = Math.abs(f2 - this.mLastMotionY);
          if ((f4 > this.mTouchSlop) && (f4 > f3))
          {
            this.mIsBeingDragged = true;
            requestParentDisallowInterceptTouchEvent(true);
            f3 = this.mInitialMotionX;
            if (f1 - f3 > 0.0F) {
              f1 = f3 + this.mTouchSlop;
            } else {
              f1 = f3 - this.mTouchSlop;
            }
            this.mLastMotionX = f1;
            this.mLastMotionY = f2;
            setScrollState(1);
            setScrollingCacheEnabled(true);
            localObject = getParent();
            if (localObject != null) {
              ((ViewParent)localObject).requestDisallowInterceptTouchEvent(true);
            }
          }
        }
        if (this.mIsBeingDragged) {
          bool = false | performDrag(paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId)));
        }
        break;
      case 1: 
        if (this.mIsBeingDragged)
        {
          localObject = this.mVelocityTracker;
          ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumVelocity);
          int j = (int)((VelocityTracker)localObject).getXVelocity(this.mActivePointerId);
          this.mPopulatePending = true;
          i = getClientWidth();
          int k = getScrollX();
          localObject = infoForCurrentScrollPosition();
          f1 = this.mPageMargin / i;
          setCurrentItemInternal(determineTargetPage(((ItemInfo)localObject).position, (k / i - ((ItemInfo)localObject).offset) / (((ItemInfo)localObject).widthFactor + f1), j, (int)(paramMotionEvent.getX(paramMotionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, j);
          bool = resetTouch();
        }
        break;
      case 0: 
        this.mScroller.abortAnimation();
        this.mPopulatePending = false;
        populate();
        f1 = paramMotionEvent.getX();
        this.mInitialMotionX = f1;
        this.mLastMotionX = f1;
        f1 = paramMotionEvent.getY();
        this.mInitialMotionY = f1;
        this.mLastMotionY = f1;
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
      }
      if (bool) {
        ViewCompat.postInvalidateOnAnimation(this);
      }
      return true;
    }
    return false;
  }
  
  boolean pageLeft()
  {
    int i = this.mCurItem;
    if (i > 0)
    {
      setCurrentItem(i - 1, true);
      return true;
    }
    return false;
  }
  
  boolean pageRight()
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    if ((localPagerAdapter != null) && (this.mCurItem < localPagerAdapter.getCount() - 1))
    {
      setCurrentItem(this.mCurItem + 1, true);
      return true;
    }
    return false;
  }
  
  void populate()
  {
    populate(this.mCurItem);
  }
  
  void populate(int paramInt)
  {
    int i = this.mCurItem;
    Object localObject2;
    if (i != paramInt)
    {
      localObject2 = infoForPosition(i);
      this.mCurItem = paramInt;
    }
    else
    {
      localObject2 = null;
    }
    if (this.mAdapter == null)
    {
      sortChildDrawingOrder();
      return;
    }
    if (this.mPopulatePending)
    {
      sortChildDrawingOrder();
      return;
    }
    if (getWindowToken() == null) {
      return;
    }
    this.mAdapter.startUpdate(this);
    int n = this.mOffscreenPageLimit;
    int i1 = Math.max(0, this.mCurItem - n);
    int i2 = this.mAdapter.getCount();
    int i3 = Math.min(i2 - 1, this.mCurItem + n);
    Object localObject1;
    if (i2 == this.mExpectedAdapterCount)
    {
      Object localObject3 = null;
      Object localObject4;
      for (paramInt = 0;; paramInt++)
      {
        localObject1 = localObject3;
        if (paramInt >= this.mItems.size()) {
          break;
        }
        localObject4 = (ItemInfo)this.mItems.get(paramInt);
        if (((ItemInfo)localObject4).position >= this.mCurItem)
        {
          localObject1 = localObject3;
          if (((ItemInfo)localObject4).position != this.mCurItem) {
            break;
          }
          localObject1 = localObject4;
          break;
        }
      }
      localObject3 = localObject1;
      if (localObject1 == null)
      {
        localObject3 = localObject1;
        if (i2 > 0) {
          localObject3 = addNewItem(this.mCurItem, paramInt);
        }
      }
      if (localObject3 != null)
      {
        float f3 = 0.0F;
        int m = paramInt - 1;
        if (m >= 0) {
          localObject1 = (ItemInfo)this.mItems.get(m);
        } else {
          localObject1 = null;
        }
        int i4 = getClientWidth();
        float f2;
        if (i4 <= 0) {
          f2 = 0.0F;
        } else {
          f2 = 2.0F - ((ItemInfo)localObject3).widthFactor + getPaddingLeft() / i4;
        }
        int k = this.mCurItem - 1;
        localObject4 = localObject1;
        int j = paramInt;
        while (k >= 0)
        {
          if ((f3 >= f2) && (k < i1))
          {
            if (localObject4 == null) {
              break;
            }
            paramInt = j;
            f1 = f3;
            i = m;
            localObject1 = localObject4;
            if (k == ((ItemInfo)localObject4).position)
            {
              paramInt = j;
              f1 = f3;
              i = m;
              localObject1 = localObject4;
              if (!((ItemInfo)localObject4).scrolling)
              {
                this.mItems.remove(m);
                this.mAdapter.destroyItem(this, k, ((ItemInfo)localObject4).object);
                i = m - 1;
                paramInt = j - 1;
                if (i >= 0) {
                  localObject1 = (ItemInfo)this.mItems.get(i);
                } else {
                  localObject1 = null;
                }
                f1 = f3;
              }
            }
          }
          else if ((localObject4 != null) && (k == ((ItemInfo)localObject4).position))
          {
            f1 = f3 + ((ItemInfo)localObject4).widthFactor;
            i = m - 1;
            if (i >= 0) {
              localObject1 = (ItemInfo)this.mItems.get(i);
            } else {
              localObject1 = null;
            }
            paramInt = j;
          }
          else
          {
            f1 = f3 + addNewItem(k, m + 1).widthFactor;
            paramInt = j + 1;
            if (m >= 0) {
              localObject1 = (ItemInfo)this.mItems.get(m);
            } else {
              localObject1 = null;
            }
            i = m;
          }
          k--;
          j = paramInt;
          f3 = f1;
          m = i;
          localObject4 = localObject1;
        }
        float f1 = ((ItemInfo)localObject3).widthFactor;
        paramInt = j + 1;
        if (f1 < 2.0F)
        {
          if (paramInt < this.mItems.size()) {
            localObject1 = (ItemInfo)this.mItems.get(paramInt);
          } else {
            localObject1 = null;
          }
          if (i4 <= 0) {
            f2 = 0.0F;
          } else {
            f2 = getPaddingRight() / i4 + 2.0F;
          }
          i = this.mCurItem + 1;
          k = i1;
          m = n;
          while (i < i2)
          {
            if ((f1 >= f2) && (i > i3))
            {
              if (localObject1 == null) {
                break;
              }
              if ((i == ((ItemInfo)localObject1).position) && (!((ItemInfo)localObject1).scrolling))
              {
                this.mItems.remove(paramInt);
                this.mAdapter.destroyItem(this, i, ((ItemInfo)localObject1).object);
                if (paramInt < this.mItems.size()) {
                  localObject1 = (ItemInfo)this.mItems.get(paramInt);
                } else {
                  localObject1 = null;
                }
              }
            }
            else if ((localObject1 != null) && (i == ((ItemInfo)localObject1).position))
            {
              f1 += ((ItemInfo)localObject1).widthFactor;
              paramInt++;
              if (paramInt < this.mItems.size()) {
                localObject1 = (ItemInfo)this.mItems.get(paramInt);
              } else {
                localObject1 = null;
              }
            }
            else
            {
              localObject1 = addNewItem(i, paramInt);
              paramInt++;
              f1 += ((ItemInfo)localObject1).widthFactor;
              if (paramInt < this.mItems.size()) {
                localObject1 = (ItemInfo)this.mItems.get(paramInt);
              } else {
                localObject1 = null;
              }
            }
            i++;
          }
        }
        calculatePageOffsets((ItemInfo)localObject3, j, (ItemInfo)localObject2);
        this.mAdapter.setPrimaryItem(this, this.mCurItem, ((ItemInfo)localObject3).object);
      }
      this.mAdapter.finishUpdate(this);
      i = getChildCount();
      for (paramInt = 0; paramInt < i; paramInt++)
      {
        localObject2 = getChildAt(paramInt);
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        ((LayoutParams)localObject1).childIndex = paramInt;
        if ((!((LayoutParams)localObject1).isDecor) && (((LayoutParams)localObject1).widthFactor == 0.0F))
        {
          localObject2 = infoForChild((View)localObject2);
          if (localObject2 != null)
          {
            ((LayoutParams)localObject1).widthFactor = ((ItemInfo)localObject2).widthFactor;
            ((LayoutParams)localObject1).position = ((ItemInfo)localObject2).position;
          }
        }
      }
      sortChildDrawingOrder();
      if (hasFocus())
      {
        localObject1 = findFocus();
        if (localObject1 != null) {
          localObject1 = infoForAnyChild((View)localObject1);
        } else {
          localObject1 = null;
        }
        if ((localObject1 == null) || (((ItemInfo)localObject1).position != this.mCurItem)) {
          for (paramInt = 0; paramInt < getChildCount(); paramInt++)
          {
            localObject2 = getChildAt(paramInt);
            localObject1 = infoForChild((View)localObject2);
            if ((localObject1 != null) && (((ItemInfo)localObject1).position == this.mCurItem) && (((View)localObject2).requestFocus(2))) {
              break;
            }
          }
        }
      }
      return;
    }
    String str;
    try
    {
      localObject1 = getResources().getResourceName(getId());
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      str = Integer.toHexString(getId());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
    }
    throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + i2 + " Pager id: " + str + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
  }
  
  public void removeOnAdapterChangeListener(OnAdapterChangeListener paramOnAdapterChangeListener)
  {
    List localList = this.mAdapterChangeListeners;
    if (localList != null) {
      localList.remove(paramOnAdapterChangeListener);
    }
  }
  
  public void removeOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    List localList = this.mOnPageChangeListeners;
    if (localList != null) {
      localList.remove(paramOnPageChangeListener);
    }
  }
  
  public void removeView(View paramView)
  {
    if (this.mInLayout) {
      removeViewInLayout(paramView);
    } else {
      super.removeView(paramView);
    }
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    Object localObject = this.mAdapter;
    int i;
    if (localObject != null)
    {
      ((PagerAdapter)localObject).setViewPagerObserver(null);
      this.mAdapter.startUpdate(this);
      for (i = 0; i < this.mItems.size(); i++)
      {
        localObject = (ItemInfo)this.mItems.get(i);
        this.mAdapter.destroyItem(this, ((ItemInfo)localObject).position, ((ItemInfo)localObject).object);
      }
      this.mAdapter.finishUpdate(this);
      this.mItems.clear();
      removeNonDecorViews();
      this.mCurItem = 0;
      scrollTo(0, 0);
    }
    PagerAdapter localPagerAdapter = this.mAdapter;
    this.mAdapter = paramPagerAdapter;
    this.mExpectedAdapterCount = 0;
    if (paramPagerAdapter != null)
    {
      if (this.mObserver == null) {
        this.mObserver = new PagerObserver();
      }
      this.mAdapter.setViewPagerObserver(this.mObserver);
      this.mPopulatePending = false;
      boolean bool = this.mFirstLayout;
      this.mFirstLayout = true;
      this.mExpectedAdapterCount = this.mAdapter.getCount();
      if (this.mRestoredCurItem >= 0)
      {
        this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
        setCurrentItemInternal(this.mRestoredCurItem, false, true);
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
      }
      else if (!bool)
      {
        populate();
      }
      else
      {
        requestLayout();
      }
    }
    localObject = this.mAdapterChangeListeners;
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      i = 0;
      int j = this.mAdapterChangeListeners.size();
      while (i < j)
      {
        ((OnAdapterChangeListener)this.mAdapterChangeListeners.get(i)).onAdapterChanged(this, localPagerAdapter, paramPagerAdapter);
        i++;
      }
    }
  }
  
  public void setCurrentItem(int paramInt)
  {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, this.mFirstLayout ^ true, false);
  }
  
  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    this.mPopulatePending = false;
    setCurrentItemInternal(paramInt, paramBoolean, false);
  }
  
  void setCurrentItemInternal(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    setCurrentItemInternal(paramInt, paramBoolean1, paramBoolean2, 0);
  }
  
  void setCurrentItemInternal(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2)
  {
    PagerAdapter localPagerAdapter = this.mAdapter;
    boolean bool = false;
    if ((localPagerAdapter != null) && (localPagerAdapter.getCount() > 0))
    {
      if ((!paramBoolean2) && (this.mCurItem == paramInt1) && (this.mItems.size() != 0))
      {
        setScrollingCacheEnabled(false);
        return;
      }
      int i;
      if (paramInt1 < 0)
      {
        i = 0;
      }
      else
      {
        i = paramInt1;
        if (paramInt1 >= this.mAdapter.getCount()) {
          i = this.mAdapter.getCount() - 1;
        }
      }
      int j = this.mOffscreenPageLimit;
      paramInt1 = this.mCurItem;
      if ((i > paramInt1 + j) || (i < paramInt1 - j)) {
        for (paramInt1 = 0; paramInt1 < this.mItems.size(); paramInt1++) {
          ((ItemInfo)this.mItems.get(paramInt1)).scrolling = true;
        }
      }
      paramBoolean2 = bool;
      if (this.mCurItem != i) {
        paramBoolean2 = true;
      }
      if (this.mFirstLayout)
      {
        this.mCurItem = i;
        if (paramBoolean2) {
          dispatchOnPageSelected(i);
        }
        requestLayout();
      }
      else
      {
        populate(i);
        scrollToItem(i, paramBoolean1, paramInt2, paramBoolean2);
      }
      return;
    }
    setScrollingCacheEnabled(false);
  }
  
  OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    OnPageChangeListener localOnPageChangeListener = this.mInternalPageChangeListener;
    this.mInternalPageChangeListener = paramOnPageChangeListener;
    return localOnPageChangeListener;
  }
  
  public void setOffscreenPageLimit(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 1)
    {
      Log.w("ViewPager", "Requested offscreen page limit " + paramInt + " too small; defaulting to " + 1);
      i = 1;
    }
    if (i != this.mOffscreenPageLimit)
    {
      this.mOffscreenPageLimit = i;
      populate();
    }
  }
  
  @Deprecated
  public void setOnPageChangeListener(OnPageChangeListener paramOnPageChangeListener)
  {
    this.mOnPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setPageMargin(int paramInt)
  {
    int i = this.mPageMargin;
    this.mPageMargin = paramInt;
    int j = getWidth();
    recomputeScrollPosition(j, j, paramInt, i);
    requestLayout();
  }
  
  public void setPageMarginDrawable(int paramInt)
  {
    setPageMarginDrawable(ContextCompat.getDrawable(getContext(), paramInt));
  }
  
  public void setPageMarginDrawable(Drawable paramDrawable)
  {
    this.mMarginDrawable = paramDrawable;
    if (paramDrawable != null) {
      refreshDrawableState();
    }
    boolean bool;
    if (paramDrawable == null) {
      bool = true;
    } else {
      bool = false;
    }
    setWillNotDraw(bool);
    invalidate();
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer)
  {
    setPageTransformer(paramBoolean, paramPageTransformer, 2);
  }
  
  public void setPageTransformer(boolean paramBoolean, PageTransformer paramPageTransformer, int paramInt)
  {
    int j = 1;
    boolean bool1;
    if (paramPageTransformer != null) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2;
    if (this.mPageTransformer != null) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    int i;
    if (bool1 != bool2) {
      i = 1;
    } else {
      i = 0;
    }
    this.mPageTransformer = paramPageTransformer;
    setChildrenDrawingOrderEnabled(bool1);
    if (bool1)
    {
      if (paramBoolean) {
        j = 2;
      }
      this.mDrawingOrder = j;
      this.mPageTransformerLayerType = paramInt;
    }
    else
    {
      this.mDrawingOrder = 0;
    }
    if (i != 0) {
      populate();
    }
  }
  
  void setScrollState(int paramInt)
  {
    if (this.mScrollState == paramInt) {
      return;
    }
    this.mScrollState = paramInt;
    if (this.mPageTransformer != null)
    {
      boolean bool;
      if (paramInt != 0) {
        bool = true;
      } else {
        bool = false;
      }
      enableLayers(bool);
    }
    dispatchOnScrollStateChanged(paramInt);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2)
  {
    smoothScrollTo(paramInt1, paramInt2, 0);
  }
  
  void smoothScrollTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (getChildCount() == 0)
    {
      setScrollingCacheEnabled(false);
      return;
    }
    Scroller localScroller = this.mScroller;
    int i;
    if ((localScroller != null) && (!localScroller.isFinished())) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (this.mIsScrollStarted) {
        i = this.mScroller.getCurrX();
      } else {
        i = this.mScroller.getStartX();
      }
      this.mScroller.abortAnimation();
      setScrollingCacheEnabled(false);
    }
    else
    {
      i = getScrollX();
    }
    int j = getScrollY();
    int k = paramInt1 - i;
    paramInt2 -= j;
    if ((k == 0) && (paramInt2 == 0))
    {
      completeScroll(false);
      populate();
      setScrollState(0);
      return;
    }
    setScrollingCacheEnabled(true);
    setScrollState(2);
    paramInt1 = getClientWidth();
    int m = paramInt1 / 2;
    float f3 = Math.min(1.0F, Math.abs(k) * 1.0F / paramInt1);
    float f2 = m;
    float f1 = m;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt3 = Math.abs(paramInt3);
    if (paramInt3 > 0)
    {
      paramInt1 = Math.round(Math.abs((f2 + f1 * f3) / paramInt3) * 1000.0F) * 4;
    }
    else
    {
      f1 = paramInt1;
      f2 = this.mAdapter.getPageWidth(this.mCurItem);
      paramInt1 = (int)((1.0F + Math.abs(k) / (this.mPageMargin + f1 * f2)) * 100.0F);
    }
    paramInt1 = Math.min(paramInt1, 600);
    this.mIsScrollStarted = false;
    this.mScroller.startScroll(i, j, k, paramInt2, paramInt1);
    ViewCompat.postInvalidateOnAnimation(this);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.mMarginDrawable)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  @Inherited
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.TYPE})
  public static @interface DecorView {}
  
  static class ItemInfo
  {
    Object object;
    float offset;
    int position;
    boolean scrolling;
    float widthFactor;
  }
  
  public static class LayoutParams
    extends ViewGroup.LayoutParams
  {
    int childIndex;
    public int gravity;
    public boolean isDecor;
    boolean needsMeasure;
    int position;
    float widthFactor = 0.0F;
    
    public LayoutParams()
    {
      super(-1);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, ViewPager.LAYOUT_ATTRS);
      this.gravity = paramContext.getInteger(0, 48);
      paramContext.recycle();
    }
  }
  
  class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    MyAccessibilityDelegate() {}
    
    private boolean canScroll()
    {
      PagerAdapter localPagerAdapter = ViewPager.this.mAdapter;
      boolean bool = true;
      if ((localPagerAdapter == null) || (ViewPager.this.mAdapter.getCount() <= 1)) {
        bool = false;
      }
      return bool;
    }
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(ViewPager.class.getName());
      paramAccessibilityEvent.setScrollable(canScroll());
      if ((paramAccessibilityEvent.getEventType() == 4096) && (ViewPager.this.mAdapter != null))
      {
        paramAccessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
        paramAccessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
        paramAccessibilityEvent.setToIndex(ViewPager.this.mCurItem);
      }
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      paramAccessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
      paramAccessibilityNodeInfoCompat.setScrollable(canScroll());
      if (ViewPager.this.canScrollHorizontally(1)) {
        paramAccessibilityNodeInfoCompat.addAction(4096);
      }
      if (ViewPager.this.canScrollHorizontally(-1)) {
        paramAccessibilityNodeInfoCompat.addAction(8192);
      }
    }
    
    public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      if (super.performAccessibilityAction(paramView, paramInt, paramBundle)) {
        return true;
      }
      switch (paramInt)
      {
      default: 
        return false;
      case 8192: 
        if (ViewPager.this.canScrollHorizontally(-1))
        {
          paramView = ViewPager.this;
          paramView.setCurrentItem(paramView.mCurItem - 1);
          return true;
        }
        return false;
      }
      if (ViewPager.this.canScrollHorizontally(1))
      {
        paramView = ViewPager.this;
        paramView.setCurrentItem(paramView.mCurItem + 1);
        return true;
      }
      return false;
    }
  }
  
  public static abstract interface OnAdapterChangeListener
  {
    public abstract void onAdapterChanged(ViewPager paramViewPager, PagerAdapter paramPagerAdapter1, PagerAdapter paramPagerAdapter2);
  }
  
  public static abstract interface OnPageChangeListener
  {
    public abstract void onPageScrollStateChanged(int paramInt);
    
    public abstract void onPageScrolled(int paramInt1, float paramFloat, int paramInt2);
    
    public abstract void onPageSelected(int paramInt);
  }
  
  public static abstract interface PageTransformer
  {
    public abstract void transformPage(View paramView, float paramFloat);
  }
  
  private class PagerObserver
    extends DataSetObserver
  {
    PagerObserver() {}
    
    public void onChanged()
    {
      ViewPager.this.dataSetChanged();
    }
    
    public void onInvalidated()
    {
      ViewPager.this.dataSetChanged();
    }
  }
  
  public static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public ViewPager.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new ViewPager.SavedState(paramAnonymousParcel, null);
      }
      
      public ViewPager.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new ViewPager.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public ViewPager.SavedState[] newArray(int paramAnonymousInt)
      {
        return new ViewPager.SavedState[paramAnonymousInt];
      }
    };
    Parcelable adapterState;
    ClassLoader loader;
    int position;
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      ClassLoader localClassLoader = paramClassLoader;
      if (paramClassLoader == null) {
        localClassLoader = getClass().getClassLoader();
      }
      this.position = paramParcel.readInt();
      this.adapterState = paramParcel.readParcelable(localClassLoader);
      this.loader = localClassLoader;
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder().append("FragmentPager.SavedState{");
      String str = Integer.toHexString(System.identityHashCode(this));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str + " position=" + this.position + "}";
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.position);
      paramParcel.writeParcelable(this.adapterState, paramInt);
    }
  }
  
  public static class SimpleOnPageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    public void onPageScrollStateChanged(int paramInt) {}
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
    
    public void onPageSelected(int paramInt) {}
  }
  
  static class ViewPositionComparator
    implements Comparator<View>
  {
    public int compare(View paramView1, View paramView2)
    {
      paramView1 = (ViewPager.LayoutParams)paramView1.getLayoutParams();
      paramView2 = (ViewPager.LayoutParams)paramView2.getLayoutParams();
      if (paramView1.isDecor != paramView2.isDecor)
      {
        int i;
        if (paramView1.isDecor) {
          i = 1;
        } else {
          i = -1;
        }
        return i;
      }
      return paramView1.position - paramView2.position;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/viewpager/widget/ViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */