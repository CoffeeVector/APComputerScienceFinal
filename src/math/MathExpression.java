package math;

import java.util.function.Function;

public abstract class MathExpression implements Function<Double, Double> {

	public abstract MathExpression getDerivative();

	public abstract MathExpression getDerivative(int depth);
}
