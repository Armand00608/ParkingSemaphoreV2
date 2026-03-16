@echo off

:: Création du dossier bin
if not exist "..\bin" mkdir "..\bin"

:: Compilation
javac -d ..\bin @compile.list

:: Exécution
java -cp ..\bin Parking.Controleur
