package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

/* loaded from: classes.dex */
public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1, reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public HorizontalWidgetRun(ConstraintWidget widget) {
        super(widget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    private void computeInsetRatio(int[] dimensions, int x1, int x2, int y1, int y2, float ratio, int side) {
        int i = x2 - x1;
        int i2 = y2 - y1;
        switch (side) {
            case -1:
                int i3 = (int) ((i2 * ratio) + 0.5f);
                int i4 = (int) ((i / ratio) + 0.5f);
                if (i3 <= i && i2 <= i2) {
                    dimensions[0] = i3;
                    dimensions[1] = i2;
                    return;
                } else {
                    if (i > i || i4 > i2) {
                        return;
                    }
                    dimensions[0] = i;
                    dimensions[1] = i4;
                    return;
                }
            case 0:
                dimensions[0] = (int) ((i2 * ratio) + 0.5f);
                dimensions[1] = i2;
                return;
            case 1:
                dimensions[0] = i;
                dimensions[1] = (int) ((i * ratio) + 0.5f);
                return;
            default:
                return;
        }
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getWidth());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.widget.getParent()) != null && (parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int width = (parent2.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(width);
                    return;
                }
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.widget.getParent()) != null && (parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
        }
        if (this.dimension.resolved && this.widget.measured) {
            if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
                if (this.widget.isInHorizontalChain()) {
                    this.start.margin = this.widget.mListAnchors[0].getMargin();
                    this.end.margin = -this.widget.mListAnchors[1].getMargin();
                    return;
                }
                DependencyNode target = getTarget(this.widget.mListAnchors[0]);
                if (target != null) {
                    addTarget(this.start, target, this.widget.mListAnchors[0].getMargin());
                }
                DependencyNode target2 = getTarget(this.widget.mListAnchors[1]);
                if (target2 != null) {
                    addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
                return;
            }
            if (this.widget.mListAnchors[0].mTarget != null) {
                DependencyNode target3 = getTarget(this.widget.mListAnchors[0]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.widget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.dimension.value);
                    return;
                }
                return;
            }
            if (this.widget.mListAnchors[1].mTarget != null) {
                DependencyNode target4 = getTarget(this.widget.mListAnchors[1]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.dimension.value);
                    return;
                }
                return;
            }
            if ((this.widget instanceof Helper) || this.widget.getParent() == null || this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget != null) {
                return;
            }
            addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
            addTarget(this.end, this.start, this.dimension.value);
            return;
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            switch (this.widget.mMatchConstraintDefaultWidth) {
                case 2:
                    ConstraintWidget parent3 = this.widget.getParent();
                    if (parent3 != null) {
                        DimensionDependency dimensionDependency = parent3.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency);
                        dimensionDependency.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        break;
                    }
                    break;
                case 3:
                    if (this.widget.mMatchConstraintDefaultHeight != 3) {
                        DimensionDependency dimensionDependency2 = this.widget.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency2);
                        dimensionDependency2.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        this.end.targets.add(this.dimension);
                        break;
                    } else {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        this.widget.verticalRun.start.updateDelegate = this;
                        this.widget.verticalRun.end.updateDelegate = this;
                        this.dimension.updateDelegate = this;
                        if (!this.widget.isInVerticalChain()) {
                            if (!this.widget.isInHorizontalChain()) {
                                this.widget.verticalRun.dimension.targets.add(this.dimension);
                                break;
                            } else {
                                this.widget.verticalRun.dimension.targets.add(this.dimension);
                                this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                                break;
                            }
                        } else {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            this.widget.verticalRun.dimension.updateDelegate = this;
                            this.dimension.targets.add(this.widget.verticalRun.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                            break;
                        }
                    }
            }
        }
        if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
            if (this.widget.isInHorizontalChain()) {
                this.start.margin = this.widget.mListAnchors[0].getMargin();
                this.end.margin = -this.widget.mListAnchors[1].getMargin();
                return;
            }
            DependencyNode target5 = getTarget(this.widget.mListAnchors[0]);
            DependencyNode target6 = getTarget(this.widget.mListAnchors[1]);
            if (target5 != null) {
                target5.addDependency(this);
            }
            if (target6 != null) {
                target6.addDependency(this);
            }
            this.mRunType = WidgetRun.RunType.CENTER;
            return;
        }
        if (this.widget.mListAnchors[0].mTarget != null) {
            DependencyNode target7 = getTarget(this.widget.mListAnchors[0]);
            if (target7 != null) {
                addTarget(this.start, target7, this.widget.mListAnchors[0].getMargin());
                addTarget(this.end, this.start, 1, this.dimension);
                return;
            }
            return;
        }
        if (this.widget.mListAnchors[1].mTarget != null) {
            DependencyNode target8 = getTarget(this.widget.mListAnchors[1]);
            if (target8 != null) {
                addTarget(this.end, target8, -this.widget.mListAnchors[1].getMargin());
                addTarget(this.start, this.end, -1, this.dimension);
                return;
            }
            return;
        }
        if ((this.widget instanceof Helper) || this.widget.getParent() == null) {
            return;
        }
        addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
        addTarget(this.end, this.start, 1, this.dimension);
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void applyToWidget() {
        if (this.start.resolved) {
            this.widget.setX(this.start.value);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.dimension.resolved = false;
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun
    boolean supportsWrapComputation() {
        return this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    @Override // androidx.constraintlayout.core.widgets.analyzer.WidgetRun, androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()]) {
            case 1:
                updateRunStart(dependency);
                break;
            case 2:
                updateRunEnd(dependency);
                break;
            case 3:
                updateRunCenter(dependency, this.widget.mLeft, this.widget.mRight, 0);
                return;
        }
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            switch (this.widget.mMatchConstraintDefaultWidth) {
                case 2:
                    ConstraintWidget parent = this.widget.getParent();
                    if (parent != null && parent.horizontalRun.dimension.resolved) {
                        this.dimension.resolve((int) ((parent.horizontalRun.dimension.value * this.widget.mMatchConstraintPercentWidth) + 0.5f));
                        break;
                    }
                    break;
                case 3:
                    if (this.widget.mMatchConstraintDefaultHeight != 0 && this.widget.mMatchConstraintDefaultHeight != 3) {
                        int i = 0;
                        switch (this.widget.getDimensionRatioSide()) {
                            case -1:
                                i = (int) ((this.widget.verticalRun.dimension.value * this.widget.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                i = (int) ((this.widget.verticalRun.dimension.value / this.widget.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                i = (int) ((this.widget.verticalRun.dimension.value * this.widget.getDimensionRatio()) + 0.5f);
                                break;
                        }
                        this.dimension.resolve(i);
                        break;
                    } else {
                        DependencyNode dependencyNode = this.widget.verticalRun.start;
                        DependencyNode dependencyNode2 = this.widget.verticalRun.end;
                        boolean z = this.widget.mLeft.mTarget != null;
                        boolean z2 = this.widget.mTop.mTarget != null;
                        boolean z3 = this.widget.mRight.mTarget != null;
                        boolean z4 = this.widget.mBottom.mTarget != null;
                        int dimensionRatioSide = this.widget.getDimensionRatioSide();
                        if (!z || !z2 || !z3 || !z4) {
                            if (!z || !z3) {
                                if (z2 && z4) {
                                    if (dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                        float dimensionRatio = this.widget.getDimensionRatio();
                                        int i2 = dependencyNode.targets.get(0).value + dependencyNode.margin;
                                        int i3 = dependencyNode2.targets.get(0).value - dependencyNode2.margin;
                                        switch (dimensionRatioSide) {
                                            case -1:
                                            case 1:
                                                int limitedDimension = getLimitedDimension(i3 - i2, 1);
                                                int i4 = (int) ((limitedDimension / dimensionRatio) + 0.5f);
                                                int limitedDimension2 = getLimitedDimension(i4, 0);
                                                if (i4 != limitedDimension2) {
                                                    limitedDimension = (int) ((limitedDimension2 * dimensionRatio) + 0.5f);
                                                }
                                                this.dimension.resolve(limitedDimension2);
                                                this.widget.verticalRun.dimension.resolve(limitedDimension);
                                                break;
                                            case 0:
                                                int limitedDimension3 = getLimitedDimension(i3 - i2, 1);
                                                int i5 = (int) ((limitedDimension3 * dimensionRatio) + 0.5f);
                                                int limitedDimension4 = getLimitedDimension(i5, 0);
                                                if (i5 != limitedDimension4) {
                                                    limitedDimension3 = (int) ((limitedDimension4 / dimensionRatio) + 0.5f);
                                                }
                                                this.dimension.resolve(limitedDimension4);
                                                this.widget.verticalRun.dimension.resolve(limitedDimension3);
                                                break;
                                        }
                                    } else {
                                        return;
                                    }
                                }
                            } else if (this.start.readyToSolve && this.end.readyToSolve) {
                                float dimensionRatio2 = this.widget.getDimensionRatio();
                                int i6 = this.start.targets.get(0).value + this.start.margin;
                                int i7 = this.end.targets.get(0).value - this.end.margin;
                                switch (dimensionRatioSide) {
                                    case -1:
                                    case 0:
                                        int limitedDimension5 = getLimitedDimension(i7 - i6, 0);
                                        int i8 = (int) ((limitedDimension5 * dimensionRatio2) + 0.5f);
                                        int limitedDimension6 = getLimitedDimension(i8, 1);
                                        if (i8 != limitedDimension6) {
                                            limitedDimension5 = (int) ((limitedDimension6 / dimensionRatio2) + 0.5f);
                                        }
                                        this.dimension.resolve(limitedDimension5);
                                        this.widget.verticalRun.dimension.resolve(limitedDimension6);
                                        break;
                                    case 1:
                                        int limitedDimension7 = getLimitedDimension(i7 - i6, 0);
                                        int i9 = (int) ((limitedDimension7 / dimensionRatio2) + 0.5f);
                                        int limitedDimension8 = getLimitedDimension(i9, 1);
                                        if (i9 != limitedDimension8) {
                                            limitedDimension7 = (int) ((limitedDimension8 * dimensionRatio2) + 0.5f);
                                        }
                                        this.dimension.resolve(limitedDimension7);
                                        this.widget.verticalRun.dimension.resolve(limitedDimension8);
                                        break;
                                }
                            } else {
                                return;
                            }
                        } else {
                            float dimensionRatio3 = this.widget.getDimensionRatio();
                            if (!dependencyNode.resolved || !dependencyNode2.resolved) {
                                if (this.start.resolved && this.end.resolved) {
                                    if (!dependencyNode.readyToSolve || !dependencyNode2.readyToSolve) {
                                        return;
                                    }
                                    computeInsetRatio(tempDimensions, this.start.value + this.start.margin, this.end.value - this.end.margin, dependencyNode.targets.get(0).value + dependencyNode.margin, dependencyNode2.targets.get(0).value - dependencyNode2.margin, dimensionRatio3, dimensionRatioSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                }
                                if (this.start.readyToSolve && this.end.readyToSolve && dependencyNode.readyToSolve && dependencyNode2.readyToSolve) {
                                    computeInsetRatio(tempDimensions, this.start.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, dependencyNode.targets.get(0).value + dependencyNode.margin, dependencyNode2.targets.get(0).value - dependencyNode2.margin, dimensionRatio3, dimensionRatioSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    break;
                                } else {
                                    return;
                                }
                            } else {
                                if (this.start.readyToSolve && this.end.readyToSolve) {
                                    computeInsetRatio(tempDimensions, this.start.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, dependencyNode.value + dependencyNode.margin, dependencyNode2.value - dependencyNode2.margin, dimensionRatio3, dimensionRatioSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    break;
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
                return;
            }
            if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.mMatchConstraintDefaultWidth == 0 && !this.widget.isInHorizontalChain()) {
                DependencyNode dependencyNode3 = this.start.targets.get(0);
                DependencyNode dependencyNode4 = this.end.targets.get(0);
                int i10 = dependencyNode3.value + this.start.margin;
                int i11 = dependencyNode4.value + this.end.margin;
                this.start.resolve(i10);
                this.end.resolve(i11);
                this.dimension.resolve(i11 - i10);
                return;
            }
            if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                int min = Math.min((this.end.targets.get(0).value + this.end.margin) - (this.start.targets.get(0).value + this.start.margin), this.dimension.wrapValue);
                int i12 = this.widget.mMatchConstraintMaxWidth;
                int max = Math.max(this.widget.mMatchConstraintMinWidth, min);
                if (i12 > 0) {
                    max = Math.min(i12, max);
                }
                this.dimension.resolve(max);
            }
            if (this.dimension.resolved) {
                DependencyNode dependencyNode5 = this.start.targets.get(0);
                DependencyNode dependencyNode6 = this.end.targets.get(0);
                int i13 = dependencyNode5.value + this.start.margin;
                int i14 = dependencyNode6.value + this.end.margin;
                float horizontalBiasPercent = this.widget.getHorizontalBiasPercent();
                if (dependencyNode5 == dependencyNode6) {
                    i13 = dependencyNode5.value;
                    i14 = dependencyNode6.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) (i13 + 0.5f + (((i14 - i13) - this.dimension.value) * horizontalBiasPercent)));
                this.end.resolve(this.start.value + this.dimension.value);
            }
        }
    }
}
