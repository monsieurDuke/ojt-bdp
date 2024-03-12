package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KFunction;

public class FunInterfaceConstructorReference
  extends FunctionReference
  implements Serializable
{
  private final Class funInterface;
  
  public FunInterfaceConstructorReference(Class paramClass)
  {
    super(1);
    this.funInterface = paramClass;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof FunInterfaceConstructorReference)) {
      return false;
    }
    paramObject = (FunInterfaceConstructorReference)paramObject;
    return this.funInterface.equals(((FunInterfaceConstructorReference)paramObject).funInterface);
  }
  
  protected KFunction getReflected()
  {
    throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
  }
  
  public int hashCode()
  {
    return this.funInterface.hashCode();
  }
  
  public String toString()
  {
    return "fun interface " + this.funInterface.getName();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/FunInterfaceConstructorReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */