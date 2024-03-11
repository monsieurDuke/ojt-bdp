package androidx.constraintlayout.motion.utils;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ViewOscillator
  extends KeyCycleOscillator
{
  private static final String TAG = "ViewOscillator";
  
  public static ViewOscillator makeSpline(String paramString)
  {
    if (paramString.startsWith("CUSTOM")) {
      return new CustomSet();
    }
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("waveOffset"))
      {
        i = 8;
        break;
        if (paramString.equals("alpha"))
        {
          i = 0;
          break;
          if (paramString.equals("transitionPathRotate"))
          {
            i = 5;
            break;
            if (paramString.equals("elevation"))
            {
              i = 1;
              break;
              if (paramString.equals("rotation"))
              {
                i = 2;
                break;
                if (paramString.equals("waveVariesBy"))
                {
                  i = 9;
                  break;
                  if (paramString.equals("scaleY"))
                  {
                    i = 7;
                    break;
                    if (paramString.equals("scaleX"))
                    {
                      i = 6;
                      break;
                      if (paramString.equals("progress"))
                      {
                        i = 13;
                        break;
                        if (paramString.equals("translationZ"))
                        {
                          i = 12;
                          break;
                          if (paramString.equals("translationY"))
                          {
                            i = 11;
                            break;
                            if (paramString.equals("translationX"))
                            {
                              i = 10;
                              break;
                              if (paramString.equals("rotationY"))
                              {
                                i = 4;
                                break;
                                if (paramString.equals("rotationX")) {
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
      return null;
    case 13: 
      return new ProgressSet();
    case 12: 
      return new TranslationZset();
    case 11: 
      return new TranslationYset();
    case 10: 
      return new TranslationXset();
    case 9: 
      return new AlphaSet();
    case 8: 
      return new AlphaSet();
    case 7: 
      return new ScaleYset();
    case 6: 
      return new ScaleXset();
    case 5: 
      return new PathRotateSet();
    case 4: 
      return new RotationYset();
    case 3: 
      return new RotationXset();
    case 2: 
      return new RotationSet();
    case 1: 
      return new ElevationSet();
    }
    return new AlphaSet();
  }
  
  public abstract void setProperty(View paramView, float paramFloat);
  
  static class AlphaSet
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setAlpha(get(paramFloat));
    }
  }
  
  static class CustomSet
    extends ViewOscillator
  {
    protected ConstraintAttribute mCustom;
    float[] value = new float[1];
    
    protected void setCustom(Object paramObject)
    {
      this.mCustom = ((ConstraintAttribute)paramObject);
    }
    
    public void setProperty(View paramView, float paramFloat)
    {
      this.value[0] = get(paramFloat);
      CustomSupport.setInterpolatedValue(this.mCustom, paramView, this.value);
    }
  }
  
  static class ElevationSet
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setElevation(get(paramFloat));
      }
    }
  }
  
  public static class PathRotateSet
    extends ViewOscillator
  {
    public void setPathRotate(View paramView, float paramFloat, double paramDouble1, double paramDouble2)
    {
      paramView.setRotation(get(paramFloat) + (float)Math.toDegrees(Math.atan2(paramDouble2, paramDouble1)));
    }
    
    public void setProperty(View paramView, float paramFloat) {}
  }
  
  static class ProgressSet
    extends ViewOscillator
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
            Log.e("ViewOscillator", "unable to setProgress", paramView);
          }
          catch (IllegalAccessException paramView)
          {
            Log.e("ViewOscillator", "unable to setProgress", paramView);
          }
        }
      }
    }
  }
  
  static class RotationSet
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotation(get(paramFloat));
    }
  }
  
  static class RotationXset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotationX(get(paramFloat));
    }
  }
  
  static class RotationYset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setRotationY(get(paramFloat));
    }
  }
  
  static class ScaleXset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setScaleX(get(paramFloat));
    }
  }
  
  static class ScaleYset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setScaleY(get(paramFloat));
    }
  }
  
  static class TranslationXset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setTranslationX(get(paramFloat));
    }
  }
  
  static class TranslationYset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      paramView.setTranslationY(get(paramFloat));
    }
  }
  
  static class TranslationZset
    extends ViewOscillator
  {
    public void setProperty(View paramView, float paramFloat)
    {
      if (Build.VERSION.SDK_INT >= 21) {
        paramView.setTranslationZ(get(paramFloat));
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/utils/ViewOscillator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */