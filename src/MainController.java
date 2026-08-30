import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller responsible for the JavaFX User Interface and event handling.
 * 
 * OOP Concepts Demonstrated:
 * - Composition & Object Creation: Instantiates and collaborates with
 *   FileProcessor, EncryptionEngine (polymorphic FileOperation), and KeyManager.
 * - Separation of Concerns: Handles UI state, user actions, and delegates
 *   file processing and encryption logic to dedicated classes.
 */
public class MainController {

    // Primary Stage reference for dialogs
    private final Stage stage;

    // OOP Objects
    private final FileProcessor fileProcessor;
    private final FileOperation encryptionEngine;
    private final KeyManager keyManager;

    // State
    private File selectedFile;

    // UI Controls
    private Label fileIconLabel;
    private Label fileNameLabel;
    private Label filePathLabel;
    private PasswordField keyField;
    private Button encryptButton;
    private Button decryptButton;
    private Button clearButton;
    private Label statusLabel;
    private Label statusBadge;

    public MainController(Stage stage) {
        this.stage = stage;
        // Instantiating collaborator objects
        this.fileProcessor = new FileProcessor();
        this.encryptionEngine = new EncryptionEngine(); // Polymorphism: FileOperation reference
        this.keyManager = new KeyManager();
    }

    /**
     * Builds and returns the main view layout.
     */
    public Parent createView() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");
        root.setPadding(new Insets(24, 32, 24, 32));

        // 1. Header
        root.setTop(createHeader());

        // 2. Center Content Area
        VBox contentBox = new VBox(16);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(16, 0, 16, 0));

        contentBox.getChildren().addAll(
            createFileSection(),
            createKeySection(),
            createActionButtonsSection()
        );

        root.setCenter(contentBox);

        // 3. Footer (Status & Clear)
        root.setBottom(createFooter());

        // Initial UI State
        updateUiState();

        return root;
    }

    private HBox createHeader() {
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        Label title = new Label("🔐 FileVault");
        title.getStyleClass().add("title-label");

        Label subtitle = new Label("File Encryption & Decryption (XOR Algorithm)");
        subtitle.getStyleClass().add("subtitle-label");

        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        statusBadge = new Label("● Ready");
        statusBadge.getStyleClass().add("status-badge");

        header.getChildren().addAll(titleBox, spacer, statusBadge);
        return header;
    }

    private VBox createFileSection() {
        VBox fileCard = new VBox(10);
        fileCard.getStyleClass().add("file-drop-card");

        fileIconLabel = new Label("📄");
        fileIconLabel.setStyle("-fx-font-size: 32px;");

        fileNameLabel = new Label("No file selected");
        fileNameLabel.getStyleClass().add("file-name-label");

        filePathLabel = new Label("Choose a file (.txt, .pdf, etc.) to encrypt or decrypt");
        filePathLabel.getStyleClass().add("file-path-label");

        Button browseButton = new Button("Browse Files");
        browseButton.getStyleClass().add("btn-browse");
        browseButton.setOnAction(e -> handleBrowse());

        fileCard.getChildren().addAll(fileIconLabel, fileNameLabel, filePathLabel, browseButton);
        return fileCard;
    }

    private VBox createKeySection() {
        VBox keyCard = new VBox(8);
        keyCard.getStyleClass().add("card-pane");

        Label keyTitle = new Label("Encryption / Decryption Key");
        keyTitle.getStyleClass().add("card-title");

        keyField = new PasswordField();
        keyField.setPromptText("Enter your secret key / password...");
        keyField.getStyleClass().add("text-input-field");
        keyField.textProperty().addListener((obs, oldVal, newVal) -> updateUiState());

        keyCard.getChildren().addAll(keyTitle, keyField);
        return keyCard;
    }

    private HBox createActionButtonsSection() {
        HBox actions = new HBox(16);
        actions.setAlignment(Pos.CENTER);

        encryptButton = new Button("🔒  Encrypt File");
        encryptButton.getStyleClass().add("btn-encrypt");
        encryptButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(encryptButton, Priority.ALWAYS);
        encryptButton.setOnAction(e -> processFileAction("Encrypt"));

        decryptButton = new Button("🔓  Decrypt File");
        decryptButton.getStyleClass().add("btn-decrypt");
        decryptButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(decryptButton, Priority.ALWAYS);
        decryptButton.setOnAction(e -> processFileAction("Decrypt"));

        actions.getChildren().addAll(encryptButton, decryptButton);
        return actions;
    }

    private VBox createFooter() {
        VBox footer = new VBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(8, 0, 0, 0));

        statusLabel = new Label("Ready to select file.");
        statusLabel.getStyleClass().addAll("status-text", "status-info");

        clearButton = new Button("↺  Reset / Clear");
        clearButton.getStyleClass().add("btn-clear");
        clearButton.setOnAction(e -> handleClear());

        footer.getChildren().addAll(statusLabel, clearButton);
        return footer;
    }

    // --- Action Handlers ---

    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Encrypt or Decrypt");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            this.selectedFile = file;
            fileNameLabel.setText(file.getName());
            filePathLabel.setText(file.getAbsolutePath() + " (" + file.length() + " bytes)");
            fileIconLabel.setText("📁");
            setStatus("File selected: " + file.getName(), "status-info");
        }
        updateUiState();
    }

    private void processFileAction(String mode) {
        // 1. Validation
        if (selectedFile == null || !fileProcessor.fileExists(selectedFile)) {
            showError("File Error", "Please select a valid input file.");
            return;
        }

        keyManager.setKey(keyField.getText());
        if (!keyManager.validateKey()) {
            showError("Key Error", "Please enter a non-empty key / password.");
            return;
        }

        // 2. Select Save Location
        FileChooser saveChooser = new FileChooser();
        saveChooser.setTitle("Save " + mode + "ed File As");

        String originalName = selectedFile.getName();
        if (mode.equals("Encrypt")) {
            saveChooser.setInitialFileName(originalName + ".enc");
        } else {
            if (originalName.endsWith(".enc")) {
                saveChooser.setInitialFileName(originalName.substring(0, originalName.length() - 4));
            } else {
                saveChooser.setInitialFileName("decrypted_" + originalName);
            }
        }

        File destinationFile = saveChooser.showSaveDialog(stage);
        if (destinationFile == null) {
            // User cancelled save dialog
            setStatus("Operation cancelled by user.", "status-text");
            return;
        }

        // 3. Process File
        try {
            // Read bytes
            byte[] inputBytes = fileProcessor.readFile(selectedFile);

            // Polymorphic call to process bytes with XOR engine
            byte[] processedBytes = encryptionEngine.processBytes(inputBytes, keyManager.getKey());

            // Write bytes
            fileProcessor.writeFile(destinationFile, processedBytes);

            // Success feedback
            setStatus("✓ " + mode + "ion completed successfully! Saved to: " + destinationFile.getName(), "status-success");
            showInfo("Success", "File " + mode.toLowerCase() + "ed successfully!\n\nSaved to:\n" + destinationFile.getAbsolutePath());

        } catch (Exception ex) {
            setStatus("✗ Error during " + mode.toLowerCase() + "ion.", "status-error");
            showError("Processing Error", "Failed to process file: " + ex.getMessage());
        }
    }

    private void handleClear() {
        this.selectedFile = null;
        this.keyManager.setKey("");
        this.keyField.clear();

        fileIconLabel.setText("📄");
        fileNameLabel.setText("No file selected");
        filePathLabel.setText("Choose a file (.txt, .pdf, etc.) to encrypt or decrypt");

        setStatus("Ready to select file.", "status-info");
        updateUiState();
    }

    private void updateUiState() {
        boolean hasFile = (selectedFile != null && selectedFile.exists());
        boolean hasKey = keyField != null && !keyField.getText().trim().isEmpty();

        boolean canExecute = hasFile && hasKey;
        if (encryptButton != null) encryptButton.setDisable(!canExecute);
        if (decryptButton != null) decryptButton.setDisable(!canExecute);

        if (statusBadge != null) {
            if (canExecute) {
                statusBadge.setText("● Ready to Process");
                statusBadge.setStyle("-fx-text-fill: #10b981;");
            } else if (hasFile) {
                statusBadge.setText("● Key Required");
                statusBadge.setStyle("-fx-text-fill: #f59e0b;");
            } else {
                statusBadge.setText("● Waiting for File");
                statusBadge.setStyle("-fx-text-fill: #94a3b8;");
            }
        }
    }

    private void setStatus(String message, String styleClass) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().removeAll("status-success", "status-error", "status-info", "status-text");
        statusLabel.getStyleClass().add(styleClass);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
