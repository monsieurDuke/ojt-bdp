package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.core.motion.utils.SpringStopEngine;
import androidx.constraintlayout.core.motion.utils.StopEngine;
import androidx.constraintlayout.core.motion.utils.StopLogicEngine;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

public class StopLogic
  extends MotionInterpolator
{
  private StopEngine mEngine;
  private SpringStopEngine mSpringStopEngine;
  private StopLogicEngine mStopLogicEngine;
  
  public StopLogic()
  {
    StopLogicEngine localStopLogicEngine = new StopLogicEngine();
    this.mStopLogicEngine = localStopLogicEngine;
    this.mEngine = localStopLogicEngine;
  }
  
  public void config(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    StopLogicEngine localStopLogicEngine = this.mStopLogicEngine;
    this.mEngine = localStopLogicEngine;
    localStopLogicEngine.config(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public String debug(String paramString, float paramFloat)
  {
    return this.mEngine.debug(paramString, paramFloat);
  }
  
  public float getInterpolation(float paramFloat)
  {
    return this.mEngine.getInterpolation(paramFloat);
  }
  
  public float getVelocity()
  {
    return this.mEngine.getVelocity();
  }
  
  public float getVelocity(float paramFloat)
  {
    return this.mEngine.getVelocity(paramFloat);
  }
  
  public boolean isStopped()
  {
    return this.mEngine.isStopped();
  }
  
  public void springConfig(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, int paramInt)
  {
    if (this.mSpringStopEngine == null) {
      this.mSpringStopEngine = new SpringStopEngine();
    }
    SpringStopEngine localSpringStopEngine = this.mSpringStopEngine;
    this.mEngine = localSpringStopEngine;
    localSpringStopEngine.springConfig(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/utils/StopLogic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */