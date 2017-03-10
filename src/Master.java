import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Master {
    private String phrase;
    private Filler filler;
    private File themeDir;
    //Check for position of current word. We should know what case it should be, don't we?
    private boolean first = true;


    Master() {
        phrase = "Empty";
    }


    private void generatePhrase(String curPhrase) {

        if (first) {
            ArrayList<String> connectedWords = filler.getConnectedWords(curPhrase, themeDir);
            if ((curPhrase != null) && ((connectedWords.size() > 0))) {
                curPhrase = String.valueOf(curPhrase.charAt(0)).toUpperCase() + curPhrase.substring(1, curPhrase.length())
                        + " " + connectedWords.get((int) (Math.random() * connectedWords.size()));
                first = false;
            }
        } else {
            //Totally the same thing as above.
            String[] curPhraseArray = curPhrase.split(" ");
            String lastPhraseWord = curPhraseArray[curPhraseArray.length - 1];
            if ((filler.getConnectedWords(lastPhraseWord, themeDir).size() > 0)) {
                //It's just much more comfortable to work with this values when they are separated.
                int tmp = (int) (Math.random() * filler.getConnectedWords(lastPhraseWord, themeDir).size());
                curPhrase += " " + filler.getConnectedWords(lastPhraseWord, themeDir).get(tmp);
            }
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
            phrase = phrase.substring(0, phrase.length()-2) + ".";
        }
    }

    void setFiller(Filler newFiller) {
        filler = newFiller;
    }

    private String getSomeText(int iterationTot) {
        if (filler == null) {
            filler = new Filler();
        }
        filler.setTheme(themeDir);
        filler.remember();
        List<String> fillerList = filler.getListGlobal();
        if (fillerList.size() > 0) {
            for (int i = 0; i < iterationTot; i++) {
                generatePhrase(fillerList.get((int) (Math.random() * fillerList.size())));
                first = true;
            }
        } else {
            System.exit(3);
        }
        return phrase;
    }

    //Returns few sentences about some theme.
    //Ahtung! You need a specified directory with thematic words.
    String getRawOutput(File theme, int numOfPhrases) {
        themeDir = theme;
        return getSomeText(numOfPhrases);
    }
}
