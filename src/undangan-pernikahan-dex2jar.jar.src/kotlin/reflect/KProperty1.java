package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\000\"\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\020\000\n\002\b\002\bf\030\000*\004\b\000\020\001*\006\b\001\020\002 \0012\b\022\004\022\002H\0020\0032\016\022\004\022\002H\001\022\004\022\002H\0020\004:\001\016J\025\020\t\032\0028\0012\006\020\n\032\0028\000H&¢\006\002\020\013J\027\020\f\032\004\030\0010\r2\006\020\n\032\0028\000H'¢\006\002\020\013R\036\020\005\032\016\022\004\022\0028\000\022\004\022\0028\0010\006X¦\004¢\006\006\032\004\b\007\020\b¨\006\017"}, d2={"Lkotlin/reflect/KProperty1;", "T", "V", "Lkotlin/reflect/KProperty;", "Lkotlin/Function1;", "getter", "Lkotlin/reflect/KProperty1$Getter;", "getGetter", "()Lkotlin/reflect/KProperty1$Getter;", "get", "receiver", "(Ljava/lang/Object;)Ljava/lang/Object;", "getDelegate", "", "Getter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KProperty1<T, V>
  extends KProperty<V>, Function1<T, V>
{
  public abstract V get(T paramT);
  
  public abstract Object getDelegate(T paramT);
  
  public abstract Getter<T, V> getGetter();
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\bf\030\000*\004\b\002\020\001*\006\b\003\020\002 \0012\b\022\004\022\002H\0020\0032\016\022\004\022\002H\001\022\004\022\002H\0020\004¨\006\005"}, d2={"Lkotlin/reflect/KProperty1$Getter;", "T", "V", "Lkotlin/reflect/KProperty$Getter;", "Lkotlin/Function1;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Getter<T, V>
    extends KProperty.Getter<V>, Function1<T, V>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KProperty1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */