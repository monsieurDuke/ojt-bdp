package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\0004\n\000\n\002\030\002\n\002\b\r\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\003\n\002\020\002\n\000\032#\020\021\032\b\022\004\022\002H\0230\022\"\004\b\000\020\023*\004\030\0010\024H\bø\001\000¢\006\002\020\025\032%\020\021\032\b\022\004\022\002H\0230\022\"\004\b\000\020\023*\006\022\002\b\0030\026H\bø\001\000¢\006\002\020\027\"\026\020\000\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\007\020\003\"\026\020\b\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\t\020\003\"\026\020\n\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\013\020\003\"\026\020\f\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\r\020\003\"\016\020\016\032\0020\017XT¢\006\002\n\000\"\016\020\020\032\0020\017XT¢\006\002\n\000*(\b\000\020\030\"\020\022\006\022\004\030\0010\032\022\004\022\0020\0330\0312\020\022\006\022\004\030\0010\032\022\004\022\0020\0330\031\002\004\n\002\b\031¨\006\034"}, d2={"EMPTY", "Lkotlinx/coroutines/internal/Symbol;", "getEMPTY$annotations", "()V", "ENQUEUE_FAILED", "getENQUEUE_FAILED$annotations", "HANDLER_INVOKED", "getHANDLER_INVOKED$annotations", "OFFER_FAILED", "getOFFER_FAILED$annotations", "OFFER_SUCCESS", "getOFFER_SUCCESS$annotations", "POLL_FAILED", "getPOLL_FAILED$annotations", "RECEIVE_RESULT", "", "RECEIVE_THROWS_ON_CLOSE", "toResult", "Lkotlinx/coroutines/channels/ChannelResult;", "E", "", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Object;", "Handler", "Lkotlin/Function1;", "", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class AbstractChannelKt
{
  public static final Symbol EMPTY = new Symbol("EMPTY");
  public static final Symbol ENQUEUE_FAILED = new Symbol("ENQUEUE_FAILED");
  public static final Symbol HANDLER_INVOKED = new Symbol("ON_CLOSE_HANDLER_INVOKED");
  public static final Symbol OFFER_FAILED;
  public static final Symbol OFFER_SUCCESS = new Symbol("OFFER_SUCCESS");
  public static final Symbol POLL_FAILED;
  public static final int RECEIVE_RESULT = 1;
  public static final int RECEIVE_THROWS_ON_CLOSE = 0;
  
  static
  {
    OFFER_FAILED = new Symbol("OFFER_FAILED");
    POLL_FAILED = new Symbol("POLL_FAILED");
  }
  
  private static final <E> Object toResult(Object paramObject)
  {
    if ((paramObject instanceof Closed)) {
      paramObject = ChannelResult.Companion.closed-JP2dKIU(((Closed)paramObject).closeCause);
    } else {
      paramObject = ChannelResult.Companion.success-JP2dKIU(paramObject);
    }
    return paramObject;
  }
  
  private static final <E> Object toResult(Closed<?> paramClosed)
  {
    return ChannelResult.Companion.closed-JP2dKIU(paramClosed.closeCause);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/AbstractChannelKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */