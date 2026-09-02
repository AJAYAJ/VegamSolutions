package `in`.vegamdigital.app.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.vegamdigital.app.domain.model.*
import `in`.vegamdigital.app.presentation.components.*
import `in`.vegamdigital.app.presentation.theme.*

@Composable
fun HomeScreen(data: Dashboard, navigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Hi, ${data.student.name.substringBefore(' ')}", { navigate("notifications") })
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { StudentCard(data.student, Modifier.fillMaxWidth(), compact = true) }
            item { CourseProgressCard(data.courses.first()) { navigate("course/${data.courses.first().id}") } }
            item {
                Card(Modifier.fillMaxWidth().clickable { navigate("bonus") }, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(PaleMint)) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.AutoAwesome, null, tint = Mint); Spacer(Modifier.width(10.dp))
                        Text("1 free bonus course unnayi — extra fee ledu", Modifier.weight(1f), color = Color(0xFF317A66), fontWeight = FontWeight.SemiBold)
                        Icon(Icons.Outlined.ChevronRight, null, tint = Mint)
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard("${data.doubts.count { it.answers.isEmpty() }}", "Open doubts", Icons.Outlined.ContactSupport, Modifier.weight(1f)) { navigate("doubts") }
                    StatCard("${data.jobs.size}", "Jobs live", Icons.Outlined.WorkOutline, Modifier.weight(1f)) { navigate("jobs") }
                    StatCard("${data.seniors.size}", "Your seniors", Icons.Outlined.Groups, Modifier.weight(1f)) { navigate("seniors") }
                }
            }
            item { SectionTitle("Updates") }
            items(data.updates) { UpdateCard(it) }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable private fun CourseProgressCard(course: Course, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(72.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(course.progress, Modifier.fillMaxSize(), strokeWidth = 7.dp, trackColor = Color(0xFFE5EAF4))
                Text("${(course.progress * 100).toInt()}%", fontWeight = FontWeight.Bold)
            }
            Column(Modifier.padding(start = 18.dp).weight(1f)) { Text(course.title, style = MaterialTheme.typography.titleMedium); Text("${course.completedModules} / ${course.moduleCount} modules complete", color = Muted) }
            Icon(Icons.Outlined.ChevronRight, null, tint = Muted)
        }
    }
}

@Composable private fun RowScope.StatCard(value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Card(modifier.clickable(onClick = onClick), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.fillMaxWidth().padding(vertical = 17.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = BrandBlue); Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold); Text(label, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable private fun UpdateCard(update: Update) { Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
    Column(Modifier.padding(16.dp)) { Text(update.type, color = BrandBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp); Spacer(Modifier.height(7.dp)); Text(update.title, fontWeight = FontWeight.Bold); Text(update.message, color = Muted); Text(update.postedAgo, color = Muted, fontSize = 11.sp) }
} }

@Composable
fun CoursesScreen(data: Dashboard, navigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Course", { navigate("notifications") })
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { SectionTitle("Your course") }
            items(data.courses.filterNot { it.isBonus }) { CourseCard(it, navigate) }
            item { Spacer(Modifier.height(10.dp)); SectionTitle("Free bonus courses", "Institute students ki free. Extra fee ledu, register cheyyalsina avasaram ledu.") }
            items(data.courses.filter { it.isBonus }) { CourseCard(it, navigate) }
        }
    }
}

@Composable private fun CourseCard(course: Course, navigate: (String) -> Unit) {
    Card(Modifier.fillMaxWidth().clickable { navigate("course/${course.id}") }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(if (course.isBonus) PaleMint else BrandBlue.copy(.12f)), contentAlignment = Alignment.Center) {
                    Icon(if (course.isBonus) Icons.Outlined.AutoAwesome else Icons.Outlined.MenuBook, null, tint = if (course.isBonus) Mint else BrandBlue)
                }
                Column(Modifier.padding(horizontal = 14.dp).weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) { Text(course.title, fontWeight = FontWeight.Bold); if (course.isBonus) Text("  FREE", color = Mint, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    Text(course.subtitle, color = Muted, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
                Icon(Icons.Outlined.ChevronRight, null, tint = Muted)
            }
            Spacer(Modifier.height(12.dp)); LinearProgressIndicator({ course.progress }, Modifier.fillMaxWidth().height(6.dp).clip(CircleShape), trackColor = Color(0xFFE7EBF4))
            Text("${course.moduleCount} modules · ${(course.progress * 100).toInt()}% complete", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 7.dp, start = 62.dp))
        }
    }
}

@Composable
fun JobsScreen(data: Dashboard, navigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Jobs", { navigate("notifications") })
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { PrimaryButton("＋  Post a job", Modifier.fillMaxWidth()) { navigate("post-job") }; Text("Meeru post chesina job admin approve chesaka andariki kanipistundi.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 7.dp)) }
            items(data.jobs) { JobCard(it) }
        }
    }
}

@Composable private fun JobCard(job: Job) {
    val context = LocalContext.current
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(job.title, style = MaterialTheme.typography.titleMedium); Text(job.postedAgo, color = Muted, fontSize = 11.sp) }
            Text("▥  ${job.company} · ${job.location}", color = Muted, modifier = Modifier.padding(vertical = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { SuggestionChip({}, { Text(job.salary) }); SuggestionChip({}, { Text(job.experience) }) }
            Text(job.description, color = Muted, modifier = Modifier.padding(vertical = 8.dp))
            Surface(shape = RoundedCornerShape(13.dp), color = Paper) { Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text(job.contactName, fontWeight = FontWeight.SemiBold); Text(job.phone, color = Muted, fontSize = 12.sp) }
                Button({ context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${job.phone}"))) }, colors = ButtonDefaults.buttonColors(containerColor = Mint)) { Icon(Icons.Outlined.Send, null); Spacer(Modifier.width(5.dp)); Text("Contact") }
            } }
        }
    }
}

@Composable
fun DoubtsScreen(data: Dashboard, navigate: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Doubts", { navigate("notifications") })
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Card(Modifier.fillMaxWidth().clickable { navigate("ask-doubt") }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) { InitialAvatar(data.student.name); Text("Mee doubt enti? Ikkada adagandi…", Modifier.padding(start = 14.dp).weight(1f), color = Muted); Icon(Icons.Outlined.Add, null) }
            } }
            item { SectionTitle("Recent questions") }
            items(data.doubts) { doubt -> Card(Modifier.fillMaxWidth().clickable { navigate("doubt/${doubt.id}") }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.padding(16.dp)) { Text(doubt.question, fontWeight = FontWeight.Bold, fontSize = 17.sp); Spacer(Modifier.height(8.dp)); Text("${doubt.author} · ${doubt.postedAgo}   ▢ ${doubt.answers.size}", color = Muted) }
            } }
        }
    }
}

@Composable
fun ProfileScreen(data: Dashboard, navigate: (String) -> Unit, logout: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Profile", { navigate("notifications") })
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { StudentCard(data.student, Modifier.fillMaxWidth()) }
            item { SectionTitle("Student hub") }
            if (data.student.isAdmin) {
                item { ActionRow(Icons.Outlined.AdminPanelSettings, "Admin Panel", "Manage students and academy settings", BrandBlue) { navigate("admin") } }
            }
            item { ActionRow(Icons.Outlined.Groups, "Your seniors", "Course complete chesina vaallatho connect avvandi", Mint) { navigate("seniors") } }
            item { ActionRow(Icons.Outlined.CardGiftcard, "Refer a friend", "Share learning with a friend", Gold) { navigate("referral") } }
            item { ActionRow(Icons.Outlined.WorkspacePremium, "Certificate", "View and share your achievement", BrandBlue) { navigate("certificate") } }
            item { ActionRow(Icons.Outlined.QueryStats, "Student progress", "Modules, activity and milestones", Color(0xFF8B66E8)) { navigate("progress") } }
            item { ActionRow(Icons.Outlined.Logout, "Logout", "Sign out from this device", Color(0xFFE35E69), logout) }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}
