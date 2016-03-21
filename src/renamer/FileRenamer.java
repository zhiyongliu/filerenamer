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

	private static void rename(String source, String target) throws IOException {
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
					Paths.get(target + s + extension),
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
		scanner.close();
		rename(source, source + "/output/");
	}
}