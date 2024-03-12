package androidx.core.app;

import android.content.res.Configuration;

public final class MultiWindowModeChangedInfo
{
  private final boolean mIsInMultiWindowMode;
  private final Configuration mNewConfig;
  
  public MultiWindowModeChangedInfo(boolean paramBoolean)
  {
    this.mIsInMultiWindowMode = paramBoolean;
    this.mNewConfig = null;
  }
  
  public MultiWindowModeChangedInfo(boolean paramBoolean, Configuration paramConfiguration)
  {
    this.mIsInMultiWindowMode = paramBoolean;
    this.mNewConfig = paramConfiguration;
  }
  
  public Configuration getNewConfig()
  {
    Configuration localConfiguration = this.mNewConfig;
    if (localConfiguration != null) {
      return localConfiguration;
    }
    throw new IllegalStateException("MultiWindowModeChangedInfo must be constructed with the constructor that takes a Configuration to call getNewConfig(). Are you running on an API 26 or higher device that makes this information available?");
  }
  
  public boolean isInMultiWindowMode()
  {
    return this.mIsInMultiWindowMode;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/MultiWindowModeChangedInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */