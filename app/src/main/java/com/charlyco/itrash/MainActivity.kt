package com.charlyco.itrash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.screens.AuthScreen
import com.charlyco.itrash.screens.ChangePassword
import com.charlyco.itrash.screens.HomeScreenAgent
import com.charlyco.itrash.screens.HomeScreenCustomer
import com.charlyco.itrash.screens.RequestDetailScreen
import com.charlyco.itrash.screens.SignInScreen
import com.charlyco.itrash.screens.SignUpScreen
import com.charlyco.itrash.screens.WelcomeScreen
import com.charlyco.itrash.ui.theme.ITrashTheme
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

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var currentLocation: Location = Location()
    private val LOCATION_PERMISSION_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val cancellationTokenSource = CancellationTokenSource()
            val  cancellationToken = cancellationTokenSource.token
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            } else {
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    cancellationToken
                ).addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = Location(location.latitude, location.longitude)
                    }
                }
            }
            ITrashApplication(currentLocation)
        }
    }
}

@Composable
fun ITrashApplication(currentLocation: Location) {
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
                SignInScreen(navController, authViewModel)
            }
            composable("sign_up") {
                SignUpScreen(navController, authViewModel)
            }
            composable("change_password") {
                ChangePassword(navController, authViewModel)
            }
            composable("auth_screen") {
                AuthScreen(navController)
            }
            composable("home_screen") {
                HomeScreenCustomer(navController = navController, homeViewModel)
            }
            composable("new_bin") {
                NewBin(navController)
            }
            composable("update_bin") {
                UpdateBin(navController)
            }
            composable("disposal") {
                DisposalRequestScreen(navController, requestViewModel)
            }
            composable("agent_home") {
                HomeScreenAgent(navController = navController, requestViewModel, currentLocation)
            }
            composable("request_detail") {
                RequestDetailScreen(requestViewModel, navController, binViewModel, authViewModel)
            }
        }
    }
}


@Composable
fun DisposalRequestScreen(navController: NavHostController, requestViewModel: RequestViewModel?) {

}

@Composable
fun UpdateBin(navController: NavHostController) {

}

@Composable
fun NewBin(navController: NavHostController) {

}




