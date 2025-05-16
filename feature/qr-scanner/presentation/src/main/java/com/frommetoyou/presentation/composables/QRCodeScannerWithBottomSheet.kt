package com.frommetoyou.presentation.composables

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.frommetoyou.presentation.QrScannerViewModel
import com.frommetoyou.presentation.util.QRCodeAnalyzer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScannerWithBottomSheet(
    viewModel: QrScannerViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var scannedCode by remember { mutableStateOf<String?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    if (showSheet && scannedCode != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                scannedCode = null
            },
            sheetState = sheetState
        ) {
            BottomSheetContent(
                viewModel = viewModel,
                scannedCode = scannedCode.orEmpty(),
                onCopy = {
                    clipboardManager.setPrimaryClip(
                        ClipData.newPlainText("QR Code", scannedCode)
                    )
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                },
                onShare = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, scannedCode)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share QR Code"))
                },
                onClose = {
                    showSheet = false
                    scannedCode = null
                }
            )
        }
    }
    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
            val analyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also {
                    it.setAnalyzer(
                        ContextCompat.getMainExecutor(ctx),
                        QRCodeAnalyzer { qrCode ->
                            if (!showSheet) {
                                scannedCode = qrCode
                                showSheet = true
                            }
                        }
                    )
                }
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                analyzer
            )
        }, ContextCompat.getMainExecutor(ctx))
        previewView
    }, modifier = Modifier.fillMaxSize())
}