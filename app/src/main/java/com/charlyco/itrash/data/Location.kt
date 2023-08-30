package com.charlyco.itrash.data

data class Location(val latitude: Double, val longitude: Double) {
    constructor() : this(0.0, 0.0)
}
