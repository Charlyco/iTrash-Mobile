package com.charlyco.itrash.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.R
import com.charlyco.itrash.ui_utils.HomeScreenToolsBar
import com.charlyco.itrash.viewModels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCustomer(navController: NavController, homeViewModel: HomeViewModel?) {
    Scaffold(topBar = { HomeScreenToolsBar(navController = navController)}) { contentPadding ->
        HomeScreenUi(navController, homeViewModel, Modifier.padding(contentPadding))
    }
}

@Composable
fun HomeScreenUi(
    navController: NavController,
    homeViewModel: HomeViewModel?,
    modifier: Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
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
                modifier = Modifier,
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
    modifier: Modifier,
    screenWidth: Int
) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .width(screenWidth.dp)
            .height(196.dp)
            .clickable { navController.navigate("disposal") }
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
            .clickable { navController.navigate("new_bin") }
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
            .clickable { navController.navigate("update_bin") }
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

@Preview
@Composable
fun PreviewHome() {
    HomeScreenCustomer(navController = rememberNavController(), homeViewModel = HomeViewModel())
}