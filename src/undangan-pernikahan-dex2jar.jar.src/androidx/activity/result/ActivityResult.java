package androidx.activity.result;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ActivityResult
  implements Parcelable
{
  public static final Parcelable.Creator<ActivityResult> CREATOR = new Parcelable.Creator()
  {
    public ActivityResult createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ActivityResult(paramAnonymousParcel);
    }
    
    public ActivityResult[] newArray(int paramAnonymousInt)
    {
      return new ActivityResult[paramAnonymousInt];
    }
  };
  private final Intent mData;
  private final int mResultCode;
  
  public ActivityResult(int paramInt, Intent paramIntent)
  {
    this.mResultCode = paramInt;
    this.mData = paramIntent;
  }
  
  ActivityResult(Parcel paramParcel)
  {
    this.mResultCode = paramParcel.readInt();
    if (paramParcel.readInt() == 0) {
      paramParcel = null;
    } else {
      paramParcel = (Intent)Intent.CREATOR.createFromParcel(paramParcel);
    }
    this.mData = paramParcel;
  }
  
  public static String resultCodeToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      String str = String.valueOf(paramInt);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    case 0: 
      return "RESULT_CANCELED";
    }
    return "RESULT_OK";
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Intent getData()
  {
    return this.mData;
  }
  
  public int getResultCode()
  {
    return this.mResultCode;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("ActivityResult{resultCode=");
    String str = resultCodeToString(this.mResultCode);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + ", data=" + this.mData + '}';
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mResultCode);
    int i;
    if (this.mData == null) {
      i = 0;
    } else {
      i = 1;
    }
    paramParcel.writeInt(i);
    Intent localIntent = this.mData;
    if (localIntent != null) {
      localIntent.writeToParcel(paramParcel, paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/result/ActivityResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */