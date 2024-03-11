package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference
  extends CallableReference
  implements FunctionBase, KFunction
{
  private final int arity;
  private final int flags;
  
  public FunctionReference(int paramInt)
  {
    this(paramInt, NO_RECEIVER, null, null, null, 0);
  }
  
  public FunctionReference(int paramInt, Object paramObject)
  {
    this(paramInt, paramObject, null, null, null, 0);
  }
  
  public FunctionReference(int paramInt1, Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt2)
  {
    super(paramObject, paramClass, paramString1, paramString2, bool);
    this.arity = paramInt1;
    this.flags = (paramInt2 >> 1);
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.function(this);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof FunctionReference))
    {
      paramObject = (FunctionReference)paramObject;
      if ((!getName().equals(((FunctionReference)paramObject).getName())) || (!getSignature().equals(((FunctionReference)paramObject).getSignature())) || (this.flags != ((FunctionReference)paramObject).flags) || (this.arity != ((FunctionReference)paramObject).arity) || (!Intrinsics.areEqual(getBoundReceiver(), ((FunctionReference)paramObject).getBoundReceiver())) || (!Intrinsics.areEqual(getOwner(), ((FunctionReference)paramObject).getOwner()))) {
        bool = false;
      }
      return bool;
    }
    if ((paramObject instanceof KFunction)) {
      return paramObject.equals(compute());
    }
    return false;
  }
  
  public int getArity()
  {
    return this.arity;
  }
  
  protected KFunction getReflected()
  {
    return (KFunction)super.getReflected();
  }
  
  public int hashCode()
  {
    int i;
    if (getOwner() == null) {
      i = 0;
    } else {
      i = getOwner().hashCode() * 31;
    }
    return (i + getName().hashCode()) * 31 + getSignature().hashCode();
  }
  
  public boolean isExternal()
  {
    return getReflected().isExternal();
  }
  
  public boolean isInfix()
  {
    return getReflected().isInfix();
  }
  
  public boolean isInline()
  {
    return getReflected().isInline();
  }
  
  public boolean isOperator()
  {
    return getReflected().isOperator();
  }
  
  public boolean isSuspend()
  {
    return getReflected().isSuspend();
  }
  
  public String toString()
  {
    Object localObject = compute();
    if (localObject != this) {
      return localObject.toString();
    }
    if ("<init>".equals(getName())) {
      localObject = "constructor (Kotlin reflection is not available)";
    } else {
      localObject = "function " + getName() + " (Kotlin reflection is not available)";
    }
    return (String)localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/FunctionReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */