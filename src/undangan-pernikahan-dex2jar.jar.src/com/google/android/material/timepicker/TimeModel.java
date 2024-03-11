package com.google.android.material.timepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TimeModel
  implements Parcelable
{
  public static final Parcelable.Creator<TimeModel> CREATOR = new Parcelable.Creator()
  {
    public TimeModel createFromParcel(Parcel paramAnonymousParcel)
    {
      return new TimeModel(paramAnonymousParcel);
    }
    
    public TimeModel[] newArray(int paramAnonymousInt)
    {
      return new TimeModel[paramAnonymousInt];
    }
  };
  public static final String NUMBER_FORMAT = "%d";
  public static final String ZERO_LEADING_NUMBER_FORMAT = "%02d";
  final int format;
  int hour;
  private final MaxInputValidator hourInputValidator;
  int minute;
  private final MaxInputValidator minuteInputValidator;
  int period;
  int selection;
  
  public TimeModel()
  {
    this(0);
  }
  
  public TimeModel(int paramInt)
  {
    this(0, 0, 10, paramInt);
  }
  
  public TimeModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.hour = paramInt1;
    this.minute = paramInt2;
    this.selection = paramInt3;
    this.format = paramInt4;
    this.period = getPeriod(paramInt1);
    this.minuteInputValidator = new MaxInputValidator(59);
    if (paramInt4 == 1) {
      paramInt1 = 24;
    } else {
      paramInt1 = 12;
    }
    this.hourInputValidator = new MaxInputValidator(paramInt1);
  }
  
  protected TimeModel(Parcel paramParcel)
  {
    this(paramParcel.readInt(), paramParcel.readInt(), paramParcel.readInt(), paramParcel.readInt());
  }
  
  public static String formatText(Resources paramResources, CharSequence paramCharSequence)
  {
    paramResources = formatText(paramResources, paramCharSequence, "%02d");
    Log5ECF72.a(paramResources);
    LogE84000.a(paramResources);
    Log229316.a(paramResources);
    return paramResources;
  }
  
  public static String formatText(Resources paramResources, CharSequence paramCharSequence, String paramString)
  {
    paramResources = paramResources.getConfiguration().locale;
    paramCharSequence = String.valueOf(paramCharSequence);
    Log5ECF72.a(paramCharSequence);
    LogE84000.a(paramCharSequence);
    Log229316.a(paramCharSequence);
    paramResources = String.format(paramResources, paramString, new Object[] { Integer.valueOf(Integer.parseInt(paramCharSequence)) });
    Log5ECF72.a(paramResources);
    LogE84000.a(paramResources);
    Log229316.a(paramResources);
    return paramResources;
  }
  
  private static int getPeriod(int paramInt)
  {
    if (paramInt >= 12) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    return paramInt;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TimeModel)) {
      return false;
    }
    paramObject = (TimeModel)paramObject;
    if ((this.hour != ((TimeModel)paramObject).hour) || (this.minute != ((TimeModel)paramObject).minute) || (this.format != ((TimeModel)paramObject).format) || (this.selection != ((TimeModel)paramObject).selection)) {
      bool = false;
    }
    return bool;
  }
  
  public int getHourForDisplay()
  {
    if (this.format == 1) {
      return this.hour % 24;
    }
    int i = this.hour;
    if (i % 12 == 0) {
      return 12;
    }
    if (this.period == 1) {
      return i - 12;
    }
    return i;
  }
  
  public MaxInputValidator getHourInputValidator()
  {
    return this.hourInputValidator;
  }
  
  public MaxInputValidator getMinuteInputValidator()
  {
    return this.minuteInputValidator;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(new Object[] { Integer.valueOf(this.format), Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.selection) });
  }
  
  public void setHour(int paramInt)
  {
    if (this.format == 1)
    {
      this.hour = paramInt;
      return;
    }
    int i;
    if (this.period == 1) {
      i = 12;
    } else {
      i = 0;
    }
    this.hour = (paramInt % 12 + i);
  }
  
  public void setHourOfDay(int paramInt)
  {
    this.period = getPeriod(paramInt);
    this.hour = paramInt;
  }
  
  public void setMinute(int paramInt)
  {
    this.minute = (paramInt % 60);
  }
  
  public void setPeriod(int paramInt)
  {
    if (paramInt != this.period)
    {
      this.period = paramInt;
      int i = this.hour;
      if ((i < 12) && (paramInt == 1)) {
        this.hour = (i + 12);
      } else if ((i >= 12) && (paramInt == 0)) {
        this.hour = (i - 12);
      }
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.hour);
    paramParcel.writeInt(this.minute);
    paramParcel.writeInt(this.selection);
    paramParcel.writeInt(this.format);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimeModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */