package problems;

import java.util.Random;

import math.NestingMathFunction;

/**
 * Class respresents an Integral Problem. Be aware when constructing this
 * object, as it doesn't actually integral the given MathFunction. The question
 * simply the derivative of the given function and the indefinite integral to
 * the question is the original MathFunction
 *
 */
public class IntegralProblem extends MathProblem {
	private NestingMathFunction mf;
	private double left;
	private double right;
	private double threshold = .01;

	/**
	 * Constructs an IntegralProblem whose given question is the derivative of the
	 * MathFunction mf and who's answer is mf(bound1) - mf(bound2)
	 * 
	 * @param mf
	 *            indefinite integral
	 * @param left
	 *            left bound
	 * @param right
	 *            right bound
	 */
	public IntegralProblem(NestingMathFunction mf, double bound1, double bound2) {
		this.mf = mf;
		this.left = bound1;
		this.right = bound2;

	}

	/**
	 * Getter for the question which is ∫( " + mf.getDerivative() + " )dx
	 * 
	 * @return the question
	 */
	@Override
	public String getQuestion() {
		return "what is the ∫( " + mf.getDerivative() + " )dx from the bounds " + left + " to " + right;
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
	 * Verification method to ensure that the question is solvable
	 * 
	 * @return is unsolvable
	 */
	public boolean isUnsolvable() {
		return ((new Double(getAnswer()).equals(Double.NaN))
				|| (new Double(getAnswer()).equals(Double.POSITIVE_INFINITY))
				|| (new Double(getAnswer()).equals(Double.NEGATIVE_INFINITY)));
	}

	/**
	 * Getter for the answer
	 * 
	 * @return the answer
	 */
	@Override
	public Double getAnswer() {
		return mf.apply(right) - mf.apply(left);
	}

	/**
	 * toString method for development
	 * 
	 * @return a string representaiton of the object
	 */
	public String toString() {
		return "∫( " + mf.getDerivative() + " )dx from the bounds " + left + " to " + right + " = " + getAnswer();
	}

	/**
	 * generate a random MathProblem that is an instanceof an IntegralProblem.
	 * 
	 * @param averageDepth
	 *            the depth of the MathFunction
	 * @param maxNumberSize
	 *            the maximum number size
	 * @return a random MathProblem
	 */
	public static MathProblem randomProblem(int averageDepth, int maxNumberSize) {
		Random r = new Random();
		return new IntegralProblem(NestingMathFunction.randomMathFunction(averageDepth, maxNumberSize),
				r.nextInt(maxNumberSize * 2) - maxNumberSize, r.nextInt(maxNumberSize) * 2 - maxNumberSize);
	}

}
