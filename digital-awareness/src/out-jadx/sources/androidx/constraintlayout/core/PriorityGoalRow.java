package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.util.Arrays;
import java.util.Comparator;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class PriorityGoalRow extends ArrayRow {
    private static final boolean DEBUG = false;
    static final int NOT_FOUND = -1;
    private static final float epsilon = 1.0E-4f;
    private int TABLE_SIZE;
    GoalVariableAccessor accessor;
    private SolverVariable[] arrayGoals;
    Cache mCache;
    private int numGoals;
    private SolverVariable[] sortArray;

    /* loaded from: classes.dex */
    class GoalVariableAccessor {
        PriorityGoalRow row;
        SolverVariable variable;

        public GoalVariableAccessor(PriorityGoalRow row) {
            this.row = row;
        }

        public void add(SolverVariable other) {
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.variable.goalStrengthVector;
                fArr[i] = fArr[i] + other.goalStrengthVector[i];
                if (Math.abs(this.variable.goalStrengthVector[i]) < 1.0E-4f) {
                    this.variable.goalStrengthVector[i] = 0.0f;
                }
            }
        }

        public boolean addToGoal(SolverVariable other, float value) {
            if (!this.variable.inGoal) {
                for (int i = 0; i < 9; i++) {
                    float f = other.goalStrengthVector[i];
                    if (f != 0.0f) {
                        float f2 = value * f;
                        if (Math.abs(f2) < 1.0E-4f) {
                            f2 = 0.0f;
                        }
                        this.variable.goalStrengthVector[i] = f2;
                    } else {
                        this.variable.goalStrengthVector[i] = 0.0f;
                    }
                }
                return true;
            }
            boolean z = true;
            for (int i2 = 0; i2 < 9; i2++) {
                float[] fArr = this.variable.goalStrengthVector;
                fArr[i2] = fArr[i2] + (other.goalStrengthVector[i2] * value);
                if (Math.abs(this.variable.goalStrengthVector[i2]) < 1.0E-4f) {
                    this.variable.goalStrengthVector[i2] = 0.0f;
                } else {
                    z = false;
                }
            }
            if (!z) {
                return false;
            }
            PriorityGoalRow.this.removeGoal(this.variable);
            return false;
        }

        public void init(SolverVariable variable) {
            this.variable = variable;
        }

        public final boolean isNegative() {
            for (int i = 8; i >= 0; i--) {
                float f = this.variable.goalStrengthVector[i];
                if (f > 0.0f) {
                    return false;
                }
                if (f < 0.0f) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isNull() {
            for (int i = 0; i < 9; i++) {
                if (this.variable.goalStrengthVector[i] != 0.0f) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isSmallerThan(SolverVariable other) {
            for (int i = 8; i >= 0; i--) {
                float f = other.goalStrengthVector[i];
                float f2 = this.variable.goalStrengthVector[i];
                if (f2 != f) {
                    return f2 < f;
                }
            }
            return false;
        }

        public void reset() {
            Arrays.fill(this.variable.goalStrengthVector, 0.0f);
        }

        public String toString() {
            String str = "[ ";
            if (this.variable != null) {
                for (int i = 0; i < 9; i++) {
                    str = str + this.variable.goalStrengthVector[i] + " ";
                }
            }
            return str + "] " + this.variable;
        }
    }

    public PriorityGoalRow(Cache cache) {
        super(cache);
        this.TABLE_SIZE = 128;
        this.arrayGoals = new SolverVariable[128];
        this.sortArray = new SolverVariable[128];
        this.numGoals = 0;
        this.accessor = new GoalVariableAccessor(this);
        this.mCache = cache;
    }

    private final void addToGoal(SolverVariable variable) {
        int i;
        int i2 = this.numGoals + 1;
        SolverVariable[] solverVariableArr = this.arrayGoals;
        if (i2 > solverVariableArr.length) {
            SolverVariable[] solverVariableArr2 = (SolverVariable[]) Arrays.copyOf(solverVariableArr, solverVariableArr.length * 2);
            this.arrayGoals = solverVariableArr2;
            this.sortArray = (SolverVariable[]) Arrays.copyOf(solverVariableArr2, solverVariableArr2.length * 2);
        }
        SolverVariable[] solverVariableArr3 = this.arrayGoals;
        int i3 = this.numGoals;
        solverVariableArr3[i3] = variable;
        int i4 = i3 + 1;
        this.numGoals = i4;
        if (i4 > 1 && solverVariableArr3[i4 - 1].id > variable.id) {
            int i5 = 0;
            while (true) {
                i = this.numGoals;
                if (i5 >= i) {
                    break;
                }
                this.sortArray[i5] = this.arrayGoals[i5];
                i5++;
            }
            Arrays.sort(this.sortArray, 0, i, new Comparator<SolverVariable>() { // from class: androidx.constraintlayout.core.PriorityGoalRow.1
                @Override // java.util.Comparator
                public int compare(SolverVariable variable1, SolverVariable variable2) {
                    return variable1.id - variable2.id;
                }
            });
            for (int i6 = 0; i6 < this.numGoals; i6++) {
                this.arrayGoals[i6] = this.sortArray[i6];
            }
        }
        variable.inGoal = true;
        variable.addToRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeGoal(SolverVariable variable) {
        for (int i = 0; i < this.numGoals; i++) {
            if (this.arrayGoals[i] == variable) {
                int i2 = i;
                while (true) {
                    int i3 = this.numGoals;
                    if (i2 >= i3 - 1) {
                        this.numGoals = i3 - 1;
                        variable.inGoal = false;
                        return;
                    } else {
                        SolverVariable[] solverVariableArr = this.arrayGoals;
                        solverVariableArr[i2] = solverVariableArr[i2 + 1];
                        i2++;
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void addError(SolverVariable error) {
        this.accessor.init(error);
        this.accessor.reset();
        error.goalStrengthVector[error.strength] = 1.0f;
        addToGoal(error);
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void clear() {
        this.numGoals = 0;
        this.constantValue = 0.0f;
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public SolverVariable getPivotCandidate(LinearSystem system, boolean[] avoid) {
        int i = -1;
        for (int i2 = 0; i2 < this.numGoals; i2++) {
            SolverVariable solverVariable = this.arrayGoals[i2];
            if (!avoid[solverVariable.id]) {
                this.accessor.init(solverVariable);
                if (i == -1) {
                    if (this.accessor.isNegative()) {
                        i = i2;
                    }
                } else if (this.accessor.isSmallerThan(this.arrayGoals[i])) {
                    i = i2;
                }
            }
        }
        if (i == -1) {
            return null;
        }
        return this.arrayGoals[i];
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public boolean isEmpty() {
        return this.numGoals == 0;
    }

    @Override // androidx.constraintlayout.core.ArrayRow
    public String toString() {
        String str = HttpUrl.FRAGMENT_ENCODE_SET + " goal -> (" + this.constantValue + ") : ";
        for (int i = 0; i < this.numGoals; i++) {
            this.accessor.init(this.arrayGoals[i]);
            str = str + this.accessor + " ";
        }
        return str;
    }

    @Override // androidx.constraintlayout.core.ArrayRow, androidx.constraintlayout.core.LinearSystem.Row
    public void updateFromRow(LinearSystem system, ArrayRow definition, boolean removeFromDefinition) {
        SolverVariable solverVariable = definition.variable;
        if (solverVariable == null) {
            return;
        }
        ArrayRow.ArrayRowVariables arrayRowVariables = definition.variables;
        int currentSize = arrayRowVariables.getCurrentSize();
        for (int i = 0; i < currentSize; i++) {
            SolverVariable variable = arrayRowVariables.getVariable(i);
            float variableValue = arrayRowVariables.getVariableValue(i);
            this.accessor.init(variable);
            if (this.accessor.addToGoal(solverVariable, variableValue)) {
                addToGoal(variable);
            }
            this.constantValue += definition.constantValue * variableValue;
        }
        removeGoal(solverVariable);
    }
}
