// TG Lab Assignment 03 Exercise 03
// Referencing DiskSpace.java in Week 5 Examples

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Lab03Ex03 {
	static void main() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the starting location: ");
		String path = scanner.nextLine();
		System.out.print("Enter the file name to count entries: ");
		System.out.print(find(new File(path), scanner.nextLine()) + " total matches found.");
	}

	// Recursive algorithm to locate matching file names in a directory and its subdirectories
	public static int find(File path, String fileName) {
		int count = 0;
		if (path.getName().equals(fileName)) {
			System.out.println("File name match: " + path.getAbsolutePath());
			count++;
		}

		// Lists directories contents and uses recursion to verify files and open subdirectories 
		if (path.isDirectory()) {
			for (File file : Objects.requireNonNull(path.listFiles())) {
				count += find(file, fileName);
			}
			return count;
		}
		return count;
	}
}
