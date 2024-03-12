package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.SplineSet.CustomSpline;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

class MotionConstrainedPoint
  implements Comparable<MotionConstrainedPoint>
{
  static final int CARTESIAN = 2;
  public static final boolean DEBUG = false;
  static final int PERPENDICULAR = 1;
  public static final String TAG = "MotionPaths";
  static String[] names = { "position", "x", "y", "width", "height", "pathRotate" };
  private float alpha = 1.0F;
  private boolean applyElevation = false;
  private float elevation = 0.0F;
  private float height;
  private int mAnimateRelativeTo = -1;
  LinkedHashMap<String, CustomVariable> mCustomVariable = new LinkedHashMap();
  private int mDrawPath = 0;
  private Easing mKeyFrameEasing;
  int mMode = 0;
  private float mPathRotate = NaN.0F;
  private float mPivotX = NaN.0F;
  private float mPivotY = NaN.0F;
  private float mProgress = NaN.0F;
  double[] mTempDelta = new double[18];
  double[] mTempValue = new double[18];
  int mVisibilityMode = 0;
  private float position;
  private float rotation = 0.0F;
  private float rotationX = 0.0F;
  public float rotationY = 0.0F;
  private float scaleX = 1.0F;
  private float scaleY = 1.0F;
  private float translationX = 0.0F;
  private float translationY = 0.0F;
  private float translationZ = 0.0F;
  int visibility;
  private float width;
  private float x;
  private float y;
  
  private boolean diff(float paramFloat1, float paramFloat2)
  {
    boolean bool3 = Float.isNaN(paramFloat1);
    boolean bool2 = true;
    boolean bool1 = true;
    if ((!bool3) && (!Float.isNaN(paramFloat2)))
    {
      if (Math.abs(paramFloat1 - paramFloat2) <= 1.0E-6F) {
        bool1 = false;
      }
      return bool1;
    }
    if (Float.isNaN(paramFloat1) != Float.isNaN(paramFloat2)) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    return bool1;
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap, int paramInt)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    label994:
    label997:
    label1022:
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      SplineSet localSplineSet = (SplineSet)paramHashMap.get(str);
      int i = -1;
      switch (str.hashCode())
      {
      }
      for (;;)
      {
        break;
        if (str.equals("pathRotate"))
        {
          i = 6;
          break;
          if (str.equals("alpha"))
          {
            i = 0;
            break;
            if (str.equals("scaleY"))
            {
              i = 9;
              break;
              if (str.equals("scaleX"))
              {
                i = 8;
                break;
                if (str.equals("pivotY"))
                {
                  i = 5;
                  break;
                  if (str.equals("pivotX"))
                  {
                    i = 4;
                    break;
                    if (str.equals("progress"))
                    {
                      i = 7;
                      break;
                      if (str.equals("translationZ"))
                      {
                        i = 12;
                        break;
                        if (str.equals("translationY"))
                        {
                          i = 11;
                          break;
                          if (str.equals("translationX"))
                          {
                            i = 10;
                            break;
                            if (str.equals("rotationZ"))
                            {
                              i = 1;
                              break;
                              if (str.equals("rotationY"))
                              {
                                i = 3;
                                break;
                                if (str.equals("rotationX")) {
                                  i = 2;
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      float f1 = 1.0F;
      float f6 = 0.0F;
      float f5 = 0.0F;
      float f3 = 0.0F;
      float f7 = 0.0F;
      float f10 = 0.0F;
      float f2 = 0.0F;
      float f11 = 0.0F;
      float f4 = 0.0F;
      float f8 = 0.0F;
      float f9 = 0.0F;
      Object localObject;
      switch (i)
      {
      default: 
        if (!str.startsWith("CUSTOM")) {
          break label997;
        }
        localObject = str.split(",")[1];
        if (!this.mCustomVariable.containsKey(localObject)) {
          break label994;
        }
        localObject = (CustomVariable)this.mCustomVariable.get(localObject);
        if ((localSplineSet instanceof SplineSet.CustomSpline)) {
          ((SplineSet.CustomSpline)localSplineSet).setPoint(paramInt, (CustomVariable)localObject);
        }
        break;
      case 12: 
        if (Float.isNaN(this.translationZ)) {
          f1 = f9;
        } else {
          f1 = this.translationZ;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 11: 
        if (Float.isNaN(this.translationY)) {
          f1 = f6;
        } else {
          f1 = this.translationY;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 10: 
        if (Float.isNaN(this.translationX)) {
          f1 = f5;
        } else {
          f1 = this.translationX;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 9: 
        if (!Float.isNaN(this.scaleY)) {
          f1 = this.scaleY;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 8: 
        if (!Float.isNaN(this.scaleX)) {
          f1 = this.scaleX;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 7: 
        if (Float.isNaN(this.mProgress)) {
          f1 = f3;
        } else {
          f1 = this.mProgress;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 6: 
        if (Float.isNaN(this.mPathRotate)) {
          f1 = f7;
        } else {
          f1 = this.mPathRotate;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 5: 
        if (Float.isNaN(this.mPivotY)) {
          f1 = f10;
        } else {
          f1 = this.mPivotY;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 4: 
        if (Float.isNaN(this.mPivotX)) {
          f1 = f2;
        } else {
          f1 = this.mPivotX;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 3: 
        if (Float.isNaN(this.rotationY)) {
          f1 = f11;
        } else {
          f1 = this.rotationY;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 2: 
        if (Float.isNaN(this.rotationX)) {
          f1 = f4;
        } else {
          f1 = this.rotationX;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 1: 
        if (Float.isNaN(this.rotation)) {
          f1 = f8;
        } else {
          f1 = this.rotation;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      case 0: 
        if (!Float.isNaN(this.alpha)) {
          f1 = this.alpha;
        }
        localSplineSet.setPoint(paramInt, f1);
        break;
      }
      Utils.loge("MotionPaths", str + " ViewSpline not a CustomSet frame = " + paramInt + ", value" + ((CustomVariable)localObject).getValueToInterpolate() + localSplineSet);
      break label1022;
      Utils.loge("MotionPaths", "UNKNOWN spline " + str);
    }
  }
  
  public void applyParameters(MotionWidget paramMotionWidget)
  {
    this.visibility = paramMotionWidget.getVisibility();
    float f;
    if (paramMotionWidget.getVisibility() != 4) {
      f = 0.0F;
    } else {
      f = paramMotionWidget.getAlpha();
    }
    this.alpha = f;
    this.applyElevation = false;
    this.rotation = paramMotionWidget.getRotationZ();
    this.rotationX = paramMotionWidget.getRotationX();
    this.rotationY = paramMotionWidget.getRotationY();
    this.scaleX = paramMotionWidget.getScaleX();
    this.scaleY = paramMotionWidget.getScaleY();
    this.mPivotX = paramMotionWidget.getPivotX();
    this.mPivotY = paramMotionWidget.getPivotY();
    this.translationX = paramMotionWidget.getTranslationX();
    this.translationY = paramMotionWidget.getTranslationY();
    this.translationZ = paramMotionWidget.getTranslationZ();
    Iterator localIterator = paramMotionWidget.getCustomAttributeNames().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      CustomVariable localCustomVariable = paramMotionWidget.getCustomAttribute(str);
      if ((localCustomVariable != null) && (localCustomVariable.isContinuous())) {
        this.mCustomVariable.put(str, localCustomVariable);
      }
    }
  }
  
  public int compareTo(MotionConstrainedPoint paramMotionConstrainedPoint)
  {
    return Float.compare(this.position, paramMotionConstrainedPoint.position);
  }
  
  void different(MotionConstrainedPoint paramMotionConstrainedPoint, HashSet<String> paramHashSet)
  {
    if (diff(this.alpha, paramMotionConstrainedPoint.alpha)) {
      paramHashSet.add("alpha");
    }
    if (diff(this.elevation, paramMotionConstrainedPoint.elevation)) {
      paramHashSet.add("translationZ");
    }
    int i = this.visibility;
    int j = paramMotionConstrainedPoint.visibility;
    if ((i != j) && (this.mVisibilityMode == 0) && ((i == 4) || (j == 4))) {
      paramHashSet.add("alpha");
    }
    if (diff(this.rotation, paramMotionConstrainedPoint.rotation)) {
      paramHashSet.add("rotationZ");
    }
    if ((!Float.isNaN(this.mPathRotate)) || (!Float.isNaN(paramMotionConstrainedPoint.mPathRotate))) {
      paramHashSet.add("pathRotate");
    }
    if ((!Float.isNaN(this.mProgress)) || (!Float.isNaN(paramMotionConstrainedPoint.mProgress))) {
      paramHashSet.add("progress");
    }
    if (diff(this.rotationX, paramMotionConstrainedPoint.rotationX)) {
      paramHashSet.add("rotationX");
    }
    if (diff(this.rotationY, paramMotionConstrainedPoint.rotationY)) {
      paramHashSet.add("rotationY");
    }
    if (diff(this.mPivotX, paramMotionConstrainedPoint.mPivotX)) {
      paramHashSet.add("pivotX");
    }
    if (diff(this.mPivotY, paramMotionConstrainedPoint.mPivotY)) {
      paramHashSet.add("pivotY");
    }
    if (diff(this.scaleX, paramMotionConstrainedPoint.scaleX)) {
      paramHashSet.add("scaleX");
    }
    if (diff(this.scaleY, paramMotionConstrainedPoint.scaleY)) {
      paramHashSet.add("scaleY");
    }
    if (diff(this.translationX, paramMotionConstrainedPoint.translationX)) {
      paramHashSet.add("translationX");
    }
    if (diff(this.translationY, paramMotionConstrainedPoint.translationY)) {
      paramHashSet.add("translationY");
    }
    if (diff(this.translationZ, paramMotionConstrainedPoint.translationZ)) {
      paramHashSet.add("translationZ");
    }
    if (diff(this.elevation, paramMotionConstrainedPoint.elevation)) {
      paramHashSet.add("elevation");
    }
  }
  
  void different(MotionConstrainedPoint paramMotionConstrainedPoint, boolean[] paramArrayOfBoolean, String[] paramArrayOfString)
  {
    int j = 0 + 1;
    paramArrayOfBoolean[0] |= diff(this.position, paramMotionConstrainedPoint.position);
    int i = j + 1;
    paramArrayOfBoolean[j] |= diff(this.x, paramMotionConstrainedPoint.x);
    j = i + 1;
    paramArrayOfBoolean[i] |= diff(this.y, paramMotionConstrainedPoint.y);
    i = j + 1;
    paramArrayOfBoolean[j] |= diff(this.width, paramMotionConstrainedPoint.width);
    paramArrayOfBoolean[i] |= diff(this.height, paramMotionConstrainedPoint.height);
  }
  
  void fillStandard(double[] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    float[] arrayOfFloat = new float[18];
    arrayOfFloat[0] = this.position;
    arrayOfFloat[1] = this.x;
    arrayOfFloat[2] = this.y;
    arrayOfFloat[3] = this.width;
    arrayOfFloat[4] = this.height;
    arrayOfFloat[5] = this.alpha;
    arrayOfFloat[6] = this.elevation;
    arrayOfFloat[7] = this.rotation;
    arrayOfFloat[8] = this.rotationX;
    arrayOfFloat[9] = this.rotationY;
    arrayOfFloat[10] = this.scaleX;
    arrayOfFloat[11] = this.scaleY;
    arrayOfFloat[12] = this.mPivotX;
    arrayOfFloat[13] = this.mPivotY;
    arrayOfFloat[14] = this.translationX;
    arrayOfFloat[15] = this.translationY;
    arrayOfFloat[16] = this.translationZ;
    arrayOfFloat[17] = this.mPathRotate;
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
  
  int getCustomData(String paramString, double[] paramArrayOfDouble, int paramInt)
  {
    CustomVariable localCustomVariable = (CustomVariable)this.mCustomVariable.get(paramString);
    if (localCustomVariable.numberOfInterpolatedValues() == 1)
    {
      paramArrayOfDouble[paramInt] = localCustomVariable.getValueToInterpolate();
      return 1;
    }
    int j = localCustomVariable.numberOfInterpolatedValues();
    paramString = new float[j];
    localCustomVariable.getValuesToInterpolate(paramString);
    int i = 0;
    while (i < j)
    {
      paramArrayOfDouble[paramInt] = paramString[i];
      i++;
      paramInt++;
    }
    return j;
  }
  
  int getCustomDataCount(String paramString)
  {
    return ((CustomVariable)this.mCustomVariable.get(paramString)).numberOfInterpolatedValues();
  }
  
  boolean hasCustomData(String paramString)
  {
    return this.mCustomVariable.containsKey(paramString);
  }
  
  void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.width = paramFloat3;
    this.height = paramFloat4;
  }
  
  public void setState(MotionWidget paramMotionWidget)
  {
    setBounds(paramMotionWidget.getX(), paramMotionWidget.getY(), paramMotionWidget.getWidth(), paramMotionWidget.getHeight());
    applyParameters(paramMotionWidget);
  }
  
  public void setState(Rect paramRect, MotionWidget paramMotionWidget, int paramInt, float paramFloat)
  {
    setBounds(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
    applyParameters(paramMotionWidget);
    this.mPivotX = NaN.0F;
    this.mPivotY = NaN.0F;
    switch (paramInt)
    {
    default: 
      break;
    case 2: 
      this.rotation = (90.0F + paramFloat);
      break;
    case 1: 
      this.rotation = (paramFloat - 90.0F);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/MotionConstrainedPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */