package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import java.io.IOException;
import java.util.ArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: 0098.java */
/* loaded from: classes.dex */
public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.PathDataNode[] nodeArray) {
            this.mNodeArray = nodeArray;
        }

        @Override // android.animation.TypeEvaluator
        public PathParser.PathDataNode[] evaluate(float fraction, PathParser.PathDataNode[] startPathData, PathParser.PathDataNode[] endPathData) {
            if (!PathParser.canMorph(startPathData, endPathData)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            if (!PathParser.canMorph(this.mNodeArray, startPathData)) {
                this.mNodeArray = PathParser.deepCopyNodes(startPathData);
            }
            for (int i = 0; i < startPathData.length; i++) {
                this.mNodeArray[i].interpolatePathDataNode(startPathData[i], endPathData[i], fraction);
            }
            return this.mNodeArray;
        }
    }

    private AnimatorInflaterCompat() {
    }

    private static Animator createAnimatorFromXml(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, float pixelSize) throws XmlPullParserException, IOException {
        return createAnimatorFromXml(context, res, theme, parser, Xml.asAttributeSet(parser), null, 0, pixelSize);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0100, code lost:
    
        r0 = new android.animation.Animator[r13.size()];
        r2 = 0;
        r3 = r13.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x010f, code lost:
    
        if (r3.hasNext() == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0111, code lost:
    
        r0[r2] = (android.animation.Animator) r3.next();
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x011d, code lost:
    
        if (r26 != 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x011f, code lost:
    
        r25.playTogether(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0123, code lost:
    
        r25.playSequentially(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0126, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x00fc, code lost:
    
        if (r25 == null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x00fe, code lost:
    
        if (r13 == null) goto L49;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.animation.Animator createAnimatorFromXml(android.content.Context r20, android.content.res.Resources r21, android.content.res.Resources.Theme r22, org.xmlpull.v1.XmlPullParser r23, android.util.AttributeSet r24, android.animation.AnimatorSet r25, int r26, float r27) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 295
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat.createAnimatorFromXml(android.content.Context, android.content.res.Resources, android.content.res.Resources$Theme, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.animation.AnimatorSet, int, float):android.animation.Animator");
    }

    private static Keyframe createNewKeyframe(Keyframe sampleKeyframe, float fraction) {
        return sampleKeyframe.getType() == Float.TYPE ? Keyframe.ofFloat(fraction) : sampleKeyframe.getType() == Integer.TYPE ? Keyframe.ofInt(fraction) : Keyframe.ofObject(fraction);
    }

    private static void distributeKeyframes(Keyframe[] keyframes, float gap, int startIndex, int endIndex) {
        float f = gap / ((endIndex - startIndex) + 2);
        for (int i = startIndex; i <= endIndex; i++) {
            keyframes[i].setFraction(keyframes[i - 1].getFraction() + f);
        }
    }

    private static void dumpKeyframes(Object[] keyframes, String header) {
        if (keyframes == null || keyframes.length == 0) {
            return;
        }
        Log.d(TAG, header);
        int length = keyframes.length;
        for (int i = 0; i < length; i++) {
            Keyframe keyframe = (Keyframe) keyframes[i];
            Object obj = "null";
            StringBuilder append = new StringBuilder().append("Keyframe ").append(i).append(": fraction ").append(keyframe.getFraction() < 0.0f ? "null" : Float.valueOf(keyframe.getFraction())).append(", , value : ");
            if (keyframe.hasValue()) {
                obj = keyframe.getValue();
            }
            Log.d(TAG, append.append(obj).toString());
        }
    }

    private static PropertyValuesHolder getPVH(TypedArray styledAttributes, int valueType, int valueFromId, int valueToId, String propertyName) {
        PropertyValuesHolder propertyValuesHolder;
        char c;
        int i;
        char c2;
        int i2;
        int i3;
        PropertyValuesHolder propertyValuesHolder2;
        TypedValue peekValue = styledAttributes.peekValue(valueFromId);
        boolean z = peekValue != null;
        int i4 = z ? peekValue.type : 0;
        TypedValue peekValue2 = styledAttributes.peekValue(valueToId);
        boolean z2 = peekValue2 != null;
        int i5 = z2 ? peekValue2.type : 0;
        int i6 = valueType == 4 ? ((z && isColorType(i4)) || (z2 && isColorType(i5))) ? 3 : 0 : valueType;
        boolean z3 = i6 == 0;
        if (i6 == 2) {
            String string = styledAttributes.getString(valueFromId);
            String string2 = styledAttributes.getString(valueToId);
            PathParser.PathDataNode[] createNodesFromPathData = PathParser.createNodesFromPathData(string);
            PathParser.PathDataNode[] createNodesFromPathData2 = PathParser.createNodesFromPathData(string2);
            if (createNodesFromPathData == null && createNodesFromPathData2 == null) {
                i3 = i5;
                propertyValuesHolder2 = null;
            } else if (createNodesFromPathData != null) {
                PathDataEvaluator pathDataEvaluator = new PathDataEvaluator();
                if (createNodesFromPathData2 == null) {
                    i3 = i5;
                    propertyValuesHolder = PropertyValuesHolder.ofObject(propertyName, pathDataEvaluator, createNodesFromPathData);
                } else {
                    if (!PathParser.canMorph(createNodesFromPathData, createNodesFromPathData2)) {
                        throw new InflateException(" Can't morph from " + string + " to " + string2);
                    }
                    propertyValuesHolder = PropertyValuesHolder.ofObject(propertyName, pathDataEvaluator, createNodesFromPathData, createNodesFromPathData2);
                    i3 = i5;
                }
            } else {
                i3 = i5;
                propertyValuesHolder2 = null;
                if (createNodesFromPathData2 != null) {
                    propertyValuesHolder = PropertyValuesHolder.ofObject(propertyName, new PathDataEvaluator(), createNodesFromPathData2);
                }
            }
            propertyValuesHolder = propertyValuesHolder2;
        } else {
            int i7 = i5;
            ArgbEvaluator argbEvaluator = i6 == 3 ? ArgbEvaluator.getInstance() : null;
            if (z3) {
                if (z) {
                    float dimension = i4 == 5 ? styledAttributes.getDimension(valueFromId, 0.0f) : styledAttributes.getFloat(valueFromId, 0.0f);
                    if (z2) {
                        propertyValuesHolder = PropertyValuesHolder.ofFloat(propertyName, dimension, i7 == 5 ? styledAttributes.getDimension(valueToId, 0.0f) : styledAttributes.getFloat(valueToId, 0.0f));
                    } else {
                        propertyValuesHolder = PropertyValuesHolder.ofFloat(propertyName, dimension);
                    }
                } else {
                    propertyValuesHolder = PropertyValuesHolder.ofFloat(propertyName, i7 == 5 ? styledAttributes.getDimension(valueToId, 0.0f) : styledAttributes.getFloat(valueToId, 0.0f));
                }
            } else if (z) {
                int dimension2 = i4 == 5 ? (int) styledAttributes.getDimension(valueFromId, 0.0f) : isColorType(i4) ? styledAttributes.getColor(valueFromId, 0) : styledAttributes.getInt(valueFromId, 0);
                if (z2) {
                    if (i7 == 5) {
                        i2 = (int) styledAttributes.getDimension(valueToId, 0.0f);
                        c2 = 0;
                    } else if (isColorType(i7)) {
                        c2 = 0;
                        i2 = styledAttributes.getColor(valueToId, 0);
                    } else {
                        c2 = 0;
                        i2 = styledAttributes.getInt(valueToId, 0);
                    }
                    int[] iArr = new int[2];
                    iArr[c2] = dimension2;
                    iArr[1] = i2;
                    propertyValuesHolder = PropertyValuesHolder.ofInt(propertyName, iArr);
                } else {
                    propertyValuesHolder = PropertyValuesHolder.ofInt(propertyName, dimension2);
                }
            } else if (z2) {
                if (i7 == 5) {
                    i = (int) styledAttributes.getDimension(valueToId, 0.0f);
                    c = 0;
                } else if (isColorType(i7)) {
                    c = 0;
                    i = styledAttributes.getColor(valueToId, 0);
                } else {
                    c = 0;
                    i = styledAttributes.getInt(valueToId, 0);
                }
                int[] iArr2 = new int[1];
                iArr2[c] = i;
                propertyValuesHolder = PropertyValuesHolder.ofInt(propertyName, iArr2);
            } else {
                propertyValuesHolder = null;
            }
            if (propertyValuesHolder != null && argbEvaluator != null) {
                propertyValuesHolder.setEvaluator(argbEvaluator);
            }
        }
        return propertyValuesHolder;
    }

    private static int inferValueTypeFromValues(TypedArray styledAttributes, int valueFromId, int valueToId) {
        TypedValue peekValue = styledAttributes.peekValue(valueFromId);
        boolean z = peekValue != null;
        int i = z ? peekValue.type : 0;
        TypedValue peekValue2 = styledAttributes.peekValue(valueToId);
        boolean z2 = peekValue2 != null;
        return ((z && isColorType(i)) || (z2 && isColorType(z2 ? peekValue2.type : 0))) ? 3 : 0;
    }

    private static int inferValueTypeOfKeyframe(Resources res, Resources.Theme theme, AttributeSet attrs, XmlPullParser parser) {
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        TypedValue peekNamedValue = TypedArrayUtils.peekNamedValue(obtainAttributes, parser, "value", 0);
        int i = ((peekNamedValue != null) && isColorType(peekNamedValue.type)) ? 3 : 0;
        obtainAttributes.recycle();
        return i;
    }

    private static boolean isColorType(int type) {
        return type >= 28 && type <= 31;
    }

    public static Animator loadAnimator(Context context, int id) throws Resources.NotFoundException {
        return Build.VERSION.SDK_INT >= 24 ? AnimatorInflater.loadAnimator(context, id) : loadAnimator(context, context.getResources(), context.getTheme(), id);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int id) throws Resources.NotFoundException {
        return loadAnimator(context, resources, theme, id, 1.0f);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int i, float f) throws Resources.NotFoundException {
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    xmlResourceParser = resources.getAnimation(i);
                    return createAnimatorFromXml(context, resources, theme, xmlResourceParser, f);
                } catch (IOException e) {
                    StringBuilder append = new StringBuilder().append("Can't load animation resource ID #0x");
                    String hexString = Integer.toHexString(i);
                    Log5ECF72.a(hexString);
                    LogE84000.a(hexString);
                    Log229316.a(hexString);
                    Resources.NotFoundException notFoundException = new Resources.NotFoundException(append.append(hexString).toString());
                    notFoundException.initCause(e);
                    throw notFoundException;
                }
            } catch (XmlPullParserException e2) {
                StringBuilder append2 = new StringBuilder().append("Can't load animation resource ID #0x");
                String hexString2 = Integer.toHexString(i);
                Log5ECF72.a(hexString2);
                LogE84000.a(hexString2);
                Log229316.a(hexString2);
                Resources.NotFoundException notFoundException2 = new Resources.NotFoundException(append2.append(hexString2).toString());
                notFoundException2.initCause(e2);
                throw notFoundException2;
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    private static ValueAnimator loadAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, ValueAnimator anim, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_ANIMATOR);
        TypedArray obtainAttributes2 = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        if (anim == null) {
            anim = new ValueAnimator();
        }
        parseAnimatorFromTypeArray(anim, obtainAttributes, obtainAttributes2, pathErrorScale, parser);
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainAttributes, parser, "interpolator", 0, 0);
        if (namedResourceId > 0) {
            anim.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, namedResourceId));
        }
        obtainAttributes.recycle();
        if (obtainAttributes2 != null) {
            obtainAttributes2.recycle();
        }
        return anim;
    }

    private static Keyframe loadKeyframe(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, int valueType, XmlPullParser parser) throws XmlPullParserException, IOException {
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(res, theme, attrs, AndroidResources.STYLEABLE_KEYFRAME);
        Keyframe keyframe = null;
        float namedFloat = TypedArrayUtils.getNamedFloat(obtainAttributes, parser, "fraction", 3, -1.0f);
        TypedValue peekNamedValue = TypedArrayUtils.peekNamedValue(obtainAttributes, parser, "value", 0);
        boolean z = peekNamedValue != null;
        if (valueType == 4) {
            valueType = (z && isColorType(peekNamedValue.type)) ? 3 : 0;
        }
        if (z) {
            switch (valueType) {
                case 0:
                    keyframe = Keyframe.ofFloat(namedFloat, TypedArrayUtils.getNamedFloat(obtainAttributes, parser, "value", 0, 0.0f));
                    break;
                case 1:
                case 3:
                    keyframe = Keyframe.ofInt(namedFloat, TypedArrayUtils.getNamedInt(obtainAttributes, parser, "value", 0, 0));
                    break;
            }
        } else {
            keyframe = valueType == 0 ? Keyframe.ofFloat(namedFloat) : Keyframe.ofInt(namedFloat);
        }
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainAttributes, parser, "interpolator", 1, 0);
        if (namedResourceId > 0) {
            keyframe.setInterpolator(AnimationUtilsCompat.loadInterpolator(context, namedResourceId));
        }
        obtainAttributes.recycle();
        return keyframe;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources res, Resources.Theme theme, AttributeSet attrs, float pathErrorScale, XmlPullParser parser) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        loadAnimator(context, res, theme, attrs, objectAnimator, pathErrorScale, parser);
        return objectAnimator;
    }

    private static PropertyValuesHolder loadPvh(Context context, Resources res, Resources.Theme theme, XmlPullParser parser, String propertyName, int valueType) throws XmlPullParserException, IOException {
        int i;
        PropertyValuesHolder propertyValuesHolder;
        Object obj;
        ArrayList arrayList;
        int i2;
        float f;
        Object obj2 = null;
        ArrayList arrayList2 = null;
        int i3 = valueType;
        while (true) {
            int next = parser.next();
            i = next;
            if (next == 3 || i == 1) {
                break;
            }
            if (parser.getName().equals("keyframe")) {
                if (i3 == 4) {
                    i3 = inferValueTypeOfKeyframe(res, theme, Xml.asAttributeSet(parser), parser);
                }
                Keyframe loadKeyframe = loadKeyframe(context, res, theme, Xml.asAttributeSet(parser), i3, parser);
                if (loadKeyframe != null) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(loadKeyframe);
                }
                parser.next();
            }
        }
        if (arrayList2 != null) {
            int size = arrayList2.size();
            int i4 = size;
            if (size > 0) {
                Keyframe keyframe = (Keyframe) arrayList2.get(0);
                Keyframe keyframe2 = (Keyframe) arrayList2.get(i4 - 1);
                float fraction = keyframe2.getFraction();
                float f2 = 0.0f;
                if (fraction < 1.0f) {
                    if (fraction < 0.0f) {
                        keyframe2.setFraction(1.0f);
                    } else {
                        arrayList2.add(arrayList2.size(), createNewKeyframe(keyframe2, 1.0f));
                        i4++;
                    }
                }
                float fraction2 = keyframe.getFraction();
                if (fraction2 != 0.0f) {
                    if (fraction2 < 0.0f) {
                        keyframe.setFraction(0.0f);
                    } else {
                        arrayList2.add(0, createNewKeyframe(keyframe, 0.0f));
                        i4++;
                    }
                }
                Keyframe[] keyframeArr = new Keyframe[i4];
                arrayList2.toArray(keyframeArr);
                int i5 = 0;
                while (i5 < i4) {
                    Keyframe keyframe3 = keyframeArr[i5];
                    if (keyframe3.getFraction() >= f2) {
                        obj = obj2;
                        arrayList = arrayList2;
                        i2 = i;
                        f = f2;
                    } else if (i5 == 0) {
                        keyframe3.setFraction(f2);
                        obj = obj2;
                        arrayList = arrayList2;
                        i2 = i;
                        f = f2;
                    } else if (i5 == i4 - 1) {
                        keyframe3.setFraction(1.0f);
                        obj = obj2;
                        arrayList = arrayList2;
                        i2 = i;
                        f = 0.0f;
                    } else {
                        int i6 = i5;
                        obj = obj2;
                        int i7 = i6 + 1;
                        arrayList = arrayList2;
                        int i8 = i5;
                        while (true) {
                            i2 = i;
                            if (i7 >= i4 - 1) {
                                f = 0.0f;
                                break;
                            }
                            f = 0.0f;
                            if (keyframeArr[i7].getFraction() >= 0.0f) {
                                break;
                            }
                            i8 = i7;
                            i7++;
                            i = i2;
                        }
                        distributeKeyframes(keyframeArr, keyframeArr[i8 + 1].getFraction() - keyframeArr[i6 - 1].getFraction(), i6, i8);
                    }
                    i5++;
                    arrayList2 = arrayList;
                    f2 = f;
                    i = i2;
                    obj2 = obj;
                }
                PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(propertyName, keyframeArr);
                if (i3 != 3) {
                    return ofKeyframe;
                }
                ofKeyframe.setEvaluator(ArgbEvaluator.getInstance());
                return ofKeyframe;
            }
            propertyValuesHolder = null;
        } else {
            propertyValuesHolder = null;
        }
        return propertyValuesHolder;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static PropertyValuesHolder[] loadValues(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        ArrayList arrayList = null;
        while (true) {
            int eventType = xmlPullParser.getEventType();
            if (eventType == 3 || eventType == 1) {
                break;
            }
            if (eventType != 2) {
                xmlPullParser.next();
            } else {
                if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                    TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                    String namedString = TypedArrayUtils.getNamedString(obtainAttributes, xmlPullParser, "propertyName", 3);
                    Log5ECF72.a(namedString);
                    LogE84000.a(namedString);
                    Log229316.a(namedString);
                    int namedInt = TypedArrayUtils.getNamedInt(obtainAttributes, xmlPullParser, "valueType", 2, 4);
                    PropertyValuesHolder loadPvh = loadPvh(context, resources, theme, xmlPullParser, namedString, namedInt);
                    if (loadPvh == null) {
                        loadPvh = getPVH(obtainAttributes, namedInt, 0, 1, namedString);
                    }
                    if (loadPvh != null) {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(loadPvh);
                    }
                    obtainAttributes.recycle();
                }
                xmlPullParser.next();
            }
        }
        PropertyValuesHolder[] propertyValuesHolderArr = null;
        if (arrayList != null) {
            int size = arrayList.size();
            propertyValuesHolderArr = new PropertyValuesHolder[size];
            for (int i = 0; i < size; i++) {
                propertyValuesHolderArr[i] = (PropertyValuesHolder) arrayList.get(i);
            }
        }
        return propertyValuesHolderArr;
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator anim, TypedArray arrayAnimator, TypedArray arrayObjectAnimator, float pixelSize, XmlPullParser parser) {
        long namedInt = TypedArrayUtils.getNamedInt(arrayAnimator, parser, TypedValues.TransitionType.S_DURATION, 1, 300);
        long namedInt2 = TypedArrayUtils.getNamedInt(arrayAnimator, parser, "startOffset", 2, 0);
        int namedInt3 = TypedArrayUtils.getNamedInt(arrayAnimator, parser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(parser, "valueFrom") && TypedArrayUtils.hasAttribute(parser, "valueTo")) {
            if (namedInt3 == 4) {
                namedInt3 = inferValueTypeFromValues(arrayAnimator, 5, 6);
            }
            PropertyValuesHolder pvh = getPVH(arrayAnimator, namedInt3, 5, 6, HttpUrl.FRAGMENT_ENCODE_SET);
            if (pvh != null) {
                anim.setValues(pvh);
            }
        }
        anim.setDuration(namedInt);
        anim.setStartDelay(namedInt2);
        anim.setRepeatCount(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatCount", 3, 0));
        anim.setRepeatMode(TypedArrayUtils.getNamedInt(arrayAnimator, parser, "repeatMode", 4, 1));
        if (arrayObjectAnimator != null) {
            setupObjectAnimator(anim, arrayObjectAnimator, namedInt3, pixelSize, parser);
        }
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    private static void setupObjectAnimator(ValueAnimator valueAnimator, TypedArray typedArray, int i, float f, XmlPullParser xmlPullParser) {
        ObjectAnimator objectAnimator = (ObjectAnimator) valueAnimator;
        String namedString = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "pathData", 1);
        Log5ECF72.a(namedString);
        LogE84000.a(namedString);
        Log229316.a(namedString);
        if (namedString != null) {
            String namedString2 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyXName", 2);
            Log5ECF72.a(namedString2);
            LogE84000.a(namedString2);
            Log229316.a(namedString2);
            String namedString3 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyYName", 3);
            Log5ECF72.a(namedString3);
            LogE84000.a(namedString3);
            Log229316.a(namedString3);
            if (i == 2 || i == 4) {
            }
            if (namedString2 == null && namedString3 == null) {
                throw new InflateException(typedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            setupPathMotion(PathParser.createPathFromPathData(namedString), objectAnimator, 0.5f * f, namedString2, namedString3);
            return;
        }
        String namedString4 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyName", 0);
        Log5ECF72.a(namedString4);
        LogE84000.a(namedString4);
        Log229316.a(namedString4);
        objectAnimator.setPropertyName(namedString4);
    }

    private static void setupPathMotion(Path path, ObjectAnimator oa, float precision, String propertyXName, String propertyYName) {
        Path path2 = path;
        PathMeasure pathMeasure = new PathMeasure(path2, false);
        float f = 0.0f;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Float.valueOf(0.0f));
        while (true) {
            f += pathMeasure.getLength();
            arrayList.add(Float.valueOf(f));
            if (!pathMeasure.nextContour()) {
                break;
            } else {
                path2 = path;
            }
        }
        PathMeasure pathMeasure2 = new PathMeasure(path2, false);
        int min = Math.min(100, ((int) (f / precision)) + 1);
        float[] fArr = new float[min];
        float[] fArr2 = new float[min];
        float[] fArr3 = new float[2];
        int i = 0;
        float f2 = f / ((float) (min - 1));
        float f3 = 0.0f;
        for (int i2 = 0; i2 < min; i2++) {
            pathMeasure2.getPosTan(f3 - ((Float) arrayList.get(i)).floatValue(), fArr3, null);
            fArr[i2] = fArr3[0];
            fArr2[i2] = fArr3[1];
            f3 += f2;
            if (i + 1 < arrayList.size() && f3 > ((Float) arrayList.get(i + 1)).floatValue()) {
                i++;
                pathMeasure2.nextContour();
            }
        }
        PropertyValuesHolder ofFloat = propertyXName != null ? PropertyValuesHolder.ofFloat(propertyXName, fArr) : null;
        PropertyValuesHolder ofFloat2 = propertyYName != null ? PropertyValuesHolder.ofFloat(propertyYName, fArr2) : null;
        if (ofFloat == null) {
            oa.setValues(ofFloat2);
        } else if (ofFloat2 == null) {
            oa.setValues(ofFloat);
        } else {
            oa.setValues(ofFloat, ofFloat2);
        }
    }
}
