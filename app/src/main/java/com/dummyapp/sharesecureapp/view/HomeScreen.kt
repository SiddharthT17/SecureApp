package com.dummyapp.sharesecureapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // Create DrawerState to control drawer opening/closing
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Create a DrawerItem list for the menu
    val drawerItems = listOf(" ", "Fleet Details", "User Profile", "Settings", "Bank Integration")

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Drawer content (Menu Items)
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxHeight()
                    .width(240.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    drawerItems.forEach { item ->
                        DrawerItem(item, scope, drawerState)
                    }
                }
                // logout
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Log out",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                scope.launch {
                                    navController.navigate(Screen.Login.route)
                                }
                            }
                    )
                }

            }
        },
        content = {
            // Main Content (Home Screen)
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Home Screen") },
                        navigationIcon = {
                            IconButton(onClick = {
                                // Open Drawer on IconButton Click
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                            }
                        }
                    )
                },
                content = { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Welcome to the Home Screen!")
                    }
                }
            )
        }
    )
}

@Composable
fun DrawerItem(item: String, scope: CoroutineScope, drawerState: DrawerState) {
    Text(
        text = item,
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                scope.launch {
                    drawerState.close()
                }
            }
    )
}



