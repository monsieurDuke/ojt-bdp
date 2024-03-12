package androidx.transition;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.ImageView;
import java.lang.reflect.Field;

class ImageViewUtils
{
  private static Field sDrawMatrixField;
  private static boolean sDrawMatrixFieldFetched;
  private static boolean sTryHiddenAnimateTransform = true;
  
  static void animateTransform(ImageView paramImageView, Matrix paramMatrix)
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      paramImageView.animateTransform(paramMatrix);
    }
    else if (paramMatrix == null)
    {
      paramMatrix = paramImageView.getDrawable();
      if (paramMatrix != null)
      {
        paramMatrix.setBounds(0, 0, paramImageView.getWidth() - paramImageView.getPaddingLeft() - paramImageView.getPaddingRight(), paramImageView.getHeight() - paramImageView.getPaddingTop() - paramImageView.getPaddingBottom());
        paramImageView.invalidate();
      }
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      hiddenAnimateTransform(paramImageView, paramMatrix);
    }
    else
    {
      Object localObject1 = paramImageView.getDrawable();
      if (localObject1 != null)
      {
        ((Drawable)localObject1).setBounds(0, 0, ((Drawable)localObject1).getIntrinsicWidth(), ((Drawable)localObject1).getIntrinsicHeight());
        localObject1 = null;
        Object localObject2 = null;
        fetchDrawMatrixField();
        Object localObject3 = sDrawMatrixField;
        if (localObject3 != null)
        {
          localObject1 = localObject2;
          try
          {
            localObject2 = (Matrix)((Field)localObject3).get(paramImageView);
            localObject1 = localObject2;
            if (localObject2 == null)
            {
              localObject1 = localObject2;
              localObject3 = new android/graphics/Matrix;
              localObject1 = localObject2;
              ((Matrix)localObject3).<init>();
              localObject2 = localObject3;
              localObject1 = localObject2;
              sDrawMatrixField.set(paramImageView, localObject2);
              localObject1 = localObject2;
            }
          }
          catch (IllegalAccessException localIllegalAccessException) {}
        }
        if (localObject1 != null) {
          ((Matrix)localObject1).set(paramMatrix);
        }
        paramImageView.invalidate();
      }
    }
  }
  
  private static void fetchDrawMatrixField()
  {
    if (!sDrawMatrixFieldFetched)
    {
      try
      {
        Field localField = ImageView.class.getDeclaredField("mDrawMatrix");
        sDrawMatrixField = localField;
        localField.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException) {}
      sDrawMatrixFieldFetched = true;
    }
  }
  
  private static void hiddenAnimateTransform(ImageView paramImageView, Matrix paramMatrix)
  {
    if (sTryHiddenAnimateTransform) {
      try
      {
        paramImageView.animateTransform(paramMatrix);
      }
      catch (NoSuchMethodError paramImageView)
      {
        sTryHiddenAnimateTransform = false;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ImageViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */