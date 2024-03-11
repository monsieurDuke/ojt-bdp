package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ComplexColorCompat
{
  private static final String LOG_TAG = "ComplexColorCompat";
  private int mColor;
  private final ColorStateList mColorStateList;
  private final Shader mShader;
  
  private ComplexColorCompat(Shader paramShader, ColorStateList paramColorStateList, int paramInt)
  {
    this.mShader = paramShader;
    this.mColorStateList = paramColorStateList;
    this.mColor = paramInt;
  }
  
  private static ComplexColorCompat createFromXml(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws IOException, XmlPullParserException
  {
    XmlResourceParser localXmlResourceParser = paramResources.getXml(paramInt);
    AttributeSet localAttributeSet = Xml.asAttributeSet(localXmlResourceParser);
    int i;
    do
    {
      i = localXmlResourceParser.next();
      paramInt = 1;
    } while ((i != 2) && (i != 1));
    if (i == 2)
    {
      String str = localXmlResourceParser.getName();
      switch (str.hashCode())
      {
      }
      for (;;)
      {
        break;
        if (str.equals("selector"))
        {
          paramInt = 0;
          break label117;
          if (str.equals("gradient")) {
            break label117;
          }
        }
      }
      paramInt = -1;
      switch (paramInt)
      {
      default: 
        throw new XmlPullParserException(localXmlResourceParser.getPositionDescription() + ": unsupported complex color tag " + str);
      case 1: 
        label117:
        return from(GradientColorInflaterCompat.createFromXmlInner(paramResources, localXmlResourceParser, localAttributeSet, paramTheme));
      }
      return from(ColorStateListInflaterCompat.createFromXmlInner(paramResources, localXmlResourceParser, localAttributeSet, paramTheme));
    }
    throw new XmlPullParserException("No start tag found");
  }
  
  static ComplexColorCompat from(int paramInt)
  {
    return new ComplexColorCompat(null, null, paramInt);
  }
  
  static ComplexColorCompat from(ColorStateList paramColorStateList)
  {
    return new ComplexColorCompat(null, paramColorStateList, paramColorStateList.getDefaultColor());
  }
  
  static ComplexColorCompat from(Shader paramShader)
  {
    return new ComplexColorCompat(paramShader, null, 0);
  }
  
  public static ComplexColorCompat inflate(Resources paramResources, int paramInt, Resources.Theme paramTheme)
  {
    try
    {
      paramResources = createFromXml(paramResources, paramInt, paramTheme);
      return paramResources;
    }
    catch (Exception paramResources)
    {
      Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", paramResources);
    }
    return null;
  }
  
  public int getColor()
  {
    return this.mColor;
  }
  
  public Shader getShader()
  {
    return this.mShader;
  }
  
  public boolean isGradient()
  {
    boolean bool;
    if (this.mShader != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStateful()
  {
    if (this.mShader == null)
    {
      ColorStateList localColorStateList = this.mColorStateList;
      if ((localColorStateList != null) && (localColorStateList.isStateful())) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public boolean onStateChanged(int[] paramArrayOfInt)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (isStateful())
    {
      ColorStateList localColorStateList = this.mColorStateList;
      int i = localColorStateList.getColorForState(paramArrayOfInt, localColorStateList.getDefaultColor());
      bool1 = bool2;
      if (i != this.mColor)
      {
        bool1 = true;
        this.mColor = i;
      }
    }
    return bool1;
  }
  
  public void setColor(int paramInt)
  {
    this.mColor = paramInt;
  }
  
  public boolean willDraw()
  {
    boolean bool;
    if ((!isGradient()) && (this.mColor == 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/ComplexColorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */