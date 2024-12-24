package com.dummyapp.sharesecureapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.data.BusinessData

// Business Account Screen
@Composable
fun BusinessAccountScreen(
    navController: NavHostController,
    businessData: MutableState<BusinessData>
) {
    var companyName by remember { mutableStateOf("") }
    var fleetDetails by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "ID Verification Screen", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()  // This will pop the back stack and go back to the previous screen
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ArrowBack")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(modifier = Modifier.padding(16.dp).padding(innerPadding)) {
            Text("Business Account Setup", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text("Company Name") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = fleetDetails,
                onValueChange = { fleetDetails = it },
                label = { Text("Fleet Details") })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                businessData.value = BusinessData(
                    companyName = companyName,
                    fleetDetails = fleetDetails
                )
                navController.navigate(Screen.ConfirmationScreen.route)
            }) {
                Text("Save Business Info", color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}
