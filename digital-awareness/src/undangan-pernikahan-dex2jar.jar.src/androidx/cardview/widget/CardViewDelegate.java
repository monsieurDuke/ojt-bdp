package androidx.cardview.widget;

import android.graphics.drawable.Drawable;
import android.view.View;

abstract interface CardViewDelegate
{
  public abstract Drawable getCardBackground();
  
  public abstract View getCardView();
  
  public abstract boolean getPreventCornerOverlap();
  
  public abstract boolean getUseCompatPadding();
  
  public abstract void setCardBackground(Drawable paramDrawable);
  
  public abstract void setMinWidthHeightInternal(int paramInt1, int paramInt2);
  
  public abstract void setShadowPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/cardview/widget/CardViewDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */