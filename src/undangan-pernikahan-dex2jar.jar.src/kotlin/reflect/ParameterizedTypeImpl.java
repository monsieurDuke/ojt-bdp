package kotlin.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000D\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\021\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\002\b\005\n\002\020\016\n\000\n\002\020\b\n\002\b\002\b\003\030\0002\0020\0012\0020\002B)\022\n\020\003\032\006\022\002\b\0030\004\022\b\020\005\032\004\030\0010\006\022\f\020\007\032\b\022\004\022\0020\0060\b¢\006\002\020\tJ\023\020\f\032\0020\r2\b\020\016\032\004\030\0010\017H\002J\023\020\020\032\b\022\004\022\0020\0060\nH\026¢\006\002\020\021J\n\020\022\032\004\030\0010\006H\026J\b\020\023\032\0020\006H\026J\b\020\024\032\0020\025H\026J\b\020\026\032\0020\027H\026J\b\020\030\032\0020\025H\026R\020\020\005\032\004\030\0010\006X\004¢\006\002\n\000R\022\020\003\032\006\022\002\b\0030\004X\004¢\006\002\n\000R\026\020\007\032\b\022\004\022\0020\0060\nX\004¢\006\004\n\002\020\013¨\006\031"}, d2={"Lkotlin/reflect/ParameterizedTypeImpl;", "Ljava/lang/reflect/ParameterizedType;", "Lkotlin/reflect/TypeImpl;", "rawType", "Ljava/lang/Class;", "ownerType", "Ljava/lang/reflect/Type;", "typeArguments", "", "(Ljava/lang/Class;Ljava/lang/reflect/Type;Ljava/util/List;)V", "", "[Ljava/lang/reflect/Type;", "equals", "", "other", "", "getActualTypeArguments", "()[Ljava/lang/reflect/Type;", "getOwnerType", "getRawType", "getTypeName", "", "hashCode", "", "toString", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ParameterizedTypeImpl
  implements ParameterizedType, TypeImpl
{
  private final Type ownerType;
  private final Class<?> rawType;
  private final Type[] typeArguments;
  
  public ParameterizedTypeImpl(Class<?> paramClass, Type paramType, List<? extends Type> paramList)
  {
    this.rawType = paramClass;
    this.ownerType = paramType;
    paramClass = (Collection)paramList;
    paramClass = paramClass.toArray(new Type[0]);
    Intrinsics.checkNotNull(paramClass, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    this.typeArguments = ((Type[])paramClass);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ParameterizedType)) && (Intrinsics.areEqual(this.rawType, ((ParameterizedType)paramObject).getRawType())) && (Intrinsics.areEqual(this.ownerType, ((ParameterizedType)paramObject).getOwnerType())) && (Arrays.equals(getActualTypeArguments(), ((ParameterizedType)paramObject).getActualTypeArguments()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Type[] getActualTypeArguments()
  {
    return this.typeArguments;
  }
  
  public Type getOwnerType()
  {
    return this.ownerType;
  }
  
  public Type getRawType()
  {
    return (Type)this.rawType;
  }
  
  public String getTypeName()
  {
    Object localObject1 = new StringBuilder();
    Object localObject2 = this.ownerType;
    if (localObject2 != null)
    {
      localObject2 = TypesJVMKt.access$typeToString((Type)localObject2);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("$");
      ((StringBuilder)localObject1).append(this.rawType.getSimpleName());
    }
    else
    {
      localObject2 = TypesJVMKt.access$typeToString((Type)this.rawType);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      ((StringBuilder)localObject1).append((String)localObject2);
    }
    localObject2 = this.typeArguments;
    int i;
    if (localObject2.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i ^ 0x1) != 0) {
      ArraysKt.joinTo$default((Object[])localObject2, (Appendable)localObject1, null, (CharSequence)"<", (CharSequence)">", 0, null, (Function1)getTypeName.1.1.INSTANCE, 50, null);
    }
    localObject1 = ((StringBuilder)localObject1).toString();
    Intrinsics.checkNotNullExpressionValue(localObject1, "StringBuilder().apply(builderAction).toString()");
    return (String)localObject1;
  }
  
  public int hashCode()
  {
    int j = this.rawType.hashCode();
    Type localType = this.ownerType;
    int i;
    if (localType != null) {
      i = localType.hashCode();
    } else {
      i = 0;
    }
    return j ^ i ^ Arrays.hashCode(getActualTypeArguments());
  }
  
  public String toString()
  {
    return getTypeName();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/ParameterizedTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */