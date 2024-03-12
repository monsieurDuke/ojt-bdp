package kotlin.io;

import java.io.File;
import kotlin.Metadata;

@Metadata(d1={"\000\032\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\030\0002\0020\001B%\022\006\020\002\032\0020\003\022\n\b\002\020\004\032\004\030\0010\003\022\n\b\002\020\005\032\004\030\0010\006¢\006\002\020\007¨\006\b"}, d2={"Lkotlin/io/FileAlreadyExistsException;", "Lkotlin/io/FileSystemException;", "file", "Ljava/io/File;", "other", "reason", "", "(Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class FileAlreadyExistsException
  extends FileSystemException
{
  public FileAlreadyExistsException(File paramFile1, File paramFile2, String paramString)
  {
    super(paramFile1, paramFile2, paramString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/FileAlreadyExistsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */