import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Solution {
	private final BigDecimal objectiveValue;
	
	private Map<Variable, BigDecimal> values;

	public Solution(double objectiveValue) {
		this.values = new HashMap<>();
		this.objectiveValue = new BigDecimal(Double.toString(objectiveValue));
	}
	
	public BigDecimal getObjectiveValue() {
		return objectiveValue;
	}

	public void addOutputValues(Variable variable, BigDecimal value) {
		values.put(variable, value);
	}
	
	public BigDecimal getOutputValue(Variable variable) {
		return values.get(variable);
	}	
}
