import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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


    private void generatePhrase(String curPhrase) throws FileNotFoundException {

        if (first) {
            //This string makes it work. Really. It was used to throw a lot of errors recently.
            if ((filler.getConnectedWords(curPhrase, themeDir) != null) && (curPhrase != null) && ((filler.getConnectedWords(curPhrase, themeDir).size() > 0))) {
                curPhrase = String.valueOf(curPhrase.charAt(0)).toUpperCase() + curPhrase.substring(1, curPhrase.length())
                        + " " + filler.getConnectedWords(curPhrase, themeDir).get((int) (Math.random() * filler.getConnectedWords(curPhrase, themeDir).size()));
                first = false;
            }
        } else {
            //Totally the same thing as above.
            if ((filler.getConnectedWords(curPhrase.split(" ")[curPhrase.split(" ").length - 1], themeDir) != null) && (filler.getConnectedWords(curPhrase.split(" ")[curPhrase.split(" ").length - 1], themeDir).size() > 0)) {
                //It's just much more comfortable to work with this values when they are separated.
                int tmp = (int) (Math.random() * filler.getConnectedWords(curPhrase.split(" ")[curPhrase.split(" ").length - 1], themeDir).size());
                curPhrase += " " + filler.getConnectedWords(curPhrase.split(" ")[curPhrase.split(" ").length - 1], themeDir).get(tmp);
            }
        }

        //Writing current string to global variable.
        phrase = curPhrase;
        //Will stop executing method if string ends with "dot"
        //Second check is needed to fix StackOverflow
        //TODO remove that crunch.
        if (phrase != null && phrase.charAt(phrase.length()-1) != '.' && (Math.random() > 0.01)) {
            generatePhrase(curPhrase);
        }
        /*
        }else{
            phrase = phrase.substring(0, curPhrase.length()-2) + ".";
        }*/
    }

    Filler getFiller() {
        return filler;
    }

    void setFiller(Filler newFiller) {
        filler = newFiller;
    }

    private String getSomeText(int iterationTot) throws IOException {
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
    String getRawOutput(File theme, int numOfPhrases) throws IOException {
        themeDir = theme;
        return getSomeText(numOfPhrases);
    }
}
