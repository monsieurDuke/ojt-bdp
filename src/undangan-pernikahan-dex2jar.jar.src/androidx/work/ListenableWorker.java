package androidx.work;

import android.content.Context;
import android.net.Network;
import android.net.Uri;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

public abstract class ListenableWorker
{
  private Context mAppContext;
  private boolean mRunInForeground;
  private volatile boolean mStopped;
  private boolean mUsed;
  private WorkerParameters mWorkerParams;
  
  public ListenableWorker(Context paramContext, WorkerParameters paramWorkerParameters)
  {
    if (paramContext != null)
    {
      if (paramWorkerParameters != null)
      {
        this.mAppContext = paramContext;
        this.mWorkerParams = paramWorkerParameters;
        return;
      }
      throw new IllegalArgumentException("WorkerParameters is null");
    }
    throw new IllegalArgumentException("Application Context is null");
  }
  
  public final Context getApplicationContext()
  {
    return this.mAppContext;
  }
  
  public Executor getBackgroundExecutor()
  {
    return this.mWorkerParams.getBackgroundExecutor();
  }
  
  public ListenableFuture<ForegroundInfo> getForegroundInfoAsync()
  {
    SettableFuture localSettableFuture = SettableFuture.create();
    localSettableFuture.setException(new IllegalStateException("Expedited WorkRequests require a ListenableWorker to provide an implementation for `getForegroundInfoAsync()`"));
    return localSettableFuture;
  }
  
  public final UUID getId()
  {
    return this.mWorkerParams.getId();
  }
  
  public final Data getInputData()
  {
    return this.mWorkerParams.getInputData();
  }
  
  public final Network getNetwork()
  {
    return this.mWorkerParams.getNetwork();
  }
  
  public final int getRunAttemptCount()
  {
    return this.mWorkerParams.getRunAttemptCount();
  }
  
  public final Set<String> getTags()
  {
    return this.mWorkerParams.getTags();
  }
  
  public TaskExecutor getTaskExecutor()
  {
    return this.mWorkerParams.getTaskExecutor();
  }
  
  public final List<String> getTriggeredContentAuthorities()
  {
    return this.mWorkerParams.getTriggeredContentAuthorities();
  }
  
  public final List<Uri> getTriggeredContentUris()
  {
    return this.mWorkerParams.getTriggeredContentUris();
  }
  
  public WorkerFactory getWorkerFactory()
  {
    return this.mWorkerParams.getWorkerFactory();
  }
  
  public boolean isRunInForeground()
  {
    return this.mRunInForeground;
  }
  
  public final boolean isStopped()
  {
    return this.mStopped;
  }
  
  public final boolean isUsed()
  {
    return this.mUsed;
  }
  
  public void onStopped() {}
  
  public final ListenableFuture<Void> setForegroundAsync(ForegroundInfo paramForegroundInfo)
  {
    this.mRunInForeground = true;
    return this.mWorkerParams.getForegroundUpdater().setForegroundAsync(getApplicationContext(), getId(), paramForegroundInfo);
  }
  
  public ListenableFuture<Void> setProgressAsync(Data paramData)
  {
    return this.mWorkerParams.getProgressUpdater().updateProgress(getApplicationContext(), getId(), paramData);
  }
  
  public void setRunInForeground(boolean paramBoolean)
  {
    this.mRunInForeground = paramBoolean;
  }
  
  public final void setUsed()
  {
    this.mUsed = true;
  }
  
  public abstract ListenableFuture<Result> startWork();
  
  public final void stop()
  {
    this.mStopped = true;
    onStopped();
  }
  
  public static abstract class Result
  {
    public static Result failure()
    {
      return new Failure();
    }
    
    public static Result failure(Data paramData)
    {
      return new Failure(paramData);
    }
    
    public static Result retry()
    {
      return new Retry();
    }
    
    public static Result success()
    {
      return new Success();
    }
    
    public static Result success(Data paramData)
    {
      return new Success(paramData);
    }
    
    public abstract Data getOutputData();
    
    public static final class Failure
      extends ListenableWorker.Result
    {
      private final Data mOutputData;
      
      public Failure()
      {
        this(Data.EMPTY);
      }
      
      public Failure(Data paramData)
      {
        this.mOutputData = paramData;
      }
      
      public boolean equals(Object paramObject)
      {
        if (this == paramObject) {
          return true;
        }
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
        {
          paramObject = (Failure)paramObject;
          return this.mOutputData.equals(((Failure)paramObject).mOutputData);
        }
        return false;
      }
      
      public Data getOutputData()
      {
        return this.mOutputData;
      }
      
      public int hashCode()
      {
        return Failure.class.getName().hashCode() * 31 + this.mOutputData.hashCode();
      }
      
      public String toString()
      {
        return "Failure {mOutputData=" + this.mOutputData + '}';
      }
    }
    
    public static final class Retry
      extends ListenableWorker.Result
    {
      public boolean equals(Object paramObject)
      {
        boolean bool = true;
        if (this == paramObject) {
          return true;
        }
        if ((paramObject == null) || (getClass() != paramObject.getClass())) {
          bool = false;
        }
        return bool;
      }
      
      public Data getOutputData()
      {
        return Data.EMPTY;
      }
      
      public int hashCode()
      {
        return Retry.class.getName().hashCode();
      }
      
      public String toString()
      {
        return "Retry";
      }
    }
    
    public static final class Success
      extends ListenableWorker.Result
    {
      private final Data mOutputData;
      
      public Success()
      {
        this(Data.EMPTY);
      }
      
      public Success(Data paramData)
      {
        this.mOutputData = paramData;
      }
      
      public boolean equals(Object paramObject)
      {
        if (this == paramObject) {
          return true;
        }
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
        {
          paramObject = (Success)paramObject;
          return this.mOutputData.equals(((Success)paramObject).mOutputData);
        }
        return false;
      }
      
      public Data getOutputData()
      {
        return this.mOutputData;
      }
      
      public int hashCode()
      {
        return Success.class.getName().hashCode() * 31 + this.mOutputData.hashCode();
      }
      
      public String toString()
      {
        return "Success {mOutputData=" + this.mOutputData + '}';
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/ListenableWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */