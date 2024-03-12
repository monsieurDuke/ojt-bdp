package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Typeface.CustomFallbackBuilder;
import android.graphics.fonts.Font;
import android.graphics.fonts.Font.Builder;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontFamily.Builder;
import android.graphics.fonts.FontStyle;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl
  extends TypefaceCompatBaseImpl
{
  private Font findBaseFont(FontFamily paramFontFamily, int paramInt)
  {
    if ((paramInt & 0x1) != 0) {
      i = 700;
    } else {
      i = 400;
    }
    if ((paramInt & 0x2) != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    FontStyle localFontStyle = new FontStyle(i, paramInt);
    Object localObject = paramFontFamily.getFont(0);
    int i = getMatchScore(localFontStyle, ((Font)localObject).getStyle());
    paramInt = 1;
    while (paramInt < paramFontFamily.getSize())
    {
      Font localFont = paramFontFamily.getFont(paramInt);
      int k = getMatchScore(localFontStyle, localFont.getStyle());
      int j = i;
      if (k < i)
      {
        localObject = localFont;
        j = k;
      }
      paramInt++;
      i = j;
    }
    return (Font)localObject;
  }
  
  private static int getMatchScore(FontStyle paramFontStyle1, FontStyle paramFontStyle2)
  {
    int j = Math.abs(paramFontStyle1.getWeight() - paramFontStyle2.getWeight()) / 100;
    int i;
    if (paramFontStyle1.getSlant() == paramFontStyle2.getSlant()) {
      i = 0;
    } else {
      i = 2;
    }
    return j + i;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    paramContext = null;
    try
    {
      for (paramFontFamilyFilesResourceEntry : paramFontFamilyFilesResourceEntry.getEntries()) {
        try
        {
          Object localObject = new android/graphics/fonts/Font$Builder;
          ((Font.Builder)localObject).<init>(paramResources, paramFontFamilyFilesResourceEntry.getResourceId());
          localObject = ((Font.Builder)localObject).setWeight(paramFontFamilyFilesResourceEntry.getWeight());
          int j;
          if (paramFontFamilyFilesResourceEntry.isItalic()) {
            j = 1;
          } else {
            j = 0;
          }
          localObject = ((Font.Builder)localObject).setSlant(j).setTtcIndex(paramFontFamilyFilesResourceEntry.getTtcIndex()).setFontVariationSettings(paramFontFamilyFilesResourceEntry.getVariationSettings()).build();
          if (paramContext == null)
          {
            paramFontFamilyFilesResourceEntry = new android/graphics/fonts/FontFamily$Builder;
            paramFontFamilyFilesResourceEntry.<init>((Font)localObject);
            paramContext = paramFontFamilyFilesResourceEntry;
          }
          else
          {
            paramContext.addFont((Font)localObject);
          }
        }
        catch (IOException paramFontFamilyFilesResourceEntry) {}
      }
      if (paramContext == null) {
        return null;
      }
      paramFontFamilyFilesResourceEntry = paramContext.build();
      paramContext = new android/graphics/Typeface$CustomFallbackBuilder;
      paramContext.<init>(paramFontFamilyFilesResourceEntry);
      paramContext = paramContext.setStyle(findBaseFont(paramFontFamilyFilesResourceEntry, paramInt).getStyle()).build();
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 8
    //   3: aload_1
    //   4: invokevirtual 134	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   7: astore 9
    //   9: aload_3
    //   10: arraylength
    //   11: istore 7
    //   13: iconst_0
    //   14: istore 5
    //   16: aload 8
    //   18: astore_1
    //   19: iload 5
    //   21: iload 7
    //   23: if_icmpge +197 -> 220
    //   26: aload_3
    //   27: iload 5
    //   29: aaload
    //   30: astore 11
    //   32: aload_1
    //   33: astore 8
    //   35: aload 9
    //   37: aload 11
    //   39: invokevirtual 140	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   42: ldc -114
    //   44: aload_2
    //   45: invokevirtual 148	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   48: astore 10
    //   50: aload 10
    //   52: ifnonnull +19 -> 71
    //   55: aload 10
    //   57: ifnull +11 -> 68
    //   60: aload_1
    //   61: astore 8
    //   63: aload 10
    //   65: invokevirtual 153	android/os/ParcelFileDescriptor:close	()V
    //   68: goto +146 -> 214
    //   71: new 62	android/graphics/fonts/Font$Builder
    //   74: astore 8
    //   76: aload 8
    //   78: aload 10
    //   80: invokespecial 156	android/graphics/fonts/Font$Builder:<init>	(Landroid/os/ParcelFileDescriptor;)V
    //   83: aload 8
    //   85: aload 11
    //   87: invokevirtual 157	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   90: invokevirtual 75	android/graphics/fonts/Font$Builder:setWeight	(I)Landroid/graphics/fonts/Font$Builder;
    //   93: astore 8
    //   95: aload 11
    //   97: invokevirtual 158	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   100: ifeq +9 -> 109
    //   103: iconst_1
    //   104: istore 6
    //   106: goto +6 -> 112
    //   109: iconst_0
    //   110: istore 6
    //   112: aload 8
    //   114: iload 6
    //   116: invokevirtual 82	android/graphics/fonts/Font$Builder:setSlant	(I)Landroid/graphics/fonts/Font$Builder;
    //   119: aload 11
    //   121: invokevirtual 159	androidx/core/provider/FontsContractCompat$FontInfo:getTtcIndex	()I
    //   124: invokevirtual 88	android/graphics/fonts/Font$Builder:setTtcIndex	(I)Landroid/graphics/fonts/Font$Builder;
    //   127: invokevirtual 100	android/graphics/fonts/Font$Builder:build	()Landroid/graphics/fonts/Font;
    //   130: astore 11
    //   132: aload_1
    //   133: ifnonnull +21 -> 154
    //   136: new 102	android/graphics/fonts/FontFamily$Builder
    //   139: astore 8
    //   141: aload 8
    //   143: aload 11
    //   145: invokespecial 105	android/graphics/fonts/FontFamily$Builder:<init>	(Landroid/graphics/fonts/Font;)V
    //   148: aload 8
    //   150: astore_1
    //   151: goto +10 -> 161
    //   154: aload_1
    //   155: aload 11
    //   157: invokevirtual 109	android/graphics/fonts/FontFamily$Builder:addFont	(Landroid/graphics/fonts/Font;)Landroid/graphics/fonts/FontFamily$Builder;
    //   160: pop
    //   161: aload 10
    //   163: ifnull +11 -> 174
    //   166: aload_1
    //   167: astore 8
    //   169: aload 10
    //   171: invokevirtual 153	android/os/ParcelFileDescriptor:close	()V
    //   174: goto +40 -> 214
    //   177: astore 11
    //   179: aload 10
    //   181: ifnull +23 -> 204
    //   184: aload 10
    //   186: invokevirtual 153	android/os/ParcelFileDescriptor:close	()V
    //   189: goto +15 -> 204
    //   192: astore 10
    //   194: aload_1
    //   195: astore 8
    //   197: aload 11
    //   199: aload 10
    //   201: invokevirtual 165	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   204: aload_1
    //   205: astore 8
    //   207: aload 11
    //   209: athrow
    //   210: astore_1
    //   211: aload 8
    //   213: astore_1
    //   214: iinc 5 1
    //   217: goto -198 -> 19
    //   220: aload_1
    //   221: ifnonnull +5 -> 226
    //   224: aconst_null
    //   225: areturn
    //   226: aload_1
    //   227: invokevirtual 112	android/graphics/fonts/FontFamily$Builder:build	()Landroid/graphics/fonts/FontFamily;
    //   230: astore_2
    //   231: new 114	android/graphics/Typeface$CustomFallbackBuilder
    //   234: astore_1
    //   235: aload_1
    //   236: aload_2
    //   237: invokespecial 117	android/graphics/Typeface$CustomFallbackBuilder:<init>	(Landroid/graphics/fonts/FontFamily;)V
    //   240: aload_1
    //   241: aload_0
    //   242: aload_2
    //   243: iload 4
    //   245: invokespecial 119	androidx/core/graphics/TypefaceCompatApi29Impl:findBaseFont	(Landroid/graphics/fonts/FontFamily;I)Landroid/graphics/fonts/Font;
    //   248: invokevirtual 28	android/graphics/fonts/Font:getStyle	()Landroid/graphics/fonts/FontStyle;
    //   251: invokevirtual 123	android/graphics/Typeface$CustomFallbackBuilder:setStyle	(Landroid/graphics/fonts/FontStyle;)Landroid/graphics/Typeface$CustomFallbackBuilder;
    //   254: invokevirtual 126	android/graphics/Typeface$CustomFallbackBuilder:build	()Landroid/graphics/Typeface;
    //   257: astore_1
    //   258: aload_1
    //   259: areturn
    //   260: astore_1
    //   261: aconst_null
    //   262: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	263	0	this	TypefaceCompatApi29Impl
    //   0	263	1	paramContext	Context
    //   0	263	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	263	3	paramArrayOfFontInfo	FontsContractCompat.FontInfo[]
    //   0	263	4	paramInt	int
    //   14	201	5	i	int
    //   104	11	6	j	int
    //   11	13	7	k	int
    //   1	211	8	localObject1	Object
    //   7	29	9	localContentResolver	android.content.ContentResolver
    //   48	137	10	localParcelFileDescriptor	android.os.ParcelFileDescriptor
    //   192	8	10	localThrowable	Throwable
    //   30	126	11	localObject2	Object
    //   177	31	11	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   71	103	177	finally
    //   112	132	177	finally
    //   136	148	177	finally
    //   154	161	177	finally
    //   184	189	192	finally
    //   35	50	210	java/io/IOException
    //   63	68	210	java/io/IOException
    //   169	174	210	java/io/IOException
    //   197	204	210	java/io/IOException
    //   207	210	210	java/io/IOException
    //   9	13	260	java/lang/Exception
    //   35	50	260	java/lang/Exception
    //   63	68	260	java/lang/Exception
    //   169	174	260	java/lang/Exception
    //   197	204	260	java/lang/Exception
    //   207	210	260	java/lang/Exception
    //   226	258	260	java/lang/Exception
  }
  
  protected Typeface createFromInputStream(Context paramContext, InputStream paramInputStream)
  {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    try
    {
      paramContext = new android/graphics/fonts/Font$Builder;
      paramContext.<init>(paramResources, paramInt1);
      paramContext = paramContext.build();
      paramResources = new android/graphics/fonts/FontFamily$Builder;
      paramResources.<init>(paramContext);
      paramString = paramResources.build();
      paramResources = new android/graphics/Typeface$CustomFallbackBuilder;
      paramResources.<init>(paramString);
      paramContext = paramResources.setStyle(paramContext.getStyle()).build();
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/TypefaceCompatApi29Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */