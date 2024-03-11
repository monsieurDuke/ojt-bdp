package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.ImageView;

public class ImageViewCompat
{
  public static ColorStateList getImageTintList(ImageView paramImageView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getImageTintList(paramImageView);
    }
    if ((paramImageView instanceof TintableImageSourceView)) {
      paramImageView = ((TintableImageSourceView)paramImageView).getSupportImageTintList();
    } else {
      paramImageView = null;
    }
    return paramImageView;
  }
  
  public static PorterDuff.Mode getImageTintMode(ImageView paramImageView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getImageTintMode(paramImageView);
    }
    if ((paramImageView instanceof TintableImageSourceView)) {
      paramImageView = ((TintableImageSourceView)paramImageView).getSupportImageTintMode();
    } else {
      paramImageView = null;
    }
    return paramImageView;
  }
  
  public static void setImageTintList(ImageView paramImageView, ColorStateList paramColorStateList)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Api21Impl.setImageTintList(paramImageView, paramColorStateList);
      if (Build.VERSION.SDK_INT == 21)
      {
        paramColorStateList = paramImageView.getDrawable();
        if ((paramColorStateList != null) && (Api21Impl.getImageTintList(paramImageView) != null))
        {
          if (paramColorStateList.isStateful()) {
            paramColorStateList.setState(paramImageView.getDrawableState());
          }
          paramImageView.setImageDrawable(paramColorStateList);
        }
      }
    }
    else if ((paramImageView instanceof TintableImageSourceView))
    {
      ((TintableImageSourceView)paramImageView).setSupportImageTintList(paramColorStateList);
    }
  }
  
  public static void setImageTintMode(ImageView paramImageView, PorterDuff.Mode paramMode)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Api21Impl.setImageTintMode(paramImageView, paramMode);
      if (Build.VERSION.SDK_INT == 21)
      {
        paramMode = paramImageView.getDrawable();
        if ((paramMode != null) && (Api21Impl.getImageTintList(paramImageView) != null))
        {
          if (paramMode.isStateful()) {
            paramMode.setState(paramImageView.getDrawableState());
          }
          paramImageView.setImageDrawable(paramMode);
        }
      }
    }
    else if ((paramImageView instanceof TintableImageSourceView))
    {
      ((TintableImageSourceView)paramImageView).setSupportImageTintMode(paramMode);
    }
  }
  
  static class Api21Impl
  {
    static ColorStateList getImageTintList(ImageView paramImageView)
    {
      return paramImageView.getImageTintList();
    }
    
    static PorterDuff.Mode getImageTintMode(ImageView paramImageView)
    {
      return paramImageView.getImageTintMode();
    }
    
    static void setImageTintList(ImageView paramImageView, ColorStateList paramColorStateList)
    {
      paramImageView.setImageTintList(paramColorStateList);
    }
    
    static void setImageTintMode(ImageView paramImageView, PorterDuff.Mode paramMode)
    {
      paramImageView.setImageTintMode(paramMode);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/ImageViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */