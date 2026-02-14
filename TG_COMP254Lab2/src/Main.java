import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;


public class Main {
	static void main() {

		// Exercise 1
		int[] test = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		int[] testTwo = { 55, 55, 55, 55, 55, 55, 55, 55, 55, 55 };
		System.out.print("\nExercise One: " + ExercisesOne.example1(test));
		System.out.print(", Exercise Two: " + ExercisesOne.example2(test));
		System.out.print(", Exercise Three: " + ExercisesOne.example3(test));
		System.out.print(", Exercise Four: " + ExercisesOne.example4(test));
		System.out.println(", Exercise Five: " + ExercisesOne.example5(test, testTwo));

		// Exercise 2


		JFrame frame = new JFrame("Exercise Two Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.add(new ExTwoVisual());
		frame.setVisible(true);


		// Exercise 3
		System.out.print("\n\nRunning Unique One test");
		System.out.println("\nSize of n to run under a minute for unique1: ~" + runUniqueTest(1, 80000, 1000));

		System.out.print("\nRunning Unique Two test");
		System.out.println("\nSize of n to run under a minute for unique2: ~" + runUniqueTest(2, 200000000, 1000));

	}

	public static int runUniqueTest(int u, int l, long cap) {
		long uniqueTime;
		int n = l;

		// Initial test to find an upper bound
		while (true) {
			uniqueTime = uniqueTest(n, u);
			// System.out.println("n = " + n + " | Time: " + uniqueTime + " ms");

			if (uniqueTime > cap) {
				break;
			}
			// Depending on conditions, n can exceed the maximum integer limit
			// Due to requirement of using unique integers in an array there is no way to test above the limit
			if (n >= (Integer.MAX_VALUE / 2)) {
				System.out.println("Reached maximum integer limit without exceeding cap.");
				return Integer.MAX_VALUE;
			}
			n = (int) (n * 1.5);
		}

		// "Binary search" to find n that runs under a set time (cap) to avoid checking every single n
		int low = n / 2;
		int lowestN = low;

		while (low <= n) {
			int mid = low + (n - low) / 2;

			// Average the time of 3 runs to reduce variability in run time
			long totalTime = 0;
			for (int i = 0; i < 3; i++) {
				totalTime += uniqueTest(mid, u);
			}
			uniqueTime = totalTime / 3;

			// System.out.println("Tested n = " + mid + " | Time: " + uniqueTime + " ms");

			// Standard binary search logic that reduces bounds
			if (uniqueTime <= cap) {
				lowestN = mid;
				low = mid + 1;
			} else {
				n = mid - 1;
			}
			System.out.print(".");

		}

		return lowestN;

	}

	public static long uniqueTest(int n, int uniq) {
		// Reverse order of random integers to force unique2 to sort
		// No duplicates for worst case
		int[] randomInt = IntStream.iterate(n, i -> i - 1).limit(n).toArray();
		long startTime = System.currentTimeMillis();
		if (uniq == 1) {
			Uniqueness.unique1(randomInt);
		} else {
			Uniqueness.unique2(randomInt);
		}
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}
}


// Exercise 01 - Week 04 Example Code
class ExercisesOne {

	/**
	 * Returns the sum of the integers in given array.
	 */
	public static int example1(int[] arr) {
		int n = arr.length, total = 0;
		for (int j = 0; j < n; j++)       // loop from 0 to n-1
			total += arr[ j ];
		return total;
	}
	// Big-Oh characterization: O(n)
	// The loop iterates n times and each loop performs a constant amount of work

	/**
	 * Returns the sum of the integers with even index in given array.
	 */
	public static int example2(int[] arr) {
		int n = arr.length, total = 0;
		for (int j = 0; j < n; j += 2)    // note the increment of 2
			total += arr[ j ];
		return total;
	}
	// Big-Oh characterization: O(n)
	// The loop iterates n/2 times (the 1/2 is ignored (constant factor))
	// The run time is proportional to n

	/**
	 * Returns the sum of the prefix sums of given array.
	 */
	public static int example3(int[] arr) {
		int n = arr.length, total = 0;
		for (int j = 0; j < n; j++)       // loop from 0 to n-1
			for (int k = 0; k <= j; k++)    // loop from 0 to j
				total += arr[ j ];
		return total;
	}
	// Big-Oh characterization: O(n^2)
	// The outer loop iterates n times and the inner loop iterates j+1 times for each iteration of n
	// (inner)total = n * (n+1) / 2 simplifies to O(n^2) (without constant factors)
	// The run time is proportional to the square of n

	/**
	 * Returns the sum of the prefix sums of given array.
	 */
	public static int example4(int[] arr) {
		int n = arr.length, prefix = 0, total = 0;
		for (int j = 0; j < n; j++) {     // loop from 0 to n-1
			prefix += arr[ j ];
			total += prefix;
		}
		return total;
	}
	// Big-Oh characterization: O(n)
	// Same result as prior, but eliminates inner loop so that the total work is proportional to n

	/**
	 * Returns the number of times second array stores sum of prefix sums from first.
	 */
	public static int example5(int[] first, int[] second) { // assume equal-length arrays
		int n = first.length, count = 0;
		for (int i = 0; i < n; i++) {     // loop from 0 to n-1
			int total = 0;
			for (int j = 0; j < n; j++)     // loop from 0 to n-1
				for (int k = 0; k <= j; k++)  // loop from 0 to j
					total += first[ k ];
			if (second[ i ] == total) count++;
		}
		return count;
	}
	// Big-Oh characterization: O(n^3) very inefficient
	// The run time is proportional to the cube of n
	// Example 3 wrapped in another loop
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


class Uniqueness {

	/**
	 * Returns true if there are no duplicate elements in the array.
	 */
	public static boolean unique1(int[] data) {
		int n = data.length;
		for (int j = 0; j < n - 1; j++)
			for (int k = j + 1; k < n; k++)
				if (data[ j ] == data[ k ])
					return false;                    // found duplicate pair
		return true;                           // if we reach this, elements are unique
	}

	/**
	 * Returns true if there are no duplicate elements in the array.
	 */
	public static boolean unique2(int[] data) {
		int n = data.length;
		int[] temp = Arrays.copyOf(data, n);   // make copy of data
		Arrays.sort(temp);                     // and sort the copy
		for (int j = 0; j < n - 1; j++)
			if (temp[ j ] == temp[ j + 1 ])            // check neighboring entries
				return false;                      // found duplicate pair
		return true;                           // if we reach this, elements are unique
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
