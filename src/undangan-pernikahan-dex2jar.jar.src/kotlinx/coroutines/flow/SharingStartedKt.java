package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.time.Duration;

@Metadata(d1={"\000\024\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\032+\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\0042\b\b\002\020\005\032\0020\004ø\001\000ø\001\001¢\006\004\b\006\020\007\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\b"}, d2={"WhileSubscribed", "Lkotlinx/coroutines/flow/SharingStarted;", "Lkotlinx/coroutines/flow/SharingStarted$Companion;", "stopTimeout", "Lkotlin/time/Duration;", "replayExpiration", "WhileSubscribed-5qebJ5I", "(Lkotlinx/coroutines/flow/SharingStarted$Companion;JJ)Lkotlinx/coroutines/flow/SharingStarted;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SharingStartedKt
{
  public static final SharingStarted WhileSubscribed-5qebJ5I(SharingStarted.Companion paramCompanion, long paramLong1, long paramLong2)
  {
    return (SharingStarted)new StartedWhileSubscribed(Duration.getInWholeMilliseconds-impl(paramLong1), Duration.getInWholeMilliseconds-impl(paramLong2));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/SharingStartedKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */