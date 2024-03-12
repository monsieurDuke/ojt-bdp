package androidx.work.impl.utils.futures;

import com.google.common.util.concurrent.ListenableFuture;

public final class SettableFuture<V>
  extends AbstractFuture<V>
{
  public static <V> SettableFuture<V> create()
  {
    return new SettableFuture();
  }
  
  public boolean set(V paramV)
  {
    return super.set(paramV);
  }
  
  public boolean setException(Throwable paramThrowable)
  {
    return super.setException(paramThrowable);
  }
  
  public boolean setFuture(ListenableFuture<? extends V> paramListenableFuture)
  {
    return super.setFuture(paramListenableFuture);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/impl/utils/futures/SettableFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */