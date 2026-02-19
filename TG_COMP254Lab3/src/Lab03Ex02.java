// TG Lab Assignment 02 Exercise 02

import java.util.Scanner;

public class Lab03Ex02 {
	static void main() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter a string: ");
			String input = scanner.nextLine();
			// Allow for phrase input, such as "Madam, I'm Adam."
			input = input.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
			System.out.println(input + " is " + (isPalindromeRecursive(input) ? "" : "not ") + "a palindrome.");
		}
	}

	// Recursive algorithm to check if a string is a palindrome
	static Boolean isPalindromeRecursive(String str) {
		// If middle character, or single character
		if (str.length() > 1) {
			// Using hint, checking first and last characters recursively
			if (str.charAt(0) == str.charAt(str.length() - 1)) {
				return isPalindromeRecursive(str.substring(1, str.length() - 1));
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}