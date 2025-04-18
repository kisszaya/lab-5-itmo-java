package jsonArrayFileManager;

import fileManager.FileManagerFactory;

public class JSONArrayFileManagerFactory {
    FileManagerFactory fileManagerFactory;

    public JSONArrayFileManagerFactory(FileManagerFactory fileManagerFactory) {
        this.fileManagerFactory = fileManagerFactory;
    }

    public JSONArrayFileManager createJSONArrayFileManager(String fileUrl) {
        return new JSONArrayFileManager(fileUrl, fileManagerFactory);
    }
}
