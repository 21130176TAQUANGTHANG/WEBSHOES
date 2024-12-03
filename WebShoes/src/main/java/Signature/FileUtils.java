package Signature;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void saveToFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }
}
