package com.sim.whether

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.sim.whether.ui.theme.WhetherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhetherTheme {
                val backStack = rememberNavBackStack(MainRoute)
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider<NavKey> {
                        entry<MainRoute> {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                WeatherMainScreen(
                                    onNavigateToSubScreen = {
                                        backStack.add(SubRoute)
                                    },
                                    modifier = Modifier.padding(innerPadding),
                                    viewModel = weatherViewModel,
                                )
                            }
                        }
                        entry<SubRoute> {
                            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                WeatherSubScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    viewModel = weatherViewModel,
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Serializable
object MainRoute : NavKey

@Serializable
object SubRoute : NavKey