package kotlin.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000:\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\021\n\002\b\002\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\003\b\003\030\000 \0242\0020\0012\0020\002:\001\024B\031\022\b\020\003\032\004\030\0010\004\022\b\020\005\032\004\030\0010\004¢\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH\002J\023\020\013\032\b\022\004\022\0020\0040\fH\026¢\006\002\020\rJ\b\020\016\032\0020\017H\026J\023\020\020\032\b\022\004\022\0020\0040\fH\026¢\006\002\020\rJ\b\020\021\032\0020\022H\026J\b\020\023\032\0020\017H\026R\020\020\005\032\004\030\0010\004X\004¢\006\002\n\000R\020\020\003\032\004\030\0010\004X\004¢\006\002\n\000¨\006\025"}, d2={"Lkotlin/reflect/WildcardTypeImpl;", "Ljava/lang/reflect/WildcardType;", "Lkotlin/reflect/TypeImpl;", "upperBound", "Ljava/lang/reflect/Type;", "lowerBound", "(Ljava/lang/reflect/Type;Ljava/lang/reflect/Type;)V", "equals", "", "other", "", "getLowerBounds", "", "()[Ljava/lang/reflect/Type;", "getTypeName", "", "getUpperBounds", "hashCode", "", "toString", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class WildcardTypeImpl
  implements WildcardType, TypeImpl
{
  public static final Companion Companion = new Companion(null);
  private static final WildcardTypeImpl STAR = new WildcardTypeImpl(null, null);
  private final Type lowerBound;
  private final Type upperBound;
  
  public WildcardTypeImpl(Type paramType1, Type paramType2)
  {
    this.upperBound = paramType1;
    this.lowerBound = paramType2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof WildcardType)) && (Arrays.equals(getUpperBounds(), ((WildcardType)paramObject).getUpperBounds())) && (Arrays.equals(getLowerBounds(), ((WildcardType)paramObject).getLowerBounds()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Type[] getLowerBounds()
  {
    Object localObject = this.lowerBound;
    if (localObject == null) {
      localObject = (Type[])new Type[0];
    } else {
      localObject = new Type[] { localObject };
    }
    return (Type[])localObject;
  }
  
  public String getTypeName()
  {
    Object localObject1;
    Object localObject2;
    if (this.lowerBound != null)
    {
      localObject1 = new StringBuilder().append("? super ");
      localObject2 = TypesJVMKt.access$typeToString(this.lowerBound);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject1 = (String)localObject2;
    }
    else
    {
      localObject1 = this.upperBound;
      if ((localObject1 != null) && (!Intrinsics.areEqual(localObject1, Object.class)))
      {
        localObject2 = new StringBuilder().append("? extends ");
        localObject1 = TypesJVMKt.access$typeToString(this.upperBound);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        localObject1 = (String)localObject1;
      }
      else
      {
        localObject1 = "?";
      }
    }
    return (String)localObject1;
  }
  
  public Type[] getUpperBounds()
  {
    Type localType2 = this.upperBound;
    Type localType1 = localType2;
    if (localType2 == null) {
      localType1 = (Type)Object.class;
    }
    return new Type[] { localType1 };
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(getUpperBounds()) ^ Arrays.hashCode(getLowerBounds());
  }
  
  public String toString()
  {
    return getTypeName();
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\021\020\003\032\0020\004¢\006\b\n\000\032\004\b\005\020\006¨\006\007"}, d2={"Lkotlin/reflect/WildcardTypeImpl$Companion;", "", "()V", "STAR", "Lkotlin/reflect/WildcardTypeImpl;", "getSTAR", "()Lkotlin/reflect/WildcardTypeImpl;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final WildcardTypeImpl getSTAR()
    {
      return WildcardTypeImpl.access$getSTAR$cp();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/WildcardTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */