package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewSpline.CustomSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R.styleable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyAttributes
  extends Key
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
  private boolean mVisibility = false;
  
  public KeyAttributes()
  {
    this.mType = 1;
    this.mCustomConstraints = new HashMap();
  }
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap)
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
          localObject = (ConstraintAttribute)this.mCustomConstraints.get(localObject);
          if (localObject != null) {
            ((ViewSpline.CustomSet)localSplineSet).setPoint(this.mFramePosition, (ConstraintAttribute)localObject);
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
              break label465;
              if (((String)localObject).equals("transitionPathRotate"))
              {
                i = 7;
                break label465;
                if (((String)localObject).equals("elevation"))
                {
                  break label465;
                  if (((String)localObject).equals("rotation"))
                  {
                    i = 2;
                    break label465;
                    if (((String)localObject).equals("transformPivotY"))
                    {
                      i = 6;
                      break label465;
                      if (((String)localObject).equals("transformPivotX"))
                      {
                        i = 5;
                        break label465;
                        if (((String)localObject).equals("scaleY"))
                        {
                          i = 9;
                          break label465;
                          if (((String)localObject).equals("scaleX"))
                          {
                            i = 8;
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
  
  public Key clone()
  {
    return new KeyAttributes().copy(this);
  }
  
  public Key copy(Key paramKey)
  {
    super.copy(paramKey);
    paramKey = (KeyAttributes)paramKey;
    this.mCurveFit = paramKey.mCurveFit;
    this.mVisibility = paramKey.mVisibility;
    this.mAlpha = paramKey.mAlpha;
    this.mElevation = paramKey.mElevation;
    this.mRotation = paramKey.mRotation;
    this.mRotationX = paramKey.mRotationX;
    this.mRotationY = paramKey.mRotationY;
    this.mPivotX = paramKey.mPivotX;
    this.mPivotY = paramKey.mPivotY;
    this.mTransitionPathRotate = paramKey.mTransitionPathRotate;
    this.mScaleX = paramKey.mScaleX;
    this.mScaleY = paramKey.mScaleY;
    this.mTranslationX = paramKey.mTranslationX;
    this.mTranslationY = paramKey.mTranslationY;
    this.mTranslationZ = paramKey.mTranslationZ;
    this.mProgress = paramKey.mProgress;
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
    if (!Float.isNaN(this.mPivotX)) {
      paramHashSet.add("transformPivotX");
    }
    if (!Float.isNaN(this.mPivotY)) {
      paramHashSet.add("transformPivotY");
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
  
  int getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet)
  {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyAttribute));
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
    if (!Float.isNaN(this.mPivotX)) {
      paramHashMap.put("transformPivotX", Integer.valueOf(this.mCurveFit));
    }
    if (!Float.isNaN(this.mPivotY)) {
      paramHashMap.put("transformPivotY", Integer.valueOf(this.mCurveFit));
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
    if (!Float.isNaN(this.mScaleY)) {
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
      if (paramString.equals("visibility"))
      {
        i = 12;
        break label410;
        if (paramString.equals("curveFit"))
        {
          i = 1;
          break label410;
          if (paramString.equals("alpha"))
          {
            i = 0;
            break label410;
            if (paramString.equals("transitionPathRotate"))
            {
              i = 13;
              break label410;
              if (paramString.equals("elevation"))
              {
                i = 2;
                break label410;
                if (paramString.equals("rotation"))
                {
                  i = 4;
                  break label410;
                  if (paramString.equals("transformPivotY"))
                  {
                    i = 8;
                    break label410;
                    if (paramString.equals("transformPivotX"))
                    {
                      i = 7;
                      break label410;
                      if (paramString.equals("scaleY"))
                      {
                        i = 10;
                        break label410;
                        if (paramString.equals("scaleX"))
                        {
                          i = 9;
                          break label410;
                          if (paramString.equals("translationZ"))
                          {
                            i = 16;
                            break label410;
                            if (paramString.equals("translationY"))
                            {
                              i = 15;
                              break label410;
                              if (paramString.equals("translationX"))
                              {
                                i = 14;
                                break label410;
                                if (paramString.equals("rotationY"))
                                {
                                  i = 6;
                                  break label410;
                                  if (paramString.equals("rotationX"))
                                  {
                                    i = 5;
                                    break label410;
                                    if (paramString.equals("transitionEasing"))
                                    {
                                      i = 11;
                                      break label410;
                                      if (paramString.equals("motionProgress"))
                                      {
                                        i = 3;
                                        break label410;
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
      this.mTranslationZ = toFloat(paramObject);
      break;
    case 15: 
      this.mTranslationY = toFloat(paramObject);
      break;
    case 14: 
      this.mTranslationX = toFloat(paramObject);
      break;
    case 13: 
      this.mTransitionPathRotate = toFloat(paramObject);
      break;
    case 12: 
      this.mVisibility = toBoolean(paramObject);
      break;
    case 11: 
      this.mTransitionEasing = paramObject.toString();
      break;
    case 10: 
      this.mScaleY = toFloat(paramObject);
      break;
    case 9: 
      this.mScaleX = toFloat(paramObject);
      break;
    case 8: 
      this.mPivotY = toFloat(paramObject);
      break;
    case 7: 
      this.mPivotX = toFloat(paramObject);
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
      label410:
      this.mAlpha = toFloat(paramObject);
    }
  }
  
  private static class Loader
  {
    private static final int ANDROID_ALPHA = 1;
    private static final int ANDROID_ELEVATION = 2;
    private static final int ANDROID_PIVOT_X = 19;
    private static final int ANDROID_PIVOT_Y = 20;
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
    private static SparseIntArray mAttrMap;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mAttrMap = localSparseIntArray;
      localSparseIntArray.append(R.styleable.KeyAttribute_android_alpha, 1);
      mAttrMap.append(R.styleable.KeyAttribute_android_elevation, 2);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotation, 4);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotationX, 5);
      mAttrMap.append(R.styleable.KeyAttribute_android_rotationY, 6);
      mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotX, 19);
      mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotY, 20);
      mAttrMap.append(R.styleable.KeyAttribute_android_scaleX, 7);
      mAttrMap.append(R.styleable.KeyAttribute_transitionPathRotate, 8);
      mAttrMap.append(R.styleable.KeyAttribute_transitionEasing, 9);
      mAttrMap.append(R.styleable.KeyAttribute_motionTarget, 10);
      mAttrMap.append(R.styleable.KeyAttribute_framePosition, 12);
      mAttrMap.append(R.styleable.KeyAttribute_curveFit, 13);
      mAttrMap.append(R.styleable.KeyAttribute_android_scaleY, 14);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationX, 15);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationY, 16);
      mAttrMap.append(R.styleable.KeyAttribute_android_translationZ, 17);
      mAttrMap.append(R.styleable.KeyAttribute_motionProgress, 18);
    }
    
    public static void read(KeyAttributes paramKeyAttributes, TypedArray paramTypedArray)
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
          Log.e("KeyAttribute", str + "   " + mAttrMap.get(k));
          break;
        case 20: 
          KeyAttributes.access$802(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mPivotY));
          break;
        case 19: 
          KeyAttributes.access$702(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mPivotX));
          break;
        case 18: 
          KeyAttributes.access$1502(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mProgress));
          break;
        case 17: 
          if (Build.VERSION.SDK_INT >= 21) {
            KeyAttributes.access$1402(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mTranslationZ));
          }
          break;
        case 16: 
          KeyAttributes.access$1302(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mTranslationY));
          break;
        case 15: 
          KeyAttributes.access$1202(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mTranslationX));
          break;
        case 14: 
          KeyAttributes.access$1002(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mScaleY));
          break;
        case 13: 
          KeyAttributes.access$302(paramKeyAttributes, paramTypedArray.getInteger(k, paramKeyAttributes.mCurveFit));
          break;
        case 12: 
          paramKeyAttributes.mFramePosition = paramTypedArray.getInt(k, paramKeyAttributes.mFramePosition);
          break;
        case 10: 
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            paramKeyAttributes.mTargetId = paramTypedArray.getResourceId(k, paramKeyAttributes.mTargetId);
            if (paramKeyAttributes.mTargetId == -1) {
              paramKeyAttributes.mTargetString = paramTypedArray.getString(k);
            }
          }
          else if (paramTypedArray.peekValue(k).type == 3)
          {
            paramKeyAttributes.mTargetString = paramTypedArray.getString(k);
          }
          else
          {
            paramKeyAttributes.mTargetId = paramTypedArray.getResourceId(k, paramKeyAttributes.mTargetId);
          }
          break;
        case 9: 
          KeyAttributes.access$902(paramKeyAttributes, paramTypedArray.getString(k));
          break;
        case 8: 
          KeyAttributes.access$1102(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mTransitionPathRotate));
          break;
        case 7: 
          KeyAttributes.access$402(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mScaleX));
          break;
        case 6: 
          KeyAttributes.access$602(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mRotationY));
          break;
        case 5: 
          KeyAttributes.access$502(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mRotationX));
          break;
        case 4: 
          KeyAttributes.access$202(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mRotation));
          break;
        case 2: 
          KeyAttributes.access$102(paramKeyAttributes, paramTypedArray.getDimension(k, paramKeyAttributes.mElevation));
          break;
        case 1: 
          KeyAttributes.access$002(paramKeyAttributes, paramTypedArray.getFloat(k, paramKeyAttributes.mAlpha));
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */