package com.charlyco.itrash.data

data class Customer(
    var userId: Int?,
    var userName: String?,
    var fullName: String?,
    var password: String?,
    var address: String?,
    var email: String?,
    var role: String,
    var phoneNumber: String?,
    var binIds: List<Int>?,
    var requestIds: List<Int>?
    ) {
    constructor(): this(null, "","", "", "", "", "", "", null, null)
}
