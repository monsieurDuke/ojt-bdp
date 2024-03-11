package kotlin.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0000\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020 \n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\000\032\"\020\n\032\0020\0012\n\020\013\032\006\022\002\b\0030\f2\f\020\r\032\b\022\004\022\0020\0070\016H\003\032\020\020\017\032\0020\0202\006\020\021\032\0020\001H\002\032\026\020\022\032\0020\001*\0020\0022\b\b\002\020\023\032\0020\024H\003\"\036\020\000\032\0020\001*\0020\0028FX\004¢\006\f\022\004\b\003\020\004\032\004\b\005\020\006\"\036\020\000\032\0020\001*\0020\0078BX\004¢\006\f\022\004\b\003\020\b\032\004\b\005\020\t¨\006\025"}, d2={"javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType$annotations", "(Lkotlin/reflect/KType;)V", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "Lkotlin/reflect/KTypeProjection;", "(Lkotlin/reflect/KTypeProjection;)V", "(Lkotlin/reflect/KTypeProjection;)Ljava/lang/reflect/Type;", "createPossiblyInnerType", "jClass", "Ljava/lang/Class;", "arguments", "", "typeToString", "", "type", "computeJavaType", "forceWrapper", "", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class TypesJVMKt
{
  private static final Type computeJavaType(KType paramKType, boolean paramBoolean)
  {
    Object localObject1 = paramKType.getClassifier();
    if ((localObject1 instanceof KTypeParameter)) {
      return (Type)new TypeVariableImpl((KTypeParameter)localObject1);
    }
    if ((localObject1 instanceof KClass))
    {
      localObject1 = (KClass)localObject1;
      if (paramBoolean) {
        localObject1 = JvmClassMappingKt.getJavaObjectType((KClass)localObject1);
      } else {
        localObject1 = JvmClassMappingKt.getJavaClass((KClass)localObject1);
      }
      Object localObject2 = paramKType.getArguments();
      if (((List)localObject2).isEmpty()) {
        return (Type)localObject1;
      }
      if (((Class)localObject1).isArray())
      {
        if (((Class)localObject1).getComponentType().isPrimitive()) {
          return (Type)localObject1;
        }
        localObject2 = (KTypeProjection)CollectionsKt.singleOrNull((List)localObject2);
        if (localObject2 != null)
        {
          paramKType = ((KTypeProjection)localObject2).component1();
          localObject2 = ((KTypeProjection)localObject2).component2();
          int i;
          if (paramKType == null) {
            i = -1;
          } else {
            i = WhenMappings.$EnumSwitchMapping$0[paramKType.ordinal()];
          }
          switch (i)
          {
          case 0: 
          default: 
            throw new NoWhenBranchMatchedException();
          case 2: 
          case 3: 
            Intrinsics.checkNotNull(localObject2);
            paramKType = computeJavaType$default((KType)localObject2, false, 1, null);
            if ((paramKType instanceof Class)) {
              paramKType = (Type)localObject1;
            } else {
              paramKType = (Type)new GenericArrayTypeImpl(paramKType);
            }
            break;
          case -1: 
          case 1: 
            paramKType = (Type)localObject1;
          }
          return paramKType;
        }
        throw new IllegalArgumentException("kotlin.Array must have exactly one type argument: " + paramKType);
      }
      return createPossiblyInnerType((Class)localObject1, (List)localObject2);
    }
    throw new UnsupportedOperationException("Unsupported type classifier: " + paramKType);
  }
  
  private static final Type createPossiblyInnerType(Class<?> paramClass, List<KTypeProjection> paramList)
  {
    Object localObject1 = paramClass.getDeclaringClass();
    if (localObject1 == null)
    {
      localObject1 = (Iterable)paramList;
      paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        paramList.add(getJavaType((KTypeProjection)((Iterator)localObject1).next()));
      }
      paramList = (List)paramList;
      return (Type)new ParameterizedTypeImpl(paramClass, null, paramList);
    }
    if (Modifier.isStatic(paramClass.getModifiers()))
    {
      localObject1 = (Type)localObject1;
      localObject2 = (Iterable)paramList;
      paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext()) {
        paramList.add(getJavaType((KTypeProjection)((Iterator)localObject2).next()));
      }
      paramList = (List)paramList;
      return (Type)new ParameterizedTypeImpl(paramClass, (Type)localObject1, paramList);
    }
    int i = paramClass.getTypeParameters().length;
    localObject1 = createPossiblyInnerType((Class)localObject1, paramList.subList(i, paramList.size()));
    Object localObject2 = (Iterable)paramList.subList(0, i);
    paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      paramList.add(getJavaType((KTypeProjection)((Iterator)localObject2).next()));
    }
    paramList = (List)paramList;
    return (Type)new ParameterizedTypeImpl(paramClass, (Type)localObject1, paramList);
  }
  
  public static final Type getJavaType(KType paramKType)
  {
    Intrinsics.checkNotNullParameter(paramKType, "<this>");
    if ((paramKType instanceof KTypeBase))
    {
      Type localType = ((KTypeBase)paramKType).getJavaType();
      if (localType != null) {
        return localType;
      }
    }
    return computeJavaType$default(paramKType, false, 1, null);
  }
  
  private static final Type getJavaType(KTypeProjection paramKTypeProjection)
  {
    KVariance localKVariance = paramKTypeProjection.getVariance();
    if (localKVariance == null) {
      return (Type)WildcardTypeImpl.Companion.getSTAR();
    }
    paramKTypeProjection = paramKTypeProjection.getType();
    Intrinsics.checkNotNull(paramKTypeProjection);
    switch (WhenMappings.$EnumSwitchMapping$0[localKVariance.ordinal()])
    {
    default: 
      throw new NoWhenBranchMatchedException();
    case 3: 
      paramKTypeProjection = (Type)new WildcardTypeImpl(computeJavaType(paramKTypeProjection, true), null);
      break;
    case 2: 
      paramKTypeProjection = computeJavaType(paramKTypeProjection, true);
      break;
    case 1: 
      paramKTypeProjection = (Type)new WildcardTypeImpl(null, computeJavaType(paramKTypeProjection, true));
    }
    return paramKTypeProjection;
  }
  
  private static final String typeToString(Type paramType)
  {
    if ((paramType instanceof Class))
    {
      if (((Class)paramType).isArray())
      {
        Object localObject = SequencesKt.generateSequence(paramType, (Function1)typeToString.unwrap.1.INSTANCE);
        paramType = new StringBuilder().append(((Class)SequencesKt.last((Sequence)localObject)).getName());
        localObject = StringsKt.repeat((CharSequence)"[]", SequencesKt.count((Sequence)localObject));
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        paramType = (String)localObject;
      }
      else
      {
        paramType = ((Class)paramType).getName();
      }
      Intrinsics.checkNotNullExpressionValue(paramType, "{\n        if (type.isArr…   } else type.name\n    }");
    }
    else
    {
      paramType = paramType.toString();
    }
    return paramType;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/TypesJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */