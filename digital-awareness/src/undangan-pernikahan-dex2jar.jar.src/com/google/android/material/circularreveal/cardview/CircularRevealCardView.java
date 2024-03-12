package com.google.android.material.circularreveal.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.circularreveal.CircularRevealWidget.RevealInfo;

public class CircularRevealCardView
  extends MaterialCardView
  implements CircularRevealWidget
{
  private final CircularRevealHelper helper = new CircularRevealHelper(this);
  
  public CircularRevealCardView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CircularRevealCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void actualDraw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
  }
  
  public boolean actualIsOpaque()
  {
    return super.isOpaque();
  }
  
  public void buildCircularRevealCache()
  {
    this.helper.buildCircularRevealCache();
  }
  
  public void destroyCircularRevealCache()
  {
    this.helper.destroyCircularRevealCache();
  }
  
  public void draw(Canvas paramCanvas)
  {
    CircularRevealHelper localCircularRevealHelper = this.helper;
    if (localCircularRevealHelper != null) {
      localCircularRevealHelper.draw(paramCanvas);
    } else {
      super.draw(paramCanvas);
    }
  }
  
  public Drawable getCircularRevealOverlayDrawable()
  {
    return this.helper.getCircularRevealOverlayDrawable();
  }
  
  public int getCircularRevealScrimColor()
  {
    return this.helper.getCircularRevealScrimColor();
  }
  
  public CircularRevealWidget.RevealInfo getRevealInfo()
  {
    return this.helper.getRevealInfo();
  }
  
  public boolean isOpaque()
  {
    CircularRevealHelper localCircularRevealHelper = this.helper;
    if (localCircularRevealHelper != null) {
      return localCircularRevealHelper.isOpaque();
    }
    return super.isOpaque();
  }
  
  public void setCircularRevealOverlayDrawable(Drawable paramDrawable)
  {
    this.helper.setCircularRevealOverlayDrawable(paramDrawable);
  }
  
  public void setCircularRevealScrimColor(int paramInt)
  {
    this.helper.setCircularRevealScrimColor(paramInt);
  }
  
  public void setRevealInfo(CircularRevealWidget.RevealInfo paramRevealInfo)
  {
    this.helper.setRevealInfo(paramRevealInfo);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/circularreveal/cardview/CircularRevealCardView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */