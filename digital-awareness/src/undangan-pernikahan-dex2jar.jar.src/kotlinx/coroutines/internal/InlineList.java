package kotlinx.coroutines.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0004\n\002\030\002\n\000\n\002\020\000\n\002\b\004\n\002\020\013\n\002\b\004\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\007\n\002\020\016\n\002\b\003\b@\030\000*\004\b\000\020\0012\0020\002B\026\022\n\b\002\020\003\032\004\030\0010\002ø\001\000¢\006\004\b\004\020\005J\032\020\006\032\0020\0072\b\020\b\032\004\030\0010\002HÖ\003¢\006\004\b\t\020\nJ$\020\013\032\0020\f2\022\020\r\032\016\022\004\022\0028\000\022\004\022\0020\f0\016H\b¢\006\004\b\017\020\020J\020\020\021\032\0020\022HÖ\001¢\006\004\b\023\020\024J'\020\025\032\b\022\004\022\0028\0000\0002\006\020\026\032\0028\000H\002ø\001\000ø\001\001ø\001\002¢\006\004\b\027\020\030J\020\020\031\032\0020\032HÖ\001¢\006\004\b\033\020\034R\020\020\003\032\004\030\0010\002X\004¢\006\002\n\000\001\003\001\004\030\0010\002ø\001\000\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006\035"}, d2={"Lkotlinx/coroutines/internal/InlineList;", "E", "", "holder", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "equals", "", "other", "equals-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "forEachReversed", "", "action", "Lkotlin/Function1;", "forEachReversed-impl", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "hashCode", "", "hashCode-impl", "(Ljava/lang/Object;)I", "plus", "element", "plus-FjFbRPM", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
@JvmInline
public final class InlineList<E>
{
  private final Object holder;
  
  public static <E> Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    if (!(paramObject2 instanceof InlineList)) {
      return false;
    }
    return Intrinsics.areEqual(paramObject1, ((InlineList)paramObject2).unbox-impl());
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final void forEachReversed-impl(Object paramObject, Function1<? super E, Unit> paramFunction1)
  {
    if (paramObject == null) {
      return;
    }
    if (!(paramObject instanceof ArrayList))
    {
      paramFunction1.invoke(paramObject);
    }
    else
    {
      if (paramObject == null) {
        break label66;
      }
      paramObject = (ArrayList)paramObject;
      int i = ((ArrayList)paramObject).size() - 1;
      if (i >= 0)
      {
        int j;
        do
        {
          j = i - 1;
          paramFunction1.invoke(((ArrayList)paramObject).get(i));
          i = j;
        } while (j >= 0);
      }
    }
    return;
    label66:
    throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
  }
  
  public static int hashCode-impl(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = 0;
    } else {
      i = paramObject.hashCode();
    }
    return i;
  }
  
  public static final Object plus-FjFbRPM(Object paramObject, E paramE)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(paramE instanceof List ^ true))) {
      throw new AssertionError();
    }
    if (paramObject == null)
    {
      paramObject = constructor-impl(paramE);
    }
    else if ((paramObject instanceof ArrayList))
    {
      if (paramObject != null)
      {
        ((ArrayList)paramObject).add(paramE);
        paramObject = constructor-impl(paramObject);
      }
      else
      {
        throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
      }
    }
    else
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(paramObject);
      localArrayList.add(paramE);
      paramObject = constructor-impl(localArrayList);
    }
    return paramObject;
  }
  
  public static String toString-impl(Object paramObject)
  {
    return "InlineList(holder=" + paramObject + ')';
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.holder, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.holder);
  }
  
  public String toString()
  {
    String str = toString-impl(this.holder);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/InlineList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */