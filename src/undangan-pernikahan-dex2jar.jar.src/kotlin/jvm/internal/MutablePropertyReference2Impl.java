package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty2.Setter;
import kotlin.reflect.KProperty2.Getter;

public class MutablePropertyReference2Impl
  extends MutablePropertyReference2
{
  public MutablePropertyReference2Impl(Class paramClass, String paramString1, String paramString2, int paramInt)
  {
    super(paramClass, paramString1, paramString2, paramInt);
  }
  
  public MutablePropertyReference2Impl(KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(((ClassBasedDeclarationContainer)paramKDeclarationContainer).getJClass(), paramString1, paramString2, paramKDeclarationContainer instanceof KClass ^ true);
  }
  
  public Object get(Object paramObject1, Object paramObject2)
  {
    return getGetter().call(new Object[] { paramObject1, paramObject2 });
  }
  
  public void set(Object paramObject1, Object paramObject2, Object paramObject3)
  {
    getSetter().call(new Object[] { paramObject1, paramObject2, paramObject3 });
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/MutablePropertyReference2Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */