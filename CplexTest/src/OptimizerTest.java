import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ilog.concert.IloException;
import ilog.concert.IloObjectiveSense;

public class OptimizerTest {
	private Optimizer optimizer;
	
	@Before
	public void setUp() {
		optimizer = new Optimizer();
	}
	
	@After
	public void tearDown() {
		optimizer = null;
	}	

	@Test
	public void testOptimizerSolveSimple() throws IloException {
		ObjectiveFunction objectiveFunction = new ObjectiveFunction("TestObjective", IloObjectiveSense.Maximize);
		Variable variable1 = new Variable("VarName", BigDecimal.ONE, Sign.PLUS);
		objectiveFunction.addObjectiveTerms(variable1);
		
		Constraint testConstraint = new Constraint("test", Operator.LE);
		Variable variable2 = new Variable("VarName", BigDecimal.ONE, Sign.PLUS);
		testConstraint.addConstraintTerms(variable2);
		testConstraint.setComparingValue(BigDecimal.TEN);
		
		Problem problem = new Problem(objectiveFunction, Collections.<Constraint>singletonList(testConstraint), "Simple.lp");
		
		Solution solution = optimizer.solve(problem);
		
		assertEquals(new BigDecimal("10.0"), solution.getObjectiveValue());
		assertEquals(new BigDecimal("10.0"), solution.getOutputValue(variable1));
	}
	
	@Test
	public void testOptimizerSolveComplex() throws IloException {
		// Maximize
		// Objective: 10 X + 50 Y + 20 Z + 60 K
		ObjectiveFunction objectiveFunction = new ObjectiveFunction("Objective", IloObjectiveSense.Maximize);
		
		Variable variable1 = new Variable("X", new BigDecimal("10.0"), Sign.PLUS);
		Variable variable2 = new Variable("Y", new BigDecimal("50.0"), Sign.PLUS);
		Variable variable3 = new Variable("Z", new BigDecimal("20.0"), Sign.PLUS);
		Variable variable4 = new Variable("K", new BigDecimal("60.0"), Sign.PLUS);
		
		objectiveFunction.addObjectiveTerms(variable1);
		objectiveFunction.addObjectiveTerms(variable2);
		objectiveFunction.addObjectiveTerms(variable3);
		objectiveFunction.addObjectiveTerms(variable4);
		
		// Subject To
		List<Constraint> constraints = new ArrayList<Constraint>();
		
		// Constraint1 : M <= 200
		Constraint availQuantity = new Constraint("Constraint1", Operator.LE);
		Variable variable5 = new Variable("M", BigDecimal.ONE, Sign.PLUS);
		availQuantity.addConstraintTerms(variable5);
		availQuantity.setComparingValue(new BigDecimal("200"));
		constraints.add(availQuantity);
		
		// Constraint2 : M >= 0
		Constraint minRel = new Constraint("Constraint2", Operator.GE);
		Variable variable6 = new Variable("M", BigDecimal.ONE, Sign.PLUS);
		minRel.addConstraintTerms(variable6);
		minRel.setComparingValue(BigDecimal.ZERO);
		constraints.add(minRel);
				
		// Constraint3 : M <= 150
		Constraint mxf = new Constraint("Constraint3", Operator.LE);
		Variable variable7 = new Variable("M", BigDecimal.ONE, Sign.PLUS);
		mxf.addConstraintTerms(variable7);
		mxf.setComparingValue(new BigDecimal("150"));
		constraints.add(mxf);
		
		// Constraint4: M - X - Y - Z - K  = 0
		Constraint totalRel = new Constraint("Constraint4", Operator.EQ);
		Variable variable8 = new Variable("M", BigDecimal.ONE, Sign.PLUS);
		Variable variable9 = new Variable("X", BigDecimal.ONE, Sign.MINUS);
		Variable variable10 = new Variable("Y", BigDecimal.ONE, Sign.MINUS);
		Variable variable11 = new Variable("Z", BigDecimal.ONE, Sign.MINUS);
		Variable variable12 = new Variable("K", BigDecimal.ONE, Sign.MINUS);
		totalRel.addConstraintTerms(variable8);
		totalRel.addConstraintTerms(variable9);
		totalRel.addConstraintTerms(variable10);
		totalRel.addConstraintTerms(variable11);
		totalRel.addConstraintTerms(variable12);
		totalRel.setComparingValue(BigDecimal.ZERO);
		constraints.add(totalRel);
		
		// Constraint5: Y <= 50
		Constraint needPri6 = new Constraint("Constraint5", Operator.LE);
		Variable variable13 = new Variable("Y", BigDecimal.ONE, Sign.PLUS);
		needPri6.addConstraintTerms(variable13);
		needPri6.setComparingValue(new BigDecimal("50"));
		constraints.add(needPri6);
		
		// Constraint6: Z <= 70
		Constraint needPri7 = new Constraint("Constraint6", Operator.LE);
		Variable variable14 = new Variable("Z", BigDecimal.ONE, Sign.PLUS);
		needPri7.addConstraintTerms(variable14);
		needPri7.setComparingValue(new BigDecimal("70"));
		constraints.add(needPri7);
		
		// Constraint7: K <= 20
		Constraint needPri8 = new Constraint("Constraint7", Operator.LE);
		Variable variable15 = new Variable("K", BigDecimal.ONE, Sign.PLUS);
		needPri8.addConstraintTerms(variable15);
		needPri8.setComparingValue(new BigDecimal("20"));
		constraints.add(needPri8);
		
		Problem problem = new Problem(objectiveFunction, constraints, "Complex.lp");
		
		Solution solution = optimizer.solve(problem);
		
		assertEquals(new BigDecimal("5200.0"), solution.getObjectiveValue());
		assertEquals(new BigDecimal("10.0"), solution.getOutputValue(variable1));
		assertEquals(new BigDecimal("50.0"), solution.getOutputValue(variable2));
		assertEquals(new BigDecimal("70.0"), solution.getOutputValue(variable3));
		assertEquals(new BigDecimal("20.0"), solution.getOutputValue(variable4));
		assertEquals(new BigDecimal("150.0"), solution.getOutputValue(variable5));
	}

}
