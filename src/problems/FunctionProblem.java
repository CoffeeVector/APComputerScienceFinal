package problems;

import java.util.Random;

import math.NestingMathFunction;

/**
 * The Class represents a Function Problem. The MathFunction given in the
 * constructor is the function given as the question, and the user is expected
 * to evaluate the output of the function with a give input
 *
 */
public class FunctionProblem extends MathProblem {
	private double input;
	private double threshold = 0.01;
	private NestingMathFunction mf;

	/**
	 * Construcst a FunctionProblem who's function is MathFunction mf and who's
	 * input is double input.
	 * 
	 * @param mf
	 *            MathFunction in question
	 * @param input
	 *            input of the function to evaluate
	 */
	public FunctionProblem(NestingMathFunction mf, double input) {
		this.mf = mf;
		this.input = input;
	}

	/**
	 * Getter for the question
	 * 
	 * @return the question
	 */
	public String getQuestion() {
		return "If f(x) = " + mf.toString() + ", then what is f(" + input + ")?";
	}

	/**
	 * Getter for the answer
	 * 
	 * @return the answer
	 */
	@Override
	public Double getAnswer() {
		return mf.apply(input);
	}

	/**
	 * Verification method for the answer
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
	 * Verification method to ensure that the question is solvable. Will return true
	 * if the answer is NaN or either Infinity
	 * 
	 * @return is unsolvable
	 */
	public boolean isUnsolvable() {
		return ((new Double(getAnswer()).equals(Double.NaN))
				|| (new Double(getAnswer()).equals(Double.POSITIVE_INFINITY))
				|| (new Double(getAnswer()).equals(Double.NEGATIVE_INFINITY)));
	}

	/**
	 * toString method for development
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		return "f( x ) = " + getQuestion() + "then " + "f( " + input + " ) = " + getAnswer();
	}

	/**
	 * generates a random FunctionProblem
	 * 
	 * @param averageDepth
	 *            the average depth of the math problem
	 * @param maxNumberSize
	 *            the maximum number size
	 * @return a random MathProblem that is an instanceof FunctionProblem
	 */
	public static MathProblem randomProblem(int averageDepth, int maxNumberSize) {
		Random r = new Random();
		return new FunctionProblem(NestingMathFunction.randomMathFunction(averageDepth, maxNumberSize),
				r.nextInt(maxNumberSize * 2) - maxNumberSize);
	}

}
