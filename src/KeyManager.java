/**
 * Manages encryption key storage, retrieval, and validation.
 * 
 * OOP Concept Demonstrated: Encapsulation
 * - The field 'key' is private and cannot be modified directly from outside.
 * - Access is controlled through public getters, setters, and validation methods.
 */
public class KeyManager {
    
    // Encapsulated private field
    private String key;

    // Constructor to initialize default state
    public KeyManager() {
        this.key = "";
    }

    // Getter
    public String getKey() {
        return this.key;
    }

    // Setter
    public void setKey(String key) {
        this.key = key;
    }

    // Key validation
    public boolean validateKey() {
        return this.key != null && !this.key.trim().isEmpty();
    }
}
