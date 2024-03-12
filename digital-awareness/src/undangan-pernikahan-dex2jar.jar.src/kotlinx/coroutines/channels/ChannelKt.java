package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(d1={"\0004\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\020\003\n\002\030\002\n\002\b\f\032\036\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\b\b\002\020\003\032\0020\004H\007\032>\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\b\b\002\020\003\032\0020\0042\b\b\002\020\005\032\0020\0062\026\b\002\020\007\032\020\022\004\022\002H\002\022\004\022\0020\t\030\0010\b\032X\020\n\032\002H\013\"\004\b\000\020\013*\b\022\004\022\002H\0130\f2#\020\r\032\037\022\025\022\023\030\0010\016¢\006\f\b\017\022\b\b\020\022\004\b\b(\021\022\004\022\002H\0130\bH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\004\b\022\020\023\032^\020\024\032\b\022\004\022\002H\0130\f\"\004\b\000\020\013*\b\022\004\022\002H\0130\f2#\020\025\032\037\022\025\022\023\030\0010\016¢\006\f\b\017\022\b\b\020\022\004\b\b(\021\022\004\022\0020\t0\bH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\004\b\026\020\023\032^\020\r\032\b\022\004\022\002H\0130\f\"\004\b\000\020\013*\b\022\004\022\002H\0130\f2#\020\025\032\037\022\025\022\023\030\0010\016¢\006\f\b\017\022\b\b\020\022\004\b\b(\021\022\004\022\0020\t0\bH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\004\b\027\020\023\032\\\020\030\032\b\022\004\022\002H\0130\f\"\004\b\000\020\013*\b\022\004\022\002H\0130\f2!\020\025\032\035\022\023\022\021H\013¢\006\f\b\017\022\b\b\020\022\004\b\b(\031\022\004\022\0020\t0\bH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \000¢\006\004\b\032\020\023\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\033"}, d2={"Channel", "Lkotlinx/coroutines/channels/Channel;", "E", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "onUndeliveredElement", "Lkotlin/Function1;", "", "getOrElse", "T", "Lkotlinx/coroutines/channels/ChannelResult;", "onFailure", "", "Lkotlin/ParameterName;", "name", "exception", "getOrElse-WpGqRn0", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onClosed", "action", "onClosed-WpGqRn0", "onFailure-WpGqRn0", "onSuccess", "value", "onSuccess-WpGqRn0", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ChannelKt
{
  public static final <E> Channel<E> Channel(int paramInt, BufferOverflow paramBufferOverflow, Function1<? super E, Unit> paramFunction1)
  {
    int j = 1;
    int i = 1;
    switch (paramInt)
    {
    default: 
      if ((paramInt == 1) && (paramBufferOverflow == BufferOverflow.DROP_OLDEST)) {
        paramBufferOverflow = (AbstractChannel)new ConflatedChannel(paramFunction1);
      }
      break;
    case 2147483647: 
      paramBufferOverflow = (Channel)new LinkedListChannel(paramFunction1);
      break;
    case 0: 
      if (paramBufferOverflow == BufferOverflow.SUSPEND) {
        paramBufferOverflow = (AbstractChannel)new RendezvousChannel(paramFunction1);
      } else {
        paramBufferOverflow = (AbstractChannel)new ArrayChannel(1, paramBufferOverflow, paramFunction1);
      }
      paramBufferOverflow = (Channel)paramBufferOverflow;
      break;
    case -1: 
      if (paramBufferOverflow == BufferOverflow.SUSPEND) {
        paramInt = i;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0) {
        return (Channel)new ConflatedChannel(paramFunction1);
      }
      throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
    case -2: 
      paramInt = j;
      if (paramBufferOverflow == BufferOverflow.SUSPEND) {
        paramInt = Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core();
      }
      paramBufferOverflow = (Channel)new ArrayChannel(paramInt, paramBufferOverflow, paramFunction1);
      break;
    }
    paramBufferOverflow = (AbstractChannel)new ArrayChannel(paramInt, paramBufferOverflow, paramFunction1);
    paramBufferOverflow = (Channel)paramBufferOverflow;
    return paramBufferOverflow;
  }
  
  public static final <T> T getOrElse-WpGqRn0(Object paramObject, Function1<? super Throwable, ? extends T> paramFunction1)
  {
    if ((paramObject instanceof ChannelResult.Failed)) {
      paramObject = paramFunction1.invoke(ChannelResult.exceptionOrNull-impl(paramObject));
    }
    return (T)paramObject;
  }
  
  public static final <T> Object onClosed-WpGqRn0(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    if ((paramObject instanceof ChannelResult.Closed)) {
      paramFunction1.invoke(ChannelResult.exceptionOrNull-impl(paramObject));
    }
    return paramObject;
  }
  
  public static final <T> Object onFailure-WpGqRn0(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    if ((paramObject instanceof ChannelResult.Failed)) {
      paramFunction1.invoke(ChannelResult.exceptionOrNull-impl(paramObject));
    }
    return paramObject;
  }
  
  public static final <T> Object onSuccess-WpGqRn0(Object paramObject, Function1<? super T, Unit> paramFunction1)
  {
    if (!(paramObject instanceof ChannelResult.Failed)) {
      paramFunction1.invoke(paramObject);
    }
    return paramObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ChannelKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */