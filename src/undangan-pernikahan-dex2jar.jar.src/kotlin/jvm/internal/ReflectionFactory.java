package kotlin.jvm.internal;

import java.util.List;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

public class ReflectionFactory
{
  private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";
  
  public KClass createKotlinClass(Class paramClass)
  {
    return new ClassReference(paramClass);
  }
  
  public KClass createKotlinClass(Class paramClass, String paramString)
  {
    return new ClassReference(paramClass);
  }
  
  public KFunction function(FunctionReference paramFunctionReference)
  {
    return paramFunctionReference;
  }
  
  public KClass getOrCreateKotlinClass(Class paramClass)
  {
    return new ClassReference(paramClass);
  }
  
  public KClass getOrCreateKotlinClass(Class paramClass, String paramString)
  {
    return new ClassReference(paramClass);
  }
  
  public KDeclarationContainer getOrCreateKotlinPackage(Class paramClass, String paramString)
  {
    return new PackageReference(paramClass, paramString);
  }
  
  public KType mutableCollectionType(KType paramKType)
  {
    TypeReference localTypeReference = (TypeReference)paramKType;
    return new TypeReference(paramKType.getClassifier(), paramKType.getArguments(), localTypeReference.getPlatformTypeUpperBound$kotlin_stdlib(), localTypeReference.getFlags$kotlin_stdlib() | 0x2);
  }
  
  public KMutableProperty0 mutableProperty0(MutablePropertyReference0 paramMutablePropertyReference0)
  {
    return paramMutablePropertyReference0;
  }
  
  public KMutableProperty1 mutableProperty1(MutablePropertyReference1 paramMutablePropertyReference1)
  {
    return paramMutablePropertyReference1;
  }
  
  public KMutableProperty2 mutableProperty2(MutablePropertyReference2 paramMutablePropertyReference2)
  {
    return paramMutablePropertyReference2;
  }
  
  public KType nothingType(KType paramKType)
  {
    TypeReference localTypeReference = (TypeReference)paramKType;
    return new TypeReference(paramKType.getClassifier(), paramKType.getArguments(), localTypeReference.getPlatformTypeUpperBound$kotlin_stdlib(), localTypeReference.getFlags$kotlin_stdlib() | 0x4);
  }
  
  public KType platformType(KType paramKType1, KType paramKType2)
  {
    return new TypeReference(paramKType1.getClassifier(), paramKType1.getArguments(), paramKType2, ((TypeReference)paramKType1).getFlags$kotlin_stdlib());
  }
  
  public KProperty0 property0(PropertyReference0 paramPropertyReference0)
  {
    return paramPropertyReference0;
  }
  
  public KProperty1 property1(PropertyReference1 paramPropertyReference1)
  {
    return paramPropertyReference1;
  }
  
  public KProperty2 property2(PropertyReference2 paramPropertyReference2)
  {
    return paramPropertyReference2;
  }
  
  public String renderLambdaToString(FunctionBase paramFunctionBase)
  {
    paramFunctionBase = paramFunctionBase.getClass().getGenericInterfaces()[0].toString();
    if (paramFunctionBase.startsWith("kotlin.jvm.functions.")) {
      paramFunctionBase = paramFunctionBase.substring("kotlin.jvm.functions.".length());
    }
    return paramFunctionBase;
  }
  
  public String renderLambdaToString(Lambda paramLambda)
  {
    return renderLambdaToString(paramLambda);
  }
  
  public void setUpperBounds(KTypeParameter paramKTypeParameter, List<KType> paramList)
  {
    ((TypeParameterReference)paramKTypeParameter).setUpperBounds(paramList);
  }
  
  public KType typeOf(KClassifier paramKClassifier, List<KTypeProjection> paramList, boolean paramBoolean)
  {
    return new TypeReference(paramKClassifier, paramList, paramBoolean);
  }
  
  public KTypeParameter typeParameter(Object paramObject, String paramString, KVariance paramKVariance, boolean paramBoolean)
  {
    return new TypeParameterReference(paramObject, paramString, paramKVariance, paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/ReflectionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */