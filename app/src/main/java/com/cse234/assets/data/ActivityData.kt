package com.cse234.assets.data

data class ActivityData(
    val userId: String,
    val activityType: String,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val distance: Float
)
