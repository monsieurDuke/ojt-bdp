package com.google.android.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableUtils
{
  public static AttributeSet parseDrawableXml(Context paramContext, int paramInt, CharSequence paramCharSequence)
  {
    try
    {
      paramContext = paramContext.getResources().getXml(paramInt);
      int i;
      do
      {
        i = paramContext.next();
      } while ((i != 2) && (i != 1));
      if (i == 2)
      {
        if (TextUtils.equals(paramContext.getName(), paramCharSequence)) {
          return Xml.asAttributeSet(paramContext);
        }
        localObject = new org/xmlpull/v1/XmlPullParserException;
        paramContext = new java/lang/StringBuilder;
        paramContext.<init>();
        ((XmlPullParserException)localObject).<init>("Must have a <" + paramCharSequence + "> start tag");
        throw ((Throwable)localObject);
      }
      paramContext = new org/xmlpull/v1/XmlPullParserException;
      paramContext.<init>("No start tag found");
      throw paramContext;
    }
    catch (IOException paramContext) {}catch (XmlPullParserException paramContext) {}
    paramCharSequence = new StringBuilder().append("Can't load badge resource ID #0x");
    Object localObject = Integer.toHexString(paramInt);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    paramCharSequence = new Resources.NotFoundException((String)localObject);
    paramCharSequence.initCause(paramContext);
    throw paramCharSequence;
  }
  
  public static void setRippleDrawableRadius(RippleDrawable paramRippleDrawable, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      paramRippleDrawable.setRadius(paramInt);
    }
    try
    {
      RippleDrawable.class.getDeclaredMethod("setMaxRadius", new Class[] { Integer.TYPE }).invoke(paramRippleDrawable, new Object[] { Integer.valueOf(paramInt) });
      return;
    }
    catch (IllegalAccessException paramRippleDrawable) {}catch (InvocationTargetException paramRippleDrawable) {}catch (NoSuchMethodException paramRippleDrawable) {}
    throw new IllegalStateException("Couldn't set RippleDrawable radius", paramRippleDrawable);
  }
  
  public static PorterDuffColorFilter updateTintFilter(Drawable paramDrawable, ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList != null) && (paramMode != null)) {
      return new PorterDuffColorFilter(paramColorStateList.getColorForState(paramDrawable.getState(), 0), paramMode);
    }
    return null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/drawable/DrawableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */