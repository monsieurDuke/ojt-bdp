package androidx.core.os;

import android.os.Build.VERSION;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\002\n\002\b\002\n\002\020\016\n\002\b\002\bÃ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\007J$\020\007\032\0020\b2\006\020\t\032\0020\0042\b\020\n\032\004\030\0010\0132\b\020\f\032\004\030\0010\001H\007¨\006\r"}, d2={"Landroidx/core/os/PersistableBundleApi21ImplKt;", "", "()V", "createPersistableBundle", "Landroid/os/PersistableBundle;", "capacity", "", "putValue", "", "persistableBundle", "key", "", "value", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class PersistableBundleApi21ImplKt
{
  public static final PersistableBundleApi21ImplKt INSTANCE = new PersistableBundleApi21ImplKt();
  
  @JvmStatic
  public static final PersistableBundle createPersistableBundle(int paramInt)
  {
    return new PersistableBundle(paramInt);
  }
  
  @JvmStatic
  public static final void putValue(PersistableBundle paramPersistableBundle, String paramString, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramPersistableBundle, "persistableBundle");
    Class localClass;
    if (paramObject == null)
    {
      paramPersistableBundle.putString(paramString, null);
    }
    else if ((paramObject instanceof Boolean))
    {
      if (Build.VERSION.SDK_INT >= 22) {
        PersistableBundleApi22ImplKt.putBoolean(paramPersistableBundle, paramString, ((Boolean)paramObject).booleanValue());
      } else {
        throw new IllegalArgumentException("Illegal value type boolean for key \"" + paramString + '"');
      }
    }
    else if ((paramObject instanceof Double))
    {
      paramPersistableBundle.putDouble(paramString, ((Number)paramObject).doubleValue());
    }
    else if ((paramObject instanceof Integer))
    {
      paramPersistableBundle.putInt(paramString, ((Number)paramObject).intValue());
    }
    else if ((paramObject instanceof Long))
    {
      paramPersistableBundle.putLong(paramString, ((Number)paramObject).longValue());
    }
    else if ((paramObject instanceof String))
    {
      paramPersistableBundle.putString(paramString, (String)paramObject);
    }
    else if ((paramObject instanceof boolean[]))
    {
      if (Build.VERSION.SDK_INT >= 22) {
        PersistableBundleApi22ImplKt.putBooleanArray(paramPersistableBundle, paramString, (boolean[])paramObject);
      } else {
        throw new IllegalArgumentException("Illegal value type boolean[] for key \"" + paramString + '"');
      }
    }
    else if ((paramObject instanceof double[]))
    {
      paramPersistableBundle.putDoubleArray(paramString, (double[])paramObject);
    }
    else if ((paramObject instanceof int[]))
    {
      paramPersistableBundle.putIntArray(paramString, (int[])paramObject);
    }
    else if ((paramObject instanceof long[]))
    {
      paramPersistableBundle.putLongArray(paramString, (long[])paramObject);
    }
    else
    {
      if (!(paramObject instanceof Object[])) {
        break label380;
      }
      localClass = paramObject.getClass().getComponentType();
      Intrinsics.checkNotNull(localClass);
      if (!String.class.isAssignableFrom(localClass)) {
        break label334;
      }
      if (paramObject == null) {
        break label324;
      }
      paramPersistableBundle.putStringArray(paramString, (String[])paramObject);
    }
    return;
    label324:
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
    label334:
    paramPersistableBundle = localClass.getCanonicalName();
    throw new IllegalArgumentException("Illegal value array type " + paramPersistableBundle + " for key \"" + paramString + '"');
    label380:
    paramPersistableBundle = paramObject.getClass().getCanonicalName();
    throw new IllegalArgumentException("Illegal value type " + paramPersistableBundle + " for key \"" + paramString + '"');
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/PersistableBundleApi21ImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */