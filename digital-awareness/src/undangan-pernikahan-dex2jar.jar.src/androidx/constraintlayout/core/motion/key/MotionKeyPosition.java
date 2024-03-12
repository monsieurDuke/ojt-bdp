package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues.PositionType;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;

public class MotionKeyPosition
  extends MotionKey
{
  static final int KEY_TYPE = 2;
  static final String NAME = "KeyPosition";
  protected static final float SELECTION_SLOPE = 20.0F;
  public static final int TYPE_CARTESIAN = 0;
  public static final int TYPE_PATH = 1;
  public static final int TYPE_SCREEN = 2;
  public float mAltPercentX = NaN.0F;
  public float mAltPercentY = NaN.0F;
  private float mCalculatedPositionX = NaN.0F;
  private float mCalculatedPositionY = NaN.0F;
  public int mCurveFit = UNSET;
  public int mDrawPath = 0;
  public int mPathMotionArc = UNSET;
  public float mPercentHeight = NaN.0F;
  public float mPercentWidth = NaN.0F;
  public float mPercentX = NaN.0F;
  public float mPercentY = NaN.0F;
  public int mPositionType = 0;
  public String mTransitionEasing = null;
  
  public MotionKeyPosition()
  {
    this.mType = 2;
  }
  
  private void calcCartesianPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f3 = paramFloat3 - paramFloat1;
    float f4 = paramFloat4 - paramFloat2;
    boolean bool = Float.isNaN(this.mPercentX);
    float f2 = 0.0F;
    if (bool) {
      paramFloat3 = 0.0F;
    } else {
      paramFloat3 = this.mPercentX;
    }
    if (Float.isNaN(this.mAltPercentY)) {
      paramFloat4 = 0.0F;
    } else {
      paramFloat4 = this.mAltPercentY;
    }
    float f1;
    if (Float.isNaN(this.mPercentY)) {
      f1 = 0.0F;
    } else {
      f1 = this.mPercentY;
    }
    if (!Float.isNaN(this.mAltPercentX)) {
      f2 = this.mAltPercentX;
    }
    this.mCalculatedPositionX = ((int)(f3 * paramFloat3 + paramFloat1 + f4 * f2));
    this.mCalculatedPositionY = ((int)(f3 * paramFloat4 + paramFloat2 + f4 * f1));
  }
  
  private void calcPathPosition(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramFloat3 -= paramFloat1;
    paramFloat4 -= paramFloat2;
    float f2 = -paramFloat4;
    float f3 = this.mPercentX;
    float f1 = this.mPercentY;
    this.mCalculatedPositionX = (paramFloat3 * f3 + paramFloat1 + f2 * f1);
    this.mCalculatedPositionY = (f3 * paramFloat4 + paramFloat2 + f1 * paramFloat3);
  }
  
  private void calcScreenPosition(int paramInt1, int paramInt2)
  {
    float f2 = paramInt1 - 0;
    float f1 = this.mPercentX;
    this.mCalculatedPositionX = (f2 * f1 + 0 / 2);
    this.mCalculatedPositionY = ((paramInt2 - 0) * f1 + 0 / 2);
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  void calcPosition(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    switch (this.mPositionType)
    {
    default: 
      calcCartesianPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
    case 2: 
      calcScreenPosition(paramInt1, paramInt2);
      return;
    }
    calcPathPosition(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public MotionKey clone()
  {
    return new MotionKeyPosition().copy(this);
  }
  
  public MotionKey copy(MotionKey paramMotionKey)
  {
    super.copy(paramMotionKey);
    paramMotionKey = (MotionKeyPosition)paramMotionKey;
    this.mTransitionEasing = paramMotionKey.mTransitionEasing;
    this.mPathMotionArc = paramMotionKey.mPathMotionArc;
    this.mDrawPath = paramMotionKey.mDrawPath;
    this.mPercentWidth = paramMotionKey.mPercentWidth;
    this.mPercentHeight = NaN.0F;
    this.mPercentX = paramMotionKey.mPercentX;
    this.mPercentY = paramMotionKey.mPercentY;
    this.mAltPercentX = paramMotionKey.mAltPercentX;
    this.mAltPercentY = paramMotionKey.mAltPercentY;
    this.mCalculatedPositionX = paramMotionKey.mCalculatedPositionX;
    this.mCalculatedPositionY = paramMotionKey.mCalculatedPositionY;
    return this;
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet) {}
  
  public int getId(String paramString)
  {
    return TypedValues.PositionType.getId(paramString);
  }
  
  float getPositionX()
  {
    return this.mCalculatedPositionX;
  }
  
  float getPositionY()
  {
    return this.mCalculatedPositionY;
  }
  
  public boolean intersects(int paramInt1, int paramInt2, FloatRect paramFloatRect1, FloatRect paramFloatRect2, float paramFloat1, float paramFloat2)
  {
    calcPosition(paramInt1, paramInt2, paramFloatRect1.centerX(), paramFloatRect1.centerY(), paramFloatRect2.centerX(), paramFloatRect2.centerY());
    return (Math.abs(paramFloat1 - this.mCalculatedPositionX) < 20.0F) && (Math.abs(paramFloat2 - this.mCalculatedPositionY) < 20.0F);
  }
  
  public void positionAttributes(MotionWidget paramMotionWidget, FloatRect paramFloatRect1, FloatRect paramFloatRect2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    switch (this.mPositionType)
    {
    default: 
      positionCartAttributes(paramFloatRect1, paramFloatRect2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
      return;
    case 2: 
      positionScreenAttributes(paramMotionWidget, paramFloatRect1, paramFloatRect2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
      return;
    }
    positionPathAttributes(paramFloatRect1, paramFloatRect2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
  }
  
  void positionCartAttributes(FloatRect paramFloatRect1, FloatRect paramFloatRect2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    float f1 = paramFloatRect1.centerX();
    float f2 = paramFloatRect1.centerY();
    float f4 = paramFloatRect2.centerX();
    float f3 = paramFloatRect2.centerY();
    f4 -= f1;
    f3 -= f2;
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = ((paramFloat1 - f1) / f4);
        paramArrayOfFloat[1] = ((paramFloat2 - f2) / f3);
      }
      else
      {
        paramArrayOfFloat[1] = ((paramFloat1 - f1) / f4);
        paramArrayOfFloat[0] = ((paramFloat2 - f2) / f3);
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfFloat[0] = ((paramFloat1 - f1) / f4);
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[1] = ((paramFloat2 - f2) / f3);
    }
  }
  
  void positionPathAttributes(FloatRect paramFloatRect1, FloatRect paramFloatRect2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    float f1 = paramFloatRect1.centerX();
    float f2 = paramFloatRect1.centerY();
    float f4 = paramFloatRect2.centerX();
    float f3 = paramFloatRect2.centerY();
    f4 -= f1;
    float f5 = f3 - f2;
    f3 = (float)Math.hypot(f4, f5);
    if (f3 < 1.0E-4D)
    {
      System.out.println("distance ~ 0");
      paramArrayOfFloat[0] = 0.0F;
      paramArrayOfFloat[1] = 0.0F;
      return;
    }
    f4 /= f3;
    float f6 = f5 / f3;
    f5 = ((paramFloat2 - f2) * f4 - (paramFloat1 - f1) * f6) / f3;
    paramFloat1 = ((paramFloat1 - f1) * f4 + (paramFloat2 - f2) * f6) / f3;
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = paramFloat1;
        paramArrayOfFloat[1] = f5;
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[0] = paramFloat1;
      paramArrayOfFloat[1] = f5;
    }
  }
  
  void positionScreenAttributes(MotionWidget paramMotionWidget, FloatRect paramFloatRect1, FloatRect paramFloatRect2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    paramFloatRect1.centerX();
    paramFloatRect1.centerY();
    paramFloatRect2.centerX();
    paramFloatRect2.centerY();
    paramMotionWidget = paramMotionWidget.getParent();
    int i = paramMotionWidget.getWidth();
    int j = paramMotionWidget.getHeight();
    if (paramArrayOfString[0] != null)
    {
      if ("percentX".equals(paramArrayOfString[0]))
      {
        paramArrayOfFloat[0] = (paramFloat1 / i);
        paramArrayOfFloat[1] = (paramFloat2 / j);
      }
      else
      {
        paramArrayOfFloat[1] = (paramFloat1 / i);
        paramArrayOfFloat[0] = (paramFloat2 / j);
      }
    }
    else
    {
      paramArrayOfString[0] = "percentX";
      paramArrayOfFloat[0] = (paramFloat1 / i);
      paramArrayOfString[1] = "percentY";
      paramArrayOfFloat[1] = (paramFloat2 / j);
    }
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramFloat);
    case 507: 
      this.mPercentY = paramFloat;
      break;
    case 506: 
      this.mPercentX = paramFloat;
      break;
    case 505: 
      this.mPercentWidth = paramFloat;
      this.mPercentHeight = paramFloat;
      break;
    case 504: 
      this.mPercentHeight = paramFloat;
      break;
    case 503: 
      this.mPercentWidth = paramFloat;
    }
    return true;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return super.setValue(paramInt1, paramInt2);
    case 510: 
      this.mPositionType = paramInt2;
      break;
    case 508: 
      this.mCurveFit = paramInt2;
      break;
    case 100: 
      this.mFramePosition = paramInt2;
    }
    return true;
  }
  
  public boolean setValue(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramString);
    }
    this.mTransitionEasing = paramString.toString();
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/key/MotionKeyPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */