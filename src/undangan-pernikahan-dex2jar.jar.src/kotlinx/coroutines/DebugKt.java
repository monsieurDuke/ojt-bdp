package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(d1={"\000(\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\007\n\002\020\002\n\000\n\002\030\002\n\002\b\002\032\027\020\022\032\0020\0232\f\020\024\032\b\022\004\022\0020\0010\025H\b\032\b\020\026\032\0020\023H\000\"\024\020\000\032\0020\001X\004¢\006\b\n\000\032\004\b\002\020\003\"\024\020\004\032\0020\005X\004¢\006\b\n\000\032\004\b\006\020\007\"\024\020\b\032\0020\001X\004¢\006\b\n\000\032\004\b\t\020\003\"\016\020\n\032\0020\013XT¢\006\002\n\000\"\016\020\f\032\0020\013XT¢\006\002\n\000\"\016\020\r\032\0020\013XT¢\006\002\n\000\"\016\020\016\032\0020\013XT¢\006\002\n\000\"\024\020\017\032\0020\001X\004¢\006\b\n\000\032\004\b\020\020\003\"\016\020\021\032\0020\013XT¢\006\002\n\000¨\006\027"}, d2={"ASSERTIONS_ENABLED", "", "getASSERTIONS_ENABLED", "()Z", "COROUTINE_ID", "Ljava/util/concurrent/atomic/AtomicLong;", "getCOROUTINE_ID", "()Ljava/util/concurrent/atomic/AtomicLong;", "DEBUG", "getDEBUG", "DEBUG_PROPERTY_NAME", "", "DEBUG_PROPERTY_VALUE_AUTO", "DEBUG_PROPERTY_VALUE_OFF", "DEBUG_PROPERTY_VALUE_ON", "RECOVER_STACK_TRACES", "getRECOVER_STACK_TRACES", "STACKTRACE_RECOVERY_PROPERTY_NAME", "assert", "", "value", "Lkotlin/Function0;", "resetCoroutineId", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class DebugKt
{
  private static final boolean ASSERTIONS_ENABLED;
  private static final AtomicLong COROUTINE_ID = new AtomicLong(0L);
  private static final boolean DEBUG;
  public static final String DEBUG_PROPERTY_NAME = "kotlinx.coroutines.debug";
  public static final String DEBUG_PROPERTY_VALUE_AUTO = "auto";
  public static final String DEBUG_PROPERTY_VALUE_OFF = "off";
  public static final String DEBUG_PROPERTY_VALUE_ON = "on";
  private static final boolean RECOVER_STACK_TRACES;
  public static final String STACKTRACE_RECOVERY_PROPERTY_NAME = "kotlinx.coroutines.stacktrace.recovery";
  
  /* Error */
  static
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: iconst_0
    //   3: putstatic 54	kotlinx/coroutines/DebugKt:ASSERTIONS_ENABLED	Z
    //   6: ldc 42
    //   8: invokestatic 60	kotlinx/coroutines/internal/SystemPropsKt:systemProp	(Ljava/lang/String;)Ljava/lang/String;
    //   11: astore_3
    //   12: aload_3
    //   13: invokestatic 66	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   16: aload_3
    //   17: invokestatic 69	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   20: aload_3
    //   21: invokestatic 72	mt/Log229316:a	(Ljava/lang/Object;)V
    //   24: aload_3
    //   25: ifnull +141 -> 166
    //   28: aload_3
    //   29: invokevirtual 78	java/lang/String:hashCode	()I
    //   32: lookupswitch	default:+44->76, 0:+85->117, 3551:+73->105, 109935:+59->91, 3005871:+47->79
    //   76: goto +55 -> 131
    //   79: aload_3
    //   80: ldc 44
    //   82: invokevirtual 82	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   85: ifeq -9 -> 76
    //   88: goto +78 -> 166
    //   91: aload_3
    //   92: ldc 46
    //   94: invokevirtual 82	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   97: ifeq -21 -> 76
    //   100: iconst_0
    //   101: istore_0
    //   102: goto +68 -> 170
    //   105: aload_3
    //   106: ldc 48
    //   108: invokevirtual 82	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   111: ifeq -35 -> 76
    //   114: goto +12 -> 126
    //   117: aload_3
    //   118: ldc 83
    //   120: invokevirtual 82	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   123: ifeq -47 -> 76
    //   126: iconst_1
    //   127: istore_0
    //   128: goto +42 -> 170
    //   131: new 85	java/lang/IllegalStateException
    //   134: dup
    //   135: new 87	java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   142: ldc 92
    //   144: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: aload_3
    //   148: invokevirtual 99	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   151: bipush 39
    //   153: invokevirtual 102	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   156: invokevirtual 106	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokevirtual 107	java/lang/Object:toString	()Ljava/lang/String;
    //   162: invokespecial 110	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   165: athrow
    //   166: invokestatic 112	kotlinx/coroutines/DebugKt:getASSERTIONS_ENABLED	()Z
    //   169: istore_0
    //   170: iload_0
    //   171: putstatic 114	kotlinx/coroutines/DebugKt:DEBUG	Z
    //   174: iload_2
    //   175: istore_1
    //   176: iload_0
    //   177: ifeq +16 -> 193
    //   180: iload_2
    //   181: istore_1
    //   182: ldc 50
    //   184: iconst_1
    //   185: invokestatic 117	kotlinx/coroutines/internal/SystemPropsKt:systemProp	(Ljava/lang/String;Z)Z
    //   188: ifeq +5 -> 193
    //   191: iconst_1
    //   192: istore_1
    //   193: iload_1
    //   194: putstatic 119	kotlinx/coroutines/DebugKt:RECOVER_STACK_TRACES	Z
    //   197: new 121	java/util/concurrent/atomic/AtomicLong
    //   200: dup
    //   201: lconst_0
    //   202: invokespecial 124	java/util/concurrent/atomic/AtomicLong:<init>	(J)V
    //   205: putstatic 126	kotlinx/coroutines/DebugKt:COROUTINE_ID	Ljava/util/concurrent/atomic/AtomicLong;
    //   208: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   101	76	0	bool1	boolean
    //   175	19	1	bool2	boolean
    //   1	180	2	bool3	boolean
    //   11	137	3	str	String
  }
  
  private static final void jdMethod_assert(Function0<Boolean> paramFunction0)
  {
    if ((getASSERTIONS_ENABLED()) && (!((Boolean)paramFunction0.invoke()).booleanValue())) {
      throw new AssertionError();
    }
  }
  
  public static final boolean getASSERTIONS_ENABLED()
  {
    return ASSERTIONS_ENABLED;
  }
  
  public static final AtomicLong getCOROUTINE_ID()
  {
    return COROUTINE_ID;
  }
  
  public static final boolean getDEBUG()
  {
    return DEBUG;
  }
  
  public static final boolean getRECOVER_STACK_TRACES()
  {
    return RECOVER_STACK_TRACES;
  }
  
  public static final void resetCoroutineId()
  {
    COROUTINE_ID.set(0L);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/DebugKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */