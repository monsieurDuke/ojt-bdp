package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import androidx.lifecycle.Lifecycle.State;
import java.util.ArrayList;

final class BackStackState
  implements Parcelable
{
  public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator()
  {
    public BackStackState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new BackStackState(paramAnonymousParcel);
    }
    
    public BackStackState[] newArray(int paramAnonymousInt)
    {
      return new BackStackState[paramAnonymousInt];
    }
  };
  private static final String TAG = "FragmentManager";
  final int mBreadCrumbShortTitleRes;
  final CharSequence mBreadCrumbShortTitleText;
  final int mBreadCrumbTitleRes;
  final CharSequence mBreadCrumbTitleText;
  final int[] mCurrentMaxLifecycleStates;
  final ArrayList<String> mFragmentWhos;
  final int mIndex;
  final String mName;
  final int[] mOldMaxLifecycleStates;
  final int[] mOps;
  final boolean mReorderingAllowed;
  final ArrayList<String> mSharedElementSourceNames;
  final ArrayList<String> mSharedElementTargetNames;
  final int mTransition;
  
  public BackStackState(Parcel paramParcel)
  {
    this.mOps = paramParcel.createIntArray();
    this.mFragmentWhos = paramParcel.createStringArrayList();
    this.mOldMaxLifecycleStates = paramParcel.createIntArray();
    this.mCurrentMaxLifecycleStates = paramParcel.createIntArray();
    this.mTransition = paramParcel.readInt();
    this.mName = paramParcel.readString();
    this.mIndex = paramParcel.readInt();
    this.mBreadCrumbTitleRes = paramParcel.readInt();
    this.mBreadCrumbTitleText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    this.mBreadCrumbShortTitleRes = paramParcel.readInt();
    this.mBreadCrumbShortTitleText = ((CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel));
    this.mSharedElementSourceNames = paramParcel.createStringArrayList();
    this.mSharedElementTargetNames = paramParcel.createStringArrayList();
    boolean bool;
    if (paramParcel.readInt() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mReorderingAllowed = bool;
  }
  
  public BackStackState(BackStackRecord paramBackStackRecord)
  {
    int k = paramBackStackRecord.mOps.size();
    this.mOps = new int[k * 5];
    if (paramBackStackRecord.mAddToBackStack)
    {
      this.mFragmentWhos = new ArrayList(k);
      this.mOldMaxLifecycleStates = new int[k];
      this.mCurrentMaxLifecycleStates = new int[k];
      int j = 0;
      int i = 0;
      while (i < k)
      {
        FragmentTransaction.Op localOp = (FragmentTransaction.Op)paramBackStackRecord.mOps.get(i);
        Object localObject = this.mOps;
        int m = j + 1;
        localObject[j] = localOp.mCmd;
        ArrayList localArrayList = this.mFragmentWhos;
        if (localOp.mFragment != null) {
          localObject = localOp.mFragment.mWho;
        } else {
          localObject = null;
        }
        localArrayList.add(localObject);
        localObject = this.mOps;
        j = m + 1;
        localObject[m] = localOp.mEnterAnim;
        localObject = this.mOps;
        m = j + 1;
        localObject[j] = localOp.mExitAnim;
        localObject = this.mOps;
        j = m + 1;
        localObject[m] = localOp.mPopEnterAnim;
        this.mOps[j] = localOp.mPopExitAnim;
        this.mOldMaxLifecycleStates[i] = localOp.mOldMaxState.ordinal();
        this.mCurrentMaxLifecycleStates[i] = localOp.mCurrentMaxState.ordinal();
        i++;
        j++;
      }
      this.mTransition = paramBackStackRecord.mTransition;
      this.mName = paramBackStackRecord.mName;
      this.mIndex = paramBackStackRecord.mIndex;
      this.mBreadCrumbTitleRes = paramBackStackRecord.mBreadCrumbTitleRes;
      this.mBreadCrumbTitleText = paramBackStackRecord.mBreadCrumbTitleText;
      this.mBreadCrumbShortTitleRes = paramBackStackRecord.mBreadCrumbShortTitleRes;
      this.mBreadCrumbShortTitleText = paramBackStackRecord.mBreadCrumbShortTitleText;
      this.mSharedElementSourceNames = paramBackStackRecord.mSharedElementSourceNames;
      this.mSharedElementTargetNames = paramBackStackRecord.mSharedElementTargetNames;
      this.mReorderingAllowed = paramBackStackRecord.mReorderingAllowed;
      return;
    }
    throw new IllegalStateException("Not on back stack");
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public BackStackRecord instantiate(FragmentManager paramFragmentManager)
  {
    BackStackRecord localBackStackRecord = new BackStackRecord(paramFragmentManager);
    int j = 0;
    int i = 0;
    while (j < this.mOps.length)
    {
      FragmentTransaction.Op localOp = new FragmentTransaction.Op();
      Object localObject = this.mOps;
      int k = j + 1;
      localOp.mCmd = localObject[j];
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Instantiate " + localBackStackRecord + " op #" + i + " base fragment #" + this.mOps[k]);
      }
      localObject = (String)this.mFragmentWhos.get(i);
      if (localObject != null) {
        localOp.mFragment = paramFragmentManager.findActiveFragment((String)localObject);
      } else {
        localOp.mFragment = null;
      }
      localOp.mOldMaxState = Lifecycle.State.values()[this.mOldMaxLifecycleStates[i]];
      localOp.mCurrentMaxState = Lifecycle.State.values()[this.mCurrentMaxLifecycleStates[i]];
      localObject = this.mOps;
      j = k + 1;
      localOp.mEnterAnim = localObject[k];
      localObject = this.mOps;
      k = j + 1;
      localOp.mExitAnim = localObject[j];
      localObject = this.mOps;
      j = k + 1;
      localOp.mPopEnterAnim = localObject[k];
      localOp.mPopExitAnim = this.mOps[j];
      localBackStackRecord.mEnterAnim = localOp.mEnterAnim;
      localBackStackRecord.mExitAnim = localOp.mExitAnim;
      localBackStackRecord.mPopEnterAnim = localOp.mPopEnterAnim;
      localBackStackRecord.mPopExitAnim = localOp.mPopExitAnim;
      localBackStackRecord.addOp(localOp);
      i++;
      j++;
    }
    localBackStackRecord.mTransition = this.mTransition;
    localBackStackRecord.mName = this.mName;
    localBackStackRecord.mIndex = this.mIndex;
    localBackStackRecord.mAddToBackStack = true;
    localBackStackRecord.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
    localBackStackRecord.mBreadCrumbTitleText = this.mBreadCrumbTitleText;
    localBackStackRecord.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
    localBackStackRecord.mBreadCrumbShortTitleText = this.mBreadCrumbShortTitleText;
    localBackStackRecord.mSharedElementSourceNames = this.mSharedElementSourceNames;
    localBackStackRecord.mSharedElementTargetNames = this.mSharedElementTargetNames;
    localBackStackRecord.mReorderingAllowed = this.mReorderingAllowed;
    localBackStackRecord.bumpBackStackNesting(1);
    return localBackStackRecord;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeIntArray(this.mOps);
    paramParcel.writeStringList(this.mFragmentWhos);
    paramParcel.writeIntArray(this.mOldMaxLifecycleStates);
    paramParcel.writeIntArray(this.mCurrentMaxLifecycleStates);
    paramParcel.writeInt(this.mTransition);
    paramParcel.writeString(this.mName);
    paramParcel.writeInt(this.mIndex);
    paramParcel.writeInt(this.mBreadCrumbTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbTitleText, paramParcel, 0);
    paramParcel.writeInt(this.mBreadCrumbShortTitleRes);
    TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, paramParcel, 0);
    paramParcel.writeStringList(this.mSharedElementSourceNames);
    paramParcel.writeStringList(this.mSharedElementTargetNames);
    paramParcel.writeInt(this.mReorderingAllowed);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/BackStackState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */