package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.transition.TransitionUtils;
import java.util.Map;

/* loaded from: classes.dex */
public class ChangeImageTransform extends Transition {
    private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final String[] sTransitionProperties = {PROPNAME_MATRIX, PROPNAME_BOUNDS};
    private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>() { // from class: androidx.transition.ChangeImageTransform.1
        @Override // android.animation.TypeEvaluator
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            return null;
        }
    };
    private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") { // from class: androidx.transition.ChangeImageTransform.2
        @Override // android.util.Property
        public Matrix get(ImageView object) {
            return null;
        }

        @Override // android.util.Property
        public void set(ImageView view, Matrix matrix) {
            ImageViewUtils.animateTransform(view, matrix);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: androidx.transition.ChangeImageTransform$3, reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if ((view instanceof ImageView) && view.getVisibility() == 0) {
            ImageView imageView = (ImageView) view;
            if (imageView.getDrawable() == null) {
                return;
            }
            Map<String, Object> map = transitionValues.values;
            map.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            map.put(PROPNAME_MATRIX, copyImageMatrix(imageView));
        }
    }

    private static Matrix centerCropMatrix(ImageView view) {
        Drawable drawable = view.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int width = view.getWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int height = view.getHeight();
        float max = Math.max(width / intrinsicWidth, height / intrinsicHeight);
        int round = Math.round((width - (intrinsicWidth * max)) / 2.0f);
        int round2 = Math.round((height - (intrinsicHeight * max)) / 2.0f);
        Matrix matrix = new Matrix();
        matrix.postScale(max, max);
        matrix.postTranslate(round, round2);
        return matrix;
    }

    private static Matrix copyImageMatrix(ImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
            switch (AnonymousClass3.$SwitchMap$android$widget$ImageView$ScaleType[view.getScaleType().ordinal()]) {
                case 1:
                    return fitXYMatrix(view);
                case 2:
                    return centerCropMatrix(view);
            }
        }
        return new Matrix(view.getImageMatrix());
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix startMatrix, Matrix endMatrix) {
        return ObjectAnimator.ofObject(imageView, (Property<ImageView, V>) ANIMATED_TRANSFORM_PROPERTY, (TypeEvaluator) new TransitionUtils.MatrixEvaluator(), (Object[]) new Matrix[]{startMatrix, endMatrix});
    }

    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject(imageView, (Property<ImageView, V>) ANIMATED_TRANSFORM_PROPERTY, (TypeEvaluator) NULL_MATRIX_EVALUATOR, (Object[]) new Matrix[]{MatrixUtils.IDENTITY_MATRIX, MatrixUtils.IDENTITY_MATRIX});
    }

    private static Matrix fitXYMatrix(ImageView view) {
        Drawable drawable = view.getDrawable();
        Matrix matrix = new Matrix();
        matrix.postScale(view.getWidth() / drawable.getIntrinsicWidth(), view.getHeight() / drawable.getIntrinsicHeight());
        return matrix;
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
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect rect = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect rect2 = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        if (rect == null || rect2 == null) {
            return null;
        }
        Matrix matrix = (Matrix) startValues.values.get(PROPNAME_MATRIX);
        Matrix matrix2 = (Matrix) endValues.values.get(PROPNAME_MATRIX);
        boolean z = (matrix == null && matrix2 == null) || (matrix != null && matrix.equals(matrix2));
        if (rect.equals(rect2) && z) {
            return null;
        }
        ImageView imageView = (ImageView) endValues.view;
        Drawable drawable = imageView.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return createNullAnimator(imageView);
        }
        if (matrix == null) {
            matrix = MatrixUtils.IDENTITY_MATRIX;
        }
        if (matrix2 == null) {
            matrix2 = MatrixUtils.IDENTITY_MATRIX;
        }
        ANIMATED_TRANSFORM_PROPERTY.set(imageView, matrix);
        return createMatrixAnimator(imageView, matrix, matrix2);
    }

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }
}
