package com.structure.app;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
    private ArrayList<ArrayList<String>> resultArray;
    private ArrayList<Combination> combArray = new ArrayList<>();

    public void setInput(File input) {
        this.input = input;
    }

    public ArrayList<Combination> process() {
        resultArray = new ArrayList<ArrayList<String>>();
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
        return breakStructureDown(resultArray);
    }

    private ArrayList<Combination> breakStructureDown(ArrayList<ArrayList<String>> processResult) {
        JSONParser parser = new JSONParser();
        for (ArrayList<String> item : processResult) {
            combArray.add(new Combination());
            for (int i = 0; i < item.size(); i++) {
                String tmp = item.get(i);
                try {
                    JSONObject obj = (JSONObject) ((JSONArray) ((JSONObject) parser.parse(tmp)).get("analysis")).get(0);
                    String s = obj.get("gr").toString();
                    //Just for breakpoint
                    //TODO here we need to append Strings to combination
                    //TODO OR we could grow list of gr-s on each iteration and create new Combinations with it;
                    combArray.size();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //sentence end
        }
        return combArray;
    }

    //FUCK ITERABLES
    private ArrayList<String> analyzeSentence(String sent) {
        Collection<Info> tmp;
        ArrayList<String> result = new ArrayList<>();
        try {
            tmp = JavaConversions.asJavaCollection(mystemAnalyzer.analyze(Request.apply(sent)).info().toIterable());
            for (Info info : tmp) {
                result.add(info.rawResponse());
            }
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }


}
