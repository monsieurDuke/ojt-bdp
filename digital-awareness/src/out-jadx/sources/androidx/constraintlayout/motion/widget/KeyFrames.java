package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class KeyFrames {
    private static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
    private static final String CUSTOM_METHOD = "CustomMethod";
    private static final String TAG = "KeyFrames";
    public static final int UNSET = -1;
    static HashMap<String, Constructor<? extends Key>> sKeyMakers;
    private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap<>();

    static {
        HashMap<String, Constructor<? extends Key>> hashMap = new HashMap<>();
        sKeyMakers = hashMap;
        try {
            hashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.PositionType.NAME, KeyPosition.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.CycleType.NAME, KeyCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
            sKeyMakers.put(TypedValues.TriggerType.NAME, KeyTrigger.class.getConstructor(new Class[0]));
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "unable to load", e);
        }
    }

    public KeyFrames() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x0013. Please report as an issue. */
    public KeyFrames(Context context, XmlPullParser parser) {
        Key key = null;
        try {
            int eventType = parser.getEventType();
            while (eventType != 1) {
                switch (eventType) {
                    case 0:
                        eventType = parser.next();
                    case 1:
                    default:
                        eventType = parser.next();
                    case 2:
                        String name = parser.getName();
                        if (sKeyMakers.containsKey(name)) {
                            try {
                                Constructor<? extends Key> constructor = sKeyMakers.get(name);
                                if (constructor == null) {
                                    throw new NullPointerException("Keymaker for " + name + " not found");
                                    break;
                                } else {
                                    key = constructor.newInstance(new Object[0]);
                                    key.load(context, Xml.asAttributeSet(parser));
                                    addKey(key);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "unable to create ", e);
                            }
                        } else if (name.equalsIgnoreCase("CustomAttribute")) {
                            if (key != null && key.mCustomConstraints != null) {
                                ConstraintAttribute.parse(context, parser, key.mCustomConstraints);
                            }
                        } else if (name.equalsIgnoreCase("CustomMethod") && key != null && key.mCustomConstraints != null) {
                            ConstraintAttribute.parse(context, parser, key.mCustomConstraints);
                        }
                        eventType = parser.next();
                        break;
                    case 3:
                        if (ViewTransition.KEY_FRAME_SET_TAG.equals(parser.getName())) {
                            return;
                        }
                        eventType = parser.next();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (XmlPullParserException e3) {
            e3.printStackTrace();
        }
    }

    static String name(int viewId, Context context) {
        return context.getResources().getResourceEntryName(viewId);
    }

    public void addAllFrames(MotionController motionController) {
        ArrayList<Key> arrayList = this.mFramesMap.get(-1);
        if (arrayList != null) {
            motionController.addKeys(arrayList);
        }
    }

    public void addFrames(MotionController motionController) {
        ArrayList<Key> arrayList = this.mFramesMap.get(Integer.valueOf(motionController.mId));
        if (arrayList != null) {
            motionController.addKeys(arrayList);
        }
        ArrayList<Key> arrayList2 = this.mFramesMap.get(-1);
        if (arrayList2 != null) {
            Iterator<Key> it = arrayList2.iterator();
            while (it.hasNext()) {
                Key next = it.next();
                if (next.matches(((ConstraintLayout.LayoutParams) motionController.mView.getLayoutParams()).constraintTag)) {
                    motionController.addKey(next);
                }
            }
        }
    }

    public void addKey(Key key) {
        if (!this.mFramesMap.containsKey(Integer.valueOf(key.mTargetId))) {
            this.mFramesMap.put(Integer.valueOf(key.mTargetId), new ArrayList<>());
        }
        ArrayList<Key> arrayList = this.mFramesMap.get(Integer.valueOf(key.mTargetId));
        if (arrayList != null) {
            arrayList.add(key);
        }
    }

    public ArrayList<Key> getKeyFramesForView(int id) {
        return this.mFramesMap.get(Integer.valueOf(id));
    }

    public Set<Integer> getKeys() {
        return this.mFramesMap.keySet();
    }
}