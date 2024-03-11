package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;

public class ProcessLifecycleOwner
  implements LifecycleOwner
{
  static final long TIMEOUT_MS = 700L;
  private static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
  private Runnable mDelayedPauseRunnable = new Runnable()
  {
    public void run()
    {
      ProcessLifecycleOwner.this.dispatchPauseIfNeeded();
      ProcessLifecycleOwner.this.dispatchStopIfNeeded();
    }
  };
  private Handler mHandler;
  ReportFragment.ActivityInitializationListener mInitializationListener = new ReportFragment.ActivityInitializationListener()
  {
    public void onCreate() {}
    
    public void onResume()
    {
      ProcessLifecycleOwner.this.activityResumed();
    }
    
    public void onStart()
    {
      ProcessLifecycleOwner.this.activityStarted();
    }
  };
  private boolean mPauseSent = true;
  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
  private int mResumedCounter = 0;
  private int mStartedCounter = 0;
  private boolean mStopSent = true;
  
  public static LifecycleOwner get()
  {
    return sInstance;
  }
  
  static void init(Context paramContext)
  {
    sInstance.attach(paramContext);
  }
  
  void activityPaused()
  {
    int i = this.mResumedCounter - 1;
    this.mResumedCounter = i;
    if (i == 0) {
      this.mHandler.postDelayed(this.mDelayedPauseRunnable, 700L);
    }
  }
  
  void activityResumed()
  {
    int i = this.mResumedCounter + 1;
    this.mResumedCounter = i;
    if (i == 1) {
      if (this.mPauseSent)
      {
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        this.mPauseSent = false;
      }
      else
      {
        this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
      }
    }
  }
  
  void activityStarted()
  {
    int i = this.mStartedCounter + 1;
    this.mStartedCounter = i;
    if ((i == 1) && (this.mStopSent))
    {
      this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      this.mStopSent = false;
    }
  }
  
  void activityStopped()
  {
    this.mStartedCounter -= 1;
    dispatchStopIfNeeded();
  }
  
  void attach(Context paramContext)
  {
    this.mHandler = new Handler();
    this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    ((Application)paramContext.getApplicationContext()).registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks()
    {
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        if (Build.VERSION.SDK_INT < 29) {
          ReportFragment.get(paramAnonymousActivity).setProcessListener(ProcessLifecycleOwner.this.mInitializationListener);
        }
      }
      
      public void onActivityPaused(Activity paramAnonymousActivity)
      {
        ProcessLifecycleOwner.this.activityPaused();
      }
      
      public void onActivityPreCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        paramAnonymousActivity.registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks()
        {
          public void onActivityPostResumed(Activity paramAnonymous2Activity)
          {
            ProcessLifecycleOwner.this.activityResumed();
          }
          
          public void onActivityPostStarted(Activity paramAnonymous2Activity)
          {
            ProcessLifecycleOwner.this.activityStarted();
          }
        });
      }
      
      public void onActivityStopped(Activity paramAnonymousActivity)
      {
        ProcessLifecycleOwner.this.activityStopped();
      }
    });
  }
  
  void dispatchPauseIfNeeded()
  {
    if (this.mResumedCounter == 0)
    {
      this.mPauseSent = true;
      this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }
  }
  
  void dispatchStopIfNeeded()
  {
    if ((this.mStartedCounter == 0) && (this.mPauseSent))
    {
      this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
      this.mStopSent = true;
    }
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mRegistry;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ProcessLifecycleOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */