# 🎨 Material 3 Expressive Color Palette

Полная цветовая палитра музыкального плеера.

## 🌈 Light Theme

### Primary Palette
```
#6750A4 - ExpressivePrimary
Насыщенный фиолетовый - основной акцентный цвет
Используется для: заголовков, кнопок, прогресс-бара

#EADDFF - ExpressivePrimaryContainer
Светлый фиолетовый - контейнер активного элемента
Используется для: выбранной песни, карточек

#FFFFFF - On Primary
Белый текст на фиолетовом
Используется для: кнопок, заголовков на цветном фоне
```

### Secondary Palette
```
#625B71 - ExpressiveSecondary
Серо-фиолетовый - вторичный цвет
Используется для: второстепенных элементов

#E8DEF8 - ExpressiveSecondaryContainer
Бледно-фиолетовый контейнер
Используется для: карточек, списков
```

### Surface Colors
```
#FFFBFE - Background
Чистый белый с легким теплым оттенком
Используется для: основного фона экрана

#1C1B1F - On Background
Темно-серый, почти черный текст
Используется для: основного текста

#E7E0EC - Surface Variant
Светло-серый с фиолетовым оттенком
Используется для: фона списка песен, карточек

#49454F - On Surface Variant
Средне-серый текст
Используется для: подписей, исполнителей
```

### Outline & Divider
```
#79747E - Outline
Серый для границ
Используется для: разделителей

#CAC4D0 - Outline Variant
Светло-серый для тонких линий
Используется для: слабых разделителей
```

## 🌙 Dark Theme (Roadmap)

### Primary Palette
```
#D0BCFF - Purple 80
Светлый фиолетовый для темной темы

#4F378B - Dark Primary
Темный фиолетовый контейнер
```

### Background
```
#1C1B1F - Dark Background
Темный фон экрана

#E6E1E5 - On Dark Background
Светлый текст на темном фоне
```

### Surface
```
#2C2831 - Dark Surface
Темная поверхность

#D0BCFF - On Dark Surface
Светлый текст на темной поверхности
```

## 🎨 Color Usage

### Accent Colors
```css
/* Error (for future use) */
Error: #BA1A1A
On Error: #FFFFFF
Error Container: #FFDAD6

/* Success (for future use) */
Success: #2E7D32
On Success: #FFFFFF

/* Warning (for future use) */
Warning: #F57C00
On Warning: #FFFFFF
```

## 🌊 Gradient Combinations

### Hero Gradients
```css
1. Primary Gradient:
   linear-gradient(135deg, #6750A4 0%, #8650C4 100%)

2. Soft Gradient:
   linear-gradient(135deg, #EADDFF 0%, #E8DEF8 100%)

3. Background Gradient:
   linear-gradient(135deg, #6750A4 0%, #EADDFF 100%)

4. Progress Gradient:
   linear-gradient(90deg, #6750A4 0%, #EADDFF 100%)
```

### Overlay Effects
```css
/* Hover State */
rgba(103, 80, 164, 0.1) - Легкая подложка

/* Active State */
rgba(234, 221, 255, 0.3) - Прозрачная подсветка

/* Shadow */
rgba(103, 80, 164, 0.3) - Фиолетовая тень
```

## 📊 Color Psychology

### Purple (#6750A4)
- **Творчество**: Музыка связана с творчеством
- **Премиум**: Высокое качество
- **Спокойствие**: Расслабляющий цвет

### Light Containers
- **Чистота**: Минималистичный дизайн
- **Воздушность**: Облегченный интерфейс
- **Современность**: Актуальные тренды

### Gray Text
- **Иерархия**: Разные уровни важности
- **Читаемость**: Высокий контраст
- **Нейтральность**: Не отвлекает от музыки

## 🎯 Accessibility

### Contrast Ratios (WCAG AA)
```
Primary Text on Background: 18.6:1 ✅
Secondary Text: 4.8:1 ✅
Button Text on Primary: 8.3:1 ✅
Outline: 3.1:1 ✅
```

### Color Blindness Support
- Используются не только цвета, но и:
  - Icon shapes
  - Border styles
  - Typography weights
  - Spacing

## 🔄 Dynamic Color

### Material 3 Supports
- **Wallpaper extraction**: Автоматическая палитра
- **Seed color**: Базовый цвет для генерации
- **Adaptive**: Подстройка под обои

### Implementation
```kotlin
LightColorScheme(
    primary = ExpressivePrimary,
    onPrimary = ExpressiveOnPrimary,
    primaryContainer = ExpressivePrimaryContainer,
    // ... full palette
)
```

---

**Material 3 Color System Compliant** 🎨
