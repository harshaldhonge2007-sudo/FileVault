public class EncryptionEngine extends FileOperation {

    @Override
    public byte[] processBytes(byte[] inputData, String key) {
        byte[] keyBytes = key.getBytes();
        byte[] result = new byte[inputData.length];

        for (int i = 0; i < inputData.length; i++) {
            result[i] = (byte) (inputData[i] ^ keyBytes[i % keyBytes.length]);
        }

        return result;
    }
}
