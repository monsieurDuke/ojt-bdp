package kotlin.jvm.internal;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;

@Metadata(d1={"\0008\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\004\n\002\020\036\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\002\b\007\030\0002\0020\001B\031\022\n\020\002\032\006\022\002\b\0030\003\022\006\020\004\032\0020\005¢\006\002\020\006J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\021H\002J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\005H\026R\030\020\002\032\006\022\002\b\0030\003X\004¢\006\b\n\000\032\004\b\007\020\bR\036\020\t\032\f\022\b\022\006\022\002\b\0030\0130\n8VX\004¢\006\006\032\004\b\f\020\rR\016\020\004\032\0020\005X\004¢\006\002\n\000¨\006\025"}, d2={"Lkotlin/jvm/internal/PackageReference;", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "moduleName", "", "(Ljava/lang/Class;Ljava/lang/String;)V", "getJClass", "()Ljava/lang/Class;", "members", "", "Lkotlin/reflect/KCallable;", "getMembers", "()Ljava/util/Collection;", "equals", "", "other", "", "hashCode", "", "toString", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class PackageReference
  implements ClassBasedDeclarationContainer
{
  private final Class<?> jClass;
  private final String moduleName;
  
  public PackageReference(Class<?> paramClass, String paramString)
  {
    this.jClass = paramClass;
    this.moduleName = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof PackageReference)) && (Intrinsics.areEqual(getJClass(), ((PackageReference)paramObject).getJClass()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Class<?> getJClass()
  {
    return this.jClass;
  }
  
  public Collection<KCallable<?>> getMembers()
  {
    throw new KotlinReflectionNotSupportedError();
  }
  
  public int hashCode()
  {
    return getJClass().hashCode();
  }
  
  public String toString()
  {
    return getJClass().toString() + " (Kotlin reflection is not available)";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PackageReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */