package ph.edu.comteq.studyplaner.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Helper Composable for Form Labels
 */
@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0D0D5C),
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

/**
 * Reusable Priority Selector Composable
 */
@Composable
fun PrioritySelector(
    selectedPriority: String,
    modifier: Modifier = Modifier
) {
    val priorities = listOf("Low", "Medium", "High")
    val selectedGray = Color(0xFF424242)
    val unselectedBg = Color(0xFFEEEEEE)
    val unselectedBorder = Color(0xFFE0E0E0)
    val darkNavy = Color(0xFF0D0D5C)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        priorities.forEach { priority ->
            val isSelected = priority.equals(selectedPriority, ignoreCase = true)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .background(
                        color = if (isSelected) selectedGray else unselectedBg,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) selectedGray else unselectedBorder
                        ),
                        shape = RoundedCornerShape(22.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = priority,
                    color = if (isSelected) Color.White else darkNavy,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * Reusable Tip Card Composable
 */
@Composable
fun TipCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E6FF)),
        border = BorderStroke(1.dp, Color(0xFFE1BEE7))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Tip",
                tint = Color(0xFF6552A3),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Tip: Add a due date and priority to stay organized.",
                fontSize = 13.sp,
                color = Color(0xFF0D0D5C),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Reusable Delete Confirmation Dialog Composable
 */
@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
    onConfirmDelete: () -> Unit = { }
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Delete Task?",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D0D5C)
            )
        },
        text = {
            Text(
                text = "This action cannot be undone.",
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

/**
 * Main Task Form Screen UI (Supports both Add and Edit modes)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onBackClick: () -> Unit = { }
) {
    val darkNavy = Color(0xFF0D0D5C)
    val headerGray = Color(0xFF424242)
    val purpleAccent = Color(0xFF6552A3)
    val backgroundColor = Color(0xFFF8F9FA)

    // Form Field Values depending on Mode
    val titleValue = if (isEditMode) "Finish Jetpack Compose UI" else ""
    val subjectValue = if (isEditMode) "Programming" else ""
    val dueDateValue = if (isEditMode) "May 24, 2025" else ""
    val selectedPriority = if (isEditMode) "High" else "Medium"
    val notesValue = if (isEditMode) "Complete the home and task screens." else ""
    val charCount = if (isEditMode) "${notesValue.length}/200" else "0/200"

    val screenTitle = if (isEditMode) "Edit Task" else "Add Task"

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
            // 1. Task Title
            FormLabel(text = "Task Title *")
            OutlinedTextField(
                value = titleValue,
                onValueChange = { },
                placeholder = { Text("Enter task title", color = Color.Gray) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Subject Dropdown Style Field
            FormLabel(text = "Subject *")
            OutlinedTextField(
                value = subjectValue,
                onValueChange = { },
                placeholder = { Text("Select subject", color = Color.Gray) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select subject",
                        tint = darkNavy
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFD1C4E9),
                    focusedBorderColor = purpleAccent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                readOnly = true,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Due Date Field (Text only)
            FormLabel(text = "Due Date *")
            OutlinedTextField(
                value = dueDateValue,
                onValueChange = { },
                placeholder = { Text("Enter due date (e.g. May 24, 2025)", color = Color.Gray) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Priority Chips Row
            FormLabel(text = "Priority *")
            PrioritySelector(selectedPriority = selectedPriority)

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Notes (optional)
            FormLabel(text = "Notes (optional)")
            OutlinedTextField(
                value = notesValue,
                onValueChange = { },
                placeholder = { Text("Add notes...", color = Color.Gray) },
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

            Spacer(modifier = Modifier.height(24.dp))

            // 6. Save Task Button
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
                    text = "Save Task",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 7. Delete Task Button (Only in Edit Mode)
            if (isEditMode) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(1.dp, Color(0xFFD32F2F)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD32F2F)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color(0xFFD32F2F)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Delete Task",
                        color = Color(0xFFD32F2F),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 8. Tip Card
            TipCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    TaskFormScreen(isEditMode = false)
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    TaskFormScreen(isEditMode = true)
}

@Preview(showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    DeleteConfirmationDialog()
}
