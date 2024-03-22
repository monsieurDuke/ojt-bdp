package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.Map;

/* loaded from: classes.dex */
public class ChangeBounds extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private boolean mReparent;
    private boolean mResizeClip;
    private int[] mTempLocation;
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final String[] sTransitionProperties = {PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") { // from class: androidx.transition.ChangeBounds.1
        private Rect mBounds = new Rect();

        @Override // android.util.Property
        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF(this.mBounds.left, this.mBounds.top);
        }

        @Override // android.util.Property
        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }
    };
    private static final Property<View, PointF> POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position") { // from class: androidx.transition.ChangeBounds.6
        @Override // android.util.Property
        public PointF get(View view) {
            return null;
        }

        @Override // android.util.Property
        public void set(View view, PointF topLeft) {
            int round = Math.round(topLeft.x);
            int round2 = Math.round(topLeft.y);
            ViewUtils.setLeftTopRightBottom(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    private static RectEvaluator sRectEvaluator = new RectEvaluator();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }

        void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            int i = this.mBottomRightCalls + 1;
            this.mBottomRightCalls = i;
            if (this.mTopLeftCalls == i) {
                setLeftTopRightBottom();
            }
        }

        void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            int i = this.mTopLeftCalls + 1;
            this.mTopLeftCalls = i;
            if (i == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }
    }

    static {
        String str = "topLeft";
        TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, str) { // from class: androidx.transition.ChangeBounds.2
            @Override // android.util.Property
            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            @Override // android.util.Property
            public void set(ViewBounds viewBounds, PointF topLeft) {
                viewBounds.setTopLeft(topLeft);
            }
        };
        String str2 = "bottomRight";
        BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.3
            @Override // android.util.Property
            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            @Override // android.util.Property
            public void set(ViewBounds viewBounds, PointF bottomRight) {
                viewBounds.setBottomRight(bottomRight);
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, str2) { // from class: androidx.transition.ChangeBounds.4
            @Override // android.util.Property
            public PointF get(View view) {
                return null;
            }

            @Override // android.util.Property
            public void set(View view, PointF bottomRight) {
                ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(bottomRight.x), Math.round(bottomRight.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, str) { // from class: androidx.transition.ChangeBounds.5
            @Override // android.util.Property
            public PointF get(View view) {
                return null;
            }

            @Override // android.util.Property
            public void set(View view, PointF topLeft) {
                ViewUtils.setLeftTopRightBottom(view, Math.round(topLeft.x), Math.round(topLeft.y), view.getRight(), view.getBottom());
            }
        };
    }

    public ChangeBounds() {
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
    }

    public ChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTempLocation = new int[2];
        this.mResizeClip = false;
        this.mReparent = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean namedBoolean = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, (XmlResourceParser) attrs, "resizeClip", 0, false);
        obtainStyledAttributes.recycle();
        setResizeClip(namedBoolean);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (!ViewCompat.isLaidOut(view) && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        values.values.put(PROPNAME_PARENT, values.view.getParent());
        if (this.mReparent) {
            values.view.getLocationInWindow(this.mTempLocation);
            values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
            values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
        }
        if (this.mResizeClip) {
            values.values.put(PROPNAME_CLIP, ViewCompat.getClipBounds(view));
        }
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        TransitionValues matchedTransitionValues = getMatchedTransitionValues(startParent, true);
        if (matchedTransitionValues == null) {
            return startParent == endParent;
        }
        return endParent == matchedTransitionValues.view;
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        final View view;
        int i;
        int i2;
        int i3;
        ObjectAnimator ofPointF;
        int i4;
        Rect rect;
        boolean z;
        Rect rect2;
        Animator mergeAnimators;
        if (startValues != null && endValues != null) {
            Map<String, Object> map = startValues.values;
            Map<String, Object> map2 = endValues.values;
            ViewGroup viewGroup = (ViewGroup) map.get(PROPNAME_PARENT);
            ViewGroup viewGroup2 = (ViewGroup) map2.get(PROPNAME_PARENT);
            if (viewGroup != null && viewGroup2 != null) {
                final View view2 = endValues.view;
                if (!parentMatches(viewGroup, viewGroup2)) {
                    int intValue = ((Integer) startValues.values.get(PROPNAME_WINDOW_X)).intValue();
                    int intValue2 = ((Integer) startValues.values.get(PROPNAME_WINDOW_Y)).intValue();
                    int intValue3 = ((Integer) endValues.values.get(PROPNAME_WINDOW_X)).intValue();
                    int intValue4 = ((Integer) endValues.values.get(PROPNAME_WINDOW_Y)).intValue();
                    if (intValue == intValue3 && intValue2 == intValue4) {
                        return null;
                    }
                    sceneRoot.getLocationInWindow(this.mTempLocation);
                    Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
                    view2.draw(new Canvas(createBitmap));
                    final BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
                    final float transitionAlpha = ViewUtils.getTransitionAlpha(view2);
                    ViewUtils.setTransitionAlpha(view2, 0.0f);
                    ViewUtils.getOverlay(sceneRoot).add(bitmapDrawable);
                    PathMotion pathMotion = getPathMotion();
                    int[] iArr = this.mTempLocation;
                    int i5 = iArr[0];
                    int i6 = iArr[1];
                    ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, pathMotion.getPath(intValue - i5, intValue2 - i6, intValue3 - i5, intValue4 - i6)));
                    ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeBounds.10
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation) {
                            ViewUtils.getOverlay(sceneRoot).remove(bitmapDrawable);
                            ViewUtils.setTransitionAlpha(view2, transitionAlpha);
                        }
                    });
                    return ofPropertyValuesHolder;
                }
                Rect rect3 = (Rect) startValues.values.get(PROPNAME_BOUNDS);
                Rect rect4 = (Rect) endValues.values.get(PROPNAME_BOUNDS);
                int i7 = rect3.left;
                int i8 = rect4.left;
                int i9 = rect3.top;
                final int i10 = rect4.top;
                int i11 = rect3.right;
                final int i12 = rect4.right;
                int i13 = rect3.bottom;
                final int i14 = rect4.bottom;
                int i15 = i11 - i7;
                int i16 = i13 - i9;
                int i17 = i12 - i8;
                int i18 = i14 - i10;
                Rect rect5 = (Rect) startValues.values.get(PROPNAME_CLIP);
                final Rect rect6 = (Rect) endValues.values.get(PROPNAME_CLIP);
                if ((i15 != 0 && i16 != 0) || (i17 != 0 && i18 != 0)) {
                    r9 = (i7 == i8 && i9 == i10) ? 0 : 0 + 1;
                    if (i11 != i12 || i13 != i14) {
                        r9++;
                    }
                }
                if ((rect5 != null && !rect5.equals(rect6)) || (rect5 == null && rect6 != null)) {
                    r9++;
                }
                if (r9 <= 0) {
                    return null;
                }
                if (this.mResizeClip) {
                    view = view2;
                    ViewUtils.setLeftTopRightBottom(view, i7, i9, i7 + Math.max(i15, i17), i9 + Math.max(i16, i18));
                    if (i7 == i8 && i9 == i10) {
                        i3 = i8;
                        ofPointF = null;
                        i2 = i9;
                        i = i7;
                    } else {
                        i = i7;
                        i2 = i9;
                        i3 = i8;
                        ofPointF = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath(i7, i9, i8, i10));
                    }
                    if (rect5 == null) {
                        i4 = 0;
                        rect = new Rect(0, 0, i15, i16);
                    } else {
                        i4 = 0;
                        rect = rect5;
                    }
                    Rect rect7 = rect6 == null ? new Rect(i4, i4, i17, i18) : rect6;
                    ObjectAnimator objectAnimator = null;
                    if (rect.equals(rect7)) {
                        z = true;
                        rect2 = rect;
                    } else {
                        ViewCompat.setClipBounds(view, rect);
                        ObjectAnimator ofObject = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, rect, rect7);
                        rect2 = rect;
                        final int i19 = i3;
                        z = true;
                        ofObject.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeBounds.8
                            private boolean mIsCanceled;

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animation) {
                                this.mIsCanceled = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                if (this.mIsCanceled) {
                                    return;
                                }
                                ViewCompat.setClipBounds(view, rect6);
                                ViewUtils.setLeftTopRightBottom(view, i19, i10, i12, i14);
                            }
                        });
                        objectAnimator = ofObject;
                    }
                    mergeAnimators = TransitionUtils.mergeAnimators(ofPointF, objectAnimator);
                } else {
                    ViewUtils.setLeftTopRightBottom(view2, i7, i9, i11, i13);
                    if (r9 != 2) {
                        if (i7 != i8) {
                            view = view2;
                        } else if (i9 != i10) {
                            view = view2;
                        } else {
                            view = view2;
                            mergeAnimators = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, getPathMotion().getPath(i11, i13, i12, i14));
                            z = true;
                        }
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, getPathMotion().getPath(i7, i9, i8, i10));
                        z = true;
                    } else if (i15 == i17 && i16 == i18) {
                        mergeAnimators = ObjectAnimatorUtils.ofPointF(view2, POSITION_PROPERTY, getPathMotion().getPath(i7, i9, i8, i10));
                        view = view2;
                        z = true;
                    } else {
                        ViewBounds viewBounds = new ViewBounds(view2);
                        ObjectAnimator ofPointF2 = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, getPathMotion().getPath(i7, i9, i8, i10));
                        ObjectAnimator ofPointF3 = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, getPathMotion().getPath(i11, i13, i12, i14));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(ofPointF2, ofPointF3);
                        animatorSet.addListener(new AnimatorListenerAdapter(viewBounds) { // from class: androidx.transition.ChangeBounds.7
                            private ViewBounds mViewBounds;
                            final /* synthetic */ ViewBounds val$viewBounds;

                            {
                                this.val$viewBounds = viewBounds;
                                this.mViewBounds = viewBounds;
                            }
                        });
                        mergeAnimators = animatorSet;
                        view = view2;
                        z = true;
                    }
                }
                if (view.getParent() instanceof ViewGroup) {
                    final ViewGroup viewGroup3 = (ViewGroup) view.getParent();
                    ViewGroupUtils.suppressLayout(viewGroup3, z);
                    addListener(new TransitionListenerAdapter() { // from class: androidx.transition.ChangeBounds.9
                        boolean mCanceled = false;

                        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                        public void onTransitionCancel(Transition transition) {
                            ViewGroupUtils.suppressLayout(viewGroup3, false);
                            this.mCanceled = true;
                        }

                        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition transition) {
                            if (!this.mCanceled) {
                                ViewGroupUtils.suppressLayout(viewGroup3, false);
                            }
                            transition.removeListener(this);
                        }

                        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                        public void onTransitionPause(Transition transition) {
                            ViewGroupUtils.suppressLayout(viewGroup3, false);
                        }

                        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                        public void onTransitionResume(Transition transition) {
                            ViewGroupUtils.suppressLayout(viewGroup3, true);
                        }
                    });
                }
                return mergeAnimators;
            }
            return null;
        }
        return null;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }
}
