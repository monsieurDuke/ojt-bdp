package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(d1={"\000\022\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\032\"\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\001H\000\032\024\020\004\032\0020\0052\n\020\006\032\006\022\002\b\0030\001H\000\032\024\020\007\032\0020\0052\n\020\006\032\006\022\002\b\0030\001H\000¨\006\b"}, d2={"probeCoroutineCreated", "Lkotlin/coroutines/Continuation;", "T", "completion", "probeCoroutineResumed", "", "frame", "probeCoroutineSuspended", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DebugProbesKt
{
  public static final <T> Continuation<T> probeCoroutineCreated(Continuation<? super T> paramContinuation)
  {
    return DebugProbesImpl.INSTANCE.probeCoroutineCreated$kotlinx_coroutines_core(paramContinuation);
  }
  
  public static final void probeCoroutineResumed(Continuation<?> paramContinuation)
  {
    DebugProbesImpl.INSTANCE.probeCoroutineResumed$kotlinx_coroutines_core(paramContinuation);
  }
  
  public static final void probeCoroutineSuspended(Continuation<?> paramContinuation)
  {
    DebugProbesImpl.INSTANCE.probeCoroutineSuspended$kotlinx_coroutines_core(paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/DebugProbesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */