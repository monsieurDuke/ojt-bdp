package androidx.core.content.pm;

import java.util.List;

public abstract class ShortcutInfoChangeListener
{
  public void onAllShortcutsRemoved() {}
  
  public void onShortcutAdded(List<ShortcutInfoCompat> paramList) {}
  
  public void onShortcutRemoved(List<String> paramList) {}
  
  public void onShortcutUpdated(List<ShortcutInfoCompat> paramList) {}
  
  public void onShortcutUsageReported(List<String> paramList) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/pm/ShortcutInfoChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */