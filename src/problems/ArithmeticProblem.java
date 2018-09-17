package problems;

import math.NestingMathFunction;

/**
 * This class is intended to randomly generate Arithmetic Questions
 * 
 *
 */
public class ArithmeticProblem extends MathProblem {
	private String question;
	private double answer;
	private double threshold = 0.01;

	/**
	 * This constructor is intentionally private because the constructor will not
	 * verify that the question correctly corresponds to the answer Please generate
	 * an ArithmeticProblem using the operation methods and the createConstant
	 * method
	 * 
	 * @param question
	 *            string representation of the question
	 * @param answer
	 *            the answer to the question
	 */
	private ArithmeticProblem(String question, double answer) {
		this.question = question;
		this.answer = answer;
	}

	/**
	 * Getter for the question
	 * 
	 * @return a string representation of the problem
	 */
	public String getQuestion() {
		return "what is " + question;
	}

	/**
	 * Getter for the answer
	 * 
	 * @return the answer to the problem
	 */
	public Double getAnswer() {
		return answer;
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
	 * Method to ensure that the question is solvable. Will return true of the
	 * answer to the question is NaN or either Infinity
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
	 * @return a string representation of the problem with the question AND answer
	 */
	public String toString() {
		return question + " = " + answer;
	}

	/**
	 * Method to generate random ArithmeticProblem
	 * 
	 * @param averageDepth
	 *            average depth of a problem
	 * @param maxNumberSize
	 * @return a random ArithmeticProblem
	 */
	public static ArithmeticProblem randomArithmeticProblem(int averageDepth, int maxNumberSize) {
		NestingMathFunction mf = NestingMathFunction.randomConstantMathFunction(averageDepth, maxNumberSize);
		return new ArithmeticProblem(mf.toString(), mf.apply(new Double(0)));
	}

	/**
	 * This method is a wrapper for the randomArithmeticProblem. Intended for easy
	 * of use for Polyamorphism
	 * 
	 * @param averageDepth
	 * @param maxNumberSize
	 * @return a random MathProblem that is an instanceof ArithmeticProblem
	 */
	public static MathProblem randomProblem(int averageDepth, int maxNumberSize) {
		return randomArithmeticProblem(averageDepth, maxNumberSize);
	}

}
