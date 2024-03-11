package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty1.Getter;

public class PropertyReference1Impl
  extends PropertyReference1
{
  public PropertyReference1Impl(Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(NO_RECEIVER, paramClass, paramString1, paramString2, paramInt);
  }
  
  public PropertyReference1Impl(Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramObject, paramClass, paramString1, paramString2, paramInt);
  }
  
  public PropertyReference1Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(NO_RECEIVER, ((ClassBasedDeclarationContainer)paramKDeclarationContainer).getJClass(), paramString1, paramString2, paramKDeclarationContainer instanceof KClass ^ true);
  }
  
  public Object get(Object paramObject)
  {
    return getGetter().call(new Object[] { paramObject });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/PropertyReference1Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */