package com.charlyco.itrash.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.data.User
import com.charlyco.itrash.ui_utils.HomeScreenToolsBar
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCustomer(
    navController: NavController,
    authViewModel: AuthViewModel?,
    binViewModel: BinViewModel?,
    dataStoreManager: DataStoreManager
) {
    val coroutineScope = rememberCoroutineScope()
    val userDetail: User? = authViewModel?.authUser!!.observeAsState().value
    val binList: MutableList<Bin>? = binViewModel?.binListByUser?.observeAsState()?.value
    Scaffold(topBar = { HomeScreenToolsBar(navController = navController)}) { contentPadding ->
        HomeScreenUi(
            navController,
            binViewModel,
            userDetail,
            binList,
            coroutineScope,
            dataStoreManager,
            Modifier.padding(contentPadding))
    }
}

@Composable
fun HomeScreenUi(
    navController: NavController,
    binViewModel: BinViewModel?,
    userDetail: User?,
    binList: List<Bin>?,
    coroutineScope: CoroutineScope,
    dataStoreManager: DataStoreManager,
    modifier: Modifier,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
    val customerId = userDetail?.userId
    val context = LocalContext.current.applicationContext
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize()
            ) {
            RequestForDisposal(
                navController,
                binViewModel,
                customerId,
                modifier = Modifier,
                coroutineScope,
                dataStoreManager,
                screenWidth)
            Spacer(modifier = Modifier.height(16.dp))
            RegisterNewBin(
                navController,
                modifier = Modifier,
                screenWidth)
            Spacer(modifier = Modifier.height(16.dp))
            UpdateBinDetails(
                navController = navController,
                modifier = Modifier,
                screenWidth = screenWidth)
        }
    }
}

@Composable
fun RequestForDisposal(
    navController: NavController,
    binViewModel: BinViewModel?,
    customerId: Int?,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    dataStoreManager: DataStoreManager,
    screenWidth: Int,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .width(screenWidth.dp)
            .height(196.dp)
            .clickable {
                coroutineScope.launch {
                    binViewModel?.getBinsByUserId(customerId, dataStoreManager)
                    navController.navigate("disposal") {
                        popUpTo("home_screen")
                        launchSingleTop = true
                    }
                }
            }
        ) {
        Column(modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "",
                modifier = modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = R.string.request_disposal),
                fontSize = TextUnit(16.0f, TextUnitType.Sp),
                modifier = modifier.paddingFromBaseline(top = 16.dp)
                )
        }
    }
}

@Composable
fun RegisterNewBin(
    navController: NavController,
    modifier: Modifier,
    screenWidth: Int
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .width(screenWidth.dp)
            .height(196.dp)
            .clickable { navController.navigate("new_bin") {
                popUpTo("home_screen")
                launchSingleTop = true
            } }
    ) {
        Column(modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "",
                modifier = modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = R.string.register_bin),
                fontSize = TextUnit(16.0f, TextUnitType.Sp),
                modifier = modifier.paddingFromBaseline(top = 16.dp)
                )
        }
    }
}

@Composable
fun UpdateBinDetails(
    navController: NavController,
    modifier: Modifier,
    screenWidth: Int
) {
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = modifier
            .width(screenWidth.dp)
            .height(196.dp)
            .clickable { navController.navigate("update_bin") {
                popUpTo("home_screen")
                launchSingleTop = true
            } }
    ) {
        Column(modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "",
                modifier = modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = R.string.update_bin),
                fontSize = TextUnit(16.0f, TextUnitType.Sp),
                modifier = modifier.paddingFromBaseline(top = 16.dp)
            )
        }
    }
}
