import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles basic file input and output operations.
 * 
 * OOP Concept Demonstrated: Encapsulation & Separation of Concerns
 * - Encapsulates low-level file I/O operations (Streams) away from the UI.
 * - Other classes only need to work with byte arrays rather than raw streams.
 */
public class FileProcessor {

    /**
     * Reads the entire contents of a file into a byte array.
     */
    public byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    /**
     * Writes a byte array to a specified target file.
     */
    public void writeFile(File file, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }

    /**
     * Checks if a file is valid and exists on the filesystem.
     */
    public boolean fileExists(File file) {
        return file != null && file.exists() && file.isFile();
    }
}
