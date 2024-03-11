package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Shader;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\032\n\000\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\032)\020\000\032\0020\001*\0020\0022\027\020\003\032\023\022\004\022\0020\005\022\004\022\0020\0010\004¢\006\002\b\006H\bø\001\000\002\007\n\005\b20\001¨\006\007"}, d2={"transform", "", "Landroid/graphics/Shader;", "block", "Lkotlin/Function1;", "Landroid/graphics/Matrix;", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ShaderKt
{
  public static final void transform(Shader paramShader, Function1<? super Matrix, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramShader, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    Matrix localMatrix = new Matrix();
    paramShader.getLocalMatrix(localMatrix);
    paramFunction1.invoke(localMatrix);
    paramShader.setLocalMatrix(localMatrix);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/ShaderKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */