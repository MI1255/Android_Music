# 📐 Design Specifications

Технические спецификации дизайна музыкального плеера.

## 📱 Размеры экрана

- **Разрешение**: 375 x 812 px (iPhone стандарт)
- **Соотношение**: 19.5:9
- **Safe Area**: 0 (без вырезов)

## 🎨 Цвета Material 3 Expressive

### Primary Colors
```css
Primary: #6750A4 (RGB: 103, 80, 164)
On Primary: #FFFFFF (RGB: 255, 255, 255)
Primary Container: #EADDFF (RGB: 234, 221, 255)
On Primary Container: #21005D (RGB: 33, 0, 93)
```

### Secondary Colors
```css
Secondary: #625B71 (RGB: 98, 91, 113)
On Secondary: #FFFFFF
Secondary Container: #E8DEF8 (RGB: 232, 222, 248)
On Secondary Container: #1D192B (RGB: 29, 25, 43)
```

### Surface Colors
```css
Background: #FFFBFE (RGB: 255, 251, 254)
On Background: #1C1B1F (RGB: 28, 27, 31)
Surface: #FFFBFE
On Surface: #1C1B1F
Surface Variant: #E7E0EC (RGB: 231, 224, 236)
On Surface Variant: #49454F (RGB: 73, 69, 79)
```

### Outline Colors
```css
Outline: #79747E (RGB: 121, 116, 126)
Outline Variant: #CAC4D0 (RGB: 202, 196, 208)
```

## 📐 Spacing System

Material 3 использует 4px grid:

```css
4px   /* 1 unit */
8px   /* 2 units */
12px  /* 3 units */
16px  /* 4 units */
20px  /* 5 units */
24px  /* 6 units */
32px  /* 8 units */
40px  /* 10 units */
48px  /* 12 units */
64px  /* 16 units */
```

## 🔲 Border Radius

Material 3 использует переменные радиусы:

```css
Extra Small: 4px
Small: 8px
Medium: 12px
Large: 16px
X Large: 28px
```

## ✏️ Typography Scale

```css
/* Display */
Display Large:   57px / 64px / -0.25px
Display Medium:  45px / 52px / 0px
Display Small:   36px / 44px / 0px

/* Headline */
Headline Large:  32px / 40px / 0px
Headline Medium: 28px / 36px / 0px
Headline Small:  24px / 32px / 0px

/* Title */
Title Large:     22px / 28px / 0px
Title Medium:    16px / 24px / 0.15px
Title Small:     14px / 20px / 0.1px

/* Body */
Body Large:      16px / 24px / 0.5px
Body Medium:     14px / 20px / 0.25px
Body Small:      12px / 16px / 0.4px

/* Label */
Label Large:     14px / 20px / 0.1px
Label Medium:    12px / 16px / 0.5px
Label Small:     11px / 16px / 0.5px
```

## 🎭 Elevation System

```css
Level 0: 0px shadow
Level 1: 0px 1px 2px rgba(0,0,0,0.3)
Level 2: 0px 2px 6px rgba(0,0,0,0.15)
Level 3: 0px 4px 8px rgba(0,0,0,0.2)
Level 4: 0px 8px 16px rgba(0,0,0,0.15)
Level 5: 0px 16px 24px rgba(0,0,0,0.15)
```

## 🎨 Gradients

### Header Gradient
```css
background: linear-gradient(135deg, #6750A4 0%, #8650C4 100%);
```

### Background Gradient
```css
background: linear-gradient(135deg, #6750A4 0%, #EADDFF 100%);
```

### Progress Bar
```css
background: linear-gradient(90deg, #6750A4 0%, #EADDFF 100%);
```

### Container Gradient
```css
background: linear-gradient(135deg, #EADDFF 0%, #E8DEF8 100%);
```

## 🔘 Button Sizes

```css
Icon Button Small:  40px x 40px
Icon Button Medium: 48px x 48px
Icon Button Large:  64px x 64px
```

## 📏 Layout Specifications

### Header
- Height: 80px
- Padding: 24px
- Font Size: 32px

### Song Item
- Height: 72px (auto)
- Padding: 16px
- Border Radius: 16px
- Margin Bottom: 8px

### Mini Player
- Height: 200px
- Padding: 20px
- Border Radius: 24px (top corners)
- Elevation: 2

### Control Buttons
- Small: 48px x 48px
- Large: 64px x 64px
- Border Radius: 50%

## 🎬 Animation Specs

### Transitions
```css
Standard: 300ms ease
Decelerate: 300ms cubic-bezier(0.0, 0.0, 0.2, 1.0)
Accelerate: 300ms cubic-bezier(0.4, 0.0, 1.0, 1.0)
Sharp:    300ms cubic-bezier(0.4, 0.0, 0.6, 1.0)
```

### Common Animations
- Hover Transform: translateX(4px)
- Button Scale: scale(1.1)
- Progress Bar: width transition 300ms

## 🎯 Touch Targets

Minimum touch target: **48px x 48px**

- Song Item: Full width
- Control Buttons: 48px+
- Play Button: 64px
- Text Selectable: Always

---

**Material Design 3 Guidelines Compliant** ✅
