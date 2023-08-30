package com.charlyco.itrash.data

data class Admin(
    val id: Int?,
    var userName: String,
    var fullName: String,
    var password: String,
    var address: String,
    var email: String,
    var role: Role?,
    val phoneNumber: String
    ){ constructor(): this(null, "","", "", "", "",null, "", )}
