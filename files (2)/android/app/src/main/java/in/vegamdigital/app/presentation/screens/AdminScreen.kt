package `in`.vegamdigital.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import `in`.vegamdigital.app.domain.model.Student
import `in`.vegamdigital.app.domain.repository.AdminLog
import `in`.vegamdigital.app.presentation.AppUiState
import `in`.vegamdigital.app.presentation.AppViewModel
import `in`.vegamdigital.app.presentation.components.PageHeader
import `in`.vegamdigital.app.presentation.components.PrimaryButton
import `in`.vegamdigital.app.presentation.components.SectionTitle
import `in`.vegamdigital.app.presentation.theme.*

@Composable
fun AdminDashboard(viewModel: AppViewModel, onNotifications: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    AdminDashboardContent(
        state = state,
        onNotifications = onNotifications,
        onCreateStudent = { student, password, onDone ->
            viewModel.createStudent(student, password, onDone)
        },
        onPostUpdate = viewModel::postUpdate,
        onRefreshLogs = viewModel::refreshAdminLogs,
        onLogout = viewModel::logout
    )
}

@Composable
fun AdminDashboardContent(
    state: AppUiState,
    onNotifications: () -> Unit,
    onCreateStudent: (Student, String, () -> Unit) -> Unit,
    onPostUpdate: (String, String, String, () -> Unit) -> Unit,
    onRefreshLogs: () -> Unit,
    onLogout: () -> Unit
) {
    var showCreateForm by remember { mutableStateOf(false) }
    var showUpdateForm by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredLogs = remember(state.adminLogs, searchQuery) {
        if (searchQuery.isBlank()) state.adminLogs
        else state.adminLogs.filter { 
            it.name.contains(searchQuery, ignoreCase = true) || 
            it.studentCode.contains(searchQuery, ignoreCase = true) 
        }
    }

    Column(Modifier.fillMaxSize()) {
        PageHeader("Admin Panel", onNotifications)
        
        Box(Modifier.weight(1f)) {
            if (showCreateForm) {
                CreateStudentForm(
                    busy = state.busy,
                    onCancel = { showCreateForm = false },
                    onSubmit = { student, password ->
                        onCreateStudent(student, password) {
                            showCreateForm = false
                        }
                    }
                )
            } else if (showUpdateForm) {
                CreateUpdateForm(
                    busy = state.busy,
                    onCancel = { showUpdateForm = false },
                    onSubmit = { type, title, message ->
                        onPostUpdate(type, title, message) {
                            showUpdateForm = false
                        }
                    }
                )
            } else {
                LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SectionTitle("Quick Actions", "Manage your academy students")
                    }
                    item {
                        Card(
                            onClick = { showCreateForm = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Row(
                                Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Outlined.PersonAdd, null, tint = BrandBlue)
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text("Create New Student", style = MaterialTheme.typography.titleMedium)
//                                Text("Add a student to Supabase Auth and Profiles", style = MaterialTheme.typography.bodySmall, color = Muted)
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            onClick = { showUpdateForm = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(Color.White)
                        ) {
                            Row(
                                Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Outlined.Campaign, null, tint = BrandBlue)
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text("Post App Update", style = MaterialTheme.typography.titleMedium)
                                    Text("Publish a notification to all students", style = MaterialTheme.typography.bodySmall, color = Muted)
                                }
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SectionTitle("Creation Logs", "Count: ${state.adminLogs.size}")
                            IconButton(onClick = onRefreshLogs, enabled = !state.busy) {
                                Icon(
                                    Icons.Outlined.Refresh,
                                    contentDescription = "Refresh creation logs",
                                    tint = if (state.busy) Muted.copy(alpha = 0.5f) else Muted
                                )
                            }
                        }
                    }

                    if (state.adminLogs.isNotEmpty()) {
                        item {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search by name or code...") },
                                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                singleLine = true
                            )
                        }
                    }

                    if (filteredLogs.isEmpty()) {
                        item {
                            Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                                Text(if (searchQuery.isBlank()) "No students created yet." else "No matches found.", color = Muted)
                            }
                        }
                    }

                    items(filteredLogs) { log ->
                        AdminLogCard(log)
                    }

                    item {
                        OutlinedButton(
                            onClick = onLogout,
                            enabled = !state.busy,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Logout")
                        }
                    }
                    
                    item { Spacer(Modifier.height(20.dp)) }
                }
            }

            if (state.busy) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                        .clickable(enabled = false) { },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BrandBlue)
                }
            }
        }
    }
}

@Composable
fun CreateUpdateForm(
    busy: Boolean,
    onCancel: () -> Unit,
    onSubmit: (String, String, String) -> Unit
) {
    val updateTypes = listOf("GENERAL", "COURSE", "JOBS")
    var type by remember { mutableStateOf(updateTypes.first()) }
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Post App Update", "This will be visible to all students") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                updateTypes.forEach { option ->
                    FilterChip(
                        selected = type == option,
                        onClick = { type = option },
                        label = { Text(option) }
                    )
                }
            }
        }
        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        item {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )
        }
        item {
            Spacer(Modifier.height(12.dp))
            PrimaryButton(
                text = if (busy) "Publishing..." else "Publish Update",
                enabled = !busy && title.isNotBlank() && message.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { onSubmit(type, title.trim(), message.trim()) }
            TextButton(onClick = onCancel, modifier = Modifier.fillMaxWidth(), enabled = !busy) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun AdminLogCard(log: AdminLog) {
    val clipboard = LocalClipboardManager.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(log.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(log.date, fontSize = 11.sp, color = Muted)
                }
                IconButton(onClick = { 
                    clipboard.setText(AnnotatedString("Code: ${log.studentCode}\nPassword: ${log.password}"))
                }) {
                    Icon(Icons.Outlined.ContentCopy, null, tint = BrandBlue, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.height(8.dp))
            LogDetail("Code", log.studentCode)
            LogDetail("Batch", log.batch)
            LogDetail("Password", log.password, isSensitive = true)
        }
    }
}

@Composable
fun LogDetail(label: String, value: String, isSensitive: Boolean = false) {
    var revealed by remember { mutableStateOf(!isSensitive) }
    Row(
        Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$label: ", color = Muted, fontSize = 13.sp)
        Text(
            if (revealed) value else "••••••••", 
            fontWeight = FontWeight.SemiBold, 
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )
        if (isSensitive) {
            IconButton(onClick = { revealed = !revealed }, modifier = Modifier.size(24.dp)) {
                Icon(
                    if (revealed) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    null,
                    modifier = Modifier.size(16.dp),
                    tint = BrandBlue
                )
            }
        }
    }
}

@Composable
fun CreateStudentForm(
    busy: Boolean,
    onCancel: () -> Unit,
    onSubmit: (Student, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("DM26-B03-014") }
    var password by remember { mutableStateOf(generatePassword()) }
    var branch by remember { mutableStateOf("SR Nager") }
    var batch by remember { mutableStateOf("B03") }
    var roll by remember { mutableStateOf("015") }
    var loc by remember { mutableStateOf("SR Nagar") }
    var course by remember { mutableStateOf("Digital Marketing") }

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("New Student Details", null/*"Email: code@students.vegamdigital.in"*/) }

        item { OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth()) }
        item { OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Student Code") }, modifier = Modifier.fillMaxWidth()) }
        item { 
            OutlinedTextField(
                value = password, 
                onValueChange = { password = it }, 
                label = { Text("Password") }, 
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { password = generatePassword() }) {
                        Icon(Icons.Outlined.Refresh, null, tint = BrandBlue)
                    }
                }
            ) 
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = branch, onValueChange = { branch = it }, label = { Text("Branch") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = batch, onValueChange = { batch = it }, label = { Text("Batch") }, modifier = Modifier.weight(1f))
            }
        }
        
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = roll, onValueChange = { roll = it }, label = { Text("Roll No") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = loc, onValueChange = { loc = it }, label = { Text("Location") }, modifier = Modifier.weight(1f))
            }
        }

        item { OutlinedTextField(value = course, onValueChange = { course = it }, label = { Text("Course") }, modifier = Modifier.fillMaxWidth()) }

        item {
            Spacer(Modifier.height(12.dp))
            PrimaryButton(
                text = if (busy) "Creating..." else "Create Student Account",
                enabled = !busy && name.isNotBlank() && code.isNotBlank() && password.length >= 6,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onSubmit(Student(code.trim().uppercase(), name.trim(), course.trim(), branch.trim().uppercase(), batch.trim().uppercase(), roll.trim(), loc.trim()), password)
                }
            )
            TextButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text("Cancel")
            }
        }
    }
}

private fun generatePassword(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*"
    return (1..8)
        .map { chars.random() }
        .joinToString("")
}

@Preview(showBackground = true)
@Composable
fun AdminDashboardPreview() {
    VegamTheme {
        AdminDashboardContent(
            state = AppUiState(
                adminLogs = listOf(
                    AdminLog("SYF-AMP-DM26-B03-014", "Anusha Reddy", "student123", "B03", "20 May, 10:30 AM"),
                    AdminLog("SYF-AMP-DM26-B03-015", "Rahul Kumar", "pass456", "B03", "21 May, 02:15 PM")
                )
            ),
            onNotifications = {},
            onCreateStudent = { _, _, _ -> },
            onPostUpdate = { _, _, _, _ -> },
            onRefreshLogs = {},
            onLogout = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateStudentFormPreview() {
    VegamTheme {
        CreateStudentForm(busy = false, onCancel = {}, onSubmit = { _, _ -> })
    }
}
