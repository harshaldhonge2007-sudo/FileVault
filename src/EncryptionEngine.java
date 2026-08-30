/**
 * Encryption engine implementing XOR encryption/decryption.
 * 
 * OOP Concepts Demonstrated:
 * - Inheritance: Extends FileOperation (subclass of FileOperation).
 * - Polymorphism: Overrides processBytes method (@Override).
 * 
 * How XOR works:
 *   Original Byte ^ Key Byte = Encrypted Byte
 *   Encrypted Byte ^ Key Byte = Original Byte
 *   Property: (A ^ B) ^ B = A
 * Because of this reversible property, the exact same method performs both encryption and decryption.
 */
public class EncryptionEngine extends FileOperation {

    @Override
    public byte[] processBytes(byte[] inputData, String key) {
        byte[] keyBytes = key.getBytes();
        byte[] result = new byte[inputData.length];

        // Loop through every byte in the input data
        for (int i = 0; i < inputData.length; i++) {
            // XOR each byte with the repeating key byte using modulo (%)
            result[i] = (byte) (inputData[i] ^ keyBytes[i % keyBytes.length]);
        }

        return result;
    }
}
