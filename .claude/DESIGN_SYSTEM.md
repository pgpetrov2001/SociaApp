# Design System & Principles
## Social Interaction Tracker App

---

## 🎨 Design Philosophy

**Core Vision**: A premium, data-focused habit tracking experience that feels calm, sophisticated, and motivating. The design language prioritizes visual hierarchy through elevation, subtle shadows, and careful typography rather than harsh lines and borders.

**Design Keywords**: 
- Dark & Modern
- Neumorphic & Glassy
- Minimal & Clean
- Data-Driven
- Premium Feel

**Inspiration References**:
- Apple Health / Fitness apps
- Streaks habit tracker
- GitHub contribution graph
- Wellness & analytics dashboards

---

## 🌙 Color System

### Base Colors

#### Background Palette
```kotlin
// Primary backgrounds
val BackgroundPrimary = Color(0xFF0A0A0A)      // Deep black
val BackgroundSecondary = Color(0xFF121212)    // Very dark gray
val BackgroundTertiary = Color(0xFF1A1A1A)     // Slightly lighter dark gray

// Gradient backgrounds
val BackgroundGradientStart = Color(0xFF0A0A0A)
val BackgroundGradientEnd = Color(0xFF1A1A1A)
```

#### Surface Colors (Cards & Containers)
```kotlin
// Neumorphic surfaces
val SurfaceElevated = Color(0xFF1E1E1E)        // Card backgrounds
val SurfaceElevatedHigher = Color(0xFF242424)  // Modal/sheet backgrounds
val SurfacePressed = Color(0xFF2A2A2A)         // Pressed state

// Glass/blur effect overlay
val SurfaceGlass = Color(0x33FFFFFF)           // 20% white overlay
val SurfaceGlassStrong = Color(0x4DFFFFFF)     // 30% white overlay
```

#### Text Colors
```kotlin
// Hierarchy
val TextPrimary = Color(0xFFFFFFFF)            // Pure white - headings, key info
val TextSecondary = Color(0xB3FFFFFF)          // 70% white - body text
val TextTertiary = Color(0x80FFFFFF)           // 50% white - labels
val TextMuted = Color(0x4DFFFFFF)              // 30% white - hints, disabled
```

### Accent Colors

#### Activity & Success (Green/Teal)
```kotlin
val ActivityLow = Color(0xFF1A4D3E)            // Low activity intensity
val ActivityMedium = Color(0xFF2A7C5F)         // Medium intensity
val ActivityHigh = Color(0xFF39D98A)           // High intensity
val ActivityMax = Color(0xFF06FFA5)            // Maximum intensity

val Success = Color(0xFF39D98A)                // Goal completion, success states
val SuccessDim = Color(0x3339D98A)             // Success backgrounds (20% opacity)
```

#### Streak & Motivation (Orange/Fire)
```kotlin
val StreakPrimary = Color(0xFFFF6B35)          // Streak indicator, fire icon
val StreakSecondary = Color(0xFFFF9F1C)        // Streak accents, highlights
val StreakGlow = Color(0x33FF6B35)             // Glow effect (20% opacity)
val StreakGradientStart = Color(0xFFFF6B35)
val StreakGradientEnd = Color(0xFFFFBE0B)      // Gold gradient end
```

#### Rating Colors (1-10 Scale)
```kotlin
val RatingLow = Color(0xFFFF4444)              // 1-3 rating
val RatingMedium = Color(0xFFFFBE0B)           // 4-7 rating
val RatingHigh = Color(0xFF39D98A)             // 8-10 rating
```

#### Semantic Colors
```kotlin
val Error = Color(0xFFFF4444)                  // Errors, warnings
val Info = Color(0xFF4A9FFF)                   // Information, hints
val Warning = Color(0xFFFFBE0B)                // Warnings, alerts
```

### Color Usage Guidelines

**Activity Intensity Mapping** (for heatmap dots):
- No activity: `BackgroundTertiary` (dark gray)
- 1-2 interactions: `ActivityLow`
- 3-4 interactions: `ActivityMedium`
- 5-7 interactions: `ActivityHigh`
- 8+ interactions: `ActivityMax`

**When to Use Each Accent**:
- **Green/Teal**: Daily counts, activity metrics, completion states, progress indicators
- **Orange**: Streak badges, fire icons, milestone celebrations, motivation elements
- **Blue**: Informational elements, help text, secondary actions
- **Red**: Errors, streak breaks, deletion confirmations

---

## 📐 Spacing & Layout

### Spacing Scale
```kotlin
object Spacing {
    val xxs = 2.dp
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
    val xxxl = 64.dp
}
```

### Common Spacing Patterns
- **Screen padding**: 16dp horizontal, 24dp vertical (top)
- **Card padding**: 16dp all sides
- **Between cards**: 16dp vertical gap
- **Between elements within cards**: 12dp
- **Between sections**: 32dp
- **Bottom nav clearance**: 80dp (for FAB)

### Corner Radius
```kotlin
object CornerRadius {
    val sm = 8.dp      // Small elements, tags
    val md = 12.dp     // Buttons, input fields
    val lg = 16.dp     // Cards, containers
    val xl = 24.dp     // Large cards, modals
    val xxl = 32.dp    // Bottom sheets
    val circle = 999.dp // Circular elements
}
```

**Usage**:
- All cards: `16.dp` radius
- All buttons: `12.dp` radius
- Input fields: `12.dp` radius
- Bottom sheets: `24.dp` top corners only
- FAB: Circular (`999.dp`)

### Elevation & Shadows

#### Neumorphic Shadow System
```kotlin
// Light shadow (top-left)
val shadowLight = Shadow(
    color = Color(0x1AFFFFFF),  // 10% white
    offset = Offset(-4f, -4f),
    blurRadius = 8f
)

// Dark shadow (bottom-right)
val shadowDark = Shadow(
    color = Color(0x66000000),  // 40% black
    offset = Offset(4f, 4f),
    blurRadius = 12f
)

// Combined neumorphic effect
fun neumorphicModifier() = Modifier
    .drawBehind {
        // Apply both shadows
    }
```

#### Elevation Levels
- **Level 0** (Flat): No shadow - background elements
- **Level 1** (Cards): Subtle neumorphic shadow - main cards
- **Level 2** (Raised): More pronounced - pressed buttons, active states
- **Level 3** (Floating): Strong shadow - FAB, tooltips
- **Level 4** (Modal): Heaviest - modal dialogs, sheets

**Compose Shadow Values**:
```kotlin
object Elevation {
    val none = 0.dp
    val card = 4.dp
    val button = 2.dp
    val fab = 6.dp
    val modal = 8.dp
}
```

---

## ✍️ Typography

### Font Family
**Primary Font**: System default (Roboto on Android)
- Alternative: Inter, SF Pro, Manrope (if custom fonts)

### Type Scale

```kotlin
object Typography {
    // Display - Hero numbers, main metrics
    val displayLarge = TextStyle(
        fontSize = 72.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 80.sp,
        letterSpacing = (-0.5).sp
    )
    
    val displayMedium = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 56.sp
    )
    
    val displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp
    )
    
    // Headings
    val headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    )
    
    val headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp
    )
    
    val headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp
    )
    
    // Body
    val bodyLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 26.sp
    )
    
    val bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    )
    
    val bodySmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    )
    
    // Labels
    val labelLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    
    val labelMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp
    )
    
    val labelSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
}
```

### Typography Usage Guidelines

- **Display**: Main counters, hero numbers (Today: 5/7)
- **Headline Large**: Screen titles ("Social Tracker")
- **Headline Medium**: Section headers ("This Month", "Activity History")
- **Headline Small**: Card titles
- **Body Large**: Primary content, descriptions
- **Body Medium**: Standard body text
- **Body Small**: Helper text, descriptions
- **Label Large**: Button text, important labels
- **Label Medium**: Input labels, tab labels
- **Label Small**: Captions, footnotes, timestamps

### Text Hierarchy Principles

1. **Numbers First**: Metrics should be larger than their labels
   ```
   72sp - "5"  (the count)
   14sp - "interactions today" (the label)
   ```

2. **High Contrast**: Always use `TextPrimary` for key information
3. **Muted Labels**: Use `TextTertiary` or `TextMuted` for labels/descriptions
4. **Spacing Over Size**: Use whitespace to create hierarchy alongside size

---

## 🎯 Component Design Specifications

### 1. Authentication Screen

#### Layout Structure
```
Screen Layout:
┌─────────────────────────────┐
│                             │
│     [Logo/Title]            │ ← displayMedium, centered
│                             │
│   ┌───────────────────┐     │
│   │  [Tab: Log In]    │     │ ← Tab switcher
│   │  [Tab: Sign Up]   │     │
│   └───────────────────┘     │
│                             │
│   ┌───────────────────┐     │
│   │ Email             │     │ ← Input field
│   └───────────────────┘     │
│   ┌───────────────────┐     │
│   │ Password          │     │
│   └───────────────────┘     │
│                             │
│   ┌───────────────────┐     │
│   │   Continue        │     │ ← Primary button
│   └───────────────────┘     │
│                             │
│        ─── OR ───           │
│                             │
│   ┌───────────────────┐     │
│   │ 🍎 Apple          │     │ ← Secondary auth buttons
│   └───────────────────┘     │
│   ┌───────────────────┐     │
│   │ 🔵 Google         │     │
│   └───────────────────┘     │
│                             │
└─────────────────────────────┘
```

#### Specifications
- **Container**: Floating card, `lg` corner radius, neumorphic shadow
- **Card Background**: `SurfaceElevated`
- **Padding**: 24dp all sides
- **Vertical spacing**: 16dp between elements
- **Tab Switcher**: Rounded pill toggle, active tab = white background
- **Input Fields**: 
  - Height: 56dp
  - Corner radius: `md` (12dp)
  - Background: `SurfaceElevatedHigher`
  - Placeholder: `TextMuted`
  - Text: `TextPrimary`
- **Primary Button**:
  - Height: 56dp
  - Background: `TextPrimary` (white)
  - Text: `BackgroundPrimary` (black)
  - Corner radius: `md`
- **Secondary Buttons**:
  - Height: 48dp
  - Background: `SurfaceElevatedHigher`
  - Border: 1dp `TextMuted`
  - Text: `TextPrimary`

---

### 2. Dashboard (Home Screen)

#### Layout Structure
```
┌─────────────────────────────┐
│ [🔥 3]  Social Tracker  [⚙️] │ ← Header
├─────────────────────────────┤
│                             │
│  ┌─────────────────────┐    │
│  │      TODAY          │    │ ← Today card
│  │   ⭕ 5/7            │    │   (circular progress)
│  │                     │    │
│  └─────────────────────┘    │
│                             │
│  ┌─────────────────────┐    │
│  │  THIS WEEK          │    │ ← Weekly card
│  │  📊 [mini chart]    │    │
│  │  Total: 28          │    │
│  └─────────────────────┘    │
│                             │
│  ┌─────────────────────┐    │
│  │  ACTIVITY HISTORY   │    │ ← Heatmap card
│  │  [GitHub dots]      │    │
│  │  121 active days    │    │
│  └─────────────────────┘    │
│                             │
│  ┌─────────────────────┐    │
│  │  THIS MONTH         │    │ ← Monthly card
│  │  87 interactions    │    │
│  │  ↑ 24% from last    │    │
│  └─────────────────────┘    │
│                             │
│            [+]              │ ← FAB
└─────────────────────────────┘
```

#### Header Specifications
- Height: 64dp
- Padding: 16dp horizontal
- Background: Transparent or very subtle gradient
- **Title**: `headlineMedium`, `TextPrimary`
- **Streak Badge** (left):
  - Size: 40x40dp circle
  - Background: `StreakGlow`
  - Border: 2dp `StreakPrimary`
  - Icon: 🔥 emoji or fire icon
  - Number: `labelMedium`, `StreakPrimary`
- **Settings Icon** (right):
  - Size: 40x40dp circle
  - Background: `SurfaceElevated`
  - Icon: gear/cog, `TextTertiary`

#### Card Specifications (Generic)
- Width: Screen width - 32dp (16dp padding each side)
- Corner radius: `lg` (16dp)
- Background: `SurfaceElevated`
- Elevation: Neumorphic shadow
- Padding: 20dp all sides
- Margin bottom: 16dp

#### Today Card (Circular Progress)
- **Progress Ring**:
  - Size: 160dp diameter
  - Stroke width: 12dp
  - Background track: `SurfaceElevatedHigher`
  - Progress: `ActivityHigh` (or gradient)
  - Centered in card
- **Center Content**:
  - Main number: `displayMedium` (48sp), `TextPrimary`
  - Slash: `headlineSmall`, `TextTertiary`
  - Goal number: `headlineMedium`, `TextSecondary`
  - Label: "interactions today", `labelSmall`, `TextMuted`

#### Weekly Card (Mini Chart)
- **Header**: "THIS WEEK", `labelLarge`, `TextSecondary`
- **Chart**: Simple bar chart (7 bars for days)
  - Bar height: Variable based on value
  - Bar width: 8dp
  - Bar spacing: 4dp
  - Bar color: `ActivityHigh` with opacity based on value
  - Today's bar: Full opacity
- **Total**: `displaySmall`, `TextPrimary`
- **Label**: "Total interactions", `labelMedium`, `TextMuted`

#### Activity History Card (Heatmap)
- **Header**: "ACTIVITY HISTORY", `labelLarge`, `TextSecondary`
- **Heatmap**:
  - Dot size: 8x8dp
  - Dot spacing: 4dp horizontal, 3dp vertical
  - Dot corner radius: 2dp (rounded squares)
  - Color mapping: See "Color Usage Guidelines"
  - Layout: 7 rows (days of week) x columns (weeks)
  - Timeline labels: "3 months ago" → "Today", `labelSmall`, `TextMuted`
- **Summary**: "121 active days", `bodyMedium`, `TextSecondary`, centered below

#### Monthly Card
- **Header**: "THIS MONTH", `labelLarge`, `TextSecondary`
- **Main Number**: `displayLarge` (72sp), `TextPrimary`
- **Label**: "interactions", `bodySmall`, `TextMuted`
- **Comparison**:
  - Arrow: ↑ or ↓, colored (`Success` or `Error`)
  - Percentage: `bodyLarge`, same color as arrow
  - Text: "from last month", `bodySmall`, `TextMuted`

#### FAB (Floating Action Button)
- Size: 64x64dp
- Position: Bottom center, 24dp from bottom
- Shape: Perfect circle
- Background: `Success` (or primary accent)
- Icon: "+" symbol, 32dp, white
- Elevation: `fab` (6dp)
- Ripple effect on press
- Haptic feedback on tap

---

### 3. Activity Detail Modal / Bottom Sheet

#### Layout Structure
```
┌─────────────────────────────┐
│       [Handle Bar]          │ ← Drag handle
├─────────────────────────────┤
│   Activity History          │ ← Header
│                             │
│  ┌─────┐ ┌─────┐ ┌─────┐    │
│  │ Dec │ │ Nov │ │ Oct │    │ ← Monthly mini heatmaps
│  │ ••• │ │ ••• │ │ ••• │    │
│  └─────┘ └─────┘ └─────┘    │
│                             │
│  ┌─────────┐ ┌─────────┐    │
│  │ 121     │ │ Dec 15  │    │ ← Stat cards (2x2 grid)
│  │ Active  │ │ Best day│    │
│  └─────────┘ └─────────┘    │
│  ┌─────────┐ ┌─────────┐    │
│  │ 87%     │ │ 14 days │    │
│  │Consiste │ │ Longest │    │
│  └─────────┘ └─────────┘    │
│                             │
│  [Legend: ▫️ ▪️ ▪️ ▪️ ▪️]     │ ← Color legend
│  Less ←───────→ More        │
│                             │
└─────────────────────────────┘
```

#### Bottom Sheet Specifications
- Background: `SurfaceElevatedHigher`
- Corner radius: `xxl` (32dp) top corners only
- Initial height: 60% of screen
- Drag handle:
  - Width: 48dp
  - Height: 4dp
  - Centered, 12dp from top
  - Color: `TextMuted`
  - Corner radius: 2dp
- Backdrop: 50% black overlay with blur (if supported)

#### Monthly Mini Heatmap
- Width: 80dp
- Height: 60dp
- Month label: `labelSmall`, `TextSecondary`, centered above
- Dots: 4x4dp, 2dp spacing
- Same color mapping as main heatmap

#### Stat Cards (Grid Items)
- Size: (Screen width - 48dp) / 2 - 8dp (for 2 columns with gap)
- Aspect ratio: 1:1 (square)
- Background: `SurfaceElevated`
- Corner radius: `md` (12dp)
- Padding: 16dp
- Layout:
  - Number: `displaySmall` (36sp), `TextPrimary`, top-aligned
  - Label: `labelMedium`, `TextTertiary`, bottom-aligned

#### Legend
- Position: Bottom of sheet, above padding
- Dots: 12x12dp each, 8dp spacing
- Labels: "Less" and "More", `labelSmall`, `TextMuted`
- Centered horizontally

---

### 4. Input Components

#### Text Input Field
```kotlin
// Specifications
height = 56.dp
cornerRadius = 12.dp
backgroundColor = SurfaceElevatedHigher
borderWidth = 0.dp (no border, only elevation)

// States
default: backgroundColor, no border
focused: backgroundColor + 2dp border in ActivityHigh
error: backgroundColor + 2dp border in Error
disabled: backgroundColor with 50% opacity

// Text
placeholder: TextMuted, bodyMedium
input: TextPrimary, bodyMedium
label: TextSecondary, labelMedium (above field)
helper/error: TextMuted or Error, labelSmall (below field)
```

#### Button Styles

**Primary Button** (CTA)
```kotlin
height = 56.dp
cornerRadius = 12.dp
backgroundColor = TextPrimary (white)
textColor = BackgroundPrimary (black)
textStyle = labelLarge, FontWeight.SemiBold
elevation = Elevation.button

// States
pressed: backgroundColor with 90% opacity
disabled: backgroundColor with 40% opacity
```

**Secondary Button** (Outline)
```kotlin
height = 48.dp
cornerRadius = 12.dp
backgroundColor = Transparent
borderWidth = 1.dp
borderColor = TextMuted
textColor = TextPrimary
textStyle = labelLarge
```

**Tertiary Button** (Text only)
```kotlin
height = 48.dp
backgroundColor = Transparent
textColor = ActivityHigh or StreakPrimary
textStyle = labelLarge, FontWeight.Medium
rippleColor = accent color with 20% opacity
```

**Icon Button**
```kotlin
size = 40.dp x 40.dp
cornerRadius = Circle (999.dp)
backgroundColor = SurfaceElevated
iconColor = TextSecondary
iconSize = 24.dp

// Pressed state
backgroundColor = SurfacePressed
```

---

### 5. Rating Input (1-10 Scale)

