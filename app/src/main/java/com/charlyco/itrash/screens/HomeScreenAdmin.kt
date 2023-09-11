package com.charlyco.itrash.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.charlyco.itrash.R
import com.charlyco.itrash.ui_utils.HomeScreenToolsBar
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import com.charlyco.itrash.viewModels.RequestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAdmin(
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    binViewModel: BinViewModel?,
    navController: NavController
) {
    val screenWith = LocalConfiguration.current.screenWidthDp - 64
    Scaffold(
        topBar = { HomeScreenToolsBar(navController = navController)},
        containerColor = MaterialTheme.colorScheme.surface
    ) {paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminScreenContents(
                    modifier = Modifier.padding(paddingValues),
                    authViewModel,
                    requestViewModel,
                    binViewModel,
                    screenWith
                )
            }
        }
    }
}

@Composable
fun AdminScreenContents(
    modifier: Modifier,
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    binViewModel: BinViewModel?,
    screenWith: Int
) {
        Column {
            ViewAllAgents(authViewModel, screenWith)
            Spacer(modifier = Modifier.height(4.dp))
            ViewAllCustomers(authViewModel, screenWith)
        }
    }

@Composable
fun ViewAllCustomers(authViewModel: AuthViewModel?, screenWith: Int) {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .width(screenWith.dp)
            .padding(vertical = 2.dp)
            .paddingFromBaseline(bottom = 2.dp)
            .clickable { }
    ) {
        ConstraintLayout {
            val (icon, text) = createRefs()

            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
                modifier = Modifier.constrainAs(icon) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, margin = 4.dp)
                }
            )
            Text(
                text = stringResource(id = R.string.all_customers),
                modifier = Modifier.constrainAs(text) {
                    centerHorizontallyTo(parent)
                    top.linkTo(icon.bottom, margin = 4.dp)
                }
            )
        }
    }
}

@Composable
fun ViewAllAgents(authViewModel: AuthViewModel?, screenWith: Int) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .width(screenWith.dp)
            .padding(vertical = 2.dp)
            .paddingFromBaseline(bottom = 2.dp)
            .clickable { }
    ) {
        ConstraintLayout {
            val (icon, text) = createRefs()

            Icon(
                imageVector = Icons.Default.AddBusiness,
                contentDescription = "",
                modifier = Modifier.constrainAs(icon) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, margin = 4.dp)
                }
                )
            Text(
                text = stringResource(id = R.string.all_agents),
                modifier = Modifier.constrainAs(text) {
                    centerHorizontallyTo(parent)
                    top.linkTo(icon.bottom, margin = 4.dp)
                }
                )
        }
    }
}
