package com.b21dccn216.smarthome

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.b21dccn216.smarthome.model.Destinations.ACTION_DATA_TABLE
import com.b21dccn216.smarthome.model.Destinations.DASHBOARD
import com.b21dccn216.smarthome.model.Destinations.PROFILE
import com.b21dccn216.smarthome.model.Destinations.SENSOR_DATA_TABLE
import com.b21dccn216.smarthome.model.AppState.LOADING
import com.b21dccn216.smarthome.model.TableUiState
import com.b21dccn216.smarthome.ui.screen.DashboardScreen
import com.b21dccn216.smarthome.ui.screen.LoadingScreen
import com.b21dccn216.smarthome.ui.screen.ProfileScreen
import com.b21dccn216.smarthome.ui.screen.TableScreen

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
        selectedIon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List,
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
    viewmodel: SmartHomeViewmodel,
    navController: NavHostController = rememberNavController()
){
        NavHost(
            navController = navController,
            startDestination = DASHBOARD
        ){
            composable(DASHBOARD) {
                DashboardScreen(
                    viewmodel = viewmodel,
                    onClickNavItem = {
                        navController.navigate(route = it)
                        viewmodel.navigateTo(screen = it)
                    })
            }

            composable(SENSOR_DATA_TABLE){
                val uiState by viewmodel.uiStateTable.collectAsState()
                val appState by viewmodel.appState.collectAsState()
                if(appState == LOADING){
                    LoadingScreen()
                }else{
                    TableScreen(
                        modifier = Modifier,
                        viewmodel = viewmodel,
                        title = "Sensor data",
                        titleColmn = listOf("temp", "humid", "light"),
                        selectedIndex = 1,
                        onClickNavItem = {tittle ->
                            navController.navigate(route = tittle)
                            viewmodel.navigateTo(screen = tittle)
                        },
                        onDateSelected = {
                            viewmodel.moveToPage(uiState.copy(time = it))
                        }
                    )
                }

            }

            composable(ACTION_DATA_TABLE){
//            TableScreen(
//                modifier = Modifier.padding(innerPaddingValues),
//                tableData = table,
//                selectedIndex = 2,
//                title = "Action",
//                onClick = {it, tittle ->
////                    selectedIndex = it
//                    navController.navigate(route = tittle)
//                }
//            )
            }

            composable(PROFILE){
                ProfileScreen(
                    modifier = Modifier,
                    onClickNavItem = {
                        navController.navigate(it)
                        viewmodel.navigateTo(it)
                    })
            }
        }
//    }else{
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "LOADING...",
//                style = MaterialTheme.typography.titleMedium)
//        }
//    }

}