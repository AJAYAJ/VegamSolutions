package `in`.vegamdigital.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import `in`.vegamdigital.app.domain.model.Dashboard
import `in`.vegamdigital.app.presentation.AppViewModel
import `in`.vegamdigital.app.presentation.components.LoadingScreen
import `in`.vegamdigital.app.presentation.screens.*
import `in`.vegamdigital.app.presentation.theme.BrandBlue
import `in`.vegamdigital.app.presentation.theme.Muted
import `in`.vegamdigital.app.presentation.theme.Paper

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
    val nav = rememberNavController()
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
            composable("admin") { AdminDashboard(viewModel) { go("notifications") } }
            composable("courses") { CoursesScreen(data, ::go) }
            composable("jobs") { JobsScreen(data, ::go) }
            composable("doubts") { DoubtsScreen(data, ::go) }
            composable("profile") { ProfileScreen(data, ::go, viewModel::logout) }
            composable("notifications") { NotificationsScreen(data.updates, nav::popBackStack) }
            composable("bonus") { BonusCoursesScreen(data, ::go, nav::popBackStack) }
            composable("seniors") { SeniorsScreen(data, nav::popBackStack) { go("notifications") } }
            composable("referral") {
                ReferralScreen(
                    data,
                    busy,
                    nav::popBackStack,
                    { go("notifications") },
                    viewModel::refer
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
                    { go("notifications") }) { q, d -> viewModel.askDoubt(q, d, nav::popBackStack) }
            }
            composable("post-job") {
                PostJobScreen(
                    busy,
                    nav::popBackStack,
                    { go("notifications") }) { viewModel.postJob(it, nav::popBackStack) }
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
                    { viewModel.answer(doubt.id, it) },
                    viewModel::refreshDoubts,
                    viewModel::startDoubtPolling,
                    viewModel::stopDoubtPolling
                )
            }
            }
        }
    }
}
