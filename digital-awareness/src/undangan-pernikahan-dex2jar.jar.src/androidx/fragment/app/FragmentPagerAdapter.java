package androidx.fragment.app;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle.State;
import androidx.viewpager.widget.PagerAdapter;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Deprecated
public abstract class FragmentPagerAdapter
  extends PagerAdapter
{
  public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
  @Deprecated
  public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
  private static final boolean DEBUG = false;
  private static final String TAG = "FragmentPagerAdapter";
  private final int mBehavior;
  private FragmentTransaction mCurTransaction = null;
  private Fragment mCurrentPrimaryItem = null;
  private boolean mExecutingFinishUpdate;
  private final FragmentManager mFragmentManager;
  
  @Deprecated
  public FragmentPagerAdapter(FragmentManager paramFragmentManager)
  {
    this(paramFragmentManager, 0);
  }
  
  public FragmentPagerAdapter(FragmentManager paramFragmentManager, int paramInt)
  {
    this.mFragmentManager = paramFragmentManager;
    this.mBehavior = paramInt;
  }
  
  private static String makeFragmentName(int paramInt, long paramLong)
  {
    return "android:switcher:" + paramInt + ":" + paramLong;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    paramViewGroup = (Fragment)paramObject;
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    this.mCurTransaction.detach(paramViewGroup);
    if (paramViewGroup.equals(this.mCurrentPrimaryItem)) {
      this.mCurrentPrimaryItem = null;
    }
  }
  
  public void finishUpdate(ViewGroup paramViewGroup)
  {
    paramViewGroup = this.mCurTransaction;
    if (paramViewGroup != null)
    {
      if (!this.mExecutingFinishUpdate) {}
      try
      {
        this.mExecutingFinishUpdate = true;
        paramViewGroup.commitNowAllowingStateLoss();
        this.mExecutingFinishUpdate = false;
      }
      finally
      {
        this.mExecutingFinishUpdate = false;
      }
    }
  }
  
  public abstract Fragment getItem(int paramInt);
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    if (this.mCurTransaction == null) {
      this.mCurTransaction = this.mFragmentManager.beginTransaction();
    }
    long l = getItemId(paramInt);
    Object localObject = makeFragmentName(paramViewGroup.getId(), l);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localObject = this.mFragmentManager.findFragmentByTag((String)localObject);
    if (localObject != null)
    {
      this.mCurTransaction.attach((Fragment)localObject);
      paramViewGroup = (ViewGroup)localObject;
    }
    else
    {
      localObject = getItem(paramInt);
      FragmentTransaction localFragmentTransaction = this.mCurTransaction;
      paramInt = paramViewGroup.getId();
      paramViewGroup = makeFragmentName(paramViewGroup.getId(), l);
      Log5ECF72.a(paramViewGroup);
      LogE84000.a(paramViewGroup);
      Log229316.a(paramViewGroup);
      localFragmentTransaction.add(paramInt, (Fragment)localObject, paramViewGroup);
      paramViewGroup = (ViewGroup)localObject;
    }
    if (paramViewGroup != this.mCurrentPrimaryItem)
    {
      paramViewGroup.setMenuVisibility(false);
      if (this.mBehavior == 1) {
        this.mCurTransaction.setMaxLifecycle(paramViewGroup, Lifecycle.State.STARTED);
      } else {
        paramViewGroup.setUserVisibleHint(false);
      }
    }
    return paramViewGroup;
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    boolean bool;
    if (((Fragment)paramObject).getView() == paramView) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader) {}
  
  public Parcelable saveState()
  {
    return null;
  }
  
  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    paramObject = (Fragment)paramObject;
    paramViewGroup = this.mCurrentPrimaryItem;
    if (paramObject != paramViewGroup)
    {
      if (paramViewGroup != null)
      {
        paramViewGroup.setMenuVisibility(false);
        if (this.mBehavior == 1)
        {
          if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
          }
          this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
        }
        else
        {
          this.mCurrentPrimaryItem.setUserVisibleHint(false);
        }
      }
      ((Fragment)paramObject).setMenuVisibility(true);
      if (this.mBehavior == 1)
      {
        if (this.mCurTransaction == null) {
          this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.setMaxLifecycle((Fragment)paramObject, Lifecycle.State.RESUMED);
      }
      else
      {
        ((Fragment)paramObject).setUserVisibleHint(true);
      }
      this.mCurrentPrimaryItem = ((Fragment)paramObject);
    }
  }
  
  public void startUpdate(ViewGroup paramViewGroup)
  {
    if (paramViewGroup.getId() != -1) {
      return;
    }
    throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */