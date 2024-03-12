package androidx.core.content.res;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\036\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\000\bÃ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\032\020\003\032\0020\0042\006\020\005\032\0020\0062\b\b\001\020\007\032\0020\bH\007¨\006\t"}, d2={"Landroidx/core/content/res/TypedArrayApi26ImplKt;", "", "()V", "getFont", "Landroid/graphics/Typeface;", "typedArray", "Landroid/content/res/TypedArray;", "index", "", "core-ktx_release"}, k=1, mv={1, 6, 0}, xi=48)
final class TypedArrayApi26ImplKt
{
  public static final TypedArrayApi26ImplKt INSTANCE = new TypedArrayApi26ImplKt();
  
  @JvmStatic
  public static final Typeface getFont(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "typedArray");
    paramTypedArray = paramTypedArray.getFont(paramInt);
    Intrinsics.checkNotNull(paramTypedArray);
    return paramTypedArray;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/TypedArrayApi26ImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */