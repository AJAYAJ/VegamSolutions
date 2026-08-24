# Vegam Digital Student App

Native Android student portal built with Kotlin, Jetpack Compose, Hilt, Room, Retrofit, and Supabase.

## Supabase setup

1. Create a Supabase project and run `supabase/schema.sql` in its SQL editor.
2. In Supabase Authentication, create a student user using the email convention
   `<lowercase-student-code>@students.vegamdigital.in`. For example:
   `syf-amp-dm26-b03-014@students.vegamdigital.in`.
3. Add a matching row to `public.profiles`, using that Auth user's UUID as `id`.
4. Add these values to the untracked `local.properties` file:

```properties
SUPABASE_URL=https://YOUR_PROJECT_REF.supabase.co
SUPABASE_ANON_KEY=YOUR_PUBLISHABLE_OR_ANON_KEY
```

Only the publishable/anon key belongs in the app. Never add a `service_role` key.

Login, session restoration/refresh, doubts, answers, job submissions, and referrals now use Supabase. Existing Room tables remain as a local legacy cache.

## Build

```shell
./gradlew :app:assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.
