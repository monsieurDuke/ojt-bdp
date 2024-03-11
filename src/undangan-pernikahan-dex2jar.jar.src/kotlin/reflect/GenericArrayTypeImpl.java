package kotlin.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\020\b\n\002\b\002\b\003\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\023\020\006\032\0020\0072\b\020\b\032\004\030\0010\tH\002J\b\020\n\032\0020\004H\026J\b\020\013\032\0020\fH\026J\b\020\r\032\0020\016H\026J\b\020\017\032\0020\fH\026R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\020"}, d2={"Lkotlin/reflect/GenericArrayTypeImpl;", "Ljava/lang/reflect/GenericArrayType;", "Lkotlin/reflect/TypeImpl;", "elementType", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)V", "equals", "", "other", "", "getGenericComponentType", "getTypeName", "", "hashCode", "", "toString", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class GenericArrayTypeImpl
  implements GenericArrayType, TypeImpl
{
  private final Type elementType;
  
  public GenericArrayTypeImpl(Type paramType)
  {
    this.elementType = paramType;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof GenericArrayType)) && (Intrinsics.areEqual(getGenericComponentType(), ((GenericArrayType)paramObject).getGenericComponentType()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Type getGenericComponentType()
  {
    return this.elementType;
  }
  
  public String getTypeName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = TypesJVMKt.access$typeToString(this.elementType);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + "[]";
  }
  
  public int hashCode()
  {
    return getGenericComponentType().hashCode();
  }
  
  public String toString()
  {
    return getTypeName();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/GenericArrayTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */