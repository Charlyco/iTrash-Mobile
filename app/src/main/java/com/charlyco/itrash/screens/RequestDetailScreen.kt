package com.charlyco.itrash.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import com.charlyco.itrash.viewModels.RequestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(
    requestViewModel: RequestViewModel?,
    navController: NavHostController,
    binViewModel: BinViewModel?,
    authViewModel: AuthViewModel?
) {
    val request: DisposalRequest? = requestViewModel?.getRequestById(requestViewModel.selectedRequestId)
    val bin: Bin? = binViewModel?.getBinsById(request?.binId)
    val customer: Customer? = authViewModel?.getCustomerById(request?.customerId)
    val context = LocalContext.current.applicationContext
Scaffold(
    topBar = {},
    ) {contentPadding ->
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
        ) {
        Column {
            RequestDetail(modifier = Modifier.padding(contentPadding), request, bin, customer)
            HandleRequestButton(authViewModel,requestViewModel, context, request)
        }
    }
}
}

@Composable
fun HandleRequestButton(
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    context: Context?,
    request: DisposalRequest?
) {
    Button(
        onClick = {
            request?.agentId = authViewModel?.user?.id
            requestViewModel?.assignAgentToRequest(request?.requestId, authViewModel?.user?.id)
            Toast.makeText(context,
                "Request with Id: ${request?.requestId}, assigned to ${authViewModel?.user?.userName}",
                Toast.LENGTH_LONG).show()
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
    ConstraintLayout {
        val (header, status, date, binOwner, binLocation, binSize) = createRefs()

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
            text = "Request Status: ${request?.requestStatus?.name!!}",
            modifier = Modifier.constrainAs(status) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(header.bottom, margin = 16.dp)
                }
            )
        Text(
            text = "Date of Request: ${request.requestDate}",
            modifier.constrainAs(date) {
                top.linkTo(status.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
                }
            )
        Text(
            text = "Name of requesting customer: ${customer?.fullName}",
            modifier.constrainAs(binOwner) {
                top.linkTo(date.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        Text(
            text = "Size of trash bin: ${bin?.binSize}",
            modifier.constrainAs(binSize) {
                top.linkTo(binOwner.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        Text(
            text = "Trash Bin located at: ${bin?.location}",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { /* Open Google map and point to location on map*/ }
                .constrainAs(binLocation) {
                    top.linkTo(binSize.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
        )
    }
}
