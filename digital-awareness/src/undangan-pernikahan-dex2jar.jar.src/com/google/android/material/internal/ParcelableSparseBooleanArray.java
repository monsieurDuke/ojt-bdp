package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseBooleanArray;

public class ParcelableSparseBooleanArray
  extends SparseBooleanArray
  implements Parcelable
{
  public static final Parcelable.Creator<ParcelableSparseBooleanArray> CREATOR = new Parcelable.Creator()
  {
    public ParcelableSparseBooleanArray createFromParcel(Parcel paramAnonymousParcel)
    {
      int j = paramAnonymousParcel.readInt();
      ParcelableSparseBooleanArray localParcelableSparseBooleanArray = new ParcelableSparseBooleanArray(j);
      int[] arrayOfInt = new int[j];
      boolean[] arrayOfBoolean = new boolean[j];
      paramAnonymousParcel.readIntArray(arrayOfInt);
      paramAnonymousParcel.readBooleanArray(arrayOfBoolean);
      for (int i = 0; i < j; i++) {
        localParcelableSparseBooleanArray.put(arrayOfInt[i], arrayOfBoolean[i]);
      }
      return localParcelableSparseBooleanArray;
    }
    
    public ParcelableSparseBooleanArray[] newArray(int paramAnonymousInt)
    {
      return new ParcelableSparseBooleanArray[paramAnonymousInt];
    }
  };
  
  public ParcelableSparseBooleanArray() {}
  
  public ParcelableSparseBooleanArray(int paramInt)
  {
    super(paramInt);
  }
  
  public ParcelableSparseBooleanArray(SparseBooleanArray paramSparseBooleanArray)
  {
    super(paramSparseBooleanArray.size());
    for (int i = 0; i < paramSparseBooleanArray.size(); i++) {
      put(paramSparseBooleanArray.keyAt(i), paramSparseBooleanArray.valueAt(i));
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int[] arrayOfInt = new int[size()];
    boolean[] arrayOfBoolean = new boolean[size()];
    for (paramInt = 0; paramInt < size(); paramInt++)
    {
      arrayOfInt[paramInt] = keyAt(paramInt);
      arrayOfBoolean[paramInt] = valueAt(paramInt);
    }
    paramParcel.writeInt(size());
    paramParcel.writeIntArray(arrayOfInt);
    paramParcel.writeBooleanArray(arrayOfBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/ParcelableSparseBooleanArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */