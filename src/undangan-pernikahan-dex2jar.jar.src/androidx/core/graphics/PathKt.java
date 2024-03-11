package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.Path.Op;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\002\b\002\n\002\020\034\n\002\030\002\n\000\n\002\020\007\n\002\b\005\032\025\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\034\020\003\032\b\022\004\022\0020\0050\004*\0020\0012\b\b\002\020\006\032\0020\007H\007\032\025\020\b\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\t\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f\032\025\020\n\032\0020\001*\0020\0012\006\020\002\032\0020\001H\n\032\025\020\013\032\0020\001*\0020\0012\006\020\002\032\0020\001H\f¨\006\f"}, d2={"and", "Landroid/graphics/Path;", "p", "flatten", "", "Landroidx/core/graphics/PathSegment;", "error", "", "minus", "or", "plus", "xor", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class PathKt
{
  public static final Path and(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "p");
    Path localPath = new Path();
    localPath.op(paramPath1, paramPath2, Path.Op.INTERSECT);
    return localPath;
  }
  
  public static final Iterable<PathSegment> flatten(Path paramPath, float paramFloat)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = PathUtils.flatten(paramPath, paramFloat);
    Intrinsics.checkNotNullExpressionValue(paramPath, "flatten(this, error)");
    return (Iterable)paramPath;
  }
  
  public static final Path minus(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "p");
    paramPath1 = new Path(paramPath1);
    paramPath1.op(paramPath2, Path.Op.DIFFERENCE);
    return paramPath1;
  }
  
  public static final Path or(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "p");
    paramPath1 = new Path(paramPath1);
    paramPath1.op(paramPath2, Path.Op.UNION);
    return paramPath1;
  }
  
  public static final Path plus(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "p");
    paramPath1 = new Path(paramPath1);
    paramPath1.op(paramPath2, Path.Op.UNION);
    return paramPath1;
  }
  
  public static final Path xor(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "p");
    paramPath1 = new Path(paramPath1);
    paramPath1.op(paramPath2, Path.Op.XOR);
    return paramPath1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/PathKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */