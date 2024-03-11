package kotlinx.coroutines.internal;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CopyableThrowable;
import kotlinx.coroutines.DebugKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000f\n\000\n\002\020\016\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\003\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\021\n\002\b\002\n\002\020\001\n\002\b\006\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\b\032\024\020\006\032\0060\007j\002`\b2\006\020\t\032\0020\001H\007\0329\020\n\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\r\032\002H\0132\006\020\016\032\002H\0132\020\020\017\032\f\022\b\022\0060\007j\002`\b0\020H\002¢\006\002\020\021\032\036\020\022\032\f\022\b\022\0060\007j\002`\b0\0202\n\020\023\032\0060\024j\002`\025H\002\0321\020\026\032\0020\0272\020\020\030\032\f\022\b\022\0060\007j\002`\b0\0312\020\020\016\032\f\022\b\022\0060\007j\002`\b0\020H\002¢\006\002\020\032\032\031\020\033\032\0020\0342\006\020\035\032\0020\fHHø\001\000¢\006\002\020\036\032+\020\037\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\0132\n\020\023\032\0060\024j\002`\025H\002¢\006\002\020 \032\037\020!\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\013H\000¢\006\002\020\"\032,\020!\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\0132\n\020\023\032\006\022\002\b\0030#H\b¢\006\002\020$\032!\020%\032\004\030\001H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\013H\002¢\006\002\020\"\032 \020&\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\013H\b¢\006\002\020\"\032\037\020'\032\002H\013\"\b\b\000\020\013*\0020\f2\006\020\035\032\002H\013H\000¢\006\002\020\"\0321\020(\032\030\022\004\022\002H\013\022\016\022\f\022\b\022\0060\007j\002`\b0\0310)\"\b\b\000\020\013*\0020\f*\002H\013H\002¢\006\002\020*\032\034\020+\032\0020,*\0060\007j\002`\b2\n\020-\032\0060\007j\002`\bH\002\032#\020.\032\0020/*\f\022\b\022\0060\007j\002`\b0\0312\006\0200\032\0020\001H\002¢\006\002\0201\032\024\0202\032\0020\027*\0020\f2\006\020\r\032\0020\fH\000\032\020\0203\032\0020,*\0060\007j\002`\bH\000\032\033\0204\032\002H\013\"\b\b\000\020\013*\0020\f*\002H\013H\002¢\006\002\020\"\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\026\020\002\032\n \003*\004\030\0010\0010\001X\004¢\006\002\n\000\"\016\020\004\032\0020\001XT¢\006\002\n\000\"\026\020\005\032\n \003*\004\030\0010\0010\001X\004¢\006\002\n\000*\f\b\000\0205\"\0020\0242\0020\024*\f\b\000\0206\"\0020\0072\0020\007\002\004\n\002\b\031¨\0067"}, d2={"baseContinuationImplClass", "", "baseContinuationImplClassName", "kotlin.jvm.PlatformType", "stackTraceRecoveryClass", "stackTraceRecoveryClassName", "artificialFrame", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "message", "createFinalException", "E", "", "cause", "result", "resultStackTrace", "Ljava/util/ArrayDeque;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Ljava/util/ArrayDeque;)Ljava/lang/Throwable;", "createStackTrace", "continuation", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "mergeRecoveredTraces", "", "recoveredStacktrace", "", "([Ljava/lang/StackTraceElement;Ljava/util/ArrayDeque;)V", "recoverAndThrow", "", "exception", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recoverFromStackFrame", "(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;", "recoverStackTrace", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;", "tryCopyAndVerify", "unwrap", "unwrapImpl", "causeAndStacktrace", "Lkotlin/Pair;", "(Ljava/lang/Throwable;)Lkotlin/Pair;", "elementWiseEquals", "", "e", "frameIndex", "", "methodName", "([Ljava/lang/StackTraceElement;Ljava/lang/String;)I", "initCause", "isArtificial", "sanitizeStackTrace", "CoroutineStackFrame", "StackTraceElement", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class StackTraceRecoveryKt
{
  private static final String baseContinuationImplClass = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
  private static final String baseContinuationImplClassName;
  private static final String stackTraceRecoveryClass = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
  private static final String stackTraceRecoveryClassName;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: ldc 71
    //   2: astore_1
    //   3: ldc 69
    //   5: astore_2
    //   6: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   9: astore_0
    //   10: ldc 69
    //   12: invokestatic 85	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   15: invokevirtual 89	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   18: invokestatic 93	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: astore_0
    //   22: goto +16 -> 38
    //   25: astore_3
    //   26: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   29: astore_0
    //   30: aload_3
    //   31: invokestatic 99	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   34: invokestatic 93	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   37: astore_0
    //   38: aload_0
    //   39: invokestatic 103	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   42: ifnonnull +6 -> 48
    //   45: goto +5 -> 50
    //   48: aload_2
    //   49: astore_0
    //   50: aload_0
    //   51: checkcast 105	java/lang/String
    //   54: putstatic 107	kotlinx/coroutines/internal/StackTraceRecoveryKt:baseContinuationImplClassName	Ljava/lang/String;
    //   57: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   60: astore_0
    //   61: ldc 71
    //   63: invokestatic 85	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   66: invokevirtual 89	java/lang/Class:getCanonicalName	()Ljava/lang/String;
    //   69: invokestatic 93	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   72: astore_0
    //   73: goto +16 -> 89
    //   76: astore_2
    //   77: getstatic 79	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   80: astore_0
    //   81: aload_2
    //   82: invokestatic 99	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   85: invokestatic 93	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   88: astore_0
    //   89: aload_0
    //   90: invokestatic 103	kotlin/Result:exceptionOrNull-impl	(Ljava/lang/Object;)Ljava/lang/Throwable;
    //   93: ifnonnull +6 -> 99
    //   96: goto +5 -> 101
    //   99: aload_1
    //   100: astore_0
    //   101: aload_0
    //   102: checkcast 105	java/lang/String
    //   105: putstatic 109	kotlinx/coroutines/internal/StackTraceRecoveryKt:stackTraceRecoveryClassName	Ljava/lang/String;
    //   108: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   9	93	0	localObject	Object
    //   2	98	1	str1	String
    //   5	44	2	str2	String
    //   76	6	2	localThrowable1	Throwable
    //   25	6	3	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   6	22	25	finally
    //   57	73	76	finally
  }
  
  public static final StackTraceElement artificialFrame(String paramString)
  {
    paramString = Intrinsics.stringPlus("\b\b\b(", paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    return new StackTraceElement(paramString, "\b", "\b", -1);
  }
  
  private static final <E extends Throwable> Pair<E, StackTraceElement[]> causeAndStacktrace(E paramE)
  {
    Throwable localThrowable = paramE.getCause();
    if ((localThrowable != null) && (Intrinsics.areEqual(localThrowable.getClass(), paramE.getClass())))
    {
      StackTraceElement[] arrayOfStackTraceElement = paramE.getStackTrace();
      int j = arrayOfStackTraceElement.length;
      int i = 0;
      while (i < j)
      {
        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[i];
        i++;
        if (isArtificial(localStackTraceElement))
        {
          i = 1;
          break label65;
        }
      }
      i = 0;
      label65:
      if (i != 0) {
        paramE = TuplesKt.to(localThrowable, arrayOfStackTraceElement);
      } else {
        paramE = TuplesKt.to(paramE, new StackTraceElement[0]);
      }
    }
    else
    {
      paramE = TuplesKt.to(paramE, new StackTraceElement[0]);
    }
    return paramE;
  }
  
  private static final <E extends Throwable> E createFinalException(E paramE1, E paramE2, ArrayDeque<StackTraceElement> paramArrayDeque)
  {
    paramArrayDeque.addFirst(artificialFrame("Coroutine boundary"));
    StackTraceElement[] arrayOfStackTraceElement = paramE1.getStackTrace();
    int m = frameIndex(arrayOfStackTraceElement, baseContinuationImplClassName);
    int j = 0;
    if (m == -1)
    {
      paramE1 = (Collection)paramArrayDeque;
      paramE1 = paramE1.toArray(new StackTraceElement[0]);
      if (paramE1 != null)
      {
        paramE2.setStackTrace((StackTraceElement[])paramE1);
        return paramE2;
      }
      throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }
    paramE1 = new StackTraceElement[paramArrayDeque.size() + m];
    int i = 0;
    for (;;)
    {
      int k = i;
      if (k >= m) {
        break;
      }
      i = k + 1;
      paramE1[k] = arrayOfStackTraceElement[k];
    }
    paramArrayDeque = paramArrayDeque.iterator();
    i = j;
    for (;;)
    {
      j = i;
      if (!paramArrayDeque.hasNext()) {
        break;
      }
      i = j + 1;
      paramE1[(m + j)] = ((StackTraceElement)paramArrayDeque.next());
    }
    paramE2.setStackTrace(paramE1);
    return paramE2;
  }
  
  private static final ArrayDeque<StackTraceElement> createStackTrace(CoroutineStackFrame paramCoroutineStackFrame)
  {
    ArrayDeque localArrayDeque = new ArrayDeque();
    StackTraceElement localStackTraceElement = paramCoroutineStackFrame.getStackTraceElement();
    if (localStackTraceElement != null) {
      localArrayDeque.add(localStackTraceElement);
    }
    for (;;)
    {
      boolean bool = paramCoroutineStackFrame instanceof CoroutineStackFrame;
      localStackTraceElement = null;
      if (!bool) {
        paramCoroutineStackFrame = null;
      }
      if (paramCoroutineStackFrame == null) {
        paramCoroutineStackFrame = localStackTraceElement;
      } else {
        paramCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
      }
      if (paramCoroutineStackFrame == null) {
        return localArrayDeque;
      }
      localStackTraceElement = paramCoroutineStackFrame.getStackTraceElement();
      if (localStackTraceElement != null) {
        localArrayDeque.add(localStackTraceElement);
      }
    }
  }
  
  private static final boolean elementWiseEquals(StackTraceElement paramStackTraceElement1, StackTraceElement paramStackTraceElement2)
  {
    boolean bool;
    if ((paramStackTraceElement1.getLineNumber() == paramStackTraceElement2.getLineNumber()) && (Intrinsics.areEqual(paramStackTraceElement1.getMethodName(), paramStackTraceElement2.getMethodName())) && (Intrinsics.areEqual(paramStackTraceElement1.getFileName(), paramStackTraceElement2.getFileName())) && (Intrinsics.areEqual(paramStackTraceElement1.getClassName(), paramStackTraceElement2.getClassName()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static final int frameIndex(StackTraceElement[] paramArrayOfStackTraceElement, String paramString)
  {
    int k = paramArrayOfStackTraceElement.length;
    int i = 0;
    while (i < k)
    {
      j = i;
      i++;
      if (Intrinsics.areEqual(paramString, paramArrayOfStackTraceElement[j].getClassName())) {
        return j;
      }
    }
    int j = -1;
    return j;
  }
  
  public static final void initCause(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    paramThrowable1.initCause(paramThrowable2);
  }
  
  public static final boolean isArtificial(StackTraceElement paramStackTraceElement)
  {
    return StringsKt.startsWith$default(paramStackTraceElement.getClassName(), "\b\b\b", false, 2, null);
  }
  
  private static final void mergeRecoveredTraces(StackTraceElement[] paramArrayOfStackTraceElement, ArrayDeque<StackTraceElement> paramArrayDeque)
  {
    int k = paramArrayOfStackTraceElement.length;
    int i = 0;
    while (i < k)
    {
      j = i;
      i++;
      if (isArtificial(paramArrayOfStackTraceElement[j])) {
        break label31;
      }
    }
    int j = -1;
    label31:
    k = j + 1;
    i = paramArrayOfStackTraceElement.length - 1;
    if (k <= i) {
      do
      {
        j = i;
        i = j - 1;
        if (elementWiseEquals(paramArrayOfStackTraceElement[j], (StackTraceElement)paramArrayDeque.getLast())) {
          paramArrayDeque.removeLast();
        }
        paramArrayDeque.addFirst(paramArrayOfStackTraceElement[j]);
      } while (j != k);
    }
  }
  
  public static final Object recoverAndThrow(Throwable paramThrowable, Continuation<?> paramContinuation)
  {
    if (DebugKt.getRECOVER_STACK_TRACES())
    {
      if (!(paramContinuation instanceof CoroutineStackFrame)) {
        throw paramThrowable;
      }
      throw access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)paramContinuation);
    }
    throw paramThrowable;
  }
  
  private static final Object recoverAndThrow$$forInline(Throwable paramThrowable, Continuation<?> paramContinuation)
  {
    if (DebugKt.getRECOVER_STACK_TRACES())
    {
      InlineMarker.mark(0);
      if (!(paramContinuation instanceof CoroutineStackFrame)) {
        throw paramThrowable;
      }
      throw access$recoverFromStackFrame(paramThrowable, (CoroutineStackFrame)paramContinuation);
    }
    throw paramThrowable;
  }
  
  private static final <E extends Throwable> E recoverFromStackFrame(E paramE, CoroutineStackFrame paramCoroutineStackFrame)
  {
    Object localObject = causeAndStacktrace(paramE);
    Throwable localThrowable = (Throwable)((Pair)localObject).component1();
    StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])((Pair)localObject).component2();
    localObject = tryCopyAndVerify(localThrowable);
    if (localObject == null) {
      return paramE;
    }
    paramCoroutineStackFrame = createStackTrace(paramCoroutineStackFrame);
    if (paramCoroutineStackFrame.isEmpty()) {
      return paramE;
    }
    if (localThrowable != paramE) {
      mergeRecoveredTraces(arrayOfStackTraceElement, paramCoroutineStackFrame);
    }
    return createFinalException(localThrowable, (Throwable)localObject, paramCoroutineStackFrame);
  }
  
  public static final <E extends Throwable> E recoverStackTrace(E paramE)
  {
    if (!DebugKt.getRECOVER_STACK_TRACES()) {
      return paramE;
    }
    Throwable localThrowable = tryCopyAndVerify(paramE);
    if (localThrowable == null) {
      return paramE;
    }
    return sanitizeStackTrace(localThrowable);
  }
  
  public static final <E extends Throwable> E recoverStackTrace(E paramE, Continuation<?> paramContinuation)
  {
    if ((DebugKt.getRECOVER_STACK_TRACES()) && ((paramContinuation instanceof CoroutineStackFrame))) {
      return access$recoverFromStackFrame(paramE, (CoroutineStackFrame)paramContinuation);
    }
    return paramE;
  }
  
  private static final <E extends Throwable> E sanitizeStackTrace(E paramE)
  {
    StackTraceElement[] arrayOfStackTraceElement1 = paramE.getStackTrace();
    int m = arrayOfStackTraceElement1.length;
    int k = frameIndex(arrayOfStackTraceElement1, stackTraceRecoveryClassName);
    int i = frameIndex(arrayOfStackTraceElement1, baseContinuationImplClassName);
    int j = 0;
    if (i == -1) {
      i = 0;
    } else {
      i = m - i;
    }
    m = m - k - i;
    StackTraceElement[] arrayOfStackTraceElement2 = new StackTraceElement[m];
    for (i = j; i < m; i++)
    {
      StackTraceElement localStackTraceElement;
      if (i == 0) {
        localStackTraceElement = artificialFrame("Coroutine boundary");
      } else {
        localStackTraceElement = arrayOfStackTraceElement1[(k + 1 + i - 1)];
      }
      arrayOfStackTraceElement2[i] = localStackTraceElement;
    }
    paramE.setStackTrace(arrayOfStackTraceElement2);
    return paramE;
  }
  
  private static final <E extends Throwable> E tryCopyAndVerify(E paramE)
  {
    Throwable localThrowable = ExceptionsConstructorKt.tryCopyException(paramE);
    if (localThrowable == null) {
      return null;
    }
    if ((!(paramE instanceof CopyableThrowable)) && (!Intrinsics.areEqual(localThrowable.getMessage(), paramE.getMessage()))) {
      return null;
    }
    return localThrowable;
  }
  
  public static final <E extends Throwable> E unwrap(E paramE)
  {
    if (DebugKt.getRECOVER_STACK_TRACES()) {
      paramE = unwrapImpl(paramE);
    }
    return paramE;
  }
  
  public static final <E extends Throwable> E unwrapImpl(E paramE)
  {
    Throwable localThrowable = paramE.getCause();
    if ((localThrowable != null) && (Intrinsics.areEqual(localThrowable.getClass(), paramE.getClass())))
    {
      StackTraceElement[] arrayOfStackTraceElement = paramE.getStackTrace();
      int k = arrayOfStackTraceElement.length;
      int j = 0;
      int i = 0;
      while (i < k)
      {
        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[i];
        i++;
        if (isArtificial(localStackTraceElement))
        {
          i = 1;
          break label72;
        }
      }
      i = j;
      label72:
      if (i != 0) {
        return localThrowable;
      }
      return paramE;
    }
    return paramE;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/StackTraceRecoveryKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */