package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(d1={"\0002\n\000\n\002\030\002\n\000\n\002\020\033\n\002\b\003\n\002\030\002\n\000\n\002\020\020\n\002\b\n\n\002\020\000\n\002\b\013\n\002\020\013\n\002\020\021\n\002\b\002\032\037\020\037\032\0020 \"\n\b\000\020\002\030\001*\0020\024*\006\022\002\b\0030!¢\006\002\020\"\"'\020\000\032\n\022\006\b\001\022\002H\0020\001\"\b\b\000\020\002*\0020\003*\002H\0028F¢\006\006\032\004\b\004\020\005\"5\020\006\032\b\022\004\022\002H\b0\007\"\016\b\000\020\b*\b\022\004\022\002H\b0\t*\002H\b8Æ\002X\004¢\006\f\022\004\b\n\020\013\032\004\b\f\020\r\"-\020\016\032\b\022\004\022\002H\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0018G¢\006\f\022\004\b\017\020\020\032\004\b\021\020\022\"&\020\023\032\b\022\004\022\002H\0020\007\"\b\b\000\020\002*\0020\024*\002H\0028Æ\002¢\006\006\032\004\b\021\020\025\";\020\023\032\016\022\n\022\b\022\004\022\002H\0020\0010\007\"\b\b\000\020\002*\0020\024*\b\022\004\022\002H\0020\0018Ç\002X\004¢\006\f\022\004\b\026\020\020\032\004\b\027\020\022\"+\020\030\032\b\022\004\022\002H\0020\007\"\b\b\000\020\002*\0020\024*\b\022\004\022\002H\0020\0018F¢\006\006\032\004\b\031\020\022\"-\020\032\032\n\022\004\022\002H\002\030\0010\007\"\b\b\000\020\002*\0020\024*\b\022\004\022\002H\0020\0018F¢\006\006\032\004\b\033\020\022\"+\020\034\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\024*\b\022\004\022\002H\0020\0078G¢\006\006\032\004\b\035\020\036¨\006#"}, d2={"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "declaringJavaClass", "Ljava/lang/Class;", "E", "", "getDeclaringJavaClass$annotations", "(Ljava/lang/Enum;)V", "getDeclaringJavaClass", "(Ljava/lang/Enum;)Ljava/lang/Class;", "java", "getJavaClass$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "getRuntimeClassOfKClassInstance$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class JvmClassMappingKt
{
  public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(T paramT)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    paramT = paramT.annotationType();
    Intrinsics.checkNotNullExpressionValue(paramT, "this as java.lang.annota…otation).annotationType()");
    paramT = getKotlinClass(paramT);
    Intrinsics.checkNotNull(paramT, "null cannot be cast to non-null type kotlin.reflect.KClass<out T of kotlin.jvm.JvmClassMappingKt.<get-annotationClass>>");
    return paramT;
  }
  
  private static final <E extends Enum<E>> Class<E> getDeclaringJavaClass(E paramE)
  {
    Intrinsics.checkNotNullParameter(paramE, "<this>");
    paramE = paramE.getDeclaringClass();
    Intrinsics.checkNotNullExpressionValue(paramE, "this as java.lang.Enum<E>).declaringClass");
    return paramE;
  }
  
  public static final <T> Class<T> getJavaClass(T paramT)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    paramT = paramT.getClass();
    Intrinsics.checkNotNull(paramT, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>");
    return paramT;
  }
  
  public static final <T> Class<T> getJavaClass(KClass<T> paramKClass)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    paramKClass = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    Intrinsics.checkNotNull(paramKClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
    return paramKClass;
  }
  
  public static final <T> Class<T> getJavaObjectType(KClass<T> paramKClass)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    paramKClass = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    if (!paramKClass.isPrimitive())
    {
      Intrinsics.checkNotNull(paramKClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
      return paramKClass;
    }
    String str = paramKClass.getName();
    if (str != null) {
      switch (str.hashCode())
      {
      default: 
        break;
      case 109413500: 
        if (str.equals("short")) {
          paramKClass = Short.class;
        }
        break;
      case 97526364: 
        if (str.equals("float")) {
          paramKClass = Float.class;
        }
        break;
      case 64711720: 
        if (str.equals("boolean")) {
          paramKClass = Boolean.class;
        }
        break;
      case 3625364: 
        if (str.equals("void")) {
          paramKClass = Void.class;
        }
        break;
      case 3327612: 
        if (str.equals("long")) {
          paramKClass = Long.class;
        }
        break;
      case 3052374: 
        if (str.equals("char")) {
          paramKClass = Character.class;
        }
        break;
      case 3039496: 
        if (str.equals("byte")) {
          paramKClass = Byte.class;
        }
        break;
      case 104431: 
        if (str.equals("int")) {
          paramKClass = Integer.class;
        }
        break;
      case -1325958191: 
        if (str.equals("double")) {
          paramKClass = Double.class;
        }
        break;
      }
    }
    Intrinsics.checkNotNull(paramKClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
    return paramKClass;
  }
  
  public static final <T> Class<T> getJavaPrimitiveType(KClass<T> paramKClass)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    paramKClass = ((ClassBasedDeclarationContainer)paramKClass).getJClass();
    if (paramKClass.isPrimitive())
    {
      Intrinsics.checkNotNull(paramKClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaPrimitiveType>>");
      return paramKClass;
    }
    paramKClass = paramKClass.getName();
    if (paramKClass != null) {
      switch (paramKClass.hashCode())
      {
      default: 
        break;
      case 761287205: 
        if (paramKClass.equals("java.lang.Double")) {
          paramKClass = Double.TYPE;
        }
        break;
      case 399092968: 
        if (paramKClass.equals("java.lang.Void")) {
          paramKClass = Void.TYPE;
        }
        break;
      case 398795216: 
        if (paramKClass.equals("java.lang.Long")) {
          paramKClass = Long.TYPE;
        }
        break;
      case 398507100: 
        if (paramKClass.equals("java.lang.Byte")) {
          paramKClass = Byte.TYPE;
        }
        break;
      case 344809556: 
        if (paramKClass.equals("java.lang.Boolean")) {
          paramKClass = Boolean.TYPE;
        }
        break;
      case 155276373: 
        if (paramKClass.equals("java.lang.Character")) {
          paramKClass = Character.TYPE;
        }
        break;
      case -515992664: 
        if (paramKClass.equals("java.lang.Short")) {
          paramKClass = Short.TYPE;
        }
        break;
      case -527879800: 
        if (paramKClass.equals("java.lang.Float")) {
          paramKClass = Float.TYPE;
        }
        break;
      case -2056817302: 
        if (paramKClass.equals("java.lang.Integer")) {
          paramKClass = Integer.TYPE;
        }
        break;
      }
    }
    paramKClass = null;
    return paramKClass;
  }
  
  public static final <T> KClass<T> getKotlinClass(Class<T> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramClass, "<this>");
    return Reflection.getOrCreateKotlinClass(paramClass);
  }
  
  public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(KClass<T> paramKClass)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    paramKClass = ((Object)paramKClass).getClass();
    Intrinsics.checkNotNull(paramKClass, "null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>>");
    return paramKClass;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/JvmClassMappingKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */