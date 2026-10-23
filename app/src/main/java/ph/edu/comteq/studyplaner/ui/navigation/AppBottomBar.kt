package ph.edu.comteq.studyplaner.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppBottomBar(
    currentRoute: String,
    onItemClick: (String) -> Unit
) {
    val darkNavy = Color(0xFF0D0D5C)
    val purpleAccent = Color(0xFF6552A3)
    val lightPurple = Color(0xFFF0E6FF)

    val items = listOf(
        BottomNavItem(Routes.HOME, "Home", Icons.Default.Home),
        BottomNavItem(Routes.TASKS, "Tasks", Icons.AutoMirrored.Filled.List),
        BottomNavItem(Routes.SUBJECTS, "Subjects", Icons.Default.Folder)
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = purpleAccent,
                    selectedTextColor = purpleAccent,
                    indicatorColor = lightPurple,
                    unselectedIconColor = darkNavy,
                    unselectedTextColor = darkNavy
                )
            )
        }
    }
}
