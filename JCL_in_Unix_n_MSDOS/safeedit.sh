#!/bin/bash

LOG_FILE="backup_log.txt"
MAX_LOG_ENTRIES=5

log_backup() {
    local filename="$1"
    local timestamp
    timestamp=$(date +"[%Y-%m-%d %H:%M:%S]")
    echo "$timestamp Backup created: $filename → $filename.bak" >> "$LOG_FILE"

    if [ "$(wc -l < "$LOG_FILE")" -gt "$MAX_LOG_ENTRIES" ]; then
        tail -n "$MAX_LOG_ENTRIES" "$LOG_FILE" > temp_log && mv temp_log "$LOG_FILE"
    fi
}

edit_file() {
    local filename="$1"
    echo "==============================="
    echo "Editing: $filename"
    echo "==============================="

    FILE_EXISTED=0
    if [ -f "$filename" ]; then
        FILE_EXISTED=1
        cp -f "$filename" "$filename.bak"
    else
        touch "$filename"
        echo "File does not exist. Creating new file: $filename"
    fi

    vi "$filename"

    # After editing, if the file existed BEFORE, log the backup
    if [ "$FILE_EXISTED" -eq 1 ]; then
        log_backup "$filename"
    fi
}

view_log() {
    clear
    echo "==============================="
    echo "Backup Log File"
    echo "==============================="
    if [ -f "$LOG_FILE" ]; then
        cat "$LOG_FILE"
    else
        echo "No backup log found."
    fi
    echo "==============================="
    read -rp "Press Enter to return to menu..."
}

show_help() {
    clear
    echo "==============================="
    echo "Help"
    echo "==============================="
    echo "Safely edit files and create backups automatically."
    echo
    echo "Modes:"
    echo "- CLI: ./safeedit.sh myfile.txt"
    echo "- Interactive: Run without arguments"
    echo
    echo "Backups:"
    echo "- Only made if file existed BEFORE editing"
    echo "- Log shows last 5 backups only"
    echo
    read -rp "Press Enter to return to menu..."
}

if [ $# -gt 1 ]; then
    echo "Too many parameters. Provide only one filename."
    exit 1
elif [ $# -eq 1 ]; then
    edit_file "$1"
    exit 0
fi

while true; do
    clear
    echo "==============================="
    echo "       SafeEdit Main Menu      "
    echo "==============================="
    echo "1. Edit file"
    echo "2. View backup log file"
    echo "3. Help"
    echo "4. Exit"
    echo "==============================="
    read -rp "Choose an option [1-4]: " choice

    case "$choice" in
        1)
            clear
            echo "==============================="
            echo "Edit File (Interactive Mode)"
            echo "==============================="
            read -rp "Enter filename: " filename
            edit_file "$filename"
            ;;
        2) view_log ;;
        3) show_help ;;
        4) echo "Goodbye!"; exit 0 ;;
        *) echo "Invalid choice"; sleep 1 ;;
    esac
done
