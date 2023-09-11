package com.charlyco.itrash.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.data.Customer
import com.charlyco.itrash.data.DisposalRequest
import com.charlyco.itrash.data.User
import com.charlyco.itrash.ui_utils.ToolBarWithNavIcon
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import com.charlyco.itrash.viewModels.RequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestViewModel: RequestViewModel?,
    navController: NavHostController,
    binViewModel: BinViewModel?,
    authViewModel: AuthViewModel?,
    dataStoreManager: DataStoreManager
) {
    val coroutineScope = rememberCoroutineScope()
    val request: DisposalRequest? = requestViewModel?.requestDetails?.observeAsState()?.value
    val bin: Bin? = binViewModel?.binById?.observeAsState()?.value
    val customer: Customer? = requestViewModel?.customer?.observeAsState()?.value
    val context = LocalContext.current.applicationContext
    val agent: User? = authViewModel?.authUser!!.observeAsState().value
Scaffold(
    topBar = {
        ToolBarWithNavIcon(
            navController = navController,
            route = "agent_home",
            color = MaterialTheme.colorScheme.background)},
    containerColor = MaterialTheme.colorScheme.background
    ) {contentPadding ->
        Column(
            modifier = Modifier.padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            RequestDetail(modifier = Modifier.padding(contentPadding), request, bin, customer)
            Spacer(modifier = Modifier.height(32.dp))
            HandleRequestButton(requestViewModel, context, request, agent, dataStoreManager, coroutineScope)
        }
    }
}


@Composable
fun HandleRequestButton(
    requestViewModel: RequestViewModel?,
    context: Context?,
    request: DisposalRequest?,
    agent: User?,
    dataStoreManager: DataStoreManager,
    coroutineScope: CoroutineScope
) {
    val isRequestAssigned = requestViewModel?.isRequestAssigned?.observeAsState()?.value
    Button(
        onClick = {
                  coroutineScope.launch {
                      request?.agentId = agent?.userId
                      requestViewModel?.assignAgentToRequest(request?.requestId, agent?.userId, dataStoreManager)
                  }
            if (isRequestAssigned == true) {
                Toast.makeText(context,
                    "Request with Id: ${request?.requestId}, assigned to ${agent?.userName}",
                    Toast.LENGTH_LONG).show()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
        Text(text = stringResource(id = R.string.handle_request))
    }
}

@Composable
fun RequestDetail(modifier: Modifier, request: DisposalRequest?, bin: Bin?, customer: Customer?) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (header, status, date, binOwner, binAddress, geoLocation, binSize) = createRefs()

        Text(
            text = stringResource(id = R.string.request_header),
            fontSize = TextUnit(20.0f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.constrainAs(header) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, margin = 8.dp)
            }
        )
        Text(
            text = "Request Status: ${request?.requestStatus}",
            modifier = Modifier.constrainAs(status) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(header.bottom, margin = 16.dp)
                }
            )
        Text(
            text = "Date of Request: ${request?.requestDate}",
            Modifier.constrainAs(date) {
                top.linkTo(status.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
                }
            )
        Text(
            text = "Name of requesting customer: ${customer?.fullName}",
            Modifier.constrainAs(binOwner) {
                top.linkTo(date.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        Text(
            text = "Size of trash bin: ${bin?.binSize}",
            Modifier.constrainAs(binSize) {
                top.linkTo(binOwner.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        Text(
            text = "Trash Bin located at: ${bin?.address}",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .constrainAs(binAddress) {
                    top.linkTo(binSize.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
        )
        Text(
            text = "Click here to view location on Google Map",
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clickable { /* Open Google map and point to location on map*/ }
                .constrainAs(geoLocation){
                    top.linkTo(binAddress.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
            )
    }
}
