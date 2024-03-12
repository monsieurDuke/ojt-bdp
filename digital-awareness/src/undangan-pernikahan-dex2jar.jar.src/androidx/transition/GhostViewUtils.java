package androidx.transition;

import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

class GhostViewUtils
{
  static GhostView addGhost(View paramView, ViewGroup paramViewGroup, Matrix paramMatrix)
  {
    if (Build.VERSION.SDK_INT == 28) {
      return GhostViewPlatform.addGhost(paramView, paramViewGroup, paramMatrix);
    }
    return GhostViewPort.addGhost(paramView, paramViewGroup, paramMatrix);
  }
  
  static void removeGhost(View paramView)
  {
    if (Build.VERSION.SDK_INT == 28) {
      GhostViewPlatform.removeGhost(paramView);
    } else {
      GhostViewPort.removeGhost(paramView);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/GhostViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */