package androidx.core.app.unusedapprestrictions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IUnusedAppRestrictionsBackportCallback
  extends IInterface
{
  public abstract void onIsPermissionRevocationEnabledForAppResult(boolean paramBoolean1, boolean paramBoolean2)
    throws RemoteException;
  
  public static class Default
    implements IUnusedAppRestrictionsBackportCallback
  {
    public IBinder asBinder()
    {
      return null;
    }
    
    public void onIsPermissionRevocationEnabledForAppResult(boolean paramBoolean1, boolean paramBoolean2)
      throws RemoteException
    {}
  }
  
  public static abstract class Stub
    extends Binder
    implements IUnusedAppRestrictionsBackportCallback
  {
    private static final String DESCRIPTOR = "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback";
    static final int TRANSACTION_onIsPermissionRevocationEnabledForAppResult = 1;
    
    public Stub()
    {
      attachInterface(this, "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback");
    }
    
    public static IUnusedAppRestrictionsBackportCallback asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback");
      if ((localIInterface != null) && ((localIInterface instanceof IUnusedAppRestrictionsBackportCallback))) {
        return (IUnusedAppRestrictionsBackportCallback)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public static IUnusedAppRestrictionsBackportCallback getDefaultImpl()
    {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IUnusedAppRestrictionsBackportCallback paramIUnusedAppRestrictionsBackportCallback)
    {
      if (Proxy.sDefaultImpl == null)
      {
        if (paramIUnusedAppRestrictionsBackportCallback != null)
        {
          Proxy.sDefaultImpl = paramIUnusedAppRestrictionsBackportCallback;
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
        paramParcel2.writeString("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback");
        return true;
      }
      paramParcel1.enforceInterface("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback");
      paramInt1 = paramParcel1.readInt();
      boolean bool2 = false;
      boolean bool1;
      if (paramInt1 != 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if (paramParcel1.readInt() != 0) {
        bool2 = true;
      }
      onIsPermissionRevocationEnabledForAppResult(bool1, bool2);
      return true;
    }
    
    private static class Proxy
      implements IUnusedAppRestrictionsBackportCallback
    {
      public static IUnusedAppRestrictionsBackportCallback sDefaultImpl;
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
        return "androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback";
      }
      
      public void onIsPermissionRevocationEnabledForAppResult(boolean paramBoolean1, boolean paramBoolean2)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback");
          int j = 0;
          if (paramBoolean1) {
            i = 1;
          } else {
            i = 0;
          }
          localParcel.writeInt(i);
          int i = j;
          if (paramBoolean2) {
            i = 1;
          }
          localParcel.writeInt(i);
          if ((!this.mRemote.transact(1, localParcel, null, 1)) && (IUnusedAppRestrictionsBackportCallback.Stub.getDefaultImpl() != null))
          {
            IUnusedAppRestrictionsBackportCallback.Stub.getDefaultImpl().onIsPermissionRevocationEnabledForAppResult(paramBoolean1, paramBoolean2);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/unusedapprestrictions/IUnusedAppRestrictionsBackportCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */