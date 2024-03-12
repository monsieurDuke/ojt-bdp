package com.google.android.material.stateful;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import androidx.collection.SimpleArrayMap;
import androidx.customview.view.AbsSavedState;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ExtendableSavedState
  extends AbsSavedState
{
  public static final Parcelable.Creator<ExtendableSavedState> CREATOR = new Parcelable.ClassLoaderCreator()
  {
    public ExtendableSavedState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ExtendableSavedState(paramAnonymousParcel, null, null);
    }
    
    public ExtendableSavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
    {
      return new ExtendableSavedState(paramAnonymousParcel, paramAnonymousClassLoader, null);
    }
    
    public ExtendableSavedState[] newArray(int paramAnonymousInt)
    {
      return new ExtendableSavedState[paramAnonymousInt];
    }
  };
  public final SimpleArrayMap<String, Bundle> extendableStates;
  
  private ExtendableSavedState(Parcel paramParcel, ClassLoader paramClassLoader)
  {
    super(paramParcel, paramClassLoader);
    int j = paramParcel.readInt();
    paramClassLoader = new String[j];
    paramParcel.readStringArray(paramClassLoader);
    Bundle[] arrayOfBundle = new Bundle[j];
    paramParcel.readTypedArray(arrayOfBundle, Bundle.CREATOR);
    this.extendableStates = new SimpleArrayMap(j);
    for (int i = 0; i < j; i++) {
      this.extendableStates.put(paramClassLoader[i], arrayOfBundle[i]);
    }
  }
  
  public ExtendableSavedState(Parcelable paramParcelable)
  {
    super(paramParcelable);
    this.extendableStates = new SimpleArrayMap();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("ExtendableSavedState{");
    String str = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + " states=" + this.extendableStates + "}";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    int i = this.extendableStates.size();
    paramParcel.writeInt(i);
    String[] arrayOfString = new String[i];
    Bundle[] arrayOfBundle = new Bundle[i];
    for (paramInt = 0; paramInt < i; paramInt++)
    {
      arrayOfString[paramInt] = ((String)this.extendableStates.keyAt(paramInt));
      arrayOfBundle[paramInt] = ((Bundle)this.extendableStates.valueAt(paramInt));
    }
    paramParcel.writeStringArray(arrayOfString);
    paramParcel.writeTypedArray(arrayOfBundle, 0);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/stateful/ExtendableSavedState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */