package ph.edu.comteq.studyplaner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Unique UI Data Model for Tasks
data class TaskItemUi(
    val title: String,
    val subject: String,
    val dueDate: String,
    val priority: String,
    val isCompleted: Boolean
)

/**
 * Reusable Priority Pill Composable
 */
@Composable
fun PriorityPill(
    priority: String,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (priority.lowercase()) {
        "high" -> Pair(Color(0xFFFFEBEE), Color(0xFFD32F2F))
        "medium" -> Pair(Color(0xFFFFF3E0), Color(0xFFEF6C00))
        else -> Pair(Color(0xFFE8F5E9), Color(0xFF2E7D32))
    }

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = priority,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Reusable Task Card Composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: TaskItemUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    val darkNavy = Color(0xFF0D0D5C)
    val purpleAccent = Color(0xFF6552A3)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { },
                colors = CheckboxDefaults.colors(
                    checkedColor = purpleAccent,
                    uncheckedColor = purpleAccent,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Task Details Column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Title (Strikethrough if completed)
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkNavy,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Subject name
                Text(
                    text = task.subject,
                    fontSize = 13.sp,
                    color = purpleAccent,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Due date and Priority Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Due Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = task.dueDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = " • ",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    PriorityPill(priority = task.priority)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Bookmark Button
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = purpleAccent
                )
            }
        }
    }
}

/**
 * Reusable Empty Tasks State Composable
 */
@Composable
fun EmptyTasksState(modifier: Modifier = Modifier) {
    val darkNavy = Color(0xFF0D0D5C)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFFF0E6FF), shape = RoundedCornerShape(40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.FindInPage,
                contentDescription = "No Tasks",
                tint = Color(0xFF6552A3),
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No tasks found",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = darkNavy
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try adjusting your search or filter.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

/**
 * Main Task List Screen UI Composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = { },
    onTaskClick: () -> Unit = { }
) {
    val darkNavy = Color(0xFF0D0D5C)
    val headerGray = Color(0xFF424242)
    val purpleAccent = Color(0xFF6552A3)
    val backgroundColor = Color(0xFFF8F9FA)

    // Hardcoded sample tasks data
    val sampleTasks = listOf(
        TaskItemUi("Finish Jetpack Compose UI", "Programming", "May 24, 2025", "High", false),
        TaskItemUi("Review IT Ethics", "IT Ethics", "May 25, 2025", "High", false),
        TaskItemUi("Complete Project Proposal", "Programming", "May 28, 2025", "Medium", false),
        TaskItemUi("Read Chapter 3", "Mathematics", "May 20, 2025", "Low", true),
        TaskItemUi("Watch Science Lecture", "Science", "May 18, 2025", "Low", true)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onMoreClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options",
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
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // Search Field
            item {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = {
                        Text(
                            text = "Search tasks by title...",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFD1C4E9),
                        focusedBorderColor = purpleAccent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
            }

            // Filter Chips Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    FilterChip(
                        selected = true,
                        onClick = { },
                        label = { Text("All", fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = purpleAccent,
                            selectedLabelColor = Color.White
                        ),
                        border = null,
                        shape = RoundedCornerShape(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FilterChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Pending") },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFFF3E5F5),
                            labelColor = darkNavy
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color(0xFFE1BEE7),
                            enabled = true,
                            selected = false
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FilterChip(
                        selected = false,
                        onClick = { },
                        label = { Text("Completed") },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFFF3E5F5),
                            labelColor = darkNavy
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Color(0xFFE1BEE7),
                            enabled = true,
                            selected = false
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            // Task Cards List
            items(sampleTasks) { task ->
                TaskCard(
                    task = task,
                    onClick = onTaskClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    TaskListScreen()
}

@Preview(showBackground = true)
@Composable
fun EmptyTasksStatePreview() {
    EmptyTasksState()
}
