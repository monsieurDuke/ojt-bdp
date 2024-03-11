package androidx.core.content;

import android.content.ContentValues;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\032;\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004¢\006\002\020\007¨\006\b"}, d2={"contentValuesOf", "Landroid/content/ContentValues;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/content/ContentValues;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ContentValuesKt
{
  public static final ContentValues contentValuesOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    ContentValues localContentValues = new ContentValues(paramVarArgs.length);
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramVarArgs[i];
      String str = (String)((Pair)localObject).component1();
      localObject = ((Pair)localObject).component2();
      if (localObject == null)
      {
        localContentValues.putNull(str);
      }
      else if ((localObject instanceof String))
      {
        localContentValues.put(str, (String)localObject);
      }
      else if ((localObject instanceof Integer))
      {
        localContentValues.put(str, (Integer)localObject);
      }
      else if ((localObject instanceof Long))
      {
        localContentValues.put(str, (Long)localObject);
      }
      else if ((localObject instanceof Boolean))
      {
        localContentValues.put(str, (Boolean)localObject);
      }
      else if ((localObject instanceof Float))
      {
        localContentValues.put(str, (Float)localObject);
      }
      else if ((localObject instanceof Double))
      {
        localContentValues.put(str, (Double)localObject);
      }
      else if ((localObject instanceof byte[]))
      {
        localContentValues.put(str, (byte[])localObject);
      }
      else if ((localObject instanceof Byte))
      {
        localContentValues.put(str, (Byte)localObject);
      }
      else
      {
        if (!(localObject instanceof Short)) {
          break label263;
        }
        localContentValues.put(str, (Short)localObject);
      }
      i++;
      continue;
      label263:
      paramVarArgs = localObject.getClass().getCanonicalName();
      throw new IllegalArgumentException("Illegal value type " + paramVarArgs + " for key \"" + str + '"');
    }
    return localContentValues;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/ContentValuesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */