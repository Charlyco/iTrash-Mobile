package com.charlyco.itrash.viewModels

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.repository.BinRepository
import com.charlyco.itrash.utils.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class BinViewModel: ViewModel() {
    val binById: MutableLiveData<Bin> = MutableLiveData()
    val binListByUser: MutableLiveData<MutableList<Bin>> = MutableLiveData()
    suspend fun getBinsByUserId(userId: Int?, dataStoreManager: DataStoreManager) {
        val binRepository = BinRepository(dataStoreManager)
        val binListFromServer = binRepository.getBinsByUserId(userId)

        binListByUser.value = binListFromServer

    }


    suspend fun getBinsById(binId: Int?, dataStoreManager: DataStoreManager) {
        val binRepository = BinRepository(dataStoreManager)
        binById.value = binRepository.getBinByBinId(binId)
    }

    suspend fun saveBin(
        newBin: Bin,
        dataStoreManager: DataStoreManager
    ): Int {
        val location = dataStoreManager.readLocationData()
        newBin.latitude = location?.latitude!!
        newBin.longitude = location.longitude!!
        val binRepository = BinRepository(dataStoreManager)
        return binRepository.createNewBin(newBin)
    }
}
class BinViewModelFactory (): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BinViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}