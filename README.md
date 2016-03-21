# filerenamer
Renames files in order of timestamps.  Pads "0"s if necessary.

## Instructions (Macbook):

### Setup:
  1. Spotlight Search to type "Terminal"
  2. Check if Java is installed on your system: type "java -version" (without the quotes)
  3. If you get an error, or if your Java version is not up to date, download and install JDK 8 or later.
  4. After installation/update, type "java -version" (without the quotes) again to see the correct version number.
  5. Download https://github.com/zhiyongliu/filerenamer/blob/master/app/renamer.jar

### Steps:
  1. Spotlight Search to type "Terminal"
  2. To use the app, change directory to where the downloaded jar file is: "cd dir_to_jar" (without the quotes)
  3. Type "java -jar renamer.jar" (without the quotes)
  4. When prompted, provide the ABSOLUTE path of the folder containing all files you want renamed.
  5. Optionally, you can provide a prefix to add in an output file name.  For instance, cn01.png.
  6. Find renamed files in the "output" folder under the ABSOLUTE path you provided in the above step.  If you want to keep them, move them elsewhere now.  If you don't, they will be removed the next time you run the app.