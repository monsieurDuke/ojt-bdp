package kotlin.properties;

import kotlin.Metadata;
import kotlin.reflect.KProperty;

@Metadata(d1={"\000\030\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\002\bç\001\030\000*\006\b\000\020\001 \000*\006\b\001\020\002 \0012\0020\003J\"\020\004\032\0028\0012\006\020\005\032\0028\0002\n\020\006\032\006\022\002\b\0030\007H¦\002¢\006\002\020\b¨\006\t"}, d2={"Lkotlin/properties/PropertyDelegateProvider;", "T", "D", "", "provideDelegate", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface PropertyDelegateProvider<T, D>
{
  public abstract D provideDelegate(T paramT, KProperty<?> paramKProperty);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/properties/PropertyDelegateProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */