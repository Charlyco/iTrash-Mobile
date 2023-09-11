package com.charlyco.itrash.ui_utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.charlyco.itrash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarWithNavIcon(
    navController: NavController,
    route: String,
    color: Color,
) {
    var onShowMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = color,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = {navController.navigate(route){
                popUpTo(route){inclusive = true}
                launchSingleTop = true
            } }){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "App Icon")
            }
        },
        actions = {
            IconButton(onClick = { onShowMenu = !onShowMenu }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options menu ")
            }
            DropdownMenu(expanded = onShowMenu, onDismissRequest = { onShowMenu = false }) {
                DropdownMenuItem(
                    text = { Text(
                        text = stringResource(id = R.string.feedback),
                        color = MaterialTheme.colorScheme.onBackground
                        ) },
                    onClick = {  })
                DropdownMenuItem(
                    text = { Text(
                        text = stringResource(id = R.string.settings),
                        color = MaterialTheme.colorScheme.onBackground
                        ) },
                    onClick = {  })
            }
        }
    )
}