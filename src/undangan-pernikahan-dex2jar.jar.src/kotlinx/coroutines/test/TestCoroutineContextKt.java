package kotlinx.coroutines.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\030\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\032+\020\000\032\0020\0012\b\b\002\020\002\032\0020\0032\027\020\004\032\023\022\004\022\0020\003\022\004\022\0020\0010\005¢\006\002\b\006H\007¨\006\007"}, d2={"withTestContext", "", "testContext", "Lkotlinx/coroutines/test/TestCoroutineContext;", "testBody", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class TestCoroutineContextKt
{
  @Deprecated(level=DeprecationLevel.ERROR, message="This API has been deprecated to integrate with Structured Concurrency.", replaceWith=@ReplaceWith(expression="testContext.runBlockingTest(testBody)", imports={"kotlin.coroutines.test"}))
  public static final void withTestContext(TestCoroutineContext paramTestCoroutineContext, Function1<? super TestCoroutineContext, Unit> paramFunction1)
  {
    paramFunction1.invoke(paramTestCoroutineContext);
    paramFunction1 = (Iterable)paramTestCoroutineContext.getExceptions();
    boolean bool = paramFunction1 instanceof Collection;
    int i = 1;
    if ((!bool) || (!((Collection)paramFunction1).isEmpty()))
    {
      paramFunction1 = paramFunction1.iterator();
      while (paramFunction1.hasNext()) {
        if (!((Throwable)paramFunction1.next() instanceof CancellationException)) {
          i = 0;
        }
      }
    }
    if (i != 0) {
      return;
    }
    paramTestCoroutineContext = Intrinsics.stringPlus("Coroutine encountered unhandled exceptions:\n", paramTestCoroutineContext.getExceptions());
    Log5ECF72.a(paramTestCoroutineContext);
    LogE84000.a(paramTestCoroutineContext);
    Log229316.a(paramTestCoroutineContext);
    throw new AssertionError(paramTestCoroutineContext);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/test/TestCoroutineContextKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */