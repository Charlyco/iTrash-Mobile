package com.charlyco.itrash.data

data class Bin(
    var binId: Int?,
    var binSize: String?,
    var latitude: Double,
    var longitude: Double,
    var address: String?,
    var ownership: String?,
    var binStatus: String?,
    var userId: Int?
){
    constructor(): this(null, "",0.0,0.0, "", "", "", null)
}
