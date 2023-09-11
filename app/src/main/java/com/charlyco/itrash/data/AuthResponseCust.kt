package com.charlyco.itrash.data

class AuthResponseCust(var token: String, val customer: Customer) {
    constructor(): this("", Customer())
}