package androidx.dynamicanimation.animation;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

class AnimationHandler
{
  private static final long FRAME_DELAY_MS = 10L;
  public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal();
  final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList();
  private final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
  long mCurrentFrameTime = 0L;
  private final SimpleArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new SimpleArrayMap();
  private boolean mListDirty = false;
  private AnimationFrameCallbackProvider mProvider;
  
  private void cleanUpList()
  {
    if (this.mListDirty)
    {
      for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; i--) {
        if (this.mAnimationCallbacks.get(i) == null) {
          this.mAnimationCallbacks.remove(i);
        }
      }
      this.mListDirty = false;
    }
  }
  
  public static long getFrameTime()
  {
    ThreadLocal localThreadLocal = sAnimatorHandler;
    if (localThreadLocal.get() == null) {
      return 0L;
    }
    return ((AnimationHandler)localThreadLocal.get()).mCurrentFrameTime;
  }
  
  public static AnimationHandler getInstance()
  {
    ThreadLocal localThreadLocal = sAnimatorHandler;
    if (localThreadLocal.get() == null) {
      localThreadLocal.set(new AnimationHandler());
    }
    return (AnimationHandler)localThreadLocal.get();
  }
  
  private boolean isCallbackDue(AnimationFrameCallback paramAnimationFrameCallback, long paramLong)
  {
    Long localLong = (Long)this.mDelayedCallbackStartTime.get(paramAnimationFrameCallback);
    if (localLong == null) {
      return true;
    }
    if (localLong.longValue() < paramLong)
    {
      this.mDelayedCallbackStartTime.remove(paramAnimationFrameCallback);
      return true;
    }
    return false;
  }
  
  public void addAnimationFrameCallback(AnimationFrameCallback paramAnimationFrameCallback, long paramLong)
  {
    if (this.mAnimationCallbacks.size() == 0) {
      getProvider().postFrameCallback();
    }
    if (!this.mAnimationCallbacks.contains(paramAnimationFrameCallback)) {
      this.mAnimationCallbacks.add(paramAnimationFrameCallback);
    }
    if (paramLong > 0L) {
      this.mDelayedCallbackStartTime.put(paramAnimationFrameCallback, Long.valueOf(SystemClock.uptimeMillis() + paramLong));
    }
  }
  
  void doAnimationFrame(long paramLong)
  {
    long l = SystemClock.uptimeMillis();
    for (int i = 0; i < this.mAnimationCallbacks.size(); i++)
    {
      AnimationFrameCallback localAnimationFrameCallback = (AnimationFrameCallback)this.mAnimationCallbacks.get(i);
      if ((localAnimationFrameCallback != null) && (isCallbackDue(localAnimationFrameCallback, l))) {
        localAnimationFrameCallback.doAnimationFrame(paramLong);
      }
    }
    cleanUpList();
  }
  
  AnimationFrameCallbackProvider getProvider()
  {
    if (this.mProvider == null) {
      if (Build.VERSION.SDK_INT >= 16) {
        this.mProvider = new FrameCallbackProvider16(this.mCallbackDispatcher);
      } else {
        this.mProvider = new FrameCallbackProvider14(this.mCallbackDispatcher);
      }
    }
    return this.mProvider;
  }
  
  public void removeCallback(AnimationFrameCallback paramAnimationFrameCallback)
  {
    this.mDelayedCallbackStartTime.remove(paramAnimationFrameCallback);
    int i = this.mAnimationCallbacks.indexOf(paramAnimationFrameCallback);
    if (i >= 0)
    {
      this.mAnimationCallbacks.set(i, null);
      this.mListDirty = true;
    }
  }
  
  public void setProvider(AnimationFrameCallbackProvider paramAnimationFrameCallbackProvider)
  {
    this.mProvider = paramAnimationFrameCallbackProvider;
  }
  
  class AnimationCallbackDispatcher
  {
    AnimationCallbackDispatcher() {}
    
    void dispatchAnimationFrame()
    {
      AnimationHandler.this.mCurrentFrameTime = SystemClock.uptimeMillis();
      AnimationHandler localAnimationHandler = AnimationHandler.this;
      localAnimationHandler.doAnimationFrame(localAnimationHandler.mCurrentFrameTime);
      if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
        AnimationHandler.this.getProvider().postFrameCallback();
      }
    }
  }
  
  static abstract interface AnimationFrameCallback
  {
    public abstract boolean doAnimationFrame(long paramLong);
  }
  
  static abstract class AnimationFrameCallbackProvider
  {
    final AnimationHandler.AnimationCallbackDispatcher mDispatcher;
    
    AnimationFrameCallbackProvider(AnimationHandler.AnimationCallbackDispatcher paramAnimationCallbackDispatcher)
    {
      this.mDispatcher = paramAnimationCallbackDispatcher;
    }
    
    abstract void postFrameCallback();
  }
  
  private static class FrameCallbackProvider14
    extends AnimationHandler.AnimationFrameCallbackProvider
  {
    private final Handler mHandler = new Handler(Looper.myLooper());
    long mLastFrameTime = -1L;
    private final Runnable mRunnable = new Runnable()
    {
      public void run()
      {
        AnimationHandler.FrameCallbackProvider14.this.mLastFrameTime = SystemClock.uptimeMillis();
        AnimationHandler.FrameCallbackProvider14.this.mDispatcher.dispatchAnimationFrame();
      }
    };
    
    FrameCallbackProvider14(AnimationHandler.AnimationCallbackDispatcher paramAnimationCallbackDispatcher)
    {
      super();
    }
    
    void postFrameCallback()
    {
      long l = Math.max(10L - (SystemClock.uptimeMillis() - this.mLastFrameTime), 0L);
      this.mHandler.postDelayed(this.mRunnable, l);
    }
  }
  
  private static class FrameCallbackProvider16
    extends AnimationHandler.AnimationFrameCallbackProvider
  {
    private final Choreographer mChoreographer = Choreographer.getInstance();
    private final Choreographer.FrameCallback mChoreographerCallback = new Choreographer.FrameCallback()
    {
      public void doFrame(long paramAnonymousLong)
      {
        AnimationHandler.FrameCallbackProvider16.this.mDispatcher.dispatchAnimationFrame();
      }
    };
    
    FrameCallbackProvider16(AnimationHandler.AnimationCallbackDispatcher paramAnimationCallbackDispatcher)
    {
      super();
    }
    
    void postFrameCallback()
    {
      this.mChoreographer.postFrameCallback(this.mChoreographerCallback);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/dynamicanimation/animation/AnimationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */