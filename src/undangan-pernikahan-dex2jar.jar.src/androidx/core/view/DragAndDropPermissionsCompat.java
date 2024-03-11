package androidx.core.view;

import android.app.Activity;
import android.os.Build.VERSION;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public final class DragAndDropPermissionsCompat
{
  private final DragAndDropPermissions mDragAndDropPermissions;
  
  private DragAndDropPermissionsCompat(DragAndDropPermissions paramDragAndDropPermissions)
  {
    this.mDragAndDropPermissions = paramDragAndDropPermissions;
  }
  
  public static DragAndDropPermissionsCompat request(Activity paramActivity, DragEvent paramDragEvent)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      paramActivity = Api24Impl.requestDragAndDropPermissions(paramActivity, paramDragEvent);
      if (paramActivity != null) {
        return new DragAndDropPermissionsCompat(paramActivity);
      }
    }
    return null;
  }
  
  public void release()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      Api24Impl.release(this.mDragAndDropPermissions);
    }
  }
  
  static class Api24Impl
  {
    static void release(DragAndDropPermissions paramDragAndDropPermissions)
    {
      paramDragAndDropPermissions.release();
    }
    
    static DragAndDropPermissions requestDragAndDropPermissions(Activity paramActivity, DragEvent paramDragEvent)
    {
      return paramActivity.requestDragAndDropPermissions(paramDragEvent);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/DragAndDropPermissionsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */