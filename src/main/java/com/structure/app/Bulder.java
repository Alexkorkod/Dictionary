package com.structure.app;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Bulder {
    public static ArrayList<String> createSentenceTemplate(String baseFileName) {
        File base = new File(baseFileName);
        try {
            FileInputStream inputStream = new FileInputStream(base);
            String everything = IOUtils.toString(inputStream);
            JSONObject jObj = new JSONObject(everything);
            JSONArray jArr = jObj.getJSONArray("output");
            JSONObject jObjChosen = jArr.getJSONObject((int) Math.round(Math.random() * jArr.length()));
            System.out.print(jObjChosen.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
