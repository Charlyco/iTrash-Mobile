package com.charlyco.itrash.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(@SerialName("latitude") val latitude: Double?, @SerialName("longitude") val longitude: Double?){
    constructor(): this(null, null)
}