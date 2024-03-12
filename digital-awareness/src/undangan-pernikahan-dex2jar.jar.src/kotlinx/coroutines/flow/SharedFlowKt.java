package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000L\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\002\020\021\n\000\n\002\020\t\n\002\b\002\n\002\020\002\n\002\b\003\0320\020\004\032\b\022\004\022\002H\0060\005\"\004\b\000\020\0062\b\b\002\020\007\032\0020\b2\b\b\002\020\t\032\0020\b2\b\b\002\020\n\032\0020\013\0326\020\f\032\b\022\004\022\002H\0060\r\"\004\b\000\020\006*\b\022\004\022\002H\0060\0162\006\020\017\032\0020\0202\006\020\021\032\0020\b2\006\020\n\032\0020\013H\000\032#\020\022\032\004\030\0010\023*\n\022\006\022\004\030\0010\0230\0242\006\020\025\032\0020\026H\002¢\006\002\020\027\032+\020\030\032\0020\031*\n\022\006\022\004\030\0010\0230\0242\006\020\025\032\0020\0262\b\020\032\032\004\030\0010\023H\002¢\006\002\020\033\"\026\020\000\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\002\020\003¨\006\034"}, d2={"NO_VALUE", "Lkotlinx/coroutines/internal/Symbol;", "getNO_VALUE$annotations", "()V", "MutableSharedFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "T", "replay", "", "extraBufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "fuseSharedFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharedFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getBufferAt", "", "", "index", "", "([Ljava/lang/Object;J)Ljava/lang/Object;", "setBufferAt", "", "item", "([Ljava/lang/Object;JLjava/lang/Object;)V", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SharedFlowKt
{
  public static final Symbol NO_VALUE = new Symbol("NO_VALUE");
  
  public static final <T> MutableSharedFlow<T> MutableSharedFlow(int paramInt1, int paramInt2, BufferOverflow paramBufferOverflow)
  {
    int j = 1;
    int i;
    if (paramInt1 >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt2 >= 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        i = j;
        if (paramInt1 <= 0)
        {
          i = j;
          if (paramInt2 <= 0) {
            if (paramBufferOverflow == BufferOverflow.SUSPEND) {
              i = j;
            } else {
              i = 0;
            }
          }
        }
        if (i != 0)
        {
          paramInt2 = paramInt1 + paramInt2;
          if (paramInt2 < 0) {
            paramInt2 = Integer.MAX_VALUE;
          }
          return (MutableSharedFlow)new SharedFlowImpl(paramInt1, paramInt2, paramBufferOverflow);
        }
        paramBufferOverflow = Intrinsics.stringPlus("replay or extraBufferCapacity must be positive with non-default onBufferOverflow strategy ", paramBufferOverflow);
        Log5ECF72.a(paramBufferOverflow);
        LogE84000.a(paramBufferOverflow);
        Log229316.a(paramBufferOverflow);
        throw new IllegalArgumentException(paramBufferOverflow.toString());
      }
      paramBufferOverflow = Intrinsics.stringPlus("extraBufferCapacity cannot be negative, but was ", Integer.valueOf(paramInt2));
      Log5ECF72.a(paramBufferOverflow);
      LogE84000.a(paramBufferOverflow);
      Log229316.a(paramBufferOverflow);
      throw new IllegalArgumentException(paramBufferOverflow.toString());
    }
    paramBufferOverflow = Intrinsics.stringPlus("replay cannot be negative, but was ", Integer.valueOf(paramInt1));
    Log5ECF72.a(paramBufferOverflow);
    LogE84000.a(paramBufferOverflow);
    Log229316.a(paramBufferOverflow);
    throw new IllegalArgumentException(paramBufferOverflow.toString());
  }
  
  public static final <T> Flow<T> fuseSharedFlow(SharedFlow<? extends T> paramSharedFlow, CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    if (((paramInt == 0) || (paramInt == -3)) && (paramBufferOverflow == BufferOverflow.SUSPEND)) {
      return (Flow)paramSharedFlow;
    }
    return (Flow)new ChannelFlowOperatorImpl((Flow)paramSharedFlow, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  private static final Object getBufferAt(Object[] paramArrayOfObject, long paramLong)
  {
    return paramArrayOfObject[((int)paramLong & paramArrayOfObject.length - 1)];
  }
  
  private static final void setBufferAt(Object[] paramArrayOfObject, long paramLong, Object paramObject)
  {
    paramArrayOfObject[((int)paramLong & paramArrayOfObject.length - 1)] = paramObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharedFlowKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */