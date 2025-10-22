plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.core.database"
}

dependencies {

    implementation(projects.core.domain)
}