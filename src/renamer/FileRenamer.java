package renamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Renames files in order of create times. The output file names are of the
 * format:
 * 
 * <pre>
 * 01.ext
 * 02.ext
 * ...
 * </pre>
 * 
 * @author zliu
 *
 */
public class FileRenamer {
	private static File[] getAllFilesByTimestamp(String path) {
		File directory = new File(path);
		File[] files = directory.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return Long.valueOf(o1.lastModified()).compareTo(
						o2.lastModified());
			}
		});
		return files;
	}

	private static int getNumberOfDigits(int number) {
		int numberOfDigits = 0;
		while (number > 0) {
			number /= 10;
			numberOfDigits++;
		}
		return numberOfDigits;
	}

	private static String increment(String number) {
		int n = Integer.parseInt(number);
		return Integer.toString(n + 1);
	}

	private static String pad(String number, int digits) {
		int diff = digits - number.length();
		for (int i = 0; i < diff; i++) {
			number = "0" + number;
		}
		return number;
	}

	private static String getFileExtension(String filename) {
		int index = filename.lastIndexOf(".");
		return filename.substring(index);
	}

	public static void rename(String source, String target) throws IOException {
		rename(source, target, "");
	}

	public static void rename(String source, String target, String prefix)
			throws IOException {
		File output = new File(target);
		File[] files = null;
		if (!output.exists()) {
			output.mkdirs();
		} else {
			files = output.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
		files = getAllFilesByTimestamp(source);
		int digits = getNumberOfDigits(files.length);
		String s = pad("1", digits);
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}
			String absolutePath = file.getAbsolutePath();
			String extension = getFileExtension(absolutePath);
			Files.copy(Paths.get(absolutePath),
					Paths.get(target + prefix + s + extension),
					StandardCopyOption.REPLACE_EXISTING);
			s = pad(increment(s), digits);
		}
		files = output.listFiles();
		for (File file : files) {
			if (file.getName().contains("DS_Store")) {
				file.delete();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.print("Folder in which files are to be renamed: ");
		Scanner scanner = new Scanner(System.in);
		String source = scanner.nextLine();
		System.out
				.print("Optionally, what string do you want to use as prefix "
						+ "(e.g., enter cn to produce cn01.png),\n"
						+ "and enter if you don't want any prefix: ");
		String prefix = scanner.nextLine();
		scanner.close();
		if (prefix.trim().isEmpty()) {

		}
		rename(source, source + "/output/", prefix);
	}
}