package androidx.emoji2.text;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;
import androidx.core.os.TraceCompat;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class MetadataRepo
{
  private static final int DEFAULT_ROOT_SIZE = 1024;
  private static final String S_TRACE_CREATE_REPO = "EmojiCompat.MetadataRepo.create";
  private final char[] mEmojiCharArray;
  private final MetadataList mMetadataList;
  private final Node mRootNode;
  private final Typeface mTypeface;
  
  private MetadataRepo(Typeface paramTypeface, MetadataList paramMetadataList)
  {
    this.mTypeface = paramTypeface;
    this.mMetadataList = paramMetadataList;
    this.mRootNode = new Node(1024);
    this.mEmojiCharArray = new char[paramMetadataList.listLength() * 2];
    constructIndex(paramMetadataList);
  }
  
  private void constructIndex(MetadataList paramMetadataList)
  {
    int j = paramMetadataList.listLength();
    for (int i = 0; i < j; i++)
    {
      paramMetadataList = new EmojiMetadata(this, i);
      Character.toChars(paramMetadataList.getId(), this.mEmojiCharArray, i * 2);
      put(paramMetadataList);
    }
  }
  
  public static MetadataRepo create(AssetManager paramAssetManager, String paramString)
    throws IOException
  {
    try
    {
      TraceCompat.beginSection("EmojiCompat.MetadataRepo.create");
      paramAssetManager = new MetadataRepo(Typeface.createFromAsset(paramAssetManager, paramString), MetadataListReader.read(paramAssetManager, paramString));
      return paramAssetManager;
    }
    finally
    {
      TraceCompat.endSection();
    }
  }
  
  public static MetadataRepo create(Typeface paramTypeface)
  {
    try
    {
      TraceCompat.beginSection("EmojiCompat.MetadataRepo.create");
      MetadataList localMetadataList = new androidx/emoji2/text/flatbuffer/MetadataList;
      localMetadataList.<init>();
      paramTypeface = new MetadataRepo(paramTypeface, localMetadataList);
      return paramTypeface;
    }
    finally
    {
      TraceCompat.endSection();
    }
  }
  
  public static MetadataRepo create(Typeface paramTypeface, InputStream paramInputStream)
    throws IOException
  {
    try
    {
      TraceCompat.beginSection("EmojiCompat.MetadataRepo.create");
      paramTypeface = new MetadataRepo(paramTypeface, MetadataListReader.read(paramInputStream));
      return paramTypeface;
    }
    finally
    {
      TraceCompat.endSection();
    }
  }
  
  public static MetadataRepo create(Typeface paramTypeface, ByteBuffer paramByteBuffer)
    throws IOException
  {
    try
    {
      TraceCompat.beginSection("EmojiCompat.MetadataRepo.create");
      paramTypeface = new MetadataRepo(paramTypeface, MetadataListReader.read(paramByteBuffer));
      return paramTypeface;
    }
    finally
    {
      TraceCompat.endSection();
    }
  }
  
  public char[] getEmojiCharArray()
  {
    return this.mEmojiCharArray;
  }
  
  public MetadataList getMetadataList()
  {
    return this.mMetadataList;
  }
  
  int getMetadataVersion()
  {
    return this.mMetadataList.version();
  }
  
  Node getRootNode()
  {
    return this.mRootNode;
  }
  
  Typeface getTypeface()
  {
    return this.mTypeface;
  }
  
  void put(EmojiMetadata paramEmojiMetadata)
  {
    Preconditions.checkNotNull(paramEmojiMetadata, "emoji metadata cannot be null");
    boolean bool;
    if (paramEmojiMetadata.getCodepointsLength() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool, "invalid metadata codepoint length");
    this.mRootNode.put(paramEmojiMetadata, 0, paramEmojiMetadata.getCodepointsLength() - 1);
  }
  
  static class Node
  {
    private final SparseArray<Node> mChildren;
    private EmojiMetadata mData;
    
    private Node()
    {
      this(1);
    }
    
    Node(int paramInt)
    {
      this.mChildren = new SparseArray(paramInt);
    }
    
    Node get(int paramInt)
    {
      Object localObject = this.mChildren;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = (Node)((SparseArray)localObject).get(paramInt);
      }
      return (Node)localObject;
    }
    
    final EmojiMetadata getData()
    {
      return this.mData;
    }
    
    void put(EmojiMetadata paramEmojiMetadata, int paramInt1, int paramInt2)
    {
      Node localNode2 = get(paramEmojiMetadata.getCodepointAt(paramInt1));
      Node localNode1 = localNode2;
      if (localNode2 == null)
      {
        localNode1 = new Node();
        this.mChildren.put(paramEmojiMetadata.getCodepointAt(paramInt1), localNode1);
      }
      if (paramInt2 > paramInt1) {
        localNode1.put(paramEmojiMetadata, paramInt1 + 1, paramInt2);
      } else {
        localNode1.mData = paramEmojiMetadata;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/MetadataRepo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */