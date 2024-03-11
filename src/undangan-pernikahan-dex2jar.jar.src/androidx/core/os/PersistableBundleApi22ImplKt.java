package androidx.core.os;

import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\013\n\000\n\002\020\030\n\000\bÃ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\"\020\003\032\0020\0042\006\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\006\020\t\032\0020\nH\007J\"\020\013\032\0020\0042\006\020\005\032\0020\0062\b\020\007\032\004\030\0010\b2\006\020\t\032\0020\fH\007¨\006\r"}, d2={"Landroidx/core/os/PersistableBundleApi22ImplKt;", "", "()V", "putBoolean", "", "persistableBundle", "Landroid/os/PersistableBundle;", "key", "", "value", "", "putBooleanArray", "", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class PersistableBundleApi22ImplKt
{
  public static final PersistableBundleApi22ImplKt INSTANCE = new PersistableBundleApi22ImplKt();
  
  @JvmStatic
  public static final void putBoolean(PersistableBundle paramPersistableBundle, String paramString, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramPersistableBundle, "persistableBundle");
    paramPersistableBundle.putBoolean(paramString, paramBoolean);
  }
  
  @JvmStatic
  public static final void putBooleanArray(PersistableBundle paramPersistableBundle, String paramString, boolean[] paramArrayOfBoolean)
  {
    Intrinsics.checkNotNullParameter(paramPersistableBundle, "persistableBundle");
    Intrinsics.checkNotNullParameter(paramArrayOfBoolean, "value");
    paramPersistableBundle.putBooleanArray(paramString, paramArrayOfBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/PersistableBundleApi22ImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */