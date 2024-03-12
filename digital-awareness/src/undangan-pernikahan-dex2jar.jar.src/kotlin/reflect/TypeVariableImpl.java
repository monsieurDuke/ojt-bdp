package kotlin.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000R\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\021\n\002\020\033\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\002\b\003\030\0002\b\022\004\022\0020\0020\0012\0020\003B\r\022\006\020\004\032\0020\005¢\006\002\020\006J\023\020\007\032\0020\b2\b\020\t\032\004\030\0010\nH\002J\021\020\013\032\b\022\004\022\0020\r0\f¢\006\002\020\016J%\020\017\032\004\030\001H\020\"\b\b\000\020\020*\0020\r2\f\020\021\032\b\022\004\022\002H\0200\022¢\006\002\020\023J\021\020\024\032\b\022\004\022\0020\r0\f¢\006\002\020\016J\023\020\025\032\b\022\004\022\0020\0260\fH\026¢\006\002\020\027J\021\020\030\032\b\022\004\022\0020\r0\f¢\006\002\020\016J\b\020\031\032\0020\002H\026J\b\020\032\032\0020\033H\026J\b\020\034\032\0020\033H\026J\b\020\035\032\0020\036H\026J\b\020\037\032\0020\033H\026R\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006 "}, d2={"Lkotlin/reflect/TypeVariableImpl;", "Ljava/lang/reflect/TypeVariable;", "Ljava/lang/reflect/GenericDeclaration;", "Lkotlin/reflect/TypeImpl;", "typeParameter", "Lkotlin/reflect/KTypeParameter;", "(Lkotlin/reflect/KTypeParameter;)V", "equals", "", "other", "", "getAnnotatedBounds", "", "", "()[Ljava/lang/annotation/Annotation;", "getAnnotation", "T", "annotationClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/annotation/Annotation;", "getAnnotations", "getBounds", "Ljava/lang/reflect/Type;", "()[Ljava/lang/reflect/Type;", "getDeclaredAnnotations", "getGenericDeclaration", "getName", "", "getTypeName", "hashCode", "", "toString", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class TypeVariableImpl
  implements TypeVariable<GenericDeclaration>, TypeImpl
{
  private final KTypeParameter typeParameter;
  
  public TypeVariableImpl(KTypeParameter paramKTypeParameter)
  {
    this.typeParameter = paramKTypeParameter;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof TypeVariable)) && (Intrinsics.areEqual(getName(), ((TypeVariable)paramObject).getName())) && (Intrinsics.areEqual(getGenericDeclaration(), ((TypeVariable)paramObject).getGenericDeclaration()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final Annotation[] getAnnotatedBounds()
  {
    return (Annotation[])new Annotation[0];
  }
  
  public final <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramClass, "annotationClass");
    return null;
  }
  
  public final Annotation[] getAnnotations()
  {
    return (Annotation[])new Annotation[0];
  }
  
  public Type[] getBounds()
  {
    Object localObject2 = (Iterable)this.typeParameter.getUpperBounds();
    Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Collection)localObject1).add(TypesJVMKt.access$computeJavaType((KType)((Iterator)localObject2).next(), true));
    }
    localObject1 = (List)localObject1;
    localObject1 = (Collection)localObject1;
    localObject1 = ((Collection)localObject1).toArray(new Type[0]);
    Intrinsics.checkNotNull(localObject1, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    return (Type[])localObject1;
  }
  
  public final Annotation[] getDeclaredAnnotations()
  {
    return (Annotation[])new Annotation[0];
  }
  
  public GenericDeclaration getGenericDeclaration()
  {
    String str = "getGenericDeclaration() is not yet supported for type variables created from KType: " + this.typeParameter;
    throw new NotImplementedError("An operation is not implemented: " + str);
  }
  
  public String getName()
  {
    return this.typeParameter.getName();
  }
  
  public String getTypeName()
  {
    return getName();
  }
  
  public int hashCode()
  {
    return getName().hashCode() ^ getGenericDeclaration().hashCode();
  }
  
  public String toString()
  {
    return getTypeName();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/TypeVariableImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */