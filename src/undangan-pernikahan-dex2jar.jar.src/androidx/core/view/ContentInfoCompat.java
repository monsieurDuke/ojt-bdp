package androidx.core.view;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContentInfo;
import android.view.ContentInfo.Builder;
import androidx.core.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ContentInfoCompat
{
  public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
  public static final int SOURCE_APP = 0;
  public static final int SOURCE_AUTOFILL = 4;
  public static final int SOURCE_CLIPBOARD = 1;
  public static final int SOURCE_DRAG_AND_DROP = 3;
  public static final int SOURCE_INPUT_METHOD = 2;
  public static final int SOURCE_PROCESS_TEXT = 5;
  private final Compat mCompat;
  
  ContentInfoCompat(Compat paramCompat)
  {
    this.mCompat = paramCompat;
  }
  
  static ClipData buildClipData(ClipDescription paramClipDescription, List<ClipData.Item> paramList)
  {
    paramClipDescription = new ClipData(new ClipDescription(paramClipDescription), (ClipData.Item)paramList.get(0));
    for (int i = 1; i < paramList.size(); i++) {
      paramClipDescription.addItem((ClipData.Item)paramList.get(i));
    }
    return paramClipDescription;
  }
  
  static String flagsToString(int paramInt)
  {
    if ((paramInt & 0x1) != 0) {
      return "FLAG_CONVERT_TO_PLAIN_TEXT";
    }
    String str = String.valueOf(paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  static Pair<ClipData, ClipData> partition(ClipData paramClipData, androidx.core.util.Predicate<ClipData.Item> paramPredicate)
  {
    ArrayList localArrayList2 = null;
    ArrayList localArrayList1 = null;
    for (int i = 0; i < paramClipData.getItemCount(); i++)
    {
      ClipData.Item localItem = paramClipData.getItemAt(i);
      if (paramPredicate.test(localItem))
      {
        if (localArrayList2 == null) {
          localArrayList2 = new ArrayList();
        }
        localArrayList2.add(localItem);
      }
      else
      {
        if (localArrayList1 == null) {
          localArrayList1 = new ArrayList();
        }
        localArrayList1.add(localItem);
      }
    }
    if (localArrayList2 == null) {
      return Pair.create(null, paramClipData);
    }
    if (localArrayList1 == null) {
      return Pair.create(paramClipData, null);
    }
    return Pair.create(buildClipData(paramClipData.getDescription(), localArrayList2), buildClipData(paramClipData.getDescription(), localArrayList1));
  }
  
  public static Pair<ContentInfo, ContentInfo> partition(ContentInfo paramContentInfo, java.util.function.Predicate<ClipData.Item> paramPredicate)
  {
    return Api31Impl.partition(paramContentInfo, paramPredicate);
  }
  
  static String sourceToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      String str = String.valueOf(paramInt);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    case 5: 
      return "SOURCE_PROCESS_TEXT";
    case 4: 
      return "SOURCE_AUTOFILL";
    case 3: 
      return "SOURCE_DRAG_AND_DROP";
    case 2: 
      return "SOURCE_INPUT_METHOD";
    case 1: 
      return "SOURCE_CLIPBOARD";
    }
    return "SOURCE_APP";
  }
  
  public static ContentInfoCompat toContentInfoCompat(ContentInfo paramContentInfo)
  {
    return new ContentInfoCompat(new Compat31Impl(paramContentInfo));
  }
  
  public ClipData getClip()
  {
    return this.mCompat.getClip();
  }
  
  public Bundle getExtras()
  {
    return this.mCompat.getExtras();
  }
  
  public int getFlags()
  {
    return this.mCompat.getFlags();
  }
  
  public Uri getLinkUri()
  {
    return this.mCompat.getLinkUri();
  }
  
  public int getSource()
  {
    return this.mCompat.getSource();
  }
  
  public Pair<ContentInfoCompat, ContentInfoCompat> partition(androidx.core.util.Predicate<ClipData.Item> paramPredicate)
  {
    ClipData localClipData = this.mCompat.getClip();
    int i = localClipData.getItemCount();
    ContentInfoCompat localContentInfoCompat = null;
    if (i == 1)
    {
      boolean bool = paramPredicate.test(localClipData.getItemAt(0));
      if (bool) {
        paramPredicate = this;
      } else {
        paramPredicate = null;
      }
      if (!bool) {
        localContentInfoCompat = this;
      }
      return Pair.create(paramPredicate, localContentInfoCompat);
    }
    paramPredicate = partition(localClipData, paramPredicate);
    if (paramPredicate.first == null) {
      return Pair.create(null, this);
    }
    if (paramPredicate.second == null) {
      return Pair.create(this, null);
    }
    return Pair.create(new Builder(this).setClip((ClipData)paramPredicate.first).build(), new Builder(this).setClip((ClipData)paramPredicate.second).build());
  }
  
  public ContentInfo toContentInfo()
  {
    return (ContentInfo)Objects.requireNonNull(this.mCompat.getWrapped());
  }
  
  public String toString()
  {
    return this.mCompat.toString();
  }
  
  private static final class Api31Impl
  {
    public static Pair<ContentInfo, ContentInfo> partition(ContentInfo paramContentInfo, java.util.function.Predicate<ClipData.Item> paramPredicate)
    {
      ClipData localClipData = paramContentInfo.getClip();
      int i = localClipData.getItemCount();
      Object localObject = null;
      if (i == 1)
      {
        boolean bool = paramPredicate.test(localClipData.getItemAt(0));
        if (bool) {
          paramPredicate = paramContentInfo;
        } else {
          paramPredicate = null;
        }
        if (bool) {
          paramContentInfo = (ContentInfo)localObject;
        }
        return Pair.create(paramPredicate, paramContentInfo);
      }
      Objects.requireNonNull(paramPredicate);
      paramPredicate = ContentInfoCompat.partition(localClipData, new ContentInfoCompat.Api31Impl..ExternalSyntheticLambda0(paramPredicate));
      if (paramPredicate.first == null) {
        return Pair.create(null, paramContentInfo);
      }
      if (paramPredicate.second == null) {
        return Pair.create(paramContentInfo, null);
      }
      return Pair.create(new ContentInfo.Builder(paramContentInfo).setClip((ClipData)paramPredicate.first).build(), new ContentInfo.Builder(paramContentInfo).setClip((ClipData)paramPredicate.second).build());
    }
  }
  
  public static final class Builder
  {
    private final ContentInfoCompat.BuilderCompat mBuilderCompat;
    
    public Builder(ClipData paramClipData, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 31) {
        this.mBuilderCompat = new ContentInfoCompat.BuilderCompat31Impl(paramClipData, paramInt);
      } else {
        this.mBuilderCompat = new ContentInfoCompat.BuilderCompatImpl(paramClipData, paramInt);
      }
    }
    
    public Builder(ContentInfoCompat paramContentInfoCompat)
    {
      if (Build.VERSION.SDK_INT >= 31) {
        this.mBuilderCompat = new ContentInfoCompat.BuilderCompat31Impl(paramContentInfoCompat);
      } else {
        this.mBuilderCompat = new ContentInfoCompat.BuilderCompatImpl(paramContentInfoCompat);
      }
    }
    
    public ContentInfoCompat build()
    {
      return this.mBuilderCompat.build();
    }
    
    public Builder setClip(ClipData paramClipData)
    {
      this.mBuilderCompat.setClip(paramClipData);
      return this;
    }
    
    public Builder setExtras(Bundle paramBundle)
    {
      this.mBuilderCompat.setExtras(paramBundle);
      return this;
    }
    
    public Builder setFlags(int paramInt)
    {
      this.mBuilderCompat.setFlags(paramInt);
      return this;
    }
    
    public Builder setLinkUri(Uri paramUri)
    {
      this.mBuilderCompat.setLinkUri(paramUri);
      return this;
    }
    
    public Builder setSource(int paramInt)
    {
      this.mBuilderCompat.setSource(paramInt);
      return this;
    }
  }
  
  private static abstract interface BuilderCompat
  {
    public abstract ContentInfoCompat build();
    
    public abstract void setClip(ClipData paramClipData);
    
    public abstract void setExtras(Bundle paramBundle);
    
    public abstract void setFlags(int paramInt);
    
    public abstract void setLinkUri(Uri paramUri);
    
    public abstract void setSource(int paramInt);
  }
  
  private static final class BuilderCompat31Impl
    implements ContentInfoCompat.BuilderCompat
  {
    private final ContentInfo.Builder mPlatformBuilder;
    
    BuilderCompat31Impl(ClipData paramClipData, int paramInt)
    {
      this.mPlatformBuilder = new ContentInfo.Builder(paramClipData, paramInt);
    }
    
    BuilderCompat31Impl(ContentInfoCompat paramContentInfoCompat)
    {
      this.mPlatformBuilder = new ContentInfo.Builder(paramContentInfoCompat.toContentInfo());
    }
    
    public ContentInfoCompat build()
    {
      return new ContentInfoCompat(new ContentInfoCompat.Compat31Impl(this.mPlatformBuilder.build()));
    }
    
    public void setClip(ClipData paramClipData)
    {
      this.mPlatformBuilder.setClip(paramClipData);
    }
    
    public void setExtras(Bundle paramBundle)
    {
      this.mPlatformBuilder.setExtras(paramBundle);
    }
    
    public void setFlags(int paramInt)
    {
      this.mPlatformBuilder.setFlags(paramInt);
    }
    
    public void setLinkUri(Uri paramUri)
    {
      this.mPlatformBuilder.setLinkUri(paramUri);
    }
    
    public void setSource(int paramInt)
    {
      this.mPlatformBuilder.setSource(paramInt);
    }
  }
  
  private static final class BuilderCompatImpl
    implements ContentInfoCompat.BuilderCompat
  {
    ClipData mClip;
    Bundle mExtras;
    int mFlags;
    Uri mLinkUri;
    int mSource;
    
    BuilderCompatImpl(ClipData paramClipData, int paramInt)
    {
      this.mClip = paramClipData;
      this.mSource = paramInt;
    }
    
    BuilderCompatImpl(ContentInfoCompat paramContentInfoCompat)
    {
      this.mClip = paramContentInfoCompat.getClip();
      this.mSource = paramContentInfoCompat.getSource();
      this.mFlags = paramContentInfoCompat.getFlags();
      this.mLinkUri = paramContentInfoCompat.getLinkUri();
      this.mExtras = paramContentInfoCompat.getExtras();
    }
    
    public ContentInfoCompat build()
    {
      return new ContentInfoCompat(new ContentInfoCompat.CompatImpl(this));
    }
    
    public void setClip(ClipData paramClipData)
    {
      this.mClip = paramClipData;
    }
    
    public void setExtras(Bundle paramBundle)
    {
      this.mExtras = paramBundle;
    }
    
    public void setFlags(int paramInt)
    {
      this.mFlags = paramInt;
    }
    
    public void setLinkUri(Uri paramUri)
    {
      this.mLinkUri = paramUri;
    }
    
    public void setSource(int paramInt)
    {
      this.mSource = paramInt;
    }
  }
  
  private static abstract interface Compat
  {
    public abstract ClipData getClip();
    
    public abstract Bundle getExtras();
    
    public abstract int getFlags();
    
    public abstract Uri getLinkUri();
    
    public abstract int getSource();
    
    public abstract ContentInfo getWrapped();
  }
  
  private static final class Compat31Impl
    implements ContentInfoCompat.Compat
  {
    private final ContentInfo mWrapped;
    
    Compat31Impl(ContentInfo paramContentInfo)
    {
      this.mWrapped = ((ContentInfo)Preconditions.checkNotNull(paramContentInfo));
    }
    
    public ClipData getClip()
    {
      return this.mWrapped.getClip();
    }
    
    public Bundle getExtras()
    {
      return this.mWrapped.getExtras();
    }
    
    public int getFlags()
    {
      return this.mWrapped.getFlags();
    }
    
    public Uri getLinkUri()
    {
      return this.mWrapped.getLinkUri();
    }
    
    public int getSource()
    {
      return this.mWrapped.getSource();
    }
    
    public ContentInfo getWrapped()
    {
      return this.mWrapped;
    }
    
    public String toString()
    {
      return "ContentInfoCompat{" + this.mWrapped + "}";
    }
  }
  
  private static final class CompatImpl
    implements ContentInfoCompat.Compat
  {
    private final ClipData mClip;
    private final Bundle mExtras;
    private final int mFlags;
    private final Uri mLinkUri;
    private final int mSource;
    
    CompatImpl(ContentInfoCompat.BuilderCompatImpl paramBuilderCompatImpl)
    {
      this.mClip = ((ClipData)Preconditions.checkNotNull(paramBuilderCompatImpl.mClip));
      this.mSource = Preconditions.checkArgumentInRange(paramBuilderCompatImpl.mSource, 0, 5, "source");
      this.mFlags = Preconditions.checkFlagsArgument(paramBuilderCompatImpl.mFlags, 1);
      this.mLinkUri = paramBuilderCompatImpl.mLinkUri;
      this.mExtras = paramBuilderCompatImpl.mExtras;
    }
    
    public ClipData getClip()
    {
      return this.mClip;
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public int getFlags()
    {
      return this.mFlags;
    }
    
    public Uri getLinkUri()
    {
      return this.mLinkUri;
    }
    
    public int getSource()
    {
      return this.mSource;
    }
    
    public ContentInfo getWrapped()
    {
      return null;
    }
    
    public String toString()
    {
      Object localObject = new StringBuilder().append("ContentInfoCompat{clip=").append(this.mClip.getDescription()).append(", source=");
      String str = ContentInfoCompat.sourceToString(this.mSource);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localObject = ((StringBuilder)localObject).append(str).append(", flags=");
      str = ContentInfoCompat.flagsToString(this.mFlags);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      StringBuilder localStringBuilder = ((StringBuilder)localObject).append(str);
      localObject = this.mLinkUri;
      str = "";
      if (localObject == null) {
        localObject = "";
      } else {
        localObject = ", hasLinkUri(" + this.mLinkUri.toString().length() + ")";
      }
      localStringBuilder = localStringBuilder.append((String)localObject);
      if (this.mExtras == null) {
        localObject = str;
      } else {
        localObject = ", hasExtras";
      }
      return (String)localObject + "}";
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Flags {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Source {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ContentInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */