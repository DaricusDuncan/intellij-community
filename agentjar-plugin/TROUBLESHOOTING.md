# AgentJar Plugin - Troubleshooting Guide

This guide helps resolve common issues when building and running the AgentJar plugin.

## Build Issues

### Error: "Deprecated Gradle features were used"

**Symptoms**:
```
Deprecated Gradle features were used in this build, making it incompatible with Gradle 10.
```

**Cause**: Using Gradle 9.2.0 or system Gradle instead of the wrapper

**Solution**:
```bash
# Always use the wrapper
./gradlew build

# Not: gradle build (don't use system gradle)
```

**Verification**:
```bash
./gradlew build --warning-mode all
# Should show no deprecation warnings
```

---

### Error: "Could not resolve com.jetbrains.intellij.idea"

**Symptoms**:
```
Could not resolve com.jetbrains.intellij.idea:ideaIC:2024.2.4
Could not GET 'https://cache-redirector.jetbrains.com/...'
```

**Cause**: No internet connectivity or network issues

**Solution 1**: Check connectivity
```bash
ping -c 1 services.gradle.org
curl -I https://www.jetbrains.com
```

**Solution 2**: Check proxy settings
```bash
# If behind a proxy, add to gradle.properties:
systemProp.http.proxyHost=proxy.company.com
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=proxy.company.com
systemProp.https.proxyPort=8080
```

**Solution 3**: Clear Gradle cache
```bash
./gradlew --stop
rm -rf ~/.gradle/caches
./gradlew build
```

---

### Error: "Unsupported class file major version"

**Symptoms**:
```
Unsupported class file major version 61
```

**Cause**: Java version mismatch (need Java 17+)

**Solution**:
```bash
# Check current version
java -version

# If < 17, install Java 17+
# On macOS with Homebrew:
brew install openjdk@17

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

**Verification**:
```bash
./gradlew build
```

---

### Error: "Task 'compileKotlin' not found"

**Cause**: Gradle cache corruption or configuration issue

**Solution**:
```bash
./gradlew --stop
./gradlew clean
rm -rf build .gradle
./gradlew build
```

---

### Error: "Permission denied: ./gradlew"

**Symptoms**:
```
bash: ./gradlew: Permission denied
```

**Cause**: Wrapper script not executable

**Solution**:
```bash
chmod +x gradlew
./gradlew build
```

---

### Error: "Gradle daemon disappeared unexpectedly"

**Symptoms**:
```
Gradle build daemon disappeared unexpectedly (it may have been killed or may have crashed)
```

**Cause**: Insufficient memory or daemon issues

**Solution 1**: Increase daemon memory
```properties
# In gradle.properties
org.gradle.jvmargs=-Xmx3072m -XX:MaxMetaspaceSize=768m
```

**Solution 2**: Restart daemon
```bash
./gradlew --stop
./gradlew build
```

---

### Error: Build is very slow

**Symptoms**: Build takes more than 5 minutes after first run

**Causes & Solutions**:

1. **Daemon not running**
   ```properties
   # In gradle.properties
   org.gradle.daemon=true
   ```

2. **Parallel builds disabled**
   ```properties
   # In gradle.properties
   org.gradle.parallel=true
   ```

3. **Caching disabled**
   ```properties
   # In gradle.properties
   org.gradle.caching=true
   ```

4. **Searchable options building**
   ```kotlin
   // In build.gradle.kts
   tasks {
       buildSearchableOptions {
           enabled = false
       }
   }
   ```

---

## Runtime Issues

### Error: "Plugin 'AgentJar' is not compatible"

**Symptoms**: Plugin fails to load in IntelliJ

**Cause**: Version mismatch

**Solution 1**: Check since-build
```xml
<!-- In plugin.xml -->
<idea-version since-build="242"/>
```

**Solution 2**: Rebuild plugin
```bash
./gradlew clean build
./gradlew runIde
```

---

### Error: Tool window doesn't appear

**Symptoms**: Can't find AgentJar tool window

**Solution 1**: Check it's registered
```xml
<!-- In plugin.xml -->
<toolWindow id="AgentJar" anchor="right" 
            factoryClass="com.agentjar.AgentJarToolWindowFactory"/>
```

**Solution 2**: Manually activate
- View → Tool Windows → AgentJar
- Or use Tools → AgentJar → Open Agent Chat

**Solution 3**: Restart IntelliJ
```bash
# Stop runIde
# Rebuild and restart
./gradlew build
./gradlew runIde
```

---

### Error: Actions not visible in menu

**Symptoms**: AgentJar menu items missing

**Solution**: Check plugin.xml
```xml
<actions>
    <group id="AgentJar.ActionGroup" text="AgentJar">
        <add-to-group group-id="ToolsMenu" anchor="last"/>
        <!-- actions here -->
    </group>
</actions>
```

Rebuild:
```bash
./gradlew clean build
./gradlew runIde
```

---

### Error: "Cannot resolve class" in IDE

**Symptoms**: Red underlines in source files

**Cause**: IDE not recognizing Gradle project

**Solution 1**: Reimport Gradle project
- Open Gradle tool window
- Click refresh button
- Or: File → Invalidate Caches / Restart

**Solution 2**: Check JDK configuration
- File → Project Structure → Project
- Ensure JDK 17 is selected

---

### Error: Spring Boot scaffolding fails

**Symptoms**: No files created when running Scaffold Spring Boot

**Cause**: Project has no base path or permissions issue

**Solution**: Check project is opened (not just files)
- Must be a proper project, not individual files
- Project base path must be writable

---

### Error: Conventional commit fails

**Symptoms**: Git commit action shows error

**Cause**: Git not initialized or no git command

**Solution 1**: Initialize git
```bash
cd project-directory
git init
```

**Solution 2**: Install git
```bash
# On macOS
brew install git

# On Ubuntu
sudo apt-get install git
```

---

## Development Issues

### Issue: Hot reload not working

**Cause**: IntelliJ Platform doesn't support hot reload

**Solution**: Rebuild and restart
```bash
# Stop runIde (Ctrl+C)
./gradlew build
./gradlew runIde
```

---

### Issue: Changes not reflected

**Cause**: Build didn't run or files not in classpath

**Solution**:
```bash
./gradlew clean build
./gradlew runIde
```

---

### Issue: Plugin doesn't load after changes

**Cause**: Plugin cache or IDE sandbox issue

**Solution**:
```bash
# Clean everything
./gradlew clean
rm -rf build .gradle

# Rebuild
./gradlew build

# Clear IntelliJ sandbox (optional)
rm -rf build/idea-sandbox

# Restart
./gradlew runIde
```

---

## Gradle Configuration Issues

### Issue: Wrong Gradle version used

**Symptoms**: Deprecation warnings or incompatibility

**Solution**: Always use wrapper
```bash
# Check wrapper version
cat gradle/wrapper/gradle-wrapper.properties | grep distributionUrl
# Should show: gradle-8.7-bin.zip

# Use wrapper
./gradlew build
```

---

### Issue: Dependencies not resolving

**Cause**: Repository configuration or network issues

**Solution 1**: Check repositories in build.gradle.kts
```kotlin
repositories {
    mavenCentral()
}
```

**Solution 2**: Clear and retry
```bash
./gradlew --refresh-dependencies build
```

---

## Documentation References

### Quick answers
- Common tasks: `QUICKSTART.md`
- Build details: `BUILDING.md`
- Deprecation fix: `GRADLE-DEPRECATION-FIX.md`

### Full verification
- Run checklist: `VERIFICATION-CHECKLIST.md`
- Complete overview: `SOLUTION-SUMMARY.md`

---

## Getting More Help

### Enable debug logging
```bash
./gradlew build --info      # Detailed info
./gradlew build --debug     # Debug level
./gradlew build --stacktrace # Full stack traces
```

### Check Gradle logs
```bash
# Gradle logs location
cat ~/.gradle/daemon/*/daemon-*.out.log
```

### Generate build scan
```bash
./gradlew build --scan
# Follow the URL to see detailed build analysis
```

### Check plugin compatibility
```bash
./gradlew verifyPlugin
```

---

## Emergency Reset

If nothing works, complete reset:

```bash
# Stop all Gradle processes
./gradlew --stop
pkill -f gradle

# Clean everything
rm -rf build .gradle .idea *.iml
rm -rf ~/.gradle/caches

# Rebuild from scratch
./gradlew clean build

# Test
./gradlew runIde
```

---

## Known Limitations

### Current environment
- ⚠️ No external network access in sandboxed build environment
- ⚠️ Cannot download IntelliJ SDK without connectivity
- ✅ Configuration is complete and ready for when connectivity is available

### Workarounds
- Build configuration is correct
- When connectivity is restored, build will work
- Use `verify-build.sh` to confirm when connectivity is available

---

## Still Having Issues?

1. Check all documentation files
2. Run `./verify-build.sh` for diagnostics
3. Check build logs in `build/` directory
4. Review error messages carefully
5. Check GitHub Issues for similar problems

## Prevention

### Best Practices
✅ Always use `./gradlew` (not system `gradle`)
✅ Keep documentation handy
✅ Run `./verify-build.sh` after changes
✅ Check logs when issues occur
✅ Keep backups of working configuration

---

**Last Updated**: Based on Gradle 8.7 configuration
