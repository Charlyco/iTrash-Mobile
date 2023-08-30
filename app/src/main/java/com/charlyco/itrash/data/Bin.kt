package com.charlyco.itrash.data

data class Bin(
    val binId: Int,
    val binSize: Double,
    val location: Location,
    val ownership: BinOwnership,
    val binStatus: BinStatus,
    val userId: Int)
