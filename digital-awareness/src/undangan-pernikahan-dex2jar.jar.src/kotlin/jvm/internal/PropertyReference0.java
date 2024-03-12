package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty0.Getter;

public abstract class PropertyReference0
  extends PropertyReference
  implements KProperty0
{
  public PropertyReference0() {}
  
  public PropertyReference0(Object paramObject)
  {
    super(paramObject);
  }
  
  public PropertyReference0(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, paramInt);
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.property0(this);
  }
  
  public Object getDelegate()
  {
    return ((KProperty0)getReflected()).getDelegate();
  }
  
  public KProperty0.Getter getGetter()
  {
    return ((KProperty0)getReflected()).getGetter();
  }
  
  public Object invoke()
  {
    return get();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PropertyReference0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */