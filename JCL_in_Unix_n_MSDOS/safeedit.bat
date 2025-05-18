@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

REM Check for too many parameters
IF NOT "%~2"=="" (
    ECHO Too many parameters entered. Please provide only one filename.
    PAUSE
    EXIT /B
    )



REM checking for the help line thing
IF "%~1"=="-h" (
    GOTO HELP
)


REM If one parameter is passed, use it
IF NOT "%~1"=="" (
    SET FILE=%~1
    SET MODE=CLI
    GOTO EDIT_FILE
)

REM Interactive mode
:MENU
CLS
ECHO.
ECHO ================================
ECHO       SafeEdit Main Menu       
ECHO ================================
ECHO 1. Edit file
ECHO 2. View backup log file
ECHO 3. Help
ECHO 4. Exit
ECHO ================================
SET /P CHOICE=Choose an option [1-4]: 

IF "%CHOICE%"=="1" GOTO ASK_FILENAME
IF "%CHOICE%"=="2" GOTO VIEW_LOG
IF "%CHOICE%"=="3" GOTO HELP
IF "%CHOICE%"=="4" GOTO EXIT
ECHO Invalid choice, please try again.
PAUSE
GOTO MENU

:ASK_FILENAME
CLS
ECHO ================================
ECHO   Edit File (Interactive Mode)
ECHO ================================
ECHO What file do you wish to edit?
SET /P FILE=
SET MODE=INTERACTIVE
GOTO EDIT_FILE

:EDIT_FILE
CLS
ECHO ================================
ECHO        Editing: %FILE%
ECHO ================================

SET BACKUP_MADE=0

REM Check if file exists before editing
IF EXIST "%FILE%" (
    REM Create backup
    COPY /Y "%FILE%" "%FILE%.bak" >NUL
    SET BACKUP_MADE=1

    REM Log the backup with timestamp
    FOR /F "tokens=2 delims==" %%I IN ('WMIC OS GET LocalDateTime /VALUE') DO SET DATETIME=%%I
    SET TIMESTAMP=!DATETIME:~0,4!-!DATETIME:~4,2!-!DATETIME:~6,2! !DATETIME:~8,2!:!DATETIME:~10,2!:!DATETIME:~12,2!
    ECHO [!TIMESTAMP!] Backup created: %FILE% → %FILE%.bak >> backup_log.txt

    REM Trim log to last 5 entries
    FINDSTR /R /C:"Backup created:" backup_log.txt > temp_log.txt
    FOR /F "skip=5 delims=" %%L IN (temp_log.txt) DO (
        MORE +1 temp_log.txt > temp_log2.txt
        MOVE /Y temp_log2.txt temp_log.txt >NUL
    )
    MOVE /Y temp_log.txt backup_log.txt >NUL 2>NUL
) ELSE (
    REM File does not exist, create it but no backup
    ECHO File "%FILE%" does not exist. Creating a new file...
    ECHO. > "%FILE%"
)

REM Open the file for editing
NOTEPAD "%FILE%"

IF "%MODE%"=="CLI" (
    ECHO Done editing "%FILE%".
    GOTO EXIT
)
GOTO MENU

:VIEW_LOG
CLS
ECHO ================================
ECHO Backup Log File
ECHO ================================
IF EXIST backup_log.txt (
    TYPE backup_log.txt
) ELSE (
    ECHO No backup log found.
)
ECHO ================================
PAUSE
GOTO MENU

REM explains about the systems usage and what it will do
:HELP
CLS
ECHO ================================
ECHO               Help
ECHO ================================
ECHO This script allows you to safely edit files
ECHO and keep backups automatically.
ECHO.
ECHO Modes:
ECHO * Interactive Mode: Prompts you to enter a file name.
ECHO * Command-Line Mode: Pass the filename as an argument.
ECHO.
ECHO Backups:
ECHO * Only created if file exists.
ECHO * Backup saved as .bak.
ECHO * Log limited to last 5 entries.
ECHO.
ECHO Example:
ECHO   safeedit.bat myfile.txt
ECHO ================================
PAUSE
GOTO MENU

:EXIT
endlocal

