@echo off
chcp 65001 >nul
echo ========================================
echo   Установка Gradle Wrapper для Windows
echo ========================================
echo.

REM Проверить Java
echo [1/5] Проверка Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java не установлена!
    echo.
    echo Установите Java JDK 17 или выше:
    echo https://adoptium.net/
    echo.
    pause
    exit /b 1
)
echo ✅ Java установлена

REM Проверить Gradle Wrapper JAR
echo.
echo [2/5] Проверка Gradle Wrapper...
if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo Скачивание gradle-wrapper.jar...
    powershell -Command "Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'"
    
    if not exist "gradle\wrapper\gradle-wrapper.jar" (
        echo ❌ Не удалось скачать gradle-wrapper.jar
        echo Скачайте вручную с: https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar
        echo И поместите в: gradle\wrapper\gradle-wrapper.jar
        pause
        exit /b 1
    )
    echo ✅ gradle-wrapper.jar скачан
) else (
    echo ✅ gradle-wrapper.jar найден
)

REM Проверить gradlew.bat
echo.
echo [3/5] Проверка gradlew.bat...
if not exist "gradlew.bat" (
    echo ❌ gradlew.bat не найден!
    pause
    exit /b 1
)
echo ✅ gradlew.bat найден

REM Тестовая сборка
echo.
echo [4/5] Первая инициализация Gradle...
call gradlew.bat --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  Первая инициализация может занять время...
    echo Загрузка Gradle 8.2...
    call gradlew.bat --version
)
echo ✅ Gradle готов

echo.
echo [5/5] Готово!
echo ========================================
echo.
echo Теперь можно собирать проект:
echo   gradlew.bat assembleDebug
echo.
echo Или установить на телефон:
echo   gradlew.bat installDebug
echo.
pause
