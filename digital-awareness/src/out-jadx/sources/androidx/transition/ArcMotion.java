package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) Math.tan(Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, Styleable.ARC_MOTION);
        XmlPullParser xmlPullParser = (XmlPullParser) attrs;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(obtainStyledAttributes, xmlPullParser, "maximumAngle", 2, DEFAULT_MAX_ANGLE_DEGREES));
        obtainStyledAttributes.recycle();
    }

    private static float toTangent(float arcInDegrees) {
        if (arcInDegrees < 0.0f || arcInDegrees > 90.0f) {
            throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) Math.tan(Math.toRadians(arcInDegrees / 2.0f));
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    @Override // androidx.transition.PathMotion
    public Path getPath(float startX, float startY, float endX, float endY) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        Path path = new Path();
        path.moveTo(startX, startY);
        float f6 = endX - startX;
        float f7 = endY - startY;
        float f8 = (f6 * f6) + (f7 * f7);
        float f9 = (startX + endX) / 2.0f;
        float f10 = (startY + endY) / 2.0f;
        float f11 = f8 * 0.25f;
        boolean z = startY > endY;
        if (Math.abs(f6) < Math.abs(f7)) {
            float abs = Math.abs(f8 / (f7 * 2.0f));
            if (z) {
                f2 = endY + abs;
                f = endX;
            } else {
                f2 = startY + abs;
                f = startX;
            }
            float f12 = this.mMinimumVerticalTangent;
            f3 = f11 * f12 * f12;
        } else {
            float f13 = f8 / (f6 * 2.0f);
            if (z) {
                f = startX + f13;
                f2 = startY;
            } else {
                f = endX - f13;
                f2 = endY;
            }
            float f14 = this.mMinimumHorizontalTangent;
            f3 = f11 * f14 * f14;
        }
        float f15 = f9 - f;
        float f16 = f10 - f2;
        float f17 = (f15 * f15) + (f16 * f16);
        float f18 = this.mMaximumTangent;
        float f19 = f11 * f18 * f18;
        if ((f17 < f3 ? f3 : f17 > f19 ? f19 : 0.0f) != 0.0f) {
            float sqrt = (float) Math.sqrt(r23 / f17);
            f4 = f10 + ((f2 - f10) * sqrt);
            f5 = f9 + ((f - f9) * sqrt);
        } else {
            f4 = f2;
            f5 = f;
        }
        path.cubicTo((startX + f5) / 2.0f, (startY + f4) / 2.0f, (f5 + endX) / 2.0f, (f4 + endY) / 2.0f, endX, endY);
        return path;
    }

    public void setMaximumAngle(float angleInDegrees) {
        this.mMaximumAngle = angleInDegrees;
        this.mMaximumTangent = toTangent(angleInDegrees);
    }

    public void setMinimumHorizontalAngle(float angleInDegrees) {
        this.mMinimumHorizontalAngle = angleInDegrees;
        this.mMinimumHorizontalTangent = toTangent(angleInDegrees);
    }

    public void setMinimumVerticalAngle(float angleInDegrees) {
        this.mMinimumVerticalAngle = angleInDegrees;
        this.mMinimumVerticalTangent = toTangent(angleInDegrees);
    }
}
