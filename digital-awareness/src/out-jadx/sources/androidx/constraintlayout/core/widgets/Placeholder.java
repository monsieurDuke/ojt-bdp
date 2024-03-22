package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;

/* loaded from: classes.dex */
public class Placeholder extends VirtualLayout {
    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem system, boolean optimize) {
        super.addToSolver(system, optimize);
        if (this.mWidgetsCount > 0) {
            ConstraintWidget constraintWidget = this.mWidgets[0];
            constraintWidget.resetAllConstraints();
            constraintWidget.connect(ConstraintAnchor.Type.LEFT, this, ConstraintAnchor.Type.LEFT);
            constraintWidget.connect(ConstraintAnchor.Type.RIGHT, this, ConstraintAnchor.Type.RIGHT);
            constraintWidget.connect(ConstraintAnchor.Type.TOP, this, ConstraintAnchor.Type.TOP);
            constraintWidget.connect(ConstraintAnchor.Type.BOTTOM, this, ConstraintAnchor.Type.BOTTOM);
        }
    }

    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    public void measure(int widthMode, int widthSize, int heightMode, int heightSize) {
        int paddingLeft = 0 + getPaddingLeft() + getPaddingRight();
        int paddingTop = 0 + getPaddingTop() + getPaddingBottom();
        if (this.mWidgetsCount > 0) {
            paddingLeft += this.mWidgets[0].getWidth();
            paddingTop += this.mWidgets[0].getHeight();
        }
        int max = Math.max(getMinWidth(), paddingLeft);
        int max2 = Math.max(getMinHeight(), paddingTop);
        int i = 0;
        int i2 = 0;
        if (widthMode == 1073741824) {
            i = widthSize;
        } else if (widthMode == Integer.MIN_VALUE) {
            i = Math.min(max, widthSize);
        } else if (widthMode == 0) {
            i = max;
        }
        if (heightMode == 1073741824) {
            i2 = heightSize;
        } else if (heightMode == Integer.MIN_VALUE) {
            i2 = Math.min(max2, heightSize);
        } else if (heightMode == 0) {
            i2 = max2;
        }
        setMeasure(i, i2);
        setWidth(i);
        setHeight(i2);
        needsCallbackFromSolver(this.mWidgetsCount > 0);
    }
}
