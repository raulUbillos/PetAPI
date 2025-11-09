#!/bin/bash
set -e

# Ensure the data directory exists and has proper permissions
# This runs as root before switching to spring user
if [ "$(id -u)" = "0" ]; then
    mkdir -p /app/data
    chown -R spring:spring /app/data 2>/dev/null || true
    chmod -R 755 /app/data 2>/dev/null || true
    # Switch to spring user and execute the command
    exec gosu spring "$@"
else
    # Already running as spring user, just execute
    exec "$@"
fi

