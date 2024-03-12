package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

final class FragmentManagerState
  implements Parcelable
{
  public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator()
  {
    public FragmentManagerState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FragmentManagerState(paramAnonymousParcel);
    }
    
    public FragmentManagerState[] newArray(int paramAnonymousInt)
    {
      return new FragmentManagerState[paramAnonymousInt];
    }
  };
  ArrayList<FragmentState> mActive;
  ArrayList<String> mAdded;
  BackStackState[] mBackStack;
  int mBackStackIndex;
  ArrayList<FragmentManager.LaunchedFragmentInfo> mLaunchedFragments;
  String mPrimaryNavActiveWho = null;
  ArrayList<String> mResultKeys = new ArrayList();
  ArrayList<Bundle> mResults = new ArrayList();
  
  public FragmentManagerState() {}
  
  public FragmentManagerState(Parcel paramParcel)
  {
    this.mActive = paramParcel.createTypedArrayList(FragmentState.CREATOR);
    this.mAdded = paramParcel.createStringArrayList();
    this.mBackStack = ((BackStackState[])paramParcel.createTypedArray(BackStackState.CREATOR));
    this.mBackStackIndex = paramParcel.readInt();
    this.mPrimaryNavActiveWho = paramParcel.readString();
    this.mResultKeys = paramParcel.createStringArrayList();
    this.mResults = paramParcel.createTypedArrayList(Bundle.CREATOR);
    this.mLaunchedFragments = paramParcel.createTypedArrayList(FragmentManager.LaunchedFragmentInfo.CREATOR);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeTypedList(this.mActive);
    paramParcel.writeStringList(this.mAdded);
    paramParcel.writeTypedArray(this.mBackStack, paramInt);
    paramParcel.writeInt(this.mBackStackIndex);
    paramParcel.writeString(this.mPrimaryNavActiveWho);
    paramParcel.writeStringList(this.mResultKeys);
    paramParcel.writeTypedList(this.mResults);
    paramParcel.writeTypedList(this.mLaunchedFragments);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentManagerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */