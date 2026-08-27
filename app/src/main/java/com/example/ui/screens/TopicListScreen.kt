package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.SubjectType
import com.example.data.provider.VideoDataProvider
import com.example.data.viewmodel.BookViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicListScreen(
    subject: SubjectType,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onNavigateToChapter: (String) -> Unit,
    onWatchVideo: (String) -> Unit = {}
) {
    val chapters = viewModel.getChaptersForSubject(subject)
    val userProgress by viewModel.userProgress.collectAsState()
    val allVideos by viewModel.allVideos.collectAsState()

    val subjectVideos = remember(subject, allVideos) {
        val pool = if (allVideos.isNotEmpty()) allVideos else VideoDataProvider.defaultVideos
        pool.filter { it.subjectType == subject }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = subject.titleEnglish,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                        Text(
                            text = "${chapters.size} Chapters • ${subjectVideos.size} Video Lectures",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Slate800)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Slate50),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Video Class Banner for this Subject
            if (subjectVideos.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                subjectVideos.firstOrNull()?.let { onWatchVideo(it.id) }
                            }
                            .testTag("subject_video_banner"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Indigo600)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    Icons.Default.PlayCircle,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Watch ${subject.titleEnglish} Classes",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${subjectVideos.size} Topic-wise Video Masterclasses",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Indigo100
                                    )
                                }
                            }

                            Button(
                                onClick = { subjectVideos.firstOrNull()?.let { onWatchVideo(it.id) } },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Watch", color = Indigo600, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            items(chapters) { chapter ->
                val isCompleted = userProgress.any { it.chapterId == chapter.id && it.isCompleted }
                val matchingVideo = subjectVideos.firstOrNull { 
                    it.linkedChapterId == chapter.id || 
                    it.topicName.contains(chapter.titleEnglish, ignoreCase = true) ||
                    chapter.titleEnglish.contains(it.topicName, ignoreCase = true)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Slate100, RoundedCornerShape(20.dp))
                        .clickable { onNavigateToChapter(chapter.id) }
                        .testTag("topic_item_${chapter.id}"),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isCompleted) Emerald100 else Indigo50
                            ) {
                                Text(
                                    text = "Ch ${chapter.chapterNumber}",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCompleted) Emerald700 else Indigo600,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = chapter.titleEnglish,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = chapter.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500
                                )
                            }

                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = "Completed",
                                    tint = Emerald600,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Open",
                                    tint = Slate400
                                )
                            }
                        }

                        // Topic Action Row: Read Chapter vs Watch Video
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilledTonalButton(
                                onClick = { onNavigateToChapter(chapter.id) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 6.dp)
                            ) {
                                Text("Read Chapter", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            }

                            if (matchingVideo != null) {
                                Button(
                                    onClick = { onWatchVideo(matchingVideo.id) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("watch_video_btn_${chapter.id}"),
                                    colors = ButtonDefaults.buttonColors(containerColor = Indigo600),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Watch Video", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


