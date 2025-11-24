package com.gantenginapp.apps.pages.allPages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gantenginapp.apps.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.gantenginapp.apps.model.User
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen(
                onBackClick = {},
                onRegistStoreClick = {},
                user = null,
                onGoToMyStore = {}
            )
        }
    }
}

@Composable
fun ProfileScreen(
    user: User?,
    onBackClick : () -> Unit,
    onRegistStoreClick: () -> Unit,
    onGoToMyStore: () -> Unit
) {
    val textToko = if (user?.role == "admin-toko") {
        "Toko Anda"
    } else {
        "Pendaftaran Toko"
    }

    val onTokoClick = if (user?.role == "admin-toko") {
        onGoToMyStore
    } else {
        onRegistStoreClick
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                IconButton(onClick = { /* Info */ }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Image
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.gantengin),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                IconButton(onClick = { /* Edit profile picture */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.gantengin),
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Info boxes
            ProfileItem(icon = Icons.Default.Person, text = user?.username ?: "No name")
            ProfileItem(icon = Icons.Default.Phone, text = user?.noHp ?: "-")
            ProfileItem(icon = Icons.Default.Email, text = user?.email ?: "-")
            ProfileItem(icon = Icons.Default.Email, text = user?.role ?: "-")
            Box(
                modifier = Modifier.clickable { onTokoClick()  }
            ) {
                ProfileItem(icon = Icons.Default.ShoppingCart, text = textToko)

            }
            ProfileItem(icon = Icons.Default.List, text = "Pilihan Toko")
        }
    }
}

@Composable
fun ProfileItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}


// Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onBackClick = {},
        onRegistStoreClick = {},
        user = null,
        onGoToMyStore = {}
    )
}

