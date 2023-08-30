package com.charlyco.itrash.data

data class User(
    val id: Int?,
    var userName: String,
    var fullName: String,
    var password: String,
    var address: String,
    var email: String,
    var role: Role?,
    var phoneNumber: String) {
    constructor(): this(null, "","", "", "", "",null, "", )
}
