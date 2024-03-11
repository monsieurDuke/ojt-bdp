package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty0.Getter;

public class PropertyReference0Impl
  extends PropertyReference0
{
  public PropertyReference0Impl(Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(NO_RECEIVER, paramClass, paramString1, paramString2, paramInt);
  }
  
  public PropertyReference0Impl(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, paramInt);
  }
  
  public PropertyReference0Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(NO_RECEIVER, ((ClassBasedDeclarationContainer)paramKDeclarationContainer).getJClass(), paramString1, paramString2, paramKDeclarationContainer instanceof KClass ^ true);
  }
  
  public Object get()
  {
    return getGetter().call(new Object[0]);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PropertyReference0Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */