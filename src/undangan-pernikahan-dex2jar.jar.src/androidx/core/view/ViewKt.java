package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000j\n\000\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\t\n\002\020\b\n\002\b\r\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\n\0325\020 \032\0020!*\0020\0022#\b\004\020\"\032\035\022\023\022\0210\002¢\006\f\b$\022\b\b%\022\004\b\b(&\022\004\022\0020!0#H\bø\001\000\0325\020'\032\0020!*\0020\0022#\b\004\020\"\032\035\022\023\022\0210\002¢\006\f\b$\022\b\b%\022\004\b\b(&\022\004\022\0020!0#H\bø\001\000\0325\020(\032\0020!*\0020\0022#\b\004\020\"\032\035\022\023\022\0210\002¢\006\f\b$\022\b\b%\022\004\b\b(&\022\004\022\0020!0#H\bø\001\000\0325\020)\032\0020!*\0020\0022#\b\004\020\"\032\035\022\023\022\0210\002¢\006\f\b$\022\b\b%\022\004\b\b(&\022\004\022\0020!0#H\bø\001\000\0325\020*\032\0020+*\0020\0022#\b\004\020\"\032\035\022\023\022\0210\002¢\006\f\b$\022\b\b%\022\004\b\b(&\022\004\022\0020!0#H\bø\001\000\032\024\020,\032\0020-*\0020\0022\b\b\002\020.\032\0020/\032(\0200\032\00201*\0020\0022\006\0202\032\002032\016\b\004\020\"\032\b\022\004\022\0020!04H\bø\001\000\032(\0205\032\00201*\0020\0022\006\0202\032\002032\016\b\004\020\"\032\b\022\004\022\0020!04H\bø\001\000\032\027\0206\032\0020!*\0020\0022\b\b\001\0207\032\0020\023H\b\032:\0208\032\0020!\"\n\b\000\0209\030\001*\0020:*\0020\0022\027\020;\032\023\022\004\022\002H9\022\004\022\0020!0#¢\006\002\b<H\bø\001\000¢\006\002\b=\032)\0208\032\0020!*\0020\0022\027\020;\032\023\022\004\022\0020:\022\004\022\0020!0#¢\006\002\b<H\bø\001\000\0325\020>\032\0020!*\0020\0022\b\b\003\020?\032\0020\0232\b\b\003\020@\032\0020\0232\b\b\003\020A\032\0020\0232\b\b\003\020B\032\0020\023H\b\0325\020C\032\0020!*\0020\0022\b\b\003\020D\032\0020\0232\b\b\003\020@\032\0020\0232\b\b\003\020E\032\0020\0232\b\b\003\020B\032\0020\023H\b\"\033\020\000\032\b\022\004\022\0020\0020\001*\0020\0028F¢\006\006\032\004\b\003\020\004\"\033\020\005\032\b\022\004\022\0020\0060\001*\0020\0028F¢\006\006\032\004\b\007\020\004\"*\020\n\032\0020\t*\0020\0022\006\020\b\032\0020\t8Æ\002@Æ\002X\016¢\006\f\032\004\b\n\020\013\"\004\b\f\020\r\"*\020\016\032\0020\t*\0020\0022\006\020\b\032\0020\t8Æ\002@Æ\002X\016¢\006\f\032\004\b\016\020\013\"\004\b\017\020\r\"*\020\020\032\0020\t*\0020\0022\006\020\b\032\0020\t8Æ\002@Æ\002X\016¢\006\f\032\004\b\020\020\013\"\004\b\021\020\r\"\026\020\022\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\024\020\025\"\026\020\026\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\027\020\025\"\026\020\030\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\031\020\025\"\026\020\032\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\033\020\025\"\026\020\034\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\035\020\025\"\026\020\036\032\0020\023*\0020\0028Æ\002¢\006\006\032\004\b\037\020\025\002\007\n\005\b20\001¨\006F"}, d2={"allViews", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "getAllViews", "(Landroid/view/View;)Lkotlin/sequences/Sequence;", "ancestors", "Landroid/view/ViewParent;", "getAncestors", "value", "", "isGone", "(Landroid/view/View;)Z", "setGone", "(Landroid/view/View;Z)V", "isInvisible", "setInvisible", "isVisible", "setVisible", "marginBottom", "", "getMarginBottom", "(Landroid/view/View;)I", "marginEnd", "getMarginEnd", "marginLeft", "getMarginLeft", "marginRight", "getMarginRight", "marginStart", "getMarginStart", "marginTop", "getMarginTop", "doOnAttach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "view", "doOnDetach", "doOnLayout", "doOnNextLayout", "doOnPreDraw", "Landroidx/core/view/OneShotPreDrawListener;", "drawToBitmap", "Landroid/graphics/Bitmap;", "config", "Landroid/graphics/Bitmap$Config;", "postDelayed", "Ljava/lang/Runnable;", "delayInMillis", "", "Lkotlin/Function0;", "postOnAnimationDelayed", "setPadding", "size", "updateLayoutParams", "T", "Landroid/view/ViewGroup$LayoutParams;", "block", "Lkotlin/ExtensionFunctionType;", "updateLayoutParamsTyped", "updatePadding", "left", "top", "right", "bottom", "updatePaddingRelative", "start", "end", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ViewKt
{
  public static final void doOnAttach(View paramView, final Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    if (ViewCompat.isAttachedToWindow(paramView)) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)new View.OnAttachStateChangeListener()
      {
        final View $this_doOnAttach;
        
        public void onViewAttachedToWindow(View paramAnonymousView)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
          this.$this_doOnAttach.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
          paramFunction1.invoke(paramAnonymousView);
        }
        
        public void onViewDetachedFromWindow(View paramAnonymousView)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
        }
      });
    }
  }
  
  public static final void doOnDetach(View paramView, final Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    if (!ViewCompat.isAttachedToWindow(paramView)) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)new View.OnAttachStateChangeListener()
      {
        final View $this_doOnDetach;
        
        public void onViewAttachedToWindow(View paramAnonymousView)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
        }
        
        public void onViewDetachedFromWindow(View paramAnonymousView)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
          this.$this_doOnDetach.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
          paramFunction1.invoke(paramAnonymousView);
        }
      });
    }
  }
  
  public static final void doOnLayout(View paramView, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    if ((ViewCompat.isLaidOut(paramView)) && (!paramView.isLayoutRequested())) {
      paramFunction1.invoke(paramView);
    } else {
      paramView.addOnLayoutChangeListener((View.OnLayoutChangeListener)new View.OnLayoutChangeListener()
      {
        final Function1 $action$inlined;
        
        public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
        {
          Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
          paramAnonymousView.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
          this.$action$inlined.invoke(paramAnonymousView);
        }
      });
    }
  }
  
  public static final void doOnNextLayout(View paramView, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramView.addOnLayoutChangeListener((View.OnLayoutChangeListener)new View.OnLayoutChangeListener()
    {
      final Function1<View, Unit> $action;
      
      public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousView, "view");
        paramAnonymousView.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
        this.$action.invoke(paramAnonymousView);
      }
    });
  }
  
  public static final OneShotPreDrawListener doOnPreDraw(final View paramView, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramView = OneShotPreDrawListener.add(paramView, (Runnable)new Runnable()
    {
      final Function1<View, Unit> $action;
      
      public final void run()
      {
        this.$action.invoke(paramView);
      }
    });
    Intrinsics.checkNotNullExpressionValue(paramView, "View.doOnPreDraw(\n    cr…dd(this) { action(this) }");
    return paramView;
  }
  
  public static final Bitmap drawToBitmap(View paramView, Bitmap.Config paramConfig)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramConfig, "config");
    if (ViewCompat.isLaidOut(paramView))
    {
      paramConfig = Bitmap.createBitmap(paramView.getWidth(), paramView.getHeight(), paramConfig);
      Intrinsics.checkNotNullExpressionValue(paramConfig, "createBitmap(width, height, config)");
      Canvas localCanvas = new Canvas(paramConfig);
      localCanvas.translate(-paramView.getScrollX(), -paramView.getScrollY());
      paramView.draw(localCanvas);
      return paramConfig;
    }
    throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
  }
  
  public static final Sequence<View> getAllViews(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramView, null)
    {
      final View $this_allViews;
      private Object L$0;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$this_allViews, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super View> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        Object localObject3;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 2: 
          localObject1 = this;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          break;
        case 1: 
          localObject1 = (SequenceScope)this.L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          localObject1 = (SequenceScope)this.L$0;
          localObject3 = this.$this_allViews;
          Continuation localContinuation = (Continuation)this;
          this.L$0 = localObject1;
          this.label = 1;
          paramAnonymousObject = localObject1;
          if (((SequenceScope)localObject1).yield(localObject3, localContinuation) == localObject2) {
            return localObject2;
          }
          break;
        }
        Object localObject1 = this.$this_allViews;
        if ((localObject1 instanceof ViewGroup))
        {
          localObject3 = ViewGroupKt.getDescendants((ViewGroup)localObject1);
          localObject1 = (Continuation)this;
          this.L$0 = null;
          this.label = 2;
          if (((SequenceScope)paramAnonymousObject).yieldAll((Sequence)localObject3, (Continuation)localObject1) == localObject2) {
            return localObject2;
          }
          paramAnonymousObject = this;
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final Sequence<ViewParent> getAncestors(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    return SequencesKt.generateSequence(paramView.getParent(), (Function1)ancestors.1.INSTANCE);
  }
  
  public static final int getMarginBottom(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      paramView = (ViewGroup.MarginLayoutParams)paramView;
    } else {
      paramView = null;
    }
    int i;
    if (paramView != null) {
      i = paramView.bottomMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginEnd(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    int i;
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      i = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)paramView);
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginLeft(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      paramView = (ViewGroup.MarginLayoutParams)paramView;
    } else {
      paramView = null;
    }
    int i;
    if (paramView != null) {
      i = paramView.leftMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginRight(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      paramView = (ViewGroup.MarginLayoutParams)paramView;
    } else {
      paramView = null;
    }
    int i;
    if (paramView != null) {
      i = paramView.rightMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginStart(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    int i;
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      i = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)paramView);
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final int getMarginTop(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView = paramView.getLayoutParams();
    if ((paramView instanceof ViewGroup.MarginLayoutParams)) {
      paramView = (ViewGroup.MarginLayoutParams)paramView;
    } else {
      paramView = null;
    }
    int i;
    if (paramView != null) {
      i = paramView.topMargin;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final boolean isGone(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    boolean bool;
    if (paramView.getVisibility() == 8) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isInvisible(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    boolean bool;
    if (paramView.getVisibility() == 4) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isVisible(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    boolean bool;
    if (paramView.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final Runnable postDelayed(View paramView, long paramLong, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      final Function0<Unit> $action;
      
      public final void run()
      {
        this.$action.invoke();
      }
    };
    paramView.postDelayed(paramFunction0, paramLong);
    return paramFunction0;
  }
  
  public static final Runnable postOnAnimationDelayed(View paramView, long paramLong, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "action");
    paramFunction0 = (Runnable)new Runnable()
    {
      final Function0<Unit> $action;
      
      public final void run()
      {
        this.$action.invoke();
      }
    };
    paramView.postOnAnimationDelayed(paramFunction0, paramLong);
    return paramFunction0;
  }
  
  public static final void setGone(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    int i;
    if (paramBoolean) {
      i = 8;
    } else {
      i = 0;
    }
    paramView.setVisibility(i);
  }
  
  public static final void setInvisible(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    paramView.setVisibility(i);
  }
  
  public static final void setPadding(View paramView, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView.setPadding(paramInt, paramInt, paramInt, paramInt);
  }
  
  public static final void setVisible(View paramView, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    }
    paramView.setVisibility(i);
  }
  
  public static final void updateLayoutParams(View paramView, Function1<? super ViewGroup.LayoutParams, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (localLayoutParams != null)
    {
      paramFunction1.invoke(localLayoutParams);
      paramView.setLayoutParams(localLayoutParams);
      return;
    }
    throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
  }
  
  public static final void updatePadding(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static final void updatePaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ViewKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */