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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.vegamdigital.app.domain.model.*
import `in`.vegamdigital.app.presentation.components.*
import `in`.vegamdigital.app.presentation.theme.*

@Composable
fun CourseDetailScreen(course: Course, back: () -> Unit, notifications: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader(course.title, notifications, back)
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(NavySoft)) {
                Column(Modifier.padding(20.dp)) { Text(if (course.isBonus) "FREE BONUS COURSE" else "YOUR COURSE", color = Gold, fontWeight = FontWeight.Bold, fontSize = 12.sp); Text(course.subtitle, color = Color.White.copy(.75f), modifier = Modifier.padding(vertical = 8.dp)); Text("${course.completedModules} of ${course.moduleCount} modules complete", color = Color.White, fontWeight = FontWeight.SemiBold); Spacer(Modifier.height(12.dp)); LinearProgressIndicator(course.progress, Modifier.fillMaxWidth().height(7.dp).clip(CircleShape), color = BrandBlue, trackColor = Color.White.copy(.15f)) }
            } }
            item { SectionTitle("Course modules") }
            items(course.lessons) { lesson -> Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(Color.White)) {
                Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(if (lesson.completed) Icons.Outlined.CheckCircle else Icons.Outlined.PlayCircleOutline, null, tint = if (lesson.completed) Mint else BrandBlue, modifier = Modifier.size(30.dp))
                    Column(Modifier.padding(horizontal = 13.dp).weight(1f)) { Text(lesson.title, fontWeight = FontWeight.SemiBold); Text(lesson.duration, color = Muted, fontSize = 12.sp) }
                    Icon(Icons.Outlined.ChevronRight, null, tint = Muted)
                }
            } }
        }
    }
}

@Composable
fun BonusCoursesScreen(data: Dashboard, navigate: (String) -> Unit, back: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Bonus courses", { navigate("notifications") }, back)
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { SectionTitle("Included free", "No registration or extra fee. Start learning immediately.") }
            items(data.courses.filter { it.isBonus }) { course -> ActionRow(Icons.Outlined.AutoAwesome, course.title, "${course.moduleCount} modules · ${course.subtitle}", Mint) { navigate("course/${course.id}") } }
        }
    }
}

@Composable
fun NotificationsScreen(updates: List<Update>, back: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        PageHeader("Notifications", {}, back)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(updates) { update -> Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.Top) {
                    Box(Modifier.size(42.dp).clip(CircleShape).background(BrandBlue.copy(.12f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.NotificationsNone, null, tint = BrandBlue) }
                    Column(Modifier.padding(start = 13.dp).weight(1f)) { Text(update.title, fontWeight = FontWeight.Bold); Text(update.message, color = Muted); Text(update.postedAgo, color = BrandBlue, fontSize = 11.sp, modifier = Modifier.padding(top = 5.dp)) }
                }
            } }
        }
    }
}

@Composable
fun SeniorsScreen(data: Dashboard, back: () -> Unit, notifications: () -> Unit) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        PageHeader("Your seniors", notifications, back)
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { StudentCard(data.student, Modifier.fillMaxWidth(), compact = true); Spacer(Modifier.height(10.dp)); SectionTitle("Your seniors", "Course already complete chesina vaallu. Nerugga hello cheppandi.") }
            items(data.seniors) { senior -> Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    InitialAvatar(senior.name); Column(Modifier.padding(horizontal = 14.dp).weight(1f)) { Text(senior.name, fontWeight = FontWeight.Bold, fontSize = 17.sp); Text("${senior.role} @ ${senior.company}", color = Muted); Text(senior.code, color = Muted, fontSize = 12.sp) }
                    Button({ context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${senior.phone.filter(Char::isDigit)}"))) }, colors = ButtonDefaults.buttonColors(containerColor = Mint)) { Icon(Icons.Outlined.Send, null); Spacer(Modifier.width(4.dp)); Text("Hello") }
                }
            } }
        }
    }
}

@Composable
fun ReferralScreen(data: Dashboard, busy: Boolean, back: () -> Unit, notifications: () -> Unit, send: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }; var note by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) {
        PageHeader("Refer a friend", notifications, back)
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { SectionTitle("Refer a friend") }
            item { Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(18.dp)) {
                Text("Your referral code", color = Muted); Text(data.student.code, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 7.dp))
                OutlinedTextField(name, { name = it }, Modifier.fillMaxWidth(), label = { Text("Friend name · పేరు") }); Spacer(Modifier.height(10.dp))
                OutlinedTextField(phone, { phone = it.filter(Char::isDigit).take(10) }, Modifier.fillMaxWidth(), label = { Text("Phone number · ఫోన్") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)); Spacer(Modifier.height(10.dp))
                OutlinedTextField(note, { note = it }, Modifier.fillMaxWidth(), label = { Text("Note (optional)") }, minLines = 2); Spacer(Modifier.height(16.dp))
                PrimaryButton("🎁  Send referral", Modifier.fillMaxWidth(), !busy) { send(name, phone, note) }
            } } }
            item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { ReferralStat("0", "Referrals sent", Modifier.weight(1f)); ReferralStat("0", "Referrals joined", Modifier.weight(1f)); ReferralStat("0", "Worked as senior", Modifier.weight(1f)) } }
        }
    }
}
@Composable private fun ReferralStat(value: String, label: String, modifier: Modifier) { Card(modifier, colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.fillMaxWidth().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(value, fontWeight = FontWeight.Bold, fontSize = 23.sp); Text(label, color = Muted, fontSize = 10.sp, textAlign = TextAlign.Center) } } }

@Composable
fun CertificateScreen(data: Dashboard, back: () -> Unit, notifications: () -> Unit) {
    val context = LocalContext.current
    Column(Modifier.fillMaxSize()) {
        PageHeader("Certificate", notifications, back)
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { SectionTitle("Certificate") }
            item { Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(14.dp)) {
                Surface(shape = RoundedCornerShape(18.dp), color = Navy, border = androidx.compose.foundation.BorderStroke(2.dp, Gold)) { Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SKILL YOU FORWARD ››", color = Gold, fontWeight = FontWeight.Bold, letterSpacing = 1.sp); Spacer(Modifier.height(12.dp)); Text("CERTIFICATE OF COMPLETION", color = Color(0xFF8DB8FF), fontWeight = FontWeight.Bold); Spacer(Modifier.height(14.dp)); Text(data.student.name, color = Color.White, style = MaterialTheme.typography.headlineMedium); Text("has successfully completed the", color = Color.White.copy(.7f)); Text("Digital Marketing — Job Seeker Track", color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center); Text(data.student.code, color = Gold, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 15.dp)); HorizontalDivider(color = Color.White.copy(.15f)); Row(Modifier.fillMaxWidth().padding(top = 14.dp), horizontalArrangement = Arrangement.SpaceEvenly) { CertificateFact("JOINED", "15 Aug 2026"); CertificateFact("COMPLETED", "15 Aug 2026") }; Spacer(Modifier.height(18.dp)); Text("Ameerpet branch · Hyderabad", color = Color.White.copy(.7f), fontSize = 12.sp)
                } }
                Spacer(Modifier.height(14.dp)); PrimaryButton("↗  Details share cheyyandi", Modifier.fillMaxWidth()) { val share = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, "${data.student.name} completed Digital Marketing at Skill You Forward. Certificate: ${data.student.code}") }; context.startActivity(Intent.createChooser(share, "Share certificate")) }
                Text("Original printed certificate kosam institute lo adagandi.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 9.dp))
            } } }
        }
    }
}
@Composable private fun CertificateFact(label: String, value: String) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(label, color = Color(0xFF8DB8FF), fontSize = 10.sp, fontWeight = FontWeight.Bold); Text(value, color = Color.White, fontWeight = FontWeight.Bold) } }

