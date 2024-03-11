package androidx.core.os;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032\006\020\000\032\0020\001\032;\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004¢\006\002\020\007¨\006\b"}, d2={"bundleOf", "Landroid/os/Bundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/Bundle;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class BundleKt
{
  public static final Bundle bundleOf()
  {
    return new Bundle(0);
  }
  
  public static final Bundle bundleOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    Bundle localBundle = new Bundle(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Object localObject1 = paramVarArgs[i];
      String str = (String)((Pair)localObject1).component1();
      Object localObject2 = ((Pair)localObject1).component2();
      if (localObject2 == null)
      {
        localBundle.putString(str, null);
      }
      else if ((localObject2 instanceof Boolean))
      {
        localBundle.putBoolean(str, ((Boolean)localObject2).booleanValue());
      }
      else if ((localObject2 instanceof Byte))
      {
        localBundle.putByte(str, ((Number)localObject2).byteValue());
      }
      else if ((localObject2 instanceof Character))
      {
        localBundle.putChar(str, ((Character)localObject2).charValue());
      }
      else if ((localObject2 instanceof Double))
      {
        localBundle.putDouble(str, ((Number)localObject2).doubleValue());
      }
      else if ((localObject2 instanceof Float))
      {
        localBundle.putFloat(str, ((Number)localObject2).floatValue());
      }
      else if ((localObject2 instanceof Integer))
      {
        localBundle.putInt(str, ((Number)localObject2).intValue());
      }
      else if ((localObject2 instanceof Long))
      {
        localBundle.putLong(str, ((Number)localObject2).longValue());
      }
      else if ((localObject2 instanceof Short))
      {
        localBundle.putShort(str, ((Number)localObject2).shortValue());
      }
      else if ((localObject2 instanceof Bundle))
      {
        localBundle.putBundle(str, (Bundle)localObject2);
      }
      else if ((localObject2 instanceof CharSequence))
      {
        localBundle.putCharSequence(str, (CharSequence)localObject2);
      }
      else if ((localObject2 instanceof Parcelable))
      {
        localBundle.putParcelable(str, (Parcelable)localObject2);
      }
      else if ((localObject2 instanceof boolean[]))
      {
        localBundle.putBooleanArray(str, (boolean[])localObject2);
      }
      else if ((localObject2 instanceof byte[]))
      {
        localBundle.putByteArray(str, (byte[])localObject2);
      }
      else if ((localObject2 instanceof char[]))
      {
        localBundle.putCharArray(str, (char[])localObject2);
      }
      else if ((localObject2 instanceof double[]))
      {
        localBundle.putDoubleArray(str, (double[])localObject2);
      }
      else if ((localObject2 instanceof float[]))
      {
        localBundle.putFloatArray(str, (float[])localObject2);
      }
      else if ((localObject2 instanceof int[]))
      {
        localBundle.putIntArray(str, (int[])localObject2);
      }
      else if ((localObject2 instanceof long[]))
      {
        localBundle.putLongArray(str, (long[])localObject2);
      }
      else if ((localObject2 instanceof short[]))
      {
        localBundle.putShortArray(str, (short[])localObject2);
      }
      else if ((localObject2 instanceof Object[]))
      {
        localObject1 = localObject2.getClass().getComponentType();
        Intrinsics.checkNotNull(localObject1);
        if (Parcelable.class.isAssignableFrom((Class)localObject1))
        {
          if (localObject2 != null) {
            localBundle.putParcelableArray(str, (Parcelable[])localObject2);
          } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<android.os.Parcelable>");
          }
        }
        else if (String.class.isAssignableFrom((Class)localObject1))
        {
          if (localObject2 != null) {
            localBundle.putStringArray(str, (String[])localObject2);
          } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
          }
        }
        else if (CharSequence.class.isAssignableFrom((Class)localObject1))
        {
          if (localObject2 != null) {
            localBundle.putCharSequenceArray(str, (CharSequence[])localObject2);
          } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<kotlin.CharSequence>");
          }
        }
        else if (Serializable.class.isAssignableFrom((Class)localObject1))
        {
          localBundle.putSerializable(str, (Serializable)localObject2);
        }
        else
        {
          paramVarArgs = ((Class)localObject1).getCanonicalName();
          throw new IllegalArgumentException("Illegal value array type " + paramVarArgs + " for key \"" + str + '"');
        }
      }
      else if ((localObject2 instanceof Serializable))
      {
        localBundle.putSerializable(str, (Serializable)localObject2);
      }
      else if ((Build.VERSION.SDK_INT >= 18) && ((localObject2 instanceof IBinder)))
      {
        BundleApi18ImplKt.putBinder(localBundle, str, (IBinder)localObject2);
      }
      else if ((Build.VERSION.SDK_INT >= 21) && ((localObject2 instanceof Size)))
      {
        BundleApi21ImplKt.putSize(localBundle, str, (Size)localObject2);
      }
      else
      {
        if ((Build.VERSION.SDK_INT < 21) || (!(localObject2 instanceof SizeF))) {
          break label833;
        }
        BundleApi21ImplKt.putSizeF(localBundle, str, (SizeF)localObject2);
      }
      i++;
      continue;
      label833:
      paramVarArgs = localObject2.getClass().getCanonicalName();
      throw new IllegalArgumentException("Illegal value type " + paramVarArgs + " for key \"" + str + '"');
    }
    return localBundle;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/BundleKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */