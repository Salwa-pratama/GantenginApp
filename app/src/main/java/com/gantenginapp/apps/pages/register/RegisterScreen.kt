package com.gantenginapp.apps.pages.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gantenginapp.apps.R
import com.gantenginapp.apps.model.User
import com.gantenginapp.apps.remote.ApiService
import com.gantenginapp.apps.remote.RetrofitClient
import com.gantenginapp.apps.remote.RegisterResponse
import com.gantenginapp.apps.ui.theme.ColorCustom
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegisterScreen(
    onRegisterSuccess: (User) -> Unit,
    onBackClick: () -> Unit
) {
    // DATA
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }

    // ERROR STATE
    var errorUsername by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf("") }
    var errorNoHp by remember { mutableStateOf("") }
    var errorPassword by remember { mutableStateOf("") }
    var apiError by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()


    val api = RetrofitClient.instance

    fun validate(): Boolean {
        var valid = true

        errorUsername = if (username.isEmpty()) {
            valid = false; "Username tidak boleh kosong"
        } else ""

        errorNoHp = if (noHp.isEmpty()) {
            valid = false; "Nomor HP tidak boleh kosong"
        } else ""

        errorEmail = when {
            email.isEmpty() -> {
                valid = false; "Email tidak boleh kosong"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                valid = false; "Format email tidak valid"
            }
            else -> ""
        }

        errorPassword = when {
            password.isEmpty() -> {
                valid = false; "Password tidak boleh kosong"
            }
            password.length < 8 -> {
                valid = false; "Password minimal 8 karakter"
            }
            else -> ""
        }

        return valid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorCustom.bg)
    ) {
        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("?", fontSize = 28.sp)
        }

        // LOGO
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.gantengin),
                contentDescription = "App logo",
                modifier = Modifier.size(260.dp)
            )
        }

        // FORM
        Column(
            modifier = Modifier
                .background(
                    ColorCustom.dark,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            Text(
                "Register",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = ColorCustom.bg,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(16.dp))

            // Username
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorCustom.black,
                    unfocusedTextColor = ColorCustom.black,
                    cursorColor = ColorCustom.black,
                    focusedIndicatorColor = ColorCustom.black,
                    unfocusedIndicatorColor = ColorCustom.black,
                    focusedLabelColor = ColorCustom.black,
                    unfocusedLabelColor = ColorCustom.black,
                    unfocusedContainerColor = ColorCustom.bg,
                    focusedContainerColor = ColorCustom.bg,
                )
            )
            if (errorUsername.isNotEmpty())
                Text(errorUsername, color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(8.dp))

            // No HP
            TextField(
                value = noHp,
                onValueChange = { noHp = it },
                label = { Text("No HP") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorCustom.black,
                    unfocusedTextColor = ColorCustom.black,
                    cursorColor = ColorCustom.black,
                    focusedIndicatorColor = ColorCustom.black,
                    unfocusedIndicatorColor = ColorCustom.black,
                    focusedLabelColor = ColorCustom.black,
                    unfocusedLabelColor = ColorCustom.black,
                    unfocusedContainerColor = ColorCustom.bg,
                    focusedContainerColor = ColorCustom.bg,
                )
            )
            if (errorNoHp.isNotEmpty())
                Text(errorNoHp, color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(8.dp))

            // Email
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorCustom.black,
                    unfocusedTextColor = ColorCustom.black,
                    cursorColor = ColorCustom.black,
                    focusedIndicatorColor = ColorCustom.black,
                    unfocusedIndicatorColor = ColorCustom.black,
                    focusedLabelColor = ColorCustom.black,
                    unfocusedLabelColor = ColorCustom.black,
                    unfocusedContainerColor = ColorCustom.bg,
                    focusedContainerColor = ColorCustom.bg,
                )
            )
            if (errorEmail.isNotEmpty())
                Text(errorEmail, color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(8.dp))

            // Password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ColorCustom.black,
                    unfocusedTextColor = ColorCustom.black,
                    cursorColor = ColorCustom.black,
                    focusedIndicatorColor = ColorCustom.black,
                    unfocusedIndicatorColor = ColorCustom.black,
                    focusedLabelColor = ColorCustom.black,
                    unfocusedLabelColor = ColorCustom.black,
                    unfocusedContainerColor = ColorCustom.bg,
                    focusedContainerColor = ColorCustom.bg,
                )
            )
            if (errorPassword.isNotEmpty())
                Text(errorPassword, color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(16.dp))

            if (apiError.isNotEmpty())
                Text(apiError, color = MaterialTheme.colorScheme.error)

            // BUTTON
            Button(
                onClick = {
                    if (validate()) {
                        loading = true
                        apiError = ""

                        coroutineScope.launch {
                            try {
                                val response: RegisterResponse = api.register(
                                    User(
                                        username = username,
                                        password = password,
                                        email = email,
                                        noHp = noHp,
                                        role = "user"
                                    )
                                )

                                if (response.status == "success" && response.data != null) {
                                    onRegisterSuccess(response.data)
                                } else {
                                    apiError = response.message
                                }
                            } catch (e: Exception) {
                                apiError = "Gagal terhubung ke server"
                            } finally {
                                loading = false
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text(if (loading) "Loading..." else "Register")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to login", color = ColorCustom.link)
            }
        }
    }
}
