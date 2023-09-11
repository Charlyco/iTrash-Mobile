package com.charlyco.itrash.data

class AuthResponseAgent(var token: String, val agent: Agent) {
    constructor(): this("", Agent())
}