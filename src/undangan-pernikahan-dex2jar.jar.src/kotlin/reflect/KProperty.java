package kotlin.reflect;

import kotlin.Metadata;

@Metadata(d1={"\000\034\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\b\bf\030\000*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\002:\002\016\017R\030\020\003\032\b\022\004\022\0028\0000\004X¦\004¢\006\006\032\004\b\005\020\006R\032\020\007\032\0020\b8&X§\004¢\006\f\022\004\b\t\020\n\032\004\b\007\020\013R\032\020\f\032\0020\b8&X§\004¢\006\f\022\004\b\r\020\n\032\004\b\f\020\013¨\006\020"}, d2={"Lkotlin/reflect/KProperty;", "V", "Lkotlin/reflect/KCallable;", "getter", "Lkotlin/reflect/KProperty$Getter;", "getGetter", "()Lkotlin/reflect/KProperty$Getter;", "isConst", "", "isConst$annotations", "()V", "()Z", "isLateinit", "isLateinit$annotations", "Accessor", "Getter", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KProperty<V>
  extends KCallable<V>
{
  public abstract Getter<V> getGetter();
  
  public abstract boolean isConst();
  
  public abstract boolean isLateinit();
  
  @Metadata(d1={"\000\024\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\003\bf\030\000*\006\b\001\020\001 \0012\0020\002R\030\020\003\032\b\022\004\022\0028\0010\004X¦\004¢\006\006\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlin/reflect/KProperty$Accessor;", "V", "", "property", "Lkotlin/reflect/KProperty;", "getProperty", "()Lkotlin/reflect/KProperty;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Accessor<V>
  {
    public abstract KProperty<V> getProperty();
  }
  
  @Metadata(k=3, mv={1, 7, 1}, xi=48)
  public static final class DefaultImpls {}
  
  @Metadata(d1={"\000\020\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\bf\030\000*\006\b\001\020\001 \0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003¨\006\004"}, d2={"Lkotlin/reflect/KProperty$Getter;", "V", "Lkotlin/reflect/KProperty$Accessor;", "Lkotlin/reflect/KFunction;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static abstract interface Getter<V>
    extends KProperty.Accessor<V>, KFunction<V>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */