package com.kongup.common.server.data

class GetLocationSearch : ArrayList<GetLocationInfo>()

data class GetLocationInfo(
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)

