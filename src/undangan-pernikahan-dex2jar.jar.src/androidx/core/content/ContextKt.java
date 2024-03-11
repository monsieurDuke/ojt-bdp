package androidx.core.content;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000:\n\002\b\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\025\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\032 \020\000\032\004\030\001H\001\"\n\b\000\020\001\030\001*\0020\002*\0020\003H\b¢\006\002\020\004\032Q\020\005\032\0020\006*\0020\0032\n\b\002\020\007\032\004\030\0010\b2\006\020\t\032\0020\n2\b\b\003\020\013\032\0020\f2\b\b\003\020\r\032\0020\f2\027\020\016\032\023\022\004\022\0020\020\022\004\022\0020\0060\017¢\006\002\b\021H\bø\001\000\032;\020\005\032\0020\006*\0020\0032\b\b\001\020\022\032\0020\f2\006\020\t\032\0020\n2\027\020\016\032\023\022\004\022\0020\020\022\004\022\0020\0060\017¢\006\002\b\021H\bø\001\000\002\007\n\005\b20\001¨\006\023"}, d2={"getSystemService", "T", "", "Landroid/content/Context;", "(Landroid/content/Context;)Ljava/lang/Object;", "withStyledAttributes", "", "set", "Landroid/util/AttributeSet;", "attrs", "", "defStyleAttr", "", "defStyleRes", "block", "Lkotlin/Function1;", "Landroid/content/res/TypedArray;", "Lkotlin/ExtensionFunctionType;", "resourceId", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ContextKt
{
  public static final void withStyledAttributes(Context paramContext, int paramInt, int[] paramArrayOfInt, Function1<? super TypedArray, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramContext, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "attrs");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramContext = paramContext.obtainStyledAttributes(paramInt, paramArrayOfInt);
    Intrinsics.checkNotNullExpressionValue(paramContext, "obtainStyledAttributes(resourceId, attrs)");
    paramFunction1.invoke(paramContext);
    paramContext.recycle();
  }
  
  public static final void withStyledAttributes(Context paramContext, AttributeSet paramAttributeSet, int[] paramArrayOfInt, int paramInt1, int paramInt2, Function1<? super TypedArray, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramContext, "<this>");
    Intrinsics.checkNotNullParameter(paramArrayOfInt, "attrs");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, paramArrayOfInt, paramInt1, paramInt2);
    Intrinsics.checkNotNullExpressionValue(paramContext, "obtainStyledAttributes(s…efStyleAttr, defStyleRes)");
    paramFunction1.invoke(paramContext);
    paramContext.recycle();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/ContextKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */