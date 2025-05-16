package com.frommetoyou.superformulachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.frommetoyou.presentation.QRGeneratorScreen
import com.frommetoyou.presentation.QRScannerScreen
import kotlinx.serialization.Serializable

@Serializable data object QRGeneratorRoute {
        const val route = "qr_generator"
}
@Serializable data object QRScannerRoute {
        const val route = "qr_scanner"
}

fun NavGraphBuilder.mainSection(navController: NavController) {
        composable(QRGeneratorRoute.route) { QRGeneratorScreen() }
        composable(QRScannerRoute.route) { QRScannerScreen() }
}