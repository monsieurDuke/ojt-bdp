package androidx.core.view.inputmethod;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import androidx.core.util.Preconditions;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class EditorInfoCompat
{
  private static final String CONTENT_MIME_TYPES_INTEROP_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  private static final String CONTENT_MIME_TYPES_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  private static final String CONTENT_SELECTION_END_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END";
  private static final String CONTENT_SELECTION_HEAD_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD";
  private static final String CONTENT_SURROUNDING_TEXT_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT";
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
  public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
  static final int MAX_INITIAL_SELECTION_LENGTH = 1024;
  static final int MEMORY_EFFICIENT_TEXT_LENGTH = 2048;
  
  public static String[] getContentMimeTypes(EditorInfo paramEditorInfo)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      paramEditorInfo = paramEditorInfo.contentMimeTypes;
      if (paramEditorInfo == null) {
        paramEditorInfo = EMPTY_STRING_ARRAY;
      }
      return paramEditorInfo;
    }
    if (paramEditorInfo.extras == null) {
      return EMPTY_STRING_ARRAY;
    }
    String[] arrayOfString2 = paramEditorInfo.extras.getStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    String[] arrayOfString1 = arrayOfString2;
    if (arrayOfString2 == null) {
      arrayOfString1 = paramEditorInfo.extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    }
    if (arrayOfString1 == null) {
      arrayOfString1 = EMPTY_STRING_ARRAY;
    }
    return arrayOfString1;
  }
  
  public static CharSequence getInitialSelectedText(EditorInfo paramEditorInfo, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getInitialSelectedText(paramEditorInfo, paramInt);
    }
    if (paramEditorInfo.extras == null) {
      return null;
    }
    int i = Math.min(paramEditorInfo.initialSelStart, paramEditorInfo.initialSelEnd);
    int k = Math.max(paramEditorInfo.initialSelStart, paramEditorInfo.initialSelEnd);
    int m = paramEditorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD");
    int j = paramEditorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END");
    if ((paramEditorInfo.initialSelStart >= 0) && (paramEditorInfo.initialSelEnd >= 0) && (j - m == k - i))
    {
      paramEditorInfo = paramEditorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT");
      if (paramEditorInfo == null) {
        return null;
      }
      if ((paramInt & 0x1) != 0)
      {
        paramEditorInfo = paramEditorInfo.subSequence(m, j);
      }
      else
      {
        paramEditorInfo = TextUtils.substring(paramEditorInfo, m, j);
        Log5ECF72.a(paramEditorInfo);
        LogE84000.a(paramEditorInfo);
        Log229316.a(paramEditorInfo);
      }
      return paramEditorInfo;
    }
    return null;
  }
  
  public static CharSequence getInitialTextAfterCursor(EditorInfo paramEditorInfo, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getInitialTextAfterCursor(paramEditorInfo, paramInt1, paramInt2);
    }
    if (paramEditorInfo.extras == null) {
      return null;
    }
    CharSequence localCharSequence = paramEditorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT");
    if (localCharSequence == null) {
      return null;
    }
    int i = paramEditorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END");
    paramInt1 = Math.min(paramInt1, localCharSequence.length() - i);
    if ((paramInt2 & 0x1) != 0)
    {
      paramEditorInfo = localCharSequence.subSequence(i, i + paramInt1);
    }
    else
    {
      paramEditorInfo = TextUtils.substring(localCharSequence, i, i + paramInt1);
      Log5ECF72.a(paramEditorInfo);
      LogE84000.a(paramEditorInfo);
      Log229316.a(paramEditorInfo);
    }
    return paramEditorInfo;
  }
  
  public static CharSequence getInitialTextBeforeCursor(EditorInfo paramEditorInfo, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getInitialTextBeforeCursor(paramEditorInfo, paramInt1, paramInt2);
    }
    if (paramEditorInfo.extras == null) {
      return null;
    }
    CharSequence localCharSequence = paramEditorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT");
    if (localCharSequence == null) {
      return null;
    }
    int i = paramEditorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD");
    paramInt1 = Math.min(paramInt1, i);
    if ((paramInt2 & 0x1) != 0)
    {
      paramEditorInfo = localCharSequence.subSequence(i - paramInt1, i);
    }
    else
    {
      paramEditorInfo = TextUtils.substring(localCharSequence, i - paramInt1, i);
      Log5ECF72.a(paramEditorInfo);
      LogE84000.a(paramEditorInfo);
      Log229316.a(paramEditorInfo);
    }
    return paramEditorInfo;
  }
  
  static int getProtocol(EditorInfo paramEditorInfo)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      return 1;
    }
    if (paramEditorInfo.extras == null) {
      return 0;
    }
    boolean bool1 = paramEditorInfo.extras.containsKey("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    boolean bool2 = paramEditorInfo.extras.containsKey("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    if ((bool1) && (bool2)) {
      return 4;
    }
    if (bool1) {
      return 3;
    }
    if (bool2) {
      return 2;
    }
    return 0;
  }
  
  private static boolean isCutOnSurrogate(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default: 
      return false;
    case 1: 
      return Character.isHighSurrogate(paramCharSequence.charAt(paramInt1));
    }
    return Character.isLowSurrogate(paramCharSequence.charAt(paramInt1));
  }
  
  private static boolean isPasswordInputType(int paramInt)
  {
    paramInt &= 0xFFF;
    boolean bool;
    if ((paramInt != 129) && (paramInt != 225) && (paramInt != 18)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static void setContentMimeTypes(EditorInfo paramEditorInfo, String[] paramArrayOfString)
  {
    if (Build.VERSION.SDK_INT >= 25)
    {
      paramEditorInfo.contentMimeTypes = paramArrayOfString;
    }
    else
    {
      if (paramEditorInfo.extras == null) {
        paramEditorInfo.extras = new Bundle();
      }
      paramEditorInfo.extras.putStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
      paramEditorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
    }
  }
  
  public static void setInitialSurroundingSubText(EditorInfo paramEditorInfo, CharSequence paramCharSequence, int paramInt)
  {
    Preconditions.checkNotNull(paramCharSequence);
    if (Build.VERSION.SDK_INT >= 30)
    {
      Api30Impl.setInitialSurroundingSubText(paramEditorInfo, paramCharSequence, paramInt);
      return;
    }
    int i;
    if (paramEditorInfo.initialSelStart > paramEditorInfo.initialSelEnd) {
      i = paramEditorInfo.initialSelEnd - paramInt;
    } else {
      i = paramEditorInfo.initialSelStart - paramInt;
    }
    int j;
    if (paramEditorInfo.initialSelStart > paramEditorInfo.initialSelEnd) {
      j = paramEditorInfo.initialSelStart - paramInt;
    } else {
      j = paramEditorInfo.initialSelEnd - paramInt;
    }
    int k = paramCharSequence.length();
    if ((paramInt >= 0) && (i >= 0) && (j <= k))
    {
      if (isPasswordInputType(paramEditorInfo.inputType))
      {
        setSurroundingText(paramEditorInfo, null, 0, 0);
        return;
      }
      if (k <= 2048)
      {
        setSurroundingText(paramEditorInfo, paramCharSequence, i, j);
        return;
      }
      trimLongSurroundingText(paramEditorInfo, paramCharSequence, i, j);
      return;
    }
    setSurroundingText(paramEditorInfo, null, 0, 0);
  }
  
  public static void setInitialSurroundingText(EditorInfo paramEditorInfo, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      Api30Impl.setInitialSurroundingSubText(paramEditorInfo, paramCharSequence, 0);
    } else {
      setInitialSurroundingSubText(paramEditorInfo, paramCharSequence, 0);
    }
  }
  
  private static void setSurroundingText(EditorInfo paramEditorInfo, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    if (paramEditorInfo.extras == null) {
      paramEditorInfo.extras = new Bundle();
    }
    if (paramCharSequence != null) {
      paramCharSequence = new SpannableStringBuilder(paramCharSequence);
    } else {
      paramCharSequence = null;
    }
    paramEditorInfo.extras.putCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT", paramCharSequence);
    paramEditorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD", paramInt1);
    paramEditorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END", paramInt2);
  }
  
  private static void trimLongSurroundingText(EditorInfo paramEditorInfo, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    int m = paramInt2 - paramInt1;
    int i;
    if (m > 1024) {
      i = 0;
    } else {
      i = m;
    }
    int k = paramCharSequence.length();
    int j = 2048 - i;
    int n = Math.min(k - paramInt2, j - Math.min(paramInt1, (int)(j * 0.8D)));
    int i1 = Math.min(paramInt1, j - n);
    int i2 = paramInt1 - i1;
    k = i1;
    j = i2;
    if (isCutOnSurrogate(paramCharSequence, paramInt1 - i1, 0))
    {
      j = i2 + 1;
      k = i1 - 1;
    }
    paramInt1 = n;
    if (isCutOnSurrogate(paramCharSequence, paramInt2 + n - 1, 1)) {
      paramInt1 = n - 1;
    }
    if (i != m) {
      paramCharSequence = TextUtils.concat(new CharSequence[] { paramCharSequence.subSequence(j, j + k), paramCharSequence.subSequence(paramInt2, paramInt2 + paramInt1) });
    } else {
      paramCharSequence = paramCharSequence.subSequence(j, j + (k + i + paramInt1));
    }
    paramInt1 = 0 + k;
    setSurroundingText(paramEditorInfo, paramCharSequence, paramInt1, paramInt1 + i);
  }
  
  private static class Api30Impl
  {
    static CharSequence getInitialSelectedText(EditorInfo paramEditorInfo, int paramInt)
    {
      return paramEditorInfo.getInitialSelectedText(paramInt);
    }
    
    static CharSequence getInitialTextAfterCursor(EditorInfo paramEditorInfo, int paramInt1, int paramInt2)
    {
      return paramEditorInfo.getInitialTextAfterCursor(paramInt1, paramInt2);
    }
    
    static CharSequence getInitialTextBeforeCursor(EditorInfo paramEditorInfo, int paramInt1, int paramInt2)
    {
      return paramEditorInfo.getInitialTextBeforeCursor(paramInt1, paramInt2);
    }
    
    static void setInitialSurroundingSubText(EditorInfo paramEditorInfo, CharSequence paramCharSequence, int paramInt)
    {
      paramEditorInfo.setInitialSurroundingSubText(paramCharSequence, paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/inputmethod/EditorInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */