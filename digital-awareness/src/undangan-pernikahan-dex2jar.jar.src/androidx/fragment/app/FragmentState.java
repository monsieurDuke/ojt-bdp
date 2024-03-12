package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.lifecycle.Lifecycle.State;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class FragmentState
  implements Parcelable
{
  public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator()
  {
    public FragmentState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FragmentState(paramAnonymousParcel);
    }
    
    public FragmentState[] newArray(int paramAnonymousInt)
    {
      return new FragmentState[paramAnonymousInt];
    }
  };
  final Bundle mArguments;
  final String mClassName;
  final int mContainerId;
  final boolean mDetached;
  final int mFragmentId;
  final boolean mFromLayout;
  final boolean mHidden;
  final int mMaxLifecycleState;
  final boolean mRemoving;
  final boolean mRetainInstance;
  Bundle mSavedFragmentState;
  final String mTag;
  final String mWho;
  
  FragmentState(Parcel paramParcel)
  {
    this.mClassName = paramParcel.readString();
    this.mWho = paramParcel.readString();
    int i = paramParcel.readInt();
    boolean bool2 = true;
    boolean bool1;
    if (i != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mFromLayout = bool1;
    this.mFragmentId = paramParcel.readInt();
    this.mContainerId = paramParcel.readInt();
    this.mTag = paramParcel.readString();
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mRetainInstance = bool1;
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mRemoving = bool1;
    if (paramParcel.readInt() != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mDetached = bool1;
    this.mArguments = paramParcel.readBundle();
    if (paramParcel.readInt() != 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    this.mHidden = bool1;
    this.mSavedFragmentState = paramParcel.readBundle();
    this.mMaxLifecycleState = paramParcel.readInt();
  }
  
  FragmentState(Fragment paramFragment)
  {
    this.mClassName = paramFragment.getClass().getName();
    this.mWho = paramFragment.mWho;
    this.mFromLayout = paramFragment.mFromLayout;
    this.mFragmentId = paramFragment.mFragmentId;
    this.mContainerId = paramFragment.mContainerId;
    this.mTag = paramFragment.mTag;
    this.mRetainInstance = paramFragment.mRetainInstance;
    this.mRemoving = paramFragment.mRemoving;
    this.mDetached = paramFragment.mDetached;
    this.mArguments = paramFragment.mArguments;
    this.mHidden = paramFragment.mHidden;
    this.mMaxLifecycleState = paramFragment.mMaxState.ordinal();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentState{");
    localStringBuilder.append(this.mClassName);
    localStringBuilder.append(" (");
    localStringBuilder.append(this.mWho);
    localStringBuilder.append(")}:");
    if (this.mFromLayout) {
      localStringBuilder.append(" fromLayout");
    }
    if (this.mContainerId != 0)
    {
      localStringBuilder.append(" id=0x");
      str = Integer.toHexString(this.mContainerId);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
    }
    String str = this.mTag;
    if ((str != null) && (!str.isEmpty()))
    {
      localStringBuilder.append(" tag=");
      localStringBuilder.append(this.mTag);
    }
    if (this.mRetainInstance) {
      localStringBuilder.append(" retainInstance");
    }
    if (this.mRemoving) {
      localStringBuilder.append(" removing");
    }
    if (this.mDetached) {
      localStringBuilder.append(" detached");
    }
    if (this.mHidden) {
      localStringBuilder.append(" hidden");
    }
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mClassName);
    paramParcel.writeString(this.mWho);
    paramParcel.writeInt(this.mFromLayout);
    paramParcel.writeInt(this.mFragmentId);
    paramParcel.writeInt(this.mContainerId);
    paramParcel.writeString(this.mTag);
    paramParcel.writeInt(this.mRetainInstance);
    paramParcel.writeInt(this.mRemoving);
    paramParcel.writeInt(this.mDetached);
    paramParcel.writeBundle(this.mArguments);
    paramParcel.writeInt(this.mHidden);
    paramParcel.writeBundle(this.mSavedFragmentState);
    paramParcel.writeInt(this.mMaxLifecycleState);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */