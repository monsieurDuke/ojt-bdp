package androidx.emoji2.text;

import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.core.util.Preconditions;

/* loaded from: classes.dex */
public abstract class EmojiSpan extends ReplacementSpan {
    private final EmojiMetadata mMetadata;
    private final Paint.FontMetricsInt mTmpFontMetrics = new Paint.FontMetricsInt();
    private short mWidth = -1;
    private short mHeight = -1;
    private float mRatio = 1.0f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public EmojiSpan(EmojiMetadata metadata) {
        Preconditions.checkNotNull(metadata, "metadata cannot be null");
        this.mMetadata = metadata;
    }

    public final int getHeight() {
        return this.mHeight;
    }

    public final int getId() {
        return getMetadata().getId();
    }

    public final EmojiMetadata getMetadata() {
        return this.mMetadata;
    }

    final float getRatio() {
        return this.mRatio;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.getFontMetricsInt(this.mTmpFontMetrics);
        this.mRatio = (Math.abs(this.mTmpFontMetrics.descent - this.mTmpFontMetrics.ascent) * 1.0f) / this.mMetadata.getHeight();
        this.mHeight = (short) (this.mMetadata.getHeight() * this.mRatio);
        this.mWidth = (short) (this.mMetadata.getWidth() * this.mRatio);
        if (fm != null) {
            fm.ascent = this.mTmpFontMetrics.ascent;
            fm.descent = this.mTmpFontMetrics.descent;
            fm.top = this.mTmpFontMetrics.top;
            fm.bottom = this.mTmpFontMetrics.bottom;
        }
        return this.mWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int getWidth() {
        return this.mWidth;
    }
}
