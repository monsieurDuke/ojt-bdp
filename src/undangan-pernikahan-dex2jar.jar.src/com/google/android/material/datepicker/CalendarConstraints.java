package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;

public final class CalendarConstraints
  implements Parcelable
{
  public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator()
  {
    public CalendarConstraints createFromParcel(Parcel paramAnonymousParcel)
    {
      Month localMonth3 = (Month)paramAnonymousParcel.readParcelable(Month.class.getClassLoader());
      Month localMonth2 = (Month)paramAnonymousParcel.readParcelable(Month.class.getClassLoader());
      Month localMonth1 = (Month)paramAnonymousParcel.readParcelable(Month.class.getClassLoader());
      return new CalendarConstraints(localMonth3, localMonth2, (CalendarConstraints.DateValidator)paramAnonymousParcel.readParcelable(CalendarConstraints.DateValidator.class.getClassLoader()), localMonth1, null);
    }
    
    public CalendarConstraints[] newArray(int paramAnonymousInt)
    {
      return new CalendarConstraints[paramAnonymousInt];
    }
  };
  private final Month end;
  private final int monthSpan;
  private Month openAt;
  private final Month start;
  private final DateValidator validator;
  private final int yearSpan;
  
  private CalendarConstraints(Month paramMonth1, Month paramMonth2, DateValidator paramDateValidator, Month paramMonth3)
  {
    this.start = paramMonth1;
    this.end = paramMonth2;
    this.openAt = paramMonth3;
    this.validator = paramDateValidator;
    if ((paramMonth3 != null) && (paramMonth1.compareTo(paramMonth3) > 0)) {
      throw new IllegalArgumentException("start Month cannot be after current Month");
    }
    if ((paramMonth3 != null) && (paramMonth3.compareTo(paramMonth2) > 0)) {
      throw new IllegalArgumentException("current Month cannot be after end Month");
    }
    this.monthSpan = (paramMonth1.monthsUntil(paramMonth2) + 1);
    this.yearSpan = (paramMonth2.year - paramMonth1.year + 1);
  }
  
  Month clamp(Month paramMonth)
  {
    if (paramMonth.compareTo(this.start) < 0) {
      return this.start;
    }
    if (paramMonth.compareTo(this.end) > 0) {
      return this.end;
    }
    return paramMonth;
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
    if (!(paramObject instanceof CalendarConstraints)) {
      return false;
    }
    paramObject = (CalendarConstraints)paramObject;
    if ((!this.start.equals(((CalendarConstraints)paramObject).start)) || (!this.end.equals(((CalendarConstraints)paramObject).end)) || (!ObjectsCompat.equals(this.openAt, ((CalendarConstraints)paramObject).openAt)) || (!this.validator.equals(((CalendarConstraints)paramObject).validator))) {
      bool = false;
    }
    return bool;
  }
  
  public DateValidator getDateValidator()
  {
    return this.validator;
  }
  
  Month getEnd()
  {
    return this.end;
  }
  
  int getMonthSpan()
  {
    return this.monthSpan;
  }
  
  Month getOpenAt()
  {
    return this.openAt;
  }
  
  Month getStart()
  {
    return this.start;
  }
  
  int getYearSpan()
  {
    return this.yearSpan;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(new Object[] { this.start, this.end, this.openAt, this.validator });
  }
  
  boolean isWithinBounds(long paramLong)
  {
    Month localMonth = this.start;
    boolean bool = true;
    if (localMonth.getDay(1) <= paramLong)
    {
      localMonth = this.end;
      if (paramLong <= localMonth.getDay(localMonth.daysInMonth)) {}
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  void setOpenAt(Month paramMonth)
  {
    this.openAt = paramMonth;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(this.start, 0);
    paramParcel.writeParcelable(this.end, 0);
    paramParcel.writeParcelable(this.openAt, 0);
    paramParcel.writeParcelable(this.validator, 0);
  }
  
  public static final class Builder
  {
    private static final String DEEP_COPY_VALIDATOR_KEY = "DEEP_COPY_VALIDATOR_KEY";
    static final long DEFAULT_END = UtcDates.canonicalYearMonthDay(Month.create(2100, 11).timeInMillis);
    static final long DEFAULT_START = UtcDates.canonicalYearMonthDay(Month.create(1900, 0).timeInMillis);
    private long end = DEFAULT_END;
    private Long openAt;
    private long start = DEFAULT_START;
    private CalendarConstraints.DateValidator validator = DateValidatorPointForward.from(Long.MIN_VALUE);
    
    public Builder() {}
    
    Builder(CalendarConstraints paramCalendarConstraints)
    {
      this.start = paramCalendarConstraints.start.timeInMillis;
      this.end = paramCalendarConstraints.end.timeInMillis;
      this.openAt = Long.valueOf(paramCalendarConstraints.openAt.timeInMillis);
      this.validator = paramCalendarConstraints.validator;
    }
    
    public CalendarConstraints build()
    {
      Object localObject = new Bundle();
      ((Bundle)localObject).putParcelable("DEEP_COPY_VALIDATOR_KEY", this.validator);
      Month localMonth1 = Month.create(this.start);
      Month localMonth2 = Month.create(this.end);
      CalendarConstraints.DateValidator localDateValidator = (CalendarConstraints.DateValidator)((Bundle)localObject).getParcelable("DEEP_COPY_VALIDATOR_KEY");
      localObject = this.openAt;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = Month.create(((Long)localObject).longValue());
      }
      return new CalendarConstraints(localMonth1, localMonth2, localDateValidator, (Month)localObject, null);
    }
    
    public Builder setEnd(long paramLong)
    {
      this.end = paramLong;
      return this;
    }
    
    public Builder setOpenAt(long paramLong)
    {
      this.openAt = Long.valueOf(paramLong);
      return this;
    }
    
    public Builder setStart(long paramLong)
    {
      this.start = paramLong;
      return this;
    }
    
    public Builder setValidator(CalendarConstraints.DateValidator paramDateValidator)
    {
      this.validator = paramDateValidator;
      return this;
    }
  }
  
  public static abstract interface DateValidator
    extends Parcelable
  {
    public abstract boolean isValid(long paramLong);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/CalendarConstraints.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */