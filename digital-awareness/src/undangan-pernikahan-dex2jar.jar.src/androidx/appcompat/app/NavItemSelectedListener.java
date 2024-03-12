package androidx.appcompat.app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

class NavItemSelectedListener
  implements AdapterView.OnItemSelectedListener
{
  private final ActionBar.OnNavigationListener mListener;
  
  public NavItemSelectedListener(ActionBar.OnNavigationListener paramOnNavigationListener)
  {
    this.mListener = paramOnNavigationListener;
  }
  
  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = this.mListener;
    if (paramAdapterView != null) {
      paramAdapterView.onNavigationItemSelected(paramInt, paramLong);
    }
  }
  
  public void onNothingSelected(AdapterView<?> paramAdapterView) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/app/NavItemSelectedListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */