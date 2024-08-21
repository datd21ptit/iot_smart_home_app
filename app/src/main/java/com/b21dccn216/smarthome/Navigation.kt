package com.b21dccn216.smarthome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.b21dccn216.smarthome.Destinations.ACTION_DATA_TABLE
import com.b21dccn216.smarthome.Destinations.DASHBOARD
import com.b21dccn216.smarthome.Destinations.PROFILE
import com.b21dccn216.smarthome.Destinations.SENSOR_DATA_TABLE
import com.b21dccn216.smarthome.ui.screen.DashboardScreen
import com.b21dccn216.smarthome.ui.screen.ProfileScreen
import com.b21dccn216.smarthome.ui.screen.SensorData
import com.b21dccn216.smarthome.ui.screen.TableScreen

object Destinations {
    const val DASHBOARD = "Home"
    const val SENSOR_DATA_TABLE = "Sensor"
    const val ACTION_DATA_TABLE = "Action"
    const val PROFILE = "Profile"
}

data class BottomNavigationItem(
    val title: String,
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
        selectedIon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
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

@Composable
fun SmarthomeNavigation(
    innerPaddingValues: PaddingValues,
    navController: NavHostController = rememberNavController()
){

    val table = mutableListOf<SensorData>()
    (1..30).forEach{it-> table.add(SensorData(id = it, temp = 20, humid = 80, light = 80, time = "20-8"))}

    NavHost(
        navController = navController,
        startDestination = DASHBOARD
    ){
        composable(DASHBOARD) {
            DashboardScreen(innerPadding = innerPaddingValues)
        }

        composable(SENSOR_DATA_TABLE){
            TableScreen(
                modifier = Modifier.padding(innerPaddingValues),
                tableData = table
            )
        }

        composable(ACTION_DATA_TABLE){
            TableScreen(
                modifier = Modifier.padding(innerPaddingValues),
                tableData = table
            )
        }

        composable(PROFILE){
            ProfileScreen(modifier = Modifier)
        }
    }
}