package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewSpline.CustomSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintSet.Constraint;
import androidx.constraintlayout.widget.ConstraintSet.Motion;
import androidx.constraintlayout.widget.ConstraintSet.PropertySet;
import androidx.constraintlayout.widget.ConstraintSet.Transform;
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
  LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap();
  private float elevation = 0.0F;
  private float height;
  private int mAnimateRelativeTo = -1;
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
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap, int paramInt)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    label1058:
    label1061:
    label1087:
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ViewSpline localViewSpline = (ViewSpline)paramHashMap.get(str);
      int i = -1;
      switch (str.hashCode())
      {
      }
      for (;;)
      {
        break;
        if (str.equals("alpha"))
        {
          i = 0;
          break;
          if (str.equals("transitionPathRotate"))
          {
            i = 7;
            break;
            if (str.equals("elevation"))
            {
              i = 1;
              break;
              if (str.equals("rotation"))
              {
                i = 2;
                break;
                if (str.equals("transformPivotY"))
                {
                  i = 6;
                  break;
                  if (str.equals("transformPivotX"))
                  {
                    i = 5;
                    break;
                    if (str.equals("scaleY"))
                    {
                      i = 10;
                      break;
                      if (str.equals("scaleX"))
                      {
                        i = 9;
                        break;
                        if (str.equals("progress"))
                        {
                          i = 8;
                          break;
                          if (str.equals("translationZ"))
                          {
                            i = 13;
                            break;
                            if (str.equals("translationY"))
                            {
                              i = 12;
                              break;
                              if (str.equals("translationX"))
                              {
                                i = 11;
                                break;
                                if (str.equals("rotationY"))
                                {
                                  i = 4;
                                  break;
                                  if (str.equals("rotationX")) {
                                    i = 3;
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
      }
      float f1 = 1.0F;
      float f12 = 0.0F;
      float f9 = 0.0F;
      float f6 = 0.0F;
      float f2 = 0.0F;
      float f8 = 0.0F;
      float f7 = 0.0F;
      float f5 = 0.0F;
      float f11 = 0.0F;
      float f3 = 0.0F;
      float f10 = 0.0F;
      float f4 = 0.0F;
      Object localObject;
      switch (i)
      {
      default: 
        if (!str.startsWith("CUSTOM")) {
          break label1061;
        }
        localObject = str.split(",")[1];
        if (!this.attributes.containsKey(localObject)) {
          break label1058;
        }
        localObject = (ConstraintAttribute)this.attributes.get(localObject);
        if ((localViewSpline instanceof ViewSpline.CustomSet)) {
          ((ViewSpline.CustomSet)localViewSpline).setPoint(paramInt, (ConstraintAttribute)localObject);
        }
        break;
      case 13: 
        if (Float.isNaN(this.translationZ)) {
          f1 = f4;
        } else {
          f1 = this.translationZ;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 12: 
        if (Float.isNaN(this.translationY)) {
          f1 = f12;
        } else {
          f1 = this.translationY;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 11: 
        if (Float.isNaN(this.translationX)) {
          f1 = f9;
        } else {
          f1 = this.translationX;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 10: 
        if (!Float.isNaN(this.scaleY)) {
          f1 = this.scaleY;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 9: 
        if (!Float.isNaN(this.scaleX)) {
          f1 = this.scaleX;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 8: 
        if (Float.isNaN(this.mProgress)) {
          f1 = f6;
        } else {
          f1 = this.mProgress;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 7: 
        if (Float.isNaN(this.mPathRotate)) {
          f1 = f2;
        } else {
          f1 = this.mPathRotate;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 6: 
        if (Float.isNaN(this.mPivotY)) {
          f1 = f8;
        } else {
          f1 = this.mPivotY;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 5: 
        if (Float.isNaN(this.mPivotX)) {
          f1 = f7;
        } else {
          f1 = this.mPivotX;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 4: 
        if (Float.isNaN(this.rotationY)) {
          f1 = f5;
        } else {
          f1 = this.rotationY;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 3: 
        if (Float.isNaN(this.rotationX)) {
          f1 = f11;
        } else {
          f1 = this.rotationX;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 2: 
        if (Float.isNaN(this.rotation)) {
          f1 = f3;
        } else {
          f1 = this.rotation;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 1: 
        if (Float.isNaN(this.elevation)) {
          f1 = f10;
        } else {
          f1 = this.elevation;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      case 0: 
        if (!Float.isNaN(this.alpha)) {
          f1 = this.alpha;
        }
        localViewSpline.setPoint(paramInt, f1);
        break;
      }
      Log.e("MotionPaths", str + " ViewSpline not a CustomSet frame = " + paramInt + ", value" + ((ConstraintAttribute)localObject).getValueToInterpolate() + localViewSpline);
      break label1087;
      Log.e("MotionPaths", "UNKNOWN spline " + str);
    }
  }
  
  public void applyParameters(View paramView)
  {
    this.visibility = paramView.getVisibility();
    float f;
    if (paramView.getVisibility() != 0) {
      f = 0.0F;
    } else {
      f = paramView.getAlpha();
    }
    this.alpha = f;
    this.applyElevation = false;
    if (Build.VERSION.SDK_INT >= 21) {
      this.elevation = paramView.getElevation();
    }
    this.rotation = paramView.getRotation();
    this.rotationX = paramView.getRotationX();
    this.rotationY = paramView.getRotationY();
    this.scaleX = paramView.getScaleX();
    this.scaleY = paramView.getScaleY();
    this.mPivotX = paramView.getPivotX();
    this.mPivotY = paramView.getPivotY();
    this.translationX = paramView.getTranslationX();
    this.translationY = paramView.getTranslationY();
    if (Build.VERSION.SDK_INT >= 21) {
      this.translationZ = paramView.getTranslationZ();
    }
  }
  
  public void applyParameters(ConstraintSet.Constraint paramConstraint)
  {
    this.mVisibilityMode = paramConstraint.propertySet.mVisibilityMode;
    this.visibility = paramConstraint.propertySet.visibility;
    float f;
    if ((paramConstraint.propertySet.visibility != 0) && (this.mVisibilityMode == 0)) {
      f = 0.0F;
    } else {
      f = paramConstraint.propertySet.alpha;
    }
    this.alpha = f;
    this.applyElevation = paramConstraint.transform.applyElevation;
    this.elevation = paramConstraint.transform.elevation;
    this.rotation = paramConstraint.transform.rotation;
    this.rotationX = paramConstraint.transform.rotationX;
    this.rotationY = paramConstraint.transform.rotationY;
    this.scaleX = paramConstraint.transform.scaleX;
    this.scaleY = paramConstraint.transform.scaleY;
    this.mPivotX = paramConstraint.transform.transformPivotX;
    this.mPivotY = paramConstraint.transform.transformPivotY;
    this.translationX = paramConstraint.transform.translationX;
    this.translationY = paramConstraint.transform.translationY;
    this.translationZ = paramConstraint.transform.translationZ;
    this.mKeyFrameEasing = Easing.getInterpolator(paramConstraint.motion.mTransitionEasing);
    this.mPathRotate = paramConstraint.motion.mPathRotate;
    this.mDrawPath = paramConstraint.motion.mDrawPath;
    this.mAnimateRelativeTo = paramConstraint.motion.mAnimateRelativeTo;
    this.mProgress = paramConstraint.propertySet.mProgress;
    Iterator localIterator = paramConstraint.mCustomConstraints.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ConstraintAttribute localConstraintAttribute = (ConstraintAttribute)paramConstraint.mCustomConstraints.get(str);
      if (localConstraintAttribute.isContinuous()) {
        this.attributes.put(str, localConstraintAttribute);
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
      paramHashSet.add("elevation");
    }
    int j = this.visibility;
    int i = paramMotionConstrainedPoint.visibility;
    if ((j != i) && (this.mVisibilityMode == 0) && ((j == 0) || (i == 0))) {
      paramHashSet.add("alpha");
    }
    if (diff(this.rotation, paramMotionConstrainedPoint.rotation)) {
      paramHashSet.add("rotation");
    }
    if ((!Float.isNaN(this.mPathRotate)) || (!Float.isNaN(paramMotionConstrainedPoint.mPathRotate))) {
      paramHashSet.add("transitionPathRotate");
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
      paramHashSet.add("transformPivotX");
    }
    if (diff(this.mPivotY, paramMotionConstrainedPoint.mPivotY)) {
      paramHashSet.add("transformPivotY");
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
    int j = 0;
    int i = 0;
    while (i < paramArrayOfInt.length)
    {
      int k = j;
      if (paramArrayOfInt[i] < arrayOfFloat.length)
      {
        paramArrayOfDouble[j] = arrayOfFloat[paramArrayOfInt[i]];
        k = j + 1;
      }
      i++;
      j = k;
    }
  }
  
  int getCustomData(String paramString, double[] paramArrayOfDouble, int paramInt)
  {
    paramString = (ConstraintAttribute)this.attributes.get(paramString);
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
    return ((ConstraintAttribute)this.attributes.get(paramString)).numberOfInterpolatedValues();
  }
  
  boolean hasCustomData(String paramString)
  {
    return this.attributes.containsKey(paramString);
  }
  
  void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.width = paramFloat3;
    this.height = paramFloat4;
  }
  
  public void setState(Rect paramRect, View paramView, int paramInt, float paramFloat)
  {
    setBounds(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
    applyParameters(paramView);
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
  
  public void setState(Rect paramRect, ConstraintSet paramConstraintSet, int paramInt1, int paramInt2)
  {
    setBounds(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
    applyParameters(paramConstraintSet.getParameters(paramInt2));
    switch (paramInt1)
    {
    default: 
      break;
    case 2: 
    case 4: 
      float f = this.rotation + 90.0F;
      this.rotation = f;
      if (f > 180.0F) {
        this.rotation = (f - 360.0F);
      }
      break;
    case 1: 
    case 3: 
      this.rotation -= 90.0F;
    }
  }
  
  public void setState(View paramView)
  {
    setBounds(paramView.getX(), paramView.getY(), paramView.getWidth(), paramView.getHeight());
    applyParameters(paramView);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionConstrainedPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */