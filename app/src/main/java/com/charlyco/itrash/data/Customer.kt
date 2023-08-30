package com.charlyco.itrash.data

data class Customer(
    val id: Int?,
    var userName: String,
    var fullName: String,
    var password: String,
    var address: String,
    var email: String,
    var role: Role?,
    val phoneNumber: String,
    val binIds: List<Int>?,
    val requestIds: List<Int>?
    ) {
    constructor(): this(null, "","", "", "", "", null, "", null, null)
}
