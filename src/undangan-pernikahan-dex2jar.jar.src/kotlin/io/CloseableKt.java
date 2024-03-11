package kotlin.io;

import kotlin.Metadata;

@Metadata(d1={"\000\034\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\003\n\002\b\004\n\002\030\002\n\002\b\003\032\030\020\000\032\0020\001*\004\030\0010\0022\b\020\003\032\004\030\0010\004H\001\032K\020\005\032\002H\006\"\n\b\000\020\007*\004\030\0010\002\"\004\b\001\020\006*\002H\0072\022\020\b\032\016\022\004\022\002H\007\022\004\022\002H\0060\tH\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\n\002\017\n\005\b20\001\n\006\b\021(\0130\001¨\006\f"}, d2={"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Requires newer compiler version to be inlined correctly.", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CloseableKt
{
  /* Error */
  public static final void closeFinally(java.io.Closeable paramCloseable, Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +31 -> 32
    //   4: aload_1
    //   5: ifnonnull +12 -> 17
    //   8: aload_0
    //   9: invokeinterface 34 1 0
    //   14: goto +18 -> 32
    //   17: aload_0
    //   18: invokeinterface 34 1 0
    //   23: goto +9 -> 32
    //   26: astore_0
    //   27: aload_1
    //   28: aload_0
    //   29: invokestatic 40	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   32: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	33	0	paramCloseable	java.io.Closeable
    //   0	33	1	paramThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   17	23	26	finally
  }
  
  /* Error */
  private static final <T extends java.io.Closeable, R> R use(T paramT, kotlin.jvm.functions.Function1<? super T, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 43
    //   3: invokestatic 49	kotlin/jvm/internal/Intrinsics:checkNotNullParameter	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: aload_0
    //   8: invokeinterface 55 2 0
    //   13: astore_1
    //   14: iconst_1
    //   15: invokestatic 61	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   18: iconst_1
    //   19: iconst_1
    //   20: iconst_0
    //   21: invokestatic 67	kotlin/internal/PlatformImplementationsKt:apiVersionIsAtLeast	(III)Z
    //   24: ifeq +11 -> 35
    //   27: aload_0
    //   28: aconst_null
    //   29: invokestatic 69	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   32: goto +13 -> 45
    //   35: aload_0
    //   36: ifnull +9 -> 45
    //   39: aload_0
    //   40: invokeinterface 34 1 0
    //   45: iconst_1
    //   46: invokestatic 72	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   49: aload_1
    //   50: areturn
    //   51: astore_2
    //   52: aload_2
    //   53: athrow
    //   54: astore_1
    //   55: iconst_1
    //   56: invokestatic 61	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   59: iconst_1
    //   60: iconst_1
    //   61: iconst_0
    //   62: invokestatic 67	kotlin/internal/PlatformImplementationsKt:apiVersionIsAtLeast	(III)Z
    //   65: ifne +20 -> 85
    //   68: aload_0
    //   69: ifnull +21 -> 90
    //   72: aload_0
    //   73: invokeinterface 34 1 0
    //   78: goto +12 -> 90
    //   81: astore_0
    //   82: goto +8 -> 90
    //   85: aload_0
    //   86: aload_2
    //   87: invokestatic 69	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   90: iconst_1
    //   91: invokestatic 72	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   94: aload_1
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	paramT	T
    //   0	96	1	paramFunction1	kotlin.jvm.functions.Function1<? super T, ? extends R>
    //   51	36	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   6	14	51	finally
    //   52	54	54	finally
    //   72	78	81	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/CloseableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */