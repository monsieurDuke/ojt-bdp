package com.google.android.material.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import com.google.android.material.R.dimen;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;

public class MaterialDialogs
{
  public static Rect getDialogBackgroundInsets(Context paramContext, int paramInt1, int paramInt2)
  {
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(paramContext, null, R.styleable.MaterialAlertDialog, paramInt1, paramInt2, new int[0]);
    paramInt2 = localTypedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetStart, paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start));
    int n = localTypedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetTop, paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top));
    int k = localTypedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetEnd, paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end));
    int i1 = localTypedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetBottom, paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom));
    localTypedArray.recycle();
    int j = paramInt2;
    int i = k;
    int m = j;
    paramInt1 = i;
    if (Build.VERSION.SDK_INT >= 17)
    {
      m = j;
      paramInt1 = i;
      if (paramContext.getResources().getConfiguration().getLayoutDirection() == 1)
      {
        paramInt1 = paramInt2;
        m = k;
      }
    }
    return new Rect(m, n, paramInt1, i1);
  }
  
  public static InsetDrawable insetDrawable(Drawable paramDrawable, Rect paramRect)
  {
    return new InsetDrawable(paramDrawable, paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/dialog/MaterialDialogs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */