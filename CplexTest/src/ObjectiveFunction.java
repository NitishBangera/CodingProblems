import java.util.ArrayList;
import java.util.List;

import ilog.concert.IloObjectiveSense;

public class ObjectiveFunction {
	private final String objectiveName;
	private final List<Variable> objectiveTerms;
	private final IloObjectiveSense objectiveSense;
	
	public ObjectiveFunction(String objectiveName, IloObjectiveSense objectiveSense) {
		this.objectiveName = objectiveName;
		this.objectiveTerms = new ArrayList<>();
		this.objectiveSense = objectiveSense;
	}

	public List<Variable> getObjectiveTerms() {
		return objectiveTerms;
	}

	public void addObjectiveTerms(Variable objectiveTerm) {
		this.objectiveTerms.add(objectiveTerm);
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public IloObjectiveSense getObjectiveSense() {
		return objectiveSense;
	}	
	
}
