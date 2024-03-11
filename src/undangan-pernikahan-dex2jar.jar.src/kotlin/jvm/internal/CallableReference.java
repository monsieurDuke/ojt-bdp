package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;

public abstract class CallableReference
  implements KCallable, Serializable
{
  public static final Object NO_RECEIVER = NoReceiver.INSTANCE;
  private final boolean isTopLevel;
  private final String name;
  private final Class owner;
  protected final Object receiver;
  private transient KCallable reflected;
  private final String signature;
  
  public CallableReference()
  {
    this(NO_RECEIVER);
  }
  
  protected CallableReference(Object paramObject)
  {
    this(paramObject, null, null, null, false);
  }
  
  protected CallableReference(Object paramObject, Class paramClass, String paramString1, String paramString2, boolean paramBoolean)
  {
    this.receiver = paramObject;
    this.owner = paramClass;
    this.name = paramString1;
    this.signature = paramString2;
    this.isTopLevel = paramBoolean;
  }
  
  public Object call(Object... paramVarArgs)
  {
    return getReflected().call(paramVarArgs);
  }
  
  public Object callBy(Map paramMap)
  {
    return getReflected().callBy(paramMap);
  }
  
  public KCallable compute()
  {
    KCallable localKCallable2 = this.reflected;
    KCallable localKCallable1 = localKCallable2;
    if (localKCallable2 == null)
    {
      localKCallable1 = computeReflected();
      this.reflected = localKCallable1;
    }
    return localKCallable1;
  }
  
  protected abstract KCallable computeReflected();
  
  public List<Annotation> getAnnotations()
  {
    return getReflected().getAnnotations();
  }
  
  public Object getBoundReceiver()
  {
    return this.receiver;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public KDeclarationContainer getOwner()
  {
    Object localObject = this.owner;
    if (localObject == null) {
      localObject = null;
    } else if (this.isTopLevel) {
      localObject = Reflection.getOrCreateKotlinPackage((Class)localObject);
    } else {
      localObject = Reflection.getOrCreateKotlinClass((Class)localObject);
    }
    return (KDeclarationContainer)localObject;
  }
  
  public List<KParameter> getParameters()
  {
    return getReflected().getParameters();
  }
  
  protected KCallable getReflected()
  {
    KCallable localKCallable = compute();
    if (localKCallable != this) {
      return localKCallable;
    }
    throw new KotlinReflectionNotSupportedError();
  }
  
  public KType getReturnType()
  {
    return getReflected().getReturnType();
  }
  
  public String getSignature()
  {
    return this.signature;
  }
  
  public List<KTypeParameter> getTypeParameters()
  {
    return getReflected().getTypeParameters();
  }
  
  public KVisibility getVisibility()
  {
    return getReflected().getVisibility();
  }
  
  public boolean isAbstract()
  {
    return getReflected().isAbstract();
  }
  
  public boolean isFinal()
  {
    return getReflected().isFinal();
  }
  
  public boolean isOpen()
  {
    return getReflected().isOpen();
  }
  
  public boolean isSuspend()
  {
    return getReflected().isSuspend();
  }
  
  private static class NoReceiver
    implements Serializable
  {
    private static final NoReceiver INSTANCE = new NoReceiver();
    
    private Object readResolve()
      throws ObjectStreamException
    {
      return INSTANCE;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/CallableReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */