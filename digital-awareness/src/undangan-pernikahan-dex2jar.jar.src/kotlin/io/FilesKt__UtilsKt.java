package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000<\n\000\n\002\020\016\n\002\030\002\n\002\b\f\n\002\020\013\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020 \n\000\n\002\030\002\n\002\b\f\032*\020\t\032\0020\0022\b\b\002\020\n\032\0020\0012\n\b\002\020\013\032\004\030\0010\0012\n\b\002\020\f\032\004\030\0010\002H\007\032*\020\r\032\0020\0022\b\b\002\020\n\032\0020\0012\n\b\002\020\013\032\004\030\0010\0012\n\b\002\020\f\032\004\030\0010\002H\007\0328\020\016\032\0020\017*\0020\0022\006\020\020\032\0020\0022\b\b\002\020\021\032\0020\0172\032\b\002\020\022\032\024\022\004\022\0020\002\022\004\022\0020\024\022\004\022\0020\0250\023\032&\020\026\032\0020\002*\0020\0022\006\020\020\032\0020\0022\b\b\002\020\021\032\0020\0172\b\b\002\020\027\032\0020\030\032\n\020\031\032\0020\017*\0020\002\032\022\020\032\032\0020\017*\0020\0022\006\020\033\032\0020\002\032\022\020\032\032\0020\017*\0020\0022\006\020\033\032\0020\001\032\n\020\034\032\0020\002*\0020\002\032\035\020\034\032\b\022\004\022\0020\0020\035*\b\022\004\022\0020\0020\035H\002¢\006\002\b\036\032\021\020\034\032\0020\037*\0020\037H\002¢\006\002\b\036\032\022\020 \032\0020\002*\0020\0022\006\020!\032\0020\002\032\024\020\"\032\004\030\0010\002*\0020\0022\006\020!\032\0020\002\032\022\020#\032\0020\002*\0020\0022\006\020!\032\0020\002\032\022\020$\032\0020\002*\0020\0022\006\020%\032\0020\002\032\022\020$\032\0020\002*\0020\0022\006\020%\032\0020\001\032\022\020&\032\0020\002*\0020\0022\006\020%\032\0020\002\032\022\020&\032\0020\002*\0020\0022\006\020%\032\0020\001\032\022\020'\032\0020\017*\0020\0022\006\020\033\032\0020\002\032\022\020'\032\0020\017*\0020\0022\006\020\033\032\0020\001\032\022\020(\032\0020\001*\0020\0022\006\020!\032\0020\002\032\033\020)\032\004\030\0010\001*\0020\0022\006\020!\032\0020\002H\002¢\006\002\b*\"\025\020\000\032\0020\001*\0020\0028F¢\006\006\032\004\b\003\020\004\"\025\020\005\032\0020\001*\0020\0028F¢\006\006\032\004\b\006\020\004\"\025\020\007\032\0020\001*\0020\0028F¢\006\006\032\004\b\b\020\004¨\006+"}, d2={"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/io/FilesKt")
class FilesKt__UtilsKt
  extends FilesKt__FileTreeWalkKt
{
  public static final boolean copyRecursively(File paramFile1, File paramFile2, boolean paramBoolean, Function2<? super File, ? super IOException, ? extends OnErrorAction> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "target");
    Intrinsics.checkNotNullParameter(paramFunction2, "onError");
    boolean bool2 = paramFile1.exists();
    boolean bool1 = true;
    if (!bool2)
    {
      if (paramFunction2.invoke(paramFile1, new NoSuchFileException(paramFile1, null, "The source file doesn't exist.", 2, null)) != OnErrorAction.TERMINATE) {
        paramBoolean = bool1;
      } else {
        paramBoolean = false;
      }
      return paramBoolean;
    }
    try
    {
      Object localObject2 = FilesKt.walkTopDown(paramFile1);
      Object localObject1 = new kotlin/io/FilesKt__UtilsKt$copyRecursively$2;
      ((copyRecursively.2)localObject1).<init>(paramFunction2);
      localObject1 = ((FileTreeWalk)localObject2).onFail((Function2)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (File)((Iterator)localObject1).next();
        Object localObject3;
        if (!((File)localObject2).exists())
        {
          localObject3 = new kotlin/io/NoSuchFileException;
          ((NoSuchFileException)localObject3).<init>((File)localObject2, null, "The source file doesn't exist.", 2, null);
          if (paramFunction2.invoke(localObject2, localObject3) == OnErrorAction.TERMINATE) {
            return false;
          }
        }
        else
        {
          Object localObject4 = FilesKt.toRelativeString((File)localObject2, paramFile1);
          Log5ECF72.a(localObject4);
          LogE84000.a(localObject4);
          Log229316.a(localObject4);
          localObject3 = new java/io/File;
          ((File)localObject3).<init>(paramFile2, (String)localObject4);
          if ((((File)localObject3).exists()) && ((!((File)localObject2).isDirectory()) || (!((File)localObject3).isDirectory())))
          {
            int i;
            if (!paramBoolean) {
              i = 1;
            } else if (((File)localObject3).isDirectory())
            {
              if (!FilesKt.deleteRecursively((File)localObject3)) {
                i = 1;
              } else {
                i = 0;
              }
            }
            else if (!((File)localObject3).delete()) {
              i = 1;
            } else {
              i = 0;
            }
            if (i != 0)
            {
              localObject4 = new kotlin/io/FileAlreadyExistsException;
              ((FileAlreadyExistsException)localObject4).<init>((File)localObject2, (File)localObject3, "The destination file already exists.");
              if (paramFunction2.invoke(localObject3, localObject4) == OnErrorAction.TERMINATE) {
                return false;
              }
              continue;
            }
          }
          if (((File)localObject2).isDirectory())
          {
            ((File)localObject3).mkdirs();
          }
          else if (FilesKt.copyTo$default((File)localObject2, (File)localObject3, paramBoolean, 0, 4, null).length() != ((File)localObject2).length())
          {
            localObject3 = new java/io/IOException;
            ((IOException)localObject3).<init>("Source file wasn't copied completely, length of destination file differs.");
            localObject3 = paramFunction2.invoke(localObject2, localObject3);
            localObject2 = OnErrorAction.TERMINATE;
            if (localObject3 == localObject2) {
              return false;
            }
          }
        }
      }
      return true;
    }
    catch (TerminateException paramFile1) {}
    return false;
  }
  
  public static final File copyTo(File paramFile1, File paramFile2, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "target");
    if (paramFile1.exists())
    {
      if (paramFile2.exists()) {
        if (paramBoolean)
        {
          if (!paramFile2.delete()) {
            throw new FileAlreadyExistsException(paramFile1, paramFile2, "Tried to overwrite the destination, but failed to delete it.");
          }
        }
        else {
          throw new FileAlreadyExistsException(paramFile1, paramFile2, "The destination file already exists.");
        }
      }
      Object localObject;
      if (paramFile1.isDirectory())
      {
        if (!paramFile2.mkdirs()) {
          throw new FileSystemException(paramFile1, paramFile2, "Failed to create target directory.");
        }
      }
      else
      {
        localObject = paramFile2.getParentFile();
        if (localObject != null) {
          ((File)localObject).mkdirs();
        }
        paramFile1 = (Closeable)new FileInputStream(paramFile1);
      }
      try
      {
        FileInputStream localFileInputStream = (FileInputStream)paramFile1;
        localObject = new java/io/FileOutputStream;
        ((FileOutputStream)localObject).<init>(paramFile2);
        localObject = (Closeable)localObject;
        try
        {
          FileOutputStream localFileOutputStream = (FileOutputStream)localObject;
          ByteStreamsKt.copyTo((InputStream)localFileInputStream, (OutputStream)localFileOutputStream, paramInt);
          CloseableKt.closeFinally((Closeable)localObject, null);
          CloseableKt.closeFinally(paramFile1, null);
          return paramFile2;
        }
        finally
        {
          try
          {
            throw localThrowable2;
          }
          finally
          {
            CloseableKt.closeFinally((Closeable)localObject, localThrowable2);
          }
        }
        throw new NoSuchFileException(paramFile1, null, "The source file doesn't exist.", 2, null);
      }
      finally
      {
        try
        {
          throw localThrowable1;
        }
        finally
        {
          CloseableKt.closeFinally(paramFile1, localThrowable1);
        }
      }
    }
  }
  
  @Deprecated(message="Avoid creating temporary directories in the default temp location with this function due to too wide permissions on the newly created directory. Use kotlin.io.path.createTempDirectory instead.")
  public static final File createTempDir(String paramString1, String paramString2, File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramString1, "prefix");
    paramString1 = File.createTempFile(paramString1, paramString2, paramFile);
    paramString1.delete();
    if (paramString1.mkdir())
    {
      Intrinsics.checkNotNullExpressionValue(paramString1, "dir");
      return paramString1;
    }
    throw new IOException("Unable to create temporary directory " + paramString1 + '.');
  }
  
  @Deprecated(message="Avoid creating temporary files in the default temp location with this function due to too wide permissions on the newly created file. Use kotlin.io.path.createTempFile instead or resort to java.io.File.createTempFile.")
  public static final File createTempFile(String paramString1, String paramString2, File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramString1, "prefix");
    paramString1 = File.createTempFile(paramString1, paramString2, paramFile);
    Intrinsics.checkNotNullExpressionValue(paramString1, "createTempFile(prefix, suffix, directory)");
    return paramString1;
  }
  
  public static final boolean deleteRecursively(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    paramFile = (Sequence)FilesKt.walkBottomUp(paramFile);
    boolean bool = true;
    Iterator localIterator = paramFile.iterator();
    while (localIterator.hasNext())
    {
      paramFile = (File)localIterator.next();
      if (((paramFile.delete()) || (!paramFile.exists())) && (bool)) {
        bool = true;
      } else {
        bool = false;
      }
    }
    return bool;
  }
  
  public static final boolean endsWith(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "other");
    FilePathComponents localFilePathComponents1 = FilesKt.toComponents(paramFile1);
    FilePathComponents localFilePathComponents2 = FilesKt.toComponents(paramFile2);
    if (localFilePathComponents2.isRooted()) {
      return Intrinsics.areEqual(paramFile1, paramFile2);
    }
    int i = localFilePathComponents1.getSize() - localFilePathComponents2.getSize();
    boolean bool;
    if (i < 0) {
      bool = false;
    } else {
      bool = localFilePathComponents1.getSegments().subList(i, localFilePathComponents1.getSize()).equals(localFilePathComponents2.getSegments());
    }
    return bool;
  }
  
  public static final boolean endsWith(File paramFile, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "other");
    return FilesKt.endsWith(paramFile, new File(paramString));
  }
  
  public static final String getExtension(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    paramFile = paramFile.getName();
    Intrinsics.checkNotNullExpressionValue(paramFile, "name");
    paramFile = StringsKt.substringAfterLast(paramFile, '.', "");
    Log5ECF72.a(paramFile);
    LogE84000.a(paramFile);
    Log229316.a(paramFile);
    return paramFile;
  }
  
  public static final String getInvariantSeparatorsPath(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    if (File.separatorChar != '/')
    {
      paramFile = paramFile.getPath();
      Intrinsics.checkNotNullExpressionValue(paramFile, "path");
      paramFile = StringsKt.replace$default(paramFile, File.separatorChar, '/', false, 4, null);
      Log5ECF72.a(paramFile);
      LogE84000.a(paramFile);
      Log229316.a(paramFile);
    }
    else
    {
      paramFile = paramFile.getPath();
      Intrinsics.checkNotNullExpressionValue(paramFile, "path");
    }
    return paramFile;
  }
  
  public static final String getNameWithoutExtension(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    paramFile = paramFile.getName();
    Intrinsics.checkNotNullExpressionValue(paramFile, "name");
    paramFile = StringsKt.substringBeforeLast$default(paramFile, ".", null, 2, null);
    Log5ECF72.a(paramFile);
    LogE84000.a(paramFile);
    Log229316.a(paramFile);
    return paramFile;
  }
  
  public static final File normalize(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Object localObject = FilesKt.toComponents(paramFile);
    paramFile = ((FilePathComponents)localObject).getRoot();
    localObject = (Iterable)normalize$FilesKt__UtilsKt(((FilePathComponents)localObject).getSegments());
    String str = File.separator;
    Intrinsics.checkNotNullExpressionValue(str, "separator");
    localObject = CollectionsKt.joinToString$default((Iterable)localObject, (CharSequence)str, null, null, 0, null, null, 62, null);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return FilesKt.resolve(paramFile, (String)localObject);
  }
  
  private static final List<File> normalize$FilesKt__UtilsKt(List<? extends File> paramList)
  {
    List localList = (List)new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (File)localIterator.next();
      String str = paramList.getName();
      if (!Intrinsics.areEqual(str, ".")) {
        if (Intrinsics.areEqual(str, ".."))
        {
          if ((!localList.isEmpty()) && (!Intrinsics.areEqual(((File)CollectionsKt.last(localList)).getName(), ".."))) {
            localList.remove(localList.size() - 1);
          } else {
            localList.add(paramList);
          }
        }
        else {
          localList.add(paramList);
        }
      }
    }
    return localList;
  }
  
  private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents paramFilePathComponents)
  {
    return new FilePathComponents(paramFilePathComponents.getRoot(), normalize$FilesKt__UtilsKt(paramFilePathComponents.getSegments()));
  }
  
  public static final File relativeTo(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "base");
    paramFile1 = FilesKt.toRelativeString(paramFile1, paramFile2);
    Log5ECF72.a(paramFile1);
    LogE84000.a(paramFile1);
    Log229316.a(paramFile1);
    return new File(paramFile1);
  }
  
  public static final File relativeToOrNull(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "base");
    paramFile1 = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    Log5ECF72.a(paramFile1);
    LogE84000.a(paramFile1);
    Log229316.a(paramFile1);
    if (paramFile1 != null) {
      paramFile1 = new File(paramFile1);
    } else {
      paramFile1 = null;
    }
    return paramFile1;
  }
  
  public static final File relativeToOrSelf(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "base");
    paramFile2 = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    Log5ECF72.a(paramFile2);
    LogE84000.a(paramFile2);
    Log229316.a(paramFile2);
    if (paramFile2 != null) {
      paramFile1 = new File(paramFile2);
    }
    return paramFile1;
  }
  
  public static final File resolve(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "relative");
    if (FilesKt.isRooted(paramFile2)) {
      return paramFile2;
    }
    paramFile1 = paramFile1.toString();
    Intrinsics.checkNotNullExpressionValue(paramFile1, "this.toString()");
    int i;
    if (((CharSequence)paramFile1).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i == 0) && (!StringsKt.endsWith$default((CharSequence)paramFile1, File.separatorChar, false, 2, null))) {
      paramFile1 = new File(paramFile1 + File.separatorChar + paramFile2);
    } else {
      paramFile1 = new File(paramFile1 + paramFile2);
    }
    return paramFile1;
  }
  
  public static final File resolve(File paramFile, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "relative");
    return FilesKt.resolve(paramFile, new File(paramString));
  }
  
  public static final File resolveSibling(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "relative");
    FilePathComponents localFilePathComponents = FilesKt.toComponents(paramFile1);
    if (localFilePathComponents.getSize() == 0) {
      paramFile1 = new File("..");
    } else {
      paramFile1 = localFilePathComponents.subPath(0, localFilePathComponents.getSize() - 1);
    }
    return FilesKt.resolve(FilesKt.resolve(localFilePathComponents.getRoot(), paramFile1), paramFile2);
  }
  
  public static final File resolveSibling(File paramFile, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "relative");
    return FilesKt.resolveSibling(paramFile, new File(paramString));
  }
  
  public static final boolean startsWith(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "other");
    paramFile1 = FilesKt.toComponents(paramFile1);
    paramFile2 = FilesKt.toComponents(paramFile2);
    boolean bool2 = Intrinsics.areEqual(paramFile1.getRoot(), paramFile2.getRoot());
    boolean bool1 = false;
    if (!bool2) {
      return false;
    }
    if (paramFile1.getSize() >= paramFile2.getSize()) {
      bool1 = paramFile1.getSegments().subList(0, paramFile2.getSize()).equals(paramFile2.getSegments());
    }
    return bool1;
  }
  
  public static final boolean startsWith(File paramFile, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "other");
    return FilesKt.startsWith(paramFile, new File(paramString));
  }
  
  public static final String toRelativeString(File paramFile1, File paramFile2)
  {
    Intrinsics.checkNotNullParameter(paramFile1, "<this>");
    Intrinsics.checkNotNullParameter(paramFile2, "base");
    String str = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    if (str != null) {
      return str;
    }
    throw new IllegalArgumentException("this and base files have different roots: " + paramFile1 + " and " + paramFile2 + '.');
  }
  
  private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File paramFile1, File paramFile2)
  {
    Object localObject = normalize$FilesKt__UtilsKt(FilesKt.toComponents(paramFile1));
    paramFile2 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(paramFile2));
    if (!Intrinsics.areEqual(((FilePathComponents)localObject).getRoot(), paramFile2.getRoot())) {
      return null;
    }
    int m = paramFile2.getSize();
    int k = ((FilePathComponents)localObject).getSize();
    int i = 0;
    int j = Math.min(k, m);
    while ((i < j) && (Intrinsics.areEqual(((FilePathComponents)localObject).getSegments().get(i), paramFile2.getSegments().get(i)))) {
      i++;
    }
    paramFile1 = new StringBuilder();
    j = m - 1;
    if (i <= j) {
      for (;;)
      {
        if (Intrinsics.areEqual(((File)paramFile2.getSegments().get(j)).getName(), "..")) {
          return null;
        }
        paramFile1.append("..");
        if (j != i) {
          paramFile1.append(File.separatorChar);
        }
        if (j == i) {
          break;
        }
        j--;
      }
    }
    if (i < k)
    {
      if (i < m) {
        paramFile1.append(File.separatorChar);
      }
      Iterable localIterable = (Iterable)CollectionsKt.drop((Iterable)((FilePathComponents)localObject).getSegments(), i);
      paramFile2 = (Appendable)paramFile1;
      localObject = File.separator;
      Intrinsics.checkNotNullExpressionValue(localObject, "separator");
      CollectionsKt.joinTo$default(localIterable, paramFile2, (CharSequence)localObject, null, null, 0, null, null, 124, null);
    }
    return paramFile1.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/FilesKt__UtilsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */