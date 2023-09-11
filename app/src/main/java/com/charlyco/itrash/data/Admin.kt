package com.charlyco.itrash.data

data class Admin(
    var userId: Int?,
    var userName: String?,
    var fullName: String?,
    var password: String?,
    var address: String?,
    var email: String?,
    var role: String,
    var phoneNumber: String?
    ){ constructor(): this(null, "","", "", "", "","", "", )}
