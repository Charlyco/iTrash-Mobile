package com.charlyco.itrash.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.charlyco.itrash.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {
    WelcomeScreenUi()
    LaunchedEffect(key1 = true) {
        delay(4000)
        navController.navigate("auth_screen") {
            popUpTo("welcome_screen") {inclusive = true}
        }
    }
}

@Composable
fun WelcomeScreenUi() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout {
            val (appName, detail, logo, text) = createRefs()

            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                fontSize = TextUnit(36.0f, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.constrainAs(appName) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(detail.top, margin = 8.dp)
                    }
                )
            Text(
                text = stringResource(id = R.string.motto),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = TextUnit(16.0f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(detail) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(logo.top, margin = 32.dp)
                }
            )
            Image(
                painter = painterResource(id = R.drawable.binpng),
                contentDescription = "",
                modifier = Modifier
                    .width(275.dp)
                    .height(314.dp)
                    .constrainAs(logo) {
                        centerVerticallyTo(parent)
                        centerHorizontallyTo(parent)
                    }
            )
            Text(
                text = "Powered by TETFUND",
                modifier = Modifier.constrainAs(text) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun PreviewUi() {
    WelcomeScreenUi()
}
