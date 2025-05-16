package com.frommetoyou.presentation

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.superformulachallenge.common.R
import com.google.gson.Gson
import qrcode.QRCode
import qrcode.color.Colors
import java.util.Locale

@Composable
fun QRGeneratorScreen(
    modifier: Modifier = Modifier,
    viewModel: QRGeneratorViewModel = hiltViewModel()
) {
    val uiState = viewModel.qrGeneratorUiState.collectAsState().value
    val timeLeftMillis by viewModel.timeLeftMillis.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is QRGeneratorUiState.Nothing -> {
                Text(
                    text = UiText.StringResource(
                        R.string.qr_gen_code
                    ).asString(),
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            is QRGeneratorUiState.Success -> {

                val seconds = (timeLeftMillis / 1000) % 60
                val minutes = (timeLeftMillis / (1000 * 60)) % 60
                val hours = (timeLeftMillis / (1000 * 60 * 60)) % 24

                val qrCodeGraphics = QRCode.ofSquares()
                    .withColor(if (timeLeftMillis > 0) Colors.DEEP_SKY_BLUE else Colors.INDIAN_RED)
                    .withSize(20)
                    .build(Gson().toJson(uiState.qrCode))
                    .render()

                val pngByteArray = remember(qrCodeGraphics) {
                    qrCodeGraphics.getBytes("PNG")
                }

                val bitmap = remember(pngByteArray) {
                    BitmapFactory.decodeByteArray(
                        pngByteArray,
                        0,
                        pngByteArray.size
                    )
                }

                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = UiText.StringResource(R.string.qr_code)
                            .asString(),
                    )
                }
                Text(
                    text = UiText.StringResource(
                        R.string.qr_code,
                        arrayOf(uiState.qrCode.seed)
                    ).asString(),
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )


                Text(
                    text = if (timeLeftMillis > 0)
                        String.format(
                            Locale.US,
                            UiText.StringResource(
                                R.string.expires_in,
                                arrayOf(hours, minutes, seconds)
                            ).asString()
                        )
                    else
                        UiText.StringResource(R.string.expired).asString(),
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 14.sp
                )
            }

            is QRGeneratorUiState.Error -> {
                Text(
                    text = UiText.StringResource(R.string.error_gen).asString()
                )
            }

            is QRGeneratorUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        TextButton(
            onClick = {
                viewModel.getQrCode()
            },
            enabled = if (uiState is QRGeneratorUiState.Loading) false else true
        ) {
            Icon(
                if (uiState is QRGeneratorUiState.Nothing)
                    Icons.Default.Add
                else
                    Icons.Default.Refresh,
                contentDescription = UiText.StringResource(R.string.refresh)
                    .asString()
            )
            Text(
                text = if (uiState is QRGeneratorUiState.Nothing)
                    UiText.StringResource(R.string.generate).asString()
                else
                    UiText.StringResource(R.string.refresh).asString()
            )
        }
    }
}