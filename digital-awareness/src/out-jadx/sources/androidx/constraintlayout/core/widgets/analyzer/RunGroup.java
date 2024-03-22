package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int direction;
    WidgetRun firstRun;
    int groupIndex;
    WidgetRun lastRun;
    public int position = 0;
    public boolean dual = false;
    ArrayList<WidgetRun> runs = new ArrayList<>();

    public RunGroup(WidgetRun run, int dir) {
        this.firstRun = null;
        this.lastRun = null;
        this.groupIndex = 0;
        int i = index;
        this.groupIndex = i;
        index = i + 1;
        this.firstRun = run;
        this.lastRun = run;
        this.direction = dir;
    }

    private boolean defineTerminalWidget(WidgetRun run, int orientation) {
        if (!run.widget.isTerminalWidget[orientation]) {
            return false;
        }
        for (Dependency dependency : run.start.dependencies) {
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode = (DependencyNode) dependency;
                if (dependencyNode.run != run && dependencyNode == dependencyNode.run.start) {
                    if (run instanceof ChainRun) {
                        Iterator<WidgetRun> it = ((ChainRun) run).widgets.iterator();
                        while (it.hasNext()) {
                            defineTerminalWidget(it.next(), orientation);
                        }
                    } else if (!(run instanceof HelperReferences)) {
                        run.widget.isTerminalWidget[orientation] = false;
                    }
                    defineTerminalWidget(dependencyNode.run, orientation);
                }
            }
        }
        for (Dependency dependency2 : run.end.dependencies) {
            if (dependency2 instanceof DependencyNode) {
                DependencyNode dependencyNode2 = (DependencyNode) dependency2;
                if (dependencyNode2.run != run && dependencyNode2 == dependencyNode2.run.start) {
                    if (run instanceof ChainRun) {
                        Iterator<WidgetRun> it2 = ((ChainRun) run).widgets.iterator();
                        while (it2.hasNext()) {
                            defineTerminalWidget(it2.next(), orientation);
                        }
                    } else if (!(run instanceof HelperReferences)) {
                        run.widget.isTerminalWidget[orientation] = false;
                    }
                    defineTerminalWidget(dependencyNode2.run, orientation);
                }
            }
        }
        return false;
    }

    private long traverseEnd(DependencyNode node, long startPosition) {
        WidgetRun widgetRun = node.run;
        if (widgetRun instanceof HelperReferences) {
            return startPosition;
        }
        long j = startPosition;
        int size = node.dependencies.size();
        for (int i = 0; i < size; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode = (DependencyNode) dependency;
                if (dependencyNode.run != widgetRun) {
                    j = Math.min(j, traverseEnd(dependencyNode, dependencyNode.margin + startPosition));
                }
            }
        }
        if (node != widgetRun.end) {
            return j;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        return Math.min(Math.min(j, traverseEnd(widgetRun.start, startPosition - wrapDimension)), (startPosition - wrapDimension) - widgetRun.start.margin);
    }

    private long traverseStart(DependencyNode node, long startPosition) {
        WidgetRun widgetRun = node.run;
        if (widgetRun instanceof HelperReferences) {
            return startPosition;
        }
        long j = startPosition;
        int size = node.dependencies.size();
        for (int i = 0; i < size; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode dependencyNode = (DependencyNode) dependency;
                if (dependencyNode.run != widgetRun) {
                    j = Math.max(j, traverseStart(dependencyNode, dependencyNode.margin + startPosition));
                }
            }
        }
        if (node != widgetRun.start) {
            return j;
        }
        long wrapDimension = widgetRun.getWrapDimension();
        return Math.max(Math.max(j, traverseStart(widgetRun.end, startPosition + wrapDimension)), (startPosition + wrapDimension) - widgetRun.end.margin);
    }

    public void add(WidgetRun run) {
        this.runs.add(run);
        this.lastRun = run;
    }

    public long computeWrapSize(ConstraintWidgetContainer container, int orientation) {
        WidgetRun widgetRun = this.firstRun;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != orientation) {
                return 0L;
            }
        } else if (orientation == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0L;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0L;
        }
        DependencyNode dependencyNode = orientation == 0 ? container.horizontalRun.start : container.verticalRun.start;
        DependencyNode dependencyNode2 = orientation == 0 ? container.horizontalRun.end : container.verticalRun.end;
        boolean contains = this.firstRun.start.targets.contains(dependencyNode);
        boolean contains2 = this.firstRun.end.targets.contains(dependencyNode2);
        long wrapDimension = this.firstRun.getWrapDimension();
        if (!contains || !contains2) {
            if (contains) {
                return Math.max(traverseStart(this.firstRun.start, this.firstRun.start.margin), this.firstRun.start.margin + wrapDimension);
            }
            if (contains2) {
                return Math.max(-traverseEnd(this.firstRun.end, this.firstRun.end.margin), (-this.firstRun.end.margin) + wrapDimension);
            }
            return (this.firstRun.start.margin + this.firstRun.getWrapDimension()) - this.firstRun.end.margin;
        }
        long traverseStart = traverseStart(this.firstRun.start, 0L);
        long traverseEnd = traverseEnd(this.firstRun.end, 0L);
        long j = traverseStart - wrapDimension;
        if (j >= (-this.firstRun.end.margin)) {
            j += this.firstRun.end.margin;
        }
        long j2 = ((-traverseEnd) - wrapDimension) - this.firstRun.start.margin;
        if (j2 >= this.firstRun.start.margin) {
            j2 -= this.firstRun.start.margin;
        }
        long j3 = this.firstRun.widget.getBiasPercent(orientation) > 0.0f ? (((float) j2) / r5) + (((float) j) / (1.0f - r5)) : 0L;
        return (this.firstRun.start.margin + ((((((float) j3) * r5) + 0.5f) + wrapDimension) + ((((float) j3) * (1.0f - r5)) + 0.5f))) - this.firstRun.end.margin;
    }

    public void defineTerminalWidgets(boolean horizontalCheck, boolean verticalCheck) {
        if (horizontalCheck) {
            WidgetRun widgetRun = this.firstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (verticalCheck) {
            WidgetRun widgetRun2 = this.firstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
