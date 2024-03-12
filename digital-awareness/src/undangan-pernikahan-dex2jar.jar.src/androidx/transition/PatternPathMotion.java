package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;

public class PatternPathMotion
  extends PathMotion
{
  private Path mOriginalPatternPath;
  private final Path mPatternPath;
  private final Matrix mTempMatrix;
  
  public PatternPathMotion()
  {
    Path localPath = new Path();
    this.mPatternPath = localPath;
    this.mTempMatrix = new Matrix();
    localPath.lineTo(1.0F, 0.0F);
    this.mOriginalPatternPath = localPath;
  }
  
  public PatternPathMotion(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.mPatternPath = new Path();
    this.mTempMatrix = new Matrix();
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.PATTERN_PATH_MOTION);
    try
    {
      paramAttributeSet = TypedArrayUtils.getNamedString(paramContext, (XmlPullParser)paramAttributeSet, "patternPathData", 0);
      Log5ECF72.a(paramAttributeSet);
      LogE84000.a(paramAttributeSet);
      Log229316.a(paramAttributeSet);
      if (paramAttributeSet != null)
      {
        setPatternPath(PathParser.createPathFromPathData(paramAttributeSet));
        return;
      }
      paramAttributeSet = new java/lang/RuntimeException;
      paramAttributeSet.<init>("pathData must be supplied for patternPathMotion");
      throw paramAttributeSet;
    }
    finally
    {
      paramContext.recycle();
    }
  }
  
  public PatternPathMotion(Path paramPath)
  {
    this.mPatternPath = new Path();
    this.mTempMatrix = new Matrix();
    setPatternPath(paramPath);
  }
  
  private static float distance(float paramFloat1, float paramFloat2)
  {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
  }
  
  public Path getPath(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramFloat3 -= paramFloat1;
    float f = paramFloat4 - paramFloat2;
    paramFloat4 = distance(paramFloat3, f);
    double d = Math.atan2(f, paramFloat3);
    this.mTempMatrix.setScale(paramFloat4, paramFloat4);
    this.mTempMatrix.postRotate((float)Math.toDegrees(d));
    this.mTempMatrix.postTranslate(paramFloat1, paramFloat2);
    Path localPath = new Path();
    this.mPatternPath.transform(this.mTempMatrix, localPath);
    return localPath;
  }
  
  public Path getPatternPath()
  {
    return this.mOriginalPatternPath;
  }
  
  public void setPatternPath(Path paramPath)
  {
    PathMeasure localPathMeasure = new PathMeasure(paramPath, false);
    float f1 = localPathMeasure.getLength();
    float[] arrayOfFloat = new float[2];
    localPathMeasure.getPosTan(f1, arrayOfFloat, null);
    float f4 = arrayOfFloat[0];
    float f3 = arrayOfFloat[1];
    localPathMeasure.getPosTan(0.0F, arrayOfFloat, null);
    float f2 = arrayOfFloat[0];
    f1 = arrayOfFloat[1];
    if ((f2 == f4) && (f1 == f3)) {
      throw new IllegalArgumentException("pattern must not end at the starting point");
    }
    this.mTempMatrix.setTranslate(-f2, -f1);
    f2 = f4 - f2;
    f1 = f3 - f1;
    f3 = 1.0F / distance(f2, f1);
    this.mTempMatrix.postScale(f3, f3);
    double d = Math.atan2(f1, f2);
    this.mTempMatrix.postRotate((float)Math.toDegrees(-d));
    paramPath.transform(this.mTempMatrix, this.mPatternPath);
    this.mOriginalPatternPath = paramPath;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/PatternPathMotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */