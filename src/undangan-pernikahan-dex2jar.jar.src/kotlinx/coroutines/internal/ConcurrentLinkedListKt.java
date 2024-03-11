package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\0008\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\t\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\b\n\002\b\003\032#\020\002\032\0028\000\"\016\b\000\020\001*\b\022\004\022\0028\0000\000*\0028\000H\000¢\006\004\b\002\020\003\032o\020\016\032\b\022\004\022\0028\0000\r\"\016\b\000\020\005*\b\022\004\022\0028\0000\004*\0028\0002\006\020\007\032\0020\00628\020\f\0324\022\023\022\0210\006¢\006\f\b\t\022\b\b\n\022\004\b\b(\007\022\025\022\023\030\0018\000¢\006\f\b\t\022\b\b\n\022\004\b\b(\013\022\004\022\0028\0000\bH\bø\001\000¢\006\004\b\016\020\017\"\032\020\021\032\0020\0208\002X\004¢\006\f\n\004\b\021\020\022\022\004\b\023\020\024\"\024\020\026\032\0020\0258\002XT¢\006\006\n\004\b\026\020\027\002\004\n\002\b\031¨\006\030"}, d2={"Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "N", "close", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "Lkotlinx/coroutines/internal/Segment;", "S", "", "id", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "prev", "createNewSegment", "Lkotlinx/coroutines/internal/SegmentOrClosed;", "findSegmentInternal", "(Lkotlinx/coroutines/internal/Segment;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "CLOSED", "Lkotlinx/coroutines/internal/Symbol;", "getCLOSED$annotations", "()V", "", "POINTERS_SHIFT", "I", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ConcurrentLinkedListKt
{
  private static final Symbol CLOSED = new Symbol("CLOSED");
  private static final int POINTERS_SHIFT = 16;
  
  public static final <N extends ConcurrentLinkedListNode<N>> N close(N paramN)
  {
    for (;;)
    {
      Object localObject = ConcurrentLinkedListNode.access$getNextOrClosed(paramN);
      if (localObject == access$getCLOSED$p()) {
        return paramN;
      }
      localObject = (ConcurrentLinkedListNode)localObject;
      if (localObject == null)
      {
        if (paramN.markAsClosed()) {
          return paramN;
        }
      }
      else {
        paramN = (N)localObject;
      }
    }
  }
  
  private static final <S extends Segment<S>> Object findSegmentInternal(S paramS, long paramLong, Function2<? super Long, ? super S, ? extends S> paramFunction2)
  {
    for (;;)
    {
      if ((paramS.getId() >= paramLong) && (!paramS.getRemoved())) {
        return SegmentOrClosed.constructor-impl(paramS);
      }
      Object localObject = ConcurrentLinkedListNode.access$getNextOrClosed((ConcurrentLinkedListNode)paramS);
      if (localObject == access$getCLOSED$p()) {
        return SegmentOrClosed.constructor-impl(access$getCLOSED$p());
      }
      localObject = (ConcurrentLinkedListNode)localObject;
      localObject = (Segment)localObject;
      if (localObject != null)
      {
        paramS = (S)localObject;
      }
      else
      {
        localObject = (Segment)paramFunction2.invoke(Long.valueOf(paramS.getId() + 1L), paramS);
        if (paramS.trySetNext((ConcurrentLinkedListNode)localObject))
        {
          if (paramS.getRemoved()) {
            paramS.remove();
          }
          paramS = (S)localObject;
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ConcurrentLinkedListKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */