import math.RandomMathProblemGenerator;

public class MathDriver {
	/**
	 * Main Method
	 * 
	 * @param args
	 *            commandline arguements
	 */
	public static void main(String[] args) {
		System.out.println(new RandomMathProblemGenerator(5, 5).getNext());
	}
}
