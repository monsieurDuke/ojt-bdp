package androidx.fragment.app;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.Lifecycle.State;
import java.io.PrintWriter;
import java.util.ArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class BackStackRecord
  extends FragmentTransaction
  implements FragmentManager.BackStackEntry, FragmentManager.OpGenerator
{
  private static final String TAG = "FragmentManager";
  boolean mCommitted;
  int mIndex = -1;
  final FragmentManager mManager;
  
  BackStackRecord(FragmentManager paramFragmentManager)
  {
    super(localFragmentFactory, localClassLoader);
    this.mManager = paramFragmentManager;
  }
  
  private static boolean isFragmentPostponed(FragmentTransaction.Op paramOp)
  {
    paramOp = paramOp.mFragment;
    boolean bool;
    if ((paramOp != null) && (paramOp.mAdded) && (paramOp.mView != null) && (!paramOp.mDetached) && (!paramOp.mHidden) && (paramOp.isPostponed())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void bumpBackStackNesting(int paramInt)
  {
    if (!this.mAddToBackStack) {
      return;
    }
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Bump nesting in " + this + " by " + paramInt);
    }
    int j = this.mOps.size();
    for (int i = 0; i < j; i++)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      if (localOp.mFragment != null)
      {
        Fragment localFragment = localOp.mFragment;
        localFragment.mBackStackNesting += paramInt;
        if (FragmentManager.isLoggingEnabled(2)) {
          Log.v("FragmentManager", "Bump nesting of " + localOp.mFragment + " to " + localOp.mFragment.mBackStackNesting);
        }
      }
    }
  }
  
  public int commit()
  {
    return commitInternal(false);
  }
  
  public int commitAllowingStateLoss()
  {
    return commitInternal(true);
  }
  
  int commitInternal(boolean paramBoolean)
  {
    if (!this.mCommitted)
    {
      if (FragmentManager.isLoggingEnabled(2))
      {
        Log.v("FragmentManager", "Commit: " + this);
        PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
        dump("  ", localPrintWriter);
        localPrintWriter.close();
      }
      this.mCommitted = true;
      if (this.mAddToBackStack) {
        this.mIndex = this.mManager.allocBackStackIndex();
      } else {
        this.mIndex = -1;
      }
      this.mManager.enqueueAction(this, paramBoolean);
      return this.mIndex;
    }
    throw new IllegalStateException("commit already called");
  }
  
  public void commitNow()
  {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, false);
  }
  
  public void commitNowAllowingStateLoss()
  {
    disallowAddToBackStack();
    this.mManager.execSingleAction(this, true);
  }
  
  public FragmentTransaction detach(Fragment paramFragment)
  {
    if ((paramFragment.mFragmentManager != null) && (paramFragment.mFragmentManager != this.mManager)) {
      throw new IllegalStateException("Cannot detach Fragment attached to a different FragmentManager. Fragment " + paramFragment.toString() + " is already attached to a FragmentManager.");
    }
    return super.detach(paramFragment);
  }
  
  void doAddOp(int paramInt1, Fragment paramFragment, String paramString, int paramInt2)
  {
    super.doAddOp(paramInt1, paramFragment, paramString, paramInt2);
    paramFragment.mFragmentManager = this.mManager;
  }
  
  public void dump(String paramString, PrintWriter paramPrintWriter)
  {
    dump(paramString, paramPrintWriter, true);
  }
  
  public void dump(String paramString, PrintWriter paramPrintWriter, boolean paramBoolean)
  {
    String str;
    if (paramBoolean)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mName=");
      paramPrintWriter.print(this.mName);
      paramPrintWriter.print(" mIndex=");
      paramPrintWriter.print(this.mIndex);
      paramPrintWriter.print(" mCommitted=");
      paramPrintWriter.println(this.mCommitted);
      if (this.mTransition != 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mTransition=#");
        str = Integer.toHexString(this.mTransition);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.print(str);
      }
      if ((this.mEnterAnim != 0) || (this.mExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mEnterAnim=#");
        str = Integer.toHexString(this.mEnterAnim);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.print(str);
        paramPrintWriter.print(" mExitAnim=#");
        str = Integer.toHexString(this.mExitAnim);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.println(str);
      }
      if ((this.mPopEnterAnim != 0) || (this.mPopExitAnim != 0))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mPopEnterAnim=#");
        str = Integer.toHexString(this.mPopEnterAnim);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.print(str);
        paramPrintWriter.print(" mPopExitAnim=#");
        str = Integer.toHexString(this.mPopExitAnim);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.println(str);
      }
      if ((this.mBreadCrumbTitleRes != 0) || (this.mBreadCrumbTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbTitleRes=#");
        str = Integer.toHexString(this.mBreadCrumbTitleRes);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.print(str);
        paramPrintWriter.print(" mBreadCrumbTitleText=");
        paramPrintWriter.println(this.mBreadCrumbTitleText);
      }
      if ((this.mBreadCrumbShortTitleRes != 0) || (this.mBreadCrumbShortTitleText != null))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mBreadCrumbShortTitleRes=#");
        str = Integer.toHexString(this.mBreadCrumbShortTitleRes);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramPrintWriter.print(str);
        paramPrintWriter.print(" mBreadCrumbShortTitleText=");
        paramPrintWriter.println(this.mBreadCrumbShortTitleText);
      }
    }
    if (!this.mOps.isEmpty())
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Operations:");
      int j = this.mOps.size();
      for (int i = 0; i < j; i++)
      {
        FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
        switch (localOp.mCmd)
        {
        default: 
          str = "cmd=" + localOp.mCmd;
          break;
        case 10: 
          str = "OP_SET_MAX_LIFECYCLE";
          break;
        case 9: 
          str = "UNSET_PRIMARY_NAV";
          break;
        case 8: 
          str = "SET_PRIMARY_NAV";
          break;
        case 7: 
          str = "ATTACH";
          break;
        case 6: 
          str = "DETACH";
          break;
        case 5: 
          str = "SHOW";
          break;
        case 4: 
          str = "HIDE";
          break;
        case 3: 
          str = "REMOVE";
          break;
        case 2: 
          str = "REPLACE";
          break;
        case 1: 
          str = "ADD";
          break;
        case 0: 
          str = "NULL";
        }
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  Op #");
        paramPrintWriter.print(i);
        paramPrintWriter.print(": ");
        paramPrintWriter.print(str);
        paramPrintWriter.print(" ");
        paramPrintWriter.println(localOp.mFragment);
        if (paramBoolean)
        {
          if ((localOp.mEnterAnim != 0) || (localOp.mExitAnim != 0))
          {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("enterAnim=#");
            str = Integer.toHexString(localOp.mEnterAnim);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            paramPrintWriter.print(str);
            paramPrintWriter.print(" exitAnim=#");
            str = Integer.toHexString(localOp.mExitAnim);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            paramPrintWriter.println(str);
          }
          if ((localOp.mPopEnterAnim != 0) || (localOp.mPopExitAnim != 0))
          {
            paramPrintWriter.print(paramString);
            paramPrintWriter.print("popEnterAnim=#");
            str = Integer.toHexString(localOp.mPopEnterAnim);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            paramPrintWriter.print(str);
            paramPrintWriter.print(" popExitAnim=#");
            str = Integer.toHexString(localOp.mPopExitAnim);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            paramPrintWriter.println(str);
          }
        }
      }
    }
  }
  
  void executeOps()
  {
    int j = this.mOps.size();
    Object localObject;
    for (int i = 0; i < j; i++)
    {
      localObject = (FragmentTransaction.Op)this.mOps.get(i);
      Fragment localFragment = ((FragmentTransaction.Op)localObject).mFragment;
      if (localFragment != null)
      {
        localFragment.setPopDirection(false);
        localFragment.setNextTransition(this.mTransition);
        localFragment.setSharedElementNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames);
      }
      switch (((FragmentTransaction.Op)localObject).mCmd)
      {
      case 2: 
      default: 
        throw new IllegalArgumentException("Unknown cmd: " + ((FragmentTransaction.Op)localObject).mCmd);
      case 10: 
        this.mManager.setMaxLifecycle(localFragment, ((FragmentTransaction.Op)localObject).mCurrentMaxState);
        break;
      case 9: 
        this.mManager.setPrimaryNavigationFragment(null);
        break;
      case 8: 
        this.mManager.setPrimaryNavigationFragment(localFragment);
        break;
      case 7: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.setExitAnimationOrder(localFragment, false);
        this.mManager.attachFragment(localFragment);
        break;
      case 6: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.detachFragment(localFragment);
        break;
      case 5: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.setExitAnimationOrder(localFragment, false);
        this.mManager.showFragment(localFragment);
        break;
      case 4: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.hideFragment(localFragment);
        break;
      case 3: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.removeFragment(localFragment);
        break;
      case 1: 
        localFragment.setAnimations(((FragmentTransaction.Op)localObject).mEnterAnim, ((FragmentTransaction.Op)localObject).mExitAnim, ((FragmentTransaction.Op)localObject).mPopEnterAnim, ((FragmentTransaction.Op)localObject).mPopExitAnim);
        this.mManager.setExitAnimationOrder(localFragment, false);
        this.mManager.addFragment(localFragment);
      }
      if ((!this.mReorderingAllowed) && (((FragmentTransaction.Op)localObject).mCmd != 1) && (localFragment != null) && (!FragmentManager.USE_STATE_MANAGER)) {
        this.mManager.moveFragmentToExpectedState(localFragment);
      }
    }
    if ((!this.mReorderingAllowed) && (!FragmentManager.USE_STATE_MANAGER))
    {
      localObject = this.mManager;
      ((FragmentManager)localObject).moveToState(((FragmentManager)localObject).mCurState, true);
    }
  }
  
  void executePopOps(boolean paramBoolean)
  {
    Object localObject;
    for (int i = this.mOps.size() - 1; i >= 0; i--)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      localObject = localOp.mFragment;
      if (localObject != null)
      {
        ((Fragment)localObject).setPopDirection(true);
        ((Fragment)localObject).setNextTransition(FragmentManager.reverseTransit(this.mTransition));
        ((Fragment)localObject).setSharedElementNames(this.mSharedElementTargetNames, this.mSharedElementSourceNames);
      }
      switch (localOp.mCmd)
      {
      case 2: 
      default: 
        throw new IllegalArgumentException("Unknown cmd: " + localOp.mCmd);
      case 10: 
        this.mManager.setMaxLifecycle((Fragment)localObject, localOp.mOldMaxState);
        break;
      case 9: 
        this.mManager.setPrimaryNavigationFragment((Fragment)localObject);
        break;
      case 8: 
        this.mManager.setPrimaryNavigationFragment(null);
        break;
      case 7: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.setExitAnimationOrder((Fragment)localObject, true);
        this.mManager.detachFragment((Fragment)localObject);
        break;
      case 6: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.attachFragment((Fragment)localObject);
        break;
      case 5: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.setExitAnimationOrder((Fragment)localObject, true);
        this.mManager.hideFragment((Fragment)localObject);
        break;
      case 4: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.showFragment((Fragment)localObject);
        break;
      case 3: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.addFragment((Fragment)localObject);
        break;
      case 1: 
        ((Fragment)localObject).setAnimations(localOp.mEnterAnim, localOp.mExitAnim, localOp.mPopEnterAnim, localOp.mPopExitAnim);
        this.mManager.setExitAnimationOrder((Fragment)localObject, true);
        this.mManager.removeFragment((Fragment)localObject);
      }
      if ((!this.mReorderingAllowed) && (localOp.mCmd != 3) && (localObject != null) && (!FragmentManager.USE_STATE_MANAGER)) {
        this.mManager.moveFragmentToExpectedState((Fragment)localObject);
      }
    }
    if ((!this.mReorderingAllowed) && (paramBoolean) && (!FragmentManager.USE_STATE_MANAGER))
    {
      localObject = this.mManager;
      ((FragmentManager)localObject).moveToState(((FragmentManager)localObject).mCurState, true);
    }
  }
  
  Fragment expandOps(ArrayList<Fragment> paramArrayList, Fragment paramFragment)
  {
    int i = 0;
    for (Fragment localFragment1 = paramFragment; i < this.mOps.size(); localFragment1 = paramFragment)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      int j;
      switch (localOp.mCmd)
      {
      case 4: 
      case 5: 
      default: 
        j = i;
        paramFragment = localFragment1;
        break;
      case 8: 
        this.mOps.add(i, new FragmentTransaction.Op(9, localFragment1));
        j = i + 1;
        paramFragment = localOp.mFragment;
        break;
      case 3: 
      case 6: 
        paramArrayList.remove(localOp.mFragment);
        j = i;
        paramFragment = localFragment1;
        if (localOp.mFragment == localFragment1)
        {
          this.mOps.add(i, new FragmentTransaction.Op(9, localOp.mFragment));
          j = i + 1;
          paramFragment = null;
        }
        break;
      case 2: 
        Fragment localFragment2 = localOp.mFragment;
        int i1 = localFragment2.mContainerId;
        int k = 0;
        j = paramArrayList.size() - 1;
        for (paramFragment = localFragment1; j >= 0; paramFragment = localFragment1)
        {
          Fragment localFragment3 = (Fragment)paramArrayList.get(j);
          int n = i;
          int m = k;
          localFragment1 = paramFragment;
          if (localFragment3.mContainerId == i1) {
            if (localFragment3 == localFragment2)
            {
              m = 1;
              n = i;
              localFragment1 = paramFragment;
            }
            else
            {
              m = i;
              localFragment1 = paramFragment;
              if (localFragment3 == paramFragment)
              {
                this.mOps.add(i, new FragmentTransaction.Op(9, localFragment3));
                m = i + 1;
                localFragment1 = null;
              }
              paramFragment = new FragmentTransaction.Op(3, localFragment3);
              paramFragment.mEnterAnim = localOp.mEnterAnim;
              paramFragment.mPopEnterAnim = localOp.mPopEnterAnim;
              paramFragment.mExitAnim = localOp.mExitAnim;
              paramFragment.mPopExitAnim = localOp.mPopExitAnim;
              this.mOps.add(m, paramFragment);
              paramArrayList.remove(localFragment3);
              n = m + 1;
              m = k;
            }
          }
          j--;
          i = n;
          k = m;
        }
        if (k != 0)
        {
          this.mOps.remove(i);
          i--;
        }
        else
        {
          localOp.mCmd = 1;
          paramArrayList.add(localFragment2);
        }
        j = i;
        break;
      case 1: 
      case 7: 
        paramArrayList.add(localOp.mFragment);
        paramFragment = localFragment1;
        j = i;
      }
      i = j + 1;
    }
    return localFragment1;
  }
  
  public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Run: " + this);
    }
    paramArrayList.add(this);
    paramArrayList1.add(Boolean.valueOf(false));
    if (this.mAddToBackStack) {
      this.mManager.addBackStackState(this);
    }
    return true;
  }
  
  public CharSequence getBreadCrumbShortTitle()
  {
    if (this.mBreadCrumbShortTitleRes != 0) {
      return this.mManager.getHost().getContext().getText(this.mBreadCrumbShortTitleRes);
    }
    return this.mBreadCrumbShortTitleText;
  }
  
  public int getBreadCrumbShortTitleRes()
  {
    return this.mBreadCrumbShortTitleRes;
  }
  
  public CharSequence getBreadCrumbTitle()
  {
    if (this.mBreadCrumbTitleRes != 0) {
      return this.mManager.getHost().getContext().getText(this.mBreadCrumbTitleRes);
    }
    return this.mBreadCrumbTitleText;
  }
  
  public int getBreadCrumbTitleRes()
  {
    return this.mBreadCrumbTitleRes;
  }
  
  public int getId()
  {
    return this.mIndex;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public FragmentTransaction hide(Fragment paramFragment)
  {
    if ((paramFragment.mFragmentManager != null) && (paramFragment.mFragmentManager != this.mManager)) {
      throw new IllegalStateException("Cannot hide Fragment attached to a different FragmentManager. Fragment " + paramFragment.toString() + " is already attached to a FragmentManager.");
    }
    return super.hide(paramFragment);
  }
  
  boolean interactsWith(int paramInt)
  {
    int k = this.mOps.size();
    for (int i = 0;; i++)
    {
      int j = 0;
      if (i >= k) {
        break;
      }
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      if (localOp.mFragment != null) {
        j = localOp.mFragment.mContainerId;
      }
      if ((j != 0) && (j == paramInt)) {
        return true;
      }
    }
    return false;
  }
  
  boolean interactsWith(ArrayList<BackStackRecord> paramArrayList, int paramInt1, int paramInt2)
  {
    if (paramInt2 == paramInt1) {
      return false;
    }
    int i2 = this.mOps.size();
    int k = -1;
    int j = 0;
    while (j < i2)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(j);
      int i;
      if (localOp.mFragment != null) {
        i = localOp.mFragment.mContainerId;
      } else {
        i = 0;
      }
      int n = k;
      if (i != 0)
      {
        n = k;
        if (i != k)
        {
          k = i;
          for (int m = paramInt1;; m++)
          {
            n = k;
            if (m >= paramInt2) {
              break;
            }
            BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(m);
            int i3 = localBackStackRecord.mOps.size();
            for (n = 0; n < i3; n++)
            {
              localOp = (FragmentTransaction.Op)localBackStackRecord.mOps.get(n);
              int i1;
              if (localOp.mFragment != null) {
                i1 = localOp.mFragment.mContainerId;
              } else {
                i1 = 0;
              }
              if (i1 == i) {
                return true;
              }
            }
          }
        }
      }
      j++;
      k = n;
    }
    return false;
  }
  
  public boolean isEmpty()
  {
    return this.mOps.isEmpty();
  }
  
  boolean isPostponed()
  {
    for (int i = 0; i < this.mOps.size(); i++) {
      if (isFragmentPostponed((FragmentTransaction.Op)this.mOps.get(i))) {
        return true;
      }
    }
    return false;
  }
  
  public FragmentTransaction remove(Fragment paramFragment)
  {
    if ((paramFragment.mFragmentManager != null) && (paramFragment.mFragmentManager != this.mManager)) {
      throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + paramFragment.toString() + " is already attached to a FragmentManager.");
    }
    return super.remove(paramFragment);
  }
  
  public void runOnCommitRunnables()
  {
    if (this.mCommitRunnables != null)
    {
      for (int i = 0; i < this.mCommitRunnables.size(); i++) {
        ((Runnable)this.mCommitRunnables.get(i)).run();
      }
      this.mCommitRunnables = null;
    }
  }
  
  public FragmentTransaction setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState)
  {
    if (paramFragment.mFragmentManager == this.mManager)
    {
      if ((paramState == Lifecycle.State.INITIALIZED) && (paramFragment.mState > -1)) {
        throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + paramState + " after the Fragment has been created");
      }
      if (paramState != Lifecycle.State.DESTROYED) {
        return super.setMaxLifecycle(paramFragment, paramState);
      }
      throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + paramState + ". Use remove() to remove the fragment from the FragmentManager and trigger its destruction.");
    }
    throw new IllegalArgumentException("Cannot setMaxLifecycle for Fragment not attached to FragmentManager " + this.mManager);
  }
  
  void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener paramOnStartEnterTransitionListener)
  {
    for (int i = 0; i < this.mOps.size(); i++)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      if (isFragmentPostponed(localOp)) {
        localOp.mFragment.setOnStartEnterTransitionListener(paramOnStartEnterTransitionListener);
      }
    }
  }
  
  public FragmentTransaction setPrimaryNavigationFragment(Fragment paramFragment)
  {
    if ((paramFragment != null) && (paramFragment.mFragmentManager != null) && (paramFragment.mFragmentManager != this.mManager)) {
      throw new IllegalStateException("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment " + paramFragment.toString() + " is already attached to a FragmentManager.");
    }
    return super.setPrimaryNavigationFragment(paramFragment);
  }
  
  public FragmentTransaction show(Fragment paramFragment)
  {
    if ((paramFragment.mFragmentManager != null) && (paramFragment.mFragmentManager != this.mManager)) {
      throw new IllegalStateException("Cannot show Fragment attached to a different FragmentManager. Fragment " + paramFragment.toString() + " is already attached to a FragmentManager.");
    }
    return super.show(paramFragment);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("BackStackEntry{");
    String str = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder.append(str);
    if (this.mIndex >= 0)
    {
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mIndex);
    }
    if (this.mName != null)
    {
      localStringBuilder.append(" ");
      localStringBuilder.append(this.mName);
    }
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  Fragment trackAddedFragmentsInPop(ArrayList<Fragment> paramArrayList, Fragment paramFragment)
  {
    for (int i = this.mOps.size() - 1; i >= 0; i--)
    {
      FragmentTransaction.Op localOp = (FragmentTransaction.Op)this.mOps.get(i);
      switch (localOp.mCmd)
      {
      case 2: 
      case 4: 
      case 5: 
      default: 
        break;
      case 10: 
        localOp.mCurrentMaxState = localOp.mOldMaxState;
        break;
      case 9: 
        paramFragment = localOp.mFragment;
        break;
      case 8: 
        paramFragment = null;
        break;
      case 3: 
      case 6: 
        paramArrayList.add(localOp.mFragment);
        break;
      case 1: 
      case 7: 
        paramArrayList.remove(localOp.mFragment);
      }
    }
    return paramFragment;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/BackStackRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */