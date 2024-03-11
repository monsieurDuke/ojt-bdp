package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuffColorFilter;
import android.os.Build.VERSION;

public class BlendModeColorFilterCompat
{
  public static ColorFilter createBlendModeColorFilterCompat(int paramInt, BlendModeCompat paramBlendModeCompat)
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject2 = null;
    Object localObject1 = null;
    if (i >= 29)
    {
      paramBlendModeCompat = BlendModeUtils.Api29Impl.obtainBlendModeFromCompat(paramBlendModeCompat);
      if (paramBlendModeCompat != null) {
        paramBlendModeCompat = Api29Impl.createBlendModeColorFilter(paramInt, paramBlendModeCompat);
      } else {
        paramBlendModeCompat = (BlendModeCompat)localObject1;
      }
      return paramBlendModeCompat;
    }
    paramBlendModeCompat = BlendModeUtils.obtainPorterDuffFromCompat(paramBlendModeCompat);
    if (paramBlendModeCompat != null) {
      paramBlendModeCompat = new PorterDuffColorFilter(paramInt, paramBlendModeCompat);
    } else {
      paramBlendModeCompat = (BlendModeCompat)localObject2;
    }
    return paramBlendModeCompat;
  }
  
  static class Api29Impl
  {
    static ColorFilter createBlendModeColorFilter(int paramInt, Object paramObject)
    {
      return new BlendModeColorFilter(paramInt, (BlendMode)paramObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/BlendModeColorFilterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */