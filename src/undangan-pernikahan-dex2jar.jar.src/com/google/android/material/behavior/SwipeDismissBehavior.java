package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;

public class SwipeDismissBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5F;
  private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0F;
  private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5F;
  public static final int STATE_DRAGGING = 1;
  public static final int STATE_IDLE = 0;
  public static final int STATE_SETTLING = 2;
  public static final int SWIPE_DIRECTION_ANY = 2;
  public static final int SWIPE_DIRECTION_END_TO_START = 1;
  public static final int SWIPE_DIRECTION_START_TO_END = 0;
  float alphaEndSwipeDistance = 0.5F;
  float alphaStartSwipeDistance = 0.0F;
  private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback()
  {
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerId = -1;
    private int originalCapturedViewLeft;
    
    private boolean shouldDismiss(View paramAnonymousView, float paramAnonymousFloat)
    {
      boolean bool1 = false;
      boolean bool3 = false;
      boolean bool2 = false;
      if (paramAnonymousFloat != 0.0F)
      {
        if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
          i = 1;
        } else {
          i = 0;
        }
        if (SwipeDismissBehavior.this.swipeDirection == 2) {
          return true;
        }
        if (SwipeDismissBehavior.this.swipeDirection == 0)
        {
          if (i != 0)
          {
            bool1 = bool2;
            if (paramAnonymousFloat >= 0.0F) {
              break label83;
            }
          }
          else
          {
            bool1 = bool2;
            if (paramAnonymousFloat <= 0.0F) {
              break label83;
            }
          }
          bool1 = true;
          label83:
          return bool1;
        }
        if (SwipeDismissBehavior.this.swipeDirection == 1)
        {
          if (i != 0 ? paramAnonymousFloat > 0.0F : paramAnonymousFloat < 0.0F) {
            bool1 = true;
          }
          return bool1;
        }
        return false;
      }
      int i = paramAnonymousView.getLeft();
      int k = this.originalCapturedViewLeft;
      int j = Math.round(paramAnonymousView.getWidth() * SwipeDismissBehavior.this.dragDismissThreshold);
      bool1 = bool3;
      if (Math.abs(i - k) >= j) {
        bool1 = true;
      }
      return bool1;
    }
    
    public int clampViewPositionHorizontal(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
        paramAnonymousInt2 = 1;
      } else {
        paramAnonymousInt2 = 0;
      }
      int i;
      if (SwipeDismissBehavior.this.swipeDirection == 0)
      {
        if (paramAnonymousInt2 != 0)
        {
          paramAnonymousInt2 = this.originalCapturedViewLeft - paramAnonymousView.getWidth();
          i = this.originalCapturedViewLeft;
        }
        else
        {
          paramAnonymousInt2 = this.originalCapturedViewLeft;
          i = this.originalCapturedViewLeft + paramAnonymousView.getWidth();
        }
      }
      else if (SwipeDismissBehavior.this.swipeDirection == 1)
      {
        if (paramAnonymousInt2 != 0)
        {
          paramAnonymousInt2 = this.originalCapturedViewLeft;
          i = this.originalCapturedViewLeft + paramAnonymousView.getWidth();
        }
        else
        {
          paramAnonymousInt2 = this.originalCapturedViewLeft - paramAnonymousView.getWidth();
          i = this.originalCapturedViewLeft;
        }
      }
      else
      {
        paramAnonymousInt2 = this.originalCapturedViewLeft - paramAnonymousView.getWidth();
        i = this.originalCapturedViewLeft + paramAnonymousView.getWidth();
      }
      return SwipeDismissBehavior.clamp(paramAnonymousInt2, paramAnonymousInt1, i);
    }
    
    public int clampViewPositionVertical(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousView.getTop();
    }
    
    public int getViewHorizontalDragRange(View paramAnonymousView)
    {
      return paramAnonymousView.getWidth();
    }
    
    public void onViewCaptured(View paramAnonymousView, int paramAnonymousInt)
    {
      this.activePointerId = paramAnonymousInt;
      this.originalCapturedViewLeft = paramAnonymousView.getLeft();
      paramAnonymousView = paramAnonymousView.getParent();
      if (paramAnonymousView != null) {
        paramAnonymousView.requestDisallowInterceptTouchEvent(true);
      }
    }
    
    public void onViewDragStateChanged(int paramAnonymousInt)
    {
      if (SwipeDismissBehavior.this.listener != null) {
        SwipeDismissBehavior.this.listener.onDragStateChanged(paramAnonymousInt);
      }
    }
    
    public void onViewPositionChanged(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      float f2 = this.originalCapturedViewLeft + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.alphaStartSwipeDistance;
      float f1 = this.originalCapturedViewLeft + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.alphaEndSwipeDistance;
      if (paramAnonymousInt1 <= f2) {
        paramAnonymousView.setAlpha(1.0F);
      } else if (paramAnonymousInt1 >= f1) {
        paramAnonymousView.setAlpha(0.0F);
      } else {
        paramAnonymousView.setAlpha(SwipeDismissBehavior.clamp(0.0F, 1.0F - SwipeDismissBehavior.fraction(f2, f1, paramAnonymousInt1), 1.0F));
      }
    }
    
    public void onViewReleased(View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      this.activePointerId = -1;
      int i = paramAnonymousView.getWidth();
      boolean bool = false;
      if (shouldDismiss(paramAnonymousView, paramAnonymousFloat1))
      {
        int k = paramAnonymousView.getLeft();
        int j = this.originalCapturedViewLeft;
        if (k < j) {
          i = j - i;
        } else {
          i = j + i;
        }
        bool = true;
      }
      else
      {
        i = this.originalCapturedViewLeft;
      }
      if (SwipeDismissBehavior.this.viewDragHelper.settleCapturedViewAt(i, paramAnonymousView.getTop())) {
        ViewCompat.postOnAnimation(paramAnonymousView, new SwipeDismissBehavior.SettleRunnable(SwipeDismissBehavior.this, paramAnonymousView, bool));
      } else if ((bool) && (SwipeDismissBehavior.this.listener != null)) {
        SwipeDismissBehavior.this.listener.onDismiss(paramAnonymousView);
      }
    }
    
    public boolean tryCaptureView(View paramAnonymousView, int paramAnonymousInt)
    {
      int i = this.activePointerId;
      boolean bool;
      if (((i == -1) || (i == paramAnonymousInt)) && (SwipeDismissBehavior.this.canSwipeDismissView(paramAnonymousView))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  float dragDismissThreshold = 0.5F;
  private boolean interceptingEvents;
  OnDismissListener listener;
  private float sensitivity = 0.0F;
  private boolean sensitivitySet;
  int swipeDirection = 2;
  ViewDragHelper viewDragHelper;
  
  static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
  }
  
  static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(Math.max(paramInt1, paramInt2), paramInt3);
  }
  
  private void ensureViewDragHelper(ViewGroup paramViewGroup)
  {
    if (this.viewDragHelper == null)
    {
      if (this.sensitivitySet) {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, this.sensitivity, this.dragCallback);
      } else {
        paramViewGroup = ViewDragHelper.create(paramViewGroup, this.dragCallback);
      }
      this.viewDragHelper = paramViewGroup;
    }
  }
  
  static float fraction(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat3 - paramFloat1) / (paramFloat2 - paramFloat1);
  }
  
  private void updateAccessibilityActions(View paramView)
  {
    ViewCompat.removeAccessibilityAction(paramView, 1048576);
    if (canSwipeDismissView(paramView)) {
      ViewCompat.replaceAccessibilityAction(paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand()
      {
        public boolean perform(View paramAnonymousView, AccessibilityViewCommand.CommandArguments paramAnonymousCommandArguments)
        {
          boolean bool = SwipeDismissBehavior.this.canSwipeDismissView(paramAnonymousView);
          int k = 0;
          if (bool)
          {
            if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1) {
              j = 1;
            } else {
              j = 0;
            }
            int i;
            if ((SwipeDismissBehavior.this.swipeDirection != 0) || (j == 0))
            {
              i = k;
              if (SwipeDismissBehavior.this.swipeDirection == 1)
              {
                i = k;
                if (j != 0) {}
              }
            }
            else
            {
              i = 1;
            }
            k = paramAnonymousView.getWidth();
            int j = k;
            if (i != 0) {
              j = -k;
            }
            ViewCompat.offsetLeftAndRight(paramAnonymousView, j);
            paramAnonymousView.setAlpha(0.0F);
            if (SwipeDismissBehavior.this.listener != null) {
              SwipeDismissBehavior.this.listener.onDismiss(paramAnonymousView);
            }
            return true;
          }
          return false;
        }
      });
    }
  }
  
  public boolean canSwipeDismissView(View paramView)
  {
    return true;
  }
  
  public int getDragState()
  {
    ViewDragHelper localViewDragHelper = this.viewDragHelper;
    int i;
    if (localViewDragHelper != null) {
      i = localViewDragHelper.getViewDragState();
    } else {
      i = 0;
    }
    return i;
  }
  
  public OnDismissListener getListener()
  {
    return this.listener;
  }
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    boolean bool = this.interceptingEvents;
    switch (paramMotionEvent.getActionMasked())
    {
    case 2: 
    default: 
      break;
    case 1: 
    case 3: 
      this.interceptingEvents = false;
      break;
    case 0: 
      this.interceptingEvents = paramCoordinatorLayout.isPointInChildBounds(paramV, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      bool = this.interceptingEvents;
    }
    if (bool)
    {
      ensureViewDragHelper(paramCoordinatorLayout);
      return this.viewDragHelper.shouldInterceptTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    boolean bool = super.onLayoutChild(paramCoordinatorLayout, paramV, paramInt);
    if (ViewCompat.getImportantForAccessibility(paramV) == 0)
    {
      ViewCompat.setImportantForAccessibility(paramV, 1);
      updateAccessibilityActions(paramV);
    }
    return bool;
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    paramCoordinatorLayout = this.viewDragHelper;
    if (paramCoordinatorLayout != null)
    {
      paramCoordinatorLayout.processTouchEvent(paramMotionEvent);
      return true;
    }
    return false;
  }
  
  public void setDragDismissDistance(float paramFloat)
  {
    this.dragDismissThreshold = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setEndAlphaSwipeDistance(float paramFloat)
  {
    this.alphaEndSwipeDistance = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setListener(OnDismissListener paramOnDismissListener)
  {
    this.listener = paramOnDismissListener;
  }
  
  public void setSensitivity(float paramFloat)
  {
    this.sensitivity = paramFloat;
    this.sensitivitySet = true;
  }
  
  public void setStartAlphaSwipeDistance(float paramFloat)
  {
    this.alphaStartSwipeDistance = clamp(0.0F, paramFloat, 1.0F);
  }
  
  public void setSwipeDirection(int paramInt)
  {
    this.swipeDirection = paramInt;
  }
  
  public static abstract interface OnDismissListener
  {
    public abstract void onDismiss(View paramView);
    
    public abstract void onDragStateChanged(int paramInt);
  }
  
  private class SettleRunnable
    implements Runnable
  {
    private final boolean dismiss;
    private final View view;
    
    SettleRunnable(View paramView, boolean paramBoolean)
    {
      this.view = paramView;
      this.dismiss = paramBoolean;
    }
    
    public void run()
    {
      if ((SwipeDismissBehavior.this.viewDragHelper != null) && (SwipeDismissBehavior.this.viewDragHelper.continueSettling(true))) {
        ViewCompat.postOnAnimation(this.view, this);
      } else if ((this.dismiss) && (SwipeDismissBehavior.this.listener != null)) {
        SwipeDismissBehavior.this.listener.onDismiss(this.view);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/behavior/SwipeDismissBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */