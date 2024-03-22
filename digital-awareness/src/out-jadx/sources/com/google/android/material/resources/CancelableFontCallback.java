package com.google.android.material.resources;

import android.graphics.Typeface;

/* loaded from: classes.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    private final ApplyFont applyFont;
    private boolean cancelled;
    private final Typeface fallbackFont;

    /* loaded from: classes.dex */
    public interface ApplyFont {
        void apply(Typeface typeface);
    }

    public CancelableFontCallback(ApplyFont applyFont, Typeface fallbackFont) {
        this.fallbackFont = fallbackFont;
        this.applyFont = applyFont;
    }

    private void updateIfNotCancelled(Typeface updatedFont) {
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(updatedFont);
    }

    public void cancel() {
        this.cancelled = true;
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public void onFontRetrievalFailed(int reason) {
        updateIfNotCancelled(this.fallbackFont);
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public void onFontRetrieved(Typeface font, boolean fontResolvedSynchronously) {
        updateIfNotCancelled(font);
    }
}
