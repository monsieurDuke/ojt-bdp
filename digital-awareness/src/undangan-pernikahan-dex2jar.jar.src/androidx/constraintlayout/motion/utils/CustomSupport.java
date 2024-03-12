package androidx.constraintlayout.motion.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class CustomSupport
{
  private static final String TAG = "CustomSupport";
  
  private static int clamp(int paramInt)
  {
    paramInt = (paramInt & (paramInt >> 31 ^ 0xFFFFFFFF)) - 255;
    return (paramInt & paramInt >> 31) + 255;
  }
  
  public static void setInterpolatedValue(ConstraintAttribute paramConstraintAttribute, View paramView, float[] paramArrayOfFloat)
  {
    Object localObject = paramView.getClass();
    String str = "set" + paramConstraintAttribute.getName();
    try
    {
      int i = 1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[paramConstraintAttribute.getType().ordinal()];
      boolean bool = true;
      int j;
      int k;
      switch (i)
      {
      default: 
        break;
      case 7: 
        ((Class)localObject).getMethod(str, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(paramArrayOfFloat[0]) });
        break;
      case 6: 
        paramConstraintAttribute = ((Class)localObject).getMethod(str, new Class[] { Boolean.TYPE });
        if (paramArrayOfFloat[0] <= 0.5F) {
          bool = false;
        }
        paramConstraintAttribute.invoke(paramView, new Object[] { Boolean.valueOf(bool) });
        break;
      case 5: 
        localObject = new java/lang/RuntimeException;
        paramArrayOfFloat = new java/lang/StringBuilder;
        paramArrayOfFloat.<init>();
        ((RuntimeException)localObject).<init>("unable to interpolate strings " + paramConstraintAttribute.getName());
        throw ((Throwable)localObject);
      case 4: 
        paramConstraintAttribute = ((Class)localObject).getMethod(str, new Class[] { Integer.TYPE });
        j = clamp((int)((float)Math.pow(paramArrayOfFloat[0], 0.45454545454545453D) * 255.0F));
        k = clamp((int)((float)Math.pow(paramArrayOfFloat[1], 0.45454545454545453D) * 255.0F));
        i = clamp((int)((float)Math.pow(paramArrayOfFloat[2], 0.45454545454545453D) * 255.0F));
        paramConstraintAttribute.invoke(paramView, new Object[] { Integer.valueOf(clamp((int)(paramArrayOfFloat[3] * 255.0F)) << 24 | j << 16 | k << 8 | i) });
        break;
      case 3: 
        paramConstraintAttribute = ((Class)localObject).getMethod(str, new Class[] { Drawable.class });
        j = clamp((int)((float)Math.pow(paramArrayOfFloat[0], 0.45454545454545453D) * 255.0F));
        k = clamp((int)((float)Math.pow(paramArrayOfFloat[1], 0.45454545454545453D) * 255.0F));
        i = clamp((int)((float)Math.pow(paramArrayOfFloat[2], 0.45454545454545453D) * 255.0F));
        int m = clamp((int)(paramArrayOfFloat[3] * 255.0F));
        paramArrayOfFloat = new android/graphics/drawable/ColorDrawable;
        paramArrayOfFloat.<init>();
        paramArrayOfFloat.setColor(m << 24 | j << 16 | k << 8 | i);
        paramConstraintAttribute.invoke(paramView, new Object[] { paramArrayOfFloat });
        break;
      case 2: 
        ((Class)localObject).getMethod(str, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(paramArrayOfFloat[0]) });
        break;
      case 1: 
        ((Class)localObject).getMethod(str, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf((int)paramArrayOfFloat[0]) });
      }
    }
    catch (InvocationTargetException paramConstraintAttribute)
    {
      paramConstraintAttribute.printStackTrace();
    }
    catch (IllegalAccessException paramConstraintAttribute)
    {
      paramArrayOfFloat = new StringBuilder().append("cannot access method ").append(str).append(" on View \"");
      paramView = Debug.getName(paramView);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      Log.e("CustomSupport", paramView + "\"");
      paramConstraintAttribute.printStackTrace();
    }
    catch (NoSuchMethodException paramConstraintAttribute)
    {
      paramArrayOfFloat = new StringBuilder().append("no method ").append(str).append(" on View \"");
      paramView = Debug.getName(paramView);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      Log.e("CustomSupport", paramView + "\"");
      paramConstraintAttribute.printStackTrace();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/utils/CustomSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */