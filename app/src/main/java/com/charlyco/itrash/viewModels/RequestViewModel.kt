package com.charlyco.itrash.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.DisposalRequest
import com.charlyco.itrash.data.RequestStatus
import com.charlyco.itrash.repository.RequestRepository
import com.charlyco.itrash.utils.DataStoreManager

class RequestViewModel: ViewModel() {
    var pendingRequestList: MutableLiveData<MutableList<DisposalRequest>> = MutableLiveData()
    var requestDetails: MutableLiveData<DisposalRequest> = MutableLiveData()
    val newRequest = DisposalRequest()
    var customer: MutableLiveData<Customer> = MutableLiveData()
    var isRequestAssigned: MutableLiveData<Boolean> = MutableLiveData()
    var requestId: MutableLiveData<Int> = MutableLiveData()

    suspend fun getAllPendingRequest(dataStoreManager: DataStoreManager) {
        val location = dataStoreManager.readLocationData()
        val requestRepository = RequestRepository(dataStoreManager)
        val requestList = requestRepository.getPendingRequestByAgentLocation(location)
        val pending = mutableListOf<DisposalRequest>()
        requestList.forEach { request ->
            if (request.requestStatus == RequestStatus.SENT.name ||
                request.requestStatus == RequestStatus.RECEIVED.name) {
                pending.add(request)
            }
        }
        pendingRequestList.value = pending
    }

    suspend fun getRequestById(selectedRequestId: Int?, dataStoreManager: DataStoreManager){
         val requestRepository = RequestRepository(dataStoreManager)
        requestDetails.value = requestRepository.getRequestDetailById(selectedRequestId)
    }

    suspend fun assignAgentToRequest(requestId: Int?, agentId: Int?, dataStoreManager: DataStoreManager) {
        val requestRepository = RequestRepository(dataStoreManager)
        isRequestAssigned.value = requestRepository.assignRequestToAgent(requestId, agentId)
    }

    suspend fun sendDisposalRequest(
        dataStoreManager: DataStoreManager
    ){
        val requestRepository = RequestRepository(dataStoreManager)
        requestId.value = requestRepository.sendDisposalRequest(newRequest);
    }

    suspend fun getCustomerById(customerId: Int?, dataStoreManager: DataStoreManager) {
        val requestRepository = RequestRepository(dataStoreManager)
        customer.value = requestRepository.getCustomerById(customerId)
    }
}

class RequestViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}