package com.charlyco.itrash.api

import com.charlyco.itrash.data.Admin
import com.charlyco.itrash.data.Agent
import com.charlyco.itrash.data.AuthResponse
import com.charlyco.itrash.data.AuthResponseAdmin
import com.charlyco.itrash.data.AuthResponseAgent
import com.charlyco.itrash.data.AuthResponseCust
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.DisposalRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ItrashService {
    @GET("auth/signIn")
    fun signIn(@Query("userName") userName: String, @Query("password") password: String): Call<AuthResponse>

    @POST("auth/customer")
    fun newCustomer(@Body customer: Customer): Call<AuthResponseCust>

    @POST("auth/agent")
    fun newAgent(@Body agent: Agent): Call<AuthResponseAgent>

    @POST("auth/admin")
    fun newAdmin(@Body admin: Admin): Call<AuthResponseAdmin>

    @POST("bin")
    fun createNewBin(@Body bin: Bin): Call<Int>
    @GET("bin/{binId}")
    fun getBinDetailById(@Path("binId") binId: Int): Call<Bin>

    @GET("bin/userId")
    fun getBinsByUserId(@Query("userId") userId: Int): Call<MutableList<Bin>>

    @POST("disposal/create")
    fun createDisposalRequest(@Body request: DisposalRequest): Call<Int>
    @GET("disposal/location")
    fun getPendingRequestByLocation(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Call<MutableList<DisposalRequest>>
    @GET("disposal")
    fun getRequestDetailById(@Query("requestId") selectedRequestId: Int?): Call<DisposalRequest>
    @GET("users/customer/{id}")
    fun getCustomerById(@Path("id") customerId: Int?): Call<Customer>
    @PUT("disposal/assign/{requestId}")
    fun assignRequestToAgent(@Path("requestId") requestId: Int?, @Query("agentId") agentId: Int?): Call<Boolean>

}