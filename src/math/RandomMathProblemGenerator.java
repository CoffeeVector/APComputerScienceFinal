package math;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

import problems.ArithmeticProblem;
import problems.DerivativeProblem;
import problems.FunctionProblem;
import problems.IntegralProblem;
import problems.MathProblem;

/**
 * This Class is intended to generate a random MathProblem with a given
 * complexity specified at construction
 *
 */
public class RandomMathProblemGenerator {
	private static Class[] allProblems;
	/**
	 * instancites the class array
	 */
	static {
		allProblems = new Class[] { ArithmeticProblem.class, DerivativeProblem.class, FunctionProblem.class,
				IntegralProblem.class };
	}

	private Random r;
	private int averageDepth;
	private int maxNumberSize;

	/**
	 * Constructor for a RandomMathProblemGenerator
	 * 
	 * 
	 * @param averageDepth
	 *            average depth of the expressions generated
	 * @param maxNumberSize
	 *            the maximum number size of the numbers generated
	 */
	public RandomMathProblemGenerator(int averageDepth, int maxNumberSize) {
		r = new Random();
		this.averageDepth = averageDepth;
		this.maxNumberSize = maxNumberSize;
	}

	/**
	 * Method that returns the next random MathProblem
	 * 
	 * @return a random MathProblem.
	 */
	public MathProblem getNext() {
		try {
			return (MathProblem) allProblems[r.nextInt(allProblems.length)]
					.getMethod("randomProblem", int.class, int.class).invoke(null, averageDepth, maxNumberSize);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Repeatedly generates a random MathProblem until one that is solvable is
	 * generated
	 * 
	 * @return a random MathProblem. If it is not solvable, return another one.
	 */
	public MathProblem getNextSolvable() {
		MathProblem mp = getNext();
		if (mp.isUnsolvable()) {
			return getNextSolvable();
		}
		return mp;
	}

}