@echo off
echo Setting up Gradle Wrapper...

if not exist gradle (
    mkdir gradle
)

echo Gradle wrapper files should be created by Android Studio or manually.
echo For now, you can use existing Gradle installation.

echo.
echo To build without Gradle Wrapper:
echo 1. Install Gradle: https://gradle.org/install/
echo 2. Run: gradle wrapper
echo 3. Then run: gradlew.bat assembleDebug

pause
