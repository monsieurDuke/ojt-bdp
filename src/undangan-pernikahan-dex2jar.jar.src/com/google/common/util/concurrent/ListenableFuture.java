package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public abstract interface ListenableFuture<V>
  extends Future<V>
{
  public abstract void addListener(Runnable paramRunnable, Executor paramExecutor);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/common/util/concurrent/ListenableFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */