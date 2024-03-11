package androidx.core.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat
{
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener)
  {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT > 17) {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, paramHandler);
    } else {
      this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
    }
  }
  
  public boolean isLongpressEnabled()
  {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean)
  {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
  {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static abstract interface GestureDetectorCompatImpl
  {
    public abstract boolean isLongpressEnabled();
    
    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
    
    public abstract void setIsLongpressEnabled(boolean paramBoolean);
    
    public abstract void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplBase
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT = ;
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    MotionEvent mCurrentDownEvent;
    boolean mDeferConfirmSingleTap;
    GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mInLongPress;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    final GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    
    GestureDetectorCompatImplBase(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      if (paramHandler != null) {
        this.mHandler = new GestureHandler(paramHandler);
      } else {
        this.mHandler = new GestureHandler();
      }
      this.mListener = paramOnGestureListener;
      if ((paramOnGestureListener instanceof GestureDetector.OnDoubleTapListener)) {
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)paramOnGestureListener);
      }
      init(paramContext);
    }
    
    private void cancel()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void cancelTaps()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void init(Context paramContext)
    {
      if (paramContext != null)
      {
        if (this.mListener != null)
        {
          this.mIsLongpressEnabled = true;
          paramContext = ViewConfiguration.get(paramContext);
          int j = paramContext.getScaledTouchSlop();
          int i = paramContext.getScaledDoubleTapSlop();
          this.mMinimumFlingVelocity = paramContext.getScaledMinimumFlingVelocity();
          this.mMaximumFlingVelocity = paramContext.getScaledMaximumFlingVelocity();
          this.mTouchSlopSquare = (j * j);
          this.mDoubleTapSlopSquare = (i * i);
          return;
        }
        throw new IllegalArgumentException("OnGestureListener must not be null");
      }
      throw new IllegalArgumentException("Context must not be null");
    }
    
    private boolean isConsideredDoubleTap(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, MotionEvent paramMotionEvent3)
    {
      boolean bool2 = this.mAlwaysInBiggerTapRegion;
      boolean bool1 = false;
      if (!bool2) {
        return false;
      }
      if (paramMotionEvent3.getEventTime() - paramMotionEvent2.getEventTime() > DOUBLE_TAP_TIMEOUT) {
        return false;
      }
      int i = (int)paramMotionEvent1.getX() - (int)paramMotionEvent3.getX();
      int j = (int)paramMotionEvent1.getY() - (int)paramMotionEvent3.getY();
      if (i * i + j * j < this.mDoubleTapSlopSquare) {
        bool1 = true;
      }
      return bool1;
    }
    
    void dispatchLongPress()
    {
      this.mHandler.removeMessages(3);
      this.mDeferConfirmSingleTap = false;
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int m = paramMotionEvent.getAction();
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int i;
      if ((m & 0xFF) == 6) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if (i != 0) {
        j = paramMotionEvent.getActionIndex();
      } else {
        j = -1;
      }
      float f2 = 0.0F;
      float f1 = 0.0F;
      int n = paramMotionEvent.getPointerCount();
      for (int k = 0; k < n; k++) {
        if (j != k)
        {
          f2 += paramMotionEvent.getX(k);
          f1 += paramMotionEvent.getY(k);
        }
      }
      if (i != 0) {
        k = n - 1;
      } else {
        k = n;
      }
      f2 /= k;
      float f3 = f1 / k;
      boolean bool5 = false;
      boolean bool2 = false;
      k = 0;
      boolean bool3 = false;
      boolean bool4 = false;
      float f4;
      switch (m & 0xFF)
      {
      case 4: 
      default: 
        bool2 = bool3;
        break;
      case 6: 
        this.mLastFocusX = f2;
        this.mDownFocusX = f2;
        this.mLastFocusY = f3;
        this.mDownFocusY = f3;
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
        k = paramMotionEvent.getActionIndex();
        m = paramMotionEvent.getPointerId(k);
        f1 = this.mVelocityTracker.getXVelocity(m);
        f2 = this.mVelocityTracker.getYVelocity(m);
        for (m = 0; m < n; m++) {
          if (m != k)
          {
            int i1 = paramMotionEvent.getPointerId(m);
            if (this.mVelocityTracker.getXVelocity(i1) * f1 + this.mVelocityTracker.getYVelocity(i1) * f2 < 0.0F)
            {
              this.mVelocityTracker.clear();
              break;
            }
          }
        }
        bool2 = bool3;
        break;
      case 5: 
        this.mLastFocusX = f2;
        this.mDownFocusX = f2;
        this.mLastFocusY = f3;
        this.mDownFocusY = f3;
        cancelTaps();
        bool2 = bool3;
        break;
      case 3: 
        cancel();
        bool2 = bool3;
        break;
      case 2: 
        if (this.mInLongPress)
        {
          bool2 = bool3;
        }
        else
        {
          f1 = this.mLastFocusX - f2;
          f4 = this.mLastFocusY - f3;
          if (this.mIsDoubleTapping)
          {
            bool2 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
          }
          else if (this.mAlwaysInTapRegion)
          {
            i = (int)(f2 - this.mDownFocusX);
            j = (int)(f3 - this.mDownFocusY);
            i = i * i + j * j;
            bool3 = bool4;
            if (i > this.mTouchSlopSquare)
            {
              bool3 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f1, f4);
              this.mLastFocusX = f2;
              this.mLastFocusY = f3;
              this.mAlwaysInTapRegion = false;
              this.mHandler.removeMessages(3);
              this.mHandler.removeMessages(1);
              this.mHandler.removeMessages(2);
            }
            bool2 = bool3;
            if (i > this.mTouchSlopSquare)
            {
              this.mAlwaysInBiggerTapRegion = false;
              bool2 = bool3;
            }
          }
        }
        break;
      }
      for (;;)
      {
        break;
        if (Math.abs(f1) < 1.0F)
        {
          bool2 = bool5;
          if (Math.abs(f4) < 1.0F) {
            break;
          }
        }
        else
        {
          bool2 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f1, f4);
          this.mLastFocusX = f2;
          this.mLastFocusY = f3;
          break;
          this.mStillDown = false;
          MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
          Object localObject;
          if (this.mIsDoubleTapping)
          {
            bool2 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
          }
          else if (this.mInLongPress)
          {
            this.mHandler.removeMessages(3);
            this.mInLongPress = false;
          }
          else if (this.mAlwaysInTapRegion)
          {
            bool3 = this.mListener.onSingleTapUp(paramMotionEvent);
            bool2 = bool3;
            if (this.mDeferConfirmSingleTap)
            {
              localObject = this.mDoubleTapListener;
              bool2 = bool3;
              if (localObject != null)
              {
                ((GestureDetector.OnDoubleTapListener)localObject).onSingleTapConfirmed(paramMotionEvent);
                bool2 = bool3;
              }
            }
          }
          else
          {
            localObject = this.mVelocityTracker;
            i = paramMotionEvent.getPointerId(0);
            ((VelocityTracker)localObject).computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            f1 = ((VelocityTracker)localObject).getYVelocity(i);
            f2 = ((VelocityTracker)localObject).getXVelocity(i);
            if ((Math.abs(f1) > this.mMinimumFlingVelocity) || (Math.abs(f2) > this.mMinimumFlingVelocity)) {
              bool2 = this.mListener.onFling(this.mCurrentDownEvent, paramMotionEvent, f2, f1);
            }
          }
          paramMotionEvent = this.mPreviousUpEvent;
          if (paramMotionEvent != null) {
            paramMotionEvent.recycle();
          }
          this.mPreviousUpEvent = localMotionEvent;
          paramMotionEvent = this.mVelocityTracker;
          if (paramMotionEvent != null)
          {
            paramMotionEvent.recycle();
            this.mVelocityTracker = null;
          }
          this.mIsDoubleTapping = false;
          this.mDeferConfirmSingleTap = false;
          this.mHandler.removeMessages(1);
          this.mHandler.removeMessages(2);
          break;
          i = k;
          boolean bool1;
          if (this.mDoubleTapListener != null)
          {
            bool2 = this.mHandler.hasMessages(3);
            if (bool2) {
              this.mHandler.removeMessages(3);
            }
            localObject = this.mCurrentDownEvent;
            if (localObject != null)
            {
              localMotionEvent = this.mPreviousUpEvent;
              if ((localMotionEvent != null) && (bool2) && (isConsideredDoubleTap((MotionEvent)localObject, localMotionEvent, paramMotionEvent)))
              {
                this.mIsDoubleTapping = true;
                bool1 = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
                break label1056;
              }
            }
            this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
            bool1 = k;
          }
          label1056:
          this.mLastFocusX = f2;
          this.mDownFocusX = f2;
          this.mLastFocusY = f3;
          this.mDownFocusY = f3;
          localMotionEvent = this.mCurrentDownEvent;
          if (localMotionEvent != null) {
            localMotionEvent.recycle();
          }
          this.mCurrentDownEvent = MotionEvent.obtain(paramMotionEvent);
          this.mAlwaysInTapRegion = true;
          this.mAlwaysInBiggerTapRegion = true;
          this.mStillDown = true;
          this.mInLongPress = false;
          this.mDeferConfirmSingleTap = false;
          if (this.mIsLongpressEnabled)
          {
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + ViewConfiguration.getLongPressTimeout());
          }
          this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
          bool2 = bool1 | this.mListener.onDown(paramMotionEvent);
        }
      }
      return bool2;
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mIsLongpressEnabled = paramBoolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDoubleTapListener = paramOnDoubleTapListener;
    }
    
    private class GestureHandler
      extends Handler
    {
      GestureHandler() {}
      
      GestureHandler(Handler paramHandler)
      {
        super();
      }
      
      public void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default: 
          throw new RuntimeException("Unknown message " + paramMessage);
        case 3: 
          if (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null) {
            if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown) {
              GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
            } else {
              GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
            }
          }
          break;
        case 2: 
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          break;
        case 1: 
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        }
      }
    }
  }
  
  static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private final GestureDetector mDetector;
    
    GestureDetectorCompatImplJellybeanMr2(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mDetector = new GestureDetector(paramContext, paramOnGestureListener, paramHandler);
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return this.mDetector.onTouchEvent(paramMotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mDetector.setIsLongpressEnabled(paramBoolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/GestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */