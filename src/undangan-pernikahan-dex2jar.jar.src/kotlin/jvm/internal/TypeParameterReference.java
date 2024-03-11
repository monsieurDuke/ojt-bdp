package kotlin.jvm.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVariance;

@Metadata(d1={"\000>\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\r\n\002\020\b\n\000\n\002\020\002\n\002\b\003\b\007\030\000 \0372\0020\001:\001\037B'\022\b\020\002\032\004\030\0010\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t¢\006\002\020\nJ\023\020\030\032\0020\t2\b\020\031\032\004\030\0010\003H\002J\b\020\032\032\0020\033H\026J\024\020\034\032\0020\0352\f\020\021\032\b\022\004\022\0020\r0\fJ\b\020\036\032\0020\005H\026R\026\020\013\032\n\022\004\022\0020\r\030\0010\fX\016¢\006\002\n\000R\020\020\002\032\004\030\0010\003X\004¢\006\002\n\000R\024\020\b\032\0020\tX\004¢\006\b\n\000\032\004\b\b\020\016R\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\017\020\020R \020\021\032\b\022\004\022\0020\r0\f8VX\004¢\006\f\022\004\b\022\020\023\032\004\b\024\020\025R\024\020\006\032\0020\007X\004¢\006\b\n\000\032\004\b\026\020\027¨\006 "}, d2={"Lkotlin/jvm/internal/TypeParameterReference;", "Lkotlin/reflect/KTypeParameter;", "container", "", "name", "", "variance", "Lkotlin/reflect/KVariance;", "isReified", "", "(Ljava/lang/Object;Ljava/lang/String;Lkotlin/reflect/KVariance;Z)V", "bounds", "", "Lkotlin/reflect/KType;", "()Z", "getName", "()Ljava/lang/String;", "upperBounds", "getUpperBounds$annotations", "()V", "getUpperBounds", "()Ljava/util/List;", "getVariance", "()Lkotlin/reflect/KVariance;", "equals", "other", "hashCode", "", "setUpperBounds", "", "toString", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class TypeParameterReference
  implements KTypeParameter
{
  public static final Companion Companion = new Companion(null);
  private volatile List<? extends KType> bounds;
  private final Object container;
  private final boolean isReified;
  private final String name;
  private final KVariance variance;
  
  public TypeParameterReference(Object paramObject, String paramString, KVariance paramKVariance, boolean paramBoolean)
  {
    this.container = paramObject;
    this.name = paramString;
    this.variance = paramKVariance;
    this.isReified = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof TypeParameterReference)) && (Intrinsics.areEqual(this.container, ((TypeParameterReference)paramObject).container)) && (Intrinsics.areEqual(getName(), ((TypeParameterReference)paramObject).getName()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public List<KType> getUpperBounds()
  {
    List localList2 = this.bounds;
    List localList1 = localList2;
    if (localList2 == null)
    {
      localList1 = CollectionsKt.listOf(Reflection.nullableTypeOf(Object.class));
      this.bounds = localList1;
    }
    return localList1;
  }
  
  public KVariance getVariance()
  {
    return this.variance;
  }
  
  public int hashCode()
  {
    Object localObject = this.container;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + getName().hashCode();
  }
  
  public boolean isReified()
  {
    return this.isReified;
  }
  
  public final void setUpperBounds(List<? extends KType> paramList)
  {
    Intrinsics.checkNotNullParameter(paramList, "upperBounds");
    if (this.bounds == null)
    {
      this.bounds = paramList;
      return;
    }
    throw new IllegalStateException(("Upper bounds of type parameter '" + this + "' have already been initialized.").toString());
  }
  
  public String toString()
  {
    return Companion.toString((KTypeParameter)this);
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\016\020\003\032\0020\0042\006\020\005\032\0020\006¨\006\007"}, d2={"Lkotlin/jvm/internal/TypeParameterReference$Companion;", "", "()V", "toString", "", "typeParameter", "Lkotlin/reflect/KTypeParameter;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final String toString(KTypeParameter paramKTypeParameter)
    {
      Intrinsics.checkNotNullParameter(paramKTypeParameter, "typeParameter");
      StringBuilder localStringBuilder = new StringBuilder();
      KVariance localKVariance = paramKTypeParameter.getVariance();
      switch (WhenMappings.$EnumSwitchMapping$0[localKVariance.ordinal()])
      {
      case 1: 
      default: 
        break;
      case 3: 
        localStringBuilder.append("out ");
        break;
      case 2: 
        localStringBuilder.append("in ");
      }
      localStringBuilder.append(paramKTypeParameter.getName());
      paramKTypeParameter = localStringBuilder.toString();
      Intrinsics.checkNotNullExpressionValue(paramKTypeParameter, "StringBuilder().apply(builderAction).toString()");
      return paramKTypeParameter;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/TypeParameterReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */