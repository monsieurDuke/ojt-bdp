package androidx.core.view;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class DragStartHelper
{
  private boolean mDragging;
  private int mLastTouchX;
  private int mLastTouchY;
  private final OnDragStartListener mListener;
  private final View.OnLongClickListener mLongClickListener = new DragStartHelper..ExternalSyntheticLambda0(this);
  private final View.OnTouchListener mTouchListener = new DragStartHelper..ExternalSyntheticLambda1(this);
  private final View mView;
  
  public DragStartHelper(View paramView, OnDragStartListener paramOnDragStartListener)
  {
    this.mView = paramView;
    this.mListener = paramOnDragStartListener;
  }
  
  public void attach()
  {
    this.mView.setOnLongClickListener(this.mLongClickListener);
    this.mView.setOnTouchListener(this.mTouchListener);
  }
  
  public void detach()
  {
    this.mView.setOnLongClickListener(null);
    this.mView.setOnTouchListener(null);
  }
  
  public void getTouchPosition(Point paramPoint)
  {
    paramPoint.set(this.mLastTouchX, this.mLastTouchY);
  }
  
  public boolean onLongClick(View paramView)
  {
    return this.mListener.onDragStart(paramView, this);
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 2: 
      if ((MotionEventCompat.isFromSource(paramMotionEvent, 8194)) && ((paramMotionEvent.getButtonState() & 0x1) != 0) && (!this.mDragging) && ((this.mLastTouchX != i) || (this.mLastTouchY != j)))
      {
        this.mLastTouchX = i;
        this.mLastTouchY = j;
        boolean bool = this.mListener.onDragStart(paramView, this);
        this.mDragging = bool;
        return bool;
      }
      break;
    case 1: 
    case 3: 
      this.mDragging = false;
      break;
    case 0: 
      this.mLastTouchX = i;
      this.mLastTouchY = j;
    }
    return false;
  }
  
  public static abstract interface OnDragStartListener
  {
    public abstract boolean onDragStart(View paramView, DragStartHelper paramDragStartHelper);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/DragStartHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */