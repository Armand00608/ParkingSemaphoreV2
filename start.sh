#!/bin/bash

# Se placer dans le repertoire du script
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

# Creation du dossier bin
mkdir -p bin

# Compilation
echo "Compilation..."
javac -d bin @compile.list
if [ $? -ne 0 ]; then
    echo "Erreur de compilation."
    exit 1
fi

# Execution
echo "Lancement de la simulation..."
java -cp bin Parking.Controleur
