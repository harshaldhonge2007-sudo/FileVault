#!/bin/bash
# Script to compile and run FileVault (JavaFX)

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

JAVAFX_LIB="$PROJECT_DIR/lib/javafx-sdk-21.0.6/lib"

if [ ! -d "$JAVAFX_LIB" ]; then
    echo "❌ JavaFX SDK not found in lib/. Please ensure JavaFX SDK is downloaded."
    exit 1
fi

echo "📦 Compiling FileVault..."
mkdir -p bin
javac --module-path "$JAVAFX_LIB" --add-modules javafx.controls -d bin src/*.java
cp src/style.css bin/

if [ $? -eq 0 ]; then
    echo "🚀 Launching FileVault..."
    java --module-path "$JAVAFX_LIB" --add-modules javafx.controls -cp bin Main
else
    echo "❌ Compilation failed."
    exit 1
fi
