package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.SparseIntArray;

public class ParcelableSparseIntArray
  extends SparseIntArray
  implements Parcelable
{
  public static final Parcelable.Creator<ParcelableSparseIntArray> CREATOR = new Parcelable.Creator()
  {
    public ParcelableSparseIntArray createFromParcel(Parcel paramAnonymousParcel)
    {
      int j = paramAnonymousParcel.readInt();
      ParcelableSparseIntArray localParcelableSparseIntArray = new ParcelableSparseIntArray(j);
      int[] arrayOfInt1 = new int[j];
      int[] arrayOfInt2 = new int[j];
      paramAnonymousParcel.readIntArray(arrayOfInt1);
      paramAnonymousParcel.readIntArray(arrayOfInt2);
      for (int i = 0; i < j; i++) {
        localParcelableSparseIntArray.put(arrayOfInt1[i], arrayOfInt2[i]);
      }
      return localParcelableSparseIntArray;
    }
    
    public ParcelableSparseIntArray[] newArray(int paramAnonymousInt)
    {
      return new ParcelableSparseIntArray[paramAnonymousInt];
    }
  };
  
  public ParcelableSparseIntArray() {}
  
  public ParcelableSparseIntArray(int paramInt)
  {
    super(paramInt);
  }
  
  public ParcelableSparseIntArray(SparseIntArray paramSparseIntArray)
  {
    for (int i = 0; i < paramSparseIntArray.size(); i++) {
      put(paramSparseIntArray.keyAt(i), paramSparseIntArray.valueAt(i));
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int[] arrayOfInt2 = new int[size()];
    int[] arrayOfInt1 = new int[size()];
    for (paramInt = 0; paramInt < size(); paramInt++)
    {
      arrayOfInt2[paramInt] = keyAt(paramInt);
      arrayOfInt1[paramInt] = valueAt(paramInt);
    }
    paramParcel.writeInt(size());
    paramParcel.writeIntArray(arrayOfInt2);
    paramParcel.writeIntArray(arrayOfInt1);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/internal/ParcelableSparseIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */