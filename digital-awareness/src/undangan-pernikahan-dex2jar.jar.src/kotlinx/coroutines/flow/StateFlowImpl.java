package kotlinx.coroutines.flow;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

@Metadata(d1={"\000p\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\000\n\002\020\001\n\002\b\004\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020\021\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\t\n\002\020 \n\002\b\n\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\b\002\030\000*\004\b\000\020\0012\b\022\004\022\0020\020062\b\022\004\022\0028\000072\b\022\004\022\0028\000082\b\022\004\022\0028\00009B\017\022\006\020\003\032\0020\002¢\006\004\b\004\020\005J!\020\t\032\0020\b2\f\020\007\032\b\022\004\022\0028\0000\006H@ø\001\000¢\006\004\b\t\020\nJ\037\020\016\032\0020\r2\006\020\013\032\0028\0002\006\020\f\032\0028\000H\026¢\006\004\b\016\020\017J\017\020\021\032\0020\020H\024¢\006\004\b\021\020\022J\037\020\026\032\n\022\006\022\004\030\0010\0200\0252\006\020\024\032\0020\023H\024¢\006\004\b\026\020\027J\033\020\032\032\0020\0312\006\020\030\032\0028\000H@ø\001\000¢\006\004\b\032\020\033J-\020\"\032\b\022\004\022\0028\0000!2\006\020\035\032\0020\0342\006\020\036\032\0020\0232\006\020 \032\0020\037H\026¢\006\004\b\"\020#J\017\020$\032\0020\031H\026¢\006\004\b$\020%J\027\020&\032\0020\r2\006\020\030\032\0028\000H\026¢\006\004\b&\020'J!\020*\032\0020\r2\b\020(\032\004\030\0010\0022\006\020)\032\0020\002H\002¢\006\004\b*\020\017R\032\020.\032\b\022\004\022\0028\0000+8VX\004¢\006\006\032\004\b,\020-R\026\020/\032\0020\0238\002@\002X\016¢\006\006\n\004\b/\0200R*\020\030\032\0028\0002\006\020\030\032\0028\0008V@VX\016¢\006\022\022\004\b4\020%\032\004\b1\0202\"\004\b3\020\005\002\004\n\002\b\031¨\0065"}, d2={"Lkotlinx/coroutines/flow/StateFlowImpl;", "T", "", "initialState", "<init>", "(Ljava/lang/Object;)V", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "", "collect", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "expect", "update", "", "compareAndSet", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/flow/StateFlowSlot;", "createSlot", "()Lkotlinx/coroutines/flow/StateFlowSlot;", "", "size", "", "createSlotArray", "(I)[Lkotlinx/coroutines/flow/StateFlowSlot;", "value", "", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlin/coroutines/CoroutineContext;", "context", "capacity", "Lkotlinx/coroutines/channels/BufferOverflow;", "onBufferOverflow", "Lkotlinx/coroutines/flow/Flow;", "fuse", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)Lkotlinx/coroutines/flow/Flow;", "resetReplayCache", "()V", "tryEmit", "(Ljava/lang/Object;)Z", "expectedState", "newState", "updateState", "", "getReplayCache", "()Ljava/util/List;", "replayCache", "sequence", "I", "getValue", "()Ljava/lang/Object;", "setValue", "getValue$annotations", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;"}, k=1, mv={1, 6, 0}, xi=48)
final class StateFlowImpl<T>
  extends AbstractSharedFlow<StateFlowSlot>
  implements MutableStateFlow<T>, CancellableFlow<T>, FusibleFlow<T>
{
  private volatile Object _state;
  private int sequence;
  
  public StateFlowImpl(Object paramObject)
  {
    this._state = paramObject;
  }
  
  private final boolean updateState(Object paramObject1, Object paramObject2)
  {
    getSlots();
    try
    {
      Object localObject = this._state;
      if (paramObject1 != null)
      {
        bool = Intrinsics.areEqual(localObject, paramObject1);
        if (!bool) {
          return false;
        }
      }
      boolean bool = Intrinsics.areEqual(localObject, paramObject2);
      if (bool) {
        return true;
      }
      this._state = paramObject2;
      int i = this.sequence;
      if ((i & 0x1) == 0)
      {
        i++;
        this.sequence = i;
        paramObject1 = getSlots();
        paramObject2 = Unit.INSTANCE;
        for (;;)
        {
          paramObject1 = (StateFlowSlot[])paramObject1;
          int j;
          if (paramObject1 != null)
          {
            int k = paramObject1.length;
            j = 0;
            while (j < k)
            {
              paramObject2 = paramObject1[j];
              j++;
              if (paramObject2 != null) {
                ((StateFlowSlot)paramObject2).makePending();
              }
            }
          }
          try
          {
            j = this.sequence;
            if (j == i)
            {
              this.sequence = (i + 1);
              return true;
            }
            i = j;
            paramObject1 = getSlots();
            paramObject2 = Unit.INSTANCE;
          }
          finally {}
        }
      }
      this.sequence = (i + 2);
      return true;
    }
    finally {}
  }
  
  /* Error */
  public Object collect(FlowCollector<? super T> paramFlowCollector, Continuation<?> paramContinuation)
  {
    // Byte code:
    //   0: aload_2
    //   1: instanceof 13
    //   4: ifeq +36 -> 40
    //   7: aload_2
    //   8: checkcast 13	kotlinx/coroutines/flow/StateFlowImpl$collect$1
    //   11: astore 13
    //   13: aload 13
    //   15: getfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   18: ldc 112
    //   20: iand
    //   21: ifeq +19 -> 40
    //   24: aload 13
    //   26: aload 13
    //   28: getfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   31: ldc 112
    //   33: iadd
    //   34: putfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   37: goto +14 -> 51
    //   40: new 13	kotlinx/coroutines/flow/StateFlowImpl$collect$1
    //   43: dup
    //   44: aload_0
    //   45: aload_2
    //   46: invokespecial 115	kotlinx/coroutines/flow/StateFlowImpl$collect$1:<init>	(Lkotlinx/coroutines/flow/StateFlowImpl;Lkotlin/coroutines/Continuation;)V
    //   49: astore 13
    //   51: aload 13
    //   53: getfield 118	kotlinx/coroutines/flow/StateFlowImpl$collect$1:result	Ljava/lang/Object;
    //   56: astore 8
    //   58: invokestatic 123	kotlin/coroutines/intrinsics/IntrinsicsKt:getCOROUTINE_SUSPENDED	()Ljava/lang/Object;
    //   61: astore 15
    //   63: aload 13
    //   65: getfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   68: tableswitch	default:+32->100, 0:+205->273, 1:+163->231, 2:+101->169, 3:+42->110
    //   100: new 125	java/lang/IllegalStateException
    //   103: dup
    //   104: ldc 127
    //   106: invokespecial 130	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   109: athrow
    //   110: aload 13
    //   112: getfield 133	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$4	Ljava/lang/Object;
    //   115: astore 9
    //   117: aload 13
    //   119: getfield 136	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$3	Ljava/lang/Object;
    //   122: checkcast 138	kotlinx/coroutines/Job
    //   125: astore 5
    //   127: aload 13
    //   129: getfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   132: checkcast 104	kotlinx/coroutines/flow/StateFlowSlot
    //   135: astore_3
    //   136: aload 13
    //   138: getfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   141: checkcast 146	kotlinx/coroutines/flow/FlowCollector
    //   144: astore 6
    //   146: aload 13
    //   148: getfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   151: checkcast 2	kotlinx/coroutines/flow/StateFlowImpl
    //   154: astore 4
    //   156: aload_3
    //   157: astore_2
    //   158: aload 4
    //   160: astore_1
    //   161: aload 8
    //   163: invokestatic 154	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   166: goto +637 -> 803
    //   169: aload 13
    //   171: getfield 133	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$4	Ljava/lang/Object;
    //   174: astore 7
    //   176: aload 13
    //   178: getfield 136	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$3	Ljava/lang/Object;
    //   181: checkcast 138	kotlinx/coroutines/Job
    //   184: astore 5
    //   186: aload 13
    //   188: getfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   191: checkcast 104	kotlinx/coroutines/flow/StateFlowSlot
    //   194: astore_3
    //   195: aload 13
    //   197: getfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   200: checkcast 146	kotlinx/coroutines/flow/FlowCollector
    //   203: astore 6
    //   205: aload 13
    //   207: getfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   210: checkcast 2	kotlinx/coroutines/flow/StateFlowImpl
    //   213: astore 4
    //   215: aload_3
    //   216: astore_2
    //   217: aload 4
    //   219: astore_1
    //   220: aload 8
    //   222: invokestatic 154	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   225: aload 7
    //   227: astore_1
    //   228: goto +407 -> 635
    //   231: aload 13
    //   233: getfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   236: checkcast 104	kotlinx/coroutines/flow/StateFlowSlot
    //   239: astore_3
    //   240: aload 13
    //   242: getfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   245: checkcast 146	kotlinx/coroutines/flow/FlowCollector
    //   248: astore 6
    //   250: aload 13
    //   252: getfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   255: checkcast 2	kotlinx/coroutines/flow/StateFlowImpl
    //   258: astore 4
    //   260: aload_3
    //   261: astore_2
    //   262: aload 4
    //   264: astore_1
    //   265: aload 8
    //   267: invokestatic 154	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   270: goto +145 -> 415
    //   273: aload 8
    //   275: invokestatic 154	kotlin/ResultKt:throwOnFailure	(Ljava/lang/Object;)V
    //   278: aload_0
    //   279: astore 5
    //   281: aload_1
    //   282: astore 7
    //   284: aload 5
    //   286: invokevirtual 158	kotlinx/coroutines/flow/StateFlowImpl:allocateSlot	()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;
    //   289: checkcast 104	kotlinx/coroutines/flow/StateFlowSlot
    //   292: astore 8
    //   294: aload 8
    //   296: astore_3
    //   297: aload 7
    //   299: astore 6
    //   301: aload 5
    //   303: astore 4
    //   305: aload 8
    //   307: astore_2
    //   308: aload 5
    //   310: astore_1
    //   311: aload 7
    //   313: instanceof 160
    //   316: ifeq +99 -> 415
    //   319: aload 8
    //   321: astore_2
    //   322: aload 5
    //   324: astore_1
    //   325: aload 7
    //   327: checkcast 160	kotlinx/coroutines/flow/SubscribedFlowCollector
    //   330: astore 9
    //   332: aload 8
    //   334: astore_2
    //   335: aload 5
    //   337: astore_1
    //   338: aload 13
    //   340: aload 5
    //   342: putfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   345: aload 8
    //   347: astore_2
    //   348: aload 5
    //   350: astore_1
    //   351: aload 13
    //   353: aload 7
    //   355: putfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   358: aload 8
    //   360: astore_2
    //   361: aload 5
    //   363: astore_1
    //   364: aload 13
    //   366: aload 8
    //   368: putfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   371: aload 8
    //   373: astore_2
    //   374: aload 5
    //   376: astore_1
    //   377: aload 13
    //   379: iconst_1
    //   380: putfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   383: aload 8
    //   385: astore_3
    //   386: aload 7
    //   388: astore 6
    //   390: aload 5
    //   392: astore 4
    //   394: aload 8
    //   396: astore_2
    //   397: aload 5
    //   399: astore_1
    //   400: aload 9
    //   402: aload 13
    //   404: invokevirtual 164	kotlinx/coroutines/flow/SubscribedFlowCollector:onSubscription	(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    //   407: aload 15
    //   409: if_acmpne +6 -> 415
    //   412: aload 15
    //   414: areturn
    //   415: aload_3
    //   416: astore_2
    //   417: aload 4
    //   419: astore_1
    //   420: aload 13
    //   422: invokeinterface 170 1 0
    //   427: getstatic 174	kotlinx/coroutines/Job:Key	Lkotlinx/coroutines/Job$Key;
    //   430: checkcast 176	kotlin/coroutines/CoroutineContext$Key
    //   433: invokeinterface 182 2 0
    //   438: checkcast 138	kotlinx/coroutines/Job
    //   441: astore 5
    //   443: aconst_null
    //   444: astore 11
    //   446: aload_3
    //   447: astore_2
    //   448: aload 4
    //   450: astore_1
    //   451: aload 4
    //   453: getfield 82	kotlinx/coroutines/flow/StateFlowImpl:_state	Ljava/lang/Object;
    //   456: astore 14
    //   458: aload 5
    //   460: ifnonnull +6 -> 466
    //   463: goto +13 -> 476
    //   466: aload_3
    //   467: astore_2
    //   468: aload 4
    //   470: astore_1
    //   471: aload 5
    //   473: invokestatic 188	kotlinx/coroutines/JobKt:ensureActive	(Lkotlinx/coroutines/Job;)V
    //   476: aload 11
    //   478: ifnull +37 -> 515
    //   481: aload 5
    //   483: astore 12
    //   485: aload_3
    //   486: astore 8
    //   488: aload 6
    //   490: astore 10
    //   492: aload 4
    //   494: astore 7
    //   496: aload 11
    //   498: astore 9
    //   500: aload_3
    //   501: astore_2
    //   502: aload 4
    //   504: astore_1
    //   505: aload 11
    //   507: aload 14
    //   509: invokestatic 92	kotlin/jvm/internal/Intrinsics:areEqual	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   512: ifne +141 -> 653
    //   515: aload_3
    //   516: astore_2
    //   517: aload 4
    //   519: astore_1
    //   520: aload 14
    //   522: getstatic 194	kotlinx/coroutines/flow/internal/NullSurrogateKt:NULL	Lkotlinx/coroutines/internal/Symbol;
    //   525: if_acmpne +9 -> 534
    //   528: aconst_null
    //   529: astore 7
    //   531: goto +7 -> 538
    //   534: aload 14
    //   536: astore 7
    //   538: aload_3
    //   539: astore_2
    //   540: aload 4
    //   542: astore_1
    //   543: aload 13
    //   545: aload 4
    //   547: putfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   550: aload_3
    //   551: astore_2
    //   552: aload 4
    //   554: astore_1
    //   555: aload 13
    //   557: aload 6
    //   559: putfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   562: aload_3
    //   563: astore_2
    //   564: aload 4
    //   566: astore_1
    //   567: aload 13
    //   569: aload_3
    //   570: putfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   573: aload_3
    //   574: astore_2
    //   575: aload 4
    //   577: astore_1
    //   578: aload 13
    //   580: aload 5
    //   582: putfield 136	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$3	Ljava/lang/Object;
    //   585: aload_3
    //   586: astore_2
    //   587: aload 4
    //   589: astore_1
    //   590: aload 13
    //   592: aload 14
    //   594: putfield 133	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$4	Ljava/lang/Object;
    //   597: aload_3
    //   598: astore_2
    //   599: aload 4
    //   601: astore_1
    //   602: aload 13
    //   604: iconst_2
    //   605: putfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   608: aload_3
    //   609: astore_2
    //   610: aload 4
    //   612: astore_1
    //   613: aload 6
    //   615: aload 7
    //   617: aload 13
    //   619: invokeinterface 196 3 0
    //   624: aload 15
    //   626: if_acmpne +6 -> 632
    //   629: aload 15
    //   631: areturn
    //   632: aload 14
    //   634: astore_1
    //   635: aload_1
    //   636: astore 9
    //   638: aload 4
    //   640: astore 7
    //   642: aload 6
    //   644: astore 10
    //   646: aload_3
    //   647: astore 8
    //   649: aload 5
    //   651: astore 12
    //   653: aload 12
    //   655: astore 5
    //   657: aload 8
    //   659: astore_3
    //   660: aload 10
    //   662: astore 6
    //   664: aload 7
    //   666: astore 4
    //   668: aload 9
    //   670: astore 11
    //   672: aload 8
    //   674: astore_2
    //   675: aload 7
    //   677: astore_1
    //   678: aload 8
    //   680: invokevirtual 200	kotlinx/coroutines/flow/StateFlowSlot:takePending	()Z
    //   683: ifne -237 -> 446
    //   686: aload 8
    //   688: astore_2
    //   689: aload 7
    //   691: astore_1
    //   692: aload 13
    //   694: aload 7
    //   696: putfield 149	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$0	Ljava/lang/Object;
    //   699: aload 8
    //   701: astore_2
    //   702: aload 7
    //   704: astore_1
    //   705: aload 13
    //   707: aload 10
    //   709: putfield 144	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$1	Ljava/lang/Object;
    //   712: aload 8
    //   714: astore_2
    //   715: aload 7
    //   717: astore_1
    //   718: aload 13
    //   720: aload 8
    //   722: putfield 141	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$2	Ljava/lang/Object;
    //   725: aload 8
    //   727: astore_2
    //   728: aload 7
    //   730: astore_1
    //   731: aload 13
    //   733: aload 12
    //   735: putfield 136	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$3	Ljava/lang/Object;
    //   738: aload 8
    //   740: astore_2
    //   741: aload 7
    //   743: astore_1
    //   744: aload 13
    //   746: aload 9
    //   748: putfield 133	kotlinx/coroutines/flow/StateFlowImpl$collect$1:L$4	Ljava/lang/Object;
    //   751: aload 8
    //   753: astore_2
    //   754: aload 7
    //   756: astore_1
    //   757: aload 13
    //   759: iconst_3
    //   760: putfield 111	kotlinx/coroutines/flow/StateFlowImpl$collect$1:label	I
    //   763: aload 8
    //   765: astore_2
    //   766: aload 7
    //   768: astore_1
    //   769: aload 8
    //   771: aload 13
    //   773: invokevirtual 203	kotlinx/coroutines/flow/StateFlowSlot:awaitPending	(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    //   776: astore 11
    //   778: aload 12
    //   780: astore 5
    //   782: aload 8
    //   784: astore_3
    //   785: aload 10
    //   787: astore 6
    //   789: aload 7
    //   791: astore 4
    //   793: aload 11
    //   795: aload 15
    //   797: if_acmpne +6 -> 803
    //   800: aload 15
    //   802: areturn
    //   803: aload 9
    //   805: astore 11
    //   807: goto -361 -> 446
    //   810: astore_3
    //   811: aload_1
    //   812: aload_2
    //   813: checkcast 205	kotlinx/coroutines/flow/internal/AbstractSharedFlowSlot
    //   816: invokevirtual 209	kotlinx/coroutines/flow/StateFlowImpl:freeSlot	(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V
    //   819: aload_3
    //   820: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	821	0	this	StateFlowImpl
    //   0	821	1	paramFlowCollector	FlowCollector<? super T>
    //   0	821	2	paramContinuation	Continuation<?>
    //   135	650	3	localObject1	Object
    //   810	10	3	localObject2	Object
    //   154	638	4	localObject3	Object
    //   125	656	5	localObject4	Object
    //   144	644	6	localObject5	Object
    //   174	616	7	localObject6	Object
    //   56	727	8	localObject7	Object
    //   115	689	9	localObject8	Object
    //   490	296	10	localObject9	Object
    //   444	362	11	localObject10	Object
    //   483	296	12	localObject11	Object
    //   11	761	13	local1	collect.1
    //   456	177	14	localObject12	Object
    //   61	740	15	localObject13	Object
    // Exception table:
    //   from	to	target	type
    //   161	166	810	finally
    //   220	225	810	finally
    //   265	270	810	finally
    //   311	319	810	finally
    //   325	332	810	finally
    //   338	345	810	finally
    //   351	358	810	finally
    //   364	371	810	finally
    //   377	383	810	finally
    //   400	412	810	finally
    //   420	443	810	finally
    //   451	458	810	finally
    //   471	476	810	finally
    //   505	515	810	finally
    //   520	528	810	finally
    //   543	550	810	finally
    //   555	562	810	finally
    //   567	573	810	finally
    //   578	585	810	finally
    //   590	597	810	finally
    //   602	608	810	finally
    //   613	629	810	finally
    //   678	686	810	finally
    //   692	699	810	finally
    //   705	712	810	finally
    //   718	725	810	finally
    //   731	738	810	finally
    //   744	751	810	finally
    //   757	763	810	finally
    //   769	778	810	finally
  }
  
  public boolean compareAndSet(T paramT1, T paramT2)
  {
    if (paramT1 == null) {
      paramT1 = NullSurrogateKt.NULL;
    }
    if (paramT2 == null) {
      paramT2 = NullSurrogateKt.NULL;
    }
    return updateState(paramT1, paramT2);
  }
  
  protected StateFlowSlot createSlot()
  {
    return new StateFlowSlot();
  }
  
  protected StateFlowSlot[] createSlotArray(int paramInt)
  {
    return new StateFlowSlot[paramInt];
  }
  
  public Object emit(T paramT, Continuation<? super Unit> paramContinuation)
  {
    setValue(paramT);
    return Unit.INSTANCE;
  }
  
  public Flow<T> fuse(CoroutineContext paramCoroutineContext, int paramInt, BufferOverflow paramBufferOverflow)
  {
    return StateFlowKt.fuseStateFlow((StateFlow)this, paramCoroutineContext, paramInt, paramBufferOverflow);
  }
  
  public List<T> getReplayCache()
  {
    return CollectionsKt.listOf(getValue());
  }
  
  public T getValue()
  {
    Symbol localSymbol = NullSurrogateKt.NULL;
    Object localObject2 = this._state;
    Object localObject1 = localObject2;
    if (localObject2 == localSymbol) {
      localObject1 = null;
    }
    return (T)localObject1;
  }
  
  public void resetReplayCache()
  {
    throw new UnsupportedOperationException("MutableStateFlow.resetReplayCache is not supported");
  }
  
  public void setValue(T paramT)
  {
    if (paramT == null) {
      paramT = NullSurrogateKt.NULL;
    }
    updateState(null, paramT);
  }
  
  public boolean tryEmit(T paramT)
  {
    setValue(paramT);
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/StateFlowImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */