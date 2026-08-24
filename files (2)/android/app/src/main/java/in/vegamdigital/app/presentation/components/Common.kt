package `in`.vegamdigital.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.vegamdigital.app.domain.model.Student
import `in`.vegamdigital.app.presentation.theme.*

@Composable
fun PageHeader(title: String, onNotifications: () -> Unit, onBack: (() -> Unit)? = null) {
    Box(
        Modifier.fillMaxWidth().background(Navy, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .statusBarsPadding().padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Column(Modifier.align(Alignment.CenterStart).padding(start = if (onBack == null) 0.dp else 42.dp)) {
            Text("DIGITAL MARKETING · AMEERPET", color = BrandBlue.copy(.85f), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Spacer(Modifier.height(5.dp))
            Text(title, color = Color.White, style = MaterialTheme.typography.headlineMedium)
        }
        if (onBack != null) IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.Outlined.ArrowBack, null, tint = Color.White)
        }
        IconButton(onClick = onNotifications, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(Icons.Outlined.NotificationsNone, "Notifications", tint = Color.White)
        }
    }
}

@Composable
fun StudentCard(student: Student, modifier: Modifier = Modifier, compact: Boolean = false) {
    Card(modifier, shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Navy)) {
        Box(Modifier.fillMaxWidth().background(Navy).padding(22.dp)) {
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("SKILL YOU FORWARD ››", color = Gold, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Text(student.location, color = Color.White.copy(.72f), fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(if (compact) 18.dp else 26.dp))
                Text(student.name, color = Color.White, style = MaterialTheme.typography.headlineMedium)
                Text(student.course, color = Color(0xFF9EC2FF), style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(14.dp))
                Text(student.code, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 1.sp)
                if (!compact) {
                    HorizontalDivider(Modifier.padding(vertical = 18.dp), color = Color.White.copy(.15f))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        CodePart("SYF", "Institute"); CodePart(student.branch, "Branch"); CodePart("DM26", "Digital Mktg · 2026")
                        CodePart(student.batch, "Batch"); CodePart(student.rollNumber, "Roll no")
                    }
                }
            }
        }
    }
}

@Composable private fun CodePart(code: String, label: String) { Column {
    Text(code, color = Gold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    Text(label, color = Color.White.copy(.65f), fontSize = 9.sp, maxLines = 1)
} }

@Composable
fun SectionTitle(title: String, subtitle: String? = null) {
    Column { Text(title, style = MaterialTheme.typography.titleLarge); if (subtitle != null) {
        Spacer(Modifier.height(4.dp)); Text(subtitle, color = Muted, style = MaterialTheme.typography.bodyMedium)
    } }
}

@Composable
fun ActionRow(icon: ImageVector, title: String, subtitle: String, tint: Color = BrandBlue, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(tint.copy(.12f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = tint)
            }
            Column(Modifier.padding(horizontal = 14.dp).weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = Muted, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Icon(Icons.Outlined.ArrowForwardIos, null, tint = Muted, modifier = Modifier.size(15.dp))
        }
    }
}

@Composable fun InitialAvatar(name: String, modifier: Modifier = Modifier) {
    Box(modifier.size(50.dp).clip(RoundedCornerShape(15.dp)).background(NavySoft), contentAlignment = Alignment.Center) {
        Text(name.take(1).uppercase(), color = Gold, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable fun PrimaryButton(text: String, modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick, modifier.height(54.dp), enabled = enabled, shape = RoundedCornerShape(14.dp)) { Text(text, fontWeight = FontWeight.Bold) }
}

@Composable fun LoadingScreen() { Box(Modifier.fillMaxSize().background(Paper), contentAlignment = Alignment.Center) { CircularProgressIndicator() } }
