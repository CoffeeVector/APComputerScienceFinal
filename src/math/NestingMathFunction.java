package math;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

/**
 * Class to model a generic mathematical function only single variable functions
 * are current supported
 *
 */
public class NestingMathFunction extends MathExpression {
	private Function<Double, Double> f;
	private NestingMathFunction derivative;// Doing it this way basically means no double derivative questions...
	private String s;
	private boolean isConstant;
	private static Random r = new Random();

	/**
	 * This is to be used to generate a MathFunction who's derivative we have not
	 * defined or is undefinable
	 * 
	 * @param s string representation of the function
	 * @param f Function object that represents the mathematical function
	 */
	private NestingMathFunction(String s, Function<Double, Double> f) {
		this(s, f, false, null);
	}

	/**
	 * This is to be used to generate a MathFunction that is variable
	 * 
	 * @param s          string representation of the function
	 * @param f          Function object that represents the mathematical function
	 * @param derivative a MathFunction that represents the derivative of the this
	 *                   MathFunction
	 */
	private NestingMathFunction(String s, Function<Double, Double> f, NestingMathFunction derivative) {
		this(s, f, false, derivative);
	}

	/**
	 * This is to be used to generate a MathFunction that is constant
	 * 
	 * @param s          string representation of the function
	 * @param f          Function object that represents the mathematical function
	 * @param isConstant whether this MathFunction is a constant. This is only for
	 *                   cosmetic reasons for when a derivative is less complicated
	 *                   if certain components are constants
	 * @param derivative a MathFunction that represents the derivative of the this
	 *                   MathFunction
	 */
	private NestingMathFunction(String s, Function<Double, Double> f, boolean isConstant,
			NestingMathFunction derivative) {
		this.s = s;
		this.f = f;
		this.isConstant = isConstant;
		this.derivative = derivative;
	}

	/**
	 * @param d input
	 * @return evaluation of the function
	 */
	public Double apply(Double d) {
		return f.apply(d);
	}

	/**
	 * 
	 * @return the derivative of this MathFunction
	 */
	public NestingMathFunction getDerivative() {
		return derivative;
	}

	/**
	 * 
	 * @return isConstant
	 */
	public boolean isConstant() {
		return isConstant;
	}

	/**
	 * @return String s
	 */
	public String toString() {
		return s;
	}

	/**
	 * 
	 * @return MathFunction where apply(d) = d and s = d and who's derivative is a
	 *         MathFunction constant 1
	 */
	public static NestingMathFunction createVariable() {
		return new NestingMathFunction(new Character('x').toString(), (d) -> (d),
				new NestingMathFunction("1", (d) -> 0.0));
	}

	/**
	 * 
	 * @param d1 value of constant
	 * @return MathFunction where apply always returns the parameter given and s = d
	 *         and who's derivative is a MathFunction constant 0
	 */
	public static NestingMathFunction createConstant(double d) {
		return new NestingMathFunction(new Double(d).toString(), new Function<Double, Double>() {
			public Double apply(Double a) {
				return new Double(d);
			}
		}, true, new NestingMathFunction("0", new Function<Double, Double>() {
			public Double apply(Double a) {
				return 0.0;
			}
		}));
	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction who's apply is the sum of first and second
	 *         MathFunction's apply, and who's derivative is the sum of the first
	 *         two MathFunction's derivative
	 */
	public static NestingMathFunction addition(NestingMathFunction a, NestingMathFunction b) {
		if (a.getDerivative().equals(null) || b.getDerivative().equals(null)) {
			return new NestingMathFunction("" + a.toString() + " + " + b.toString(), (d) -> (a.apply(d) + b.apply(d)));
		}
		return new NestingMathFunction("" + a.toString() + " + " + b.toString(), (d) -> (a.apply(d) + b.apply(d)),
				(a.isConstant() == b.isConstant() == true),
				new NestingMathFunction("" + a.getDerivative().toString() + " + " + b.getDerivative().toString(),
						(d) -> (a.getDerivative().apply(d) + b.getDerivative().apply(d))));
	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction who's apply is the differance of first and second
	 *         MathFunction's apply, and who's derivative is the differance of the
	 *         first two MathFunction's derivative
	 */
	public static NestingMathFunction subtraction(NestingMathFunction a, NestingMathFunction b) {
		if (a.getDerivative().equals(null) || b.getDerivative().equals(null)) {
			return new NestingMathFunction("( " + a.toString() + " ) - ( " + b.toString() + " )",
					(d) -> (a.apply(d) - b.apply(d)));
		}
		return new NestingMathFunction("( " + a.toString() + " ) - ( " + b.toString() + " )",
				(d) -> a.apply(d) - b.apply(d), (a.isConstant() == b.isConstant() == true),
				new NestingMathFunction("(" + a.getDerivative().toString() + " - " + b.getDerivative().toString() + ")",
						(d) -> (a.getDerivative().apply(d) - b.getDerivative().apply(d))));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction who's apply is the product of first and second
	 *         MathFunction's apply, and who's derivative is the product rule of the
	 *         first two MathFunction's derivative
	 */
	public static NestingMathFunction multiplication(NestingMathFunction a, NestingMathFunction b) {
		if (a.getDerivative().equals(null) || b.getDerivative().equals(null)) {
			return new NestingMathFunction("( " + a.toString() + " ) * ( " + b.toString() + " )",
					(d) -> (a.apply(d) * b.apply(d)));
		}
		return new NestingMathFunction("( " + a.toString() + " ) * ( " + b.toString() + " )",
				(d) -> (a.apply(d) * b.apply(d)), (a.isConstant() == b.isConstant() == true),
				new NestingMathFunction(
						"(" + a.toString() + " * " + b.getDerivative().toString() + ") + (" + b.toString() + " * "
								+ a.getDerivative().toString() + ")",
						(d) -> a.apply(d) * b.getDerivative().apply(d) + b.apply(d) * a.getDerivative().apply(d)));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction who's apply is the quotient of first and second
	 *         MathFunction's apply, and who's derivative is the quotient rule of
	 *         the first two MathFunction's derivative
	 */
	public static NestingMathFunction division(NestingMathFunction a, NestingMathFunction b) {
		if (a.getDerivative().equals(null) || b.getDerivative().equals(null)) {
			return new NestingMathFunction("( " + a.toString() + " ) / ( " + b.toString() + " )",
					(d) -> (a.apply(d) / b.apply(d)), null);

		}
		return new NestingMathFunction("( " + a.toString() + " ) / ( " + b.toString() + " )",
				(d) -> (a.apply(d) / b.apply(d)), (a.isConstant() == b.isConstant() == true),
				new NestingMathFunction(
						"( ( (" + a.getDerivative() + ") * ( " + b + ") ) - ( (" + b.getDerivative() + ") * (" + a
								+ ") )" + " ) / ( " + b + " ^ 2 )",
						(d) -> (a.getDerivative().apply(d) * b.apply(d) - a.apply(d) * b.getDerivative().apply(d))
								/ (Math.pow(b.apply(d), 2))));
	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction who's apply is the power of first and second
	 *         MathFunction's apply. Although it may seem natural to simply put the
	 *         power rule here, it does not apply of MathFunction is not a constant.
	 *         Although we can use it while b is a constant, it would only be used
	 *         as for cosmetic reasons A proof for the general form of the product
	 *         rule can be found in documentation.
	 * 
	 */
	public static NestingMathFunction pow(NestingMathFunction a, NestingMathFunction b) {
		if (a.getDerivative().equals(null) || b.getDerivative().equals(null)) {
			return new NestingMathFunction("( " + a.toString() + " ) ^ ( " + b.toString() + " )",
					(d) -> Math.pow(a.apply(d), b.apply(d)), null);
		}

		String derivativeOutputString = null;
		if (b.isConstant()) {
			derivativeOutputString = "( " + b + " ) * ( ( " + a + " ) ^ ( " + b + " - 1 ) )";
		} else {
			derivativeOutputString = "(" + a + " ^ " + b + ") * ((" + b + ") / (" + a + ") * (" + a.getDerivative()
					+ ") + (" + b.getDerivative() + ") * ( ln( " + a + " ) ))";
		}
		return new NestingMathFunction("( " + a.toString() + " ) ^ ( " + b.toString() + " )",
				(d) -> Math.pow(a.apply(d), b.apply(d)), (a.isConstant() == b.isConstant() == true),
				new NestingMathFunction(derivativeOutputString,
						(d) -> (Math.pow(a.apply(d), b.apply(d)))
								* (b.apply(d) / a.apply(d) * a.getDerivative().apply(d)
										+ b.getDerivative().apply(d) * Math.log(a.apply(d)))));
	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction generated from a random binary operation
	 */
	public static NestingMathFunction randomBinaryOperation(NestingMathFunction a, NestingMathFunction b) {
		Random rand = new Random();
		try {
			return (NestingMathFunction) (binaryOperationMethods()[rand.nextInt(binaryOperationCount())]).invoke(null,
					a, b);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param a first MathFunction
	 * 
	 * @return a MathFunction generated from the natural log MathFunction a
	 */
	public static NestingMathFunction ln(NestingMathFunction a) {
		return new NestingMathFunction("ln( " + a.toString() + " )", (d) -> (Math.log(a.apply(d))), (a.isConstant()),
				new NestingMathFunction("( 1 / ( " + a + ") * ( " + a.getDerivative() + " ) )",
						(d) -> 1.0 / a.apply(d) * a.getDerivative().apply(d)));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @return a MathFunciton generated from e (Euler's number) to the power of
	 *         MathFunction a
	 */
	public static NestingMathFunction exp(NestingMathFunction a) {
		return new NestingMathFunction("e ^ ( " + a.toString() + " )", (d) -> Math.exp(a.apply(d)), (a.isConstant()),
				new NestingMathFunction("( e ^ ( " + a + ") * ( " + a.getDerivative() + " ) )",
						(d) -> Math.exp(a.apply(d)) * a.getDerivative().apply(d)));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @return a MathFunction generated from sin of a
	 */
	public static NestingMathFunction sin(NestingMathFunction a) {
		return new NestingMathFunction("sin( " + a.toString() + " )", (d) -> Math.sin(a.apply(d)), (a.isConstant()),
				new NestingMathFunction("( cos( " + a + ") * ( " + a.getDerivative() + " ) )",
						(d) -> Math.cos(a.apply(d)) * a.getDerivative().apply(d)));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @return a MathFunction generated from cos of a
	 */
	public static NestingMathFunction cos(NestingMathFunction a) {
		return new NestingMathFunction("cos( " + a.toString() + " )", (d) -> Math.cos(a.apply(d)), (a.isConstant()),
				new NestingMathFunction("( -sin( " + a + ") * ( " + a.getDerivative() + " ) )",
						(d) -> -1.0 * Math.sin(a.apply(d)) * a.getDerivative().apply(d)));

	}

	/**
	 * 
	 * @param a first MathFunction
	 * @return a MathFunction generated from tan of a
	 */
	public static NestingMathFunction tan(NestingMathFunction a) {
		return new NestingMathFunction("tan( " + a.toString() + " )", (d) -> Math.tan(a.apply(d)), (a.isConstant()),
				new NestingMathFunction("( 1 / cos( " + a + ") / cos( " + a + ") * ( " + a.getDerivative() + " ) )",
						(d) -> 1.0 / Math.cos(a.apply(d)) / Math.cos(a.apply(d)) * a.getDerivative().apply(d)));
	}

	/**
	 * 
	 * @param a first MathFunction
	 * @param b second MathFunction
	 * @return a MathFunction generated from a random binary operation
	 */
	public static NestingMathFunction randomUnaryOperation(NestingMathFunction a) {
		Random rand = new Random();

		try {
			return (NestingMathFunction) (unaryOperationMethods()[rand.nextInt(unaryOperationCount())]).invoke(null, a);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
		// switch (rand.nextInt(unaryOperationCount())) {
		// case 0:
		// return ln(a);
		// default:
		// System.out.println("Operation not implemented, bug!");
		// return null;
		// }
	}

	/**
	 * 
	 * @param maxDepth      average depth of the function
	 * @param maxNumberSize max number size of the function (positive and negative)
	 * @return randomized MathFunction
	 */
	public static NestingMathFunction randomMathFunction(int maxDepth, int maxNumberSize) {
		// TODO allow maxDepth to be decimal
		return ((r.nextInt(2) == 0)
				? (NestingMathFunction
						.randomBinaryOperation(
								((r.nextInt(maxDepth) == 0)
										? ((r.nextInt(2) == 0)
												? NestingMathFunction
														.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
												: NestingMathFunction.createVariable())
										: NestingMathFunction.randomMathFunction(maxDepth - 1, maxNumberSize)),
								((r.nextInt(maxDepth) == 0)
										? ((r.nextInt(2) == 0)
												? NestingMathFunction
														.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
												: NestingMathFunction.createVariable())
										: NestingMathFunction.randomMathFunction(maxDepth - 1, maxNumberSize))))
				: (NestingMathFunction
						.randomUnaryOperation(((r.nextInt(maxDepth) == 0)
								? ((r.nextInt(2) == 0)
										? NestingMathFunction
												.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
										: NestingMathFunction.createVariable())
								: NestingMathFunction.randomMathFunction(maxDepth - 1, maxNumberSize)))));
	}

	public static NestingMathFunction _randomMathFunction(int averageDepth, int maxNumberSize) {
		// TODO allow averageDepth to be decimal
		return ((r.nextInt(2) == 0)
				? (NestingMathFunction
						.randomBinaryOperation(
								((r.nextInt(averageDepth) == 0)
										? ((r.nextInt(2) == 0)
												? NestingMathFunction
														.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
												: NestingMathFunction.createVariable())
										: NestingMathFunction._randomMathFunction(averageDepth, maxNumberSize)),
								((r.nextInt(averageDepth) == 0)
										? ((r.nextInt(2) == 0)
												? NestingMathFunction
														.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
												: NestingMathFunction.createVariable())
										: NestingMathFunction._randomMathFunction(averageDepth, maxNumberSize))))
				: (NestingMathFunction
						.randomUnaryOperation(((r.nextInt(averageDepth) == 0)
								? ((r.nextInt(2) == 0)
										? NestingMathFunction
												.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
										: NestingMathFunction.createVariable())
								: NestingMathFunction._randomMathFunction(averageDepth, maxNumberSize)))));
	}

	/**
	 * 
	 * @param averageDepth  average depth of the function
	 * @param maxNumberSize max number size of the function (positive and negative)
	 * @return randomized MathFunction
	 */
	public static NestingMathFunction randomConstantMathFunction(int averageDepth, int maxNumberSize) {
		return ((r.nextInt(2) == 0)
				? (NestingMathFunction.randomBinaryOperation(
						((r.nextInt(averageDepth) == 0) ? NestingMathFunction
								.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
								: NestingMathFunction.randomConstantMathFunction(averageDepth - 1, maxNumberSize)),
						((r.nextInt(averageDepth) == 0)
								? NestingMathFunction.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
								: NestingMathFunction.randomConstantMathFunction(averageDepth - 1, maxNumberSize))))
				: (NestingMathFunction.randomUnaryOperation(((r.nextInt(averageDepth) == 0)
						? NestingMathFunction.createConstant(r.nextInt(maxNumberSize * 2) - maxNumberSize)
						: NestingMathFunction.randomConstantMathFunction(averageDepth - 1, maxNumberSize)))));
	}

	/**
	 * 
	 * @return the number of binary operations available in the MathFunction class
	 *         not including the randomBinaryOperation
	 */
	public static int binaryOperationCount() {
		int counter = 0;
		for (Method m : NestingMathFunction.class.getMethods()) {
			if (!m.getName().equals("randomBinaryOperation")) {
				if (Arrays.equals(m.getParameterTypes(),
						new Class[] { NestingMathFunction.class, NestingMathFunction.class })) {
					++counter;
				}
			}
		}
		return counter;// The minus one os si we don't count the randomBinaryOperation

	}

	/**
	 * 
	 * @return an Array of binary operations available in the MathFunction class
	 */
	public static Method[] binaryOperationMethods() {
		int index = 0;
		Method[] out = new Method[binaryOperationCount()];
		for (Method m : NestingMathFunction.class.getMethods()) {
			if (!m.getName().equals("randomBinaryOperation")) {
				if (Arrays.equals(m.getParameterTypes(),
						new Class[] { NestingMathFunction.class, NestingMathFunction.class })) {
					out[index++] = m;
				}
			}
		}
		return out;
	}

	/**
	 * 
	 * @return the number of unary operations available in the MathFunction class
	 */
	public static int unaryOperationCount() {
		int counter = 0;
		for (Method m : NestingMathFunction.class.getMethods()) {
			if (!m.getName().equals("randomUnaryOperation")) {
				if (Arrays.equals(m.getParameterTypes(), new Class[] { NestingMathFunction.class })) {
					++counter;
				}
			}
		}
		return counter;// The minus one is so we don't count the randomUnaryOperation method
	}

	/**
	 * The method works by first creating an Array of Methods with the size of
	 * unaryOperationCount. It would then add the method if and only of the method a
	 * single MathFunction parameter unless if the method's name is
	 * randomUnaryOperation
	 * 
	 * @return an Array of unary operations available in the MathFunction class
	 */
	public static Method[] unaryOperationMethods() {
		int index = 0;
		Method[] out = new Method[unaryOperationCount()];
		for (Method m : NestingMathFunction.class.getMethods()) {
			if (!m.getName().equals("randomUnaryOperation")) {
				if (Arrays.equals(m.getParameterTypes(), new Class[] { NestingMathFunction.class })) {
					out[index++] = m;
				}
			}
		}
		return out;
	}

	/**
	 * 
	 * @return a 2 dimensional array of all the operation methods
	 */
	public static Method[][] operationMethods() {
		return new Method[][] { binaryOperationMethods(), unaryOperationMethods() };
	}

	@Override
	public MathExpression getDerivative(int depth) {
		// TODO Can only compute the derivatives during instanciation of the object,
		// therefore it is not feasible generate an abitrary depth of derivatives
		return null;
	}

}
