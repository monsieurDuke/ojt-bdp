package androidx.core.content;

import android.os.RemoteException;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback;

public class UnusedAppRestrictionsBackportCallback
{
  private IUnusedAppRestrictionsBackportCallback mCallback;
  
  public UnusedAppRestrictionsBackportCallback(IUnusedAppRestrictionsBackportCallback paramIUnusedAppRestrictionsBackportCallback)
  {
    this.mCallback = paramIUnusedAppRestrictionsBackportCallback;
  }
  
  public void onResult(boolean paramBoolean1, boolean paramBoolean2)
    throws RemoteException
  {
    this.mCallback.onIsPermissionRevocationEnabledForAppResult(paramBoolean1, paramBoolean2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/UnusedAppRestrictionsBackportCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */