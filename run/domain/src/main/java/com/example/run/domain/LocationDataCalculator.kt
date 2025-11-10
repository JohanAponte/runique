package com.example.run.domain

import com.example.core.domain.location.LocationTimestamp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationDataCalculator {
    fun getTotalDistanceMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations
            .sumOf { timestampsPerLine ->
                timestampsPerLine
                    .zipWithNext { location1, location2 ->
                        location1.location.location.distanceTo(location2.location.location)
                    }.sum().roundToInt()
            }
    }

    fun getMaxSpeedKmh(locations: List<List<LocationTimestamp>>): Double {
        return locations.maxOf { locationsSet ->
            locationsSet.zipWithNext { location1, location2 ->
                val distance = location1.location.location.distanceTo(
                    other = location2.location.location
                )
                val hourDifference = (location2.durationTimestamp - location1.durationTimestamp)
                    .toDouble(DurationUnit.HOURS)
                if (hourDifference == 0.0) {
                    0.0
                } else {
                    (distance / 1000.0) / hourDifference
                }
            }.maxOrNull() ?: 0.0
        }
    }

    fun getTotalElevationMetes(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { locationsSet ->
            locationsSet.zipWithNext { location1, location2 ->
                val altitude1 = location1.location.altitude
                val altitude2 = location2.location.altitude
                (altitude2 - altitude1).coerceAtLeast(0.0)
            }.sum().roundToInt()
        }
    }
}