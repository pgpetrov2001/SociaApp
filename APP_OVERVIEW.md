# FLOW - Social Confidence App

## Main Idea
A mobile app helping users overcome social anxiety and build confidence in approaching people through daily challenges, progress tracking, and gamification.

---

## Screens

### Onboarding Flow

| Screen | Purpose |
|--------|---------|
| **Splash** | Animated logo with "FLOW" branding, auto-advances after 3s |
| **Name Entry** | User enters their name to personalize the experience |
| **Onboarding (6 steps)** | Personalization quiz: challenges, age, goals, blockers |
| **Interstitial (x2)** | Motivational messages based on user's answers |
| **Loading** | Animated progress bar while "building personalized plan" |
| **Results** | Success statistics and social proof (86% overcome fear, etc.) |

### Authentication & Conversion

| Screen | Purpose |
|--------|---------|
| **Login** | Email/password auth with Apple & Google sign-in options |
| **Benefits** | Showcases core features: reminders, motivation, accountability |
| **Notification Permission** | Requests push notification access |
| **Paywall** | Subscription offer: Weekly ($9.99) or Yearly ($1.99/week) |
| **Rating** | Requests app review with user testimonials |

### Main App

| Screen | Purpose |
|--------|---------|
| **Social Tracker** | Main dashboard with daily goals, streak counter, activity calendar |

---

## Key Features

- **Daily Tracking** - Log conversations with quality ratings (1-4 stars)
- **Activity Calendar** - 6-month heatmap view of progress
- **Notes Journal** - Add notes to each interaction
- **Conversation Hints** - 8 starter templates for approaching people
- **Statistics** - Monthly charts and activity breakdown
- **Push Notifications** - Daily reminders and motivation

---

## Streak System

### Streak Tracking
| Metric | Description |
|--------|-------------|
| **Current Streak** | Consecutive days with at least 1 logged interaction |
| **Best Streak** | All-time highest streak achieved |

### Streak Milestone Notifications

| Milestone | Notification |
|-----------|--------------|
| **1 Day** | "Great start! You logged your first day!" |
| **3 Days** | "3 days strong! You're building momentum!" |
| **7 Days (1 Week)** | "1 week streak! Consistency is key!" |
| **14 Days (2 Weeks)** | "2 weeks! You're becoming unstoppable!" |
| **30 Days (1 Month)** | "1 month streak! You're a champion!" |
| **90 Days (3 Months)** | "90 days! True dedication!" |
| **180 Days (6 Months)** | "Half a year! Incredible commitment!" |
| **365 Days (1 Year)** | "1 YEAR STREAK! You're legendary!" |

---

## User Flow

```
Splash → Name Entry → Onboarding Quiz → Motivational Messages → Loading →
Results → Login → Benefits → Notifications → Paywall →
Rating → Social Tracker (Main App)
```

---

## Tech Stack
- Kotlin + Jetpack Compose
- Dark theme UI (navy background with gradient accents)
- Bottom sheet modals for secondary actions
