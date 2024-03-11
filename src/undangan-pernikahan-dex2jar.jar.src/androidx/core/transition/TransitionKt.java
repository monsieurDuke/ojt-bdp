package androidx.core.transition;

import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000 \n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\013\032É\001\020\000\032\0020\001*\0020\0022#\b\006\020\003\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\t\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\n\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\013\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\0042#\b\006\020\f\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\r\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\017\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\020\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\021\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\0325\020\022\032\0020\001*\0020\0022#\b\004\020\016\032\035\022\023\022\0210\002¢\006\f\b\005\022\b\b\006\022\004\b\b(\007\022\004\022\0020\b0\004H\bø\001\000\002\007\n\005\b20\001¨\006\023"}, d2={"addListener", "Landroid/transition/Transition$TransitionListener;", "Landroid/transition/Transition;", "onEnd", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "transition", "", "onStart", "onCancel", "onResume", "onPause", "doOnCancel", "action", "doOnEnd", "doOnPause", "doOnResume", "doOnStart", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class TransitionKt
{
  public static final Transition.TransitionListener addListener(Transition paramTransition, Function1<? super Transition, Unit> paramFunction11, final Function1<? super Transition, Unit> paramFunction12, final Function1<? super Transition, Unit> paramFunction13, final Function1<? super Transition, Unit> paramFunction14, final Function1<? super Transition, Unit> paramFunction15)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction11, "onEnd");
    Intrinsics.checkNotNullParameter(paramFunction12, "onStart");
    Intrinsics.checkNotNullParameter(paramFunction13, "onCancel");
    Intrinsics.checkNotNullParameter(paramFunction14, "onResume");
    Intrinsics.checkNotNullParameter(paramFunction15, "onPause");
    paramFunction11 = new Transition.TransitionListener()
    {
      final Function1<Transition, Unit> $onEnd;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        paramFunction13.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onEnd.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        paramFunction15.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        paramFunction14.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        paramFunction12.invoke(paramAnonymousTransition);
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction11);
    return (Transition.TransitionListener)paramFunction11;
  }
  
  public static final Transition.TransitionListener doOnCancel(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Transition.TransitionListener()
    {
      final Function1 $onCancel;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onCancel.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction1);
    return (Transition.TransitionListener)paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnEnd(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Transition.TransitionListener()
    {
      final Function1 $onEnd;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onEnd.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction1);
    return (Transition.TransitionListener)paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnPause(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Transition.TransitionListener()
    {
      final Function1 $onPause;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onPause.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction1);
    return (Transition.TransitionListener)paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnResume(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Transition.TransitionListener()
    {
      final Function1 $onResume;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onResume.invoke(paramAnonymousTransition);
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction1);
    return (Transition.TransitionListener)paramFunction1;
  }
  
  public static final Transition.TransitionListener doOnStart(Transition paramTransition, Function1<? super Transition, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTransition, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramFunction1 = new Transition.TransitionListener()
    {
      final Function1 $onStart;
      
      public void onTransitionCancel(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionEnd(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionPause(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionResume(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
      }
      
      public void onTransitionStart(Transition paramAnonymousTransition)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousTransition, "transition");
        this.$onStart.invoke(paramAnonymousTransition);
      }
    };
    paramTransition.addListener((Transition.TransitionListener)paramFunction1);
    return (Transition.TransitionListener)paramFunction1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/transition/TransitionKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */