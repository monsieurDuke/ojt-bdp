package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import androidx.fragment.R.styleable;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class FragmentLayoutInflaterFactory
  implements LayoutInflater.Factory2
{
  private static final String TAG = "FragmentManager";
  final FragmentManager mFragmentManager;
  
  FragmentLayoutInflaterFactory(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }
  
  public View onCreateView(View paramView, String paramString, final Context paramContext, AttributeSet paramAttributeSet)
  {
    if (FragmentContainerView.class.getName().equals(paramString)) {
      return new FragmentContainerView(paramContext, paramAttributeSet, this.mFragmentManager);
    }
    boolean bool = "fragment".equals(paramString);
    paramString = null;
    if (!bool) {
      return null;
    }
    Object localObject1 = paramAttributeSet.getAttributeValue(null, "class");
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Fragment);
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = localTypedArray.getString(R.styleable.Fragment_android_name);
    }
    int k = localTypedArray.getResourceId(R.styleable.Fragment_android_id, -1);
    String str = localTypedArray.getString(R.styleable.Fragment_android_tag);
    localTypedArray.recycle();
    if ((localObject2 != null) && (FragmentFactory.isFragmentClass(paramContext.getClassLoader(), (String)localObject2)))
    {
      int i;
      if (paramView != null) {
        i = paramView.getId();
      } else {
        i = 0;
      }
      if ((i == -1) && (k == -1) && (str == null)) {
        throw new IllegalArgumentException(paramAttributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + (String)localObject2);
      }
      if (k != -1) {
        paramString = this.mFragmentManager.findFragmentById(k);
      }
      localObject1 = paramString;
      if (paramString == null)
      {
        localObject1 = paramString;
        if (str != null) {
          localObject1 = this.mFragmentManager.findFragmentByTag(str);
        }
      }
      paramString = (String)localObject1;
      if (localObject1 == null)
      {
        paramString = (String)localObject1;
        if (i != -1) {
          paramString = this.mFragmentManager.findFragmentById(i);
        }
      }
      if (paramString == null)
      {
        paramString = this.mFragmentManager.getFragmentFactory().instantiate(paramContext.getClassLoader(), (String)localObject2);
        paramString.mFromLayout = true;
        int j;
        if (k != 0) {
          j = k;
        } else {
          j = i;
        }
        paramString.mFragmentId = j;
        paramString.mContainerId = i;
        paramString.mTag = str;
        paramString.mInLayout = true;
        paramString.mFragmentManager = this.mFragmentManager;
        paramString.mHost = this.mFragmentManager.getHost();
        paramString.onInflate(this.mFragmentManager.getHost().getContext(), paramAttributeSet, paramString.mSavedFragmentState);
        localObject1 = this.mFragmentManager.addFragment(paramString);
        paramAttributeSet = paramString;
        paramContext = (Context)localObject1;
        if (FragmentManager.isLoggingEnabled(2))
        {
          paramContext = new StringBuilder().append("Fragment ").append(paramString).append(" has been inflated via the <fragment> tag: id=0x");
          paramAttributeSet = Integer.toHexString(k);
          Log5ECF72.a(paramAttributeSet);
          LogE84000.a(paramAttributeSet);
          Log229316.a(paramAttributeSet);
          Log.v("FragmentManager", paramAttributeSet);
          paramAttributeSet = paramString;
          paramContext = (Context)localObject1;
        }
      }
      else
      {
        if (paramString.mInLayout) {
          break label724;
        }
        paramString.mInLayout = true;
        paramString.mFragmentManager = this.mFragmentManager;
        paramString.mHost = this.mFragmentManager.getHost();
        paramString.onInflate(this.mFragmentManager.getHost().getContext(), paramAttributeSet, paramString.mSavedFragmentState);
        localObject1 = this.mFragmentManager.createOrGetFragmentStateManager(paramString);
        paramAttributeSet = paramString;
        paramContext = (Context)localObject1;
        if (FragmentManager.isLoggingEnabled(2))
        {
          paramAttributeSet = new StringBuilder().append("Retained Fragment ").append(paramString).append(" has been re-attached via the <fragment> tag: id=0x");
          paramContext = Integer.toHexString(k);
          Log5ECF72.a(paramContext);
          LogE84000.a(paramContext);
          Log229316.a(paramContext);
          Log.v("FragmentManager", paramContext);
          paramContext = (Context)localObject1;
          paramAttributeSet = paramString;
        }
      }
      paramAttributeSet.mContainer = ((ViewGroup)paramView);
      paramContext.moveToExpectedState();
      paramContext.ensureInflatedView();
      if (paramAttributeSet.mView != null)
      {
        if (k != 0) {
          paramAttributeSet.mView.setId(k);
        }
        if (paramAttributeSet.mView.getTag() == null) {
          paramAttributeSet.mView.setTag(str);
        }
        paramAttributeSet.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
        {
          public void onViewAttachedToWindow(View paramAnonymousView)
          {
            paramAnonymousView = paramContext.getFragment();
            paramContext.moveToExpectedState();
            SpecialEffectsController.getOrCreateController((ViewGroup)paramAnonymousView.mView.getParent(), FragmentLayoutInflaterFactory.this.mFragmentManager).forceCompleteAllOperations();
          }
          
          public void onViewDetachedFromWindow(View paramAnonymousView) {}
        });
        return paramAttributeSet.mView;
      }
      throw new IllegalStateException("Fragment " + (String)localObject2 + " did not create a view.");
      label724:
      paramString = new StringBuilder().append(paramAttributeSet.getPositionDescription()).append(": Duplicate id 0x");
      paramView = Integer.toHexString(k);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      paramString = paramString.append(paramView).append(", tag ").append(str).append(", or parent id 0x");
      paramView = Integer.toHexString(i);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      throw new IllegalArgumentException(paramView + " with another fragment for " + (String)localObject2);
    }
    return null;
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentLayoutInflaterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */