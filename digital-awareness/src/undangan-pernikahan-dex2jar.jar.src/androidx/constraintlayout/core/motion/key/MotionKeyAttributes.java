package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.SplineSet.CustomSpline;
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MotionKeyAttributes
  extends MotionKey
{
  private static final boolean DEBUG = false;
  public static final int KEY_TYPE = 1;
  static final String NAME = "KeyAttribute";
  private static final String TAG = "KeyAttributes";
  private float mAlpha = NaN.0F;
  private int mCurveFit = -1;
  private float mElevation = NaN.0F;
  private float mPivotX = NaN.0F;
  private float mPivotY = NaN.0F;
  private float mProgress = NaN.0F;
  private float mRotation = NaN.0F;
  private float mRotationX = NaN.0F;
  private float mRotationY = NaN.0F;
  private float mScaleX = NaN.0F;
  private float mScaleY = NaN.0F;
  private String mTransitionEasing;
  private float mTransitionPathRotate = NaN.0F;
  private float mTranslationX = NaN.0F;
  private float mTranslationY = NaN.0F;
  private float mTranslationZ = NaN.0F;
  private int mVisibility = 0;
  
  public MotionKeyAttributes()
  {
    this.mType = 1;
    this.mCustom = new HashMap();
  }
  
  private float getFloatValue(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return NaN.0F;
    case 316: 
      return this.mTransitionPathRotate;
    case 315: 
      return this.mProgress;
    case 314: 
      return this.mPivotY;
    case 313: 
      return this.mPivotX;
    case 312: 
      return this.mScaleY;
    case 311: 
      return this.mScaleX;
    case 310: 
      return this.mRotation;
    case 309: 
      return this.mRotationY;
    case 308: 
      return this.mRotationX;
    case 307: 
      return this.mElevation;
    case 306: 
      return this.mTranslationZ;
    case 305: 
      return this.mTranslationY;
    case 304: 
      return this.mTranslationX;
    case 303: 
      return this.mAlpha;
    }
    return this.mFramePosition;
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      SplineSet localSplineSet = (SplineSet)paramHashMap.get(localObject);
      if (localSplineSet != null)
      {
        boolean bool = ((String)localObject).startsWith("CUSTOM");
        int i = 1;
        if (bool)
        {
          localObject = ((String)localObject).substring("CUSTOM".length() + 1);
          localObject = (CustomVariable)this.mCustom.get(localObject);
          if (localObject != null) {
            ((SplineSet.CustomSpline)localSplineSet).setPoint(this.mFramePosition, (CustomVariable)localObject);
          }
        }
        else
        {
          switch (((String)localObject).hashCode())
          {
          }
          for (;;)
          {
            break;
            if (((String)localObject).equals("pathRotate"))
            {
              i = 7;
              break label465;
              if (((String)localObject).equals("alpha"))
              {
                i = 0;
                break label465;
                if (((String)localObject).equals("elevation"))
                {
                  break label465;
                  if (((String)localObject).equals("scaleY"))
                  {
                    i = 9;
                    break label465;
                    if (((String)localObject).equals("scaleX"))
                    {
                      i = 8;
                      break label465;
                      if (((String)localObject).equals("pivotY"))
                      {
                        i = 6;
                        break label465;
                        if (((String)localObject).equals("pivotX"))
                        {
                          i = 5;
                          break label465;
                          if (((String)localObject).equals("progress"))
                          {
                            i = 13;
                            break label465;
                            if (((String)localObject).equals("translationZ"))
                            {
                              i = 12;
                              break label465;
                              if (((String)localObject).equals("translationY"))
                              {
                                i = 11;
                                break label465;
                                if (((String)localObject).equals("translationX"))
                                {
                                  i = 10;
                                  break label465;
                                  if (((String)localObject).equals("rotationZ"))
                                  {
                                    i = 2;
                                    break label465;
                                    if (((String)localObject).equals("rotationY"))
                                    {
                                      i = 4;
                                      break label465;
                                      if (((String)localObject).equals("rotationX"))
                                      {
                                        i = 3;
                                        break label465;
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
          i = -1;
          switch (i)
          {
          default: 
            System.err.println("not supported by KeyAttributes " + (String)localObject);
            break;
          case 13: 
            if (!Float.isNaN(this.mProgress)) {
              localSplineSet.setPoint(this.mFramePosition, this.mProgress);
            }
            break;
          case 12: 
            if (!Float.isNaN(this.mTranslationZ)) {
              localSplineSet.setPoint(this.mFramePosition, this.mTranslationZ);
            }
            break;
          case 11: 
            if (!Float.isNaN(this.mTranslationY)) {
              localSplineSet.setPoint(this.mFramePosition, this.mTranslationY);
            }
            break;
          case 10: 
            if (!Float.isNaN(this.mTranslationX)) {
              localSplineSet.setPoint(this.mFramePosition, this.mTranslationX);
            }
            break;
          case 9: 
            if (!Float.isNaN(this.mScaleY)) {
              localSplineSet.setPoint(this.mFramePosition, this.mScaleY);
            }
            break;
          case 8: 
            if (!Float.isNaN(this.mScaleX)) {
              localSplineSet.setPoint(this.mFramePosition, this.mScaleX);
            }
            break;
          case 7: 
            if (!Float.isNaN(this.mTransitionPathRotate)) {
              localSplineSet.setPoint(this.mFramePosition, this.mTransitionPathRotate);
            }
            break;
          case 6: 
            if (!Float.isNaN(this.mRotationY)) {
              localSplineSet.setPoint(this.mFramePosition, this.mPivotY);
            }
            break;
          case 5: 
            if (!Float.isNaN(this.mRotationX)) {
              localSplineSet.setPoint(this.mFramePosition, this.mPivotX);
            }
            break;
          case 4: 
            if (!Float.isNaN(this.mRotationY)) {
              localSplineSet.setPoint(this.mFramePosition, this.mRotationY);
            }
            break;
          case 3: 
            if (!Float.isNaN(this.mRotationX)) {
              localSplineSet.setPoint(this.mFramePosition, this.mRotationX);
            }
            break;
          case 2: 
            if (!Float.isNaN(this.mRotation)) {
              localSplineSet.setPoint(this.mFramePosition, this.mRotation);
            }
            break;
          case 1: 
            if (!Float.isNaN(this.mElevation)) {
              localSplineSet.setPoint(this.mFramePosition, this.mElevation);
            }
            break;
          case 0: 
            label465:
            if (!Float.isNaN(this.mAlpha)) {
              localSplineSet.setPoint(this.mFramePosition, this.mAlpha);
            }
            break;
          }
        }
      }
    }
  }
  
  public MotionKey clone()
  {
    return null;
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet)
  {
    if (!Float.isNaN(this.mAlpha)) {
      paramHashSet.add("alpha");
    }
    if (!Float.isNaN(this.mElevation)) {
      paramHashSet.add("elevation");
    }
    if (!Float.isNaN(this.mRotation)) {
      paramHashSet.add("rotationZ");
    }
    if (!Float.isNaN(this.mRotationX)) {
      paramHashSet.add("rotationX");
    }
    if (!Float.isNaN(this.mRotationY)) {
      paramHashSet.add("rotationY");
    }
    if (!Float.isNaN(this.mPivotX)) {
      paramHashSet.add("pivotX");
    }
    if (!Float.isNaN(this.mPivotY)) {
      paramHashSet.add("pivotY");
    }
    if (!Float.isNaN(this.mTranslationX)) {
      paramHashSet.add("translationX");
    }
    if (!Float.isNaN(this.mTranslationY)) {
      paramHashSet.add("translationY");
    }
    if (!Float.isNaN(this.mTranslationZ)) {
      paramHashSet.add("translationZ");
    }
    if (!Float.isNaN(this.mTransitionPathRotate)) {
      paramHashSet.add("pathRotate");
    }
    if (!Float.isNaN(this.mScaleX)) {
      paramHashSet.add("scaleX");
    }
    if (!Float.isNaN(this.mScaleY)) {
      paramHashSet.add("scaleY");
    }
    if (!Float.isNaN(this.mProgress)) {
      paramHashSet.add("progress");
    }
    if (this.mCustom.size() > 0)
    {
      Iterator localIterator = this.mCustom.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramHashSet.add("CUSTOM," + str);
      }
    }
  }
  
  public int getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public int getId(String paramString)
  {
    return TypedValues.AttributesType.getId(paramString);
  }
  
  public void printAttributes()
  {
    Object localObject = new HashSet();
    getAttributeNames((HashSet)localObject);
    System.out.println(" ------------- " + this.mFramePosition + " -------------");
    localObject = (String[])((HashSet)localObject).toArray(new String[0]);
    for (int i = 0; i < localObject.length; i++)
    {
      int j = TypedValues.AttributesType.getId(localObject[i]);
      System.out.println(localObject[i] + ":" + getFloatValue(j));
    }
  }
  
  public void setInterpolation(HashMap<String, Integer> paramHashMap)
  {
    if (!Float.isNaN(this.mAlpha)) {
      paramHashMap.put("alpha", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mElevation)) {
      paramHashMap.put("elevation", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotation)) {
      paramHashMap.put("rotationZ", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotationX)) {
      paramHashMap.put("rotationX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotationY)) {
      paramHashMap.put("rotationY", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mPivotX)) {
      paramHashMap.put("pivotX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mPivotY)) {
      paramHashMap.put("pivotY", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mTranslationX)) {
      paramHashMap.put("translationX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mTranslationY)) {
      paramHashMap.put("translationY", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mTranslationZ)) {
      paramHashMap.put("translationZ", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mTransitionPathRotate)) {
      paramHashMap.put("pathRotate", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mScaleX)) {
      paramHashMap.put("scaleX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mScaleY)) {
      paramHashMap.put("scaleY", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mProgress)) {
      paramHashMap.put("progress", Integer.valueOf(this.mCurveFit));
    }
    if (this.mCustom.size() > 0)
    {
      Iterator localIterator = this.mCustom.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramHashMap.put("CUSTOM," + str, Integer.valueOf(this.mCurveFit));
      }
    }
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramFloat);
    case 316: 
      this.mTransitionPathRotate = paramFloat;
      break;
    case 315: 
      this.mProgress = paramFloat;
      break;
    case 314: 
      this.mPivotY = paramFloat;
      break;
    case 313: 
      this.mPivotX = paramFloat;
      break;
    case 312: 
      this.mScaleY = paramFloat;
      break;
    case 311: 
      this.mScaleX = paramFloat;
      break;
    case 310: 
      this.mRotation = paramFloat;
      break;
    case 309: 
      this.mRotationY = paramFloat;
      break;
    case 308: 
      this.mRotationX = paramFloat;
      break;
    case 307: 
      this.mElevation = paramFloat;
      break;
    case 306: 
      this.mTranslationZ = paramFloat;
      break;
    case 305: 
      this.mTranslationY = paramFloat;
      break;
    case 304: 
      this.mTranslationX = paramFloat;
      break;
    case 303: 
      this.mAlpha = paramFloat;
      break;
    case 100: 
      this.mTransitionPathRotate = paramFloat;
    }
    return true;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      if (!setValue(paramInt1, paramInt2)) {
        return super.setValue(paramInt1, paramInt2);
      }
      break;
    case 302: 
      this.mVisibility = paramInt2;
      break;
    case 301: 
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
    case 317: 
      this.mTransitionEasing = paramString;
      break;
    case 101: 
      this.mTargetString = paramString;
    }
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/key/MotionKeyAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */