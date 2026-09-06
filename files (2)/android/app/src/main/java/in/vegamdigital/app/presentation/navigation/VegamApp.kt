package `in`.vegamdigital.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.compose.ui.tooling.preview.Preview
import `in`.vegamdigital.app.domain.model.*
import `in`.vegamdigital.app.presentation.AppViewModel
import `in`.vegamdigital.app.presentation.components.LoadingScreen
import `in`.vegamdigital.app.presentation.components.LocalUnreadNotificationCount
import `in`.vegamdigital.app.presentation.screens.*
import `in`.vegamdigital.app.presentation.theme.BrandBlue
import `in`.vegamdigital.app.presentation.theme.Muted
import `in`.vegamdigital.app.presentation.theme.Paper
import `in`.vegamdigital.app.presentation.theme.VegamTheme

private data class BottomItem(
    val route: String,
    val label: String,
    val selected: ImageVector,
    val normal: ImageVector
)

private val bottomItems = listOf(
    BottomItem("home", "Home", Icons.Rounded.Home, Icons.Outlined.Home),
//    BottomItem("courses", "Course", Icons.Rounded.MenuBook, Icons.Outlined.MenuBook),
    BottomItem("jobs", "Jobs", Icons.Rounded.Work, Icons.Outlined.WorkOutline),
    BottomItem("doubts", "Doubts", Icons.Rounded.ContactSupport, Icons.Outlined.ContactSupport),
    BottomItem("profile", "Profile", Icons.Rounded.Person, Icons.Outlined.PersonOutline)
)

@Composable
fun VegamApp(viewModel: AppViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(state.message) { state.message?.let { snackbar.showSnackbar(it); viewModel.clearMessage() } }
    Scaffold(snackbarHost = { SnackbarHost(snackbar) }, containerColor = Paper) { _ ->
        when (state.signedIn) {
            null -> LoadingScreen()
            false -> LoginScreen(state.busy, viewModel::login)
            true -> state.dashboard?.let { MainShell(it, state.busy, viewModel, state.isAdmin) } ?: LoadingScreen()
        }
    }
}

@Composable
private fun MainShell(data: Dashboard, busy: Boolean, viewModel: AppViewModel, isAdmin: Boolean) {
    MainShellContent(
        data = data,
        busy = busy,
        isAdmin = isAdmin,
        onLogout = { viewModel.logout() },
        onRefer = { n, e, p -> viewModel.refer(n, e, p) },
        onAskDoubt = { q, d, onDone -> viewModel.askDoubt(q, d, onDone) },
        onPostJob = { j, onDone -> viewModel.postJob(j, onDone) },
        onAnswer = { id, ans -> viewModel.answer(id, ans) },
        onRefreshDoubts = { viewModel.refreshDoubts() },
        onStartDoubtPolling = { viewModel.startDoubtPolling() },
        onStopDoubtPolling = { viewModel.stopDoubtPolling() },
        adminDashboard = { onNotifications -> AdminDashboard(viewModel, onNotifications) }
    )
}

@Composable
private fun MainShellContent(
    data: Dashboard,
    busy: Boolean,
    isAdmin: Boolean,
    onLogout: () -> Unit,
    onRefer: (String, String, String) -> Unit,
    onAskDoubt: (String, String, () -> Unit) -> Unit,
    onPostJob: (Job, () -> Unit) -> Unit,
    onAnswer: (Long, String) -> Unit,
    onRefreshDoubts: () -> Unit,
    onStartDoubtPolling: () -> Unit,
    onStopDoubtPolling: () -> Unit,
    adminDashboard: @Composable (onNotifications: () -> Unit) -> Unit
) {
    val nav = rememberNavController()
    val context = LocalContext.current
    val readState = remember { context.getSharedPreferences("notification_read_state", android.content.Context.MODE_PRIVATE) }
    val lastSeenKey = remember(data.student.code) { "last_seen_${data.student.code}" }
    var lastSeenMillis by remember(lastSeenKey) {
        mutableLongStateOf(readState.getLong(lastSeenKey, 0L))
    }
    val unreadCount = remember(data.updates, lastSeenMillis) {
        data.updates.count { update -> update.createdAt.toEpochMillis() > lastSeenMillis }
    }
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    val items = remember(isAdmin) {
        if (isAdmin) {
            listOf( BottomItem("admin", "Admin", Icons.Rounded.AdminPanelSettings, Icons.Outlined.AdminPanelSettings))
        } else {
            bottomItems
        }
    }

    val showBottom = items.any { it.route == current }
    fun go(route: String) {
        nav.navigate(route)
    }
    CompositionLocalProvider(LocalUnreadNotificationCount provides unreadCount) {
    Scaffold(
        containerColor = Paper,
        bottomBar = {
            if (showBottom) NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
                items.forEach { item ->
                    val selected = current == item.route; NavigationBarItem(
                    selected = selected,
                    onClick = {
                        nav.navigate(item.route) {
                            popUpTo(nav.graph.findStartDestination().id) {
                                saveState = true
                            }; launchSingleTop = true; restoreState = true
                        }
                    },
                    icon = { Icon(if (selected) item.selected else item.normal, item.label) },
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandBlue,
                        selectedTextColor = BrandBlue,
                        unselectedIconColor = Muted,
                        unselectedTextColor = Muted,
                        indicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )
                }
            }
        }
    ) { padding ->
        NavHost(nav, if (isAdmin) "admin" else "home", Modifier.padding(padding)) {
            composable("home") { HomeScreen(data, ::go) }
            composable("admin") { adminDashboard { go("notifications") } }
            composable("courses") { CoursesScreen(data, ::go) }
            composable("jobs") { JobsScreen(data, ::go) }
            composable("doubts") { DoubtsScreen(data, ::go) }
            composable("profile") { ProfileScreen(data, ::go, onLogout) }
            composable("notifications") {
                LaunchedEffect(data.updates) {
                    val newestUpdate = data.updates.maxOfOrNull { it.createdAt.toEpochMillis() } ?: 0L
                    if (newestUpdate > lastSeenMillis) {
                        lastSeenMillis = newestUpdate
                        readState.edit().putLong(lastSeenKey, newestUpdate).apply()
                    }
                }
                NotificationsScreen(data.updates, nav::popBackStack)
            }
            composable("bonus") { BonusCoursesScreen(data, ::go, nav::popBackStack) }
            composable("seniors") { SeniorsScreen(data, nav::popBackStack) { go("notifications") } }
            composable("referral") {
                ReferralScreen(
                    data,
                    busy,
                    nav::popBackStack,
                    { go("notifications") },
                    onRefer
                )
            }
            composable("certificate") {
                CertificateScreen(
                    data,
                    nav::popBackStack
                ) { go("notifications") }
            }
            composable("progress") {
                ProgressScreen(
                    data,
                    nav::popBackStack
                ) { go("notifications") }
            }
            composable("ask-doubt") {
                AskDoubtScreen(
                    busy,
                    nav::popBackStack,
                    { go("notifications") }) { q, d -> onAskDoubt(q, d, nav::popBackStack) }
            }
            composable("post-job") {
                PostJobScreen(
                    busy,
                    nav::popBackStack,
                    { go("notifications") }) { onPostJob(it, nav::popBackStack) }
            }
            composable("course/{id}") { entry ->
                data.courses.find {
                    it.id == entry.arguments?.getString(
                        "id"
                    )
                }?.let { CourseDetailScreen(it, nav::popBackStack) { go("notifications") } }
            }
            composable("doubt/{id}") { entry ->
                val id = entry.arguments?.getString("id")
                    ?.toLongOrNull(); data.doubts.find { it.id == id }?.let { doubt ->
                DoubtDetailScreen(
                    doubt,
                    busy,
                    nav::popBackStack,
                    { go("notifications") },
                    { onAnswer(doubt.id, it) },
                    onRefreshDoubts,
                    onStartDoubtPolling,
                    onStopDoubtPolling
                )
            }
            }
        }
    }
    }
}

private fun String?.toEpochMillis(): Long = runCatching {
    if (this.isNullOrBlank()) 0L else java.time.Instant.parse(this).toEpochMilli()
}.getOrDefault(0L)

private val sampleDashboard = Dashboard(
    student = Student("SYF-AMP-DM26-B03-014", "Anusha Reddy", "Digital Marketing", "SR Nagar", "B03", "014", "Hyderabad"),
    courses = listOf(
        Course("dm-basics", "Digital Marketing Basics", "Intro to SEO, SEM and Social Media", 10, 4, false)
    ),
    jobs = emptyList(),
    doubts = emptyList(),
    seniors = emptyList(),
    updates = listOf(
        Update("COURSE", "New module available", "Analytics and client reporting has been added.", "2 hours ago", createdAt = "2024-05-20T10:30:00Z")
    )
)

@Preview(showBackground = true)
@Composable
private fun MainShellPreview() {
    VegamTheme {
        MainShellContent(
            data = sampleDashboard,
            busy = false,
            isAdmin = false,
            onLogout = {},
            onRefer = { _, _, _ -> },
            onAskDoubt = { _, _, _ -> },
            onPostJob = { _, _ -> },
            onAnswer = { _, _ -> },
            onRefreshDoubts = {},
            onStartDoubtPolling = {},
            onStopDoubtPolling = {},
            adminDashboard = {}
        )
    }
}
