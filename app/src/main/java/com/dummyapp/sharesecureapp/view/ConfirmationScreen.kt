package com.dummyapp.sharesecureapp.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.data.UserData
import kotlinx.coroutines.launch

// Confirmation Screen
@Composable
fun ConfirmationScreen(navController: NavHostController, userData: MutableState<UserData>) {
    val scaffoldState = rememberScaffoldState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Confirmation", color = Color.White) },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text("Account Created Successfully!", style = MaterialTheme.typography.h5)
//            Spacer(modifier = Modifier.height(16.dp))
//            Text("Name: ${userData.value.firstName} ${userData.value.lastName}", style = MaterialTheme.typography.h5)
//            Text("Email: ${userData.value.email}", style = MaterialTheme.typography.h5)
//            Text("ShareCode: ${userData.value.shareCode}", style = MaterialTheme.typography.h5)
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = { /* Copy to Clipboard */ }) {
//                Text("Copy ShareCode", color = Color.White, fontWeight = FontWeight.Medium)
//            }
//            Button(onClick = { navController.navigate(Screen.BankIntegrationScreen.route) }) {
//                Text("Bank Integration", color = Color.White, fontWeight = FontWeight.Medium)
//            }
            SuccessAnimationScreen(navController, userData, clipboardManager)
        }
    }
}

@Composable
fun SuccessAnimationScreen(
    navController: NavHostController,
    userData: MutableState<UserData>,
    clipboardManager: ClipboardManager
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Animatable for stroke size
    val animatedStrokeWidth = remember { Animatable(0f) }

    // Trigger the animation once the state changes (account created successfully)
    LaunchedEffect(Unit) {
        animationPlayed = true
        scope.launch {
            animatedStrokeWidth.animateTo(
                targetValue = 10f,
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Account Created!", style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(16.dp))

            Canvas(
                modifier = Modifier
                    .size(120.dp)
            ) {
                val center = size.center

                // Draw circle
                drawCircle(
                    color = Color.Green.copy(alpha = 0.5f),
                    radius = size.minDimension / 2,
                    style = Stroke(width = 5f)
                )

                // Draw checkmark
                if (animationPlayed) {
                    drawLine(
                        color = Color.Green,
                        start = center.copy(x = center.x - 20.dp.toPx(), y = center.y),
                        end = center.copy(x = center.x, y = center.y + 20.dp.toPx()),
                        strokeWidth = animatedStrokeWidth.value
                    )

                    drawLine(
                        color = Color.Green,
                        start = center.copy(x = center.x, y = center.y + 20.dp.toPx()),
                        end = center.copy(x = center.x + 30.dp.toPx(), y = center.y - 10.dp.toPx()),
                        strokeWidth = animatedStrokeWidth.value
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Name: ${userData.value.firstName} ${userData.value.lastName}", style = MaterialTheme.typography.h5)
            Text("Email: ${userData.value.email}", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            Text("ShareCode: ${userData.value.shareCode}", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                clipboardManager.setText(AnnotatedString(userData.value.shareCode))
            }) {
                Text("Copy ShareCode", color = Color.White, fontWeight = FontWeight.Medium)
            }
            Button(onClick = { navController.navigate(Screen.BankIntegrationScreen.route) }) {
                Text("Bank Integration", color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

