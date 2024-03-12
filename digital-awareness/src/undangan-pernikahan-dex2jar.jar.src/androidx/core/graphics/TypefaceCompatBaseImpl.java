package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

class TypefaceCompatBaseImpl
{
  private static final int INVALID_KEY = 0;
  private static final String TAG = "TypefaceCompatBaseImpl";
  private ConcurrentHashMap<Long, FontResourcesParserCompat.FontFamilyFilesResourceEntry> mFontFamilies = new ConcurrentHashMap();
  
  private void addFontFamily(Typeface paramTypeface, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry)
  {
    long l = getUniqueKey(paramTypeface);
    if (l != 0L) {
      this.mFontFamilies.put(Long.valueOf(l), paramFontFamilyFilesResourceEntry);
    }
  }
  
  private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, int paramInt)
  {
    (FontResourcesParserCompat.FontFileResourceEntry)findBestFont(paramFontFamilyFilesResourceEntry.getEntries(), paramInt, new StyleExtractor()
    {
      public int getWeight(FontResourcesParserCompat.FontFileResourceEntry paramAnonymousFontFileResourceEntry)
      {
        return paramAnonymousFontFileResourceEntry.getWeight();
      }
      
      public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry paramAnonymousFontFileResourceEntry)
      {
        return paramAnonymousFontFileResourceEntry.isItalic();
      }
    });
  }
  
  private static <T> T findBestFont(T[] paramArrayOfT, int paramInt, StyleExtractor<T> paramStyleExtractor)
  {
    int i;
    if ((paramInt & 0x1) == 0) {
      i = 400;
    } else {
      i = 700;
    }
    int i1;
    if ((paramInt & 0x2) != 0) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    Object localObject = null;
    int j = Integer.MAX_VALUE;
    int n = paramArrayOfT.length;
    paramInt = 0;
    while (paramInt < n)
    {
      T ? = paramArrayOfT[paramInt];
      int m = Math.abs(paramStyleExtractor.getWeight(?) - i);
      int k;
      if (paramStyleExtractor.isItalic(?) == i1) {
        k = 0;
      } else {
        k = 1;
      }
      m = m * 2 + k;
      if (localObject != null)
      {
        k = j;
        if (j <= m) {}
      }
      else
      {
        localObject = ?;
        k = m;
      }
      paramInt++;
      j = k;
    }
    return (T)localObject;
  }
  
  private static long getUniqueKey(Typeface paramTypeface)
  {
    if (paramTypeface == null) {
      return 0L;
    }
    try
    {
      Field localField = Typeface.class.getDeclaredField("native_instance");
      localField.setAccessible(true);
      long l = ((Number)localField.get(paramTypeface)).longValue();
      return l;
    }
    catch (IllegalAccessException paramTypeface)
    {
      Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", paramTypeface);
      return 0L;
    }
    catch (NoSuchFieldException paramTypeface)
    {
      Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", paramTypeface);
    }
    return 0L;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    FontResourcesParserCompat.FontFileResourceEntry localFontFileResourceEntry = findBestEntry(paramFontFamilyFilesResourceEntry, paramInt);
    if (localFontFileResourceEntry == null) {
      return null;
    }
    paramContext = TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, localFontFileResourceEntry.getResourceId(), localFontFileResourceEntry.getFileName(), 0, paramInt);
    addFontFamily(paramContext, paramFontFamilyFilesResourceEntry);
    return paramContext;
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: aload_3
    //   10: iload 4
    //   12: invokevirtual 147	androidx/core/graphics/TypefaceCompatBaseImpl:findBestInfo	([Landroidx/core/provider/FontsContractCompat$FontInfo;I)Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   15: astore 5
    //   17: aconst_null
    //   18: astore_3
    //   19: aconst_null
    //   20: astore_2
    //   21: aload_1
    //   22: invokevirtual 153	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   25: aload 5
    //   27: invokevirtual 159	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   30: invokevirtual 165	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   33: astore 5
    //   35: aload 5
    //   37: astore_2
    //   38: aload 5
    //   40: astore_3
    //   41: aload_0
    //   42: aload_1
    //   43: aload 5
    //   45: invokevirtual 169	androidx/core/graphics/TypefaceCompatBaseImpl:createFromInputStream	(Landroid/content/Context;Ljava/io/InputStream;)Landroid/graphics/Typeface;
    //   48: astore_1
    //   49: aload 5
    //   51: invokestatic 175	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   54: aload_1
    //   55: areturn
    //   56: astore_1
    //   57: aload_2
    //   58: invokestatic 175	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   61: aload_1
    //   62: athrow
    //   63: astore_1
    //   64: aload_3
    //   65: invokestatic 175	androidx/core/graphics/TypefaceCompatUtil:closeQuietly	(Ljava/io/Closeable;)V
    //   68: aconst_null
    //   69: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	70	0	this	TypefaceCompatBaseImpl
    //   0	70	1	paramContext	Context
    //   0	70	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	70	3	paramArrayOfFontInfo	FontsContractCompat.FontInfo[]
    //   0	70	4	paramInt	int
    //   15	35	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   21	35	56	finally
    //   41	49	56	finally
    //   21	35	63	java/io/IOException
    //   41	49	63	java/io/IOException
  }
  
  /* Error */
  protected Typeface createFromInputStream(Context paramContext, java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 181	androidx/core/graphics/TypefaceCompatUtil:getTempFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnonnull +5 -> 11
    //   9: aconst_null
    //   10: areturn
    //   11: aload_1
    //   12: aload_2
    //   13: invokestatic 185	androidx/core/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Ljava/io/InputStream;)Z
    //   16: istore_3
    //   17: iload_3
    //   18: ifne +10 -> 28
    //   21: aload_1
    //   22: invokevirtual 191	java/io/File:delete	()Z
    //   25: pop
    //   26: aconst_null
    //   27: areturn
    //   28: aload_1
    //   29: invokevirtual 194	java/io/File:getPath	()Ljava/lang/String;
    //   32: invokestatic 198	android/graphics/Typeface:createFromFile	(Ljava/lang/String;)Landroid/graphics/Typeface;
    //   35: astore_2
    //   36: aload_1
    //   37: invokevirtual 191	java/io/File:delete	()Z
    //   40: pop
    //   41: aload_2
    //   42: areturn
    //   43: astore_2
    //   44: aload_1
    //   45: invokevirtual 191	java/io/File:delete	()Z
    //   48: pop
    //   49: aload_2
    //   50: athrow
    //   51: astore_2
    //   52: aload_1
    //   53: invokevirtual 191	java/io/File:delete	()Z
    //   56: pop
    //   57: aconst_null
    //   58: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	59	0	this	TypefaceCompatBaseImpl
    //   0	59	1	paramContext	Context
    //   0	59	2	paramInputStream	java.io.InputStream
    //   16	2	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   11	17	43	finally
    //   28	36	43	finally
    //   11	17	51	java/lang/RuntimeException
    //   28	36	51	java/lang/RuntimeException
  }
  
  /* Error */
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 181	androidx/core/graphics/TypefaceCompatUtil:getTempFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnonnull +5 -> 11
    //   9: aconst_null
    //   10: areturn
    //   11: aload_1
    //   12: aload_2
    //   13: iload_3
    //   14: invokestatic 202	androidx/core/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Landroid/content/res/Resources;I)Z
    //   17: istore 6
    //   19: iload 6
    //   21: ifne +10 -> 31
    //   24: aload_1
    //   25: invokevirtual 191	java/io/File:delete	()Z
    //   28: pop
    //   29: aconst_null
    //   30: areturn
    //   31: aload_1
    //   32: invokevirtual 194	java/io/File:getPath	()Ljava/lang/String;
    //   35: invokestatic 198	android/graphics/Typeface:createFromFile	(Ljava/lang/String;)Landroid/graphics/Typeface;
    //   38: astore_2
    //   39: aload_1
    //   40: invokevirtual 191	java/io/File:delete	()Z
    //   43: pop
    //   44: aload_2
    //   45: areturn
    //   46: astore_2
    //   47: aload_1
    //   48: invokevirtual 191	java/io/File:delete	()Z
    //   51: pop
    //   52: aload_2
    //   53: athrow
    //   54: astore_2
    //   55: aload_1
    //   56: invokevirtual 191	java/io/File:delete	()Z
    //   59: pop
    //   60: aconst_null
    //   61: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	TypefaceCompatBaseImpl
    //   0	62	1	paramContext	Context
    //   0	62	2	paramResources	Resources
    //   0	62	3	paramInt1	int
    //   0	62	4	paramString	String
    //   0	62	5	paramInt2	int
    //   17	3	6	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   11	19	46	finally
    //   31	39	46	finally
    //   11	19	54	java/lang/RuntimeException
    //   31	39	54	java/lang/RuntimeException
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    (FontsContractCompat.FontInfo)findBestFont(paramArrayOfFontInfo, paramInt, new StyleExtractor()
    {
      public int getWeight(FontsContractCompat.FontInfo paramAnonymousFontInfo)
      {
        return paramAnonymousFontInfo.getWeight();
      }
      
      public boolean isItalic(FontsContractCompat.FontInfo paramAnonymousFontInfo)
      {
        return paramAnonymousFontInfo.isItalic();
      }
    });
  }
  
  FontResourcesParserCompat.FontFamilyFilesResourceEntry getFontFamily(Typeface paramTypeface)
  {
    long l = getUniqueKey(paramTypeface);
    if (l == 0L) {
      return null;
    }
    return (FontResourcesParserCompat.FontFamilyFilesResourceEntry)this.mFontFamilies.get(Long.valueOf(l));
  }
  
  private static abstract interface StyleExtractor<T>
  {
    public abstract int getWeight(T paramT);
    
    public abstract boolean isItalic(T paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompatBaseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */