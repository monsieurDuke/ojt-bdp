package androidx.core.content;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback.Stub;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService.Stub;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class UnusedAppRestrictionsBackportServiceConnection
  implements ServiceConnection
{
  private final Context mContext;
  private boolean mHasBoundService = false;
  ResolvableFuture<Integer> mResultFuture;
  IUnusedAppRestrictionsBackportService mUnusedAppRestrictionsService = null;
  
  UnusedAppRestrictionsBackportServiceConnection(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private IUnusedAppRestrictionsBackportCallback getBackportCallback()
  {
    new IUnusedAppRestrictionsBackportCallback.Stub()
    {
      public void onIsPermissionRevocationEnabledForAppResult(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2)
        throws RemoteException
      {
        if (paramAnonymousBoolean1)
        {
          if (paramAnonymousBoolean2) {
            UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(Integer.valueOf(3));
          } else {
            UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(Integer.valueOf(2));
          }
        }
        else
        {
          UnusedAppRestrictionsBackportServiceConnection.this.mResultFuture.set(Integer.valueOf(0));
          Log.e("PackageManagerCompat", "Unable to retrieve the permission revocation setting from the backport");
        }
      }
    };
  }
  
  public void connectAndFetchResult(ResolvableFuture<Integer> paramResolvableFuture)
  {
    if (!this.mHasBoundService)
    {
      this.mHasBoundService = true;
      this.mResultFuture = paramResolvableFuture;
      Intent localIntent = new Intent("android.support.unusedapprestrictions.action.CustomUnusedAppRestrictionsBackportService");
      paramResolvableFuture = PackageManagerCompat.getPermissionRevocationVerifierApp(this.mContext.getPackageManager());
      Log5ECF72.a(paramResolvableFuture);
      LogE84000.a(paramResolvableFuture);
      Log229316.a(paramResolvableFuture);
      paramResolvableFuture = localIntent.setPackage(paramResolvableFuture);
      this.mContext.bindService(paramResolvableFuture, this, 1);
      return;
    }
    throw new IllegalStateException("Each UnusedAppRestrictionsBackportServiceConnection can only be bound once.");
  }
  
  public void disconnectFromService()
  {
    if (this.mHasBoundService)
    {
      this.mHasBoundService = false;
      this.mContext.unbindService(this);
      return;
    }
    throw new IllegalStateException("bindService must be called before unbind");
  }
  
  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    paramComponentName = IUnusedAppRestrictionsBackportService.Stub.asInterface(paramIBinder);
    this.mUnusedAppRestrictionsService = paramComponentName;
    try
    {
      paramComponentName.isPermissionRevocationEnabledForApp(getBackportCallback());
    }
    catch (RemoteException paramComponentName)
    {
      this.mResultFuture.set(Integer.valueOf(0));
    }
  }
  
  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    this.mUnusedAppRestrictionsService = null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/UnusedAppRestrictionsBackportServiceConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */