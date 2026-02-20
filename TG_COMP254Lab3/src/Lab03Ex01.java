// TG Lab Assignment 03 Exercise 01

public class Lab03Ex01 {

	static void main(String[] args) {
		int numOne = 963;
		int numTwo = 36;
		int product = recursiveProduct(numOne, numTwo);

		System.out.println("The product of " + numOne + " and " + numTwo + " is: " + product);
	}

	// Recursive algorithm to compute the product of two positive integers
	static int recursiveProduct(int j, int k) {
		if (j < 0 || k < 0) {
			throw new IllegalArgumentException("Both integers must be non-negative.");
		}

		// Works through k, adding j to itself each time (5*3 becomes 5 + 5 + 5 + 0)
		if (k == 0) {
			return 0;
		} else {
			return j + recursiveProduct(j, k - 1);
		}
	}

	// linearSum Example from ArraySum

	/**
	 * Returns the sum of the first n integers of the given array.
	 */
	public static int linearSum(int[] data, int n) {
		if (n == 0)
			return 0;
		else
			return linearSum(data, n - 1) + data[ n - 1 ];
	}
}