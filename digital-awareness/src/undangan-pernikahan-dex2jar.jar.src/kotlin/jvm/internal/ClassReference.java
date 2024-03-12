package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Function;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000p\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\020\033\n\002\b\003\n\002\020\036\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\026\n\002\030\002\n\002\b\007\n\002\020\016\n\002\b\b\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\020\001\n\000\n\002\020\b\n\002\b\005\030\000 O2\b\022\004\022\0020\0020\0012\0020\003:\001OB\021\022\n\020\004\032\006\022\002\b\0030\005¢\006\002\020\006J\023\020F\032\0020\0222\b\020G\032\004\030\0010\002H\002J\b\020H\032\0020IH\002J\b\020J\032\0020KH\026J\022\020L\032\0020\0222\b\020M\032\004\030\0010\002H\027J\b\020N\032\00201H\026R\032\020\007\032\b\022\004\022\0020\t0\b8VX\004¢\006\006\032\004\b\n\020\013R \020\f\032\016\022\n\022\b\022\004\022\0020\0020\0160\r8VX\004¢\006\006\032\004\b\017\020\020R\032\020\021\032\0020\0228VX\004¢\006\f\022\004\b\023\020\024\032\004\b\021\020\025R\032\020\026\032\0020\0228VX\004¢\006\f\022\004\b\027\020\024\032\004\b\026\020\025R\032\020\030\032\0020\0228VX\004¢\006\f\022\004\b\031\020\024\032\004\b\030\020\025R\032\020\032\032\0020\0228VX\004¢\006\f\022\004\b\033\020\024\032\004\b\032\020\025R\032\020\034\032\0020\0228VX\004¢\006\f\022\004\b\035\020\024\032\004\b\034\020\025R\032\020\036\032\0020\0228VX\004¢\006\f\022\004\b\037\020\024\032\004\b\036\020\025R\032\020 \032\0020\0228VX\004¢\006\f\022\004\b!\020\024\032\004\b \020\025R\032\020\"\032\0020\0228VX\004¢\006\f\022\004\b#\020\024\032\004\b\"\020\025R\032\020$\032\0020\0228VX\004¢\006\f\022\004\b%\020\024\032\004\b$\020\025R\030\020\004\032\006\022\002\b\0030\005X\004¢\006\b\n\000\032\004\b&\020'R\036\020(\032\f\022\b\022\006\022\002\b\0030)0\r8VX\004¢\006\006\032\004\b*\020\020R\036\020+\032\f\022\b\022\006\022\002\b\0030\0010\r8VX\004¢\006\006\032\004\b,\020\020R\026\020-\032\004\030\0010\0028VX\004¢\006\006\032\004\b.\020/R\026\0200\032\004\030\001018VX\004¢\006\006\032\004\b2\0203R(\0204\032\020\022\f\022\n\022\006\b\001\022\0020\0020\0010\b8VX\004¢\006\f\022\004\b5\020\024\032\004\b6\020\013R\026\0207\032\004\030\001018VX\004¢\006\006\032\004\b8\0203R \0209\032\b\022\004\022\0020:0\b8VX\004¢\006\f\022\004\b;\020\024\032\004\b<\020\013R \020=\032\b\022\004\022\0020>0\b8VX\004¢\006\f\022\004\b?\020\024\032\004\b@\020\013R\034\020A\032\004\030\0010B8VX\004¢\006\f\022\004\bC\020\024\032\004\bD\020E¨\006P"}, d2={"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class ClassReference
  implements KClass<Object>, ClassBasedDeclarationContainer
{
  public static final Companion Companion = new Companion(null);
  private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
  private static final HashMap<String, String> classFqNames;
  private static final HashMap<String, String> primitiveFqNames;
  private static final HashMap<String, String> primitiveWrapperFqNames;
  private static final Map<String, String> simpleNames;
  private final Class<?> jClass;
  
  static
  {
    Object localObject2 = (Iterable)CollectionsKt.listOf(new Class[] { Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class });
    Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    int i = 0;
    Object localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = ((Iterator)localObject3).next();
      if (i < 0) {
        CollectionsKt.throwIndexOverflow();
      }
      ((Collection)localObject1).add(TuplesKt.to((Class)localObject2, Integer.valueOf(i)));
      i++;
    }
    localObject1 = (List)localObject1;
    FUNCTION_CLASSES = MapsKt.toMap((Iterable)localObject1);
    localObject2 = new HashMap();
    ((HashMap)localObject2).put("boolean", "kotlin.Boolean");
    ((HashMap)localObject2).put("char", "kotlin.Char");
    ((HashMap)localObject2).put("byte", "kotlin.Byte");
    ((HashMap)localObject2).put("short", "kotlin.Short");
    ((HashMap)localObject2).put("int", "kotlin.Int");
    ((HashMap)localObject2).put("float", "kotlin.Float");
    ((HashMap)localObject2).put("long", "kotlin.Long");
    ((HashMap)localObject2).put("double", "kotlin.Double");
    primitiveFqNames = (HashMap)localObject2;
    localObject3 = new HashMap();
    ((HashMap)localObject3).put("java.lang.Boolean", "kotlin.Boolean");
    ((HashMap)localObject3).put("java.lang.Character", "kotlin.Char");
    ((HashMap)localObject3).put("java.lang.Byte", "kotlin.Byte");
    ((HashMap)localObject3).put("java.lang.Short", "kotlin.Short");
    ((HashMap)localObject3).put("java.lang.Integer", "kotlin.Int");
    ((HashMap)localObject3).put("java.lang.Float", "kotlin.Float");
    ((HashMap)localObject3).put("java.lang.Long", "kotlin.Long");
    ((HashMap)localObject3).put("java.lang.Double", "kotlin.Double");
    primitiveWrapperFqNames = (HashMap)localObject3;
    localObject1 = new HashMap();
    ((HashMap)localObject1).put("java.lang.Object", "kotlin.Any");
    ((HashMap)localObject1).put("java.lang.String", "kotlin.String");
    ((HashMap)localObject1).put("java.lang.CharSequence", "kotlin.CharSequence");
    ((HashMap)localObject1).put("java.lang.Throwable", "kotlin.Throwable");
    ((HashMap)localObject1).put("java.lang.Cloneable", "kotlin.Cloneable");
    ((HashMap)localObject1).put("java.lang.Number", "kotlin.Number");
    ((HashMap)localObject1).put("java.lang.Comparable", "kotlin.Comparable");
    ((HashMap)localObject1).put("java.lang.Enum", "kotlin.Enum");
    ((HashMap)localObject1).put("java.lang.annotation.Annotation", "kotlin.Annotation");
    ((HashMap)localObject1).put("java.lang.Iterable", "kotlin.collections.Iterable");
    ((HashMap)localObject1).put("java.util.Iterator", "kotlin.collections.Iterator");
    ((HashMap)localObject1).put("java.util.Collection", "kotlin.collections.Collection");
    ((HashMap)localObject1).put("java.util.List", "kotlin.collections.List");
    ((HashMap)localObject1).put("java.util.Set", "kotlin.collections.Set");
    ((HashMap)localObject1).put("java.util.ListIterator", "kotlin.collections.ListIterator");
    ((HashMap)localObject1).put("java.util.Map", "kotlin.collections.Map");
    ((HashMap)localObject1).put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
    ((HashMap)localObject1).put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
    ((HashMap)localObject1).put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
    ((HashMap)localObject1).putAll((Map)localObject2);
    ((HashMap)localObject1).putAll((Map)localObject3);
    localObject2 = ((HashMap)localObject2).values();
    Intrinsics.checkNotNullExpressionValue(localObject2, "primitiveFqNames.values");
    localObject3 = ((Iterable)localObject2).iterator();
    Object localObject4;
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = ((Iterator)localObject3).next();
      localObject2 = (Map)localObject1;
      String str = (String)localObject4;
      StringBuilder localStringBuilder = new StringBuilder().append("kotlin.jvm.internal.");
      Intrinsics.checkNotNullExpressionValue(str, "kotlinName");
      localObject4 = StringsKt.substringAfterLast$default(str, '.', null, 2, null);
      Log5ECF72.a(localObject4);
      LogE84000.a(localObject4);
      Log229316.a(localObject4);
      localObject4 = TuplesKt.to((String)localObject4 + "CompanionObject", str + ".Companion");
      ((Map)localObject2).put(((Pair)localObject4).getFirst(), ((Pair)localObject4).getSecond());
    }
    localObject2 = (Map)localObject1;
    localObject3 = FUNCTION_CLASSES.entrySet().iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (Map.Entry)((Iterator)localObject3).next();
      localObject2 = (Class)((Map.Entry)localObject4).getKey();
      i = ((Number)((Map.Entry)localObject4).getValue()).intValue();
      ((HashMap)localObject1).put(((Class)localObject2).getName(), "kotlin.Function" + i);
    }
    classFqNames = (HashMap)localObject1;
    localObject2 = (Map)localObject1;
    localObject1 = (Map)new LinkedHashMap(MapsKt.mapCapacity(((Map)localObject2).size()));
    localObject2 = ((Iterable)((Map)localObject2).entrySet()).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject4 = ((Iterator)localObject2).next();
      localObject3 = ((Map.Entry)localObject4).getKey();
      localObject4 = StringsKt.substringAfterLast$default((String)((Map.Entry)localObject4).getValue(), '.', null, 2, null);
      Log5ECF72.a(localObject4);
      LogE84000.a(localObject4);
      Log229316.a(localObject4);
      ((Map)localObject1).put(localObject3, localObject4);
    }
    simpleNames = (Map)localObject1;
  }
  
  public ClassReference(Class<?> paramClass)
  {
    this.jClass = paramClass;
  }
  
  private final Void error()
  {
    throw new KotlinReflectionNotSupportedError();
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (((paramObject instanceof ClassReference)) && (Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType((KClass)this), JvmClassMappingKt.getJavaObjectType((KClass)paramObject)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public List<Annotation> getAnnotations()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public Collection<KFunction<Object>> getConstructors()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public Class<?> getJClass()
  {
    return this.jClass;
  }
  
  public Collection<KCallable<?>> getMembers()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public Collection<KClass<?>> getNestedClasses()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public Object getObjectInstance()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public String getQualifiedName()
  {
    return Companion.getClassQualifiedName(getJClass());
  }
  
  public List<KClass<? extends Object>> getSealedSubclasses()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public String getSimpleName()
  {
    return Companion.getClassSimpleName(getJClass());
  }
  
  public List<KType> getSupertypes()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public List<KTypeParameter> getTypeParameters()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public KVisibility getVisibility()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public int hashCode()
  {
    return JvmClassMappingKt.getJavaObjectType((KClass)this).hashCode();
  }
  
  public boolean isAbstract()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isCompanion()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isData()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isFinal()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isFun()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isInner()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isInstance(Object paramObject)
  {
    return Companion.isInstance(paramObject, getJClass());
  }
  
  public boolean isOpen()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isSealed()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public boolean isValue()
  {
    error();
    throw new KotlinNothingValueException();
  }
  
  public String toString()
  {
    return getJClass().toString() + " (Kotlin reflection is not available)";
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020$\n\002\030\002\n\002\030\002\n\002\020\b\n\000\n\002\030\002\n\002\020\016\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\024\020\017\032\004\030\0010\n2\n\020\020\032\006\022\002\b\0030\005J\024\020\021\032\004\030\0010\n2\n\020\020\032\006\022\002\b\0030\005J\034\020\022\032\0020\0232\b\020\024\032\004\030\0010\0012\n\020\020\032\006\022\002\b\0030\005R&\020\003\032\032\022\020\022\016\022\n\b\001\022\006\022\002\b\0030\0060\005\022\004\022\0020\0070\004X\004¢\006\002\n\000R*\020\b\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X\004¢\006\002\n\000R*\020\f\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X\004¢\006\002\n\000R*\020\r\032\036\022\004\022\0020\n\022\004\022\0020\n0\tj\016\022\004\022\0020\n\022\004\022\0020\n`\013X\004¢\006\002\n\000R\032\020\016\032\016\022\004\022\0020\n\022\004\022\0020\n0\004X\004¢\006\002\n\000¨\006\025"}, d2={"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final String getClassQualifiedName(Class<?> paramClass)
    {
      Intrinsics.checkNotNullParameter(paramClass, "jClass");
      boolean bool = paramClass.isAnonymousClass();
      String str = null;
      Object localObject = null;
      if (bool)
      {
        localObject = str;
      }
      else if (paramClass.isLocalClass())
      {
        localObject = str;
      }
      else if (paramClass.isArray())
      {
        paramClass = paramClass.getComponentType();
        if (paramClass.isPrimitive())
        {
          str = (String)ClassReference.access$getClassFqNames$cp().get(paramClass.getName());
          paramClass = (Class<?>)localObject;
          if (str != null) {
            paramClass = str + "Array";
          }
        }
        else
        {
          paramClass = (Class<?>)localObject;
        }
        localObject = paramClass;
        if (paramClass == null) {
          localObject = "kotlin.Array";
        }
      }
      else
      {
        str = (String)ClassReference.access$getClassFqNames$cp().get(paramClass.getName());
        localObject = str;
        if (str == null) {
          localObject = paramClass.getCanonicalName();
        }
      }
      return (String)localObject;
    }
    
    public final String getClassSimpleName(Class<?> paramClass)
    {
      Intrinsics.checkNotNullParameter(paramClass, "jClass");
      boolean bool = paramClass.isAnonymousClass();
      Object localObject = "Array";
      String str1 = null;
      if (bool)
      {
        localObject = null;
      }
      else
      {
        String str2;
        if (paramClass.isLocalClass())
        {
          str2 = paramClass.getSimpleName();
          localObject = paramClass.getEnclosingMethod();
          if (localObject != null)
          {
            Intrinsics.checkNotNullExpressionValue(str2, "name");
            str1 = StringsKt.substringAfter$default(str2, ((Method)localObject).getName() + '$', null, 2, null);
            Log5ECF72.a(str1);
            LogE84000.a(str1);
            Log229316.a(str1);
            localObject = str1;
            if (str1 != null) {}
          }
          else
          {
            paramClass = paramClass.getEnclosingConstructor();
            if (paramClass != null)
            {
              Intrinsics.checkNotNullExpressionValue(str2, "name");
              localObject = StringsKt.substringAfter$default(str2, paramClass.getName() + '$', null, 2, null);
              Log5ECF72.a(localObject);
              LogE84000.a(localObject);
              Log229316.a(localObject);
            }
            else
            {
              Intrinsics.checkNotNullExpressionValue(str2, "name");
              localObject = StringsKt.substringAfter$default(str2, '$', null, 2, null);
              Log5ECF72.a(localObject);
              LogE84000.a(localObject);
              Log229316.a(localObject);
            }
          }
        }
        else if (paramClass.isArray())
        {
          paramClass = paramClass.getComponentType();
          if (paramClass.isPrimitive())
          {
            str2 = (String)ClassReference.access$getSimpleNames$cp().get(paramClass.getName());
            paramClass = str1;
            if (str2 != null) {
              paramClass = str2 + "Array";
            }
          }
          else
          {
            paramClass = str1;
          }
          if (paramClass != null) {
            localObject = paramClass;
          }
        }
        else
        {
          str1 = (String)ClassReference.access$getSimpleNames$cp().get(paramClass.getName());
          localObject = str1;
          if (str1 == null) {
            localObject = paramClass.getSimpleName();
          }
        }
      }
      return (String)localObject;
    }
    
    public final boolean isInstance(Object paramObject, Class<?> paramClass)
    {
      Intrinsics.checkNotNullParameter(paramClass, "jClass");
      Object localObject = ClassReference.access$getFUNCTION_CLASSES$cp();
      Intrinsics.checkNotNull(localObject, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
      localObject = (Integer)((Map)localObject).get(paramClass);
      if (localObject != null) {
        return TypeIntrinsics.isFunctionOfArity(paramObject, ((Number)localObject).intValue());
      }
      if (paramClass.isPrimitive()) {
        paramClass = JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(paramClass));
      }
      return paramClass.isInstance(paramObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/ClassReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */