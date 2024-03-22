package androidx.emoji2.text;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.MetadataRepo;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class EmojiProcessor {
    private static final int ACTION_ADVANCE_BOTH = 1;
    private static final int ACTION_ADVANCE_END = 2;
    private static final int ACTION_FLUSH = 3;
    private final int[] mEmojiAsDefaultStyleExceptions;
    private EmojiCompat.GlyphChecker mGlyphChecker;
    private final MetadataRepo mMetadataRepo;
    private final EmojiCompat.SpanFactory mSpanFactory;
    private final boolean mUseEmojiAsDefaultStyle;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class CodepointIndexFinder {
        private static final int INVALID_INDEX = -1;

        private CodepointIndexFinder() {
        }

        static int findIndexBackward(CharSequence cs, int from, int numCodePoints) {
            int i = from;
            boolean z = false;
            int length = cs.length();
            if (i < 0 || length < i || numCodePoints < 0) {
                return -1;
            }
            int i2 = numCodePoints;
            while (i2 != 0) {
                i--;
                if (i < 0) {
                    return z ? -1 : 0;
                }
                char charAt = cs.charAt(i);
                if (z) {
                    if (!Character.isHighSurrogate(charAt)) {
                        return -1;
                    }
                    z = false;
                    i2--;
                } else if (!Character.isSurrogate(charAt)) {
                    i2--;
                } else {
                    if (Character.isHighSurrogate(charAt)) {
                        return -1;
                    }
                    z = true;
                }
            }
            return i;
        }

        static int findIndexForward(CharSequence cs, int from, int numCodePoints) {
            int i = from;
            boolean z = false;
            int length = cs.length();
            if (i < 0 || length < i || numCodePoints < 0) {
                return -1;
            }
            int i2 = numCodePoints;
            while (i2 != 0) {
                if (i >= length) {
                    if (z) {
                        return -1;
                    }
                    return length;
                }
                char charAt = cs.charAt(i);
                if (z) {
                    if (!Character.isLowSurrogate(charAt)) {
                        return -1;
                    }
                    i2--;
                    z = false;
                    i++;
                } else if (!Character.isSurrogate(charAt)) {
                    i2--;
                    i++;
                } else {
                    if (Character.isLowSurrogate(charAt)) {
                        return -1;
                    }
                    z = true;
                    i++;
                }
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class ProcessorSm {
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

        ProcessorSm(MetadataRepo.Node rootNode, boolean useEmojiAsDefaultStyle, int[] emojiAsDefaultStyleExceptions) {
            this.mRootNode = rootNode;
            this.mCurrentNode = rootNode;
            this.mUseEmojiAsDefaultStyle = useEmojiAsDefaultStyle;
            this.mEmojiAsDefaultStyleExceptions = emojiAsDefaultStyleExceptions;
        }

        private static boolean isEmojiStyle(int codePoint) {
            return codePoint == 65039;
        }

        private static boolean isTextStyle(int codePoint) {
            return codePoint == 65038;
        }

        private int reset() {
            this.mState = 1;
            this.mCurrentNode = this.mRootNode;
            this.mCurrentDepth = 0;
            return 1;
        }

        private boolean shouldUseEmojiPresentationStyleForSingleCodepoint() {
            if (this.mCurrentNode.getData().isDefaultEmoji() || isEmojiStyle(this.mLastCodepoint)) {
                return true;
            }
            if (this.mUseEmojiAsDefaultStyle) {
                if (this.mEmojiAsDefaultStyleExceptions == null) {
                    return true;
                }
                if (Arrays.binarySearch(this.mEmojiAsDefaultStyleExceptions, this.mCurrentNode.getData().getCodepointAt(0)) < 0) {
                    return true;
                }
            }
            return false;
        }

        int check(int codePoint) {
            int reset;
            MetadataRepo.Node node = this.mCurrentNode.get(codePoint);
            switch (this.mState) {
                case 2:
                    if (node == null) {
                        if (!isTextStyle(codePoint)) {
                            if (!isEmojiStyle(codePoint)) {
                                if (this.mCurrentNode.getData() == null) {
                                    reset = reset();
                                    break;
                                } else if (this.mCurrentDepth != 1) {
                                    this.mFlushNode = this.mCurrentNode;
                                    reset = 3;
                                    reset();
                                    break;
                                } else if (!shouldUseEmojiPresentationStyleForSingleCodepoint()) {
                                    reset = reset();
                                    break;
                                } else {
                                    this.mFlushNode = this.mCurrentNode;
                                    reset = 3;
                                    reset();
                                    break;
                                }
                            } else {
                                reset = 2;
                                break;
                            }
                        } else {
                            reset = reset();
                            break;
                        }
                    } else {
                        this.mCurrentNode = node;
                        this.mCurrentDepth++;
                        reset = 2;
                        break;
                    }
                default:
                    if (node != null) {
                        this.mState = 2;
                        this.mCurrentNode = node;
                        this.mCurrentDepth = 1;
                        reset = 2;
                        break;
                    } else {
                        reset = reset();
                        break;
                    }
            }
            this.mLastCodepoint = codePoint;
            return reset;
        }

        EmojiMetadata getCurrentMetadata() {
            return this.mCurrentNode.getData();
        }

        EmojiMetadata getFlushMetadata() {
            return this.mFlushNode.getData();
        }

        boolean isInFlushableState() {
            return this.mState == 2 && this.mCurrentNode.getData() != null && (this.mCurrentDepth > 1 || shouldUseEmojiPresentationStyleForSingleCodepoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EmojiProcessor(MetadataRepo metadataRepo, EmojiCompat.SpanFactory spanFactory, EmojiCompat.GlyphChecker glyphChecker, boolean useEmojiAsDefaultStyle, int[] emojiAsDefaultStyleExceptions) {
        this.mSpanFactory = spanFactory;
        this.mMetadataRepo = metadataRepo;
        this.mGlyphChecker = glyphChecker;
        this.mUseEmojiAsDefaultStyle = useEmojiAsDefaultStyle;
        this.mEmojiAsDefaultStyleExceptions = emojiAsDefaultStyleExceptions;
    }

    private void addEmoji(Spannable spannable, EmojiMetadata metadata, int start, int end) {
        spannable.setSpan(this.mSpanFactory.createSpan(metadata), start, end, 33);
    }

    private static boolean delete(Editable content, KeyEvent event, boolean forwardDelete) {
        EmojiSpan[] emojiSpanArr;
        if (hasModifiers(event)) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(content);
        int selectionEnd = Selection.getSelectionEnd(content);
        if (!hasInvalidSelection(selectionStart, selectionEnd) && (emojiSpanArr = (EmojiSpan[]) content.getSpans(selectionStart, selectionEnd, EmojiSpan.class)) != null && emojiSpanArr.length > 0) {
            for (EmojiSpan emojiSpan : emojiSpanArr) {
                int spanStart = content.getSpanStart(emojiSpan);
                int spanEnd = content.getSpanEnd(emojiSpan);
                if ((forwardDelete && spanStart == selectionStart) || ((!forwardDelete && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                    content.delete(spanStart, spanEnd);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean handleDeleteSurroundingText(InputConnection inputConnection, Editable editable, int beforeLength, int afterLength, boolean inCodePoints) {
        int max;
        int min;
        if (editable == null || inputConnection == null || beforeLength < 0 || afterLength < 0) {
            return false;
        }
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        if (hasInvalidSelection(selectionStart, selectionEnd)) {
            return false;
        }
        if (inCodePoints) {
            max = CodepointIndexFinder.findIndexBackward(editable, selectionStart, Math.max(beforeLength, 0));
            min = CodepointIndexFinder.findIndexForward(editable, selectionEnd, Math.max(afterLength, 0));
            if (max == -1 || min == -1) {
                return false;
            }
        } else {
            max = Math.max(selectionStart - beforeLength, 0);
            min = Math.min(selectionEnd + afterLength, editable.length());
        }
        EmojiSpan[] emojiSpanArr = (EmojiSpan[]) editable.getSpans(max, min, EmojiSpan.class);
        if (emojiSpanArr == null || emojiSpanArr.length <= 0) {
            return false;
        }
        for (EmojiSpan emojiSpan : emojiSpanArr) {
            int spanStart = editable.getSpanStart(emojiSpan);
            int spanEnd = editable.getSpanEnd(emojiSpan);
            max = Math.min(spanStart, max);
            min = Math.max(spanEnd, min);
        }
        int max2 = Math.max(max, 0);
        int min2 = Math.min(min, editable.length());
        inputConnection.beginBatchEdit();
        editable.delete(max2, min2);
        inputConnection.endBatchEdit();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean handleOnKeyDown(Editable editable, int keyCode, KeyEvent event) {
        boolean delete;
        switch (keyCode) {
            case 67:
                delete = delete(editable, event, false);
                break;
            case 112:
                delete = delete(editable, event, true);
                break;
            default:
                delete = false;
                break;
        }
        if (!delete) {
            return false;
        }
        MetaKeyKeyListener.adjustMetaAfterKeypress(editable);
        return true;
    }

    private boolean hasGlyph(CharSequence charSequence, int start, int end, EmojiMetadata metadata) {
        if (metadata.getHasGlyph() == 0) {
            metadata.setHasGlyph(this.mGlyphChecker.hasGlyph(charSequence, start, end, metadata.getSdkAdded()));
        }
        return metadata.getHasGlyph() == 2;
    }

    private static boolean hasInvalidSelection(int start, int end) {
        return start == -1 || end == -1 || start != end;
    }

    private static boolean hasModifiers(KeyEvent event) {
        return !KeyEvent.metaStateHasNoModifiers(event.getMetaState());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getEmojiMatch(CharSequence charSequence) {
        return getEmojiMatch(charSequence, this.mMetadataRepo.getMetadataVersion());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0044 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0016 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getEmojiMatch(java.lang.CharSequence r10, int r11) {
        /*
            r9 = this;
            androidx.emoji2.text.EmojiProcessor$ProcessorSm r0 = new androidx.emoji2.text.EmojiProcessor$ProcessorSm
            androidx.emoji2.text.MetadataRepo r1 = r9.mMetadataRepo
            androidx.emoji2.text.MetadataRepo$Node r1 = r1.getRootNode()
            boolean r2 = r9.mUseEmojiAsDefaultStyle
            int[] r3 = r9.mEmojiAsDefaultStyleExceptions
            r0.<init>(r1, r2, r3)
            int r1 = r10.length()
            r2 = 0
            r3 = 0
            r4 = 0
        L16:
            if (r2 >= r1) goto L4d
            int r5 = java.lang.Character.codePointAt(r10, r2)
            int r6 = r0.check(r5)
            androidx.emoji2.text.EmojiMetadata r7 = r0.getCurrentMetadata()
            switch(r6) {
                case 1: goto L3b;
                case 2: goto L35;
                case 3: goto L28;
                default: goto L27;
            }
        L27:
            goto L42
        L28:
            androidx.emoji2.text.EmojiMetadata r7 = r0.getFlushMetadata()
            short r8 = r7.getCompatAdded()
            if (r8 > r11) goto L42
            int r4 = r4 + 1
            goto L42
        L35:
            int r8 = java.lang.Character.charCount(r5)
            int r2 = r2 + r8
            goto L42
        L3b:
            int r8 = java.lang.Character.charCount(r5)
            int r2 = r2 + r8
            r3 = 0
        L42:
            if (r7 == 0) goto L4c
            short r8 = r7.getCompatAdded()
            if (r8 > r11) goto L4c
            int r3 = r3 + 1
        L4c:
            goto L16
        L4d:
            r5 = 2
            if (r4 == 0) goto L51
            return r5
        L51:
            boolean r6 = r0.isInFlushableState()
            if (r6 == 0) goto L63
            androidx.emoji2.text.EmojiMetadata r6 = r0.getCurrentMetadata()
            short r7 = r6.getCompatAdded()
            if (r7 > r11) goto L63
            r5 = 1
            return r5
        L63:
            if (r3 != 0) goto L67
            r5 = 0
            return r5
        L67:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiProcessor.getEmojiMatch(java.lang.CharSequence, int):int");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0129, code lost:
    
        ((androidx.emoji2.text.SpannableBuilder) r10).endBatchEdit();
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0048 A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:84:0x000d, B:87:0x0012, B:89:0x0016, B:91:0x0025, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0066, B:28:0x0075, B:29:0x0084, B:32:0x009d, B:33:0x00a1, B:38:0x00a7, B:43:0x00b3, B:44:0x00be, B:45:0x00c9, B:47:0x00d0, B:50:0x00d6, B:52:0x00e2, B:56:0x00e8, B:60:0x00f2, B:63:0x00fe, B:64:0x0104, B:66:0x010f, B:6:0x002c), top: B:83:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c9 A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:84:0x000d, B:87:0x0012, B:89:0x0016, B:91:0x0025, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0066, B:28:0x0075, B:29:0x0084, B:32:0x009d, B:33:0x00a1, B:38:0x00a7, B:43:0x00b3, B:44:0x00be, B:45:0x00c9, B:47:0x00d0, B:50:0x00d6, B:52:0x00e2, B:56:0x00e8, B:60:0x00f2, B:63:0x00fe, B:64:0x0104, B:66:0x010f, B:6:0x002c), top: B:83:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d6 A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:84:0x000d, B:87:0x0012, B:89:0x0016, B:91:0x0025, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0066, B:28:0x0075, B:29:0x0084, B:32:0x009d, B:33:0x00a1, B:38:0x00a7, B:43:0x00b3, B:44:0x00be, B:45:0x00c9, B:47:0x00d0, B:50:0x00d6, B:52:0x00e2, B:56:0x00e8, B:60:0x00f2, B:63:0x00fe, B:64:0x0104, B:66:0x010f, B:6:0x002c), top: B:83:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00fe A[Catch: all -> 0x0130, TryCatch #0 {all -> 0x0130, blocks: (B:84:0x000d, B:87:0x0012, B:89:0x0016, B:91:0x0025, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0066, B:28:0x0075, B:29:0x0084, B:32:0x009d, B:33:0x00a1, B:38:0x00a7, B:43:0x00b3, B:44:0x00be, B:45:0x00c9, B:47:0x00d0, B:50:0x00d6, B:52:0x00e2, B:56:0x00e8, B:60:0x00f2, B:63:0x00fe, B:64:0x0104, B:66:0x010f, B:6:0x002c), top: B:83:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x010f A[Catch: all -> 0x0130, TRY_LEAVE, TryCatch #0 {all -> 0x0130, blocks: (B:84:0x000d, B:87:0x0012, B:89:0x0016, B:91:0x0025, B:8:0x0037, B:10:0x0041, B:12:0x0044, B:14:0x0048, B:16:0x0054, B:18:0x0057, B:22:0x0066, B:28:0x0075, B:29:0x0084, B:32:0x009d, B:33:0x00a1, B:38:0x00a7, B:43:0x00b3, B:44:0x00be, B:45:0x00c9, B:47:0x00d0, B:50:0x00d6, B:52:0x00e2, B:56:0x00e8, B:60:0x00f2, B:63:0x00fe, B:64:0x0104, B:66:0x010f, B:6:0x002c), top: B:83:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.CharSequence process(java.lang.CharSequence r10, int r11, int r12, int r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 324
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.EmojiProcessor.process(java.lang.CharSequence, int, int, int, boolean):java.lang.CharSequence");
    }
}
