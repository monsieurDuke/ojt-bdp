package androidx.core.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.Animator.AnimatorPauseListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000(\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\n\032¤\001\020\000\032\0020\001*\0020\0022#\b\006\020\003\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\t\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\n\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\013\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\032Z\020\f\032\0020\r*\0020\0022#\b\006\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\017\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\020\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\022\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\023\032\0020\r*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\024\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\025\032\0020\r*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\026\032\0020\001*\0020\0022#\b\004\020\021\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\002\007\n\005\b20\001¨\006\027"}, d2={"addListener", "Landroid/animation/Animator$AnimatorListener;", "Landroid/animation/Animator;", "onEnd", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "animator", "", "onStart", "onCancel", "onRepeat", "addPauseListener", "Landroid/animation/Animator$AnimatorPauseListener;", "onResume", "onPause", "doOnCancel", "action", "doOnEnd", "doOnPause", "doOnRepeat", "doOnResume", "doOnStart", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class AnimatorKt
{
  public static final Animator.AnimatorListener addListener(Animator paramAnimator, final Function1<? super Animator, Unit> paramFunction11, final Function1<? super Animator, Unit> paramFunction12, final Function1<? super Animator, Unit> paramFunction13, Function1<? super Animator, Unit> paramFunction14)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction11, "onEnd");
    Intrinsics.checkNotNullParameter(paramFunction12, "onStart");
    Intrinsics.checkNotNullParameter(paramFunction13, "onCancel");
    Intrinsics.checkNotNullParameter(paramFunction14, "onRepeat");
    paramFunction11 = new Animator.AnimatorListener()
    {
      final Function1<Animator, Unit> $onRepeat;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        paramFunction13.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        paramFunction11.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onRepeat.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        paramFunction12.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addListener((Animator.AnimatorListener)paramFunction11);
    return (Animator.AnimatorListener)paramFunction11;
  }
  
  public static final Animator.AnimatorPauseListener addPauseListener(Animator paramAnimator, final Function1<? super Animator, Unit> paramFunction11, Function1<? super Animator, Unit> paramFunction12)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction11, "onResume");
    Intrinsics.checkNotNullParameter(paramFunction12, "onPause");
    paramFunction11 = new Animator.AnimatorPauseListener()
    {
      final Function1<Animator, Unit> $onPause;
      
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onPause.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        paramFunction11.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addPauseListener((Animator.AnimatorPauseListener)paramFunction11);
    return (Animator.AnimatorPauseListener)paramFunction11;
  }
  
  public static final Animator.AnimatorListener doOnCancel(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorListener()
    {
      final Function1 $onCancel;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onCancel.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener((Animator.AnimatorListener)paramFunction1);
    return (Animator.AnimatorListener)paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnEnd(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorListener()
    {
      final Function1 $onEnd;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onEnd.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener((Animator.AnimatorListener)paramFunction1);
    return (Animator.AnimatorListener)paramFunction1;
  }
  
  public static final Animator.AnimatorPauseListener doOnPause(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorPauseListener()
    {
      final Function1 $onPause;
      
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onPause.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addPauseListener((Animator.AnimatorPauseListener)paramFunction1);
    return (Animator.AnimatorPauseListener)paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnRepeat(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorListener()
    {
      final Function1 $onRepeat;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onRepeat.invoke(paramAnonymousAnimator);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
    };
    paramAnimator.addListener((Animator.AnimatorListener)paramFunction1);
    return (Animator.AnimatorListener)paramFunction1;
  }
  
  public static final Animator.AnimatorPauseListener doOnResume(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorPauseListener()
    {
      final Function1 $onResume;
      
      public void onAnimationPause(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationResume(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onResume.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addPauseListener((Animator.AnimatorPauseListener)paramFunction1);
    return (Animator.AnimatorPauseListener)paramFunction1;
  }
  
  public static final Animator.AnimatorListener doOnStart(Animator paramAnimator, Function1<? super Animator, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramAnimator, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Animator.AnimatorListener()
    {
      final Function1 $onStart;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousAnimator, "animator");
        this.$onStart.invoke(paramAnonymousAnimator);
      }
    };
    paramAnimator.addListener((Animator.AnimatorListener)paramFunction1);
    return (Animator.AnimatorListener)paramFunction1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/animation/AnimatorKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */