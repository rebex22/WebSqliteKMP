import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("app.cash.sqldelight") version "2.2.1"
}

sqldelight {
    databases {
        create("HockeyDb") {
            packageName.set("cz.mmaso.apptest10.sqldelight.hockey")
            generateAsync.set(true)
            srcDirs("src/jsMain/sqldelight")
        }
    }
}

kotlin {
    jvmToolchain(21)

    jvm()
    
    js {
        browser()
        binaries.executable()
        useCommonJs()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        // browser()
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    sourceMaps = true
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
        jsMain.dependencies {
            implementation("app.cash.sqldelight:web-worker-driver:2.2.1")
            implementation("app.cash.sqldelight:primitive-adapters:2.2.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.2.1")
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))

            implementation(npm("sql.js", "1.13.0")) // 1.8.0
            implementation(npm("dateformat", "5.0.3")) // 3.0.3
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.2.1"))
            implementation(npm("idb-keyval", "6.2.1"))
        }
        wasmJsMain.dependencies {
            implementation("app.cash.sqldelight:web-worker-driver:2.2.1")
            implementation("app.cash.sqldelight:primitive-adapters:2.2.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.2.1")
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))

            implementation(npm("sql.js", "1.13.0"))
            implementation(npm("dateformat", "5.0.3"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.2.1"))
            implementation(npm("idb-keyval", "6.2.1"))
        }
    }
}


compose.desktop {
    application {
        mainClass = "cz.mmaso.apptest10.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "cz.mmaso.apptest10"
            packageVersion = "1.0.0"
        }
    }
}

/*
tasks.withType<KotlinNpmInstallTask>().configureEach {
    args.addAll(listOf("--mutex", "file:${file("../build/.yarn-mutex")}"))
}
*/