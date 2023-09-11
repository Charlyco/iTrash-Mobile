package com.charlyco.itrash.viewModels

import android.print.PrintDocumentAdapter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.charlyco.itrash.data.Admin
import com.charlyco.itrash.data.Agent
import com.charlyco.itrash.data.AuthResponse
import com.charlyco.itrash.data.AuthResponseAdmin
import com.charlyco.itrash.data.AuthResponseAgent
import com.charlyco.itrash.data.AuthResponseCust
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.User
import com.charlyco.itrash.repository.AuthRepository
import com.charlyco.itrash.repository.BinRepository
import com.charlyco.itrash.utils.DataStoreManager
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    val authUser: MutableLiveData<User> = MutableLiveData()
    val token: MutableLiveData<String> = MutableLiveData("")
    private var repository = AuthRepository()

    companion object {
         val TOKEN_KEY = stringPreferencesKey("token")
    }
    suspend fun loginWithUsernameAndPassword(
        userName: String,
        password: String,
        dataStoreManager: DataStoreManager
    ): AuthResponse {
        val authResponse = repository.login(userName, password)
        authUser.value = authResponse.user
        dataStoreManager.writeTokenData(authResponse.token)
        //saveToken(tokenDataStore, authResponse.token)
        return authResponse
    }

    /*private fun saveToken(
        tokenDataStore: DataStore<Preferences>,
        token: String
    ) {
        viewModelScope.launch {
            tokenDataStore.edit { bearerToken ->
                bearerToken[TOKEN_KEY] = token
            }
        }
    }*/

    suspend fun createNewCustomer(user: User, dataStoreManager: DataStoreManager): AuthResponseCust {
        val customer = Customer()
        customer.userName = user.userName
        customer.fullName = user.fullName
        customer.email = user.email
        customer.password = user.password
        customer.address = user.address
        customer.role = user.role
        val authResponse = repository.createCustomer(customer)

        val token = authResponse.token
        authUser.value = mapCustomerToUser(authResponse.customer)
        dataStoreManager.writeTokenData(token)
        //saveToken(tokenDataStore, token)
        return authResponse
    }

    private fun mapCustomerToUser(customer: Customer): User {
        val user = User()
        user.userId = customer.userId
        user.role = customer.role
        user.email = customer.email
        user.userName = customer.userName
        user.phoneNumber = customer.phoneNumber.toString()
        user.fullName = customer.fullName
        return user
    }

    suspend fun createNewAgent(user: User, dataStoreManager: DataStoreManager): AuthResponseAgent {
        val agent = Agent()
        agent.userName = user.userName
        agent.fullName = user.fullName
        agent.email = user.email
        agent.password = user.password
        agent.address = user.address
        agent.role = user.role
        val authResponse = repository.createAgent(agent)

        val token = authResponse.token
        authUser.value = mapAgentToUser(authResponse.agent)
        dataStoreManager.writeTokenData(token)
        //saveToken(tokenDataStore, token)
        return authResponse
    }

    private fun mapAgentToUser(agent: Agent): User {
        val user = User()
        user.userId = agent.userId
        user.role = agent.role
        user.email = agent.email
        user.userName = agent.userName
        user.phoneNumber = agent.phoneNumber.toString()
        user.fullName = agent.fullName
        return user
    }

    suspend fun createNewAdmin(user: User, dataStoreManager: DataStoreManager): AuthResponseAdmin {
        val admin = Admin()
        admin.userName = user.userName
        admin.fullName = user.fullName
        admin.email = user.email
        admin.password = user.password
        admin.address = user.address
        admin.role = user.role
        val authResponse = repository.createAdmin(admin)


        val token = authResponse.token
        authUser.value = mapAdminToUser(authResponse.admin)
        dataStoreManager.writeTokenData(token)
        //saveToken(tokenDataStore, token)
        return authResponse
    }

    private fun mapAdminToUser(admin: Admin): User {
        val user = User()
        user.userId = admin.userId
        user.role = admin.role
        user.email = admin.email
        user.userName = admin.userName
        user.phoneNumber = admin.phoneNumber.toString()
        user.fullName = admin.fullName
        return user
    }

    fun saveLoginDetails(text: String, text1: String) {

    }

    fun getCustomerById(customerId: Int?): Customer? {
        //get customer object from server
        return null
    }
}

class AuthViewModelFactory (): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}