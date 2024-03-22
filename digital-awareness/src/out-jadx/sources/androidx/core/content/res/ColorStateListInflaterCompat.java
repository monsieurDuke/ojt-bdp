package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R;
import androidx.core.math.MathUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    private ColorStateListInflaterCompat() {
    }

    public static ColorStateList createFromXml(Resources r, XmlPullParser parser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(parser);
        do {
            next = parser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(r, parser, asAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static ColorStateList createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        String name = parser.getName();
        if (name.equals("selector")) {
            return inflate(r, parser, attrs, theme);
        }
        throw new XmlPullParserException(parser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }

    public static ColorStateList inflate(Resources resources, int resId, Resources.Theme theme) {
        try {
            return createFromXml(resources, resources.getXml(resId), theme);
        } catch (Exception e) {
            Log.e("CSLCompat", "Failed to inflate ColorStateList.", e);
            return null;
        }
    }

    private static ColorStateList inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int i;
        int color;
        Resources resources = r;
        Resources.Theme theme2 = theme;
        int i2 = 1;
        int depth2 = parser.getDepth() + 1;
        int[][] iArr = new int[20];
        int i3 = 0;
        int[] iArr2 = new int[iArr.length];
        int[][] iArr3 = iArr;
        while (true) {
            int next = parser.next();
            int i4 = next;
            if (next != i2 && ((depth = parser.getDepth()) >= depth2 || i4 != 3)) {
                if (i4 != 2 || depth > depth2) {
                    i = depth2;
                } else if (parser.getName().equals("item")) {
                    TypedArray obtainAttributes = obtainAttributes(resources, theme2, attrs, R.styleable.ColorStateListItem);
                    int resourceId = obtainAttributes.getResourceId(R.styleable.ColorStateListItem_android_color, -1);
                    if (resourceId == -1 || isColorInt(resources, resourceId)) {
                        color = obtainAttributes.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                    } else {
                        try {
                            color = createFromXml(resources, resources.getXml(resourceId), theme2).getDefaultColor();
                        } catch (Exception e) {
                            color = obtainAttributes.getColor(R.styleable.ColorStateListItem_android_color, -65281);
                        }
                    }
                    float f = 1.0f;
                    if (obtainAttributes.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                        f = obtainAttributes.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
                    } else if (obtainAttributes.hasValue(R.styleable.ColorStateListItem_alpha)) {
                        f = obtainAttributes.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
                    }
                    float f2 = (Build.VERSION.SDK_INT < 31 || !obtainAttributes.hasValue(R.styleable.ColorStateListItem_android_lStar)) ? obtainAttributes.getFloat(R.styleable.ColorStateListItem_lStar, -1.0f) : obtainAttributes.getFloat(R.styleable.ColorStateListItem_android_lStar, -1.0f);
                    obtainAttributes.recycle();
                    int i5 = 0;
                    int attributeCount = attrs.getAttributeCount();
                    int[] iArr4 = new int[attributeCount];
                    int i6 = 0;
                    while (i6 < attributeCount) {
                        int i7 = depth2;
                        int attributeNameResource = attrs.getAttributeNameResource(i6);
                        int i8 = i4;
                        if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr.alpha && attributeNameResource != R.attr.MT_Bin) {
                            int i9 = i5 + 1;
                            iArr4[i5] = attrs.getAttributeBooleanValue(i6, false) ? attributeNameResource : -attributeNameResource;
                            i5 = i9;
                        }
                        i6++;
                        depth2 = i7;
                        i4 = i8;
                    }
                    int[] trimStateSet = StateSet.trimStateSet(iArr4, i5);
                    iArr2 = GrowingArrayUtils.append(iArr2, i3, modulateColorAlpha(color, f, f2));
                    iArr3 = (int[][]) GrowingArrayUtils.append(iArr3, i3, trimStateSet);
                    i3++;
                    resources = r;
                    theme2 = theme;
                    depth2 = depth2;
                    i2 = 1;
                } else {
                    i = depth2;
                }
                resources = r;
                theme2 = theme;
                depth2 = i;
                i2 = 1;
            }
        }
        int[] iArr5 = new int[i3];
        int[][] iArr6 = new int[i3];
        System.arraycopy(iArr2, 0, iArr5, 0, i3);
        System.arraycopy(iArr3, 0, iArr6, 0, i3);
        return new ColorStateList(iArr6, iArr5);
    }

    private static boolean isColorInt(Resources r, int resId) {
        TypedValue typedValue = getTypedValue();
        r.getValue(resId, typedValue, true);
        return typedValue.type >= 28 && typedValue.type <= 31;
    }

    private static int modulateColorAlpha(int color, float alphaMod, float lStar) {
        boolean z = lStar >= 0.0f && lStar <= 100.0f;
        if (alphaMod == 1.0f && !z) {
            return color;
        }
        int clamp = MathUtils.clamp((int) ((Color.alpha(color) * alphaMod) + 0.5f), 0, 255);
        if (z) {
            CamColor fromColor = CamColor.fromColor(color);
            color = CamColor.toColor(fromColor.getHue(), fromColor.getChroma(), lStar);
        }
        return (16777215 & color) | (clamp << 24);
    }

    private static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        return theme == null ? res.obtainAttributes(set, attrs) : theme.obtainStyledAttributes(set, attrs, 0, 0);
    }
}
