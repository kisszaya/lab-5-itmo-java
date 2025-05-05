package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.LinkedList;

public class File {
    public static void saveJSONArray(String fileUrl, LinkedList<JSONObject> obgList) throws IOException {
        JSONArray content = new JSONArray();
        for (JSONObject obj : obgList) {
            content.put(obj);
        }
        File.save(fileUrl, content.toString());
    }

    public static JSONArray readJSONArray(String fileUrl) throws JSONException, FileNotFoundException, IOException {
        String rawString = File.read(fileUrl);
        if (rawString.isEmpty()) {
            return new JSONArray();
        }

        return new JSONArray(rawString);
    }

    public static String read(String fileUrl) throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(fileUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder json = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            json.append((char) c);
        }
        return json.toString();
    }

    public static void save(String fileUrl, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileUrl, false);
        fileWriter.write(content);
        fileWriter.close();
    }
}
