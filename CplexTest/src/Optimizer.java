import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.Status;

public class Optimizer {	
	private IloCplex instantiateCplex() throws IloException{
		IloCplex cplex = new IloCplex();
		
		cplex.setOut(System.out);
		cplex.setWarning(System.err);
		
		cplex.setParam(IloCplex.IntParam.Reduce, 0);
		cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);
		cplex.setParam(IloCplex.IntParam.MIPEmphasis, IloCplex.MIPEmphasis.Feasibility);
		cplex.setParam(IloCplex.IntParam.Threads, 2);
		cplex.setParam(IloCplex.IntParam.ParallelMode, IloCplex.ParallelMode.Deterministic);		
		cplex.setParam(IloCplex.BooleanParam.NumericalEmphasis, true);
		cplex.setParam(IloCplex.BooleanParam.DataCheck, true);
		cplex.setParam(IloCplex.BooleanParam.PreInd, true);
		cplex.setParam(IloCplex.DoubleParam.TiLim, 1200);
		
		return cplex;
	}
	
	private IloLinearNumExpr createLinearNumberExpression(IloCplex cplex, List<Variable> variables, 
			Map<Variable, IloNumVar> cplexVariables) throws IloException {
		IloLinearNumExpr linearExpression = cplex.linearNumExpr();
		
		for (Variable variable : variables) {
			final IloNumVar cplexVariable;
		
			if (cplexVariables.containsKey(variable)) {
				cplexVariable = cplexVariables.get(variable);
			} else {
				cplexVariable = cplex.numVar(0D, Double.MAX_VALUE, variable.getVariableName());
				cplexVariables.put(variable, cplexVariable);
			}
			
			BigDecimal coefficient = variable.getCoefficient();
			
			if (coefficient == null) {
				coefficient = BigDecimal.ONE;
			}
			
			double cplexVariableCoefficient = variable.getCoeSign() == Sign.MINUS 
					? coefficient.negate().doubleValue() : coefficient.doubleValue();
					
			linearExpression.addTerm(cplexVariable, cplexVariableCoefficient);
		}
		
		return linearExpression;
	}
	
	private void createOptimizationObjectiveFunction(IloCplex cplex, ObjectiveFunction objectiveFunction, 
			Map<Variable, IloNumVar> cplexVariables) throws IloException {
		final IloLinearNumExpr objectiveExpression = createLinearNumberExpression(cplex, 
				objectiveFunction.getObjectiveTerms(), cplexVariables);
		cplex.addObjective(objectiveFunction.getObjectiveSense(), objectiveExpression)
		.setName(objectiveFunction.getObjectiveName());
	}
	
	private void createOptimizationConstraints(IloCplex cplex, List<Constraint> constraints, 
			Map<Variable, IloNumVar> cplexVariables) throws IloException {
		for (Constraint constraint : constraints) {
			final IloLinearNumExpr subjectToExpression = createLinearNumberExpression(cplex, 
					constraint.getConstraintTerms(), cplexVariables);
			final double comparingValue = constraint.getComparingValue().doubleValue();
			final String constraintName = constraint.getConstraintName();
			final Operator comparingOperator = constraint.getComparingOperator();
			
			switch(comparingOperator) {
				case EQ:
					cplex.addEq(subjectToExpression, comparingValue, constraintName);
					break;
				case LE:
					cplex.addLe(subjectToExpression, comparingValue, constraintName);
					break;
				case GE:
					cplex.addGe(subjectToExpression, comparingValue, constraintName);
					break;
				default:
					System.out.println("Invalid Operator : " + comparingOperator.name());
			}
		}
	}
	
	public Solution solve(Problem problem) throws IloException {
		IloCplex cplex = null; 
				
		try {
		cplex = instantiateCplex();
		final Map<Variable, IloNumVar> cplexVariables = new HashMap<>();
		
		createOptimizationObjectiveFunction(cplex, problem.getObjectiveFunction(), cplexVariables);
		createOptimizationConstraints(cplex, problem.getConstraints(), cplexVariables);
		
		cplex.exportModel(problem.getExportModelPath());
		
		final boolean cplexResult = cplex.solve();
		final Status cplexStatus = cplex.getStatus();
		
		System.out.printf("Status : %s Time : %s, SolveResult : %s, Variables : %s", 
				cplexStatus, cplex.getCplexTime(), cplexResult, cplexVariables.size());		
		
		Solution solution = new Solution(cplex.getObjValue());
				
		if (cplexResult) {
			for (Map.Entry<Variable, IloNumVar> variable : cplexVariables.entrySet()) {
				double cplexValue = cplex.getValue(variable.getValue());
				solution.addOutputValues(variable.getKey(), new BigDecimal(Double.toString(cplexValue)));
			}
		}
		
		return solution;		
		} finally {
			if (null != cplex) {
				cplex.end();
			}
		}		
	}
}
