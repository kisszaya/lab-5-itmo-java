package jsonArrayFileManager;

import fileManager.FileManager;
import fileManager.FileManagerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.LinkedList;

public class JSONArrayFileManager {
    FileManager fileManager;

    public JSONArrayFileManager(String fileUrl, FileManagerFactory fileManagerFactory) throws JSONException {
        fileManager = fileManagerFactory.createFileManager(fileUrl);
    }

    public JSONArray read() throws JSONException, FileNotFoundException, IOException {
        String rawString = fileManager.read();
        return getJsonFromString(rawString);
    }

    public void saveObjectListInArray(LinkedList<JSONObject> obgList) throws IOException {
        JSONArray content = new JSONArray();
        for (JSONObject obj : obgList) {
            content.put(obj);
        }
        fileManager.save(content.toString());
    }

    private JSONArray getJsonFromString(String string) throws JSONException {
        if (string.isEmpty()) {
            return new JSONArray();
        }

        return new JSONArray(string);
    }
}
