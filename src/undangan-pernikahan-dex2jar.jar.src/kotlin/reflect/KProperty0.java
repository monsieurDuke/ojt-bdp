package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(d1={"\000 \n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\020\000\n\002\b\002\bf\030\000*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\001\fJ\r\020\b\032\0028\000H&¢\006\002\020\tJ\n\020\n\032\004\030\0010\013H'R\030\020\004\032\b\022\004\022\0028\0000\005X¦\004¢\006\006\032\004\b\006\020\007¨\006\r"}, d2={"Lkotlin/reflect/KProperty0;", "V", "Lkotlin/reflect/KProperty;", "Lkotlin/Function0;", "getter", "Lkotlin/reflect/KProperty0$Getter;", "getGetter", "()Lkotlin/reflect/KProperty0$Getter;", "get", "()Ljava/lang/Object;", "getDelegate", "", "Getter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KProperty0<V>
  extends KProperty<V>, Function0<V>
{
  public abstract V get();
  
  public abstract Object getDelegate();
  
  public abstract Getter<V> getGetter();
  
  @Metadata(d1={"\000\020\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\bf\030\000*\006\b\001\020\001 \0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003¨\006\004"}, d2={"Lkotlin/reflect/KProperty0$Getter;", "V", "Lkotlin/reflect/KProperty$Getter;", "Lkotlin/Function0;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Getter<V>
    extends KProperty.Getter<V>, Function0<V>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KProperty0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */