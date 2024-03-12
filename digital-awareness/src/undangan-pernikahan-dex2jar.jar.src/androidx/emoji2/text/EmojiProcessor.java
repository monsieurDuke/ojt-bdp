package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import java.util.Arrays;

final class EmojiProcessor
{
  private static final int ACTION_ADVANCE_BOTH = 1;
  private static final int ACTION_ADVANCE_END = 2;
  private static final int ACTION_FLUSH = 3;
  private final int[] mEmojiAsDefaultStyleExceptions;
  private EmojiCompat.GlyphChecker mGlyphChecker;
  private final MetadataRepo mMetadataRepo;
  private final EmojiCompat.SpanFactory mSpanFactory;
  private final boolean mUseEmojiAsDefaultStyle;
  
  EmojiProcessor(MetadataRepo paramMetadataRepo, EmojiCompat.SpanFactory paramSpanFactory, EmojiCompat.GlyphChecker paramGlyphChecker, boolean paramBoolean, int[] paramArrayOfInt)
  {
    this.mSpanFactory = paramSpanFactory;
    this.mMetadataRepo = paramMetadataRepo;
    this.mGlyphChecker = paramGlyphChecker;
    this.mUseEmojiAsDefaultStyle = paramBoolean;
    this.mEmojiAsDefaultStyleExceptions = paramArrayOfInt;
  }
  
  private void addEmoji(Spannable paramSpannable, EmojiMetadata paramEmojiMetadata, int paramInt1, int paramInt2)
  {
    paramSpannable.setSpan(this.mSpanFactory.createSpan(paramEmojiMetadata), paramInt1, paramInt2, 33);
  }
  
  private static boolean delete(Editable paramEditable, KeyEvent paramKeyEvent, boolean paramBoolean)
  {
    if (hasModifiers(paramKeyEvent)) {
      return false;
    }
    int j = Selection.getSelectionStart(paramEditable);
    int i = Selection.getSelectionEnd(paramEditable);
    if (hasInvalidSelection(j, i)) {
      return false;
    }
    paramKeyEvent = (EmojiSpan[])paramEditable.getSpans(j, i, EmojiSpan.class);
    if ((paramKeyEvent != null) && (paramKeyEvent.length > 0))
    {
      int k = paramKeyEvent.length;
      for (i = 0; i < k; i++)
      {
        Object localObject = paramKeyEvent[i];
        int n = paramEditable.getSpanStart(localObject);
        int m = paramEditable.getSpanEnd(localObject);
        if (((paramBoolean) && (n == j)) || ((!paramBoolean) && (m == j)) || ((j > n) && (j < m)))
        {
          paramEditable.delete(n, m);
          return true;
        }
      }
    }
    return false;
  }
  
  static boolean handleDeleteSurroundingText(InputConnection paramInputConnection, Editable paramEditable, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((paramEditable != null) && (paramInputConnection != null))
    {
      if ((paramInt1 >= 0) && (paramInt2 >= 0))
      {
        int j = Selection.getSelectionStart(paramEditable);
        int i = Selection.getSelectionEnd(paramEditable);
        if (hasInvalidSelection(j, i)) {
          return false;
        }
        if (paramBoolean)
        {
          paramInt1 = CodepointIndexFinder.findIndexBackward(paramEditable, j, Math.max(paramInt1, 0));
          i = CodepointIndexFinder.findIndexForward(paramEditable, i, Math.max(paramInt2, 0));
          if (paramInt1 != -1)
          {
            paramInt2 = i;
            if (i != -1) {}
          }
          else
          {
            return false;
          }
        }
        else
        {
          paramInt1 = Math.max(j - paramInt1, 0);
          paramInt2 = Math.min(i + paramInt2, paramEditable.length());
        }
        EmojiSpan[] arrayOfEmojiSpan = (EmojiSpan[])paramEditable.getSpans(paramInt1, paramInt2, EmojiSpan.class);
        if ((arrayOfEmojiSpan != null) && (arrayOfEmojiSpan.length > 0))
        {
          j = arrayOfEmojiSpan.length;
          for (i = 0; i < j; i++)
          {
            EmojiSpan localEmojiSpan = arrayOfEmojiSpan[i];
            int m = paramEditable.getSpanStart(localEmojiSpan);
            int k = paramEditable.getSpanEnd(localEmojiSpan);
            paramInt1 = Math.min(m, paramInt1);
            paramInt2 = Math.max(k, paramInt2);
          }
          paramInt1 = Math.max(paramInt1, 0);
          paramInt2 = Math.min(paramInt2, paramEditable.length());
          paramInputConnection.beginBatchEdit();
          paramEditable.delete(paramInt1, paramInt2);
          paramInputConnection.endBatchEdit();
          return true;
        }
        return false;
      }
      return false;
    }
    return false;
  }
  
  static boolean handleOnKeyDown(Editable paramEditable, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool;
    switch (paramInt)
    {
    default: 
      bool = false;
      break;
    case 112: 
      bool = delete(paramEditable, paramKeyEvent, true);
      break;
    case 67: 
      bool = delete(paramEditable, paramKeyEvent, false);
    }
    if (bool)
    {
      MetaKeyKeyListener.adjustMetaAfterKeypress(paramEditable);
      return true;
    }
    return false;
  }
  
  private boolean hasGlyph(CharSequence paramCharSequence, int paramInt1, int paramInt2, EmojiMetadata paramEmojiMetadata)
  {
    if (paramEmojiMetadata.getHasGlyph() == 0) {
      paramEmojiMetadata.setHasGlyph(this.mGlyphChecker.hasGlyph(paramCharSequence, paramInt1, paramInt2, paramEmojiMetadata.getSdkAdded()));
    }
    boolean bool;
    if (paramEmojiMetadata.getHasGlyph() == 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean hasInvalidSelection(int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((paramInt1 != -1) && (paramInt2 != -1) && (paramInt1 == paramInt2)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean hasModifiers(KeyEvent paramKeyEvent)
  {
    return KeyEvent.metaStateHasNoModifiers(paramKeyEvent.getMetaState()) ^ true;
  }
  
  int getEmojiMatch(CharSequence paramCharSequence)
  {
    return getEmojiMatch(paramCharSequence, this.mMetadataRepo.getMetadataVersion());
  }
  
  int getEmojiMatch(CharSequence paramCharSequence, int paramInt)
  {
    ProcessorSm localProcessorSm = new ProcessorSm(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
    int i2 = paramCharSequence.length();
    int n = 0;
    int i = 0;
    int i1;
    for (int m = 0; n < i2; m = i1)
    {
      int j = Character.codePointAt(paramCharSequence, n);
      int k = localProcessorSm.check(j);
      Object localObject = localProcessorSm.getCurrentMetadata();
      switch (k)
      {
      default: 
        j = n;
        k = i;
        i1 = m;
        break;
      case 3: 
        EmojiMetadata localEmojiMetadata = localProcessorSm.getFlushMetadata();
        j = n;
        k = i;
        i1 = m;
        localObject = localEmojiMetadata;
        if (localEmojiMetadata.getCompatAdded() <= paramInt)
        {
          i1 = m + 1;
          j = n;
          k = i;
          localObject = localEmojiMetadata;
        }
        break;
      case 2: 
        j = n + Character.charCount(j);
        k = i;
        i1 = m;
        break;
      case 1: 
        j = n + Character.charCount(j);
        k = 0;
        i1 = m;
      }
      i = k;
      if (localObject != null)
      {
        i = k;
        if (((EmojiMetadata)localObject).getCompatAdded() <= paramInt) {
          i = k + 1;
        }
      }
      n = j;
    }
    if (m != 0) {
      return 2;
    }
    if ((localProcessorSm.isInFlushableState()) && (localProcessorSm.getCurrentMetadata().getCompatAdded() <= paramInt)) {
      return 1;
    }
    if (i == 0) {
      return 0;
    }
    return 2;
  }
  
  CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    boolean bool = paramCharSequence instanceof SpannableBuilder;
    if (bool) {
      ((SpannableBuilder)paramCharSequence).beginBatchEdit();
    }
    Object localObject3 = null;
    if (!bool) {}
    try
    {
      Object localObject1;
      if (!(paramCharSequence instanceof Spannable))
      {
        localObject1 = localObject3;
        if ((paramCharSequence instanceof Spanned))
        {
          localObject1 = localObject3;
          if (((Spanned)paramCharSequence).nextSpanTransition(paramInt1 - 1, paramInt2 + 1, EmojiSpan.class) <= paramInt2) {
            localObject1 = new UnprecomputeTextOnModificationSpannable(paramCharSequence);
          }
        }
      }
      else
      {
        localObject1 = new androidx/emoji2/text/UnprecomputeTextOnModificationSpannable;
        ((UnprecomputeTextOnModificationSpannable)localObject1).<init>((Spannable)paramCharSequence);
      }
      int i = paramInt1;
      int j = paramInt2;
      Object localObject4;
      int m;
      int k;
      if (localObject1 != null)
      {
        localObject4 = (EmojiSpan[])((UnprecomputeTextOnModificationSpannable)localObject1).getSpans(paramInt1, paramInt2, EmojiSpan.class);
        i = paramInt1;
        j = paramInt2;
        if (localObject4 != null)
        {
          i = paramInt1;
          j = paramInt2;
          if (localObject4.length > 0)
          {
            m = localObject4.length;
            for (k = 0;; k++)
            {
              i = paramInt1;
              j = paramInt2;
              if (k >= m) {
                break;
              }
              localObject3 = localObject4[k];
              j = ((UnprecomputeTextOnModificationSpannable)localObject1).getSpanStart(localObject3);
              i = ((UnprecomputeTextOnModificationSpannable)localObject1).getSpanEnd(localObject3);
              if (j != paramInt2) {
                ((UnprecomputeTextOnModificationSpannable)localObject1).removeSpan(localObject3);
              }
              paramInt1 = Math.min(j, paramInt1);
              paramInt2 = Math.max(i, paramInt2);
            }
          }
        }
      }
      if ((i != j) && (i < paramCharSequence.length()))
      {
        k = paramInt3;
        if (paramInt3 != Integer.MAX_VALUE)
        {
          k = paramInt3;
          if (localObject1 != null) {
            k = paramInt3 - ((EmojiSpan[])((UnprecomputeTextOnModificationSpannable)localObject1).getSpans(0, ((UnprecomputeTextOnModificationSpannable)localObject1).length(), EmojiSpan.class)).length;
          }
        }
        paramInt1 = 0;
        localObject4 = new androidx/emoji2/text/EmojiProcessor$ProcessorSm;
        ((ProcessorSm)localObject4).<init>(this.mMetadataRepo.getRootNode(), this.mUseEmojiAsDefaultStyle, this.mEmojiAsDefaultStyleExceptions);
        paramInt3 = i;
        paramInt2 = Character.codePointAt(paramCharSequence, paramInt3);
        m = i;
        i = paramInt2;
        while ((paramInt3 < j) && (paramInt1 < k))
        {
          int n;
          int i1;
          switch (((ProcessorSm)localObject4).check(i))
          {
          default: 
            localObject3 = localObject1;
            n = paramInt1;
            paramInt2 = m;
            break;
          case 3: 
            if (!paramBoolean)
            {
              localObject3 = localObject1;
              paramInt2 = paramInt1;
              if (hasGlyph(paramCharSequence, m, paramInt3, ((ProcessorSm)localObject4).getFlushMetadata())) {}
            }
            else
            {
              localObject3 = localObject1;
              if (localObject1 == null)
              {
                localObject3 = new androidx/emoji2/text/UnprecomputeTextOnModificationSpannable;
                localObject1 = new android/text/SpannableString;
                ((SpannableString)localObject1).<init>(paramCharSequence);
                ((UnprecomputeTextOnModificationSpannable)localObject3).<init>((Spannable)localObject1);
              }
              addEmoji((Spannable)localObject3, ((ProcessorSm)localObject4).getFlushMetadata(), m, paramInt3);
              paramInt2 = paramInt1 + 1;
            }
            paramInt1 = paramInt3;
            n = paramInt2;
            paramInt2 = paramInt1;
            break;
          case 2: 
            i1 = paramInt3 + Character.charCount(i);
            localObject3 = localObject1;
            n = paramInt1;
            paramInt3 = i1;
            paramInt2 = m;
            if (i1 < j)
            {
              i = Character.codePointAt(paramCharSequence, i1);
              localObject3 = localObject1;
              n = paramInt1;
              paramInt3 = i1;
              paramInt2 = m;
            }
            break;
          case 1: 
            i1 = m + Character.charCount(Character.codePointAt(paramCharSequence, m));
            m = i1;
            localObject3 = localObject1;
            n = paramInt1;
            paramInt3 = m;
            paramInt2 = i1;
            if (m < j)
            {
              i = Character.codePointAt(paramCharSequence, m);
              paramInt2 = i1;
              paramInt3 = m;
              n = paramInt1;
              localObject3 = localObject1;
            }
            break;
          }
          localObject1 = localObject3;
          paramInt1 = n;
          m = paramInt2;
        }
        localObject3 = localObject1;
        if (((ProcessorSm)localObject4).isInFlushableState())
        {
          localObject3 = localObject1;
          if (paramInt1 < k) {
            if (!paramBoolean)
            {
              localObject3 = localObject1;
              if (hasGlyph(paramCharSequence, m, paramInt3, ((ProcessorSm)localObject4).getCurrentMetadata())) {}
            }
            else
            {
              localObject3 = localObject1;
              if (localObject1 == null)
              {
                localObject3 = new androidx/emoji2/text/UnprecomputeTextOnModificationSpannable;
                ((UnprecomputeTextOnModificationSpannable)localObject3).<init>(paramCharSequence);
              }
              addEmoji((Spannable)localObject3, ((ProcessorSm)localObject4).getCurrentMetadata(), m, paramInt3);
            }
          }
        }
        if (localObject3 != null)
        {
          localObject1 = ((UnprecomputeTextOnModificationSpannable)localObject3).getUnwrappedSpannable();
          return (CharSequence)localObject1;
        }
        return paramCharSequence;
      }
      return paramCharSequence;
    }
    finally
    {
      if (bool) {
        ((SpannableBuilder)paramCharSequence).endBatchEdit();
      }
    }
  }
  
  private static final class CodepointIndexFinder
  {
    private static final int INVALID_INDEX = -1;
    
    static int findIndexBackward(CharSequence paramCharSequence, int paramInt1, int paramInt2)
    {
      int i = paramInt1;
      int j = 0;
      paramInt1 = paramCharSequence.length();
      if ((i >= 0) && (paramInt1 >= i))
      {
        if (paramInt2 < 0) {
          return -1;
        }
        paramInt1 = paramInt2;
        paramInt2 = j;
        for (;;)
        {
          if (paramInt1 == 0) {
            return i;
          }
          i--;
          if (i < 0)
          {
            if (paramInt2 != 0) {
              return -1;
            }
            return 0;
          }
          char c = paramCharSequence.charAt(i);
          if (paramInt2 != 0)
          {
            if (!Character.isHighSurrogate(c)) {
              return -1;
            }
            paramInt2 = 0;
            paramInt1--;
          }
          else if (!Character.isSurrogate(c))
          {
            paramInt1--;
          }
          else
          {
            if (Character.isHighSurrogate(c)) {
              return -1;
            }
            paramInt2 = 1;
          }
        }
      }
      return -1;
    }
    
    static int findIndexForward(CharSequence paramCharSequence, int paramInt1, int paramInt2)
    {
      int j = 0;
      int k = paramCharSequence.length();
      if ((paramInt1 >= 0) && (k >= paramInt1))
      {
        if (paramInt2 < 0) {
          return -1;
        }
        int i = paramInt2;
        paramInt2 = j;
        for (;;)
        {
          if (i == 0) {
            return paramInt1;
          }
          if (paramInt1 >= k)
          {
            if (paramInt2 != 0) {
              return -1;
            }
            return k;
          }
          char c = paramCharSequence.charAt(paramInt1);
          if (paramInt2 != 0)
          {
            if (!Character.isLowSurrogate(c)) {
              return -1;
            }
            i--;
            paramInt2 = 0;
            paramInt1++;
          }
          else if (!Character.isSurrogate(c))
          {
            i--;
            paramInt1++;
          }
          else
          {
            if (Character.isLowSurrogate(c)) {
              return -1;
            }
            paramInt2 = 1;
            paramInt1++;
          }
        }
      }
      return -1;
    }
  }
  
  static final class ProcessorSm
  {
    private static final int STATE_DEFAULT = 1;
    private static final int STATE_WALKING = 2;
    private int mCurrentDepth;
    private MetadataRepo.Node mCurrentNode;
    private final int[] mEmojiAsDefaultStyleExceptions;
    private MetadataRepo.Node mFlushNode;
    private int mLastCodepoint;
    private final MetadataRepo.Node mRootNode;
    private int mState = 1;
    private final boolean mUseEmojiAsDefaultStyle;
    
    ProcessorSm(MetadataRepo.Node paramNode, boolean paramBoolean, int[] paramArrayOfInt)
    {
      this.mRootNode = paramNode;
      this.mCurrentNode = paramNode;
      this.mUseEmojiAsDefaultStyle = paramBoolean;
      this.mEmojiAsDefaultStyleExceptions = paramArrayOfInt;
    }
    
    private static boolean isEmojiStyle(int paramInt)
    {
      boolean bool;
      if (paramInt == 65039) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private static boolean isTextStyle(int paramInt)
    {
      boolean bool;
      if (paramInt == 65038) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private int reset()
    {
      this.mState = 1;
      this.mCurrentNode = this.mRootNode;
      this.mCurrentDepth = 0;
      return 1;
    }
    
    private boolean shouldUseEmojiPresentationStyleForSingleCodepoint()
    {
      if (this.mCurrentNode.getData().isDefaultEmoji()) {
        return true;
      }
      if (isEmojiStyle(this.mLastCodepoint)) {
        return true;
      }
      if (this.mUseEmojiAsDefaultStyle)
      {
        if (this.mEmojiAsDefaultStyleExceptions == null) {
          return true;
        }
        int i = this.mCurrentNode.getData().getCodepointAt(0);
        if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, i) < 0) {
          return true;
        }
      }
      return false;
    }
    
    int check(int paramInt)
    {
      MetadataRepo.Node localNode = this.mCurrentNode.get(paramInt);
      switch (this.mState)
      {
      default: 
        if (localNode == null) {
          i = reset();
        }
        break;
      case 2: 
        if (localNode != null)
        {
          this.mCurrentNode = localNode;
          this.mCurrentDepth += 1;
          i = 2;
        }
        else if (isTextStyle(paramInt))
        {
          i = reset();
        }
        else if (isEmojiStyle(paramInt))
        {
          i = 2;
        }
        else if (this.mCurrentNode.getData() != null)
        {
          if (this.mCurrentDepth == 1)
          {
            if (shouldUseEmojiPresentationStyleForSingleCodepoint())
            {
              this.mFlushNode = this.mCurrentNode;
              i = 3;
              reset();
            }
            else
            {
              i = reset();
            }
          }
          else
          {
            this.mFlushNode = this.mCurrentNode;
            i = 3;
            reset();
          }
        }
        else
        {
          i = reset();
        }
        break;
      }
      this.mState = 2;
      this.mCurrentNode = localNode;
      this.mCurrentDepth = 1;
      int i = 2;
      this.mLastCodepoint = paramInt;
      return i;
    }
    
    EmojiMetadata getCurrentMetadata()
    {
      return this.mCurrentNode.getData();
    }
    
    EmojiMetadata getFlushMetadata()
    {
      return this.mFlushNode.getData();
    }
    
    boolean isInFlushableState()
    {
      int i = this.mState;
      boolean bool = true;
      if ((i != 2) || (this.mCurrentNode.getData() == null) || ((this.mCurrentDepth > 1) || (!shouldUseEmojiPresentationStyleForSingleCodepoint()))) {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/EmojiProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */