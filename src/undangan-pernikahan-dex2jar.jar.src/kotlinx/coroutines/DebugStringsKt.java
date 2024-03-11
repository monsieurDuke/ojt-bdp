package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\024\n\000\n\002\020\016\n\002\020\000\n\002\b\005\n\002\030\002\n\000\032\020\020\007\032\0020\001*\006\022\002\b\0030\bH\000\"\030\020\000\032\0020\001*\0020\0028@X\004¢\006\006\032\004\b\003\020\004\"\030\020\005\032\0020\001*\0020\0028@X\004¢\006\006\032\004\b\006\020\004¨\006\t"}, d2={"classSimpleName", "", "", "getClassSimpleName", "(Ljava/lang/Object;)Ljava/lang/String;", "hexAddress", "getHexAddress", "toDebugString", "Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DebugStringsKt
{
  public static final String getClassSimpleName(Object paramObject)
  {
    return paramObject.getClass().getSimpleName();
  }
  
  public static final String getHexAddress(Object paramObject)
  {
    paramObject = Integer.toHexString(System.identityHashCode(paramObject));
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    return (String)paramObject;
  }
  
  public static final String toDebugString(Continuation<?> paramContinuation)
  {
    if ((paramContinuation instanceof DispatchedContinuation))
    {
      paramContinuation = paramContinuation.toString();
    }
    else
    {
      Object localObject2;
      try
      {
        Object localObject1 = Result.Companion;
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        localObject1 = ((StringBuilder)localObject1).append(paramContinuation).append('@');
        localObject3 = getHexAddress(paramContinuation);
        Log5ECF72.a(localObject3);
        LogE84000.a(localObject3);
        Log229316.a(localObject3);
        localObject1 = Result.constructor-impl((String)localObject3);
      }
      finally
      {
        Object localObject3 = Result.Companion;
        localObject2 = Result.constructor-impl(ResultKt.createFailure(localThrowable));
      }
      if (Result.exceptionOrNull-impl(localObject2) != null)
      {
        localObject2 = new StringBuilder().append(paramContinuation.getClass().getName()).append('@');
        paramContinuation = getHexAddress(paramContinuation);
        Log5ECF72.a(paramContinuation);
        LogE84000.a(paramContinuation);
        Log229316.a(paramContinuation);
        localObject2 = paramContinuation;
      }
      paramContinuation = (String)localObject2;
    }
    return paramContinuation;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DebugStringsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */