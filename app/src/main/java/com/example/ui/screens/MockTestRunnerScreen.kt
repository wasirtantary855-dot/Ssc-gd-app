package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MockTest
import com.example.data.model.Question
import com.example.data.viewmodel.BookViewModel
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.IndiaGreen
import com.example.ui.theme.SaffronOrange
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockTestRunnerScreen(
    testId: Int,
    viewModel: BookViewModel,
    onBackClick: () -> Unit
) {
    val mockTest = viewModel.getMockTestById(testId) ?: return
    val questions = mockTest.questions

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var userAnswers by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) } // Question index -> Option Index
    var remainingSeconds by remember { mutableStateOf(mockTest.durationMinutes * 60) }
    var isSubmitted by remember { mutableStateOf(false) }
    var showConfirmSubmit by remember { mutableStateOf(false) }

    // Live Timer
    LaunchedEffect(isSubmitted) {
        if (!isSubmitted) {
            while (remainingSeconds > 0) {
                delay(1000L)
                remainingSeconds -= 1
            }
            if (remainingSeconds == 0) {
                isSubmitted = true
            }
        }
    }

    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    if (isSubmitted) {
        // Result Summary View
        val correctCount = questions.indices.count { idx -> userAnswers[idx] == questions[idx].correctOptionIndex }
        val wrongCount = userAnswers.size - correctCount
        val unattemptedCount = questions.size - userAnswers.size
        val score = (correctCount * 2.0f) - (wrongCount * 0.25f)
        val maxScore = questions.size * 2.0f
        val timeSpent = (mockTest.durationMinutes * 60) - remainingSeconds

        LaunchedEffect(Unit) {
            viewModel.saveMockTestResult(
                testId = mockTest.id,
                testTitle = mockTest.title,
                totalQ = questions.size,
                correct = correctCount,
                wrong = wrongCount,
                unattempted = unattemptedCount,
                score = score,
                maxScore = maxScore,
                timeSeconds = timeSpent
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mock Test Scorecard & Results") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🏆 Total Score Obtained",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "%.2f / %.0f".format(score, maxScore),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = SaffronOrange
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatChip("Correct", "$correctCount", IndiaGreen)
                                StatChip("Wrong", "$wrongCount", Color.Red)
                                StatChip("Unattempted", "$unattemptedCount", Color.Gray)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "📋 Detailed Solutions & Answer Keys",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                itemsIndexed(questions) { idx: Int, q: Question ->
                    val userAns = userAnswers[idx]
                    val isCorrect = userAns == q.correctOptionIndex

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                userAns == null -> MaterialTheme.colorScheme.surfaceVariant
                                isCorrect -> Color(0xFFDCFCE7)
                                else -> Color(0xFFFEE2E2)
                            }
                        )
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Q${idx + 1}. ${q.questionText}",
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Your Answer: ${if (userAns != null) listOf("A", "B", "C", "D")[userAns] else "Not Answered"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isCorrect) IndiaGreen else Color.Red
                            )
                            Text(
                                text = "Correct Answer: Option (${listOf("A", "B", "C", "D")[q.correctOptionIndex]})",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Detailed Solution: ${q.detailedSolution}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = GoldAccent)
                        Spacer(modifier = Modifier.width(6.dp))
                        val minutes = remainingSeconds / 60
                        val seconds = remainingSeconds % 60
                        Text(
                            text = "%02d:%02d".format(minutes, seconds),
                            fontWeight = FontWeight.Bold,
                            color = if (remainingSeconds < 300) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = { showConfirmSubmit = true },
                        colors = ButtonDefaults.buttonColors(containerColor = IndiaGreen),
                        modifier = Modifier.testTag("submit_test_btn")
                    ) {
                        Text("Submit Test")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Question Palette Bar
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(questions) { idx: Int, q: Question ->
                    val isAnswered = userAnswers.containsKey(idx)
                    val isCurrent = idx == currentQuestionIndex

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = when {
                                    isCurrent -> GoldAccent
                                    isAnswered -> IndiaGreen
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                },
                                shape = CircleShape
                            )
                            .clickable { currentQuestionIndex = idx },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${idx + 1}",
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent || isAnswered) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentQuestion != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = SaffronOrange,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Question ${currentQuestionIndex + 1} / ${questions.size}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        val options = listOf(
                            currentQuestion.optionA,
                            currentQuestion.optionB,
                            currentQuestion.optionC,
                            currentQuestion.optionD
                        )

                        val currentAnswer = userAnswers[currentQuestionIndex]

                        options.forEachIndexed { optIdx, optText ->
                            val isSelected = currentAnswer == optIdx
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        userAnswers = userAnswers.toMutableMap().apply {
                                            put(currentQuestionIndex, optIdx)
                                        }
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) GoldAccent.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, GoldAccent) else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            userAnswers = userAnswers.toMutableMap().apply {
                                                put(currentQuestionIndex, optIdx)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = optText, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            if (currentQuestionIndex > 0) currentQuestionIndex -= 1
                        },
                        enabled = currentQuestionIndex > 0
                    ) {
                        Text("Previous")
                    }

                    Button(
                        onClick = {
                            if (currentQuestionIndex < questions.size - 1) currentQuestionIndex += 1
                        },
                        enabled = currentQuestionIndex < questions.size - 1
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }

    if (showConfirmSubmit) {
        AlertDialog(
            onDismissRequest = { showConfirmSubmit = false },
            title = { Text("Submit Mock Test?") },
            text = {
                Text("You have answered ${userAnswers.size} of ${questions.size} questions. Are you sure you want to submit the test?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmSubmit = false
                        isSubmitted = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = IndiaGreen)
                ) {
                    Text("Yes, Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmSubmit = false }) {
                    Text("Continue Test")
                }
            }
        )
    }
}

@Composable
fun StatChip(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}
