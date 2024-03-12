package androidx.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.util.Property;

class PropertyValuesHolderUtils
{
  static PropertyValuesHolder ofPointF(Property<?, PointF> paramProperty, Path paramPath)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return PropertyValuesHolder.ofObject(paramProperty, null, paramPath);
    }
    return PropertyValuesHolder.ofFloat(new PathProperty(paramProperty, paramPath), new float[] { 0.0F, 1.0F });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/PropertyValuesHolderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */