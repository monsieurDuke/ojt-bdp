package androidx.core.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.WindowInsetsController.OnControllableInsetsChangedListener;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.collection.SimpleArrayMap;

public final class WindowInsetsControllerCompat
{
  public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;
  public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
  public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
  private final Impl mImpl;
  
  public WindowInsetsControllerCompat(Window paramWindow, View paramView)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      this.mImpl = new Impl30(paramWindow, this);
    } else if (Build.VERSION.SDK_INT >= 26) {
      this.mImpl = new Impl26(paramWindow, paramView);
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new Impl23(paramWindow, paramView);
    } else if (Build.VERSION.SDK_INT >= 20) {
      this.mImpl = new Impl20(paramWindow, paramView);
    } else {
      this.mImpl = new Impl();
    }
  }
  
  @Deprecated
  private WindowInsetsControllerCompat(WindowInsetsController paramWindowInsetsController)
  {
    this.mImpl = new Impl30(paramWindowInsetsController, this);
  }
  
  @Deprecated
  public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(WindowInsetsController paramWindowInsetsController)
  {
    return new WindowInsetsControllerCompat(paramWindowInsetsController);
  }
  
  public void addOnControllableInsetsChangedListener(OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener)
  {
    this.mImpl.addOnControllableInsetsChangedListener(paramOnControllableInsetsChangedListener);
  }
  
  public void controlWindowInsetsAnimation(int paramInt, long paramLong, Interpolator paramInterpolator, CancellationSignal paramCancellationSignal, WindowInsetsAnimationControlListenerCompat paramWindowInsetsAnimationControlListenerCompat)
  {
    this.mImpl.controlWindowInsetsAnimation(paramInt, paramLong, paramInterpolator, paramCancellationSignal, paramWindowInsetsAnimationControlListenerCompat);
  }
  
  public int getSystemBarsBehavior()
  {
    return this.mImpl.getSystemBarsBehavior();
  }
  
  public void hide(int paramInt)
  {
    this.mImpl.hide(paramInt);
  }
  
  public boolean isAppearanceLightNavigationBars()
  {
    return this.mImpl.isAppearanceLightNavigationBars();
  }
  
  public boolean isAppearanceLightStatusBars()
  {
    return this.mImpl.isAppearanceLightStatusBars();
  }
  
  public void removeOnControllableInsetsChangedListener(OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener)
  {
    this.mImpl.removeOnControllableInsetsChangedListener(paramOnControllableInsetsChangedListener);
  }
  
  public void setAppearanceLightNavigationBars(boolean paramBoolean)
  {
    this.mImpl.setAppearanceLightNavigationBars(paramBoolean);
  }
  
  public void setAppearanceLightStatusBars(boolean paramBoolean)
  {
    this.mImpl.setAppearanceLightStatusBars(paramBoolean);
  }
  
  public void setSystemBarsBehavior(int paramInt)
  {
    this.mImpl.setSystemBarsBehavior(paramInt);
  }
  
  public void show(int paramInt)
  {
    this.mImpl.show(paramInt);
  }
  
  private static class Impl
  {
    void addOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener) {}
    
    void controlWindowInsetsAnimation(int paramInt, long paramLong, Interpolator paramInterpolator, CancellationSignal paramCancellationSignal, WindowInsetsAnimationControlListenerCompat paramWindowInsetsAnimationControlListenerCompat) {}
    
    int getSystemBarsBehavior()
    {
      return 0;
    }
    
    void hide(int paramInt) {}
    
    public boolean isAppearanceLightNavigationBars()
    {
      return false;
    }
    
    public boolean isAppearanceLightStatusBars()
    {
      return false;
    }
    
    void removeOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener) {}
    
    public void setAppearanceLightNavigationBars(boolean paramBoolean) {}
    
    public void setAppearanceLightStatusBars(boolean paramBoolean) {}
    
    void setSystemBarsBehavior(int paramInt) {}
    
    void show(int paramInt) {}
  }
  
  private static class Impl20
    extends WindowInsetsControllerCompat.Impl
  {
    private final View mView;
    protected final Window mWindow;
    
    Impl20(Window paramWindow, View paramView)
    {
      this.mWindow = paramWindow;
      this.mView = paramView;
    }
    
    private void hideForType(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 8: 
        ((InputMethodManager)this.mWindow.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mWindow.getDecorView().getWindowToken(), 0);
        break;
      case 2: 
        setSystemUiFlag(2);
        return;
      case 1: 
        setSystemUiFlag(4);
        return;
      }
    }
    
    private void showForType(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 8: 
        View localView1 = this.mView;
        if ((!localView1.isInEditMode()) && (!localView1.onCheckIsTextEditor())) {
          localView1 = this.mWindow.getCurrentFocus();
        } else {
          localView1.requestFocus();
        }
        View localView2 = localView1;
        if (localView1 == null) {
          localView2 = this.mWindow.findViewById(16908290);
        }
        if ((localView2 != null) && (localView2.hasWindowFocus())) {
          localView2.post(new WindowInsetsControllerCompat.Impl20..ExternalSyntheticLambda0(localView2));
        }
        break;
      case 2: 
        unsetSystemUiFlag(2);
        return;
      case 1: 
        unsetSystemUiFlag(4);
        unsetWindowFlag(1024);
        return;
      }
    }
    
    void addOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener) {}
    
    void controlWindowInsetsAnimation(int paramInt, long paramLong, Interpolator paramInterpolator, CancellationSignal paramCancellationSignal, WindowInsetsAnimationControlListenerCompat paramWindowInsetsAnimationControlListenerCompat) {}
    
    int getSystemBarsBehavior()
    {
      return 0;
    }
    
    void hide(int paramInt)
    {
      int i = 1;
      while (i <= 256)
      {
        if ((paramInt & i) != 0) {
          hideForType(i);
        }
        i <<= 1;
      }
    }
    
    void removeOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener) {}
    
    void setSystemBarsBehavior(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 2: 
        unsetSystemUiFlag(2048);
        setSystemUiFlag(4096);
        break;
      case 1: 
        unsetSystemUiFlag(4096);
        setSystemUiFlag(2048);
        break;
      case 0: 
        unsetSystemUiFlag(6144);
      }
    }
    
    protected void setSystemUiFlag(int paramInt)
    {
      View localView = this.mWindow.getDecorView();
      localView.setSystemUiVisibility(localView.getSystemUiVisibility() | paramInt);
    }
    
    protected void setWindowFlag(int paramInt)
    {
      this.mWindow.addFlags(paramInt);
    }
    
    void show(int paramInt)
    {
      int i = 1;
      while (i <= 256)
      {
        if ((paramInt & i) != 0) {
          showForType(i);
        }
        i <<= 1;
      }
    }
    
    protected void unsetSystemUiFlag(int paramInt)
    {
      View localView = this.mWindow.getDecorView();
      localView.setSystemUiVisibility(localView.getSystemUiVisibility() & (paramInt ^ 0xFFFFFFFF));
    }
    
    protected void unsetWindowFlag(int paramInt)
    {
      this.mWindow.clearFlags(paramInt);
    }
  }
  
  private static class Impl23
    extends WindowInsetsControllerCompat.Impl20
  {
    Impl23(Window paramWindow, View paramView)
    {
      super(paramView);
    }
    
    public boolean isAppearanceLightStatusBars()
    {
      boolean bool;
      if ((this.mWindow.getDecorView().getSystemUiVisibility() & 0x2000) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setAppearanceLightStatusBars(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        unsetWindowFlag(67108864);
        setWindowFlag(Integer.MIN_VALUE);
        setSystemUiFlag(8192);
      }
      else
      {
        unsetSystemUiFlag(8192);
      }
    }
  }
  
  private static class Impl26
    extends WindowInsetsControllerCompat.Impl23
  {
    Impl26(Window paramWindow, View paramView)
    {
      super(paramView);
    }
    
    public boolean isAppearanceLightNavigationBars()
    {
      boolean bool;
      if ((this.mWindow.getDecorView().getSystemUiVisibility() & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setAppearanceLightNavigationBars(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        unsetWindowFlag(134217728);
        setWindowFlag(Integer.MIN_VALUE);
        setSystemUiFlag(16);
      }
      else
      {
        unsetSystemUiFlag(16);
      }
    }
  }
  
  private static class Impl30
    extends WindowInsetsControllerCompat.Impl
  {
    final WindowInsetsControllerCompat mCompatController;
    final WindowInsetsController mInsetsController;
    private final SimpleArrayMap<WindowInsetsControllerCompat.OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> mListeners = new SimpleArrayMap();
    protected Window mWindow;
    
    Impl30(Window paramWindow, WindowInsetsControllerCompat paramWindowInsetsControllerCompat)
    {
      this(paramWindow.getInsetsController(), paramWindowInsetsControllerCompat);
      this.mWindow = paramWindow;
    }
    
    Impl30(WindowInsetsController paramWindowInsetsController, WindowInsetsControllerCompat paramWindowInsetsControllerCompat)
    {
      this.mInsetsController = paramWindowInsetsController;
      this.mCompatController = paramWindowInsetsControllerCompat;
    }
    
    void addOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener)
    {
      if (this.mListeners.containsKey(paramOnControllableInsetsChangedListener)) {
        return;
      }
      WindowInsetsControllerCompat.Impl30..ExternalSyntheticLambda0 localExternalSyntheticLambda0 = new WindowInsetsControllerCompat.Impl30..ExternalSyntheticLambda0(this, paramOnControllableInsetsChangedListener);
      this.mListeners.put(paramOnControllableInsetsChangedListener, localExternalSyntheticLambda0);
      this.mInsetsController.addOnControllableInsetsChangedListener(localExternalSyntheticLambda0);
    }
    
    void controlWindowInsetsAnimation(int paramInt, long paramLong, Interpolator paramInterpolator, CancellationSignal paramCancellationSignal, final WindowInsetsAnimationControlListenerCompat paramWindowInsetsAnimationControlListenerCompat)
    {
      paramWindowInsetsAnimationControlListenerCompat = new WindowInsetsAnimationControlListener()
      {
        private WindowInsetsAnimationControllerCompat mCompatAnimController = null;
        
        public void onCancelled(WindowInsetsAnimationController paramAnonymousWindowInsetsAnimationController)
        {
          WindowInsetsAnimationControlListenerCompat localWindowInsetsAnimationControlListenerCompat = paramWindowInsetsAnimationControlListenerCompat;
          if (paramAnonymousWindowInsetsAnimationController == null) {
            paramAnonymousWindowInsetsAnimationController = null;
          } else {
            paramAnonymousWindowInsetsAnimationController = this.mCompatAnimController;
          }
          localWindowInsetsAnimationControlListenerCompat.onCancelled(paramAnonymousWindowInsetsAnimationController);
        }
        
        public void onFinished(WindowInsetsAnimationController paramAnonymousWindowInsetsAnimationController)
        {
          paramWindowInsetsAnimationControlListenerCompat.onFinished(this.mCompatAnimController);
        }
        
        public void onReady(WindowInsetsAnimationController paramAnonymousWindowInsetsAnimationController, int paramAnonymousInt)
        {
          paramAnonymousWindowInsetsAnimationController = new WindowInsetsAnimationControllerCompat(paramAnonymousWindowInsetsAnimationController);
          this.mCompatAnimController = paramAnonymousWindowInsetsAnimationController;
          paramWindowInsetsAnimationControlListenerCompat.onReady(paramAnonymousWindowInsetsAnimationController, paramAnonymousInt);
        }
      };
      this.mInsetsController.controlWindowInsetsAnimation(paramInt, paramLong, paramInterpolator, paramCancellationSignal, paramWindowInsetsAnimationControlListenerCompat);
    }
    
    int getSystemBarsBehavior()
    {
      return this.mInsetsController.getSystemBarsBehavior();
    }
    
    void hide(int paramInt)
    {
      this.mInsetsController.hide(paramInt);
    }
    
    public boolean isAppearanceLightNavigationBars()
    {
      boolean bool;
      if ((this.mInsetsController.getSystemBarsAppearance() & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isAppearanceLightStatusBars()
    {
      boolean bool;
      if ((this.mInsetsController.getSystemBarsAppearance() & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void removeOnControllableInsetsChangedListener(WindowInsetsControllerCompat.OnControllableInsetsChangedListener paramOnControllableInsetsChangedListener)
    {
      paramOnControllableInsetsChangedListener = (WindowInsetsController.OnControllableInsetsChangedListener)this.mListeners.remove(paramOnControllableInsetsChangedListener);
      if (paramOnControllableInsetsChangedListener != null) {
        this.mInsetsController.removeOnControllableInsetsChangedListener(paramOnControllableInsetsChangedListener);
      }
    }
    
    public void setAppearanceLightNavigationBars(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        if (this.mWindow != null) {
          setSystemUiFlag(16);
        }
        this.mInsetsController.setSystemBarsAppearance(16, 16);
      }
      else
      {
        if (this.mWindow != null) {
          unsetSystemUiFlag(16);
        }
        this.mInsetsController.setSystemBarsAppearance(0, 16);
      }
    }
    
    public void setAppearanceLightStatusBars(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        if (this.mWindow != null) {
          setSystemUiFlag(8192);
        }
        this.mInsetsController.setSystemBarsAppearance(8, 8);
      }
      else
      {
        if (this.mWindow != null) {
          unsetSystemUiFlag(8192);
        }
        this.mInsetsController.setSystemBarsAppearance(0, 8);
      }
    }
    
    void setSystemBarsBehavior(int paramInt)
    {
      this.mInsetsController.setSystemBarsBehavior(paramInt);
    }
    
    protected void setSystemUiFlag(int paramInt)
    {
      View localView = this.mWindow.getDecorView();
      localView.setSystemUiVisibility(localView.getSystemUiVisibility() | paramInt);
    }
    
    void show(int paramInt)
    {
      if ((this.mWindow != null) && ((paramInt & 0x8) != 0) && (Build.VERSION.SDK_INT < 32)) {
        ((InputMethodManager)this.mWindow.getContext().getSystemService("input_method")).isActive();
      }
      this.mInsetsController.show(paramInt);
    }
    
    protected void unsetSystemUiFlag(int paramInt)
    {
      View localView = this.mWindow.getDecorView();
      localView.setSystemUiVisibility(localView.getSystemUiVisibility() & (paramInt ^ 0xFFFFFFFF));
    }
  }
  
  public static abstract interface OnControllableInsetsChangedListener
  {
    public abstract void onControllableInsetsChanged(WindowInsetsControllerCompat paramWindowInsetsControllerCompat, int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/WindowInsetsControllerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */