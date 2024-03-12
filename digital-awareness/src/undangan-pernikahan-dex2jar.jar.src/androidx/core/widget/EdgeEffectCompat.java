package androidx.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

public final class EdgeEffectCompat
{
  private final EdgeEffect mEdgeEffect;
  
  @Deprecated
  public EdgeEffectCompat(Context paramContext)
  {
    this.mEdgeEffect = new EdgeEffect(paramContext);
  }
  
  public static EdgeEffect create(Context paramContext, AttributeSet paramAttributeSet)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.create(paramContext, paramAttributeSet);
    }
    return new EdgeEffect(paramContext);
  }
  
  public static float getDistance(EdgeEffect paramEdgeEffect)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.getDistance(paramEdgeEffect);
    }
    return 0.0F;
  }
  
  public static void onPull(EdgeEffect paramEdgeEffect, float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.onPull(paramEdgeEffect, paramFloat1, paramFloat2);
    } else {
      paramEdgeEffect.onPull(paramFloat1);
    }
  }
  
  public static float onPullDistance(EdgeEffect paramEdgeEffect, float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.onPullDistance(paramEdgeEffect, paramFloat1, paramFloat2);
    }
    onPull(paramEdgeEffect, paramFloat1, paramFloat2);
    return paramFloat1;
  }
  
  @Deprecated
  public boolean draw(Canvas paramCanvas)
  {
    return this.mEdgeEffect.draw(paramCanvas);
  }
  
  @Deprecated
  public void finish()
  {
    this.mEdgeEffect.finish();
  }
  
  @Deprecated
  public boolean isFinished()
  {
    return this.mEdgeEffect.isFinished();
  }
  
  @Deprecated
  public boolean onAbsorb(int paramInt)
  {
    this.mEdgeEffect.onAbsorb(paramInt);
    return true;
  }
  
  @Deprecated
  public boolean onPull(float paramFloat)
  {
    this.mEdgeEffect.onPull(paramFloat);
    return true;
  }
  
  @Deprecated
  public boolean onPull(float paramFloat1, float paramFloat2)
  {
    onPull(this.mEdgeEffect, paramFloat1, paramFloat2);
    return true;
  }
  
  @Deprecated
  public boolean onRelease()
  {
    this.mEdgeEffect.onRelease();
    return this.mEdgeEffect.isFinished();
  }
  
  @Deprecated
  public void setSize(int paramInt1, int paramInt2)
  {
    this.mEdgeEffect.setSize(paramInt1, paramInt2);
  }
  
  static class Api21Impl
  {
    static void onPull(EdgeEffect paramEdgeEffect, float paramFloat1, float paramFloat2)
    {
      paramEdgeEffect.onPull(paramFloat1, paramFloat2);
    }
  }
  
  private static class Api31Impl
  {
    public static EdgeEffect create(Context paramContext, AttributeSet paramAttributeSet)
    {
      try
      {
        paramAttributeSet = new EdgeEffect(paramContext, paramAttributeSet);
        return paramAttributeSet;
      }
      finally {}
      return new EdgeEffect(paramContext);
    }
    
    public static float getDistance(EdgeEffect paramEdgeEffect)
    {
      try
      {
        float f = paramEdgeEffect.getDistance();
        return f;
      }
      finally {}
      return 0.0F;
    }
    
    public static float onPullDistance(EdgeEffect paramEdgeEffect, float paramFloat1, float paramFloat2)
    {
      try
      {
        float f = paramEdgeEffect.onPullDistance(paramFloat1, paramFloat2);
        return f;
      }
      finally
      {
        paramEdgeEffect.onPull(paramFloat1, paramFloat2);
      }
      return 0.0F;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/EdgeEffectCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */