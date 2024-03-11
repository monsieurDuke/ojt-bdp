package kotlin.reflect;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\005\bf\030\000*\004\b\000\020\001*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\b\022\004\022\002H\0020\004:\001\016J\035\020\t\032\0020\n2\006\020\013\032\0028\0002\006\020\f\032\0028\001H&¢\006\002\020\rR\036\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006X¦\004¢\006\006\032\004\b\007\020\b¨\006\017"}, d2={"Lkotlin/reflect/KMutableProperty1;", "T", "V", "Lkotlin/reflect/KProperty1;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty1$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty1$Setter;", "set", "", "receiver", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KMutableProperty1<T, V>
  extends KProperty1<T, V>, KMutableProperty<V>
{
  public abstract Setter<T, V> getSetter();
  
  public abstract void set(T paramT, V paramV);
  
  @Metadata(d1={"\000\026\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\000\bf\030\000*\004\b\002\020\001*\004\b\003\020\0022\b\022\004\022\002H\0020\0032\024\022\004\022\002H\001\022\004\022\002H\002\022\004\022\0020\0050\004¨\006\006"}, d2={"Lkotlin/reflect/KMutableProperty1$Setter;", "T", "V", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function2;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Setter<T, V>
    extends KMutableProperty.Setter<V>, Function2<T, V, Unit>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KMutableProperty1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */