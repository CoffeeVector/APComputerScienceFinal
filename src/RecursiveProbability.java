import java.util.Random;

public class RecursiveProbability {

	private static Random r = new Random();

	public static int getNumber(int average) {
		return (r.nextInt(average) == 0) ? 1 : (getNumber(average) + 1);
	}

	public static void main(String[] args) {
		double average = 0;
		int count = 0;
		for (int v = 0; v < 10000; v++) {

			++count;
			average = average * (count - 1) / (count);
			average += 1.0 * getNumber(100) / count;

		}
		System.out.println("_average" + average);

	}
}
