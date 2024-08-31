package com.b21dccn216.smarthome

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.b21dccn216.smarthome.model.Destinations.ACTION_DATA_TABLE
import com.b21dccn216.smarthome.model.Destinations.DASHBOARD
import com.b21dccn216.smarthome.model.Destinations.PROFILE
import com.b21dccn216.smarthome.model.Destinations.SENSOR_DATA_TABLE
import com.b21dccn216.smarthome.model.AppState.LOADING
import com.b21dccn216.smarthome.ui.components.BottomNavigationApp
import com.b21dccn216.smarthome.ui.screen.DashboardScreen
import com.b21dccn216.smarthome.ui.screen.LoadingScreen
import com.b21dccn216.smarthome.ui.screen.ProfileScreen
import com.b21dccn216.smarthome.ui.screen.TableScreen

data class BottomNavigationItem(
    val title: Pair<Int, String>,
    val selectedIon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

val items = listOf(
    BottomNavigationItem(
        DASHBOARD,
        selectedIon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),
    BottomNavigationItem(
        SENSOR_DATA_TABLE,
        selectedIon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List,
        hasNews = false,
    ),
    BottomNavigationItem(
        ACTION_DATA_TABLE,
        selectedIon = Icons.Filled.PlayArrow,
        unselectedIcon = Icons.Outlined.PlayArrow,
        hasNews = false,
    ),
    BottomNavigationItem(
        PROFILE,
        selectedIon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmarthomeNavigation(
    viewmodel: SmartHomeViewmodel,
    navController: NavHostController = rememberNavController()
) {
    val currentScreen by viewmodel.currentScreen.collectAsState()
    val appState by viewmodel.appState.collectAsState()
    val uiStateTable by viewmodel.uiStateTable.collectAsState()

    Scaffold(
        topBar = {
            when (currentScreen) {
                DASHBOARD -> {
                    TopAppBar(title = { Text(text = "Dashboard") })
                }
                SENSOR_DATA_TABLE -> {
                    TopAppBar(title = { Text(text = "Sensor data") })
                }
                ACTION_DATA_TABLE -> {
                    TopAppBar(title = { Text(text = "Control data") })
                }
                else -> null
            }
        },
        bottomBar = {
            BottomNavigationApp(
                onClickNavItem = {
                    navController.navigate(route = it.second)
                    viewmodel.navigateTo(screen = it)
                },
                currentIndex = currentScreen.first,
            )
        }
    ) { innerPadding ->
        if(appState == LOADING){
            LoadingScreen(Modifier.padding(innerPadding))
        }else{
            NavHost(
                navController = navController,
                startDestination = DASHBOARD.second
            ) {
                composable(DASHBOARD.second) {
                    DashboardScreen(
                        viewmodel = viewmodel,
                        innerPadding = innerPadding)
                }
                composable(SENSOR_DATA_TABLE.second) {
                    TableScreen(
                        viewmodel = viewmodel,
                        titleColumn = listOf("Temp", "Humid", "Light"),
                        innerPadding = innerPadding,
                        tableData = uiStateTable.tableSensorData
                    )
                }
                composable(ACTION_DATA_TABLE.second) {
                    TableScreen(
                        viewmodel = viewmodel,
                        titleColumn = listOf("Led", "Fan", "Relay"),
                        innerPadding = innerPadding,
                        tableData = uiStateTable.tableActionData
                    )
                }
                composable(PROFILE.second) {
                    ProfileScreen(modifier = Modifier, innerPadding = innerPadding)
                }
            }
        }

    }
}