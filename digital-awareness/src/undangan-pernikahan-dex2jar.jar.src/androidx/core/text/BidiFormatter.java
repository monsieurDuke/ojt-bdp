package androidx.core.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class BidiFormatter
{
  private static final int DEFAULT_FLAGS = 2;
  static final BidiFormatter DEFAULT_LTR_INSTANCE;
  static final BidiFormatter DEFAULT_RTL_INSTANCE;
  static final TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC;
  private static final int DIR_LTR = -1;
  private static final int DIR_RTL = 1;
  private static final int DIR_UNKNOWN = 0;
  private static final String EMPTY_STRING = "";
  private static final int FLAG_STEREO_RESET = 2;
  private static final char LRE = '‪';
  private static final char LRM = '‎';
  private static final String LRM_STRING;
  private static final char PDF = '‬';
  private static final char RLE = '‫';
  private static final char RLM = '‏';
  private static final String RLM_STRING;
  private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
  private final int mFlags;
  private final boolean mIsRtlContext;
  
  static
  {
    TextDirectionHeuristicCompat localTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    DEFAULT_TEXT_DIRECTION_HEURISTIC = localTextDirectionHeuristicCompat;
    String str = Character.toString('‎');
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    LRM_STRING = str;
    str = Character.toString('‏');
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    RLM_STRING = str;
    DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, localTextDirectionHeuristicCompat);
    DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, localTextDirectionHeuristicCompat);
  }
  
  BidiFormatter(boolean paramBoolean, int paramInt, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    this.mIsRtlContext = paramBoolean;
    this.mFlags = paramInt;
    this.mDefaultTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
  }
  
  private static int getEntryDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getEntryDir();
  }
  
  private static int getExitDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getExitDir();
  }
  
  public static BidiFormatter getInstance()
  {
    return new Builder().build();
  }
  
  public static BidiFormatter getInstance(Locale paramLocale)
  {
    return new Builder(paramLocale).build();
  }
  
  public static BidiFormatter getInstance(boolean paramBoolean)
  {
    return new Builder(paramBoolean).build();
  }
  
  static boolean isRtlLocale(Locale paramLocale)
  {
    int i = TextUtilsCompat.getLayoutDirectionFromLocale(paramLocale);
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private String markAfter(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!this.mIsRtlContext) && ((bool) || (getExitDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((this.mIsRtlContext) && ((!bool) || (getExitDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  private String markBefore(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!this.mIsRtlContext) && ((bool) || (getEntryDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((this.mIsRtlContext) && ((!bool) || (getEntryDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  public boolean getStereoReset()
  {
    boolean bool;
    if ((this.mFlags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isRtl(CharSequence paramCharSequence)
  {
    return this.mDefaultTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  public boolean isRtl(String paramString)
  {
    return isRtl(paramString);
  }
  
  public boolean isRtlContext()
  {
    return this.mIsRtlContext;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence)
  {
    return unicodeWrap(paramCharSequence, this.mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramCharSequence, paramTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramCharSequence == null) {
      return null;
    }
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    if ((getStereoReset()) && (paramBoolean))
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markBefore(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    if (bool != this.mIsRtlContext)
    {
      char c;
      if (bool) {
        c = '‫';
      } else {
        c = '‪';
      }
      localSpannableStringBuilder.append(c);
      localSpannableStringBuilder.append(paramCharSequence);
      localSpannableStringBuilder.append('‬');
    }
    else
    {
      localSpannableStringBuilder.append(paramCharSequence);
    }
    if (paramBoolean)
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markAfter(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    return localSpannableStringBuilder;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, boolean paramBoolean)
  {
    return unicodeWrap(paramCharSequence, this.mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public String unicodeWrap(String paramString)
  {
    return unicodeWrap(paramString, this.mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramString == null) {
      return null;
    }
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, paramBoolean).toString();
  }
  
  public String unicodeWrap(String paramString, boolean paramBoolean)
  {
    return unicodeWrap(paramString, this.mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public static final class Builder
  {
    private int mFlags;
    private boolean mIsRtlContext;
    private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;
    
    public Builder()
    {
      initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
    }
    
    public Builder(Locale paramLocale)
    {
      initialize(BidiFormatter.isRtlLocale(paramLocale));
    }
    
    public Builder(boolean paramBoolean)
    {
      initialize(paramBoolean);
    }
    
    private static BidiFormatter getDefaultInstanceFromContext(boolean paramBoolean)
    {
      BidiFormatter localBidiFormatter;
      if (paramBoolean) {
        localBidiFormatter = BidiFormatter.DEFAULT_RTL_INSTANCE;
      } else {
        localBidiFormatter = BidiFormatter.DEFAULT_LTR_INSTANCE;
      }
      return localBidiFormatter;
    }
    
    private void initialize(boolean paramBoolean)
    {
      this.mIsRtlContext = paramBoolean;
      this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
      this.mFlags = 2;
    }
    
    public BidiFormatter build()
    {
      if ((this.mFlags == 2) && (this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC)) {
        return getDefaultInstanceFromContext(this.mIsRtlContext);
      }
      return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
    }
    
    public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
    {
      this.mTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
      return this;
    }
    
    public Builder stereoReset(boolean paramBoolean)
    {
      if (paramBoolean) {
        this.mFlags |= 0x2;
      } else {
        this.mFlags &= 0xFFFFFFFD;
      }
      return this;
    }
  }
  
  private static class DirectionalityEstimator
  {
    private static final byte[] DIR_TYPE_CACHE = new byte['܀'];
    private static final int DIR_TYPE_CACHE_SIZE = 1792;
    private int charIndex;
    private final boolean isHtml;
    private char lastChar;
    private final int length;
    private final CharSequence text;
    
    static
    {
      for (int i = 0; i < 1792; i++) {
        DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
      }
    }
    
    DirectionalityEstimator(CharSequence paramCharSequence, boolean paramBoolean)
    {
      this.text = paramCharSequence;
      this.isHtml = paramBoolean;
      this.length = paramCharSequence.length();
    }
    
    private static byte getCachedDirectionality(char paramChar)
    {
      byte b;
      if (paramChar < '܀') {
        b = DIR_TYPE_CACHE[paramChar];
      } else {
        b = Character.getDirectionality(paramChar);
      }
      return b;
    }
    
    private byte skipEntityBackward()
    {
      int i = this.charIndex;
      char c;
      do
      {
        int j = this.charIndex;
        if (j <= 0) {
          break;
        }
        CharSequence localCharSequence = this.text;
        j--;
        this.charIndex = j;
        c = localCharSequence.charAt(j);
        this.lastChar = c;
        if (c == '&') {
          return 12;
        }
      } while (c != ';');
      this.charIndex = i;
      this.lastChar = ';';
      return 13;
    }
    
    private byte skipEntityForward()
    {
      char c;
      do
      {
        int i = this.charIndex;
        if (i >= this.length) {
          break;
        }
        CharSequence localCharSequence = this.text;
        this.charIndex = (i + 1);
        c = localCharSequence.charAt(i);
        this.lastChar = c;
      } while (c != ';');
      return 12;
    }
    
    private byte skipTagBackward()
    {
      int j = this.charIndex;
      for (;;)
      {
        int k = this.charIndex;
        if (k <= 0) {
          break;
        }
        CharSequence localCharSequence = this.text;
        k--;
        this.charIndex = k;
        int i = localCharSequence.charAt(k);
        this.lastChar = i;
        if (i == 60) {
          return 12;
        }
        if (i == 62) {
          break;
        }
        if ((i == 34) || (i == 39))
        {
          k = this.lastChar;
          do
          {
            int m = this.charIndex;
            if (m <= 0) {
              break;
            }
            localCharSequence = this.text;
            m--;
            this.charIndex = m;
            i = localCharSequence.charAt(m);
            this.lastChar = i;
          } while (i != k);
        }
      }
      this.charIndex = j;
      this.lastChar = '>';
      return 13;
    }
    
    private byte skipTagForward()
    {
      int j = this.charIndex;
      for (;;)
      {
        int k = this.charIndex;
        if (k >= this.length) {
          break;
        }
        CharSequence localCharSequence = this.text;
        this.charIndex = (k + 1);
        int i = localCharSequence.charAt(k);
        this.lastChar = i;
        if (i == 62) {
          return 12;
        }
        if ((i == 34) || (i == 39))
        {
          k = this.lastChar;
          do
          {
            int m = this.charIndex;
            if (m >= this.length) {
              break;
            }
            localCharSequence = this.text;
            this.charIndex = (m + 1);
            i = localCharSequence.charAt(m);
            this.lastChar = i;
          } while (i != k);
        }
      }
      this.charIndex = j;
      this.lastChar = '<';
      return 13;
    }
    
    byte dirTypeBackward()
    {
      char c = this.text.charAt(this.charIndex - 1);
      this.lastChar = c;
      int i;
      if (Character.isLowSurrogate(c))
      {
        i = Character.codePointBefore(this.text, this.charIndex);
        this.charIndex -= Character.charCount(i);
        return Character.getDirectionality(i);
      }
      this.charIndex -= 1;
      byte b2 = getCachedDirectionality(this.lastChar);
      byte b1 = b2;
      if (this.isHtml)
      {
        i = this.lastChar;
        if (i == 62)
        {
          b1 = skipTagBackward();
        }
        else
        {
          b1 = b2;
          if (i == 59) {
            b1 = skipEntityBackward();
          }
        }
      }
      return b1;
    }
    
    byte dirTypeForward()
    {
      char c = this.text.charAt(this.charIndex);
      this.lastChar = c;
      int i;
      if (Character.isHighSurrogate(c))
      {
        i = Character.codePointAt(this.text, this.charIndex);
        this.charIndex += Character.charCount(i);
        return Character.getDirectionality(i);
      }
      this.charIndex += 1;
      byte b2 = getCachedDirectionality(this.lastChar);
      byte b1 = b2;
      if (this.isHtml)
      {
        i = this.lastChar;
        if (i == 60)
        {
          b1 = skipTagForward();
        }
        else
        {
          b1 = b2;
          if (i == 38) {
            b1 = skipEntityForward();
          }
        }
      }
      return b1;
    }
    
    int getEntryDir()
    {
      this.charIndex = 0;
      int i = 0;
      int k = 0;
      int j = 0;
      while ((this.charIndex < this.length) && (j == 0)) {
        switch (dirTypeForward())
        {
        default: 
          j = i;
          break;
        case 18: 
          i--;
          k = 0;
          break;
        case 16: 
        case 17: 
          i++;
          k = 1;
          break;
        case 14: 
        case 15: 
          i++;
          k = -1;
          break;
        case 9: 
          break;
        case 1: 
        case 2: 
          if (i == 0) {
            return 1;
          }
          j = i;
          break;
        case 0: 
          if (i == 0) {
            return -1;
          }
          j = i;
        }
      }
      if (j == 0) {
        return 0;
      }
      if (k != 0) {
        return k;
      }
      while (this.charIndex > 0) {
        switch (dirTypeBackward())
        {
        default: 
          break;
        case 18: 
          i++;
          break;
        case 16: 
        case 17: 
          if (j == i) {
            return 1;
          }
          i--;
          break;
        case 14: 
        case 15: 
          if (j == i) {
            return -1;
          }
          i--;
        }
      }
      return 0;
    }
    
    int getExitDir()
    {
      this.charIndex = this.length;
      int i = 0;
      int j = 0;
      while (this.charIndex > 0) {
        switch (dirTypeBackward())
        {
        default: 
          if (j == 0) {
            j = i;
          }
          break;
        case 18: 
          i++;
          break;
        case 16: 
        case 17: 
          if (j == i) {
            return 1;
          }
          i--;
          break;
        case 14: 
        case 15: 
          if (j == i) {
            return -1;
          }
          i--;
          break;
        case 9: 
          break;
        case 1: 
        case 2: 
          if (i == 0) {
            return 1;
          }
          if (j == 0) {
            j = i;
          }
          break;
        case 0: 
          if (i == 0) {
            return -1;
          }
          if (j == 0) {
            j = i;
          }
          break;
        }
      }
      return 0;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/BidiFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */