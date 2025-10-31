# 🛠️ Сборка приложения БЕЗ Android Studio

## 📋 Что понадобится:

1. **Java JDK 17** или выше
2. **Android SDK** (командные инструменты)
3. **Gradle** (будет скачан автоматически)

---

## 🚀 Быстрая установка

### Windows

#### 1. Установить Java JDK
```powershell
# Скачать с сайта Oracle или OpenJDK
# https://adoptium.net/
# Установить и добавить в PATH
```

Проверка:
```powershell
java -version
javac -version
```

#### 2. Установить Android SDK
```powershell
# Скачать Android SDK Command Line Tools
# https://developer.android.com/studio#command-tools

# Распаковать в C:\Android\sdk
# Создать переменную среды ANDROID_HOME = C:\Android\sdk
```

Добавить в PATH:
```
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\cmdline-tools\latest\bin
```

Установить SDK:
```powershell
sdkmanager.bat "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

#### 3. Создать local.properties
Создайте файл `local.properties` в корне проекта:
```properties
sdk.dir=C:\\Android\\sdk
```

---

## 🔨 Сборка APK

### Сборка debug APK
```powershell
# Windows
.\gradlew.bat assembleDebug

# APK будет здесь:
# app\build\outputs\apk\debug\app-debug.apk
```

### Сборка release APK
```powershell
.\gradlew.bat assembleRelease

# APK будет здесь:
# app\build\outputs\apk\release\app-release.apk
```

---

## 📱 Установка на телефон

### Через ADB (рекомендуется)

1. **Включите режим разработчика на телефоне**
2. **Подключите USB и разрешите отладку**
3. **Установите APK:**

```powershell
# Установить debug версию
.\gradlew.bat installDebug

# Или вручную
adb install app\build\outputs\apk\debug\app-debug.apk
```

---

## 🔧 Альтернативный способ: онлайн-сборка

### GitHub Actions (бесплатно)

Создайте файл `.github/workflows/build.yml`:

```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Setup Java
      uses: actions/setup-java@v3
      with:
        distribution: 'temurin'
        java-version: '17'
        
    - name: Setup Android SDK
      uses: android-actions/setup-android@v2
      
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Build with Gradle
      run: ./gradlew assembleDebug
      
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug.apk
        path: app/build/outputs/apk/debug/app-debug.apk
```

После сборки скачайте APK из Actions tab на GitHub.

---

## 🌐 Онлайн-компиляторы

### AppCenter (Microsoft)
- https://appcenter.ms/
- Подключите GitHub репозиторий
- Автоматическая сборка при каждом push

### Codemagic
- https://codemagic.io/
- Бесплатный план для open-source проектов

### Bitrise
- https://www.bitrise.io/
- 200 минут сборки в месяц бесплатно

---

## 🔄 Полный workflow без IDE

### 1. Клонировать проект
```powershell
git clone <your-repo>
cd AndroidMusic
```

### 2. Настроить SDK
Создайте `local.properties`:
```properties
sdk.dir=C:\\Путь\\К\\SDK
```

### 3. Собрать APK
```powershell
.\gradlew.bat assembleDebug
```

### 4. Установить на телефон
```powershell
adb devices  # Проверить устройство
adb install app\build\outputs\apk\debug\app-debug.apk
```

### 5. Запустить приложение
```powershell
adb shell am start -n com.androidmusic.player/.MainActivity
```

### 6. Посмотреть логи
```powershell
adb logcat | findstr MusicPlayer
```

---

## 🐛 Отладка без IDE

### Логи в реальном времени
```powershell
adb logcat
```

### Сохранить логи в файл
```powershell
adb logcat > log.txt
```

### Фильтровать по тегу
```powershell
adb logcat -s MusicPlayerViewModel
adb logcat *:E  # Только ошибки
```

### Очистить логи
```powershell
adb logcat -c
```

---

## 🎯 Полезные команды Gradle

```powershell
# Сборка всех вариантов
.\gradlew.bat build

# Очистка проекта
.\gradlew.bat clean

# Установка и запуск
.\gradlew.bat installDebug

# Проверка зависимостей
.\gradlew.bat dependencies

# Линтинг кода
.\gradlew.bat lint

# Собрать и подписать release
.\gradlew.bat assembleRelease
```

---

## 📦 Подписание APK для релиза

### Создать keystore
```powershell
keytool -genkey -v -keystore music-player.keystore -alias music -keyalg RSA -keysize 2048 -validity 10000
```

### Создать keystore.properties
```properties
storePassword=ваш_пароль
keyPassword=ваш_пароль
keyAlias=music
storeFile=music-player.keystore
```

Добавьте в `app/build.gradle.kts`:
```kotlin
android {
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
            
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }
    
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

---

## 🚀 Автоматизация (скрипт для Windows)

Создайте `build-and-install.bat`:

```batch
@echo off
echo Building APK...
call gradlew.bat assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo Build successful!
    echo Installing on device...
    call gradlew.bat installDebug
    
    if %ERRORLEVEL% EQU 0 (
        echo Installation successful!
        echo Starting app...
        adb shell am start -n com.androidmusic.player/.MainActivity
    ) else (
        echo Installation failed!
    )
) else (
    echo Build failed!
)

pause
```

Запуск:
```powershell
.\build-and-install.bat
```

---

## 📊 Размер APK

После сборки:
```powershell
# Проверить размер
ls app\build\outputs\apk\debug\app-debug.apk
```

Уменьшение размера:
```powershell
# Включить ProGuard (сломает код, нужна настройка)
.\gradlew.bat assembleRelease
```

---

## ✅ Чеклист

- [ ] Java JDK установлен и в PATH
- [ ] Android SDK установлен
- [ ] ANDROID_HOME настроен
- [ ] local.properties создан
- [ ] gradlew имеет права на выполнение
- [ ] USB отладка включена на телефоне
- [ ] adb devices показывает устройство

---

## 🆘 Решение проблем

### "gradlew: command not found"
Windows: `gradlew.bat` вместо `gradlew`

### "SDK not found"
Проверьте `local.properties` и путь к SDK

### "Build failed"
```powershell
.\gradlew.bat clean
.\gradlew.bat build --stacktrace
```

### Разрешения Gradle
```powershell
# Linux/Mac
chmod +x gradlew

# Windows - не требуется
```

---

## 🎉 Готово!

Теперь вы можете собирать и устанавливать приложение без Android Studio!

**Быстрый старт:**
```powershell
.\gradlew.bat assembleDebug
adb install app\build\outputs\apk\debug\app-debug.apk
```
