package androidx.transition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupOverlayApi14
  extends ViewOverlayApi14
  implements ViewGroupOverlayImpl
{
  ViewGroupOverlayApi14(Context paramContext, ViewGroup paramViewGroup, View paramView)
  {
    super(paramContext, paramViewGroup, paramView);
  }
  
  static ViewGroupOverlayApi14 createFrom(ViewGroup paramViewGroup)
  {
    return (ViewGroupOverlayApi14)ViewOverlayApi14.createFrom(paramViewGroup);
  }
  
  public void add(View paramView)
  {
    this.mOverlayViewGroup.add(paramView);
  }
  
  public void remove(View paramView)
  {
    this.mOverlayViewGroup.remove(paramView);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewGroupOverlayApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */