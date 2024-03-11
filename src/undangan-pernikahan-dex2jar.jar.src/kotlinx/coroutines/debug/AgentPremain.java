package kotlinx.coroutines.debug;

import java.io.PrintStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.debug.internal.AgentInstallationType;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import sun.misc.Signal;

@Metadata(d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\000\n\002\020\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\002\bÁ\002\030\0002\0020\001:\001\fB\007\b\002¢\006\002\020\002J\b\020\005\032\0020\006H\002J\032\020\007\032\0020\0062\b\020\b\032\004\030\0010\t2\006\020\n\032\0020\013H\007R\016\020\003\032\0020\004X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/debug/AgentPremain;", "", "()V", "enableCreationStackTraces", "", "installSignalHandler", "", "premain", "args", "", "instrumentation", "Ljava/lang/instrument/Instrumentation;", "DebugProbesTransformer", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class AgentPremain
{
  public static final AgentPremain INSTANCE = new AgentPremain();
  private static final boolean enableCreationStackTraces;
  
  static
  {
    Object localObject2 = null;
    try
    {
      localObject1 = Result.Companion;
      localObject1 = System.getProperty("kotlinx.coroutines.debug.enable.creation.stack.trace");
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = Boolean.valueOf(Boolean.parseBoolean((String)localObject1));
      }
      localObject1 = Result.constructor-impl(localObject1);
    }
    finally
    {
      localObject1 = Result.Companion;
      localObject1 = Result.constructor-impl(ResultKt.createFailure(localThrowable));
    }
    if (Result.isFailure-impl(localObject1)) {
      localObject1 = localObject2;
    }
    Object localObject1 = (Boolean)localObject1;
    boolean bool;
    if (localObject1 == null) {
      bool = DebugProbesImpl.INSTANCE.getEnableCreationStackTraces();
    } else {
      bool = ((Boolean)localObject1).booleanValue();
    }
    enableCreationStackTraces = bool;
  }
  
  /* Error */
  private final void installSignalHandler()
  {
    // Byte code:
    //   0: new 109	sun/misc/Signal
    //   3: astore_1
    //   4: aload_1
    //   5: ldc 111
    //   7: invokespecial 114	sun/misc/Signal:<init>	(Ljava/lang/String;)V
    //   10: new 116	kotlinx/coroutines/debug/AgentPremain$$ExternalSyntheticLambda0
    //   13: astore_2
    //   14: aload_2
    //   15: invokespecial 117	kotlinx/coroutines/debug/AgentPremain$$ExternalSyntheticLambda0:<init>	()V
    //   18: aload_1
    //   19: aload_2
    //   20: invokestatic 121	sun/misc/Signal:handle	(Lsun/misc/Signal;Lsun/misc/SignalHandler;)Lsun/misc/SignalHandler;
    //   23: pop
    //   24: goto +4 -> 28
    //   27: astore_1
    //   28: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	29	0	this	AgentPremain
    //   3	16	1	localSignal	Signal
    //   27	1	1	localObject	Object
    //   13	7	2	localExternalSyntheticLambda0	AgentPremain..ExternalSyntheticLambda0
    // Exception table:
    //   from	to	target	type
    //   0	24	27	finally
  }
  
  private static final void installSignalHandler$lambda-1(Signal paramSignal)
  {
    if (DebugProbesImpl.INSTANCE.isInstalled$kotlinx_coroutines_core()) {
      DebugProbesImpl.INSTANCE.dumpCoroutines(System.out);
    } else {
      System.out.println("Cannot perform coroutines dump, debug probes are disabled");
    }
  }
  
  @JvmStatic
  public static final void premain(String paramString, Instrumentation paramInstrumentation)
  {
    AgentInstallationType.INSTANCE.setInstalledStatically$kotlinx_coroutines_core(true);
    paramInstrumentation.addTransformer((ClassFileTransformer)DebugProbesTransformer.INSTANCE);
    DebugProbesImpl.INSTANCE.setEnableCreationStackTraces(enableCreationStackTraces);
    DebugProbesImpl.INSTANCE.install();
    INSTANCE.installSignalHandler();
  }
  
  @Metadata(d1={"\000,\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\022\n\000\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J:\020\003\032\004\030\0010\0042\006\020\005\032\0020\0062\006\020\007\032\0020\b2\f\020\t\032\b\022\002\b\003\030\0010\n2\006\020\013\032\0020\f2\b\020\r\032\004\030\0010\004H\026¨\006\016"}, d2={"Lkotlinx/coroutines/debug/AgentPremain$DebugProbesTransformer;", "Ljava/lang/instrument/ClassFileTransformer;", "()V", "transform", "", "loader", "Ljava/lang/ClassLoader;", "className", "", "classBeingRedefined", "Ljava/lang/Class;", "protectionDomain", "Ljava/security/ProtectionDomain;", "classfileBuffer", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class DebugProbesTransformer
    implements ClassFileTransformer
  {
    public static final DebugProbesTransformer INSTANCE = new DebugProbesTransformer();
    
    public byte[] transform(ClassLoader paramClassLoader, String paramString, Class<?> paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte)
    {
      if (!Intrinsics.areEqual(paramString, "kotlin/coroutines/jvm/internal/DebugProbesKt")) {
        return null;
      }
      AgentInstallationType.INSTANCE.setInstalledStatically$kotlinx_coroutines_core(true);
      return ByteStreamsKt.readBytes(paramClassLoader.getResourceAsStream("DebugProbesKt.bin"));
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/AgentPremain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */