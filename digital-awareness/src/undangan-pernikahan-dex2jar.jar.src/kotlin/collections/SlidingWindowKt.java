package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000*\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\002\n\002\020(\n\002\020 \n\002\b\003\n\002\020\013\n\002\b\002\n\002\030\002\n\000\032\030\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\003H\000\032H\020\005\032\016\022\n\022\b\022\004\022\002H\b0\0070\006\"\004\b\000\020\b2\f\020\t\032\b\022\004\022\002H\b0\0062\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\n\032\0020\0132\006\020\f\032\0020\013H\000\032D\020\r\032\016\022\n\022\b\022\004\022\002H\b0\0070\016\"\004\b\000\020\b*\b\022\004\022\002H\b0\0162\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\n\032\0020\0132\006\020\f\032\0020\013H\000¨\006\017"}, d2={"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class SlidingWindowKt
{
  public static final void checkWindowSizeStep(int paramInt1, int paramInt2)
  {
    int i;
    if ((paramInt1 > 0) && (paramInt2 > 0)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      String str;
      if (paramInt1 != paramInt2) {
        str = "Both size " + paramInt1 + " and step " + paramInt2 + " must be greater than zero.";
      } else {
        str = "size " + paramInt1 + " must be greater than zero.";
      }
      throw new IllegalArgumentException(str.toString());
    }
  }
  
  public static final <T> Iterator<List<T>> windowedIterator(final Iterator<? extends T> paramIterator, int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    Intrinsics.checkNotNullParameter(paramIterator, "iterator");
    if (!paramIterator.hasNext()) {
      return (Iterator)EmptyIterator.INSTANCE;
    }
    SequencesKt.iterator((Function2)new RestrictedSuspendLambda(paramInt1, paramInt2)
    {
      final int $size;
      int I$0;
      private Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$size, paramInt2, paramIterator, paramBoolean2, paramBoolean1, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super List<? extends T>> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        Object localObject2;
        Object localObject6;
        Object localObject4;
        Object localObject3;
        Object localObject5;
        int i;
        Object localObject7;
        int j;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 5: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 4: 
          localObject2 = (RingBuffer)this.L$1;
          localObject6 = (SequenceScope)this.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject4 = this;
          localObject3 = localObject1;
          localObject1 = localObject2;
          break;
        case 3: 
          localObject3 = this;
          localObject4 = (Iterator)((1)localObject3).L$2;
          localObject2 = (RingBuffer)((1)localObject3).L$1;
          localObject5 = (SequenceScope)((1)localObject3).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject2;
          break;
        case 2: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 1: 
          i = this.I$0;
          localObject6 = (Iterator)this.L$2;
          localObject2 = (ArrayList)this.L$1;
          localObject7 = (SequenceScope)this.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = this;
          localObject4 = localObject1;
          localObject1 = localObject2;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject3 = this;
          paramAnonymousObject = (SequenceScope)((1)localObject3).L$0;
          i = RangesKt.coerceAtMost(((1)localObject3).$size, 1024);
          j = paramInt2 - ((1)localObject3).$size;
          if (j < 0) {
            break label525;
          }
          localObject4 = new ArrayList(i);
          i = 0;
          localObject6 = paramIterator;
          localObject2 = paramAnonymousObject;
          paramAnonymousObject = localObject4;
          localObject5 = localObject1;
        }
        while (((Iterator)localObject6).hasNext())
        {
          localObject1 = ((Iterator)localObject6).next();
          if (i > 0)
          {
            i--;
          }
          else
          {
            ((ArrayList)paramAnonymousObject).add(localObject1);
            if (((ArrayList)paramAnonymousObject).size() == ((1)localObject3).$size)
            {
              Continuation localContinuation = (Continuation)localObject3;
              ((1)localObject3).L$0 = localObject2;
              ((1)localObject3).L$1 = paramAnonymousObject;
              ((1)localObject3).L$2 = localObject6;
              ((1)localObject3).I$0 = j;
              ((1)localObject3).label = 1;
              localObject4 = localObject5;
              i = j;
              localObject1 = paramAnonymousObject;
              localObject7 = localObject2;
              if (((SequenceScope)localObject2).yield(paramAnonymousObject, localContinuation) == localObject5) {
                return localObject5;
              }
              if (paramBoolean2)
              {
                ((ArrayList)localObject1).clear();
                paramAnonymousObject = localObject1;
              }
              else
              {
                paramAnonymousObject = new ArrayList(((1)localObject3).$size);
              }
              int k = i;
              localObject5 = localObject4;
              j = i;
              i = k;
              localObject2 = localObject7;
            }
          }
        }
        if (((true ^ ((Collection)paramAnonymousObject).isEmpty())) && ((paramBoolean1) || (((ArrayList)paramAnonymousObject).size() == ((1)localObject3).$size)))
        {
          localObject1 = (Continuation)localObject3;
          ((1)localObject3).L$0 = null;
          ((1)localObject3).L$1 = null;
          ((1)localObject3).L$2 = null;
          ((1)localObject3).label = 2;
          if (((SequenceScope)localObject2).yield(paramAnonymousObject, (Continuation)localObject1) == localObject5) {
            return localObject5;
          }
          break label918;
          label525:
          localObject5 = new RingBuffer(i);
          localObject4 = paramIterator;
          localObject2 = paramAnonymousObject;
          paramAnonymousObject = localObject5;
          while (((Iterator)localObject4).hasNext())
          {
            ((RingBuffer)paramAnonymousObject).add(((Iterator)localObject4).next());
            if (((RingBuffer)paramAnonymousObject).isFull())
            {
              i = ((RingBuffer)paramAnonymousObject).size();
              j = ((1)localObject3).$size;
              if (i < j)
              {
                paramAnonymousObject = ((RingBuffer)paramAnonymousObject).expanded(j);
              }
              else
              {
                if (paramBoolean2) {
                  localObject6 = (List)paramAnonymousObject;
                } else {
                  localObject6 = (List)new ArrayList((Collection)paramAnonymousObject);
                }
                localObject7 = (Continuation)localObject3;
                ((1)localObject3).L$0 = localObject2;
                ((1)localObject3).L$1 = paramAnonymousObject;
                ((1)localObject3).L$2 = localObject4;
                ((1)localObject3).label = 3;
                localObject5 = localObject2;
                if (((SequenceScope)localObject2).yield(localObject6, (Continuation)localObject7) == localObject1) {
                  return localObject1;
                }
                ((RingBuffer)paramAnonymousObject).removeFirst(paramInt2);
                localObject2 = localObject5;
              }
            }
          }
          if (paramBoolean1)
          {
            localObject4 = paramAnonymousObject;
            paramAnonymousObject = localObject2;
            localObject2 = localObject1;
            localObject1 = localObject4;
            localObject4 = localObject3;
            while (((RingBuffer)localObject1).size() > paramInt2)
            {
              if (paramBoolean2) {
                localObject5 = (List)localObject1;
              } else {
                localObject5 = (List)new ArrayList((Collection)localObject1);
              }
              localObject7 = (Continuation)localObject4;
              ((1)localObject4).L$0 = paramAnonymousObject;
              ((1)localObject4).L$1 = localObject1;
              ((1)localObject4).L$2 = null;
              ((1)localObject4).label = 4;
              localObject3 = localObject2;
              localObject6 = paramAnonymousObject;
              if (((SequenceScope)paramAnonymousObject).yield(localObject5, (Continuation)localObject7) == localObject2) {
                return localObject2;
              }
              ((RingBuffer)localObject1).removeFirst(paramInt2);
              localObject2 = localObject3;
              paramAnonymousObject = localObject6;
            }
            if ((true ^ ((Collection)localObject1).isEmpty()))
            {
              localObject3 = (Continuation)localObject4;
              ((1)localObject4).L$0 = null;
              ((1)localObject4).L$1 = null;
              ((1)localObject4).L$2 = null;
              ((1)localObject4).label = 5;
              if (((SequenceScope)paramAnonymousObject).yield(localObject1, (Continuation)localObject3) == localObject2) {
                return localObject2;
              }
            }
          }
        }
        label918:
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final <T> Sequence<List<T>> windowedSequence(Sequence<? extends T> paramSequence, final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    checkWindowSizeStep(paramInt1, paramInt2);
    (Sequence)new Sequence()
    {
      final Sequence $this_windowedSequence$inlined;
      
      public Iterator<List<? extends T>> iterator()
      {
        return SlidingWindowKt.windowedIterator(this.$this_windowedSequence$inlined.iterator(), paramInt1, paramInt2, paramBoolean1, paramBoolean2);
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/SlidingWindowKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */