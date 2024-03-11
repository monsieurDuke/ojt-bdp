package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IUnusedAppRestrictionsBackportService
  extends IInterface
{
  public abstract void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback paramIUnusedAppRestrictionsBackportCallback)
    throws RemoteException;
  
  public static class Default
    implements IUnusedAppRestrictionsBackportService
  {
    public IBinder asBinder()
    {
      return null;
    }
    
    public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback paramIUnusedAppRestrictionsBackportCallback)
      throws RemoteException
    {}
  }
  
  public static abstract class Stub
    extends Binder
    implements IUnusedAppRestrictionsBackportService
  {
    private static final String DESCRIPTOR = "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService";
    static final int TRANSACTION_isPermissionRevocationEnabledForApp = 1;
    
    public Stub()
    {
      attachInterface(this, "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService");
    }
    
    public static IUnusedAppRestrictionsBackportService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService");
      if ((localIInterface != null) && ((localIInterface instanceof IUnusedAppRestrictionsBackportService))) {
        return (IUnusedAppRestrictionsBackportService)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public static IUnusedAppRestrictionsBackportService getDefaultImpl()
    {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IUnusedAppRestrictionsBackportService paramIUnusedAppRestrictionsBackportService)
    {
      if (Proxy.sDefaultImpl == null)
      {
        if (paramIUnusedAppRestrictionsBackportService != null)
        {
          Proxy.sDefaultImpl = paramIUnusedAppRestrictionsBackportService;
          return true;
        }
        return false;
      }
      throw new IllegalStateException("setDefaultImpl() called twice");
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService");
        return true;
      }
      paramParcel1.enforceInterface("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService");
      isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
      return true;
    }
    
    private static class Proxy
      implements IUnusedAppRestrictionsBackportService
    {
      public static IUnusedAppRestrictionsBackportService sDefaultImpl;
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor()
      {
        return "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService";
      }
      
      public void isPermissionRevocationEnabledForApp(IUnusedAppRestrictionsBackportCallback paramIUnusedAppRestrictionsBackportCallback)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService");
          IBinder localIBinder;
          if (paramIUnusedAppRestrictionsBackportCallback != null) {
            localIBinder = paramIUnusedAppRestrictionsBackportCallback.asBinder();
          } else {
            localIBinder = null;
          }
          localParcel.writeStrongBinder(localIBinder);
          if ((!this.mRemote.transact(1, localParcel, null, 1)) && (IUnusedAppRestrictionsBackportService.Stub.getDefaultImpl() != null))
          {
            IUnusedAppRestrictionsBackportService.Stub.getDefaultImpl().isPermissionRevocationEnabledForApp(paramIUnusedAppRestrictionsBackportCallback);
            return;
          }
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/unusedapprestrictions/IUnusedAppRestrictionsBackportService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */