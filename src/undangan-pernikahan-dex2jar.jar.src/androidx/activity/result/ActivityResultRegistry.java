package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public abstract class ActivityResultRegistry
{
  private static final int INITIAL_REQUEST_CODE_VALUE = 65536;
  private static final String KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS = "KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS";
  private static final String KEY_COMPONENT_ACTIVITY_PENDING_RESULTS = "KEY_COMPONENT_ACTIVITY_PENDING_RESULT";
  private static final String KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT = "KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT";
  private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS = "KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS";
  private static final String KEY_COMPONENT_ACTIVITY_REGISTERED_RCS = "KEY_COMPONENT_ACTIVITY_REGISTERED_RCS";
  private static final String LOG_TAG = "ActivityResultRegistry";
  final transient Map<String, CallbackAndContract<?>> mKeyToCallback = new HashMap();
  private final Map<String, LifecycleContainer> mKeyToLifecycleContainers = new HashMap();
  final Map<String, Integer> mKeyToRc = new HashMap();
  ArrayList<String> mLaunchedKeys = new ArrayList();
  final Map<String, Object> mParsedPendingResults = new HashMap();
  final Bundle mPendingResults = new Bundle();
  private Random mRandom = new Random();
  private final Map<Integer, String> mRcToKey = new HashMap();
  
  private void bindRcKey(int paramInt, String paramString)
  {
    this.mRcToKey.put(Integer.valueOf(paramInt), paramString);
    this.mKeyToRc.put(paramString, Integer.valueOf(paramInt));
  }
  
  private <O> void doDispatch(String paramString, int paramInt, Intent paramIntent, CallbackAndContract<O> paramCallbackAndContract)
  {
    if ((paramCallbackAndContract != null) && (paramCallbackAndContract.mCallback != null) && (this.mLaunchedKeys.contains(paramString)))
    {
      paramCallbackAndContract.mCallback.onActivityResult(paramCallbackAndContract.mContract.parseResult(paramInt, paramIntent));
      this.mLaunchedKeys.remove(paramString);
    }
    else
    {
      this.mParsedPendingResults.remove(paramString);
      this.mPendingResults.putParcelable(paramString, new ActivityResult(paramInt, paramIntent));
    }
  }
  
  private int generateRandomNumber()
  {
    for (int i = this.mRandom.nextInt(2147418112) + 65536; this.mRcToKey.containsKey(Integer.valueOf(i)); i = this.mRandom.nextInt(2147418112) + 65536) {}
    return i;
  }
  
  private void registerKey(String paramString)
  {
    if ((Integer)this.mKeyToRc.get(paramString) != null) {
      return;
    }
    bindRcKey(generateRandomNumber(), paramString);
  }
  
  public final boolean dispatchResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    String str = (String)this.mRcToKey.get(Integer.valueOf(paramInt1));
    if (str == null) {
      return false;
    }
    doDispatch(str, paramInt2, paramIntent, (CallbackAndContract)this.mKeyToCallback.get(str));
    return true;
  }
  
  public final <O> boolean dispatchResult(int paramInt, O paramO)
  {
    String str = (String)this.mRcToKey.get(Integer.valueOf(paramInt));
    if (str == null) {
      return false;
    }
    Object localObject = (CallbackAndContract)this.mKeyToCallback.get(str);
    if ((localObject != null) && (((CallbackAndContract)localObject).mCallback != null))
    {
      localObject = ((CallbackAndContract)localObject).mCallback;
      if (this.mLaunchedKeys.remove(str)) {
        ((ActivityResultCallback)localObject).onActivityResult(paramO);
      }
    }
    else
    {
      this.mPendingResults.remove(str);
      this.mParsedPendingResults.put(str, paramO);
    }
    return true;
  }
  
  public abstract <I, O> void onLaunch(int paramInt, ActivityResultContract<I, O> paramActivityResultContract, I paramI, ActivityOptionsCompat paramActivityOptionsCompat);
  
  public final void onRestoreInstanceState(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    ArrayList localArrayList1 = paramBundle.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
    ArrayList localArrayList2 = paramBundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
    if ((localArrayList2 != null) && (localArrayList1 != null))
    {
      this.mLaunchedKeys = paramBundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
      this.mRandom = ((Random)paramBundle.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT"));
      this.mPendingResults.putAll(paramBundle.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT"));
      for (int i = 0; i < localArrayList2.size(); i++)
      {
        paramBundle = (String)localArrayList2.get(i);
        if (this.mKeyToRc.containsKey(paramBundle))
        {
          Integer localInteger = (Integer)this.mKeyToRc.remove(paramBundle);
          if (!this.mPendingResults.containsKey(paramBundle)) {
            this.mRcToKey.remove(localInteger);
          }
        }
        bindRcKey(((Integer)localArrayList1.get(i)).intValue(), (String)localArrayList2.get(i));
      }
      return;
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList(this.mKeyToRc.values()));
    paramBundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList(this.mKeyToRc.keySet()));
    paramBundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList(this.mLaunchedKeys));
    paramBundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle)this.mPendingResults.clone());
    paramBundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", this.mRandom);
  }
  
  public final <I, O> ActivityResultLauncher<I> register(final String paramString, final ActivityResultContract<I, O> paramActivityResultContract, ActivityResultCallback<O> paramActivityResultCallback)
  {
    registerKey(paramString);
    this.mKeyToCallback.put(paramString, new CallbackAndContract(paramActivityResultCallback, paramActivityResultContract));
    if (this.mParsedPendingResults.containsKey(paramString))
    {
      localObject = this.mParsedPendingResults.get(paramString);
      this.mParsedPendingResults.remove(paramString);
      paramActivityResultCallback.onActivityResult(localObject);
    }
    Object localObject = (ActivityResult)this.mPendingResults.getParcelable(paramString);
    if (localObject != null)
    {
      this.mPendingResults.remove(paramString);
      paramActivityResultCallback.onActivityResult(paramActivityResultContract.parseResult(((ActivityResult)localObject).getResultCode(), ((ActivityResult)localObject).getData()));
    }
    new ActivityResultLauncher()
    {
      public ActivityResultContract<I, ?> getContract()
      {
        return paramActivityResultContract;
      }
      
      public void launch(I paramAnonymousI, ActivityOptionsCompat paramAnonymousActivityOptionsCompat)
      {
        Integer localInteger = (Integer)ActivityResultRegistry.this.mKeyToRc.get(paramString);
        if (localInteger != null)
        {
          ActivityResultRegistry.this.mLaunchedKeys.add(paramString);
          try
          {
            ActivityResultRegistry.this.onLaunch(localInteger.intValue(), paramActivityResultContract, paramAnonymousI, paramAnonymousActivityOptionsCompat);
            return;
          }
          catch (Exception paramAnonymousI)
          {
            ActivityResultRegistry.this.mLaunchedKeys.remove(paramString);
            throw paramAnonymousI;
          }
        }
        throw new IllegalStateException("Attempting to launch an unregistered ActivityResultLauncher with contract " + paramActivityResultContract + " and input " + paramAnonymousI + ". You must ensure the ActivityResultLauncher is registered before calling launch().");
      }
      
      public void unregister()
      {
        ActivityResultRegistry.this.unregister(paramString);
      }
    };
  }
  
  public final <I, O> ActivityResultLauncher<I> register(final String paramString, LifecycleOwner paramLifecycleOwner, final ActivityResultContract<I, O> paramActivityResultContract, final ActivityResultCallback<O> paramActivityResultCallback)
  {
    Lifecycle localLifecycle = paramLifecycleOwner.getLifecycle();
    if (!localLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED))
    {
      registerKey(paramString);
      LifecycleContainer localLifecycleContainer = (LifecycleContainer)this.mKeyToLifecycleContainers.get(paramString);
      paramLifecycleOwner = localLifecycleContainer;
      if (localLifecycleContainer == null) {
        paramLifecycleOwner = new LifecycleContainer(localLifecycle);
      }
      paramLifecycleOwner.addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          if (Lifecycle.Event.ON_START.equals(paramAnonymousEvent))
          {
            ActivityResultRegistry.this.mKeyToCallback.put(paramString, new ActivityResultRegistry.CallbackAndContract(paramActivityResultCallback, paramActivityResultContract));
            if (ActivityResultRegistry.this.mParsedPendingResults.containsKey(paramString))
            {
              paramAnonymousLifecycleOwner = ActivityResultRegistry.this.mParsedPendingResults.get(paramString);
              ActivityResultRegistry.this.mParsedPendingResults.remove(paramString);
              paramActivityResultCallback.onActivityResult(paramAnonymousLifecycleOwner);
            }
            paramAnonymousLifecycleOwner = (ActivityResult)ActivityResultRegistry.this.mPendingResults.getParcelable(paramString);
            if (paramAnonymousLifecycleOwner != null)
            {
              ActivityResultRegistry.this.mPendingResults.remove(paramString);
              paramActivityResultCallback.onActivityResult(paramActivityResultContract.parseResult(paramAnonymousLifecycleOwner.getResultCode(), paramAnonymousLifecycleOwner.getData()));
            }
          }
          else if (Lifecycle.Event.ON_STOP.equals(paramAnonymousEvent))
          {
            ActivityResultRegistry.this.mKeyToCallback.remove(paramString);
          }
          else if (Lifecycle.Event.ON_DESTROY.equals(paramAnonymousEvent))
          {
            ActivityResultRegistry.this.unregister(paramString);
          }
        }
      });
      this.mKeyToLifecycleContainers.put(paramString, paramLifecycleOwner);
      new ActivityResultLauncher()
      {
        public ActivityResultContract<I, ?> getContract()
        {
          return paramActivityResultContract;
        }
        
        public void launch(I paramAnonymousI, ActivityOptionsCompat paramAnonymousActivityOptionsCompat)
        {
          Integer localInteger = (Integer)ActivityResultRegistry.this.mKeyToRc.get(paramString);
          if (localInteger != null)
          {
            ActivityResultRegistry.this.mLaunchedKeys.add(paramString);
            try
            {
              ActivityResultRegistry.this.onLaunch(localInteger.intValue(), paramActivityResultContract, paramAnonymousI, paramAnonymousActivityOptionsCompat);
              return;
            }
            catch (Exception paramAnonymousI)
            {
              ActivityResultRegistry.this.mLaunchedKeys.remove(paramString);
              throw paramAnonymousI;
            }
          }
          throw new IllegalStateException("Attempting to launch an unregistered ActivityResultLauncher with contract " + paramActivityResultContract + " and input " + paramAnonymousI + ". You must ensure the ActivityResultLauncher is registered before calling launch().");
        }
        
        public void unregister()
        {
          ActivityResultRegistry.this.unregister(paramString);
        }
      };
    }
    throw new IllegalStateException("LifecycleOwner " + paramLifecycleOwner + " is attempting to register while current state is " + localLifecycle.getCurrentState() + ". LifecycleOwners must call register before they are STARTED.");
  }
  
  final void unregister(String paramString)
  {
    if (!this.mLaunchedKeys.contains(paramString))
    {
      localObject = (Integer)this.mKeyToRc.remove(paramString);
      if (localObject != null) {
        this.mRcToKey.remove(localObject);
      }
    }
    this.mKeyToCallback.remove(paramString);
    if (this.mParsedPendingResults.containsKey(paramString))
    {
      Log.w("ActivityResultRegistry", "Dropping pending result for request " + paramString + ": " + this.mParsedPendingResults.get(paramString));
      this.mParsedPendingResults.remove(paramString);
    }
    if (this.mPendingResults.containsKey(paramString))
    {
      Log.w("ActivityResultRegistry", "Dropping pending result for request " + paramString + ": " + this.mPendingResults.getParcelable(paramString));
      this.mPendingResults.remove(paramString);
    }
    Object localObject = (LifecycleContainer)this.mKeyToLifecycleContainers.get(paramString);
    if (localObject != null)
    {
      ((LifecycleContainer)localObject).clearObservers();
      this.mKeyToLifecycleContainers.remove(paramString);
    }
  }
  
  private static class CallbackAndContract<O>
  {
    final ActivityResultCallback<O> mCallback;
    final ActivityResultContract<?, O> mContract;
    
    CallbackAndContract(ActivityResultCallback<O> paramActivityResultCallback, ActivityResultContract<?, O> paramActivityResultContract)
    {
      this.mCallback = paramActivityResultCallback;
      this.mContract = paramActivityResultContract;
    }
  }
  
  private static class LifecycleContainer
  {
    final Lifecycle mLifecycle;
    private final ArrayList<LifecycleEventObserver> mObservers;
    
    LifecycleContainer(Lifecycle paramLifecycle)
    {
      this.mLifecycle = paramLifecycle;
      this.mObservers = new ArrayList();
    }
    
    void addObserver(LifecycleEventObserver paramLifecycleEventObserver)
    {
      this.mLifecycle.addObserver(paramLifecycleEventObserver);
      this.mObservers.add(paramLifecycleEventObserver);
    }
    
    void clearObservers()
    {
      Iterator localIterator = this.mObservers.iterator();
      while (localIterator.hasNext())
      {
        LifecycleEventObserver localLifecycleEventObserver = (LifecycleEventObserver)localIterator.next();
        this.mLifecycle.removeObserver(localLifecycleEventObserver);
      }
      this.mObservers.clear();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/ActivityResultRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */