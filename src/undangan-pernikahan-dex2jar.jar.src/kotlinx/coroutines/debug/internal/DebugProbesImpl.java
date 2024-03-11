package kotlinx.coroutines.debug.internal;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.Continuation<*>;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineId;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000Ö\001\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\022\n\002\020\003\n\002\b\006\n\002\030\002\n\002\b\005\n\002\020$\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\020\n\002\030\002\n\002\b\002\n\002\020\"\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\033\bÀ\002\030\0002\0020\024:\002\001B\t\b\002¢\006\004\b\001\020\002J3\020\b\032\b\022\004\022\0028\0000\004\"\004\b\000\020\0032\f\020\005\032\b\022\004\022\0028\0000\0042\b\020\007\032\004\030\0010\006H\002¢\006\004\b\b\020\tJ\025\020\r\032\0020\f2\006\020\013\032\0020\n¢\006\004\b\r\020\016J\023\020\021\032\b\022\004\022\0020\0200\017¢\006\004\b\021\020\022J\023\020\025\032\b\022\004\022\0020\0240\023¢\006\004\b\025\020\026J@\020\034\032\b\022\004\022\0028\0000\017\"\b\b\000\020\027*\0020\0242\036\b\004\020\033\032\030\022\b\022\006\022\002\b\0030\031\022\004\022\0020\032\022\004\022\0028\0000\030H\b¢\006\004\b\034\020\035J\027\020\036\032\0020\f2\006\020\013\032\0020\nH\002¢\006\004\b\036\020\016J\023\020 \032\b\022\004\022\0020\0370\017¢\006\004\b \020\022J)\020$\032\b\022\004\022\0020\"0\0172\006\020!\032\0020\0202\f\020#\032\b\022\004\022\0020\"0\017¢\006\004\b$\020%J\025\020'\032\0020&2\006\020!\032\0020\020¢\006\004\b'\020(J5\020,\032\b\022\004\022\0020\"0\0172\006\020)\032\0020&2\b\020+\032\004\030\0010*2\f\020#\032\b\022\004\022\0020\"0\017H\002¢\006\004\b,\020-J?\0202\032\016\022\004\022\0020.\022\004\022\0020.012\006\020/\032\0020.2\f\0200\032\b\022\004\022\0020\"0\0232\f\020#\032\b\022\004\022\0020\"0\017H\002¢\006\004\b2\0203J3\0205\032\0020.2\006\0204\032\0020.2\f\0200\032\b\022\004\022\0020\"0\0232\f\020#\032\b\022\004\022\0020\"0\017H\002¢\006\004\b5\0206J\035\0209\032\020\022\004\022\00208\022\004\022\0020\f\030\00107H\002¢\006\004\b9\020:J\025\020=\032\0020&2\006\020<\032\0020;¢\006\004\b=\020>J\r\020?\032\0020\f¢\006\004\b?\020\002J%\020A\032\0020\f2\006\020\013\032\0020\n2\f\020@\032\b\022\004\022\0020\"0\017H\002¢\006\004\bA\020BJ\033\020D\032\0020\f2\n\020C\032\006\022\002\b\0030\031H\002¢\006\004\bD\020EJ)\020H\032\b\022\004\022\0028\0000\004\"\004\b\000\020\0032\f\020\005\032\b\022\004\022\0028\0000\004H\000¢\006\004\bF\020GJ\033\020K\032\0020\f2\n\020\007\032\006\022\002\b\0030\004H\000¢\006\004\bI\020JJ\033\020M\032\0020\f2\n\020\007\032\006\022\002\b\0030\004H\000¢\006\004\bL\020JJ'\020P\032\b\022\004\022\0020\"0\017\"\b\b\000\020\003*\0020N2\006\020O\032\0028\000H\002¢\006\004\bP\020QJ\017\020R\032\0020\fH\002¢\006\004\bR\020\002J\017\020S\032\0020\fH\002¢\006\004\bS\020\002J\r\020T\032\0020\f¢\006\004\bT\020\002J\037\020V\032\0020\f2\006\020\007\032\0020U2\006\020)\032\0020&H\002¢\006\004\bV\020WJ#\020X\032\0020\f2\n\020\007\032\006\022\002\b\0030\0042\006\020)\032\0020&H\002¢\006\004\bX\020YJ/\020X\032\0020\f2\n\020C\032\006\022\002\b\0030\0312\n\020\007\032\006\022\002\b\0030\0042\006\020)\032\0020&H\002¢\006\004\bX\020ZJ;\020b\032\0020\f*\0020;2\022\020]\032\016\022\004\022\0020;\022\004\022\0020\\0[2\n\020`\032\0060^j\002`_2\006\020a\032\0020&H\002¢\006\004\bb\020cJ\027\020d\032\00208*\006\022\002\b\0030\031H\002¢\006\004\bd\020eJ\035\020C\032\b\022\002\b\003\030\0010\031*\006\022\002\b\0030\004H\002¢\006\004\bC\020fJ\032\020C\032\b\022\002\b\003\030\0010\031*\0020UH\020¢\006\004\bC\020gJ\026\020h\032\004\030\0010U*\0020UH\020¢\006\004\bh\020iJ\033\020j\032\004\030\0010\006*\b\022\004\022\0020\"0\017H\002¢\006\004\bj\020kJ\023\020l\032\0020&*\0020\024H\002¢\006\004\bl\020mR\024\020n\032\0020&8\002XT¢\006\006\n\004\bn\020oR \020q\032\016\022\004\022\0020U\022\004\022\0020\\0p8\002X\004¢\006\006\n\004\bq\020rR\036\020v\032\f\022\b\022\006\022\002\b\0030\0310s8BX\004¢\006\006\032\004\bt\020uR$\020w\032\022\022\b\022\006\022\002\b\0030\031\022\004\022\002080p8\002X\004¢\006\006\n\004\bw\020rR\024\020y\032\0020x8\002X\004¢\006\006\n\004\by\020zR\024\020|\032\0020{8\002X\004¢\006\006\n\004\b|\020}R\"\020~\032\020\022\004\022\00208\022\004\022\0020\f\030\001078\002X\004¢\006\006\n\004\b~\020R)\020\001\032\002088\006@\006X\016¢\006\030\n\006\b\001\020\001\032\006\b\001\020\001\"\006\b\001\020\001R\031\020\001\032\0020.8\002@\002X\016¢\006\b\n\006\b\001\020\001R\027\020\001\032\002088@X\004¢\006\b\032\006\b\001\020\001R)\020\001\032\002088\006@\006X\016¢\006\030\n\006\b\001\020\001\032\006\b\001\020\001\"\006\b\001\020\001R\033\020\001\032\004\030\0010*8\002@\002X\016¢\006\b\n\006\b\001\020\001R\"\020\001\032\0020&*\0020;8BX\004¢\006\017\022\006\b\001\020\001\032\005\b\001\020>R\033\020\001\032\00208*\0020\"8BX\004¢\006\b\032\006\b\001\020\001¨\006\001"}, d2={"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "<init>", "()V", "T", "Lkotlin/coroutines/Continuation;", "completion", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "frame", "createOwner", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/StackTraceFrame;)Lkotlin/coroutines/Continuation;", "Ljava/io/PrintStream;", "out", "", "dumpCoroutines", "(Ljava/io/PrintStream;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "dumpCoroutinesInfo", "()Ljava/util/List;", "", "", "dumpCoroutinesInfoAsJsonAndReferences", "()[Ljava/lang/Object;", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "Lkotlin/coroutines/CoroutineContext;", "create", "dumpCoroutinesInfoImpl", "(Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "dumpCoroutinesSynchronized", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpDebuggerInfo", "info", "Ljava/lang/StackTraceElement;", "coroutineTrace", "enhanceStackTraceWithThreadDump", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;Ljava/util/List;)Ljava/util/List;", "", "enhanceStackTraceWithThreadDumpAsJson", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;)Ljava/lang/String;", "state", "Ljava/lang/Thread;", "thread", "enhanceStackTraceWithThreadDumpImpl", "(Ljava/lang/String;Ljava/lang/Thread;Ljava/util/List;)Ljava/util/List;", "", "indexOfResumeWith", "actualTrace", "Lkotlin/Pair;", "findContinuationStartIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "frameIndex", "findIndexOfFrame", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "Lkotlin/Function1;", "", "getDynamicAttach", "()Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/Job;", "job", "hierarchyToString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "install", "frames", "printStackTrace", "(Ljava/io/PrintStream;Ljava/util/List;)V", "owner", "probeCoroutineCompleted", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)V", "probeCoroutineCreated$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "probeCoroutineCreated", "probeCoroutineResumed$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)V", "probeCoroutineResumed", "probeCoroutineSuspended$kotlinx_coroutines_core", "probeCoroutineSuspended", "", "throwable", "sanitizeStackTrace", "(Ljava/lang/Throwable;)Ljava/util/List;", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "uninstall", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateRunningState", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/String;)V", "updateState", "(Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "map", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "builder", "indent", "build", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "isFinished", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Z", "(Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "realCaller", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "toStackTraceFrame", "(Ljava/util/List;)Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "toStringWithQuotes", "(Ljava/lang/Object;)Ljava/lang/String;", "ARTIFICIAL_FRAME_MESSAGE", "Ljava/lang/String;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "callerInfoCache", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "getCapturedCoroutines", "()Ljava/util/Set;", "capturedCoroutines", "capturedCoroutinesMap", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "coroutineStateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Ljava/text/SimpleDateFormat;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dynamicAttach", "Lkotlin/jvm/functions/Function1;", "enableCreationStackTraces", "Z", "getEnableCreationStackTraces", "()Z", "setEnableCreationStackTraces", "(Z)V", "installations", "I", "isInstalled$kotlinx_coroutines_core", "isInstalled", "sanitizeStackTraces", "getSanitizeStackTraces", "setSanitizeStackTraces", "weakRefCleanerThread", "Ljava/lang/Thread;", "getDebugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "debugString", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class DebugProbesImpl
{
  private static final String ARTIFICIAL_FRAME_MESSAGE = "Coroutine creation stacktrace";
  public static final DebugProbesImpl INSTANCE;
  private static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> callerInfoCache = new ConcurrentWeakMap(true);
  private static final ConcurrentWeakMap<CoroutineOwner<?>, Boolean> capturedCoroutinesMap;
  private static final ReentrantReadWriteLock coroutineStateLock;
  private static final SimpleDateFormat dateFormat;
  private static final DebugProbesImpl.SequenceNumberRefVolatile debugProbesImpl$SequenceNumberRefVolatile;
  private static final Function1<Boolean, Unit> dynamicAttach;
  private static boolean enableCreationStackTraces;
  private static volatile int installations;
  private static boolean sanitizeStackTraces;
  private static final AtomicLongFieldUpdater sequenceNumber$FU = AtomicLongFieldUpdater.newUpdater(DebugProbesImpl.SequenceNumberRefVolatile.class, "sequenceNumber");
  private static Thread weakRefCleanerThread;
  
  static
  {
    DebugProbesImpl localDebugProbesImpl = new DebugProbesImpl();
    INSTANCE = localDebugProbesImpl;
    dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    capturedCoroutinesMap = new ConcurrentWeakMap(false, 1, null);
    debugProbesImpl$SequenceNumberRefVolatile = new DebugProbesImpl.SequenceNumberRefVolatile(0L);
    coroutineStateLock = new ReentrantReadWriteLock();
    sanitizeStackTraces = true;
    enableCreationStackTraces = true;
    dynamicAttach = localDebugProbesImpl.getDynamicAttach();
  }
  
  private final void build(Job paramJob, Map<Job, DebugCoroutineInfoImpl> paramMap, StringBuilder paramStringBuilder, String paramString)
  {
    Object localObject = (DebugCoroutineInfoImpl)paramMap.get(paramJob);
    if (localObject == null)
    {
      if (!(paramJob instanceof ScopeCoroutine))
      {
        paramStringBuilder.append(paramString + getDebugString(paramJob) + '\n');
        paramString = Intrinsics.stringPlus(paramString, "\t");
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
      }
    }
    else
    {
      StackTraceElement localStackTraceElement = (StackTraceElement)CollectionsKt.firstOrNull(((DebugCoroutineInfoImpl)localObject).lastObservedStackTrace());
      localObject = ((DebugCoroutineInfoImpl)localObject).getState();
      paramStringBuilder.append(paramString + getDebugString(paramJob) + ", continuation is " + (String)localObject + " at line " + localStackTraceElement + '\n');
      paramString = Intrinsics.stringPlus(paramString, "\t");
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
    }
    paramJob = paramJob.getChildren().iterator();
    while (paramJob.hasNext()) {
      build((Job)paramJob.next(), paramMap, paramStringBuilder, paramString);
    }
  }
  
  private final <T> Continuation<T> createOwner(Continuation<? super T> paramContinuation, StackTraceFrame paramStackTraceFrame)
  {
    if (!isInstalled$kotlinx_coroutines_core()) {
      return paramContinuation;
    }
    paramStackTraceFrame = new CoroutineOwner(paramContinuation, new DebugCoroutineInfoImpl(paramContinuation.getContext(), paramStackTraceFrame, sequenceNumber$FU.incrementAndGet(debugProbesImpl$SequenceNumberRefVolatile)), (CoroutineStackFrame)paramStackTraceFrame);
    paramContinuation = capturedCoroutinesMap;
    ((Map)paramContinuation).put(paramStackTraceFrame, Boolean.valueOf(true));
    if (!isInstalled$kotlinx_coroutines_core()) {
      paramContinuation.clear();
    }
    return (Continuation)paramStackTraceFrame;
  }
  
  private final <R> List<R> dumpCoroutinesInfoImpl(Function2<? super CoroutineOwner<?>, ? super CoroutineContext, ? extends R> paramFunction2)
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
    int m = 0;
    int k = 0;
    if (i == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      if (((DebugProbesImpl)localObject2).isInstalled$kotlinx_coroutines_core())
      {
        Sequence localSequence = CollectionsKt.asSequence((Iterable)((DebugProbesImpl)localObject2).getCapturedCoroutines());
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfoImpl$lambda_12$$inlined$sortedBy$1;
        ((dumpCoroutinesInfoImpl.lambda_12..inlined.sortedBy.1)localObject2).<init>();
        localSequence = SequencesKt.sortedWith(localSequence, (Comparator)localObject2);
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfoImpl$1$3;
        ((dumpCoroutinesInfoImpl.1.3)localObject2).<init>(paramFunction2);
        paramFunction2 = SequencesKt.toList(SequencesKt.mapNotNull(localSequence, (Function1)localObject2));
        return paramFunction2;
      }
      paramFunction2 = new java/lang/IllegalStateException;
      paramFunction2.<init>("Debug probes are not installed".toString());
      throw paramFunction2;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      j = m;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
      InlineMarker.finallyEnd(1);
    }
  }
  
  private final void dumpCoroutinesSynchronized(PrintStream paramPrintStream)
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i;
    if (((ReentrantReadWriteLock)localObject1).getWriteHoldCount() == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    ReentrantReadWriteLock.WriteLock localWriteLock = ((ReentrantReadWriteLock)localObject1).writeLock();
    localWriteLock.lock();
    int k = 0;
    try
    {
      localObject1 = INSTANCE;
      if (((DebugProbesImpl)localObject1).isInstalled$kotlinx_coroutines_core())
      {
        Object localObject2 = Intrinsics.stringPlus("Coroutines dump ", dateFormat.format(Long.valueOf(System.currentTimeMillis())));
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        paramPrintStream.print((String)localObject2);
        localObject2 = SequencesKt.filter(CollectionsKt.asSequence((Iterable)((DebugProbesImpl)localObject1).getCapturedCoroutines()), (Function1)dumpCoroutinesSynchronized.1.2.INSTANCE);
        localObject1 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesSynchronized$lambda_19$$inlined$sortedBy$1;
        ((dumpCoroutinesSynchronized.lambda_19..inlined.sortedBy.1)localObject1).<init>();
        localObject1 = SequencesKt.sortedWith((Sequence)localObject2, (Comparator)localObject1);
        Iterator localIterator = ((Sequence)localObject1).iterator();
        while (localIterator.hasNext())
        {
          CoroutineOwner localCoroutineOwner = (CoroutineOwner)localIterator.next();
          DebugCoroutineInfoImpl localDebugCoroutineInfoImpl = localCoroutineOwner.info;
          List localList2 = localDebugCoroutineInfoImpl.lastObservedStackTrace();
          DebugProbesImpl localDebugProbesImpl = INSTANCE;
          List localList1 = localDebugProbesImpl.enhanceStackTraceWithThreadDumpImpl(localDebugCoroutineInfoImpl.getState(), localDebugCoroutineInfoImpl.lastObservedThread, localList2);
          if ((Intrinsics.areEqual(localDebugCoroutineInfoImpl.getState(), "RUNNING")) && (localList1 == localList2))
          {
            localObject2 = Intrinsics.stringPlus(localDebugCoroutineInfoImpl.getState(), " (Last suspension stacktrace, not an actual stacktrace)");
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
          }
          else
          {
            localObject2 = localDebugCoroutineInfoImpl.getState();
          }
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          paramPrintStream.print("\n\nCoroutine " + localCoroutineOwner.delegate + ", state: " + (String)localObject2);
          if (localList2.isEmpty())
          {
            localObject2 = Intrinsics.stringPlus("\n\tat ", StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace"));
            Log5ECF72.a(localObject2);
            LogE84000.a(localObject2);
            Log229316.a(localObject2);
            paramPrintStream.print((String)localObject2);
            localDebugProbesImpl.printStackTrace(paramPrintStream, localDebugCoroutineInfoImpl.getCreationStackTrace());
          }
          else
          {
            localDebugProbesImpl.printStackTrace(paramPrintStream, localList1);
          }
        }
        paramPrintStream = Unit.INSTANCE;
        return;
      }
      paramPrintStream = new java/lang/IllegalStateException;
      paramPrintStream.<init>("Debug probes are not installed".toString());
      throw paramPrintStream;
    }
    finally
    {
      j = 0;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      localWriteLock.unlock();
    }
  }
  
  /* Error */
  private final List<StackTraceElement> enhanceStackTraceWithThreadDumpImpl(String paramString, Thread paramThread, List<StackTraceElement> paramList)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 482
    //   4: invokestatic 486	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   7: ifeq +317 -> 324
    //   10: aload_2
    //   11: ifnonnull +6 -> 17
    //   14: goto +310 -> 324
    //   17: getstatic 525	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   20: astore_1
    //   21: aload_0
    //   22: checkcast 2	kotlinx/coroutines/debug/internal/DebugProbesImpl
    //   25: astore_1
    //   26: aload_2
    //   27: invokevirtual 531	java/lang/Thread:getStackTrace	()[Ljava/lang/StackTraceElement;
    //   30: invokestatic 534	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   33: astore_1
    //   34: goto +16 -> 50
    //   37: astore_2
    //   38: getstatic 525	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   41: astore_1
    //   42: aload_2
    //   43: invokestatic 540	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   46: invokestatic 534	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   49: astore_1
    //   50: aload_1
    //   51: astore_2
    //   52: aload_1
    //   53: invokestatic 544	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   56: ifeq +5 -> 61
    //   59: aconst_null
    //   60: astore_2
    //   61: aload_2
    //   62: checkcast 546	[Ljava/lang/StackTraceElement;
    //   65: astore_1
    //   66: aload_1
    //   67: ifnonnull +5 -> 72
    //   70: aload_3
    //   71: areturn
    //   72: aload_1
    //   73: arraylength
    //   74: istore 8
    //   76: iconst_0
    //   77: istore 7
    //   79: iconst_0
    //   80: istore 4
    //   82: iload 4
    //   84: iload 8
    //   86: if_icmpge +71 -> 157
    //   89: iload 4
    //   91: istore 5
    //   93: iinc 4 1
    //   96: aload_1
    //   97: iload 5
    //   99: aaload
    //   100: astore_2
    //   101: aload_2
    //   102: invokevirtual 549	java/lang/StackTraceElement:getClassName	()Ljava/lang/String;
    //   105: ldc_w 551
    //   108: invokestatic 486	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   111: ifeq +35 -> 146
    //   114: aload_2
    //   115: invokevirtual 554	java/lang/StackTraceElement:getMethodName	()Ljava/lang/String;
    //   118: ldc_w 556
    //   121: invokestatic 486	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   124: ifeq +22 -> 146
    //   127: aload_2
    //   128: invokevirtual 559	java/lang/StackTraceElement:getFileName	()Ljava/lang/String;
    //   131: ldc_w 561
    //   134: invokestatic 486	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   137: ifeq +9 -> 146
    //   140: iconst_1
    //   141: istore 6
    //   143: goto +6 -> 149
    //   146: iconst_0
    //   147: istore 6
    //   149: iload 6
    //   151: ifeq -69 -> 82
    //   154: goto +6 -> 160
    //   157: iconst_m1
    //   158: istore 5
    //   160: aload_0
    //   161: iload 5
    //   163: aload_1
    //   164: aload_3
    //   165: invokespecial 563	kotlinx/coroutines/debug/internal/DebugProbesImpl:findContinuationStartIndex	(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;
    //   168: astore_2
    //   169: aload_2
    //   170: invokevirtual 568	kotlin/Pair:component1	()Ljava/lang/Object;
    //   173: checkcast 570	java/lang/Number
    //   176: invokevirtual 573	java/lang/Number:intValue	()I
    //   179: istore 8
    //   181: aload_2
    //   182: invokevirtual 576	kotlin/Pair:component2	()Ljava/lang/Object;
    //   185: checkcast 570	java/lang/Number
    //   188: invokevirtual 573	java/lang/Number:intValue	()I
    //   191: istore 9
    //   193: iload 8
    //   195: iconst_m1
    //   196: if_icmpne +5 -> 201
    //   199: aload_3
    //   200: areturn
    //   201: new 578	java/util/ArrayList
    //   204: dup
    //   205: aload_3
    //   206: invokeinterface 581 1 0
    //   211: iload 5
    //   213: iadd
    //   214: iload 8
    //   216: isub
    //   217: iconst_1
    //   218: isub
    //   219: iload 9
    //   221: isub
    //   222: invokespecial 583	java/util/ArrayList:<init>	(I)V
    //   225: astore_2
    //   226: iload 7
    //   228: istore 4
    //   230: iload 4
    //   232: istore 6
    //   234: iload 6
    //   236: iload 5
    //   238: iload 9
    //   240: isub
    //   241: if_icmpge +26 -> 267
    //   244: iload 6
    //   246: iconst_1
    //   247: iadd
    //   248: istore 4
    //   250: aload_2
    //   251: checkcast 585	java/util/Collection
    //   254: aload_1
    //   255: iload 6
    //   257: aaload
    //   258: invokeinterface 588 2 0
    //   263: pop
    //   264: goto -34 -> 230
    //   267: iload 8
    //   269: iconst_1
    //   270: iadd
    //   271: istore 4
    //   273: aload_3
    //   274: invokeinterface 581 1 0
    //   279: istore 6
    //   281: iload 4
    //   283: istore 5
    //   285: iload 5
    //   287: iload 6
    //   289: if_icmpge +30 -> 319
    //   292: iload 5
    //   294: iconst_1
    //   295: iadd
    //   296: istore 4
    //   298: aload_2
    //   299: checkcast 585	java/util/Collection
    //   302: aload_3
    //   303: iload 5
    //   305: invokeinterface 591 2 0
    //   310: invokeinterface 588 2 0
    //   315: pop
    //   316: goto -35 -> 281
    //   319: aload_2
    //   320: checkcast 497	java/util/List
    //   323: areturn
    //   324: aload_3
    //   325: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	326	0	this	DebugProbesImpl
    //   0	326	1	paramString	String
    //   0	326	2	paramThread	Thread
    //   0	326	3	paramList	List<StackTraceElement>
    //   80	217	4	i	int
    //   91	213	5	j	int
    //   141	149	6	k	int
    //   77	150	7	m	int
    //   74	197	8	n	int
    //   191	50	9	i1	int
    // Exception table:
    //   from	to	target	type
    //   17	34	37	finally
  }
  
  private final Pair<Integer, Integer> findContinuationStartIndex(int paramInt, StackTraceElement[] paramArrayOfStackTraceElement, List<StackTraceElement> paramList)
  {
    for (int i = 0; i < 3; i++)
    {
      int j = INSTANCE.findIndexOfFrame(paramInt - 1 - i, paramArrayOfStackTraceElement, paramList);
      if (j != -1) {
        return TuplesKt.to(Integer.valueOf(j), Integer.valueOf(i));
      }
    }
    return TuplesKt.to(Integer.valueOf(-1), Integer.valueOf(0));
  }
  
  private final int findIndexOfFrame(int paramInt, StackTraceElement[] paramArrayOfStackTraceElement, List<StackTraceElement> paramList)
  {
    paramArrayOfStackTraceElement = (StackTraceElement)ArraysKt.getOrNull(paramArrayOfStackTraceElement, paramInt);
    int j = -1;
    if (paramArrayOfStackTraceElement == null) {
      return -1;
    }
    paramInt = 0;
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      StackTraceElement localStackTraceElement = (StackTraceElement)paramList.next();
      int i;
      if ((Intrinsics.areEqual(localStackTraceElement.getFileName(), paramArrayOfStackTraceElement.getFileName())) && (Intrinsics.areEqual(localStackTraceElement.getClassName(), paramArrayOfStackTraceElement.getClassName())) && (Intrinsics.areEqual(localStackTraceElement.getMethodName(), paramArrayOfStackTraceElement.getMethodName()))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return paramInt;
      }
      paramInt++;
    }
    paramInt = j;
    return paramInt;
  }
  
  private final Set<CoroutineOwner<?>> getCapturedCoroutines()
  {
    return capturedCoroutinesMap.keySet();
  }
  
  private final String getDebugString(Job paramJob)
  {
    if ((paramJob instanceof JobSupport)) {
      paramJob = ((JobSupport)paramJob).toDebugString();
    } else {
      paramJob = paramJob.toString();
    }
    return paramJob;
  }
  
  private final Function1<Boolean, Unit> getDynamicAttach()
  {
    try
    {
      Object localObject1 = Result.Companion;
      localObject1 = (DebugProbesImpl)this;
      localObject1 = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(new Object[0]);
      if (localObject1 != null)
      {
        localObject1 = Result.constructor-impl((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localObject1, 1));
      }
      else
      {
        localObject1 = new java/lang/NullPointerException;
        ((NullPointerException)localObject1).<init>("null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>");
        throw ((Throwable)localObject1);
      }
    }
    finally
    {
      Object localObject3 = Result.Companion;
      Object localObject2 = Result.constructor-impl(ResultKt.createFailure(localThrowable));
      localObject3 = localObject2;
      if (Result.isFailure-impl(localObject2)) {
        localObject3 = null;
      }
      return (Function1)localObject3;
    }
  }
  
  private final boolean isFinished(CoroutineOwner<?> paramCoroutineOwner)
  {
    Object localObject = paramCoroutineOwner.info.getContext();
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (Job)((CoroutineContext)localObject).get((CoroutineContext.Key)Job.Key);
    }
    if (localObject == null) {
      return false;
    }
    if (!((Job)localObject).isCompleted()) {
      return false;
    }
    capturedCoroutinesMap.remove(paramCoroutineOwner);
    return true;
  }
  
  private final boolean isInternalMethod(StackTraceElement paramStackTraceElement)
  {
    return StringsKt.startsWith$default(paramStackTraceElement.getClassName(), "kotlinx.coroutines", false, 2, null);
  }
  
  private final CoroutineOwner<?> owner(Continuation<?> paramContinuation)
  {
    boolean bool = paramContinuation instanceof CoroutineStackFrame;
    Object localObject = null;
    if (bool) {
      paramContinuation = (CoroutineStackFrame)paramContinuation;
    } else {
      paramContinuation = null;
    }
    if (paramContinuation == null) {
      paramContinuation = (Continuation<?>)localObject;
    } else {
      paramContinuation = owner(paramContinuation);
    }
    return paramContinuation;
  }
  
  private final CoroutineOwner<?> owner(CoroutineStackFrame paramCoroutineStackFrame)
  {
    CoroutineStackFrame localCoroutineStackFrame;
    do
    {
      if ((paramCoroutineStackFrame instanceof CoroutineOwner))
      {
        paramCoroutineStackFrame = (CoroutineOwner)paramCoroutineStackFrame;
        break;
      }
      localCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
      paramCoroutineStackFrame = localCoroutineStackFrame;
    } while (localCoroutineStackFrame != null);
    paramCoroutineStackFrame = null;
    return paramCoroutineStackFrame;
  }
  
  private final void printStackTrace(PrintStream paramPrintStream, List<StackTraceElement> paramList)
  {
    paramList = ((Iterable)paramList).iterator();
    while (paramList.hasNext())
    {
      String str = Intrinsics.stringPlus("\n\tat ", (StackTraceElement)paramList.next());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      paramPrintStream.print(str);
    }
  }
  
  private final void probeCoroutineCompleted(CoroutineOwner<?> paramCoroutineOwner)
  {
    capturedCoroutinesMap.remove(paramCoroutineOwner);
    paramCoroutineOwner = paramCoroutineOwner.info.getLastObservedFrame$kotlinx_coroutines_core();
    if (paramCoroutineOwner == null) {
      paramCoroutineOwner = null;
    } else {
      paramCoroutineOwner = realCaller(paramCoroutineOwner);
    }
    if (paramCoroutineOwner == null) {
      return;
    }
    callerInfoCache.remove(paramCoroutineOwner);
  }
  
  private final CoroutineStackFrame realCaller(CoroutineStackFrame paramCoroutineStackFrame)
  {
    CoroutineStackFrame localCoroutineStackFrame;
    do
    {
      localCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
      if (localCoroutineStackFrame == null) {
        return null;
      }
      paramCoroutineStackFrame = localCoroutineStackFrame;
    } while (localCoroutineStackFrame.getStackTraceElement() == null);
    return localCoroutineStackFrame;
  }
  
  private final <T extends Throwable> List<StackTraceElement> sanitizeStackTrace(T paramT)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramT.getStackTrace();
    int m = arrayOfStackTraceElement.length;
    int i = arrayOfStackTraceElement.length;
    int j = -1;
    i--;
    int k;
    if (i >= 0) {
      do
      {
        k = i - 1;
        if (Intrinsics.areEqual(arrayOfStackTraceElement[i].getClassName(), "kotlin.coroutines.jvm.internal.DebugProbesKt")) {
          break;
        }
        i = k;
      } while (k >= 0);
    }
    i = j;
    if (!sanitizeStackTraces)
    {
      k = m - i;
      ArrayList localArrayList = new ArrayList(k);
      for (j = 0; j < k; j++)
      {
        if (j == 0) {
          paramT = StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace");
        } else {
          paramT = arrayOfStackTraceElement[(j + i)];
        }
        localArrayList.add(paramT);
      }
      return (List)localArrayList;
    }
    paramT = new ArrayList(m - i + 1);
    ((Collection)paramT).add(StackTraceRecoveryKt.artificialFrame("Coroutine creation stacktrace"));
    i++;
    while (i < m) {
      if (isInternalMethod(arrayOfStackTraceElement[i]))
      {
        ((Collection)paramT).add(arrayOfStackTraceElement[i]);
        for (j = i + 1; (j < m) && (isInternalMethod(arrayOfStackTraceElement[j])); j++) {}
        for (k = j - 1; (k > i) && (arrayOfStackTraceElement[k].getFileName() == null); k--) {}
        if ((k > i) && (k < j - 1)) {
          ((Collection)paramT).add(arrayOfStackTraceElement[k]);
        }
        ((Collection)paramT).add(arrayOfStackTraceElement[(j - 1)]);
        i = j;
      }
      else
      {
        ((Collection)paramT).add(arrayOfStackTraceElement[i]);
        i++;
      }
    }
    return (List)paramT;
  }
  
  private final void startWeakRefCleanerThread()
  {
    weakRefCleanerThread = ThreadsKt.thread$default(false, true, null, "Coroutines Debugger Cleaner", 0, (Function0)startWeakRefCleanerThread.1.INSTANCE, 21, null);
  }
  
  private final void stopWeakRefCleanerThread()
  {
    Thread localThread = weakRefCleanerThread;
    if (localThread == null) {
      return;
    }
    weakRefCleanerThread = null;
    localThread.interrupt();
    localThread.join();
  }
  
  private final StackTraceFrame toStackTraceFrame(List<StackTraceElement> paramList)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    if (!paramList.isEmpty())
    {
      ListIterator localListIterator = paramList.listIterator(paramList.size());
      for (paramList = (List<StackTraceElement>)localObject2;; paramList = new StackTraceFrame((CoroutineStackFrame)paramList, (StackTraceElement)localObject1))
      {
        localObject1 = paramList;
        if (!localListIterator.hasPrevious()) {
          break;
        }
        localObject1 = (StackTraceElement)localListIterator.previous();
      }
    }
    return (StackTraceFrame)localObject1;
  }
  
  private final String toStringWithQuotes(Object paramObject)
  {
    return '"' + paramObject + '"';
  }
  
  private final void updateRunningState(CoroutineStackFrame paramCoroutineStackFrame, String paramString)
  {
    ReentrantReadWriteLock.ReadLock localReadLock = coroutineStateLock.readLock();
    localReadLock.lock();
    try
    {
      DebugProbesImpl localDebugProbesImpl = INSTANCE;
      boolean bool = localDebugProbesImpl.isInstalled$kotlinx_coroutines_core();
      if (!bool) {
        return;
      }
      ConcurrentWeakMap localConcurrentWeakMap = callerInfoCache;
      Object localObject2 = (DebugCoroutineInfoImpl)localConcurrentWeakMap.remove(paramCoroutineStackFrame);
      if (localObject2 == null)
      {
        Object localObject1 = localDebugProbesImpl.owner(paramCoroutineStackFrame);
        CoroutineStackFrame localCoroutineStackFrame = null;
        if (localObject1 == null) {
          localObject1 = null;
        } else {
          localObject1 = ((CoroutineOwner)localObject1).info;
        }
        if (localObject1 == null) {
          return;
        }
        localObject2 = ((DebugCoroutineInfoImpl)localObject1).getLastObservedFrame$kotlinx_coroutines_core();
        if (localObject2 != null) {
          for (;;)
          {
            localCoroutineStackFrame = localDebugProbesImpl.realCaller((CoroutineStackFrame)localObject2);
          }
        }
        localObject2 = localObject1;
        if (localCoroutineStackFrame != null)
        {
          localConcurrentWeakMap.remove(localCoroutineStackFrame);
          localObject2 = localObject1;
        }
      }
      ((DebugCoroutineInfoImpl)localObject2).updateState$kotlinx_coroutines_core(paramString, (Continuation)paramCoroutineStackFrame);
      paramCoroutineStackFrame = localDebugProbesImpl.realCaller(paramCoroutineStackFrame);
      if (paramCoroutineStackFrame == null) {
        return;
      }
      ((Map)localConcurrentWeakMap).put(paramCoroutineStackFrame, localObject2);
      paramCoroutineStackFrame = Unit.INSTANCE;
      return;
    }
    finally
    {
      localReadLock.unlock();
    }
  }
  
  private final void updateState(Continuation<?> paramContinuation, String paramString)
  {
    if (!isInstalled$kotlinx_coroutines_core()) {
      return;
    }
    if ((Intrinsics.areEqual(paramString, "RUNNING")) && (KotlinVersion.CURRENT.isAtLeast(1, 3, 30)))
    {
      if ((paramContinuation instanceof CoroutineStackFrame)) {
        paramContinuation = (CoroutineStackFrame)paramContinuation;
      } else {
        paramContinuation = null;
      }
      if (paramContinuation == null) {
        return;
      }
      updateRunningState(paramContinuation, paramString);
      return;
    }
    CoroutineOwner localCoroutineOwner = owner(paramContinuation);
    if (localCoroutineOwner == null) {
      return;
    }
    updateState(localCoroutineOwner, paramContinuation, paramString);
  }
  
  private final void updateState(CoroutineOwner<?> paramCoroutineOwner, Continuation<?> paramContinuation, String paramString)
  {
    ReentrantReadWriteLock.ReadLock localReadLock = coroutineStateLock.readLock();
    localReadLock.lock();
    try
    {
      boolean bool = INSTANCE.isInstalled$kotlinx_coroutines_core();
      if (!bool) {
        return;
      }
      paramCoroutineOwner.info.updateState$kotlinx_coroutines_core(paramString, paramContinuation);
      paramCoroutineOwner = Unit.INSTANCE;
      return;
    }
    finally
    {
      localReadLock.unlock();
    }
  }
  
  public final void dumpCoroutines(PrintStream paramPrintStream)
  {
    try
    {
      INSTANCE.dumpCoroutinesSynchronized(paramPrintStream);
      Unit localUnit = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  public final List<DebugCoroutineInfo> dumpCoroutinesInfo()
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
    int k = 0;
    int m = 0;
    if (i == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      if (((DebugProbesImpl)localObject2).isInstalled$kotlinx_coroutines_core())
      {
        Sequence localSequence = CollectionsKt.asSequence((Iterable)((DebugProbesImpl)localObject2).getCapturedCoroutines());
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfoImpl$lambda_12$$inlined$sortedBy$1;
        ((dumpCoroutinesInfoImpl.lambda_12..inlined.sortedBy.1)localObject2).<init>();
        localSequence = SequencesKt.sortedWith(localSequence, (Comparator)localObject2);
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1;
        ((dumpCoroutinesInfo..inlined.dumpCoroutinesInfoImpl.1)localObject2).<init>();
        localObject2 = SequencesKt.toList(SequencesKt.mapNotNull(localSequence, (Function1)localObject2));
        return (List<DebugCoroutineInfo>)localObject2;
      }
      localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Debug probes are not installed".toString());
      throw ((Throwable)localObject2);
    }
    finally
    {
      j = k;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
    }
  }
  
  public final Object[] dumpCoroutinesInfoAsJsonAndReferences()
  {
    Object localObject4 = dumpCoroutinesInfo();
    int i = ((List)localObject4).size();
    ArrayList localArrayList2 = new ArrayList(i);
    ArrayList localArrayList1 = new ArrayList(i);
    ArrayList localArrayList3 = new ArrayList(i);
    Iterator localIterator = ((List)localObject4).iterator();
    Object localObject3;
    while (localIterator.hasNext())
    {
      DebugCoroutineInfo localDebugCoroutineInfo = (DebugCoroutineInfo)localIterator.next();
      CoroutineContext localCoroutineContext = localDebugCoroutineInfo.getContext();
      localObject1 = (CoroutineName)localCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
      localObject3 = null;
      if (localObject1 == null) {}
      do
      {
        localObject2 = null;
        break;
        localObject1 = ((CoroutineName)localObject1).getName();
      } while (localObject1 == null);
      localObject2 = toStringWithQuotes(localObject1);
      localObject1 = (CoroutineDispatcher)localCoroutineContext.get((CoroutineContext.Key)CoroutineDispatcher.Key);
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = toStringWithQuotes(localObject1);
      }
      StringBuilder localStringBuilder = new StringBuilder().append("\n                {\n                    \"name\": ");
      localObject2 = localStringBuilder.append(localObject2);
      localStringBuilder = ((StringBuilder)localObject2).append(",\n                    \"id\": ");
      localObject2 = (CoroutineId)localCoroutineContext.get((CoroutineContext.Key)CoroutineId.Key);
      if (localObject2 == null) {
        localObject2 = localObject3;
      } else {
        localObject2 = Long.valueOf(((CoroutineId)localObject2).getId());
      }
      localObject2 = localStringBuilder.append(localObject2);
      localObject2 = ((StringBuilder)localObject2).append(",\n                    \"dispatcher\": ");
      localObject1 = ((StringBuilder)localObject2).append(localObject1);
      localObject1 = ((StringBuilder)localObject1).append(",\n                    \"sequenceNumber\": ").append(localDebugCoroutineInfo.getSequenceNumber());
      localObject1 = ((StringBuilder)localObject1).append(",\n                    \"state\": \"").append(localDebugCoroutineInfo.getState());
      localObject1 = StringsKt.trimIndent("\"\n                } \n                ");
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localArrayList3.add(localObject1);
      localArrayList1.add(localDebugCoroutineInfo.getLastObservedFrame());
      localArrayList2.add(localDebugCoroutineInfo.getLastObservedThread());
    }
    Object localObject1 = new StringBuilder().append('[');
    Object localObject2 = CollectionsKt.joinToString$default((Iterable)localArrayList3, null, null, null, 0, null, null, 63, null);
    Log5ECF72.a(localObject2);
    LogE84000.a(localObject2);
    Log229316.a(localObject2);
    localObject1 = (String)localObject2 + ']';
    localObject2 = (Collection)localArrayList2;
    localObject2 = ((Collection)localObject2).toArray(new Thread[0]);
    if (localObject2 != null)
    {
      localObject3 = (Collection)localArrayList1;
      localObject3 = ((Collection)localObject3).toArray(new CoroutineStackFrame[0]);
      if (localObject3 != null)
      {
        localObject4 = (Collection)localObject4;
        localObject4 = ((Collection)localObject4).toArray(new DebugCoroutineInfo[0]);
        if (localObject4 != null) {
          return new Object[] { localObject1, localObject2, localObject3, localObject4 };
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
      }
      throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
  }
  
  public final List<DebuggerInfo> dumpDebuggerInfo()
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
    int m = 0;
    int k = 0;
    if (i == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      if (((DebugProbesImpl)localObject2).isInstalled$kotlinx_coroutines_core())
      {
        Sequence localSequence = CollectionsKt.asSequence((Iterable)((DebugProbesImpl)localObject2).getCapturedCoroutines());
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfoImpl$lambda_12$$inlined$sortedBy$1;
        ((dumpCoroutinesInfoImpl.lambda_12..inlined.sortedBy.1)localObject2).<init>();
        localSequence = SequencesKt.sortedWith(localSequence, (Comparator)localObject2);
        localObject2 = new kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1;
        ((dumpDebuggerInfo..inlined.dumpCoroutinesInfoImpl.1)localObject2).<init>();
        localObject2 = SequencesKt.toList(SequencesKt.mapNotNull(localSequence, (Function1)localObject2));
        return (List<DebuggerInfo>)localObject2;
      }
      localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Debug probes are not installed".toString());
      throw ((Throwable)localObject2);
    }
    finally
    {
      j = m;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
    }
  }
  
  public final List<StackTraceElement> enhanceStackTraceWithThreadDump(DebugCoroutineInfo paramDebugCoroutineInfo, List<StackTraceElement> paramList)
  {
    return enhanceStackTraceWithThreadDumpImpl(paramDebugCoroutineInfo.getState(), paramDebugCoroutineInfo.getLastObservedThread(), paramList);
  }
  
  public final String enhanceStackTraceWithThreadDumpAsJson(DebugCoroutineInfo paramDebugCoroutineInfo)
  {
    paramDebugCoroutineInfo = enhanceStackTraceWithThreadDump(paramDebugCoroutineInfo, paramDebugCoroutineInfo.lastObservedStackTrace());
    Object localObject = (List)new ArrayList();
    Iterator localIterator = paramDebugCoroutineInfo.iterator();
    while (localIterator.hasNext())
    {
      StackTraceElement localStackTraceElement = (StackTraceElement)localIterator.next();
      paramDebugCoroutineInfo = new StringBuilder().append("\n                {\n                    \"declaringClass\": \"").append(localStackTraceElement.getClassName());
      paramDebugCoroutineInfo = paramDebugCoroutineInfo.append("\",\n                    \"methodName\": \"").append(localStackTraceElement.getMethodName());
      StringBuilder localStringBuilder = paramDebugCoroutineInfo.append("\",\n                    \"fileName\": ");
      paramDebugCoroutineInfo = localStackTraceElement.getFileName();
      if (paramDebugCoroutineInfo == null) {
        paramDebugCoroutineInfo = null;
      } else {
        paramDebugCoroutineInfo = toStringWithQuotes(paramDebugCoroutineInfo);
      }
      paramDebugCoroutineInfo = localStringBuilder.append(paramDebugCoroutineInfo);
      paramDebugCoroutineInfo = paramDebugCoroutineInfo.append(",\n                    \"lineNumber\": ").append(localStackTraceElement.getLineNumber());
      paramDebugCoroutineInfo = StringsKt.trimIndent("\n                }\n                ");
      Log5ECF72.a(paramDebugCoroutineInfo);
      LogE84000.a(paramDebugCoroutineInfo);
      Log229316.a(paramDebugCoroutineInfo);
      ((List)localObject).add(paramDebugCoroutineInfo);
    }
    paramDebugCoroutineInfo = new StringBuilder().append('[');
    localObject = CollectionsKt.joinToString$default((Iterable)localObject, null, null, null, 0, null, null, 63, null);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return (String)localObject + ']';
  }
  
  public final boolean getEnableCreationStackTraces()
  {
    return enableCreationStackTraces;
  }
  
  public final boolean getSanitizeStackTraces()
  {
    return sanitizeStackTraces;
  }
  
  public final String hierarchyToString(Job paramJob)
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i;
    if (((ReentrantReadWriteLock)localObject1).getWriteHoldCount() == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      Object localObject3;
      Object localObject4;
      if (((DebugProbesImpl)localObject2).isInstalled$kotlinx_coroutines_core())
      {
        localObject3 = (Iterable)((DebugProbesImpl)localObject2).getCapturedCoroutines();
        localObject2 = new java/util/ArrayList;
        ((ArrayList)localObject2).<init>();
        localObject2 = (Collection)localObject2;
        localObject3 = ((Iterable)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = ((Iterator)localObject3).next();
          if (((CoroutineOwner)localObject4).delegate.getContext().get((CoroutineContext.Key)Job.Key) != null) {
            j = 1;
          } else {
            j = 0;
          }
          if (j != 0) {
            ((Collection)localObject2).add(localObject4);
          }
        }
        localObject2 = (List)localObject2;
        localObject3 = (Iterable)localObject2;
        j = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10)), 16);
        localObject2 = new java/util/LinkedHashMap;
        ((LinkedHashMap)localObject2).<init>(j);
        localObject2 = (Map)localObject2;
        localObject3 = ((Iterable)localObject3).iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = ((Iterator)localObject3).next();
          ((Map)localObject2).put(JobKt.getJob(((CoroutineOwner)localObject4).delegate.getContext()), ((CoroutineOwner)localObject4).info);
        }
        localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        localObject4 = INSTANCE;
      }
      try
      {
        ((DebugProbesImpl)localObject4).build(paramJob, (Map)localObject2, (StringBuilder)localObject3, "");
        paramJob = ((StringBuilder)localObject3).toString();
        Intrinsics.checkNotNullExpressionValue(paramJob, "StringBuilder().apply(builderAction).toString()");
        j = 0;
        while (j < i)
        {
          j++;
          localReadLock.lock();
        }
        ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
        return paramJob;
      }
      finally
      {
        break label378;
      }
      paramJob = new java/lang/IllegalStateException;
      paramJob.<init>("Debug probes are not installed".toString());
      throw paramJob;
    }
    finally {}
    label378:
    j = 0;
    while (j < i)
    {
      j++;
      localReadLock.lock();
    }
    ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
    throw paramJob;
  }
  
  public final void install()
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
    int m = 0;
    int i1 = 0;
    int k = 0;
    int n = 0;
    if (i == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      installations += 1;
      j = installations;
      if (j > 1) {
        return;
      }
      ((DebugProbesImpl)localObject2).startWeakRefCleanerThread();
      boolean bool = AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core();
      if (bool) {
        return;
      }
      localObject2 = dynamicAttach;
      if (localObject2 != null) {
        for (;;)
        {
          ((Function1)localObject2).invoke(Boolean.valueOf(true));
        }
      }
      localObject2 = Unit.INSTANCE;
      return;
    }
    finally
    {
      j = k;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
    }
  }
  
  public final boolean isInstalled$kotlinx_coroutines_core()
  {
    boolean bool;
    if (installations > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(Continuation<? super T> paramContinuation)
  {
    if (!isInstalled$kotlinx_coroutines_core()) {
      return paramContinuation;
    }
    if (owner(paramContinuation) != null) {
      return paramContinuation;
    }
    StackTraceFrame localStackTraceFrame1;
    if (enableCreationStackTraces)
    {
      localStackTraceFrame1 = toStackTraceFrame(sanitizeStackTrace((Throwable)new Exception()));
    }
    else
    {
      localStackTraceFrame1 = null;
      StackTraceFrame localStackTraceFrame2 = (StackTraceFrame)null;
    }
    return createOwner(paramContinuation, localStackTraceFrame1);
  }
  
  public final void probeCoroutineResumed$kotlinx_coroutines_core(Continuation<?> paramContinuation)
  {
    updateState(paramContinuation, "RUNNING");
  }
  
  public final void probeCoroutineSuspended$kotlinx_coroutines_core(Continuation<?> paramContinuation)
  {
    updateState(paramContinuation, "SUSPENDED");
  }
  
  public final void setEnableCreationStackTraces(boolean paramBoolean)
  {
    enableCreationStackTraces = paramBoolean;
  }
  
  public final void setSanitizeStackTraces(boolean paramBoolean)
  {
    sanitizeStackTraces = paramBoolean;
  }
  
  public final void uninstall()
  {
    Object localObject1 = coroutineStateLock;
    ReentrantReadWriteLock.ReadLock localReadLock = ((ReentrantReadWriteLock)localObject1).readLock();
    int i = ((ReentrantReadWriteLock)localObject1).getWriteHoldCount();
    int n = 0;
    int m = 0;
    int k = 0;
    int i1 = 0;
    if (i == 0) {
      i = ((ReentrantReadWriteLock)localObject1).getReadHoldCount();
    } else {
      i = 0;
    }
    int j = 0;
    while (j < i)
    {
      j++;
      localReadLock.unlock();
    }
    localObject1 = ((ReentrantReadWriteLock)localObject1).writeLock();
    ((ReentrantReadWriteLock.WriteLock)localObject1).lock();
    try
    {
      Object localObject2 = INSTANCE;
      if (((DebugProbesImpl)localObject2).isInstalled$kotlinx_coroutines_core())
      {
        installations -= 1;
        j = installations;
        if (j != 0) {
          return;
        }
        ((DebugProbesImpl)localObject2).stopWeakRefCleanerThread();
        capturedCoroutinesMap.clear();
        callerInfoCache.clear();
        boolean bool = AgentInstallationType.INSTANCE.isInstalledStatically$kotlinx_coroutines_core();
        if (bool) {
          return;
        }
        localObject2 = dynamicAttach;
        if (localObject2 != null) {
          for (;;)
          {
            ((Function1)localObject2).invoke(Boolean.valueOf(false));
          }
        }
        localObject2 = Unit.INSTANCE;
        return;
      }
      localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Agent was not installed".toString());
      throw ((Throwable)localObject2);
    }
    finally
    {
      j = k;
      while (j < i)
      {
        j++;
        localReadLock.lock();
      }
      ((ReentrantReadWriteLock.WriteLock)localObject1).unlock();
    }
  }
  
  @Metadata(d1={"\000<\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0020\003B%\022\f\020\004\032\b\022\004\022\0028\0000\002\022\006\020\005\032\0020\006\022\b\020\007\032\004\030\0010\003¢\006\002\020\bJ\n\020\020\032\004\030\0010\021H\026J\036\020\022\032\0020\0232\f\020\024\032\b\022\004\022\0028\0000\025H\026ø\001\000¢\006\002\020\026J\b\020\027\032\0020\030H\026R\026\020\t\032\004\030\0010\0038VX\004¢\006\006\032\004\b\n\020\013R\022\020\f\032\0020\rX\005¢\006\006\032\004\b\016\020\017R\026\020\004\032\b\022\004\022\0028\0000\0028\006X\004¢\006\002\n\000R\020\020\007\032\004\030\0010\003X\004¢\006\002\n\000R\020\020\005\032\0020\0068\006X\004¢\006\002\n\000\002\004\n\002\b\031¨\006\031"}, d2={"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "T", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "frame", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  private static final class CoroutineOwner<T>
    implements Continuation<T>, CoroutineStackFrame
  {
    public final Continuation<T> delegate;
    private final CoroutineStackFrame frame;
    public final DebugCoroutineInfoImpl info;
    
    public CoroutineOwner(Continuation<? super T> paramContinuation, DebugCoroutineInfoImpl paramDebugCoroutineInfoImpl, CoroutineStackFrame paramCoroutineStackFrame)
    {
      this.delegate = paramContinuation;
      this.info = paramDebugCoroutineInfoImpl;
      this.frame = paramCoroutineStackFrame;
    }
    
    public CoroutineStackFrame getCallerFrame()
    {
      CoroutineStackFrame localCoroutineStackFrame = this.frame;
      if (localCoroutineStackFrame == null) {
        localCoroutineStackFrame = null;
      } else {
        localCoroutineStackFrame = localCoroutineStackFrame.getCallerFrame();
      }
      return localCoroutineStackFrame;
    }
    
    public CoroutineContext getContext()
    {
      return this.delegate.getContext();
    }
    
    public StackTraceElement getStackTraceElement()
    {
      Object localObject = this.frame;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = ((CoroutineStackFrame)localObject).getStackTraceElement();
      }
      return (StackTraceElement)localObject;
    }
    
    public void resumeWith(Object paramObject)
    {
      DebugProbesImpl.access$probeCoroutineCompleted(DebugProbesImpl.INSTANCE, this);
      this.delegate.resumeWith(paramObject);
    }
    
    public String toString()
    {
      return this.delegate.toString();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/DebugProbesImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */