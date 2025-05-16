package com.frommetoyou.superformulachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.frommetoyou.presentation.QRGeneratorScreen
import com.frommetoyou.presentation.QRScannerScreen
import kotlinx.serialization.Serializable

@Serializable data object QRGeneratorRoute
@Serializable data object QRScannerRoute

fun NavGraphBuilder.mainSection(navController: NavController) {
        composable<QRGeneratorRoute>() { QRGeneratorScreen() }
        composable<QRScannerRoute>() { QRScannerScreen() }
}