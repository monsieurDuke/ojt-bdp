package com.google.android.material.color;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class ColorResourcesTableCreator
{
  private static final byte ANDROID_PACKAGE_ID = 1;
  private static final PackageInfo ANDROID_PACKAGE_INFO = new PackageInfo(1, "android");
  private static final byte APPLICATION_PACKAGE_ID = 127;
  private static final Comparator<ColorResource> COLOR_RESOURCE_COMPARATOR = new Comparator()
  {
    public int compare(ColorResourcesTableCreator.ColorResource paramAnonymousColorResource1, ColorResourcesTableCreator.ColorResource paramAnonymousColorResource2)
    {
      return ColorResourcesTableCreator.ColorResource.access$000(paramAnonymousColorResource1) - ColorResourcesTableCreator.ColorResource.access$000(paramAnonymousColorResource2);
    }
  };
  private static final short HEADER_TYPE_PACKAGE = 512;
  private static final short HEADER_TYPE_RES_TABLE = 2;
  private static final short HEADER_TYPE_STRING_POOL = 1;
  private static final short HEADER_TYPE_TYPE = 513;
  private static final short HEADER_TYPE_TYPE_SPEC = 514;
  private static final String RESOURCE_TYPE_NAME_COLOR = "color";
  private static byte typeIdColor;
  
  private static byte[] charToByteArray(char paramChar)
  {
    return new byte[] { (byte)(paramChar & 0xFF), (byte)(paramChar >> '\b' & 0xFF) };
  }
  
  static byte[] create(Context paramContext, Map<Integer, Integer> paramMap)
    throws IOException
  {
    if (!paramMap.entrySet().isEmpty())
    {
      PackageInfo localPackageInfo = new PackageInfo(127, paramContext.getPackageName());
      HashMap localHashMap = new HashMap();
      ColorResource localColorResource = null;
      Iterator localIterator = paramMap.entrySet().iterator();
      paramMap = localColorResource;
      while (localIterator.hasNext())
      {
        paramMap = (Map.Entry)localIterator.next();
        localColorResource = new ColorResource(((Integer)paramMap.getKey()).intValue(), paramContext.getResources().getResourceName(((Integer)paramMap.getKey()).intValue()), ((Integer)paramMap.getValue()).intValue());
        if (paramContext.getResources().getResourceTypeName(((Integer)paramMap.getKey()).intValue()).equals("color"))
        {
          if (localColorResource.packageId == 1)
          {
            paramMap = ANDROID_PACKAGE_INFO;
          }
          else
          {
            if (localColorResource.packageId != Byte.MAX_VALUE) {
              break label231;
            }
            paramMap = localPackageInfo;
          }
          if (!localHashMap.containsKey(paramMap)) {
            localHashMap.put(paramMap, new ArrayList());
          }
          ((List)localHashMap.get(paramMap)).add(localColorResource);
          paramMap = localColorResource;
          continue;
          label231:
          throw new IllegalArgumentException("Not supported with unknown package id: " + localColorResource.packageId);
        }
        else
        {
          paramContext = new StringBuilder().append("Non color resource found: name=");
          paramMap = localColorResource.name;
          Log5ECF72.a(paramMap);
          LogE84000.a(paramMap);
          Log229316.a(paramMap);
          paramContext = paramContext.append(paramMap).append(", typeId=");
          paramMap = Integer.toHexString(localColorResource.typeId & 0xFF);
          Log5ECF72.a(paramMap);
          LogE84000.a(paramMap);
          Log229316.a(paramMap);
          throw new IllegalArgumentException(paramMap);
        }
      }
      byte b = paramMap.typeId;
      typeIdColor = b;
      if (b != 0)
      {
        paramContext = new ByteArrayOutputStream();
        new ResTable(localHashMap).writeTo(paramContext);
        return paramContext.toByteArray();
      }
      throw new IllegalArgumentException("No color resources found for harmonization.");
    }
    throw new IllegalArgumentException("No color resources provided for harmonization.");
  }
  
  private static byte[] intToByteArray(int paramInt)
  {
    return new byte[] { (byte)(paramInt & 0xFF), (byte)(paramInt >> 8 & 0xFF), (byte)(paramInt >> 16 & 0xFF), (byte)(paramInt >> 24 & 0xFF) };
  }
  
  private static byte[] shortToByteArray(short paramShort)
  {
    return new byte[] { (byte)(paramShort & 0xFF), (byte)(paramShort >> 8 & 0xFF) };
  }
  
  private static byte[] stringToByteArray(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    paramString = new byte[arrayOfChar.length * 2 + 4];
    byte[] arrayOfByte = shortToByteArray((short)arrayOfChar.length);
    paramString[0] = arrayOfByte[0];
    paramString[1] = arrayOfByte[1];
    for (int i = 0; i < arrayOfChar.length; i++)
    {
      arrayOfByte = charToByteArray(arrayOfChar[i]);
      paramString[(i * 2 + 2)] = arrayOfByte[0];
      paramString[(i * 2 + 3)] = arrayOfByte[1];
    }
    paramString[(paramString.length - 2)] = 0;
    paramString[(paramString.length - 1)] = 0;
    return paramString;
  }
  
  private static byte[] stringToByteArrayUtf8(String paramString)
  {
    paramString = paramString.getBytes(Charset.forName("UTF-8"));
    int i = (byte)paramString.length;
    byte[] arrayOfByte = new byte[paramString.length + 3];
    System.arraycopy(paramString, 0, arrayOfByte, 2, i);
    arrayOfByte[1] = i;
    arrayOfByte[0] = i;
    arrayOfByte[(arrayOfByte.length - 1)] = 0;
    return arrayOfByte;
  }
  
  static class ColorResource
  {
    private final short entryId;
    private final String name;
    private final byte packageId;
    private final byte typeId;
    private final int value;
    
    ColorResource(int paramInt1, String paramString, int paramInt2)
    {
      this.name = paramString;
      this.value = paramInt2;
      this.entryId = ((short)(0xFFFF & paramInt1));
      this.typeId = ((byte)(paramInt1 >> 16 & 0xFF));
      this.packageId = ((byte)(paramInt1 >> 24 & 0xFF));
    }
  }
  
  private static class PackageChunk
  {
    private static final short HEADER_SIZE = 288;
    private static final int PACKAGE_NAME_MAX_LENGTH = 128;
    private final ColorResourcesTableCreator.ResChunkHeader header;
    private final ColorResourcesTableCreator.StringPoolChunk keyStrings;
    private final ColorResourcesTableCreator.PackageInfo packageInfo;
    private final ColorResourcesTableCreator.TypeSpecChunk typeSpecChunk;
    private final ColorResourcesTableCreator.StringPoolChunk typeStrings;
    
    PackageChunk(ColorResourcesTableCreator.PackageInfo paramPackageInfo, List<ColorResourcesTableCreator.ColorResource> paramList)
    {
      this.packageInfo = paramPackageInfo;
      this.typeStrings = new ColorResourcesTableCreator.StringPoolChunk(false, new String[] { "?1", "?2", "?3", "?4", "?5", "color" });
      String[] arrayOfString = new String[paramList.size()];
      for (int i = 0; i < paramList.size(); i++)
      {
        paramPackageInfo = ((ColorResourcesTableCreator.ColorResource)paramList.get(i)).name;
        Log5ECF72.a(paramPackageInfo);
        LogE84000.a(paramPackageInfo);
        Log229316.a(paramPackageInfo);
        arrayOfString[i] = paramPackageInfo;
      }
      this.keyStrings = new ColorResourcesTableCreator.StringPoolChunk(true, arrayOfString);
      this.typeSpecChunk = new ColorResourcesTableCreator.TypeSpecChunk(paramList);
      this.header = new ColorResourcesTableCreator.ResChunkHeader((short)512, (short)288, getChunkSize());
    }
    
    int getChunkSize()
    {
      return this.typeStrings.getChunkSize() + 288 + this.keyStrings.getChunkSize() + this.typeSpecChunk.getChunkSizeWithTypeChunk();
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      this.header.writeTo(paramByteArrayOutputStream);
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(ColorResourcesTableCreator.PackageInfo.access$1000(this.packageInfo)));
      Object localObject = ColorResourcesTableCreator.PackageInfo.access$1100(this.packageInfo);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localObject = ((String)localObject).toCharArray();
      for (int i = 0; i < 128; i++) {
        if (i < localObject.length) {
          paramByteArrayOutputStream.write(ColorResourcesTableCreator.charToByteArray(localObject[i]));
        } else {
          paramByteArrayOutputStream.write(ColorResourcesTableCreator.charToByteArray('\000'));
        }
      }
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(288));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.typeStrings.getChunkSize() + 288));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(0));
      this.typeStrings.writeTo(paramByteArrayOutputStream);
      this.keyStrings.writeTo(paramByteArrayOutputStream);
      this.typeSpecChunk.writeTo(paramByteArrayOutputStream);
    }
  }
  
  static class PackageInfo
  {
    private final int id;
    private final String name;
    
    PackageInfo(int paramInt, String paramString)
    {
      this.id = paramInt;
      this.name = paramString;
    }
  }
  
  private static class ResChunkHeader
  {
    private final int chunkSize;
    private final short headerSize;
    private final short type;
    
    ResChunkHeader(short paramShort1, short paramShort2, int paramInt)
    {
      this.type = paramShort1;
      this.headerSize = paramShort2;
      this.chunkSize = paramInt;
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.shortToByteArray(this.type));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.shortToByteArray(this.headerSize));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.chunkSize));
    }
  }
  
  private static class ResEntry
  {
    private static final byte DATA_TYPE_AARRGGBB = 28;
    private static final short ENTRY_SIZE = 8;
    private static final short FLAG_PUBLIC = 2;
    private static final int SIZE = 16;
    private static final short VALUE_SIZE = 8;
    private final int data;
    private final int keyStringIndex;
    
    ResEntry(int paramInt1, int paramInt2)
    {
      this.keyStringIndex = paramInt1;
      this.data = paramInt2;
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.shortToByteArray((short)8));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.shortToByteArray((short)2));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.keyStringIndex));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.shortToByteArray((short)8));
      paramByteArrayOutputStream.write(new byte[] { 0, 28 });
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.data));
    }
  }
  
  private static class ResTable
  {
    private static final short HEADER_SIZE = 12;
    private final ColorResourcesTableCreator.ResChunkHeader header;
    private final List<ColorResourcesTableCreator.PackageChunk> packageChunks = new ArrayList();
    private final int packageCount;
    private final ColorResourcesTableCreator.StringPoolChunk stringPool;
    
    ResTable(Map<ColorResourcesTableCreator.PackageInfo, List<ColorResourcesTableCreator.ColorResource>> paramMap)
    {
      this.packageCount = paramMap.size();
      this.stringPool = new ColorResourcesTableCreator.StringPoolChunk(new String[0]);
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        List localList = (List)localEntry.getValue();
        Collections.sort(localList, ColorResourcesTableCreator.COLOR_RESOURCE_COMPARATOR);
        this.packageChunks.add(new ColorResourcesTableCreator.PackageChunk((ColorResourcesTableCreator.PackageInfo)localEntry.getKey(), localList));
      }
      this.header = new ColorResourcesTableCreator.ResChunkHeader((short)2, (short)12, getOverallSize());
    }
    
    private int getOverallSize()
    {
      int i = 0;
      Iterator localIterator = this.packageChunks.iterator();
      while (localIterator.hasNext()) {
        i += ((ColorResourcesTableCreator.PackageChunk)localIterator.next()).getChunkSize();
      }
      return this.stringPool.getChunkSize() + 12 + i;
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      this.header.writeTo(paramByteArrayOutputStream);
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.packageCount));
      this.stringPool.writeTo(paramByteArrayOutputStream);
      Iterator localIterator = this.packageChunks.iterator();
      while (localIterator.hasNext()) {
        ((ColorResourcesTableCreator.PackageChunk)localIterator.next()).writeTo(paramByteArrayOutputStream);
      }
    }
  }
  
  private static class StringPoolChunk
  {
    private static final int FLAG_UTF8 = 256;
    private static final short HEADER_SIZE = 28;
    private static final int STYLED_SPAN_LIST_END = -1;
    private final int chunkSize;
    private final ColorResourcesTableCreator.ResChunkHeader header;
    private final int stringCount;
    private final List<Integer> stringIndex = new ArrayList();
    private final List<byte[]> strings = new ArrayList();
    private final int stringsPaddingSize;
    private final int stringsStart;
    private final int styledSpanCount;
    private final List<Integer> styledSpanIndex = new ArrayList();
    private final List<List<ColorResourcesTableCreator.StringStyledSpan>> styledSpans = new ArrayList();
    private final int styledSpansStart;
    private final boolean utf8Encode;
    
    StringPoolChunk(boolean paramBoolean, String... paramVarArgs)
    {
      this.utf8Encode = paramBoolean;
      int j = 0;
      int k = paramVarArgs.length;
      int n = 0;
      Object localObject;
      for (int i = 0; i < k; i++)
      {
        localObject = processString(paramVarArgs[i]);
        this.stringIndex.add(Integer.valueOf(j));
        j += ((byte[])((Pair)localObject).first).length;
        this.strings.add(((Pair)localObject).first);
        this.styledSpans.add(((Pair)localObject).second);
      }
      i = 0;
      Iterator localIterator2 = this.styledSpans.iterator();
      while (localIterator2.hasNext())
      {
        List localList = (List)localIterator2.next();
        Iterator localIterator1 = localList.iterator();
        while (localIterator1.hasNext())
        {
          localObject = (ColorResourcesTableCreator.StringStyledSpan)localIterator1.next();
          this.stringIndex.add(Integer.valueOf(j));
          j += ColorResourcesTableCreator.StringStyledSpan.access$700((ColorResourcesTableCreator.StringStyledSpan)localObject).length;
          this.strings.add(ColorResourcesTableCreator.StringStyledSpan.access$700((ColorResourcesTableCreator.StringStyledSpan)localObject));
        }
        this.styledSpanIndex.add(Integer.valueOf(i));
        i += localList.size() * 12 + 4;
      }
      k = j % 4;
      int m;
      if (k == 0) {
        m = 0;
      } else {
        m = 4 - k;
      }
      this.stringsPaddingSize = m;
      int i1 = this.strings.size();
      this.stringCount = i1;
      this.styledSpanCount = (this.strings.size() - paramVarArgs.length);
      if (this.strings.size() - paramVarArgs.length > 0) {
        k = 1;
      } else {
        k = 0;
      }
      if (k == 0)
      {
        this.styledSpanIndex.clear();
        this.styledSpans.clear();
      }
      i1 = i1 * 4 + 28 + this.styledSpanIndex.size() * 4;
      this.stringsStart = i1;
      m += j;
      if (k != 0) {
        j = i1 + m;
      } else {
        j = 0;
      }
      this.styledSpansStart = j;
      j = n;
      if (k != 0) {
        j = i;
      }
      i = i1 + m + j;
      this.chunkSize = i;
      this.header = new ColorResourcesTableCreator.ResChunkHeader((short)1, (short)28, i);
    }
    
    StringPoolChunk(String... paramVarArgs)
    {
      this(false, paramVarArgs);
    }
    
    private Pair<byte[], List<ColorResourcesTableCreator.StringStyledSpan>> processString(String paramString)
    {
      if (this.utf8Encode) {
        paramString = ColorResourcesTableCreator.stringToByteArrayUtf8(paramString);
      } else {
        paramString = ColorResourcesTableCreator.stringToByteArray(paramString);
      }
      return new Pair(paramString, Collections.emptyList());
    }
    
    int getChunkSize()
    {
      return this.chunkSize;
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      this.header.writeTo(paramByteArrayOutputStream);
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.stringCount));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.styledSpanCount));
      if (this.utf8Encode) {
        i = 256;
      } else {
        i = 0;
      }
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(i));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.stringsStart));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.styledSpansStart));
      Iterator localIterator1 = this.stringIndex.iterator();
      while (localIterator1.hasNext()) {
        paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(((Integer)localIterator1.next()).intValue()));
      }
      localIterator1 = this.styledSpanIndex.iterator();
      while (localIterator1.hasNext()) {
        paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(((Integer)localIterator1.next()).intValue()));
      }
      localIterator1 = this.strings.iterator();
      while (localIterator1.hasNext()) {
        paramByteArrayOutputStream.write((byte[])localIterator1.next());
      }
      int i = this.stringsPaddingSize;
      if (i > 0) {
        paramByteArrayOutputStream.write(new byte[i]);
      }
      localIterator1 = this.styledSpans.iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((List)localIterator1.next()).iterator();
        while (localIterator2.hasNext()) {
          ((ColorResourcesTableCreator.StringStyledSpan)localIterator2.next()).writeTo(paramByteArrayOutputStream);
        }
        paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(-1));
      }
    }
  }
  
  private static class StringStyledSpan
  {
    private int firstCharacterIndex;
    private int lastCharacterIndex;
    private int nameReference;
    private byte[] styleString;
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.nameReference));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.firstCharacterIndex));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.lastCharacterIndex));
    }
  }
  
  private static class TypeChunk
  {
    private static final byte CONFIG_SIZE = 64;
    private static final short HEADER_SIZE = 84;
    private static final int OFFSET_NO_ENTRY = -1;
    private final byte[] config;
    private final int entryCount;
    private final ColorResourcesTableCreator.ResChunkHeader header;
    private final int[] offsetTable;
    private final ColorResourcesTableCreator.ResEntry[] resEntries;
    
    TypeChunk(List<ColorResourcesTableCreator.ColorResource> paramList, Set<Short> paramSet, int paramInt)
    {
      Object localObject = new byte[64];
      this.config = ((byte[])localObject);
      this.entryCount = paramInt;
      localObject[0] = 64;
      this.resEntries = new ColorResourcesTableCreator.ResEntry[paramList.size()];
      for (int j = 0; j < paramList.size(); j++)
      {
        localObject = (ColorResourcesTableCreator.ColorResource)paramList.get(j);
        this.resEntries[j] = new ColorResourcesTableCreator.ResEntry(j, ((ColorResourcesTableCreator.ColorResource)localObject).value);
      }
      this.offsetTable = new int[paramInt];
      j = 0;
      for (int i = 0; i < paramInt; i = (short)(i + 1)) {
        if (paramSet.contains(Short.valueOf(i)))
        {
          this.offsetTable[i] = j;
          j += 16;
        }
        else
        {
          this.offsetTable[i] = -1;
        }
      }
      this.header = new ColorResourcesTableCreator.ResChunkHeader((short)513, (short)84, getChunkSize());
    }
    
    private int getEntryStart()
    {
      return getOffsetTableSize() + 84;
    }
    
    private int getOffsetTableSize()
    {
      return this.offsetTable.length * 4;
    }
    
    int getChunkSize()
    {
      return getEntryStart() + this.resEntries.length * 16;
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      this.header.writeTo(paramByteArrayOutputStream);
      int i = ColorResourcesTableCreator.typeIdColor;
      int k = 0;
      paramByteArrayOutputStream.write(new byte[] { i, 0, 0, 0 });
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.entryCount));
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(getEntryStart()));
      paramByteArrayOutputStream.write(this.config);
      Object localObject = this.offsetTable;
      int m = localObject.length;
      for (int j = 0; j < m; j++) {
        paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(localObject[j]));
      }
      localObject = this.resEntries;
      m = localObject.length;
      for (j = k; j < m; j++) {
        localObject[j].writeTo(paramByteArrayOutputStream);
      }
    }
  }
  
  private static class TypeSpecChunk
  {
    private static final short HEADER_SIZE = 16;
    private static final int SPEC_PUBLIC = 1073741824;
    private final int entryCount;
    private final int[] entryFlags;
    private final ColorResourcesTableCreator.ResChunkHeader header;
    private final ColorResourcesTableCreator.TypeChunk typeChunk;
    
    TypeSpecChunk(List<ColorResourcesTableCreator.ColorResource> paramList)
    {
      this.entryCount = (((ColorResourcesTableCreator.ColorResource)paramList.get(paramList.size() - 1)).entryId + 1);
      HashSet localHashSet = new HashSet();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext()) {
        localHashSet.add(Short.valueOf(((ColorResourcesTableCreator.ColorResource)localIterator.next()).entryId));
      }
      this.entryFlags = new int[this.entryCount];
      int j;
      for (int i = 0; i < this.entryCount; j = (short)(i + 1)) {
        if (localHashSet.contains(Short.valueOf(i))) {
          this.entryFlags[i] = 1073741824;
        }
      }
      this.header = new ColorResourcesTableCreator.ResChunkHeader((short)514, (short)16, getChunkSize());
      this.typeChunk = new ColorResourcesTableCreator.TypeChunk(paramList, localHashSet, this.entryCount);
    }
    
    private int getChunkSize()
    {
      return this.entryCount * 4 + 16;
    }
    
    int getChunkSizeWithTypeChunk()
    {
      return getChunkSize() + this.typeChunk.getChunkSize();
    }
    
    void writeTo(ByteArrayOutputStream paramByteArrayOutputStream)
      throws IOException
    {
      this.header.writeTo(paramByteArrayOutputStream);
      int i = ColorResourcesTableCreator.typeIdColor;
      int j = 0;
      paramByteArrayOutputStream.write(new byte[] { i, 0, 0, 0 });
      paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(this.entryCount));
      int[] arrayOfInt = this.entryFlags;
      int k = arrayOfInt.length;
      while (j < k)
      {
        paramByteArrayOutputStream.write(ColorResourcesTableCreator.intToByteArray(arrayOfInt[j]));
        j++;
      }
      this.typeChunk.writeTo(paramByteArrayOutputStream);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/color/ColorResourcesTableCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */