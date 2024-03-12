package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000R\n\000\n\002\020\002\n\002\030\002\n\000\n\002\020\b\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\020\007\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\016\n\000\n\002\020\021\n\002\020\r\n\002\b\005\n\002\030\002\n\002\b\002\032\026\020\000\032\0020\001*\0020\0022\b\b\001\020\003\032\0020\004H\002\032\024\020\005\032\0020\006*\0020\0022\b\b\001\020\003\032\0020\004\032\026\020\007\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004H\007\032\024\020\b\032\0020\t*\0020\0022\b\b\001\020\003\032\0020\004\032\024\020\n\032\0020\013*\0020\0022\b\b\001\020\003\032\0020\004\032\026\020\f\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004H\007\032\026\020\r\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004H\007\032\024\020\016\032\0020\017*\0020\0022\b\b\001\020\003\032\0020\004\032\024\020\020\032\0020\013*\0020\0022\b\b\001\020\003\032\0020\004\032\026\020\021\032\0020\022*\0020\0022\b\b\001\020\003\032\0020\004H\007\032\024\020\023\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004\032\024\020\024\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004\032\026\020\025\032\0020\004*\0020\0022\b\b\001\020\003\032\0020\004H\007\032\024\020\026\032\0020\027*\0020\0022\b\b\001\020\003\032\0020\004\032\037\020\030\032\b\022\004\022\0020\0320\031*\0020\0022\b\b\001\020\003\032\0020\004¢\006\002\020\033\032\024\020\034\032\0020\032*\0020\0022\b\b\001\020\003\032\0020\004\032/\020\035\032\002H\036\"\004\b\000\020\036*\0020\0022\022\020\037\032\016\022\004\022\0020\002\022\004\022\002H\0360 H\bø\001\000¢\006\002\020!\002\007\n\005\b20\001¨\006\""}, d2={"checkAttribute", "", "Landroid/content/res/TypedArray;", "index", "", "getBooleanOrThrow", "", "getColorOrThrow", "getColorStateListOrThrow", "Landroid/content/res/ColorStateList;", "getDimensionOrThrow", "", "getDimensionPixelOffsetOrThrow", "getDimensionPixelSizeOrThrow", "getDrawableOrThrow", "Landroid/graphics/drawable/Drawable;", "getFloatOrThrow", "getFontOrThrow", "Landroid/graphics/Typeface;", "getIntOrThrow", "getIntegerOrThrow", "getResourceIdOrThrow", "getStringOrThrow", "", "getTextArrayOrThrow", "", "", "(Landroid/content/res/TypedArray;I)[Ljava/lang/CharSequence;", "getTextOrThrow", "use", "R", "block", "Lkotlin/Function1;", "(Landroid/content/res/TypedArray;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class TypedArrayKt
{
  private static final void checkAttribute(TypedArray paramTypedArray, int paramInt)
  {
    if (paramTypedArray.hasValue(paramInt)) {
      return;
    }
    throw new IllegalArgumentException("Attribute not defined in set.");
  }
  
  public static final boolean getBooleanOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getBoolean(paramInt, false);
  }
  
  public static final int getColorOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getColor(paramInt, 0);
  }
  
  public static final ColorStateList getColorStateListOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    paramTypedArray = paramTypedArray.getColorStateList(paramInt);
    if (paramTypedArray != null) {
      return paramTypedArray;
    }
    throw new IllegalStateException("Attribute value was not a color or color state list.".toString());
  }
  
  public static final float getDimensionOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getDimension(paramInt, 0.0F);
  }
  
  public static final int getDimensionPixelOffsetOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getDimensionPixelOffset(paramInt, 0);
  }
  
  public static final int getDimensionPixelSizeOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getDimensionPixelSize(paramInt, 0);
  }
  
  public static final Drawable getDrawableOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    paramTypedArray = paramTypedArray.getDrawable(paramInt);
    Intrinsics.checkNotNull(paramTypedArray);
    return paramTypedArray;
  }
  
  public static final float getFloatOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getFloat(paramInt, 0.0F);
  }
  
  public static final Typeface getFontOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return TypedArrayApi26ImplKt.getFont(paramTypedArray, paramInt);
  }
  
  public static final int getIntOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getInt(paramInt, 0);
  }
  
  public static final int getIntegerOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getInteger(paramInt, 0);
  }
  
  public static final int getResourceIdOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    return paramTypedArray.getResourceId(paramInt, 0);
  }
  
  public static final String getStringOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    paramTypedArray = paramTypedArray.getString(paramInt);
    if (paramTypedArray != null) {
      return paramTypedArray;
    }
    throw new IllegalStateException("Attribute value could not be coerced to String.".toString());
  }
  
  public static final CharSequence[] getTextArrayOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    paramTypedArray = paramTypedArray.getTextArray(paramInt);
    Intrinsics.checkNotNullExpressionValue(paramTypedArray, "getTextArray(index)");
    return paramTypedArray;
  }
  
  public static final CharSequence getTextOrThrow(TypedArray paramTypedArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    checkAttribute(paramTypedArray, paramInt);
    paramTypedArray = paramTypedArray.getText(paramInt);
    if (paramTypedArray != null) {
      return paramTypedArray;
    }
    throw new IllegalStateException("Attribute value could not be coerced to CharSequence.".toString());
  }
  
  public static final <R> R use(TypedArray paramTypedArray, Function1<? super TypedArray, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramTypedArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramFunction1 = paramFunction1.invoke(paramTypedArray);
    paramTypedArray.recycle();
    return paramFunction1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/res/TypedArrayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */