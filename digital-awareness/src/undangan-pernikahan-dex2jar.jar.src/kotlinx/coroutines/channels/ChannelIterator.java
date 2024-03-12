package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(d1={"\000\024\n\002\030\002\n\000\n\002\020\000\n\000\n\002\020\013\n\002\b\005\bf\030\000*\006\b\000\020\001 \0012\0020\002J\021\020\003\032\0020\004H¦Bø\001\000¢\006\002\020\005J\016\020\006\032\0028\000H¦\002¢\006\002\020\007J\023\020\b\032\0028\000H@ø\001\000¢\006\004\b\006\020\005\002\004\n\002\b\031¨\006\t"}, d2={"Lkotlinx/coroutines/channels/ChannelIterator;", "E", "", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "next", "()Ljava/lang/Object;", "next0", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract interface ChannelIterator<E>
{
  public abstract Object hasNext(Continuation<? super Boolean> paramContinuation);
  
  public abstract E next();
  
  @Metadata(k=3, mv={1, 6, 0}, xi=48)
  public static final class DefaultImpls {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ChannelIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */