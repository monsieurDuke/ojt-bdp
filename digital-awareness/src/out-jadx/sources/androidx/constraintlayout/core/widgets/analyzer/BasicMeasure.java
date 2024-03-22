package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer constraintWidgetContainer;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();
    private Measure mMeasure = new Measure();

    /* loaded from: classes.dex */
    public static class Measure {
        public static int SELF_DIMENSIONS = 0;
        public static int TRY_GIVEN_DIMENSIONS = 1;
        public static int USE_GIVEN_DIMENSIONS = 2;
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measureStrategy;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    /* loaded from: classes.dex */
    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.constraintWidgetContainer = constraintWidgetContainer;
    }

    private boolean measure(Measurer measurer, ConstraintWidget widget, int measureStrategy) {
        this.mMeasure.horizontalBehavior = widget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = widget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = widget.getWidth();
        this.mMeasure.verticalDimension = widget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.measureStrategy = measureStrategy;
        boolean z = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z2 = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z3 = z && widget.mDimensionRatio > 0.0f;
        boolean z4 = z2 && widget.mDimensionRatio > 0.0f;
        if (z3 && widget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (z4 && widget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(widget, this.mMeasure);
        widget.setWidth(this.mMeasure.measuredWidth);
        widget.setHeight(this.mMeasure.measuredHeight);
        widget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        widget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.measureStrategy = Measure.SELF_DIMENSIONS;
        return this.mMeasure.measuredNeedsSolverPass;
    }

    private void measureChildren(ConstraintWidgetContainer layout) {
        int size = layout.mChildren.size();
        boolean optimizeFor = layout.optimizeFor(64);
        Measurer measurer = layout.getMeasurer();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = layout.mChildren.get(i);
            if (!(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Barrier) && !constraintWidget.isInVirtualLayout() && (!optimizeFor || constraintWidget.horizontalRun == null || constraintWidget.verticalRun == null || !constraintWidget.horizontalRun.dimension.resolved || !constraintWidget.verticalRun.dimension.resolved)) {
                boolean z = false;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.getDimensionBehaviour(0);
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.getDimensionBehaviour(1);
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth != 1 && dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight != 1) {
                    z = true;
                }
                if (!z && layout.optimizeFor(1) && !(constraintWidget instanceof VirtualLayout)) {
                    if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0 && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z = true;
                    }
                    if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0 && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !constraintWidget.isInHorizontalChain()) {
                        z = true;
                    }
                    if ((dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.mDimensionRatio > 0.0f) {
                        z = true;
                    }
                }
                if (!z) {
                    measure(measurer, constraintWidget, Measure.SELF_DIMENSIONS);
                    if (layout.mMetrics != null) {
                        layout.mMetrics.measuredWidgets++;
                    }
                }
            }
        }
        measurer.didMeasures();
    }

    private void solveLinearSystem(ConstraintWidgetContainer layout, String reason, int pass, int w, int h) {
        int minWidth = layout.getMinWidth();
        int minHeight = layout.getMinHeight();
        layout.setMinWidth(0);
        layout.setMinHeight(0);
        layout.setWidth(w);
        layout.setHeight(h);
        layout.setMinWidth(minWidth);
        layout.setMinHeight(minHeight);
        this.constraintWidgetContainer.setPass(pass);
        this.constraintWidgetContainer.layout();
    }

    public long solverMeasure(ConstraintWidgetContainer layout, int optimizationLevel, int paddingX, int paddingY, int widthMode, int widthSize, int heightMode, int heightSize, int lastMeasureWidth, int lastMeasureHeight) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        int i2;
        int i3;
        boolean z5;
        boolean z6;
        long j;
        int widthSize2;
        Measurer measurer;
        boolean z7;
        int i4;
        long j2;
        boolean directMeasureSetup;
        int widthSize3;
        boolean z8;
        Measurer measurer2 = layout.getMeasurer();
        long j3 = 0;
        int size = layout.mChildren.size();
        int width = layout.getWidth();
        int height = layout.getHeight();
        boolean enabled = Optimizer.enabled(optimizationLevel, 128);
        boolean z9 = enabled || Optimizer.enabled(optimizationLevel, 64);
        if (z9) {
            int i5 = 0;
            while (i5 < size) {
                ConstraintWidget constraintWidget = layout.mChildren.get(i5);
                boolean z10 = z9;
                boolean z11 = (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.getDimensionRatio() > 0.0f;
                if (!constraintWidget.isInHorizontalChain() || !z11) {
                    if (constraintWidget.isInVerticalChain() && z11) {
                        z2 = false;
                        break;
                    }
                    if (constraintWidget instanceof VirtualLayout) {
                        z2 = false;
                        break;
                    }
                    if (constraintWidget.isInHorizontalChain() || constraintWidget.isInVerticalChain()) {
                        z2 = false;
                        break;
                    }
                    i5++;
                    z9 = z10;
                } else {
                    z2 = false;
                    break;
                }
            }
            z = z9;
        } else {
            z = z9;
        }
        z2 = z;
        if (z2 && LinearSystem.sMetrics != null) {
            LinearSystem.sMetrics.measures++;
        }
        boolean z12 = z2 & ((widthMode == 1073741824 && heightMode == 1073741824) || enabled);
        int i6 = 0;
        if (z12) {
            int min = Math.min(layout.getMaxWidth(), widthSize);
            int min2 = Math.min(layout.getMaxHeight(), heightSize);
            if (widthMode == 1073741824 && layout.getWidth() != min) {
                layout.setWidth(min);
                layout.invalidateGraph();
            }
            if (heightMode == 1073741824 && layout.getHeight() != min2) {
                layout.setHeight(min2);
                layout.invalidateGraph();
            }
            if (widthMode == 1073741824 && heightMode == 1073741824) {
                directMeasureSetup = layout.directMeasure(enabled);
                i6 = 2;
                widthSize3 = min;
                z8 = true;
            } else {
                directMeasureSetup = layout.directMeasureSetup(enabled);
                if (widthMode == 1073741824) {
                    widthSize3 = min;
                    directMeasureSetup &= layout.directMeasureWithOrientation(enabled, 0);
                    i6 = 0 + 1;
                } else {
                    widthSize3 = min;
                }
                if (heightMode == 1073741824) {
                    z8 = true;
                    directMeasureSetup &= layout.directMeasureWithOrientation(enabled, 1);
                    i6++;
                } else {
                    z8 = true;
                }
            }
            if (directMeasureSetup) {
                if (widthMode != 1073741824) {
                    z8 = false;
                }
                layout.updateFromRuns(z8, heightMode == 1073741824);
            }
            i = i6;
            z4 = directMeasureSetup;
            z3 = true;
        } else {
            z3 = true;
            z4 = false;
            i = 0;
        }
        if (z4 && i == 2) {
            return 0L;
        }
        int optimizationLevel2 = layout.getOptimizationLevel();
        if (size > 0) {
            measureChildren(layout);
        }
        updateHierarchy(layout);
        int size2 = this.mVariableDimensionsWidgets.size();
        if (size > 0) {
            i2 = size2;
            i3 = optimizationLevel2;
            z5 = z3;
            z6 = false;
            solveLinearSystem(layout, "First pass", 0, width, height);
        } else {
            i2 = size2;
            i3 = optimizationLevel2;
            z5 = z3;
            z6 = false;
        }
        int i7 = i2;
        if (i7 > 0) {
            boolean z13 = false;
            boolean z14 = layout.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? z5 : z6;
            boolean z15 = layout.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? z5 : z6;
            int max = Math.max(layout.getWidth(), this.constraintWidgetContainer.getMinWidth());
            int max2 = Math.max(layout.getHeight(), this.constraintWidgetContainer.getMinHeight());
            int i8 = 0;
            while (i8 < i7) {
                ConstraintWidget constraintWidget2 = this.mVariableDimensionsWidgets.get(i8);
                if (constraintWidget2 instanceof VirtualLayout) {
                    int width2 = constraintWidget2.getWidth();
                    int height2 = constraintWidget2.getHeight();
                    i4 = size;
                    boolean measure = z13 | measure(measurer2, constraintWidget2, Measure.TRY_GIVEN_DIMENSIONS);
                    if (layout.mMetrics != null) {
                        j2 = j3;
                        layout.mMetrics.measuredMatchWidgets++;
                    } else {
                        j2 = j3;
                    }
                    int width3 = constraintWidget2.getWidth();
                    int height3 = constraintWidget2.getHeight();
                    if (width3 != width2) {
                        constraintWidget2.setWidth(width3);
                        if (z14 && constraintWidget2.getRight() > max) {
                            max = Math.max(max, constraintWidget2.getRight() + constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                        }
                        measure = true;
                    }
                    if (height3 != height2) {
                        constraintWidget2.setHeight(height3);
                        if (z15 && constraintWidget2.getBottom() > max2) {
                            max2 = Math.max(max2, constraintWidget2.getBottom() + constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                        }
                        measure = true;
                    }
                    z13 = measure | ((VirtualLayout) constraintWidget2).needSolverPass();
                } else {
                    j2 = j3;
                    i4 = size;
                }
                i8++;
                size = i4;
                j3 = j2;
            }
            j = j3;
            int i9 = 0;
            while (i9 < 2) {
                int i10 = 0;
                boolean z16 = z13;
                int i11 = max;
                int i12 = max2;
                while (i10 < i7) {
                    ConstraintWidget constraintWidget3 = this.mVariableDimensionsWidgets.get(i10);
                    if (((constraintWidget3 instanceof Helper) && !(constraintWidget3 instanceof VirtualLayout)) || (constraintWidget3 instanceof Guideline) || constraintWidget3.getVisibility() == 8 || ((z12 && constraintWidget3.horizontalRun.dimension.resolved && constraintWidget3.verticalRun.dimension.resolved) || (constraintWidget3 instanceof VirtualLayout))) {
                        widthSize2 = i7;
                        measurer = measurer2;
                    } else {
                        int width4 = constraintWidget3.getWidth();
                        int height4 = constraintWidget3.getHeight();
                        int baselineDistance = constraintWidget3.getBaselineDistance();
                        widthSize2 = i7;
                        boolean measure2 = z16 | measure(measurer2, constraintWidget3, i9 == 2 + (-1) ? Measure.USE_GIVEN_DIMENSIONS : Measure.TRY_GIVEN_DIMENSIONS);
                        if (layout.mMetrics != null) {
                            measurer = measurer2;
                            z7 = measure2;
                            layout.mMetrics.measuredMatchWidgets++;
                        } else {
                            measurer = measurer2;
                            z7 = measure2;
                        }
                        int width5 = constraintWidget3.getWidth();
                        int height5 = constraintWidget3.getHeight();
                        if (width5 != width4) {
                            constraintWidget3.setWidth(width5);
                            if (z14 && constraintWidget3.getRight() > i11) {
                                i11 = Math.max(i11, constraintWidget3.getRight() + constraintWidget3.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                            }
                            z16 = true;
                        } else {
                            z16 = z7;
                        }
                        if (height5 != height4) {
                            constraintWidget3.setHeight(height5);
                            if (z15 && constraintWidget3.getBottom() > i12) {
                                i12 = Math.max(i12, constraintWidget3.getBottom() + constraintWidget3.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                            z16 = true;
                        }
                        if (constraintWidget3.hasBaseline() && baselineDistance != constraintWidget3.getBaselineDistance()) {
                            z16 = true;
                        }
                    }
                    i10++;
                    i7 = widthSize2;
                    measurer2 = measurer;
                }
                int widthSize4 = i7;
                Measurer measurer3 = measurer2;
                if (!z16) {
                    break;
                }
                solveLinearSystem(layout, "intermediate pass", i9 + 1, width, height);
                z13 = false;
                i9++;
                i7 = widthSize4;
                max = i11;
                max2 = i12;
                measurer2 = measurer3;
            }
        } else {
            j = 0;
        }
        layout.setOptimizationLevel(i3);
        return j;
    }

    public void updateHierarchy(ConstraintWidgetContainer layout) {
        this.mVariableDimensionsWidgets.clear();
        int size = layout.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = layout.mChildren.get(i);
            if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.mVariableDimensionsWidgets.add(constraintWidget);
            }
        }
        layout.invalidateGraph();
    }
}
