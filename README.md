# 🔐 FileVault — Simple 2nd-Year Java OOP Project

A clean, modern, beginner-friendly **File Encryption & Decryption Application** built using **Java and JavaFX**.

Designed specifically for **2nd-year Computer Engineering OOP demonstrations and vivas**.

---

## 🎯 Project Overview

**FileVault** allows users to securely encrypt and decrypt any file using a classic, educational **XOR-based symmetric encryption algorithm**. 

The code is intentionally structured into **6 simple, easy-to-understand Java classes** demonstrating the core principles of Object-Oriented Programming without unnecessary over-engineering.

---

## 🏗️ Project Architecture & OOP Concepts

```text
src/
├── Main.java              # Entry point (Inheritance: extends Application)
├── MainController.java    # UI layout & events (Composition & Object Collaboration)
├── EncryptionEngine.java  # XOR logic (Inheritance & Polymorphism: extends FileOperation)
├── FileOperation.java     # Abstraction: Abstract base class defining processBytes()
├── FileProcessor.java     # Encapsulation: File byte reading and writing
├── KeyManager.java        # Encapsulation: Private key storage and validation
└── style.css              # Clean, modern dark-themed JavaFX CSS
```

### 1. Encapsulation
- **`KeyManager`**: The secret key is stored in a `private String key` field, accessible only via getters, setters, and `validateKey()`.
- **`FileProcessor`**: Hides low-level I/O streams (`FileInputStream`, `FileOutputStream`) behind simple `readFile()` and `writeFile()` methods.

### 2. Abstraction
- **`FileOperation`**: Defines an abstract contract:
  ```java
  public abstract byte[] processBytes(byte[] inputData, String key);
  ```
  It establishes *what* needs to be done without enforcing *how* it is done.

### 3. Polymorphism
- In `MainController`, we reference `EncryptionEngine` through its abstract superclass:
  ```java
  FileOperation operation = new EncryptionEngine();
  byte[] result = operation.processBytes(data, key);
  ```
  This demonstrates dynamic method dispatch at runtime.

### 4. Inheritance
- `Main extends Application` (JavaFX framework lifecycle).
- `EncryptionEngine extends FileOperation` (academic abstract class hierarchy).

### 5. Object Collaboration & Constructors
- `MainController` initializes and delegates work to `FileProcessor`, `KeyManager`, and `EncryptionEngine`.

---

## 💡 How the XOR Algorithm Works

The encryption utilizes the symmetric bitwise **XOR (`^`)** operator:

$$\text{Original Byte} \oplus \text{Key Byte} = \text{Encrypted Byte}$$
$$\text{Encrypted Byte} \oplus \text{Key Byte} = \text{Original Byte}$$

### Key Property:
$$(A \oplus B) \oplus B = A$$

Because of this reversible mathematical property:
- Encrypting `sample.txt` with key `"pass123"` produces an encrypted binary file.
- Passing the encrypted bytes back into the **exact same XOR method** with `"pass123"` recovers the original file.

> **Note**: This is an educational implementation designed for clear demonstration of algorithms and OOP concepts, not for military-grade cryptography.

---

## 🚀 How to Run

### Quick Start (macOS / Linux):

Simply execute the included run script:

```bash
./run.sh
```

### Manual Compilation & Execution:

```bash
# 1. Compile
javac --module-path lib/javafx-sdk-21.0.6/lib --add-modules javafx.controls -d bin src/*.java
cp src/style.css bin/

# 2. Run
java --module-path lib/javafx-sdk-21.0.6/lib --add-modules javafx.controls -cp bin Main
```

---

## 🖥️ User Workflow

1. **Browse File**: Click **Browse Files** and select any file (e.g. `test_files/sample.txt`).
2. **Enter Key**: Type any secret key into the password field.
3. **Encrypt**: Click **🔒 Encrypt File** and choose where to save the output (e.g. `sample.txt.enc`).
4. **Decrypt**: Select the encrypted file, enter the same key, and click **🔓 Decrypt File** to restore the original file.
5. **Reset**: Click **↺ Reset / Clear** to restore the UI to its initial blank state.
