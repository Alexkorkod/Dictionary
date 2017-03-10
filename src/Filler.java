import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Ganitsky
 * Date: 12.11.12
 * Time: 22:55
 */

class Filler {
    private Scanner mainInputScanner;
    private List<String> listGlobal;
    private File inputFile;
    private File themeDir;

    Filler(File input) {
        inputFile = input;
        listGlobal = new ArrayList<String>();
        try {
            processInput();
        } catch (IOException ioex) {
            System.exit(10);
        }
    }

    Filler() {
        listGlobal = new ArrayList<String>();
    }

    void setTheme(File theme) {
        themeDir = theme;
    }

    void remember() {
        remember(themeDir);
    }

    private void processInput() throws IOException {
        if (inputFile != null && inputFile.exists() && !inputFile.isDirectory()) {
            mainInputScanner = new Scanner(inputFile);
            //List of words in line
            String[] tmpForIn;
            FileWriter flWr;
            boolean doesExist;

            while (mainInputScanner.hasNextLine()) {
                tmpForIn = mainInputScanner.nextLine().split(" ");

                for (int i = 0; i < tmpForIn.length; i++) {

                    /* if (tmpForIn[i].equals("?")) {
                        tmpForIn[i] = "\?";
                    } */

                    doesExist = false;
                    FileOutputStream outputStream = null;

                    //Checking if this word already exist.
                    if (getListGlobal().contains(tmpForIn[i])) {
                        doesExist = true;
                    } else {
                        //Writing words to list.
                        getListGlobal().add(tmpForIn[i]);
                    }

                    //Creating a new file for non-existing words
                    if ((!doesExist)) {
                        //If we are going to add some new words, we need to uncomment the string below
                        outputStream = new FileOutputStream(themeDir.getName() + "\\" + tmpForIn[i] + ".txt", true);
                    }

                    flWr = new FileWriter(themeDir.getName() + "\\" + tmpForIn[i] + ".txt", true);

                    //Updating files with connections
                    if ((i != tmpForIn.length - 1)) {
                        flWr.write(tmpForIn[i + 1]);
                        flWr.write(" ");
                    }

                    streamCloser(flWr, outputStream);
                }
            }
        }
    }

    private FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt");
        }
    };

    //Here we parse all the text files in directory
    private void remember(File themeDir) {
        if (themeDir.exists()) {
            String[] memArr = themeDir.list(filter);
            if (memArr != null) {
                for (String aMemArr : memArr) {
                    if (aMemArr.length() > 4) {
                        listGlobal.add(aMemArr.substring(0, aMemArr.length() - 4));
                    }
                }
            }
        } else {
            System.exit(1);
        }
    }

    List<String> getListGlobal() {
        return listGlobal;
    }


    ArrayList<String> getConnectedWords(String neededWord, File themeDir) throws FileNotFoundException {
        File neededWordFile = new File(themeDir.getName() + File.separator + neededWord + ".txt");

        if (neededWordFile.exists()) {
            mainInputScanner = new Scanner(neededWordFile);
            ArrayList<String> returnedArr = new ArrayList<String>();
            while (mainInputScanner.hasNext()) {
                returnedArr.add(mainInputScanner.next());
            }
            return returnedArr;
        } else {
            return null;
        }

    }

    private void streamCloser(FileWriter flWr, FileOutputStream outputStream) throws IOException {
        if (flWr != null) {
            flWr.close();
        }

        if (outputStream != null) {
            outputStream.close();
        }
    }

    //Just a logic template.

    private void checkPunct(String[] inputWord, int iterator) {
        char punctChecker = inputWord[iterator].toCharArray()[inputWord.length - 1];
        switch (punctChecker) {
            case '.':
            case ',':
            case ';':
            case '?':

        }

    }

    void setInputFile(File file) {
        if (file.isFile()) {
            inputFile = file;
        } else {
            System.exit(2);
        }
    }
}
