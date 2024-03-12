package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;

public class TransitionBuilder
{
  private static final String TAG = "TransitionBuilder";
  
  public static MotionScene.Transition buildTransition(MotionScene paramMotionScene, int paramInt1, int paramInt2, ConstraintSet paramConstraintSet1, int paramInt3, ConstraintSet paramConstraintSet2)
  {
    MotionScene.Transition localTransition = new MotionScene.Transition(paramInt1, paramMotionScene, paramInt2, paramInt3);
    updateConstraintSetInMotionScene(paramMotionScene, localTransition, paramConstraintSet1, paramConstraintSet2);
    return localTransition;
  }
  
  private static void updateConstraintSetInMotionScene(MotionScene paramMotionScene, MotionScene.Transition paramTransition, ConstraintSet paramConstraintSet1, ConstraintSet paramConstraintSet2)
  {
    int j = paramTransition.getStartConstraintSetId();
    int i = paramTransition.getEndConstraintSetId();
    paramMotionScene.setConstraintSet(j, paramConstraintSet1);
    paramMotionScene.setConstraintSet(i, paramConstraintSet2);
  }
  
  public static void validate(MotionLayout paramMotionLayout)
  {
    if (paramMotionLayout.mScene != null)
    {
      MotionScene localMotionScene = paramMotionLayout.mScene;
      if (localMotionScene.validateLayout(paramMotionLayout))
      {
        if ((localMotionScene.mCurrentTransition != null) && (!localMotionScene.getDefinedTransitions().isEmpty())) {
          return;
        }
        throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
      }
      throw new RuntimeException("MotionLayout doesn't have the right motion scene.");
    }
    throw new RuntimeException("Invalid motion layout. Layout missing Motion Scene.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/TransitionBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */