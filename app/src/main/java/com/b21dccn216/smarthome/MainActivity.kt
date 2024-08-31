package com.b21dccn216.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.b21dccn216.smarthome.data.ApiService
import com.b21dccn216.smarthome.data.SmartHomeRepository
import com.b21dccn216.smarthome.data.retrofit
import com.b21dccn216.smarthome.ui.theme.SmartHomeTheme



@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartHomeTheme {
                val apiService = retrofit.create(ApiService::class.java)
                val repository = SmartHomeRepository(apiService)
                val viewmodel = SmartHomeViewmodel(repository)
                SmarthomeNavigation(
                    viewmodel = viewmodel
                )
            }
        }
    }
}