#### Option 1: Number Grid
```
Layout: 2 rows x 5 columns
┌───┬───┬───┬───┬───┐
│ 1 │ 2 │ 3 │ 4 │ 5 │
├───┼───┼───┼───┼───┤
│ 6 │ 7 │ 8 │ 9 │10 │
└───┴───┴───┴───┴───┘

Cell size: 64dp x 64dp
Gap: 8dp
Corner radius: 12dp
Background (unselected): SurfaceElevated
Background (selected): RatingHigh (or Low/Medium based on value)
Text: displaySmall, TextPrimary
```

#### Option 2: Slider with Labels
```
Slider track:
- Height: 6dp
- Active color: ActivityHigh
- Inactive color: SurfaceElevatedHigher
- Thumb size: 32dp circle
- Thumb color: White
- Steps: 10 (discrete positions)

Labels below:
- "😞 Poor" at 1
- "😐 Okay" at 5
- "😊 Great" at 10
- labelSmall, TextMuted
```

---

### 6. Notes Input

```kotlin
// Multiline text field
minHeight = 120.dp
maxHeight = 240.dp (scrollable beyond)
cornerRadius = 12.dp
backgroundColor = SurfaceElevated
padding = 16.dp

// Text
textColor = TextPrimary
textStyle = bodyMedium
placeholderColor = TextMuted

// Character counter
position = bottom right, inside padding
text = "125/500", labelSmall, TextMuted
```

---

## 🎭 Animations & Interactions

### Animation Principles
1. **Subtle & Fast**: Animations should be quick (150-300ms)
2. **Purposeful**: Every animation should have a reason
3. **Smooth**: Use easing curves, never linear
4. **Native Feel**: Follow platform conventions

### Animation Specifications

#### Button Press
```kotlin
duration = 100ms
scaleX/scaleY = 0.95
easing = FastOutSlowIn
```

#### FAB Press
```kotlin
duration = 150ms
scaleX/scaleY = 0.9
rotation = 45° (+ transforms to X when active)
easing = EaseInOutCubic
```

#### Counter Increment
```kotlin
duration = 300ms
effect = Slide up + fade in (new number)
        Slide up + fade out (old number)
easing = EaseOutCubic
haptic = Light impact
```

#### Card Appearance (Staggered)
```kotlin
duration = 400ms per card
stagger = 80ms between cards
effect = Fade in + slide up (from 32dp below)
easing = EaseOutQuart
```

#### Progress Ring Fill
```kotlin
duration = 800ms
effect = Sweep angle from 0° to target
easing = EaseOutCubic
```

#### Bottom Sheet
```kotlin
duration = 300ms
effect = Slide up from bottom
backdrop = Fade in simultaneously
easing = EaseOutQuint
```

#### Modal Appearance
```kotlin
duration = 250ms
effect = Scale from 0.9 to 1.0 + fade in
backdrop = Fade in
easing = EaseOutCubic
```

### Haptic Feedback
- **Button press**: Light impact
- **Counter increment**: Light impact
- **Goal reached**: Medium impact + success sound (optional)
- **Streak milestone**: Heavy impact + celebration sound (optional)
- **Error**: Error vibration pattern

---

## 📱 Responsive Design

### Screen Size Breakpoints
- **Small**: < 360dp width (compact phones)
- **Medium**: 360dp - 600dp (standard phones)
- **Large**: 600dp - 840dp (large phones, foldables)
- **Extra Large**: > 840dp (tablets)

### Responsive Adjustments

#### Small Screens
- Reduce padding to 12dp
- Smaller card sizes
- Stack cards vertically (no grid)
- Reduce font sizes slightly (90% of base)

#### Large Screens (Tablets)
- Max content width: 600dp, centered
- Or: Two-column layout for cards
- Increase padding to 24dp
- Use larger corner radius (20dp)

---

## ♿ Accessibility

### Requirements
1. **Minimum Touch Target**: 48dp x 48dp for all interactive elements
2. **Color Contrast**: Minimum 4.5:1 for body text, 3:1 for large text
3. **Screen Reader Support**: All elements labeled with contentDescription
4. **Focus Indicators**: Visible focus state for keyboard navigation
5. **Dynamic Type**: Support user font size preferences (up to 200%)

### High Contrast Mode
When system high contrast is enabled:
- Increase border width to 2dp
- Use solid colors instead of gradients
- Increase shadow intensity
- Ensure 7:1 contrast ratio

---

## 🔧 Implementation Guidelines

### Compose Implementation

#### Theme Setup
```kotlin
@Composable
fun SocialTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Success,
            secondary = StreakPrimary,
            tertiary = ActivityHigh,
            background = BackgroundPrimary,
            surface = SurfaceElevated,
            onPrimary = BackgroundPrimary,
            onSecondary = TextPrimary,
            onBackground = TextPrimary,
            onSurface = TextPrimary,
            error = Error
        ),
        typography = Typography(...),
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp)
        )
    ) {
        content()
    }
}
```

#### Neumorphic Card Component
```kotlin
@Composable
fun NeumorphicCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.3f),
                ambientColor = Color.White.copy(alpha = 0.1f)
            )
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        color = SurfaceElevated,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}
```

### XML Implementation (if not using Compose)

#### Card Style
```xml
<style name="Widget.SocialTracker.Card">
    <item name="android:background">@drawable/neumorphic_card_background</item>
    <item name="android:elevation">4dp</item>
    <item name="cornerRadius">16dp</item>
    <item name="contentPadding">20dp</item>
</style>
```

#### Neumorphic Drawable
```xml
<!-- res/drawable/neumorphic_card_background.xml -->
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/surface_elevated"/>
    <corners android:radius="16dp"/>
</shape>
```

---

## 📋 Component Checklist

When creating any new component, ensure:
- [ ] Follows dark mode color system
- [ ] Uses correct corner radius from scale
- [ ] Has neumorphic shadow/elevation
- [ ] Typography follows hierarchy
- [ ] Touch targets are minimum 48dp
- [ ] Has appropriate pressed/focused states
- [ ] Includes haptic feedback (if interactive)
- [ ] Has smooth animations (if applicable)
- [ ] Accessible (contentDescription, contrast)
- [ ] Responsive across screen sizes

---

## 🎨 Asset Requirements

### Icons
- **Format**: Vector (SVG or Vector Drawable)
- **Size**: 24dp base size
- **Stroke**: 2dp weight
- **Style**: Rounded corners, minimal
- **Color**: Single color (tinted programmatically)

### Illustrations
- **Style**: Minimal, geometric
- **Colors**: From accent palette
- **Usage**: Empty states, onboarding

### Exportables
From design-assets folder:
- Authentication screen
- Dashboard (all cards)
- Activity detail modal
- Input screens (rating, notes)
- Each screen at 1x, 2x, 3x resolution (or as vectors)

---

## 📦 Design Tokens (for Design-Dev Handoff)

```json
{
  "colors": {
    "background": {
      "primary": "#0A0A0A",
      "secondary": "#121212",
      "tertiary": "#1A1A1A"
    },
    "surface": {
      "elevated": "#1E1E1E",
      "elevated-higher": "#242424"
    },
    "text": {
      "primary": "#FFFFFF",
      "secondary": "#B3FFFFFF",
      "tertiary": "#80FFFFFF",
      "muted": "#4DFFFFFF"
    },
    "accent": {
      "success": "#39D98A",
      "streak": "#FF6B35",
      "error": "#FF4444"
    }
  },
  "spacing": {
    "xs": "4dp",
    "sm": "8dp",
    "md": "16dp",
    "lg": "24dp",
    "xl": "32dp"
  },
  "corner-radius": {
    "sm": "8dp",
    "md": "12dp",
    "lg": "16dp",
    "xl": "24dp"
  },
  "typography": {
    "display-large": "72sp/bold",
    "headline-medium": "24sp/semibold",
    "body-medium": "16sp/normal"
  }
}
```

---

**Version**: 1.0  
**Last Updated**: December 2024  
**Status**: Active - Reference for all UI implementation
