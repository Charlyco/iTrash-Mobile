package com.charlyco.itrash.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charlyco.itrash.data.DisposalRequest
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.data.RequestStatus
import java.time.LocalDateTime

class RequestViewModel: ViewModel() {
    var selectedRequestId = 0
    fun getAllPendingRequest(location: Location): List<DisposalRequest> {
        //call repository function to retrieve all pending request where the bin location is within permitted proximity
        return listOf(
            DisposalRequest(1, RequestStatus.SENT, 23, 34, LocalDateTime.now(),null),
            DisposalRequest(2, RequestStatus.SENT, 23, 34, LocalDateTime.now(),null),
            DisposalRequest(3, RequestStatus.SENT, 23, 34, LocalDateTime.now(),null),
            DisposalRequest(4, RequestStatus.SENT, 23, 34, LocalDateTime.now(),null),
            DisposalRequest(5, RequestStatus.SENT, 23, 34, LocalDateTime.now(),null)
        )
    }

    fun setSelectedRequestId(requestId: Int) {
        selectedRequestId = requestId
    }

    fun getRequestById(selectedRequestId: Int?): DisposalRequest {
        return DisposalRequest(1, RequestStatus.SENT, 23, 34, LocalDateTime.now(), null)
    }

    fun assignAgentToRequest(requestId: Int?, id: Int?) {
        TODO("Not yet implemented")
    }

}
class RequestViewModelFactory (): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}