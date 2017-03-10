package com.my.whores;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Core {
    protected static class Word {
        private String current;
        private String previous;
        private String next;

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public Word() {
            next = current = previous = null;
        }

        public Word(String current) {
            this.current = current;
            next = previous = null;
        }

        public Word(String previous, String current, String next) {
            this.previous = previous;
            this.current = current;
            this.next = next;
        }
    }

    protected static ArrayList<Word> words;
    private static final String OUTPUT_FILE = "C:\\output.txt";

    public static void main(String[] args) throws IOException {
        words = new ArrayList<Word>();
        parse(new File("C:\\test.txt"));
        showDictionary();
    }

    private static void showDictionary() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE, true));
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            writer.write(word.getPrevious() + ":" + word.getCurrent() + ":" + word.getNext() + "\n");
        }
        writer.close();
    }

    private static void generate(int iterations) throws FileNotFoundException {
        String output = "";
        int first = (int) (Math.random() * words.size()), next = first;
        for (int i = 0; i < iterations; i++) {
            output += words.get(next).getCurrent();
            //words.
        }
    }

    private static void parse(File file) throws FileNotFoundException {
        ArrayList<String> tmpWords = new ArrayList<String>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.length() > 3)
                if (!tmpWords.contains(line.substring(0, line.length() - 2)))
                    tmpWords.add(line);
        }
        for (int i = 0; i < tmpWords.size(); i++) {
            Word word = new Word(tmpWords.get(i));
            if (i != 0)
                word.setPrevious(tmpWords.get(i - 1));
            if (i != tmpWords.size() - 1)
                word.setNext(tmpWords.get(i + 1));
            words.add(word);
        }
    }
}
