package problems;

import java.util.Random;

import math.NestingMathFunction;

/**
 * Class represents a DerivativeProblem. The question is giving a function and
 * asks for the the derivative with an input
 *
 */
public class DerivativeProblem extends MathProblem {
	private NestingMathFunction mf;
	private double input;

	private double threshold = .01;

	/**
	 * 
	 * 
	 * 
	 * @param mf
	 *            MathFunction that represents the function to differeciate
	 * @param input
	 *            input for the derivative of mf
	 */
	public DerivativeProblem(NestingMathFunction mf, double input) {
		this.mf = mf;
		this.input = input;
	}

	/**
	 * @return string represntation of the question
	 */
	@Override
	public String getQuestion() {
		return "if f(x) = " + mf + " then what is f'(" + input + ")";
	}

	/**
	 * 
	 * @param answer
	 *            the proposed answer to the problem
	 * @return a boolean answer that is true if the proposed answer is within the
	 *         threshold of the true answer
	 */
	public boolean verifyAnswer(Double answer) {
		if (getAnswer() == 0) {
			return Math.abs((getAnswer() - answer)) < threshold;
		} else {
			return (Math.abs((1 - getAnswer() / answer)) < threshold);
		}
	}

	/**
	 * 
	 * @return is unsolvable
	 */
	public boolean isUnsolvable() {
		return ((new Double(getAnswer()).equals(Double.NaN))
				|| (new Double(getAnswer()).equals(Double.POSITIVE_INFINITY))
				|| (new Double(getAnswer()).equals(Double.NEGATIVE_INFINITY)));
	}

	/**
	 * @return the answer to the question
	 */
	@Override
	public Double getAnswer() {
		if (mf.getDerivative().equals(null)) {
			System.out.println(
					"Rip rip rip, bug bug bug !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("WHO DECIDED TO ADD AN OPERATION WITHOUT DEFINING ITS DERIVATIVE");
			return 420.0;
		}
		return mf.getDerivative().apply(input);
	}

	/**
	 * @return question and answer
	 */
	public String toString() {
		return "if f(x) = " + mf + " then f'(" + input + ") = " + getAnswer();
	}

	public static MathProblem randomProblem(int averageDepth, int maxNumberSize) {
		Random r = new Random();
		return new DerivativeProblem(NestingMathFunction.randomMathFunction(averageDepth, maxNumberSize),
				r.nextInt(maxNumberSize) - maxNumberSize);
	}

}
