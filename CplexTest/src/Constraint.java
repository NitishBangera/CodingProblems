import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Constraint {
	private final String constraintName;
	private final List<Variable> constraintTerms;
	private final Operator comparingOperator;
	private BigDecimal comparingValue;
	
	public Constraint(String constraintName, Operator comparingOperator) {
		this.constraintName = constraintName;
		this.constraintTerms = new ArrayList<>();
		this.comparingOperator = comparingOperator;
	}

	public BigDecimal getComparingValue() {
		return comparingValue;
	}

	public void setComparingValue(BigDecimal comparingValue) {
		this.comparingValue = comparingValue;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public List<Variable> getConstraintTerms() {
		return constraintTerms;
	}
	
	public void addConstraintTerms(Variable objectiveTerm) {
		this.constraintTerms.add(objectiveTerm);
	}

	public Operator getComparingOperator() {
		return comparingOperator;
	}		
}
