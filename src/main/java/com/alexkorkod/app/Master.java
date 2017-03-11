package com.alexkorkod.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Master {
    private String phrase;
    private Filler filler;
    private File themeDir;
    //Check for position of current word. We should know what case it should be, don't we?
    private boolean first = true;
    private Logger errLogger = LogManager.getRootLogger();
    private Logger commonLogger = LogManager.getLogger("common");

    final String STOP = "STOP RIGHT THERE YOU CRIMINAL SCUM";
    private final String[] punctuationMarks = {
            " \\.",
            " ,",
            " !",
            " ;",
            " \\)",
            " :",
            " \\.\\.\\."
    };
    private static volatile Master instance;

    Logger getLogger() {
        return commonLogger;
    }

    void setThemeDir(File themeDir) {
        this.themeDir = themeDir;
    }

    private Master() {
        phrase = "Empty";
    }

    static Master getInstance() {
        Master localInstance = instance;
        if (localInstance == null) {
            synchronized (Master.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Master();
                }
            }
        }
        return localInstance;
    }

    private void generatePhrase(String curPhrase) {

        ArrayList<String> connectedWords;
        String[] curPhraseArray = curPhrase.split(" ");
        String lastPhraseWord = curPhraseArray[curPhraseArray.length - 1];
        connectedWords = filler.getConnectedWords(lastPhraseWord, themeDir);
        int tmp = (int) (Math.random() * connectedWords.size());
        if (first) {
            curPhrase = String.valueOf(curPhrase.charAt(0)).toUpperCase() + curPhrase.substring(1, curPhrase.length());
            first = false;
        }
        if ((connectedWords.size() > 0)) {
            curPhrase += " " + connectedWords.get(tmp);
        }

        //Writing current string to global variable.
        phrase = curPhrase;
        //Will stop executing method if string ends with "dot"
        //Second check is needed to fix StackOverflow
        //TODO remove this workaround
        if (Math.random() > 0.01) {
            if (phrase != null && phrase.charAt(phrase.length()-1) != '.') {
                generatePhrase(curPhrase);
            }
        } else {
            phrase = phrase + ".";
        }
    }

    void setFiller(Filler newFiller) {
        filler = newFiller;
    }

    void setFiller() {
        if (filler == null) {
            filler = new Filler();
        } else {
            commonLogger.warn("Trying to set already existing filter");
        }
        filler.setTheme(themeDir);
        filler.remember();
    }

    String getMadeUpText() {
        List<String> fillerList = filler.getListGlobal();
        if (fillerList.size() > 0) {
            generatePhrase(fillerList.get((int) (Math.random() * fillerList.size())));
            first = true;
        } else {
            errLogger.error("Filler list is empty. Dir: " + themeDir.getName());
            phrase = STOP;
        }
        for (String mark : punctuationMarks) {
            String tmp = mark + "\\.";
            phrase = phrase.replaceAll(tmp, ".");
            tmp = mark.replaceAll(" ", "");
            phrase = phrase.replaceAll(mark, tmp);
        }
        return phrase;
    }
}
