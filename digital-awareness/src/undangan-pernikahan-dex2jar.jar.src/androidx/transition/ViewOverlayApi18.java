package androidx.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

class ViewOverlayApi18
  implements ViewOverlayImpl
{
  private final ViewOverlay mViewOverlay;
  
  ViewOverlayApi18(View paramView)
  {
    this.mViewOverlay = paramView.getOverlay();
  }
  
  public void add(Drawable paramDrawable)
  {
    this.mViewOverlay.add(paramDrawable);
  }
  
  public void remove(Drawable paramDrawable)
  {
    this.mViewOverlay.remove(paramDrawable);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewOverlayApi18.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */