// TG Lab Assignment 02 Exercise 01

public class Lab02Ex01 {
	static void main() {

		int[] test = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		int[] testTwo = { 55, 55, 55, 55, 55, 55, 55, 55, 55, 55 };
		System.out.print("\nExercise One: " + ExercisesOne.example1(test));
		System.out.print(", Exercise Two: " + ExercisesOne.example2(test));
		System.out.print(", Exercise Three: " + ExercisesOne.example3(test));
		System.out.print(", Exercise Four: " + ExercisesOne.example4(test));
		System.out.println(", Exercise Five: " + ExercisesOne.example5(test, testTwo));
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