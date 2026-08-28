package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.model.Question
import com.example.data.model.SolvedExample
import com.example.data.viewmodel.BookViewModel
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.IndiaGreen
import com.example.ui.theme.SaffronOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterDetailScreen(
    chapterId: String,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onAskAiTutor: (String) -> Unit,
    onWatchVideo: ((String) -> Unit)? = null
) {
    val chapter = viewModel.getChapterById(chapterId) ?: return
    val userBookmarks by viewModel.userBookmarks.collectAsState()
    val userNotes by viewModel.userNotes.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    val allVideos by viewModel.allVideos.collectAsState()

    val isBookmarked = userBookmarks.any { it.id == chapter.id }
    var noteInput by remember { mutableStateOf("") }
    var showNoteDialog by remember { mutableStateOf(false) }

    val matchingVideo = remember(chapter, allVideos) {
        val pool = if (allVideos.isNotEmpty()) allVideos else com.example.data.provider.VideoDataProvider.defaultVideos
        pool.firstOrNull {
            it.linkedChapterId == chapter.id ||
            it.topicName.contains(chapter.titleEnglish, ignoreCase = true) ||
            chapter.titleEnglish.contains(it.topicName, ignoreCase = true)
        }
    }

    val chapterNotes = userNotes.filter { it.chapterId == chapter.id }

    LaunchedEffect(chapterId) {
        viewModel.markChapterRead(chapterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = chapter.titleEnglish,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (matchingVideo != null) {
                        IconButton(
                            onClick = { onWatchVideo?.invoke(matchingVideo.id) },
                            modifier = Modifier.testTag("chapter_watch_video_action")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = "Watch Video Class",
                                tint = Color(0xFFCC0000)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            if (isSpeaking) viewModel.stopSpeaking() else viewModel.speakText(chapter.conceptExplanation)
                        },
                        modifier = Modifier.testTag("tts_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                            contentDescription = "Read Aloud",
                            tint = if (isSpeaking) SaffronOrange else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(
                                chapter.id,
                                chapter.titleEnglish,
                                "CHAPTER",
                                chapter.subjectType.titleEnglish
                            )
                        },
                        modifier = Modifier.testTag("bookmark_chapter_btn")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GoldAccent else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onAskAiTutor(chapter.titleEnglish) },
                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null) },
                text = { Text("Ask AI Tutor") },
                containerColor = GoldAccent,
                contentColor = Color.Black,
                modifier = Modifier.testTag("ask_ai_fab")
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Title Header Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                color = SaffronOrange,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = chapter.subjectType.titleEnglish,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = "⏱️ ${chapter.estimatedReadTimeMinutes} min read",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = chapter.titleEnglish,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = chapter.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            // Video Class Banner if available for this topic
            if (matchingVideo != null) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable { onWatchVideo?.invoke(matchingVideo.id) }
                            .testTag("chapter_video_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp, 40.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.Black),
                                    contentAlignment = Alignment.Center
                                ) {
                                    coil.compose.AsyncImage(
                                        model = com.example.data.provider.VideoDataProvider.getThumbnailUrl(matchingVideo.youtubeVideoId),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                    )
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = "Video Class: ${matchingVideo.topicName}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${matchingVideo.duration} • ${matchingVideo.instructor}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = com.example.ui.theme.GoldAccent
                                    )
                                }
                            }

                            Button(
                                onClick = { onWatchVideo?.invoke(matchingVideo.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.Indigo600),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("Watch", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Concept Explanation Section
            item {
                Text(
                    text = "📖 Concepts & Fundamental Rules",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = chapter.conceptExplanation,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Rules Bullet Points
            if (chapter.rulesAndConcepts.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "📌 Important Rules & Guidelines",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    chapter.rulesAndConcepts.forEach { rule ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("• ", fontWeight = FontWeight.Bold, color = GoldAccent)
                            Text(rule, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Formula Boxes
            if (chapter.formulas.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "📐 Mathematical Formulas & Shortcuts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(chapter.formulas) { formula ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .border(1.dp, GoldAccent, RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = formula.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = formula.formulaText,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronOrange,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = formula.explanation,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            // Short Tricks Box
            if (chapter.shortTricks.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFD97706))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "⚡ Short Trick Box",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            chapter.shortTricks.forEach { trick ->
                                Text(
                                    text = "• $trick",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF78350F),
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // Solved Examples Section
            if (chapter.solvedExamples.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "✍️ Solved Examples with Step-by-Step Solutions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(chapter.solvedExamples) { example ->
                    SolvedExampleCard(example = example)
                }
            }

            // Practice Questions Section
            if (chapter.practiceQuestions.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "📝 SSC GD Practice MCQs with Detailed Solutions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                itemsIndexed(chapter.practiceQuestions) { index, question ->
                    PracticeQuestionCard(questionNumber = index + 1, question = question, viewModel = viewModel)
                }
            }

            // User Study Notes Box
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📓 Personal Study Notes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = { showNoteDialog = true }) {
                        Icon(Icons.Default.AddComment, contentDescription = "Add Note", tint = GoldAccent)
                    }
                }

                if (chapterNotes.isEmpty()) {
                    Text(
                        text = "No notes added for this chapter yet. Tap the + icon to write your personal study notes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    chapterNotes.forEach { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = note.noteContent,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = { viewModel.deleteNote(note.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete Note", tint = Color.Red.copy(alpha = 0.7f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = { Text("Add New Note") },
            text = {
                OutlinedTextField(
                    value = noteInput,
                    onValueChange = { noteInput = it },
                    placeholder = { Text("e.g. Practice this formula at least 10 times...") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (noteInput.isNotBlank()) {
                            viewModel.saveNote(chapter.id, chapter.titleEnglish, noteInput)
                            noteInput = ""
                            showNoteDialog = false
                        }
                    }
                ) {
                    Text("Save Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SolvedExampleCard(example: SolvedExample) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = example.questionText,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = example.solutionText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (!example.shortTrickText.isNull_or_empty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = Color(0xFFECFDF5),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "⚡ Shortcut: ${example.shortTrickText}",
                        style = MaterialTheme.typography.bodySmall,
                        color = IndiaGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

private fun String?.isNull_or_empty(): Boolean = this == null || this.trim().isEmpty()

@Composable
fun PracticeQuestionCard(questionNumber: Int, question: Question, viewModel: BookViewModel? = null) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var showSolution by remember { mutableStateOf(false) }

    val bookmarks = viewModel?.userBookmarks?.collectAsState()?.value ?: emptyList()
    val isBookmarked = bookmarks.any { it.id == question.id }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Text(
                    text = "Question $questionNumber: ${question.questionText}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (viewModel != null) {
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(
                                id = question.id,
                                title = "Question: ${question.questionText.take(30)}...",
                                type = "QUESTION",
                                subtitle = "Subject: ${question.subjectType.titleEnglish}"
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            val options = listOf(question.optionA, question.optionB, question.optionC, question.optionD)
            options.forEachIndexed { index, option ->
                val isSelected = selectedOption == index
                val isCorrect = question.correctOptionIndex == index

                val optionColor = when {
                    selectedOption == null -> MaterialTheme.colorScheme.surfaceVariant
                    isCorrect -> Color(0xFFDCFCE7)
                    isSelected && !isCorrect -> Color(0xFFFEE2E2)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedOption = index
                            showSolution = true
                        },
                    colors = CardDefaults.cardColors(containerColor = optionColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = listOf("A", "B", "C", "D")[index],
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = option, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            AnimatedVisibility(visible = showSolution) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Answer: Option (${listOf("A", "B", "C", "D")[question.correctOptionIndex]})",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Detailed Solution: ${question.detailedSolution}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
