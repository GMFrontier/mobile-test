package com.frommetoyou.core_ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.superformulachallenge.common.R

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDenied: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = if (isPermanentlyDenied)
                    onGoToAppSettingsClick
                else
                    onOkClick
            ) {
                Text(
                    text = if (isPermanentlyDenied)
                        UiText.StringResource(R.string.grant_permission)
                            .asString(

                            ) else UiText.StringResource(
                        R.string.ok
                    ).asString(),
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        title = {
            Text(
                text = UiText.StringResource(R.string.permissions_required)
                    .asString(),
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDenied)
                    .asString(),
                fontWeight = FontWeight.Bold,
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDenied: Boolean): UiText.StringResource
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDenied: Boolean): UiText.StringResource {
        return if (isPermanentlyDenied) {
            UiText.StringResource(R.string.notification_permanently_declined)
        } else
            UiText.StringResource(R.string.ask_camera_permission)
    }
}