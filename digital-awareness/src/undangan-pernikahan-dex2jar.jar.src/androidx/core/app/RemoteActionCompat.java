package androidx.core.app;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.VersionedParcelable;

public final class RemoteActionCompat
  implements VersionedParcelable
{
  public PendingIntent mActionIntent;
  public CharSequence mContentDescription;
  public boolean mEnabled;
  public IconCompat mIcon;
  public boolean mShouldShowIcon;
  public CharSequence mTitle;
  
  public RemoteActionCompat() {}
  
  public RemoteActionCompat(RemoteActionCompat paramRemoteActionCompat)
  {
    Preconditions.checkNotNull(paramRemoteActionCompat);
    this.mIcon = paramRemoteActionCompat.mIcon;
    this.mTitle = paramRemoteActionCompat.mTitle;
    this.mContentDescription = paramRemoteActionCompat.mContentDescription;
    this.mActionIntent = paramRemoteActionCompat.mActionIntent;
    this.mEnabled = paramRemoteActionCompat.mEnabled;
    this.mShouldShowIcon = paramRemoteActionCompat.mShouldShowIcon;
  }
  
  public RemoteActionCompat(IconCompat paramIconCompat, CharSequence paramCharSequence1, CharSequence paramCharSequence2, PendingIntent paramPendingIntent)
  {
    this.mIcon = ((IconCompat)Preconditions.checkNotNull(paramIconCompat));
    this.mTitle = ((CharSequence)Preconditions.checkNotNull(paramCharSequence1));
    this.mContentDescription = ((CharSequence)Preconditions.checkNotNull(paramCharSequence2));
    this.mActionIntent = ((PendingIntent)Preconditions.checkNotNull(paramPendingIntent));
    this.mEnabled = true;
    this.mShouldShowIcon = true;
  }
  
  public static RemoteActionCompat createFromRemoteAction(RemoteAction paramRemoteAction)
  {
    Preconditions.checkNotNull(paramRemoteAction);
    RemoteActionCompat localRemoteActionCompat = new RemoteActionCompat(IconCompat.createFromIcon(Api26Impl.getIcon(paramRemoteAction)), Api26Impl.getTitle(paramRemoteAction), Api26Impl.getContentDescription(paramRemoteAction), Api26Impl.getActionIntent(paramRemoteAction));
    localRemoteActionCompat.setEnabled(Api26Impl.isEnabled(paramRemoteAction));
    if (Build.VERSION.SDK_INT >= 28) {
      localRemoteActionCompat.setShouldShowIcon(Api28Impl.shouldShowIcon(paramRemoteAction));
    }
    return localRemoteActionCompat;
  }
  
  public PendingIntent getActionIntent()
  {
    return this.mActionIntent;
  }
  
  public CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }
  
  public IconCompat getIcon()
  {
    return this.mIcon;
  }
  
  public CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public boolean isEnabled()
  {
    return this.mEnabled;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mEnabled = paramBoolean;
  }
  
  public void setShouldShowIcon(boolean paramBoolean)
  {
    this.mShouldShowIcon = paramBoolean;
  }
  
  public boolean shouldShowIcon()
  {
    return this.mShouldShowIcon;
  }
  
  public RemoteAction toRemoteAction()
  {
    RemoteAction localRemoteAction = Api26Impl.createRemoteAction(this.mIcon.toIcon(), this.mTitle, this.mContentDescription, this.mActionIntent);
    Api26Impl.setEnabled(localRemoteAction, isEnabled());
    if (Build.VERSION.SDK_INT >= 28) {
      Api28Impl.setShouldShowIcon(localRemoteAction, shouldShowIcon());
    }
    return localRemoteAction;
  }
  
  static class Api26Impl
  {
    static RemoteAction createRemoteAction(Icon paramIcon, CharSequence paramCharSequence1, CharSequence paramCharSequence2, PendingIntent paramPendingIntent)
    {
      return new RemoteAction(paramIcon, paramCharSequence1, paramCharSequence2, paramPendingIntent);
    }
    
    static PendingIntent getActionIntent(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.getActionIntent();
    }
    
    static CharSequence getContentDescription(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.getContentDescription();
    }
    
    static Icon getIcon(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.getIcon();
    }
    
    static CharSequence getTitle(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.getTitle();
    }
    
    static boolean isEnabled(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.isEnabled();
    }
    
    static void setEnabled(RemoteAction paramRemoteAction, boolean paramBoolean)
    {
      paramRemoteAction.setEnabled(paramBoolean);
    }
  }
  
  static class Api28Impl
  {
    static void setShouldShowIcon(RemoteAction paramRemoteAction, boolean paramBoolean)
    {
      paramRemoteAction.setShouldShowIcon(paramBoolean);
    }
    
    static boolean shouldShowIcon(RemoteAction paramRemoteAction)
    {
      return paramRemoteAction.shouldShowIcon();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/RemoteActionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */