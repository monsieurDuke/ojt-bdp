package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;

@Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\013\n\002\b\005\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\032\020\003\032\0020\004X\016¢\006\016\n\000\032\004\b\005\020\006\"\004\b\007\020\b¨\006\t"}, d2={"Lkotlinx/coroutines/debug/internal/AgentInstallationType;", "", "()V", "isInstalledStatically", "", "isInstalledStatically$kotlinx_coroutines_core", "()Z", "setInstalledStatically$kotlinx_coroutines_core", "(Z)V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class AgentInstallationType
{
  public static final AgentInstallationType INSTANCE = new AgentInstallationType();
  private static boolean isInstalledStatically;
  
  public final boolean isInstalledStatically$kotlinx_coroutines_core()
  {
    return isInstalledStatically;
  }
  
  public final void setInstalledStatically$kotlinx_coroutines_core(boolean paramBoolean)
  {
    isInstalledStatically = paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/debug/internal/AgentInstallationType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */