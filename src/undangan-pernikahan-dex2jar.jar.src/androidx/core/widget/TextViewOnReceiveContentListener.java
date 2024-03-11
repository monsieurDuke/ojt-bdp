package androidx.core.widget;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.Selection;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.OnReceiveContentListener;

public final class TextViewOnReceiveContentListener
  implements OnReceiveContentListener
{
  private static final String LOG_TAG = "ReceiveContent";
  
  private static CharSequence coerceToText(Context paramContext, ClipData.Item paramItem, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.coerce(paramContext, paramItem, paramInt);
    }
    return ApiImpl.coerce(paramContext, paramItem, paramInt);
  }
  
  private static void replaceSelection(Editable paramEditable, CharSequence paramCharSequence)
  {
    int j = Selection.getSelectionStart(paramEditable);
    int k = Selection.getSelectionEnd(paramEditable);
    int i = Math.max(0, Math.min(j, k));
    j = Math.max(0, Math.max(j, k));
    Selection.setSelection(paramEditable, j);
    paramEditable.replace(i, j, paramCharSequence);
  }
  
  public ContentInfoCompat onReceiveContent(View paramView, ContentInfoCompat paramContentInfoCompat)
  {
    if (Log.isLoggable("ReceiveContent", 3)) {
      Log.d("ReceiveContent", "onReceive: " + paramContentInfoCompat);
    }
    if (paramContentInfoCompat.getSource() == 2) {
      return paramContentInfoCompat;
    }
    ClipData localClipData = paramContentInfoCompat.getClip();
    int m = paramContentInfoCompat.getFlags();
    paramContentInfoCompat = (TextView)paramView;
    paramView = (Editable)paramContentInfoCompat.getText();
    paramContentInfoCompat = paramContentInfoCompat.getContext();
    int j = 0;
    int i = 0;
    while (i < localClipData.getItemCount())
    {
      CharSequence localCharSequence = coerceToText(paramContentInfoCompat, localClipData.getItemAt(i), m);
      int k = j;
      if (localCharSequence != null) {
        if (j == 0)
        {
          replaceSelection(paramView, localCharSequence);
          k = 1;
        }
        else
        {
          paramView.insert(Selection.getSelectionEnd(paramView), "\n");
          paramView.insert(Selection.getSelectionEnd(paramView), localCharSequence);
          k = j;
        }
      }
      i++;
      j = k;
    }
    return null;
  }
  
  private static final class Api16Impl
  {
    static CharSequence coerce(Context paramContext, ClipData.Item paramItem, int paramInt)
    {
      if ((paramInt & 0x1) != 0)
      {
        paramContext = paramItem.coerceToText(paramContext);
        if ((paramContext instanceof Spanned)) {
          paramContext = paramContext.toString();
        }
        return paramContext;
      }
      return paramItem.coerceToStyledText(paramContext);
    }
  }
  
  private static final class ApiImpl
  {
    static CharSequence coerce(Context paramContext, ClipData.Item paramItem, int paramInt)
    {
      paramItem = paramItem.coerceToText(paramContext);
      paramContext = paramItem;
      if ((paramInt & 0x1) != 0)
      {
        paramContext = paramItem;
        if ((paramItem instanceof Spanned)) {
          paramContext = paramItem.toString();
        }
      }
      return paramContext;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/TextViewOnReceiveContentListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */