package androidx.customview.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;

public class ViewDragHelper
{
  private static final int BASE_SETTLE_DURATION = 256;
  public static final int DIRECTION_ALL = 3;
  public static final int DIRECTION_HORIZONTAL = 1;
  public static final int DIRECTION_VERTICAL = 2;
  public static final int EDGE_ALL = 15;
  public static final int EDGE_BOTTOM = 8;
  public static final int EDGE_LEFT = 1;
  public static final int EDGE_RIGHT = 2;
  private static final int EDGE_SIZE = 20;
  public static final int EDGE_TOP = 4;
  public static final int INVALID_POINTER = -1;
  private static final int MAX_SETTLE_DURATION = 600;
  public static final int STATE_DRAGGING = 1;
  public static final int STATE_IDLE = 0;
  public static final int STATE_SETTLING = 2;
  private static final String TAG = "ViewDragHelper";
  private static final Interpolator sInterpolator = new Interpolator()
  {
    public float getInterpolation(float paramAnonymousFloat)
    {
      paramAnonymousFloat -= 1.0F;
      return paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat * paramAnonymousFloat + 1.0F;
    }
  };
  private int mActivePointerId = -1;
  private final Callback mCallback;
  private View mCapturedView;
  private final int mDefaultEdgeSize;
  private int mDragState;
  private int[] mEdgeDragsInProgress;
  private int[] mEdgeDragsLocked;
  private int mEdgeSize;
  private int[] mInitialEdgesTouched;
  private float[] mInitialMotionX;
  private float[] mInitialMotionY;
  private float[] mLastMotionX;
  private float[] mLastMotionY;
  private float mMaxVelocity;
  private float mMinVelocity;
  private final ViewGroup mParentView;
  private int mPointersDown;
  private boolean mReleaseInProgress;
  private OverScroller mScroller;
  private final Runnable mSetIdleRunnable = new Runnable()
  {
    public void run()
    {
      ViewDragHelper.this.setDragState(0);
    }
  };
  private int mTouchSlop;
  private int mTrackingEdges;
  private VelocityTracker mVelocityTracker;
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback)
  {
    if (paramViewGroup != null)
    {
      if (paramCallback != null)
      {
        this.mParentView = paramViewGroup;
        this.mCallback = paramCallback;
        paramViewGroup = ViewConfiguration.get(paramContext);
        int i = (int)(20.0F * paramContext.getResources().getDisplayMetrics().density + 0.5F);
        this.mDefaultEdgeSize = i;
        this.mEdgeSize = i;
        this.mTouchSlop = paramViewGroup.getScaledTouchSlop();
        this.mMaxVelocity = paramViewGroup.getScaledMaximumFlingVelocity();
        this.mMinVelocity = paramViewGroup.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(paramContext, sInterpolator);
        return;
      }
      throw new IllegalArgumentException("Callback may not be null");
    }
    throw new IllegalArgumentException("Parent view may not be null");
  }
  
  private boolean checkNewEdgeDrag(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    paramFloat1 = Math.abs(paramFloat1);
    paramFloat2 = Math.abs(paramFloat2);
    int i = this.mInitialEdgesTouched[paramInt1];
    boolean bool2 = false;
    if (((i & paramInt2) == paramInt2) && ((this.mTrackingEdges & paramInt2) != 0) && ((this.mEdgeDragsLocked[paramInt1] & paramInt2) != paramInt2) && ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) != paramInt2))
    {
      i = this.mTouchSlop;
      if ((paramFloat1 > i) || (paramFloat2 > i))
      {
        if ((paramFloat1 < 0.5F * paramFloat2) && (this.mCallback.onEdgeLock(paramInt2)))
        {
          int[] arrayOfInt = this.mEdgeDragsLocked;
          arrayOfInt[paramInt1] |= paramInt2;
          return false;
        }
        boolean bool1 = bool2;
        if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) == 0)
        {
          bool1 = bool2;
          if (paramFloat1 > this.mTouchSlop) {
            bool1 = true;
          }
        }
        return bool1;
      }
    }
    return false;
  }
  
  private boolean checkTouchSlop(View paramView, float paramFloat1, float paramFloat2)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramView == null) {
      return false;
    }
    int i;
    if (this.mCallback.getViewHorizontalDragRange(paramView) > 0) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (this.mCallback.getViewVerticalDragRange(paramView) > 0) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i != 0) && (j != 0))
    {
      i = this.mTouchSlop;
      bool1 = bool3;
      if (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 > i * i) {
        bool1 = true;
      }
      return bool1;
    }
    if (i != 0)
    {
      if (Math.abs(paramFloat1) > this.mTouchSlop) {
        bool1 = true;
      }
      return bool1;
    }
    if (j != 0)
    {
      bool1 = bool2;
      if (Math.abs(paramFloat2) > this.mTouchSlop) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  private float clampMag(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f = Math.abs(paramFloat1);
    if (f < paramFloat2) {
      return 0.0F;
    }
    if (f > paramFloat3)
    {
      if (paramFloat1 <= 0.0F) {
        paramFloat3 = -paramFloat3;
      }
      return paramFloat3;
    }
    return paramFloat1;
  }
  
  private int clampMag(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.abs(paramInt1);
    if (i < paramInt2) {
      return 0;
    }
    if (i > paramInt3)
    {
      if (paramInt1 <= 0) {
        paramInt3 = -paramInt3;
      }
      return paramInt3;
    }
    return paramInt1;
  }
  
  private void clearMotionHistory()
  {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null) {
      return;
    }
    Arrays.fill(arrayOfFloat, 0.0F);
    Arrays.fill(this.mInitialMotionY, 0.0F);
    Arrays.fill(this.mLastMotionX, 0.0F);
    Arrays.fill(this.mLastMotionY, 0.0F);
    Arrays.fill(this.mInitialEdgesTouched, 0);
    Arrays.fill(this.mEdgeDragsInProgress, 0);
    Arrays.fill(this.mEdgeDragsLocked, 0);
    this.mPointersDown = 0;
  }
  
  private void clearMotionHistory(int paramInt)
  {
    if ((this.mInitialMotionX != null) && (isPointerDown(paramInt)))
    {
      this.mInitialMotionX[paramInt] = 0.0F;
      this.mInitialMotionY[paramInt] = 0.0F;
      this.mLastMotionX[paramInt] = 0.0F;
      this.mLastMotionY[paramInt] = 0.0F;
      this.mInitialEdgesTouched[paramInt] = 0;
      this.mEdgeDragsInProgress[paramInt] = 0;
      this.mEdgeDragsLocked[paramInt] = 0;
      this.mPointersDown &= (1 << paramInt ^ 0xFFFFFFFF);
      return;
    }
  }
  
  private int computeAxisDuration(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 0) {
      return 0;
    }
    int i = this.mParentView.getWidth();
    int j = i / 2;
    float f3 = Math.min(1.0F, Math.abs(paramInt1) / i);
    float f2 = j;
    float f1 = j;
    f3 = distanceInfluenceForSnapDuration(f3);
    paramInt2 = Math.abs(paramInt2);
    if (paramInt2 > 0) {
      paramInt1 = Math.round(Math.abs((f2 + f1 * f3) / paramInt2) * 1000.0F) * 4;
    } else {
      paramInt1 = (int)((1.0F + Math.abs(paramInt1) / paramInt3) * 256.0F);
    }
    return Math.min(paramInt1, 600);
  }
  
  private int computeSettleDuration(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt3 = clampMag(paramInt3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    paramInt4 = clampMag(paramInt4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int m = Math.abs(paramInt1);
    int k = Math.abs(paramInt2);
    int i = Math.abs(paramInt3);
    int j = Math.abs(paramInt4);
    int n = i + j;
    int i1 = m + k;
    float f1;
    if (paramInt3 != 0) {
      f1 = i / n;
    } else {
      f1 = m / i1;
    }
    float f2;
    if (paramInt4 != 0) {
      f2 = j / n;
    } else {
      f2 = k / i1;
    }
    paramInt1 = computeAxisDuration(paramInt1, paramInt3, this.mCallback.getViewHorizontalDragRange(paramView));
    paramInt2 = computeAxisDuration(paramInt2, paramInt4, this.mCallback.getViewVerticalDragRange(paramView));
    return (int)(paramInt1 * f1 + paramInt2 * f2);
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback)
  {
    paramViewGroup = create(paramViewGroup, paramCallback);
    paramViewGroup.mTouchSlop = ((int)(paramViewGroup.mTouchSlop * (1.0F / paramFloat)));
    return paramViewGroup;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback)
  {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  private void dispatchViewReleased(float paramFloat1, float paramFloat2)
  {
    this.mReleaseInProgress = true;
    this.mCallback.onViewReleased(this.mCapturedView, paramFloat1, paramFloat2);
    this.mReleaseInProgress = false;
    if (this.mDragState == 1) {
      setDragState(0);
    }
  }
  
  private float distanceInfluenceForSnapDuration(float paramFloat)
  {
    return (float)Math.sin((paramFloat - 0.5F) * 0.47123894F);
  }
  
  private void dragTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = paramInt1;
    int i = paramInt2;
    int k = this.mCapturedView.getLeft();
    int m = this.mCapturedView.getTop();
    if (paramInt3 != 0)
    {
      paramInt1 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, paramInt1, paramInt3);
      ViewCompat.offsetLeftAndRight(this.mCapturedView, paramInt1 - k);
    }
    else
    {
      paramInt1 = j;
    }
    if (paramInt4 != 0)
    {
      i = this.mCallback.clampViewPositionVertical(this.mCapturedView, paramInt2, paramInt4);
      ViewCompat.offsetTopAndBottom(this.mCapturedView, i - m);
    }
    if ((paramInt3 != 0) || (paramInt4 != 0)) {
      this.mCallback.onViewPositionChanged(this.mCapturedView, paramInt1, i, paramInt1 - k, i - m);
    }
  }
  
  private void ensureMotionHistorySizeForId(int paramInt)
  {
    Object localObject = this.mInitialMotionX;
    if ((localObject == null) || (localObject.length <= paramInt))
    {
      float[] arrayOfFloat2 = new float[paramInt + 1];
      float[] arrayOfFloat4 = new float[paramInt + 1];
      float[] arrayOfFloat3 = new float[paramInt + 1];
      float[] arrayOfFloat1 = new float[paramInt + 1];
      int[] arrayOfInt2 = new int[paramInt + 1];
      int[] arrayOfInt3 = new int[paramInt + 1];
      int[] arrayOfInt1 = new int[paramInt + 1];
      if (localObject != null)
      {
        System.arraycopy(localObject, 0, arrayOfFloat2, 0, localObject.length);
        localObject = this.mInitialMotionY;
        System.arraycopy(localObject, 0, arrayOfFloat4, 0, localObject.length);
        localObject = this.mLastMotionX;
        System.arraycopy(localObject, 0, arrayOfFloat3, 0, localObject.length);
        localObject = this.mLastMotionY;
        System.arraycopy(localObject, 0, arrayOfFloat1, 0, localObject.length);
        localObject = this.mInitialEdgesTouched;
        System.arraycopy(localObject, 0, arrayOfInt2, 0, localObject.length);
        localObject = this.mEdgeDragsInProgress;
        System.arraycopy(localObject, 0, arrayOfInt3, 0, localObject.length);
        localObject = this.mEdgeDragsLocked;
        System.arraycopy(localObject, 0, arrayOfInt1, 0, localObject.length);
      }
      this.mInitialMotionX = arrayOfFloat2;
      this.mInitialMotionY = arrayOfFloat4;
      this.mLastMotionX = arrayOfFloat3;
      this.mLastMotionY = arrayOfFloat1;
      this.mInitialEdgesTouched = arrayOfInt2;
      this.mEdgeDragsInProgress = arrayOfInt3;
      this.mEdgeDragsLocked = arrayOfInt1;
    }
  }
  
  private boolean forceSettleCapturedViewAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = this.mCapturedView.getLeft();
    int i = this.mCapturedView.getTop();
    paramInt1 -= j;
    paramInt2 -= i;
    if ((paramInt1 == 0) && (paramInt2 == 0))
    {
      this.mScroller.abortAnimation();
      setDragState(0);
      return false;
    }
    paramInt3 = computeSettleDuration(this.mCapturedView, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mScroller.startScroll(j, i, paramInt1, paramInt2, paramInt3);
    setDragState(2);
    return true;
  }
  
  private int getEdgesTouched(int paramInt1, int paramInt2)
  {
    int j = 0;
    if (paramInt1 < this.mParentView.getLeft() + this.mEdgeSize) {
      j = 0x0 | 0x1;
    }
    int i = j;
    if (paramInt2 < this.mParentView.getTop() + this.mEdgeSize) {
      i = j | 0x4;
    }
    j = i;
    if (paramInt1 > this.mParentView.getRight() - this.mEdgeSize) {
      j = i | 0x2;
    }
    paramInt1 = j;
    if (paramInt2 > this.mParentView.getBottom() - this.mEdgeSize) {
      paramInt1 = j | 0x8;
    }
    return paramInt1;
  }
  
  private boolean isValidPointerForActionMove(int paramInt)
  {
    if (!isPointerDown(paramInt))
    {
      Log.e("ViewDragHelper", "Ignoring pointerId=" + paramInt + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
      return false;
    }
    return true;
  }
  
  private void releaseViewForPointerUp()
  {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
    dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
  }
  
  private void reportNewEdgeDrags(float paramFloat1, float paramFloat2, int paramInt)
  {
    int i = 0;
    if (checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 1)) {
      i = 0x0 | 0x1;
    }
    int j = i;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 4)) {
      j = i | 0x4;
    }
    i = j;
    if (checkNewEdgeDrag(paramFloat1, paramFloat2, paramInt, 2)) {
      i = j | 0x2;
    }
    j = i;
    if (checkNewEdgeDrag(paramFloat2, paramFloat1, paramInt, 8)) {
      j = i | 0x8;
    }
    if (j != 0)
    {
      int[] arrayOfInt = this.mEdgeDragsInProgress;
      arrayOfInt[paramInt] |= j;
      this.mCallback.onEdgeDragStarted(j, paramInt);
    }
  }
  
  private void saveInitialMotion(float paramFloat1, float paramFloat2, int paramInt)
  {
    ensureMotionHistorySizeForId(paramInt);
    float[] arrayOfFloat = this.mInitialMotionX;
    this.mLastMotionX[paramInt] = paramFloat1;
    arrayOfFloat[paramInt] = paramFloat1;
    arrayOfFloat = this.mInitialMotionY;
    this.mLastMotionY[paramInt] = paramFloat2;
    arrayOfFloat[paramInt] = paramFloat2;
    this.mInitialEdgesTouched[paramInt] = getEdgesTouched((int)paramFloat1, (int)paramFloat2);
    this.mPointersDown |= 1 << paramInt;
  }
  
  private void saveLastMotion(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getPointerCount();
    for (int i = 0; i < j; i++)
    {
      int k = paramMotionEvent.getPointerId(i);
      if (isValidPointerForActionMove(k))
      {
        float f1 = paramMotionEvent.getX(i);
        float f2 = paramMotionEvent.getY(i);
        this.mLastMotionX[k] = f1;
        this.mLastMotionY[k] = f2;
      }
    }
  }
  
  public void abort()
  {
    cancel();
    if (this.mDragState == 2)
    {
      int i = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      this.mScroller.abortAnimation();
      int k = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      this.mCallback.onViewPositionChanged(this.mCapturedView, k, j, k - i, j - m);
    }
    setDragState(0);
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool2 = paramView instanceof ViewGroup;
    boolean bool1 = true;
    if (bool2)
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int k = paramView.getScrollX();
      int j = paramView.getScrollY();
      for (int i = localViewGroup.getChildCount() - 1; i >= 0; i--)
      {
        View localView = localViewGroup.getChildAt(i);
        if ((paramInt3 + k >= localView.getLeft()) && (paramInt3 + k < localView.getRight()) && (paramInt4 + j >= localView.getTop()) && (paramInt4 + j < localView.getBottom()) && (canScroll(localView, true, paramInt1, paramInt2, paramInt3 + k - localView.getLeft(), paramInt4 + j - localView.getTop()))) {
          return true;
        }
      }
    }
    if (paramBoolean) {
      if (!paramView.canScrollHorizontally(-paramInt1))
      {
        if (paramView.canScrollVertically(-paramInt2)) {
          return bool1;
        }
      }
      else {
        return bool1;
      }
    }
    paramBoolean = false;
    return paramBoolean;
  }
  
  public void cancel()
  {
    this.mActivePointerId = -1;
    clearMotionHistory();
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  public void captureChildView(View paramView, int paramInt)
  {
    if (paramView.getParent() == this.mParentView)
    {
      this.mCapturedView = paramView;
      this.mActivePointerId = paramInt;
      this.mCallback.onViewCaptured(paramView, paramInt);
      setDragState(1);
      return;
    }
    throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
  }
  
  public boolean checkTouchSlop(int paramInt)
  {
    int j = this.mInitialMotionX.length;
    for (int i = 0; i < j; i++) {
      if (checkTouchSlop(paramInt, i)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean checkTouchSlop(int paramInt1, int paramInt2)
  {
    boolean bool4 = isPointerDown(paramInt2);
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (!bool4) {
      return false;
    }
    int i;
    if ((paramInt1 & 0x1) == 1) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramInt1 & 0x2) == 2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    float f1 = this.mLastMotionX[paramInt2] - this.mInitialMotionX[paramInt2];
    float f2 = this.mLastMotionY[paramInt2] - this.mInitialMotionY[paramInt2];
    if ((i != 0) && (paramInt1 != 0))
    {
      paramInt1 = this.mTouchSlop;
      bool1 = bool3;
      if (f1 * f1 + f2 * f2 > paramInt1 * paramInt1) {
        bool1 = true;
      }
      return bool1;
    }
    if (i != 0)
    {
      if (Math.abs(f1) > this.mTouchSlop) {
        bool1 = true;
      }
      return bool1;
    }
    if (paramInt1 != 0)
    {
      bool1 = bool2;
      if (Math.abs(f2) > this.mTouchSlop) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  public boolean continueSettling(boolean paramBoolean)
  {
    int i = this.mDragState;
    boolean bool2 = false;
    if (i == 2)
    {
      boolean bool3 = this.mScroller.computeScrollOffset();
      i = this.mScroller.getCurrX();
      int k = this.mScroller.getCurrY();
      int m = i - this.mCapturedView.getLeft();
      int j = k - this.mCapturedView.getTop();
      if (m != 0) {
        ViewCompat.offsetLeftAndRight(this.mCapturedView, m);
      }
      if (j != 0) {
        ViewCompat.offsetTopAndBottom(this.mCapturedView, j);
      }
      if ((m != 0) || (j != 0)) {
        this.mCallback.onViewPositionChanged(this.mCapturedView, i, k, m, j);
      }
      boolean bool1 = bool3;
      if (bool3)
      {
        bool1 = bool3;
        if (i == this.mScroller.getFinalX())
        {
          bool1 = bool3;
          if (k == this.mScroller.getFinalY())
          {
            this.mScroller.abortAnimation();
            bool1 = false;
          }
        }
      }
      if (!bool1) {
        if (paramBoolean) {
          this.mParentView.post(this.mSetIdleRunnable);
        } else {
          setDragState(0);
        }
      }
    }
    paramBoolean = bool2;
    if (this.mDragState == 2) {
      paramBoolean = true;
    }
    return paramBoolean;
  }
  
  public View findTopChildUnder(int paramInt1, int paramInt2)
  {
    for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--)
    {
      View localView = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
      if ((paramInt1 >= localView.getLeft()) && (paramInt1 < localView.getRight()) && (paramInt2 >= localView.getTop()) && (paramInt2 < localView.getBottom())) {
        return localView;
      }
    }
    return null;
  }
  
  public void flingCapturedView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mReleaseInProgress)
    {
      this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), paramInt1, paramInt3, paramInt2, paramInt4);
      setDragState(2);
      return;
    }
    throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
  }
  
  public int getActivePointerId()
  {
    return this.mActivePointerId;
  }
  
  public View getCapturedView()
  {
    return this.mCapturedView;
  }
  
  public int getDefaultEdgeSize()
  {
    return this.mDefaultEdgeSize;
  }
  
  public int getEdgeSize()
  {
    return this.mEdgeSize;
  }
  
  public float getMinVelocity()
  {
    return this.mMinVelocity;
  }
  
  public int getTouchSlop()
  {
    return this.mTouchSlop;
  }
  
  public int getViewDragState()
  {
    return this.mDragState;
  }
  
  public boolean isCapturedViewUnder(int paramInt1, int paramInt2)
  {
    return isViewUnder(this.mCapturedView, paramInt1, paramInt2);
  }
  
  public boolean isEdgeTouched(int paramInt)
  {
    int j = this.mInitialEdgesTouched.length;
    for (int i = 0; i < j; i++) {
      if (isEdgeTouched(paramInt, i)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isEdgeTouched(int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((isPointerDown(paramInt2)) && ((this.mInitialEdgesTouched[paramInt2] & paramInt1) != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isPointerDown(int paramInt)
  {
    int i = this.mPointersDown;
    boolean bool = true;
    if ((i & 1 << paramInt) == 0) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isViewUnder(View paramView, int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramView == null) {
      return false;
    }
    if ((paramInt1 >= paramView.getLeft()) && (paramInt1 < paramView.getRight()) && (paramInt2 >= paramView.getTop()) && (paramInt2 < paramView.getBottom())) {
      bool = true;
    }
    return bool;
  }
  
  public void processTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getActionIndex();
    if (i == 0) {
      cancel();
    }
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int k;
    float f1;
    float f2;
    Object localObject;
    switch (i)
    {
    case 4: 
    default: 
      break;
    case 6: 
      int m = paramMotionEvent.getPointerId(j);
      if ((this.mDragState == 1) && (m == this.mActivePointerId))
      {
        k = -1;
        int n = paramMotionEvent.getPointerCount();
        for (i = 0;; i++)
        {
          j = k;
          if (i >= n) {
            break;
          }
          j = paramMotionEvent.getPointerId(i);
          if (j != this.mActivePointerId)
          {
            f1 = paramMotionEvent.getX(i);
            f2 = paramMotionEvent.getY(i);
            View localView = findTopChildUnder((int)f1, (int)f2);
            localObject = this.mCapturedView;
            if ((localView == localObject) && (tryCaptureViewForDrag((View)localObject, j)))
            {
              j = this.mActivePointerId;
              break;
            }
          }
        }
        if (j == -1) {
          releaseViewForPointerUp();
        }
      }
      clearMotionHistory(m);
      break;
    case 5: 
      i = paramMotionEvent.getPointerId(j);
      f1 = paramMotionEvent.getX(j);
      f2 = paramMotionEvent.getY(j);
      saveInitialMotion(f1, f2, i);
      if (this.mDragState == 0)
      {
        tryCaptureViewForDrag(findTopChildUnder((int)f1, (int)f2), i);
        k = this.mInitialEdgesTouched[i];
        j = this.mTrackingEdges;
        if ((k & j) != 0) {
          this.mCallback.onEdgeTouched(j & k, i);
        }
      }
      else if (isCapturedViewUnder((int)f1, (int)f2))
      {
        tryCaptureViewForDrag(this.mCapturedView, i);
        break;
      }
      break;
    case 3: 
      if (this.mDragState == 1) {
        dispatchViewReleased(0.0F, 0.0F);
      }
      cancel();
      break;
    case 2: 
      if (this.mDragState == 1)
      {
        if (isValidPointerForActionMove(this.mActivePointerId))
        {
          i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
          f1 = paramMotionEvent.getX(i);
          f2 = paramMotionEvent.getY(i);
          localObject = this.mLastMotionX;
          j = this.mActivePointerId;
          i = (int)(f1 - localObject[j]);
          j = (int)(f2 - this.mLastMotionY[j]);
          dragTo(this.mCapturedView.getLeft() + i, this.mCapturedView.getTop() + j, i, j);
          saveLastMotion(paramMotionEvent);
        }
      }
      else
      {
        j = paramMotionEvent.getPointerCount();
        for (i = 0; i < j; i++)
        {
          k = paramMotionEvent.getPointerId(i);
          if (isValidPointerForActionMove(k))
          {
            f1 = paramMotionEvent.getX(i);
            float f3 = paramMotionEvent.getY(i);
            f2 = f1 - this.mInitialMotionX[k];
            float f4 = f3 - this.mInitialMotionY[k];
            reportNewEdgeDrags(f2, f4, k);
            if (this.mDragState == 1) {
              break;
            }
            localObject = findTopChildUnder((int)f1, (int)f3);
            if ((checkTouchSlop((View)localObject, f2, f4)) && (tryCaptureViewForDrag((View)localObject, k))) {
              break;
            }
          }
        }
        saveLastMotion(paramMotionEvent);
      }
      break;
    case 1: 
      if (this.mDragState == 1) {
        releaseViewForPointerUp();
      }
      cancel();
      break;
    case 0: 
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      j = paramMotionEvent.getPointerId(0);
      paramMotionEvent = findTopChildUnder((int)f1, (int)f2);
      saveInitialMotion(f1, f2, j);
      tryCaptureViewForDrag(paramMotionEvent, j);
      i = this.mInitialEdgesTouched[j];
      k = this.mTrackingEdges;
      if ((i & k) != 0) {
        this.mCallback.onEdgeTouched(k & i, j);
      }
      break;
    }
  }
  
  void setDragState(int paramInt)
  {
    this.mParentView.removeCallbacks(this.mSetIdleRunnable);
    if (this.mDragState != paramInt)
    {
      this.mDragState = paramInt;
      this.mCallback.onViewDragStateChanged(paramInt);
      if (this.mDragState == 0) {
        this.mCapturedView = null;
      }
    }
  }
  
  public void setEdgeSize(int paramInt)
  {
    this.mEdgeSize = paramInt;
  }
  
  public void setEdgeTrackingEnabled(int paramInt)
  {
    this.mTrackingEdges = paramInt;
  }
  
  public void setMinVelocity(float paramFloat)
  {
    this.mMinVelocity = paramFloat;
  }
  
  public boolean settleCapturedViewAt(int paramInt1, int paramInt2)
  {
    if (this.mReleaseInProgress) {
      return forceSettleCapturedViewAt(paramInt1, paramInt2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId));
    }
    throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
  }
  
  public boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int m = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getActionIndex();
    if (m == 0) {
      cancel();
    }
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i;
    float f2;
    float f1;
    int k;
    switch (m)
    {
    case 4: 
    default: 
      break;
    case 6: 
      clearMotionHistory(paramMotionEvent.getPointerId(j));
      break;
    case 5: 
      i = paramMotionEvent.getPointerId(j);
      f2 = paramMotionEvent.getX(j);
      f1 = paramMotionEvent.getY(j);
      saveInitialMotion(f2, f1, i);
      j = this.mDragState;
      if (j == 0)
      {
        j = this.mInitialEdgesTouched[i];
        k = this.mTrackingEdges;
        if ((j & k) != 0) {
          this.mCallback.onEdgeTouched(k & j, i);
        }
      }
      else if (j == 2)
      {
        paramMotionEvent = findTopChildUnder((int)f2, (int)f1);
        if (paramMotionEvent == this.mCapturedView) {
          tryCaptureViewForDrag(paramMotionEvent, i);
        }
        break;
      }
      break;
    case 2: 
      if (this.mInitialMotionX != null)
      {
        if (this.mInitialMotionY == null) {
          break;
        }
        i = paramMotionEvent.getPointerCount();
        for (k = 0; k < i; k++)
        {
          int i1 = paramMotionEvent.getPointerId(k);
          if (isValidPointerForActionMove(i1))
          {
            float f4 = paramMotionEvent.getX(k);
            f1 = paramMotionEvent.getY(k);
            f2 = f4 - this.mInitialMotionX[i1];
            float f3 = f1 - this.mInitialMotionY[i1];
            View localView = findTopChildUnder((int)f4, (int)f1);
            int n;
            if ((localView != null) && (checkTouchSlop(localView, f2, f3))) {
              n = 1;
            } else {
              n = 0;
            }
            if (n != 0)
            {
              int i2 = localView.getLeft();
              int i3 = (int)f2;
              int i4 = this.mCallback.clampViewPositionHorizontal(localView, i3 + i2, (int)f2);
              i3 = localView.getTop();
              int i5 = (int)f3;
              int i6 = this.mCallback.clampViewPositionVertical(localView, i5 + i3, (int)f3);
              i5 = this.mCallback.getViewHorizontalDragRange(localView);
              int i7 = this.mCallback.getViewVerticalDragRange(localView);
              if (((i5 == 0) || ((i5 > 0) && (i4 == i2))) && ((i7 == 0) || ((i7 > 0) && (i6 == i3)))) {
                break;
              }
            }
            else
            {
              reportNewEdgeDrags(f2, f3, i1);
              if ((this.mDragState == 1) || ((n != 0) && (tryCaptureViewForDrag(localView, i1)))) {
                break;
              }
            }
          }
        }
        saveLastMotion(paramMotionEvent);
      }
      break;
    case 1: 
    case 3: 
      cancel();
      break;
    case 0: 
      f2 = paramMotionEvent.getX();
      f1 = paramMotionEvent.getY();
      k = paramMotionEvent.getPointerId(0);
      saveInitialMotion(f2, f1, k);
      paramMotionEvent = findTopChildUnder((int)f2, (int)f1);
      if ((paramMotionEvent == this.mCapturedView) && (this.mDragState == 2)) {
        tryCaptureViewForDrag(paramMotionEvent, k);
      }
      i = this.mInitialEdgesTouched[k];
      j = this.mTrackingEdges;
      if ((i & j) != 0) {
        this.mCallback.onEdgeTouched(j & i, k);
      }
      break;
    }
    boolean bool = false;
    if (this.mDragState == 1) {
      bool = true;
    }
    return bool;
  }
  
  public boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2)
  {
    this.mCapturedView = paramView;
    this.mActivePointerId = -1;
    boolean bool = forceSettleCapturedViewAt(paramInt1, paramInt2, 0, 0);
    if ((!bool) && (this.mDragState == 0) && (this.mCapturedView != null)) {
      this.mCapturedView = null;
    }
    return bool;
  }
  
  boolean tryCaptureViewForDrag(View paramView, int paramInt)
  {
    if ((paramView == this.mCapturedView) && (this.mActivePointerId == paramInt)) {
      return true;
    }
    if ((paramView != null) && (this.mCallback.tryCaptureView(paramView, paramInt)))
    {
      this.mActivePointerId = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    }
    return false;
  }
  
  public static abstract class Callback
  {
    public int clampViewPositionHorizontal(View paramView, int paramInt1, int paramInt2)
    {
      return 0;
    }
    
    public int clampViewPositionVertical(View paramView, int paramInt1, int paramInt2)
    {
      return 0;
    }
    
    public int getOrderedChildIndex(int paramInt)
    {
      return paramInt;
    }
    
    public int getViewHorizontalDragRange(View paramView)
    {
      return 0;
    }
    
    public int getViewVerticalDragRange(View paramView)
    {
      return 0;
    }
    
    public void onEdgeDragStarted(int paramInt1, int paramInt2) {}
    
    public boolean onEdgeLock(int paramInt)
    {
      return false;
    }
    
    public void onEdgeTouched(int paramInt1, int paramInt2) {}
    
    public void onViewCaptured(View paramView, int paramInt) {}
    
    public void onViewDragStateChanged(int paramInt) {}
    
    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2) {}
    
    public abstract boolean tryCaptureView(View paramView, int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/customview/widget/ViewDragHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */