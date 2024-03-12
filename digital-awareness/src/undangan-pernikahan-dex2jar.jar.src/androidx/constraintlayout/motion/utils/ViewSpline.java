package androidx.constraintlayout.motion.utils;

import android.os.Build.VERSION;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ViewSpline
  extends SplineSet
{
  private static final String TAG = "ViewSpline";
  
  public static ViewSpline makeCustomSpline(String paramString, SparseArray<ConstraintAttribute> paramSparseArray)
  {
    return new CustomSet(paramString, paramSparseArray);
  }
  
  public static ViewSpline makeSpline(String paramString)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("waveOffset"))
      {
        i = 10;
        break label383;
        if (paramString.equals("alpha"))
        {
          i = 0;
          break label383;
          if (paramString.equals("transitionPathRotate"))
          {
            i = 7;
            break label383;
            if (paramString.equals("elevation"))
            {
              i = 1;
              break label383;
              if (paramString.equals("rotation"))
              {
                i = 2;
                break label383;
                if (paramString.equals("transformPivotY"))
                {
                  i = 6;
                  break label383;
                  if (paramString.equals("transformPivotX"))
                  {
                    i = 5;
                    break label383;
                    if (paramString.equals("waveVariesBy"))
                    {
                      i = 11;
                      break label383;
                      if (paramString.equals("scaleY"))
                      {
                        i = 9;
                        break label383;
                        if (paramString.equals("scaleX"))
                        {
                          i = 8;
                          break label383;
                          if (paramString.equals("progress"))
                          {
                            i = 15;
                            break label383;
                            if (paramString.equals("translationZ"))
                            {
                              i = 14;
                              break label383;
                              if (paramString.equals("translationY"))
                              {
                                i = 13;
                                break label383;
                                if (paramString.equals("translationX"))
                                {
                                  i = 12;
                                  break label383;
                                  if (paramString.equals("rotationY"))
                                  {
                                    i = 4;
                                    break label383;
                                    if (paramString.equals("rotationX"))
                                    {
                                      i = 3;
                                      break label383;
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
      return null;
    case 15: 
      return new ProgressSet();
    case 14: 
      return new TranslationZset();
    case 13: 
      return new TranslationYset();
    case 12: 
      return new TranslationXset();
    case 11: 
      return new AlphaSet();
    case 10: 
      return new AlphaSet();
    case 9: 
      return new ScaleYset();
    case 8: 
      return new ScaleXset();
    case 7: 
      return new PathRotate();
    case 6: 
      return new PivotYset();
    case 5: 
      return new PivotXset();
    case 4: 
      return new RotationYset();
    case 3: 
      return new RotationXset();
    case 2: 
      return new RotationSet();
    case 1: 
      label383:
      return new ElevationSet();
    }
    return new AlphaSet();
  }
  
  public abstract void setProperty(View paramView, float paramFloat);
  
  static class AlphaSet
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setAlpha(get(paramFloat));
    }
  }
  
  public static class CustomSet
    extends ViewSpline
  {
    String mAttributeName = paramString.split(",")[1];
    SparseArray<ConstraintAttribute> mConstraintAttributeList;
    float[] mTempValues;
    
    public CustomSet(String paramString, SparseArray<ConstraintAttribute> paramSparseArray)
    {
      this.mConstraintAttributeList = paramSparseArray;
    }
    
    public void setPoint(int paramInt, float paramFloat)
    {
      throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
    }
    
    public void setPoint(int paramInt, ConstraintAttribute paramConstraintAttribute)
    {
      this.mConstraintAttributeList.append(paramInt, paramConstraintAttribute);
    }
    
    public void setProperty(View paramView, float paramFloat)
    {
      this.mCurveFit.getPos(paramFloat, this.mTempValues);
      CustomSupport.setInterpolatedValue((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0), paramView, this.mTempValues);
    }
    
    public void setup(int paramInt)
    {
      int k = this.mConstraintAttributeList.size();
      int i = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).numberOfInterpolatedValues();
      double[] arrayOfDouble = new double[k];
      this.mTempValues = new float[i];
      double[][] arrayOfDouble1 = new double[k][i];
      for (i = 0; i < k; i++)
      {
        int j = this.mConstraintAttributeList.keyAt(i);
        Object localObject = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(i);
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
      }
      this.mCurveFit = CurveFit.get(paramInt, arrayOfDouble, arrayOfDouble1);
    }
  }
  
  static class ElevationSet
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setElevation(get(paramFloat));
      }
    }
  }
  
  public static class PathRotate
    extends ViewSpline
  {
    public void setPathRotate(View paramView, float paramFloat, double paramDouble1, double paramDouble2)
    {
      paramView.setRotation(get(paramFloat) + (float)Math.toDegrees(Math.atan2(paramDouble2, paramDouble1)));
    }
    
    public void setProperty(View paramView, float paramFloat) {}
  }
  
  static class PivotXset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setPivotX(get(paramFloat));
    }
  }
  
  static class PivotYset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setPivotY(get(paramFloat));
    }
  }
  
  static class ProgressSet
    extends ViewSpline
  {
    boolean mNoMethod = false;
    
    public void setProperty(View paramView, float paramFloat)
    {
      if ((paramView instanceof MotionLayout))
      {
        ((MotionLayout)paramView).setProgress(get(paramFloat));
      }
      else
      {
        if (this.mNoMethod) {
          return;
        }
        Object localObject = null;
        try
        {
          Method localMethod = paramView.getClass().getMethod("setProgress", new Class[] { Float.TYPE });
          localObject = localMethod;
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          this.mNoMethod = true;
        }
        if (localObject != null) {
          try
          {
            ((Method)localObject).invoke(paramView, new Object[] { Float.valueOf(get(paramFloat)) });
          }
          catch (InvocationTargetException paramView)
          {
            Log.e("ViewSpline", "unable to setProgress", paramView);
          }
          catch (IllegalAccessException paramView)
          {
            Log.e("ViewSpline", "unable to setProgress", paramView);
          }
        }
      }
    }
  }
  
  static class RotationSet
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotation(get(paramFloat));
    }
  }
  
  static class RotationXset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotationX(get(paramFloat));
    }
  }
  
  static class RotationYset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotationY(get(paramFloat));
    }
  }
  
  static class ScaleXset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setScaleX(get(paramFloat));
    }
  }
  
  static class ScaleYset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setScaleY(get(paramFloat));
    }
  }
  
  static class TranslationXset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setTranslationX(get(paramFloat));
    }
  }
  
  static class TranslationYset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setTranslationY(get(paramFloat));
    }
  }
  
  static class TranslationZset
    extends ViewSpline
  {
    public void setProperty(View paramView, float paramFloat)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setTranslationZ(get(paramFloat));
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/utils/ViewSpline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */