package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;

/* loaded from: classes.dex */
public abstract class WidgetRun implements Dependency {
    protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public int matchConstraintsType;
    RunGroup runGroup;
    ConstraintWidget widget;
    DimensionDependency dimension = new DimensionDependency(this);
    public int orientation = 0;
    boolean resolved = false;
    public DependencyNode start = new DependencyNode(this);
    public DependencyNode end = new DependencyNode(this);
    protected RunType mRunType = RunType.NONE;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.WidgetRun$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;

        static {
            int[] iArr = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr;
            try {
                iArr[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* loaded from: classes.dex */
    enum RunType {
        NONE,
        START,
        END,
        CENTER
    }

    public WidgetRun(ConstraintWidget widget) {
        this.widget = widget;
    }

    private void resolveDimension(int orientation, int distance) {
        switch (this.matchConstraintsType) {
            case 0:
                this.dimension.resolve(getLimitedDimension(distance, orientation));
                return;
            case 1:
                this.dimension.resolve(Math.min(getLimitedDimension(this.dimension.wrapValue, orientation), distance));
                return;
            case 2:
                ConstraintWidget parent = this.widget.getParent();
                if (parent != null) {
                    if ((orientation == 0 ? parent.horizontalRun : parent.verticalRun).dimension.resolved) {
                        ConstraintWidget constraintWidget = this.widget;
                        this.dimension.resolve(getLimitedDimension((int) ((r2.dimension.value * (orientation == 0 ? constraintWidget.mMatchConstraintPercentWidth : constraintWidget.mMatchConstraintPercentHeight)) + 0.5f), orientation));
                        return;
                    }
                    return;
                }
                return;
            case 3:
                if (this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.horizontalRun.matchConstraintsType == 3 && this.widget.verticalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.verticalRun.matchConstraintsType == 3) {
                    return;
                }
                ConstraintWidget constraintWidget2 = this.widget;
                if ((orientation == 0 ? constraintWidget2.verticalRun : constraintWidget2.horizontalRun).dimension.resolved) {
                    float dimensionRatio = this.widget.getDimensionRatio();
                    this.dimension.resolve(orientation == 1 ? (int) ((r0.dimension.value / dimensionRatio) + 0.5f) : (int) ((r0.dimension.value * dimensionRatio) + 0.5f));
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void addTarget(DependencyNode node, DependencyNode target, int margin) {
        node.targets.add(target);
        node.margin = margin;
        target.dependencies.add(node);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void addTarget(DependencyNode node, DependencyNode target, int marginFactor, DimensionDependency dimensionDependency) {
        node.targets.add(target);
        node.targets.add(this.dimension);
        node.marginFactor = marginFactor;
        node.marginDependency = dimensionDependency;
        target.dependencies.add(node);
        dimensionDependency.dependencies.add(node);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void apply();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void applyToWidget();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void clear();

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getLimitedDimension(int dimension, int orientation) {
        if (orientation == 0) {
            int i = this.widget.mMatchConstraintMaxWidth;
            int dimension2 = Math.max(this.widget.mMatchConstraintMinWidth, dimension);
            if (i > 0) {
                dimension2 = Math.min(i, dimension);
            }
            return dimension2 != dimension ? dimension2 : dimension;
        }
        int i2 = this.widget.mMatchConstraintMaxHeight;
        int dimension3 = Math.max(this.widget.mMatchConstraintMinHeight, dimension);
        if (i2 > 0) {
            dimension3 = Math.min(i2, dimension);
        }
        return dimension3 != dimension ? dimension3 : dimension;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final DependencyNode getTarget(ConstraintAnchor anchor) {
        if (anchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = anchor.mTarget.mOwner;
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[anchor.mTarget.mType.ordinal()]) {
            case 1:
                return constraintWidget.horizontalRun.start;
            case 2:
                return constraintWidget.horizontalRun.end;
            case 3:
                return constraintWidget.verticalRun.start;
            case 4:
                return constraintWidget.verticalRun.baseline;
            case 5:
                return constraintWidget.verticalRun.end;
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final DependencyNode getTarget(ConstraintAnchor anchor, int orientation) {
        if (anchor.mTarget == null) {
            return null;
        }
        ConstraintWidget constraintWidget = anchor.mTarget.mOwner;
        WidgetRun widgetRun = orientation == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[anchor.mTarget.mType.ordinal()]) {
            case 1:
            case 3:
                return widgetRun.start;
            case 2:
            case 5:
                return widgetRun.end;
            case 4:
            default:
                return null;
        }
    }

    public long getWrapDimension() {
        if (this.dimension.resolved) {
            return this.dimension.value;
        }
        return 0L;
    }

    public boolean isCenterConnection() {
        int i = 0;
        int size = this.start.targets.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.start.targets.get(i2).run != this) {
                i++;
            }
        }
        int size2 = this.end.targets.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (this.end.targets.get(i3).run != this) {
                i++;
            }
        }
        return i >= 2;
    }

    public boolean isDimensionResolved() {
        return this.dimension.resolved;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    abstract void reset();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean supportsWrapComputation();

    @Override // androidx.constraintlayout.core.widgets.analyzer.Dependency
    public void update(Dependency dependency) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateRunCenter(Dependency dependency, ConstraintAnchor startAnchor, ConstraintAnchor endAnchor, int orientation) {
        DependencyNode target = getTarget(startAnchor);
        DependencyNode target2 = getTarget(endAnchor);
        if (target.resolved && target2.resolved) {
            int margin = target.value + startAnchor.getMargin();
            int margin2 = target2.value - endAnchor.getMargin();
            int i = margin2 - margin;
            if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                resolveDimension(orientation, i);
            }
            if (this.dimension.resolved) {
                if (this.dimension.value == i) {
                    this.start.resolve(margin);
                    this.end.resolve(margin2);
                    return;
                }
                ConstraintWidget constraintWidget = this.widget;
                float horizontalBiasPercent = orientation == 0 ? constraintWidget.getHorizontalBiasPercent() : constraintWidget.getVerticalBiasPercent();
                if (target == target2) {
                    margin = target.value;
                    margin2 = target2.value;
                    horizontalBiasPercent = 0.5f;
                }
                this.start.resolve((int) (margin + 0.5f + (((margin2 - margin) - this.dimension.value) * horizontalBiasPercent)));
                this.end.resolve(this.start.value + this.dimension.value);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateRunEnd(Dependency dependency) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateRunStart(Dependency dependency) {
    }

    public long wrapSize(int direction) {
        if (!this.dimension.resolved) {
            return 0L;
        }
        long j = this.dimension.value;
        return isCenterConnection() ? j + (this.start.margin - this.end.margin) : direction == 0 ? j + this.start.margin : j - this.end.margin;
    }
}
