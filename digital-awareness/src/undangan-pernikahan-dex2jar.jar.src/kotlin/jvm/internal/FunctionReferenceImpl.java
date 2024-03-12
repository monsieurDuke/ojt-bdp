package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl
  extends FunctionReference
{
  public FunctionReferenceImpl(int paramInt1, Class paramClass, String paramString1, String paramString2, int paramInt2)
  {
    super(paramInt1, NO_RECEIVER, paramClass, paramString1, paramString2, paramInt2);
  }
  
  public FunctionReferenceImpl(int paramInt1, Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt2)
  {
    super(paramInt1, paramObject, paramClass, paramString1, paramString2, paramInt2);
  }
  
  public FunctionReferenceImpl(int paramInt, KDeclarationContainer paramKDeclarationContainer, String paramString1, String paramString2)
  {
    super(paramInt, NO_RECEIVER, ((ClassBasedDeclarationContainer)paramKDeclarationContainer).getJClass(), paramString1, paramString2, paramKDeclarationContainer instanceof KClass ^ true);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/FunctionReferenceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */