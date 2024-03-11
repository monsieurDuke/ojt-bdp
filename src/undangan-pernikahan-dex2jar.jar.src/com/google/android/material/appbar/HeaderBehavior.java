package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

abstract class HeaderBehavior<V extends View>
  extends ViewOffsetBehavior<V>
{
  private static final int INVALID_POINTER = -1;
  private int activePointerId = -1;
  private Runnable flingRunnable;
  private boolean isBeingDragged;
  private int lastMotionY;
  OverScroller scroller;
  private int touchSlop = -1;
  private VelocityTracker velocityTracker;
  
  public HeaderBehavior() {}
  
  public HeaderBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void ensureVelocityTracker()
  {
    if (this.velocityTracker == null) {
      this.velocityTracker = VelocityTracker.obtain();
    }
  }
  
  boolean canDragView(V paramV)
  {
    return false;
  }
  
  final boolean fling(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, float paramFloat)
  {
    Runnable localRunnable = this.flingRunnable;
    if (localRunnable != null)
    {
      paramV.removeCallbacks(localRunnable);
      this.flingRunnable = null;
    }
    if (this.scroller == null) {
      this.scroller = new OverScroller(paramV.getContext());
    }
    this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(paramFloat), 0, 0, paramInt1, paramInt2);
    if (this.scroller.computeScrollOffset())
    {
      paramCoordinatorLayout = new FlingRunnable(paramCoordinatorLayout, paramV);
      this.flingRunnable = paramCoordinatorLayout;
      ViewCompat.postOnAnimation(paramV, paramCoordinatorLayout);
      return true;
    }
    onFlingFinished(paramCoordinatorLayout, paramV);
    return false;
  }
  
  int getMaxDragOffset(V paramV)
  {
    return -paramV.getHeight();
  }
  
  int getScrollRangeForDragFling(V paramV)
  {
    return paramV.getHeight();
  }
  
  int getTopBottomOffsetForScrollingSibling()
  {
    return getTopAndBottomOffset();
  }
  
  void onFlingFinished(CoordinatorLayout paramCoordinatorLayout, V paramV) {}
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (this.touchSlop < 0) {
      this.touchSlop = ViewConfiguration.get(paramCoordinatorLayout.getContext()).getScaledTouchSlop();
    }
    int i;
    if ((paramMotionEvent.getActionMasked() == 2) && (this.isBeingDragged))
    {
      i = this.activePointerId;
      if (i == -1) {
        return false;
      }
      i = paramMotionEvent.findPointerIndex(i);
      if (i == -1) {
        return false;
      }
      i = (int)paramMotionEvent.getY(i);
      if (Math.abs(i - this.lastMotionY) > this.touchSlop)
      {
        this.lastMotionY = i;
        return true;
      }
    }
    if (paramMotionEvent.getActionMasked() == 0)
    {
      this.activePointerId = -1;
      int j = (int)paramMotionEvent.getX();
      i = (int)paramMotionEvent.getY();
      boolean bool;
      if ((canDragView(paramV)) && (paramCoordinatorLayout.isPointInChildBounds(paramV, j, i))) {
        bool = true;
      } else {
        bool = false;
      }
      this.isBeingDragged = bool;
      if (bool)
      {
        this.lastMotionY = i;
        this.activePointerId = paramMotionEvent.getPointerId(0);
        ensureVelocityTracker();
        paramCoordinatorLayout = this.scroller;
        if ((paramCoordinatorLayout != null) && (!paramCoordinatorLayout.isFinished()))
        {
          this.scroller.abortAnimation();
          return true;
        }
      }
    }
    paramCoordinatorLayout = this.velocityTracker;
    if (paramCoordinatorLayout != null) {
      paramCoordinatorLayout.addMovement(paramMotionEvent);
    }
    return false;
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    int j = 0;
    int k = 0;
    int m = paramMotionEvent.getActionMasked();
    boolean bool2 = true;
    int i = k;
    switch (m)
    {
    case 4: 
    case 5: 
    default: 
      break;
    case 6: 
      if (paramMotionEvent.getActionIndex() == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.activePointerId = paramMotionEvent.getPointerId(i);
      this.lastMotionY = ((int)(paramMotionEvent.getY(i) + 0.5F));
      break;
    case 2: 
      i = paramMotionEvent.findPointerIndex(this.activePointerId);
      if (i == -1) {
        return false;
      }
      i = (int)paramMotionEvent.getY(i);
      k = this.lastMotionY;
      this.lastMotionY = i;
      scroll(paramCoordinatorLayout, paramV, k - i, getMaxDragOffset(paramV), 0);
      break;
    case 1: 
      VelocityTracker localVelocityTracker = this.velocityTracker;
      i = k;
      if (localVelocityTracker != null)
      {
        i = 1;
        localVelocityTracker.addMovement(paramMotionEvent);
        this.velocityTracker.computeCurrentVelocity(1000);
        float f = this.velocityTracker.getYVelocity(this.activePointerId);
        fling(paramCoordinatorLayout, paramV, -getScrollRangeForDragFling(paramV), 0, f);
      }
      break;
    }
    this.isBeingDragged = false;
    this.activePointerId = -1;
    paramCoordinatorLayout = this.velocityTracker;
    j = i;
    if (paramCoordinatorLayout != null)
    {
      paramCoordinatorLayout.recycle();
      this.velocityTracker = null;
      j = i;
    }
    paramCoordinatorLayout = this.velocityTracker;
    if (paramCoordinatorLayout != null) {
      paramCoordinatorLayout.addMovement(paramMotionEvent);
    }
    boolean bool1 = bool2;
    if (!this.isBeingDragged) {
      if (j != 0) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
    }
    return bool1;
  }
  
  final int scroll(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, getTopBottomOffsetForScrollingSibling() - paramInt1, paramInt2, paramInt3);
  }
  
  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    return setHeaderTopBottomOffset(paramCoordinatorLayout, paramV, paramInt, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }
  
  int setHeaderTopBottomOffset(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3)
  {
    int k = getTopAndBottomOffset();
    int j = 0;
    int i = j;
    if (paramInt2 != 0)
    {
      i = j;
      if (k >= paramInt2)
      {
        i = j;
        if (k <= paramInt3)
        {
          paramInt1 = MathUtils.clamp(paramInt1, paramInt2, paramInt3);
          i = j;
          if (k != paramInt1)
          {
            setTopAndBottomOffset(paramInt1);
            i = k - paramInt1;
          }
        }
      }
    }
    return i;
  }
  
  private class FlingRunnable
    implements Runnable
  {
    private final V layout;
    private final CoordinatorLayout parent;
    
    FlingRunnable(V paramV)
    {
      this.parent = paramV;
      View localView;
      this.layout = localView;
    }
    
    public void run()
    {
      if ((this.layout != null) && (HeaderBehavior.this.scroller != null)) {
        if (HeaderBehavior.this.scroller.computeScrollOffset())
        {
          HeaderBehavior localHeaderBehavior = HeaderBehavior.this;
          localHeaderBehavior.setHeaderTopBottomOffset(this.parent, this.layout, localHeaderBehavior.scroller.getCurrY());
          ViewCompat.postOnAnimation(this.layout, this);
        }
        else
        {
          HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/appbar/HeaderBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */