package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashMap;

/* loaded from: classes.dex */
public class DesignTool implements ProxyInterface {
    private static final boolean DEBUG = false;
    private static final String TAG = "DesignTool";
    static final HashMap<Pair<Integer, Integer>, String> allAttributes;
    static final HashMap<String, String> allMargins;
    private final MotionLayout mMotionLayout;
    private MotionScene mSceneCache;
    private String mLastStartState = null;
    private String mLastEndState = null;
    private int mLastStartStateId = -1;
    private int mLastEndStateId = -1;

    static {
        HashMap<Pair<Integer, Integer>, String> hashMap = new HashMap<>();
        allAttributes = hashMap;
        HashMap<String, String> hashMap2 = new HashMap<>();
        allMargins = hashMap2;
        hashMap.put(Pair.create(4, 4), "layout_constraintBottom_toBottomOf");
        hashMap.put(Pair.create(4, 3), "layout_constraintBottom_toTopOf");
        hashMap.put(Pair.create(3, 4), "layout_constraintTop_toBottomOf");
        hashMap.put(Pair.create(3, 3), "layout_constraintTop_toTopOf");
        hashMap.put(Pair.create(6, 6), "layout_constraintStart_toStartOf");
        hashMap.put(Pair.create(6, 7), "layout_constraintStart_toEndOf");
        hashMap.put(Pair.create(7, 6), "layout_constraintEnd_toStartOf");
        hashMap.put(Pair.create(7, 7), "layout_constraintEnd_toEndOf");
        hashMap.put(Pair.create(1, 1), "layout_constraintLeft_toLeftOf");
        hashMap.put(Pair.create(1, 2), "layout_constraintLeft_toRightOf");
        hashMap.put(Pair.create(2, 2), "layout_constraintRight_toRightOf");
        hashMap.put(Pair.create(2, 1), "layout_constraintRight_toLeftOf");
        hashMap.put(Pair.create(5, 5), "layout_constraintBaseline_toBaselineOf");
        hashMap2.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
        hashMap2.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
        hashMap2.put("layout_constraintTop_toBottomOf", "layout_marginTop");
        hashMap2.put("layout_constraintTop_toTopOf", "layout_marginTop");
        hashMap2.put("layout_constraintStart_toStartOf", "layout_marginStart");
        hashMap2.put("layout_constraintStart_toEndOf", "layout_marginStart");
        hashMap2.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
        hashMap2.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
        hashMap2.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
        hashMap2.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
        hashMap2.put("layout_constraintRight_toRightOf", "layout_marginRight");
        hashMap2.put("layout_constraintRight_toLeftOf", "layout_marginRight");
    }

    public DesignTool(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    private static void Connect(int dpi, ConstraintSet set, View view, HashMap<String, String> attributes, int from, int to) {
        String str = allAttributes.get(Pair.create(Integer.valueOf(from), Integer.valueOf(to)));
        String str2 = attributes.get(str);
        if (str2 != null) {
            int i = 0;
            String str3 = allMargins.get(str);
            if (str3 != null) {
                i = GetPxFromDp(dpi, attributes.get(str3));
            }
            set.connect(view.getId(), from, Integer.parseInt(str2), to, i);
        }
    }

    private static int GetPxFromDp(int dpi, String value) {
        int indexOf;
        if (value == null || (indexOf = value.indexOf(100)) == -1) {
            return 0;
        }
        return (int) ((Integer.valueOf(value.substring(0, indexOf)).intValue() * dpi) / 160.0f);
    }

    private static void SetAbsolutePositions(int dpi, ConstraintSet set, View view, HashMap<String, String> attributes) {
        String str = attributes.get("layout_editor_absoluteX");
        if (str != null) {
            set.setEditorAbsoluteX(view.getId(), GetPxFromDp(dpi, str));
        }
        String str2 = attributes.get("layout_editor_absoluteY");
        if (str2 != null) {
            set.setEditorAbsoluteY(view.getId(), GetPxFromDp(dpi, str2));
        }
    }

    private static void SetBias(ConstraintSet set, View view, HashMap<String, String> attributes, int type) {
        String str = attributes.get(type == 1 ? "layout_constraintVertical_bias" : "layout_constraintHorizontal_bias");
        if (str != null) {
            if (type == 0) {
                set.setHorizontalBias(view.getId(), Float.parseFloat(str));
            } else if (type == 1) {
                set.setVerticalBias(view.getId(), Float.parseFloat(str));
            }
        }
    }

    private static void SetDimensions(int dpi, ConstraintSet set, View view, HashMap<String, String> attributes, int type) {
        String str = attributes.get(type == 1 ? "layout_height" : "layout_width");
        if (str != null) {
            int GetPxFromDp = str.equalsIgnoreCase("wrap_content") ? -2 : GetPxFromDp(dpi, str);
            if (type == 0) {
                set.constrainWidth(view.getId(), GetPxFromDp);
            } else {
                set.constrainHeight(view.getId(), GetPxFromDp);
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public int designAccess(int cmd, String type, Object viewObject, float[] in, int inLength, float[] out, int outLength) {
        View view = (View) viewObject;
        MotionController motionController = null;
        if (cmd != 0 && (this.mMotionLayout.mScene == null || view == null || (motionController = this.mMotionLayout.mFrameArrayList.get(view)) == null)) {
            return -1;
        }
        switch (cmd) {
            case 0:
                return 1;
            case 1:
                int duration = this.mMotionLayout.mScene.getDuration() / 16;
                motionController.buildPath(out, duration);
                return duration;
            case 2:
                int duration2 = this.mMotionLayout.mScene.getDuration() / 16;
                motionController.buildKeyFrames(out, null);
                return duration2;
            case 3:
                int duration3 = this.mMotionLayout.mScene.getDuration() / 16;
                return motionController.getAttributeValues(type, out, outLength);
            default:
                return -1;
        }
    }

    public void disableAutoTransition(boolean disable) {
        this.mMotionLayout.disableAutoTransition(disable);
    }

    public void dumpConstraintSet(String set) {
        if (this.mMotionLayout.mScene == null) {
            this.mMotionLayout.mScene = this.mSceneCache;
        }
        int lookUpConstraintId = this.mMotionLayout.lookUpConstraintId(set);
        System.out.println(" dumping  " + set + " (" + lookUpConstraintId + ")");
        try {
            this.mMotionLayout.mScene.getConstraintSet(lookUpConstraintId).dump(this.mMotionLayout.mScene, new int[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAnimationKeyFrames(Object view, float[] key) {
        if (this.mMotionLayout.mScene == null) {
            return -1;
        }
        int duration = this.mMotionLayout.mScene.getDuration() / 16;
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get(view);
        if (motionController == null) {
            return 0;
        }
        motionController.buildKeyFrames(key, null);
        return duration;
    }

    public int getAnimationPath(Object view, float[] path, int len) {
        if (this.mMotionLayout.mScene == null) {
            return -1;
        }
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get(view);
        if (motionController == null) {
            return 0;
        }
        motionController.buildPath(path, len);
        return len;
    }

    public void getAnimationRectangles(Object view, float[] path) {
        if (this.mMotionLayout.mScene == null) {
            return;
        }
        int duration = this.mMotionLayout.mScene.getDuration() / 16;
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get(view);
        if (motionController == null) {
            return;
        }
        motionController.buildRectangles(path, duration);
    }

    public String getEndState() {
        int endState = this.mMotionLayout.getEndState();
        if (this.mLastEndStateId == endState) {
            return this.mLastEndState;
        }
        String constraintSetNames = this.mMotionLayout.getConstraintSetNames(endState);
        if (constraintSetNames != null) {
            this.mLastEndState = constraintSetNames;
            this.mLastEndStateId = endState;
        }
        return constraintSetNames;
    }

    public int getKeyFrameInfo(Object view, int type, int[] info) {
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get((View) view);
        if (motionController == null) {
            return 0;
        }
        return motionController.getKeyFrameInfo(type, info);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public float getKeyFramePosition(Object view, int type, float x, float y) {
        MotionController motionController;
        if ((view instanceof View) && (motionController = this.mMotionLayout.mFrameArrayList.get((View) view)) != null) {
            return motionController.getKeyFrameParameter(type, x, y);
        }
        return 0.0f;
    }

    public int getKeyFramePositions(Object view, int[] type, float[] pos) {
        MotionController motionController = this.mMotionLayout.mFrameArrayList.get((View) view);
        if (motionController == null) {
            return 0;
        }
        return motionController.getKeyFramePositions(type, pos);
    }

    public Object getKeyframe(int type, int target, int position) {
        if (this.mMotionLayout.mScene == null) {
            return null;
        }
        return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), type, target, position);
    }

    public Object getKeyframe(Object view, int type, int position) {
        if (this.mMotionLayout.mScene == null) {
            return null;
        }
        return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), type, ((View) view).getId(), position);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public Object getKeyframeAtLocation(Object viewObject, float x, float y) {
        MotionController motionController;
        View view = (View) viewObject;
        if (this.mMotionLayout.mScene == null) {
            return -1;
        }
        if (view == null || (motionController = this.mMotionLayout.mFrameArrayList.get(view)) == null) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        return motionController.getPositionKeyframe(viewGroup.getWidth(), viewGroup.getHeight(), x, y);
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public Boolean getPositionKeyframe(Object keyFrame, Object view, float x, float y, String[] attribute, float[] value) {
        if (!(keyFrame instanceof KeyPositionBase)) {
            return false;
        }
        this.mMotionLayout.mFrameArrayList.get((View) view).positionKeyframe((View) view, (KeyPositionBase) keyFrame, x, y, attribute, value);
        this.mMotionLayout.rebuildScene();
        this.mMotionLayout.mInTransition = true;
        return true;
    }

    public float getProgress() {
        return this.mMotionLayout.getProgress();
    }

    public String getStartState() {
        int startState = this.mMotionLayout.getStartState();
        if (this.mLastStartStateId == startState) {
            return this.mLastStartState;
        }
        String constraintSetNames = this.mMotionLayout.getConstraintSetNames(startState);
        if (constraintSetNames != null) {
            this.mLastStartState = constraintSetNames;
            this.mLastStartStateId = startState;
        }
        return this.mMotionLayout.getConstraintSetNames(startState);
    }

    public String getState() {
        if (this.mLastStartState != null && this.mLastEndState != null) {
            float progress = getProgress();
            if (progress <= 0.01f) {
                return this.mLastStartState;
            }
            if (progress >= 1.0f - 0.01f) {
                return this.mLastEndState;
            }
        }
        return this.mLastStartState;
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public long getTransitionTimeMs() {
        return this.mMotionLayout.getTransitionTimeMs();
    }

    public boolean isInTransition() {
        return (this.mLastStartState == null || this.mLastEndState == null) ? false : true;
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setAttributes(int dpi, String constraintSetId, Object opaqueView, Object opaqueAttributes) {
        View view = (View) opaqueView;
        HashMap hashMap = (HashMap) opaqueAttributes;
        int lookUpConstraintId = this.mMotionLayout.lookUpConstraintId(constraintSetId);
        ConstraintSet constraintSet = this.mMotionLayout.mScene.getConstraintSet(lookUpConstraintId);
        if (constraintSet == null) {
            return;
        }
        constraintSet.clear(view.getId());
        SetDimensions(dpi, constraintSet, view, hashMap, 0);
        SetDimensions(dpi, constraintSet, view, hashMap, 1);
        Connect(dpi, constraintSet, view, hashMap, 6, 6);
        Connect(dpi, constraintSet, view, hashMap, 6, 7);
        Connect(dpi, constraintSet, view, hashMap, 7, 7);
        Connect(dpi, constraintSet, view, hashMap, 7, 6);
        Connect(dpi, constraintSet, view, hashMap, 1, 1);
        Connect(dpi, constraintSet, view, hashMap, 1, 2);
        Connect(dpi, constraintSet, view, hashMap, 2, 2);
        Connect(dpi, constraintSet, view, hashMap, 2, 1);
        Connect(dpi, constraintSet, view, hashMap, 3, 3);
        Connect(dpi, constraintSet, view, hashMap, 3, 4);
        Connect(dpi, constraintSet, view, hashMap, 4, 3);
        Connect(dpi, constraintSet, view, hashMap, 4, 4);
        Connect(dpi, constraintSet, view, hashMap, 5, 5);
        SetBias(constraintSet, view, hashMap, 0);
        SetBias(constraintSet, view, hashMap, 1);
        SetAbsolutePositions(dpi, constraintSet, view, hashMap);
        this.mMotionLayout.updateState(lookUpConstraintId, constraintSet);
        this.mMotionLayout.requestLayout();
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setKeyFrame(Object view, int position, String name, Object value) {
        if (this.mMotionLayout.mScene != null) {
            this.mMotionLayout.mScene.setKeyframe((View) view, position, name, value);
            this.mMotionLayout.mTransitionGoalPosition = position / 100.0f;
            this.mMotionLayout.mTransitionLastPosition = 0.0f;
            this.mMotionLayout.rebuildScene();
            this.mMotionLayout.evaluate(true);
        }
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public boolean setKeyFramePosition(Object view, int position, int type, float x, float y) {
        if ((view instanceof View) && this.mMotionLayout.mScene != null) {
            MotionController motionController = this.mMotionLayout.mFrameArrayList.get(view);
            int position2 = (int) (this.mMotionLayout.mTransitionPosition * 100.0f);
            if (motionController != null && this.mMotionLayout.mScene.hasKeyFramePosition((View) view, position2)) {
                float keyFrameParameter = motionController.getKeyFrameParameter(2, x, y);
                float keyFrameParameter2 = motionController.getKeyFrameParameter(5, x, y);
                this.mMotionLayout.mScene.setKeyframe((View) view, position2, "motion:percentX", Float.valueOf(keyFrameParameter));
                this.mMotionLayout.mScene.setKeyframe((View) view, position2, "motion:percentY", Float.valueOf(keyFrameParameter2));
                this.mMotionLayout.rebuildScene();
                this.mMotionLayout.evaluate(true);
                this.mMotionLayout.invalidate();
                return true;
            }
        }
        return false;
    }

    public void setKeyframe(Object keyFrame, String tag, Object value) {
        if (keyFrame instanceof Key) {
            ((Key) keyFrame).setValue(tag, value);
            this.mMotionLayout.rebuildScene();
            this.mMotionLayout.mInTransition = true;
        }
    }

    public void setState(String id) {
        if (id == null) {
            id = "motion_base";
        }
        if (this.mLastStartState == id) {
            return;
        }
        this.mLastStartState = id;
        this.mLastEndState = null;
        if (this.mMotionLayout.mScene == null) {
            this.mMotionLayout.mScene = this.mSceneCache;
        }
        int lookUpConstraintId = this.mMotionLayout.lookUpConstraintId(id);
        this.mLastStartStateId = lookUpConstraintId;
        if (lookUpConstraintId != 0) {
            if (lookUpConstraintId == this.mMotionLayout.getStartState()) {
                this.mMotionLayout.setProgress(0.0f);
            } else if (lookUpConstraintId == this.mMotionLayout.getEndState()) {
                this.mMotionLayout.setProgress(1.0f);
            } else {
                this.mMotionLayout.transitionToState(lookUpConstraintId);
                this.mMotionLayout.setProgress(1.0f);
            }
        }
        this.mMotionLayout.requestLayout();
    }

    @Override // androidx.constraintlayout.motion.widget.ProxyInterface
    public void setToolPosition(float position) {
        if (this.mMotionLayout.mScene == null) {
            this.mMotionLayout.mScene = this.mSceneCache;
        }
        this.mMotionLayout.setProgress(position);
        this.mMotionLayout.evaluate(true);
        this.mMotionLayout.requestLayout();
        this.mMotionLayout.invalidate();
    }

    public void setTransition(String start, String end) {
        if (this.mMotionLayout.mScene == null) {
            this.mMotionLayout.mScene = this.mSceneCache;
        }
        int lookUpConstraintId = this.mMotionLayout.lookUpConstraintId(start);
        int lookUpConstraintId2 = this.mMotionLayout.lookUpConstraintId(end);
        this.mMotionLayout.setTransition(lookUpConstraintId, lookUpConstraintId2);
        this.mLastStartStateId = lookUpConstraintId;
        this.mLastEndStateId = lookUpConstraintId2;
        this.mLastStartState = start;
        this.mLastEndState = end;
    }

    public void setViewDebug(Object view, int debugMode) {
        MotionController motionController;
        if ((view instanceof View) && (motionController = this.mMotionLayout.mFrameArrayList.get(view)) != null) {
            motionController.setDrawPath(debugMode);
            this.mMotionLayout.invalidate();
        }
    }
}
