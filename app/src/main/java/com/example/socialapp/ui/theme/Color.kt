package com.example.socialapp.ui.theme

import androidx.compose.ui.graphics.Color

// Design System Colors - Dark Navy Theme (matching onboarding)

// Background Palette
val BackgroundPrimary = Color(0xFF0D1B2A)      // Dark navy (main background)
val BackgroundSecondary = Color(0xFF112240)    // Slightly lighter navy
val BackgroundTertiary = Color(0xFF1B2838)     // Card-level dark

// Surface Colors (Cards & Containers)
val SurfaceElevated = Color(0xFF1B2838)        // Card backgrounds
val SurfaceElevatedHigher = Color(0xFF233554)  // Modal/sheet backgrounds
val SurfacePressed = Color(0xFF2D4A6A)         // Pressed state

// Glass/blur effect overlay
val SurfaceGlass = Color(0x33FFFFFF)           // 20% white overlay
val SurfaceGlassStrong = Color(0x4DFFFFFF)     // 30% white overlay

// Text Colors
val TextPrimary = Color(0xFFFFFFFF)            // Pure white - headings, key info
val TextSecondary = Color(0xB3FFFFFF)          // 70% white - body text
val TextTertiary = Color(0x80FFFFFF)           // 50% white - labels
val TextMuted = Color(0x4DFFFFFF)              // 30% white - hints, disabled

// Accent Colors (matching onboarding)
val AccentBlue = Color(0xFF4A90D9)             // Primary accent - buttons, links
val AccentBlueDim = Color(0x334A90D9)          // Blue backgrounds (20% opacity)

// Activity & Success (Green/Teal)
val ActivityLow = Color(0xFF1E5245)            // Low activity intensity
val ActivityMedium = Color(0xFF2E8B6A)         // Medium intensity
val ActivityHigh = Color(0xFF39D98A)           // High intensity (accent green)
val ActivityMax = Color(0xFF06FFA5)            // Maximum intensity
val Success = Color(0xFF39D98A)                // Goal completion, success states
val SuccessDim = Color(0x3339D98A)             // Success backgrounds (20% opacity)

// Streak & Motivation (Orange/Fire)
val StreakPrimary = Color(0xFFFF6B35)          // Streak indicator, fire icon
val StreakSecondary = Color(0xFFFF9F1C)        // Streak accents, highlights
val StreakGlow = Color(0x33FF6B35)             // Glow effect (20% opacity)

// Semantic Colors
val Error = Color(0xFFFF6B6B)                  // Errors, warnings (softer red)
val Info = Color(0xFF4A90D9)                   // Information, hints (accent blue)
val Warning = Color(0xFFFFBE0B)                // Warnings, alerts