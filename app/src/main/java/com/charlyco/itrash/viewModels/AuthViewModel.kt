package com.charlyco.itrash.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.charlyco.itrash.data.Admin
import com.charlyco.itrash.data.Agent
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.Role
import com.charlyco.itrash.data.User

class AuthViewModel: ViewModel() {
    var authState: MutableLiveData<String> = MutableLiveData("")
    val user: User = User()

    fun loginWithEmailAndPassword(email: String, password: String) {
        authState.value = "charlyco84@gmail.com"
    }

    fun saveUserAndReturnToken(user: User) {
        when(user.role) {
            Role.CUSTOMER -> {
                val customer = Customer()
                customer.userName = user.userName
                customer.fullName = user.fullName
                customer.email = user.email
                customer.password = user.password
                customer.address = user.address
                customer.role = user.role
                //Call Repository method to save user to online database
            }
            Role.AGENT -> {
                val agent = Agent()
                agent.userName = user.userName
                agent.fullName = user.fullName
                agent.email = user.email
                agent.password = user.password
                agent.address = user.address
                agent.role = user.role
                //Call Repository method to save user to online database
            } else -> {
            val admin = Admin()
            admin.userName = user.userName
            admin.fullName = user.fullName
            admin.email = user.email
            admin.password = user.password
            admin.address = user.address
            admin.role = user.role
            //Call Repository method to save user to online database
            }
        }
    }

    fun saveLoginDetails(text: String, text1: String) {

    }

    fun getCustomerById(customerId: Int?): Customer? {
        TODO("Not yet implemented")
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