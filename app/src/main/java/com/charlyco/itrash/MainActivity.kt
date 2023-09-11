package com.charlyco.itrash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.screens.AuthScreen
import com.charlyco.itrash.screens.ChangePassword
import com.charlyco.itrash.screens.CreateNewBin
import com.charlyco.itrash.screens.SendRequestScreen
import com.charlyco.itrash.screens.HomeScreenAdmin
import com.charlyco.itrash.screens.HomeScreenAgent
import com.charlyco.itrash.screens.HomeScreenCustomer
import com.charlyco.itrash.screens.RequestDetailScreen
import com.charlyco.itrash.screens.SignInScreen
import com.charlyco.itrash.screens.SignUpScreen
import com.charlyco.itrash.screens.WelcomeScreen
import com.charlyco.itrash.ui.theme.ITrashTheme
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.AuthViewModelFactory
import com.charlyco.itrash.viewModels.BinViewModel
import com.charlyco.itrash.viewModels.BinViewModelFactory
import com.charlyco.itrash.viewModels.HomeViewModel
import com.charlyco.itrash.viewModels.HomeViewModelFactory
import com.charlyco.itrash.viewModels.RequestViewModel
import com.charlyco.itrash.viewModels.RequestViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location = Location()
    private val LOCATION_PERMISSION_REQUEST_CODE = 0
    private lateinit var dataStoreManager: DataStoreManager
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager.getInstance(applicationContext)
        val cancellationTokenSource = CancellationTokenSource()
        getLocation(cancellationTokenSource)
        setContent {
            ITrashApplication(dataStoreManager)
        }
    }

    private fun getLocation(cancellationTokenSource: CancellationTokenSource) {
        val cancellationToken = cancellationTokenSource.token
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationToken
            ).addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = Location(location.latitude, location.longitude)
                    saveLocationData(currentLocation)
                }
            }
        }
    }

    private fun saveLocationData(location: Location) {
       lifecycleScope.launch { dataStoreManager.writeLocationData(location) }
    }
}

@Composable
fun ITrashApplication(dataStoreManager: DataStoreManager) {
    ITrashTheme {
        val navController = rememberNavController()
        val owner = LocalViewModelStoreOwner.current
        val authViewModel: AuthViewModel? =
            owner?.let {
                viewModel(
                    it, "AuthViewModel", AuthViewModelFactory()
                )
            }
        val homeViewModel: HomeViewModel? =
            owner?.let {
                viewModel(
                    it, "HomeViewModel", HomeViewModelFactory()
                )
            }
        val requestViewModel:RequestViewModel? =
            owner?.let {
                viewModel(
                    it, "RequestViewModel", RequestViewModelFactory())
            }
        val binViewModel: BinViewModel? =
            owner?.let {
                viewModel(
                    it, "BinViewModel", BinViewModelFactory()
                )
            }

        NavHost(navController = navController, startDestination = "welcome_screen") {
            composable("welcome_screen") {
                WelcomeScreen(navController)
            }
            composable("sign_in") {
                SignInScreen(navController, authViewModel, requestViewModel, dataStoreManager)
            }
            composable("sign_up") {
                SignUpScreen(navController, authViewModel, requestViewModel, dataStoreManager)
            }
            composable("change_password") {
                ChangePassword(navController, authViewModel)
            }
            composable("auth_screen") {
                AuthScreen(navController)
            }
            composable("home_screen") {
                HomeScreenCustomer(navController = navController, authViewModel, binViewModel, dataStoreManager)
            }
            composable("new_bin") {
                CreateNewBin(navController = navController, binViewModel, authViewModel, dataStoreManager)
            }
            composable("update_bin") {
                UpdateBin(navController, dataStoreManager)
            }
            composable("disposal") {
                SendRequestScreen(navController, requestViewModel, binViewModel, authViewModel, dataStoreManager)
            }
            composable("agent_home") {
                HomeScreenAgent(navController = navController, requestViewModel, binViewModel, dataStoreManager)
            }
            composable("request_detail") {
                RequestDetailScreen(requestViewModel, navController, binViewModel, authViewModel, dataStoreManager)
            }
            composable("admin_home") {
                HomeScreenAdmin(authViewModel, requestViewModel, binViewModel, navController)
            }
        }
    }
}

@Composable
fun UpdateBin(navController: NavHostController, dataStoreManager: DataStoreManager) {

}





