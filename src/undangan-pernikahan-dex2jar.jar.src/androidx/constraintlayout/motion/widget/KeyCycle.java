package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintAttribute.AttributeType;
import androidx.constraintlayout.widget.R.styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyCycle
  extends Key
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
  private int mWaveVariesBy = -1;
  
  public KeyCycle()
  {
    this.mType = 4;
    this.mCustomConstraints = new HashMap();
  }
  
  public void addCycleValues(HashMap<String, ViewOscillator> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = (String)localIterator.next();
      Object localObject1;
      if (((String)localObject2).startsWith("CUSTOM"))
      {
        localObject1 = ((String)localObject2).substring("CUSTOM".length() + 1);
        localObject1 = (ConstraintAttribute)this.mCustomConstraints.get(localObject1);
        if ((localObject1 != null) && (((ConstraintAttribute)localObject1).getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE))
        {
          localObject2 = (ViewOscillator)paramHashMap.get(localObject2);
          if (localObject2 != null) {
            ((ViewOscillator)localObject2).setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, ((ConstraintAttribute)localObject1).getValueToInterpolate(), localObject1);
          }
        }
      }
      else
      {
        float f = getValue((String)localObject2);
        if (!Float.isNaN(f))
        {
          localObject1 = (ViewOscillator)paramHashMap.get(localObject2);
          if (localObject1 != null) {
            ((ViewOscillator)localObject1).setPoint(this.mFramePosition, this.mWaveShape, this.mCustomWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, this.mWavePhase, f);
          }
        }
      }
    }
  }
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap)
  {
    Debug.logStack("KeyCycle", "add " + paramHashMap.size() + " values", 2);
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      SplineSet localSplineSet = (SplineSet)paramHashMap.get(str);
      if (localSplineSet != null)
      {
        int i = -1;
        switch (str.hashCode())
        {
        }
        for (;;)
        {
          break;
          if (str.equals("wavePhase"))
          {
            i = 12;
            break;
            if (str.equals("waveOffset"))
            {
              i = 11;
              break;
              if (str.equals("alpha"))
              {
                i = 0;
                break;
                if (str.equals("transitionPathRotate"))
                {
                  i = 5;
                  break;
                  if (str.equals("elevation"))
                  {
                    i = 1;
                    break;
                    if (str.equals("rotation"))
                    {
                      i = 2;
                      break;
                      if (str.equals("scaleY"))
                      {
                        i = 7;
                        break;
                        if (str.equals("scaleX"))
                        {
                          i = 6;
                          break;
                          if (str.equals("progress"))
                          {
                            i = 13;
                            break;
                            if (str.equals("translationZ"))
                            {
                              i = 10;
                              break;
                              if (str.equals("translationY"))
                              {
                                i = 9;
                                break;
                                if (str.equals("translationX"))
                                {
                                  i = 8;
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
        switch (i)
        {
        default: 
          if (!str.startsWith("CUSTOM")) {
            Log.v("WARNING KeyCycle", "  UNKNOWN  " + str);
          }
          break;
        case 13: 
          localSplineSet.setPoint(this.mFramePosition, this.mProgress);
          break;
        case 12: 
          localSplineSet.setPoint(this.mFramePosition, this.mWavePhase);
          break;
        case 11: 
          localSplineSet.setPoint(this.mFramePosition, this.mWaveOffset);
          break;
        case 10: 
          localSplineSet.setPoint(this.mFramePosition, this.mTranslationZ);
          break;
        case 9: 
          localSplineSet.setPoint(this.mFramePosition, this.mTranslationY);
          break;
        case 8: 
          localSplineSet.setPoint(this.mFramePosition, this.mTranslationX);
          break;
        case 7: 
          localSplineSet.setPoint(this.mFramePosition, this.mScaleY);
          break;
        case 6: 
          localSplineSet.setPoint(this.mFramePosition, this.mScaleX);
          break;
        case 5: 
          localSplineSet.setPoint(this.mFramePosition, this.mTransitionPathRotate);
          break;
        case 4: 
          localSplineSet.setPoint(this.mFramePosition, this.mRotationY);
          break;
        case 3: 
          localSplineSet.setPoint(this.mFramePosition, this.mRotationX);
          break;
        case 2: 
          localSplineSet.setPoint(this.mFramePosition, this.mRotation);
          break;
        case 1: 
          localSplineSet.setPoint(this.mFramePosition, this.mElevation);
          break;
        case 0: 
          localSplineSet.setPoint(this.mFramePosition, this.mAlpha);
        }
      }
    }
  }
  
  public Key clone()
  {
    return new KeyCycle().copy(this);
  }
  
  public Key copy(Key paramKey)
  {
    super.copy(paramKey);
    paramKey = (KeyCycle)paramKey;
    this.mTransitionEasing = paramKey.mTransitionEasing;
    this.mCurveFit = paramKey.mCurveFit;
    this.mWaveShape = paramKey.mWaveShape;
    this.mCustomWaveShape = paramKey.mCustomWaveShape;
    this.mWavePeriod = paramKey.mWavePeriod;
    this.mWaveOffset = paramKey.mWaveOffset;
    this.mWavePhase = paramKey.mWavePhase;
    this.mProgress = paramKey.mProgress;
    this.mWaveVariesBy = paramKey.mWaveVariesBy;
    this.mAlpha = paramKey.mAlpha;
    this.mElevation = paramKey.mElevation;
    this.mRotation = paramKey.mRotation;
    this.mTransitionPathRotate = paramKey.mTransitionPathRotate;
    this.mRotationX = paramKey.mRotationX;
    this.mRotationY = paramKey.mRotationY;
    this.mScaleX = paramKey.mScaleX;
    this.mScaleY = paramKey.mScaleY;
    this.mTranslationX = paramKey.mTranslationX;
    this.mTranslationY = paramKey.mTranslationY;
    this.mTranslationZ = paramKey.mTranslationZ;
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
      paramHashSet.add("rotation");
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
      paramHashSet.add("transitionPathRotate");
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
    if (this.mCustomConstraints.size() > 0)
    {
      Iterator localIterator = this.mCustomConstraints.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramHashSet.add("CUSTOM," + str);
      }
    }
  }
  
  public float getValue(String paramString)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("wavePhase"))
      {
        i = 12;
        break label349;
        if (paramString.equals("waveOffset"))
        {
          i = 11;
          break label349;
          if (paramString.equals("alpha"))
          {
            i = 0;
            break label349;
            if (paramString.equals("transitionPathRotate"))
            {
              i = 5;
              break label349;
              if (paramString.equals("elevation"))
              {
                i = 1;
                break label349;
                if (paramString.equals("rotation"))
                {
                  i = 2;
                  break label349;
                  if (paramString.equals("scaleY"))
                  {
                    i = 7;
                    break label349;
                    if (paramString.equals("scaleX"))
                    {
                      i = 6;
                      break label349;
                      if (paramString.equals("progress"))
                      {
                        i = 13;
                        break label349;
                        if (paramString.equals("translationZ"))
                        {
                          i = 10;
                          break label349;
                          if (paramString.equals("translationY"))
                          {
                            i = 9;
                            break label349;
                            if (paramString.equals("translationX"))
                            {
                              i = 8;
                              break label349;
                              if (paramString.equals("rotationY"))
                              {
                                i = 4;
                                break label349;
                                if (paramString.equals("rotationX"))
                                {
                                  i = 3;
                                  break label349;
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
      if (!paramString.startsWith("CUSTOM")) {
        Log.v("WARNING! KeyCycle", "  UNKNOWN  " + paramString);
      }
      break;
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
      return this.mElevation;
    case 0: 
      label349:
      return this.mAlpha;
    }
    return NaN.0F;
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet)
  {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyCycle));
  }
  
  public void setValue(String paramString, Object paramObject)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("waveShape"))
      {
        i = 17;
        break label443;
        if (paramString.equals("wavePhase"))
        {
          i = 16;
          break label443;
          if (paramString.equals("curveFit"))
          {
            i = 1;
            break label443;
            if (paramString.equals("wavePeriod"))
            {
              i = 14;
              break label443;
              if (paramString.equals("waveOffset"))
              {
                i = 15;
                break label443;
                if (paramString.equals("alpha"))
                {
                  i = 0;
                  break label443;
                  if (paramString.equals("transitionPathRotate"))
                  {
                    i = 10;
                    break label443;
                    if (paramString.equals("elevation"))
                    {
                      i = 2;
                      break label443;
                      if (paramString.equals("rotation"))
                      {
                        i = 4;
                        break label443;
                        if (paramString.equals("scaleY"))
                        {
                          i = 8;
                          break label443;
                          if (paramString.equals("scaleX"))
                          {
                            i = 7;
                            break label443;
                            if (paramString.equals("translationZ"))
                            {
                              i = 13;
                              break label443;
                              if (paramString.equals("translationY"))
                              {
                                i = 12;
                                break label443;
                                if (paramString.equals("translationX"))
                                {
                                  i = 11;
                                  break label443;
                                  if (paramString.equals("rotationY"))
                                  {
                                    i = 6;
                                    break label443;
                                    if (paramString.equals("rotationX"))
                                    {
                                      i = 5;
                                      break label443;
                                      if (paramString.equals("transitionEasing"))
                                      {
                                        i = 9;
                                        break label443;
                                        if (paramString.equals("motionProgress"))
                                        {
                                          i = 3;
                                          break label443;
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
      break;
    case 17: 
      if ((paramObject instanceof Integer))
      {
        this.mWaveShape = toInt(paramObject);
      }
      else
      {
        this.mWaveShape = 7;
        this.mCustomWaveShape = paramObject.toString();
      }
      break;
    case 16: 
      this.mWavePhase = toFloat(paramObject);
      break;
    case 15: 
      this.mWaveOffset = toFloat(paramObject);
      break;
    case 14: 
      this.mWavePeriod = toFloat(paramObject);
      break;
    case 13: 
      this.mTranslationZ = toFloat(paramObject);
      break;
    case 12: 
      this.mTranslationY = toFloat(paramObject);
      break;
    case 11: 
      this.mTranslationX = toFloat(paramObject);
      break;
    case 10: 
      this.mTransitionPathRotate = toFloat(paramObject);
      break;
    case 9: 
      this.mTransitionEasing = paramObject.toString();
      break;
    case 8: 
      this.mScaleY = toFloat(paramObject);
      break;
    case 7: 
      this.mScaleX = toFloat(paramObject);
      break;
    case 6: 
      this.mRotationY = toFloat(paramObject);
      break;
    case 5: 
      this.mRotationX = toFloat(paramObject);
      break;
    case 4: 
      this.mRotation = toFloat(paramObject);
      break;
    case 3: 
      this.mProgress = toFloat(paramObject);
      break;
    case 2: 
      this.mElevation = toFloat(paramObject);
      break;
    case 1: 
      this.mCurveFit = toInt(paramObject);
      break;
    case 0: 
      label443:
      this.mAlpha = toFloat(paramObject);
    }
  }
  
  private static class Loader
  {
    private static final int ANDROID_ALPHA = 9;
    private static final int ANDROID_ELEVATION = 10;
    private static final int ANDROID_ROTATION = 11;
    private static final int ANDROID_ROTATION_X = 12;
    private static final int ANDROID_ROTATION_Y = 13;
    private static final int ANDROID_SCALE_X = 15;
    private static final int ANDROID_SCALE_Y = 16;
    private static final int ANDROID_TRANSLATION_X = 17;
    private static final int ANDROID_TRANSLATION_Y = 18;
    private static final int ANDROID_TRANSLATION_Z = 19;
    private static final int CURVE_FIT = 4;
    private static final int FRAME_POSITION = 2;
    private static final int PROGRESS = 20;
    private static final int TARGET_ID = 1;
    private static final int TRANSITION_EASING = 3;
    private static final int TRANSITION_PATH_ROTATE = 14;
    private static final int WAVE_OFFSET = 7;
    private static final int WAVE_PERIOD = 6;
    private static final int WAVE_PHASE = 21;
    private static final int WAVE_SHAPE = 5;
    private static final int WAVE_VARIES_BY = 8;
    private static SparseIntArray mAttrMap;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mAttrMap = localSparseIntArray;
      localSparseIntArray.append(R.styleable.KeyCycle_motionTarget, 1);
      mAttrMap.append(R.styleable.KeyCycle_framePosition, 2);
      mAttrMap.append(R.styleable.KeyCycle_transitionEasing, 3);
      mAttrMap.append(R.styleable.KeyCycle_curveFit, 4);
      mAttrMap.append(R.styleable.KeyCycle_waveShape, 5);
      mAttrMap.append(R.styleable.KeyCycle_wavePeriod, 6);
      mAttrMap.append(R.styleable.KeyCycle_waveOffset, 7);
      mAttrMap.append(R.styleable.KeyCycle_waveVariesBy, 8);
      mAttrMap.append(R.styleable.KeyCycle_android_alpha, 9);
      mAttrMap.append(R.styleable.KeyCycle_android_elevation, 10);
      mAttrMap.append(R.styleable.KeyCycle_android_rotation, 11);
      mAttrMap.append(R.styleable.KeyCycle_android_rotationX, 12);
      mAttrMap.append(R.styleable.KeyCycle_android_rotationY, 13);
      mAttrMap.append(R.styleable.KeyCycle_transitionPathRotate, 14);
      mAttrMap.append(R.styleable.KeyCycle_android_scaleX, 15);
      mAttrMap.append(R.styleable.KeyCycle_android_scaleY, 16);
      mAttrMap.append(R.styleable.KeyCycle_android_translationX, 17);
      mAttrMap.append(R.styleable.KeyCycle_android_translationY, 18);
      mAttrMap.append(R.styleable.KeyCycle_android_translationZ, 19);
      mAttrMap.append(R.styleable.KeyCycle_motionProgress, 20);
      mAttrMap.append(R.styleable.KeyCycle_wavePhase, 21);
    }
    
    private static void read(KeyCycle paramKeyCycle, TypedArray paramTypedArray)
    {
      int j = paramTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramTypedArray.getIndex(i);
        switch (mAttrMap.get(k))
        {
        default: 
          StringBuilder localStringBuilder = new StringBuilder().append("unused attribute 0x");
          String str = Integer.toHexString(k);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          Log.e("KeyCycle", str + "   " + mAttrMap.get(k));
          break;
        case 21: 
          KeyCycle.access$2002(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mWavePhase) / 360.0F);
          break;
        case 20: 
          KeyCycle.access$1902(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mProgress));
          break;
        case 19: 
          if (Build.VERSION.SDK_INT >= 21) {
            KeyCycle.access$1802(paramKeyCycle, paramTypedArray.getDimension(k, paramKeyCycle.mTranslationZ));
          }
          break;
        case 18: 
          KeyCycle.access$1702(paramKeyCycle, paramTypedArray.getDimension(k, paramKeyCycle.mTranslationY));
          break;
        case 17: 
          KeyCycle.access$1602(paramKeyCycle, paramTypedArray.getDimension(k, paramKeyCycle.mTranslationX));
          break;
        case 16: 
          KeyCycle.access$1502(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mScaleY));
          break;
        case 15: 
          KeyCycle.access$1402(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mScaleX));
          break;
        case 14: 
          KeyCycle.access$1302(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mTransitionPathRotate));
          break;
        case 13: 
          KeyCycle.access$1202(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mRotationY));
          break;
        case 12: 
          KeyCycle.access$1102(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mRotationX));
          break;
        case 11: 
          KeyCycle.access$1002(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mRotation));
          break;
        case 10: 
          KeyCycle.access$902(paramKeyCycle, paramTypedArray.getDimension(k, paramKeyCycle.mElevation));
          break;
        case 9: 
          KeyCycle.access$802(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mAlpha));
          break;
        case 8: 
          KeyCycle.access$702(paramKeyCycle, paramTypedArray.getInt(k, paramKeyCycle.mWaveVariesBy));
          break;
        case 7: 
          if (paramTypedArray.peekValue(k).type == 5) {
            KeyCycle.access$602(paramKeyCycle, paramTypedArray.getDimension(k, paramKeyCycle.mWaveOffset));
          } else {
            KeyCycle.access$602(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mWaveOffset));
          }
          break;
        case 6: 
          KeyCycle.access$502(paramKeyCycle, paramTypedArray.getFloat(k, paramKeyCycle.mWavePeriod));
          break;
        case 5: 
          if (paramTypedArray.peekValue(k).type == 3)
          {
            KeyCycle.access$302(paramKeyCycle, paramTypedArray.getString(k));
            KeyCycle.access$402(paramKeyCycle, 7);
          }
          else
          {
            KeyCycle.access$402(paramKeyCycle, paramTypedArray.getInt(k, paramKeyCycle.mWaveShape));
          }
          break;
        case 4: 
          KeyCycle.access$202(paramKeyCycle, paramTypedArray.getInteger(k, paramKeyCycle.mCurveFit));
          break;
        case 3: 
          KeyCycle.access$102(paramKeyCycle, paramTypedArray.getString(k));
          break;
        case 2: 
          paramKeyCycle.mFramePosition = paramTypedArray.getInt(k, paramKeyCycle.mFramePosition);
          break;
        case 1: 
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            paramKeyCycle.mTargetId = paramTypedArray.getResourceId(k, paramKeyCycle.mTargetId);
            if (paramKeyCycle.mTargetId == -1) {
              paramKeyCycle.mTargetString = paramTypedArray.getString(k);
            }
          }
          else if (paramTypedArray.peekValue(k).type == 3)
          {
            paramKeyCycle.mTargetString = paramTypedArray.getString(k);
          }
          else
          {
            paramKeyCycle.mTargetId = paramTypedArray.getResourceId(k, paramKeyCycle.mTargetId);
          }
          break;
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */