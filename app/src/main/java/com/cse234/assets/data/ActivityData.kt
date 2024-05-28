package com.cse234.assets.data

data class ActivityData(
    val userId: String,
    val activityType: String,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val distance: Double
){
    constructor() : this("", "", 0L, 0L, 0L, 0.0)
}
