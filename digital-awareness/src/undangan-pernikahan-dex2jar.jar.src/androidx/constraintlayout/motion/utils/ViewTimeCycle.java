package androidx.constraintlayout.motion.utils;

import android.os.Build.VERSION;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ViewTimeCycle
  extends TimeCycleSplineSet
{
  private static final String TAG = "ViewTimeCycle";
  
  public static ViewTimeCycle makeCustomSpline(String paramString, SparseArray<ConstraintAttribute> paramSparseArray)
  {
    return new CustomSet(paramString, paramSparseArray);
  }
  
  public static ViewTimeCycle makeSpline(String paramString, long paramLong)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("alpha"))
      {
        i = 0;
        break label291;
        if (paramString.equals("transitionPathRotate"))
        {
          i = 5;
          break label291;
          if (paramString.equals("elevation"))
          {
            i = 1;
            break label291;
            if (paramString.equals("rotation"))
            {
              i = 2;
              break label291;
              if (paramString.equals("scaleY"))
              {
                i = 7;
                break label291;
                if (paramString.equals("scaleX"))
                {
                  i = 6;
                  break label291;
                  if (paramString.equals("progress"))
                  {
                    i = 11;
                    break label291;
                    if (paramString.equals("translationZ"))
                    {
                      i = 10;
                      break label291;
                      if (paramString.equals("translationY"))
                      {
                        i = 9;
                        break label291;
                        if (paramString.equals("translationX"))
                        {
                          i = 8;
                          break label291;
                          if (paramString.equals("rotationY"))
                          {
                            i = 4;
                            break label291;
                            if (paramString.equals("rotationX"))
                            {
                              i = 3;
                              break label291;
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
      return null;
    case 11: 
      paramString = new ProgressSet();
      break;
    case 10: 
      paramString = new TranslationZset();
      break;
    case 9: 
      paramString = new TranslationYset();
      break;
    case 8: 
      paramString = new TranslationXset();
      break;
    case 7: 
      paramString = new ScaleYset();
      break;
    case 6: 
      paramString = new ScaleXset();
      break;
    case 5: 
      paramString = new PathRotate();
      break;
    case 4: 
      paramString = new RotationYset();
      break;
    case 3: 
      paramString = new RotationXset();
      break;
    case 2: 
      paramString = new RotationSet();
      break;
    case 1: 
      paramString = new ElevationSet();
      break;
    case 0: 
      label291:
      paramString = new AlphaSet();
    }
    paramString.setStartTime(paramLong);
    return paramString;
  }
  
  public float get(float paramFloat, long paramLong, View paramView, KeyCache paramKeyCache)
  {
    this.mCurveFit.getPos(paramFloat, this.mCache);
    float f2 = this.mCache[1];
    boolean bool = false;
    if (f2 == 0.0F)
    {
      this.mContinue = false;
      return this.mCache[2];
    }
    if (Float.isNaN(this.last_cycle))
    {
      this.last_cycle = paramKeyCache.getFloatValue(paramView, this.mType, 0);
      if (Float.isNaN(this.last_cycle)) {
        this.last_cycle = 0.0F;
      }
    }
    long l = this.last_time;
    this.last_cycle = ((float)((this.last_cycle + (paramLong - l) * 1.0E-9D * f2) % 1.0D));
    paramKeyCache.setFloatValue(paramView, this.mType, 0, this.last_cycle);
    this.last_time = paramLong;
    float f3 = this.mCache[0];
    paramFloat = calcWave(this.last_cycle);
    float f1 = this.mCache[2];
    if ((f3 != 0.0F) || (f2 != 0.0F)) {
      bool = true;
    }
    this.mContinue = bool;
    return f3 * paramFloat + f1;
  }
  
  public abstract boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache);
  
  static class AlphaSet
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setAlpha(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  public static class CustomSet
    extends ViewTimeCycle
  {
    String mAttributeName = paramString.split(",")[1];
    float[] mCache;
    SparseArray<ConstraintAttribute> mConstraintAttributeList;
    float[] mTempValues;
    SparseArray<float[]> mWaveProperties = new SparseArray();
    
    public CustomSet(String paramString, SparseArray<ConstraintAttribute> paramSparseArray)
    {
      this.mConstraintAttributeList = paramSparseArray;
    }
    
    public void setPoint(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
    }
    
    public void setPoint(int paramInt1, ConstraintAttribute paramConstraintAttribute, float paramFloat1, int paramInt2, float paramFloat2)
    {
      this.mConstraintAttributeList.append(paramInt1, paramConstraintAttribute);
      this.mWaveProperties.append(paramInt1, new float[] { paramFloat1, paramFloat2 });
      this.mWaveShape = Math.max(this.mWaveShape, paramInt2);
    }
    
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      float[] arrayOfFloat = this.mTempValues;
      float f1 = arrayOfFloat[(arrayOfFloat.length - 2)];
      paramFloat = arrayOfFloat[(arrayOfFloat.length - 1)];
      long l = this.last_time;
      if (Float.isNaN(this.last_cycle))
      {
        this.last_cycle = paramKeyCache.getFloatValue(paramView, this.mAttributeName, 0);
        if (Float.isNaN(this.last_cycle)) {
          this.last_cycle = 0.0F;
        }
      }
      this.last_cycle = ((float)((this.last_cycle + (paramLong - l) * 1.0E-9D * f1) % 1.0D));
      this.last_time = paramLong;
      float f2 = calcWave(this.last_cycle);
      this.mContinue = false;
      for (int i = 0; i < this.mCache.length; i++)
      {
        boolean bool2 = this.mContinue;
        boolean bool1;
        if (this.mTempValues[i] != 0.0D) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.mContinue = (bool2 | bool1);
        this.mCache[i] = (this.mTempValues[i] * f2 + paramFloat);
      }
      CustomSupport.setInterpolatedValue((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0), paramView, this.mCache);
      if (f1 != 0.0F) {
        this.mContinue = true;
      }
      return this.mContinue;
    }
    
    public void setup(int paramInt)
    {
      int m = this.mConstraintAttributeList.size();
      int k = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).numberOfInterpolatedValues();
      double[] arrayOfDouble = new double[m];
      this.mTempValues = new float[k + 2];
      this.mCache = new float[k];
      double[][] arrayOfDouble1 = new double[m][k + 2];
      for (int i = 0; i < m; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(i);
        float[] arrayOfFloat = (float[])this.mWaveProperties.valueAt(i);
        arrayOfDouble[i] = (j * 0.01D);
        ((ConstraintAttribute)localObject).getValuesToInterpolate(this.mTempValues);
        for (j = 0;; j++)
        {
          localObject = this.mTempValues;
          if (j >= localObject.length) {
            break;
          }
          arrayOfDouble1[i][j] = localObject[j];
        }
        arrayOfDouble1[i][k] = arrayOfFloat[0];
        arrayOfDouble1[i][(k + 1)] = arrayOfFloat[1];
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
    }
  }
  
  static class ElevationSet
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setElevation(get(paramFloat, paramLong, paramView, paramKeyCache));
      }
      return this.mContinue;
    }
  }
  
  public static class PathRotate
    extends ViewTimeCycle
  {
    public boolean setPathRotate(View paramView, KeyCache paramKeyCache, float paramFloat, long paramLong, double paramDouble1, double paramDouble2)
    {
      paramView.setRotation(get(paramFloat, paramLong, paramView, paramKeyCache) + (float)Math.toDegrees(Math.atan2(paramDouble2, paramDouble1)));
      return this.mContinue;
    }
    
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      return this.mContinue;
    }
  }
  
  static class ProgressSet
    extends ViewTimeCycle
  {
    boolean mNoMethod = false;
    
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      if ((paramView instanceof MotionLayout))
      {
        ((MotionLayout)paramView).setProgress(get(paramFloat, paramLong, paramView, paramKeyCache));
      }
      else
      {
        if (this.mNoMethod) {
          return false;
        }
        Object localObject;
        try
        {
          Method localMethod = paramView.getClass().getMethod("setProgress", new Class[] { Float.TYPE });
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          this.mNoMethod = true;
          localObject = null;
        }
        if (localObject != null) {
          try
          {
            ((Method)localObject).invoke(paramView, new Object[] { Float.valueOf(get(paramFloat, paramLong, paramView, paramKeyCache)) });
          }
          catch (InvocationTargetException paramView)
          {
            Log.e("ViewTimeCycle", "unable to setProgress", paramView);
          }
          catch (IllegalAccessException paramView)
          {
            Log.e("ViewTimeCycle", "unable to setProgress", paramView);
          }
        }
      }
      return this.mContinue;
    }
  }
  
  static class RotationSet
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setRotation(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class RotationXset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setRotationX(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class RotationYset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setRotationY(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class ScaleXset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setScaleX(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class ScaleYset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setScaleY(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class TranslationXset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setTranslationX(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class TranslationYset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      paramView.setTranslationY(get(paramFloat, paramLong, paramView, paramKeyCache));
      return this.mContinue;
    }
  }
  
  static class TranslationZset
    extends ViewTimeCycle
  {
    public boolean setProperty(View paramView, float paramFloat, long paramLong, KeyCache paramKeyCache)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setTranslationZ(get(paramFloat, paramLong, paramView, paramKeyCache));
      }
      return this.mContinue;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/utils/ViewTimeCycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */