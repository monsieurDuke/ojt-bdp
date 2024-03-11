package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.Resources;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.color.MaterialColors;

public class SnackbarContentLayout
  extends LinearLayout
  implements ContentViewCallback
{
  private Button actionView;
  private int maxInlineActionWidth;
  private TextView messageView;
  
  public SnackbarContentLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SnackbarContentLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private static void updateTopBottomPadding(View paramView, int paramInt1, int paramInt2)
  {
    if (ViewCompat.isPaddingRelative(paramView)) {
      ViewCompat.setPaddingRelative(paramView, ViewCompat.getPaddingStart(paramView), paramInt1, ViewCompat.getPaddingEnd(paramView), paramInt2);
    } else {
      paramView.setPadding(paramView.getPaddingLeft(), paramInt1, paramView.getPaddingRight(), paramInt2);
    }
  }
  
  private boolean updateViewsWithinLayout(int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool = false;
    if (paramInt1 != getOrientation())
    {
      setOrientation(paramInt1);
      bool = true;
    }
    if ((this.messageView.getPaddingTop() != paramInt2) || (this.messageView.getPaddingBottom() != paramInt3))
    {
      updateTopBottomPadding(this.messageView, paramInt2, paramInt3);
      bool = true;
    }
    return bool;
  }
  
  public void animateContentIn(int paramInt1, int paramInt2)
  {
    this.messageView.setAlpha(0.0F);
    this.messageView.animate().alpha(1.0F).setDuration(paramInt2).setStartDelay(paramInt1).start();
    if (this.actionView.getVisibility() == 0)
    {
      this.actionView.setAlpha(0.0F);
      this.actionView.animate().alpha(1.0F).setDuration(paramInt2).setStartDelay(paramInt1).start();
    }
  }
  
  public void animateContentOut(int paramInt1, int paramInt2)
  {
    this.messageView.setAlpha(1.0F);
    this.messageView.animate().alpha(0.0F).setDuration(paramInt2).setStartDelay(paramInt1).start();
    if (this.actionView.getVisibility() == 0)
    {
      this.actionView.setAlpha(1.0F);
      this.actionView.animate().alpha(0.0F).setDuration(paramInt2).setStartDelay(paramInt1).start();
    }
  }
  
  public Button getActionView()
  {
    return this.actionView;
  }
  
  public TextView getMessageView()
  {
    return this.messageView;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.messageView = ((TextView)findViewById(R.id.snackbar_text));
    this.actionView = ((Button)findViewById(R.id.snackbar_action));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (getOrientation() == 1) {
      return;
    }
    int j = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
    int k = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
    int i;
    if (this.messageView.getLayout().getLineCount() > 1) {
      i = 1;
    } else {
      i = 0;
    }
    int m = 0;
    if ((i != 0) && (this.maxInlineActionWidth > 0) && (this.actionView.getMeasuredWidth() > this.maxInlineActionWidth))
    {
      i = m;
      if (updateViewsWithinLayout(1, j, j - k)) {
        i = 1;
      }
    }
    else
    {
      if (i == 0) {
        j = k;
      }
      i = m;
      if (updateViewsWithinLayout(0, j, j)) {
        i = 1;
      }
    }
    if (i != 0) {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public void setMaxInlineActionWidth(int paramInt)
  {
    this.maxInlineActionWidth = paramInt;
  }
  
  void updateActionTextColorAlphaIfNeeded(float paramFloat)
  {
    if (paramFloat != 1.0F)
    {
      int i = this.actionView.getCurrentTextColor();
      int j = MaterialColors.getColor(this, R.attr.colorSurface);
      i = MaterialColors.layer(j, i, paramFloat);
      this.actionView.setTextColor(i);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/snackbar/SnackbarContentLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */