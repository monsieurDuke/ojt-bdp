package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableCompat
{
  private static final String TAG = "DrawableCompat";
  private static Method sGetLayoutDirectionMethod;
  private static boolean sGetLayoutDirectionMethodFetched;
  private static Method sSetLayoutDirectionMethod;
  private static boolean sSetLayoutDirectionMethodFetched;
  
  public static void applyTheme(Drawable paramDrawable, Resources.Theme paramTheme)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.applyTheme(paramDrawable, paramTheme);
    }
  }
  
  public static boolean canApplyTheme(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.canApplyTheme(paramDrawable);
    }
    return false;
  }
  
  public static void clearColorFilter(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramDrawable.clearColorFilter();
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      paramDrawable.clearColorFilter();
      if ((paramDrawable instanceof InsetDrawable))
      {
        clearColorFilter(Api19Impl.getDrawable((InsetDrawable)paramDrawable));
      }
      else if ((paramDrawable instanceof WrappedDrawable))
      {
        clearColorFilter(((WrappedDrawable)paramDrawable).getWrappedDrawable());
      }
      else if ((paramDrawable instanceof DrawableContainer))
      {
        paramDrawable = (DrawableContainer)paramDrawable;
        paramDrawable = (DrawableContainer.DrawableContainerState)paramDrawable.getConstantState();
        if (paramDrawable != null)
        {
          int i = 0;
          int j = paramDrawable.getChildCount();
          while (i < j)
          {
            Drawable localDrawable = Api19Impl.getChild(paramDrawable, i);
            if (localDrawable != null) {
              clearColorFilter(localDrawable);
            }
            i++;
          }
        }
      }
    }
    else
    {
      paramDrawable.clearColorFilter();
    }
  }
  
  public static int getAlpha(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.getAlpha(paramDrawable);
    }
    return 0;
  }
  
  public static ColorFilter getColorFilter(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getColorFilter(paramDrawable);
    }
    return null;
  }
  
  public static int getLayoutDirection(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getLayoutDirection(paramDrawable);
    }
    if (Build.VERSION.SDK_INT >= 17)
    {
      if (!sGetLayoutDirectionMethodFetched)
      {
        try
        {
          Method localMethod1 = Drawable.class.getDeclaredMethod("getLayoutDirection", new Class[0]);
          sGetLayoutDirectionMethod = localMethod1;
          localMethod1.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("DrawableCompat", "Failed to retrieve getLayoutDirection() method", localNoSuchMethodException);
        }
        sGetLayoutDirectionMethodFetched = true;
      }
      Method localMethod2 = sGetLayoutDirectionMethod;
      if (localMethod2 != null) {
        try
        {
          int i = ((Integer)localMethod2.invoke(paramDrawable, new Object[0])).intValue();
          return i;
        }
        catch (Exception paramDrawable)
        {
          Log.i("DrawableCompat", "Failed to invoke getLayoutDirection() via reflection", paramDrawable);
          sGetLayoutDirectionMethod = null;
        }
      }
      return 0;
    }
    return 0;
  }
  
  public static void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
    throws XmlPullParserException, IOException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.inflate(paramDrawable, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    } else {
      paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet);
    }
  }
  
  public static boolean isAutoMirrored(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.isAutoMirrored(paramDrawable);
    }
    return false;
  }
  
  @Deprecated
  public static void jumpToCurrentState(Drawable paramDrawable)
  {
    paramDrawable.jumpToCurrentState();
  }
  
  public static void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      Api19Impl.setAutoMirrored(paramDrawable, paramBoolean);
    }
  }
  
  public static void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setHotspot(paramDrawable, paramFloat1, paramFloat2);
    }
  }
  
  public static void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setHotspotBounds(paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public static boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.setLayoutDirection(paramDrawable, paramInt);
    }
    if (Build.VERSION.SDK_INT >= 17)
    {
      if (!sSetLayoutDirectionMethodFetched)
      {
        try
        {
          Method localMethod1 = Drawable.class.getDeclaredMethod("setLayoutDirection", new Class[] { Integer.TYPE });
          sSetLayoutDirectionMethod = localMethod1;
          localMethod1.setAccessible(true);
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Log.i("DrawableCompat", "Failed to retrieve setLayoutDirection(int) method", localNoSuchMethodException);
        }
        sSetLayoutDirectionMethodFetched = true;
      }
      Method localMethod2 = sSetLayoutDirectionMethod;
      if (localMethod2 != null) {
        try
        {
          localMethod2.invoke(paramDrawable, new Object[] { Integer.valueOf(paramInt) });
          return true;
        }
        catch (Exception paramDrawable)
        {
          Log.i("DrawableCompat", "Failed to invoke setLayoutDirection(int) via reflection", paramDrawable);
          sSetLayoutDirectionMethod = null;
        }
      }
      return false;
    }
    return false;
  }
  
  public static void setTint(Drawable paramDrawable, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setTint(paramDrawable, paramInt);
    } else if ((paramDrawable instanceof TintAwareDrawable)) {
      ((TintAwareDrawable)paramDrawable).setTint(paramInt);
    }
  }
  
  public static void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setTintList(paramDrawable, paramColorStateList);
    } else if ((paramDrawable instanceof TintAwareDrawable)) {
      ((TintAwareDrawable)paramDrawable).setTintList(paramColorStateList);
    }
  }
  
  public static void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setTintMode(paramDrawable, paramMode);
    } else if ((paramDrawable instanceof TintAwareDrawable)) {
      ((TintAwareDrawable)paramDrawable).setTintMode(paramMode);
    }
  }
  
  public static <T extends Drawable> T unwrap(Drawable paramDrawable)
  {
    if ((paramDrawable instanceof WrappedDrawable)) {
      return ((WrappedDrawable)paramDrawable).getWrappedDrawable();
    }
    return paramDrawable;
  }
  
  public static Drawable wrap(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramDrawable;
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (!(paramDrawable instanceof TintAwareDrawable)) {
        return new WrappedDrawableApi21(paramDrawable);
      }
      return paramDrawable;
    }
    if (!(paramDrawable instanceof TintAwareDrawable)) {
      return new WrappedDrawableApi14(paramDrawable);
    }
    return paramDrawable;
  }
  
  static class Api19Impl
  {
    static int getAlpha(Drawable paramDrawable)
    {
      return paramDrawable.getAlpha();
    }
    
    static Drawable getChild(DrawableContainer.DrawableContainerState paramDrawableContainerState, int paramInt)
    {
      return paramDrawableContainerState.getChild(paramInt);
    }
    
    static Drawable getDrawable(InsetDrawable paramInsetDrawable)
    {
      return paramInsetDrawable.getDrawable();
    }
    
    static boolean isAutoMirrored(Drawable paramDrawable)
    {
      return paramDrawable.isAutoMirrored();
    }
    
    static void setAutoMirrored(Drawable paramDrawable, boolean paramBoolean)
    {
      paramDrawable.setAutoMirrored(paramBoolean);
    }
  }
  
  static class Api21Impl
  {
    static void applyTheme(Drawable paramDrawable, Resources.Theme paramTheme)
    {
      paramDrawable.applyTheme(paramTheme);
    }
    
    static boolean canApplyTheme(Drawable paramDrawable)
    {
      return paramDrawable.canApplyTheme();
    }
    
    static ColorFilter getColorFilter(Drawable paramDrawable)
    {
      return paramDrawable.getColorFilter();
    }
    
    static void inflate(Drawable paramDrawable, Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme)
      throws XmlPullParserException, IOException
    {
      paramDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    }
    
    static void setHotspot(Drawable paramDrawable, float paramFloat1, float paramFloat2)
    {
      paramDrawable.setHotspot(paramFloat1, paramFloat2);
    }
    
    static void setHotspotBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramDrawable.setHotspotBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void setTint(Drawable paramDrawable, int paramInt)
    {
      paramDrawable.setTint(paramInt);
    }
    
    static void setTintList(Drawable paramDrawable, ColorStateList paramColorStateList)
    {
      paramDrawable.setTintList(paramColorStateList);
    }
    
    static void setTintMode(Drawable paramDrawable, PorterDuff.Mode paramMode)
    {
      paramDrawable.setTintMode(paramMode);
    }
  }
  
  static class Api23Impl
  {
    static int getLayoutDirection(Drawable paramDrawable)
    {
      return paramDrawable.getLayoutDirection();
    }
    
    static boolean setLayoutDirection(Drawable paramDrawable, int paramInt)
    {
      return paramDrawable.setLayoutDirection(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/DrawableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */