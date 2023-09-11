package com.charlyco.itrash.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Bin
import com.charlyco.itrash.data.BinOwnership
import com.charlyco.itrash.data.BinSize
import com.charlyco.itrash.data.BinStatus
import com.charlyco.itrash.data.User
import com.charlyco.itrash.ui_utils.ToolBarWithNavIcon
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.BinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewBin(
    navController: NavHostController,
    binViewModel: BinViewModel?,
    authViewModel: AuthViewModel?,
    dataStoreManager: DataStoreManager
) {
    val context = LocalContext.current.applicationContext
    val user: User? = authViewModel?.authUser!!.observeAsState().value
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { ToolBarWithNavIcon(
            navController = navController,
            route = "home_screen",
            color = MaterialTheme.colorScheme.surface)},
        containerColor = MaterialTheme.colorScheme.surface
    ) {paddingValues ->
       NewBinUi(
           Modifier.padding(paddingValues),
           context,
           binViewModel,
           coroutineScope,
           dataStoreManager,
           user
           )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBinUi(
    modifier: Modifier,
    context: Context?,
    binViewModel: BinViewModel?,
    coroutineScope: CoroutineScope,
    dataStoreManager: DataStoreManager,
    user: User?
) {
    val binSize = BinSize.entries
    var selectedSize by remember{ mutableStateOf(binSize[0])}
    val screenWith = LocalConfiguration.current.screenWidthDp - 32
    var address by remember { mutableStateOf("") }
    val newBin = Bin()

    Column(
        modifier = Modifier.padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = stringResource(
                id = R.string.create_bin_header),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(16.0f, TextUnitType.Sp),
            modifier = Modifier.padding(horizontal = 8.dp)
            )
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = stringResource(id = R.string.select_size))
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            binSize.forEach { size ->
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        selected = size == selectedSize,
                        onClick = {
                            selectedSize = size
                        })
                    Text(text = size.name,
                        color = MaterialTheme.colorScheme.onBackground
                        )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            coroutineScope.launch {
                newBin.binStatus = BinStatus.FULL.name
                newBin.userId = user?.userId
                newBin.address = user?.address
                newBin.binSize = selectedSize.name
                newBin.ownership = BinOwnership.PRIVATE.name
                val binId = binViewModel?.saveBin(newBin, dataStoreManager)
                if (binId != null) {
                    Toast.makeText(context, "Bin created with assigned Id: $binId", Toast.LENGTH_LONG).show()
                }
            }
        }) {
            Text(text = stringResource(id = R.string.create_bin))
        }
    }
}
