import java.math.BigDecimal;

public class Variable {
	private final String variableName;
	
	private final BigDecimal coefficient;
	
	private final Sign coeSign;

	public Variable(String variableName, BigDecimal coefficient, Sign coeSign) {
		this.variableName = variableName;
		this.coefficient = coefficient;
		this.coeSign = coeSign;
	}

	public String getVariableName() {
		return variableName;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public Sign getCoeSign() {
		return coeSign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variableName == null) ? 0 : variableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (variableName == null) {
			if (other.variableName != null)
				return false;
		} else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}
}
