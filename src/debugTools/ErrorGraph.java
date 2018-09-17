package debugTools;

import java.util.ArrayList;
import java.util.function.Function;

public class ErrorGraph implements Function<Double, Double> {
	private class Point {
		private double x;
		private double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public void setX(double x) {
			this.x = x;
		}

		public void setY(double y) {
			this.y = y;
		}

		public Point clone() {
			return new Point(x, y);
		}
	}

	private ArrayList<Point> allPoints;

	public ErrorGraph() {
		allPoints = new ArrayList<Point>();
	}

	private int search(Point p) {
		for (int i = 0; i < allPoints.size(); i++) {
			if (p.getX() == allPoints.get(i).getX()) {
				return i;
			}
		}
		return -1;
	}

	private void add(Point p) {
		int index = search(p);
		if (index != -1) {
			allPoints.get(index).setX((allPoints.get(index).getX() + p.getX()) / 2);
			allPoints.get(index).setY((allPoints.get(index).getY() + p.getY()) / 2);
		}
	}

	public void add(double x, double y) {
		add(new Point(x, y));
	}

	public Double apply(Double t) {
		return null;
	}

}