@Composable
fun ProgressScreen(data: Dashboard, back: () -> Unit, notifications: () -> Unit) {
    val course = data.courses.first()
    Column(Modifier.fillMaxSize()) {
        PageHeader("Student progress", notifications, back)
        LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Navy)) { Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) { CircularProgressIndicator(course.progress, Modifier.size(120.dp), strokeWidth = 10.dp, color = BrandBlue, trackColor = Color.White.copy(.15f)); Text("100% complete", color = Color.White, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 14.dp)); Text("Excellent work, ${data.student.name.substringBefore(' ')}!", color = Gold) } } }
            item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { ProgressStat("15", "Modules", Modifier.weight(1f)); ProgressStat("1", "Certificate", Modifier.weight(1f)); ProgressStat("2", "Answers", Modifier.weight(1f)) } }
            item { SectionTitle("Milestones") }
            items(listOf("Course enrolled" to "15 Aug 2026", "All modules completed" to "15 Aug 2026", "Certificate unlocked" to "15 Aug 2026")) { milestone -> ActionRow(Icons.Outlined.CheckCircle, milestone.first, milestone.second, Mint) {} }
        }
    }
}
@Composable private fun ProgressStat(value: String, label: String, modifier: Modifier) { Card(modifier, colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.fillMaxWidth().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(value, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = BrandBlue); Text(label, color = Muted, fontSize = 11.sp) } } }

@Composable
fun AskDoubtScreen(busy: Boolean, back: () -> Unit, notifications: () -> Unit, submit: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }; var details by remember { mutableStateOf("") }
    FormPage("Ask a doubt", back, notifications) {
        OutlinedTextField(title, { title = it }, Modifier.fillMaxWidth(), label = { Text("Your question") }, minLines = 2)
        Spacer(Modifier.height(12.dp)); OutlinedTextField(details, { details = it }, Modifier.fillMaxWidth(), label = { Text("More details (optional)") }, minLines = 5)
        Spacer(Modifier.height(18.dp)); PrimaryButton("Post question", Modifier.fillMaxWidth(), !busy) { submit(title, details) }
    }
}

@Composable
fun DoubtDetailScreen(doubt: Doubt, busy: Boolean, back: () -> Unit, notifications: () -> Unit, answer: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) { PageHeader("Doubt", notifications, back); LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(18.dp)) { Text(doubt.question, style = MaterialTheme.typography.titleLarge); Text("${doubt.author} · ${doubt.postedAgo}", color = Muted, modifier = Modifier.padding(vertical = 8.dp)); if (doubt.description.isNotBlank()) Text(doubt.description) } } }
        item { SectionTitle("${doubt.answers.size} answers") }
        items(doubt.answers) { item -> Card(colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(15.dp)) { Text(item.author, fontWeight = FontWeight.Bold, color = BrandBlue); Text(item.text, modifier = Modifier.padding(vertical = 5.dp)); Text(item.postedAgo, color = Muted, fontSize = 11.sp) } } }
        item { OutlinedTextField(text, { text = it }, Modifier.fillMaxWidth(), label = { Text("Mee answer rayandi") }, minLines = 2); Spacer(Modifier.height(10.dp)); PrimaryButton("Send answer", Modifier.fillMaxWidth(), !busy) { answer(text); text = "" } }
    } }
}

@Composable
fun PostJobScreen(busy: Boolean, back: () -> Unit, notifications: () -> Unit, submit: (Job) -> Unit) {
    var title by remember { mutableStateOf("") }; var company by remember { mutableStateOf("") }; var location by remember { mutableStateOf("") }; var salary by remember { mutableStateOf("") }; var experience by remember { mutableStateOf("") }; var details by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }
    FormPage("Post a job", back, notifications) {
        Field(title, { title = it }, "Job title"); Field(company, { company = it }, "Company"); Field(location, { location = it }, "Location"); Field(salary, { salary = it }, "Salary"); Field(experience, { experience = it }, "Experience"); Field(details, { details = it }, "Description", 3); Field(phone, { phone = it }, "Contact phone")
        PrimaryButton("Submit for approval", Modifier.fillMaxWidth(), !busy) { submit(Job(0, title, company, location, salary, experience, details, "Anusha Reddy", phone, "Pending approval")) }
    }
}

@Composable private fun FormPage(title: String, back: () -> Unit, notifications: () -> Unit, content: @Composable ColumnScope.() -> Unit) { Column(Modifier.fillMaxSize()) { PageHeader(title, notifications, back); LazyColumn(contentPadding = PaddingValues(18.dp)) { item { Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(18.dp), content = content) } } } } }
@Composable private fun Field(value: String, change: (String) -> Unit, label: String, lines: Int = 1) { OutlinedTextField(value, change, Modifier.fillMaxWidth().padding(bottom = 10.dp), label = { Text(label) }, minLines = lines, singleLine = lines == 1) }
