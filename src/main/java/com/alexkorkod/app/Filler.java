package com.alexkorkod.app;

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
    private Master masterInstance = Master.getInstance();

    Filler(File input) {
        inputFile = input;
        listGlobal = new ArrayList<String>();
        processInput();
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

    private void processInput() {
        if (inputFile != null && inputFile.exists() && !inputFile.isDirectory()) {
            try {
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
            } catch (IOException ioex) {
                masterInstance.getLogger().error(ioex.getMessage());
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
            masterInstance.getLogger().warn("Passed thematic directory does not exists");
        }
    }

    List<String> getListGlobal() {
        return listGlobal;
    }


    ArrayList<String> getConnectedWords(String neededWord, File themeDir) {
        File neededWordFile = new File(themeDir.getName() + File.separator + neededWord + ".txt");
        ArrayList<String> returnedArr = new ArrayList<String>();

        if (neededWordFile.exists()) {
            try {
                mainInputScanner = new Scanner(neededWordFile);
                while (mainInputScanner.hasNext()) {
                    returnedArr.add(mainInputScanner.next());
                }
            } catch (FileNotFoundException fnfex) {
                masterInstance.getLogger().warn("There were no file for next word");
            }
        }
        return returnedArr;
    }

    private void streamCloser(FileWriter flWr, FileOutputStream outputStream) throws IOException {
        if (flWr != null) {
            flWr.close();
        }

        if (outputStream != null) {
            outputStream.close();
        }
    }
}
