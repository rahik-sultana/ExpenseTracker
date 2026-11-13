# ExpenseTracker – Project Master Instructions

## 1. Project Overview
- Project Name: ExpenseTracker
- Platform: Android
- IDE: Android Studio + Google Antigravity
- Package Name: com.example.expensetracker
- Primary Language: Use the language already configured in the project.
- Goal: Build a modern, useful and user-friendly Expense Tracker app.

## 2. About the Developer
I am a CSE student and a beginner in Android development.
My knowledge:
- Basic C and C++
- Basic Java
- Learning Android development and Kotlin

Always write beginner-friendly code and explain important technical concepts simply.

## 3. AI Assistant Responsibilities
Act as my personal Android development assistant.

Whenever I give a requirement:
1. Understand my requirement.
2. Inspect the existing project before changing anything.
3. Identify the files that need modification.
4. Explain the proposed changes.
5. Implement the requested feature.
6. Check for errors and build the project when appropriate.
7. Update this GEMINI.md file with confirmed requirements and decisions.

Do not modify unrelated files unnecessarily.

## 4. Coding Rules
- Maintain the existing project architecture.
- Use the existing programming language and dependencies.
- Write clean, readable and organized code.
- Use meaningful variable and function names.
- Avoid unnecessary complexity.
- Do not duplicate existing functionality.
- Do not delete working features.
- Do not replace the entire project to implement a small feature.
- Maintain compatibility with the current Gradle configuration.

## 5. UI/UX Guidelines
The app should have:
- Modern and clean design.
- Simple navigation.
- Consistent colors and typography.
- Proper spacing and alignment.
- User-friendly buttons and input fields.
- Responsive layouts for different screen sizes.
- Good readability and accessibility.

Do not redesign existing screens without my approval.

## 6. Feature Development
For every new feature:
- Understand its purpose.
- Check existing implementations.
- Reuse existing components where possible.
- Maintain consistency with the application.
- Handle invalid inputs.
- Test the feature after implementation.

If a requirement is unclear, ask me before making assumptions.

## 7. Project Safety
- Inspect code before making major changes.
- Ask permission before deleting or restructuring important files.
- Never overwrite working code unnecessarily.
- Do not expose passwords, API keys or secrets.
- Preserve existing project configuration and user data.
- Explain potentially breaking changes before applying them.

## 8. Living Documentation
GEMINI.md is the central project instruction and requirement document.

Whenever I confirm a new requirement:
1. Update the relevant section.
2. Preserve previous requirements that are still valid.
3. Remove outdated requirements only when replaced or cancelled.
4. Record important technical decisions.
5. Avoid contradictory instructions.
6. Keep the document organized and updated.

Never invent requirements or mark an unimplemented feature as completed.

## 9. Communication Style
- Communicate in simple English or Banglish.
- Explain code in beginner-friendly language.
- Give step-by-step guidance.
- Mention which files were modified.
- Explain why changes were necessary.
- Summarize completed work.
- Report unresolved errors honestly.

Do not overwhelm me with advanced technical explanations.

## 10. Current Project Status & Implemented Features
The project is actively being developed with clean, modular Kotlin code and Android View-based architecture.

### Implemented Features:
1. **App Identity & Launcher Icon:**
   - Permanent App Icon: Official Concept 02 ("Geometric E-T Vault") adaptive icon in light lavender, purple, and soft light blue gradient.
   - Animated Splash Screen: `SplashActivity` with centered logo, soft white halo glow, and fade-in animation, routing to Welcome or Dashboard.

2. **First-Time Welcome Onboarding:**
   - `WelcomeActivity` shown only on the very first launch.
   - Collects user's display name, saves to local `SharedPreferences` via `UserManager`.
   - Never shown again unless reset from Profile.

3. **Personalized Dashboard (`DashboardFragment`):**
   - Header with greeting: "Welcome Back, [Name]!" and user initials avatar.
   - **Status Bar Spacing:** Handled cleanly with `android:fitsSystemWindows="true"` and `ViewCompat.setOnApplyWindowInsetsListener` to prevent any status bar overlap across different screen sizes and notch designs.
   - **Quick Profile Navigation:** Tapping the greeting row automatically switches to the Profile tab.
   - Available Balance card with Income and Expense summary badges.
   - Integrated "Add Transaction" section with 6 capsule-style rounded elements:
     1. Amount Input (Capsule with ৳ currency prefix)
     2. Segmented Type Selector (Expense vs. Income)
     3. Category Dropdown (Food, Transport, Shopping, Bills, Education, Others)
     4. Date Picker Container (triggers native `DatePickerDialog`)
     5. Description (Multi-line rounded box)
     6. Save Transaction Button (Capsule MaterialButton)

4. **Bottom Navigation (`BottomNavigationView`):**
   - 4 fixed tabs: Home (`DashboardFragment`), Alerts (`NotificationsFragment`), Profile (`ProfileFragment`), Settings (`SettingsFragment`).

5. **Profile Management (`ProfileFragment`):**
   - Displays user's name and initials.
   - Allows editing and saving the user's display name.
   - **Persistent Profile Avatar:** Gallery photos are copied directly into the app's private internal storage (`context.filesDir/profile_avatar.jpg`) and referenced via `SharedPreferences`. This prevents Android from revoking temporary content URI permissions when the app closes and reopens.
   - Synchronized with both the Profile screen avatar and Dashboard header avatar (`ivHeaderAvatar`).
   - Clean overwriting on new photo selection, and file deletion on photo removal or profile reset.
   - "Reset Profile" button for testing onboarding.

## 11. Golden Rule
My confirmed requirements are the source of truth.

Always inspect before modifying, preserve existing functionality, implement features incrementally, and keep GEMINI.md synchronized with the actual project.