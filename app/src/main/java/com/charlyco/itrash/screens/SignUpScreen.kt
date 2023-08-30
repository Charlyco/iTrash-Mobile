package com.charlyco.itrash.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Role
import com.charlyco.itrash.ui_utils.PlainTopBar
import com.charlyco.itrash.ui_utils.RolesRadioButtons
import com.charlyco.itrash.viewModels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel?) {
    Scaffold(
        topBar = {PlainTopBar(navController = navController, route = "auth_screen")}
    ) {
        ScreenContents(
            modifier = Modifier.padding(it),
            navController = navController,
            authViewModel = authViewModel)
    }
}

@Composable
fun ScreenContents(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(top = 64.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(modifier = Modifier)
            InputBoxesCard(Modifier, navController, authViewModel, screenWidth)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBoxesCard(
    modifier: Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?,
    screenWidth: Int
) {
    var fullName by remember { mutableStateOf(TextFieldValue("")) }
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
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
                    authViewModel?.user?.fullName = fullName.text
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.tertiary
                ),
                label = {Text(text = stringResource(id = R.string.full_name))},
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {Icon(imageVector = Icons.Default.Person, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 4.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            TextField(
                value = userName,
                onValueChange = {
                    userName = it
                    authViewModel?.user?.userName = userName.text
                },
                label = {Text(text = stringResource(id = R.string.user_name))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {Icon(imageVector = Icons.Default.AccountBox, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    authViewModel?.user?.password = password.text
                },
                label = {Text(text = stringResource(id = R.string.password))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.tertiary
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
            Spacer(modifier = modifier.height(16.dp))
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    authViewModel?.user?.email = email.text
                },
                label = {Text(text = stringResource(id = R.string.email))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            TextField(
                value = address,
                onValueChange = {
                    address = it
                    authViewModel?.user?.address = address.text
                },
                label = {Text(text = stringResource(id = R.string.address))},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    textColor = MaterialTheme.colorScheme.tertiary
                ),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {Icon(imageVector = Icons.Default.Place, contentDescription = "")},
                modifier = modifier
                    .paddingFromBaseline(top = 10.dp)
                    .width(screenWidth.dp)
                    .height(48.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            RolesRadioButtons(authViewModel = authViewModel)
            Spacer(modifier = modifier.height(16.dp))
            Button(
                modifier = modifier
                    .paddingFromBaseline(top = 16.dp)
                    .width(screenWidth.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                onClick = {
                    authViewModel?.saveUserAndReturnToken(authViewModel.user)
                    navController.navigate("home_screen") {
                        launchSingleTop = true
                        popUpTo("sign_up") { inclusive = true }
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
            modifier = modifier.size(96.dp),
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

@Preview
@Composable
fun PreviewSignUp(){
    SignUpScreen(navController = rememberNavController(), authViewModel = AuthViewModel())
}