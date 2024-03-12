package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PathUtils
{
  public static Collection<PathSegment> flatten(Path paramPath)
  {
    return flatten(paramPath, 0.5F);
  }
  
  public static Collection<PathSegment> flatten(Path paramPath, float paramFloat)
  {
    paramPath = Api26Impl.approximate(paramPath, paramFloat);
    int j = paramPath.length / 3;
    ArrayList localArrayList = new ArrayList(j);
    for (int i = 1; i < j; i++)
    {
      int m = i * 3;
      int k = (i - 1) * 3;
      float f2 = paramPath[m];
      paramFloat = paramPath[(m + 1)];
      float f1 = paramPath[(m + 2)];
      float f3 = paramPath[k];
      float f5 = paramPath[(k + 1)];
      float f4 = paramPath[(k + 2)];
      if ((f2 != f3) && ((paramFloat != f5) || (f1 != f4))) {
        localArrayList.add(new PathSegment(new PointF(f5, f4), f3, new PointF(paramFloat, f1), f2));
      }
    }
    return localArrayList;
  }
  
  static class Api26Impl
  {
    static float[] approximate(Path paramPath, float paramFloat)
    {
      return paramPath.approximate(paramFloat);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/PathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */