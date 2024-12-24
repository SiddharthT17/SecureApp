package com.dummyapp.sharesecureapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dummyapp.sharesecureapp.data.BusinessData
import com.dummyapp.sharesecureapp.data.UserData
import com.dummyapp.sharesecureapp.view.BankIntegrationScreen
import com.dummyapp.sharesecureapp.view.BusinessAccountScreen
import com.dummyapp.sharesecureapp.view.ConfirmationScreen
import com.dummyapp.sharesecureapp.view.CreateAccountScreen
import com.dummyapp.sharesecureapp.view.HomeScreen
import com.dummyapp.sharesecureapp.view.IDVerificationScreen
import com.dummyapp.sharesecureapp.view.LoginScreen
import com.dummyapp.sharesecureapp.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 3. App Drawer Navigation Composable with Login Screen as Start Destination
@Preview
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var viewModel = LoginViewModel();
    val isSuccessObserver by viewModel.isSuccess.collectAsState(true)

    var currentScreen by remember { mutableStateOf("login") }
    val userData = remember { mutableStateOf(UserData()) }
    val businessData = remember { mutableStateOf(BusinessData()) }
    val users = remember { mutableStateListOf<UserData>() }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Log.d("sid", "isSuccessObserver:"+isSuccessObserver)
            AnimatedVisibility(visible = isSuccessObserver) {
                TopAppBar(
                    title = { Text(text = "", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    navController = navController,
                    users = users
                )
            }
            composable(Screen.CreateAccountScreen.route,
                arguments = listOf(
                    navArgument("isPersonal") { type = NavType.BoolType },
                )
                ) { backStackEntry ->
                // Retrieve parameters
                val isPersonal = backStackEntry.arguments?.getBoolean("isPersonal") ?: true
                CreateAccountScreen(navController, users, userData, isPersonal)
            }
            composable(Screen.IDVerificationScreen.route) { IDVerificationScreen(navController, userData)}
            composable(Screen.BankIntegrationScreen.route) { BankIntegrationScreen(navController, userData)}
            composable(Screen.ConfirmationScreen.route) { ConfirmationScreen(navController, userData) }
            composable(Screen.BusinessAccountScreen.route) { BusinessAccountScreen(navController, businessData)}
            composable(Screen.HomeScreen.route) { HomeScreen(navController)}
        }
    }
}

@Composable
fun DelayedNavigationScreen(navController: NavHostController, route: String, context: Context?= null, msg: String? = null) {
    // Trigger delay on entering the screen
    LaunchedEffect(Unit) {
        delay(2000) // Delay for 3 seconds
        navController.navigate(route)
        if(msg?.isNotEmpty() == true) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                .show()
        }
    }

    // Display a loading or transition UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Please wait...",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CircularProgressIndicator() // Show a loading indicator
    }
}
