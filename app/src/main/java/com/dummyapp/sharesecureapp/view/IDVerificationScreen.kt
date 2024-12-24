package com.dummyapp.sharesecureapp.view

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dummyapp.sharesecureapp.DelayedNavigationScreen
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.data.UserData
import com.dummyapp.sharesecureapp.generateShareCode

@Composable
fun IDVerificationScreen(navController: NavHostController, userData: MutableState<UserData>) {
    var filePath by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    var fileName by remember {
        mutableStateOf("No file selected")
    }
    var navigateScreen by remember { mutableStateOf("") }

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current // Get the context here

    // Launcher for picking a file
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedFileUri = uri
            selectedFileType = uri.let { getFileType(context, it) }
            // Get the file type and validate it
            fileName = getFileNameFromUri(context, uri) ?: ""
            errorMessage = ""
//            if (fileType.endsWith(".jpg") || fileType.endsWith(".jpeg") || fileType.endsWith(".pdf")) {
//                filePath = uri.toString()
//                errorMessage = ""
//            } else {
//                errorMessage = "Invalid file type! Only .jpg or .pdf files are allowed."
//            }
        } ?: run {
            errorMessage = "No file selected."
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "ID Verification", color = Color.White) },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Upload your ID", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))

                if (filePath.isNotEmpty()) {
                    Text("Selected File: $filePath", style = MaterialTheme.typography.body1)
                } else {
                    Text(
                        fileName,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    // Launch the file picker
                    filePickerLauncher.launch("*/*") // Accept any file type
                }) {
                    Text("Choose File", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
                // preview
                when {
                    selectedFileUri == null -> {
                        Text("No file selected", color = Color.Gray)
                    }

                    selectedFileType?.startsWith("image/") == true -> {
                        // Image preview for supported image types
                        Image(
                            painter = rememberAsyncImagePainter(selectedFileUri),
                            contentDescription = "Uploaded Image",
                            modifier = Modifier
                                .size(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    selectedFileType == "application/pdf" -> {
                        // PDF Placeholder
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "PDF Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = "PDF Uploaded: ${selectedFileUri?.lastPathSegment}",
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    else -> {
                        Text("Unsupported file type", color = Color.Red)
                    }
                }
                // Display preview based on file type
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(
                            ".png"
                        )
                    ) {
                        // Save file information and proceed
                        userData.value =
                            userData.value.copy(shareCode = generateShareCode(userData.value))
                        navigateScreen = Screen.ConfirmationScreen.route
                        //navController.navigate(Screen.ConfirmationScreen.route)
                    } else {
                        errorMessage = "Please select a file to continue."
                    }
                }) {
                    Text("Submit", color = Color.White)
                }

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(errorMessage, color = MaterialTheme.colors.error)
                }
            }
            if (navigateScreen.isNotEmpty()) {
                DelayedNavigationScreen(navController, navigateScreen)
            }
        }
    }
}

@Composable
fun IDVerificationScreenImagePicker(
    navController: NavHostController,
    userData: MutableState<UserData>
) {
    // State to manage selected file
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current // Get the context here

    // File picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedFileUri = uri
        selectedFileType = uri?.let { getFileType(context, it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "ID Verification",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Upload Button
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Upload Driver's License or Passport")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display File Preview
        when {
            selectedFileUri == null -> {
                Text("No file selected", color = Color.Gray)
            }

            selectedFileType?.startsWith("image/") == true -> {
                // Image preview for supported image types
                Image(
                    painter = rememberAsyncImagePainter(selectedFileUri),
                    contentDescription = "Uploaded Image",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            selectedFileType == "application/pdf" -> {
                // PDF Placeholder
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "PDF Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "PDF Uploaded: ${selectedFileUri?.lastPathSegment}",
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            else -> {
                Text("Unsupported file type", color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
//        Button(
//            onClick = {
//                if (selectedFileUri != null) {
//                    // Proceed with selected file
//                    navController.navigate("confirmation")
//                } else {
//                    Toast.makeText(
//                        LocalContext.current,
//                        "Please upload a valid file first",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Submit")
//        }

        Button(onClick = {
            if (selectedFileUri != null) {
                //errorMessage = "Please select a file to continue."
            } else {
                // Save file information and proceed
                userData.value =
                    userData.value.copy(shareCode = generateShareCode(userData.value))
                navController.navigate(Screen.BankIntegrationScreen.route)
            }
        }) {
            Text("Submit")
        }
    }
}

fun getFileType(context: Context, uri: Uri): String? {
    return context.contentResolver.getType(uri)
}

// Helper function to get file type
@Composable
fun getFileType(uri: Uri): String? {
    val context = LocalContext.current
    return context.contentResolver.getType(uri)
}


// ID Verification Screen
@Composable
fun IDVerificationScreenOld(
    navController: NavHostController,
    userData: MutableState<UserData>
) {
    var fileName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Upload your ID (DL or Passport)", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fileName,
                onValueChange = { fileName = it },
                label = { Text("File Name (e.g., myid.jpg)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (fileName.endsWith(".jpg") || fileName.endsWith(".pdf")) {
                    userData.value = userData.value.copy(
                        shareCode = generateShareCode(userData.value)
                    )
                    navController.navigate(Screen.ConfirmationScreen.route)
                } else {
                    errorMessage = "Invalid file type! Only .jpg or .pdf allowed."
                }
            }) {
                Text(
                    "Submit",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage, color = MaterialTheme.colors.error)
            }
        }
    }

}

private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var fileName: String? = null

    // Check if URI scheme is 'file'
    if (uri.scheme == "file") {
        fileName = uri.lastPathSegment
    } else if (uri.scheme == "content") {
        val contentResolver: ContentResolver = context.contentResolver
        // Use ContentResolver to query the file's metadata (e.g., display name)
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    fileName = cursor.getString(displayNameIndex)
                }
            }
        }
    }

    return fileName
}
