package com.frommetoyou.superformulachallenge

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.frommetoyou.core_ui.components.SuperFab
import com.frommetoyou.superformulachallenge.ui.navigation.CentralNavigation
import com.frommetoyou.superformulachallenge.ui.navigation.QRGeneratorRoute
import com.frommetoyou.superformulachallenge.ui.navigation.QRScannerRoute
import com.frommetoyou.superformulachallenge.ui.navigation.Screens
import com.frommetoyou.superformulachallenge.ui.theme.SuperformulaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        setContent {
            SuperformulaChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CentralNavigation(navController)
                        SuperFab(
                            navigateToQrGenerator = {
                                navController.navigate(
                                    QRGeneratorRoute.route
                                )
                            },
                            navigateToQrScanner = {
                                navController.navigate(
                                    QRScannerRoute.route
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
