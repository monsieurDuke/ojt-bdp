package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

public abstract class PropertyReference
  extends CallableReference
  implements KProperty
{
  public PropertyReference() {}
  
  public PropertyReference(Object paramObject)
  {
    super(paramObject);
  }
  
  public PropertyReference(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, bool);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof PropertyReference))
    {
      paramObject = (PropertyReference)paramObject;
      if ((!getOwner().equals(((PropertyReference)paramObject).getOwner())) || (!getName().equals(((PropertyReference)paramObject).getName())) || (!getSignature().equals(((PropertyReference)paramObject).getSignature())) || (!Intrinsics.areEqual(getBoundReceiver(), ((PropertyReference)paramObject).getBoundReceiver()))) {
        bool = false;
      }
      return bool;
    }
    if ((paramObject instanceof KProperty)) {
      return paramObject.equals(compute());
    }
    return false;
  }
  
  protected KProperty getReflected()
  {
    return (KProperty)super.getReflected();
  }
  
  public int hashCode()
  {
    return (getOwner().hashCode() * 31 + getName().hashCode()) * 31 + getSignature().hashCode();
  }
  
  public boolean isConst()
  {
    return getReflected().isConst();
  }
  
  public boolean isLateinit()
  {
    return getReflected().isLateinit();
  }
  
  public String toString()
  {
    KCallable localKCallable = compute();
    if (localKCallable != this) {
      return localKCallable.toString();
    }
    return "property " + getName() + " (Kotlin reflection is not available)";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PropertyReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */