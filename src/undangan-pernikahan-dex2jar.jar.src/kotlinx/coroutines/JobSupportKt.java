package kotlinx.coroutines;

import kotlin.Metadata;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\000\"\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\004\n\002\020\b\n\002\b\007\n\002\020\000\n\002\b\002\032\020\020\025\032\004\030\0010\026*\004\030\0010\026H\000\032\020\020\027\032\004\030\0010\026*\004\030\0010\026H\000\"\026\020\000\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\002\020\003\"\026\020\004\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\005\020\003\"\026\020\006\032\0020\0018\000X\004¢\006\b\n\000\022\004\b\007\020\003\"\026\020\b\032\0020\t8\002X\004¢\006\b\n\000\022\004\b\n\020\003\"\026\020\013\032\0020\t8\002X\004¢\006\b\n\000\022\004\b\f\020\003\"\016\020\r\032\0020\016XT¢\006\002\n\000\"\016\020\017\032\0020\016XT¢\006\002\n\000\"\026\020\020\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\021\020\003\"\026\020\022\032\0020\0018\002X\004¢\006\b\n\000\022\004\b\023\020\003\"\016\020\024\032\0020\016XT¢\006\002\n\000¨\006\030"}, d2={"COMPLETING_ALREADY", "Lkotlinx/coroutines/internal/Symbol;", "getCOMPLETING_ALREADY$annotations", "()V", "COMPLETING_RETRY", "getCOMPLETING_RETRY$annotations", "COMPLETING_WAITING_CHILDREN", "getCOMPLETING_WAITING_CHILDREN$annotations", "EMPTY_ACTIVE", "Lkotlinx/coroutines/Empty;", "getEMPTY_ACTIVE$annotations", "EMPTY_NEW", "getEMPTY_NEW$annotations", "FALSE", "", "RETRY", "SEALED", "getSEALED$annotations", "TOO_LATE_TO_CANCEL", "getTOO_LATE_TO_CANCEL$annotations", "TRUE", "boxIncomplete", "", "unboxState", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class JobSupportKt
{
  private static final Symbol COMPLETING_ALREADY = new Symbol("COMPLETING_ALREADY");
  private static final Symbol COMPLETING_RETRY;
  public static final Symbol COMPLETING_WAITING_CHILDREN = new Symbol("COMPLETING_WAITING_CHILDREN");
  private static final Empty EMPTY_ACTIVE = new Empty(true);
  private static final Empty EMPTY_NEW;
  private static final int FALSE = 0;
  private static final int RETRY = -1;
  private static final Symbol SEALED;
  private static final Symbol TOO_LATE_TO_CANCEL;
  private static final int TRUE = 1;
  
  static
  {
    COMPLETING_RETRY = new Symbol("COMPLETING_RETRY");
    TOO_LATE_TO_CANCEL = new Symbol("TOO_LATE_TO_CANCEL");
    SEALED = new Symbol("SEALED");
    EMPTY_NEW = new Empty(false);
  }
  
  public static final Object boxIncomplete(Object paramObject)
  {
    if ((paramObject instanceof Incomplete)) {
      paramObject = new IncompleteStateBox((Incomplete)paramObject);
    }
    return paramObject;
  }
  
  public static final Object unboxState(Object paramObject)
  {
    if ((paramObject instanceof IncompleteStateBox)) {
      localObject = (IncompleteStateBox)paramObject;
    } else {
      localObject = null;
    }
    if (localObject != null) {
      for (;;)
      {
        Incomplete localIncomplete = ((IncompleteStateBox)localObject).state;
        localObject = localIncomplete;
        if (localIncomplete != null) {
          break;
        }
      }
    }
    Object localObject = paramObject;
    return localObject;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/JobSupportKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */