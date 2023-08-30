package com.charlyco.itrash.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charlyco.itrash.data.Bin

class BinViewModel: ViewModel() {
    fun getBinsByUserId(userId: Int?): List<Bin> {
        return emptyList()
    }

    fun getBinsById(binId: Int?): Bin {
        TODO("Not yet implemented")
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