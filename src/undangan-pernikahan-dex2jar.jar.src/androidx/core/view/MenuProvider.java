package androidx.core.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract interface MenuProvider
{
  public abstract void onCreateMenu(Menu paramMenu, MenuInflater paramMenuInflater);
  
  public void onMenuClosed(Menu paramMenu) {}
  
  public abstract boolean onMenuItemSelected(MenuItem paramMenuItem);
  
  public void onPrepareMenu(Menu paramMenu) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/MenuProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */