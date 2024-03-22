package androidx.constraintlayout.core.parser;

import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class CLNumber extends CLElement {
    float value;

    public CLNumber(float value) {
        super(null);
        this.value = Float.NaN;
        this.value = value;
    }

    public CLNumber(char[] content) {
        super(content);
        this.value = Float.NaN;
    }

    public static CLElement allocate(char[] content) {
        return new CLNumber(content);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public float getFloat() {
        if (Float.isNaN(this.value)) {
            this.value = Float.parseFloat(content());
        }
        return this.value;
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public int getInt() {
        if (Float.isNaN(this.value)) {
            this.value = Integer.parseInt(content());
        }
        return (int) this.value;
    }

    public boolean isInt() {
        float f = getFloat();
        return ((float) ((int) f)) == f;
    }

    public void putValue(float value) {
        this.value = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toFormattedJSON(int indent, int forceIndent) {
        StringBuilder sb = new StringBuilder();
        addIndent(sb, indent);
        float f = getFloat();
        int i = (int) f;
        if (i == f) {
            sb.append(i);
        } else {
            sb.append(f);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toJSON() {
        float f = getFloat();
        int i = (int) f;
        return ((float) i) == f ? HttpUrl.FRAGMENT_ENCODE_SET + i : HttpUrl.FRAGMENT_ENCODE_SET + f;
    }
}
