package kotlin.reflect;

import kotlin.Metadata;
import kotlin.Unit;

@Metadata(d1={"\000\024\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\bf\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002:\001\007R\030\020\003\032\b\022\004\022\0028\0000\004X¦\004¢\006\006\032\004\b\005\020\006¨\006\b"}, d2={"Lkotlin/reflect/KMutableProperty;", "V", "Lkotlin/reflect/KProperty;", "setter", "Lkotlin/reflect/KMutableProperty$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty$Setter;", "Setter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KMutableProperty<V>
  extends KProperty<V>
{
  public abstract Setter<V> getSetter();
  
  @Metadata(d1={"\000\024\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\000\bf\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\0020\0040\003¨\006\005"}, d2={"Lkotlin/reflect/KMutableProperty$Setter;", "V", "Lkotlin/reflect/KProperty$Accessor;", "Lkotlin/reflect/KFunction;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Setter<V>
    extends KProperty.Accessor<V>, KFunction<Unit>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KMutableProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */