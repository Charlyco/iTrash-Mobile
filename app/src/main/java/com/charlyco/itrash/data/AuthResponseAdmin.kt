package com.charlyco.itrash.data

data class AuthResponseAdmin(var token: String, val admin: Admin) {
    constructor(): this("", Admin())
}