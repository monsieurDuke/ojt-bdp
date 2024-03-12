package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(d1={"\000.\n\000\n\002\030\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\020\000\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\003\032F\020\000\032\b\022\004\022\0020\0020\001\"\004\b\000\020\0032\f\020\004\032\b\022\004\022\002H\0030\0012\034\b\004\020\005\032\026\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\006H\b¢\006\002\b\b\032D\020\t\032\b\022\004\022\0020\0020\001\"\004\b\000\020\003*\030\b\001\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\0062\f\020\004\032\b\022\004\022\002H\0030\001H\007ø\001\000¢\006\002\020\n\032]\020\t\032\b\022\004\022\0020\0020\001\"\004\b\000\020\013\"\004\b\001\020\003*#\b\001\022\004\022\002H\013\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\f¢\006\002\b\r2\006\020\016\032\002H\0132\f\020\004\032\b\022\004\022\002H\0030\001H\007ø\001\000¢\006\002\020\017\032\036\020\020\032\b\022\004\022\002H\0030\001\"\004\b\000\020\003*\b\022\004\022\002H\0030\001H\007\032A\020\021\032\004\030\0010\007\"\004\b\000\020\003*\030\b\001\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\0062\f\020\004\032\b\022\004\022\002H\0030\001H\bø\001\000¢\006\002\020\022\032Z\020\021\032\004\030\0010\007\"\004\b\000\020\013\"\004\b\001\020\003*#\b\001\022\004\022\002H\013\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\f¢\006\002\b\r2\006\020\016\032\002H\0132\f\020\004\032\b\022\004\022\002H\0030\001H\bø\001\000¢\006\002\020\023\032n\020\021\032\004\030\0010\007\"\004\b\000\020\013\"\004\b\001\020\024\"\004\b\002\020\003*)\b\001\022\004\022\002H\013\022\004\022\002H\024\022\n\022\b\022\004\022\002H\0030\001\022\006\022\004\030\0010\0070\025¢\006\002\b\r2\006\020\016\032\002H\0132\006\020\026\032\002H\0242\f\020\004\032\b\022\004\022\002H\0030\001H\bø\001\000¢\006\002\020\027\002\004\n\002\b\031¨\006\030"}, d2={"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "P", "Lkotlin/Function3;", "param", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/coroutines/intrinsics/IntrinsicsKt")
class IntrinsicsKt__IntrinsicsJvmKt
{
  private static final <T> Continuation<Unit> createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> paramContinuation, final Function1<? super Continuation<? super T>, ? extends Object> paramFunction1)
  {
    CoroutineContext localCoroutineContext = paramContinuation.getContext();
    if (localCoroutineContext == EmptyCoroutineContext.INSTANCE) {
      paramContinuation = (Continuation)new RestrictedContinuationImpl(paramContinuation)
      {
        private int label;
        
        protected Object invokeSuspend(Object paramAnonymousObject)
        {
          switch (this.label)
          {
          default: 
            throw new IllegalStateException("This coroutine had already completed".toString());
          case 1: 
            this.label = 2;
            ResultKt.throwOnFailure(paramAnonymousObject);
            break;
          case 0: 
            this.label = 1;
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = paramFunction1.invoke(this);
          }
          return paramAnonymousObject;
        }
      };
    } else {
      paramContinuation = (Continuation)new ContinuationImpl(paramContinuation, localCoroutineContext)
      {
        private int label;
        
        protected Object invokeSuspend(Object paramAnonymousObject)
        {
          switch (this.label)
          {
          default: 
            throw new IllegalStateException("This coroutine had already completed".toString());
          case 1: 
            this.label = 2;
            ResultKt.throwOnFailure(paramAnonymousObject);
            break;
          case 0: 
            this.label = 1;
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = paramFunction1.invoke(this);
          }
          return paramAnonymousObject;
        }
      };
    }
    return paramContinuation;
  }
  
  public static final <T> Continuation<Unit> createCoroutineUnintercepted(final Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "<this>");
    Intrinsics.checkNotNullParameter(paramContinuation, "completion");
    paramContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    if ((paramFunction1 instanceof BaseContinuationImpl))
    {
      paramFunction1 = ((BaseContinuationImpl)paramFunction1).create(paramContinuation);
    }
    else
    {
      CoroutineContext localCoroutineContext = paramContinuation.getContext();
      if (localCoroutineContext == EmptyCoroutineContext.INSTANCE) {
        paramFunction1 = (Continuation)new RestrictedContinuationImpl(paramContinuation)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            switch (this.label)
            {
            default: 
              throw new IllegalStateException("This coroutine had already completed".toString());
            case 1: 
              this.label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 0: 
              this.label = 1;
              ResultKt.throwOnFailure(paramAnonymousObject);
              paramAnonymousObject = (Continuation)this;
              Intrinsics.checkNotNull(paramFunction1, "null cannot be cast to non-null type kotlin.Function1<kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-0>, kotlin.Any?>");
              paramAnonymousObject = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(paramAnonymousObject);
            }
            return paramAnonymousObject;
          }
        };
      } else {
        paramFunction1 = (Continuation)new ContinuationImpl(paramContinuation, localCoroutineContext)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            switch (this.label)
            {
            default: 
              throw new IllegalStateException("This coroutine had already completed".toString());
            case 1: 
              this.label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 0: 
              this.label = 1;
              ResultKt.throwOnFailure(paramAnonymousObject);
              paramAnonymousObject = (Continuation)this;
              Intrinsics.checkNotNull(paramFunction1, "null cannot be cast to non-null type kotlin.Function1<kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-0>, kotlin.Any?>");
              paramAnonymousObject = ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(paramAnonymousObject);
            }
            return paramAnonymousObject;
          }
        };
      }
    }
    return paramFunction1;
  }
  
  public static final <R, T> Continuation<Unit> createCoroutineUnintercepted(final Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, final R paramR, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramFunction2, "<this>");
    Intrinsics.checkNotNullParameter(paramContinuation, "completion");
    Continuation localContinuation = DebugProbesKt.probeCoroutineCreated(paramContinuation);
    if ((paramFunction2 instanceof BaseContinuationImpl))
    {
      paramFunction2 = ((BaseContinuationImpl)paramFunction2).create(paramR, localContinuation);
    }
    else
    {
      paramContinuation = localContinuation.getContext();
      if (paramContinuation == EmptyCoroutineContext.INSTANCE) {
        paramFunction2 = (Continuation)new RestrictedContinuationImpl(localContinuation)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            switch (this.label)
            {
            default: 
              throw new IllegalStateException("This coroutine had already completed".toString());
            case 1: 
              this.label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 0: 
              this.label = 1;
              ResultKt.throwOnFailure(paramAnonymousObject);
              paramAnonymousObject = (Continuation)this;
              Intrinsics.checkNotNull(paramFunction2, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-1>, kotlin.Any?>");
              paramAnonymousObject = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramR, paramAnonymousObject);
            }
            return paramAnonymousObject;
          }
        };
      } else {
        paramFunction2 = (Continuation)new ContinuationImpl(localContinuation, paramContinuation)
        {
          private int label;
          
          protected Object invokeSuspend(Object paramAnonymousObject)
          {
            switch (this.label)
            {
            default: 
              throw new IllegalStateException("This coroutine had already completed".toString());
            case 1: 
              this.label = 2;
              ResultKt.throwOnFailure(paramAnonymousObject);
              break;
            case 0: 
              this.label = 1;
              ResultKt.throwOnFailure(paramAnonymousObject);
              paramAnonymousObject = (Continuation)this;
              Intrinsics.checkNotNull(paramFunction2, "null cannot be cast to non-null type kotlin.Function2<R of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-1, kotlin.coroutines.Continuation<T of kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted$lambda-1>, kotlin.Any?>");
              paramAnonymousObject = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramR, paramAnonymousObject);
            }
            return paramAnonymousObject;
          }
        };
      }
    }
    return paramFunction2;
  }
  
  public static final <T> Continuation<T> intercepted(Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramContinuation, "<this>");
    Object localObject;
    if ((paramContinuation instanceof ContinuationImpl)) {
      localObject = (ContinuationImpl)paramContinuation;
    } else {
      localObject = null;
    }
    if (localObject != null)
    {
      Continuation localContinuation = ((ContinuationImpl)localObject).intercepted();
      localObject = localContinuation;
      if (localContinuation != null) {}
    }
    else
    {
      localObject = paramContinuation;
    }
    return (Continuation<T>)localObject;
  }
  
  private static final <T> Object startCoroutineUninterceptedOrReturn(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "<this>");
    Intrinsics.checkNotNullParameter(paramContinuation, "completion");
    return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction1, 1)).invoke(paramContinuation);
  }
  
  private static final <R, T> Object startCoroutineUninterceptedOrReturn(Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2, R paramR, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramFunction2, "<this>");
    Intrinsics.checkNotNullParameter(paramContinuation, "completion");
    return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction2, 2)).invoke(paramR, paramContinuation);
  }
  
  private static final <R, P, T> Object startCoroutineUninterceptedOrReturn(Function3<? super R, ? super P, ? super Continuation<? super T>, ? extends Object> paramFunction3, R paramR, P paramP, Continuation<? super T> paramContinuation)
  {
    Intrinsics.checkNotNullParameter(paramFunction3, "<this>");
    Intrinsics.checkNotNullParameter(paramContinuation, "completion");
    return ((Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity(paramFunction3, 3)).invoke(paramR, paramP, paramContinuation);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */