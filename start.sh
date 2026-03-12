#!/bin/bash

# Creation du dossier bin
mkdir -p ../bin

# Compilation
javac -d ../bin @compile.list

# Execution
java -cp ../bin Parking.Controleur
