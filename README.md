[![Android Build](https://github.com/dinesh-thiyagarajan/Juice-Kadai/actions/workflows/build.yml/badge.svg)](https://github.com/dinesh-thiyagarajan/Juice-Kadai/actions/workflows/build.yml)

# Juice Kadai

**Juice Kadai** is a Kotlin Multiplatform project targeting Android, Desktop for placing juice orders, the app uses firebase realtime database for storing data

# Screenshots

![juice_kadai_landing_screen](https://github.com/dinesh-thiyagarajan/Juice-Kadai/assets/17405840/252a3ab9-f3b2-4345-b2ea-a30c00e2ae0d)

![juice_kadai_selection](https://github.com/dinesh-thiyagarajan/Juice-Kadai/assets/17405840/c4e1752a-1a94-4396-9e76-e36d9fba3dab)

# Setup Instructions

- Install Android Studio (Latest Stable or Canary Build)
- Add Kotlin Multi platform Plugin
- Please read the documentation for compose multiplatform [setup](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-setup.html) and follow the process
- Choose the type of build that you wanted, ex: desktop, android, ios
- Create a new project in firebase and add your app package name and download the google-services.json and paste it under rootProjectDir/composeApp/google-services.json
- Copy Project ID, App ID, API Key from the google-services.json and paste it under the respective keys given below
- PROJECT_ID = "Your Project ID"
  APP_ID = "Your Project App ID"
  API_KEY = "Your Project Api Key"
  PRINT_HTTP_LOGS = "true"
- Copy the Database URL and API Key from firebase console and paste it in local.properties in their respective fields
- Run this command ./gradlew generateBuildKonfig in the terminal in the root directory of the project, inside composeApp/build/buildkonfig a BuildKonfig file will be generated, any keys and values referenced in the local.properties can be accessed from here
- Run the app

# Acknowledgements

- The icons used in this app are from [SVG Repo](https://www.svgrepo.com)
- This app uses [Firebase Kotlin SDK](https://github.com/gitliveapp/firebase-kotlin-sdk) for Firebase Database Connection and Auth
  
