package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.DifficultyLevel
import com.example.data.model.SubjectType
import com.example.data.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeQuizScreen(
    viewModel: BookViewModel,
    onBackClick: () -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf<DifficultyLevel?>(null) }
    var selectedSubject by remember { mutableStateOf<SubjectType?>(null) }

    val allQuestions = viewModel.allChapters.flatMap { it.practiceQuestions }

    val filteredQuestions = allQuestions.filter { q ->
        (selectedDifficulty == null || q.difficultyLevel == selectedDifficulty) &&
                (selectedSubject == null || q.subjectType == selectedSubject)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SSC GD Practice Question Bank", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Filter Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Filter by Difficulty:", style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = selectedDifficulty == null,
                            onClick = { selectedDifficulty = null },
                            label = { Text("All Levels") }
                        )
                        DifficultyLevel.values().forEach { diff ->
                            FilterChip(
                                selected = selectedDifficulty == diff,
                                onClick = { selectedDifficulty = diff },
                                label = { Text(diff.name.replace("_", " ").lowercase().capitalize()) }
                            )
                        }
                    }
                }
            }

            Text(
                text = "Total Questions Available: ${filteredQuestions.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(filteredQuestions) { idx, q ->
                    PracticeQuestionCard(questionNumber = idx + 1, question = q)
                }
            }
        }
    }
}
