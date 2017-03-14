package com.structure.app;

import org.apache.commons.io.IOUtils;
import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * План следующий:
 * Взять вводные данные. (файл с текстом)
 * Разбить по предложениям
 * Проанализировать каждой предложение stem-ом
 * Записать структуры.
 */
public class Core {

    private final MyStem mystemAnalyzer =
            new Factory("-igd --format json --weight")
                    .newMyStem("3.0", Option.<File>empty()).get();
    public File input;
    private String[] sentences;
    private ArrayList<java.lang.Iterable<Info>> resultArray;

    public void setInput(File input) {
        this.input = input;
    }

    public ArrayList<Iterable<Info>> process() {
        resultArray = new ArrayList<>();
        try(FileInputStream inputStream = new FileInputStream(input)) {
            String everything = IOUtils.toString(inputStream);
            sentences = everything.split("\\.");
            for (String sentence : sentences) {
                resultArray.add(analyzeSentence(sentence));
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
            return null;
        }
        return resultArray;
    }

    private Iterable<Info> analyzeSentence(String sent) {
        Iterable<Info> result;
        try {
            result = JavaConversions.asJavaIterable(
                            mystemAnalyzer
                                    .analyze(Request.apply(sent))
                                    .info()
                                    .toIterable());
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
