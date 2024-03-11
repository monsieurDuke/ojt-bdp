package androidx.transition;

import android.graphics.Canvas;
import android.os.Build.VERSION;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CanvasUtils
{
  private static Method sInorderBarrierMethod;
  private static boolean sOrderMethodsFetched;
  private static Method sReorderBarrierMethod;
  
  static void enableZ(Canvas paramCanvas, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      if (Build.VERSION.SDK_INT >= 29)
      {
        if (paramBoolean) {
          paramCanvas.enableZ();
        } else {
          paramCanvas.disableZ();
        }
      }
      else
      {
        if (Build.VERSION.SDK_INT == 28) {
          break label172;
        }
        if (!sOrderMethodsFetched)
        {
          try
          {
            Method localMethod1 = Canvas.class.getDeclaredMethod("insertReorderBarrier", new Class[0]);
            sReorderBarrierMethod = localMethod1;
            localMethod1.setAccessible(true);
            localMethod1 = Canvas.class.getDeclaredMethod("insertInorderBarrier", new Class[0]);
            sInorderBarrierMethod = localMethod1;
            localMethod1.setAccessible(true);
          }
          catch (NoSuchMethodException localNoSuchMethodException) {}
          sOrderMethodsFetched = true;
        }
        Method localMethod2;
        if (paramBoolean) {
          try
          {
            localMethod2 = sReorderBarrierMethod;
            if (localMethod2 != null) {
              localMethod2.invoke(paramCanvas, new Object[0]);
            }
          }
          catch (InvocationTargetException paramCanvas)
          {
            break label159;
          }
          catch (IllegalAccessException paramCanvas)
          {
            break label171;
          }
        }
        if (!paramBoolean)
        {
          localMethod2 = sInorderBarrierMethod;
          if (localMethod2 != null)
          {
            localMethod2.invoke(paramCanvas, new Object[0]);
            break label171;
            label159:
            throw new RuntimeException(paramCanvas.getCause());
          }
        }
      }
    }
    label171:
    return;
    label172:
    throw new IllegalStateException("This method doesn't work on Pie!");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/CanvasUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */