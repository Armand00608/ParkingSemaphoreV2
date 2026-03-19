#!/bin/bash

# Création du dossier bin
mkdir -p ../bin

# Compilation
javac -d ../bin @compile.list

# Exécution
java -cp ../bin Parking.Controleur
