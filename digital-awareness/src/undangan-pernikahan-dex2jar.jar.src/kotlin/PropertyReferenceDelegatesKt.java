package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;

@Metadata(d1={"\0004\n\002\b\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\0324\020\000\032\002H\001\"\004\b\000\020\001*\b\022\004\022\002H\0010\0022\b\020\003\032\004\030\0010\0042\n\020\005\032\006\022\002\b\0030\006H\n¢\006\002\020\007\032>\020\000\032\002H\001\"\004\b\000\020\b\"\004\b\001\020\001*\016\022\004\022\002H\b\022\004\022\002H\0010\t2\006\020\003\032\002H\b2\n\020\005\032\006\022\002\b\0030\006H\n¢\006\002\020\n\032<\020\013\032\0020\f\"\004\b\000\020\001*\b\022\004\022\002H\0010\r2\b\020\003\032\004\030\0010\0042\n\020\005\032\006\022\002\b\0030\0062\006\020\016\032\002H\001H\n¢\006\002\020\017\032F\020\013\032\0020\f\"\004\b\000\020\b\"\004\b\001\020\001*\016\022\004\022\002H\b\022\004\022\002H\0010\0202\006\020\003\032\002H\b2\n\020\005\032\006\022\002\b\0030\0062\006\020\016\032\002H\001H\n¢\006\002\020\021¨\006\022"}, d2={"getValue", "V", "Lkotlin/reflect/KProperty0;", "thisRef", "", "property", "Lkotlin/reflect/KProperty;", "(Lkotlin/reflect/KProperty0;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "T", "Lkotlin/reflect/KProperty1;", "(Lkotlin/reflect/KProperty1;Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "Lkotlin/reflect/KMutableProperty0;", "value", "(Lkotlin/reflect/KMutableProperty0;Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "Lkotlin/reflect/KMutableProperty1;", "(Lkotlin/reflect/KMutableProperty1;Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class PropertyReferenceDelegatesKt
{
  private static final <V> V getValue(KProperty0<? extends V> paramKProperty0, Object paramObject, KProperty<?> paramKProperty)
  {
    Intrinsics.checkNotNullParameter(paramKProperty0, "<this>");
    Intrinsics.checkNotNullParameter(paramKProperty, "property");
    return (V)paramKProperty0.get();
  }
  
  private static final <T, V> V getValue(KProperty1<T, ? extends V> paramKProperty1, T paramT, KProperty<?> paramKProperty)
  {
    Intrinsics.checkNotNullParameter(paramKProperty1, "<this>");
    Intrinsics.checkNotNullParameter(paramKProperty, "property");
    return (V)paramKProperty1.get(paramT);
  }
  
  private static final <V> void setValue(KMutableProperty0<V> paramKMutableProperty0, Object paramObject, KProperty<?> paramKProperty, V paramV)
  {
    Intrinsics.checkNotNullParameter(paramKMutableProperty0, "<this>");
    Intrinsics.checkNotNullParameter(paramKProperty, "property");
    paramKMutableProperty0.set(paramV);
  }
  
  private static final <T, V> void setValue(KMutableProperty1<T, V> paramKMutableProperty1, T paramT, KProperty<?> paramKProperty, V paramV)
  {
    Intrinsics.checkNotNullParameter(paramKMutableProperty1, "<this>");
    Intrinsics.checkNotNullParameter(paramKProperty, "property");
    paramKMutableProperty1.set(paramT, paramV);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/PropertyReferenceDelegatesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */