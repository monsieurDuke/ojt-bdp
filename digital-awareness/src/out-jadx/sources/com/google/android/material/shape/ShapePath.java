package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ShapePath {
    protected static final float ANGLE_LEFT = 180.0f;
    private static final float ANGLE_UP = 270.0f;
    private boolean containsIncompatibleShadowOp;

    @Deprecated
    public float currentShadowAngle;

    @Deprecated
    public float endShadowAngle;

    @Deprecated
    public float endX;

    @Deprecated
    public float endY;
    private final List<PathOperation> operations = new ArrayList();
    private final List<ShadowCompatOperation> shadowCompatOperations = new ArrayList();

    @Deprecated
    public float startX;

    @Deprecated
    public float startY;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ArcShadowOperation extends ShadowCompatOperation {
        private final PathArcOperation operation;

        public ArcShadowOperation(PathArcOperation operation) {
            this.operation = operation;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public void draw(Matrix transform, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            shadowRenderer.drawCornerShadow(canvas, transform, new RectF(this.operation.getLeft(), this.operation.getTop(), this.operation.getRight(), this.operation.getBottom()), shadowElevation, this.operation.getStartAngle(), this.operation.getSweepAngle());
        }
    }

    /* loaded from: classes.dex */
    static class LineShadowOperation extends ShadowCompatOperation {
        private final PathLineOperation operation;
        private final float startX;
        private final float startY;

        public LineShadowOperation(PathLineOperation operation, float startX, float startY) {
            this.operation = operation;
            this.startX = startX;
            this.startY = startY;
        }

        @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
        public void draw(Matrix transform, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(this.operation.y - this.startY, this.operation.x - this.startX), 0.0f);
            Matrix matrix = new Matrix(transform);
            matrix.preTranslate(this.startX, this.startY);
            matrix.preRotate(getAngle());
            shadowRenderer.drawEdgeShadow(canvas, matrix, rectF, shadowElevation);
        }

        float getAngle() {
            return (float) Math.toDegrees(Math.atan((this.operation.y - this.startY) / (this.operation.x - this.startX)));
        }
    }

    /* loaded from: classes.dex */
    public static class PathArcOperation extends PathOperation {
        private static final RectF rectF = new RectF();

        @Deprecated
        public float bottom;

        @Deprecated
        public float left;

        @Deprecated
        public float right;

        @Deprecated
        public float startAngle;

        @Deprecated
        public float sweepAngle;

        @Deprecated
        public float top;

        public PathArcOperation(float left, float top, float right, float bottom) {
            setLeft(left);
            setTop(top);
            setRight(right);
            setBottom(bottom);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getBottom() {
            return this.bottom;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getLeft() {
            return this.left;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getRight() {
            return this.right;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getStartAngle() {
            return this.startAngle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getSweepAngle() {
            return this.sweepAngle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getTop() {
            return this.top;
        }

        private void setBottom(float bottom) {
            this.bottom = bottom;
        }

        private void setLeft(float left) {
            this.left = left;
        }

        private void setRight(float right) {
            this.right = right;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSweepAngle(float sweepAngle) {
            this.sweepAngle = sweepAngle;
        }

        private void setTop(float top) {
            this.top = top;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix transform, Path path) {
            Matrix matrix = this.matrix;
            transform.invert(matrix);
            path.transform(matrix);
            RectF rectF2 = rectF;
            rectF2.set(getLeft(), getTop(), getRight(), getBottom());
            path.arcTo(rectF2, getStartAngle(), getSweepAngle(), false);
            path.transform(transform);
        }
    }

    /* loaded from: classes.dex */
    public static class PathCubicOperation extends PathOperation {
        private float controlX1;
        private float controlX2;
        private float controlY1;
        private float controlY2;
        private float endX;
        private float endY;

        public PathCubicOperation(float controlX1, float controlY1, float controlX2, float controlY2, float endX, float endY) {
            setControlX1(controlX1);
            setControlY1(controlY1);
            setControlX2(controlX2);
            setControlY2(controlY2);
            setEndX(endX);
            setEndY(endY);
        }

        private float getControlX1() {
            return this.controlX1;
        }

        private float getControlX2() {
            return this.controlX2;
        }

        private float getControlY1() {
            return this.controlY1;
        }

        private float getControlY2() {
            return this.controlY1;
        }

        private float getEndX() {
            return this.endX;
        }

        private float getEndY() {
            return this.endY;
        }

        private void setControlX1(float controlX1) {
            this.controlX1 = controlX1;
        }

        private void setControlX2(float controlX2) {
            this.controlX2 = controlX2;
        }

        private void setControlY1(float controlY1) {
            this.controlY1 = controlY1;
        }

        private void setControlY2(float controlY2) {
            this.controlY2 = controlY2;
        }

        private void setEndX(float endX) {
            this.endX = endX;
        }

        private void setEndY(float endY) {
            this.endY = endY;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix transform, Path path) {
            Matrix matrix = this.matrix;
            transform.invert(matrix);
            path.transform(matrix);
            path.cubicTo(this.controlX1, this.controlY1, this.controlX2, this.controlY2, this.endX, this.endY);
            path.transform(transform);
        }
    }

    /* loaded from: classes.dex */
    public static class PathLineOperation extends PathOperation {
        private float x;
        private float y;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix transform, Path path) {
            Matrix matrix = this.matrix;
            transform.invert(matrix);
            path.transform(matrix);
            path.lineTo(this.x, this.y);
            path.transform(transform);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class PathOperation {
        protected final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    /* loaded from: classes.dex */
    public static class PathQuadOperation extends PathOperation {

        @Deprecated
        public float controlX;

        @Deprecated
        public float controlY;

        @Deprecated
        public float endX;

        @Deprecated
        public float endY;

        private float getControlX() {
            return this.controlX;
        }

        private float getControlY() {
            return this.controlY;
        }

        private float getEndX() {
            return this.endX;
        }

        private float getEndY() {
            return this.endY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setControlX(float controlX) {
            this.controlX = controlX;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setControlY(float controlY) {
            this.controlY = controlY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndX(float endX) {
            this.endX = endX;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndY(float endY) {
            this.endY = endY;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix transform, Path path) {
            Matrix matrix = this.matrix;
            transform.invert(matrix);
            path.transform(matrix);
            path.quadTo(getControlX(), getControlY(), getEndX(), getEndY());
            path.transform(transform);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class ShadowCompatOperation {
        static final Matrix IDENTITY_MATRIX = new Matrix();

        ShadowCompatOperation() {
        }

        public abstract void draw(Matrix matrix, ShadowRenderer shadowRenderer, int i, Canvas canvas);

        public final void draw(ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
            draw(IDENTITY_MATRIX, shadowRenderer, shadowElevation, canvas);
        }
    }

    public ShapePath() {
        reset(0.0f, 0.0f);
    }

    public ShapePath(float startX, float startY) {
        reset(startX, startY);
    }

    private void addConnectingShadowIfNecessary(float nextShadowAngle) {
        if (getCurrentShadowAngle() == nextShadowAngle) {
            return;
        }
        float currentShadowAngle = ((nextShadowAngle - getCurrentShadowAngle()) + 360.0f) % 360.0f;
        if (currentShadowAngle > ANGLE_LEFT) {
            return;
        }
        PathArcOperation pathArcOperation = new PathArcOperation(getEndX(), getEndY(), getEndX(), getEndY());
        pathArcOperation.setStartAngle(getCurrentShadowAngle());
        pathArcOperation.setSweepAngle(currentShadowAngle);
        this.shadowCompatOperations.add(new ArcShadowOperation(pathArcOperation));
        setCurrentShadowAngle(nextShadowAngle);
    }

    private void addShadowCompatOperation(ShadowCompatOperation shadowOperation, float startShadowAngle, float endShadowAngle) {
        addConnectingShadowIfNecessary(startShadowAngle);
        this.shadowCompatOperations.add(shadowOperation);
        setCurrentShadowAngle(endShadowAngle);
    }

    private float getCurrentShadowAngle() {
        return this.currentShadowAngle;
    }

    private float getEndShadowAngle() {
        return this.endShadowAngle;
    }

    private void setCurrentShadowAngle(float currentShadowAngle) {
        this.currentShadowAngle = currentShadowAngle;
    }

    private void setEndShadowAngle(float endShadowAngle) {
        this.endShadowAngle = endShadowAngle;
    }

    private void setEndX(float endX) {
        this.endX = endX;
    }

    private void setEndY(float endY) {
        this.endY = endY;
    }

    private void setStartX(float startX) {
        this.startX = startX;
    }

    private void setStartY(float startY) {
        this.startY = startY;
    }

    public void addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        PathArcOperation pathArcOperation = new PathArcOperation(left, top, right, bottom);
        pathArcOperation.setStartAngle(startAngle);
        pathArcOperation.setSweepAngle(sweepAngle);
        this.operations.add(pathArcOperation);
        ArcShadowOperation arcShadowOperation = new ArcShadowOperation(pathArcOperation);
        float f = startAngle + sweepAngle;
        boolean z = sweepAngle < 0.0f;
        addShadowCompatOperation(arcShadowOperation, z ? (startAngle + ANGLE_LEFT) % 360.0f : startAngle, z ? (ANGLE_LEFT + f) % 360.0f : f);
        setEndX(((left + right) * 0.5f) + (((right - left) / 2.0f) * ((float) Math.cos(Math.toRadians(startAngle + sweepAngle)))));
        setEndY(((top + bottom) * 0.5f) + (((bottom - top) / 2.0f) * ((float) Math.sin(Math.toRadians(startAngle + sweepAngle)))));
    }

    public void applyToPath(Matrix transform, Path path) {
        int size = this.operations.size();
        for (int i = 0; i < size; i++) {
            this.operations.get(i).applyToPath(transform, path);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean containsIncompatibleShadowOp() {
        return this.containsIncompatibleShadowOp;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ShadowCompatOperation createShadowCompatOperation(Matrix transform) {
        addConnectingShadowIfNecessary(getEndShadowAngle());
        final Matrix matrix = new Matrix(transform);
        final ArrayList arrayList = new ArrayList(this.shadowCompatOperations);
        return new ShadowCompatOperation() { // from class: com.google.android.material.shape.ShapePath.1
            @Override // com.google.android.material.shape.ShapePath.ShadowCompatOperation
            public void draw(Matrix matrix2, ShadowRenderer shadowRenderer, int shadowElevation, Canvas canvas) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ShadowCompatOperation) it.next()).draw(matrix, shadowRenderer, shadowElevation, canvas);
                }
            }
        };
    }

    public void cubicToPoint(float controlX1, float controlY1, float controlX2, float controlY2, float toX, float toY) {
        this.operations.add(new PathCubicOperation(controlX1, controlY1, controlX2, controlY2, toX, toY));
        this.containsIncompatibleShadowOp = true;
        setEndX(toX);
        setEndY(toY);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getEndX() {
        return this.endX;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getEndY() {
        return this.endY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getStartX() {
        return this.startX;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getStartY() {
        return this.startY;
    }

    public void lineTo(float x, float y) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.x = x;
        pathLineOperation.y = y;
        this.operations.add(pathLineOperation);
        LineShadowOperation lineShadowOperation = new LineShadowOperation(pathLineOperation, getEndX(), getEndY());
        addShadowCompatOperation(lineShadowOperation, lineShadowOperation.getAngle() + ANGLE_UP, lineShadowOperation.getAngle() + ANGLE_UP);
        setEndX(x);
        setEndY(y);
    }

    public void quadToPoint(float controlX, float controlY, float toX, float toY) {
        PathQuadOperation pathQuadOperation = new PathQuadOperation();
        pathQuadOperation.setControlX(controlX);
        pathQuadOperation.setControlY(controlY);
        pathQuadOperation.setEndX(toX);
        pathQuadOperation.setEndY(toY);
        this.operations.add(pathQuadOperation);
        this.containsIncompatibleShadowOp = true;
        setEndX(toX);
        setEndY(toY);
    }

    public void reset(float startX, float startY) {
        reset(startX, startY, ANGLE_UP, 0.0f);
    }

    public void reset(float startX, float startY, float shadowStartAngle, float shadowSweepAngle) {
        setStartX(startX);
        setStartY(startY);
        setEndX(startX);
        setEndY(startY);
        setCurrentShadowAngle(shadowStartAngle);
        setEndShadowAngle((shadowStartAngle + shadowSweepAngle) % 360.0f);
        this.operations.clear();
        this.shadowCompatOperations.clear();
        this.containsIncompatibleShadowOp = false;
    }
}
