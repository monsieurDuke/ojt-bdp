package kotlin.reflect;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\006\bf\030\000*\004\b\000\020\001*\004\b\001\020\002*\004\b\002\020\0032\024\022\004\022\002H\001\022\004\022\002H\002\022\004\022\002H\0030\0042\b\022\004\022\002H\0030\005:\001\020J%\020\n\032\0020\0132\006\020\f\032\0028\0002\006\020\r\032\0028\0012\006\020\016\032\0028\002H&¢\006\002\020\017R$\020\006\032\024\022\004\022\0028\000\022\004\022\0028\001\022\004\022\0028\0020\007X¦\004¢\006\006\032\004\b\b\020\t¨\006\021"}, d2={"Lkotlin/reflect/KMutableProperty2;", "D", "E", "V", "Lkotlin/reflect/KProperty2;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty2$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty2$Setter;", "set", "", "receiver1", "receiver2", "value", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KMutableProperty2<D, E, V>
  extends KProperty2<D, E, V>, KMutableProperty<V>
{
  public abstract Setter<D, E, V> getSetter();
  
  public abstract void set(D paramD, E paramE, V paramV);
  
  @Metadata(d1={"\000\026\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\020\002\n\000\bf\030\000*\004\b\003\020\001*\004\b\004\020\002*\004\b\005\020\0032\b\022\004\022\002H\0030\0042\032\022\004\022\002H\001\022\004\022\002H\002\022\004\022\002H\003\022\004\022\0020\0060\005¨\006\007"}, d2={"Lkotlin/reflect/KMutableProperty2$Setter;", "D", "E", "V", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function3;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Setter<D, E, V>
    extends KMutableProperty.Setter<V>, Function3<D, E, V, Unit>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KMutableProperty2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */