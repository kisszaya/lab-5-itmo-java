package fileManager;

public class FileManagerFactory {

    public FileManager createFileManager(String fileUrl) {
        return new FileManager(fileUrl);
    }
}
