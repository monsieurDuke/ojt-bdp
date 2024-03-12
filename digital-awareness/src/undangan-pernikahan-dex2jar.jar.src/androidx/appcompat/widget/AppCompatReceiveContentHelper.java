package androidx.appcompat.widget;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.ContentInfoCompat.Builder;
import androidx.core.view.ViewCompat;

final class AppCompatReceiveContentHelper
{
  private static final String LOG_TAG = "ReceiveContent";
  
  static boolean maybeHandleDragEventViaPerformReceiveContent(View paramView, DragEvent paramDragEvent)
  {
    if ((Build.VERSION.SDK_INT < 31) && (Build.VERSION.SDK_INT >= 24) && (paramDragEvent.getLocalState() == null) && (ViewCompat.getOnReceiveContentMimeTypes(paramView) != null))
    {
      Activity localActivity = tryGetActivity(paramView);
      if (localActivity == null)
      {
        Log.i("ReceiveContent", "Can't handle drop: no activity: view=" + paramView);
        return false;
      }
      if (paramDragEvent.getAction() == 1) {
        return paramView instanceof TextView ^ true;
      }
      if (paramDragEvent.getAction() == 3)
      {
        boolean bool;
        if ((paramView instanceof TextView)) {
          bool = OnDropApi24Impl.onDropForTextView(paramDragEvent, (TextView)paramView, localActivity);
        } else {
          bool = OnDropApi24Impl.onDropForView(paramDragEvent, paramView, localActivity);
        }
        return bool;
      }
      return false;
    }
    return false;
  }
  
  static boolean maybeHandleMenuActionViaPerformReceiveContent(TextView paramTextView, int paramInt)
  {
    int j = Build.VERSION.SDK_INT;
    int i = 0;
    if ((j < 31) && (ViewCompat.getOnReceiveContentMimeTypes(paramTextView) != null) && ((paramInt == 16908322) || (paramInt == 16908337)))
    {
      Object localObject = (ClipboardManager)paramTextView.getContext().getSystemService("clipboard");
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((ClipboardManager)localObject).getPrimaryClip();
      }
      if ((localObject != null) && (((ClipData)localObject).getItemCount() > 0))
      {
        localObject = new ContentInfoCompat.Builder((ClipData)localObject, 1);
        if (paramInt == 16908322) {
          paramInt = i;
        } else {
          paramInt = 1;
        }
        ViewCompat.performReceiveContent(paramTextView, ((ContentInfoCompat.Builder)localObject).setFlags(paramInt).build());
      }
      return true;
    }
    return false;
  }
  
  static Activity tryGetActivity(View paramView)
  {
    for (paramView = paramView.getContext(); (paramView instanceof ContextWrapper); paramView = ((ContextWrapper)paramView).getBaseContext()) {
      if ((paramView instanceof Activity)) {
        return (Activity)paramView;
      }
    }
    return null;
  }
  
  private static final class OnDropApi24Impl
  {
    static boolean onDropForTextView(DragEvent paramDragEvent, TextView paramTextView, Activity paramActivity)
    {
      paramActivity.requestDragAndDropPermissions(paramDragEvent);
      int i = paramTextView.getOffsetForPosition(paramDragEvent.getX(), paramDragEvent.getY());
      paramTextView.beginBatchEdit();
      try
      {
        Selection.setSelection((Spannable)paramTextView.getText(), i);
        paramActivity = new androidx/core/view/ContentInfoCompat$Builder;
        paramActivity.<init>(paramDragEvent.getClipData(), 3);
        ViewCompat.performReceiveContent(paramTextView, paramActivity.build());
        return true;
      }
      finally
      {
        paramTextView.endBatchEdit();
      }
    }
    
    static boolean onDropForView(DragEvent paramDragEvent, View paramView, Activity paramActivity)
    {
      paramActivity.requestDragAndDropPermissions(paramDragEvent);
      ViewCompat.performReceiveContent(paramView, new ContentInfoCompat.Builder(paramDragEvent.getClipData(), 3).build());
      return true;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatReceiveContentHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */