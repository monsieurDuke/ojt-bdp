package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000:\n\000\n\002\020\001\n\000\n\002\020\016\n\000\n\002\020\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\006\n\002\020\013\n\002\b\002\032\t\020\000\032\0020\001H\b\032\021\020\000\032\0020\0012\006\020\002\032\0020\003H\b\0323\020\004\032\0020\0052\006\020\006\032\0020\0072\022\020\b\032\016\022\004\022\0020\007\022\004\022\0020\0050\tH\bø\001\000\002\b\n\006\b\001\022\002\020\002\0322\020\n\032\002H\013\"\004\b\000\020\0132\f\020\f\032\b\022\004\022\002H\0130\rH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\016\032K\020\017\032\002H\013\"\004\b\000\020\020\"\004\b\001\020\0132\006\020\021\032\002H\0202\027\020\f\032\023\022\004\022\002H\020\022\004\022\002H\0130\t¢\006\002\b\022H\bø\001\000\002\n\n\b\b\001\022\002\020\002 \001¢\006\002\020\023\032<\020\024\032\002H\020\"\004\b\000\020\020*\002H\0202\022\020\f\032\016\022\004\022\002H\020\022\004\022\0020\0050\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\032A\020\025\032\002H\020\"\004\b\000\020\020*\002H\0202\027\020\f\032\023\022\004\022\002H\020\022\004\022\0020\0050\t¢\006\002\b\022H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\032B\020\026\032\002H\013\"\004\b\000\020\020\"\004\b\001\020\013*\002H\0202\022\020\f\032\016\022\004\022\002H\020\022\004\022\002H\0130\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\032G\020\n\032\002H\013\"\004\b\000\020\020\"\004\b\001\020\013*\002H\0202\027\020\f\032\023\022\004\022\002H\020\022\004\022\002H\0130\t¢\006\002\b\022H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\032>\020\027\032\004\030\001H\020\"\004\b\000\020\020*\002H\0202\022\020\030\032\016\022\004\022\002H\020\022\004\022\0020\0310\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\032>\020\032\032\004\030\001H\020\"\004\b\000\020\020*\002H\0202\022\020\030\032\016\022\004\022\002H\020\022\004\022\0020\0310\tH\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\023\002\007\n\005\b20\001¨\006\033"}, d2={"TODO", "", "reason", "", "repeat", "", "times", "", "action", "Lkotlin/Function1;", "run", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "with", "T", "receiver", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "also", "apply", "let", "takeIf", "predicate", "", "takeUnless", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/StandardKt")
class StandardKt__StandardKt
{
  private static final Void TODO()
  {
    throw new NotImplementedError(null, 1, null);
  }
  
  private static final Void TODO(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "reason");
    throw new NotImplementedError("An operation is not implemented: " + paramString);
  }
  
  private static final <T> T also(T paramT, Function1<? super T, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramFunction1.invoke(paramT);
    return paramT;
  }
  
  private static final <T> T apply(T paramT, Function1<? super T, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramFunction1.invoke(paramT);
    return paramT;
  }
  
  private static final <T, R> R let(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    return (R)paramFunction1.invoke(paramT);
  }
  
  private static final void repeat(int paramInt, Function1<? super Integer, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    for (int i = 0; i < paramInt; i++) {
      paramFunction1.invoke(Integer.valueOf(i));
    }
  }
  
  private static final <T, R> R run(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    return (R)paramFunction1.invoke(paramT);
  }
  
  private static final <R> R run(Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    return (R)paramFunction0.invoke();
  }
  
  private static final <T> T takeIf(T paramT, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (!((Boolean)paramFunction1.invoke(paramT)).booleanValue()) {
      paramT = null;
    }
    return paramT;
  }
  
  private static final <T> T takeUnless(T paramT, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "predicate");
    if (((Boolean)paramFunction1.invoke(paramT)).booleanValue()) {
      paramT = null;
    }
    return paramT;
  }
  
  private static final <T, R> R with(T paramT, Function1<? super T, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    return (R)paramFunction1.invoke(paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/StandardKt__StandardKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */