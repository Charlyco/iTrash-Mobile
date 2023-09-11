package com.charlyco.itrash.data
data class AuthResponse(var token: String, val user: User) {
    constructor(): this("", User())
}
