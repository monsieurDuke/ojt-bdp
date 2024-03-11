package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\024\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\032\024\020\000\032\0020\001*\0020\0022\b\b\002\020\003\032\0020\004\032\n\020\005\032\0020\001*\0020\002\032\n\020\006\032\0020\001*\0020\002¨\006\007"}, d2={"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/io/FilesKt")
class FilesKt__FileTreeWalkKt
  extends FilesKt__FileReadWriteKt
{
  public static final FileTreeWalk walk(File paramFile, FileWalkDirection paramFileWalkDirection)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramFileWalkDirection, "direction");
    return new FileTreeWalk(paramFile, paramFileWalkDirection);
  }
  
  public static final FileTreeWalk walkBottomUp(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    return FilesKt.walk(paramFile, FileWalkDirection.BOTTOM_UP);
  }
  
  public static final FileTreeWalk walkTopDown(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    return FilesKt.walk(paramFile, FileWalkDirection.TOP_DOWN);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/FilesKt__FileTreeWalkKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */