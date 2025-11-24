package com.gantenginapp.apps.pages.allPages
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
import androidx.compose.ui.tooling.preview.Preview
import com.gantenginapp.apps.R
import com.gantenginapp.apps.model.User
import com.gantenginapp.apps.ui.theme.ColorCustom
// API
import com.gantenginapp.apps.remote.RetrofitClient
import kotlinx.coroutines.launch
import com.gantenginapp.apps.remote.RegistStoreResponse
import com.gantenginapp.apps.remote.RegistStoreRequest
@Composable
fun RegisterStoreScreen(
    user: User?,
    onRegisterStoreSuccess: () -> Unit
) {
    // Data Dikirim ke server
    var name by remember { mutableStateOf("") }
    var noHp by remember {mutableStateOf("")}
    var jamBuka by remember { mutableStateOf("") }
    var jamTutup by remember { mutableStateOf("") }

    // animasi Loading
    var loading by remember {mutableStateOf(value = false)}

    // ERROR STATE
    var errorName by remember { mutableStateOf("") }
    var errorNoHp by remember { mutableStateOf("") }
    var errorJamBuka by remember { mutableStateOf("") }
    var errorJamTutup by remember { mutableStateOf("") }
    var apiError by remember { mutableStateOf("") }


    // Library Coroutine
    val coroutineScope = rememberCoroutineScope ()

    val api = RetrofitClient.instance

    // Fungsi Validasi
    fun validate() : Boolean {
        var valid = true

        errorName = if (name.isEmpty()) {
            valid = false; "Nama Toko Tidak Boleh kosong"
        } else ""

        errorNoHp = if (noHp.isEmpty()) {
            valid = false; "Nomor HP Tidak Boleh kosong"
        } else ""

        errorJamBuka = if (jamBuka.isEmpty()) {
            valid = false; "Jam Buka Tidak Boleh kosong"
        } else ""

        errorJamTutup = if (jamTutup.isEmpty()) {
            valid = false; "Jam Tutup Tidak Boleh kosong"
        } else ""


        return valid


    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorCustom.bg)
    ) {
        // Bagian Atas (Logo dan Header)
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("?", fontSize = 28.sp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gantengin),
                    contentDescription = "App logo",
                    modifier = Modifier.size(260.dp)
                )
            }
        }
        // Bagian Bawah (Form Register)
        Column(
            modifier = Modifier
                .background(
                    color = ColorCustom.dark,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .fillMaxHeight()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Regist Your Store",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = ColorCustom.bg,
                    fontWeight = FontWeight.Bold
                ) 
            )
            Spacer(Modifier.height(16.dp))
            // Name
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Store Name") },
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

            if (errorName.isNotEmpty())
                Text(errorName, color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(8.dp))
            // No Hp
            TextField(
                value = noHp,
                onValueChange = { noHp = it },
                label = { Text("NoHp") },
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
            // Jam Kerja
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Jam Buka
                Column(Modifier.height(100.dp).weight(1f)) {
                    TextField(
                        value = jamBuka,
                        onValueChange = { jamBuka = it },
                        label = { Text("Jam Buka (ex: 09:00)") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
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
                    if (errorJamBuka.isNotEmpty())
                        Text(errorJamBuka, color = MaterialTheme.colorScheme.error)

                }

                // Jam Tutup
                Column(Modifier.height(100.dp).weight(1f)) {
                    TextField(
                        value = jamTutup,
                        onValueChange = { jamTutup = it },
                        label = { Text("Jam Tutup (ex: 22:00)") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
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
                    if (errorJamTutup.isNotEmpty())
                        Text(errorJamTutup, color = MaterialTheme.colorScheme.error)
                }


            }

            Spacer(Modifier.height(16.dp))

            if (apiError.isNotEmpty())
                Text(apiError, color = MaterialTheme.colorScheme.error)
            // Tombol Register
            Button(
                onClick = {
                    if (validate()) {
                        loading = true
                        apiError = ""

                        coroutineScope.launch {
                            try {
                                val response: RegistStoreResponse = api.registStore(
                                    RegistStoreRequest(
                                        id = user?.id ?: "",
                                        name = name,
                                        noHp = noHp,
                                        jamBuka = jamBuka,
                                        jamTutup = jamTutup
                                    )
                                )
                                if (response.status == "success")  {
                                    // RESET FORM
                                    name = ""
                                    noHp = ""
                                    jamBuka = ""
                                    jamTutup = ""
                                    // PASS DATA ke ATAS
                                    onRegisterStoreSuccess()
                                    apiError = response.message
                                } else {
                                    apiError = response.message
                                }

                            } catch (e:Exception) {
                                apiError = e.message ?: "Error tidak diketahui"

                            } finally {
                                loading = false


                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                Text(if (loading) "Loading..." else "Register..")
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}






@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterStoreScreen(
        user = null,
        onRegisterStoreSuccess = {}
    )
}