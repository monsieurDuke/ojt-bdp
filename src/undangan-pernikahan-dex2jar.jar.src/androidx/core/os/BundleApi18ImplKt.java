package androidx.core.os;

import android.os.Bundle;
import android.os.IBinder;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000$\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\bÃ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\"\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH\007¨\006\013"}, d2={"Landroidx/core/os/BundleApi18ImplKt;", "", "()V", "putBinder", "", "bundle", "Landroid/os/Bundle;", "key", "", "value", "Landroid/os/IBinder;", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class BundleApi18ImplKt
{
  public static final BundleApi18ImplKt INSTANCE = new BundleApi18ImplKt();
  
  @JvmStatic
  public static final void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder)
  {
    Intrinsics.checkNotNullParameter(paramBundle, "bundle");
    Intrinsics.checkNotNullParameter(paramString, "key");
    paramBundle.putBinder(paramString, paramIBinder);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/BundleApi18ImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */