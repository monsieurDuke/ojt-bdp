package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLNumber;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParsingException;
import androidx.constraintlayout.core.state.Transition;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.core.os.EnvironmentCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0016.java */
/* loaded from: classes.dex */
public class WidgetFrame {
    private static final boolean OLD_SYSTEM = true;
    public static float phone_orientation = Float.NaN;
    public float alpha;
    public int bottom;
    public float interpolatedPos;
    public int left;
    public final HashMap<String, CustomVariable> mCustom;
    public String name;
    public float pivotX;
    public float pivotY;
    public int right;
    public float rotationX;
    public float rotationY;
    public float rotationZ;
    public float scaleX;
    public float scaleY;
    public int top;
    public float translationX;
    public float translationY;
    public float translationZ;
    public int visibility;
    public ConstraintWidget widget;

    public WidgetFrame() {
        this.widget = null;
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
        this.pivotX = Float.NaN;
        this.pivotY = Float.NaN;
        this.rotationX = Float.NaN;
        this.rotationY = Float.NaN;
        this.rotationZ = Float.NaN;
        this.translationX = Float.NaN;
        this.translationY = Float.NaN;
        this.translationZ = Float.NaN;
        this.scaleX = Float.NaN;
        this.scaleY = Float.NaN;
        this.alpha = Float.NaN;
        this.interpolatedPos = Float.NaN;
        this.visibility = 0;
        this.mCustom = new HashMap<>();
        this.name = null;
    }

    public WidgetFrame(WidgetFrame frame) {
        this.widget = null;
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
        this.pivotX = Float.NaN;
        this.pivotY = Float.NaN;
        this.rotationX = Float.NaN;
        this.rotationY = Float.NaN;
        this.rotationZ = Float.NaN;
        this.translationX = Float.NaN;
        this.translationY = Float.NaN;
        this.translationZ = Float.NaN;
        this.scaleX = Float.NaN;
        this.scaleY = Float.NaN;
        this.alpha = Float.NaN;
        this.interpolatedPos = Float.NaN;
        this.visibility = 0;
        this.mCustom = new HashMap<>();
        this.name = null;
        this.widget = frame.widget;
        this.left = frame.left;
        this.top = frame.top;
        this.right = frame.right;
        this.bottom = frame.bottom;
        updateAttributes(frame);
    }

    public WidgetFrame(ConstraintWidget widget) {
        this.widget = null;
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
        this.pivotX = Float.NaN;
        this.pivotY = Float.NaN;
        this.rotationX = Float.NaN;
        this.rotationY = Float.NaN;
        this.rotationZ = Float.NaN;
        this.translationX = Float.NaN;
        this.translationY = Float.NaN;
        this.translationZ = Float.NaN;
        this.scaleX = Float.NaN;
        this.scaleY = Float.NaN;
        this.alpha = Float.NaN;
        this.interpolatedPos = Float.NaN;
        this.visibility = 0;
        this.mCustom = new HashMap<>();
        this.name = null;
        this.widget = widget;
    }

    private static void add(StringBuilder s, String title, float value) {
        if (Float.isNaN(value)) {
            return;
        }
        s.append(title);
        s.append(": ");
        s.append(value);
        s.append(",\n");
    }

    private static void add(StringBuilder s, String title, int value) {
        s.append(title);
        s.append(": ");
        s.append(value);
        s.append(",\n");
    }

    private static float interpolate(float start, float end, float defaultValue, float progress) {
        boolean isNaN = Float.isNaN(start);
        boolean isNaN2 = Float.isNaN(end);
        if (isNaN && isNaN2) {
            return Float.NaN;
        }
        if (isNaN) {
            start = defaultValue;
        }
        if (isNaN2) {
            end = defaultValue;
        }
        return ((end - start) * progress) + start;
    }

