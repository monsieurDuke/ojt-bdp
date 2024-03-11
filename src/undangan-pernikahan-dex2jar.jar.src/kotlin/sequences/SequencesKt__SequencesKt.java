package kotlin.sequences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.random.Random;

@Metadata(d1={"\000L\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020(\n\002\b\006\n\002\030\002\n\002\020\b\n\002\030\002\n\000\n\002\020\000\n\002\b\006\n\002\020\021\n\002\b\005\n\002\020\034\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\020 \n\000\032.\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\024\b\004\020\003\032\016\022\n\022\b\022\004\022\002H\0020\0050\004H\bø\001\000\032\022\020\006\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002\032b\020\007\032\b\022\004\022\002H\b0\001\"\004\b\000\020\002\"\004\b\001\020\t\"\004\b\002\020\b2\f\020\n\032\b\022\004\022\002H\0020\0012\030\020\013\032\024\022\004\022\0020\r\022\004\022\002H\002\022\004\022\002H\t0\f2\030\020\003\032\024\022\004\022\002H\t\022\n\022\b\022\004\022\002H\b0\0050\016H\000\032&\020\017\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\0202\016\020\021\032\n\022\006\022\004\030\001H\0020\004\032<\020\017\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\0202\016\020\022\032\n\022\006\022\004\030\001H\0020\0042\024\020\021\032\020\022\004\022\002H\002\022\006\022\004\030\001H\0020\016\032=\020\017\032\b\022\004\022\002H\0020\001\"\b\b\000\020\002*\0020\0202\b\020\023\032\004\030\001H\0022\024\020\021\032\020\022\004\022\002H\002\022\006\022\004\030\001H\0020\016H\007¢\006\002\020\024\032+\020\025\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\022\020\026\032\n\022\006\b\001\022\002H\0020\027\"\002H\002¢\006\002\020\030\032\034\020\031\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\005\032\034\020\032\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\001\032C\020\033\032\b\022\004\022\002H\b0\001\"\004\b\000\020\002\"\004\b\001\020\b*\b\022\004\022\002H\0020\0012\030\020\003\032\024\022\004\022\002H\002\022\n\022\b\022\004\022\002H\b0\0050\016H\002¢\006\002\b\034\032)\020\033\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0350\001H\007¢\006\002\b\036\032\"\020\033\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0010\001\0322\020\037\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\022\020 \032\016\022\n\022\b\022\004\022\002H\0020\0010\004H\007\032!\020!\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\n\022\004\022\002H\002\030\0010\001H\b\032\036\020\"\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\001H\007\032&\020\"\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020#\032\0020$H\007\032@\020%\032\032\022\n\022\b\022\004\022\002H\0020'\022\n\022\b\022\004\022\002H\b0'0&\"\004\b\000\020\002\"\004\b\001\020\b*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\b0&0\001\002\007\n\005\b20\001¨\006("}, d2={"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "flatMapIndexed", "R", "C", "source", "transform", "Lkotlin/Function2;", "", "Lkotlin/Function1;", "generateSequence", "", "nextFunction", "seedFunction", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "shuffled", "random", "Lkotlin/random/Random;", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/sequences/SequencesKt")
class SequencesKt__SequencesKt
  extends SequencesKt__SequencesJVMKt
{
  private static final <T> Sequence<T> Sequence(Function0<? extends Iterator<? extends T>> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "iterator");
    (Sequence)new Sequence()
    {
      final Function0<Iterator<T>> $iterator;
      
      public Iterator<T> iterator()
      {
        return (Iterator)this.$iterator.invoke();
      }
    };
  }
  
  public static final <T> Sequence<T> asSequence(Iterator<? extends T> paramIterator)
  {
    Intrinsics.checkNotNullParameter(paramIterator, "<this>");
    SequencesKt.constrainOnce((Sequence)new Sequence()
    {
      final Iterator $this_asSequence$inlined;
      
      public Iterator<T> iterator()
      {
        return this.$this_asSequence$inlined;
      }
    });
  }
  
  public static final <T> Sequence<T> constrainOnce(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    if (!(paramSequence instanceof ConstrainedOnceSequence)) {
      paramSequence = (Sequence)new ConstrainedOnceSequence(paramSequence);
    }
    return paramSequence;
  }
  
  public static final <T> Sequence<T> emptySequence()
  {
    return (Sequence)EmptySequence.INSTANCE;
  }
  
  public static final <T, C, R> Sequence<R> flatMapIndexed(Sequence<? extends T> paramSequence, final Function2<? super Integer, ? super T, ? extends C> paramFunction2, final Function1<? super C, ? extends Iterator<? extends R>> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "source");
    Intrinsics.checkNotNullParameter(paramFunction2, "transform");
    Intrinsics.checkNotNullParameter(paramFunction1, "iterator");
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramSequence, paramFunction2)
    {
      final Sequence<T> $source;
      int I$0;
      private Object L$0;
      Object L$1;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$source, paramFunction2, paramFunction1, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super R> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        Object localObject1;
        int i;
        Object localObject2;
        SequenceScope localSequenceScope;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          localObject1 = this;
          i = ((1)localObject1).I$0;
          localObject2 = (Iterator)((1)localObject1).L$1;
          localSequenceScope = (SequenceScope)((1)localObject1).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          localObject1 = localObject2;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = this;
          localSequenceScope = (SequenceScope)((1)paramAnonymousObject).L$0;
          localObject1 = ((1)paramAnonymousObject).$source.iterator();
          int j;
          for (i = 0; ((Iterator)localObject1).hasNext(); i = j)
          {
            localObject2 = ((Iterator)localObject1).next();
            Object localObject4 = paramFunction2;
            j = i + 1;
            if (i < 0) {
              CollectionsKt.throwIndexOverflow();
            }
            localObject2 = ((Function2)localObject4).invoke(Boxing.boxInt(i), localObject2);
            localObject4 = (Iterator)paramFunction1.invoke(localObject2);
            localObject2 = (Continuation)paramAnonymousObject;
            ((1)paramAnonymousObject).L$0 = localSequenceScope;
            ((1)paramAnonymousObject).L$1 = localObject1;
            ((1)paramAnonymousObject).I$0 = j;
            ((1)paramAnonymousObject).label = 1;
            if (localSequenceScope.yieldAll((Iterator)localObject4, (Continuation)localObject2) == localObject3) {
              return localObject3;
            }
          }
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final <T> Sequence<T> flatten(Sequence<? extends Sequence<? extends T>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    return flatten$SequencesKt__SequencesKt(paramSequence, (Function1)flatten.1.INSTANCE);
  }
  
  private static final <T, R> Sequence<R> flatten$SequencesKt__SequencesKt(Sequence<? extends T> paramSequence, Function1<? super T, ? extends Iterator<? extends R>> paramFunction1)
  {
    if ((paramSequence instanceof TransformingSequence)) {
      return ((TransformingSequence)paramSequence).flatten$kotlin_stdlib(paramFunction1);
    }
    return (Sequence)new FlatteningSequence(paramSequence, (Function1)flatten.3.INSTANCE, paramFunction1);
  }
  
  public static final <T> Sequence<T> flattenSequenceOfIterable(Sequence<? extends Iterable<? extends T>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    return flatten$SequencesKt__SequencesKt(paramSequence, (Function1)flatten.2.INSTANCE);
  }
  
  public static final <T> Sequence<T> generateSequence(T paramT, Function1<? super T, ? extends T> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "nextFunction");
    if (paramT == null) {
      paramT = (Sequence)EmptySequence.INSTANCE;
    } else {
      paramT = (Sequence)new GeneratorSequence((Function0)new Lambda(paramT)
      {
        final T $seed;
        
        public final T invoke()
        {
          return (T)this.$seed;
        }
      }, paramFunction1);
    }
    return paramT;
  }
  
  public static final <T> Sequence<T> generateSequence(Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "nextFunction");
    SequencesKt.constrainOnce((Sequence)new GeneratorSequence(paramFunction0, (Function1)new Lambda(paramFunction0)
    {
      final Function0<T> $nextFunction;
      
      public final T invoke(T paramAnonymousT)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousT, "it");
        return (T)this.$nextFunction.invoke();
      }
    }));
  }
  
  public static final <T> Sequence<T> generateSequence(Function0<? extends T> paramFunction0, Function1<? super T, ? extends T> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "seedFunction");
    Intrinsics.checkNotNullParameter(paramFunction1, "nextFunction");
    return (Sequence)new GeneratorSequence(paramFunction0, paramFunction1);
  }
  
  public static final <T> Sequence<T> ifEmpty(Sequence<? extends T> paramSequence, final Function0<? extends Sequence<? extends T>> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramSequence, paramFunction0)
    {
      final Sequence<T> $this_ifEmpty;
      private Object L$0;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$this_ifEmpty, paramFunction0, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super T> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 2: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 1: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = (SequenceScope)this.L$0;
          localObject2 = this.$this_ifEmpty.iterator();
          if (!((Iterator)localObject2).hasNext()) {
            break label118;
          }
          localContinuation = (Continuation)this;
          this.label = 1;
          if (((SequenceScope)paramAnonymousObject).yieldAll((Iterator)localObject2, localContinuation) == localObject1) {
            return localObject1;
          }
          break;
        }
        break label155;
        label118:
        Object localObject2 = (Sequence)paramFunction0.invoke();
        Continuation localContinuation = (Continuation)this;
        this.label = 2;
        if (((SequenceScope)paramAnonymousObject).yieldAll((Sequence)localObject2, localContinuation) == localObject1) {
          return localObject1;
        }
        label155:
        return Unit.INSTANCE;
      }
    });
  }
  
  private static final <T> Sequence<T> orEmpty(Sequence<? extends T> paramSequence)
  {
    if (paramSequence == null) {
      paramSequence = SequencesKt.emptySequence();
    }
    return paramSequence;
  }
  
  public static final <T> Sequence<T> sequenceOf(T... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    int i;
    if (paramVarArgs.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramVarArgs = SequencesKt.emptySequence();
    } else {
      paramVarArgs = ArraysKt.asSequence(paramVarArgs);
    }
    return paramVarArgs;
  }
  
  public static final <T> Sequence<T> shuffled(Sequence<? extends T> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    return SequencesKt.shuffled(paramSequence, (Random)Random.Default);
  }
  
  public static final <T> Sequence<T> shuffled(Sequence<? extends T> paramSequence, final Random paramRandom)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    Intrinsics.checkNotNullParameter(paramRandom, "random");
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramSequence, paramRandom)
    {
      final Sequence<T> $this_shuffled;
      private Object L$0;
      Object L$1;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$this_shuffled, paramRandom, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super T> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        1 local1;
        List localList;
        Object localObject2;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 1: 
          local1 = this;
          localList = (List)local1.L$1;
          localObject2 = (SequenceScope)local1.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          local1 = this;
          paramAnonymousObject = (SequenceScope)local1.L$0;
          localList = SequencesKt.toMutableList(local1.$this_shuffled);
        }
        while ((((Collection)localList).isEmpty() ^ true))
        {
          int i = paramRandom.nextInt(localList.size());
          localObject2 = CollectionsKt.removeLast(localList);
          Object localObject1 = localObject2;
          if (i < localList.size()) {
            localObject1 = localList.set(i, localObject2);
          }
          Continuation localContinuation = (Continuation)local1;
          local1.L$0 = paramAnonymousObject;
          local1.L$1 = localList;
          local1.label = 1;
          localObject2 = paramAnonymousObject;
          if (((SequenceScope)paramAnonymousObject).yield(localObject1, localContinuation) == localObject3) {
            return localObject3;
          }
          paramAnonymousObject = localObject2;
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final <T, R> Pair<List<T>, List<R>> unzip(Sequence<? extends Pair<? extends T, ? extends R>> paramSequence)
  {
    Intrinsics.checkNotNullParameter(paramSequence, "<this>");
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator = paramSequence.iterator();
    while (localIterator.hasNext())
    {
      paramSequence = (Pair)localIterator.next();
      localArrayList2.add(paramSequence.getFirst());
      localArrayList1.add(paramSequence.getSecond());
    }
    return TuplesKt.to(localArrayList2, localArrayList1);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/sequences/SequencesKt__SequencesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */