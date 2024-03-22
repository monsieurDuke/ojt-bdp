package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_CHAIN_NEW = 3;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mDisplayedWidgets;
    private int mHorizontalStyle = -1;
    private int mVerticalStyle = -1;
    private int mFirstHorizontalStyle = -1;
    private int mFirstVerticalStyle = -1;
    private int mLastHorizontalStyle = -1;
    private int mLastVerticalStyle = -1;
    private float mHorizontalBias = 0.5f;
    private float mVerticalBias = 0.5f;
    private float mFirstHorizontalBias = 0.5f;
    private float mFirstVerticalBias = 0.5f;
    private float mLastHorizontalBias = 0.5f;
    private float mLastVerticalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mVerticalGap = 0;
    private int mHorizontalAlign = 2;
    private int mVerticalAlign = 2;
    private int mWrapMode = 0;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private int[] mAlignedDimensions = null;
    private int mDisplayedWidgetsCount = 0;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class WidgetsList {
        private ConstraintAnchor mBottom;
        private ConstraintAnchor mLeft;
        private int mMax;
        private int mOrientation;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private ConstraintAnchor mRight;
        private ConstraintAnchor mTop;
        private ConstraintWidget biggest = null;
        int biggestDimension = 0;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mStartIndex = 0;
        private int mCount = 0;
        private int mNbMatchConstraintsWidgets = 0;

        public WidgetsList(int orientation, ConstraintAnchor left, ConstraintAnchor top, ConstraintAnchor right, ConstraintAnchor bottom, int max) {
            this.mOrientation = 0;
            this.mPaddingLeft = 0;
            this.mPaddingTop = 0;
            this.mPaddingRight = 0;
            this.mPaddingBottom = 0;
            this.mMax = 0;
            this.mOrientation = orientation;
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = max;
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int i = this.mCount;
            for (int i2 = 0; i2 < i && this.mStartIndex + i2 < Flow.this.mDisplayedWidgetsCount; i2++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i2];
                if (this.mOrientation == 0) {
                    int width = constraintWidget.getWidth();
                    int i3 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i3 = 0;
                    }
                    this.mWidth += width + i3;
                    int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.biggestDimension < widgetHeight) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetHeight;
                        this.mHeight = widgetHeight;
                    }
                } else {
                    int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    int i4 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i4 = 0;
                    }
                    this.mHeight += widgetHeight2 + i4;
                    if (this.biggest == null || this.biggestDimension < widgetWidth) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetWidth;
                        this.mWidth = widgetWidth;
                    }
                }
            }
        }

        public void add(ConstraintWidget widget) {
            if (this.mOrientation == 0) {
                int widgetWidth = Flow.this.getWidgetWidth(widget, this.mMax);
                if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetWidth = 0;
                }
                int i = Flow.this.mHorizontalGap;
                if (widget.getVisibility() == 8) {
                    i = 0;
                }
                this.mWidth += widgetWidth + i;
                int widgetHeight = Flow.this.getWidgetHeight(widget, this.mMax);
                if (this.biggest == null || this.biggestDimension < widgetHeight) {
                    this.biggest = widget;
                    this.biggestDimension = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                int widgetWidth2 = Flow.this.getWidgetWidth(widget, this.mMax);
                int widgetHeight2 = Flow.this.getWidgetHeight(widget, this.mMax);
                if (widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetHeight2 = 0;
                }
                int i2 = Flow.this.mVerticalGap;
                if (widget.getVisibility() == 8) {
                    i2 = 0;
                }
                this.mHeight += widgetHeight2 + i2;
                if (this.biggest == null || this.biggestDimension < widgetWidth2) {
                    this.biggest = widget;
                    this.biggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public void clear() {
            this.biggestDimension = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void createConstraints(boolean isInRtl, int chainIndex, boolean isLastChain) {
            int i = this.mCount;
            for (int i2 = 0; i2 < i && this.mStartIndex + i2 < Flow.this.mDisplayedWidgetsCount; i2++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i2];
                if (constraintWidget != null) {
                    constraintWidget.resetAnchors();
                }
            }
            if (i == 0 || this.biggest == null) {
                return;
            }
            boolean z = isLastChain && chainIndex == 0;
            int i3 = -1;
            int i4 = -1;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = i5;
                if (isInRtl) {
                    i6 = (i - 1) - i5;
                }
                if (this.mStartIndex + i6 >= Flow.this.mDisplayedWidgetsCount) {
                    break;
                }
                ConstraintWidget constraintWidget2 = Flow.this.mDisplayedWidgets[this.mStartIndex + i6];
                if (constraintWidget2 != null && constraintWidget2.getVisibility() == 0) {
                    if (i3 == -1) {
                        i3 = i5;
                    }
                    i4 = i5;
                }
            }
            ConstraintWidget constraintWidget3 = null;
            if (this.mOrientation != 0) {
                ConstraintWidget constraintWidget4 = this.biggest;
                constraintWidget4.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
                int i7 = this.mPaddingLeft;
                if (chainIndex > 0) {
                    i7 += Flow.this.mHorizontalGap;
                }
                if (isInRtl) {
                    constraintWidget4.mRight.connect(this.mRight, i7);
                    if (isLastChain) {
                        constraintWidget4.mLeft.connect(this.mLeft, this.mPaddingRight);
                    }
                    if (chainIndex > 0) {
                        this.mRight.mOwner.mLeft.connect(constraintWidget4.mRight, 0);
                    }
                } else {
                    constraintWidget4.mLeft.connect(this.mLeft, i7);
                    if (isLastChain) {
                        constraintWidget4.mRight.connect(this.mRight, this.mPaddingRight);
                    }
                    if (chainIndex > 0) {
                        this.mLeft.mOwner.mRight.connect(constraintWidget4.mLeft, 0);
                    }
                }
                for (int i8 = 0; i8 < i && this.mStartIndex + i8 < Flow.this.mDisplayedWidgetsCount; i8++) {
                    ConstraintWidget constraintWidget5 = Flow.this.mDisplayedWidgets[this.mStartIndex + i8];
                    if (constraintWidget5 != null) {
                        if (i8 == 0) {
                            constraintWidget5.connect(constraintWidget5.mTop, this.mTop, this.mPaddingTop);
                            int i9 = Flow.this.mVerticalStyle;
                            float f = Flow.this.mVerticalBias;
                            if (this.mStartIndex == 0 && Flow.this.mFirstVerticalStyle != -1) {
                                i9 = Flow.this.mFirstVerticalStyle;
                                f = Flow.this.mFirstVerticalBias;
                            } else if (isLastChain && Flow.this.mLastVerticalStyle != -1) {
                                i9 = Flow.this.mLastVerticalStyle;
                                f = Flow.this.mLastVerticalBias;
                            }
                            constraintWidget5.setVerticalChainStyle(i9);
                            constraintWidget5.setVerticalBiasPercent(f);
                        }
                        if (i8 == i - 1) {
                            constraintWidget5.connect(constraintWidget5.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (constraintWidget3 != null) {
                            constraintWidget5.mTop.connect(constraintWidget3.mBottom, Flow.this.mVerticalGap);
                            if (i8 == i3) {
                                constraintWidget5.mTop.setGoneMargin(this.mPaddingTop);
                            }
                            constraintWidget3.mBottom.connect(constraintWidget5.mTop, 0);
                            if (i8 == i4 + 1) {
                                constraintWidget3.mBottom.setGoneMargin(this.mPaddingBottom);
                            }
                        }
                        if (constraintWidget5 != constraintWidget4) {
                            if (isInRtl) {
                                switch (Flow.this.mHorizontalAlign) {
                                    case 0:
                                        constraintWidget5.mRight.connect(constraintWidget4.mRight, 0);
                                        break;
                                    case 1:
                                        constraintWidget5.mLeft.connect(constraintWidget4.mLeft, 0);
                                        break;
                                    case 2:
                                        constraintWidget5.mLeft.connect(constraintWidget4.mLeft, 0);
                                        constraintWidget5.mRight.connect(constraintWidget4.mRight, 0);
                                        break;
                                }
                            } else {
                                switch (Flow.this.mHorizontalAlign) {
                                    case 0:
                                        constraintWidget5.mLeft.connect(constraintWidget4.mLeft, 0);
                                        break;
                                    case 1:
                                        constraintWidget5.mRight.connect(constraintWidget4.mRight, 0);
                                        break;
                                    case 2:
                                        if (z) {
                                            constraintWidget5.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                            constraintWidget5.mRight.connect(this.mRight, this.mPaddingRight);
                                            break;
                                        } else {
                                            constraintWidget5.mLeft.connect(constraintWidget4.mLeft, 0);
                                            constraintWidget5.mRight.connect(constraintWidget4.mRight, 0);
                                            break;
                                        }
                                }
                            }
                        }
                        constraintWidget3 = constraintWidget5;
                    }
                }
                return;
            }
            ConstraintWidget constraintWidget6 = this.biggest;
            constraintWidget6.setVerticalChainStyle(Flow.this.mVerticalStyle);
            int i10 = this.mPaddingTop;
            if (chainIndex > 0) {
                i10 += Flow.this.mVerticalGap;
            }
            constraintWidget6.mTop.connect(this.mTop, i10);
            if (isLastChain) {
                constraintWidget6.mBottom.connect(this.mBottom, this.mPaddingBottom);
            }
            if (chainIndex > 0) {
                this.mTop.mOwner.mBottom.connect(constraintWidget6.mTop, 0);
            }
            ConstraintWidget constraintWidget7 = constraintWidget6;
            if (Flow.this.mVerticalAlign == 3 && !constraintWidget6.hasBaseline()) {
                int i11 = 0;
                while (true) {
                    if (i11 >= i) {
                        break;
                    }
                    int i12 = i11;
                    if (isInRtl) {
                        i12 = (i - 1) - i11;
                    }
                    if (this.mStartIndex + i12 >= Flow.this.mDisplayedWidgetsCount) {
                        break;
                    }
                    ConstraintWidget constraintWidget8 = Flow.this.mDisplayedWidgets[this.mStartIndex + i12];
                    if (constraintWidget8.hasBaseline()) {
                        constraintWidget7 = constraintWidget8;
                        break;
                    }
                    i11++;
                }
            }
            for (int i13 = 0; i13 < i; i13++) {
                int i14 = i13;
                if (isInRtl) {
                    i14 = (i - 1) - i13;
                }
                if (this.mStartIndex + i14 >= Flow.this.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget9 = Flow.this.mDisplayedWidgets[this.mStartIndex + i14];
                if (constraintWidget9 != null) {
                    if (i13 == 0) {
                        constraintWidget9.connect(constraintWidget9.mLeft, this.mLeft, this.mPaddingLeft);
                    }
                    if (i14 == 0) {
                        int i15 = Flow.this.mHorizontalStyle;
                        float f2 = Flow.this.mHorizontalBias;
                        if (isInRtl) {
                            f2 = 1.0f - f2;
                        }
                        if (this.mStartIndex == 0 && Flow.this.mFirstHorizontalStyle != -1) {
                            i15 = Flow.this.mFirstHorizontalStyle;
                            float f3 = Flow.this.mFirstHorizontalBias;
                            if (isInRtl) {
                                f3 = 1.0f - f3;
                            }
                            f2 = f3;
                        } else if (isLastChain && Flow.this.mLastHorizontalStyle != -1) {
                            i15 = Flow.this.mLastHorizontalStyle;
                            float f4 = Flow.this.mLastHorizontalBias;
                            if (isInRtl) {
                                f4 = 1.0f - f4;
                            }
                            f2 = f4;
                        }
                        constraintWidget9.setHorizontalChainStyle(i15);
                        constraintWidget9.setHorizontalBiasPercent(f2);
                    }
                    if (i13 == i - 1) {
                        constraintWidget9.connect(constraintWidget9.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget3 != null) {
                        constraintWidget9.mLeft.connect(constraintWidget3.mRight, Flow.this.mHorizontalGap);
                        if (i13 == i3) {
                            constraintWidget9.mLeft.setGoneMargin(this.mPaddingLeft);
                        }
                        constraintWidget3.mRight.connect(constraintWidget9.mLeft, 0);
                        if (i13 == i4 + 1) {
                            constraintWidget3.mRight.setGoneMargin(this.mPaddingRight);
                        }
                    }
                    if (constraintWidget9 != constraintWidget6) {
                        if (Flow.this.mVerticalAlign != 3 || !constraintWidget7.hasBaseline() || constraintWidget9 == constraintWidget7 || !constraintWidget9.hasBaseline()) {
                            switch (Flow.this.mVerticalAlign) {
                                case 0:
                                    constraintWidget9.mTop.connect(constraintWidget6.mTop, 0);
                                    break;
                                case 1:
                                    constraintWidget9.mBottom.connect(constraintWidget6.mBottom, 0);
                                    break;
                                default:
                                    if (z) {
                                        constraintWidget9.mTop.connect(this.mTop, this.mPaddingTop);
                                        constraintWidget9.mBottom.connect(this.mBottom, this.mPaddingBottom);
                                        break;
                                    } else {
                                        constraintWidget9.mTop.connect(constraintWidget6.mTop, 0);
                                        constraintWidget9.mBottom.connect(constraintWidget6.mBottom, 0);
                                        break;
                                    }
                            }
                        } else {
                            constraintWidget9.mBaseline.connect(constraintWidget7.mBaseline, 0);
                        }
                    }
                    constraintWidget3 = constraintWidget9;
                }
            }
        }

        public int getHeight() {
            return this.mOrientation == 1 ? this.mHeight - Flow.this.mVerticalGap : this.mHeight;
        }

        public int getWidth() {
            return this.mOrientation == 0 ? this.mWidth - Flow.this.mHorizontalGap : this.mWidth;
        }

        public void measureMatchConstraints(int availableSpace) {
            int i = this.mNbMatchConstraintsWidgets;
            if (i == 0) {
                return;
            }
            int i2 = this.mCount;
            int i3 = availableSpace / i;
            for (int i4 = 0; i4 < i2 && this.mStartIndex + i4 < Flow.this.mDisplayedWidgetsCount; i4++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i4];
                if (this.mOrientation == 0) {
                    if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                        Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i3, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                    }
                } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i3);
                }
            }
            recomputeDimensions();
        }

        public void setStartIndex(int value) {
            this.mStartIndex = value;
        }

        public void setup(int orientation, ConstraintAnchor left, ConstraintAnchor top, ConstraintAnchor right, ConstraintAnchor bottom, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom, int max) {
            this.mOrientation = orientation;
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
            this.mPaddingLeft = paddingLeft;
            this.mPaddingTop = paddingTop;
            this.mPaddingRight = paddingRight;
            this.mPaddingBottom = paddingBottom;
            this.mMax = max;
        }
    }

    private void createAlignedConstraints(boolean isInRtl) {
        ConstraintWidget constraintWidget;
        if (this.mAlignedDimensions == null || this.mAlignedBiggestElementsInCols == null || this.mAlignedBiggestElementsInRows == null) {
            return;
        }
        for (int i = 0; i < this.mDisplayedWidgetsCount; i++) {
            this.mDisplayedWidgets[i].resetAnchors();
        }
        int[] iArr = this.mAlignedDimensions;
        int i2 = iArr[0];
        int i3 = iArr[1];
        ConstraintWidget constraintWidget2 = null;
        float f = this.mHorizontalBias;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i4;
            if (isInRtl) {
                i5 = (i2 - i4) - 1;
                f = 1.0f - this.mHorizontalBias;
            }
            ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[i5];
            if (constraintWidget3 != null && constraintWidget3.getVisibility() != 8) {
                if (i4 == 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                    constraintWidget3.setHorizontalChainStyle(this.mHorizontalStyle);
                    constraintWidget3.setHorizontalBiasPercent(f);
                }
                if (i4 == i2 - 1) {
                    constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                }
                if (i4 > 0 && constraintWidget2 != null) {
                    constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.mHorizontalGap);
                    constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                }
                constraintWidget2 = constraintWidget3;
            }
        }
        for (int i6 = 0; i6 < i3; i6++) {
            ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInRows[i6];
            if (constraintWidget4 != null && constraintWidget4.getVisibility() != 8) {
                if (i6 == 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                    constraintWidget4.setVerticalChainStyle(this.mVerticalStyle);
                    constraintWidget4.setVerticalBiasPercent(this.mVerticalBias);
                }
                if (i6 == i3 - 1) {
                    constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                }
                if (i6 > 0 && constraintWidget2 != null) {
                    constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.mVerticalGap);
                    constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                }
                constraintWidget2 = constraintWidget4;
            }
        }
        for (int i7 = 0; i7 < i2; i7++) {
            for (int i8 = 0; i8 < i3; i8++) {
                int i9 = (i8 * i2) + i7;
                if (this.mOrientation == 1) {
                    i9 = (i7 * i3) + i8;
                }
                ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                if (i9 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i9]) != null && constraintWidget.getVisibility() != 8) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInCols[i7];
                    ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInRows[i8];
                    if (constraintWidget != constraintWidget5) {
                        constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                        constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                    }
                    if (constraintWidget != constraintWidget6) {
                        constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                        constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetHeight(ConstraintWidget widget, int max) {
        if (widget == null) {
            return 0;
        }
        if (widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (widget.mMatchConstraintDefaultHeight == 0) {
                return 0;
            }
            if (widget.mMatchConstraintDefaultHeight == 2) {
                int i = (int) (widget.mMatchConstraintPercentHeight * max);
                if (i != widget.getHeight()) {
                    widget.setMeasureRequested(true);
                    measure(widget, widget.getHorizontalDimensionBehaviour(), widget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i);
                }
                return i;
            }
            if (widget.mMatchConstraintDefaultHeight == 1) {
                return widget.getHeight();
            }
            if (widget.mMatchConstraintDefaultHeight == 3) {
                return (int) ((widget.getWidth() * widget.mDimensionRatio) + 0.5f);
            }
        }
        return widget.getHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetWidth(ConstraintWidget widget, int max) {
        if (widget == null) {
            return 0;
        }
        if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (widget.mMatchConstraintDefaultWidth == 0) {
                return 0;
            }
            if (widget.mMatchConstraintDefaultWidth == 2) {
                int i = (int) (widget.mMatchConstraintPercentWidth * max);
                if (i != widget.getWidth()) {
                    widget.setMeasureRequested(true);
                    measure(widget, ConstraintWidget.DimensionBehaviour.FIXED, i, widget.getVerticalDimensionBehaviour(), widget.getHeight());
                }
                return i;
            }
            if (widget.mMatchConstraintDefaultWidth == 1) {
                return widget.getWidth();
            }
            if (widget.mMatchConstraintDefaultWidth == 3) {
                return (int) ((widget.getHeight() * widget.mDimensionRatio) + 0.5f);
            }
        }
        return widget.getWidth();
    }

    private void measureAligned(ConstraintWidget[] widgets, int count, int orientation, int max, int[] measured) {
        ConstraintWidget constraintWidget;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        if (orientation == 0) {
            i2 = this.mMaxElementsWrap;
            if (i2 <= 0) {
                int i3 = 0;
                i2 = 0;
                for (int i4 = 0; i4 < count; i4++) {
                    if (i4 > 0) {
                        i3 += this.mHorizontalGap;
                    }
                    ConstraintWidget constraintWidget2 = widgets[i4];
                    if (constraintWidget2 != null) {
                        i3 += getWidgetWidth(constraintWidget2, max);
                        if (i3 > max) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
        } else {
            i = this.mMaxElementsWrap;
            if (i <= 0) {
                int i5 = 0;
                i = 0;
                for (int i6 = 0; i6 < count; i6++) {
                    if (i6 > 0) {
                        i5 += this.mVerticalGap;
                    }
                    ConstraintWidget constraintWidget3 = widgets[i6];
                    if (constraintWidget3 != null) {
                        i5 += getWidgetHeight(constraintWidget3, max);
                        if (i5 > max) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
        }
        if (this.mAlignedDimensions == null) {
            this.mAlignedDimensions = new int[2];
        }
        int i7 = 1;
        if ((i == 0 && orientation == 1) || (i2 == 0 && orientation == 0)) {
            z = true;
        }
        while (!z) {
            if (orientation == 0) {
                i = (int) Math.ceil(count / i2);
            } else {
                i2 = (int) Math.ceil(count / i);
            }
            ConstraintWidget[] constraintWidgetArr = this.mAlignedBiggestElementsInCols;
            if (constraintWidgetArr == null || constraintWidgetArr.length < i2) {
                this.mAlignedBiggestElementsInCols = new ConstraintWidget[i2];
            } else {
                Arrays.fill(constraintWidgetArr, (Object) null);
            }
            ConstraintWidget[] constraintWidgetArr2 = this.mAlignedBiggestElementsInRows;
            if (constraintWidgetArr2 == null || constraintWidgetArr2.length < i) {
                this.mAlignedBiggestElementsInRows = new ConstraintWidget[i];
            } else {
                Arrays.fill(constraintWidgetArr2, (Object) null);
            }
            int i8 = 0;
            while (i8 < i2) {
                int i9 = 0;
                while (i9 < i) {
                    int i10 = (i9 * i2) + i8;
                    if (orientation == i7) {
                        i10 = (i8 * i) + i9;
                    }
                    if (i10 < widgets.length && (constraintWidget = widgets[i10]) != null) {
                        int widgetWidth = getWidgetWidth(constraintWidget, max);
                        ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInCols[i8];
                        if (constraintWidget4 == null || constraintWidget4.getWidth() < widgetWidth) {
                            this.mAlignedBiggestElementsInCols[i8] = constraintWidget;
                        }
                        int widgetHeight = getWidgetHeight(constraintWidget, max);
                        ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInRows[i9];
                        if (constraintWidget5 == null || constraintWidget5.getHeight() < widgetHeight) {
                            this.mAlignedBiggestElementsInRows[i9] = constraintWidget;
                        }
                    }
                    i9++;
                    i7 = 1;
                }
                i8++;
                i7 = 1;
            }
            int i11 = 0;
            for (int i12 = 0; i12 < i2; i12++) {
                ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInCols[i12];
                if (constraintWidget6 != null) {
                    if (i12 > 0) {
                        i11 += this.mHorizontalGap;
                    }
                    i11 += getWidgetWidth(constraintWidget6, max);
                }
            }
            int i13 = 0;
            for (int i14 = 0; i14 < i; i14++) {
                ConstraintWidget constraintWidget7 = this.mAlignedBiggestElementsInRows[i14];
                if (constraintWidget7 != null) {
                    if (i14 > 0) {
                        i13 += this.mVerticalGap;
                    }
                    i13 += getWidgetHeight(constraintWidget7, max);
                }
            }
            measured[0] = i11;
            measured[1] = i13;
            if (orientation == 0) {
                if (i11 <= max) {
                    z = true;
                } else if (i2 > 1) {
                    i2--;
                } else {
                    z = true;
                }
            } else if (i13 <= max) {
                z = true;
            } else if (i > 1) {
                i--;
            } else {
                z = true;
            }
            i7 = 1;
        }
        int[] iArr = this.mAlignedDimensions;
        iArr[0] = i2;
        iArr[1] = i;
    }

    private void measureChainWrap(ConstraintWidget[] widgets, int count, int orientation, int max, int[] measured) {
        WidgetsList widgetsList;
        int i;
        int i2;
        int i3;
        if (count == 0) {
            return;
        }
        this.mChainList.clear();
        WidgetsList widgetsList2 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
        this.mChainList.add(widgetsList2);
        int i4 = 0;
        if (orientation == 0) {
            int i5 = 0;
            WidgetsList widgetsList3 = widgetsList2;
            int i6 = 0;
            while (i6 < count) {
                ConstraintWidget constraintWidget = widgets[i6];
                int widgetWidth = getWidgetWidth(constraintWidget, max);
                int i7 = constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                boolean z = (i5 == max || (this.mHorizontalGap + i5) + widgetWidth > max) && widgetsList3.biggest != null;
                if ((z || i6 <= 0 || (i3 = this.mMaxElementsWrap) <= 0 || i6 % i3 != 0) ? z : true) {
                    WidgetsList widgetsList4 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
                    widgetsList4.setStartIndex(i6);
                    this.mChainList.add(widgetsList4);
                    widgetsList3 = widgetsList4;
                    i5 = widgetWidth;
                } else {
                    i5 = i6 > 0 ? i5 + this.mHorizontalGap + widgetWidth : widgetWidth;
                }
                widgetsList3.add(constraintWidget);
                i6++;
                i4 = i7;
            }
            widgetsList = widgetsList3;
        } else {
            int i8 = 0;
            WidgetsList widgetsList5 = widgetsList2;
            int i9 = 0;
            while (i9 < count) {
                ConstraintWidget constraintWidget2 = widgets[i9];
                int widgetHeight = getWidgetHeight(constraintWidget2, max);
                int i10 = constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                boolean z2 = (i8 == max || (this.mVerticalGap + i8) + widgetHeight > max) && widgetsList5.biggest != null;
                if ((z2 || i9 <= 0 || (i = this.mMaxElementsWrap) <= 0 || i9 % i != 0) ? z2 : true) {
                    WidgetsList widgetsList6 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
                    widgetsList6.setStartIndex(i9);
                    this.mChainList.add(widgetsList6);
                    widgetsList5 = widgetsList6;
                    i8 = widgetHeight;
                } else {
                    i8 = i9 > 0 ? i8 + this.mVerticalGap + widgetHeight : widgetHeight;
                }
                widgetsList5.add(constraintWidget2);
                i9++;
                i4 = i10;
            }
            widgetsList = widgetsList5;
        }
        int size = this.mChainList.size();
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = this.mTop;
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = this.mBottom;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        boolean z3 = getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i4 > 0 && z3) {
            int i11 = 0;
            while (i11 < size) {
                boolean z4 = z3;
                WidgetsList widgetsList7 = this.mChainList.get(i11);
                if (orientation == 0) {
                    i2 = i4;
                    widgetsList7.measureMatchConstraints(max - widgetsList7.getWidth());
                } else {
                    i2 = i4;
                    widgetsList7.measureMatchConstraints(max - widgetsList7.getHeight());
                }
                i11++;
                z3 = z4;
                i4 = i2;
            }
        }
        int i12 = paddingTop;
        int i13 = paddingRight;
        int i14 = paddingBottom;
        ConstraintAnchor constraintAnchor5 = constraintAnchor;
        int i15 = 0;
        int i16 = 0;
        for (int i17 = 0; i17 < size; i17++) {
            WidgetsList widgetsList8 = this.mChainList.get(i17);
            if (orientation == 0) {
                if (i17 < size - 1) {
                    constraintAnchor4 = this.mChainList.get(i17 + 1).biggest.mTop;
                    i14 = 0;
                } else {
                    constraintAnchor4 = this.mBottom;
                    i14 = getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor6 = widgetsList8.biggest.mBottom;
                widgetsList8.setup(orientation, constraintAnchor5, constraintAnchor2, constraintAnchor3, constraintAnchor4, paddingLeft, i12, i13, i14, max);
                i12 = 0;
                int max2 = Math.max(i15, widgetsList8.getWidth());
                i16 += widgetsList8.getHeight();
                if (i17 > 0) {
                    i16 += this.mVerticalGap;
                }
                i15 = max2;
                constraintAnchor2 = constraintAnchor6;
            } else {
                ConstraintAnchor constraintAnchor7 = constraintAnchor2;
                int i18 = i16;
                int i19 = i15;
                if (i17 < size - 1) {
                    constraintAnchor3 = this.mChainList.get(i17 + 1).biggest.mLeft;
                    i13 = 0;
                } else {
                    constraintAnchor3 = this.mRight;
                    i13 = getPaddingRight();
                }
                ConstraintAnchor constraintAnchor8 = widgetsList8.biggest.mRight;
                widgetsList8.setup(orientation, constraintAnchor5, constraintAnchor7, constraintAnchor3, constraintAnchor4, paddingLeft, i12, i13, i14, max);
                constraintAnchor5 = constraintAnchor8;
                paddingLeft = 0;
                i15 = i19 + widgetsList8.getWidth();
                int max3 = Math.max(i18, widgetsList8.getHeight());
                if (i17 > 0) {
                    i15 += this.mHorizontalGap;
                    i16 = max3;
                    constraintAnchor2 = constraintAnchor7;
                } else {
                    i16 = max3;
                    constraintAnchor2 = constraintAnchor7;
                }
            }
        }
        measured[0] = i15;
        measured[1] = i16;
    }

    private void measureChainWrap_new(ConstraintWidget[] widgets, int count, int orientation, int max, int[] measured) {
        WidgetsList widgetsList;
        int i;
        int i2;
        int i3;
        if (count == 0) {
            return;
        }
        this.mChainList.clear();
        WidgetsList widgetsList2 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
        this.mChainList.add(widgetsList2);
        int i4 = 0;
        if (orientation == 0) {
            int i5 = 0;
            int i6 = 0;
            WidgetsList widgetsList3 = widgetsList2;
            int i7 = 0;
            while (i7 < count) {
                int i8 = i6 + 1;
                ConstraintWidget constraintWidget = widgets[i7];
                int widgetWidth = getWidgetWidth(constraintWidget, max);
                int i9 = constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                boolean z = (i5 == max || (this.mHorizontalGap + i5) + widgetWidth > max) && widgetsList3.biggest != null;
                if ((z || i7 <= 0 || (i3 = this.mMaxElementsWrap) <= 0 || i8 <= i3) ? z : true) {
                    WidgetsList widgetsList4 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
                    widgetsList4.setStartIndex(i7);
                    this.mChainList.add(widgetsList4);
                    widgetsList3 = widgetsList4;
                    i6 = i8;
                    i5 = widgetWidth;
                } else if (i7 > 0) {
                    i5 += this.mHorizontalGap + widgetWidth;
                    i6 = 0;
                } else {
                    i6 = 0;
                    i5 = widgetWidth;
                }
                widgetsList3.add(constraintWidget);
                i7++;
                i4 = i9;
            }
            widgetsList = widgetsList3;
        } else {
            int i10 = 0;
            WidgetsList widgetsList5 = widgetsList2;
            int i11 = 0;
            int i12 = 0;
            while (i12 < count) {
                ConstraintWidget constraintWidget2 = widgets[i12];
                int widgetHeight = getWidgetHeight(constraintWidget2, max);
                int i13 = constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? i4 + 1 : i4;
                boolean z2 = (i10 == max || (this.mVerticalGap + i10) + widgetHeight > max) && widgetsList5.biggest != null;
                if ((z2 || i12 <= 0 || (i = this.mMaxElementsWrap) <= 0 || i11 <= i) ? z2 : true) {
                    WidgetsList widgetsList6 = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
                    widgetsList6.setStartIndex(i12);
                    this.mChainList.add(widgetsList6);
                    widgetsList5 = widgetsList6;
                    i10 = widgetHeight;
                } else if (i12 > 0) {
                    i10 += this.mVerticalGap + widgetHeight;
                    i11 = 0;
                } else {
                    i11 = 0;
                    i10 = widgetHeight;
                }
                widgetsList5.add(constraintWidget2);
                i12++;
                i4 = i13;
            }
            widgetsList = widgetsList5;
        }
        int size = this.mChainList.size();
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = this.mTop;
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = this.mBottom;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        boolean z3 = getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i4 > 0 && z3) {
            int i14 = 0;
            while (i14 < size) {
                boolean z4 = z3;
                WidgetsList widgetsList7 = this.mChainList.get(i14);
                if (orientation == 0) {
                    i2 = i4;
                    widgetsList7.measureMatchConstraints(max - widgetsList7.getWidth());
                } else {
                    i2 = i4;
                    widgetsList7.measureMatchConstraints(max - widgetsList7.getHeight());
                }
                i14++;
                z3 = z4;
                i4 = i2;
            }
        }
        int i15 = paddingTop;
        int i16 = paddingRight;
        int i17 = paddingBottom;
        ConstraintAnchor constraintAnchor5 = constraintAnchor;
        int i18 = 0;
        int i19 = 0;
        for (int i20 = 0; i20 < size; i20++) {
            WidgetsList widgetsList8 = this.mChainList.get(i20);
            if (orientation == 0) {
                if (i20 < size - 1) {
                    constraintAnchor4 = this.mChainList.get(i20 + 1).biggest.mTop;
                    i17 = 0;
                } else {
                    constraintAnchor4 = this.mBottom;
                    i17 = getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor6 = widgetsList8.biggest.mBottom;
                widgetsList8.setup(orientation, constraintAnchor5, constraintAnchor2, constraintAnchor3, constraintAnchor4, paddingLeft, i15, i16, i17, max);
                i15 = 0;
                int max2 = Math.max(i18, widgetsList8.getWidth());
                i19 += widgetsList8.getHeight();
                if (i20 > 0) {
                    i19 += this.mVerticalGap;
                }
                i18 = max2;
                constraintAnchor2 = constraintAnchor6;
            } else {
                ConstraintAnchor constraintAnchor7 = constraintAnchor2;
                int i21 = i19;
                int i22 = i18;
                if (i20 < size - 1) {
                    constraintAnchor3 = this.mChainList.get(i20 + 1).biggest.mLeft;
                    i16 = 0;
                } else {
                    constraintAnchor3 = this.mRight;
                    i16 = getPaddingRight();
                }
                ConstraintAnchor constraintAnchor8 = widgetsList8.biggest.mRight;
                widgetsList8.setup(orientation, constraintAnchor5, constraintAnchor7, constraintAnchor3, constraintAnchor4, paddingLeft, i15, i16, i17, max);
                constraintAnchor5 = constraintAnchor8;
                paddingLeft = 0;
                i18 = i22 + widgetsList8.getWidth();
                int max3 = Math.max(i21, widgetsList8.getHeight());
                if (i20 > 0) {
                    i18 += this.mHorizontalGap;
                    i19 = max3;
                    constraintAnchor2 = constraintAnchor7;
                } else {
                    i19 = max3;
                    constraintAnchor2 = constraintAnchor7;
                }
            }
        }
        measured[0] = i18;
        measured[1] = i19;
    }

    private void measureNoWrap(ConstraintWidget[] widgets, int count, int orientation, int max, int[] measured) {
        WidgetsList widgetsList;
        if (count == 0) {
            return;
        }
        if (this.mChainList.size() == 0) {
            widgetsList = new WidgetsList(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, max);
            this.mChainList.add(widgetsList);
        } else {
            widgetsList = this.mChainList.get(0);
            widgetsList.clear();
            widgetsList.setup(orientation, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), max);
        }
        for (int i = 0; i < count; i++) {
            widgetsList.add(widgets[i]);
        }
        measured[0] = widgetsList.getWidth();
        measured[1] = widgetsList.getHeight();
    }

    @Override // androidx.constraintlayout.core.widgets.ConstraintWidget
    public void addToSolver(LinearSystem system, boolean optimize) {
        super.addToSolver(system, optimize);
        boolean z = getParent() != null && ((ConstraintWidgetContainer) getParent()).isRtl();
        switch (this.mWrapMode) {
            case 0:
                if (this.mChainList.size() > 0) {
                    this.mChainList.get(0).createConstraints(z, 0, true);
                    break;
                }
                break;
            case 1:
                int size = this.mChainList.size();
                int i = 0;
                while (i < size) {
                    this.mChainList.get(i).createConstraints(z, i, i == size + (-1));
                    i++;
                }
                break;
            case 2:
                createAlignedConstraints(z);
                break;
            case 3:
                int size2 = this.mChainList.size();
                int i2 = 0;
                while (i2 < size2) {
                    this.mChainList.get(i2).createConstraints(z, i2, i2 == size2 + (-1));
                    i2++;
                }
                break;
        }
        needsCallbackFromSolver(false);
    }

    @Override // androidx.constraintlayout.core.widgets.HelperWidget, androidx.constraintlayout.core.widgets.ConstraintWidget
    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(src, hashMap);
        Flow flow = (Flow) src;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    public float getMaxElementsWrap() {
        return this.mMaxElementsWrap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r20v0 */
    /* JADX WARN: Type inference failed for: r20v1 */
    /* JADX WARN: Type inference failed for: r20v2 */
    /* JADX WARN: Type inference failed for: r20v3 */
    /* JADX WARN: Type inference failed for: r20v4 */
    /* JADX WARN: Type inference failed for: r20v5 */
    @Override // androidx.constraintlayout.core.widgets.VirtualLayout
    public void measure(int widthMode, int widthSize, int heightMode, int heightSize) {
        ConstraintWidget[] constraintWidgetArr;
        int i;
        int[] iArr;
        ?? r20;
        if (this.mWidgetsCount > 0 && !measureChildren()) {
            setMeasure(0, 0);
            needsCallbackFromSolver(false);
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int[] iArr2 = new int[2];
        int i2 = (widthSize - paddingLeft) - paddingRight;
        int i3 = this.mOrientation;
        int i4 = i3 == 1 ? (heightSize - paddingTop) - paddingBottom : i2;
        if (i3 == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr2 = this.mWidgets;
        int i5 = 0;
        for (int i6 = 0; i6 < this.mWidgetsCount; i6++) {
            if (this.mWidgets[i6].getVisibility() == 8) {
                i5++;
            }
        }
        int i7 = this.mWidgetsCount;
        if (i5 > 0) {
            ConstraintWidget[] constraintWidgetArr3 = new ConstraintWidget[this.mWidgetsCount - i5];
            int i8 = 0;
            int i9 = 0;
            while (i9 < this.mWidgetsCount) {
                ConstraintWidget constraintWidget = this.mWidgets[i9];
                int i10 = i7;
                if (constraintWidget.getVisibility() != 8) {
                    constraintWidgetArr3[i8] = constraintWidget;
                    i8++;
                }
                i9++;
                i7 = i10;
            }
            constraintWidgetArr = constraintWidgetArr3;
            i = i8;
        } else {
            constraintWidgetArr = constraintWidgetArr2;
            i = i7;
        }
        this.mDisplayedWidgets = constraintWidgetArr;
        this.mDisplayedWidgetsCount = i;
        switch (this.mWrapMode) {
            case 0:
                iArr = iArr2;
                r20 = 1;
                measureNoWrap(constraintWidgetArr, i, this.mOrientation, i4, iArr2);
                break;
            case 1:
                iArr = iArr2;
                r20 = 1;
                measureChainWrap(constraintWidgetArr, i, this.mOrientation, i4, iArr2);
                break;
            case 2:
                iArr = iArr2;
                r20 = 1;
                measureAligned(constraintWidgetArr, i, this.mOrientation, i4, iArr2);
                break;
            case 3:
                r20 = 1;
                iArr = iArr2;
                measureChainWrap_new(constraintWidgetArr, i, this.mOrientation, i4, iArr2);
                break;
            default:
                iArr = iArr2;
                r20 = 1;
                break;
        }
        int i11 = iArr[0] + paddingLeft + paddingRight;
        int i12 = iArr[r20] + paddingTop + paddingBottom;
        int i13 = 0;
        int i14 = 0;
        if (widthMode == 1073741824) {
            i13 = widthSize;
        } else if (widthMode == Integer.MIN_VALUE) {
            i13 = Math.min(i11, widthSize);
        } else if (widthMode == 0) {
            i13 = i11;
        }
        if (heightMode == 1073741824) {
            i14 = heightSize;
        } else if (heightMode == Integer.MIN_VALUE) {
            i14 = Math.min(i12, heightSize);
        } else if (heightMode == 0) {
            i14 = i12;
        }
        setMeasure(i13, i14);
        setWidth(i13);
        setHeight(i14);
        needsCallbackFromSolver(this.mWidgetsCount > 0 ? r20 : false);
    }

    public void setFirstHorizontalBias(float value) {
        this.mFirstHorizontalBias = value;
    }

    public void setFirstHorizontalStyle(int value) {
        this.mFirstHorizontalStyle = value;
    }

    public void setFirstVerticalBias(float value) {
        this.mFirstVerticalBias = value;
    }

    public void setFirstVerticalStyle(int value) {
        this.mFirstVerticalStyle = value;
    }

    public void setHorizontalAlign(int value) {
        this.mHorizontalAlign = value;
    }

    public void setHorizontalBias(float value) {
        this.mHorizontalBias = value;
    }

    public void setHorizontalGap(int value) {
        this.mHorizontalGap = value;
    }

    public void setHorizontalStyle(int value) {
        this.mHorizontalStyle = value;
    }

    public void setLastHorizontalBias(float value) {
        this.mLastHorizontalBias = value;
    }

    public void setLastHorizontalStyle(int value) {
        this.mLastHorizontalStyle = value;
    }

    public void setLastVerticalBias(float value) {
        this.mLastVerticalBias = value;
    }

    public void setLastVerticalStyle(int value) {
        this.mLastVerticalStyle = value;
    }

    public void setMaxElementsWrap(int value) {
        this.mMaxElementsWrap = value;
    }

    public void setOrientation(int value) {
        this.mOrientation = value;
    }

    public void setVerticalAlign(int value) {
        this.mVerticalAlign = value;
    }

    public void setVerticalBias(float value) {
        this.mVerticalBias = value;
    }

    public void setVerticalGap(int value) {
        this.mVerticalGap = value;
    }

    public void setVerticalStyle(int value) {
        this.mVerticalStyle = value;
    }

    public void setWrapMode(int value) {
        this.mWrapMode = value;
    }
}
