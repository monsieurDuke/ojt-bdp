package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty1.Setter;
import kotlin.reflect.KProperty1.Getter;

public abstract class MutablePropertyReference1
  extends MutablePropertyReference
  implements KMutableProperty1
{
  public MutablePropertyReference1() {}
  
  public MutablePropertyReference1(Object paramObject)
  {
    super(paramObject);
  }
  
  public MutablePropertyReference1(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, paramInt);
  }
  
  protected KCallable computeReflected()
  {
    return Reflection.mutableProperty1(this);
  }
  
  public Object getDelegate(Object paramObject)
  {
    return ((KMutableProperty1)getReflected()).getDelegate(paramObject);
  }
  
  public KProperty1.Getter getGetter()
  {
    return ((KMutableProperty1)getReflected()).getGetter();
  }
  
  public KMutableProperty1.Setter getSetter()
  {
    return ((KMutableProperty1)getReflected()).getSetter();
  }
  
  public Object invoke(Object paramObject)
  {
    return get(paramObject);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/MutablePropertyReference1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */