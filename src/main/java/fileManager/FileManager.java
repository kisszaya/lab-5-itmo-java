package fileManager;

import java.io.*;

public class FileManager {
    String fileUrl;

    public FileManager(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public File getFile() {
        return new File(fileUrl);
    }

    public String read() throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(this.fileUrl);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder json = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            json.append((char) c);
        }
        return json.toString();
    }

    public void save(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(this.fileUrl, false);
        fileWriter.write(content);
        fileWriter.close();
    }
}
