package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000T\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020)\n\002\b\003\n\002\030\002\n\002\b\t\032\025\020\020\032\0020\021*\0020\0032\006\020\022\032\0020\002H\n\0323\020\023\032\0020\024*\0020\0032!\020\025\032\035\022\023\022\0210\002¢\006\f\b\027\022\b\b\030\022\004\b\b(\022\022\004\022\0020\0240\026H\bø\001\000\032H\020\031\032\0020\024*\0020\00326\020\025\0322\022\023\022\0210\r¢\006\f\b\027\022\b\b\030\022\004\b\b(\033\022\023\022\0210\002¢\006\f\b\027\022\b\b\030\022\004\b\b(\022\022\004\022\0020\0240\032H\bø\001\000\032\025\020\034\032\0020\002*\0020\0032\006\020\033\032\0020\rH\002\032\r\020\035\032\0020\021*\0020\003H\b\032\r\020\036\032\0020\021*\0020\003H\b\032\023\020\037\032\b\022\004\022\0020\0020 *\0020\003H\002\032\025\020!\032\0020\024*\0020\0032\006\020\022\032\0020\002H\n\032\025\020\"\032\0020\024*\0020\0032\006\020\022\032\0020\002H\n\032\027\020#\032\0020\024*\0020$2\b\b\001\020\f\032\0020\rH\b\0325\020%\032\0020\024*\0020$2\b\b\003\020&\032\0020\r2\b\b\003\020'\032\0020\r2\b\b\003\020(\032\0020\r2\b\b\003\020)\032\0020\rH\b\0325\020*\032\0020\024*\0020$2\b\b\003\020+\032\0020\r2\b\b\003\020'\032\0020\r2\b\b\003\020,\032\0020\r2\b\b\003\020)\032\0020\rH\b\"\033\020\000\032\b\022\004\022\0020\0020\001*\0020\0038F¢\006\006\032\004\b\004\020\005\"\033\020\006\032\b\022\004\022\0020\0020\001*\0020\0038F¢\006\006\032\004\b\007\020\005\"\026\020\b\032\0020\t*\0020\0038Æ\002¢\006\006\032\004\b\n\020\013\"\026\020\f\032\0020\r*\0020\0038Æ\002¢\006\006\032\004\b\016\020\017\002\007\n\005\b20\001¨\006-"}, d2={"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "descendants", "getDescendants", "indices", "Lkotlin/ranges/IntRange;", "getIndices", "(Landroid/view/ViewGroup;)Lkotlin/ranges/IntRange;", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", "view", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ViewGroupKt
{
  public static final boolean contains(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    Intrinsics.checkNotNullParameter(paramView, "view");
    boolean bool;
    if (paramViewGroup.indexOfChild(paramView) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final void forEach(ViewGroup paramViewGroup, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    int i = 0;
    int j = paramViewGroup.getChildCount();
    while (i < j)
    {
      View localView = paramViewGroup.getChildAt(i);
      Intrinsics.checkNotNullExpressionValue(localView, "getChildAt(index)");
      paramFunction1.invoke(localView);
      i++;
    }
  }
  
  public static final void forEachIndexed(ViewGroup paramViewGroup, Function2<? super Integer, ? super View, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int i = 0;
    int j = paramViewGroup.getChildCount();
    while (i < j)
    {
      View localView = paramViewGroup.getChildAt(i);
      Intrinsics.checkNotNullExpressionValue(localView, "getChildAt(index)");
      paramFunction2.invoke(Integer.valueOf(i), localView);
      i++;
    }
  }
  
  public static final View get(ViewGroup paramViewGroup, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    View localView = paramViewGroup.getChildAt(paramInt);
    if (localView != null) {
      return localView;
    }
    throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + paramViewGroup.getChildCount());
  }
  
  public static final Sequence<View> getChildren(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    (Sequence)new Sequence()
    {
      final ViewGroup $this_children;
      
      public Iterator<View> iterator()
      {
        return ViewGroupKt.iterator(this.$this_children);
      }
    };
  }
  
  public static final Sequence<View> getDescendants(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    SequencesKt.sequence((Function2)new RestrictedSuspendLambda(paramViewGroup, null)
    {
      final ViewGroup $this_descendants;
      int I$0;
      int I$1;
      private Object L$0;
      Object L$1;
      Object L$2;
      int label;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        paramAnonymousContinuation = new 1(this.$this_descendants, paramAnonymousContinuation);
        paramAnonymousContinuation.L$0 = paramAnonymousObject;
        return (Continuation)paramAnonymousContinuation;
      }
      
      public final Object invoke(SequenceScope<? super View> paramAnonymousSequenceScope, Continuation<? super Unit> paramAnonymousContinuation)
      {
        return ((1)create(paramAnonymousSequenceScope, paramAnonymousContinuation)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        Object localObject1;
        int k;
        int j;
        int i;
        Object localObject2;
        Object localObject3;
        Object localObject4;
        switch (this.label)
        {
        default: 
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        case 2: 
          localObject1 = this;
          k = 0;
          j = ((1)localObject1).I$1;
          i = ((1)localObject1).I$0;
          localObject2 = (ViewGroup)((1)localObject1).L$1;
          localObject3 = (SequenceScope)((1)localObject1).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          localObject1 = localObject3;
          break;
        case 1: 
          localObject1 = this;
          k = 0;
          j = ((1)localObject1).I$1;
          i = ((1)localObject1).I$0;
          localObject3 = (View)((1)localObject1).L$2;
          localObject2 = (ViewGroup)((1)localObject1).L$1;
          localObject4 = (SequenceScope)((1)localObject1).L$0;
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = localObject1;
          localObject1 = localObject4;
          break;
        case 0: 
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = this;
          localObject2 = (SequenceScope)((1)paramAnonymousObject).L$0;
          localObject1 = ((1)paramAnonymousObject).$this_descendants;
          k = 0;
          i = 0;
          j = ((ViewGroup)localObject1).getChildCount();
        }
        while (i < j)
        {
          localObject3 = ((ViewGroup)localObject1).getChildAt(i);
          Intrinsics.checkNotNullExpressionValue(localObject3, "getChildAt(index)");
          ((1)paramAnonymousObject).L$0 = localObject2;
          ((1)paramAnonymousObject).L$1 = localObject1;
          ((1)paramAnonymousObject).L$2 = localObject3;
          ((1)paramAnonymousObject).I$0 = i;
          ((1)paramAnonymousObject).I$1 = j;
          ((1)paramAnonymousObject).label = 1;
          if (((SequenceScope)localObject2).yield(localObject3, (Continuation)paramAnonymousObject) == localObject5) {
            return localObject5;
          }
          localObject4 = localObject1;
          localObject1 = localObject2;
          localObject2 = localObject4;
          if ((localObject3 instanceof ViewGroup))
          {
            localObject3 = ViewGroupKt.getDescendants((ViewGroup)localObject3);
            ((1)paramAnonymousObject).L$0 = localObject1;
            ((1)paramAnonymousObject).L$1 = localObject2;
            ((1)paramAnonymousObject).L$2 = null;
            ((1)paramAnonymousObject).I$0 = i;
            ((1)paramAnonymousObject).I$1 = j;
            ((1)paramAnonymousObject).label = 2;
            if (((SequenceScope)localObject1).yieldAll((Sequence)localObject3, (Continuation)paramAnonymousObject) == localObject5) {
              return localObject5;
            }
            localObject3 = localObject2;
            localObject2 = localObject1;
            localObject1 = localObject3;
          }
          else
          {
            localObject3 = localObject2;
            localObject2 = localObject1;
            localObject1 = localObject3;
          }
          i++;
        }
        return Unit.INSTANCE;
      }
    });
  }
  
  public static final IntRange getIndices(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    return RangesKt.until(0, paramViewGroup.getChildCount());
  }
  
  public static final int getSize(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    return paramViewGroup.getChildCount();
  }
  
  public static final boolean isEmpty(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    boolean bool;
    if (paramViewGroup.getChildCount() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    boolean bool;
    if (paramViewGroup.getChildCount() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final Iterator<View> iterator(ViewGroup paramViewGroup)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    (Iterator)new Iterator()
    {
      final ViewGroup $this_iterator;
      private int index;
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_iterator.getChildCount()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public View next()
      {
        Object localObject = this.$this_iterator;
        int i = this.index;
        this.index = (i + 1);
        localObject = ((ViewGroup)localObject).getChildAt(i);
        if (localObject != null) {
          return (View)localObject;
        }
        throw new IndexOutOfBoundsException();
      }
      
      public void remove()
      {
        ViewGroup localViewGroup = this.$this_iterator;
        int i = this.index - 1;
        this.index = i;
        localViewGroup.removeViewAt(i);
      }
    };
  }
  
  public static final void minusAssign(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    Intrinsics.checkNotNullParameter(paramView, "view");
    paramViewGroup.removeView(paramView);
  }
  
  public static final void plusAssign(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkNotNullParameter(paramViewGroup, "<this>");
    Intrinsics.checkNotNullParameter(paramView, "view");
    paramViewGroup.addView(paramView);
  }
  
  public static final void setMargins(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramMarginLayoutParams, "<this>");
    paramMarginLayoutParams.setMargins(paramInt, paramInt, paramInt, paramInt);
  }
  
  public static final void updateMargins(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkNotNullParameter(paramMarginLayoutParams, "<this>");
    paramMarginLayoutParams.setMargins(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static final void updateMarginsRelative(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkNotNullParameter(paramMarginLayoutParams, "<this>");
    paramMarginLayoutParams.setMarginStart(paramInt1);
    paramMarginLayoutParams.topMargin = paramInt2;
    paramMarginLayoutParams.setMarginEnd(paramInt3);
    paramMarginLayoutParams.bottomMargin = paramInt4;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ViewGroupKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */