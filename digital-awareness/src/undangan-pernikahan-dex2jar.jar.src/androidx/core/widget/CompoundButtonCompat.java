package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat
{
  private static final String TAG = "CompoundButtonCompat";
  private static Field sButtonDrawableField;
  private static boolean sButtonDrawableFieldFetched;
  
  public static Drawable getButtonDrawable(CompoundButton paramCompoundButton)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getButtonDrawable(paramCompoundButton);
    }
    if (!sButtonDrawableFieldFetched)
    {
      try
      {
        Field localField1 = CompoundButton.class.getDeclaredField("mButtonDrawable");
        sButtonDrawableField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", localNoSuchFieldException);
      }
      sButtonDrawableFieldFetched = true;
    }
    Field localField2 = sButtonDrawableField;
    if (localField2 != null) {
      try
      {
        paramCompoundButton = (Drawable)localField2.get(paramCompoundButton);
        return paramCompoundButton;
      }
      catch (IllegalAccessException paramCompoundButton)
      {
        Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", paramCompoundButton);
        sButtonDrawableField = null;
      }
    }
    return null;
  }
  
  public static ColorStateList getButtonTintList(CompoundButton paramCompoundButton)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getButtonTintList(paramCompoundButton);
    }
    if ((paramCompoundButton instanceof TintableCompoundButton)) {
      return ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintList();
    }
    return null;
  }
  
  public static PorterDuff.Mode getButtonTintMode(CompoundButton paramCompoundButton)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getButtonTintMode(paramCompoundButton);
    }
    if ((paramCompoundButton instanceof TintableCompoundButton)) {
      return ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintMode();
    }
    return null;
  }
  
  public static void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setButtonTintList(paramCompoundButton, paramColorStateList);
    } else if ((paramCompoundButton instanceof TintableCompoundButton)) {
      ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintList(paramColorStateList);
    }
  }
  
  public static void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setButtonTintMode(paramCompoundButton, paramMode);
    } else if ((paramCompoundButton instanceof TintableCompoundButton)) {
      ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintMode(paramMode);
    }
  }
  
  static class Api21Impl
  {
    static ColorStateList getButtonTintList(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonTintList();
    }
    
    static PorterDuff.Mode getButtonTintMode(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonTintMode();
    }
    
    static void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
    {
      paramCompoundButton.setButtonTintList(paramColorStateList);
    }
    
    static void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
    {
      paramCompoundButton.setButtonTintMode(paramMode);
    }
  }
  
  static class Api23Impl
  {
    static Drawable getButtonDrawable(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonDrawable();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/CompoundButtonCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */