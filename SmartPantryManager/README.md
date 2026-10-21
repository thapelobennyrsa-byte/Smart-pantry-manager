# Smart Pantry Manager

A Java Android application that tracks pantry ingredients and suggests recipes only when every required ingredient is present in sufficient quantity.

## Technology
- Java and Android Studio
- SQLite via `SQLiteOpenHelper`
- Activities, Intents, ListView and a customisable list adapter pattern
- 18 seeded recipes; pantry data persists locally across sessions

## Open and run
1. Install Android Studio and a compatible JDK.
2. Open the `SmartPantryManager` folder in Android Studio.
3. Allow Gradle sync to complete. Use Android SDK 35 (or update compileSdk to an installed SDK).
4. Start an emulator or connect an Android device with USB debugging enabled.
5. Press Run.

## Features
- Add, view, edit and delete pantry items.
- Validate required fields and positive numeric quantities.
- Browse recipes that pass strict ingredient and quantity checks.
- Open recipe details and preparation steps.
- Settings preference and bottom-style navigation controls.
- SQLite local persistence and 18 initial recipes.

## Important quantity convention
For strict quantity comparisons, enter quantities in the same base quantity convention used by a recipe. Recipe data is intentionally simple for this academic prototype; it does not automatically convert between units such as grams and kilograms.

## GitHub evidence
Create a public repository and commit your work incrementally as you develop, test and fix the project. Do not fabricate a historical commit timeline. Replace this section with the actual repository URL before submitting.

## Final submission checklist
- Test all CRUD actions on an emulator/device.
- Capture genuine screenshots from the running application.
- Record and include a narrated 5–7 minute demonstration video.
- Add your actual GitHub repository URL to the written report.
- Complete personal details and sign the originality declaration.

## Submission details
- Student: Thapelo Benny Kgole
- Student number: 402311478
- Module: Mobile App Development 700

Before submission, replace this section with the real public GitHub repository URL and ensure the repository contains the actual incremental development history. Do not invent commit history.
