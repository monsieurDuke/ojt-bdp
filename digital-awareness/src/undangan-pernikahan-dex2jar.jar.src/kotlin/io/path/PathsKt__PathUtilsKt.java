package kotlin.io.path;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000²\001\n\000\n\002\020\016\n\002\030\002\n\002\b\027\n\002\020\021\n\002\b\005\n\002\030\002\n\002\b\007\n\002\020\001\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\b\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\t\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\"\n\002\030\002\n\002\b\n\n\002\020 \n\002\b\004\n\002\030\002\n\000\n\002\020$\n\002\b\f\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\032\021\020\026\032\0020\0022\006\020\027\032\0020\001H\b\032*\020\026\032\0020\0022\006\020\030\032\0020\0012\022\020\031\032\n\022\006\b\001\022\0020\0010\032\"\0020\001H\b¢\006\002\020\033\032?\020\034\032\0020\0022\b\020\035\032\004\030\0010\0022\n\b\002\020\036\032\004\030\0010\0012\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\007¢\006\002\020!\0326\020\034\032\0020\0022\n\b\002\020\036\032\004\030\0010\0012\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\020\"\032K\020#\032\0020\0022\b\020\035\032\004\030\0010\0022\n\b\002\020\036\032\004\030\0010\0012\n\b\002\020$\032\004\030\0010\0012\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\007¢\006\002\020%\032B\020#\032\0020\0022\n\b\002\020\036\032\004\030\0010\0012\n\b\002\020$\032\004\030\0010\0012\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\020&\032\034\020'\032\0020(2\006\020\027\032\0020\0022\n\020)\032\006\022\002\b\0030*H\001\032\r\020+\032\0020\002*\0020\002H\b\032\r\020,\032\0020\001*\0020\002H\b\032.\020-\032\0020\002*\0020\0022\006\020.\032\0020\0022\022\020/\032\n\022\006\b\001\022\002000\032\"\00200H\b¢\006\002\0201\032\037\020-\032\0020\002*\0020\0022\006\020.\032\0020\0022\b\b\002\0202\032\00203H\b\032.\0204\032\0020\002*\0020\0022\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\0205\032.\0206\032\0020\002*\0020\0022\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\0205\032.\0207\032\0020\002*\0020\0022\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\0205\032\025\0208\032\0020\002*\0020\0022\006\020.\032\0020\002H\b\0326\0209\032\0020\002*\0020\0022\006\020.\032\0020\0022\032\020\037\032\016\022\n\b\001\022\006\022\002\b\0030 0\032\"\006\022\002\b\0030 H\b¢\006\002\020:\032\r\020;\032\0020<*\0020\002H\b\032\r\020=\032\00203*\0020\002H\b\032\025\020>\032\0020\002*\0020\0022\006\020?\032\0020\002H\n\032\025\020>\032\0020\002*\0020\0022\006\020?\032\0020\001H\n\032&\020@\032\00203*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020B\0322\020C\032\002HD\"\n\b\000\020D\030\001*\0020E*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020F\0324\020G\032\004\030\001HD\"\n\b\000\020D\030\001*\0020E*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020F\032\r\020H\032\0020I*\0020\002H\b\032\r\020J\032\0020K*\0020\002H\b\032.\020L\032\0020<*\0020\0022\b\b\002\020M\032\0020\0012\022\020N\032\016\022\004\022\0020\002\022\004\022\0020<0OH\bø\001\000\0320\020P\032\004\030\0010Q*\0020\0022\006\020R\032\0020\0012\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020S\032&\020T\032\0020U*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020V\032(\020W\032\004\030\0010X*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020Y\032,\020Z\032\b\022\004\022\0020\\0[*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020]\032&\020^\032\00203*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020B\032\r\020_\032\00203*\0020\002H\b\032\r\020`\032\00203*\0020\002H\b\032\r\020a\032\00203*\0020\002H\b\032&\020b\032\00203*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020B\032\025\020c\032\00203*\0020\0022\006\020?\032\0020\002H\b\032\r\020d\032\00203*\0020\002H\b\032\r\020e\032\00203*\0020\002H\b\032\034\020f\032\b\022\004\022\0020\0020g*\0020\0022\b\b\002\020M\032\0020\001H\007\032.\020h\032\0020\002*\0020\0022\006\020.\032\0020\0022\022\020/\032\n\022\006\b\001\022\002000\032\"\00200H\b¢\006\002\0201\032\037\020h\032\0020\002*\0020\0022\006\020.\032\0020\0022\b\b\002\0202\032\00203H\b\032&\020i\032\00203*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020B\0322\020j\032\002Hk\"\n\b\000\020k\030\001*\0020l*\0020\0022\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020m\032<\020j\032\020\022\004\022\0020\001\022\006\022\004\030\0010Q0n*\0020\0022\006\020\037\032\0020\0012\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020o\032\r\020p\032\0020\002*\0020\002H\b\032\024\020q\032\0020\002*\0020\0022\006\020\030\032\0020\002H\007\032\026\020r\032\004\030\0010\002*\0020\0022\006\020\030\032\0020\002H\007\032\024\020s\032\0020\002*\0020\0022\006\020\030\032\0020\002H\007\0328\020t\032\0020\002*\0020\0022\006\020R\032\0020\0012\b\020u\032\004\030\0010Q2\022\020/\032\n\022\006\b\001\022\0020A0\032\"\0020AH\b¢\006\002\020v\032\025\020w\032\0020\002*\0020\0022\006\020u\032\0020UH\b\032\025\020x\032\0020\002*\0020\0022\006\020u\032\0020XH\b\032\033\020y\032\0020\002*\0020\0022\f\020u\032\b\022\004\022\0020\\0[H\b\032\r\020z\032\0020\002*\0020{H\b\032@\020|\032\002H}\"\004\b\000\020}*\0020\0022\b\b\002\020M\032\0020\0012\030\020~\032\024\022\n\022\b\022\004\022\0020\0020\022\004\022\002H}0OH\bø\001\000¢\006\003\020\001\"\036\020\000\032\0020\001*\0020\0028FX\004¢\006\f\022\004\b\003\020\004\032\004\b\005\020\006\"\037\020\007\032\0020\001*\0020\0028Æ\002X\004¢\006\f\022\004\b\b\020\004\032\004\b\t\020\006\"\036\020\n\032\0020\001*\0020\0028FX\004¢\006\f\022\004\b\013\020\004\032\004\b\f\020\006\"\036\020\r\032\0020\001*\0020\0028FX\004¢\006\f\022\004\b\016\020\004\032\004\b\017\020\006\"\036\020\020\032\0020\001*\0020\0028FX\004¢\006\f\022\004\b\021\020\004\032\004\b\022\020\006\"\037\020\023\032\0020\001*\0020\0028Æ\002X\004¢\006\f\022\004\b\024\020\004\032\004\b\025\020\006\002\007\n\005\b20\001¨\006\001"}, d2={"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "Lkotlin/Function1;", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, k=5, mv={1, 6, 0}, xi=49, xs="kotlin/io/path/PathsKt")
class PathsKt__PathUtilsKt
  extends PathsKt__PathReadWriteKt
{
  private static final Path Path(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "path");
    paramString = Paths.get(paramString, new String[0]);
    Intrinsics.checkNotNullExpressionValue(paramString, "get(path)");
    return paramString;
  }
  
  private static final Path Path(String paramString, String... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramString, "base");
    Intrinsics.checkNotNullParameter(paramVarArgs, "subpaths");
    paramString = Paths.get(paramString, (String[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramString, "get(base, *subpaths)");
    return paramString;
  }
  
  private static final Path absolute(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = paramPath.toAbsolutePath();
    Intrinsics.checkNotNullExpressionValue(paramPath, "toAbsolutePath()");
    return paramPath;
  }
  
  private static final String absolutePathString(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return paramPath.toAbsolutePath().toString();
  }
  
  private static final Path copyTo(Path paramPath1, Path paramPath2, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    CopyOption[] arrayOfCopyOption;
    if (paramBoolean)
    {
      arrayOfCopyOption = new CopyOption[1];
      arrayOfCopyOption[0] = ((CopyOption)StandardCopyOption.REPLACE_EXISTING);
    }
    else
    {
      arrayOfCopyOption = new CopyOption[0];
    }
    paramPath1 = Files.copy(paramPath1, paramPath2, (CopyOption[])Arrays.copyOf(arrayOfCopyOption, arrayOfCopyOption.length));
    Intrinsics.checkNotNullExpressionValue(paramPath1, "copy(this, target, *options)");
    return paramPath1;
  }
  
  private static final Path copyTo(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath1 = Files.copy(paramPath1, paramPath2, (CopyOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath1, "copy(this, target, *options)");
    return paramPath1;
  }
  
  private static final Path createDirectories(Path paramPath, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramPath = Files.createDirectories(paramPath, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "createDirectories(this, *attributes)");
    return paramPath;
  }
  
  private static final Path createDirectory(Path paramPath, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramPath = Files.createDirectory(paramPath, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "createDirectory(this, *attributes)");
    return paramPath;
  }
  
  private static final Path createFile(Path paramPath, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramPath = Files.createFile(paramPath, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "createFile(this, *attributes)");
    return paramPath;
  }
  
  private static final Path createLinkPointingTo(Path paramPath1, Path paramPath2)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    paramPath1 = Files.createLink(paramPath1, paramPath2);
    Intrinsics.checkNotNullExpressionValue(paramPath1, "createLink(this, target)");
    return paramPath1;
  }
  
  private static final Path createSymbolicLinkPointingTo(Path paramPath1, Path paramPath2, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramPath1 = Files.createSymbolicLink(paramPath1, paramPath2, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath1, "createSymbolicLink(this, target, *attributes)");
    return paramPath1;
  }
  
  private static final Path createTempDirectory(String paramString, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramString = Files.createTempDirectory(paramString, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramString, "createTempDirectory(prefix, *attributes)");
    return paramString;
  }
  
  public static final Path createTempDirectory(Path paramPath, String paramString, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    if (paramPath != null)
    {
      paramPath = Files.createTempDirectory(paramPath, paramString, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
      Intrinsics.checkNotNullExpressionValue(paramPath, "createTempDirectory(dire…ory, prefix, *attributes)");
    }
    else
    {
      paramPath = Files.createTempDirectory(paramString, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
      Intrinsics.checkNotNullExpressionValue(paramPath, "createTempDirectory(prefix, *attributes)");
    }
    return paramPath;
  }
  
  private static final Path createTempFile(String paramString1, String paramString2, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    paramString1 = Files.createTempFile(paramString1, paramString2, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramString1, "createTempFile(prefix, suffix, *attributes)");
    return paramString1;
  }
  
  public static final Path createTempFile(Path paramPath, String paramString1, String paramString2, FileAttribute<?>... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "attributes");
    if (paramPath != null)
    {
      paramPath = Files.createTempFile(paramPath, paramString1, paramString2, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
      Intrinsics.checkNotNullExpressionValue(paramPath, "createTempFile(directory…fix, suffix, *attributes)");
    }
    else
    {
      paramPath = Files.createTempFile(paramString1, paramString2, (FileAttribute[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
      Intrinsics.checkNotNullExpressionValue(paramPath, "createTempFile(prefix, suffix, *attributes)");
    }
    return paramPath;
  }
  
  private static final void deleteExisting(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Files.delete(paramPath);
  }
  
  private static final boolean deleteIfExists(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.deleteIfExists(paramPath);
  }
  
  private static final Path div(Path paramPath, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "other");
    paramPath = paramPath.resolve(paramString);
    Intrinsics.checkNotNullExpressionValue(paramPath, "this.resolve(other)");
    return paramPath;
  }
  
  private static final Path div(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "other");
    paramPath1 = paramPath1.resolve(paramPath2);
    Intrinsics.checkNotNullExpressionValue(paramPath1, "this.resolve(other)");
    return paramPath1;
  }
  
  private static final boolean exists(Path paramPath, LinkOption... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.exists(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public static final Void fileAttributeViewNotAvailable(Path paramPath, Class<?> paramClass)
  {
    Intrinsics.checkNotNullParameter(paramPath, "path");
    Intrinsics.checkNotNullParameter(paramClass, "attributeViewClass");
    throw new UnsupportedOperationException("The desired attribute view type " + paramClass + " is not available for the file " + paramPath + '.');
  }
  
  private static final long fileSize(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.size(paramPath);
  }
  
  private static final FileStore fileStore(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = Files.getFileStore(paramPath);
    Intrinsics.checkNotNullExpressionValue(paramPath, "getFileStore(this)");
    return paramPath;
  }
  
  private static final void forEachDirectoryEntry(Path paramPath, String paramString, Function1<? super Path, Unit> paramFunction1)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "glob");
    Intrinsics.checkNotNullParameter(paramFunction1, "action");
    paramPath = (Closeable)Files.newDirectoryStream(paramPath, paramString);
    try
    {
      paramString = (DirectoryStream)paramPath;
      Intrinsics.checkNotNullExpressionValue(paramString, "it");
      paramString = ((Iterable)paramString).iterator();
      while (paramString.hasNext()) {
        paramFunction1.invoke(paramString.next());
      }
      paramString = Unit.INSTANCE;
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramPath, null);
      InlineMarker.finallyEnd(1);
      return;
    }
    finally
    {
      try
      {
        throw paramString;
      }
      finally
      {
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(paramPath, paramString);
        InlineMarker.finallyEnd(1);
      }
    }
  }
  
  private static final Object getAttribute(Path paramPath, String paramString, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "attribute");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.getAttribute(paramPath, paramString, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  public static final String getExtension(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Path localPath = paramPath.getFileName();
    paramPath = "";
    if (localPath != null)
    {
      paramPath = StringsKt.substringAfterLast(localPath.toString(), '.', "");
      Log5ECF72.a(paramPath);
      LogE84000.a(paramPath);
      Log229316.a(paramPath);
    }
    return paramPath;
  }
  
  private static final String getInvariantSeparatorsPath(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = PathsKt.getInvariantSeparatorsPathString(paramPath);
    Log5ECF72.a(paramPath);
    LogE84000.a(paramPath);
    Log229316.a(paramPath);
    return paramPath;
  }
  
  public static final String getInvariantSeparatorsPathString(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    String str = paramPath.getFileSystem().getSeparator();
    if (!Intrinsics.areEqual(str, "/"))
    {
      paramPath = paramPath.toString();
      Intrinsics.checkNotNullExpressionValue(str, "separator");
      paramPath = StringsKt.replace$default(paramPath, str, "/", false, 4, null);
      Log5ECF72.a(paramPath);
      LogE84000.a(paramPath);
      Log229316.a(paramPath);
    }
    else
    {
      paramPath = paramPath.toString();
    }
    return paramPath;
  }
  
  private static final FileTime getLastModifiedTime(Path paramPath, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.getLastModifiedTime(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "getLastModifiedTime(this, *options)");
    return paramPath;
  }
  
  public static final String getName(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = paramPath.getFileName();
    if (paramPath == null) {
      paramPath = null;
    } else {
      paramPath = paramPath.toString();
    }
    Object localObject = paramPath;
    if (paramPath == null) {
      localObject = "";
    }
    return (String)localObject;
  }
  
  public static final String getNameWithoutExtension(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = paramPath.getFileName();
    if (paramPath == null)
    {
      paramPath = "";
    }
    else
    {
      paramPath = StringsKt.substringBeforeLast$default(paramPath.toString(), ".", null, 2, null);
      Log5ECF72.a(paramPath);
      LogE84000.a(paramPath);
      Log229316.a(paramPath);
    }
    return paramPath;
  }
  
  private static final UserPrincipal getOwner(Path paramPath, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.getOwner(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  private static final String getPathString(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return paramPath.toString();
  }
  
  private static final Set<PosixFilePermission> getPosixFilePermissions(Path paramPath, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.getPosixFilePermissions(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "getPosixFilePermissions(this, *options)");
    return paramPath;
  }
  
  private static final boolean isDirectory(Path paramPath, LinkOption... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.isDirectory(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  private static final boolean isExecutable(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.isExecutable(paramPath);
  }
  
  private static final boolean isHidden(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.isHidden(paramPath);
  }
  
  private static final boolean isReadable(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.isReadable(paramPath);
  }
  
  private static final boolean isRegularFile(Path paramPath, LinkOption... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.isRegularFile(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  private static final boolean isSameFileAs(Path paramPath1, Path paramPath2)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "other");
    return Files.isSameFile(paramPath1, paramPath2);
  }
  
  private static final boolean isSymbolicLink(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.isSymbolicLink(paramPath);
  }
  
  private static final boolean isWritable(Path paramPath)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    return Files.isWritable(paramPath);
  }
  
  public static final List<Path> listDirectoryEntries(Path paramPath, String paramString)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "glob");
    paramPath = (Closeable)Files.newDirectoryStream(paramPath, paramString);
    try
    {
      paramString = (DirectoryStream)paramPath;
      Intrinsics.checkNotNullExpressionValue(paramString, "it");
      paramString = CollectionsKt.toList((Iterable)paramString);
      CloseableKt.closeFinally(paramPath, null);
      return paramString;
    }
    finally
    {
      try
      {
        throw localThrowable;
      }
      finally
      {
        CloseableKt.closeFinally(paramPath, localThrowable);
      }
    }
  }
  
  private static final Path moveTo(Path paramPath1, Path paramPath2, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    CopyOption[] arrayOfCopyOption;
    if (paramBoolean)
    {
      arrayOfCopyOption = new CopyOption[1];
      arrayOfCopyOption[0] = ((CopyOption)StandardCopyOption.REPLACE_EXISTING);
    }
    else
    {
      arrayOfCopyOption = new CopyOption[0];
    }
    paramPath1 = Files.move(paramPath1, paramPath2, (CopyOption[])Arrays.copyOf(arrayOfCopyOption, arrayOfCopyOption.length));
    Intrinsics.checkNotNullExpressionValue(paramPath1, "move(this, target, *options)");
    return paramPath1;
  }
  
  private static final Path moveTo(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "target");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath1 = Files.move(paramPath1, paramPath2, (CopyOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath1, "move(this, target, *options)");
    return paramPath1;
  }
  
  private static final boolean notExists(Path paramPath, LinkOption... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    return Files.notExists(paramPath, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
  }
  
  private static final Map<String, Object> readAttributes(Path paramPath, String paramString, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "attributes");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.readAttributes(paramPath, paramString, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "readAttributes(this, attributes, *options)");
    return paramPath;
  }
  
  private static final Path readSymbolicLink(Path paramPath)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    paramPath = Files.readSymbolicLink(paramPath);
    Intrinsics.checkNotNullExpressionValue(paramPath, "readSymbolicLink(this)");
    return paramPath;
  }
  
  public static final Path relativeTo(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "base");
    try
    {
      Path localPath = PathRelativizer.INSTANCE.tryRelativeTo(paramPath1, paramPath2);
      return localPath;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new IllegalArgumentException(localIllegalArgumentException.getMessage() + "\nthis path: " + paramPath1 + "\nbase path: " + paramPath2, (Throwable)localIllegalArgumentException);
    }
  }
  
  public static final Path relativeToOrNull(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "base");
    try
    {
      paramPath1 = PathRelativizer.INSTANCE.tryRelativeTo(paramPath1, paramPath2);
    }
    catch (IllegalArgumentException paramPath1)
    {
      paramPath1 = (Path)null;
      paramPath1 = null;
    }
    return paramPath1;
  }
  
  public static final Path relativeToOrSelf(Path paramPath1, Path paramPath2)
  {
    Intrinsics.checkNotNullParameter(paramPath1, "<this>");
    Intrinsics.checkNotNullParameter(paramPath2, "base");
    Path localPath = PathsKt.relativeToOrNull(paramPath1, paramPath2);
    paramPath2 = localPath;
    if (localPath == null) {
      paramPath2 = paramPath1;
    }
    return paramPath2;
  }
  
  private static final Path setAttribute(Path paramPath, String paramString, Object paramObject, LinkOption... paramVarArgs)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "attribute");
    Intrinsics.checkNotNullParameter(paramVarArgs, "options");
    paramPath = Files.setAttribute(paramPath, paramString, paramObject, (LinkOption[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    Intrinsics.checkNotNullExpressionValue(paramPath, "setAttribute(this, attribute, value, *options)");
    return paramPath;
  }
  
  private static final Path setLastModifiedTime(Path paramPath, FileTime paramFileTime)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramFileTime, "value");
    paramPath = Files.setLastModifiedTime(paramPath, paramFileTime);
    Intrinsics.checkNotNullExpressionValue(paramPath, "setLastModifiedTime(this, value)");
    return paramPath;
  }
  
  private static final Path setOwner(Path paramPath, UserPrincipal paramUserPrincipal)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramUserPrincipal, "value");
    paramPath = Files.setOwner(paramPath, paramUserPrincipal);
    Intrinsics.checkNotNullExpressionValue(paramPath, "setOwner(this, value)");
    return paramPath;
  }
  
  private static final Path setPosixFilePermissions(Path paramPath, Set<? extends PosixFilePermission> paramSet)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramSet, "value");
    paramPath = Files.setPosixFilePermissions(paramPath, paramSet);
    Intrinsics.checkNotNullExpressionValue(paramPath, "setPosixFilePermissions(this, value)");
    return paramPath;
  }
  
  private static final Path toPath(URI paramURI)
  {
    Intrinsics.checkNotNullParameter(paramURI, "<this>");
    paramURI = Paths.get(paramURI);
    Intrinsics.checkNotNullExpressionValue(paramURI, "get(this)");
    return paramURI;
  }
  
  private static final <T> T useDirectoryEntries(Path paramPath, String paramString, Function1<? super Sequence<? extends Path>, ? extends T> paramFunction1)
    throws IOException
  {
    Intrinsics.checkNotNullParameter(paramPath, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "glob");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramPath = (Closeable)Files.newDirectoryStream(paramPath, paramString);
    try
    {
      paramString = (DirectoryStream)paramPath;
      Intrinsics.checkNotNullExpressionValue(paramString, "it");
      paramString = paramFunction1.invoke(CollectionsKt.asSequence((Iterable)paramString));
      InlineMarker.finallyStart(1);
      CloseableKt.closeFinally(paramPath, null);
      InlineMarker.finallyEnd(1);
      return paramString;
    }
    finally
    {
      try
      {
        throw paramString;
      }
      finally
      {
        InlineMarker.finallyStart(1);
        CloseableKt.closeFinally(paramPath, paramString);
        InlineMarker.finallyEnd(1);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/path/PathsKt__PathUtilsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */