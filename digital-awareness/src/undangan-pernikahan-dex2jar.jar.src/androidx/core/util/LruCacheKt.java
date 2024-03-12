package androidx.core.util;

import android.util.LruCache;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000:\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\020\013\n\002\b\003\n\002\020\002\n\000\032û\001\020\000\032\016\022\004\022\002H\002\022\004\022\002H\0030\001\"\b\b\000\020\002*\0020\004\"\b\b\001\020\003*\0020\0042\006\020\005\032\0020\00628\b\006\020\007\0322\022\023\022\021H\002¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\023\022\021H\003¢\006\f\b\t\022\b\b\n\022\004\b\b(\f\022\004\022\0020\0060\b2%\b\006\020\r\032\037\022\023\022\021H\002¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\006\022\004\030\001H\0030\0162d\b\006\020\017\032^\022\023\022\0210\021¢\006\f\b\t\022\b\b\n\022\004\b\b(\022\022\023\022\021H\002¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\023\022\021H\003¢\006\f\b\t\022\b\b\n\022\004\b\b(\023\022\025\022\023\030\001H\003¢\006\f\b\t\022\b\b\n\022\004\b\b(\024\022\004\022\0020\0250\020H\bø\001\000\002\007\n\005\b20\001¨\006\026"}, d2={"lruCache", "Landroid/util/LruCache;", "K", "V", "", "maxSize", "", "sizeOf", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "key", "value", "create", "Lkotlin/Function1;", "onEntryRemoved", "Lkotlin/Function4;", "", "evicted", "oldValue", "newValue", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class LruCacheKt
{
  public static final <K, V> LruCache<K, V> lruCache(int paramInt, final Function2<? super K, ? super V, Integer> paramFunction2, final Function1<? super K, ? extends V> paramFunction1, final Function4<? super Boolean, ? super K, ? super V, ? super V, Unit> paramFunction4)
  {
    Intrinsics.checkNotNullParameter(paramFunction2, "sizeOf");
    Intrinsics.checkNotNullParameter(paramFunction1, "create");
    Intrinsics.checkNotNullParameter(paramFunction4, "onEntryRemoved");
    (LruCache)new LruCache(paramInt)
    {
      protected V create(K paramAnonymousK)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousK, "key");
        return (V)paramFunction1.invoke(paramAnonymousK);
      }
      
      protected void entryRemoved(boolean paramAnonymousBoolean, K paramAnonymousK, V paramAnonymousV1, V paramAnonymousV2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousK, "key");
        Intrinsics.checkNotNullParameter(paramAnonymousV1, "oldValue");
        paramFunction4.invoke(Boolean.valueOf(paramAnonymousBoolean), paramAnonymousK, paramAnonymousV1, paramAnonymousV2);
      }
      
      protected int sizeOf(K paramAnonymousK, V paramAnonymousV)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousK, "key");
        Intrinsics.checkNotNullParameter(paramAnonymousV, "value");
        return ((Number)paramFunction2.invoke(paramAnonymousK, paramAnonymousV)).intValue();
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/LruCacheKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */