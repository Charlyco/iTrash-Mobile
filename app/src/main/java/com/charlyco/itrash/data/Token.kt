package com.charlyco.itrash.data

data class Token(val tokenId: Int, val token: String, val expired: Boolean, val revoked: Boolean, val userId: Int)
