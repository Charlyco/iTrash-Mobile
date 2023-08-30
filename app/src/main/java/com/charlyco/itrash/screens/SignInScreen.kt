package com.charlyco.itrash.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.ui_utils.PlainTopBar
import com.charlyco.itrash.R
import com.charlyco.itrash.data.Role
import com.charlyco.itrash.ui_utils.RolesRadioButtons
import com.charlyco.itrash.viewModels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController, authViewModel: AuthViewModel?) {
    val authState: String by authViewModel!!.authState.observeAsState("")
    Scaffold(
        topBar = { PlainTopBar(navController, "auth_screen") }
    ) {contentPadding ->
       SignInScreenContent(
           modifier = Modifier.padding(contentPadding),
           authState = authState,
           authViewModel = authViewModel,
           navController = navController)
    }
}

@Composable
fun SignInScreenContent(
    modifier: Modifier,
    authState: String,
    authViewModel: AuthViewModel?,
    navController: NavController
){
    val screenWidth = LocalConfiguration.current.screenWidthDp - 64
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        color = MaterialTheme.colorScheme.surface
        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcome_back),
                modifier = Modifier.padding(top = 32.dp, bottom = 4.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TextUnit(20.0f, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold
                )

            Text(
                text = stringResource(id = R.string.enter_details),
                modifier = Modifier.padding(top = 4.dp, bottom = 64.dp),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = TextUnit(16.0f, TextUnitType.Sp)
                )
            EmailLogin(
                modifier = Modifier,
                screenWidth = screenWidth,
                authState = authState,
                authViewModel = authViewModel,
                navController = navController
                )
            Spacer(modifier = modifier.height(96.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text =  stringResource(id = R.string.no_account),
                    modifier = Modifier.padding(end = 4.dp)
                    )
                Text(text =  stringResource(id = R.string.sign_up),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable { navController.navigate("sign_up") }
                    )
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailLogin(
    modifier: Modifier,
    screenWidth: Int,
    authState: String,
    authViewModel: AuthViewModel?,
    navController: NavController
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }  //will be filled from memory
    var password by remember { mutableStateOf(TextFieldValue("")) } //if login details is saved
    var showPassword by remember { mutableStateOf(value = false) }
    var isCheckBoxChecked by remember { mutableStateOf(false) }

    ConstraintLayout {
        val (emailBox, passwordBox, checkBox, forgotPassword, role, button) = createRefs()
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text(text = stringResource(id = R.string.email))},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = modifier
                .width(screenWidth.dp)
                .height(48.dp)
                .border(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                    shape = MaterialTheme.shapes.medium
                )
                .constrainAs(emailBox) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                },
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "") },
        )
        
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Password")},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = modifier
                .width(screenWidth.dp)
                .height(48.dp)
                .border(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                    shape = MaterialTheme.shapes.medium
                )
                .constrainAs(passwordBox) {
                    centerHorizontallyTo(parent)
                    top.linkTo(emailBox.bottom, margin = 32.dp)
                },
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
        )
        Row(
            modifier = modifier.constrainAs(checkBox) {
                start.linkTo(parent.start)
               top.linkTo(passwordBox.bottom)
                },
             verticalAlignment = Alignment.CenterVertically
         ) {
            Checkbox(
                checked = isCheckBoxChecked,
                onCheckedChange = {
                    isCheckBoxChecked = true
                    }
                )
            Text(
                text = stringResource(id = R.string.remember_me),
                fontSize = TextUnit(14.0f, TextUnitType.Sp)
                )
        }
        Text(
            text = stringResource(id = R.string.forgot_password),
            modifier = modifier
                .clickable { navController.navigate("change_password") }
                .constrainAs(forgotPassword) {
                    end.linkTo(parent.end)
                    top.linkTo(passwordBox.bottom, margin = 16.dp)
                },
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = TextUnit(14.0f, TextUnitType.Sp)
        )
        Surface(color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.constrainAs(role) {
                top.linkTo(checkBox.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
        }) {
            RolesRadioButtons(authViewModel = authViewModel)
        }
        Button(
            modifier = modifier
                .width(screenWidth.dp)
                .constrainAs(button) {
                    centerHorizontallyTo(parent)
                    top.linkTo(role.bottom, margin = 32.dp)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = {
                authViewModel?.loginWithEmailAndPassword(email.text, password.text)
                if (authState == email.text) {
                    when(authViewModel?.user?.role) {
                        Role.CUSTOMER -> {navController.navigate("home_screen") {
                            popUpTo("auth_screen") {inclusive = true}
                        } }
                        Role.AGENT -> {navController.navigate("agent_home") {
                            popUpTo("auth_screen") {inclusive = true}
                        }}

                        else -> {}
                    }

                }
                if (isCheckBoxChecked) {
                    authViewModel?.saveLoginDetails(email.text, password.text)
                }
            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = TextUnit(14.0F, TextUnitType.Sp),
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    SignInScreen(navController = navController, authViewModel = authViewModel)
}