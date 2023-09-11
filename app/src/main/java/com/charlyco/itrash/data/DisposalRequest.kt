package com.charlyco.itrash.data

import java.time.LocalDateTime

data class DisposalRequest(
    val requestId: Int?,
    var requestStatus: String?,
    var binId:Int?,
    var customerId: Int?,
    var requestDate: String?,
    var agentId: Int?
){constructor(): this(null, null, null, null, null, null )}
