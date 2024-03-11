package kotlin.reflect;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;

@Metadata(d1={"\000d\n\002\030\002\n\000\n\002\020\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\036\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\024\n\002\030\002\n\002\b\007\n\002\020\016\n\002\b\003\n\002\020 \n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\020\b\n\002\b\003\bf\030\000*\b\b\000\020\001*\0020\0022\0020\0032\0020\0042\0020\005J\023\020@\032\0020\f2\b\020A\032\004\030\0010\002H¦\002J\b\020B\032\0020CH&J\022\020D\032\0020\f2\b\020E\032\004\030\0010\002H'R\036\020\006\032\016\022\n\022\b\022\004\022\0028\0000\b0\007X¦\004¢\006\006\032\004\b\t\020\nR\032\020\013\032\0020\f8&X§\004¢\006\f\022\004\b\r\020\016\032\004\b\013\020\017R\032\020\020\032\0020\f8&X§\004¢\006\f\022\004\b\021\020\016\032\004\b\020\020\017R\032\020\022\032\0020\f8&X§\004¢\006\f\022\004\b\023\020\016\032\004\b\022\020\017R\032\020\024\032\0020\f8&X§\004¢\006\f\022\004\b\025\020\016\032\004\b\024\020\017R\032\020\026\032\0020\f8&X§\004¢\006\f\022\004\b\027\020\016\032\004\b\026\020\017R\032\020\030\032\0020\f8&X§\004¢\006\f\022\004\b\031\020\016\032\004\b\030\020\017R\032\020\032\032\0020\f8&X§\004¢\006\f\022\004\b\033\020\016\032\004\b\032\020\017R\032\020\034\032\0020\f8&X§\004¢\006\f\022\004\b\035\020\016\032\004\b\034\020\017R\032\020\036\032\0020\f8&X§\004¢\006\f\022\004\b\037\020\016\032\004\b\036\020\017R\034\020 \032\f\022\b\022\006\022\002\b\0030!0\007X¦\004¢\006\006\032\004\b\"\020\nR\034\020#\032\f\022\b\022\006\022\002\b\0030\0000\007X¦\004¢\006\006\032\004\b$\020\nR\024\020%\032\004\030\0018\000X¦\004¢\006\006\032\004\b&\020'R\024\020(\032\004\030\0010)X¦\004¢\006\006\032\004\b*\020+R(\020,\032\020\022\f\022\n\022\006\b\001\022\0028\0000\0000-8&X§\004¢\006\f\022\004\b.\020\016\032\004\b/\0200R\024\0201\032\004\030\0010)X¦\004¢\006\006\032\004\b2\020+R \0203\032\b\022\004\022\002040-8&X§\004¢\006\f\022\004\b5\020\016\032\004\b6\0200R \0207\032\b\022\004\022\002080-8&X§\004¢\006\f\022\004\b9\020\016\032\004\b:\0200R\034\020;\032\004\030\0010<8&X§\004¢\006\f\022\004\b=\020\016\032\004\b>\020?¨\006F"}, d2={"Lkotlin/reflect/KClass;", "T", "", "Lkotlin/reflect/KDeclarationContainer;", "Lkotlin/reflect/KAnnotatedElement;", "Lkotlin/reflect/KClassifier;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "", "getSealedSubclasses$annotations", "getSealedSubclasses", "()Ljava/util/List;", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "hashCode", "", "isInstance", "value", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public abstract interface KClass<T>
  extends KDeclarationContainer, KAnnotatedElement, KClassifier
{
  public abstract boolean equals(Object paramObject);
  
  public abstract Collection<KFunction<T>> getConstructors();
  
  public abstract Collection<KCallable<?>> getMembers();
  
  public abstract Collection<KClass<?>> getNestedClasses();
  
  public abstract T getObjectInstance();
  
  public abstract String getQualifiedName();
  
  public abstract List<KClass<? extends T>> getSealedSubclasses();
  
  public abstract String getSimpleName();
  
  public abstract List<KType> getSupertypes();
  
  public abstract List<KTypeParameter> getTypeParameters();
  
  public abstract KVisibility getVisibility();
  
  public abstract int hashCode();
  
  public abstract boolean isAbstract();
  
  public abstract boolean isCompanion();
  
  public abstract boolean isData();
  
  public abstract boolean isFinal();
  
  public abstract boolean isFun();
  
  public abstract boolean isInner();
  
  public abstract boolean isInstance(Object paramObject);
  
  public abstract boolean isOpen();
  
  public abstract boolean isSealed();
  
  public abstract boolean isValue();
  
  @Metadata(k=3, mv={1, 7, 1}, xi=48)
  public static final class DefaultImpls {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */