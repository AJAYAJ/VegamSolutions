# Vegam Digital Student App

Native Android student portal built from the supplied UI references.

## Stack

- Kotlin and Jetpack Compose
- Clean Architecture packages (`domain`, `data`, `presentation`, `di`)
- Hilt dependency injection
- Navigation Compose
- Room for the signed-in session and locally created jobs/doubts
- Retrofit API contract for the future backend
- Dummy Firebase-shaped Auth, Firestore, Storage and FCM gateways

## Demo login

- Student code: `SYF-AMP-DM26-B03-014`
- Password: `student123`

The dummy authentication accepts a non-empty student code and passwords of at least four characters.

## Build

```shell
./gradlew :app:assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Connecting Firebase later

Implement the four interfaces in `data/remote/RemoteServices.kt` with the Firebase SDK, add `google-services.json` and the Google Services Gradle plugin, then change the Hilt bindings in `di/AppModule.kt`. Presentation and domain code do not need to change.
