package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ChainHead;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Direct {
    private static final boolean APPLY_MATCH_PARENT = false;
    private static final boolean DEBUG = false;
    private static final boolean EARLY_TERMINATION = true;
    private static BasicMeasure.Measure measure = new BasicMeasure.Measure();
    private static int hcount = 0;
    private static int vcount = 0;

    private static boolean canMeasure(int level, ConstraintWidget layout) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = layout.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer constraintWidgetContainer = layout.getParent() != null ? (ConstraintWidgetContainer) layout.getParent() : null;
        if (constraintWidgetContainer == null || constraintWidgetContainer.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        if (constraintWidgetContainer == null || constraintWidgetContainer.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        boolean z = horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedHorizontally() || horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(0)) || (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 1 && layout.hasResolvedTargets(0, layout.getWidth()));
        boolean z2 = verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedVertically() || verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(1)) || (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 1 && layout.hasResolvedTargets(1, layout.getHeight()));
        if (layout.mDimensionRatio > 0.0f && (z || z2)) {
            return EARLY_TERMINATION;
        }
        if (z && z2) {
            return EARLY_TERMINATION;
        }
        return false;
    }

    private static void horizontalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, boolean isRtl) {
        if (layout.isHorizontalSolvingPassDone()) {
            return;
        }
        hcount++;
        if (!(layout instanceof ConstraintWidgetContainer) && layout.isMeasureRequested() && canMeasure(level + 1, layout)) {
            ConstraintWidgetContainer.measure(level + 1, layout, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        ConstraintAnchor anchor = layout.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = layout.getAnchor(ConstraintAnchor.Type.RIGHT);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget = next.mOwner;
                boolean canMeasure = canMeasure(level + 1, constraintWidget);
                if (constraintWidget.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                boolean z = ((next == constraintWidget.mLeft && constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.hasFinalValue()) || (next == constraintWidget.mRight && constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.hasFinalValue())) ? EARLY_TERMINATION : false;
                if (constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                    if (!constraintWidget.isMeasureRequested()) {
                        if (next == constraintWidget.mLeft && constraintWidget.mRight.mTarget == null) {
                            int margin = constraintWidget.mLeft.getMargin() + finalValue;
                            constraintWidget.setFinalHorizontal(margin, constraintWidget.getWidth() + margin);
                            horizontalSolvingPass(level + 1, constraintWidget, measurer, isRtl);
                        } else if (next == constraintWidget.mRight && constraintWidget.mLeft.mTarget == null) {
                            int margin2 = finalValue - constraintWidget.mRight.getMargin();
                            constraintWidget.setFinalHorizontal(margin2 - constraintWidget.getWidth(), margin2);
                            horizontalSolvingPass(level + 1, constraintWidget, measurer, isRtl);
                        } else if (z && !constraintWidget.isInHorizontalChain()) {
                            solveHorizontalCenterConstraints(level + 1, measurer, constraintWidget, isRtl);
                        }
                    }
                } else if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxWidth >= 0 && constraintWidget.mMatchConstraintMinWidth >= 0 && ((constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.getDimensionRatio() == 0.0f)) && !constraintWidget.isInHorizontalChain() && !constraintWidget.isInVirtualLayout() && z && !constraintWidget.isInHorizontalChain())) {
                    solveHorizontalMatchConstraint(level + 1, layout, measurer, constraintWidget, isRtl);
                }
            }
        }
        if (layout instanceof Guideline) {
            return;
        }
        if (anchor2.getDependents() != null && anchor2.hasFinalValue()) {
            Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
            while (it2.hasNext()) {
                ConstraintAnchor next2 = it2.next();
                ConstraintWidget constraintWidget2 = next2.mOwner;
                boolean canMeasure2 = canMeasure(level + 1, constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure2) {
                    ConstraintWidgetContainer.measure(level + 1, constraintWidget2, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                boolean z2 = ((next2 == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget != null && constraintWidget2.mRight.mTarget.hasFinalValue()) || (next2 == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget != null && constraintWidget2.mLeft.mTarget.hasFinalValue())) ? EARLY_TERMINATION : false;
                if (constraintWidget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !canMeasure2) {
                    if (constraintWidget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.mMatchConstraintMaxWidth >= 0 && constraintWidget2.mMatchConstraintMinWidth >= 0) {
                        if (constraintWidget2.getVisibility() != 8) {
                            if (constraintWidget2.mMatchConstraintDefaultWidth == 0 && constraintWidget2.getDimensionRatio() == 0.0f) {
                            }
                        }
                        if (!constraintWidget2.isInHorizontalChain() && !constraintWidget2.isInVirtualLayout() && z2 && !constraintWidget2.isInHorizontalChain()) {
                            solveHorizontalMatchConstraint(level + 1, layout, measurer, constraintWidget2, isRtl);
                        }
                    }
                }
                if (!constraintWidget2.isMeasureRequested()) {
                    if (next2 == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget == null) {
                        int margin3 = constraintWidget2.mLeft.getMargin() + finalValue2;
                        constraintWidget2.setFinalHorizontal(margin3, constraintWidget2.getWidth() + margin3);
                        horizontalSolvingPass(level + 1, constraintWidget2, measurer, isRtl);
                    } else if (next2 == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget == null) {
                        int margin4 = finalValue2 - constraintWidget2.mRight.getMargin();
                        constraintWidget2.setFinalHorizontal(margin4 - constraintWidget2.getWidth(), margin4);
                        horizontalSolvingPass(level + 1, constraintWidget2, measurer, isRtl);
                    } else if (z2 && !constraintWidget2.isInHorizontalChain()) {
                        solveHorizontalCenterConstraints(level + 1, measurer, constraintWidget2, isRtl);
                    }
                }
            }
        }
        layout.markHorizontalSolvingPassDone();
    }

    public static String ls(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append("+-(" + level + ") ");
        return sb.toString();
    }

    private static void solveBarrier(int level, Barrier barrier, BasicMeasure.Measurer measurer, int orientation, boolean isRtl) {
        if (barrier.allSolved()) {
            if (orientation == 0) {
                horizontalSolvingPass(level + 1, barrier, measurer, isRtl);
            } else {
                verticalSolvingPass(level + 1, barrier, measurer);
            }
        }
    }

    public static boolean solveChain(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead, boolean isChainSpread, boolean isChainSpreadInside, boolean isChainPacked) {
        int finalValue;
        int finalValue2;
        int finalValue3;
        ConstraintWidget constraintWidget;
        int i;
        int height;
        ConstraintWidget constraintWidget2;
        boolean z;
        boolean z2;
        ConstraintAnchor constraintAnchor;
        BasicMeasure.Measure measure2;
        ConstraintWidget constraintWidget3;
        if (isChainPacked) {
            return false;
        }
        if (orientation == 0) {
            if (!container.isResolvedHorizontally()) {
                return false;
            }
        } else if (!container.isResolvedVertically()) {
            return false;
        }
        boolean isRtl = container.isRtl();
        ConstraintWidget first = chainHead.getFirst();
        ConstraintWidget last = chainHead.getLast();
        ConstraintWidget firstVisibleWidget = chainHead.getFirstVisibleWidget();
        ConstraintWidget lastVisibleWidget = chainHead.getLastVisibleWidget();
        ConstraintWidget head = chainHead.getHead();
        ConstraintWidget constraintWidget4 = first;
        boolean z3 = false;
        ConstraintAnchor constraintAnchor2 = first.mListAnchors[offset];
        ConstraintAnchor constraintAnchor3 = last.mListAnchors[offset + 1];
        if (constraintAnchor2.mTarget == null || constraintAnchor3.mTarget == null || !constraintAnchor2.mTarget.hasFinalValue() || !constraintAnchor3.mTarget.hasFinalValue() || firstVisibleWidget == null || lastVisibleWidget == null || (finalValue3 = (finalValue2 = constraintAnchor3.mTarget.getFinalValue() - lastVisibleWidget.mListAnchors[offset + 1].getMargin()) - (finalValue = constraintAnchor2.mTarget.getFinalValue() + firstVisibleWidget.mListAnchors[offset].getMargin())) <= 0) {
            return false;
        }
        int i2 = 0;
        BasicMeasure.Measure measure3 = new BasicMeasure.Measure();
        int i3 = 0;
        int i4 = 0;
        while (!z3) {
            if (!canMeasure(0 + 1, constraintWidget4)) {
                return false;
            }
            ConstraintWidget constraintWidget5 = last;
            if (constraintWidget4.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                return false;
            }
            if (constraintWidget4.isMeasureRequested()) {
                z2 = z3;
                constraintAnchor = constraintAnchor2;
                measure2 = measure3;
                ConstraintWidgetContainer.measure(0 + 1, constraintWidget4, container.getMeasurer(), measure2, BasicMeasure.Measure.SELF_DIMENSIONS);
            } else {
                z2 = z3;
                constraintAnchor = constraintAnchor2;
                measure2 = measure3;
            }
            int margin = i2 + constraintWidget4.mListAnchors[offset].getMargin();
            i2 = (orientation == 0 ? margin + constraintWidget4.getWidth() : margin + constraintWidget4.getHeight()) + constraintWidget4.mListAnchors[offset + 1].getMargin();
            i3++;
            if (constraintWidget4.getVisibility() != 8) {
                i4++;
            }
            ConstraintAnchor constraintAnchor4 = constraintWidget4.mListAnchors[offset + 1].mTarget;
            if (constraintAnchor4 != null) {
                constraintWidget3 = constraintAnchor4.mOwner;
                if (constraintWidget3.mListAnchors[offset].mTarget == null || constraintWidget3.mListAnchors[offset].mTarget.mOwner != constraintWidget4) {
                    constraintWidget3 = null;
                }
            } else {
                constraintWidget3 = null;
            }
            if (constraintWidget3 != null) {
                constraintWidget4 = constraintWidget3;
                z3 = z2;
            } else {
                z3 = EARLY_TERMINATION;
            }
            measure3 = measure2;
            last = constraintWidget5;
            constraintAnchor2 = constraintAnchor;
        }
        int i5 = i3;
        int i6 = i4;
        if (i6 == 0 || i6 != i5 || finalValue3 < i2) {
            return false;
        }
        int i7 = finalValue3 - i2;
        if (isChainSpread) {
            i7 /= i6 + 1;
            constraintWidget = constraintWidget4;
            i = 1;
        } else if (!isChainSpreadInside) {
            constraintWidget = constraintWidget4;
            i = 1;
        } else if (i6 > 2) {
            constraintWidget = constraintWidget4;
            i = 1;
            i7 = (i7 / i6) - 1;
        } else {
            constraintWidget = constraintWidget4;
            i = 1;
        }
        if (i6 == i) {
            int horizontalBiasPercent = (int) (finalValue + 0.5f + (i7 * (orientation == 0 ? head.getHorizontalBiasPercent() : head.getVerticalBiasPercent())));
            if (orientation == 0) {
                firstVisibleWidget.setFinalHorizontal(horizontalBiasPercent, firstVisibleWidget.getWidth() + horizontalBiasPercent);
            } else {
                firstVisibleWidget.setFinalVertical(horizontalBiasPercent, firstVisibleWidget.getHeight() + horizontalBiasPercent);
            }
            horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
            return EARLY_TERMINATION;
        }
        if (!isChainSpread) {
            if (!isChainSpreadInside) {
                return EARLY_TERMINATION;
            }
            if (i6 != 2) {
                return false;
            }
            if (orientation == 0) {
                firstVisibleWidget.setFinalHorizontal(finalValue, firstVisibleWidget.getWidth() + finalValue);
                lastVisibleWidget.setFinalHorizontal(finalValue2 - lastVisibleWidget.getWidth(), finalValue2);
                horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                horizontalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer(), isRtl);
                return EARLY_TERMINATION;
            }
            firstVisibleWidget.setFinalVertical(finalValue, firstVisibleWidget.getHeight() + finalValue);
            lastVisibleWidget.setFinalVertical(finalValue2 - lastVisibleWidget.getHeight(), finalValue2);
            verticalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer());
            verticalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer());
            return EARLY_TERMINATION;
        }
        boolean z4 = false;
        int i8 = finalValue + i7;
        ConstraintWidget constraintWidget6 = first;
        while (!z4) {
            boolean z5 = z4;
            ConstraintWidget constraintWidget7 = first;
            if (constraintWidget6.getVisibility() != 8) {
                int margin2 = i8 + constraintWidget6.mListAnchors[offset].getMargin();
                if (orientation == 0) {
                    constraintWidget6.setFinalHorizontal(margin2, constraintWidget6.getWidth() + margin2);
                    horizontalSolvingPass(0 + 1, constraintWidget6, container.getMeasurer(), isRtl);
                    height = margin2 + constraintWidget6.getWidth();
                } else {
                    constraintWidget6.setFinalVertical(margin2, constraintWidget6.getHeight() + margin2);
                    verticalSolvingPass(0 + 1, constraintWidget6, container.getMeasurer());
                    height = margin2 + constraintWidget6.getHeight();
                }
                i8 = height + constraintWidget6.mListAnchors[offset + 1].getMargin() + i7;
            } else if (orientation == 0) {
                constraintWidget6.setFinalHorizontal(i8, i8);
                horizontalSolvingPass(0 + 1, constraintWidget6, container.getMeasurer(), isRtl);
            } else {
                constraintWidget6.setFinalVertical(i8, i8);
                verticalSolvingPass(0 + 1, constraintWidget6, container.getMeasurer());
            }
            constraintWidget6.addToSolver(system, false);
            ConstraintAnchor constraintAnchor5 = constraintWidget6.mListAnchors[offset + 1].mTarget;
            if (constraintAnchor5 != null) {
                constraintWidget2 = constraintAnchor5.mOwner;
                if (constraintWidget2.mListAnchors[offset].mTarget == null || constraintWidget2.mListAnchors[offset].mTarget.mOwner != constraintWidget6) {
                    constraintWidget2 = null;
                }
            } else {
                constraintWidget2 = null;
            }
            if (constraintWidget2 != null) {
                constraintWidget6 = constraintWidget2;
                z = z5;
            } else {
                z = EARLY_TERMINATION;
            }
            z4 = z;
            first = constraintWidget7;
        }
        return EARLY_TERMINATION;
    }

    private static void solveHorizontalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        float horizontalBiasPercent = widget.getHorizontalBiasPercent();
        int finalValue = widget.mLeft.mTarget.getFinalValue();
        int finalValue2 = widget.mRight.mTarget.getFinalValue();
        int margin = widget.mLeft.getMargin() + finalValue;
        int margin2 = finalValue2 - widget.mRight.getMargin();
        if (finalValue == finalValue2) {
            horizontalBiasPercent = 0.5f;
            margin = finalValue;
            margin2 = finalValue2;
        }
        int width = widget.getWidth();
        int i = (margin2 - margin) - width;
        if (margin > margin2) {
            i = (margin - margin2) - width;
        }
        int i2 = i > 0 ? (int) ((i * horizontalBiasPercent) + 0.5f) : (int) (i * horizontalBiasPercent);
        int i3 = margin + i2;
        int i4 = i3 + width;
        if (margin > margin2) {
            i3 = margin + i2;
            i4 = i3 - width;
        }
        widget.setFinalHorizontal(i3, i4);
        horizontalSolvingPass(level + 1, widget, measurer, isRtl);
    }

    private static void solveHorizontalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        float horizontalBiasPercent = widget.getHorizontalBiasPercent();
        int finalValue = widget.mLeft.mTarget.getFinalValue() + widget.mLeft.getMargin();
        int finalValue2 = widget.mRight.mTarget.getFinalValue() - widget.mRight.getMargin();
        if (finalValue2 >= finalValue) {
            int width = widget.getWidth();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultWidth == 2) {
                    width = (int) (widget.getHorizontalBiasPercent() * 0.5f * (layout instanceof ConstraintWidgetContainer ? layout.getWidth() : layout.getParent().getWidth()));
                } else if (widget.mMatchConstraintDefaultWidth == 0) {
                    width = finalValue2 - finalValue;
                }
                width = Math.max(widget.mMatchConstraintMinWidth, width);
                if (widget.mMatchConstraintMaxWidth > 0) {
                    width = Math.min(widget.mMatchConstraintMaxWidth, width);
                }
            }
            int i = finalValue + ((int) ((((finalValue2 - finalValue) - width) * horizontalBiasPercent) + 0.5f));
            widget.setFinalHorizontal(i, i + width);
            horizontalSolvingPass(level + 1, widget, measurer, isRtl);
        }
    }

    private static void solveVerticalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        float verticalBiasPercent = widget.getVerticalBiasPercent();
        int finalValue = widget.mTop.mTarget.getFinalValue();
        int finalValue2 = widget.mBottom.mTarget.getFinalValue();
        int margin = widget.mTop.getMargin() + finalValue;
        int margin2 = finalValue2 - widget.mBottom.getMargin();
        if (finalValue == finalValue2) {
            verticalBiasPercent = 0.5f;
            margin = finalValue;
            margin2 = finalValue2;
        }
        int height = widget.getHeight();
        int i = (margin2 - margin) - height;
        if (margin > margin2) {
            i = (margin - margin2) - height;
        }
        int i2 = i > 0 ? (int) ((i * verticalBiasPercent) + 0.5f) : (int) (i * verticalBiasPercent);
        int i3 = margin + i2;
        int i4 = i3 + height;
        if (margin > margin2) {
            i3 = margin - i2;
            i4 = i3 - height;
        }
        widget.setFinalVertical(i3, i4);
        verticalSolvingPass(level + 1, widget, measurer);
    }

    private static void solveVerticalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        float verticalBiasPercent = widget.getVerticalBiasPercent();
        int finalValue = widget.mTop.mTarget.getFinalValue() + widget.mTop.getMargin();
        int finalValue2 = widget.mBottom.mTarget.getFinalValue() - widget.mBottom.getMargin();
        if (finalValue2 >= finalValue) {
            int height = widget.getHeight();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultHeight == 2) {
                    height = (int) (verticalBiasPercent * 0.5f * (layout instanceof ConstraintWidgetContainer ? layout.getHeight() : layout.getParent().getHeight()));
                } else if (widget.mMatchConstraintDefaultHeight == 0) {
                    height = finalValue2 - finalValue;
                }
                height = Math.max(widget.mMatchConstraintMinHeight, height);
                if (widget.mMatchConstraintMaxHeight > 0) {
                    height = Math.min(widget.mMatchConstraintMaxHeight, height);
                }
            }
            int i = finalValue + ((int) ((((finalValue2 - finalValue) - height) * verticalBiasPercent) + 0.5f));
            widget.setFinalVertical(i, i + height);
            verticalSolvingPass(level + 1, widget, measurer);
        }
    }

    public static void solvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = layout.getVerticalDimensionBehaviour();
        hcount = 0;
        vcount = 0;
        layout.resetFinalResolution();
        ArrayList<ConstraintWidget> children = layout.getChildren();
        int size = children.size();
        for (int i = 0; i < size; i++) {
            children.get(i).resetFinalResolution();
        }
        boolean isRtl = layout.isRtl();
        if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            layout.setFinalHorizontal(0, layout.getWidth());
        } else {
            layout.setFinalLeft(0);
        }
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = children.get(i2);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && layout.isResolvedHorizontally()) {
                        guideline.setFinalValue(layout.getWidth() - guideline.getRelativeEnd());
                    } else if (layout.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * layout.getWidth()) + 0.5f));
                    }
                    z = EARLY_TERMINATION;
                }
            } else if ((constraintWidget instanceof Barrier) && ((Barrier) constraintWidget).getOrientation() == 0) {
                z2 = EARLY_TERMINATION;
            }
        }
        if (z) {
            for (int i3 = 0; i3 < size; i3++) {
                ConstraintWidget constraintWidget2 = children.get(i3);
                if (constraintWidget2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) constraintWidget2;
                    if (guideline2.getOrientation() == 1) {
                        horizontalSolvingPass(0, guideline2, measurer, isRtl);
                    }
                }
            }
        }
        horizontalSolvingPass(0, layout, measurer, isRtl);
        if (z2) {
            for (int i4 = 0; i4 < size; i4++) {
                ConstraintWidget constraintWidget3 = children.get(i4);
                if (constraintWidget3 instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintWidget3;
                    if (barrier.getOrientation() == 0) {
                        solveBarrier(0, barrier, measurer, 0, isRtl);
                    }
                }
            }
        }
        if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            layout.setFinalVertical(0, layout.getHeight());
        } else {
            layout.setFinalTop(0);
        }
        boolean z3 = false;
        boolean z4 = false;
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = children.get(i5);
            if (constraintWidget4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) constraintWidget4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && layout.isResolvedVertically()) {
                        guideline3.setFinalValue(layout.getHeight() - guideline3.getRelativeEnd());
                    } else if (layout.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * layout.getHeight()) + 0.5f));
                    }
                    z3 = EARLY_TERMINATION;
                }
            } else if ((constraintWidget4 instanceof Barrier) && ((Barrier) constraintWidget4).getOrientation() == 1) {
                z4 = EARLY_TERMINATION;
            }
        }
        if (z3) {
            for (int i6 = 0; i6 < size; i6++) {
                ConstraintWidget constraintWidget5 = children.get(i6);
                if (constraintWidget5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) constraintWidget5;
                    if (guideline4.getOrientation() == 0) {
                        verticalSolvingPass(1, guideline4, measurer);
                    }
                }
            }
        }
        verticalSolvingPass(0, layout, measurer);
        if (z4) {
            for (int i7 = 0; i7 < size; i7++) {
                ConstraintWidget constraintWidget6 = children.get(i7);
                if (constraintWidget6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) constraintWidget6;
                    if (barrier2.getOrientation() == 1) {
                        solveBarrier(0, barrier2, measurer, 1, isRtl);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < size; i8++) {
            ConstraintWidget constraintWidget7 = children.get(i8);
            if (constraintWidget7.isMeasureRequested() && canMeasure(0, constraintWidget7)) {
                ConstraintWidgetContainer.measure(0, constraintWidget7, measurer, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
                if (!(constraintWidget7 instanceof Guideline)) {
                    horizontalSolvingPass(0, constraintWidget7, measurer, isRtl);
                    verticalSolvingPass(0, constraintWidget7, measurer);
                } else if (((Guideline) constraintWidget7).getOrientation() == 0) {
                    verticalSolvingPass(0, constraintWidget7, measurer);
                } else {
                    horizontalSolvingPass(0, constraintWidget7, measurer, isRtl);
                }
            }
        }
    }

    private static void verticalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer) {
        if (layout.isVerticalSolvingPassDone()) {
            return;
        }
        vcount++;
        if (!(layout instanceof ConstraintWidgetContainer) && layout.isMeasureRequested() && canMeasure(level + 1, layout)) {
            ConstraintWidgetContainer.measure(level + 1, layout, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        ConstraintAnchor anchor = layout.getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor2 = layout.getAnchor(ConstraintAnchor.Type.BOTTOM);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget = next.mOwner;
                boolean canMeasure = canMeasure(level + 1, constraintWidget);
                if (constraintWidget.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                boolean z = ((next == constraintWidget.mTop && constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.hasFinalValue()) || (next == constraintWidget.mBottom && constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.hasFinalValue())) ? EARLY_TERMINATION : false;
                if (constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                    if (!constraintWidget.isMeasureRequested()) {
                        if (next == constraintWidget.mTop && constraintWidget.mBottom.mTarget == null) {
                            int margin = constraintWidget.mTop.getMargin() + finalValue;
                            constraintWidget.setFinalVertical(margin, constraintWidget.getHeight() + margin);
                            verticalSolvingPass(level + 1, constraintWidget, measurer);
                        } else if (next == constraintWidget.mBottom && constraintWidget.mTop.mTarget == null) {
                            int margin2 = finalValue - constraintWidget.mBottom.getMargin();
                            constraintWidget.setFinalVertical(margin2 - constraintWidget.getHeight(), margin2);
                            verticalSolvingPass(level + 1, constraintWidget, measurer);
                        } else if (z && !constraintWidget.isInVerticalChain()) {
                            solveVerticalCenterConstraints(level + 1, measurer, constraintWidget);
                        }
                    }
                } else if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintMaxHeight >= 0 && constraintWidget.mMatchConstraintMinHeight >= 0 && ((constraintWidget.getVisibility() == 8 || (constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.getDimensionRatio() == 0.0f)) && !constraintWidget.isInVerticalChain() && !constraintWidget.isInVirtualLayout() && z && !constraintWidget.isInVerticalChain())) {
                    solveVerticalMatchConstraint(level + 1, layout, measurer, constraintWidget);
                }
            }
        }
        if (layout instanceof Guideline) {
            return;
        }
        if (anchor2.getDependents() != null && anchor2.hasFinalValue()) {
            Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
            while (it2.hasNext()) {
                ConstraintAnchor next2 = it2.next();
                ConstraintWidget constraintWidget2 = next2.mOwner;
                boolean canMeasure2 = canMeasure(level + 1, constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure2) {
                    ConstraintWidgetContainer.measure(level + 1, constraintWidget2, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                boolean z2 = ((next2 == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget != null && constraintWidget2.mBottom.mTarget.hasFinalValue()) || (next2 == constraintWidget2.mBottom && constraintWidget2.mTop.mTarget != null && constraintWidget2.mTop.mTarget.hasFinalValue())) ? EARLY_TERMINATION : false;
                if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !canMeasure2) {
                    if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.mMatchConstraintMaxHeight >= 0 && constraintWidget2.mMatchConstraintMinHeight >= 0) {
                        if (constraintWidget2.getVisibility() != 8) {
                            if (constraintWidget2.mMatchConstraintDefaultHeight == 0 && constraintWidget2.getDimensionRatio() == 0.0f) {
                            }
                        }
                        if (!constraintWidget2.isInVerticalChain() && !constraintWidget2.isInVirtualLayout() && z2 && !constraintWidget2.isInVerticalChain()) {
                            solveVerticalMatchConstraint(level + 1, layout, measurer, constraintWidget2);
                        }
                    }
                }
                if (!constraintWidget2.isMeasureRequested()) {
                    if (next2 == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget == null) {
                        int margin3 = constraintWidget2.mTop.getMargin() + finalValue2;
                        constraintWidget2.setFinalVertical(margin3, constraintWidget2.getHeight() + margin3);
                        verticalSolvingPass(level + 1, constraintWidget2, measurer);
                    } else if (next2 == constraintWidget2.mBottom && constraintWidget2.mTop.mTarget == null) {
                        int margin4 = finalValue2 - constraintWidget2.mBottom.getMargin();
                        constraintWidget2.setFinalVertical(margin4 - constraintWidget2.getHeight(), margin4);
                        verticalSolvingPass(level + 1, constraintWidget2, measurer);
                    } else if (z2 && !constraintWidget2.isInVerticalChain()) {
                        solveVerticalCenterConstraints(level + 1, measurer, constraintWidget2);
                    }
                }
            }
        }
        ConstraintAnchor anchor3 = layout.getAnchor(ConstraintAnchor.Type.BASELINE);
        if (anchor3.getDependents() != null && anchor3.hasFinalValue()) {
            int finalValue3 = anchor3.getFinalValue();
            Iterator<ConstraintAnchor> it3 = anchor3.getDependents().iterator();
            while (it3.hasNext()) {
                ConstraintAnchor next3 = it3.next();
                ConstraintWidget constraintWidget3 = next3.mOwner;
                boolean canMeasure3 = canMeasure(level + 1, constraintWidget3);
                if (constraintWidget3.isMeasureRequested() && canMeasure3) {
                    ConstraintWidgetContainer.measure(level + 1, constraintWidget3, measurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                }
                if (constraintWidget3.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure3) {
                    if (!constraintWidget3.isMeasureRequested() && next3 == constraintWidget3.mBaseline) {
                        constraintWidget3.setFinalBaseline(next3.getMargin() + finalValue3);
                        verticalSolvingPass(level + 1, constraintWidget3, measurer);
                    }
                }
            }
        }
        layout.markVerticalSolvingPassDone();
    }
}
