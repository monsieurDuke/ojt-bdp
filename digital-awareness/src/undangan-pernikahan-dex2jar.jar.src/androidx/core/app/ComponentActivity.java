package androidx.core.app;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.KeyEventDispatcher.Component;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;

public class ComponentActivity
  extends Activity
  implements LifecycleOwner, KeyEventDispatcher.Component
{
  private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap();
  private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  
  private static boolean shouldSkipDump(String[] paramArrayOfString)
  {
    boolean bool3 = false;
    boolean bool1 = false;
    boolean bool2 = false;
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      paramArrayOfString = paramArrayOfString[0];
      int i = -1;
      switch (paramArrayOfString.hashCode())
      {
      }
      for (;;)
      {
        break;
        if (paramArrayOfString.equals("--autofill"))
        {
          i = 0;
          break;
          if (paramArrayOfString.equals("--contentcapture"))
          {
            i = 1;
            break;
            if (paramArrayOfString.equals("--translation")) {
              i = 2;
            }
          }
        }
      }
      switch (i)
      {
      default: 
        break;
      case 2: 
        bool1 = bool2;
        if (Build.VERSION.SDK_INT >= 31) {
          bool1 = true;
        }
        return bool1;
      case 1: 
        bool1 = bool3;
        if (Build.VERSION.SDK_INT >= 29) {
          bool1 = true;
        }
        return bool1;
      case 0: 
        if (Build.VERSION.SDK_INT >= 26) {
          bool1 = true;
        }
        return bool1;
      }
    }
    return false;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    View localView = getWindow().getDecorView();
    if ((localView != null) && (KeyEventDispatcher.dispatchBeforeHierarchy(localView, paramKeyEvent))) {
      return true;
    }
    return KeyEventDispatcher.dispatchKeyEvent(this, localView, this, paramKeyEvent);
  }
  
  public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
  {
    View localView = getWindow().getDecorView();
    if ((localView != null) && (KeyEventDispatcher.dispatchBeforeHierarchy(localView, paramKeyEvent))) {
      return true;
    }
    return super.dispatchKeyShortcutEvent(paramKeyEvent);
  }
  
  @Deprecated
  public <T extends ExtraData> T getExtraData(Class<T> paramClass)
  {
    return (ExtraData)this.mExtraDataMap.get(paramClass);
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ReportFragment.injectIfNeededIn(this);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    super.onSaveInstanceState(paramBundle);
  }
  
  @Deprecated
  public void putExtraData(ExtraData paramExtraData)
  {
    this.mExtraDataMap.put(paramExtraData.getClass(), paramExtraData);
  }
  
  protected final boolean shouldDumpInternalState(String[] paramArrayOfString)
  {
    return shouldSkipDump(paramArrayOfString) ^ true;
  }
  
  public boolean superDispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  @Deprecated
  public static class ExtraData {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/ComponentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */