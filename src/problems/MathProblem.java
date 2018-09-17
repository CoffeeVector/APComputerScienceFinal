package problems;

/**
 * Class that represents all MathProblems
 *
 */
public abstract class MathProblem {
	/**
	 * Getter for the question
	 * 
	 * @return the question
	 */
	public abstract String getQuestion();

	/**
	 * Verification method for the answer
	 * 
	 * @return whether the player answers the question correctly
	 */
	public abstract boolean verifyAnswer(Double answer);

	/**
	 * Verification method to ensure that the question is solvable
	 * 
	 * @return true of the question is unsolvable. i.e. NaN -Infinity +Infinity.
	 */
	public abstract boolean isUnsolvable();

	/**
	 * Getter for the answer
	 * 
	 * @return the answer.
	 */
	public abstract Double getAnswer();

	/**
	 * toString method for development
	 * 
	 * @return a string representation of the question
	 */
	public abstract String toString();

	/**
	 * Generates random Problem whose complexity matches that of the parameters
	 * 
	 * @param averageDepth
	 *            average depth of the random math problem
	 * @param maxNumberSize
	 *            max size of the numbers in the math problem
	 * @return random math problem
	 */
	public static MathProblem randomProblem(int averageDepth, int maxNumberSize) {
		return null;
	}

}
