# AgentJar Plugin - Verification Checklist

This checklist helps verify that the Gradle deprecation issue is resolved and the plugin is ready for development.

## Pre-Build Checks

### Environment Setup
- [ ] Java 17 or higher installed
  ```bash
  java -version  # Should show 17 or higher
  ```

- [ ] Internet connectivity available
  ```bash
  ping -c 1 services.gradle.org  # Should succeed
  ```

- [ ] Sufficient disk space (~2GB)
  ```bash
  df -h .  # Check available space
  ```

### File Structure
- [x] `build.gradle.kts` exists
- [x] `settings.gradle.kts` exists
- [x] `gradle.properties` exists
- [x] `gradle/wrapper/gradle-wrapper.properties` exists
- [x] `gradlew` exists and is executable
- [x] `src/main/kotlin/com/agentjar/` contains source files
- [x] `src/main/resources/META-INF/plugin.xml` exists

### Configuration Verification
- [x] Gradle wrapper version is 8.7
  ```bash
  grep "gradle-8.7" gradle/wrapper/gradle-wrapper.properties
  ```

- [x] IntelliJ plugin version is 1.17.4
  ```bash
  grep '1.17.4' build.gradle.kts
  ```

- [x] Kotlin version is 1.9.24
  ```bash
  grep '1.9.24' build.gradle.kts
  ```

## Build Verification

### First Build
- [ ] Run build with warning mode
  ```bash
  ./gradlew clean build --warning-mode all | tee build.log
  ```

- [ ] Verify no deprecation warnings
  ```bash
  grep -i "deprecated" build.log
  # Should return no results
  ```

- [ ] Verify build success
  ```bash
  echo $?  # Should be 0
  ```

- [ ] Check JAR created
  ```bash
  ls -lh build/libs/agentjar-plugin-0.0.1.jar
  # Should exist
  ```

### Alternative: Use Verification Script
- [ ] Run automated verification
  ```bash
  ./verify-build.sh
  ```

## Runtime Verification

### Launch Plugin
- [ ] Run in IntelliJ sandbox
  ```bash
  ./gradlew runIde
  ```

### Test Tool Window
- [ ] Open AgentJar tool window
  - View → Tool Windows → AgentJar
  - Should show welcome message

### Test Actions
- [ ] Test "Open Agent Chat"
  - Tools → AgentJar → Open Agent Chat
  - Should activate tool window

- [ ] Test "Scaffold Spring Boot"
  - Create new project
  - Tools → AgentJar → Scaffold Spring Boot
  - Should create application.properties and DemoApplication.java

- [ ] Test "Conventional Commit"
  - Make some file changes
  - Tools → AgentJar → Conventional Commit
  - Should prompt for type, scope, description
  - Should execute git commit

## Gradle Deprecation Check

### Detailed Warning Check
- [ ] Run with maximum warnings
  ```bash
  ./gradlew build --warning-mode all --stacktrace 2>&1 | tee warnings.log
  ```

- [ ] Check for Gradle 10 deprecations
  ```bash
  grep -i "gradle 10" warnings.log
  # Should return no results
  ```

- [ ] Check for deprecated APIs
  ```bash
  grep -i "deprecated" warnings.log
  # Should return no results
  ```

### Verification Output
Expected output:
```
BUILD SUCCESSFUL in Xs
```

No lines containing:
- "Deprecated Gradle features"
- "incompatible with Gradle 10"
- "deprecated API"

## Additional Verification

### Plugin Verification
- [ ] Run plugin verifier
  ```bash
  ./gradlew verifyPlugin
  ```

### Distribution Build
- [ ] Create distribution
  ```bash
  ./gradlew buildPlugin
  ```

- [ ] Check distribution created
  ```bash
  ls -lh build/distributions/agentjar-plugin-0.0.1.zip
  ```

### Performance Check
- [ ] Incremental build speed
  ```bash
  time ./gradlew build
  # Should complete in < 1 minute after first build
  ```

## Documentation Verification

### Files Present
- [x] README.md
- [x] QUICKSTART.md
- [x] BUILDING.md
- [x] GRADLE-DEPRECATION-FIX.md
- [x] SOLUTION-SUMMARY.md
- [x] This file (VERIFICATION-CHECKLIST.md)

### Content Quality
- [x] README has feature list
- [x] QUICKSTART has common commands
- [x] BUILDING has detailed build info
- [x] GRADLE-DEPRECATION-FIX explains the solution
- [x] SOLUTION-SUMMARY covers everything

## Common Issues

### Issue: Build fails with network error
**Check**: Internet connectivity
```bash
curl -I https://services.gradle.org
```

**Solution**: Ensure network access to gradle.org and jetbrains.com

### Issue: Java version error
**Check**: Java version
```bash
java -version
```

**Solution**: Install Java 17 or higher

### Issue: Permission denied on gradlew
**Check**: File permissions
```bash
ls -l gradlew
```

**Solution**: Make executable
```bash
chmod +x gradlew
```

### Issue: Gradle daemon issues
**Solution**: Stop and restart
```bash
./gradlew --stop
./gradlew build
```

## Success Criteria

All of the following must be true:

✅ Build completes successfully
✅ No deprecation warnings in output
✅ No "incompatible with Gradle 10" messages
✅ JAR file created in build/libs/
✅ Plugin runs in IntelliJ sandbox
✅ Tool window displays correctly
✅ All three actions execute

## Final Verification

Once all checks pass:

```bash
echo "✅ AgentJar Plugin Verification Complete"
echo "✅ Gradle 8.7 configured"
echo "✅ No deprecation warnings"
echo "✅ Ready for development"
```

## Next Steps After Verification

1. ✅ Gradle deprecation issue resolved
2. → Implement chat UI
3. → Add agent backend abstraction
4. → Integrate Docker support
5. → Add GitHub integration
6. → Add security features (diff preview, permissions)
7. → Create branded IDE distribution

## Notes

- First build takes 5-10 minutes (downloads SDK)
- Subsequent builds take ~30 seconds
- Keep build log files for troubleshooting
- Run verification after any build configuration changes

---

**Last Updated**: Based on Gradle 8.7 configuration
**Status**: Ready for verification (requires connectivity)
