package androidx.appcompat.widget;

import android.os.Build.VERSION;
import android.view.View;

public class TooltipCompat
{
  public static void setTooltipText(View paramView, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setTooltipText(paramView, paramCharSequence);
    } else {
      TooltipCompatHandler.setTooltipText(paramView, paramCharSequence);
    }
  }
  
  static class Api26Impl
  {
    static void setTooltipText(View paramView, CharSequence paramCharSequence)
    {
      paramView.setTooltipText(paramCharSequence);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/TooltipCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */