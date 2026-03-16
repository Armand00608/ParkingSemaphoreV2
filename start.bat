@echo off
REM Script de compilation et lancement de la simulation de parking (Windows)

REM Se placer dans le repertoire du script
cd /d "%~dp0"

REM Creation du dossier bin
if not exist bin mkdir bin

REM Compilation
echo Compilation...
javac -d bin @compile.list
if %ERRORLEVEL% neq 0 (
    echo Erreur de compilation.
    pause
    exit /b 1
)

REM Execution
echo Lancement de la simulation...
java -cp bin Parking.Controleur
pause
