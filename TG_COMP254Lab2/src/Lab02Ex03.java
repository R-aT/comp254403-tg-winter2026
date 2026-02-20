import java.util.Arrays;
import java.util.stream.IntStream;

public class Lab02Ex03 {
	static void main() {
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