package com.charlyco.itrash.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.charlyco.itrash.data.Location
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreManager(private val applicationContext: Context) {
    private object PreferencesKeys {
        val tokenKey = stringPreferencesKey("token")
        val locationKey = stringPreferencesKey("location")
    }

    // Singleton pattern for DataStoreManager
    companion object {
        private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "token_datastore")
        private val Context.locationDataStore: DataStore<Preferences> by preferencesDataStore(name = "location_datastore")
        @Volatile
        private var instance: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return instance ?: synchronized(this) {
                instance ?: DataStoreManager(context.applicationContext).also { instance = it }
            }
        }
    }

    suspend fun readTokenData(): String? {
        return applicationContext.tokenDataStore.data.map { it[PreferencesKeys.tokenKey] }.firstOrNull()
    }

    suspend fun writeTokenData(token: String) {
        applicationContext.tokenDataStore.edit { preferences ->
            preferences[PreferencesKeys.tokenKey] = token
        }
    }

    suspend fun readLocationData(): Location? {
        return applicationContext.locationDataStore.data.map {location ->
            val serializedLocation = location[PreferencesKeys.locationKey]?: ""
            if (serializedLocation.isEmpty()) {
                null
            }else {
                Json.decodeFromString<Location>(serializedLocation)
            }
        }.first()
    }

    suspend fun writeLocationData(location: Location) {
        val serialisedLocation = Json.encodeToString(location)
        applicationContext.locationDataStore.edit { preferences ->
            preferences[PreferencesKeys.locationKey] = serialisedLocation
        }
    }
}
