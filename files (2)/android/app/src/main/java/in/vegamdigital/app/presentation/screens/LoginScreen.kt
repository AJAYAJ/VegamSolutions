package `in`.vegamdigital.app.presentation.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.vegamdigital.app.presentation.components.PrimaryButton
import `in`.vegamdigital.app.presentation.theme.*

@Composable
fun LoginScreen(busy: Boolean, onLogin: (String, String) -> Unit) {
    var code by remember { mutableStateOf("SYF-AMP-DM26-B03-014") }
    var password by remember { mutableStateOf("student123") }
    Box(
        Modifier
            .fillMaxSize()
            .background(Paper)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(92.dp)
                    .background(Navy, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "VDA",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "››",
                    color = Gold,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 11.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(28.dp))
            Text("Vegam Digital Academy", style = MaterialTheme.typography.headlineLarge, color = Ink)
            Text(
                "డిజిటల్ మార్కెటింగ్ — క్లాసులు, జాబ్స్, డౌట్స్",
                color = Muted,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(34.dp))
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                var passwordVisible by remember { mutableStateOf(false) }
                Column(Modifier.padding(18.dp)) {
                    OutlinedTextField(
                        code,
                        { code = it },
                        Modifier.fillMaxWidth(),
                        label = { Text("Student code · మీ కోడ్") },
                        leadingIcon = { Icon(Icons.Outlined.Person, null) },
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        password,
                        { password = it },
                        Modifier.fillMaxWidth(),
                        label = { Text("Password · పాస్‌వర్డ్") },
                        leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible){
                                        Icons.Outlined.VisibilityOff
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                    contentDescription = if (passwordVisible){
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    }
                                )
                            }
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )
                    Spacer(Modifier.height(18.dp))
                    PrimaryButton(
                        if (busy) "Signing in…" else "Login",
                        Modifier.fillMaxWidth(),
                        !busy
                    ) { onLogin(code, password) }
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("Login details kavala? Please contact us", color = Muted)
            Text(
                "☎  +91 90000 00000",
                color = BrandBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
