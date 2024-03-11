package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.motion.utils.ViewTimeCycle.CustomSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R.styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyTimeCycle
  extends Key
{
  public static final int KEY_TYPE = 3;
  static final String NAME = "KeyTimeCycle";
  public static final int SHAPE_BOUNCE = 6;
  public static final int SHAPE_COS_WAVE = 5;
  public static final int SHAPE_REVERSE_SAW_WAVE = 4;
  public static final int SHAPE_SAW_WAVE = 3;
  public static final int SHAPE_SIN_WAVE = 0;
  public static final int SHAPE_SQUARE_WAVE = 1;
  public static final int SHAPE_TRIANGLE_WAVE = 2;
  private static final String TAG = "KeyTimeCycle";
  public static final String WAVE_OFFSET = "waveOffset";
  public static final String WAVE_PERIOD = "wavePeriod";
  public static final String WAVE_SHAPE = "waveShape";
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
  
  public KeyTimeCycle()
  {
    this.mType = 3;
    this.mCustomConstraints = new HashMap();
  }
  
  public void addTimeValues(HashMap<String, ViewTimeCycle> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      ViewTimeCycle localViewTimeCycle = (ViewTimeCycle)paramHashMap.get(localObject);
      if (localViewTimeCycle != null)
      {
        boolean bool = ((String)localObject).startsWith("CUSTOM");
        int i = 1;
        if (bool)
        {
          localObject = ((String)localObject).substring("CUSTOM".length() + 1);
          localObject = (ConstraintAttribute)this.mCustomConstraints.get(localObject);
          if (localObject != null) {
            ((ViewTimeCycle.CustomSet)localViewTimeCycle).setPoint(this.mFramePosition, (ConstraintAttribute)localObject, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
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
            if (((String)localObject).equals("alpha"))
            {
              i = 0;
              break label429;
              if (((String)localObject).equals("transitionPathRotate"))
              {
                i = 5;
                break label429;
                if (((String)localObject).equals("elevation"))
                {
                  break label429;
                  if (((String)localObject).equals("rotation"))
                  {
                    i = 2;
                    break label429;
                    if (((String)localObject).equals("scaleY"))
                    {
                      i = 7;
                      break label429;
                      if (((String)localObject).equals("scaleX"))
                      {
                        i = 6;
                        break label429;
                        if (((String)localObject).equals("progress"))
                        {
                          i = 11;
                          break label429;
                          if (((String)localObject).equals("translationZ"))
                          {
                            i = 10;
                            break label429;
                            if (((String)localObject).equals("translationY"))
                            {
                              i = 9;
                              break label429;
                              if (((String)localObject).equals("translationX"))
                              {
                                i = 8;
                                break label429;
                                if (((String)localObject).equals("rotationY"))
                                {
                                  i = 4;
                                  break label429;
                                  if (((String)localObject).equals("rotationX"))
                                  {
                                    i = 3;
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
            Log.e("KeyTimeCycles", "UNKNOWN addValues \"" + (String)localObject + "\"");
            break;
          case 11: 
            if (!Float.isNaN(this.mProgress)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mProgress, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 10: 
            if (!Float.isNaN(this.mTranslationZ)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 9: 
            if (!Float.isNaN(this.mTranslationY)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mTranslationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 8: 
            if (!Float.isNaN(this.mTranslationX)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mTranslationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 7: 
            if (!Float.isNaN(this.mScaleY)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mScaleY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 6: 
            if (!Float.isNaN(this.mScaleX)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mScaleX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 5: 
            if (!Float.isNaN(this.mTransitionPathRotate)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mTransitionPathRotate, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 4: 
            if (!Float.isNaN(this.mRotationY)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mRotationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 3: 
            if (!Float.isNaN(this.mRotationX)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mRotationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 2: 
            if (!Float.isNaN(this.mRotation)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mRotation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 1: 
            if (!Float.isNaN(this.mElevation)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mElevation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          case 0: 
            label429:
            if (!Float.isNaN(this.mAlpha)) {
              localViewTimeCycle.setPoint(this.mFramePosition, this.mAlpha, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
            }
            break;
          }
        }
      }
    }
  }
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap)
  {
    throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
  }
  
  public Key clone()
  {
    return new KeyTimeCycle().copy(this);
  }
  
  public Key copy(Key paramKey)
  {
    super.copy(paramKey);
    paramKey = (KeyTimeCycle)paramKey;
    this.mTransitionEasing = paramKey.mTransitionEasing;
    this.mCurveFit = paramKey.mCurveFit;
    this.mWaveShape = paramKey.mWaveShape;
    this.mWavePeriod = paramKey.mWavePeriod;
    this.mWaveOffset = paramKey.mWaveOffset;
    this.mProgress = paramKey.mProgress;
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
      paramHashSet.add("transitionPathRotate");
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
  
  public void load(Context paramContext, AttributeSet paramAttributeSet)
  {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyTimeCycle));
  }
  
  public void setInterpolation(HashMap<String, Integer> paramHashMap)
  {
    if (this.mCurveFit == -1) {
      return;
    }
    if (!Float.isNaN(this.mAlpha)) {
      paramHashMap.put("alpha", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mElevation)) {
      paramHashMap.put("elevation", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotation)) {
      paramHashMap.put("rotation", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotationX)) {
      paramHashMap.put("rotationX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mRotationY)) {
      paramHashMap.put("rotationY", Integer.valueOf(this.mCurveFit));
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
      paramHashMap.put("transitionPathRotate", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mScaleX)) {
      paramHashMap.put("scaleX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mScaleX)) {
      paramHashMap.put("scaleY", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mProgress)) {
      paramHashMap.put("progress", Integer.valueOf(this.mCurveFit));
    }
    if (this.mCustomConstraints.size() > 0)
    {
      Iterator localIterator = this.mCustomConstraints.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        paramHashMap.put("CUSTOM," + str, Integer.valueOf(this.mCurveFit));
      }
    }
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
        i = 16;
        break label409;
        if (paramString.equals("curveFit"))
        {
          i = 1;
          break label409;
          if (paramString.equals("wavePeriod"))
          {
            i = 14;
            break label409;
            if (paramString.equals("waveOffset"))
            {
              i = 15;
              break label409;
              if (paramString.equals("alpha"))
              {
                i = 0;
                break label409;
                if (paramString.equals("transitionPathRotate"))
                {
                  i = 10;
                  break label409;
                  if (paramString.equals("elevation"))
                  {
                    i = 2;
                    break label409;
                    if (paramString.equals("rotation"))
                    {
                      i = 4;
                      break label409;
                      if (paramString.equals("scaleY"))
                      {
                        i = 8;
                        break label409;
                        if (paramString.equals("scaleX"))
                        {
                          i = 7;
                          break label409;
                          if (paramString.equals("translationZ"))
                          {
                            i = 13;
                            break label409;
                            if (paramString.equals("translationY"))
                            {
                              i = 12;
                              break label409;
                              if (paramString.equals("translationX"))
                              {
                                i = 11;
                                break label409;
                                if (paramString.equals("rotationY"))
                                {
                                  i = 6;
                                  break label409;
                                  if (paramString.equals("rotationX"))
                                  {
                                    i = 5;
                                    break label409;
                                    if (paramString.equals("transitionEasing"))
                                    {
                                      i = 9;
                                      break label409;
                                      if (paramString.equals("motionProgress"))
                                      {
                                        i = 3;
                                        break label409;
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
    case 16: 
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
      label409:
      this.mAlpha = toFloat(paramObject);
    }
  }
  
  private static class Loader
  {
    private static final int ANDROID_ALPHA = 1;
    private static final int ANDROID_ELEVATION = 2;
    private static final int ANDROID_ROTATION = 4;
    private static final int ANDROID_ROTATION_X = 5;
    private static final int ANDROID_ROTATION_Y = 6;
    private static final int ANDROID_SCALE_X = 7;
    private static final int ANDROID_SCALE_Y = 14;
    private static final int ANDROID_TRANSLATION_X = 15;
    private static final int ANDROID_TRANSLATION_Y = 16;
    private static final int ANDROID_TRANSLATION_Z = 17;
    private static final int CURVE_FIT = 13;
    private static final int FRAME_POSITION = 12;
    private static final int PROGRESS = 18;
    private static final int TARGET_ID = 10;
    private static final int TRANSITION_EASING = 9;
    private static final int TRANSITION_PATH_ROTATE = 8;
    private static final int WAVE_OFFSET = 21;
    private static final int WAVE_PERIOD = 20;
    private static final int WAVE_SHAPE = 19;
    private static SparseIntArray mAttrMap;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mAttrMap = localSparseIntArray;
      localSparseIntArray.append(R.styleable.KeyTimeCycle_android_alpha, 1);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_elevation, 2);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotation, 4);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
      mAttrMap.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
      mAttrMap.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
      mAttrMap.append(R.styleable.KeyTimeCycle_motionTarget, 10);
      mAttrMap.append(R.styleable.KeyTimeCycle_framePosition, 12);
      mAttrMap.append(R.styleable.KeyTimeCycle_curveFit, 13);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationX, 15);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationY, 16);
      mAttrMap.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
      mAttrMap.append(R.styleable.KeyTimeCycle_motionProgress, 18);
      mAttrMap.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
      mAttrMap.append(R.styleable.KeyTimeCycle_waveOffset, 21);
      mAttrMap.append(R.styleable.KeyTimeCycle_waveShape, 19);
    }
    
    public static void read(KeyTimeCycle paramKeyTimeCycle, TypedArray paramTypedArray)
    {
      int j = paramTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramTypedArray.getIndex(i);
        switch (mAttrMap.get(k))
        {
        case 3: 
        case 11: 
        default: 
          StringBuilder localStringBuilder = new StringBuilder().append("unused attribute 0x");
          String str = Integer.toHexString(k);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          Log.e("KeyTimeCycle", str + "   " + mAttrMap.get(k));
          break;
        case 21: 
          if (paramTypedArray.peekValue(k).type == 5) {
            KeyTimeCycle.access$702(paramKeyTimeCycle, paramTypedArray.getDimension(k, paramKeyTimeCycle.mWaveOffset));
          } else {
            KeyTimeCycle.access$702(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mWaveOffset));
          }
          break;
        case 20: 
          KeyTimeCycle.access$602(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mWavePeriod));
          break;
        case 19: 
          if (paramTypedArray.peekValue(k).type == 3)
          {
            KeyTimeCycle.access$402(paramKeyTimeCycle, paramTypedArray.getString(k));
            KeyTimeCycle.access$502(paramKeyTimeCycle, 7);
          }
          else
          {
            KeyTimeCycle.access$502(paramKeyTimeCycle, paramTypedArray.getInt(k, paramKeyTimeCycle.mWaveShape));
          }
          break;
        case 18: 
          KeyTimeCycle.access$1702(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mProgress));
          break;
        case 17: 
          if (Build.VERSION.SDK_INT >= 21) {
            KeyTimeCycle.access$1602(paramKeyTimeCycle, paramTypedArray.getDimension(k, paramKeyTimeCycle.mTranslationZ));
          }
          break;
        case 16: 
          KeyTimeCycle.access$1502(paramKeyTimeCycle, paramTypedArray.getDimension(k, paramKeyTimeCycle.mTranslationY));
          break;
        case 15: 
          KeyTimeCycle.access$1402(paramKeyTimeCycle, paramTypedArray.getDimension(k, paramKeyTimeCycle.mTranslationX));
          break;
        case 14: 
          KeyTimeCycle.access$1202(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mScaleY));
          break;
        case 13: 
          KeyTimeCycle.access$302(paramKeyTimeCycle, paramTypedArray.getInteger(k, paramKeyTimeCycle.mCurveFit));
          break;
        case 12: 
          paramKeyTimeCycle.mFramePosition = paramTypedArray.getInt(k, paramKeyTimeCycle.mFramePosition);
          break;
        case 10: 
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            paramKeyTimeCycle.mTargetId = paramTypedArray.getResourceId(k, paramKeyTimeCycle.mTargetId);
            if (paramKeyTimeCycle.mTargetId == -1) {
              paramKeyTimeCycle.mTargetString = paramTypedArray.getString(k);
            }
          }
          else if (paramTypedArray.peekValue(k).type == 3)
          {
            paramKeyTimeCycle.mTargetString = paramTypedArray.getString(k);
          }
          else
          {
            paramKeyTimeCycle.mTargetId = paramTypedArray.getResourceId(k, paramKeyTimeCycle.mTargetId);
          }
          break;
        case 9: 
          KeyTimeCycle.access$1102(paramKeyTimeCycle, paramTypedArray.getString(k));
          break;
        case 8: 
          KeyTimeCycle.access$1302(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mTransitionPathRotate));
          break;
        case 7: 
          KeyTimeCycle.access$802(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mScaleX));
          break;
        case 6: 
          KeyTimeCycle.access$1002(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mRotationY));
          break;
        case 5: 
          KeyTimeCycle.access$902(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mRotationX));
          break;
        case 4: 
          KeyTimeCycle.access$202(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mRotation));
          break;
        case 2: 
          KeyTimeCycle.access$102(paramKeyTimeCycle, paramTypedArray.getDimension(k, paramKeyTimeCycle.mElevation));
          break;
        case 1: 
          KeyTimeCycle.access$002(paramKeyTimeCycle, paramTypedArray.getFloat(k, paramKeyTimeCycle.mAlpha));
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyTimeCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */