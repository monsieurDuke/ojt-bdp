package kotlin.system;

import kotlin.Metadata;

@Metadata(d1={"\000\016\n\000\n\002\020\001\n\000\n\002\020\b\n\000\032\021\020\000\032\0020\0012\006\020\002\032\0020\003H\b¨\006\004"}, d2={"exitProcess", "", "status", "", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ProcessKt
{
  private static final Void exitProcess(int paramInt)
  {
    System.exit(paramInt);
    throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/system/ProcessKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */