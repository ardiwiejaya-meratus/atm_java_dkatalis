#!/bin/bash

# start.sh — Build dan run ATM CLI application

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "Building ATM application..."
mvn clean package -q

echo "Starting ATM CLI..."
java -jar target/atm.jar
