package com.frommetoyou.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.frommetoyou.presentation.composables.QRCodeScannerWithBottomSheet
import com.frommetoyou.presentation.composables.QRScannerGuideWithPath

@Composable
fun QRScannerScreen(modifier: Modifier = Modifier) {
    Box {
        QRCodeScannerWithBottomSheet()
        QRScannerGuideWithPath()
    }
}
