package kotlin.properties;

import kotlin.Metadata;
import kotlin.reflect.KProperty;

@Metadata(d1={"\000 \n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\bf\030\000*\006\b\000\020\001 \000*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\003J\"\020\004\032\0028\0012\006\020\005\032\0028\0002\n\020\006\032\006\022\002\b\0030\007H¦\002¢\006\002\020\bJ*\020\t\032\0020\n2\006\020\005\032\0028\0002\n\020\006\032\006\022\002\b\0030\0072\006\020\013\032\0028\001H¦\002¢\006\002\020\f¨\006\r"}, d2={"Lkotlin/properties/ReadWriteProperty;", "T", "V", "Lkotlin/properties/ReadOnlyProperty;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface ReadWriteProperty<T, V>
  extends ReadOnlyProperty<T, V>
{
  public abstract V getValue(T paramT, KProperty<?> paramKProperty);
  
  public abstract void setValue(T paramT, KProperty<?> paramKProperty, V paramV);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/properties/ReadWriteProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */