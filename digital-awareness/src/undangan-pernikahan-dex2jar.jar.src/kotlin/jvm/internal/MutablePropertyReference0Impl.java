package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty0.Setter;
import kotlin.reflect.KProperty0.Getter;

public class MutablePropertyReference0Impl
  extends MutablePropertyReference0
{
  public MutablePropertyReference0Impl(Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(NO_RECEIVER, paramClass, paramString1, paramString2, paramInt);
  }
  
  public MutablePropertyReference0Impl(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, paramInt);
  }
  
  public MutablePropertyReference0Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(NO_RECEIVER, ((ClassBasedDeclarationContainer)paramKDeclarationContainer).getJClass(), paramString1, paramString2, paramKDeclarationContainer instanceof KClass ^ true);
  }
  
  public Object get()
  {
    return getGetter().call(new Object[0]);
  }
  
  public void set(Object paramObject)
  {
    getSetter().call(new Object[] { paramObject });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/MutablePropertyReference0Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */