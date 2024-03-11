package androidx.core.view.animation;

import android.graphics.Path;
import android.os.Build.VERSION;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public final class PathInterpolatorCompat
{
  public static Interpolator create(float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.createPathInterpolator(paramFloat1, paramFloat2);
    }
    return new PathInterpolatorApi14(paramFloat1, paramFloat2);
  }
  
  public static Interpolator create(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.createPathInterpolator(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
    return new PathInterpolatorApi14(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public static Interpolator create(Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.createPathInterpolator(paramPath);
    }
    return new PathInterpolatorApi14(paramPath);
  }
  
  static class Api21Impl
  {
    static PathInterpolator createPathInterpolator(float paramFloat1, float paramFloat2)
    {
      return new PathInterpolator(paramFloat1, paramFloat2);
    }
    
    static PathInterpolator createPathInterpolator(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return new PathInterpolator(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
    
    static PathInterpolator createPathInterpolator(Path paramPath)
    {
      return new PathInterpolator(paramPath);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/animation/PathInterpolatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */