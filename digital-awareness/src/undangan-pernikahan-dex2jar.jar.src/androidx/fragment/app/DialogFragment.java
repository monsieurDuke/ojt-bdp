package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

public class DialogFragment
  extends Fragment
  implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener
{
  private static final String SAVED_BACK_STACK_ID = "android:backStackId";
  private static final String SAVED_CANCELABLE = "android:cancelable";
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
  private static final String SAVED_INTERNAL_DIALOG_SHOWING = "android:dialogShowing";
  private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
  private static final String SAVED_STYLE = "android:style";
  private static final String SAVED_THEME = "android:theme";
  public static final int STYLE_NORMAL = 0;
  public static final int STYLE_NO_FRAME = 2;
  public static final int STYLE_NO_INPUT = 3;
  public static final int STYLE_NO_TITLE = 1;
  private int mBackStackId = -1;
  private boolean mCancelable = true;
  private boolean mCreatingDialog;
  private Dialog mDialog;
  private boolean mDialogCreated = false;
  private Runnable mDismissRunnable = new Runnable()
  {
    public void run()
    {
      DialogFragment.this.mOnDismissListener.onDismiss(DialogFragment.this.mDialog);
    }
  };
  private boolean mDismissed;
  private Handler mHandler;
  private Observer<LifecycleOwner> mObserver = new Observer()
  {
    public void onChanged(LifecycleOwner paramAnonymousLifecycleOwner)
    {
      if ((paramAnonymousLifecycleOwner != null) && (DialogFragment.this.mShowsDialog))
      {
        paramAnonymousLifecycleOwner = DialogFragment.this.requireView();
        if (paramAnonymousLifecycleOwner.getParent() == null)
        {
          if (DialogFragment.this.mDialog != null)
          {
            if (FragmentManager.isLoggingEnabled(3)) {
              Log.d("FragmentManager", "DialogFragment " + this + " setting the content view on " + DialogFragment.this.mDialog);
            }
            DialogFragment.this.mDialog.setContentView(paramAnonymousLifecycleOwner);
          }
        }
        else {
          throw new IllegalStateException("DialogFragment can not be attached to a container view");
        }
      }
    }
  };
  private DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener()
  {
    public void onCancel(DialogInterface paramAnonymousDialogInterface)
    {
      if (DialogFragment.this.mDialog != null)
      {
        paramAnonymousDialogInterface = DialogFragment.this;
        paramAnonymousDialogInterface.onCancel(paramAnonymousDialogInterface.mDialog);
      }
    }
  };
  private DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener()
  {
    public void onDismiss(DialogInterface paramAnonymousDialogInterface)
    {
      if (DialogFragment.this.mDialog != null)
      {
        paramAnonymousDialogInterface = DialogFragment.this;
        paramAnonymousDialogInterface.onDismiss(paramAnonymousDialogInterface.mDialog);
      }
    }
  };
  private boolean mShownByMe;
  private boolean mShowsDialog = true;
  private int mStyle = 0;
  private int mTheme = 0;
  private boolean mViewDestroyed;
  
  public DialogFragment() {}
  
  public DialogFragment(int paramInt)
  {
    super(paramInt);
  }
  
  private void dismissInternal(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mDismissed) {
      return;
    }
    this.mDismissed = true;
    this.mShownByMe = false;
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      ((Dialog)localObject).setOnDismissListener(null);
      this.mDialog.dismiss();
      if (!paramBoolean2) {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
          onDismiss(this.mDialog);
        } else {
          this.mHandler.post(this.mDismissRunnable);
        }
      }
    }
    this.mViewDestroyed = true;
    if (this.mBackStackId >= 0)
    {
      getParentFragmentManager().popBackStack(this.mBackStackId, 1);
      this.mBackStackId = -1;
    }
    else
    {
      localObject = getParentFragmentManager().beginTransaction();
      ((FragmentTransaction)localObject).remove(this);
      if (paramBoolean1) {
        ((FragmentTransaction)localObject).commitAllowingStateLoss();
      } else {
        ((FragmentTransaction)localObject).commit();
      }
    }
  }
  
  private void prepareDialog(Bundle paramBundle)
  {
    if (!this.mShowsDialog) {
      return;
    }
    if (!this.mDialogCreated) {
      try
      {
        this.mCreatingDialog = true;
        paramBundle = onCreateDialog(paramBundle);
        this.mDialog = paramBundle;
        if (this.mShowsDialog)
        {
          setupDialog(paramBundle, this.mStyle);
          paramBundle = getContext();
          if ((paramBundle instanceof Activity)) {
            this.mDialog.setOwnerActivity((Activity)paramBundle);
          }
          this.mDialog.setCancelable(this.mCancelable);
          this.mDialog.setOnCancelListener(this.mOnCancelListener);
          this.mDialog.setOnDismissListener(this.mOnDismissListener);
          this.mDialogCreated = true;
        }
        else
        {
          this.mDialog = null;
        }
      }
      finally
      {
        this.mCreatingDialog = false;
      }
    }
  }
  
  FragmentContainer createFragmentContainer()
  {
    new FragmentContainer()
    {
      public View onFindViewById(int paramAnonymousInt)
      {
        if (this.val$fragmentContainer.onHasView()) {
          return this.val$fragmentContainer.onFindViewById(paramAnonymousInt);
        }
        return DialogFragment.this.onFindViewById(paramAnonymousInt);
      }
      
      public boolean onHasView()
      {
        boolean bool;
        if ((!this.val$fragmentContainer.onHasView()) && (!DialogFragment.this.onHasView())) {
          bool = false;
        } else {
          bool = true;
        }
        return bool;
      }
    };
  }
  
  public void dismiss()
  {
    dismissInternal(false, false);
  }
  
  public void dismissAllowingStateLoss()
  {
    dismissInternal(true, false);
  }
  
  public Dialog getDialog()
  {
    return this.mDialog;
  }
  
  public boolean getShowsDialog()
  {
    return this.mShowsDialog;
  }
  
  public int getTheme()
  {
    return this.mTheme;
  }
  
  public boolean isCancelable()
  {
    return this.mCancelable;
  }
  
  public void onAttach(Context paramContext)
  {
    super.onAttach(paramContext);
    getViewLifecycleOwnerLiveData().observeForever(this.mObserver);
    if (!this.mShownByMe) {
      this.mDismissed = false;
    }
  }
  
  public void onCancel(DialogInterface paramDialogInterface) {}
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mHandler = new Handler();
    boolean bool;
    if (this.mContainerId == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mShowsDialog = bool;
    if (paramBundle != null)
    {
      this.mStyle = paramBundle.getInt("android:style", 0);
      this.mTheme = paramBundle.getInt("android:theme", 0);
      this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
      this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
      this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "onCreateDialog called for DialogFragment " + this);
    }
    return new Dialog(requireContext(), getTheme());
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    Dialog localDialog = this.mDialog;
    if (localDialog != null)
    {
      this.mViewDestroyed = true;
      localDialog.setOnDismissListener(null);
      this.mDialog.dismiss();
      if (!this.mDismissed) {
        onDismiss(this.mDialog);
      }
      this.mDialog = null;
      this.mDialogCreated = false;
    }
  }
  
  public void onDetach()
  {
    super.onDetach();
    if ((!this.mShownByMe) && (!this.mDismissed)) {
      this.mDismissed = true;
    }
    getViewLifecycleOwnerLiveData().removeObserver(this.mObserver);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface)
  {
    if (!this.mViewDestroyed)
    {
      if (FragmentManager.isLoggingEnabled(3)) {
        Log.d("FragmentManager", "onDismiss called for DialogFragment " + this);
      }
      dismissInternal(true, true);
    }
  }
  
  View onFindViewById(int paramInt)
  {
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      return localDialog.findViewById(paramInt);
    }
    return null;
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle)
  {
    LayoutInflater localLayoutInflater = super.onGetLayoutInflater(paramBundle);
    if ((this.mShowsDialog) && (!this.mCreatingDialog))
    {
      prepareDialog(paramBundle);
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.d("FragmentManager", "get layout inflater for DialogFragment " + this + " from dialog context");
      }
      Dialog localDialog = this.mDialog;
      paramBundle = localLayoutInflater;
      if (localDialog != null) {
        paramBundle = localLayoutInflater.cloneInContext(localDialog.getContext());
      }
      return paramBundle;
    }
    if (FragmentManager.isLoggingEnabled(2))
    {
      paramBundle = "getting layout inflater for DialogFragment " + this;
      if (!this.mShowsDialog) {
        Log.d("FragmentManager", "mShowsDialog = false: " + paramBundle);
      } else {
        Log.d("FragmentManager", "mCreatingDialog = true: " + paramBundle);
      }
    }
    return localLayoutInflater;
  }
  
  boolean onHasView()
  {
    return this.mDialogCreated;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      localObject = ((Dialog)localObject).onSaveInstanceState();
      ((Bundle)localObject).putBoolean("android:dialogShowing", false);
      paramBundle.putBundle("android:savedDialogState", (Bundle)localObject);
    }
    int i = this.mStyle;
    if (i != 0) {
      paramBundle.putInt("android:style", i);
    }
    i = this.mTheme;
    if (i != 0) {
      paramBundle.putInt("android:theme", i);
    }
    boolean bool = this.mCancelable;
    if (!bool) {
      paramBundle.putBoolean("android:cancelable", bool);
    }
    bool = this.mShowsDialog;
    if (!bool) {
      paramBundle.putBoolean("android:showsDialog", bool);
    }
    i = this.mBackStackId;
    if (i != -1) {
      paramBundle.putInt("android:backStackId", i);
    }
  }
  
  public void onStart()
  {
    super.onStart();
    Object localObject = this.mDialog;
    if (localObject != null)
    {
      this.mViewDestroyed = false;
      ((Dialog)localObject).show();
      localObject = this.mDialog.getWindow().getDecorView();
      ViewTreeLifecycleOwner.set((View)localObject, this);
      ViewTreeViewModelStoreOwner.set((View)localObject, this);
      ViewTreeSavedStateRegistryOwner.set((View)localObject, this);
    }
  }
  
  public void onStop()
  {
    super.onStop();
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.hide();
    }
  }
  
  public void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    if ((this.mDialog != null) && (paramBundle != null))
    {
      paramBundle = paramBundle.getBundle("android:savedDialogState");
      if (paramBundle != null) {
        this.mDialog.onRestoreInstanceState(paramBundle);
      }
    }
  }
  
  void performCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    super.performCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    if ((this.mView == null) && (this.mDialog != null) && (paramBundle != null))
    {
      paramLayoutInflater = paramBundle.getBundle("android:savedDialogState");
      if (paramLayoutInflater != null) {
        this.mDialog.onRestoreInstanceState(paramLayoutInflater);
      }
    }
  }
  
  public final Dialog requireDialog()
  {
    Dialog localDialog = getDialog();
    if (localDialog != null) {
      return localDialog;
    }
    throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    this.mCancelable = paramBoolean;
    Dialog localDialog = this.mDialog;
    if (localDialog != null) {
      localDialog.setCancelable(paramBoolean);
    }
  }
  
  public void setShowsDialog(boolean paramBoolean)
  {
    this.mShowsDialog = paramBoolean;
  }
  
  public void setStyle(int paramInt1, int paramInt2)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.d("FragmentManager", "Setting style and theme for DialogFragment " + this + " to " + paramInt1 + ", " + paramInt2);
    }
    this.mStyle = paramInt1;
    if ((paramInt1 == 2) || (paramInt1 == 3)) {
      this.mTheme = 16973913;
    }
    if (paramInt2 != 0) {
      this.mTheme = paramInt2;
    }
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 3: 
      Window localWindow = paramDialog.getWindow();
      if (localWindow != null) {
        localWindow.addFlags(24);
      }
      break;
    }
    paramDialog.requestWindowFeature(1);
  }
  
  public int show(FragmentTransaction paramFragmentTransaction, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentTransaction.add(this, paramString);
    this.mViewDestroyed = false;
    int i = paramFragmentTransaction.commit();
    this.mBackStackId = i;
    return i;
  }
  
  public void show(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentManager = paramFragmentManager.beginTransaction();
    paramFragmentManager.add(this, paramString);
    paramFragmentManager.commit();
  }
  
  public void showNow(FragmentManager paramFragmentManager, String paramString)
  {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentManager = paramFragmentManager.beginTransaction();
    paramFragmentManager.add(this, paramString);
    paramFragmentManager.commitNow();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/DialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */