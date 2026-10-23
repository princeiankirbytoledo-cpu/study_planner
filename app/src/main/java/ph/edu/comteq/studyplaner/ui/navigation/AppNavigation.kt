package ph.edu.comteq.studyplaner.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ph.edu.comteq.studyplaner.ui.screens.HomeScreen
import ph.edu.comteq.studyplaner.ui.screens.SubjectFormScreen
import ph.edu.comteq.studyplaner.ui.screens.SubjectManagementScreen
import ph.edu.comteq.studyplaner.ui.screens.TaskFormScreen
import ph.edu.comteq.studyplaner.ui.screens.TaskListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME

    val topLevelRoutes = listOf(Routes.HOME, Routes.TASKS, Routes.SUBJECTS)
    val showBottomBar = currentRoute in topLevelRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onAddSubjectClick = { navController.navigate(Routes.ADD_SUBJECT) }
                )
            }
            composable(Routes.TASKS) {
                TaskListScreen(
                    onMoreClick = { navController.navigate(Routes.ADD_TASK) },
                    onTaskClick = { navController.navigate(Routes.EDIT_TASK) }
                )
            }
            composable(Routes.SUBJECTS) {
                SubjectManagementScreen(
                    onAddSubjectClick = { navController.navigate(Routes.ADD_SUBJECT) },
                    onSubjectClick = { navController.navigate(Routes.EDIT_SUBJECT) }
                )
            }
            composable(Routes.ADD_TASK) {
                TaskFormScreen(
                    isEditMode = false,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Routes.EDIT_TASK) {
                TaskFormScreen(
                    isEditMode = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Routes.ADD_SUBJECT) {
                SubjectFormScreen(
                    isEditMode = false,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Routes.EDIT_SUBJECT) {
                SubjectFormScreen(
                    isEditMode = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
