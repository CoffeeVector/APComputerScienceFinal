package stressTesting;

import math.NestingMathFunction;

/**
 * Testing driver for development How deep/complex can a MathFunction go until
 * it gets a Stackoverflow error?
 */
public class DepthStressTester {
	private static void a1() {
		double average = 0;
		int count = 0;
		for (int v = 0; v < 10000; v++) {
			String s = NestingMathFunction.randomMathFunction(5, 30).toString();
			int depth = 0;
			for (char c : s.toCharArray()) {
				if (c == ')') {
					break;
				}
				if (c == '(') {
					++depth;
				}
			}
			++count;
			average = average * (count - 1) / (count);
			average += 1.0 * depth / count;

		}
		System.out.println("average" + average);
	}

	private static void a2() {

		double average = 0;
		int count = 0;
		for (int v = 0; v < 10000; v++) {
			String s = NestingMathFunction._randomMathFunction(10, 30).toString();
			int depth = 0;
			for (char c : s.toCharArray()) {
				if (c == ')') {
					break;
				}
				if (c == '(') {
					++depth;
				}
			}
			++count;
			average = average * (count - 1) / (count);
			average += 1.0 * depth / count;

		}
		System.out.println("_average" + average);

	}

	public static void main(String[] args) {
		// a1();
		a2();
	}
}
