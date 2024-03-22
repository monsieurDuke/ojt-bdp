package androidx.constraintlayout.core.widgets.analyzer;

/* loaded from: classes.dex */
class BaselineDimensionDependency extends DimensionDependency {
    public BaselineDimensionDependency(WidgetRun run) {
        super(run);
    }

    public void update(DependencyNode node) {
        ((VerticalWidgetRun) this.run).baseline.margin = this.run.widget.getBaselineDistance();
        this.resolved = true;
    }
}
