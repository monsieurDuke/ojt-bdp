package androidx.core.content;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService.Stub;

public abstract class UnusedAppRestrictionsBackportService
  extends Service
{
  public static final String ACTION_UNUSED_APP_RESTRICTIONS_BACKPORT_CONNECTION = "android.support.unusedapprestrictions.action.CustomUnusedAppRestrictionsBackportService";
  private IUnusedAppRestrictionsBackportService.Stub mBinder = new IUnusedAppRestrictionsBackportService.Stub()
  {
    public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback paramAnonymousIUnusedAppRestrictionsBackportCallback)
      throws RemoteException
    {
      if (paramAnonymousIUnusedAppRestrictionsBackportCallback == null) {
        return;
      }
      paramAnonymousIUnusedAppRestrictionsBackportCallback = new UnusedAppRestrictionsBackportCallback(paramAnonymousIUnusedAppRestrictionsBackportCallback);
      UnusedAppRestrictionsBackportService.this.isPermissionRevocationEnabled(paramAnonymousIUnusedAppRestrictionsBackportCallback);
    }
  };
  
  protected abstract void isPermissionRevocationEnabled(UnusedAppRestrictionsBackportCallback paramUnusedAppRestrictionsBackportCallback);
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/UnusedAppRestrictionsBackportService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */