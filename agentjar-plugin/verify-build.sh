#!/bin/bash
# Verification script for AgentJar Plugin build
# This script verifies the Gradle build configuration and checks for deprecation warnings

set -e

echo "========================================="
echo "AgentJar Plugin Build Verification"
echo "========================================="
echo ""

# Check Java version
echo "Checking Java version..."
java -version 2>&1 | head -1
JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "ERROR: Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi
echo "✓ Java version OK"
echo ""

# Check Gradle wrapper
echo "Checking Gradle wrapper..."
if [ ! -f "./gradlew" ]; then
    echo "ERROR: Gradle wrapper not found!"
    exit 1
fi
echo "✓ Gradle wrapper found"
echo ""

# Display Gradle version
echo "Gradle version:"
./gradlew --version | grep "Gradle"
echo ""

# Clean build
echo "Running clean build with warning mode..."
./gradlew clean build --warning-mode all 2>&1 | tee build-output.log

# Check for deprecation warnings
if grep -i "deprecated" build-output.log; then
    echo ""
    echo "⚠️  WARNING: Deprecation warnings detected!"
    echo "Please review build-output.log for details"
    exit 1
else
    echo ""
    echo "✓ No deprecation warnings found"
fi

# Check if build was successful
if [ -f "build/libs/agentjar-plugin-0.0.1.jar" ]; then
    echo "✓ Build successful - JAR created"
    ls -lh build/libs/
else
    echo "⚠️  Build completed but JAR not found"
fi

echo ""
echo "========================================="
echo "Build Verification Complete"
echo "========================================="
echo ""
echo "Next steps:"
echo "  1. Run './gradlew runIde' to test the plugin"
echo "  2. Run './gradlew verifyPlugin' to verify compatibility"
echo "  3. Run './gradlew buildPlugin' to create distribution"
echo ""

# Cleanup
rm -f build-output.log
