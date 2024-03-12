package androidx.core.app;

import androidx.core.util.Consumer;

public abstract interface OnPictureInPictureModeChangedProvider
{
  public abstract void addOnPictureInPictureModeChangedListener(Consumer<PictureInPictureModeChangedInfo> paramConsumer);
  
  public abstract void removeOnPictureInPictureModeChangedListener(Consumer<PictureInPictureModeChangedInfo> paramConsumer);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/OnPictureInPictureModeChangedProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */