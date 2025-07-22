// Project structure for com.okemwag.communitynews
// This is what your actual folder structure should look like:

/*
CommunityNews/
├── app/
│   ├── src/main/java/com/okemwag/communitynews/
│   │   ├── MainActivity.kt
│   │   ├── CommunityNewsApplication.kt
│   │   └── di/
│   │       └── AppModule.kt
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── core/
│   ├── common/
│   │   ├── src/main/java/com/okemwag/communitynews/core/common/
│   │   │   ├── result/
│   │   │   │   ├── Result.kt
│   │   │   │   └── NetworkResult.kt
│   │   │   ├── utils/
│   │   │   │   ├── Constants.kt
│   │   │   │   ├── Extensions.kt
│   │   │   │   └── DateUtils.kt
│   │   │   └── di/
│   │   │       └── CommonModule.kt
│   │   └── build.gradle.kts
│   ├── network/
│   │   ├── src/main/java/com/okemwag/communitynews/core/network/
│   │   │   ├── ApiService.kt
│   │   │   ├── NetworkModule.kt
│   │   │   ├── interceptors/
│   │   │   │   ├── AuthInterceptor.kt
│   │   │   │   └── LoggingInterceptor.kt
│   │   │   └── model/
│   │   │       ├── ApiResponse.kt
│   │   │       └── ErrorResponse.kt
│   │   └── build.gradle.kts
│   ├── database/
│   │   ├── src/main/java/com/okemwag/communitynews/core/database/
│   │   │   ├── CommunityNewsDatabase.kt
│   │   │   ├── dao/
│   │   │   ├── entities/
│   │   │   └── DatabaseModule.kt
│   │   └── build.gradle.kts
│   ├── blockchain/
│   │   ├── src/main/java/com/okemwag/communitynews/core/blockchain/
│   │   │   ├── Web3Manager.kt
│   │   │   ├── contracts/
│   │   │   ├── models/
│   │   │   └── BlockchainModule.kt
│   │   └── build.gradle.kts
│   └── design-system/
│       ├── src/main/java/com/okemwag/communitynews/core/design/
│       │   ├── theme/
│       │   ├── components/
│       │   └── icons/
│       └── build.gradle.kts
├── feature/
│   ├── auth/
│   ├── news/
│   ├── alerts/
│   ├── classifieds/
│   ├── profile/
│   └── wallet/
├── shared/
│   ├── domain/
│   ├── data/
│   └── ui/
├── build.gradle.kts (Project level)
├── gradle.properties
├── settings.gradle.kts
└── versions.gradle.kts
*/

// ===========================================
// settings.gradle.kts
// ===========================================
pluginManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
}
}

dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
maven { url = uri("https://jitpack.io") }
maven { url = uri("https://artifacts.ethereum.org/artifactory/maven-public/") }
}
}

rootProject.name = "CommunityNews"

// App module
include(":app")

// Core modules
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:blockchain")
include(":core:design-system")

// Feature modules
include(":feature:auth")
include(":feature:news")
include(":feature:alerts")
include(":feature:classifieds")
include(":feature:profile")
include(":feature:wallet")

// Shared modules
include(":shared:domain")
include(":shared:data")
include(":shared:ui")

// ===========================================
// Project-level build.gradle.kts
// ===========================================
buildscript {
extra.apply {
set("compose_version", "1.5.8")
set("kotlin_version", "1.9.22")
set("hilt_version", "2.48")
set("room_version", "2.6.1")
set("retrofit_version", "2.9.0")
set("coroutines_version", "1.7.3")
set("lifecycle_version", "2.7.0")
set("web3j_version", "4.10.3")
}
}

plugins {
id("com.android.application") version "8.2.2" apply false
id("com.android.library") version "8.2.2" apply false
id("org.jetbrains.kotlin.android") version "1.9.22" apply false
id("com.google.dagger.hilt.android") version "2.48" apply false
id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
id("io.gitlab.arturbosch.detekt") version "1.23.4" apply false
}

// ===========================================
// versions.gradle.kts (Version Catalog)
// ===========================================
// This is for centralized version management
// In a real project, you'd use version catalogs

// ===========================================
// app/build.gradle.kts
// ===========================================
plugins {
id("com.android.application")
id("org.jetbrains.kotlin.android")
id("com.google.dagger.hilt.android")
id("com.google.devtools.ksp")
id("kotlin-parcelize")
id("io.gitlab.arturbosch.detekt")
}

android {
namespace = "com.okemwag.communitynews"
compileSdk = 34

    defaultConfig {
        applicationId = "com.okemwag.communitynews"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        // Build config fields for different environments
        buildConfigField("String", "API_BASE_URL", "\"https://api.communitynews.okemwag.com/\"")
        buildConfigField("String", "BLOCKCHAIN_NETWORK", "\"polygon-mumbai\"")
        buildConfigField("boolean", "DEBUG_MODE", "true")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            
            buildConfigField("String", "API_BASE_URL", "\"https://api-dev.communitynews.okemwag.com/\"")
            buildConfigField("String", "BLOCKCHAIN_NETWORK", "\"polygon-mumbai\"")
            buildConfigField("boolean", "DEBUG_MODE", "true")
        }
        
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            
            buildConfigField("String", "API_BASE_URL", "\"https://api.communitynews.okemwag.com/\"")
            buildConfigField("String", "BLOCKCHAIN_NETWORK", "\"polygon-mainnet\"")
            buildConfigField("boolean", "DEBUG_MODE", "false")
        }
        
        create("staging") {
            initWith(getByName("release"))
            isMinifyEnabled = false
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            
            buildConfigField("String", "API_BASE_URL", "\"https://api-staging.communitynews.okemwag.com/\"")
            buildConfigField("String", "BLOCKCHAIN_NETWORK", "\"polygon-mumbai\"")
            buildConfigField("boolean", "DEBUG_MODE", "false")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
        }
    }

    // For different product flavors (community vs enterprise)
    flavorDimensions += "version"
    productFlavors {
        create("community") {
            dimension = "version"
            buildConfigField("boolean", "PREMIUM_FEATURES", "false")
        }
        
        create("enterprise") {
            dimension = "version"
            buildConfigField("boolean", "PREMIUM_FEATURES", "true")
            applicationIdSuffix = ".enterprise"
        }
    }
}

dependencies {
// Core modules
implementation(project(":core:common"))
implementation(project(":core:network"))
implementation(project(":core:database"))
implementation(project(":core:blockchain"))
implementation(project(":core:design-system"))

    // Feature modules
    implementation(project(":feature:auth"))
    implementation(project(":feature:news"))
    implementation(project(":feature:alerts"))
    implementation(project(":feature:classifieds"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:wallet"))

    // Shared modules
    implementation(project(":shared:domain"))
    implementation(project(":shared:data"))
    implementation(project(":shared:ui"))

    // AndroidX Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Compose
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_version"]}")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    ksp("com.google.dagger:hilt-compiler:${rootProject.extra["hilt_version"]}")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.retrofit2:converter-gson:${rootProject.extra["retrofit_version"]}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["coroutines_version"]}")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Web3 & Blockchain
    implementation("org.web3j:core:${rootProject.extra["web3j_version"]}")
    implementation("org.web3j:contracts:${rootProject.extra["web3j_version"]}")
    implementation("org.web3j:crypto:${rootProject.extra["web3j_version"]}")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["coroutines_version"]}")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")
}

// ===========================================
// gradle.properties
// ===========================================
/*
# Project-wide Gradle settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true

# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
android.useAndroidX=true

# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official

# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true

# Enable build features
android.enableJetifier=true
android.suppressUnsupportedCompileSdk=true

# KSP
ksp.incremental=true
ksp.incremental.intermodule=true

# Hilt
dagger.hilt.android.internal.disableAndroidSuperclassValidation=true
*/