package kotlin.reflect;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000 \n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\004\bf\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\001\fJ\025\020\b\032\0020\t2\006\020\n\032\0028\000H&¢\006\002\020\013R\030\020\004\032\b\022\004\022\0028\0000\005X¦\004¢\006\006\032\004\b\006\020\007¨\006\r"}, d2={"Lkotlin/reflect/KMutableProperty0;", "V", "Lkotlin/reflect/KProperty0;", "Lkotlin/reflect/KMutableProperty;", "setter", "Lkotlin/reflect/KMutableProperty0$Setter;", "getSetter", "()Lkotlin/reflect/KMutableProperty0$Setter;", "set", "", "value", "(Ljava/lang/Object;)V", "Setter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KMutableProperty0<V>
  extends KProperty0<V>, KMutableProperty<V>
{
  public abstract Setter<V> getSetter();
  
  public abstract void set(V paramV);
  
  @Metadata(d1={"\000\024\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\000\bf\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\0022\016\022\004\022\002H\001\022\004\022\0020\0040\003¨\006\005"}, d2={"Lkotlin/reflect/KMutableProperty0$Setter;", "V", "Lkotlin/reflect/KMutableProperty$Setter;", "Lkotlin/Function1;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Setter<V>
    extends KMutableProperty.Setter<V>, Function1<V, Unit>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KMutableProperty0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */