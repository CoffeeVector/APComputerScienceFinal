package polynomial;

import java.util.TreeMap;

import math.MathExpression;

public class PolynomialExpression extends MathExpression {
	private TreeMap<Double, Double> coefficients;// degree to coefficientF

	public PolynomialExpression(TreeMap<Double, Double> h) {
		coefficients = h;
	}

	public PolynomialExpression() {
		coefficients = new TreeMap<Double, Double>();
	}

	public void addCoefficient(double coefficient, double degree) {
		if (coefficients.get(degree) == null) {
			coefficients.put(degree, coefficient);
		} else {
			coefficients.put(degree, coefficient + coefficients.get(degree));
		}
	}

	@Override
	public Double apply(Double x) {
		double out = 0;
		for (Double degree : coefficients.keySet()) {
			out += Math.pow(x, degree) * coefficients.get(degree);
		}
		return out;
	}

	public TreeMap<Double, Double> getCoefficients() {
		return coefficients;
	}

	@Override
	public MathExpression getDerivative() {
		return getDerivative(1);
	}

	@Override
	public MathExpression getDerivative(int depth) {
		PolynomialExpression out = new PolynomialExpression();
		if (depth >= 1) {
			for (Double degree : coefficients.keySet()) {
				double coefficient = coefficients.get(degree);
				for (int i = 0; i < depth; i++) {
					coefficient *= degree - i;
				}
				out.getCoefficients().put(degree - depth, coefficient);
				// out.setCoefficient(coefficient, degree - depth);
			}
		} else if (depth <= -1) {

		} else {
			return this;
		}
		return out;
	}

	public String toString() {
		String out = "";
		for (Double degree : coefficients.keySet()) {
			if (coefficients.get(degree).doubleValue() != 0.0) {
				if (coefficients.get(degree).doubleValue() == 1) {
					out += "";
				} else if (coefficients.get(degree).doubleValue() == Math
						.floor(coefficients.get(degree).doubleValue())) {
					out += "" + (int) coefficients.get(degree).doubleValue();
				} else {
					out += "" + coefficients.get(degree);
				}

				if (coefficients.get(degree).doubleValue() == 1 && degree.doubleValue() == 0.0) {
					out += " 1 ";
				}
				if (degree.doubleValue() == 0.0) {
					out += " + ";
				} else if (degree.doubleValue() == 1.0) {
					out += "x + ";
				} else if (degree.doubleValue() == Math.floor(degree.doubleValue())) {
					out += "x^" + (int) degree.doubleValue() + " + ";
				} else {
					out += "x^" + degree + " + ";
				}
			} else {
				out += " + ";
			}
		}
		if (out.charAt(1) == '+') {
			return out.substring(2, out.length() - 1);
		} else {
			return out.substring(0, out.length() - 1 - 2);
		}
	}

	private static int factorial(int n) {
		int out = 1;
		for (int i = 0; i < n; i++) {
			out *= i + 1;
		}
		return out;
	}

	public static PolynomialExpression sin(int terms) {
		PolynomialExpression out = new PolynomialExpression();
		for (int i = 0; i < terms; i++) {
			out.getCoefficients().put((i * 2 + 1.0), Math.pow(-1.0, i) / factorial(i * 2 + 1));
		}
		return out;
	}

	public static PolynomialExpression addition(PolynomialExpression a, PolynomialExpression b) {
		PolynomialExpression out = new PolynomialExpression(a.getCoefficients());
		for (Double degree : b.getCoefficients().keySet()) {
			out.addCoefficient(b.getCoefficients().get(degree), degree);
		}
		return out;
	}

	public static PolynomialExpression multiplication(PolynomialExpression a, PolynomialExpression b) {
		PolynomialExpression out = new PolynomialExpression();
		for (Double d1 : b.getCoefficients().keySet()) {
			for (Double d2 : a.getCoefficients().keySet()) {
				out.addCoefficient(b.getCoefficients().get(d1) * a.getCoefficients().get(d2), d1 + d2);
			}
		}
		return out;
	}
}
