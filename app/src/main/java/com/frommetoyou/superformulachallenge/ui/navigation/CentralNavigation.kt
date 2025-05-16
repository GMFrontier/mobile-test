package com.frommetoyou.superformulachallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.frommetoyou.core_ui.utils.UiText
import kotlinx.serialization.Serializable

@Composable
fun CentralNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.QRGenerator.route
    ) {
        mainSection(navController)
    }
}

@Composable
fun AppNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = getScreens()

}

@Composable
fun getScreens(): List<Screens<out Any>> {
    return listOf(
        Screens.QRGenerator,
        Screens.QRScanner,
    )
}

@Serializable
sealed class Screens<T>(
    val name: UiText, // the name of the tab
    val route: String

) {

    @Serializable
    data object QRGenerator : Screens<QRGeneratorRoute>(
        name = UiText.DynamicString("QR Generator"),
        route = QRGeneratorRoute.route
    )

    @Serializable
    data object QRScanner : Screens<QRScannerRoute>(
        name = UiText.DynamicString("QR Scanner"),
        route = QRScannerRoute.route
    )

}