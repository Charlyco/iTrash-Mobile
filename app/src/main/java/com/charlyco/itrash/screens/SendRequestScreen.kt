package com.charlyco.itrash.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.RequestStatus
import com.charlyco.itrash.data.User
import com.charlyco.itrash.ui_utils.ToolBarWithNavIcon
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import com.charlyco.itrash.viewModels.RequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendRequestScreen(
    navController: NavController,
    requestViewModel: RequestViewModel?,
    binViewModel: BinViewModel?,
    authViewModel: AuthViewModel?,
    dataStoreManager: DataStoreManager
) {
    val userDetail : User? = authViewModel?.authUser!!.observeAsState().value
    Scaffold(
        topBar = {
            ToolBarWithNavIcon(
                navController = navController,
                route = "home_screen",
                color = MaterialTheme.colorScheme.background)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {contentPadding ->
        CreateRequestUi(Modifier.padding(contentPadding),
            requestViewModel,
            binViewModel,
            userDetail,
            dataStoreManager,
            navController)
    }
}

@Composable
fun CreateRequestUi(
    modifier: Modifier,
    requestViewModel: RequestViewModel?,
    binViewModel: BinViewModel?,
    userDetail: User?,
    dataStoreManager: DataStoreManager,
    navController: NavController
) {
    val context = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth(),
            rememberLazyListState(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            item{
                SelectableBinList(requestViewModel, binViewModel)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SendRequestButton(requestViewModel, context, userDetail, coroutineScope, dataStoreManager, navController)
            }
        }

    }
}

@Composable
fun SendRequestButton(
    requestViewModel: RequestViewModel?,
    context: Context,
    userDetail: User?,
    coroutineScope: CoroutineScope,
    dataStoreManager: DataStoreManager,
    navController: NavController,

    ) {
    val requestId = requestViewModel?.requestId?.observeAsState()?.value
    Button(
        onClick = {
            coroutineScope.launch {
                requestViewModel?.newRequest?.customerId = userDetail?.userId
                requestViewModel?.newRequest?.requestDate = LocalDateTime.now().toString()
                requestViewModel?.newRequest?.requestStatus = RequestStatus.SENT.name
                requestViewModel?.sendDisposalRequest(dataStoreManager)
            }
            if (requestId != null) {
                Toast.makeText(context, "Request Sent and current Id is: $requestId", Toast.LENGTH_LONG).show()
                navController.navigate("home_screen") {
                    popUpTo("disposal"){inclusive = true}
                }
            }
                  },

    ) {
        Text(text = stringResource(id = R.string.send_request))
    }
}

@Composable
fun SelectableBinList(
    requestViewModel: RequestViewModel?,
    binViewModel: BinViewModel?,
) {
    val binList = binViewModel?.binListByUser?.observeAsState()?.value
    if (binList != null && binList.size > 0) {
        var selectedBin by remember { mutableStateOf(binList[0])}
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            binList.forEach { bin ->
                Surface(modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = bin == selectedBin,
                        onClick = {
                            selectedBin = bin
                            requestViewModel?.newRequest?.binId = bin.binId
                        }
                    )) {
                    ConstraintLayout {
                        val (radio, image, binId) = createRefs()
                        RadioButton(
                            selected = bin == selectedBin,
                            onClick = {
                                selectedBin = bin
                                requestViewModel?.newRequest?.binId = bin.binId },
                            modifier = Modifier.constrainAs(radio) {
                                start.linkTo(parent.start, margin = 4.dp)
                                centerVerticallyTo(parent)
                            }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.binpng),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(96.dp)
                                .constrainAs(image) {
                                    top.linkTo(parent.top, margin = 8.dp)
                                    start.linkTo(radio.end, margin = 8.dp)
                                }
                        )
                        Text(
                            text = "Bin ID: ${bin.binId}",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.constrainAs(binId) {
                                top.linkTo(image.bottom, margin = 8.dp)
                                start.linkTo(radio.end, margin = 8.dp)
                            }
                        )
                    }
                }
            }
        }

        }
    }

