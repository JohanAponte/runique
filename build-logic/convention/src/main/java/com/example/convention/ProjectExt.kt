package com.example.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Extension property to access the `libs` version catalog from a Project.
 *
 * Returns the named version catalog "libs" from the project's VersionCatalogsExtension.
 * Usage: `project.libs` or inside Project-scoped code.
 */
val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")