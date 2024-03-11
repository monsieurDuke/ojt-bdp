package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MotionPaths
  implements Comparable<MotionPaths>
{
  public static final int CARTESIAN = 0;
  public static final boolean DEBUG = false;
  static final int OFF_HEIGHT = 4;
  static final int OFF_PATH_ROTATE = 5;
  static final int OFF_POSITION = 0;
  static final int OFF_WIDTH = 3;
  static final int OFF_X = 1;
  static final int OFF_Y = 2;
  public static final boolean OLD_WAY = false;
  public static final int PERPENDICULAR = 1;
  public static final int SCREEN = 2;
  public static final String TAG = "MotionPaths";
  static String[] names = { "position", "x", "y", "width", "height", "pathRotate" };
  HashMap<String, CustomVariable> customAttributes = new HashMap();
  float height;
  int mAnimateCircleAngleTo;
  int mAnimateRelativeTo = -1;
  int mDrawPath = 0;
  Easing mKeyFrameEasing;
  int mMode = 0;
  int mPathMotionArc = -1;
  float mPathRotate = NaN.0F;
  float mProgress = NaN.0F;
  float mRelativeAngle = NaN.0F;
  Motion mRelativeToController = null;
  double[] mTempDelta = new double[18];
  double[] mTempValue = new double[18];
  float position;
  float time;
  float width;
  float x;
  float y;
  
  public MotionPaths() {}
  
  public MotionPaths(int paramInt1, int paramInt2, MotionKeyPosition paramMotionKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2)
  {
    if (paramMotionPaths1.mAnimateRelativeTo != -1)
    {
      initPolar(paramInt1, paramInt2, paramMotionKeyPosition, paramMotionPaths1, paramMotionPaths2);
      return;
    }
    switch (paramMotionKeyPosition.mPositionType)
    {
    default: 
      initCartesian(paramMotionKeyPosition, paramMotionPaths1, paramMotionPaths2);
      return;
    case 2: 
      initScreen(paramInt1, paramInt2, paramMotionKeyPosition, paramMotionPaths1, paramMotionPaths2);
      return;
    }
    initPath(paramMotionKeyPosition, paramMotionPaths1, paramMotionPaths2);
  }
  
  private boolean diff(float paramFloat1, float paramFloat2)
  {
    boolean bool3 = Float.isNaN(paramFloat1);
    boolean bool1 = true;
    boolean bool2 = true;
    if ((!bool3) && (!Float.isNaN(paramFloat2)))
    {
      if (Math.abs(paramFloat1 - paramFloat2) > 1.0E-6F) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    if (Float.isNaN(paramFloat1) == Float.isNaN(paramFloat2)) {
      bool1 = false;
    }
    return bool1;
  }
  
  private static final float xRotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    return (paramFloat5 - paramFloat3) * paramFloat2 - (paramFloat6 - paramFloat4) * paramFloat1 + paramFloat3;
  }
  
  private static final float yRotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    return (paramFloat5 - paramFloat3) * paramFloat1 + (paramFloat6 - paramFloat4) * paramFloat2 + paramFloat4;
  }
  
  public void applyParameters(MotionWidget paramMotionWidget)
  {
    this.mKeyFrameEasing = Easing.getInterpolator(paramMotionWidget.motion.mTransitionEasing);
    this.mPathMotionArc = paramMotionWidget.motion.mPathMotionArc;
    this.mAnimateRelativeTo = paramMotionWidget.motion.mAnimateRelativeTo;
    this.mPathRotate = paramMotionWidget.motion.mPathRotate;
    this.mDrawPath = paramMotionWidget.motion.mDrawPath;
    this.mAnimateCircleAngleTo = paramMotionWidget.motion.mAnimateCircleAngleTo;
    this.mProgress = paramMotionWidget.propertySet.mProgress;
    this.mRelativeAngle = 0.0F;
    Iterator localIterator = paramMotionWidget.getCustomAttributeNames().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      CustomVariable localCustomVariable = paramMotionWidget.getCustomAttribute(str);
      if ((localCustomVariable != null) && (localCustomVariable.isContinuous())) {
        this.customAttributes.put(str, localCustomVariable);
      }
    }
  }
  
  public int compareTo(MotionPaths paramMotionPaths)
  {
    return Float.compare(this.position, paramMotionPaths.position);
  }
  
  public void configureRelativeTo(Motion paramMotion)
  {
    paramMotion.getPos(this.mProgress);
  }
  
  void different(MotionPaths paramMotionPaths, boolean[] paramArrayOfBoolean, String[] paramArrayOfString, boolean paramBoolean)
  {
    boolean bool1 = diff(this.x, paramMotionPaths.x);
    boolean bool2 = diff(this.y, paramMotionPaths.y);
    int i = 0 + 1;
    paramArrayOfBoolean[0] |= diff(this.position, paramMotionPaths.position);
    int j = i + 1;
    paramArrayOfBoolean[i] |= bool1 | bool2 | paramBoolean;
    i = j + 1;
    paramArrayOfBoolean[j] |= bool1 | bool2 | paramBoolean;
    j = i + 1;
    paramArrayOfBoolean[i] |= diff(this.width, paramMotionPaths.width);
    paramArrayOfBoolean[j] |= diff(this.height, paramMotionPaths.height);
  }
  
  void fillStandard(double[] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    float[] arrayOfFloat = new float[6];
    arrayOfFloat[0] = this.position;
    arrayOfFloat[1] = this.x;
    arrayOfFloat[2] = this.y;
    arrayOfFloat[3] = this.width;
    arrayOfFloat[4] = this.height;
    arrayOfFloat[5] = this.mPathRotate;
    int k = 0;
    int i = 0;
    while (i < paramArrayOfInt.length)
    {
      int j = k;
      if (paramArrayOfInt[i] < arrayOfFloat.length)
      {
        paramArrayOfDouble[k] = arrayOfFloat[paramArrayOfInt[i]];
        j = k + 1;
      }
      i++;
      k = j;
    }
  }
  
  void getBounds(int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat, int paramInt)
  {
    float f1 = this.x;
    f1 = this.y;
    float f2 = this.width;
    f1 = this.height;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      float f3 = (float)paramArrayOfDouble[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f1 = f3;
        break;
      case 3: 
        f2 = f3;
        break;
      case 2: 
        
      }
    }
    paramArrayOfFloat[paramInt] = f2;
    paramArrayOfFloat[(paramInt + 1)] = f1;
  }
  
  void getCenter(double paramDouble, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat, int paramInt)
  {
    float f3 = this.x;
    float f2 = this.y;
    float f5 = this.width;
    float f4 = this.height;
    float f1;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      f1 = (float)paramArrayOfDouble[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f4 = f1;
        break;
      case 3: 
        f5 = f1;
        break;
      case 2: 
        f2 = f1;
        break;
      case 1: 
        f3 = f1;
      }
    }
    paramArrayOfDouble = this.mRelativeToController;
    if (paramArrayOfDouble != null)
    {
      paramArrayOfInt = new float[2];
      paramArrayOfDouble.getCenter(paramDouble, paramArrayOfInt, new float[2]);
      f1 = paramArrayOfInt[0];
      float f6 = paramArrayOfInt[1];
      f1 = (float)(f1 + f3 * Math.sin(f2) - f5 / 2.0F);
      f2 = (float)(f6 - f3 * Math.cos(f2) - f4 / 2.0F);
      f3 = f1;
    }
    paramArrayOfFloat[paramInt] = (f5 / 2.0F + f3 + 0.0F);
    paramArrayOfFloat[(paramInt + 1)] = (f4 / 2.0F + f2 + 0.0F);
  }
  
  void getCenter(double paramDouble, int[] paramArrayOfInt, double[] paramArrayOfDouble1, float[] paramArrayOfFloat1, double[] paramArrayOfDouble2, float[] paramArrayOfFloat2)
  {
    float f4 = this.x;
    float f3 = this.y;
    float f6 = this.width;
    float f5 = this.height;
    float f8 = 0.0F;
    float f7 = 0.0F;
    float f10 = 0.0F;
    float f9 = 0.0F;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      f2 = (float)paramArrayOfDouble1[i];
      f1 = (float)paramArrayOfDouble2[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f5 = f2;
        f9 = f1;
        break;
      case 3: 
        f6 = f2;
        f10 = f1;
        break;
      case 2: 
        f3 = f2;
        f7 = f1;
        break;
      case 1: 
        f8 = f1;
        f4 = f2;
      }
    }
    float f1 = f10 / 2.0F + f8;
    float f2 = f9 / 2.0F + f7;
    paramArrayOfDouble1 = this.mRelativeToController;
    if (paramArrayOfDouble1 != null)
    {
      paramArrayOfDouble2 = new float[2];
      paramArrayOfInt = new float[2];
      paramArrayOfDouble1.getCenter(paramDouble, paramArrayOfDouble2, paramArrayOfInt);
      f2 = paramArrayOfDouble2[0];
      f10 = paramArrayOfDouble2[1];
      f1 = paramArrayOfInt[0];
      f9 = paramArrayOfInt[1];
      f2 = (float)(f2 + f4 * Math.sin(f3) - f6 / 2.0F);
      f4 = (float)(f10 - f4 * Math.cos(f3) - f5 / 2.0F);
      f1 = (float)(f1 + f8 * Math.sin(f3) + Math.cos(f3) * f7);
      f7 = (float)(f9 - f8 * Math.cos(f3) + Math.sin(f3) * f7);
      f3 = f4;
      f4 = f2;
      f2 = f7;
    }
    paramArrayOfFloat1[0] = (f6 / 2.0F + f4 + 0.0F);
    paramArrayOfFloat1[1] = (f5 / 2.0F + f3 + 0.0F);
    paramArrayOfFloat2[0] = f1;
    paramArrayOfFloat2[1] = f2;
  }
  
  void getCenterVelocity(double paramDouble, int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat, int paramInt)
  {
    float f3 = this.x;
    float f2 = this.y;
    float f5 = this.width;
    float f4 = this.height;
    float f1;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      f1 = (float)paramArrayOfDouble[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f4 = f1;
        break;
      case 3: 
        f5 = f1;
        break;
      case 2: 
        f2 = f1;
        break;
      case 1: 
        f3 = f1;
      }
    }
    paramArrayOfInt = this.mRelativeToController;
    if (paramArrayOfInt != null)
    {
      paramArrayOfDouble = new float[2];
      paramArrayOfInt.getCenter(paramDouble, paramArrayOfDouble, new float[2]);
      f1 = paramArrayOfDouble[0];
      float f6 = paramArrayOfDouble[1];
      f1 = (float)(f1 + f3 * Math.sin(f2) - f5 / 2.0F);
      f2 = (float)(f6 - f3 * Math.cos(f2) - f4 / 2.0F);
      f3 = f1;
    }
    paramArrayOfFloat[paramInt] = (f5 / 2.0F + f3 + 0.0F);
    paramArrayOfFloat[(paramInt + 1)] = (f4 / 2.0F + f2 + 0.0F);
  }
  
  int getCustomData(String paramString, double[] paramArrayOfDouble, int paramInt)
  {
    paramString = (CustomVariable)this.customAttributes.get(paramString);
    if (paramString == null) {
      return 0;
    }
    if (paramString.numberOfInterpolatedValues() == 1)
    {
      paramArrayOfDouble[paramInt] = paramString.getValueToInterpolate();
      return 1;
    }
    int j = paramString.numberOfInterpolatedValues();
    float[] arrayOfFloat = new float[j];
    paramString.getValuesToInterpolate(arrayOfFloat);
    int i = 0;
    while (i < j)
    {
      paramArrayOfDouble[paramInt] = arrayOfFloat[i];
      i++;
      paramInt++;
    }
    return j;
  }
  
  int getCustomDataCount(String paramString)
  {
    paramString = (CustomVariable)this.customAttributes.get(paramString);
    if (paramString == null) {
      return 0;
    }
    return paramString.numberOfInterpolatedValues();
  }
  
  void getRect(int[] paramArrayOfInt, double[] paramArrayOfDouble, float[] paramArrayOfFloat, int paramInt)
  {
    float f2 = this.x;
    float f3 = this.y;
    float f7 = this.width;
    float f4 = this.height;
    int m = 0;
    int k = 0;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      f1 = (float)paramArrayOfDouble[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f4 = f1;
        break;
      case 3: 
        f7 = f1;
        break;
      case 2: 
        f3 = f1;
        break;
      case 1: 
        f2 = f1;
      }
    }
    paramArrayOfInt = this.mRelativeToController;
    if (paramArrayOfInt != null)
    {
      f1 = paramArrayOfInt.getCenterX();
      f5 = this.mRelativeToController.getCenterY();
      f1 = (float)(f1 + f2 * Math.sin(f3) - f7 / 2.0F);
      f3 = (float)(f5 - f2 * Math.cos(f3) - f4 / 2.0F);
      f2 = f1;
    }
    float f6 = f2;
    float f5 = f3;
    float f15 = f2 + f7;
    float f14 = f5;
    float f9 = f15;
    float f13 = f3 + f4;
    float f8 = f6;
    float f12 = f13;
    float f10 = f6 + f7 / 2.0F;
    float f11 = f5 + f4 / 2.0F;
    if (!Float.isNaN(NaN.0F)) {
      f10 = f6 + (f15 - f6) * NaN.0F;
    }
    if (!Float.isNaN(NaN.0F)) {
      f11 = f5 + (f13 - f5) * NaN.0F;
    }
    f4 = f6;
    f3 = f15;
    f2 = f9;
    float f1 = f8;
    if (1.0F != 1.0F)
    {
      f1 = (f6 + f15) / 2.0F;
      f4 = (f6 - f1) * 1.0F + f1;
      f3 = (f15 - f1) * 1.0F + f1;
      f2 = (f9 - f1) * 1.0F + f1;
      f1 = (f8 - f1) * 1.0F + f1;
    }
    f9 = f5;
    f8 = f14;
    f7 = f13;
    f6 = f12;
    if (1.0F != 1.0F)
    {
      f6 = (f5 + f13) / 2.0F;
      f9 = (f5 - f6) * 1.0F + f6;
      f8 = (f14 - f6) * 1.0F + f6;
      f7 = (f13 - f6) * 1.0F + f6;
      f6 = (f12 - f6) * 1.0F + f6;
    }
    if (0.0F != 0.0F)
    {
      float f16 = (float)Math.sin(Math.toRadians(0.0F));
      f15 = (float)Math.cos(Math.toRadians(0.0F));
      f5 = xRotate(f16, f15, f10, f11, f4, f9);
      f9 = yRotate(f16, f15, f10, f11, f4, f9);
      f13 = xRotate(f16, f15, f10, f11, f3, f8);
      f8 = yRotate(f16, f15, f10, f11, f3, f8);
      f12 = xRotate(f16, f15, f10, f11, f2, f7);
      f14 = yRotate(f16, f15, f10, f11, f2, f7);
      f7 = xRotate(f16, f15, f10, f11, f1, f6);
      f6 = yRotate(f16, f15, f10, f11, f1, f6);
      f4 = f5;
      f3 = f13;
      f2 = f12;
      f5 = f14;
      f1 = f7;
    }
    else
    {
      f5 = f7;
    }
    i = paramInt + 1;
    paramArrayOfFloat[paramInt] = (f4 + 0.0F);
    int j = i + 1;
    paramArrayOfFloat[i] = (f9 + 0.0F);
    paramInt = j + 1;
    paramArrayOfFloat[j] = (f3 + 0.0F);
    i = paramInt + 1;
    paramArrayOfFloat[paramInt] = (f8 + 0.0F);
    paramInt = i + 1;
    paramArrayOfFloat[i] = (f2 + 0.0F);
    i = paramInt + 1;
    paramArrayOfFloat[paramInt] = (f5 + 0.0F);
    paramInt = i + 1;
    paramArrayOfFloat[i] = (f1 + 0.0F);
    paramArrayOfFloat[paramInt] = (f6 + 0.0F);
  }
  
  boolean hasCustomData(String paramString)
  {
    return this.customAttributes.containsKey(paramString);
  }
  
  void initCartesian(MotionKeyPosition paramMotionKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2)
  {
    float f1 = paramMotionKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramMotionKeyPosition.mDrawPath;
    float f2;
    if (Float.isNaN(paramMotionKeyPosition.mPercentWidth)) {
      f2 = f1;
    } else {
      f2 = paramMotionKeyPosition.mPercentWidth;
    }
    float f3;
    if (Float.isNaN(paramMotionKeyPosition.mPercentHeight)) {
      f3 = f1;
    } else {
      f3 = paramMotionKeyPosition.mPercentHeight;
    }
    float f13 = paramMotionPaths2.width;
    float f4 = paramMotionPaths1.width;
    float f7 = f13 - f4;
    float f14 = paramMotionPaths2.height;
    float f6 = paramMotionPaths1.height;
    float f8 = f14 - f6;
    this.position = this.time;
    float f11 = paramMotionPaths1.x;
    float f12 = f4 / 2.0F;
    float f5 = paramMotionPaths1.y;
    float f10 = f6 / 2.0F;
    float f9 = paramMotionPaths2.x;
    float f15 = f13 / 2.0F;
    f13 = paramMotionPaths2.y;
    f14 /= 2.0F;
    f9 = f9 + f15 - (f11 + f12);
    f10 = f13 + f14 - (f5 + f10);
    this.x = ((int)(f11 + f9 * f1 - f7 * f2 / 2.0F));
    this.y = ((int)(f5 + f10 * f1 - f8 * f3 / 2.0F));
    this.width = ((int)(f4 + f7 * f2));
    this.height = ((int)(f6 + f8 * f3));
    if (Float.isNaN(paramMotionKeyPosition.mPercentX)) {
      f4 = f1;
    } else {
      f4 = paramMotionKeyPosition.mPercentX;
    }
    if (Float.isNaN(paramMotionKeyPosition.mAltPercentY)) {
      f5 = 0.0F;
    } else {
      f5 = paramMotionKeyPosition.mAltPercentY;
    }
    if (!Float.isNaN(paramMotionKeyPosition.mPercentY)) {
      f1 = paramMotionKeyPosition.mPercentY;
    }
    if (Float.isNaN(paramMotionKeyPosition.mAltPercentX)) {
      f6 = 0.0F;
    } else {
      f6 = paramMotionKeyPosition.mAltPercentX;
    }
    this.mMode = 0;
    this.x = ((int)(paramMotionPaths1.x + f9 * f4 + f10 * f6 - f7 * f2 / 2.0F));
    this.y = ((int)(paramMotionPaths1.y + f9 * f5 + f10 * f1 - f8 * f3 / 2.0F));
    this.mKeyFrameEasing = Easing.getInterpolator(paramMotionKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramMotionKeyPosition.mPathMotionArc;
  }
  
  void initPath(MotionKeyPosition paramMotionKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2)
  {
    float f1 = paramMotionKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramMotionKeyPosition.mDrawPath;
    if (Float.isNaN(paramMotionKeyPosition.mPercentWidth)) {
      f2 = f1;
    } else {
      f2 = paramMotionKeyPosition.mPercentWidth;
    }
    float f3;
    if (Float.isNaN(paramMotionKeyPosition.mPercentHeight)) {
      f3 = f1;
    } else {
      f3 = paramMotionKeyPosition.mPercentHeight;
    }
    float f6 = paramMotionPaths2.width - paramMotionPaths1.width;
    float f5 = paramMotionPaths2.height - paramMotionPaths1.height;
    this.position = this.time;
    if (!Float.isNaN(paramMotionKeyPosition.mPercentX)) {
      f1 = paramMotionKeyPosition.mPercentX;
    }
    float f9 = paramMotionPaths1.x;
    float f10 = paramMotionPaths1.width;
    float f7 = f10 / 2.0F;
    float f8 = paramMotionPaths1.y;
    float f4 = paramMotionPaths1.height;
    float f11 = f4 / 2.0F;
    float f15 = paramMotionPaths2.x;
    float f14 = paramMotionPaths2.width / 2.0F;
    float f12 = paramMotionPaths2.y;
    float f13 = paramMotionPaths2.height / 2.0F;
    f7 = f15 + f14 - (f7 + f9);
    f11 = f12 + f13 - (f8 + f11);
    this.x = ((int)(f9 + f7 * f1 - f6 * f2 / 2.0F));
    this.y = ((int)(f8 + f11 * f1 - f5 * f3 / 2.0F));
    this.width = ((int)(f10 + f6 * f2));
    this.height = ((int)(f4 + f5 * f3));
    if (Float.isNaN(paramMotionKeyPosition.mPercentY)) {
      f4 = 0.0F;
    } else {
      f4 = paramMotionKeyPosition.mPercentY;
    }
    f8 = -f11;
    this.mMode = 1;
    float f2 = (int)(paramMotionPaths1.x + f7 * f1 - f6 * f2 / 2.0F);
    this.x = f2;
    f1 = (int)(paramMotionPaths1.y + f11 * f1 - f5 * f3 / 2.0F);
    this.y = f1;
    this.x = (f2 + f8 * f4);
    this.y = (f1 + f7 * f4);
    this.mAnimateRelativeTo = this.mAnimateRelativeTo;
    this.mKeyFrameEasing = Easing.getInterpolator(paramMotionKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramMotionKeyPosition.mPathMotionArc;
  }
  
  void initPolar(int paramInt1, int paramInt2, MotionKeyPosition paramMotionKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2)
  {
    float f1 = paramMotionKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramMotionKeyPosition.mDrawPath;
    this.mMode = paramMotionKeyPosition.mPositionType;
    if (Float.isNaN(paramMotionKeyPosition.mPercentWidth)) {
      f2 = f1;
    } else {
      f2 = paramMotionKeyPosition.mPercentWidth;
    }
    if (Float.isNaN(paramMotionKeyPosition.mPercentHeight)) {
      f3 = f1;
    } else {
      f3 = paramMotionKeyPosition.mPercentHeight;
    }
    float f6 = paramMotionPaths2.width;
    float f5 = paramMotionPaths1.width;
    float f7 = paramMotionPaths2.height;
    float f4 = paramMotionPaths1.height;
    this.position = this.time;
    this.width = ((int)(f5 + (f6 - f5) * f2));
    this.height = ((int)(f4 + (f7 - f4) * f3));
    switch (paramMotionKeyPosition.mPositionType)
    {
    default: 
      if (Float.isNaN(paramMotionKeyPosition.mPercentX)) {
        f2 = f1;
      }
      break;
    case 2: 
      if (Float.isNaN(paramMotionKeyPosition.mPercentX))
      {
        f3 = paramMotionPaths2.x;
        f2 = paramMotionPaths1.x;
        f2 = (f3 - f2) * f1 + f2;
      }
      else
      {
        f2 = paramMotionKeyPosition.mPercentX * Math.min(f3, f2);
      }
      this.x = f2;
      if (Float.isNaN(paramMotionKeyPosition.mPercentY))
      {
        f3 = paramMotionPaths2.y;
        f2 = paramMotionPaths1.y;
        f1 = (f3 - f2) * f1 + f2;
      }
      else
      {
        f1 = paramMotionKeyPosition.mPercentY;
      }
      this.y = f1;
      break;
    case 1: 
      if (Float.isNaN(paramMotionKeyPosition.mPercentX)) {
        f2 = f1;
      } else {
        f2 = paramMotionKeyPosition.mPercentX;
      }
      f3 = paramMotionPaths2.x;
      f4 = paramMotionPaths1.x;
      this.x = (f2 * (f3 - f4) + f4);
      if (!Float.isNaN(paramMotionKeyPosition.mPercentY)) {
        f1 = paramMotionKeyPosition.mPercentY;
      }
      f3 = paramMotionPaths2.y;
      f2 = paramMotionPaths1.y;
      this.y = (f1 * (f3 - f2) + f2);
      break;
    }
    float f2 = paramMotionKeyPosition.mPercentX;
    float f3 = paramMotionPaths2.x;
    f4 = paramMotionPaths1.x;
    this.x = (f2 * (f3 - f4) + f4);
    if (!Float.isNaN(paramMotionKeyPosition.mPercentY)) {
      f1 = paramMotionKeyPosition.mPercentY;
    }
    f2 = paramMotionPaths2.y;
    f3 = paramMotionPaths1.y;
    this.y = (f1 * (f2 - f3) + f3);
    this.mAnimateRelativeTo = paramMotionPaths1.mAnimateRelativeTo;
    this.mKeyFrameEasing = Easing.getInterpolator(paramMotionKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramMotionKeyPosition.mPathMotionArc;
  }
  
  void initScreen(int paramInt1, int paramInt2, MotionKeyPosition paramMotionKeyPosition, MotionPaths paramMotionPaths1, MotionPaths paramMotionPaths2)
  {
    float f1 = paramMotionKeyPosition.mFramePosition / 100.0F;
    this.time = f1;
    this.mDrawPath = paramMotionKeyPosition.mDrawPath;
    float f2;
    if (Float.isNaN(paramMotionKeyPosition.mPercentWidth)) {
      f2 = f1;
    } else {
      f2 = paramMotionKeyPosition.mPercentWidth;
    }
    float f3;
    if (Float.isNaN(paramMotionKeyPosition.mPercentHeight)) {
      f3 = f1;
    } else {
      f3 = paramMotionKeyPosition.mPercentHeight;
    }
    float f13 = paramMotionPaths2.width;
    float f11 = paramMotionPaths1.width;
    float f8 = f13 - f11;
    float f15 = paramMotionPaths2.height;
    float f7 = paramMotionPaths1.height;
    float f6 = f15 - f7;
    this.position = this.time;
    float f9 = paramMotionPaths1.x;
    float f4 = f11 / 2.0F;
    float f12 = paramMotionPaths1.y;
    float f10 = f7 / 2.0F;
    float f5 = paramMotionPaths2.x;
    f13 /= 2.0F;
    float f14 = paramMotionPaths2.y;
    f15 /= 2.0F;
    this.x = ((int)(f9 + (f5 + f13 - (f9 + f4)) * f1 - f8 * f2 / 2.0F));
    this.y = ((int)(f12 + (f14 + f15 - (f12 + f10)) * f1 - f6 * f3 / 2.0F));
    this.width = ((int)(f11 + f8 * f2));
    this.height = ((int)(f7 + f6 * f3));
    this.mMode = 2;
    if (!Float.isNaN(paramMotionKeyPosition.mPercentX))
    {
      paramInt1 = (int)(paramInt1 - this.width);
      this.x = ((int)(paramMotionKeyPosition.mPercentX * paramInt1));
    }
    if (!Float.isNaN(paramMotionKeyPosition.mPercentY))
    {
      paramInt1 = (int)(paramInt2 - this.height);
      this.y = ((int)(paramMotionKeyPosition.mPercentY * paramInt1));
    }
    this.mAnimateRelativeTo = this.mAnimateRelativeTo;
    this.mKeyFrameEasing = Easing.getInterpolator(paramMotionKeyPosition.mTransitionEasing);
    this.mPathMotionArc = paramMotionKeyPosition.mPathMotionArc;
  }
  
  void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.width = paramFloat3;
    this.height = paramFloat4;
  }
  
  void setDpDt(float paramFloat1, float paramFloat2, float[] paramArrayOfFloat, int[] paramArrayOfInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    float f5 = 0.0F;
    float f4 = 0.0F;
    float f3 = 0.0F;
    float f2 = 0.0F;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      f1 = (float)paramArrayOfDouble1[i];
      float f6 = (float)paramArrayOfDouble2[i];
      switch (paramArrayOfInt[i])
      {
      default: 
        break;
      case 4: 
        f2 = f1;
        break;
      case 3: 
        f3 = f1;
        break;
      case 2: 
        f4 = f1;
        break;
      case 1: 
        f5 = f1;
      }
    }
    float f1 = f5 - 0.0F * f3 / 2.0F;
    f4 -= 0.0F * f2 / 2.0F;
    paramArrayOfFloat[0] = ((1.0F - paramFloat1) * f1 + (f1 + (0.0F + 1.0F) * f3) * paramFloat1 + 0.0F);
    paramArrayOfFloat[1] = ((1.0F - paramFloat2) * f4 + (f4 + (0.0F + 1.0F) * f2) * paramFloat2 + 0.0F);
  }
  
  void setView(float paramFloat, MotionWidget paramMotionWidget, int[] paramArrayOfInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3)
  {
    float f5 = this.x;
    float f6 = this.y;
    float f9 = this.width;
    float f8 = this.height;
    float f11 = 0.0F;
    float f10 = 0.0F;
    float f12 = 0.0F;
    float f2 = 0.0F;
    float f1 = 0.0F;
    float f7 = NaN.0F;
    if ((paramArrayOfInt.length != 0) && (this.mTempValue.length <= paramArrayOfInt[(paramArrayOfInt.length - 1)]))
    {
      i = paramArrayOfInt[(paramArrayOfInt.length - 1)] + 1;
      this.mTempValue = new double[i];
      this.mTempDelta = new double[i];
    }
    Arrays.fill(this.mTempValue, NaN.0D);
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      this.mTempValue[paramArrayOfInt[i]] = paramArrayOfDouble1[i];
      this.mTempDelta[paramArrayOfInt[i]] = paramArrayOfDouble2[i];
    }
    i = 0;
    float f3;
    for (;;)
    {
      paramArrayOfInt = this.mTempValue;
      if (i >= paramArrayOfInt.length) {
        break;
      }
      boolean bool = Double.isNaN(paramArrayOfInt[i]);
      double d = 0.0D;
      if (bool) {
        if (paramArrayOfDouble3 != null)
        {
          if (paramArrayOfDouble3[i] == 0.0D) {
            break label373;
          }
        }
        else {
          break label373;
        }
      }
      if (paramArrayOfDouble3 != null) {
        d = paramArrayOfDouble3[i];
      }
      if (!Double.isNaN(this.mTempValue[i])) {
        d = this.mTempValue[i] + d;
      }
      f3 = (float)d;
      paramArrayOfInt = this.mTempDelta;
      float f4 = (float)paramArrayOfInt[i];
      switch (i)
      {
      default: 
        break;
      case 5: 
        f7 = f3;
        f3 = f5;
        break;
      case 4: 
        f8 = f3;
        f2 = f4;
        f3 = f5;
        break;
      case 3: 
        f9 = f3;
        f12 = f4;
        f3 = f5;
        break;
      case 2: 
        f6 = f3;
        f10 = f4;
        f3 = f5;
        break;
      case 1: 
        f11 = f4;
        break;
      case 0: 
        f1 = f3;
        f3 = f5;
        break;
      }
      label373:
      f3 = f5;
      i++;
      f5 = f3;
    }
    paramArrayOfDouble3 = this.mRelativeToController;
    if (paramArrayOfDouble3 != null)
    {
      paramArrayOfInt = new float[2];
      paramArrayOfDouble1 = new float[2];
      paramArrayOfDouble3.getCenter(paramFloat, paramArrayOfInt, paramArrayOfDouble1);
      paramFloat = paramArrayOfInt[0];
      f1 = paramArrayOfInt[1];
      f3 = paramArrayOfDouble1[0];
      f2 = paramArrayOfDouble1[1];
      paramFloat = (float)(paramFloat + f5 * Math.sin(f6) - f9 / 2.0F);
      f1 = (float)(f1 - f5 * Math.cos(f6) - f8 / 2.0F);
      f3 = (float)(f3 + f11 * Math.sin(f6) + f5 * Math.cos(f6) * f10);
      f2 = (float)(f2 - f11 * Math.cos(f6) + f5 * Math.sin(f6) * f10);
      if (paramArrayOfDouble2.length >= 2)
      {
        paramArrayOfDouble2[0] = f3;
        paramArrayOfDouble2[1] = f2;
      }
      if (!Float.isNaN(f7)) {
        paramMotionWidget.setRotationZ((float)(f7 + Math.toDegrees(Math.atan2(f2, f3))));
      }
    }
    else
    {
      if (!Float.isNaN(f7))
      {
        paramFloat = f12 / 2.0F;
        f1 = f2 / 2.0F;
        paramMotionWidget.setRotationZ((float)(0.0F + (f7 + Math.toDegrees(Math.atan2(f10 + f1, f11 + paramFloat)))));
      }
      f1 = f6;
      paramFloat = f5;
    }
    i = (int)(paramFloat + 0.5F);
    int j = (int)(f1 + 0.5F);
    int m = (int)(paramFloat + 0.5F + f9);
    int k = (int)(0.5F + f1 + f8);
    paramMotionWidget.layout(i, j, m, k);
  }
  
  public void setupRelative(Motion paramMotion, MotionPaths paramMotionPaths)
  {
    double d1 = this.x + this.width / 2.0F - paramMotionPaths.x - paramMotionPaths.width / 2.0F;
    double d2 = this.y + this.height / 2.0F - paramMotionPaths.y - paramMotionPaths.height / 2.0F;
    this.mRelativeToController = paramMotion;
    this.x = ((float)Math.hypot(d2, d1));
    if (Float.isNaN(this.mRelativeAngle)) {
      this.y = ((float)(Math.atan2(d2, d1) + 1.5707963267948966D));
    } else {
      this.y = ((float)Math.toRadians(this.mRelativeAngle));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/MotionPaths.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */