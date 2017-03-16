package com.structure.app;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.*;
import java.util.*;

class Core {

    private final MyStem mystemAnalyzer =
            new Factory("-igd --format json --weight")
                    .newMyStem("3.0", Option.empty()).get();
    private File input;
    private ArrayList<ArrayList<String>> resultArray;
    private ArrayList<Combination> combinationsList = new ArrayList<>();
    private String outputFileName = "output.json";

    void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
    void setInput(File input) {
        this.input = input;
    }

    void process() {
        resultArray = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(input)) {
            String everything = IOUtils.toString(inputStream);
            String[] sentences = everything.split("\\.");
            for (String sentence : sentences) {
                analyzeSentence(sentence);
            }
            breakStructureDown();
            sortCombinations();
            writeResults();
            //ArrayList<String> sentence = Builder.createSentenceTemplate(outputFileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void breakStructureDown() {
        for (ArrayList<String> item : resultArray) {
            for (int i = 0; i < item.size(); i++) {
                ArrayList<String> col = new ArrayList<>();
                ArrayList<String> example = new ArrayList<>();
                Position.PositionEnum placement = Position.PositionEnum.ELSE;
                if (i == 0) {
                    placement = Position.PositionEnum.FIRST;
                }
                for (int j = i; j < item.size(); j++) {
                    String tmp = item.get(j);
                    JSONObject jobj = new JSONObject(tmp);
                    JSONArray infoArr = (JSONArray) jobj.get("analysis");
                    String text = jobj.get("text").toString();
                    String gr;
                    if (j == item.size() - 1) {
                        placement = Position.PositionEnum.LAST;
                    }
                    if (infoArr.length() > 0) {
                        gr = ((JSONObject) infoArr.get(0)).get("gr").toString();
                    } else {
                        gr = "Unknown";
                    }
                    col.add(gr);
                    example.add(text);
                    int pos = posInCombArray(col);
                    if (pos < 0) {
                        combinationsList.add(new Combination(col, example, placement));
                    } else {
                        Combination currentCombination = combinationsList.get(pos);
                        int placePos = currentCombination.findPositionPosition(placement);
                        if (placePos >= 0) {
                            currentCombination.getPositionsArray().get(placePos).increment();
                        } else {
                            currentCombination.getPositionsArray().add(new Position(placement));
                        }
                        currentCombination.increment();
                    }
                }
            }
        }
    }

    private int posInCombArray(ArrayList<String> collection) {
        int key = 0;
        for (Combination comb : combinationsList) {
            if (collection.equals(comb.getCombArray())) {
                return key;
            }
            key++;
        }
        return -1;
    }

    private void analyzeSentence(String sent) {
        Collection<Info> tmp;
        ArrayList<String> result = new ArrayList<>();
        try {
            tmp = JavaConversions.asJavaCollection(mystemAnalyzer.analyze(Request.apply(sent)).info().toIterable());
            for (Info info : tmp) {
                result.add(info.rawResponse());
            }
            resultArray.add(result);
        } catch (MyStemApplicationException e) {
            e.printStackTrace();
        }
    }

    private void writeResults() {
        Gson gson = new Gson();
        try {
            gson.toJson(combinationsList, new FileWriter(outputFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortCombinations() {
        combinationsList.sort(Comparator.comparingInt(Combination::getOccurrences));
        combinationsList.removeIf(com -> com.getOccurrences() < 2);
        combinationsList.removeIf(com -> com.getCombArray().size() < 2);
        Collections.reverse(combinationsList);
    }
}
