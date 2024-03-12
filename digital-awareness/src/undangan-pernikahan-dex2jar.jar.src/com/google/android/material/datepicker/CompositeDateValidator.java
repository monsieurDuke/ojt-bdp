package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CompositeDateValidator
  implements CalendarConstraints.DateValidator
{
  private static final Operator ALL_OPERATOR = new Operator()
  {
    public int getId()
    {
      return 2;
    }
    
    public boolean isValid(List<CalendarConstraints.DateValidator> paramAnonymousList, long paramAnonymousLong)
    {
      paramAnonymousList = paramAnonymousList.iterator();
      while (paramAnonymousList.hasNext())
      {
        CalendarConstraints.DateValidator localDateValidator = (CalendarConstraints.DateValidator)paramAnonymousList.next();
        if (localDateValidator != null) {
          if (!localDateValidator.isValid(paramAnonymousLong)) {
            return false;
          }
        }
      }
      return true;
    }
  };
  private static final Operator ANY_OPERATOR = new Operator()
  {
    public int getId()
    {
      return 1;
    }
    
    public boolean isValid(List<CalendarConstraints.DateValidator> paramAnonymousList, long paramAnonymousLong)
    {
      paramAnonymousList = paramAnonymousList.iterator();
      while (paramAnonymousList.hasNext())
      {
        CalendarConstraints.DateValidator localDateValidator = (CalendarConstraints.DateValidator)paramAnonymousList.next();
        if (localDateValidator != null) {
          if (localDateValidator.isValid(paramAnonymousLong)) {
            return true;
          }
        }
      }
      return false;
    }
  };
  private static final int COMPARATOR_ALL_ID = 2;
  private static final int COMPARATOR_ANY_ID = 1;
  public static final Parcelable.Creator<CompositeDateValidator> CREATOR = new Parcelable.Creator()
  {
    public CompositeDateValidator createFromParcel(Parcel paramAnonymousParcel)
    {
      ArrayList localArrayList = paramAnonymousParcel.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader());
      int i = paramAnonymousParcel.readInt();
      if (i == 2) {
        paramAnonymousParcel = CompositeDateValidator.ALL_OPERATOR;
      } else if (i == 1) {
        paramAnonymousParcel = CompositeDateValidator.ANY_OPERATOR;
      } else {
        paramAnonymousParcel = CompositeDateValidator.ALL_OPERATOR;
      }
      return new CompositeDateValidator((List)Preconditions.checkNotNull(localArrayList), paramAnonymousParcel, null);
    }
    
    public CompositeDateValidator[] newArray(int paramAnonymousInt)
    {
      return new CompositeDateValidator[paramAnonymousInt];
    }
  };
  private final Operator operator;
  private final List<CalendarConstraints.DateValidator> validators;
  
  private CompositeDateValidator(List<CalendarConstraints.DateValidator> paramList, Operator paramOperator)
  {
    this.validators = paramList;
    this.operator = paramOperator;
  }
  
  public static CalendarConstraints.DateValidator allOf(List<CalendarConstraints.DateValidator> paramList)
  {
    return new CompositeDateValidator(paramList, ALL_OPERATOR);
  }
  
  public static CalendarConstraints.DateValidator anyOf(List<CalendarConstraints.DateValidator> paramList)
  {
    return new CompositeDateValidator(paramList, ANY_OPERATOR);
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
    if (!(paramObject instanceof CompositeDateValidator)) {
      return false;
    }
    paramObject = (CompositeDateValidator)paramObject;
    if ((!this.validators.equals(((CompositeDateValidator)paramObject).validators)) || (this.operator.getId() != ((CompositeDateValidator)paramObject).operator.getId())) {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    return this.validators.hashCode();
  }
  
  public boolean isValid(long paramLong)
  {
    return this.operator.isValid(this.validators, paramLong);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeList(this.validators);
    paramParcel.writeInt(this.operator.getId());
  }
  
  private static abstract interface Operator
  {
    public abstract int getId();
    
    public abstract boolean isValid(List<CalendarConstraints.DateValidator> paramList, long paramLong);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/CompositeDateValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */