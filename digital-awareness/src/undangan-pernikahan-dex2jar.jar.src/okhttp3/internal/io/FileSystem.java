package okhttp3.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\t\n\000\n\002\030\002\n\002\b\002\bf\030\000 \0242\0020\001:\001\024J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\020\020\006\032\0020\0072\006\020\004\032\0020\005H&J\020\020\b\032\0020\0072\006\020\t\032\0020\005H&J\020\020\n\032\0020\0132\006\020\004\032\0020\005H&J\030\020\f\032\0020\0072\006\020\r\032\0020\0052\006\020\016\032\0020\005H&J\020\020\017\032\0020\0032\006\020\004\032\0020\005H&J\020\020\020\032\0020\0212\006\020\004\032\0020\005H&J\020\020\022\032\0020\0232\006\020\004\032\0020\005H&¨\006\025"}, d2={"Lokhttp3/internal/io/FileSystem;", "", "appendingSink", "Lokio/Sink;", "file", "Ljava/io/File;", "delete", "", "deleteContents", "directory", "exists", "", "rename", "from", "to", "sink", "size", "", "source", "Lokio/Source;", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public abstract interface FileSystem
{
  public static final Companion Companion = new Companion(null);
  public static final FileSystem SYSTEM = (FileSystem)new FileSystem.Companion.SystemFileSystem();
  
  public abstract Sink appendingSink(File paramFile)
    throws FileNotFoundException;
  
  public abstract void delete(File paramFile)
    throws IOException;
  
  public abstract void deleteContents(File paramFile)
    throws IOException;
  
  public abstract boolean exists(File paramFile);
  
  public abstract void rename(File paramFile1, File paramFile2)
    throws IOException;
  
  public abstract Sink sink(File paramFile)
    throws FileNotFoundException;
  
  public abstract long size(File paramFile);
  
  public abstract Source source(File paramFile)
    throws FileNotFoundException;
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\b\003\030\0002\0020\001:\001\005B\007\b\002¢\006\002\020\002R\026\020\003\032\0020\0048\006X\004ø\001\000¢\006\002\n\000¨\006\001\002\007\n\005\bF0\001¨\006\006"}, d2={"Lokhttp3/internal/io/FileSystem$Companion;", "", "()V", "SYSTEM", "Lokhttp3/internal/io/FileSystem;", "SystemFileSystem", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    static final Companion $$INSTANCE;
    
    @Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\t\n\000\n\002\030\002\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\005¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\026J\020\020\007\032\0020\b2\006\020\005\032\0020\006H\026J\020\020\t\032\0020\b2\006\020\n\032\0020\006H\026J\020\020\013\032\0020\f2\006\020\005\032\0020\006H\026J\030\020\r\032\0020\b2\006\020\016\032\0020\0062\006\020\017\032\0020\006H\026J\020\020\020\032\0020\0042\006\020\005\032\0020\006H\026J\020\020\021\032\0020\0222\006\020\005\032\0020\006H\026J\020\020\023\032\0020\0242\006\020\005\032\0020\006H\026J\b\020\025\032\0020\026H\026¨\006\027"}, d2={"Lokhttp3/internal/io/FileSystem$Companion$SystemFileSystem;", "Lokhttp3/internal/io/FileSystem;", "()V", "appendingSink", "Lokio/Sink;", "file", "Ljava/io/File;", "delete", "", "deleteContents", "directory", "exists", "", "rename", "from", "to", "sink", "size", "", "source", "Lokio/Source;", "toString", "", "okhttp"}, k=1, mv={1, 4, 0})
    private static final class SystemFileSystem
      implements FileSystem
    {
      public Sink appendingSink(File paramFile)
        throws FileNotFoundException
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        try
        {
          Sink localSink = Okio.appendingSink(paramFile);
          paramFile = localSink;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          paramFile.getParentFile().mkdirs();
          paramFile = Okio.appendingSink(paramFile);
        }
        return paramFile;
      }
      
      public void delete(File paramFile)
        throws IOException
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        if ((!paramFile.delete()) && (paramFile.exists())) {
          throw ((Throwable)new IOException("failed to delete " + paramFile));
        }
      }
      
      public void deleteContents(File paramFile)
        throws IOException
      {
        Intrinsics.checkNotNullParameter(paramFile, "directory");
        File[] arrayOfFile = paramFile.listFiles();
        if (arrayOfFile != null)
        {
          int j = arrayOfFile.length;
          int i = 0;
          while (i < j)
          {
            paramFile = arrayOfFile[i];
            Intrinsics.checkNotNullExpressionValue(paramFile, "file");
            if (paramFile.isDirectory()) {
              deleteContents(paramFile);
            }
            if (paramFile.delete()) {
              i++;
            } else {
              throw ((Throwable)new IOException("failed to delete " + paramFile));
            }
          }
          return;
        }
        throw ((Throwable)new IOException("not a readable directory: " + paramFile));
      }
      
      public boolean exists(File paramFile)
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        return paramFile.exists();
      }
      
      public void rename(File paramFile1, File paramFile2)
        throws IOException
      {
        Intrinsics.checkNotNullParameter(paramFile1, "from");
        Intrinsics.checkNotNullParameter(paramFile2, "to");
        delete(paramFile2);
        if (paramFile1.renameTo(paramFile2)) {
          return;
        }
        throw ((Throwable)new IOException("failed to rename " + paramFile1 + " to " + paramFile2));
      }
      
      public Sink sink(File paramFile)
        throws FileNotFoundException
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        try
        {
          Sink localSink = Okio.sink$default(paramFile, false, 1, null);
          paramFile = localSink;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          paramFile.getParentFile().mkdirs();
          paramFile = Okio.sink$default(paramFile, false, 1, null);
        }
        return paramFile;
      }
      
      public long size(File paramFile)
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        return paramFile.length();
      }
      
      public Source source(File paramFile)
        throws FileNotFoundException
      {
        Intrinsics.checkNotNullParameter(paramFile, "file");
        return Okio.source(paramFile);
      }
      
      public String toString()
      {
        return "FileSystem.SYSTEM";
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/io/FileSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */