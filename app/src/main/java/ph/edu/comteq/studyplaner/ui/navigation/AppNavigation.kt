package ph.edu.comteq.studyplaner.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ph.edu.comteq.studyplaner.ui.screens.TaskItemUi
import ph.edu.comteq.studyplaner.ui.screens.TaskListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME

    // Shared Tasks State across screens
    val tasksList = remember { mutableStateListOf<TaskItemUi>() }
    var selectedTaskToEdit by remember { mutableStateOf<TaskItemUi?>(null) }
    var activeSubjectFilter by remember { mutableStateOf<String?>(null) }

    val topLevelRoutes = listOf(Routes.HOME, Routes.TASKS, Routes.SUBJECTS)
    val showBottomBar = currentRoute in topLevelRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        if (route == Routes.TASKS) {
                            activeSubjectFilter = null
                        }
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
                    tasks = tasksList,
                    onSubjectClick = { subjectName ->
                        activeSubjectFilter = subjectName
                        navController.navigate(Routes.TASKS)
                    }
                )
            }
            composable(Routes.TASKS) {
                TaskListScreen(
                    tasks = tasksList,
                    selectedSubjectFilter = activeSubjectFilter,
                    onMoreClick = {
                        selectedTaskToEdit = null
                        navController.navigate(Routes.ADD_TASK)
                    },
                    onTaskClick = { task ->
                        selectedTaskToEdit = task
                        navController.navigate(Routes.EDIT_TASK)
                    },
                    onBackClick = {
                        activeSubjectFilter = null
                        navController.popBackStack()
                    }
                )
            }
            composable(Routes.SUBJECTS) {
                SubjectManagementScreen()
            }
            composable(Routes.ADD_TASK) {
                TaskFormScreen(
                    isEditMode = false,
                    initialTask = null,
                    defaultSubject = activeSubjectFilter,
                    onBackClick = { navController.popBackStack() },
                    onSaveTask = { newTask ->
                        tasksList.add(newTask)
                    }
                )
            }
            composable(Routes.EDIT_TASK) {
                TaskFormScreen(
                    isEditMode = true,
                    initialTask = selectedTaskToEdit,
                    defaultSubject = activeSubjectFilter,
                    onBackClick = { navController.popBackStack() },
                    onSaveTask = { updatedTask ->
                        selectedTaskToEdit?.let { oldTask ->
                            val index = tasksList.indexOf(oldTask)
                            if (index != -1) {
                                tasksList[index] = updatedTask
                            }
                        }
                    }
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
