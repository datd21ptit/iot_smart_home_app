package com.b21dccn216.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.b21dccn216.smarthome.ui.screen.DashboardScreen
import com.b21dccn216.smarthome.ui.screen.SensorData
import com.b21dccn216.smarthome.ui.screen.TableScreen
import com.b21dccn216.smarthome.ui.theme.SmartHomeTheme



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            var title by remember { mutableStateOf("Home") }
            SmartHomeTheme {
                var selectedIndex by remember{ mutableStateOf(0)}
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = title) })
                    },
                    bottomBar = {
                        NavigationBar() {
                            items.forEachIndexed{ index, item ->
                                NavigationBarItem(
                                    selected = index == selectedIndex,
                                    onClick = {
                                        title = item.title
                                        navController.navigate(route = item.title)
                                        selectedIndex = index
                                              },
                                    icon = {
                                        Icon(imageVector = if(index == selectedIndex) item.selectedIon else item.unselectedIcon,
                                            contentDescription = null)
                                    })
                            }
                        }
                    }
                ) { innerPadding ->
//                    DashboardScreen(innerPadding = innerPadding)


                    SmarthomeNavigation(
                        navController = navController,
                        innerPaddingValues = innerPadding)
                }

            }
        }
    }
}
