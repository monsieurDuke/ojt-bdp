package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\020\n\002\b\002\n\002\020\000\n\002\030\002\n\002\b\004\032+\020\000\032\002H\001\"\b\b\000\020\001*\0020\002*\b\022\004\022\002H\0010\0032\b\020\004\032\004\030\0010\002H\007¢\006\002\020\005\032-\020\006\032\004\030\001H\001\"\b\b\000\020\001*\0020\002*\b\022\004\022\002H\0010\0032\b\020\004\032\004\030\0010\002H\007¢\006\002\020\005¨\006\007"}, d2={"cast", "T", "", "Lkotlin/reflect/KClass;", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "safeCast", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class KClasses
{
  public static final <T> T cast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    if (paramKClass.isInstance(paramObject))
    {
      Intrinsics.checkNotNull(paramObject, "null cannot be cast to non-null type T of kotlin.reflect.KClasses.cast");
      return (T)paramObject;
    }
    throw new ClassCastException("Value cannot be cast to " + paramKClass.getQualifiedName());
  }
  
  public static final <T> T safeCast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    if (paramKClass.isInstance(paramObject)) {
      Intrinsics.checkNotNull(paramObject, "null cannot be cast to non-null type T of kotlin.reflect.KClasses.safeCast");
    } else {
      paramObject = null;
    }
    return (T)paramObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KClasses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */