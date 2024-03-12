package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R.attr;
import androidx.core.R.styleable;
import androidx.core.math.MathUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ColorStateListInflaterCompat
{
  private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal();
  
  public static ColorStateList createFromXml(Resources paramResources, XmlPullParser paramXmlPullParser, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    AttributeSet localAttributeSet = Xml.asAttributeSet(paramXmlPullParser);
    int i;
    do
    {
      i = paramXmlPullParser.next();
    } while ((i != 2) && (i != 1));
    if (i == 2) {
      return createFromXmlInner(paramResources, paramXmlPullParser, localAttributeSet, paramTheme);
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  public static ColorStateList createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    String str = paramXmlPullParser.getName();
    if (str.equals("selector")) {
      return inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
    throw new XmlPullParserException(paramXmlPullParser.getPositionDescription() + ": invalid color state list tag " + str);
  }
  
  private static TypedValue getTypedValue()
  {
    ThreadLocal localThreadLocal = sTempTypedValue;
    TypedValue localTypedValue2 = (TypedValue)localThreadLocal.get();
    TypedValue localTypedValue1 = localTypedValue2;
    if (localTypedValue2 == null)
    {
      localTypedValue1 = new TypedValue();
      localThreadLocal.set(localTypedValue1);
    }
    return localTypedValue1;
  }
  
  public static ColorStateList inflate(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    try
    {
      paramResources = createFromXml(paramResources, paramResources.getXml(paramInt), paramTheme);
      return paramResources;
    }
    catch (Exception paramResources)
    {
      Log.e("CSLCompat", "Failed to inflate ColorStateList.", paramResources);
    }
    return null;
  }
  
  private static ColorStateList inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth() + 1;
    int[][] arrayOfInt1 = new int[20][];
    int[] arrayOfInt = new int[arrayOfInt1.length];
    int k = 0;
    for (;;)
    {
      int j = paramXmlPullParser.next();
      int m = j;
      if (j == 1) {
        break;
      }
      j = paramXmlPullParser.getDepth();
      if ((j < i) && (m == 3)) {
        break;
      }
      if ((m == 2) && (j <= i) && (paramXmlPullParser.getName().equals("item")))
      {
        Object localObject = obtainAttributes(paramResources, paramTheme, paramAttributeSet, R.styleable.ColorStateListItem);
        j = ((TypedArray)localObject).getResourceId(R.styleable.ColorStateListItem_android_color, -1);
        if ((j != -1) && (!isColorInt(paramResources, j))) {
          try
          {
            j = createFromXml(paramResources, paramResources.getXml(j), paramTheme).getDefaultColor();
          }
          catch (Exception localException)
          {
            j = ((TypedArray)localObject).getColor(R.styleable.ColorStateListItem_android_color, -65281);
          }
        } else {
          j = ((TypedArray)localObject).getColor(R.styleable.ColorStateListItem_android_color, -65281);
        }
        float f1 = 1.0F;
        if (((TypedArray)localObject).hasValue(R.styleable.ColorStateListItem_android_alpha)) {
          f1 = ((TypedArray)localObject).getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0F);
        } else if (((TypedArray)localObject).hasValue(R.styleable.ColorStateListItem_alpha)) {
          f1 = ((TypedArray)localObject).getFloat(R.styleable.ColorStateListItem_alpha, 1.0F);
        }
        float f2;
        if ((Build.VERSION.SDK_INT >= 31) && (((TypedArray)localObject).hasValue(R.styleable.ColorStateListItem_android_lStar))) {
          f2 = ((TypedArray)localObject).getFloat(R.styleable.ColorStateListItem_android_lStar, -1.0F);
        } else {
          f2 = ((TypedArray)localObject).getFloat(R.styleable.ColorStateListItem_lStar, -1.0F);
        }
        ((TypedArray)localObject).recycle();
        int n = 0;
        int i4 = paramAttributeSet.getAttributeCount();
        localObject = new int[i4];
        int i1 = 0;
        while (i1 < i4)
        {
          int i3 = paramAttributeSet.getAttributeNameResource(i1);
          int i2 = n;
          if (i3 != 16843173)
          {
            i2 = n;
            if (i3 != 16843551)
            {
              i2 = n;
              if (i3 != R.attr.alpha)
              {
                i2 = n;
                if (i3 != R.attr.lStar)
                {
                  if (paramAttributeSet.getAttributeBooleanValue(i1, false)) {
                    i2 = i3;
                  } else {
                    i2 = -i3;
                  }
                  localObject[n] = i2;
                  i2 = n + 1;
                }
              }
            }
          }
          i1++;
          n = i2;
        }
        localObject = StateSet.trimStateSet((int[])localObject, n);
        arrayOfInt = GrowingArrayUtils.append(arrayOfInt, k, modulateColorAlpha(j, f1, f2));
        arrayOfInt1 = (int[][])GrowingArrayUtils.append(arrayOfInt1, k, localObject);
        k++;
      }
    }
    paramResources = new int[k];
    paramXmlPullParser = new int[k][];
    System.arraycopy(arrayOfInt, 0, paramResources, 0, k);
    System.arraycopy(arrayOfInt1, 0, paramXmlPullParser, 0, k);
    return new ColorStateList(paramXmlPullParser, paramResources);
  }
  
  private static boolean isColorInt(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = getTypedValue();
    boolean bool = true;
    paramResources.getValue(paramInt, localTypedValue, true);
    if ((localTypedValue.type < 28) || (localTypedValue.type > 31)) {
      bool = false;
    }
    return bool;
  }
  
  private static int modulateColorAlpha(int paramInt, float paramFloat1, float paramFloat2)
  {
    int i;
    if ((paramFloat2 >= 0.0F) && (paramFloat2 <= 100.0F)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramFloat1 == 1.0F) && (i == 0)) {
      return paramInt;
    }
    int k = MathUtils.clamp((int)(Color.alpha(paramInt) * paramFloat1 + 0.5F), 0, 255);
    int j = paramInt;
    if (i != 0)
    {
      CamColor localCamColor = CamColor.fromColor(paramInt);
      j = CamColor.toColor(localCamColor.getHue(), localCamColor.getChroma(), paramFloat2);
    }
    return 0xFFFFFF & j | k << 24;
  }
  
  private static TypedArray obtainAttributes(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int[] paramArrayOfInt)
  {
    if (paramTheme == null) {
      paramResources = paramResources.obtainAttributes(paramAttributeSet, paramArrayOfInt);
    } else {
      paramResources = paramTheme.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt, 0, 0);
    }
    return paramResources;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/ColorStateListInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */