package com.frommetoyou.presentation

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frommetoyou.core_ui.utils.UiText
import kotlinx.coroutines.delay
import qrcode.QRCode
import qrcode.color.Colors
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import com.frommetoyou.superformulachallenge.common.R

@Composable
fun QRGeneratorScreen(modifier: Modifier = Modifier) {

    var qrCode by remember {
        mutableStateOf("Hello world!")
    }

    val expiresAt = "2025-11-12T13:10:42.24Z"

    val dateFormat = remember {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    val expirationTime = remember(expiresAt) {
        dateFormat.parse(expiresAt)?.time ?: 0L
    }

    var timeLeftMillis by remember { mutableStateOf(expirationTime - System.currentTimeMillis()) }

    LaunchedEffect(expirationTime) {
        while (timeLeftMillis > 0) {
            delay(1000)
            timeLeftMillis = expirationTime - System.currentTimeMillis()
        }
    }

    val seconds = (timeLeftMillis / 1000) % 60
    val minutes = (timeLeftMillis / (1000 * 60)) % 60
    val hours = (timeLeftMillis / (1000 * 60 * 60)) % 24

    val qrCodeGraphics = QRCode.ofRoundedSquares()
        .withColor(Colors.DEEP_SKY_BLUE)
        .withSize(25)
        .build(qrCode)
        .render()
    val pngByteArray = remember { qrCodeGraphics.getBytes("PNG") }
    val bitmap = remember(pngByteArray) {
        BitmapFactory.decodeByteArray(pngByteArray, 0, pngByteArray.size)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = UiText.StringResource(R.string.qr_code).asString(),
                modifier = Modifier
            )
        }
        Text(
            text = UiText.StringResource(R.string.qr_code, arrayOf(qrCode)).asString(),
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 18.sp
        )
        Text(
            text = if (timeLeftMillis > 0)
                String.format(Locale.US,"Expira en %02d:%02d:%02d", hours, minutes, seconds)
            else
                UiText.StringResource(R.string.expired).asString(),
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 14.sp
        )

        TextButton(
            onClick = {}
        ) {
            Icon(Icons.Default.Refresh, contentDescription = UiText.StringResource(R.string.refresh).asString())
            Text(text = UiText.StringResource(R.string.refresh).asString())
        }


    }
}