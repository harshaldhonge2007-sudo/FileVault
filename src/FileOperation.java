/**
 * Abstract class representing a generic file operation.
 * 
 * OOP Concept Demonstrated: Abstraction
 * - An abstract class defines a template/contract without providing full implementation.
 * - Subclasses must provide their own implementation of the abstract method.
 */
public abstract class FileOperation {
    
    /**
     * Abstract method to process an array of bytes using a key.
     * 
     * @param inputData The raw bytes of the file
     * @param key The encryption/decryption key string
     * @return The processed bytes
     */
    public abstract byte[] processBytes(byte[] inputData, String key);
}
