#!/bin/bash
# Build script for AgentJar plugin
# This script builds the plugin and prepares it for distribution

set -e  # Exit on error

echo "================================"
echo "AgentJar Plugin Build Script"
echo "================================"
echo ""

# Check if we're in the right directory
if [ ! -f "build.gradle.kts" ]; then
    echo "Error: build.gradle.kts not found!"
    echo "Please run this script from the agentjar-plugin directory"
    exit 1
fi

# Check Java version
echo "Checking Java version..."
java -version 2>&1 | head -n 1
echo ""

# Clean previous builds
echo "Cleaning previous builds..."
./gradlew clean
echo ""

# Run tests
echo "Running tests..."
if ./gradlew test; then
    echo "✓ All tests passed!"
else
    echo "✗ Tests failed!"
    echo "Fix the tests before building the plugin."
    exit 1
fi
echo ""

# Build the plugin
echo "Building plugin..."
if ./gradlew buildPlugin; then
    echo "✓ Plugin built successfully!"
else
    echo "✗ Build failed!"
    exit 1
fi
echo ""

# Show build artifacts
echo "Build artifacts:"
echo "----------------"
ls -lh build/distributions/
echo ""

# Get the plugin ZIP file
PLUGIN_ZIP=$(ls build/distributions/*.zip | head -n 1)

if [ -f "$PLUGIN_ZIP" ]; then
    echo "✓ Plugin package created:"
    echo "  $PLUGIN_ZIP"
    echo ""
    echo "To install:"
    echo "  1. Open IntelliJ IDEA"
    echo "  2. Go to Settings > Plugins"
    echo "  3. Click the gear icon (⚙️)"
    echo "  4. Select 'Install Plugin from Disk...'"
    echo "  5. Choose: $PLUGIN_ZIP"
    echo "  6. Restart IntelliJ IDEA"
else
    echo "✗ Plugin package not found!"
    exit 1
fi

echo ""
echo "================================"
echo "Build completed successfully!"
echo "================================"
