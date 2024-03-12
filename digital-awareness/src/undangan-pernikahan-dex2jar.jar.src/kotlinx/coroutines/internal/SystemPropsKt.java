package kotlinx.coroutines.internal;

import kotlin.Metadata;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"kotlinx/coroutines/internal/SystemPropsKt__SystemPropsKt", "kotlinx/coroutines/internal/SystemPropsKt__SystemProps_commonKt"}, k=4, mv={1, 6, 0}, xi=48)
public final class SystemPropsKt
{
  public static final int getAVAILABLE_PROCESSORS()
  {
    return SystemPropsKt__SystemPropsKt.getAVAILABLE_PROCESSORS();
  }
  
  public static final int systemProp(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramInt1, paramInt2, paramInt3);
  }
  
  public static final long systemProp(String paramString, long paramLong1, long paramLong2, long paramLong3)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramLong1, paramLong2, paramLong3);
  }
  
  public static final String systemProp(String paramString)
  {
    paramString = SystemPropsKt__SystemPropsKt.systemProp(paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return paramString;
  }
  
  public static final boolean systemProp(String paramString, boolean paramBoolean)
  {
    return SystemPropsKt__SystemProps_commonKt.systemProp(paramString, paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/SystemPropsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */