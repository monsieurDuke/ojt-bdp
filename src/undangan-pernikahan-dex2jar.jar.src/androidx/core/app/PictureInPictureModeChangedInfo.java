package androidx.core.app;

import android.content.res.Configuration;

public final class PictureInPictureModeChangedInfo
{
  private final boolean mIsInPictureInPictureMode;
  private final Configuration mNewConfig;
  
  public PictureInPictureModeChangedInfo(boolean paramBoolean)
  {
    this.mIsInPictureInPictureMode = paramBoolean;
    this.mNewConfig = null;
  }
  
  public PictureInPictureModeChangedInfo(boolean paramBoolean, Configuration paramConfiguration)
  {
    this.mIsInPictureInPictureMode = paramBoolean;
    this.mNewConfig = paramConfiguration;
  }
  
  public Configuration getNewConfig()
  {
    Configuration localConfiguration = this.mNewConfig;
    if (localConfiguration != null) {
      return localConfiguration;
    }
    throw new IllegalStateException("PictureInPictureModeChangedInfo must be constructed with the constructor that takes a Configuration to call getNewConfig(). Are you running on an API 26 or higher device that makes this information available?");
  }
  
  public boolean isInPictureInPictureMode()
  {
    return this.mIsInPictureInPictureMode;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/PictureInPictureModeChangedInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */