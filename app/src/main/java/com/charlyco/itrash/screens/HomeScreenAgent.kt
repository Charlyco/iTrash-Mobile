package com.charlyco.itrash.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.DisposalRequest
import com.charlyco.itrash.data.Location
import com.charlyco.itrash.ui_utils.HomeScreenToolsBar
import com.charlyco.itrash.viewModels.RequestViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAgent(
    navController: NavController,
    requestViewModel: RequestViewModel?,
    agentLocation: Location
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
    Scaffold(
        topBar = { HomeScreenToolsBar(navController = navController) }
    ) {
        Surface(
            modifier = Modifier.padding(top = 64.dp)) {
            PendingRequestList(
                modifier = Modifier.padding(it),
                navController,
                requestViewModel,
                agentLocation,
                screenWidth)
        }
    }
}

@Composable
fun PendingRequestList(
    modifier: Modifier,
    navController: NavController,
    requestViewModel: RequestViewModel?,
    agentLocation: Location,
    screenWidth: Int
) {
    val requestList: List<DisposalRequest> = requestViewModel!!.getAllPendingRequest(agentLocation)
    LazyColumn(modifier = Modifier.padding(top = 4.dp),
        rememberLazyListState() ) {
        items(requestList) { request ->
           RequestItem(request, navController, screenWidth, requestViewModel)
        }
    }
}

@Composable
fun RequestItem(
    request: DisposalRequest,
    navController: NavController,
    screenWidth: Int,
    requestViewModel: RequestViewModel
) {
    Surface(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .clickable {
                requestViewModel.setSelectedRequestId(request.requestId)
                navController.navigate("request_detail")
            },
            //.width(screenWidth.dp),
        color = MaterialTheme.colorScheme.background
        ) {
        ConstraintLayout {
           val (header,requestId, binId, customerId, status) = createRefs()
            Text(
                text = stringResource(id = R.string.request_header),
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                fontSize = TextUnit(16.0f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
                )
            Text(
                text = "Request ID: ${request.requestId}",
                modifier = Modifier.constrainAs(requestId) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(header.bottom, margin = 4.dp)
                    }
                )
            Text(
                text = "Request Status: ${request.requestStatus}",
                modifier = Modifier.constrainAs(status) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(requestId.bottom, margin = 4.dp)
                },
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Bin Id: ${request.binId}",
                modifier = Modifier.constrainAs(binId) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(status.bottom, margin = 4.dp)
                }
            )
            Text(
                text = "Customer ID: ${request.customerId}",
                modifier = Modifier.constrainAs(customerId) {
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(binId.bottom, margin = 4.dp)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeAgent() {
    HomeScreenAgent(
        navController = rememberNavController(),
        requestViewModel = RequestViewModel(),
        agentLocation = Location(0.0, 0.0)
    )
}