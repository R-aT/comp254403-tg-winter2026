// TG Lab Assignment 02 Exercise 02

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Lab02Ex02 {
	static void main() {

		// Exercise 2
		JFrame frame = new JFrame("Exercise Two Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.add(new ExTwoVisual());
		frame.setVisible(true);
	}
}

class ExTwoVisual extends JPanel {

	private final List<Double> logN = new ArrayList<>();
	private final List<Double> logOne = new ArrayList<>();
	private final List<Double> logTwo = new ArrayList<>();

	public ExTwoVisual() {

		for (int i = 50000; i < 600000; i *= 1.5) {
			double[] randomDoubles = new Random().doubles(i, 0.0, 10.0).toArray();
			// System.out.println(Arrays.toString(randomDoubles));

			long startTime = System.nanoTime();
			double[] storage = PrefixAverage.prefixAverage1(randomDoubles);
			long firstTime = System.nanoTime() - startTime;

			startTime = System.nanoTime();
			storage = PrefixAverage.prefixAverage2(randomDoubles);
			long secondTime = System.nanoTime() - startTime;

			if (i > 50000) {

				logN.add(Math.log10(i));
				logOne.add(Math.log10(firstTime));
				logTwo.add(Math.log10(secondTime));
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int padding = 20;
		int w = getWidth() - 2 * padding;
		int h = getHeight() - 2 * padding;

		double minX = Collections.min(logN);
		double maxX = Collections.max(logN);
		double minY = Math.min(Collections.min(logOne),
				Collections.min(logTwo));
		double maxY = Math.max(Collections.max(logOne),
				Collections.max(logTwo));

		// Draw x/y lines
		g2.drawLine(padding, padding + h,
				padding + w, padding + h);
		g2.drawLine(padding, padding,
				padding, padding + h);

		// Data lines
		g2.setColor(Color.RED);
		drawSeries(g2, logN, logOne, padding, w, h,
				minX, maxX, minY, maxY);

		g2.setColor(Color.BLUE);
		drawSeries(g2, logN, logTwo, padding, w, h,
				minX, maxX, minY, maxY);

		drawLegend(g2);
	}

	private void drawSeries(Graphics2D g2,
							List<Double> xData,
							List<Double> yData,
							int padding, int w, int h,
							double minX, double maxX,
							double minY, double maxY) {

		for (int i = 0; i < xData.size() - 1; i++) {

			int x1 = padding + (int) ((xData.get(i) - minX)
					/ (maxX - minX) * w);

			int y1 = padding + h - (int) ((yData.get(i) - minY)
					/ (maxY - minY) * h);

			int x2 = padding + (int) ((xData.get(i + 1) - minX)
					/ (maxX - minX) * w);

			int y2 = padding + h - (int) ((yData.get(i + 1) - minY)
					/ (maxY - minY) * h);

			g2.drawLine(x1, y1, x2, y2);
			g2.fillOval(x1 - 2, y1 - 2, 4, 4);
			g2.fillOval(x2 - 2, y2 - 2, 4, 4);
		}
	}

	private void drawLegend(Graphics2D g2) {

		int x = 30;
		int y = 60;

		g2.setColor(Color.RED);
		g2.fillRect(x, y, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawString("prefixAverage1 - O(n^2)", x + 25, y + 12);

		g2.setColor(Color.BLUE);
		g2.fillRect(x, y + 25, 15, 15);
		g2.setColor(Color.BLACK);
		g2.drawString("prefixAverage2 - O(n)", x + 25, y + 37);
	}
}


class PrefixAverage {

	/**
	 * Returns an array a such that, for all j, a[j] equals the average of x[0], ..., x[j].
	 */
	public static double[] prefixAverage1(double[] x) {
		int n = x.length;
		double[] a = new double[ n ];    // filled with zeros by default
		for (int j = 0; j < n; j++) {
			double total = 0;            // begin computing x[0] + ... + x[j]
			for (int i = 0; i <= j; i++)
				total += x[ i ];
			a[ j ] = total / (j + 1);        // record the average
		}
		return a;
	}
	// Similar to ExerciseOne.3 the sum is calculated each time, reducing efficiency. O(n^2)

	/**
	 * Returns an array a such that, for all j, a[j] equals the average of x[0], ..., x[j].
	 */
	public static double[] prefixAverage2(double[] x) {
		int n = x.length;
		double[] a = new double[ n ];    // filled with zeros by default
		double total = 0;              // compute prefix sum as x[0] + x[1] + ...
		for (int j = 0; j < n; j++) {
			total += x[ j ];               // update prefix sum to include x[j]
			a[ j ] = total / (j + 1);        // compute average based on current sum
		}
		return a;
	}
	// Eliminates inner loop, improving efficiency O(n)

}