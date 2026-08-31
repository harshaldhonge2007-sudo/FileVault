public class KeyManager {
    
    private String key;

    public KeyManager() {
        this.key = "";
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean validateKey() {
        return this.key != null && !this.key.trim().isEmpty();
    }
}
