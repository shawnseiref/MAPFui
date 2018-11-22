package backEnd.Solution.SolutionValidators;

import backEnd.Error.AError;
import backEnd.Game.SubScenario;

import java.util.ArrayList;

public abstract class ASolutionValidator implements ISolutionValidator {
    public abstract ArrayList<AError> validateSol(SubScenario scene);


}
