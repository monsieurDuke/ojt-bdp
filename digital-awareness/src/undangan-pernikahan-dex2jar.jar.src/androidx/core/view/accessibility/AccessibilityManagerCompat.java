package androidx.core.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener;
import android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener;
import java.util.List;

public final class AccessibilityManagerCompat
{
  @Deprecated
  public static boolean addAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, AccessibilityStateChangeListener paramAccessibilityStateChangeListener)
  {
    if (paramAccessibilityStateChangeListener == null) {
      return false;
    }
    return paramAccessibilityManager.addAccessibilityStateChangeListener(new AccessibilityStateChangeListenerWrapper(paramAccessibilityStateChangeListener));
  }
  
  public static boolean addTouchExplorationStateChangeListener(AccessibilityManager paramAccessibilityManager, TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.addTouchExplorationStateChangeListenerWrapper(paramAccessibilityManager, paramTouchExplorationStateChangeListener);
    }
    return false;
  }
  
  @Deprecated
  public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager, int paramInt)
  {
    return paramAccessibilityManager.getEnabledAccessibilityServiceList(paramInt);
  }
  
  @Deprecated
  public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager)
  {
    return paramAccessibilityManager.getInstalledAccessibilityServiceList();
  }
  
  @Deprecated
  public static boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager)
  {
    return paramAccessibilityManager.isTouchExplorationEnabled();
  }
  
  @Deprecated
  public static boolean removeAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, AccessibilityStateChangeListener paramAccessibilityStateChangeListener)
  {
    if (paramAccessibilityStateChangeListener == null) {
      return false;
    }
    return paramAccessibilityManager.removeAccessibilityStateChangeListener(new AccessibilityStateChangeListenerWrapper(paramAccessibilityStateChangeListener));
  }
  
  public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager paramAccessibilityManager, TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.removeTouchExplorationStateChangeListenerWrapper(paramAccessibilityManager, paramTouchExplorationStateChangeListener);
    }
    return false;
  }
  
  @Deprecated
  public static abstract interface AccessibilityStateChangeListener
  {
    @Deprecated
    public abstract void onAccessibilityStateChanged(boolean paramBoolean);
  }
  
  @Deprecated
  public static abstract class AccessibilityStateChangeListenerCompat
    implements AccessibilityManagerCompat.AccessibilityStateChangeListener
  {}
  
  private static class AccessibilityStateChangeListenerWrapper
    implements AccessibilityManager.AccessibilityStateChangeListener
  {
    AccessibilityManagerCompat.AccessibilityStateChangeListener mListener;
    
    AccessibilityStateChangeListenerWrapper(AccessibilityManagerCompat.AccessibilityStateChangeListener paramAccessibilityStateChangeListener)
    {
      this.mListener = paramAccessibilityStateChangeListener;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof AccessibilityStateChangeListenerWrapper)) {
        return false;
      }
      paramObject = (AccessibilityStateChangeListenerWrapper)paramObject;
      return this.mListener.equals(((AccessibilityStateChangeListenerWrapper)paramObject).mListener);
    }
    
    public int hashCode()
    {
      return this.mListener.hashCode();
    }
    
    public void onAccessibilityStateChanged(boolean paramBoolean)
    {
      this.mListener.onAccessibilityStateChanged(paramBoolean);
    }
  }
  
  static class Api19Impl
  {
    static boolean addTouchExplorationStateChangeListenerWrapper(AccessibilityManager paramAccessibilityManager, AccessibilityManagerCompat.TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener)
    {
      return paramAccessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerCompat.TouchExplorationStateChangeListenerWrapper(paramTouchExplorationStateChangeListener));
    }
    
    static boolean removeTouchExplorationStateChangeListenerWrapper(AccessibilityManager paramAccessibilityManager, AccessibilityManagerCompat.TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener)
    {
      return paramAccessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerCompat.TouchExplorationStateChangeListenerWrapper(paramTouchExplorationStateChangeListener));
    }
  }
  
  public static abstract interface TouchExplorationStateChangeListener
  {
    public abstract void onTouchExplorationStateChanged(boolean paramBoolean);
  }
  
  private static final class TouchExplorationStateChangeListenerWrapper
    implements AccessibilityManager.TouchExplorationStateChangeListener
  {
    final AccessibilityManagerCompat.TouchExplorationStateChangeListener mListener;
    
    TouchExplorationStateChangeListenerWrapper(AccessibilityManagerCompat.TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener)
    {
      this.mListener = paramTouchExplorationStateChangeListener;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (!(paramObject instanceof TouchExplorationStateChangeListenerWrapper)) {
        return false;
      }
      paramObject = (TouchExplorationStateChangeListenerWrapper)paramObject;
      return this.mListener.equals(((TouchExplorationStateChangeListenerWrapper)paramObject).mListener);
    }
    
    public int hashCode()
    {
      return this.mListener.hashCode();
    }
    
    public void onTouchExplorationStateChanged(boolean paramBoolean)
    {
      this.mListener.onTouchExplorationStateChanged(paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/accessibility/AccessibilityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */