package com.charlyco.itrash.data

import java.time.LocalDateTime

data class DisposalRequest(
    val requestId: Int,
    val requestStatus: RequestStatus,
    val binId:Int,
    val customerId: Int,
    val requestDate: LocalDateTime,
    var agentId: Int?
)
