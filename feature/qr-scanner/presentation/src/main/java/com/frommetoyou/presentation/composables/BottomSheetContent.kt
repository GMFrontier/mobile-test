package com.frommetoyou.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.presentation.QRScannerUiState
import com.frommetoyou.presentation.QrScannerViewModel
import com.frommetoyou.superformulachallenge.common.R

@Composable
fun BottomSheetContent(
    viewModel: QrScannerViewModel,
    scannedCode: String,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onClose: () -> Unit
) {
    viewModel.isValidQR(scannedCode)
    val uiState = viewModel.qrScannerUiState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            UiText.StringResource(R.string.scanned_code).asString(),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        when(uiState) {
            is QRScannerUiState.ValidQR -> {
                Text(
                    text = uiState.qrModel.seed,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(24.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            onCopy.invoke()
                            onClose.invoke()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = "Copy",
                                Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(UiText.StringResource(R.string.copy).asString())
                        }
                        Button(onClick = onShare) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = UiText.StringResource(R.string.share)
                                    .asString(),
                                Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(UiText.StringResource(R.string.copy).asString())
                        }
                    }
            }
            QRScannerUiState.Expired -> {
                Text(
                    text = UiText.StringResource(
                        R.string.expired
                    ).asString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onClose) {
            Text(UiText.StringResource(R.string.close).asString())
        }
    }
}