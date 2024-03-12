package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.emoji2.text.flatbuffer.MetadataItem;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class EmojiMetadata
{
  public static final int HAS_GLYPH_ABSENT = 1;
  public static final int HAS_GLYPH_EXISTS = 2;
  public static final int HAS_GLYPH_UNKNOWN = 0;
  private static final ThreadLocal<MetadataItem> sMetadataItem = new ThreadLocal();
  private volatile int mHasGlyph = 0;
  private final int mIndex;
  private final MetadataRepo mMetadataRepo;
  
  EmojiMetadata(MetadataRepo paramMetadataRepo, int paramInt)
  {
    this.mMetadataRepo = paramMetadataRepo;
    this.mIndex = paramInt;
  }
  
  private MetadataItem getMetadataItem()
  {
    ThreadLocal localThreadLocal = sMetadataItem;
    MetadataItem localMetadataItem2 = (MetadataItem)localThreadLocal.get();
    MetadataItem localMetadataItem1 = localMetadataItem2;
    if (localMetadataItem2 == null)
    {
      localMetadataItem1 = new MetadataItem();
      localThreadLocal.set(localMetadataItem1);
    }
    this.mMetadataRepo.getMetadataList().list(localMetadataItem1, this.mIndex);
    return localMetadataItem1;
  }
  
  public void draw(Canvas paramCanvas, float paramFloat1, float paramFloat2, Paint paramPaint)
  {
    Typeface localTypeface2 = this.mMetadataRepo.getTypeface();
    Typeface localTypeface1 = paramPaint.getTypeface();
    paramPaint.setTypeface(localTypeface2);
    int i = this.mIndex;
    paramCanvas.drawText(this.mMetadataRepo.getEmojiCharArray(), i * 2, 2, paramFloat1, paramFloat2, paramPaint);
    paramPaint.setTypeface(localTypeface1);
  }
  
  public int getCodepointAt(int paramInt)
  {
    return getMetadataItem().codepoints(paramInt);
  }
  
  public int getCodepointsLength()
  {
    return getMetadataItem().codepointsLength();
  }
  
  public short getCompatAdded()
  {
    return getMetadataItem().compatAdded();
  }
  
  public int getHasGlyph()
  {
    return this.mHasGlyph;
  }
  
  public short getHeight()
  {
    return getMetadataItem().height();
  }
  
  public int getId()
  {
    return getMetadataItem().id();
  }
  
  public short getSdkAdded()
  {
    return getMetadataItem().sdkAdded();
  }
  
  public Typeface getTypeface()
  {
    return this.mMetadataRepo.getTypeface();
  }
  
  public short getWidth()
  {
    return getMetadataItem().width();
  }
  
  public boolean isDefaultEmoji()
  {
    return getMetadataItem().emojiStyle();
  }
  
  public void resetHasGlyphCache()
  {
    this.mHasGlyph = 0;
  }
  
  public void setHasGlyph(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 1;
    }
    this.mHasGlyph = i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append(", id:");
    String str = Integer.toHexString(getId());
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder.append(str);
    localStringBuilder.append(", codepoints:");
    int j = getCodepointsLength();
    for (int i = 0; i < j; i++)
    {
      str = Integer.toHexString(getCodepointAt(i));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
      localStringBuilder.append(" ");
    }
    return localStringBuilder.toString();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface HasGlyph {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/EmojiMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */