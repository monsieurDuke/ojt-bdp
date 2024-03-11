package androidx.room.util;

public class SneakyThrow
{
  public static void reThrow(Exception paramException)
  {
    sneakyThrow(paramException);
  }
  
  private static <E extends Throwable> void sneakyThrow(Throwable paramThrowable)
    throws Throwable
  {
    throw paramThrowable;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/SneakyThrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */