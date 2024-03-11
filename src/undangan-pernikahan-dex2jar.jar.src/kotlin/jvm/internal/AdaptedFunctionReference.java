package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KDeclarationContainer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class AdaptedFunctionReference
  implements FunctionBase, Serializable
{
  private final int arity;
  private final int flags;
  private final boolean isTopLevel;
  private final String name;
  private final Class owner;
  protected final Object receiver;
  private final String signature;
  
  public AdaptedFunctionReference(int paramInt1, Class paramClass, String paramString1, String paramString2, int paramInt2)
  {
    this(paramInt1, CallableReference.NO_RECEIVER, paramClass, paramString1, paramString2, paramInt2);
  }
  
  public AdaptedFunctionReference(int paramInt1, Object paramObject, Class paramClass, String paramString1, String paramString2, int paramInt2)
  {
    this.receiver = paramObject;
    this.owner = paramClass;
    this.name = paramString1;
    this.signature = paramString2;
    boolean bool = true;
    if ((paramInt2 & 0x1) != 1) {
      bool = false;
    }
    this.isTopLevel = bool;
    this.arity = paramInt1;
    this.flags = (paramInt2 >> 1);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof AdaptedFunctionReference)) {
      return false;
    }
    paramObject = (AdaptedFunctionReference)paramObject;
    if ((this.isTopLevel != ((AdaptedFunctionReference)paramObject).isTopLevel) || (this.arity != ((AdaptedFunctionReference)paramObject).arity) || (this.flags != ((AdaptedFunctionReference)paramObject).flags) || (!Intrinsics.areEqual(this.receiver, ((AdaptedFunctionReference)paramObject).receiver)) || (!Intrinsics.areEqual(this.owner, ((AdaptedFunctionReference)paramObject).owner)) || (!this.name.equals(((AdaptedFunctionReference)paramObject).name)) || (!this.signature.equals(((AdaptedFunctionReference)paramObject).signature))) {
      bool = false;
    }
    return bool;
  }
  
  public int getArity()
  {
    return this.arity;
  }
  
  public KDeclarationContainer getOwner()
  {
    Object localObject = this.owner;
    if (localObject == null) {
      localObject = null;
    } else if (this.isTopLevel) {
      localObject = Reflection.getOrCreateKotlinPackage((Class)localObject);
    } else {
      localObject = Reflection.getOrCreateKotlinClass((Class)localObject);
    }
    return (KDeclarationContainer)localObject;
  }
  
  public int hashCode()
  {
    Object localObject = this.receiver;
    int j = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = this.owner;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    int m = this.name.hashCode();
    int n = this.signature.hashCode();
    int k;
    if (this.isTopLevel) {
      k = 1231;
    } else {
      k = 1237;
    }
    return (((((i * 31 + j) * 31 + m) * 31 + n) * 31 + k) * 31 + this.arity) * 31 + this.flags;
  }
  
  public String toString()
  {
    String str = Reflection.renderLambdaToString(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/AdaptedFunctionReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */