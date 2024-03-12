package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet.CustomVarSet;
import androidx.constraintlayout.core.motion.utils.TypedValues.CycleType;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MotionKeyTimeCycle
  extends MotionKey
{
  public static final int KEY_TYPE = 3;
  static final String NAME = "KeyTimeCycle";
  private static final String TAG = "KeyTimeCycle";
  private float mAlpha = NaN.0F;
  private int mCurveFit = -1;
  private String mCustomWaveShape = null;
  private float mElevation = NaN.0F;
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
  private float mWaveOffset = 0.0F;
  private float mWavePeriod = NaN.0F;
  private int mWaveShape = 0;
  
  public MotionKeyTimeCycle()
  {
    this.mType = 3;
    this.mCustom = new HashMap();
  }
  
  public void addTimeValues(HashMap<String, TimeCycleSplineSet> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      TimeCycleSplineSet localTimeCycleSplineSet = (TimeCycleSplineSet)paramHashMap.get(localObject);
      if (localTimeCycleSplineSet != null)
      {
        boolean bool = ((String)localObject).startsWith("CUSTOM");
        int i = 1;
        if (bool)
        {
          localObject = ((String)localObject).substring("CUSTOM".length() + 1);
          localObject = (CustomVariable)this.mCustom.get(localObject);
          if (localObject != null) {
            ((TimeCycleSplineSet.CustomVarSet)localTimeCycleSplineSet).setPoint(this.mFramePosition, (CustomVariable)localObject, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
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
              i = 4;
              break label429;
              if (((String)localObject).equals("alpha"))
              {
                i = 0;
                break label429;
                if (((String)localObject).equals("elevation"))
                {
                  i = 10;
                  break label429;
                  if (((String)localObject).equals("scaleY"))
                  {
                    i = 6;
                    break label429;
                    if (((String)localObject).equals("scaleX"))
                    {
                      i = 5;
                      break label429;
                      if (((String)localObject).equals("progress"))
                      {
                        i = 11;
                        break label429;
                        if (((String)localObject).equals("translationZ"))
                        {
                          i = 9;
                          break label429;
                          if (((String)localObject).equals("translationY"))
                          {
                            i = 8;
                            break label429;
                            if (((String)localObject).equals("translationX"))
                            {
                              i = 7;
                              break label429;
                              if (((String)localObject).equals("rotationZ"))
                              {
                                i = 3;
                                break label429;
                                if (((String)localObject).equals("rotationY"))
                                {
                                  i = 2;
                                  break label429;
                                  if (((String)localObject).equals("rotationX")) {
                                    break label429;
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
            Utils.loge("KeyTimeCycles", "UNKNOWN addValues \"" + (String)localObject + "\"");
            break;
          case 11: 
            if (!Float.isNaN(this.mProgress)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mProgress, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 10: 
            if (!Float.isNaN(this.mTranslationZ)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 9: 
            if (!Float.isNaN(this.mTranslationZ)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 8: 
            if (!Float.isNaN(this.mTranslationY)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mTranslationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 7: 
            if (!Float.isNaN(this.mTranslationX)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mTranslationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 6: 
            if (!Float.isNaN(this.mScaleY)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mScaleY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 5: 
            if (!Float.isNaN(this.mScaleX)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mScaleX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 4: 
            if (!Float.isNaN(this.mTransitionPathRotate)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mTransitionPathRotate, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 3: 
            if (!Float.isNaN(this.mRotation)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mRotation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 2: 
            if (!Float.isNaN(this.mRotationY)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mRotationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 1: 
            if (!Float.isNaN(this.mRotationX)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mRotationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 0: 
            label429:
            if (!Float.isNaN(this.mAlpha)) {
              localTimeCycleSplineSet.setPoint(this.mFramePosition, this.mAlpha, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          }
        }
      }
    }
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  public MotionKey clone()
  {
    return new MotionKeyTimeCycle().copy(this);
  }
  
  public MotionKeyTimeCycle copy(MotionKey paramMotionKey)
  {
    super.copy(paramMotionKey);
    paramMotionKey = (MotionKeyTimeCycle)paramMotionKey;
    this.mTransitionEasing = paramMotionKey.mTransitionEasing;
    this.mCurveFit = paramMotionKey.mCurveFit;
    this.mWaveShape = paramMotionKey.mWaveShape;
    this.mWavePeriod = paramMotionKey.mWavePeriod;
    this.mWaveOffset = paramMotionKey.mWaveOffset;
    this.mProgress = paramMotionKey.mProgress;
    this.mAlpha = paramMotionKey.mAlpha;
    this.mElevation = paramMotionKey.mElevation;
    this.mRotation = paramMotionKey.mRotation;
    this.mTransitionPathRotate = paramMotionKey.mTransitionPathRotate;
    this.mRotationX = paramMotionKey.mRotationX;
    this.mRotationY = paramMotionKey.mRotationY;
    this.mScaleX = paramMotionKey.mScaleX;
    this.mScaleY = paramMotionKey.mScaleY;
    this.mTranslationX = paramMotionKey.mTranslationX;
    this.mTranslationY = paramMotionKey.mTranslationY;
    this.mTranslationZ = paramMotionKey.mTranslationZ;
    return this;
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
    if (!Float.isNaN(this.mScaleX)) {
      paramHashSet.add("scaleX");
    }
    if (!Float.isNaN(this.mScaleY)) {
      paramHashSet.add("scaleY");
    }
    if (!Float.isNaN(this.mTransitionPathRotate)) {
      paramHashSet.add("pathRotate");
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
  
  public int getId(String paramString)
  {
    return TypedValues.CycleType.getId(paramString);
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramFloat);
    case 424: 
      this.mWaveOffset = toFloat(Float.valueOf(paramFloat));
      break;
    case 423: 
      this.mWavePeriod = toFloat(Float.valueOf(paramFloat));
      break;
    case 416: 
      this.mTransitionPathRotate = toFloat(Float.valueOf(paramFloat));
      break;
    case 403: 
      this.mAlpha = paramFloat;
      break;
    case 401: 
      this.mCurveFit = toInt(Float.valueOf(paramFloat));
      break;
    case 315: 
      this.mProgress = toFloat(Float.valueOf(paramFloat));
      break;
    case 312: 
      this.mScaleY = toFloat(Float.valueOf(paramFloat));
      break;
    case 311: 
      this.mScaleX = toFloat(Float.valueOf(paramFloat));
      break;
    case 310: 
      this.mRotation = toFloat(Float.valueOf(paramFloat));
      break;
    case 309: 
      this.mRotationY = toFloat(Float.valueOf(paramFloat));
      break;
    case 308: 
      this.mRotationX = toFloat(Float.valueOf(paramFloat));
      break;
    case 307: 
      this.mElevation = toFloat(Float.valueOf(paramFloat));
      break;
    case 306: 
      this.mTranslationZ = toFloat(Float.valueOf(paramFloat));
      break;
    case 305: 
      this.mTranslationY = toFloat(Float.valueOf(paramFloat));
      break;
    case 304: 
      this.mTranslationX = toFloat(Float.valueOf(paramFloat));
    }
    return true;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return super.setValue(paramInt1, paramInt2);
    case 421: 
      this.mWaveShape = paramInt2;
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
    case 421: 
      this.mWaveShape = 7;
      this.mCustomWaveShape = paramString;
      break;
    case 420: 
      this.mTransitionEasing = paramString;
    }
    return true;
  }
  
  public boolean setValue(int paramInt, boolean paramBoolean)
  {
    return super.setValue(paramInt, paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/key/MotionKeyTimeCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */