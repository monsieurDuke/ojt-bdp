package androidx.activity.result;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class IntentSenderRequest
  implements Parcelable
{
  public static final Parcelable.Creator<IntentSenderRequest> CREATOR = new Parcelable.Creator()
  {
    public IntentSenderRequest createFromParcel(Parcel paramAnonymousParcel)
    {
      return new IntentSenderRequest(paramAnonymousParcel);
    }
    
    public IntentSenderRequest[] newArray(int paramAnonymousInt)
    {
      return new IntentSenderRequest[paramAnonymousInt];
    }
  };
  private final Intent mFillInIntent;
  private final int mFlagsMask;
  private final int mFlagsValues;
  private final IntentSender mIntentSender;
  
  IntentSenderRequest(IntentSender paramIntentSender, Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.mIntentSender = paramIntentSender;
    this.mFillInIntent = paramIntent;
    this.mFlagsMask = paramInt1;
    this.mFlagsValues = paramInt2;
  }
  
  IntentSenderRequest(Parcel paramParcel)
  {
    this.mIntentSender = ((IntentSender)paramParcel.readParcelable(IntentSender.class.getClassLoader()));
    this.mFillInIntent = ((Intent)paramParcel.readParcelable(Intent.class.getClassLoader()));
    this.mFlagsMask = paramParcel.readInt();
    this.mFlagsValues = paramParcel.readInt();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Intent getFillInIntent()
  {
    return this.mFillInIntent;
  }
  
  public int getFlagsMask()
  {
    return this.mFlagsMask;
  }
  
  public int getFlagsValues()
  {
    return this.mFlagsValues;
  }
  
  public IntentSender getIntentSender()
  {
    return this.mIntentSender;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.mIntentSender, paramInt);
    paramParcel.writeParcelable(this.mFillInIntent, paramInt);
    paramParcel.writeInt(this.mFlagsMask);
    paramParcel.writeInt(this.mFlagsValues);
  }
  
  public static final class Builder
  {
    private Intent mFillInIntent;
    private int mFlagsMask;
    private int mFlagsValues;
    private IntentSender mIntentSender;
    
    public Builder(PendingIntent paramPendingIntent)
    {
      this(paramPendingIntent.getIntentSender());
    }
    
    public Builder(IntentSender paramIntentSender)
    {
      this.mIntentSender = paramIntentSender;
    }
    
    public IntentSenderRequest build()
    {
      return new IntentSenderRequest(this.mIntentSender, this.mFillInIntent, this.mFlagsMask, this.mFlagsValues);
    }
    
    public Builder setFillInIntent(Intent paramIntent)
    {
      this.mFillInIntent = paramIntent;
      return this;
    }
    
    public Builder setFlags(int paramInt1, int paramInt2)
    {
      this.mFlagsValues = paramInt1;
      this.mFlagsMask = paramInt2;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/IntentSenderRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */