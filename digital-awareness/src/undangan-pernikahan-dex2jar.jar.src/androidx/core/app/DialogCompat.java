package androidx.core.app;

import android.app.Dialog;
import android.os.Build.VERSION;
import android.view.View;

public class DialogCompat
{
  public static View requireViewById(Dialog paramDialog, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return (View)Api28Impl.requireViewById(paramDialog, paramInt);
    }
    paramDialog = paramDialog.findViewById(paramInt);
    if (paramDialog != null) {
      return paramDialog;
    }
    throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
  }
  
  static class Api28Impl
  {
    static <T> T requireViewById(Dialog paramDialog, int paramInt)
    {
      return paramDialog.requireViewById(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/DialogCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */