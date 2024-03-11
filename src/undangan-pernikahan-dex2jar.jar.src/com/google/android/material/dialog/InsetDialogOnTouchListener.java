package com.google.android.material.dialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public class InsetDialogOnTouchListener
  implements View.OnTouchListener
{
  private final Dialog dialog;
  private final int leftInset;
  private final int prePieSlop;
  private final int topInset;
  
  public InsetDialogOnTouchListener(Dialog paramDialog, Rect paramRect)
  {
    this.dialog = paramDialog;
    this.leftInset = paramRect.left;
    this.topInset = paramRect.top;
    this.prePieSlop = ViewConfiguration.get(paramDialog.getContext()).getScaledWindowTouchSlop();
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    Object localObject = paramView.findViewById(16908290);
    int m = this.leftInset + ((View)localObject).getLeft();
    int i = ((View)localObject).getWidth();
    int j = this.topInset + ((View)localObject).getTop();
    int k = ((View)localObject).getHeight();
    if (new RectF(m, j, i + m, k + j).contains(paramMotionEvent.getX(), paramMotionEvent.getY())) {
      return false;
    }
    localObject = MotionEvent.obtain(paramMotionEvent);
    if (paramMotionEvent.getAction() == 1) {
      ((MotionEvent)localObject).setAction(4);
    }
    if (Build.VERSION.SDK_INT < 28)
    {
      ((MotionEvent)localObject).setAction(0);
      i = this.prePieSlop;
      ((MotionEvent)localObject).setLocation(-i - 1, -i - 1);
    }
    paramView.performClick();
    return this.dialog.onTouchEvent((MotionEvent)localObject);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/dialog/InsetDialogOnTouchListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */