package androidx.lifecycle;

import android.app.Application;

public class AndroidViewModel
  extends ViewModel
{
  private Application mApplication;
  
  public AndroidViewModel(Application paramApplication)
  {
    this.mApplication = paramApplication;
  }
  
  public <T extends Application> T getApplication()
  {
    return this.mApplication;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/AndroidViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */