import polynomial.PolynomialExpression;

public class PolynomialDriver {
	public static void main(String[] args) {
		PolynomialExpression a = new PolynomialExpression();
		a.addCoefficient(1, 1);
		a.addCoefficient(1, 0);
		System.out.println(a);
	}
}
