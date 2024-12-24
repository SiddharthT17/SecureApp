package com.dummyapp.sharesecureapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.SecureStorage
import com.dummyapp.sharesecureapp.data.UserData
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.material.TextButton
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import com.dummyapp.sharesecureapp.DelayedNavigationScreen

// Bank Integration Screen
@Composable
fun BankIntegrationScreen(
    navController: NavHostController,
    userData: MutableState<UserData>
) {
    val context = LocalContext.current
    var accountNumber by remember { mutableStateOf("") }
    var routingNumber by remember { mutableStateOf("") }
    var sharedCode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    var navigateScreen by remember { mutableStateOf("") }

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Bank Integration", color = Color.White) },
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bank Integration", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    label = { Text("Account Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = routingNumber,
                    onValueChange = { routingNumber = it },
                    label = { Text("Routing Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = sharedCode,
                    onValueChange = { sharedCode = it },
                    label = { Text("Enter shared code") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    trailingIcon = {
                        IconButton(onClick = {
                            sharedCode = clipboardManager.getText()?.text ?: ""
                            if (sharedCode.isBlank()) {
                                Toast.makeText(context, "Clipboard is empty", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Icon(Icons.Default.ContentPaste, contentDescription = "Paste Text")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (accountNumber.isNotBlank() && routingNumber.isNotBlank()) {
                            userData.value = userData.value.copy(
                                bankAccount = accountNumber,
                                routingNumber = routingNumber
                            )
                            SecureStorage.saveBankDetails(context, accountNumber, routingNumber, sharedCode)
                            navigateScreen = Screen.HomeScreen.route
                            //navController.navigate(Screen.HomeScreen.route)
                        } else {
                            //Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                            errorMessage = "All fields are mandatory!"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Save Bank Details",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Skip",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

//            Button(onClick = {
//                if (accountNumber.isEmpty() || routingNumber.isEmpty()) {
//                    errorMessage = "All fields are mandatory!"
//                } else {
//                    userData.value = userData.value.copy(
//                        bankAccount = accountNumber,
//                        routingNumber = routingNumber
//                    )
//                    navController.navigate(Screen.ConfirmationScreen.route)
//                }
//            }) {
//                Text(
//                    "Save Bank Info",
//                    color = Color.White,
//                    fontWeight = FontWeight.Medium
//                )
//            }

                Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = {
//                    val (savedAccount, savedRouting) = SecureStorage.getBankDetails(context)
//                    if (savedAccount != null && savedRouting != null) {
//                        Toast.makeText(
//                            context,
//                            "Account: $savedAccount, Routing: $savedRouting",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    } else {
//                        Toast.makeText(context, "No bank details found", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Retrieve Bank Details")
//            }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(errorMessage, color = MaterialTheme.colors.error)
                }
            }
            if (navigateScreen.isNotEmpty()) {
                DelayedNavigationScreen(navController, navigateScreen, context, "Bank details saved securely")
            }
        }
    }
}
