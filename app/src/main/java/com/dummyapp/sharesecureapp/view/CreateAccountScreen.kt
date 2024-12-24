package com.dummyapp.sharesecureapp.view

import android.content.Context
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.DelayedNavigationScreen
import com.dummyapp.sharesecureapp.R
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.SecureStorage
import com.dummyapp.sharesecureapp.data.TruckType
import com.dummyapp.sharesecureapp.data.UserData

// Account Creation Screen
@Composable
fun CreateAccountScreen(
    navController: NavHostController,
    users: MutableList<UserData>,
    userData: MutableState<UserData>,
    isPersonal: Boolean,
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(true) }
    val (confirmPasswordVisible, setConfirmPasswordVisible) = remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState()

    // State variables for error messages
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var businessName by remember { mutableStateOf("") }

    var navigateScreen by remember { mutableStateOf("") }

    val context = LocalContext.current

    val fleetList = remember {
        mutableStateListOf(
            TruckType(name = "Ten-footer", size = "10-foot", count = 0),
            TruckType(name = "Fifteen-footer", size = "15-foot", count = 0),
            TruckType(name = "Twenty-six-footer", size = "26-foot", count = 0)
        )
    }

    // Validation function
    fun validateFields(): Boolean {
        var isValid = true

        // First Name validation
        if (firstName.isBlank()) {
            firstNameError = "First Name cannot be empty"
            isValid = false
        } else {
            firstNameError = null
        }

        // Last Name validation
        if (lastName.isBlank()) {
            lastNameError = "Last Name cannot be empty"
            isValid = false
        } else {
            lastNameError = null
        }

        // Email validation
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email address"
            isValid = false
        } else {
            emailError = null
        }

        // Password validation
        if (password.isBlank() || password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError = null
        }

        // Confirm Password validation
        if (confirmPassword != password) {
            confirmPasswordError = "Passwords do not match"
            isValid = false
        } else {
            confirmPasswordError = null
        }

        return isValid
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AnimatedVisibility(visible = true) {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isPersonal) "Personal Account" else "Business Account",
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()  // This will pop the back stack and go back to the previous screen
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "ArrowBack"
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(2.dp))
                firstNameError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(2.dp))
                lastNameError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(2.dp))
                emailError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("State (e.g., NY)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (!passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password_lock), // Replace with your user icon resource
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                            Icon(
                                painter = painterResource(id = if (passwordVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel), // Toggle visibility icon
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))
                passwordError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (!confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.password_lock), // Replace with your user icon resource
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { setConfirmPasswordVisible(!confirmPasswordVisible) }) {
                            Icon(
                                painter = painterResource(id = if (confirmPasswordVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel), // Toggle visibility icon
                                contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))
                confirmPasswordError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
                Spacer(modifier = Modifier.height(8.dp))

                if (!isPersonal) {
                    // Fleet Details Section
                    Text("Fleet Details", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Dynamic Fleet List
                    fleetList.forEachIndexed { index, truckType ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
//                            .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${truckType.name} (${truckType.size})")

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Decrease Count Button
                                IconButton(
                                    onClick = {
                                        if (truckType.count > 0) {
                                            fleetList[index] =
                                                truckType.copy(count = truckType.count - 1)
                                        }
                                    }
                                ) {
                                    Icon(
                                        painterResource(R.drawable.baseline_remove_24),
                                        contentDescription = "Decrease Count"
                                    )
                                }

                                // Truck Count
                                Text("${truckType.count}", style = MaterialTheme.typography.body1)

                                // Increase Count Button
                                IconButton(
                                    onClick = {
                                        fleetList[index] =
                                            truckType.copy(count = truckType.count + 1)
                                    }
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase Count")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(onClick = {
                        if (validateFields()) {
                            if (password != confirmPassword) {
                                errorMessage = "Passwords do not match!"
                            } else if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                                errorMessage = "All fields are mandatory!"
                            } else {
                                val newUser = UserData(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email,
                                    password = password,
                                    state = state ?: ""
                                )
                                users.add(newUser)
                                userData.value = newUser
                                SecureStorage.saveUserDetails(context, email, password)
                                navigateScreen = Screen.Login.route
                                //navController.navigate(Screen.IDVerificationScreen.route)
                            }
                        }
                    }) {
                        Text(
                            "Sign Up",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Already have an account?", //Or Sign up with
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.Login.route)
                        }
                    ) {
                        Text("Sign In", color = Color.White, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(errorMessage, color = MaterialTheme.colors.error)
                }
            }

            if (navigateScreen.isNotEmpty()) {
                DelayedNavigationScreen(navController, navigateScreen, context, "Sign up successful")
            }
        }
    }
}

@Composable
fun ShowCustomToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100) // Adjust position
        show()
    }
}
