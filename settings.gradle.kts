pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        /**
         * Step-2 : add below maven repository for download dependency
         * **/
        maven {
            url = uri("https://maven.pkg.github.com/sunfox-technologies/spandan_ecg_android_sdk")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PACKAGE_ACCESS_TOKEN")
            }
        }

        maven {
            url = uri("https://maven.pkg.github.com/sunfox-technologies/spandan_ecg_android_sdk_ui")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PACKAGE_ACCESS_TOKEN")
            }
        }

        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "POC_SDK_UI_PDF_2"
include(":app")
 