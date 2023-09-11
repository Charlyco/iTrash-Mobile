package com.charlyco.itrash.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.charlyco.itrash.api.ItrashService
import com.charlyco.itrash.api.ItrashServiceBuilder
import com.charlyco.itrash.data.Admin
import com.charlyco.itrash.data.Agent
import com.charlyco.itrash.data.AuthResponse
import com.charlyco.itrash.data.AuthResponseAdmin
import com.charlyco.itrash.data.AuthResponseAgent
import com.charlyco.itrash.data.AuthResponseCust
import com.charlyco.itrash.data.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository {
    private val apiService: ItrashService = ItrashServiceBuilder().buildService("").create(ItrashService::class.java)

    suspend fun createCustomer(customer: Customer):AuthResponseCust {
        return suspendCoroutine { continuation ->
            val call = apiService.newCustomer(customer)
            call.enqueue(object : Callback<AuthResponseCust> {
                override fun onResponse(call: Call<AuthResponseCust>, response: Response<AuthResponseCust>
                ) {
                    if (response.isSuccessful) {
                        val authResponse = mapResponseCustomer(response.body())
                        continuation.resume(authResponse)
                    }
                }

                override fun onFailure(call: Call<AuthResponseCust>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private fun mapResponseCustomer(body: AuthResponseCust?): AuthResponseCust {
        val callResponse = AuthResponseCust()
        callResponse.token = body!!.token
        callResponse.customer.userId = body.customer.userId
        callResponse.customer.userName = body.customer.userName
        callResponse.customer.fullName = body.customer.fullName
        callResponse.customer.password = body.customer.password
        callResponse.customer.email = body.customer.email
        callResponse.customer.address = body.customer.address
        callResponse.customer.role = body.customer.role
        callResponse.customer.phoneNumber = body.customer.phoneNumber
        callResponse.customer.binIds = body.customer.binIds
        callResponse.customer.requestIds = body.customer.requestIds

        return callResponse
    }

    private fun mapResponse(body: AuthResponse?): AuthResponse {
        val callResponse = AuthResponse()
        callResponse.token = body!!.token
        callResponse.user.userId = body.user.userId
        callResponse.user.userName = body.user.userName
        callResponse.user.fullName = body.user.fullName
        callResponse.user.password = body.user.password
        callResponse.user.email = body.user.email
        callResponse.user.address = body.user.address
        callResponse.user.role = body.user.role
        callResponse.user.phoneNumber = body.user.phoneNumber

        return callResponse
    }

    suspend fun createAgent(agent: Agent): AuthResponseAgent {
        return suspendCoroutine { continuation ->
            val call = apiService.newAgent(agent)
            call.enqueue(object : Callback<AuthResponseAgent> {
                override fun onResponse(
                    call: Call<AuthResponseAgent>,
                    response: Response<AuthResponseAgent>
                ) {
                    if (response.isSuccessful) {
                        val authResponse = mapResponseAgent(response.body())
                        continuation.resume(authResponse)
                    }
                }

                override fun onFailure(call: Call<AuthResponseAgent>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private fun mapResponseAgent(body: AuthResponseAgent?): AuthResponseAgent {
        val callResponse = AuthResponseAgent()
        callResponse.token = body!!.token
        callResponse.agent.userId = body.agent.userId
        callResponse.agent.userName = body.agent.userName
        callResponse.agent.fullName = body.agent.fullName
        callResponse.agent.password = body.agent.password
        callResponse.agent.email = body.agent.email
        callResponse.agent.address = body.agent.address
        callResponse.agent.role = body.agent.role
        callResponse.agent.phoneNumber = body.agent.phoneNumber
        callResponse.agent.currentLocation = body.agent.currentLocation
        callResponse.agent.requestIds = body.agent.requestIds

        return callResponse
    }

    suspend fun createAdmin(admin: Admin): AuthResponseAdmin {
        return suspendCoroutine { continuation ->
            val call = apiService.newAdmin(admin)
            call.enqueue(object : Callback<AuthResponseAdmin> {
                override fun onResponse(call: Call<AuthResponseAdmin>, response: Response<AuthResponseAdmin>) {
                    if (response.isSuccessful) {
                        val authResponse = mapResponseAdmin(response.body())
                        continuation.resume(authResponse)
                        Log.i("Create customerR: ", response.body()?.admin.toString())
                    }
                }

                override fun onFailure(call: Call<AuthResponseAdmin>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private fun mapResponseAdmin(body: AuthResponseAdmin?): AuthResponseAdmin {
            val callResponse = AuthResponseAdmin()
            callResponse.token = body!!.token
            callResponse.admin.userId = body.admin.userId
            callResponse.admin.userName = body.admin.userName
            callResponse.admin.fullName = body.admin.fullName
            callResponse.admin.password = body.admin.password
            callResponse.admin.email = body.admin.email
            callResponse.admin.address = body.admin.address
            callResponse.admin.role = body.admin.role
            callResponse.admin.phoneNumber = body.admin.phoneNumber

        return callResponse
    }

    suspend fun login(userName: String, password: String): AuthResponse {
        return suspendCoroutine { continuation ->
            val call = apiService.signIn(userName, password)
            call.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = mapResponse(response.body())
                        continuation.resume(authResponse)
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}