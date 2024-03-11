package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.CustomSupport;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewOscillator.PathRotateSet;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewSpline.PathRotate;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.motion.utils.ViewTimeCycle.PathRotate;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintSet.Constraint;
import androidx.constraintlayout.widget.ConstraintSet.Motion;
import androidx.constraintlayout.widget.ConstraintSet.Transform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionController
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
  private HashMap<String, ViewSpline> mAttributesMap;
  String mConstraintTag;
  float mCurrentCenterX;
  float mCurrentCenterY;
  private int mCurveFitType = -1;
  private HashMap<String, ViewOscillator> mCycleMap;
  private MotionPaths mEndMotionPath = new MotionPaths();
  private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
  boolean mForceMeasure = false;
  int mId;
  private double[] mInterpolateData;
  private int[] mInterpolateVariables;
  private double[] mInterpolateVelocity;
  private ArrayList<Key> mKeyList = new ArrayList();
  private KeyTrigger[] mKeyTriggers;
  private ArrayList<MotionPaths> mMotionPaths = new ArrayList();
  float mMotionStagger = NaN.0F;
  private boolean mNoMovement = false;
  private int mPathMotionArc = Key.UNSET;
  private Interpolator mQuantizeMotionInterpolator = null;
  private float mQuantizeMotionPhase = NaN.0F;
  private int mQuantizeMotionSteps = Key.UNSET;
  private CurveFit[] mSpline;
  float mStaggerOffset = 0.0F;
  float mStaggerScale = 1.0F;
  private MotionPaths mStartMotionPath = new MotionPaths();
  private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
  Rect mTempRect = new Rect();
  private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
  private int mTransformPivotTarget = Key.UNSET;
  private View mTransformPivotView = null;
  private float[] mValuesBuff = new float[4];
  private float[] mVelocity = new float[1];
  View mView;
  
  MotionController(View paramView)
  {
    setView(paramView);
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
  
  private static Interpolator getInterpolator(Context paramContext, int paramInt1, String paramString, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return null;
    case 5: 
      return new OvershootInterpolator();
    case 4: 
      return new BounceInterpolator();
    case 3: 
      return null;
    case 2: 
      return new DecelerateInterpolator();
    case 1: 
      return new AccelerateInterpolator();
    case 0: 
      return new AccelerateDecelerateInterpolator();
    case -1: 
      new Interpolator()
      {
        public float getInterpolation(float paramAnonymousFloat)
        {
          return (float)MotionController.this.get(paramAnonymousFloat);
        }
      };
    }
    return AnimationUtils.loadInterpolator(paramContext, paramInt2);
  }
  
  private float getPreCycleDistance()
  {
    int j = 100;
    float[] arrayOfFloat = new float[2];
    float f6 = 1.0F / (100 - 1);
    float f3 = 0.0F;
    double d2 = 0.0D;
    double d1 = 0.0D;
    int i = 0;
    while (i < j)
    {
      float f7 = i * f6;
      double d3 = f7;
      Object localObject2 = this.mStartMotionPath.mKeyFrameEasing;
      Iterator localIterator = this.mMotionPaths.iterator();
      float f4 = 0.0F;
      float f1 = NaN.0F;
      float f2;
      while (localIterator.hasNext())
      {
        MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
        float f5 = f1;
        Object localObject1 = localObject2;
        f2 = f4;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f7)
          {
            localObject1 = localMotionPaths.mKeyFrameEasing;
            f2 = localMotionPaths.time;
            f5 = f1;
          }
          else
          {
            f5 = f1;
            localObject1 = localObject2;
            f2 = f4;
            if (Float.isNaN(f1))
            {
              f5 = localMotionPaths.time;
              f2 = f4;
              localObject1 = localObject2;
            }
          }
        }
        f1 = f5;
        localObject2 = localObject1;
        f4 = f2;
      }
      if (localObject2 != null)
      {
        f2 = f1;
        if (Float.isNaN(f1)) {
          f2 = 1.0F;
        }
        d3 = (f2 - f4) * (float)((Easing)localObject2).get((f7 - f4) / (f2 - f4)) + f4;
        f1 = f2;
      }
      this.mSpline[0].getPos(d3, this.mInterpolateData);
      this.mStartMotionPath.getCenter(d3, this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
      f1 = f3;
      if (i > 0) {
        f1 = (float)(f3 + Math.hypot(d1 - arrayOfFloat[1], d2 - arrayOfFloat[0]));
      }
      d2 = arrayOfFloat[0];
      d1 = arrayOfFloat[1];
      i++;
      f3 = f1;
    }
    return f3;
  }
  
  private void insertKey(MotionPaths paramMotionPaths)
  {
    int i = Collections.binarySearch(this.mMotionPaths, paramMotionPaths);
    if (i == 0) {
      Log.e("MotionController", " KeyPath position \"" + paramMotionPaths.position + "\" outside of range");
    }
    this.mMotionPaths.add(-i - 1, paramMotionPaths);
  }
  
  private void readView(MotionPaths paramMotionPaths)
  {
    paramMotionPaths.setBounds((int)this.mView.getX(), (int)this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
  }
  
  public void addKey(Key paramKey)
  {
    this.mKeyList.add(paramKey);
  }
  
  void addKeys(ArrayList<Key> paramArrayList)
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
      localObject2 = (ViewOscillator)((HashMap)localObject2).get("translationX");
    }
    localObject2 = this.mCycleMap;
    if (localObject2 != null) {
      localObject2 = (ViewOscillator)((HashMap)localObject2).get("translationY");
    }
    for (int i = 0; i < paramInt; i++)
    {
      float f4 = i * f3;
      float f5 = this.mStaggerScale;
      float f1 = f4;
      float f6;
      if (f5 != 1.0F)
      {
        f6 = this.mStaggerOffset;
        f2 = f4;
        if (f4 < f6) {
          f2 = 0.0F;
        }
        f1 = f2;
        if (f2 > f6)
        {
          f1 = f2;
          if (f2 < 1.0D) {
            f1 = Math.min((f2 - f6) * f5, 1.0F);
          }
        }
      }
      double d = f1;
      localObject2 = this.mStartMotionPath.mKeyFrameEasing;
      f4 = 0.0F;
      float f2 = NaN.0F;
      Iterator localIterator = this.mMotionPaths.iterator();
      while (localIterator.hasNext())
      {
        MotionPaths localMotionPaths = (MotionPaths)localIterator.next();
        localObject3 = localObject2;
        f6 = f4;
        f5 = f2;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f1)
          {
            localObject3 = localMotionPaths.mKeyFrameEasing;
            f6 = localMotionPaths.time;
            f5 = f2;
          }
          else
          {
            localObject3 = localObject2;
            f6 = f4;
            f5 = f2;
            if (Float.isNaN(f2))
            {
              f5 = localMotionPaths.time;
              f6 = f4;
              localObject3 = localObject2;
            }
          }
        }
        localObject2 = localObject3;
        f4 = f6;
        f2 = f5;
      }
      if (localObject2 != null)
      {
        f5 = f2;
        if (Float.isNaN(f2)) {
          f5 = 1.0F;
        }
        d = (f5 - f4) * (float)((Easing)localObject2).get((f1 - f4) / (f5 - f4)) + f4;
      }
      this.mSpline[0].getPos(d, this.mInterpolateData);
      Object localObject3 = this.mArcSpline;
      if (localObject3 != null)
      {
        localObject2 = this.mInterpolateData;
        if (localObject2.length > 0) {
          ((CurveFit)localObject3).getPos(d, (double[])localObject2);
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
  
  int buildKeyFrames(float[] paramArrayOfFloat, int[] paramArrayOfInt)
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
        this.mStartMotionPath.getCenter(arrayOfDouble[i], this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, j);
        j += 2;
      }
      return j / 2;
    }
    return 0;
  }
  
  void buildPath(float[] paramArrayOfFloat, int paramInt)
  {
    float f6 = 1.0F / (paramInt - 1);
    Object localObject1 = this.mAttributesMap;
    ViewOscillator localViewOscillator = null;
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
      localObject3 = (ViewOscillator)((HashMap)localObject3).get("translationX");
    }
    Object localObject4 = this.mCycleMap;
    if (localObject4 != null) {
      localViewOscillator = (ViewOscillator)((HashMap)localObject4).get("translationY");
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
        float f5 = f2;
        localObject4 = localObject5;
        f4 = f3;
        if (localMotionPaths.mKeyFrameEasing != null) {
          if (localMotionPaths.time < f1)
          {
            localObject4 = localMotionPaths.mKeyFrameEasing;
            f4 = localMotionPaths.time;
            f5 = f2;
          }
          else
          {
            f5 = f2;
            localObject4 = localObject5;
            f4 = f3;
            if (Float.isNaN(f2))
            {
              f5 = localMotionPaths.time;
              f4 = f3;
              localObject4 = localObject5;
            }
          }
        }
        f2 = f5;
        localObject5 = localObject4;
        f3 = f4;
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
      localObject4 = this.mArcSpline;
      if (localObject4 != null)
      {
        localObject5 = this.mInterpolateData;
        if (localObject5.length > 0) {
          ((CurveFit)localObject4).getPos(d, (double[])localObject5);
        }
      }
      this.mStartMotionPath.getCenter(d, this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, i * 2);
      int j;
      if (localObject3 != null)
      {
        j = i * 2;
        paramArrayOfFloat[j] += ((ViewOscillator)localObject3).get(f1);
      }
      else if (localObject1 != null)
      {
        j = i * 2;
        paramArrayOfFloat[j] += ((SplineSet)localObject1).get(f1);
      }
      if (localViewOscillator != null)
      {
        j = i * 2 + 1;
        paramArrayOfFloat[j] += localViewOscillator.get(f1);
      }
      else if (localObject2 != null)
      {
        j = i * 2 + 1;
        paramArrayOfFloat[j] += ((SplineSet)localObject2).get(f1);
      }
    }
  }
  
  void buildRect(float paramFloat, float[] paramArrayOfFloat, int paramInt)
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
  
  void endTrigger(boolean paramBoolean)
  {
    Object localObject = Debug.getName(this.mView);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (("button".equals(localObject)) && (this.mKeyTriggers != null)) {
      for (int i = 0;; i++)
      {
        localObject = this.mKeyTriggers;
        if (i >= localObject.length) {
          break;
        }
        localObject = localObject[i];
        float f;
        if (paramBoolean) {
          f = -100.0F;
        } else {
          f = 100.0F;
        }
        ((KeyTrigger)localObject).conditionallyFire(f, this.mView);
      }
    }
  }
  
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
    double[] arrayOfDouble1 = new double[4];
    double[] arrayOfDouble2 = new double[4];
    int[] arrayOfInt = new int[4];
    this.mSpline[0].getPos(paramDouble, arrayOfDouble1);
    this.mSpline[0].getSlope(paramDouble, arrayOfDouble2);
    Arrays.fill(paramArrayOfFloat2, 0.0F);
    this.mStartMotionPath.getCenter(paramDouble, this.mInterpolateVariables, arrayOfDouble1, paramArrayOfFloat1, arrayOfDouble2, paramArrayOfFloat2);
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
    float f2 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f4 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    paramFloat1 = this.mEndMotionPath.width;
    float f3 = this.mStartMotionPath.width;
    float f1 = this.mEndMotionPath.height;
    float f5 = this.mStartMotionPath.height;
    paramArrayOfFloat[0] = ((1.0F - paramFloat2) * f2 + (f2 + (paramFloat1 - f3)) * paramFloat2);
    paramArrayOfFloat[1] = ((1.0F - paramFloat3) * f4 + (f4 + (f1 - f5)) * paramFloat3);
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
  
  MotionPaths getKeyFrame(int paramInt)
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
      Object localObject = (Key)localIterator.next();
      if ((((Key)localObject).mType != paramInt) && (paramInt == -1))
      {
        i = k;
      }
      else
      {
        paramArrayOfInt[k] = 0;
        i = k + 1;
        paramArrayOfInt[i] = ((Key)localObject).mType;
        i++;
        paramArrayOfInt[i] = ((Key)localObject).mFramePosition;
        float f = ((Key)localObject).mFramePosition / 100.0F;
        this.mSpline[0].getPos(f, this.mInterpolateData);
        this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, arrayOfFloat, 0);
        i++;
        paramArrayOfInt[i] = Float.floatToIntBits(arrayOfFloat[0]);
        int m = i + 1;
        paramArrayOfInt[m] = Float.floatToIntBits(arrayOfFloat[1]);
        i = m;
        if ((localObject instanceof KeyPosition))
        {
          localObject = (KeyPosition)localObject;
          i = m + 1;
          paramArrayOfInt[i] = ((KeyPosition)localObject).mPositionType;
          i++;
          paramArrayOfInt[i] = Float.floatToIntBits(((KeyPosition)localObject).mPercentX);
          i++;
          paramArrayOfInt[i] = Float.floatToIntBits(((KeyPosition)localObject).mPercentY);
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
    float f1 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f6 = this.mStartMotionPath.x;
    float f7 = this.mStartMotionPath.width / 2.0F;
    float f4 = this.mStartMotionPath.y;
    float f5 = this.mStartMotionPath.height / 2.0F;
    float f3 = (float)Math.hypot(f1, f2);
    if (f3 < 1.0E-7D) {
      return NaN.0F;
    }
    paramFloat1 -= f6 + f7;
    f4 = paramFloat2 - (f4 + f5);
    if ((float)Math.hypot(paramFloat1, f4) == 0.0F) {
      return 0.0F;
    }
    paramFloat2 = paramFloat1 * f1 + f4 * f2;
    switch (paramInt)
    {
    default: 
      return 0.0F;
    case 5: 
      return f4 / f2;
    case 4: 
      return paramFloat1 / f2;
    case 3: 
      return f4 / f1;
    case 2: 
      return paramFloat1 / f1;
    case 1: 
      return (float)Math.sqrt(f3 * f3 - paramFloat2 * paramFloat2);
    }
    return paramFloat2 / f3;
  }
  
  public int getKeyFramePositions(int[] paramArrayOfInt, float[] paramArrayOfFloat)
  {
    int j = 0;
    int i = 0;
    Iterator localIterator = this.mKeyList.iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)localIterator.next();
      paramArrayOfInt[j] = (localKey.mFramePosition + localKey.mType * 1000);
      float f = localKey.mFramePosition / 100.0F;
      this.mSpline[0].getPos(f, this.mInterpolateData);
      this.mStartMotionPath.getCenter(f, this.mInterpolateVariables, this.mInterpolateData, paramArrayOfFloat, i);
      i += 2;
      j++;
    }
    return j;
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
  
  KeyPositionBase getPositionKeyframe(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    RectF localRectF2 = new RectF();
    localRectF2.left = this.mStartMotionPath.x;
    localRectF2.top = this.mStartMotionPath.y;
    localRectF2.right = (localRectF2.left + this.mStartMotionPath.width);
    localRectF2.bottom = (localRectF2.top + this.mStartMotionPath.height);
    RectF localRectF1 = new RectF();
    localRectF1.left = this.mEndMotionPath.x;
    localRectF1.top = this.mEndMotionPath.y;
    localRectF1.right = (localRectF1.left + this.mEndMotionPath.width);
    localRectF1.bottom = (localRectF1.top + this.mEndMotionPath.height);
    Iterator localIterator = this.mKeyList.iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)localIterator.next();
      if (((localKey instanceof KeyPositionBase)) && (((KeyPositionBase)localKey).intersects(paramInt1, paramInt2, localRectF2, localRectF1, paramFloat1, paramFloat2))) {
        return (KeyPositionBase)localKey;
      }
    }
    return null;
  }
  
  void getPostLayoutDvDp(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat)
  {
    float f6 = getAdjustedPosition(paramFloat1, this.mVelocity);
    Object localObject1 = this.mAttributesMap;
    ViewOscillator localViewOscillator = null;
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
      localObject3 = (SplineSet)((HashMap)localObject3).get("rotation");
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
      localObject6 = (ViewOscillator)((HashMap)localObject6).get("translationX");
    }
    Object localObject7 = this.mCycleMap;
    if (localObject7 == null) {
      localObject7 = null;
    } else {
      localObject7 = (ViewOscillator)((HashMap)localObject7).get("translationY");
    }
    Object localObject8 = this.mCycleMap;
    if (localObject8 == null) {
      localObject8 = null;
    } else {
      localObject8 = (ViewOscillator)((HashMap)localObject8).get("rotation");
    }
    Object localObject9 = this.mCycleMap;
    if (localObject9 == null) {
      localObject9 = null;
    } else {
      localObject9 = (ViewOscillator)((HashMap)localObject9).get("scaleX");
    }
    Object localObject10 = this.mCycleMap;
    if (localObject10 != null) {
      localViewOscillator = (ViewOscillator)((HashMap)localObject10).get("scaleY");
    }
    localObject10 = new VelocityMatrix();
    ((VelocityMatrix)localObject10).clear();
    ((VelocityMatrix)localObject10).setRotationVelocity((SplineSet)localObject3, f6);
    ((VelocityMatrix)localObject10).setTranslationVelocity((SplineSet)localObject1, (SplineSet)localObject2, f6);
    ((VelocityMatrix)localObject10).setScaleVelocity((SplineSet)localObject4, (SplineSet)localObject5, f6);
    ((VelocityMatrix)localObject10).setRotationVelocity((KeyCycleOscillator)localObject8, f6);
    ((VelocityMatrix)localObject10).setTranslationVelocity((KeyCycleOscillator)localObject6, (KeyCycleOscillator)localObject7, f6);
    ((VelocityMatrix)localObject10).setScaleVelocity((KeyCycleOscillator)localObject9, localViewOscillator, f6);
    CurveFit localCurveFit = this.mArcSpline;
    if (localCurveFit != null)
    {
      localObject1 = this.mInterpolateData;
      if (localObject1.length > 0)
      {
        localCurveFit.getPos(f6, (double[])localObject1);
        this.mArcSpline.getSlope(f6, this.mInterpolateVelocity);
        this.mStartMotionPath.setDpDt(paramFloat2, paramFloat3, paramArrayOfFloat, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
      }
      ((VelocityMatrix)localObject10).applyTransform(paramFloat2, paramFloat3, paramInt1, paramInt2, paramArrayOfFloat);
      return;
    }
    if (this.mSpline != null)
    {
      paramFloat1 = getAdjustedPosition(f6, this.mVelocity);
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
    float f1 = this.mEndMotionPath.x - this.mStartMotionPath.x;
    float f2 = this.mEndMotionPath.y - this.mStartMotionPath.y;
    float f5 = this.mEndMotionPath.width;
    float f3 = this.mStartMotionPath.width;
    float f4 = this.mEndMotionPath.height;
    paramFloat1 = this.mStartMotionPath.height;
    paramArrayOfFloat[0] = ((1.0F - paramFloat2) * f1 + (f1 + (f5 - f3)) * paramFloat2);
    paramArrayOfFloat[1] = ((1.0F - paramFloat3) * f2 + (f2 + (f4 - paramFloat1)) * paramFloat3);
    ((VelocityMatrix)localObject10).clear();
    ((VelocityMatrix)localObject10).setRotationVelocity((SplineSet)localObject3, f6);
    ((VelocityMatrix)localObject10).setTranslationVelocity((SplineSet)localObject1, (SplineSet)localObject2, f6);
    ((VelocityMatrix)localObject10).setScaleVelocity((SplineSet)localObject4, (SplineSet)localObject5, f6);
    ((VelocityMatrix)localObject10).setRotationVelocity((KeyCycleOscillator)localObject8, f6);
    ((VelocityMatrix)localObject10).setTranslationVelocity((KeyCycleOscillator)localObject6, (KeyCycleOscillator)localObject7, f6);
    ((VelocityMatrix)localObject10).setScaleVelocity((KeyCycleOscillator)localObject9, localViewOscillator, f6);
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
  
  public View getView()
  {
    return this.mView;
  }
  
  boolean interpolate(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
  {
    paramFloat = getAdjustedPosition(paramFloat, null);
    float f3;
    float f2;
    float f1;
    if (this.mQuantizeMotionSteps != Key.UNSET)
    {
      f3 = 1.0F / this.mQuantizeMotionSteps;
      f2 = (float)Math.floor(paramFloat / f3);
      f1 = paramFloat % f3 / f3;
      paramFloat = f1;
      if (!Float.isNaN(this.mQuantizeMotionPhase)) {
        paramFloat = (this.mQuantizeMotionPhase + f1) % 1.0F;
      }
      localObject1 = this.mQuantizeMotionInterpolator;
      if (localObject1 != null) {
        paramFloat = ((Interpolator)localObject1).getInterpolation(paramFloat);
      } else if (paramFloat > 0.5D) {
        paramFloat = 1.0F;
      } else {
        paramFloat = 0.0F;
      }
      paramFloat = paramFloat * f3 + f2 * f3;
    }
    Object localObject1 = this.mAttributesMap;
    if (localObject1 != null)
    {
      localObject1 = ((HashMap)localObject1).values().iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((ViewSpline)((Iterator)localObject1).next()).setProperty(paramView, paramFloat);
      }
    }
    localObject1 = this.mTimeCycleAttributesMap;
    boolean bool1;
    Object localObject3;
    if (localObject1 != null)
    {
      localObject2 = ((HashMap)localObject1).values().iterator();
      bool1 = false;
      localObject1 = null;
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (ViewTimeCycle)((Iterator)localObject2).next();
        if ((localObject3 instanceof ViewTimeCycle.PathRotate)) {
          localObject1 = (ViewTimeCycle.PathRotate)localObject3;
        } else {
          bool1 |= ((ViewTimeCycle)localObject3).setProperty(paramView, paramFloat, paramLong, paramKeyCache);
        }
      }
    }
    else
    {
      bool1 = false;
      localObject1 = null;
    }
    Object localObject2 = this.mSpline;
    float f4;
    int i;
    boolean bool2;
    if (localObject2 != null)
    {
      localObject2[0].getPos(paramFloat, this.mInterpolateData);
      this.mSpline[0].getSlope(paramFloat, this.mInterpolateVelocity);
      localObject3 = this.mArcSpline;
      if (localObject3 != null)
      {
        localObject2 = this.mInterpolateData;
        if (localObject2.length > 0)
        {
          ((CurveFit)localObject3).getPos(paramFloat, (double[])localObject2);
          this.mArcSpline.getSlope(paramFloat, this.mInterpolateVelocity);
        }
      }
      if (!this.mNoMovement)
      {
        this.mStartMotionPath.setView(paramFloat, paramView, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null, this.mForceMeasure);
        this.mForceMeasure = false;
      }
      if (this.mTransformPivotTarget != Key.UNSET)
      {
        if (this.mTransformPivotView == null) {
          this.mTransformPivotView = ((View)paramView.getParent()).findViewById(this.mTransformPivotTarget);
        }
        localObject2 = this.mTransformPivotView;
        if (localObject2 != null)
        {
          f1 = (((View)localObject2).getTop() + this.mTransformPivotView.getBottom()) / 2.0F;
          f4 = (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight()) / 2.0F;
          if ((paramView.getRight() - paramView.getLeft() > 0) && (paramView.getBottom() - paramView.getTop() > 0))
          {
            f2 = paramView.getLeft();
            f3 = paramView.getTop();
            paramView.setPivotX(f4 - f2);
            paramView.setPivotY(f1 - f3);
          }
        }
      }
      localObject2 = this.mAttributesMap;
      if (localObject2 != null)
      {
        localObject3 = ((HashMap)localObject2).values().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject2 = (SplineSet)((Iterator)localObject3).next();
          if ((localObject2 instanceof ViewSpline.PathRotate))
          {
            double[] arrayOfDouble = this.mInterpolateVelocity;
            if (arrayOfDouble.length > 1) {
              ((ViewSpline.PathRotate)localObject2).setPathRotate(paramView, paramFloat, arrayOfDouble[0], arrayOfDouble[1]);
            }
          }
        }
      }
      if (localObject1 != null)
      {
        localObject2 = this.mInterpolateVelocity;
        bool1 |= ((ViewTimeCycle.PathRotate)localObject1).setPathRotate(paramView, paramKeyCache, paramFloat, paramLong, localObject2[0], localObject2[1]);
      }
      for (i = 1;; i++)
      {
        paramKeyCache = this.mSpline;
        if (i >= paramKeyCache.length) {
          break;
        }
        paramKeyCache[i].getPos(paramFloat, this.mValuesBuff);
        CustomSupport.setInterpolatedValue((ConstraintAttribute)this.mStartMotionPath.attributes.get(this.mAttributeNames[(i - 1)]), paramView, this.mValuesBuff);
      }
      if (this.mStartPoint.mVisibilityMode == 0) {
        if (paramFloat <= 0.0F) {
          paramView.setVisibility(this.mStartPoint.visibility);
        } else if (paramFloat >= 1.0F) {
          paramView.setVisibility(this.mEndPoint.visibility);
        } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
          paramView.setVisibility(0);
        } else {}
      }
      bool2 = bool1;
      if (this.mKeyTriggers != null)
      {
        for (i = 0;; i++)
        {
          paramKeyCache = this.mKeyTriggers;
          if (i >= paramKeyCache.length) {
            break;
          }
          paramKeyCache[i].conditionallyFire(paramFloat, paramView);
        }
        bool2 = bool1;
      }
    }
    else
    {
      float f5 = this.mStartMotionPath.x + (this.mEndMotionPath.x - this.mStartMotionPath.x) * paramFloat;
      float f7 = this.mStartMotionPath.y + (this.mEndMotionPath.y - this.mStartMotionPath.y) * paramFloat;
      f3 = this.mStartMotionPath.width;
      f4 = this.mEndMotionPath.width;
      float f8 = this.mStartMotionPath.width;
      float f6 = this.mStartMotionPath.height;
      f1 = this.mEndMotionPath.height;
      f2 = this.mStartMotionPath.height;
      int k = (int)(f5 + 0.5F);
      int j = (int)(f7 + 0.5F);
      i = (int)(f5 + 0.5F + (f3 + (f4 - f8) * paramFloat));
      int m = (int)(0.5F + f7 + (f6 + (f1 - f2) * paramFloat));
      if ((this.mEndMotionPath.width == this.mStartMotionPath.width) && (this.mEndMotionPath.height == this.mStartMotionPath.height) && (!this.mForceMeasure)) {
        break label1100;
      }
      paramView.measure(View.MeasureSpec.makeMeasureSpec(i - k, 1073741824), View.MeasureSpec.makeMeasureSpec(m - j, 1073741824));
      this.mForceMeasure = false;
      label1100:
      paramView.layout(k, j, i, m);
      bool2 = bool1;
    }
    paramKeyCache = this.mCycleMap;
    if (paramKeyCache != null)
    {
      paramKeyCache = paramKeyCache.values().iterator();
      while (paramKeyCache.hasNext())
      {
        localObject1 = (ViewOscillator)paramKeyCache.next();
        if ((localObject1 instanceof ViewOscillator.PathRotateSet))
        {
          localObject1 = (ViewOscillator.PathRotateSet)localObject1;
          localObject2 = this.mInterpolateVelocity;
          ((ViewOscillator.PathRotateSet)localObject1).setPathRotate(paramView, paramFloat, localObject2[0], localObject2[1]);
        }
        else
        {
          ((ViewOscillator)localObject1).setProperty(paramView, paramFloat);
        }
      }
    }
    return bool2;
  }
  
  String name()
  {
    return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
  }
  
  void positionKeyframe(View paramView, KeyPositionBase paramKeyPositionBase, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    RectF localRectF1 = new RectF();
    localRectF1.left = this.mStartMotionPath.x;
    localRectF1.top = this.mStartMotionPath.y;
    localRectF1.right = (localRectF1.left + this.mStartMotionPath.width);
    localRectF1.bottom = (localRectF1.top + this.mStartMotionPath.height);
    RectF localRectF2 = new RectF();
    localRectF2.left = this.mEndMotionPath.x;
    localRectF2.top = this.mEndMotionPath.y;
    localRectF2.right = (localRectF2.left + this.mEndMotionPath.width);
    localRectF2.bottom = (localRectF2.top + this.mEndMotionPath.height);
    paramKeyPositionBase.positionAttributes(paramView, localRectF1, localRectF2, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
  }
  
  public void remeasure()
  {
    this.mForceMeasure = true;
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
      paramInt1 = paramRect1.left;
      i = paramRect1.right;
      paramInt3 = paramRect1.bottom;
      j = paramRect1.top;
      paramRect2.left = (paramInt2 - (paramRect1.width() + (paramInt3 + j)) / 2);
      paramRect2.top = ((paramInt1 + i - paramRect1.height()) / 2);
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
      j = paramRect1.left;
      paramInt1 = paramRect1.right;
      i = paramRect1.top;
      paramInt3 = paramRect1.bottom;
      paramRect2.left = (paramInt2 - (paramRect1.width() + (i + paramInt3)) / 2);
      paramRect2.top = ((j + paramInt1 - paramRect1.height()) / 2);
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
  
  void setBothStates(View paramView)
  {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    this.mNoMovement = true;
    this.mStartMotionPath.setBounds(paramView.getX(), paramView.getY(), paramView.getWidth(), paramView.getHeight());
    this.mEndMotionPath.setBounds(paramView.getX(), paramView.getY(), paramView.getWidth(), paramView.getHeight());
    this.mStartPoint.setState(paramView);
    this.mEndPoint.setState(paramView);
  }
  
  public void setDrawPath(int paramInt)
  {
    this.mStartMotionPath.mDrawPath = paramInt;
  }
  
  void setEndState(Rect paramRect, ConstraintSet paramConstraintSet, int paramInt1, int paramInt2)
  {
    int i = paramConstraintSet.mRotate;
    Rect localRect = paramRect;
    if (i != 0)
    {
      rotate(paramRect, this.mTempRect, i, paramInt1, paramInt2);
      localRect = this.mTempRect;
    }
    this.mEndMotionPath.time = 1.0F;
    this.mEndMotionPath.position = 1.0F;
    readView(this.mEndMotionPath);
    this.mEndMotionPath.setBounds(localRect.left, localRect.top, localRect.width(), localRect.height());
    this.mEndMotionPath.applyParameters(paramConstraintSet.getParameters(this.mId));
    this.mEndPoint.setState(localRect, paramConstraintSet, i, this.mId);
  }
  
  public void setPathMotionArc(int paramInt)
  {
    this.mPathMotionArc = paramInt;
  }
  
  void setStartCurrentState(View paramView)
  {
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    this.mStartMotionPath.setBounds(paramView.getX(), paramView.getY(), paramView.getWidth(), paramView.getHeight());
    this.mStartPoint.setState(paramView);
  }
  
  void setStartState(Rect paramRect, ConstraintSet paramConstraintSet, int paramInt1, int paramInt2)
  {
    int i = paramConstraintSet.mRotate;
    if (i != 0) {
      rotate(paramRect, this.mTempRect, i, paramInt1, paramInt2);
    }
    this.mStartMotionPath.time = 0.0F;
    this.mStartMotionPath.position = 0.0F;
    readView(this.mStartMotionPath);
    this.mStartMotionPath.setBounds(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
    ConstraintSet.Constraint localConstraint = paramConstraintSet.getParameters(this.mId);
    this.mStartMotionPath.applyParameters(localConstraint);
    this.mMotionStagger = localConstraint.motion.mMotionStagger;
    this.mStartPoint.setState(paramRect, paramConstraintSet, i, this.mId);
    this.mTransformPivotTarget = localConstraint.transform.transformPivotTarget;
    this.mQuantizeMotionSteps = localConstraint.motion.mQuantizeMotionSteps;
    this.mQuantizeMotionPhase = localConstraint.motion.mQuantizeMotionPhase;
    this.mQuantizeMotionInterpolator = getInterpolator(this.mView.getContext(), localConstraint.motion.mQuantizeInterpolatorType, localConstraint.motion.mQuantizeInterpolatorString, localConstraint.motion.mQuantizeInterpolatorID);
  }
  
  public void setStartState(ViewState paramViewState, View paramView, int paramInt1, int paramInt2, int paramInt3)
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
      int j = paramViewState.left;
      paramInt2 = paramViewState.right;
      i = paramViewState.top;
      int k = paramViewState.bottom;
      localRect.left = (paramInt3 - (paramViewState.width() + (i + k)) / 2);
      localRect.top = ((j + paramInt2 - paramViewState.height()) / 2);
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
    this.mStartPoint.setState(localRect, paramView, paramInt1, paramViewState.rotation);
  }
  
  public void setTransformPivotTarget(int paramInt)
  {
    this.mTransformPivotTarget = paramInt;
    this.mTransformPivotView = null;
  }
  
  public void setView(View paramView)
  {
    this.mView = paramView;
    this.mId = paramView.getId();
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof ConstraintLayout.LayoutParams)) {
      this.mConstraintTag = ((ConstraintLayout.LayoutParams)paramView).getConstraintTag();
    }
  }
  
  public void setup(int paramInt1, int paramInt2, float paramFloat, long paramLong)
  {
    Object localObject3 = new HashSet();
    Object localObject6 = new HashSet();
    Object localObject5 = new HashSet();
    HashSet localHashSet = new HashSet();
    Object localObject4 = new HashMap();
    Object localObject2 = null;
    Object localObject1 = null;
    if (this.mPathMotionArc != Key.UNSET) {
      this.mStartMotionPath.mPathMotionArc = this.mPathMotionArc;
    }
    this.mStartPoint.different(this.mEndPoint, (HashSet)localObject5);
    Object localObject7 = this.mKeyList;
    if (localObject7 != null)
    {
      localObject7 = ((ArrayList)localObject7).iterator();
      while (((Iterator)localObject7).hasNext())
      {
        localObject8 = (Key)((Iterator)localObject7).next();
        if ((localObject8 instanceof KeyPosition))
        {
          localObject2 = (KeyPosition)localObject8;
          insertKey(new MotionPaths(paramInt1, paramInt2, (KeyPosition)localObject2, this.mStartMotionPath, this.mEndMotionPath));
          if (((KeyPosition)localObject2).mCurveFit != Key.UNSET) {
            this.mCurveFitType = ((KeyPosition)localObject2).mCurveFit;
          }
        }
        else if ((localObject8 instanceof KeyCycle))
        {
          ((Key)localObject8).getAttributeNames(localHashSet);
        }
        else if ((localObject8 instanceof KeyTimeCycle))
        {
          ((Key)localObject8).getAttributeNames((HashSet)localObject6);
        }
        else if ((localObject8 instanceof KeyTrigger))
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add((KeyTrigger)localObject8);
          localObject1 = localObject2;
        }
        else
        {
          ((Key)localObject8).setInterpolation((HashMap)localObject4);
          ((Key)localObject8).getAttributeNames((HashSet)localObject5);
        }
      }
      localObject2 = localObject1;
    }
    if (localObject2 != null) {
      this.mKeyTriggers = ((KeyTrigger[])((ArrayList)localObject2).toArray(new KeyTrigger[0]));
    }
    Object localObject10;
    Object localObject9;
    Object localObject11;
    if (!((HashSet)localObject5).isEmpty())
    {
      this.mAttributesMap = new HashMap();
      localObject7 = ((HashSet)localObject5).iterator();
      while (((Iterator)localObject7).hasNext())
      {
        localObject3 = (String)((Iterator)localObject7).next();
        if (((String)localObject3).startsWith("CUSTOM,"))
        {
          localObject10 = new SparseArray();
          localObject9 = localObject3.split(",")[1];
          localObject11 = this.mKeyList.iterator();
          while (((Iterator)localObject11).hasNext())
          {
            localObject1 = (Key)((Iterator)localObject11).next();
            if (((Key)localObject1).mCustomConstraints != null)
            {
              localObject8 = (ConstraintAttribute)((Key)localObject1).mCustomConstraints.get(localObject9);
              if (localObject8 != null) {
                ((SparseArray)localObject10).append(((Key)localObject1).mFramePosition, localObject8);
              }
            }
          }
          localObject1 = ViewSpline.makeCustomSpline((String)localObject3, (SparseArray)localObject10);
        }
        else
        {
          localObject1 = ViewSpline.makeSpline((String)localObject3);
        }
        if (localObject1 != null)
        {
          ((ViewSpline)localObject1).setType((String)localObject3);
          this.mAttributesMap.put(localObject3, localObject1);
        }
      }
      localObject1 = this.mKeyList;
      if (localObject1 != null)
      {
        localObject3 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject1 = (Key)((Iterator)localObject3).next();
          if ((localObject1 instanceof KeyAttributes)) {
            ((Key)localObject1).addValues(this.mAttributesMap);
          }
        }
      }
      this.mStartPoint.addValues(this.mAttributesMap, 0);
      this.mEndPoint.addValues(this.mAttributesMap, 100);
      localObject1 = this.mAttributesMap.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject7 = (String)((Iterator)localObject1).next();
        paramInt2 = 0;
        paramInt1 = paramInt2;
        if (((HashMap)localObject4).containsKey(localObject7))
        {
          localObject3 = (Integer)((HashMap)localObject4).get(localObject7);
          paramInt1 = paramInt2;
          if (localObject3 != null) {
            paramInt1 = ((Integer)localObject3).intValue();
          }
        }
        localObject3 = (SplineSet)this.mAttributesMap.get(localObject7);
        if (localObject3 != null) {
          ((SplineSet)localObject3).setup(paramInt1);
        }
      }
    }
    if (!((HashSet)localObject6).isEmpty())
    {
      if (this.mTimeCycleAttributesMap == null) {
        this.mTimeCycleAttributesMap = new HashMap();
      }
      localObject1 = ((HashSet)localObject6).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject7 = (String)((Iterator)localObject1).next();
        if (!this.mTimeCycleAttributesMap.containsKey(localObject7))
        {
          if (((String)localObject7).startsWith("CUSTOM,"))
          {
            localObject3 = new SparseArray();
            localObject8 = localObject7.split(",")[1];
            localObject9 = this.mKeyList.iterator();
            while (((Iterator)localObject9).hasNext())
            {
              localObject10 = (Key)((Iterator)localObject9).next();
              if (((Key)localObject10).mCustomConstraints != null)
              {
                localObject11 = (ConstraintAttribute)((Key)localObject10).mCustomConstraints.get(localObject8);
                if (localObject11 != null) {
                  ((SparseArray)localObject3).append(((Key)localObject10).mFramePosition, localObject11);
                }
              }
            }
            localObject3 = ViewTimeCycle.makeCustomSpline((String)localObject7, (SparseArray)localObject3);
          }
          else
          {
            localObject3 = ViewTimeCycle.makeSpline((String)localObject7, paramLong);
          }
          if (localObject3 != null)
          {
            ((ViewTimeCycle)localObject3).setType((String)localObject7);
            this.mTimeCycleAttributesMap.put(localObject7, localObject3);
          }
        }
      }
      localObject1 = this.mKeyList;
      if (localObject1 != null)
      {
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject3 = (Key)((Iterator)localObject1).next();
          if ((localObject3 instanceof KeyTimeCycle)) {
            ((KeyTimeCycle)localObject3).addTimeValues(this.mTimeCycleAttributesMap);
          }
        }
      }
      localObject1 = this.mTimeCycleAttributesMap.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (String)((Iterator)localObject1).next();
        paramInt1 = 0;
        if (((HashMap)localObject4).containsKey(localObject3)) {
          paramInt1 = ((Integer)((HashMap)localObject4).get(localObject3)).intValue();
        }
        ((ViewTimeCycle)this.mTimeCycleAttributesMap.get(localObject3)).setup(paramInt1);
      }
    }
    localObject7 = new MotionPaths[this.mMotionPaths.size() + 2];
    paramInt1 = 1;
    localObject7[0] = this.mStartMotionPath;
    localObject7[(localObject7.length - 1)] = this.mEndMotionPath;
    if ((this.mMotionPaths.size() > 0) && (this.mCurveFitType == -1)) {
      this.mCurveFitType = 0;
    }
    localObject1 = this.mMotionPaths.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject7[paramInt1] = ((MotionPaths)((Iterator)localObject1).next());
      paramInt1++;
    }
    int j = 18;
    Object localObject8 = new HashSet();
    localObject3 = this.mEndMotionPath.attributes.keySet().iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject1 = (String)((Iterator)localObject3).next();
      if ((this.mStartMotionPath.attributes.containsKey(localObject1)) && (!((HashSet)localObject5).contains("CUSTOM," + (String)localObject1))) {
        ((HashSet)localObject8).add(localObject1);
      }
    }
    localObject1 = (String[])((HashSet)localObject8).toArray(new String[0]);
    this.mAttributeNames = ((String[])localObject1);
    this.mAttributeInterpolatorCount = new int[localObject1.length];
    paramInt1 = 0;
    localObject1 = localObject6;
    for (;;)
    {
      localObject3 = this.mAttributeNames;
      if (paramInt1 >= localObject3.length) {
        break;
      }
      localObject3 = localObject3[paramInt1];
      this.mAttributeInterpolatorCount[paramInt1] = 0;
      for (paramInt2 = 0; paramInt2 < localObject7.length; paramInt2++) {
        if (localObject7[paramInt2].attributes.containsKey(localObject3))
        {
          localObject6 = (ConstraintAttribute)localObject7[paramInt2].attributes.get(localObject3);
          if (localObject6 != null)
          {
            localObject3 = this.mAttributeInterpolatorCount;
            localObject3[paramInt1] += ((ConstraintAttribute)localObject6).numberOfInterpolatedValues();
            break;
          }
        }
      }
      paramInt1++;
    }
    boolean bool;
    if (localObject7[0].mPathMotionArc != Key.UNSET) {
      bool = true;
    } else {
      bool = false;
    }
    localObject3 = new boolean[this.mAttributeNames.length + 18];
    paramInt1 = 1;
    localObject1 = localObject5;
    while (paramInt1 < localObject7.length)
    {
      localObject7[paramInt1].different(localObject7[(paramInt1 - 1)], (boolean[])localObject3, this.mAttributeNames, bool);
      paramInt1++;
    }
    int i = 0;
    paramInt1 = 1;
    while (paramInt1 < localObject3.length)
    {
      paramInt2 = i;
      if (localObject3[paramInt1] != 0) {
        paramInt2 = i + 1;
      }
      paramInt1++;
      i = paramInt2;
    }
    this.mInterpolateVariables = new int[i];
    int k = Math.max(2, i);
    this.mInterpolateData = new double[k];
    this.mInterpolateVelocity = new double[k];
    paramInt1 = 0;
    paramInt2 = 1;
    while (paramInt2 < localObject3.length)
    {
      i = paramInt1;
      if (localObject3[paramInt2] != 0)
      {
        this.mInterpolateVariables[paramInt1] = paramInt2;
        i = paramInt1 + 1;
      }
      paramInt2++;
      paramInt1 = i;
    }
    paramInt2 = localObject7.length;
    i = this.mInterpolateVariables.length;
    localObject6 = new double[paramInt2][i];
    localObject8 = new double[localObject7.length];
    paramInt2 = 0;
    localObject1 = localObject4;
    while (paramInt2 < localObject7.length)
    {
      localObject7[paramInt2].fillStandard(localObject6[paramInt2], this.mInterpolateVariables);
      localObject8[paramInt2] = localObject7[paramInt2].time;
      paramInt2++;
    }
    i = 0;
    paramInt2 = j;
    paramInt1 = k;
    for (;;)
    {
      localObject1 = this.mInterpolateVariables;
      if (i >= localObject1.length) {
        break;
      }
      if (localObject1[i] < MotionPaths.names.length)
      {
        localObject1 = MotionPaths.names[this.mInterpolateVariables[i]] + " [";
        for (j = 0; j < localObject7.length; j++) {
          localObject1 = (String)localObject1 + localObject6[j][i];
        }
      }
      i++;
    }
    this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
    paramInt1 = 0;
    localObject1 = localObject3;
    for (;;)
    {
      localObject4 = this.mAttributeNames;
      if (paramInt1 >= localObject4.length) {
        break;
      }
      i = 0;
      localObject2 = null;
      localObject3 = null;
      localObject9 = localObject4[paramInt1];
      paramInt2 = 0;
      while (paramInt2 < localObject7.length)
      {
        j = i;
        localObject4 = localObject2;
        localObject5 = localObject3;
        if (localObject7[paramInt2].hasCustomData((String)localObject9))
        {
          localObject4 = localObject2;
          if (localObject2 == null)
          {
            localObject3 = new double[localObject7.length];
            k = localObject7.length;
            j = localObject7[paramInt2].getCustomDataCount((String)localObject9);
            localObject4 = new double[k][j];
          }
          localObject3[i] = localObject7[paramInt2].time;
          localObject7[paramInt2].getCustomData((String)localObject9, localObject4[i], 0);
          j = i + 1;
          localObject5 = localObject3;
        }
        paramInt2++;
        i = j;
        localObject2 = localObject4;
        localObject3 = localObject5;
      }
      localObject3 = Arrays.copyOf((double[])localObject3, i);
      localObject2 = (double[][])Arrays.copyOf((Object[])localObject2, i);
      this.mSpline[(paramInt1 + 1)] = CurveFit.get(this.mCurveFitType, (double[])localObject3, (double[][])localObject2);
      paramInt1++;
    }
    this.mSpline[0] = CurveFit.get(this.mCurveFitType, (double[])localObject8, (double[][])localObject6);
    if (localObject7[0].mPathMotionArc != Key.UNSET)
    {
      paramInt2 = localObject7.length;
      localObject2 = new int[paramInt2];
      localObject1 = new double[paramInt2];
      localObject3 = new double[paramInt2][2];
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      {
        localObject2[paramInt1] = localObject7[paramInt1].mPathMotionArc;
        localObject1[paramInt1] = localObject7[paramInt1].time;
        localObject3[paramInt1][0] = localObject7[paramInt1].x;
        localObject3[paramInt1][1] = localObject7[paramInt1].y;
      }
      this.mArcSpline = CurveFit.getArc((int[])localObject2, (double[])localObject1, (double[][])localObject3);
    }
    float f = NaN.0F;
    this.mCycleMap = new HashMap();
    if (this.mKeyList != null)
    {
      localObject3 = localHashSet.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (String)((Iterator)localObject3).next();
        localObject1 = ViewOscillator.makeSpline((String)localObject2);
        if (localObject1 != null)
        {
          paramFloat = f;
          if (((ViewOscillator)localObject1).variesByPath())
          {
            paramFloat = f;
            if (Float.isNaN(f)) {
              paramFloat = getPreCycleDistance();
            }
          }
          ((ViewOscillator)localObject1).setType((String)localObject2);
          this.mCycleMap.put(localObject2, localObject1);
          f = paramFloat;
        }
      }
      localObject1 = this.mKeyList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Key)((Iterator)localObject1).next();
        if ((localObject2 instanceof KeyCycle)) {
          ((KeyCycle)localObject2).addCycleValues(this.mCycleMap);
        }
      }
      localObject1 = this.mCycleMap.values().iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((ViewOscillator)((Iterator)localObject1).next()).setup(f);
      }
    }
  }
  
  public void setupRelative(MotionController paramMotionController)
  {
    this.mStartMotionPath.setupRelative(paramMotionController, paramMotionController.mStartMotionPath);
    this.mEndMotionPath.setupRelative(paramMotionController, paramMotionController.mEndMotionPath);
  }
  
  public String toString()
  {
    return " start: x: " + this.mStartMotionPath.x + " y: " + this.mStartMotionPath.y + " end: x: " + this.mEndMotionPath.x + " y: " + this.mEndMotionPath.y;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */