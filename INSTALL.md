# 📱 Как установить приложение БЕЗ Android Studio

## ⚡ Самый быстрый способ (5 минут)

### Шаг 1: GitHub
1. Создайте аккаунт на https://github.com (если нет)
2. Нажмите "New repository"
3. Назовите "AndroidMusic"
4. Нажмите "Create repository"

### Шаг 2: Загрузите код
В терминале PowerShell:
```powershell
git init
git add .
git commit -m "Music Player"
git branch -M main
git remote add origin https://github.com/ваш_username/AndroidMusic.git
git push -u origin main
```

### Шаг 3: Соберите APK
1. На GitHub откройте вкладку **Actions**
2. Справа **"Run workflow"**
3. Нажмите кнопку **"Run workflow"**
4. Дождитесь завершения (~2 минуты)

### Шаг 4: Скачайте APK
1. В разделе Actions выберите выполненную задачу
2. Прокрутите вниз до **"Artifacts"**
3. Нажмите **"app-debug.apk"**
4. Распакуйте скачанный файл
5. Нажмите **"app-debug.apk"** внутри

### Шаг 5: Установите на телефон
**Вариант A - через USB:**
```powershell
adb install app-debug.apk
```

**Вариант B - вручную:**
1. Скопируйте APK на телефон
2. Настройки → Безопасность → Включите "Неизвестные источники"
3. Откройте APK и установите

---

## ✅ Альтернативные способы

### Способ 1: Локальная сборка

**Если у вас уже установлен Java:**

```powershell
# 1. Установите Gradle
# Скачайте с: https://gradle.org/install/

# 2. Создайте wrapper
gradle wrapper --gradle-version=8.2

# 3. Соберите APK
.\gradlew.bat assembleDebug

# 4. APK готов!
# Путь: app\build\outputs\apk\debug\app-debug.apk
```

### Способ 2: Онлайн-сборка

**Codemagic.io:**
- Зарегистрируйтесь на https://codemagic.io
- Подключите GitHub
- Соберите за 1 клик

**AppCenter:**
- https://appcenter.ms
- Автоматическая сборка

---

## 🎯 Краткая памятка

```
GitHub Actions (рекомендуется):
git push → Actions → Run workflow → Download APK

Локально:
gradle wrapper → gradlew assembleDebug → APK готов
```

---

## 🆘 Проблемы?

**"Не могу скачать с GitHub"**  
Решение: Используйте ZIP и сборку через https://codemagic.io

**"APK не устанавливается"**  
Решение: Настройки → Разрешить неизвестные источники

**"Ошибка сборки"**  
Решение: В GitHub Actions → Откройте workflow → Проверьте логи

---

**Готово! 🎉**

Подробности в файлах:
- [quick-start.md](quick-start.md) - Быстрые инструкции
- [BUILD.md](BUILD.md) - Детальная инструкция по сборке
- [DEBUG.md](DEBUG.md) - Отладка приложения
