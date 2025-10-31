# 🚀 Быстрый старт БЕЗ Android Studio

## ✅ Вариант 1: Использовать готовый Gradle

### Шаг 1: Установить Gradle
Скачайте с https://gradle.org/install/

### Шаг 2: Создать Gradle Wrapper
```powershell
gradle wrapper --gradle-version=8.2
```

### Шаг 3: Собрать APK
```powershell
.\gradlew.bat assembleDebug
```

### Шаг 4: Установить на телефон
```powershell
# Убедитесь что ADB установлен
adb install app\build\outputs\apk\debug\app-debug.apk
```

---

## ✅ Вариант 2: GitHub Actions (рекомендуется!)

### Самый простой способ - использовать GitHub!

1. **Создайте репозиторий на GitHub**
2. **Загрузите код:**
   ```powershell
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/ваш_username/AndroidMusic.git
   git push -u origin main
   ```

3. **Создайте файл `.github/workflows/build.yml`:**
   ```yaml
   name: Build APK
   
   on:
     workflow_dispatch:
   
   jobs:
     build:
       runs-on: ubuntu-latest
       steps:
       - uses: actions/checkout@v3
       - uses: actions/setup-java@v3
         with:
           distribution: 'temurin'
           java-version: '17'
       - uses: android-actions/setup-android@v2
       - run: chmod +x gradlew
       - run: ./gradlew assembleDebug
       - uses: actions/upload-artifact@v3
         with:
           name: app-debug.apk
           path: app/build/outputs/apk/debug/app-debug.apk
   ```

4. **Запустите сборку:**
   - Перейдите в Actions на GitHub
   - Нажмите "Run workflow"
   - Дождитесь завершения
   - Скачайте APK из artifacts

**Готово! Теперь у вас есть APK без установки Android Studio!**

---

## ✅ Вариант 3: Онлайн сборка

### Codemagic.io (Бесплатно!)

1. Зарегистрируйтесь на https://codemagic.io/
2. Подключите GitHub репозиторий
3. Автоматическая настройка
4. Скачайте APK

### AppCenter.ms

1. Зарегистрируйтесь на https://appcenter.ms/
2. Создайте новый проект
3. Подключите GitHub
4. Скачайте готовый APK

---

## ✅ Вариант 4: VSCode + расширения

Если хотите редактировать код:

1. **Установите VSCode**
2. **Установите расширения:**
   - Kotlin
   - Gradle for Java
   - Android iOS Emulator

3. **Используйте терминал для сборки:**
   ```powershell
   code .  # Открыть в VSCode
   # В терминале:
   gradle wrapper
   .\gradlew.bat assembleDebug
   ```

---

## ⚡ Самый быстрый способ

### Для тех, кто хочет СЕЙЧАС:

1. **Откройте https://github.com/new**
2. **Создайте репозиторий**
3. **Загрузите код**
4. **Создайте файл `.github/workflows/build.yml`** (код выше)
5. **Нажмите Actions → Run workflow**
6. **Скачайте APK**

**Время: 5 минут!**

---

## 🎯 Краткая инструкция

### Если у вас уже есть Java и Android SDK:

```powershell
# 1. Создать wrapper
gradle wrapper

# 2. Собрать
.\gradlew.bat assembleDebug

# 3. Установить
adb install app\build\outputs\apk\debug\app-debug.apk
```

### Если ничего не установлено:

**Используйте GitHub Actions** - это самый простой путь!

---

## 📱 Что делать после получения APK?

1. **Перекиньте APK на телефон** (через USB, Bluetooth, или cloud)
2. **Включите "Установка из неизвестных источников"** в настройках
3. **Откройте APK** и установите
4. **Готово!**

---

## 🆘 Проблемы?

### "Gradle not found"
Решение: Используйте GitHub Actions

### "SDK not found"  
Решение: Используйте GitHub Actions

### "Build failed"
Решение: Проверьте логи, обычно проблемы с зависимостями

---

## 💡 Рекомендация

**Для новичков:** Используйте GitHub Actions - это самое простое решение

**Для продвинутых:** Локальная сборка через Gradle

**Для кодинга:** VSCode + расширения

---

Удачи! 🎉
