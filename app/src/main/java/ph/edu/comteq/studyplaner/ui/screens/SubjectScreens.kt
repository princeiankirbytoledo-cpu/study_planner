package ph.edu.comteq.studyplaner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Unique UI Data Model for Subject Item
data class SubjectItemUi(
    val name: String,
    val taskCount: Int,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
    val iconTint: Color
)

/**
 * ManageSubjectCard Composable (Named specifically to avoid collision with SubjectCard)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageSubjectCard(
    subject: SubjectItemUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    onOptionsClick: () -> Unit = { }
) {
    val darkNavy = Color(0xFF0D0D5C)
    val subtitleGray = Color(0xFF757575)

    Card(
        modifier = modifier
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
            // Rounded pastel icon box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = subject.iconBackgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = subject.icon,
                    contentDescription = subject.name,
                    tint = subject.iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Subject name and task count
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = subject.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${subject.taskCount} tasks",
                    fontSize = 14.sp,
                    color = subtitleGray
                )
            }

            // Three-dot options icon (navigates to edit_subject)
            IconButton(onClick = onOptionsClick) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color(0xFFB0BEC5),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * SCREEN 1: My Subjects Screen UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectManagementScreen(
    modifier: Modifier = Modifier,
    onAddSubjectClick: () -> Unit = { },
    onSubjectClick: () -> Unit = { }
) {
    val headerGray = Color(0xFF424242)
    val buttonGreen = Color(0xFF2E7D32)
    val lightPurple = Color(0xFFF0E6FF)
    val backgroundColor = Color(0xFFF8F9FA)

    // Hardcoded sample subjects data
    val sampleSubjects = listOf(
        SubjectItemUi(
            name = "Mathematics",
            taskCount = 3,
            icon = Icons.Default.Calculate,
            iconBackgroundColor = lightPurple,
            iconTint = Color(0xFF7C4DFF)
        ),
        SubjectItemUi(
            name = "Science",
            taskCount = 4,
            icon = Icons.Default.Science,
            iconBackgroundColor = Color(0xFFE8F5E9),
            iconTint = Color(0xFF2E7D32)
        ),
        SubjectItemUi(
            name = "English",
            taskCount = 2,
            icon = Icons.AutoMirrored.Filled.MenuBook,
            iconBackgroundColor = Color(0xFFFFF3E0),
            iconTint = Color(0xFFEF6C00)
        ),
        SubjectItemUi(
            name = "Programming",
            taskCount = 5,
            icon = Icons.Default.Code,
            iconBackgroundColor = Color(0xFFE3F2FD),
            iconTint = Color(0xFF1565C0)
        )
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
                            text = "My Subjects",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
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
            // Subject Cards List
            items(sampleSubjects) { subject ->
                ManageSubjectCard(
                    subject = subject,
                    onClick = onSubjectClick,
                    onOptionsClick = onSubjectClick
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

/**
 * SCREEN 2: Add / Edit Subject Form UI
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectFormScreen(
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onBackClick: () -> Unit = { }
) {
    val headerGray = Color(0xFF424242)
    val purpleAccent = Color(0xFF6552A3)
    val lightPurple = Color(0xFFF0E6FF)
    val backgroundColor = Color(0xFFF8F9FA)

    // Form Field Values depending on Mode
    val nameValue = if (isEditMode) "Programming" else ""
    val descriptionValue = if (isEditMode) "Coding and software development." else ""
    val charCount = if (isEditMode) "${descriptionValue.length}/100" else "0/100"
    val screenTitle = if (isEditMode) "Edit Subject" else "Add Subject"

    // Fixed Icon choices (8 icons)
    val availableIcons = listOf(
        Icons.Default.Calculate,
        Icons.Default.Science,
        Icons.AutoMirrored.Filled.MenuBook,
        Icons.Default.Code,
        Icons.Default.Public,
        Icons.Default.MusicNote,
        Icons.Default.Palette,
        Icons.Default.FitnessCenter
    )

    // In Edit mode, "Code" icon is selected (index 3). In Add mode, default first icon is selected (index 0).
    val selectedIconIndex = if (isEditMode) 3 else 0

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // 1. Subject Name
            FormLabel(text = "Subject Name *")
            OutlinedTextField(
                value = nameValue,
                onValueChange = { },
                placeholder = { Text("Enter subject name", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFD1C4E9),
                    focusedBorderColor = purpleAccent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Choose Icon Grid
            FormLabel(text = "Choose Icon")
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                userScrollEnabled = false
            ) {
                items(availableIcons.size) { index ->
                    val isSelected = index == selectedIconIndex
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(
                                color = if (isSelected) lightPurple else Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) purpleAccent else Color(0xFFE0E0E0)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = availableIcons[index],
                            contentDescription = "Subject Icon",
                            tint = purpleAccent,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Description (optional)
            FormLabel(text = "Description (optional)")
            OutlinedTextField(
                value = descriptionValue,
                onValueChange = { },
                placeholder = { Text("Add a short description...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFD1C4E9),
                    focusedBorderColor = purpleAccent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
            Text(
                text = charCount,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, end = 4.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 4. Save Subject Button (Does nothing when clicked)
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text(
                    text = "Save Subject",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * DELETE SUBJECT DIALOG (Preview only)
 */
@Composable
fun DeleteSubjectDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    onConfirmDelete: () -> Unit = { }
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Delete Subject?",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D0D5C)
            )
        },
        text = {
            Text(
                text = "This subject has 5 tasks. Deleting it may affect those tasks. This action cannot be undone.",
                color = Color.Gray
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Delete", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel", color = Color(0xFF0D0D5C))
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    )
}

// Previews
@Preview(showBackground = true)
@Composable
fun SubjectManagementScreenPreview() {
    SubjectManagementScreen()
}

@Preview(showBackground = true)
@Composable
fun AddSubjectScreenPreview() {
    SubjectFormScreen(isEditMode = false)
}

@Preview(showBackground = true)
@Composable
fun EditSubjectScreenPreview() {
    SubjectFormScreen(isEditMode = true)
}

@Preview(showBackground = true)
@Composable
fun DeleteSubjectDialogPreview() {
    DeleteSubjectDialog()
}
