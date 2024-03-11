package androidx.appcompat.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.core.content.ContextCompat;

public final class AppCompatResources
{
  public static ColorStateList getColorStateList(Context paramContext, int paramInt)
  {
    return ContextCompat.getColorStateList(paramContext, paramInt);
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt)
  {
    return ResourceManagerInternal.get().getDrawable(paramContext, paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/content/res/AppCompatResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */