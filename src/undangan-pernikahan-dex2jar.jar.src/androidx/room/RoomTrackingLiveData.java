package androidx.room;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class RoomTrackingLiveData<T>
  extends LiveData<T>
{
  final Callable<T> mComputeFunction;
  final AtomicBoolean mComputing = new AtomicBoolean(false);
  private final InvalidationLiveDataContainer mContainer;
  final RoomDatabase mDatabase;
  final boolean mInTransaction;
  final AtomicBoolean mInvalid = new AtomicBoolean(true);
  final Runnable mInvalidationRunnable = new Runnable()
  {
    public void run()
    {
      boolean bool = RoomTrackingLiveData.this.hasActiveObservers();
      if ((RoomTrackingLiveData.this.mInvalid.compareAndSet(false, true)) && (bool)) {
        RoomTrackingLiveData.this.getQueryExecutor().execute(RoomTrackingLiveData.this.mRefreshRunnable);
      }
    }
  };
  final InvalidationTracker.Observer mObserver;
  final Runnable mRefreshRunnable = new Runnable()
  {
    public void run()
    {
      if (RoomTrackingLiveData.this.mRegisteredObserver.compareAndSet(false, true)) {
        RoomTrackingLiveData.this.mDatabase.getInvalidationTracker().addWeakObserver(RoomTrackingLiveData.this.mObserver);
      }
      int i;
      do
      {
        i = 0;
        int j = 0;
        if (RoomTrackingLiveData.this.mComputing.compareAndSet(false, true))
        {
          Object localObject1 = null;
          i = j;
          try
          {
            for (;;)
            {
              boolean bool = RoomTrackingLiveData.this.mInvalid.compareAndSet(true, false);
              if (bool)
              {
                i = 1;
                try
                {
                  localObject1 = RoomTrackingLiveData.this.mComputeFunction.call();
                }
                catch (Exception localException)
                {
                  RuntimeException localRuntimeException = new java/lang/RuntimeException;
                  localRuntimeException.<init>("Exception while computing database live data.", localException);
                  throw localRuntimeException;
                }
              }
            }
            if (i != 0) {
              RoomTrackingLiveData.this.postValue(localException);
            }
          }
          finally
          {
            RoomTrackingLiveData.this.mComputing.set(false);
          }
        }
      } while ((i != 0) && (RoomTrackingLiveData.this.mInvalid.get()));
    }
  };
  final AtomicBoolean mRegisteredObserver = new AtomicBoolean(false);
  
  RoomTrackingLiveData(RoomDatabase paramRoomDatabase, InvalidationLiveDataContainer paramInvalidationLiveDataContainer, boolean paramBoolean, Callable<T> paramCallable, String[] paramArrayOfString)
  {
    this.mDatabase = paramRoomDatabase;
    this.mInTransaction = paramBoolean;
    this.mComputeFunction = paramCallable;
    this.mContainer = paramInvalidationLiveDataContainer;
    this.mObserver = new InvalidationTracker.Observer(paramArrayOfString)
    {
      public void onInvalidated(Set<String> paramAnonymousSet)
      {
        ArchTaskExecutor.getInstance().executeOnMainThread(RoomTrackingLiveData.this.mInvalidationRunnable);
      }
    };
  }
  
  Executor getQueryExecutor()
  {
    if (this.mInTransaction) {
      return this.mDatabase.getTransactionExecutor();
    }
    return this.mDatabase.getQueryExecutor();
  }
  
  protected void onActive()
  {
    super.onActive();
    this.mContainer.onActive(this);
    getQueryExecutor().execute(this.mRefreshRunnable);
  }
  
  protected void onInactive()
  {
    super.onInactive();
    this.mContainer.onInactive(this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/RoomTrackingLiveData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */