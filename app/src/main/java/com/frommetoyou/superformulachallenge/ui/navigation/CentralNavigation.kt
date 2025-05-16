package com.frommetoyou.superformulachallenge.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.frommetoyou.core_ui.utils.UiText
import kotlinx.serialization.Serializable

@Composable
fun CentralNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.QRScanner.route
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
    val route: T //graphs defined in feature module for each tab

) {

    @Serializable
    data object QRGenerator : Screens<QRGeneratorRoute>(
        name = UiText.DynamicString("QR Generator"),
        route = QRGeneratorRoute
    )

    @Serializable
    data object QRScanner : Screens<QRScannerRoute>(
        name = UiText.DynamicString("QR Scanner"),
        route = QRScannerRoute
    )

}