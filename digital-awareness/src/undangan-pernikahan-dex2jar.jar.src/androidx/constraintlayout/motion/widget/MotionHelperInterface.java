package androidx.constraintlayout.motion.widget;

import android.graphics.Canvas;
import android.view.View;
import java.util.HashMap;

public abstract interface MotionHelperInterface
  extends Animatable, MotionLayout.TransitionListener
{
  public abstract boolean isDecorator();
  
  public abstract boolean isUseOnHide();
  
  public abstract boolean isUsedOnShow();
  
  public abstract void onFinishedMotionScene(MotionLayout paramMotionLayout);
  
  public abstract void onPostDraw(Canvas paramCanvas);
  
  public abstract void onPreDraw(Canvas paramCanvas);
  
  public abstract void onPreSetup(MotionLayout paramMotionLayout, HashMap<View, MotionController> paramHashMap);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionHelperInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */