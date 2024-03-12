package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;

public class PathInterpolatorCompat
  implements Interpolator
{
  public static final double EPSILON = 1.0E-5D;
  public static final int MAX_NUM_POINTS = 3000;
  private static final float PRECISION = 0.002F;
  private float[] mX;
  private float[] mY;
  
  public PathInterpolatorCompat(Context paramContext, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    this(paramContext.getResources(), paramContext.getTheme(), paramAttributeSet, paramXmlPullParser);
  }
  
  public PathInterpolatorCompat(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PATH_INTERPOLATOR);
    parseInterpolatorFromTypeArray(paramResources, paramXmlPullParser);
    paramResources.recycle();
  }
  
  private void initCubic(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    Path localPath = new Path();
    localPath.moveTo(0.0F, 0.0F);
    localPath.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 1.0F, 1.0F);
    initPath(localPath);
  }
  
  private void initPath(Path paramPath)
  {
    paramPath = new PathMeasure(paramPath, false);
    float f1 = paramPath.getLength();
    int k = Math.min(3000, (int)(f1 / 0.002F) + 1);
    if (k > 0)
    {
      this.mX = new float[k];
      this.mY = new float[k];
      float[] arrayOfFloat = new float[2];
      for (int i = 0; i < k; i++)
      {
        paramPath.getPosTan(i * f1 / (k - 1), arrayOfFloat, null);
        this.mX[i] = arrayOfFloat[0];
        this.mY[i] = arrayOfFloat[1];
      }
      if ((Math.abs(this.mX[0]) <= 1.0E-5D) && (Math.abs(this.mY[0]) <= 1.0E-5D) && (Math.abs(this.mX[(k - 1)] - 1.0F) <= 1.0E-5D) && (Math.abs(this.mY[(k - 1)] - 1.0F) <= 1.0E-5D))
      {
        f1 = 0.0F;
        i = 0;
        int j = 0;
        while (j < k)
        {
          arrayOfFloat = this.mX;
          float f2 = arrayOfFloat[i];
          if (f2 >= f1)
          {
            arrayOfFloat[j] = f2;
            f1 = f2;
            j++;
            i++;
          }
          else
          {
            throw new IllegalArgumentException("The Path cannot loop back on itself, x :" + f2);
          }
        }
        if (!paramPath.nextContour()) {
          return;
        }
        throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
      }
      throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1) start: " + this.mX[0] + "," + this.mY[0] + " end:" + this.mX[(k - 1)] + "," + this.mY[(k - 1)]);
    }
    throw new IllegalArgumentException("The Path has a invalid length " + f1);
  }
  
  private void initQuad(float paramFloat1, float paramFloat2)
  {
    Path localPath = new Path();
    localPath.moveTo(0.0F, 0.0F);
    localPath.quadTo(paramFloat1, paramFloat2, 1.0F, 1.0F);
    initPath(localPath);
  }
  
  private void parseInterpolatorFromTypeArray(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser)
  {
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "pathData"))
    {
      paramXmlPullParser = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "pathData", 4);
      Log5ECF72.a(paramXmlPullParser);
      LogE84000.a(paramXmlPullParser);
      Log229316.a(paramXmlPullParser);
      paramTypedArray = PathParser.createPathFromPathData(paramXmlPullParser);
      if (paramTypedArray != null) {
        initPath(paramTypedArray);
      } else {
        throw new InflateException("The path is null, which is created from " + paramXmlPullParser);
      }
    }
    else
    {
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlX1")) {
        break label193;
      }
      if (!TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlY1")) {
        break label183;
      }
      float f2 = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlX1", 0, 0.0F);
      float f1 = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlY1", 1, 0.0F);
      boolean bool = TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlX2");
      if (bool != TypedArrayUtils.hasAttribute(paramXmlPullParser, "controlY2")) {
        break label173;
      }
      if (!bool) {
        initQuad(f2, f1);
      } else {
        initCubic(f2, f1, TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlX2", 2, 0.0F), TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "controlY2", 3, 0.0F));
      }
    }
    return;
    label173:
    throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
    label183:
    throw new InflateException("pathInterpolator requires the controlY1 attribute");
    label193:
    throw new InflateException("pathInterpolator requires the controlX1 attribute");
  }
  
  public float getInterpolation(float paramFloat)
  {
    if (paramFloat <= 0.0F) {
      return 0.0F;
    }
    if (paramFloat >= 1.0F) {
      return 1.0F;
    }
    int j = 0;
    int i = this.mX.length - 1;
    while (i - j > 1)
    {
      int k = (j + i) / 2;
      if (paramFloat < this.mX[k]) {
        i = k;
      } else {
        j = k;
      }
    }
    float[] arrayOfFloat = this.mX;
    float f2 = arrayOfFloat[i];
    float f1 = arrayOfFloat[j];
    f2 -= f1;
    if (f2 == 0.0F) {
      return this.mY[j];
    }
    f1 = (paramFloat - f1) / f2;
    arrayOfFloat = this.mY;
    paramFloat = arrayOfFloat[j];
    return (arrayOfFloat[i] - paramFloat) * f1 + paramFloat;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/vectordrawable/graphics/drawable/PathInterpolatorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */