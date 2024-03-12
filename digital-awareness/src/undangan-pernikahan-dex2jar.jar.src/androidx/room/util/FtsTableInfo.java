package androidx.room.util;

import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FtsTableInfo
{
  private static final String[] FTS_OPTIONS = { "tokenize=", "compress=", "content=", "languageid=", "matchinfo=", "notindexed=", "order=", "prefix=", "uncompress=" };
  public final Set<String> columns;
  public final String name;
  public final Set<String> options;
  
  public FtsTableInfo(String paramString1, Set<String> paramSet, String paramString2)
  {
    this.name = paramString1;
    this.columns = paramSet;
    this.options = parseOptions(paramString2);
  }
  
  public FtsTableInfo(String paramString, Set<String> paramSet1, Set<String> paramSet2)
  {
    this.name = paramString;
    this.columns = paramSet1;
    this.options = paramSet2;
  }
  
  static Set<String> parseOptions(String paramString)
  {
    if (paramString.isEmpty()) {
      return new HashSet();
    }
    Object localObject2 = paramString.substring(paramString.indexOf('(') + 1, paramString.lastIndexOf(')'));
    Object localObject1 = new ArrayList();
    paramString = new ArrayDeque();
    int j = -1;
    int i = 0;
    while (i < ((String)localObject2).length())
    {
      char c = ((String)localObject2).charAt(i);
      int k;
      switch (c)
      {
      default: 
        k = j;
        break;
      case ']': 
        k = j;
        if (!paramString.isEmpty())
        {
          k = j;
          if (((Character)paramString.peek()).charValue() == '[')
          {
            paramString.pop();
            k = j;
          }
        }
        break;
      case '[': 
        k = j;
        if (paramString.isEmpty())
        {
          paramString.push(Character.valueOf(c));
          k = j;
        }
        break;
      case ',': 
        k = j;
        if (paramString.isEmpty())
        {
          ((List)localObject1).add(((String)localObject2).substring(j + 1, i).trim());
          k = i;
        }
        break;
      case '"': 
      case '\'': 
      case '`': 
        if (paramString.isEmpty())
        {
          paramString.push(Character.valueOf(c));
          k = j;
        }
        else
        {
          k = j;
          if (((Character)paramString.peek()).charValue() == c)
          {
            paramString.pop();
            k = j;
          }
        }
        break;
      }
      i++;
      j = k;
    }
    ((List)localObject1).add(((String)localObject2).substring(j + 1).trim());
    paramString = new HashSet();
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      String str = (String)((Iterator)localObject1).next();
      localObject2 = FTS_OPTIONS;
      j = localObject2.length;
      for (i = 0; i < j; i++) {
        if (str.startsWith(localObject2[i])) {
          paramString.add(str);
        }
      }
    }
    return paramString;
  }
  
  public static FtsTableInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    return new FtsTableInfo(paramString, readColumns(paramSupportSQLiteDatabase, paramString), readOptions(paramSupportSQLiteDatabase, paramString));
  }
  
  private static Set<String> readColumns(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("PRAGMA table_info(`" + paramString + "`)");
    paramString = new HashSet();
    try
    {
      if (paramSupportSQLiteDatabase.getColumnCount() > 0)
      {
        int i = paramSupportSQLiteDatabase.getColumnIndex("name");
        while (paramSupportSQLiteDatabase.moveToNext()) {
          paramString.add(paramSupportSQLiteDatabase.getString(i));
        }
      }
      return paramString;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static Set<String> readOptions(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    String str = "";
    paramString = paramSupportSQLiteDatabase.query("SELECT * FROM sqlite_master WHERE `name` = '" + paramString + "'");
    paramSupportSQLiteDatabase = str;
    try
    {
      if (paramString.moveToFirst()) {
        paramSupportSQLiteDatabase = paramString.getString(paramString.getColumnIndexOrThrow("sql"));
      }
      return parseOptions(paramSupportSQLiteDatabase);
    }
    finally
    {
      paramString.close();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (FtsTableInfo)paramObject;
      Object localObject = this.name;
      if (localObject != null ? !((String)localObject).equals(((FtsTableInfo)paramObject).name) : ((FtsTableInfo)paramObject).name != null) {
        return false;
      }
      localObject = this.columns;
      if (localObject != null ? !localObject.equals(((FtsTableInfo)paramObject).columns) : ((FtsTableInfo)paramObject).columns != null) {
        return false;
      }
      localObject = this.options;
      if (localObject != null) {
        bool = localObject.equals(((FtsTableInfo)paramObject).options);
      } else if (((FtsTableInfo)paramObject).options != null) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int hashCode()
  {
    Object localObject = this.name;
    int k = 0;
    int i;
    if (localObject != null) {
      i = ((String)localObject).hashCode();
    } else {
      i = 0;
    }
    localObject = this.columns;
    int j;
    if (localObject != null) {
      j = localObject.hashCode();
    } else {
      j = 0;
    }
    localObject = this.options;
    if (localObject != null) {
      k = localObject.hashCode();
    }
    return (i * 31 + j) * 31 + k;
  }
  
  public String toString()
  {
    return "FtsTableInfo{name='" + this.name + '\'' + ", columns=" + this.columns + ", options=" + this.options + '}';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/FtsTableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */