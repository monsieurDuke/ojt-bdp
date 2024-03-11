package kotlin.io;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0000\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020 \n\002\b\002\n\002\020\013\n\002\b\004\n\002\020\016\n\002\b\005\n\002\020\b\n\002\b\r\b\b\030\0002\0020\001B\035\b\000\022\006\020\002\032\0020\003\022\f\020\004\032\b\022\004\022\0020\0030\005¢\006\002\020\006J\t\020\026\032\0020\003HÆ\003J\017\020\027\032\b\022\004\022\0020\0030\005HÆ\003J#\020\030\032\0020\0002\b\b\002\020\002\032\0020\0032\016\b\002\020\004\032\b\022\004\022\0020\0030\005HÆ\001J\023\020\031\032\0020\b2\b\020\032\032\004\030\0010\001HÖ\003J\t\020\033\032\0020\023HÖ\001J\026\020\034\032\0020\0032\006\020\035\032\0020\0232\006\020\036\032\0020\023J\t\020\037\032\0020\rHÖ\001R\021\020\007\032\0020\b8F¢\006\006\032\004\b\007\020\tR\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\n\020\013R\021\020\f\032\0020\r8F¢\006\006\032\004\b\016\020\017R\027\020\004\032\b\022\004\022\0020\0030\005¢\006\b\n\000\032\004\b\020\020\021R\021\020\022\032\0020\0238F¢\006\006\032\004\b\024\020\025¨\006 "}, d2={"Lkotlin/io/FilePathComponents;", "", "root", "Ljava/io/File;", "segments", "", "(Ljava/io/File;Ljava/util/List;)V", "isRooted", "", "()Z", "getRoot", "()Ljava/io/File;", "rootName", "", "getRootName", "()Ljava/lang/String;", "getSegments", "()Ljava/util/List;", "size", "", "getSize", "()I", "component1", "component2", "copy", "equals", "other", "hashCode", "subPath", "beginIndex", "endIndex", "toString", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class FilePathComponents
{
  private final File root;
  private final List<File> segments;
  
  public FilePathComponents(File paramFile, List<? extends File> paramList)
  {
    this.root = paramFile;
    this.segments = paramList;
  }
  
  public final File component1()
  {
    return this.root;
  }
  
  public final List<File> component2()
  {
    return this.segments;
  }
  
  public final FilePathComponents copy(File paramFile, List<? extends File> paramList)
  {
    Intrinsics.checkNotNullParameter(paramFile, "root");
    Intrinsics.checkNotNullParameter(paramList, "segments");
    return new FilePathComponents(paramFile, paramList);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof FilePathComponents)) {
      return false;
    }
    paramObject = (FilePathComponents)paramObject;
    if (!Intrinsics.areEqual(this.root, ((FilePathComponents)paramObject).root)) {
      return false;
    }
    return Intrinsics.areEqual(this.segments, ((FilePathComponents)paramObject).segments);
  }
  
  public final File getRoot()
  {
    return this.root;
  }
  
  public final String getRootName()
  {
    String str = this.root.getPath();
    Intrinsics.checkNotNullExpressionValue(str, "root.path");
    return str;
  }
  
  public final List<File> getSegments()
  {
    return this.segments;
  }
  
  public final int getSize()
  {
    return this.segments.size();
  }
  
  public int hashCode()
  {
    return this.root.hashCode() * 31 + this.segments.hashCode();
  }
  
  public final boolean isRooted()
  {
    String str = this.root.getPath();
    Intrinsics.checkNotNullExpressionValue(str, "root.path");
    boolean bool;
    if (((CharSequence)str).length() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final File subPath(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt1 <= paramInt2) && (paramInt2 <= getSize()))
    {
      Object localObject = (Iterable)this.segments.subList(paramInt1, paramInt2);
      String str = File.separator;
      Intrinsics.checkNotNullExpressionValue(str, "separator");
      localObject = CollectionsKt.joinToString$default((Iterable)localObject, (CharSequence)str, null, null, 0, null, null, 62, null);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      return new File((String)localObject);
    }
    throw new IllegalArgumentException();
  }
  
  public String toString()
  {
    return "FilePathComponents(root=" + this.root + ", segments=" + this.segments + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/FilePathComponents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */