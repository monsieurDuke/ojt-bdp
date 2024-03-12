package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.core.os.CancellationSignal;
import androidx.core.os.CancellationSignal.OnCancelListener;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.R.animator;
import androidx.fragment.R.id;

class FragmentAnim
{
  static void animateRemoveFragment(final Fragment paramFragment, AnimationOrAnimator paramAnimationOrAnimator, final FragmentTransition.Callback paramCallback)
  {
    final View localView = paramFragment.mView;
    ViewGroup localViewGroup = paramFragment.mContainer;
    localViewGroup.startViewTransition(localView);
    final CancellationSignal localCancellationSignal = new CancellationSignal();
    localCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener()
    {
      public void onCancel()
      {
        if (FragmentAnim.this.getAnimatingAway() != null)
        {
          View localView = FragmentAnim.this.getAnimatingAway();
          FragmentAnim.this.setAnimatingAway(null);
          localView.clearAnimation();
        }
        FragmentAnim.this.setAnimator(null);
      }
    });
    paramCallback.onStart(paramFragment, localCancellationSignal);
    if (paramAnimationOrAnimator.animation != null)
    {
      paramAnimationOrAnimator = new EndViewTransitionAnimation(paramAnimationOrAnimator.animation, localViewGroup, localView);
      paramFragment.setAnimatingAway(paramFragment.mView);
      paramAnimationOrAnimator.setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          FragmentAnim.this.post(new Runnable()
          {
            public void run()
            {
              if (FragmentAnim.2.this.val$fragment.getAnimatingAway() != null)
              {
                FragmentAnim.2.this.val$fragment.setAnimatingAway(null);
                FragmentAnim.2.this.val$callback.onComplete(FragmentAnim.2.this.val$fragment, FragmentAnim.2.this.val$signal);
              }
            }
          });
        }
        
        public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      paramFragment.mView.startAnimation(paramAnimationOrAnimator);
    }
    else
    {
      Animator localAnimator = paramAnimationOrAnimator.animator;
      paramFragment.setAnimator(paramAnimationOrAnimator.animator);
      localAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          FragmentAnim.this.endViewTransition(localView);
          paramAnonymousAnimator = paramFragment.getAnimator();
          paramFragment.setAnimator(null);
          if ((paramAnonymousAnimator != null) && (FragmentAnim.this.indexOfChild(localView) < 0)) {
            paramCallback.onComplete(paramFragment, localCancellationSignal);
          }
        }
      });
      localAnimator.setTarget(paramFragment.mView);
      localAnimator.start();
    }
  }
  
  private static int getNextAnim(Fragment paramFragment, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
    {
      if (paramBoolean1) {
        return paramFragment.getPopEnterAnim();
      }
      return paramFragment.getPopExitAnim();
    }
    if (paramBoolean1) {
      return paramFragment.getEnterAnim();
    }
    return paramFragment.getExitAnim();
  }
  
  static AnimationOrAnimator loadAnimation(Context paramContext, Fragment paramFragment, boolean paramBoolean1, boolean paramBoolean2)
  {
    int k = paramFragment.getNextTransition();
    int j = getNextAnim(paramFragment, paramBoolean1, paramBoolean2);
    paramFragment.setAnimations(0, 0, 0, 0);
    if ((paramFragment.mContainer != null) && (paramFragment.mContainer.getTag(R.id.visible_removing_fragment_view_tag) != null)) {
      paramFragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, null);
    }
    if ((paramFragment.mContainer != null) && (paramFragment.mContainer.getLayoutTransition() != null)) {
      return null;
    }
    Animation localAnimation = paramFragment.onCreateAnimation(k, paramBoolean1, j);
    if (localAnimation != null) {
      return new AnimationOrAnimator(localAnimation);
    }
    paramFragment = paramFragment.onCreateAnimator(k, paramBoolean1, j);
    if (paramFragment != null) {
      return new AnimationOrAnimator(paramFragment);
    }
    int i = j;
    if (j == 0)
    {
      i = j;
      if (k != 0) {
        i = transitToAnimResourceId(k, paramBoolean1);
      }
    }
    if (i != 0)
    {
      paramBoolean1 = "anim".equals(paramContext.getResources().getResourceTypeName(i));
      k = 0;
      j = k;
      if (paramBoolean1) {
        try
        {
          paramFragment = AnimationUtils.loadAnimation(paramContext, i);
          if (paramFragment != null)
          {
            paramFragment = new AnimationOrAnimator(paramFragment);
            return paramFragment;
          }
          j = 1;
        }
        catch (RuntimeException paramFragment)
        {
          j = k;
        }
        catch (Resources.NotFoundException paramContext)
        {
          throw paramContext;
        }
      }
      if (j == 0) {
        try
        {
          paramFragment = AnimatorInflater.loadAnimator(paramContext, i);
          if (paramFragment != null)
          {
            paramFragment = new AnimationOrAnimator(paramFragment);
            return paramFragment;
          }
        }
        catch (RuntimeException paramFragment)
        {
          if (!paramBoolean1)
          {
            paramContext = AnimationUtils.loadAnimation(paramContext, i);
            if (paramContext != null) {
              return new AnimationOrAnimator(paramContext);
            }
          }
          else
          {
            throw paramFragment;
          }
        }
      }
    }
    return null;
  }
  
  private static int transitToAnimResourceId(int paramInt, boolean paramBoolean)
  {
    int i = -1;
    switch (paramInt)
    {
    default: 
      paramInt = i;
      break;
    case 8194: 
      if (paramBoolean) {
        paramInt = R.animator.fragment_close_enter;
      } else {
        paramInt = R.animator.fragment_close_exit;
      }
      break;
    case 4099: 
      if (paramBoolean) {
        paramInt = R.animator.fragment_fade_enter;
      } else {
        paramInt = R.animator.fragment_fade_exit;
      }
      break;
    case 4097: 
      if (paramBoolean) {
        paramInt = R.animator.fragment_open_enter;
      } else {
        paramInt = R.animator.fragment_open_exit;
      }
      break;
    }
    return paramInt;
  }
  
  static class AnimationOrAnimator
  {
    public final Animation animation;
    public final Animator animator;
    
    AnimationOrAnimator(Animator paramAnimator)
    {
      this.animation = null;
      this.animator = paramAnimator;
      if (paramAnimator != null) {
        return;
      }
      throw new IllegalStateException("Animator cannot be null");
    }
    
    AnimationOrAnimator(Animation paramAnimation)
    {
      this.animation = paramAnimation;
      this.animator = null;
      if (paramAnimation != null) {
        return;
      }
      throw new IllegalStateException("Animation cannot be null");
    }
  }
  
  static class EndViewTransitionAnimation
    extends AnimationSet
    implements Runnable
  {
    private boolean mAnimating = true;
    private final View mChild;
    private boolean mEnded;
    private final ViewGroup mParent;
    private boolean mTransitionEnded;
    
    EndViewTransitionAnimation(Animation paramAnimation, ViewGroup paramViewGroup, View paramView)
    {
      super();
      this.mParent = paramViewGroup;
      this.mChild = paramView;
      addAnimation(paramAnimation);
      paramViewGroup.post(this);
    }
    
    public boolean getTransformation(long paramLong, Transformation paramTransformation)
    {
      this.mAnimating = true;
      if (this.mEnded) {
        return true ^ this.mTransitionEnded;
      }
      if (!super.getTransformation(paramLong, paramTransformation))
      {
        this.mEnded = true;
        OneShotPreDrawListener.add(this.mParent, this);
      }
      return true;
    }
    
    public boolean getTransformation(long paramLong, Transformation paramTransformation, float paramFloat)
    {
      this.mAnimating = true;
      if (this.mEnded) {
        return true ^ this.mTransitionEnded;
      }
      if (!super.getTransformation(paramLong, paramTransformation, paramFloat))
      {
        this.mEnded = true;
        OneShotPreDrawListener.add(this.mParent, this);
      }
      return true;
    }
    
    public void run()
    {
      if ((!this.mEnded) && (this.mAnimating))
      {
        this.mAnimating = false;
        this.mParent.post(this);
      }
      else
      {
        this.mParent.endViewTransition(this.mChild);
        this.mTransitionEnded = true;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentAnim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */