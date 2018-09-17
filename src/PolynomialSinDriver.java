import polynomial.PolynomialExpression;

public class PolynomialSinDriver {
	public static void main(String[] args) {
		PolynomialExpression sinApproximation = PolynomialExpression.sin(10);
		System.out.println(sinApproximation.getCoefficients());
	}
}
