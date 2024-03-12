package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.PathRotateSet;
import androidx.constraintlayout.core.motion.utils.KeyFrameArray.CustomVar;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.motion.utils.ViewState;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Motion
  implements TypedValues
{
  static final int BOUNCE = 4;
  private static final boolean DEBUG = false;
  public static final int DRAW_PATH_AS_CONFIGURED = 4;
  public static final int DRAW_PATH_BASIC = 1;
  public static final int DRAW_PATH_CARTESIAN = 3;
  public static final int DRAW_PATH_NONE = 0;
  public static final int DRAW_PATH_RECTANGLE = 5;
  public static final int DRAW_PATH_RELATIVE = 2;
  public static final int DRAW_PATH_SCREEN = 6;
  static final int EASE_IN = 1;
  static final int EASE_IN_OUT = 0;
  static final int EASE_OUT = 2;
  private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
  public static final int HORIZONTAL_PATH_X = 2;
  public static final int HORIZONTAL_PATH_Y = 3;
  private static final int INTERPOLATOR_REFERENCE_ID = -2;
  private static final int INTERPOLATOR_UNDEFINED = -3;
  static final int LINEAR = 3;
  static final int OVERSHOOT = 5;
  public static final int PATH_PERCENT = 0;
  public static final int PATH_PERPENDICULAR = 1;
  public static final int ROTATION_LEFT = 2;
  public static final int ROTATION_RIGHT = 1;
  private static final int SPLINE_STRING = -1;
  private static final String TAG = "MotionController";
  public static final int VERTICAL_PATH_X = 4;
  public static final int VERTICAL_PATH_Y = 5;
  private int MAX_DIMENSION = 4;
  String[] attributeTable;
  private CurveFit mArcSpline;
  private int[] mAttributeInterpolatorCount;
  private String[] mAttributeNames;
  private HashMap<String, SplineSet> mAttributesMap;
  String mConstraintTag;
  float mCurrentCenterX;
  float mCurrentCenterY;
  private int mCurveFitType = -1;
  private HashMap<String, KeyCycleOscillator> mCycleMap;
  private MotionPaths mEndMotionPath = new MotionPaths();
  private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
  int mId;
  private double[] mInterpolateData;
  private int[] mInterpolateVariables;
  private double[] mInterpolateVelocity;
  private ArrayList<MotionKey> mKeyList = new ArrayList();
  private MotionKeyTrigger[] mKeyTriggers;
  private ArrayList<MotionPaths> mMotionPaths = new ArrayList();
  float mMotionStagger = NaN.0F;
  private boolean mNoMovement = false;
  private int mPathMotionArc = -1;
  private DifferentialInterpolator mQuantizeMotionInterpolator = null;
  private float mQuantizeMotionPhase = NaN.0F;
  private int mQuantizeMotionSteps = -1;
  private CurveFit[] mSpline;
  float mStaggerOffset = 0.0F;
  float mStaggerScale = 1.0F;
  private MotionPaths mStartMotionPath = new MotionPaths();
  private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
  Rect mTempRect = new Rect();
  private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
  private int mTransformPivotTarget = -1;
  private MotionWidget mTransformPivotView = null;
  private float[] mValuesBuff = new float[4];
  private float[] mVelocity = new float[1];
  MotionWidget mView;
  
  public Motion(MotionWidget paramMotionWidget)
  {
    setView(paramMotionWidget);
  }
  
  private float getAdjustedPosition(float paramFloat, float[] paramArrayOfFloat)
  {
    float f1;
    float f4;
    if (paramArrayOfFloat != null)
    {
      paramArrayOfFloat[0] = 1.0F;
      f1 = paramFloat;
    }
    else
    {
      f4 = this.mStaggerScale;
      f1 = paramFloat;
      if (f4 != 1.0D)
      {
        f3 = this.mStaggerOffset;
        f2 = paramFloat;
        if (paramFloat < f3) {
          f2 = 0.0F;
        }
        f1 = f2;
        if (f2 > f3)
        {
          f1 = f2;
          if (f2 < 1.0D) {
            f1 = Math.min((f2 - f3) * f4, 1.0F);
          }
        }
      }
    }
    float f3 = f1;
    Object localObject2 = this.mStartMotionPath.mKeyFrameEasing;
    float f2 = 0.0F;
    paramFloat = NaN.0F;
    Iterator localIterator = this.mMotionPaths.iterator();
    while (localIterator.hasNext())
    {
      MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
      Object localObject1 = localObject2;
      f4 = f2;
      float f5 = paramFloat;
      if (localMotionPaths.mKeyFrameEasing != null) {
        if (localMotionPaths.time < f1)
        {
          localObject1 = localMotionPaths.mKeyFrameEasing;
          f4 = localMotionPaths.time;
          f5 = paramFloat;
        }
        else
        {
          localObject1 = localObject2;
          f4 = f2;
          f5 = paramFloat;
          if (Float.isNaN(paramFloat))
          {
            f5 = localMotionPaths.time;
            f4 = f2;
            localObject1 = localObject2;
          }
        }
      }
      localObject2 = localObject1;
      f2 = f4;
      paramFloat = f5;
    }
    if (localObject2 != null)
    {
      f3 = paramFloat;
      if (Float.isNaN(paramFloat)) {
        f3 = 1.0F;
      }
      f1 = (f1 - f2) / (f3 - f2);
      paramFloat = (f3 - f2) * (float)((Easing)localObject2).get(f1) + f2;
      f3 = paramFloat;
      if (paramArrayOfFloat != null)
      {
        paramArrayOfFloat[0] = ((float)((Easing)localObject2).getDiff(f1));
        f3 = paramFloat;
      }
    }
    return f3;
  }
  
  private static DifferentialInterpolator getInterpolator(int paramInt1, String paramString, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return null;
    }
    new DifferentialInterpolator()
    {
      float mX;
      final Easing val$easing;
      
      public float getInterpolation(float paramAnonymousFloat)
      {
        this.mX = paramAnonymousFloat;
        return (float)this.val$easing.get(paramAnonymousFloat);
      }
      
      public float getVelocity()
      {
        return (float)this.val$easing.getDiff(this.mX);
      }
    };
  }
  
  private float getPreCycleDistance()
  {
    int i = 100;
    float[] arrayOfFloat = new float[2];
    float f6 = 1.0F / (100 - 1);
    float f3 = 0.0F;
    double d1 = 0.0D;
    double d2 = 0.0D;
    int j = 0;
    while (j < i)
    {
      float f7 = j * f6;
      double d3 = f7;
      Object localObject1 = this.mStartMotionPath.mKeyFrameEasing;
      Iterator localIterator = this.mMotionPaths.iterator();
      float f4 = 0.0F;
      float f1 = NaN.0F;
      float f2;
      while (localIterator.hasNext())
      {
        MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
        f2 = f1;
        Object localObject2 = localObject1;
        float f5 = f4;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f7)
          {
            localObject2 = localMotionPaths.mKeyFrameEasing;
            f5 = localMotionPaths.time;
            f2 = f1;
          }
          else
          {
            f2 = f1;
            localObject2 = localObject1;
            f5 = f4;
            if (Float.isNaN(f1))
            {
              f2 = localMotionPaths.time;
              f5 = f4;
              localObject2 = localObject1;
            }
          }
        }
        f1 = f2;
        localObject1 = localObject2;
        f4 = f5;
      }
      if (localObject1 != null)
      {
        f2 = f1;
        if (Float.isNaN(f1)) {
          f2 = 1.0F;
        }
        d3 = (f2 - f4) * (float)((Easing)localObject1).get((f7 - f4) / (f2 - f4)) + f4;
        f1 = f2;
      }
      this.mSpline[0].getPos(d3, this.mInterpolateData);
      this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
      f1 = f3;
      if (j > 0) {
        f1 = (float)(f3 + Math.hypot(d2 - arrayOfFloat[1], d1 - arrayOfFloat[0]));
      }
      d1 = arrayOfFloat[0];
      d2 = arrayOfFloat[1];
      j++;
      f3 = f1;
    }
    return f3;
  }
  
  private void insertKey(MotionPaths paramMotionPaths)
  {
    Object localObject = null;
    Iterator localIterator = this.mMotionPaths.iterator();
    while (localIterator.hasNext())
    {
      MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
      if (paramMotionPaths.position == localMotionPaths.position) {
        localObject = localMotionPaths;
      }
    }
    if (localObject != null) {
      this.mMotionPaths.remove(localObject);
    }
    int i = Collections.binarySearch(this.mMotionPaths, paramMotionPaths);
    if (i == 0) {
      Utils.loge("MotionController", " KeyPath position \"" + paramMotionPaths.position + "\" outside of range");
    }
    this.mMotionPaths.add(-i - 1, paramMotionPaths);
  }
  
  private void readView(MotionPaths paramMotionPaths)
  {
    paramMotionPaths.setBounds(this.mView.getX(), this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
  }
  
  public void addKey(MotionKey paramMotionKey)
  {
    this.mKeyList.add(paramMotionKey);
  }
  
  void addKeys(ArrayList<MotionKey> paramArrayList)
  {
    this.mKeyList.addAll(paramArrayList);
  }
  
  void buildBounds(float[] paramArrayOfFloat, int paramInt)
  {
    float f3 = 1.0F / (paramInt - 1);
    Object localObject1 = this.mAttributesMap;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (SplineSet)((HashMap)localObject1).get("translationX");
    }
    Object localObject2 = this.mAttributesMap;
    if (localObject2 != null) {
      localObject2 = (SplineSet)((HashMap)localObject2).get("translationY");
    }
    localObject2 = this.mCycleMap;
    if (localObject2 != null) {
      localObject2 = (KeyCycleOscillator)((HashMap)localObject2).get("translationX");
    }
    localObject2 = this.mCycleMap;
    if (localObject2 != null) {
      localObject2 = (KeyCycleOscillator)((HashMap)localObject2).get("translationY");
    }
    for (int i = 0; i < paramInt; i++)
    {
      float f4 = i * f3;
      float f6 = this.mStaggerScale;
      float f1 = f4;
      float f5;
      if (f6 != 1.0F)
      {
        f5 = this.mStaggerOffset;
        f2 = f4;
        if (f4 < f5) {
          f2 = 0.0F;
        }
        f1 = f2;
        if (f2 > f5)
        {
          f1 = f2;
          if (f2 < 1.0D) {
            f1 = Math.min((f2 - f5) * f6, 1.0F);
          }
        }
      }
      double d = f1;
      Object localObject3 = this.mStartMotionPath.mKeyFrameEasing;
      f4 = 0.0F;
      float f2 = NaN.0F;
      Iterator localIterator = this.mMotionPaths.iterator();
      while (localIterator.hasNext())
      {
        MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
        localObject2 = localObject3;
        f5 = f4;
        f6 = f2;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f1)
          {
            localObject2 = localMotionPaths.mKeyFrameEasing;
            f5 = localMotionPaths.time;
            f6 = f2;
          }
          else
          {
            localObject2 = localObject3;
            f5 = f4;
            f6 = f2;
            if (Float.isNaN(f2))
            {
              f6 = localMotionPaths.time;
              f5 = f4;
              localObject2 = localObject3;
            }
          }
        }
        localObject3 = localObject2;
        f4 = f5;
        f2 = f6;
      }
      if (localObject3 != null)
      {
        f5 = f2;
        if (Float.isNaN(f2)) {
          f5 = 1.0F;
        }
        d = (f5 - f4) * (float)((Easing)localObject3).get((f1 - f4) / (f5 - f4)) + f4;
      }
      this.mSpline[0].getPos(d, this.mInterpolateData);
      localObject2 = this.mArcSpline;
      if (localObject2 != null)
      {
        localObject3 = this.mInterpolateData;
        if (localObject3.length > 0) {
          ((CurveFit)localObject2).getPos(d, (double[])localObject3);
        }
      }
      this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, i * 2);
    }
  }
  
  int buildKeyBounds(float[] paramArrayOfFloat, int[] paramArrayOfInt)
  {
    if (paramArrayOfFloat != null)
    {
      int i = 0;
      double[] arrayOfDouble = this.mSpline[0].getTimePoints();
      if (paramArrayOfInt != null)
      {
        Iterator localIterator = this.mMotionPaths.iterator();
        while (localIterator.hasNext())
        {
          paramArrayOfInt[i] = ((MotionPaths)localIterator.next()).mMode;
          i++;
        }
      }
      int j = 0;
      for (i = 0; i < arrayOfDouble.length; i++)
      {
        this.mSpline[0].getPos(arrayOfDouble[i], this.mInterpolateData);
        this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, j);
        j += 2;
      }
      return j / 2;
    }
    return 0;
  }
  
  public int buildKeyFrames(float[] paramArrayOfFloat, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfFloat != null)
    {
      int i = 0;
      double[] arrayOfDouble = this.mSpline[0].getTimePoints();
      if (paramArrayOfInt1 != null)
      {
        Iterator localIterator = this.mMotionPaths.iterator();
        while (localIterator.hasNext())
        {
          paramArrayOfInt1[i] = ((MotionPaths)localIterator.next()).mMode;
          i++;
        }
      }
      i = 0;
      if (paramArrayOfInt2 != null)
      {
        paramArrayOfInt1 = this.mMotionPaths.iterator();
        while (paramArrayOfInt1.hasNext())
        {
          paramArrayOfInt2[i] = ((int)(((MotionPaths)paramArrayOfInt1.next()).position * 100.0F));
          i++;
        }
      }
      int j = 0;
      for (i = 0; i < arrayOfDouble.length; i++)
      {
        this.mSpline[0].getPos(arrayOfDouble[i], this.mInterpolateData);
        this.mStartMotionPath.getCenter(arrayOfDouble[i], this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, j);
        j += 2;
      }
      return j / 2;
    }
    return 0;
  }
  
  public void buildPath(float[] paramArrayOfFloat, int paramInt)
  {
    float f6 = 1.0F / (paramInt - 1);
    Object localObject1 = this.mAttributesMap;
    KeyCycleOscillator localKeyCycleOscillator = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (SplineSet)((HashMap)localObject1).get("translationX");
    }
    Object localObject2 = this.mAttributesMap;
    if (localObject2 == null) {
      localObject2 = null;
    } else {
      localObject2 = (SplineSet)((HashMap)localObject2).get("translationY");
    }
    Object localObject3 = this.mCycleMap;
    if (localObject3 == null) {
      localObject3 = null;
    } else {
      localObject3 = (KeyCycleOscillator)((HashMap)localObject3).get("translationX");
    }
    Object localObject4 = this.mCycleMap;
    if (localObject4 != null) {
      localKeyCycleOscillator = (KeyCycleOscillator)((HashMap)localObject4).get("translationY");
    }
    for (int i = 0; i < paramInt; i++)
    {
      float f2 = i * f6;
      float f3 = this.mStaggerScale;
      float f4;
      float f1;
      if (f3 != 1.0F)
      {
        f4 = this.mStaggerOffset;
        f1 = f2;
        if (f2 < f4) {
          f1 = 0.0F;
        }
        if ((f1 > f4) && (f1 < 1.0D)) {
          f1 = Math.min((f1 - f4) * f3, 1.0F);
        }
      }
      else
      {
        f1 = f2;
      }
      double d = f1;
      Object localObject5 = this.mStartMotionPath.mKeyFrameEasing;
      Iterator localIterator = this.mMotionPaths.iterator();
      f3 = 0.0F;
      f2 = NaN.0F;
      while (localIterator.hasNext())
      {
        MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
        f4 = f2;
        localObject4 = localObject5;
        float f5 = f3;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f1)
          {
            localObject4 = localMotionPaths.mKeyFrameEasing;
            f5 = localMotionPaths.time;
            f4 = f2;
          }
          else
          {
            f4 = f2;
            localObject4 = localObject5;
            f5 = f3;
            if (Float.isNaN(f2))
            {
              f4 = localMotionPaths.time;
              f5 = f3;
              localObject4 = localObject5;
            }
          }
        }
        f2 = f4;
        localObject5 = localObject4;
        f3 = f5;
      }
      if (localObject5 != null)
      {
        f4 = f2;
        if (Float.isNaN(f2)) {
          f4 = 1.0F;
        }
        d = (f4 - f3) * (float)((Easing)localObject5).get((f1 - f3) / (f4 - f3)) + f3;
      }
      this.mSpline[0].getPos(d, this.mInterpolateData);
      localObject5 = this.mArcSpline;
      if (localObject5 != null)
      {
        localObject4 = this.mInterpolateData;
        if (localObject4.length > 0) {
          ((CurveFit)localObject5).getPos(d, (double[])localObject4);
        }
      }
      this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, i * 2);
      int j;
      if (localObject3 != null)
      {
        j = i * 2;
        paramArrayOfFloat[j] += ((KeyCycleOscillator)localObject3).get(f1);
      }
      else if (localObject1 != null)
      {
        j = i * 2;
        paramArrayOfFloat[j] += ((SplineSet)localObject1).get(f1);
      }
      if (localKeyCycleOscillator != null)
      {
        j = i * 2 + 1;
        paramArrayOfFloat[j] += localKeyCycleOscillator.get(f1);
      }
      else if (localObject2 != null)
      {
        j = i * 2 + 1;
        paramArrayOfFloat[j] += ((SplineSet)localObject2).get(f1);
      }
    }
  }
  
  public void buildRect(float paramFloat, float[] paramArrayOfFloat, int paramInt)
  {
    paramFloat = getAdjustedPosition(paramFloat, null);
    this.mSpline[0].getPos(paramFloat, this.mInterpolateData);
    this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, paramInt);
  }
  
  void buildRectangles(float[] paramArrayOfFloat, int paramInt)
  {
    float f2 = 1.0F / (paramInt - 1);
    for (int i = 0; i < paramInt; i++)
    {
      float f1 = getAdjustedPosition(i * f2, null);
      this.mSpline[0].getPos(f1, this.mInterpolateData);
      this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, i * 8);
    }
  }
  
  void endTrigger(boolean paramBoolean) {}
  
  public int getAnimateRelativeTo()
  {
    return this.mStartMotionPath.mAnimateRelativeTo;
  }
  
  int getAttributeValues(String paramString, float[] paramArrayOfFloat, int paramInt)
  {
    float f = 1.0F / (paramInt - 1);
    paramString = (SplineSet)this.mAttributesMap.get(paramString);
    if (paramString == null) {
      return -1;
    }
    for (paramInt = 0; paramInt < paramArrayOfFloat.length; paramInt++) {
      paramArrayOfFloat[paramInt] = paramString.get(paramInt / (paramArrayOfFloat.length - 1));
    }
    return paramArrayOfFloat.length;
  }
  
  public void getCenter(double paramDouble, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    double[] arrayOfDouble2 = new double[4];
    double[] arrayOfDouble1 = new double[4];
    int[] arrayOfInt = new int[4];
    this.mSpline[0].getPos(paramDouble, arrayOfDouble2);
    this.mSpline[0].getSlope(paramDouble, arrayOfDouble1);
    Arrays.fill(paramArrayOfFloat2, 0.0F);
    this.mStartMotionPath.getCenter(paramDouble, this.mInterpolateVariables, arrayOfDouble2, paramArrayOfFloat1, arrayOfDouble1, paramArrayOfFloat2);
  }
  
  public float getCenterX()
  {
    return this.mCurrentCenterX;
  }
  
  public float getCenterY()
  {
    return this.mCurrentCenterY;
  }
  
  void getDpDt(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat)
  {
    paramFloat1 = getAdjustedPosition(paramFloat1, this.mVelocity);
    Object localObject = this.mSpline;
    if (localObject != null)
    {
      localObject[0].getSlope(paramFloat1, this.mInterpolateVelocity);
      this.mSpline[0].getPos(paramFloat1, this.mInterpolateData);
      f1 = this.mVelocity[0];
      double[] arrayOfDouble;
      for (int i = 0;; i++)
      {
        arrayOfDouble = this.mInterpolateVelocity;
        if (i >= arrayOfDouble.length) {
          break;
        }
        arrayOfDouble[i] *= f1;
      }
      localObject = this.mArcSpline;
      if (localObject != null)
      {
        arrayOfDouble = this.mInterpolateData;
        if (arrayOfDouble.length > 0)
        {
          ((CurveFit)localObject).getPos(paramFloat1, arrayOfDouble);
          this.mArcSpline.getSlope(paramFloat1, this.mInterpolateVelocity);
          this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOfFloat, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
        }
        return;
      }
      this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOfFloat, this.mInterpolateVariables, arrayOfDouble, this.mInterpolateData);
      return;
    }
    float f3 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f4 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f1 = this.mEndMotionPath.width;
    float f5 = this.mStartMotionPath.width;
    paramFloat1 = this.mEndMotionPath.height;
    float f2 = this.mStartMotionPath.height;
    paramArrayOfFloat[0] = ((1.0F - paramFloat2) * f3 + (f3 + (f1 - f5)) * paramFloat2);
    paramArrayOfFloat[1] = ((1.0F - paramFloat3) * f4 + (f4 + (paramFloat1 - f2)) * paramFloat3);
  }
  
  public int getDrawPath()
  {
    int i = this.mStartMotionPath.mDrawPath;
    Iterator localIterator = this.mMotionPaths.iterator();
    while (localIterator.hasNext()) {
      i = Math.max(i, ((MotionPaths)localIterator.next()).mDrawPath);
    }
    return Math.max(i, this.mEndMotionPath.mDrawPath);
  }
  
  public float getFinalHeight()
  {
    return this.mEndMotionPath.height;
  }
  
  public float getFinalWidth()
  {
    return this.mEndMotionPath.width;
  }
  
  public float getFinalX()
  {
    return this.mEndMotionPath.x;
  }
  
  public float getFinalY()
  {
    return this.mEndMotionPath.y;
  }
  
  public int getId(String paramString)
  {
    return 0;
  }
  
  public MotionPaths getKeyFrame(int paramInt)
  {
    return (MotionPaths)this.mMotionPaths.get(paramInt);
  }
  
  public int getKeyFrameInfo(int paramInt, int[] paramArrayOfInt)
  {
    int j = 0;
    int i = 0;
    float[] arrayOfFloat = new float[2];
    Iterator localIterator = this.mKeyList.iterator();
    for (;;)
    {
      int k = i;
      if (!localIterator.hasNext()) {
        break;
      }
      Object localObject = (MotionKey)localIterator.next();
      if ((((MotionKey)localObject).mType != paramInt) && (paramInt == -1))
      {
        i = k;
      }
      else
      {
        paramArrayOfInt[k] = 0;
        i = k + 1;
        paramArrayOfInt[i] = ((MotionKey)localObject).mType;
        i++;
        paramArrayOfInt[i] = ((MotionKey)localObject).mFramePosition;
        float f = ((MotionKey)localObject).mFramePosition / 100.0F;
        this.mSpline[0].getPos(f, this.mInterpolateData);
        this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
        i++;
        paramArrayOfInt[i] = Float.floatToIntBits(arrayOfFloat[0]);
        int m = i + 1;
        paramArrayOfInt[m] = Float.floatToIntBits(arrayOfFloat[1]);
        i = m;
        if ((localObject instanceof MotionKeyPosition))
        {
          localObject = (MotionKeyPosition)localObject;
          i = m + 1;
          paramArrayOfInt[i] = ((MotionKeyPosition)localObject).mPositionType;
          i++;
          paramArrayOfInt[i] = Float.floatToIntBits(((MotionKeyPosition)localObject).mPercentX);
          i++;
          paramArrayOfInt[i] = Float.floatToIntBits(((MotionKeyPosition)localObject).mPercentY);
        }
        i++;
        paramArrayOfInt[k] = (i - k);
        j++;
      }
    }
    return j;
  }
  
  float getKeyFrameParameter(int paramInt, float paramFloat1, float paramFloat2)
  {
    float f3 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f7 = this.mStartMotionPath.x;
    float f6 = this.mStartMotionPath.width / 2.0F;
    float f4 = this.mStartMotionPath.y;
    float f5 = this.mStartMotionPath.height / 2.0F;
    float f1 = (float)Math.hypot(f3, f2);
    if (f1 < 1.0E-7D) {
      return NaN.0F;
    }
    paramFloat1 -= f7 + f6;
    f4 = paramFloat2 - (f4 + f5);
    if ((float)Math.hypot(paramFloat1, f4) == 0.0F) {
      return 0.0F;
    }
    paramFloat2 = paramFloat1 * f3 + f4 * f2;
    switch (paramInt)
    {
    default: 
      return 0.0F;
    case 5: 
      return f4 / f2;
    case 4: 
      return paramFloat1 / f2;
    case 3: 
      return f4 / f3;
    case 2: 
      return paramFloat1 / f3;
    case 1: 
      return (float)Math.sqrt(f1 * f1 - paramFloat2 * paramFloat2);
    }
    return paramFloat2 / f1;
  }
  
  public int getKeyFramePositions(int[] paramArrayOfInt, float[] paramArrayOfFloat)
  {
    int i = 0;
    int j = 0;
    Iterator localIterator = this.mKeyList.iterator();
    while (localIterator.hasNext())
    {
      MotionKey localMotionKey = (MotionKey)localIterator.next();
      paramArrayOfInt[i] = (localMotionKey.mFramePosition + localMotionKey.mType * 1000);
      float f = localMotionKey.mFramePosition / 100.0F;
      this.mSpline[0].getPos(f, this.mInterpolateData);
      this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, j);
      j += 2;
      i++;
    }
    return i;
  }
  
  double[] getPos(double paramDouble)
  {
    this.mSpline[0].getPos(paramDouble, this.mInterpolateData);
    CurveFit localCurveFit = this.mArcSpline;
    if (localCurveFit != null)
    {
      double[] arrayOfDouble = this.mInterpolateData;
      if (arrayOfDouble.length > 0) {
        localCurveFit.getPos(paramDouble, arrayOfDouble);
      }
    }
    return this.mInterpolateData;
  }
  
  MotionKeyPosition getPositionKeyframe(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    FloatRect localFloatRect1 = new FloatRect();
    localFloatRect1.left = this.mStartMotionPath.x;
    localFloatRect1.top = this.mStartMotionPath.y;
    localFloatRect1.right = (localFloatRect1.left + this.mStartMotionPath.width);
    localFloatRect1.bottom = (localFloatRect1.top + this.mStartMotionPath.height);
    FloatRect localFloatRect2 = new FloatRect();
    localFloatRect2.left = this.mEndMotionPath.x;
    localFloatRect2.top = this.mEndMotionPath.y;
    localFloatRect2.right = (localFloatRect2.left + this.mEndMotionPath.width);
    localFloatRect2.bottom = (localFloatRect2.top + this.mEndMotionPath.height);
    Iterator localIterator = this.mKeyList.iterator();
    while (localIterator.hasNext())
    {
      MotionKey localMotionKey = (MotionKey)localIterator.next();
      if (((localMotionKey instanceof MotionKeyPosition)) && (((MotionKeyPosition)localMotionKey).intersects(paramInt1, paramInt2, localFloatRect1, localFloatRect2, paramFloat1, paramFloat2))) {
        return (MotionKeyPosition)localMotionKey;
      }
    }
    return null;
  }
  
  void getPostLayoutDvDp(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat)
  {
    float f1 = getAdjustedPosition(paramFloat1, this.mVelocity);
    Object localObject1 = this.mAttributesMap;
    KeyCycleOscillator localKeyCycleOscillator = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (SplineSet)((HashMap)localObject1).get("translationX");
    }
    Object localObject2 = this.mAttributesMap;
    if (localObject2 == null) {
      localObject2 = null;
    } else {
      localObject2 = (SplineSet)((HashMap)localObject2).get("translationY");
    }
    Object localObject3 = this.mAttributesMap;
    if (localObject3 == null) {
      localObject3 = null;
    } else {
      localObject3 = (SplineSet)((HashMap)localObject3).get("rotationZ");
    }
    Object localObject4 = this.mAttributesMap;
    if (localObject4 == null) {
      localObject4 = null;
    } else {
      localObject4 = (SplineSet)((HashMap)localObject4).get("scaleX");
    }
    Object localObject5 = this.mAttributesMap;
    if (localObject5 == null) {
      localObject5 = null;
    } else {
      localObject5 = (SplineSet)((HashMap)localObject5).get("scaleY");
    }
    Object localObject6 = this.mCycleMap;
    if (localObject6 == null) {
      localObject6 = null;
    } else {
      localObject6 = (KeyCycleOscillator)((HashMap)localObject6).get("translationX");
    }
    Object localObject7 = this.mCycleMap;
    if (localObject7 == null) {
      localObject7 = null;
    } else {
      localObject7 = (KeyCycleOscillator)((HashMap)localObject7).get("translationY");
    }
    Object localObject8 = this.mCycleMap;
    if (localObject8 == null) {
      localObject8 = null;
    } else {
      localObject8 = (KeyCycleOscillator)((HashMap)localObject8).get("rotationZ");
    }
    Object localObject9 = this.mCycleMap;
    if (localObject9 == null) {
      localObject9 = null;
    } else {
      localObject9 = (KeyCycleOscillator)((HashMap)localObject9).get("scaleX");
    }
    Object localObject10 = this.mCycleMap;
    if (localObject10 != null) {
      localKeyCycleOscillator = (KeyCycleOscillator)((HashMap)localObject10).get("scaleY");
    }
    localObject10 = new VelocityMatrix();
    ((VelocityMatrix)localObject10).clear();
    ((VelocityMatrix)localObject10).setRotationVelocity((SplineSet)localObject3, f1);
    ((VelocityMatrix)localObject10).setTranslationVelocity((SplineSet)localObject1, (SplineSet)localObject2, f1);
    ((VelocityMatrix)localObject10).setScaleVelocity((SplineSet)localObject4, (SplineSet)localObject5, f1);
    ((VelocityMatrix)localObject10).setRotationVelocity((KeyCycleOscillator)localObject8, f1);
    ((VelocityMatrix)localObject10).setTranslationVelocity((KeyCycleOscillator)localObject6, (KeyCycleOscillator)localObject7, f1);
    ((VelocityMatrix)localObject10).setScaleVelocity((KeyCycleOscillator)localObject9, localKeyCycleOscillator, f1);
    CurveFit localCurveFit = this.mArcSpline;
    if (localCurveFit != null)
    {
      localObject1 = this.mInterpolateData;
      if (localObject1.length > 0)
      {
        localCurveFit.getPos(f1, (double[])localObject1);
        this.mArcSpline.getSlope(f1, this.mInterpolateVelocity);
        this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOfFloat, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
      }
      ((VelocityMatrix)localObject10).applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOfFloat);
      return;
    }
    if (this.mSpline != null)
    {
      paramFloat1 = getAdjustedPosition(f1, this.mVelocity);
      this.mSpline[0].getSlope(paramFloat1, this.mInterpolateVelocity);
      this.mSpline[0].getPos(paramFloat1, this.mInterpolateData);
      paramFloat1 = this.mVelocity[0];
      for (int i = 0;; i++)
      {
        localObject1 = this.mInterpolateVelocity;
        if (i >= localObject1.length) {
          break;
        }
        localObject1[i] *= paramFloat1;
      }
      this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOfFloat, this.mInterpolateVariables, (double[])localObject1, this.mInterpolateData);
      ((VelocityMatrix)localObject10).applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOfFloat);
      return;
    }
    float f3 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f5 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f4 = this.mEndMotionPath.width;
    float f2 = this.mStartMotionPath.width;
    paramFloat1 = this.mEndMotionPath.height;
    float f6 = this.mStartMotionPath.height;
    paramArrayOfFloat[0] = ((1.0F - paramFloat2) * f3 + (f3 + (f4 - f2)) * paramFloat2);
    paramArrayOfFloat[1] = ((1.0F - paramFloat3) * f5 + (f5 + (paramFloat1 - f6)) * paramFloat3);
    ((VelocityMatrix)localObject10).clear();
    ((VelocityMatrix)localObject10).setRotationVelocity((SplineSet)localObject3, f1);
    ((VelocityMatrix)localObject10).setTranslationVelocity((SplineSet)localObject1, (SplineSet)localObject2, f1);
    ((VelocityMatrix)localObject10).setScaleVelocity((SplineSet)localObject4, (SplineSet)localObject5, f1);
    ((VelocityMatrix)localObject10).setRotationVelocity((KeyCycleOscillator)localObject8, f1);
    ((VelocityMatrix)localObject10).setTranslationVelocity((KeyCycleOscillator)localObject6, (KeyCycleOscillator)localObject7, f1);
    ((VelocityMatrix)localObject10).setScaleVelocity((KeyCycleOscillator)localObject9, localKeyCycleOscillator, f1);
    ((VelocityMatrix)localObject10).applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOfFloat);
  }
  
  public float getStartHeight()
  {
    return this.mStartMotionPath.height;
  }
  
  public float getStartWidth()
  {
    return this.mStartMotionPath.width;
  }
  
  public float getStartX()
  {
    return this.mStartMotionPath.x;
  }
  
  public float getStartY()
  {
    return this.mStartMotionPath.y;
  }
  
  public int getTransformPivotTarget()
  {
    return this.mTransformPivotTarget;
  }
  
  public MotionWidget getView()
  {
    return this.mView;
  }
  
  public boolean interpolate(MotionWidget paramMotionWidget, float paramFloat, long paramLong, KeyCache paramKeyCache)
  {
    paramFloat = getAdjustedPosition(paramFloat, null);
    int i = this.mQuantizeMotionSteps;
    float f2;
    float f3;
    float f1;
    if (i != -1)
    {
      f2 = 1.0F / i;
      f3 = (float)Math.floor(paramFloat / f2);
      f1 = paramFloat % f2 / f2;
      paramFloat = f1;
      if (!Float.isNaN(this.mQuantizeMotionPhase)) {
        paramFloat = (this.mQuantizeMotionPhase + f1) % 1.0F;
      }
      paramKeyCache = this.mQuantizeMotionInterpolator;
      if (paramKeyCache != null) {
        paramFloat = paramKeyCache.getInterpolation(paramFloat);
      } else if (paramFloat > 0.5D) {
        paramFloat = 1.0F;
      } else {
        paramFloat = 0.0F;
      }
      paramFloat = paramFloat * f2 + f3 * f2;
    }
    paramKeyCache = this.mAttributesMap;
    if (paramKeyCache != null)
    {
      paramKeyCache = paramKeyCache.values().iterator();
      while (paramKeyCache.hasNext()) {
        ((SplineSet)paramKeyCache.next()).setProperty(paramMotionWidget, paramFloat);
      }
    }
    paramKeyCache = this.mSpline;
    Object localObject;
    float f4;
    if (paramKeyCache != null)
    {
      paramKeyCache[0].getPos(paramFloat, this.mInterpolateData);
      this.mSpline[0].getSlope(paramFloat, this.mInterpolateVelocity);
      localObject = this.mArcSpline;
      if (localObject != null)
      {
        paramKeyCache = this.mInterpolateData;
        if (paramKeyCache.length > 0)
        {
          ((CurveFit)localObject).getPos(paramFloat, paramKeyCache);
          this.mArcSpline.getSlope(paramFloat, this.mInterpolateVelocity);
        }
      }
      if (!this.mNoMovement) {
        this.mStartMotionPath.setView(paramFloat, paramMotionWidget, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null);
      }
      if (this.mTransformPivotTarget != -1)
      {
        if (this.mTransformPivotView == null) {
          this.mTransformPivotView = paramMotionWidget.getParent().findViewById(this.mTransformPivotTarget);
        }
        paramKeyCache = this.mTransformPivotView;
        if (paramKeyCache != null)
        {
          f2 = (paramKeyCache.getTop() + this.mTransformPivotView.getBottom()) / 2.0F;
          f3 = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0F;
          if ((paramMotionWidget.getRight() - paramMotionWidget.getLeft() > 0) && (paramMotionWidget.getBottom() - paramMotionWidget.getTop() > 0))
          {
            f1 = paramMotionWidget.getLeft();
            f4 = paramMotionWidget.getTop();
            paramMotionWidget.setPivotX(f3 - f1);
            paramMotionWidget.setPivotY(f2 - f4);
          }
        }
      }
      for (i = 1;; i++)
      {
        paramKeyCache = this.mSpline;
        if (i >= paramKeyCache.length) {
          break;
        }
        paramKeyCache[i].getPos(paramFloat, this.mValuesBuff);
        ((CustomVariable)this.mStartMotionPath.customAttributes.get(this.mAttributeNames[(i - 1)])).setInterpolatedValue(paramMotionWidget, this.mValuesBuff);
      }
      if (this.mStartPoint.mVisibilityMode == 0) {
        if (paramFloat <= 0.0F) {
          paramMotionWidget.setVisibility(this.mStartPoint.visibility);
        } else if (paramFloat >= 1.0F) {
          paramMotionWidget.setVisibility(this.mEndPoint.visibility);
        } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
          paramMotionWidget.setVisibility(4);
        }
      }
      if (this.mKeyTriggers != null) {
        for (i = 0;; i++)
        {
          paramKeyCache = this.mKeyTriggers;
          if (i >= paramKeyCache.length) {
            break;
          }
          paramKeyCache[i].conditionallyFire(paramFloat, paramMotionWidget);
        }
      }
    }
    else
    {
      f4 = this.mStartMotionPath.x + (this.mEndMotionPath.x - this.mStartMotionPath.x) * paramFloat;
      f1 = this.mStartMotionPath.y + (this.mEndMotionPath.y - this.mStartMotionPath.y) * paramFloat;
      float f6 = this.mStartMotionPath.width;
      float f7 = this.mEndMotionPath.width;
      f3 = this.mStartMotionPath.width;
      float f5 = this.mStartMotionPath.height;
      float f8 = this.mEndMotionPath.height;
      f2 = this.mStartMotionPath.height;
      int k = (int)(f4 + 0.5F);
      int m = (int)(f1 + 0.5F);
      int j = (int)(f4 + 0.5F + (f6 + (f7 - f3) * paramFloat));
      i = (int)(0.5F + f1 + (f5 + (f8 - f2) * paramFloat));
      paramMotionWidget.layout(k, m, j, i);
    }
    paramKeyCache = this.mCycleMap;
    if (paramKeyCache != null)
    {
      paramKeyCache = paramKeyCache.values().iterator();
      while (paramKeyCache.hasNext())
      {
        localObject = (KeyCycleOscillator)paramKeyCache.next();
        if ((localObject instanceof KeyCycleOscillator.PathRotateSet))
        {
          localObject = (KeyCycleOscillator.PathRotateSet)localObject;
          double[] arrayOfDouble = this.mInterpolateVelocity;
          ((KeyCycleOscillator.PathRotateSet)localObject).setPathRotate(paramMotionWidget, paramFloat, arrayOfDouble[0], arrayOfDouble[1]);
        }
        else
        {
          ((KeyCycleOscillator)localObject).setProperty(paramMotionWidget, paramFloat);
        }
      }
    }
    return false;
  }
  
  String name()
  {
    return this.mView.getName();
  }
  
  void positionKeyframe(MotionWidget paramMotionWidget, MotionKeyPosition paramMotionKeyPosition, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    FloatRect localFloatRect2 = new FloatRect();
    localFloatRect2.left = this.mStartMotionPath.x;
    localFloatRect2.top = this.mStartMotionPath.y;
    localFloatRect2.right = (localFloatRect2.left + this.mStartMotionPath.width);
    localFloatRect2.bottom = (localFloatRect2.top + this.mStartMotionPath.height);
    FloatRect localFloatRect1 = new FloatRect();
    localFloatRect1.left = this.mEndMotionPath.x;
    localFloatRect1.top = this.mEndMotionPath.y;
    localFloatRect1.right = (localFloatRect1.left + this.mEndMotionPath.width);
    localFloatRect1.bottom = (localFloatRect1.top + this.mEndMotionPath.height);
    paramMotionKeyPosition.positionAttributes(paramMotionWidget, localFloatRect2, localFloatRect1, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
  }
  
  void rotate(Rect paramRect1, Rect paramRect2, int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    int j;
    switch (paramInt1)
    {
    default: 
      break;
    case 4: 
      i = paramRect1.left;
      j = paramRect1.right;
      paramInt1 = paramRect1.bottom;
      paramInt3 = paramRect1.top;
      paramRect2.left = (paramInt2 - (paramRect1.width() + (paramInt1 + paramInt3)) / 2);
      paramRect2.top = ((i + j - paramRect1.height()) / 2);
      paramRect2.right = (paramRect2.left + paramRect1.width());
      paramRect2.bottom = (paramRect2.top + paramRect1.height());
      break;
    case 3: 
      paramInt1 = paramRect1.left + paramRect1.right;
      paramInt2 = paramRect1.top;
      paramInt2 = paramRect1.bottom;
      paramRect2.left = (paramRect1.height() / 2 + paramRect1.top - paramInt1 / 2);
      paramRect2.top = (paramInt3 - (paramRect1.height() + paramInt1) / 2);
      paramRect2.right = (paramRect2.left + paramRect1.width());
      paramRect2.bottom = (paramRect2.top + paramRect1.height());
      break;
    case 2: 
      i = paramRect1.left;
      paramInt1 = paramRect1.right;
      j = paramRect1.top;
      paramInt3 = paramRect1.bottom;
      paramRect2.left = (paramInt2 - (paramRect1.width() + (j + paramInt3)) / 2);
      paramRect2.top = ((i + paramInt1 - paramRect1.height()) / 2);
      paramRect2.right = (paramRect2.left + paramRect1.width());
      paramRect2.bottom = (paramRect2.top + paramRect1.height());
      break;
    case 1: 
      paramInt1 = paramRect1.left;
      paramInt2 = paramRect1.right;
      paramRect2.left = ((paramRect1.top + paramRect1.bottom - paramRect1.width()) / 2);
      paramRect2.top = (paramInt3 - (paramRect1.height() + (paramInt1 + paramInt2)) / 2);
      paramRect2.right = (paramRect2.left + paramRect1.width());
      paramRect2.bottom = (paramRect2.top + paramRect1.height());
    }
  }
  
  void setBothStates(MotionWidget paramMotionWidget)
  {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    this.mNoMovement = true;
    this.mStartMotionPath.setBounds(paramMotionWidget.getX(), paramMotionWidget.getY(), paramMotionWidget.getWidth(), paramMotionWidget.getHeight());
    this.mEndMotionPath.setBounds(paramMotionWidget.getX(), paramMotionWidget.getY(), paramMotionWidget.getWidth(), paramMotionWidget.getHeight());
    this.mStartPoint.setState(paramMotionWidget);
    this.mEndPoint.setState(paramMotionWidget);
  }
  
  public void setDrawPath(int paramInt)
  {
    this.mStartMotionPath.mDrawPath = paramInt;
  }
  
  public void setEnd(MotionWidget paramMotionWidget)
  {
    this.mEndMotionPath.time = 1.0F;
    this.mEndMotionPath.position = 1.0F;
    readView(this.mEndMotionPath);
    this.mEndMotionPath.setBounds(paramMotionWidget.getLeft(), paramMotionWidget.getTop(), paramMotionWidget.getWidth(), paramMotionWidget.getHeight());
    this.mEndMotionPath.applyParameters(paramMotionWidget);
    this.mEndPoint.setState(paramMotionWidget);
  }
  
  public void setPathMotionArc(int paramInt)
  {
    this.mPathMotionArc = paramInt;
  }
  
  public void setStart(MotionWidget paramMotionWidget)
  {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    this.mStartMotionPath.setBounds(paramMotionWidget.getX(), paramMotionWidget.getY(), paramMotionWidget.getWidth(), paramMotionWidget.getHeight());
    this.mStartMotionPath.applyParameters(paramMotionWidget);
    this.mStartPoint.setState(paramMotionWidget);
  }
  
  public void setStartState(ViewState paramViewState, MotionWidget paramMotionWidget, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    Rect localRect = new Rect();
    int i;
    switch (paramInt1)
    {
    default: 
      break;
    case 2: 
      int k = paramViewState.left;
      paramInt2 = paramViewState.right;
      i = paramViewState.top;
      int j = paramViewState.bottom;
      localRect.left = (paramInt3 - (paramViewState.width() + (i + j)) / 2);
      localRect.top = ((k + paramInt2 - paramViewState.height()) / 2);
      localRect.right = (localRect.left + paramViewState.width());
      localRect.bottom = (localRect.top + paramViewState.height());
      break;
    case 1: 
      paramInt3 = paramViewState.left;
      i = paramViewState.right;
      localRect.left = ((paramViewState.top + paramViewState.bottom - paramViewState.width()) / 2);
      localRect.top = (paramInt2 - (paramViewState.height() + (paramInt3 + i)) / 2);
      localRect.right = (localRect.left + paramViewState.width());
      localRect.bottom = (localRect.top + paramViewState.height());
    }
    this.mStartMotionPath.setBounds(localRect.left, localRect.top, localRect.width(), localRect.height());
    this.mStartPoint.setState(localRect, paramMotionWidget, paramInt1, paramViewState.rotation);
  }
  
  public void setTransformPivotTarget(int paramInt)
  {
    this.mTransformPivotTarget = paramInt;
    this.mTransformPivotView = null;
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    return false;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return false;
    case 704: 
      return true;
    }
    setPathMotionArc(paramInt2);
    return true;
  }
  
  public boolean setValue(int paramInt, String paramString)
  {
    if (705 == paramInt)
    {
      System.out.println("TYPE_INTERPOLATOR  " + paramString);
      this.mQuantizeMotionInterpolator = getInterpolator(-1, paramString, 0);
    }
    return false;
  }
  
  public boolean setValue(int paramInt, boolean paramBoolean)
  {
    return false;
  }
  
  public void setView(MotionWidget paramMotionWidget)
  {
    this.mView = paramMotionWidget;
  }
  
  public void setup(int paramInt1, int paramInt2, float paramFloat, long paramLong)
  {
    Object localObject3 = new HashSet();
    Object localObject4 = new HashSet();
    Object localObject5 = new HashSet();
    HashSet localHashSet = new HashSet();
    HashMap localHashMap = new HashMap();
    Object localObject2 = null;
    Object localObject1 = null;
    int i = this.mPathMotionArc;
    if (i != -1) {
      this.mStartMotionPath.mPathMotionArc = i;
    }
    this.mStartPoint.different(this.mEndPoint, (HashSet)localObject5);
    Object localObject6 = this.mKeyList;
    if (localObject6 != null)
    {
      localObject6 = ((ArrayList)localObject6).iterator();
      for (;;)
      {
        localObject2 = localObject1;
        if (!((Iterator)localObject6).hasNext()) {
          break;
        }
        localObject7 = (MotionKey)((Iterator)localObject6).next();
        if ((localObject7 instanceof MotionKeyPosition))
        {
          localObject2 = (MotionKeyPosition)localObject7;
          insertKey(new MotionPaths(paramInt1, paramInt2, (MotionKeyPosition)localObject2, this.mStartMotionPath, this.mEndMotionPath));
          if (((MotionKeyPosition)localObject2).mCurveFit != -1) {
            this.mCurveFitType = ((MotionKeyPosition)localObject2).mCurveFit;
          }
        }
        else if ((localObject7 instanceof MotionKeyCycle))
        {
          ((MotionKey)localObject7).getAttributeNames(localHashSet);
        }
        else if ((localObject7 instanceof MotionKeyTimeCycle))
        {
          ((MotionKey)localObject7).getAttributeNames((HashSet)localObject4);
        }
        else if ((localObject7 instanceof MotionKeyTrigger))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add((MotionKeyTrigger)localObject7);
          localObject1 = localObject2;
        }
        else
        {
          ((MotionKey)localObject7).setInterpolation(localHashMap);
          ((MotionKey)localObject7).getAttributeNames((HashSet)localObject5);
        }
      }
    }
    if (localObject2 != null) {
      this.mKeyTriggers = ((MotionKeyTrigger[])((ArrayList)localObject2).toArray(new MotionKeyTrigger[0]));
    }
    if (!((HashSet)localObject5).isEmpty())
    {
      this.mAttributesMap = new HashMap();
      localObject6 = ((HashSet)localObject5).iterator();
      localObject1 = localObject3;
      while (((Iterator)localObject6).hasNext())
      {
        localObject7 = (String)((Iterator)localObject6).next();
        if (((String)localObject7).startsWith("CUSTOM,"))
        {
          localObject8 = new KeyFrameArray.CustomVar();
          localObject3 = localObject7.split(",")[1];
          localObject9 = this.mKeyList.iterator();
          while (((Iterator)localObject9).hasNext())
          {
            MotionKey localMotionKey = (MotionKey)((Iterator)localObject9).next();
            if (localMotionKey.mCustom != null)
            {
              CustomVariable localCustomVariable = (CustomVariable)localMotionKey.mCustom.get(localObject3);
              if (localCustomVariable != null) {
                ((KeyFrameArray.CustomVar)localObject8).append(localMotionKey.mFramePosition, localCustomVariable);
              }
            }
          }
          localObject3 = SplineSet.makeCustomSplineSet((String)localObject7, (KeyFrameArray.CustomVar)localObject8);
        }
        else
        {
          localObject3 = SplineSet.makeSpline((String)localObject7, paramLong);
        }
        if (localObject3 != null)
        {
          ((SplineSet)localObject3).setType((String)localObject7);
          this.mAttributesMap.put(localObject7, localObject3);
        }
      }
      localObject1 = this.mKeyList;
      if (localObject1 != null)
      {
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (MotionKey)((Iterator)localObject1).next();
          if ((localObject2 instanceof MotionKeyAttributes)) {
            ((MotionKey)localObject2).addValues(this.mAttributesMap);
          }
        }
      }
      this.mStartPoint.addValues(this.mAttributesMap, 0);
      this.mEndPoint.addValues(this.mAttributesMap, 100);
      localObject1 = this.mAttributesMap.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (String)((Iterator)localObject1).next();
        paramInt2 = 0;
        paramInt1 = paramInt2;
        if (localHashMap.containsKey(localObject2))
        {
          localObject3 = (Integer)localHashMap.get(localObject2);
          paramInt1 = paramInt2;
          if (localObject3 != null) {
            paramInt1 = ((Integer)localObject3).intValue();
          }
        }
        localObject2 = (SplineSet)this.mAttributesMap.get(localObject2);
        if (localObject2 != null) {
          ((SplineSet)localObject2).setup(paramInt1);
        }
      }
    }
    if (!((HashSet)localObject4).isEmpty())
    {
      if (this.mTimeCycleAttributesMap == null) {
        this.mTimeCycleAttributesMap = new HashMap();
      }
      localObject1 = ((HashSet)localObject4).iterator();
      localObject2 = localObject4;
      while (((Iterator)localObject1).hasNext())
      {
        localObject6 = (String)((Iterator)localObject1).next();
        if (!this.mTimeCycleAttributesMap.containsKey(localObject6))
        {
          if (((String)localObject6).startsWith("CUSTOM,"))
          {
            localObject4 = new KeyFrameArray.CustomVar();
            localObject3 = localObject6.split(",")[1];
            localObject7 = this.mKeyList.iterator();
            while (((Iterator)localObject7).hasNext())
            {
              localObject8 = (MotionKey)((Iterator)localObject7).next();
              if (((MotionKey)localObject8).mCustom != null)
              {
                localObject9 = (CustomVariable)((MotionKey)localObject8).mCustom.get(localObject3);
                if (localObject9 != null) {
                  ((KeyFrameArray.CustomVar)localObject4).append(((MotionKey)localObject8).mFramePosition, (CustomVariable)localObject9);
                }
              }
            }
            localObject3 = localObject2;
            localObject4 = SplineSet.makeCustomSplineSet((String)localObject6, (KeyFrameArray.CustomVar)localObject4);
            localObject2 = localObject1;
            localObject1 = localObject3;
          }
          else
          {
            localObject3 = localObject1;
            localObject1 = localObject2;
            localObject4 = SplineSet.makeSpline((String)localObject6, paramLong);
            localObject2 = localObject3;
          }
          if (localObject4 == null)
          {
            localObject3 = localObject2;
            localObject2 = localObject1;
            localObject1 = localObject3;
          }
          else
          {
            ((SplineSet)localObject4).setType((String)localObject6);
            localObject3 = localObject1;
            localObject1 = localObject2;
            localObject2 = localObject3;
          }
        }
      }
      localObject1 = this.mKeyList;
      if (localObject1 != null)
      {
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (MotionKey)((Iterator)localObject1).next();
          if ((localObject2 instanceof MotionKeyTimeCycle)) {
            ((MotionKeyTimeCycle)localObject2).addTimeValues(this.mTimeCycleAttributesMap);
          }
        }
      }
      localObject2 = this.mTimeCycleAttributesMap.keySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (String)((Iterator)localObject2).next();
        paramInt1 = 0;
        if (localHashMap.containsKey(localObject1)) {
          paramInt1 = ((Integer)localHashMap.get(localObject1)).intValue();
        }
        ((TimeCycleSplineSet)this.mTimeCycleAttributesMap.get(localObject1)).setup(paramInt1);
      }
    }
    Object localObject7 = new MotionPaths[this.mMotionPaths.size() + 2];
    paramInt1 = 1;
    localObject7[0] = this.mStartMotionPath;
    localObject7[(localObject7.length - 1)] = this.mEndMotionPath;
    if ((this.mMotionPaths.size() > 0) && (this.mCurveFitType == MotionKey.UNSET)) {
      this.mCurveFitType = 0;
    }
    localObject1 = this.mMotionPaths.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject7[paramInt1] = ((MotionPaths)((Iterator)localObject1).next());
      paramInt1++;
    }
    int j = 18;
    localObject4 = new HashSet();
    localObject1 = this.mEndMotionPath.customAttributes.keySet().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      if ((this.mStartMotionPath.customAttributes.containsKey(localObject2)) && (!((HashSet)localObject5).contains("CUSTOM," + (String)localObject2))) {
        ((HashSet)localObject4).add(localObject2);
      }
    }
    localObject1 = (String[])((HashSet)localObject4).toArray(new String[0]);
    this.mAttributeNames = ((String[])localObject1);
    this.mAttributeInterpolatorCount = new int[localObject1.length];
    for (paramInt1 = 0;; paramInt1++)
    {
      localObject1 = this.mAttributeNames;
      if (paramInt1 >= localObject1.length) {
        break;
      }
      localObject2 = localObject1[paramInt1];
      this.mAttributeInterpolatorCount[paramInt1] = 0;
      for (paramInt2 = 0; paramInt2 < localObject7.length; paramInt2++) {
        if (localObject7[paramInt2].customAttributes.containsKey(localObject2))
        {
          localObject1 = (CustomVariable)localObject7[paramInt2].customAttributes.get(localObject2);
          if (localObject1 != null)
          {
            localObject2 = this.mAttributeInterpolatorCount;
            localObject2[paramInt1] += ((CustomVariable)localObject1).numberOfInterpolatedValues();
            break;
          }
        }
      }
    }
    boolean bool;
    if (localObject7[0].mPathMotionArc != -1) {
      bool = true;
    } else {
      bool = false;
    }
    localObject6 = new boolean[this.mAttributeNames.length + 18];
    for (paramInt1 = 1; paramInt1 < localObject7.length; paramInt1++) {
      localObject7[paramInt1].different(localObject7[(paramInt1 - 1)], (boolean[])localObject6, this.mAttributeNames, bool);
    }
    paramInt2 = 0;
    paramInt1 = 1;
    while (paramInt1 < localObject6.length)
    {
      i = paramInt2;
      if (localObject6[paramInt1] != 0) {
        i = paramInt2 + 1;
      }
      paramInt1++;
      paramInt2 = i;
    }
    this.mInterpolateVariables = new int[paramInt2];
    paramInt1 = Math.max(2, paramInt2);
    this.mInterpolateData = new double[paramInt1];
    this.mInterpolateVelocity = new double[paramInt1];
    paramInt1 = 0;
    paramInt2 = 1;
    while (paramInt2 < localObject6.length)
    {
      i = paramInt1;
      if (localObject6[paramInt2] != 0)
      {
        this.mInterpolateVariables[paramInt1] = paramInt2;
        i = paramInt1 + 1;
      }
      paramInt2++;
      paramInt1 = i;
    }
    i = localObject7.length;
    paramInt2 = this.mInterpolateVariables.length;
    Object localObject8 = new double[i][paramInt2];
    Object localObject9 = new double[localObject7.length];
    for (paramInt2 = 0; paramInt2 < localObject7.length; paramInt2++)
    {
      localObject7[paramInt2].fillStandard(localObject8[paramInt2], this.mInterpolateVariables);
      localObject9[paramInt2] = localObject7[paramInt2].time;
    }
    paramInt1 = 0;
    localObject1 = localHashMap;
    localObject2 = localObject5;
    for (;;)
    {
      localObject3 = this.mInterpolateVariables;
      if (paramInt1 >= localObject3.length) {
        break;
      }
      paramInt2 = localObject3[paramInt1];
      if (paramInt2 < MotionPaths.names.length)
      {
        localObject3 = MotionPaths.names[this.mInterpolateVariables[paramInt1]] + " [";
        for (i = 0; i < localObject7.length; i++) {
          localObject3 = (String)localObject3 + localObject8[i][paramInt1];
        }
      }
      paramInt1++;
    }
    this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
    paramInt2 = 0;
    localObject1 = localObject6;
    localObject2 = localObject4;
    paramInt1 = j;
    for (;;)
    {
      localObject5 = this.mAttributeNames;
      if (paramInt2 >= localObject5.length) {
        break;
      }
      j = 0;
      localObject3 = (double[][])null;
      localObject4 = null;
      localObject5 = localObject5[paramInt2];
      for (i = 0; i < localObject7.length; i++) {
        if (localObject7[i].hasCustomData((String)localObject5))
        {
          if (localObject3 == null)
          {
            localObject4 = new double[localObject7.length];
            int k = localObject7.length;
            int m = localObject7[i].getCustomDataCount((String)localObject5);
            localObject3 = new double[k][m];
          }
          localObject4[j] = localObject7[i].time;
          localObject7[i].getCustomData((String)localObject5, localObject3[j], 0);
          j++;
        }
      }
      localObject4 = Arrays.copyOf((double[])localObject4, j);
      localObject3 = (double[][])Arrays.copyOf((Object[])localObject3, j);
      this.mSpline[(paramInt2 + 1)] = CurveFit.get(this.mCurveFitType, (double[])localObject4, (double[][])localObject3);
      paramInt2++;
    }
    this.mSpline[0] = CurveFit.get(this.mCurveFitType, (double[])localObject9, (double[][])localObject8);
    if (localObject7[0].mPathMotionArc != -1)
    {
      paramInt2 = localObject7.length;
      localObject3 = new int[paramInt2];
      localObject1 = new double[paramInt2];
      localObject2 = new double[paramInt2][2];
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      {
        localObject3[paramInt1] = localObject7[paramInt1].mPathMotionArc;
        localObject1[paramInt1] = localObject7[paramInt1].time;
        localObject2[paramInt1][0] = localObject7[paramInt1].x;
        localObject2[paramInt1][1] = localObject7[paramInt1].y;
      }
      this.mArcSpline = CurveFit.getArc((int[])localObject3, (double[])localObject1, (double[][])localObject2);
    }
    paramFloat = NaN.0F;
    this.mCycleMap = new HashMap();
    if (this.mKeyList != null)
    {
      localObject3 = localHashSet.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (String)((Iterator)localObject3).next();
        localObject1 = KeyCycleOscillator.makeWidgetCycle((String)localObject2);
        if (localObject1 != null)
        {
          float f = paramFloat;
          if (((KeyCycleOscillator)localObject1).variesByPath())
          {
            f = paramFloat;
            if (Float.isNaN(paramFloat)) {
              f = getPreCycleDistance();
            }
          }
          ((KeyCycleOscillator)localObject1).setType((String)localObject2);
          this.mCycleMap.put(localObject2, localObject1);
          paramFloat = f;
        }
      }
      localObject2 = this.mKeyList.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (MotionKey)((Iterator)localObject2).next();
        if ((localObject1 instanceof MotionKeyCycle)) {
          ((MotionKeyCycle)localObject1).addCycleValues(this.mCycleMap);
        }
      }
      localObject1 = this.mCycleMap.values().iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((KeyCycleOscillator)((Iterator)localObject1).next()).setup(paramFloat);
      }
    }
  }
  
  public void setupRelative(Motion paramMotion)
  {
    this.mStartMotionPath.setupRelative(paramMotion, paramMotion.mStartMotionPath);
    this.mEndMotionPath.setupRelative(paramMotion, paramMotion.mEndMotionPath);
  }
  
  public String toString()
  {
    return " start: x: " + this.mStartMotionPath.x + " y: " + this.mStartMotionPath.y + " end: x: " + this.mEndMotionPath.x + " y: " + this.mEndMotionPath.y;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/Motion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */