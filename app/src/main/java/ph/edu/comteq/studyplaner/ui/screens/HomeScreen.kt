package ph.edu.comteq.studyplaner.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

// Helper function to get time-of-day greeting
private fun getGreetingMessage(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good morning, Prince Ian! 👋"
        in 12..16 -> "Good afternoon, Prince Ian! 👋"
        else -> "Good evening, Prince Ian! 👋"
    }
}

data class HomeSubjectItem(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
    val iconTint: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tasks: List<TaskItemUi> = emptyList(),
    onAddSubjectClick: () -> Unit = { },
    onSubjectClick: (String) -> Unit = { }
) {
    val context = LocalContext.current
    val darkNavy = Color(0xFF0D0D5C)
    val headerGray = Color(0xFF424242)
    val buttonGreen = Color(0xFF2E7D32)
    val backgroundColor = Color(0xFFF8F9FA)

    // Dynamic Greeting Function based on time of day
    val greetingText = remember { getGreetingMessage() }

    // Initial list of subjects
    val initialSubjects = listOf(
        HomeSubjectItem("1", "Mathematics", Icons.Default.Calculate, Color(0xFFF0E6FF), Color(0xFF7C4DFF)),
        HomeSubjectItem("2", "Science", Icons.Default.Science, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
        HomeSubjectItem("3", "English", Icons.AutoMirrored.Filled.MenuBook, Color(0xFFFFF3E0), Color(0xFFEF6C00)),
        HomeSubjectItem("4", "Programming", Icons.Default.Code, Color(0xFFE3F2FD), Color(0xFF1565C0))
    )
    val subjectsList = remember { initialSubjects.toMutableStateList() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color(0xFF7C4DFF),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Graduation Cap",
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Study Planner",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "You have no new notifications", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notification",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = headerGray
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Dynamic Greeting section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = greetingText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Here are your subjects (${subjectsList.size})",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Subject Cards List (Task count computed dynamically from shared tasks list)
            items(subjectsList, key = { it.id }) { subject ->
                val dynamicCount = tasks.count { it.subject.equals(subject.name, ignoreCase = true) }

                SubjectCard(
                    icon = subject.icon,
                    iconBackgroundColor = subject.iconBackgroundColor,
                    iconTint = subject.iconTint,
                    subjectName = subject.name,
                    taskCount = dynamicCount,
                    onClick = {
                        onSubjectClick(subject.name)
                    }
                )
            }

            // Add Subject Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onAddSubjectClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonGreen
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Subject",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Subject",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(
    icon: ImageVector,
    iconBackgroundColor: Color,
    iconTint: Color,
    subjectName: String,
    taskCount: Int,
    onClick: () -> Unit = { }
) {
    val textColor = Color(0xFF0D0D5C)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = iconBackgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = subjectName,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = subjectName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$taskCount tasks",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Go",
                tint = Color(0xFFB0BEC5),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