    public static void interpolate(int parentWidth, int parentHeight, WidgetFrame frame, WidgetFrame start, WidgetFrame end, Transition transition, float progress) {
        float f;
        int i;
        int i2;
        int i3;
        float f2;
        int i4;
        int i5;
        int i6;
        Iterator<String> it;
        int i7;
        int i8;
        int i9;
        int i10;
        WidgetFrame widgetFrame = frame;
        WidgetFrame widgetFrame2 = start;
        WidgetFrame widgetFrame3 = end;
        int i11 = (int) (progress * 100.0f);
        int i12 = widgetFrame2.left;
        int i13 = widgetFrame2.top;
        int i14 = widgetFrame3.left;
        int i15 = widgetFrame3.top;
        int i16 = widgetFrame2.right - widgetFrame2.left;
        int i17 = widgetFrame2.bottom - widgetFrame2.top;
        int i18 = widgetFrame3.right - widgetFrame3.left;
        int i19 = widgetFrame3.bottom - widgetFrame3.top;
        float f3 = progress;
        float f4 = widgetFrame2.alpha;
        float f5 = widgetFrame3.alpha;
        if (widgetFrame2.visibility == 8) {
            i12 = (int) (i12 - (i18 / 2.0f));
            i13 = (int) (i13 - (i19 / 2.0f));
            i2 = i19;
            if (Float.isNaN(f4)) {
                i = i18;
                f = 0.0f;
            } else {
                i = i18;
                f = f4;
            }
        } else {
            f = f4;
            i = i16;
            i2 = i17;
        }
        int i20 = i12;
        if (widgetFrame3.visibility == 8) {
            i14 = (int) (i14 - (i / 2.0f));
            i15 = (int) (i15 - (i2 / 2.0f));
            i18 = i;
            i3 = i2;
            if (Float.isNaN(f5)) {
                f5 = 0.0f;
            }
        } else {
            i3 = i19;
        }
        if (Float.isNaN(f) && !Float.isNaN(f5)) {
            f = 1.0f;
        }
        if (!Float.isNaN(f) && Float.isNaN(f5)) {
            f5 = 1.0f;
        }
        float f6 = widgetFrame2.visibility == 4 ? 0.0f : f;
        int i21 = i13;
        if (widgetFrame3.visibility == 4) {
            f5 = 0.0f;
        }
        if (widgetFrame.widget == null || !transition.hasPositionKeyframes()) {
            f2 = progress;
            i4 = i21;
            i5 = i20;
            i6 = i14;
        } else {
            Transition.KeyPosition findPreviousPosition = transition.findPreviousPosition(widgetFrame.widget.stringId, i11);
            Transition.KeyPosition findNextPosition = transition.findNextPosition(widgetFrame.widget.stringId, i11);
            if (findPreviousPosition == findNextPosition) {
                findNextPosition = null;
            }
            int i22 = 100;
            if (findPreviousPosition != null) {
                i20 = (int) (findPreviousPosition.x * parentWidth);
                i8 = i14;
                i7 = parentHeight;
                int i23 = (int) (findPreviousPosition.y * i7);
                i9 = findPreviousPosition.frame;
                i21 = i23;
            } else {
                i7 = parentHeight;
                i8 = i14;
                i9 = 0;
            }
            if (findNextPosition != null) {
                i10 = (int) (findNextPosition.x * parentWidth);
                i15 = (int) (findNextPosition.y * i7);
                i22 = findNextPosition.frame;
            } else {
                i10 = i8;
            }
            f2 = progress;
            f3 = ((100.0f * f2) - i9) / (i22 - i9);
            i6 = i10;
            i4 = i21;
            i5 = i20;
        }
        widgetFrame.widget = widgetFrame2.widget;
        int i24 = (int) (i5 + ((i6 - i5) * f3));
        widgetFrame.left = i24;
        int i25 = (int) (i4 + ((i15 - i4) * f3));
        widgetFrame.top = i25;
        widgetFrame.right = i24 + ((int) (((1.0f - f2) * i) + (i18 * f2)));
        widgetFrame.bottom = i25 + ((int) (((1.0f - f2) * i2) + (i3 * f2)));
        widgetFrame.pivotX = interpolate(widgetFrame2.pivotX, widgetFrame3.pivotX, 0.5f, f2);
        widgetFrame.pivotY = interpolate(widgetFrame2.pivotY, widgetFrame3.pivotY, 0.5f, f2);
        widgetFrame.rotationX = interpolate(widgetFrame2.rotationX, widgetFrame3.rotationX, 0.0f, f2);
        widgetFrame.rotationY = interpolate(widgetFrame2.rotationY, widgetFrame3.rotationY, 0.0f, f2);
        widgetFrame.rotationZ = interpolate(widgetFrame2.rotationZ, widgetFrame3.rotationZ, 0.0f, f2);
        widgetFrame.scaleX = interpolate(widgetFrame2.scaleX, widgetFrame3.scaleX, 1.0f, f2);
        widgetFrame.scaleY = interpolate(widgetFrame2.scaleY, widgetFrame3.scaleY, 1.0f, f2);
        widgetFrame.translationX = interpolate(widgetFrame2.translationX, widgetFrame3.translationX, 0.0f, f2);
        widgetFrame.translationY = interpolate(widgetFrame2.translationY, widgetFrame3.translationY, 0.0f, f2);
        widgetFrame.translationZ = interpolate(widgetFrame2.translationZ, widgetFrame3.translationZ, 0.0f, f2);
        widgetFrame.alpha = interpolate(f6, f5, 1.0f, f2);
        Set<String> keySet = widgetFrame3.mCustom.keySet();
        widgetFrame.mCustom.clear();
        Iterator<String> it2 = keySet.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            Set<String> set = keySet;
            if (widgetFrame2.mCustom.containsKey(next)) {
                CustomVariable customVariable = widgetFrame2.mCustom.get(next);
                CustomVariable customVariable2 = widgetFrame3.mCustom.get(next);
                CustomVariable customVariable3 = new CustomVariable(customVariable);
                it = it2;
                widgetFrame.mCustom.put(next, customVariable3);
                if (customVariable.numberOfInterpolatedValues() == 1) {
                    customVariable3.setValue(Float.valueOf(interpolate(customVariable.getValueToInterpolate(), customVariable2.getValueToInterpolate(), 0.0f, f2)));
                } else {
                    int numberOfInterpolatedValues = customVariable.numberOfInterpolatedValues();
                    float[] fArr = new float[numberOfInterpolatedValues];
                    float[] fArr2 = new float[numberOfInterpolatedValues];
                    customVariable.getValuesToInterpolate(fArr);
                    customVariable2.getValuesToInterpolate(fArr2);
                    int i26 = 0;
                    while (i26 < numberOfInterpolatedValues) {
                        fArr[i26] = interpolate(fArr[i26], fArr2[i26], 0.0f, f2);
                        customVariable3.setValue(fArr);
                        i26++;
                        numberOfInterpolatedValues = numberOfInterpolatedValues;
                        customVariable2 = customVariable2;
                        fArr2 = fArr2;
                    }
                }
            } else {
                it = it2;
            }
            widgetFrame = frame;
            widgetFrame2 = start;
            widgetFrame3 = end;
            keySet = set;
            it2 = it;
        }
    }

    private void serializeAnchor(StringBuilder ret, ConstraintAnchor.Type type) {
        ConstraintAnchor anchor = this.widget.getAnchor(type);
        if (anchor == null || anchor.mTarget == null) {
            return;
        }
        ret.append("Anchor");
        ret.append(type.name());
        ret.append(": ['");
        String str = anchor.mTarget.getOwner().stringId;
        ret.append(str == null ? "#PARENT" : str);
        ret.append("', '");
        ret.append(anchor.mTarget.getType().name());
        ret.append("', '");
        ret.append(anchor.mMargin);
        ret.append("'],\n");
    }

    public void addCustomColor(String name, int color) {
        setCustomAttribute(name, TypedValues.Custom.TYPE_COLOR, color);
    }

    public void addCustomFloat(String name, float value) {
        setCustomAttribute(name, TypedValues.Custom.TYPE_FLOAT, value);
    }

    public float centerX() {
        return this.left + ((this.right - r0) / 2.0f);
    }

    public float centerY() {
        return this.top + ((this.bottom - r0) / 2.0f);
    }

    public CustomVariable getCustomAttribute(String name) {
        return this.mCustom.get(name);
    }

    public Set<String> getCustomAttributeNames() {
        return this.mCustom.keySet();
    }

    public int getCustomColor(String name) {
        if (this.mCustom.containsKey(name)) {
            return this.mCustom.get(name).getColorValue();
        }
        return -21880;
    }

    public float getCustomFloat(String name) {
        if (this.mCustom.containsKey(name)) {
            return this.mCustom.get(name).getFloatValue();
        }
        return Float.NaN;
    }

    public String getId() {
        ConstraintWidget constraintWidget = this.widget;
        return constraintWidget == null ? EnvironmentCompat.MEDIA_UNKNOWN : constraintWidget.stringId;
    }

    public int height() {
        return Math.max(0, this.bottom - this.top);
    }

    public boolean isDefaultTransform() {
        if (Float.isNaN(this.rotationX) && Float.isNaN(this.rotationY) && Float.isNaN(this.rotationZ) && Float.isNaN(this.translationX) && Float.isNaN(this.translationY) && Float.isNaN(this.translationZ) && Float.isNaN(this.scaleX) && Float.isNaN(this.scaleY) && Float.isNaN(this.alpha)) {
            return OLD_SYSTEM;
        }
        return false;
    }

    void logv(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str2 = (".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName()) + " " + (hashCode() % 1000);
        System.out.println((this.widget != null ? str2 + "/" + (this.widget.hashCode() % 1000) : str2 + "/NULL") + " " + str);
    }

    void parseCustom(CLElement custom) throws CLParsingException {
        CLObject cLObject = (CLObject) custom;
        int size = cLObject.size();
        for (int i = 0; i < size; i++) {
            CLKey cLKey = (CLKey) cLObject.get(i);
            cLKey.content();
            CLElement value = cLKey.getValue();
            String content = value.content();
            if (content.matches("#[0-9a-fA-F]+")) {
                setCustomAttribute(cLKey.content(), TypedValues.Custom.TYPE_COLOR, Integer.parseInt(content.substring(1), 16));
            } else if (value instanceof CLNumber) {
                setCustomAttribute(cLKey.content(), TypedValues.Custom.TYPE_FLOAT, value.getFloat());
            } else {
                setCustomAttribute(cLKey.content(), TypedValues.Custom.TYPE_STRING, content);
            }
        }
    }

    void printCustomAttributes() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str = (".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName()) + " " + (hashCode() % 1000);
        String str2 = this.widget != null ? str + "/" + (this.widget.hashCode() % 1000) + " " : str + "/NULL ";
        HashMap<String, CustomVariable> hashMap = this.mCustom;
        if (hashMap != null) {
            Iterator<String> it = hashMap.keySet().iterator();
            while (it.hasNext()) {
                System.out.println(str2 + this.mCustom.get(it.next()).toString());
            }
        }
    }

    public StringBuilder serialize(StringBuilder ret) {
        return serialize(ret, false);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public StringBuilder serialize(StringBuilder sb, boolean z) {
        sb.append("{\n");
        add(sb, "left", this.left);
        add(sb, "top", this.top);
        add(sb, "right", this.right);
        add(sb, "bottom", this.bottom);
        add(sb, "pivotX", this.pivotX);
        add(sb, "pivotY", this.pivotY);
        add(sb, "rotationX", this.rotationX);
        add(sb, "rotationY", this.rotationY);
        add(sb, "rotationZ", this.rotationZ);
        add(sb, "translationX", this.translationX);
        add(sb, "translationY", this.translationY);
        add(sb, "translationZ", this.translationZ);
        add(sb, "scaleX", this.scaleX);
        add(sb, "scaleY", this.scaleY);
        add(sb, "alpha", this.alpha);
        add(sb, "visibility", this.visibility);
        add(sb, "interpolatedPos", this.interpolatedPos);
        if (this.widget != null) {
            for (ConstraintAnchor.Type type : ConstraintAnchor.Type.values()) {
                serializeAnchor(sb, type);
            }
        }
        if (z) {
            add(sb, "phone_orientation", phone_orientation);
        }
        if (z) {
            add(sb, "phone_orientation", phone_orientation);
        }
        if (this.mCustom.size() != 0) {
            sb.append("custom : {\n");
            for (String str : this.mCustom.keySet()) {
                CustomVariable customVariable = this.mCustom.get(str);
                sb.append(str);
                sb.append(": ");
                switch (customVariable.getType()) {
                    case TypedValues.Custom.TYPE_INT /* 900 */:
                        sb.append(customVariable.getIntegerValue());
                        sb.append(",\n");
                        break;
                    case TypedValues.Custom.TYPE_FLOAT /* 901 */:
                    case TypedValues.Custom.TYPE_DIMENSION /* 905 */:
                        sb.append(customVariable.getFloatValue());
                        sb.append(",\n");
                        break;
                    case TypedValues.Custom.TYPE_COLOR /* 902 */:
                        sb.append("'");
                        String colorString = CustomVariable.colorString(customVariable.getIntegerValue());
                        Log5ECF72.a(colorString);
                        LogE84000.a(colorString);
                        Log229316.a(colorString);
                        sb.append(colorString);
                        sb.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_STRING /* 903 */:
                        sb.append("'");
                        sb.append(customVariable.getStringValue());
                        sb.append("',\n");
                        break;
                    case TypedValues.Custom.TYPE_BOOLEAN /* 904 */:
                        sb.append("'");
                        sb.append(customVariable.getBooleanValue());
                        sb.append("',\n");
                        break;
                }
            }
            sb.append("}\n");
        }
        sb.append("}\n");
        return sb;
    }

    public void setCustomAttribute(String name, int type, float value) {
        if (this.mCustom.containsKey(name)) {
            this.mCustom.get(name).setFloatValue(value);
        } else {
            this.mCustom.put(name, new CustomVariable(name, type, value));
        }
    }

    public void setCustomAttribute(String name, int type, int value) {
        if (this.mCustom.containsKey(name)) {
            this.mCustom.get(name).setIntValue(value);
        } else {
            this.mCustom.put(name, new CustomVariable(name, type, value));
        }
    }

    public void setCustomAttribute(String name, int type, String value) {
        if (this.mCustom.containsKey(name)) {
            this.mCustom.get(name).setStringValue(value);
        } else {
            this.mCustom.put(name, new CustomVariable(name, type, value));
        }
    }

    public void setCustomAttribute(String name, int type, boolean value) {
        if (this.mCustom.containsKey(name)) {
            this.mCustom.get(name).setBooleanValue(value);
        } else {
            this.mCustom.put(name, new CustomVariable(name, type, value));
        }
    }

    public void setCustomValue(CustomAttribute valueAt, float[] mTempValues) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean setValue(String key, CLElement value) throws CLParsingException {
        char c;
        switch (key.hashCode()) {
            case -1881940865:
                if (key.equals("phone_orientation")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1383228885:
                if (key.equals("bottom")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1349088399:
                if (key.equals("custom")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1249320806:
                if (key.equals("rotationX")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1249320805:
                if (key.equals("rotationY")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1249320804:
                if (key.equals("rotationZ")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1225497657:
                if (key.equals("translationX")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1225497656:
                if (key.equals("translationY")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1225497655:
                if (key.equals("translationZ")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -987906986:
                if (key.equals("pivotX")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -987906985:
                if (key.equals("pivotY")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -908189618:
                if (key.equals("scaleX")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -908189617:
                if (key.equals("scaleY")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 115029:
                if (key.equals("top")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 3317767:
                if (key.equals("left")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 92909918:
                if (key.equals("alpha")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 108511772:
                if (key.equals("right")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 642850769:
                if (key.equals("interpolatedPos")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.pivotX = value.getFloat();
                return OLD_SYSTEM;
            case 1:
                this.pivotY = value.getFloat();
                return OLD_SYSTEM;
            case 2:
                this.rotationX = value.getFloat();
                return OLD_SYSTEM;
            case 3:
                this.rotationY = value.getFloat();
                return OLD_SYSTEM;
            case 4:
                this.rotationZ = value.getFloat();
                return OLD_SYSTEM;
            case 5:
                this.translationX = value.getFloat();
                return OLD_SYSTEM;
            case 6:
                this.translationY = value.getFloat();
                return OLD_SYSTEM;
            case 7:
                this.translationZ = value.getFloat();
                return OLD_SYSTEM;
            case '\b':
                this.scaleX = value.getFloat();
                return OLD_SYSTEM;
            case '\t':
                this.scaleY = value.getFloat();
                return OLD_SYSTEM;
            case '\n':
                this.alpha = value.getFloat();
                return OLD_SYSTEM;
            case 11:
                this.interpolatedPos = value.getFloat();
                return OLD_SYSTEM;
            case '\f':
                phone_orientation = value.getFloat();
                return OLD_SYSTEM;
            case '\r':
                this.top = value.getInt();
                return OLD_SYSTEM;
            case 14:
                this.left = value.getInt();
                return OLD_SYSTEM;
            case 15:
                this.right = value.getInt();
                return OLD_SYSTEM;
            case 16:
                this.bottom = value.getInt();
                return OLD_SYSTEM;
            case 17:
                parseCustom(value);
                return OLD_SYSTEM;
            default:
                return false;
        }
    }

    public WidgetFrame update() {
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget != null) {
            this.left = constraintWidget.getLeft();
            this.top = this.widget.getTop();
            this.right = this.widget.getRight();
            this.bottom = this.widget.getBottom();
            updateAttributes(this.widget.frame);
        }
        return this;
    }

    public WidgetFrame update(ConstraintWidget widget) {
        if (widget == null) {
            return this;
        }
        this.widget = widget;
        update();
        return this;
    }

    public void updateAttributes(WidgetFrame frame) {
        this.pivotX = frame.pivotX;
        this.pivotY = frame.pivotY;
        this.rotationX = frame.rotationX;
        this.rotationY = frame.rotationY;
        this.rotationZ = frame.rotationZ;
        this.translationX = frame.translationX;
        this.translationY = frame.translationY;
        this.translationZ = frame.translationZ;
        this.scaleX = frame.scaleX;
        this.scaleY = frame.scaleY;
        this.alpha = frame.alpha;
        this.visibility = frame.visibility;
        this.mCustom.clear();
        if (frame != null) {
            for (CustomVariable customVariable : frame.mCustom.values()) {
                this.mCustom.put(customVariable.getName(), customVariable.copy());
            }
        }
    }

    public int width() {
        return Math.max(0, this.right - this.left);
    }
}
