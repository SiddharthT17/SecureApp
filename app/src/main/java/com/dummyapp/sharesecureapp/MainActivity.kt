package com.dummyapp.sharesecureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dummyapp.sharesecureapp.data.UserData
import kotlin.random.Random

// 1. Define Navigation Destinations
sealed class Screen(val route: String, val title: String) {
    object Login : Screen("login", "Login")
    object CreateAccountScreen : Screen("createAccount/{isPersonal}", "CreateAccountScreen")
    object BankIntegrationScreen : Screen("bankIntegration", "BankIntegrationScreen")
    object IDVerificationScreen : Screen("idVerification", "IDVerificationScreen")
    object ConfirmationScreen : Screen("confirmation", "ConfirmationScreen")
    object BusinessAccountScreen : Screen("businessAccount", "BusinessAccountScreen")
    object HomeScreen : Screen("homeScreen", "HomeScreen")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

//@Preview
//@Composable
//fun AccountManagementApp() {
//    var currentScreen by remember { mutableStateOf("login") }
//    val userData = remember { mutableStateOf(UserData()) }
//    val businessData = remember { mutableStateOf(BusinessData()) }
//    val users = remember { mutableStateListOf<UserData>() }
//
//    when (currentScreen) {
//        "login" -> LoginScreen(
//            users = users,
//            onLoginSuccess = { currentScreen = "confirmation" },
//            onCreateAccount = { currentScreen = "createAccount" },
//            navController = navController
//        )
//
//        "createAccount" -> CreateAccountScreen(navController, users, userData) { currentScreen = "idVerification" }
//        "idVerification" -> IDVerificationScreen(navController, userData) { currentScreen = "bankIntegration" }
//        "bankIntegration" -> BankIntegrationScreen(navController, userData) { currentScreen = "confirmation" }
//        "confirmation" -> ConfirmationScreen(navController, userData)
//        "businessAccount" -> BusinessAccountScreen(navController, businessData) { currentScreen = "confirmation" }
//    }
//}

@Composable
fun ImageFromDrawable() {
    // Replace 'your_image' with your drawable resource name
    val image = painterResource(id = R.drawable.fleet_car_icon)

    Image(
        painter = image,
        contentDescription = "Image from Drawable",
        modifier = Modifier.size(100.dp) // Adjust the modifier as needed
    )
}

// ShareCode Generator
fun generateShareCode(userData: UserData): String {
    val initials = userData.firstName.take(1) + userData.lastName.take(1)
    val stateAbbreviation = userData.state.take(2).uppercase()
    val randomDigits = Random.nextInt(1000, 9999)
    return "$initials$stateAbbreviation-$randomDigits"
}
