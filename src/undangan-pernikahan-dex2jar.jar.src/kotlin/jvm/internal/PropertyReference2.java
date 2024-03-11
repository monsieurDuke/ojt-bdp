package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KProperty2.Getter;

public abstract class PropertyReference2
  extends PropertyReference
  implements KProperty2
{
  public PropertyReference2() {}
  
  public PropertyReference2(Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(NO_RECEIVER, paramClass, paramString1, paramString2, paramInt);
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.property2(this);
  }
  
  public Object getDelegate(Object paramObject1, Object paramObject2)
  {
    return ((KProperty2)getReflected()).getDelegate(paramObject1, paramObject2);
  }
  
  public KProperty2.Getter getGetter()
  {
    return ((KProperty2)getReflected()).getGetter();
  }
  
  public Object invoke(Object paramObject1, Object paramObject2)
  {
    return get(paramObject1, paramObject2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PropertyReference2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */