package kotlin.io.path;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\006\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\007\032\0020\0042\006\020\b\032\0020\0042\006\020\t\032\0020\004R\026\020\003\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000R\026\020\006\032\n \005*\004\030\0010\0040\004X\004¢\006\002\n\000¨\006\n"}, d2={"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", "base", "kotlin-stdlib-jdk7"}, k=1, mv={1, 6, 0}, xi=48)
final class PathRelativizer
{
  public static final PathRelativizer INSTANCE = new PathRelativizer();
  private static final Path emptyPath = Paths.get("", new String[0]);
  private static final Path parentPath = Paths.get("..", new String[0]);
  
  public final Path tryRelativeTo(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "path");
    Intrinsics.checkNotNullParameter(paramPath2, "base");
    Object localObject = paramPath2.normalize();
    paramPath2 = paramPath1.normalize();
    paramPath1 = ((Path)localObject).relativize(paramPath2);
    int k = Math.min(((Path)localObject).getNameCount(), paramPath2.getNameCount());
    int i = 0;
    int j;
    Path localPath1;
    do
    {
      j = i;
      if (j >= k) {
        break;
      }
      i = j + 1;
      Path localPath2 = ((Path)localObject).getName(j);
      localPath1 = parentPath;
      if (!Intrinsics.areEqual(localPath2, localPath1)) {
        break;
      }
    } while (Intrinsics.areEqual(paramPath2.getName(j), localPath1));
    throw new IllegalArgumentException("Unable to compute relative path");
    if ((!Intrinsics.areEqual(paramPath2, localObject)) && (Intrinsics.areEqual(localObject, emptyPath)))
    {
      paramPath1 = paramPath2;
    }
    else
    {
      localObject = paramPath1.toString();
      paramPath2 = paramPath1.getFileSystem().getSeparator();
      Intrinsics.checkNotNullExpressionValue(paramPath2, "rn.fileSystem.separator");
      if (StringsKt.endsWith$default((String)localObject, paramPath2, false, 2, null))
      {
        paramPath2 = paramPath1.getFileSystem();
        paramPath1 = StringsKt.dropLast((String)localObject, paramPath1.getFileSystem().getSeparator().length());
        Log5ECF72.a(paramPath1);
        LogE84000.a(paramPath1);
        Log229316.a(paramPath1);
        paramPath1 = paramPath2.getPath(paramPath1, new String[0]);
      }
    }
    Intrinsics.checkNotNullExpressionValue(paramPath1, "r");
    return paramPath1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/path/PathRelativizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */