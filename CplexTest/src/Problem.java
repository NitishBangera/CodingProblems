import java.util.List;

public class Problem {
	private final ObjectiveFunction objectiveFunction;
	
	private final List<Constraint> constraints;
	
	private final String exportModelPath;

	public Problem(ObjectiveFunction objectiveFunction, List<Constraint> constraints, String exportModelPath) {
		this.objectiveFunction = objectiveFunction;
		this.constraints = constraints;
		this.exportModelPath = exportModelPath;
	}

	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public String getExportModelPath() {
		return exportModelPath;
	}	
}
