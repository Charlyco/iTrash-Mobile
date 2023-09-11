package com.charlyco.itrash.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.charlyco.itrash.api.ItrashService
import com.charlyco.itrash.api.ItrashServiceBuilder
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.DisposalRequest
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.data.RequestStatus
import com.charlyco.itrash.utils.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RequestRepository(dataStoreManager: DataStoreManager) {
    var authToken = ""
    init {
        authToken = runBlocking {
            dataStoreManager.readTokenData()!!
        }
    }
    private val requestApiService: ItrashService = ItrashServiceBuilder().buildService(authToken).create(
        ItrashService::class.java)

    suspend fun getPendingRequestByAgentLocation(location: Location?): MutableList<DisposalRequest> {
        return suspendCoroutine { continuation ->
            val call = requestApiService.getPendingRequestByLocation(location?.latitude!!, location.longitude!!)
            call.enqueue(object: Callback<MutableList<DisposalRequest>> {
                override fun onResponse(
                    call: Call<MutableList<DisposalRequest>>,
                    response: Response<MutableList<DisposalRequest>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        continuation.resume(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<MutableList<DisposalRequest>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    suspend fun sendDisposalRequest(newRequest: DisposalRequest): Int {
        return suspendCoroutine { continuation ->
            val call = requestApiService.createDisposalRequest(newRequest)
            call.enqueue(object: Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun getRequestDetailById(selectedRequestId: Int?): DisposalRequest {
        return suspendCoroutine { continuation ->
            val call = requestApiService.getRequestDetailById(selectedRequestId)
            call.enqueue(object: Callback<DisposalRequest>{
                override fun onResponse(
                    call: Call<DisposalRequest>,
                    response: Response<DisposalRequest>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<DisposalRequest>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    suspend fun getCustomerById(customerId: Int?): Customer{
        return suspendCoroutine { continuation ->
            val call = requestApiService.getCustomerById(customerId)
            call.enqueue(object: Callback<Customer> {
                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    suspend fun assignRequestToAgent(requestId: Int?, agentId: Int?): Boolean {
        return suspendCoroutine { continuation ->
            val call = requestApiService.assignRequestToAgent(requestId, agentId)
            call.enqueue(object: Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}