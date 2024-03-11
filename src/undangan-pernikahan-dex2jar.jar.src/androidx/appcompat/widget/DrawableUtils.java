package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build.VERSION;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DrawableUtils
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private static final int[] EMPTY_STATE_SET = new int[0];
  public static final Rect INSETS_NONE = new Rect();
  
  public static boolean canSafelyMutateDrawable(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return true;
    }
    if ((Build.VERSION.SDK_INT < 15) && ((paramDrawable instanceof InsetDrawable))) {
      return false;
    }
    if ((Build.VERSION.SDK_INT < 15) && ((paramDrawable instanceof GradientDrawable))) {
      return false;
    }
    if ((Build.VERSION.SDK_INT < 17) && ((paramDrawable instanceof LayerDrawable))) {
      return false;
    }
    if ((paramDrawable instanceof DrawableContainer))
    {
      paramDrawable = paramDrawable.getConstantState();
      if ((paramDrawable instanceof DrawableContainer.DrawableContainerState))
      {
        paramDrawable = ((DrawableContainer.DrawableContainerState)paramDrawable).getChildren();
        int j = paramDrawable.length;
        for (int i = 0; i < j; i++) {
          if (!canSafelyMutateDrawable(paramDrawable[i])) {
            return false;
          }
        }
      }
    }
    else
    {
      if ((paramDrawable instanceof WrappedDrawable)) {
        return canSafelyMutateDrawable(((WrappedDrawable)paramDrawable).getWrappedDrawable());
      }
      if ((paramDrawable instanceof DrawableWrapper)) {
        return canSafelyMutateDrawable(((DrawableWrapper)paramDrawable).getWrappedDrawable());
      }
      if ((paramDrawable instanceof ScaleDrawable)) {
        return canSafelyMutateDrawable(((ScaleDrawable)paramDrawable).getDrawable());
      }
    }
    return true;
  }
  
  static void fixDrawable(Drawable paramDrawable)
  {
    String str = paramDrawable.getClass().getName();
    if ((Build.VERSION.SDK_INT == 21) && ("android.graphics.drawable.VectorDrawable".equals(str))) {
      forceDrawableStateChange(paramDrawable);
    } else if ((Build.VERSION.SDK_INT >= 29) && (Build.VERSION.SDK_INT < 31) && ("android.graphics.drawable.ColorStateListDrawable".equals(str))) {
      forceDrawableStateChange(paramDrawable);
    }
  }
  
  private static void forceDrawableStateChange(Drawable paramDrawable)
  {
    int[] arrayOfInt = paramDrawable.getState();
    if ((arrayOfInt != null) && (arrayOfInt.length != 0)) {
      paramDrawable.setState(EMPTY_STATE_SET);
    } else {
      paramDrawable.setState(CHECKED_STATE_SET);
    }
    paramDrawable.setState(arrayOfInt);
  }
  
  public static Rect getOpticalBounds(Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      paramDrawable = Api29Impl.getOpticalInsets(paramDrawable);
      return new Rect(paramDrawable.left, paramDrawable.top, paramDrawable.right, paramDrawable.bottom);
    }
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.getOpticalInsets(DrawableCompat.unwrap(paramDrawable));
    }
    return INSETS_NONE;
  }
  
  public static PorterDuff.Mode parseTintMode(int paramInt, PorterDuff.Mode paramMode)
  {
    switch (paramInt)
    {
    default: 
      return paramMode;
    case 16: 
      return PorterDuff.Mode.ADD;
    case 15: 
      return PorterDuff.Mode.SCREEN;
    case 14: 
      return PorterDuff.Mode.MULTIPLY;
    case 9: 
      return PorterDuff.Mode.SRC_ATOP;
    case 5: 
      return PorterDuff.Mode.SRC_IN;
    }
    return PorterDuff.Mode.SRC_OVER;
  }
  
  static class Api18Impl
  {
    private static final Field sBottom;
    private static final Method sGetOpticalInsets;
    private static final Field sLeft;
    private static final boolean sReflectionSuccessful;
    private static final Field sRight;
    private static final Field sTop;
    
    static
    {
      Object localObject26 = null;
      Method localMethod1 = null;
      Object localObject25 = null;
      Object localObject23 = null;
      Object localObject24 = null;
      Object localObject4 = null;
      Object localObject22 = null;
      Object localObject21 = null;
      Object localObject5 = null;
      Object localObject20 = null;
      Object localObject6 = null;
      Field localField = null;
      Object localObject19 = null;
      Object localObject18 = null;
      int j = 0;
      int i = 0;
      Object localObject16 = localObject25;
      Object localObject14 = localObject4;
      Object localObject10 = localObject5;
      Object localObject9 = localField;
      Object localObject17 = localObject26;
      Object localObject15 = localObject23;
      Object localObject12 = localObject22;
      Object localObject8 = localObject20;
      Method localMethod2 = localMethod1;
      Object localObject7 = localObject24;
      Object localObject11 = localObject21;
      Object localObject13 = localObject6;
      Object localObject3;
      try
      {
        Class localClass = Class.forName("android.graphics.Insets");
        localObject16 = localObject25;
        localObject14 = localObject4;
        localObject10 = localObject5;
        localObject9 = localField;
        localObject17 = localObject26;
        localObject15 = localObject23;
        localObject12 = localObject22;
        localObject8 = localObject20;
        localMethod2 = localMethod1;
        localObject7 = localObject24;
        localObject11 = localObject21;
        localObject13 = localObject6;
        localMethod1 = Drawable.class.getMethod("getOpticalInsets", new Class[0]);
        localObject16 = localMethod1;
        localObject14 = localObject4;
        localObject10 = localObject5;
        localObject9 = localField;
        localObject17 = localMethod1;
        localObject15 = localObject23;
        localObject12 = localObject22;
        localObject8 = localObject20;
        localMethod2 = localMethod1;
        localObject7 = localObject24;
        localObject11 = localObject21;
        localObject13 = localObject6;
        localObject4 = localClass.getField("left");
        localObject16 = localMethod1;
        localObject14 = localObject4;
        localObject10 = localObject5;
        localObject9 = localField;
        localObject17 = localMethod1;
        localObject15 = localObject4;
        localObject12 = localObject22;
        localObject8 = localObject20;
        localMethod2 = localMethod1;
        localObject7 = localObject4;
        localObject11 = localObject21;
        localObject13 = localObject6;
        localObject5 = localClass.getField("top");
        localObject16 = localMethod1;
        localObject14 = localObject4;
        localObject10 = localObject5;
        localObject9 = localField;
        localObject17 = localMethod1;
        localObject15 = localObject4;
        localObject12 = localObject5;
        localObject8 = localObject20;
        localMethod2 = localMethod1;
        localObject7 = localObject4;
        localObject11 = localObject5;
        localObject13 = localObject6;
        localObject6 = localClass.getField("right");
        localObject16 = localMethod1;
        localObject14 = localObject4;
        localObject10 = localObject5;
        localObject9 = localObject6;
        localObject17 = localMethod1;
        localObject15 = localObject4;
        localObject12 = localObject5;
        localObject8 = localObject6;
        localMethod2 = localMethod1;
        localObject7 = localObject4;
        localObject11 = localObject5;
        localObject13 = localObject6;
        localField = localClass.getField("bottom");
        localObject8 = localField;
        i = 1;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Object localObject1 = localObject16;
        localObject4 = localObject14;
        localObject5 = localObject10;
        localObject6 = localObject9;
        localObject8 = localObject19;
        i = j;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Object localObject2 = localObject17;
        localObject4 = localObject15;
        localObject5 = localObject12;
        localObject6 = localObject8;
        localObject8 = localObject18;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localObject8 = localObject18;
        localObject6 = localObject13;
        localObject5 = localObject11;
        localObject4 = localObject7;
        localObject3 = localMethod2;
      }
      if (i != 0)
      {
        sGetOpticalInsets = (Method)localObject3;
        sLeft = (Field)localObject4;
        sTop = (Field)localObject5;
        sRight = (Field)localObject6;
        sBottom = (Field)localObject8;
        sReflectionSuccessful = true;
      }
      else
      {
        sGetOpticalInsets = null;
        sLeft = null;
        sTop = null;
        sRight = null;
        sBottom = null;
        sReflectionSuccessful = false;
      }
    }
    
    static Rect getOpticalInsets(Drawable paramDrawable)
    {
      if ((Build.VERSION.SDK_INT < 29) && (sReflectionSuccessful)) {
        try
        {
          paramDrawable = sGetOpticalInsets.invoke(paramDrawable, new Object[0]);
          if (paramDrawable != null)
          {
            paramDrawable = new Rect(sLeft.getInt(paramDrawable), sTop.getInt(paramDrawable), sRight.getInt(paramDrawable), sBottom.getInt(paramDrawable));
            return paramDrawable;
          }
        }
        catch (InvocationTargetException paramDrawable) {}catch (IllegalAccessException paramDrawable) {}
      }
      return DrawableUtils.INSETS_NONE;
    }
  }
  
  static class Api29Impl
  {
    static Insets getOpticalInsets(Drawable paramDrawable)
    {
      return paramDrawable.getOpticalInsets();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/DrawableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */