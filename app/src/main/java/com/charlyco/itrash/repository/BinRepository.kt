package com.charlyco.itrash.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.charlyco.itrash.api.ItrashService
import com.charlyco.itrash.api.ItrashServiceBuilder
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.utils.DataStoreManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BinRepository(dataStoreManager: DataStoreManager) {
    var authToken = ""
    init {
        authToken = runBlocking {
            dataStoreManager.readTokenData()!!
            }
    }
    private val binApiService: ItrashService = ItrashServiceBuilder().buildService(authToken).create(ItrashService::class.java)
    suspend fun createNewBin(newBin: Bin): Int{
        return suspendCoroutine { continuation ->
            val call = binApiService.createNewBin(newBin)
            call.enqueue(object: Callback<Int> {
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

    suspend fun getBinByBinId(binId: Int?): Bin {
        return suspendCoroutine { continuation ->
            val call = binId?.let { binApiService.getBinDetailById(it) }
            call?.enqueue(object: Callback<Bin> {
                override fun onResponse(call: Call<Bin>, response: Response<Bin>) {
                    if (response.isSuccessful && response.body() != null) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<Bin>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    suspend fun getBinsByUserId(userId: Int?): MutableList<Bin> {
        return suspendCoroutine { continuation ->
            val call = userId?.let { binApiService.getBinsByUserId(it) }
            call?.enqueue(object: Callback<MutableList<Bin>> {
                override fun onResponse(call: Call<MutableList<Bin>>, response: Response<MutableList<Bin>>) {
                    if (response.isSuccessful && response.body() != null) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<MutableList<Bin>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}