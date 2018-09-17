package stressTesting;

import java.util.ArrayList;

import debugTools.ANSIColor;
import math.RandomMathProblemGenerator;
import problems.MathProblem;

/**
 * 
 * Testing Driver for development How long are all solvable problems solvable?
 */
public class RandomSolvableProblemStressTest {
	public static void main(String[] args) {
		long timeStamp = System.nanoTime();
		ArrayList<String> failed = new ArrayList<String>();
		for (int v = 0; v < 1000000; v++) {
			RandomMathProblemGenerator rmpg = new RandomMathProblemGenerator(5, 5);

			MathProblem mp = rmpg.getNextSolvable();

			if (mp.isUnsolvable()) {
				failed.add(mp.toString());
				System.out.println(ANSIColor.RED + "failed");
				System.out.println(mp);
				System.out.println(ANSIColor.RESET);
			} else {
				System.out.println(ANSIColor.GREEN + "passed");
			}
		}
		System.out.println(ANSIColor.RED + "failCount: " + failed.size() + ANSIColor.RESET);
		System.out.println(ANSIColor.RED);
		for (String s : failed) {
			System.out.println(s);
		}
		System.out.println(ANSIColor.RESET);
		System.out.println("elapsed time: " + (System.nanoTime() - timeStamp) * 1.0 / 1E9);
	}
}
