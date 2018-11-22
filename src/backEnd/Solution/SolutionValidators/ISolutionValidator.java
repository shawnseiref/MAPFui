package backEnd.Solution.SolutionValidators;

import backEnd.Error.AError;
import backEnd.Game.SubScenario;

import java.util.ArrayList;

public interface ISolutionValidator {
    public ArrayList<AError> validateSol(SubScenario scene);
}
