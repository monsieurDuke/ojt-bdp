package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues.AttributesType;
import androidx.constraintlayout.core.motion.utils.Utils;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MotionKeyCycle
  extends MotionKey
{
  public static final int KEY_TYPE = 4;
  static final String NAME = "KeyCycle";
  public static final int SHAPE_BOUNCE = 6;
  public static final int SHAPE_COS_WAVE = 5;
  public static final int SHAPE_REVERSE_SAW_WAVE = 4;
  public static final int SHAPE_SAW_WAVE = 3;
  public static final int SHAPE_SIN_WAVE = 0;
  public static final int SHAPE_SQUARE_WAVE = 1;
  public static final int SHAPE_TRIANGLE_WAVE = 2;
  private static final String TAG = "KeyCycle";
  public static final String WAVE_OFFSET = "waveOffset";
  public static final String WAVE_PERIOD = "wavePeriod";
  public static final String WAVE_PHASE = "wavePhase";
  public static final String WAVE_SHAPE = "waveShape";
  private float mAlpha = NaN.0F;
  private int mCurveFit = 0;
  private String mCustomWaveShape = null;
  private float mElevation = NaN.0F;
  private float mProgress = NaN.0F;
  private float mRotation = NaN.0F;
  private float mRotationX = NaN.0F;
  private float mRotationY = NaN.0F;
  private float mScaleX = NaN.0F;
  private float mScaleY = NaN.0F;
  private String mTransitionEasing = null;
  private float mTransitionPathRotate = NaN.0F;
  private float mTranslationX = NaN.0F;
  private float mTranslationY = NaN.0F;
  private float mTranslationZ = NaN.0F;
  private float mWaveOffset = 0.0F;
  private float mWavePeriod = NaN.0F;
  private float mWavePhase = 0.0F;
  private int mWaveShape = -1;
  
  public MotionKeyCycle()
  {
    this.mType = 4;
    this.mCustom = new HashMap();
  }
  
  public void addCycleValues(HashMap<String, KeyCycleOscillator> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = (String)localIterator.next();
      if (((String)localObject1).startsWith("CUSTOM"))
      {
        Object localObject2 = ((String)localObject1).substring("CUSTOM".length() + 1);
        localObject2 = (CustomVariable)this.mCustom.get(localObject2);
        if ((localObject2 != null) && (((CustomVariable)localObject2).getType() == 901))
        {
          localObject1 = (KeyCycleOscillator)paramHashMap.get(localObject1);
          if (localObject1 != null) {
            ((KeyCycleOscillator)localObject1).setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, ((CustomVariable)localObject2).getValueToInterpolate(), localObject2);
          }
        }
      }
      else
      {
        float f = getValue((String)localObject1);
        if (!Float.isNaN(f))
        {
          localObject1 = (KeyCycleOscillator)paramHashMap.get(localObject1);
          if (localObject1 != null) {
            ((KeyCycleOscillator)localObject1).setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, -1, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, f);
          }
        }
      }
    }
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  public MotionKey clone()
  {
    return null;
  }
  
  public void dump()
  {
    System.out.println("MotionKeyCycle{mWaveShape=" + this.mWaveShape + ", mWavePeriod=" + this.mWavePeriod + ", mWaveOffset=" + this.mWaveOffset + ", mWavePhase=" + this.mWavePhase + ", mRotation=" + this.mRotation + '}');
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
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("visibility"))
      {
        i = 1;
        break label515;
        if (paramString.equals("waveShape"))
        {
          i = 17;
          break label515;
          if (paramString.equals("pathRotate"))
          {
            i = 14;
            break label515;
            if (paramString.equals("curveFit"))
            {
              i = 0;
              break label515;
              if (paramString.equals("phase"))
              {
                i = 18;
                break label515;
                if (paramString.equals("alpha"))
                {
                  i = 2;
                  break label515;
                  if (paramString.equals("scaleY"))
                  {
                    i = 10;
                    break label515;
                    if (paramString.equals("scaleX"))
                    {
                      i = 9;
                      break label515;
                      if (paramString.equals("pivotY"))
                      {
                        i = 12;
                        break label515;
                        if (paramString.equals("pivotX"))
                        {
                          i = 11;
                          break label515;
                          if (paramString.equals("period"))
                          {
                            i = 16;
                            break label515;
                            if (paramString.equals("progress"))
                            {
                              i = 13;
                              break label515;
                              if (paramString.equals("offset"))
                              {
                                i = 19;
                                break label515;
                                if (paramString.equals("translationZ"))
                                {
                                  i = 5;
                                  break label515;
                                  if (paramString.equals("translationY"))
                                  {
                                    i = 4;
                                    break label515;
                                    if (paramString.equals("translationX"))
                                    {
                                      i = 3;
                                      break label515;
                                      if (paramString.equals("rotationZ"))
                                      {
                                        i = 8;
                                        break label515;
                                        if (paramString.equals("rotationY"))
                                        {
                                          i = 7;
                                          break label515;
                                          if (paramString.equals("rotationX"))
                                          {
                                            i = 6;
                                            break label515;
                                            if (paramString.equals("easing"))
                                            {
                                              i = 15;
                                              break label515;
                                              if (paramString.equals("customWave"))
                                              {
                                                i = 20;
                                                break label515;
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
                }
              }
            }
          }
        }
      }
    }
    int i = -1;
    switch (i)
    {
    default: 
      return -1;
    case 20: 
      return 422;
    case 19: 
      return 424;
    case 18: 
      return 425;
    case 17: 
      return 421;
    case 16: 
      return 423;
    case 15: 
      return 420;
    case 14: 
      return 416;
    case 13: 
      return 315;
    case 12: 
      return 314;
    case 11: 
      return 313;
    case 10: 
      return 312;
    case 9: 
      return 311;
    case 8: 
      return 310;
    case 7: 
      return 309;
    case 6: 
      return 308;
    case 5: 
      return 306;
    case 4: 
      return 305;
    case 3: 
      return 304;
    case 2: 
      return 403;
    case 1: 
      label515:
      return 402;
    }
    return 401;
  }
  
  public float getValue(String paramString)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("pathRotate"))
      {
        i = 5;
        break label347;
        if (paramString.equals("phase"))
        {
          i = 12;
          break label347;
          if (paramString.equals("alpha"))
          {
            i = 0;
            break label347;
            if (paramString.equals("elevation"))
            {
              i = 1;
              break label347;
              if (paramString.equals("scaleY"))
              {
                i = 7;
                break label347;
                if (paramString.equals("scaleX"))
                {
                  i = 6;
                  break label347;
                  if (paramString.equals("progress"))
                  {
                    i = 13;
                    break label347;
                    if (paramString.equals("offset"))
                    {
                      i = 11;
                      break label347;
                      if (paramString.equals("translationZ"))
                      {
                        i = 10;
                        break label347;
                        if (paramString.equals("translationY"))
                        {
                          i = 9;
                          break label347;
                          if (paramString.equals("translationX"))
                          {
                            i = 8;
                            break label347;
                            if (paramString.equals("rotationZ"))
                            {
                              i = 2;
                              break label347;
                              if (paramString.equals("rotationY"))
                              {
                                i = 4;
                                break label347;
                                if (paramString.equals("rotationX"))
                                {
                                  i = 3;
                                  break label347;
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
    int i = -1;
    switch (i)
    {
    default: 
      return NaN.0F;
    case 13: 
      return this.mProgress;
    case 12: 
      return this.mWavePhase;
    case 11: 
      return this.mWaveOffset;
    case 10: 
      return this.mTranslationZ;
    case 9: 
      return this.mTranslationY;
    case 8: 
      return this.mTranslationX;
    case 7: 
      return this.mScaleY;
    case 6: 
      return this.mScaleX;
    case 5: 
      return this.mTransitionPathRotate;
    case 4: 
      return this.mRotationY;
    case 3: 
      return this.mRotationX;
    case 2: 
      return this.mRotation;
    case 1: 
      label347:
      return this.mElevation;
    }
    return this.mAlpha;
  }
  
  public void printAttributes()
  {
    Object localObject = new HashSet();
    getAttributeNames((HashSet)localObject);
    Utils.log(" ------------- " + this.mFramePosition + " -------------");
    Utils.log("MotionKeyCycle{Shape=" + this.mWaveShape + ", Period=" + this.mWavePeriod + ", Offset=" + this.mWaveOffset + ", Phase=" + this.mWavePhase + '}');
    localObject = (String[])((HashSet)localObject).toArray(new String[0]);
    for (int i = 0; i < localObject.length; i++)
    {
      TypedValues.AttributesType.getId(localObject[i]);
      Utils.log(localObject[i] + ":" + getValue(localObject[i]));
    }
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramFloat);
    case 425: 
      this.mWavePhase = paramFloat;
      break;
    case 424: 
      this.mWaveOffset = paramFloat;
      break;
    case 423: 
      this.mWavePeriod = paramFloat;
      break;
    case 416: 
      this.mTransitionPathRotate = paramFloat;
      break;
    case 403: 
      this.mAlpha = paramFloat;
      break;
    case 315: 
      this.mProgress = paramFloat;
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
    }
    return true;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      if (setValue(paramInt1, paramInt2)) {
        return true;
      }
      break;
    case 421: 
      this.mWaveShape = paramInt2;
      return true;
    case 401: 
      this.mCurveFit = paramInt2;
      return true;
    }
    return super.setValue(paramInt1, paramInt2);
  }
  
  public boolean setValue(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    case 421: 
    default: 
      return super.setValue(paramInt, paramString);
    case 422: 
      this.mCustomWaveShape = paramString;
      return true;
    }
    this.mTransitionEasing = paramString;
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/key/MotionKeyCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */