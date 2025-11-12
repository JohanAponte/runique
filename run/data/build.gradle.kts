plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.run.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.bundles.koin)
    implementation(libs.koin.android.workmanager)
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.run.domain)

}