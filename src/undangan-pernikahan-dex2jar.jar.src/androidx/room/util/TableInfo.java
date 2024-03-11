package androidx.room.util;

import android.database.Cursor;
import android.os.Build.VERSION;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TableInfo
{
  public static final int CREATED_FROM_DATABASE = 2;
  public static final int CREATED_FROM_ENTITY = 1;
  public static final int CREATED_FROM_UNKNOWN = 0;
  public final Map<String, Column> columns;
  public final Set<ForeignKey> foreignKeys;
  public final Set<Index> indices;
  public final String name;
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet)
  {
    this(paramString, paramMap, paramSet, Collections.emptySet());
  }
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet, Set<Index> paramSet1)
  {
    this.name = paramString;
    this.columns = Collections.unmodifiableMap(paramMap);
    this.foreignKeys = Collections.unmodifiableSet(paramSet);
    if (paramSet1 == null) {
      paramString = null;
    } else {
      paramString = Collections.unmodifiableSet(paramSet1);
    }
    this.indices = paramString;
  }
  
  public static TableInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    return new TableInfo(paramString, readColumns(paramSupportSQLiteDatabase, paramString), readForeignKeys(paramSupportSQLiteDatabase, paramString), readIndices(paramSupportSQLiteDatabase, paramString));
  }
  
  private static Map<String, Column> readColumns(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("PRAGMA table_info(`" + paramString + "`)");
    HashMap localHashMap = new HashMap();
    try
    {
      if (paramSupportSQLiteDatabase.getColumnCount() > 0)
      {
        int i = paramSupportSQLiteDatabase.getColumnIndex("name");
        int m = paramSupportSQLiteDatabase.getColumnIndex("type");
        int k = paramSupportSQLiteDatabase.getColumnIndex("notnull");
        int n = paramSupportSQLiteDatabase.getColumnIndex("pk");
        int j = paramSupportSQLiteDatabase.getColumnIndex("dflt_value");
        while (paramSupportSQLiteDatabase.moveToNext())
        {
          paramString = paramSupportSQLiteDatabase.getString(i);
          String str2 = paramSupportSQLiteDatabase.getString(m);
          boolean bool;
          if (paramSupportSQLiteDatabase.getInt(k) != 0) {
            bool = true;
          } else {
            bool = false;
          }
          int i1 = paramSupportSQLiteDatabase.getInt(n);
          String str1 = paramSupportSQLiteDatabase.getString(j);
          Column localColumn = new androidx/room/util/TableInfo$Column;
          localColumn.<init>(paramString, str2, bool, i1, str1, 2);
          localHashMap.put(paramString, localColumn);
        }
      }
      return localHashMap;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static List<ForeignKeyWithSequence> readForeignKeyFieldMappings(Cursor paramCursor)
  {
    int k = paramCursor.getColumnIndex("id");
    int n = paramCursor.getColumnIndex("seq");
    int j = paramCursor.getColumnIndex("from");
    int i1 = paramCursor.getColumnIndex("to");
    int m = paramCursor.getCount();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < m; i++)
    {
      paramCursor.moveToPosition(i);
      localArrayList.add(new ForeignKeyWithSequence(paramCursor.getInt(k), paramCursor.getInt(n), paramCursor.getString(j), paramCursor.getString(i1)));
    }
    Collections.sort(localArrayList);
    return localArrayList;
  }
  
  private static Set<ForeignKey> readForeignKeys(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    HashSet localHashSet = new HashSet();
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("PRAGMA foreign_key_list(`" + paramString + "`)");
    try
    {
      int i = paramSupportSQLiteDatabase.getColumnIndex("id");
      int i2 = paramSupportSQLiteDatabase.getColumnIndex("seq");
      int n = paramSupportSQLiteDatabase.getColumnIndex("table");
      int m = paramSupportSQLiteDatabase.getColumnIndex("on_delete");
      int i1 = paramSupportSQLiteDatabase.getColumnIndex("on_update");
      List localList = readForeignKeyFieldMappings(paramSupportSQLiteDatabase);
      int k = paramSupportSQLiteDatabase.getCount();
      for (int j = 0; j < k; j++)
      {
        paramSupportSQLiteDatabase.moveToPosition(j);
        if (paramSupportSQLiteDatabase.getInt(i2) == 0)
        {
          int i3 = paramSupportSQLiteDatabase.getInt(i);
          ArrayList localArrayList = new java/util/ArrayList;
          localArrayList.<init>();
          paramString = new java/util/ArrayList;
          paramString.<init>();
          Object localObject = localList.iterator();
          while (((Iterator)localObject).hasNext())
          {
            ForeignKeyWithSequence localForeignKeyWithSequence = (ForeignKeyWithSequence)((Iterator)localObject).next();
            if (localForeignKeyWithSequence.mId == i3)
            {
              localArrayList.add(localForeignKeyWithSequence.mFrom);
              paramString.add(localForeignKeyWithSequence.mTo);
            }
          }
          localObject = new androidx/room/util/TableInfo$ForeignKey;
          ((ForeignKey)localObject).<init>(paramSupportSQLiteDatabase.getString(n), paramSupportSQLiteDatabase.getString(m), paramSupportSQLiteDatabase.getString(i1), localArrayList, paramString);
          localHashSet.add(localObject);
        }
      }
      return localHashSet;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static Index readIndex(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString, boolean paramBoolean)
  {
    paramSupportSQLiteDatabase = paramSupportSQLiteDatabase.query("PRAGMA index_xinfo(`" + paramString + "`)");
    try
    {
      int i = paramSupportSQLiteDatabase.getColumnIndex("seqno");
      int k = paramSupportSQLiteDatabase.getColumnIndex("cid");
      int j = paramSupportSQLiteDatabase.getColumnIndex("name");
      if ((i != -1) && (k != -1) && (j != -1))
      {
        TreeMap localTreeMap = new java/util/TreeMap;
        localTreeMap.<init>();
        while (paramSupportSQLiteDatabase.moveToNext()) {
          if (paramSupportSQLiteDatabase.getInt(k) >= 0) {
            localTreeMap.put(Integer.valueOf(paramSupportSQLiteDatabase.getInt(i)), paramSupportSQLiteDatabase.getString(j));
          }
        }
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>(localTreeMap.size());
        localArrayList.addAll(localTreeMap.values());
        paramString = new Index(paramString, paramBoolean, localArrayList);
        return paramString;
      }
      return null;
    }
    finally
    {
      paramSupportSQLiteDatabase.close();
    }
  }
  
  private static Set<Index> readIndices(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString)
  {
    paramString = paramSupportSQLiteDatabase.query("PRAGMA index_list(`" + paramString + "`)");
    try
    {
      int j = paramString.getColumnIndex("name");
      int i = paramString.getColumnIndex("origin");
      int m = paramString.getColumnIndex("unique");
      if ((j != -1) && (i != -1) && (m != -1))
      {
        HashSet localHashSet = new java/util/HashSet;
        localHashSet.<init>();
        while (paramString.moveToNext()) {
          if ("c".equals(paramString.getString(i)))
          {
            Object localObject = paramString.getString(j);
            int k = paramString.getInt(m);
            boolean bool = true;
            if (k != 1) {
              bool = false;
            }
            localObject = readIndex(paramSupportSQLiteDatabase, (String)localObject, bool);
            if (localObject == null) {
              return null;
            }
            localHashSet.add(localObject);
          }
        }
        return localHashSet;
      }
      return null;
    }
    finally
    {
      paramString.close();
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (TableInfo)paramObject;
      Object localObject = this.name;
      if (localObject != null ? !((String)localObject).equals(((TableInfo)paramObject).name) : ((TableInfo)paramObject).name != null) {
        return false;
      }
      localObject = this.columns;
      if (localObject != null ? !localObject.equals(((TableInfo)paramObject).columns) : ((TableInfo)paramObject).columns != null) {
        return false;
      }
      localObject = this.foreignKeys;
      if (localObject != null ? !localObject.equals(((TableInfo)paramObject).foreignKeys) : ((TableInfo)paramObject).foreignKeys != null) {
        return false;
      }
      localObject = this.indices;
      if (localObject != null)
      {
        paramObject = ((TableInfo)paramObject).indices;
        if (paramObject != null) {
          return localObject.equals(paramObject);
        }
      }
      return true;
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
    localObject = this.foreignKeys;
    if (localObject != null) {
      k = localObject.hashCode();
    }
    return (i * 31 + j) * 31 + k;
  }
  
  public String toString()
  {
    return "TableInfo{name='" + this.name + '\'' + ", columns=" + this.columns + ", foreignKeys=" + this.foreignKeys + ", indices=" + this.indices + '}';
  }
  
  public static class Column
  {
    public final int affinity;
    public final String defaultValue;
    private final int mCreatedFrom;
    public final String name;
    public final boolean notNull;
    public final int primaryKeyPosition;
    public final String type;
    
    @Deprecated
    public Column(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
    {
      this(paramString1, paramString2, paramBoolean, paramInt, null, 0);
    }
    
    public Column(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, String paramString3, int paramInt2)
    {
      this.name = paramString1;
      this.type = paramString2;
      this.notNull = paramBoolean;
      this.primaryKeyPosition = paramInt1;
      this.affinity = findAffinity(paramString2);
      this.defaultValue = paramString3;
      this.mCreatedFrom = paramInt2;
    }
    
    private static int findAffinity(String paramString)
    {
      if (paramString == null) {
        return 5;
      }
      paramString = paramString.toUpperCase(Locale.US);
      if (paramString.contains("INT")) {
        return 3;
      }
      if ((!paramString.contains("CHAR")) && (!paramString.contains("CLOB")) && (!paramString.contains("TEXT")))
      {
        if (paramString.contains("BLOB")) {
          return 5;
        }
        if ((!paramString.contains("REAL")) && (!paramString.contains("FLOA")) && (!paramString.contains("DOUB"))) {
          return 1;
        }
        return 4;
      }
      return 2;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (Column)paramObject;
        if (Build.VERSION.SDK_INT >= 20)
        {
          if (this.primaryKeyPosition != ((Column)paramObject).primaryKeyPosition) {
            return false;
          }
        }
        else if (isPrimaryKey() != ((Column)paramObject).isPrimaryKey()) {
          return false;
        }
        if (!this.name.equals(((Column)paramObject).name)) {
          return false;
        }
        if (this.notNull != ((Column)paramObject).notNull) {
          return false;
        }
        String str;
        if ((this.mCreatedFrom == 1) && (((Column)paramObject).mCreatedFrom == 2))
        {
          str = this.defaultValue;
          if ((str != null) && (!str.equals(((Column)paramObject).defaultValue))) {
            return false;
          }
        }
        if ((this.mCreatedFrom == 2) && (((Column)paramObject).mCreatedFrom == 1))
        {
          str = ((Column)paramObject).defaultValue;
          if ((str != null) && (!str.equals(this.defaultValue))) {
            return false;
          }
        }
        int i = this.mCreatedFrom;
        if ((i != 0) && (i == ((Column)paramObject).mCreatedFrom))
        {
          str = this.defaultValue;
          if (str != null ? !str.equals(((Column)paramObject).defaultValue) : ((Column)paramObject).defaultValue != null) {
            return false;
          }
        }
        if (this.affinity != ((Column)paramObject).affinity) {
          bool = false;
        }
        return bool;
      }
      return false;
    }
    
    public int hashCode()
    {
      int k = this.name.hashCode();
      int j = this.affinity;
      int i;
      if (this.notNull) {
        i = 1231;
      } else {
        i = 1237;
      }
      return ((k * 31 + j) * 31 + i) * 31 + this.primaryKeyPosition;
    }
    
    public boolean isPrimaryKey()
    {
      boolean bool;
      if (this.primaryKeyPosition > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public String toString()
    {
      return "Column{name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", affinity='" + this.affinity + '\'' + ", notNull=" + this.notNull + ", primaryKeyPosition=" + this.primaryKeyPosition + ", defaultValue='" + this.defaultValue + '\'' + '}';
    }
  }
  
  public static class ForeignKey
  {
    public final List<String> columnNames;
    public final String onDelete;
    public final String onUpdate;
    public final List<String> referenceColumnNames;
    public final String referenceTable;
    
    public ForeignKey(String paramString1, String paramString2, String paramString3, List<String> paramList1, List<String> paramList2)
    {
      this.referenceTable = paramString1;
      this.onDelete = paramString2;
      this.onUpdate = paramString3;
      this.columnNames = Collections.unmodifiableList(paramList1);
      this.referenceColumnNames = Collections.unmodifiableList(paramList2);
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (ForeignKey)paramObject;
        if (!this.referenceTable.equals(((ForeignKey)paramObject).referenceTable)) {
          return false;
        }
        if (!this.onDelete.equals(((ForeignKey)paramObject).onDelete)) {
          return false;
        }
        if (!this.onUpdate.equals(((ForeignKey)paramObject).onUpdate)) {
          return false;
        }
        if (!this.columnNames.equals(((ForeignKey)paramObject).columnNames)) {
          return false;
        }
        return this.referenceColumnNames.equals(((ForeignKey)paramObject).referenceColumnNames);
      }
      return false;
    }
    
    public int hashCode()
    {
      return (((this.referenceTable.hashCode() * 31 + this.onDelete.hashCode()) * 31 + this.onUpdate.hashCode()) * 31 + this.columnNames.hashCode()) * 31 + this.referenceColumnNames.hashCode();
    }
    
    public String toString()
    {
      return "ForeignKey{referenceTable='" + this.referenceTable + '\'' + ", onDelete='" + this.onDelete + '\'' + ", onUpdate='" + this.onUpdate + '\'' + ", columnNames=" + this.columnNames + ", referenceColumnNames=" + this.referenceColumnNames + '}';
    }
  }
  
  static class ForeignKeyWithSequence
    implements Comparable<ForeignKeyWithSequence>
  {
    final String mFrom;
    final int mId;
    final int mSequence;
    final String mTo;
    
    ForeignKeyWithSequence(int paramInt1, int paramInt2, String paramString1, String paramString2)
    {
      this.mId = paramInt1;
      this.mSequence = paramInt2;
      this.mFrom = paramString1;
      this.mTo = paramString2;
    }
    
    public int compareTo(ForeignKeyWithSequence paramForeignKeyWithSequence)
    {
      int i = this.mId - paramForeignKeyWithSequence.mId;
      if (i == 0) {
        return this.mSequence - paramForeignKeyWithSequence.mSequence;
      }
      return i;
    }
  }
  
  public static class Index
  {
    public static final String DEFAULT_PREFIX = "index_";
    public final List<String> columns;
    public final String name;
    public final boolean unique;
    
    public Index(String paramString, boolean paramBoolean, List<String> paramList)
    {
      this.name = paramString;
      this.unique = paramBoolean;
      this.columns = paramList;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if ((paramObject != null) && (getClass() == paramObject.getClass()))
      {
        paramObject = (Index)paramObject;
        if (this.unique != ((Index)paramObject).unique) {
          return false;
        }
        if (!this.columns.equals(((Index)paramObject).columns)) {
          return false;
        }
        if (this.name.startsWith("index_")) {
          return ((Index)paramObject).name.startsWith("index_");
        }
        return this.name.equals(((Index)paramObject).name);
      }
      return false;
    }
    
    public int hashCode()
    {
      int i;
      if (this.name.startsWith("index_")) {
        i = "index_".hashCode();
      } else {
        i = this.name.hashCode();
      }
      return (i * 31 + this.unique) * 31 + this.columns.hashCode();
    }
    
    public String toString()
    {
      return "Index{name='" + this.name + '\'' + ", unique=" + this.unique + ", columns=" + this.columns + '}';
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/util/TableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */