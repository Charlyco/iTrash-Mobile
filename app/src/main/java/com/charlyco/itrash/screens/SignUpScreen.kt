package com.charlyco.itrash.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Role
import com.charlyco.itrash.data.User
import com.charlyco.itrash.ui_utils.PlainTopBar
import com.charlyco.itrash.utils.DataStoreManager
import com.charlyco.itrash.viewModels.AuthViewModel
import com.charlyco.itrash.viewModels.RequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private val user = User()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    dataStoreManager: DataStoreManager
) {
val createdUser: User? = authViewModel!!.authUser.observeAsState().value
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {PlainTopBar(navController = navController, route = "auth_screen")}
    ) {
            ScreenContents(
                modifier = Modifier.padding(it),
                navController = navController,
                authViewModel = authViewModel,
                requestViewModel = requestViewModel,
                dataStoreManager,
                coroutineScope = coroutineScope
            )
        }
    }

@Composable
fun ScreenContents(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    dataStoreManager: DataStoreManager,
    coroutineScope: CoroutineScope,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(top = 64.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(modifier = Modifier)
            InputBoxesCard(
                Modifier,
                navController,
                authViewModel,
                requestViewModel,
                screenWidth,
                dataStoreManager,
                coroutineScope)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBoxesCard(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?,
    requestViewModel: RequestViewModel?,
    screenWidth: Int,
    dataStoreManager: DataStoreManager,
    coroutineScope: CoroutineScope
) {
    var fullName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    user.fullName = fullName
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                placeholder = {Text(text = stringResource(id = R.string.full_name))},
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {Icon(imageVector = Icons.Default.Person, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 4.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = userName,
                onValueChange = {
                    userName = it
                    user.userName = userName
                },
                placeholder = {Text(text = stringResource(id = R.string.user_name))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    user.password = password
                },
                placeholder = {Text(text = stringResource(id = R.string.password))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                }else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .height(48.dp)
                    .width(screenWidth.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    user.email = email
                },
                placeholder = {Text(text = stringResource(id = R.string.email))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
        Spacer(modifier = modifier.height(12.dp))
        TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                user.phoneNumber = phoneNumber
            },
            placeholder = {Text(text = stringResource(id = R.string.phone))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.onBackground
            ),
            shape = MaterialTheme.shapes.medium,
            leadingIcon = {Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")},
            modifier = modifier
                .paddingFromBaseline(top = 10.dp)
                .width(screenWidth.dp)
                .height(48.dp)
        )
            Spacer(modifier = modifier.height(12.dp))
            TextField(
                value = address,
                onValueChange = {
                    address = it
                    user.address = address
                },
                placeholder = {Text(text = stringResource(id = R.string.address))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {Icon(imageVector = Icons.Default.Place, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(12.dp))
            RolesCheckBox()
            Spacer(modifier = modifier.height(12.dp))
            Button(
                modifier = modifier
                                .width(screenWidth.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    coroutineScope.launch {
                            when(user.role) {
                                Role.CUSTOMER.name -> {
                                    val authUser = authViewModel?.createNewCustomer(user, dataStoreManager)
                                    if (authUser?.customer?.userName == user.userName) {
                                        navController.navigate("home_screen") {
                                        popUpTo("auth_screen") {inclusive = true}
                                        launchSingleTop = true }
                                    }
                                }
                                Role.AGENT.name -> {
                                    val authUser = authViewModel?.createNewAgent(user, dataStoreManager)
                                    if (authUser?.agent?.userName == user.userName) {
                                            requestViewModel?.getAllPendingRequest(dataStoreManager)
                                        navController.navigate("agent_home") {
                                        popUpTo("auth_screen") {inclusive = true}
                                        launchSingleTop = true }
                                    }
                                }
                                else -> {
                                    val authUser = authViewModel?.createNewAdmin(user, dataStoreManager)
                                    if (authUser?.admin?.userName == user.userName) {
                                        navController.navigate("admin_home") {
                                        popUpTo("auth_screen") {inclusive = true}
                                        launchSingleTop = true }
                                    }
                                }
                            }
                        }
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.create_account),
                    fontSize = TextUnit(14.0F, TextUnitType.Sp),
                    fontFamily = FontFamily.SansSerif
                )
        }
    }
}

@Composable
fun Logo(modifier: Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "",
            modifier = modifier.size(88.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.fill_info),
            fontSize = TextUnit(14.0f, TextUnitType.Sp),
            textAlign = TextAlign.Center,
            modifier = modifier.paddingFromBaseline(bottom = 16.dp, top = 16.dp)
        )
    }
}

@Composable
fun RolesCheckBox(
) {
    var selectedOption by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Role.entries.forEach { role ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .selectable(
                        selected = role.name == selectedOption,
                        onClick = { selectedOption = role.name }
                    )
            ) {
                Checkbox(
                    checked = role.name == selectedOption,
                    onCheckedChange = {coroutineScope.launch {
                        selectedOption = role.name
                        user.role = selectedOption
                    }})
                Text(text = role.name)
            }
        }
    }
}

