package com.charlyco.itrash.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.charlyco.itrash.R

@Composable
fun AuthScreen(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout {
            val (text1, text2, image, signIn, signUp) = createRefs()
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.constrainAs(text1) {
                    top.linkTo(parent.top, margin = 128.dp)
                    centerHorizontallyTo(parent)
                },
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TextUnit(36.0f, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = stringResource(id = R.string.click_to_continue),
                modifier = Modifier.constrainAs(text2) {
                    top.linkTo(text1.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                },
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TextUnit(18.0f, TextUnitType.Sp)
            )

            Image(painter = painterResource(id = R.drawable.nature1),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
                    .constrainAs(image) {
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                }
                )
            Button(
                onClick = { navController.navigate("sign_in") },
                modifier = Modifier.width(128.dp)
                    .constrainAs(signIn) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = TextUnit(14.0F, TextUnitType.Sp),
                    fontFamily = FontFamily.SansSerif
                )
            }

            Button(
                onClick = { navController.navigate("sign_up") },
                modifier = Modifier
                    .width(128.dp)
                    .constrainAs(signUp) {
                        centerHorizontallyTo(parent)
                        top.linkTo(signIn.bottom, margin = 16.dp)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    fontSize = TextUnit(14.0F, TextUnitType.Sp),
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAuthUi() {
    AuthScreen(navController = rememberNavController())
}