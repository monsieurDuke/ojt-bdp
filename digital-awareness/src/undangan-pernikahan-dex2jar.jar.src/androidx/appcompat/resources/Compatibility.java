package androidx.appcompat.resources;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class Compatibility
{
  public static class Api15Impl
  {
    public static void getValueForDensity(Resources paramResources, int paramInt1, int paramInt2, TypedValue paramTypedValue, boolean paramBoolean)
    {
      paramResources.getValueForDensity(paramInt1, paramInt2, paramTypedValue, paramBoolean);
    }
  }
  
  public static class Api18Impl
  {
    public static void setAutoCancel(ObjectAnimator paramObjectAnimator, boolean paramBoolean)
    {
      paramObjectAnimator.setAutoCancel(paramBoolean);
    }
  }
  
  public static class Api21Impl
  {
    public static Drawable createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
      throws IOException, XmlPullParserException
    {
      return Drawable.createFromXmlInner(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
    
    public static int getChangingConfigurations(TypedArray paramTypedArray)
    {
      return paramTypedArray.getChangingConfigurations();
    }
    
    public static void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
      throws IOException, XmlPullParserException
    {
      paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/resources/Compatibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */