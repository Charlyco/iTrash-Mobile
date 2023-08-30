package com.charlyco.itrash.ui_utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.charlyco.itrash.data.Role
import com.charlyco.itrash.viewModels.AuthViewModel

@Composable
fun RolesRadioButtons(authViewModel: AuthViewModel?) {
    var selectedOption by remember { mutableStateOf(Role.CUSTOMER) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Role.entries.forEach { role ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .selectable(
                        selected = role == selectedOption,
                        onClick = {selectedOption = role}
                    )
            ) {
                RadioButton(
                    selected = role == selectedOption,
                    onClick = {
                        selectedOption = role
                        authViewModel?.user?.role = selectedOption
                    })

                Text(text = role.name)
            }
        }
    }
}