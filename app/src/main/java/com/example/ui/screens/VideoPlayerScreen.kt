package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.SubjectType
import com.example.data.model.VideoLesson
import com.example.data.provider.VideoDataProvider
import com.example.data.viewmodel.BookViewModel
import com.example.ui.components.EmbeddedYouTubePlayer
import com.example.ui.components.openYouTubeVideo
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoId: String,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onNavigateToChapter: ((String) -> Unit)? = null,
    onNavigateToPractice: (() -> Unit)? = null,
    onNavigateToMockTests: (() -> Unit)? = null,
    onAskAiTutor: ((String) -> Unit)? = null,
    onSelectVideo: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val allVideos by viewModel.allVideos.collectAsState()
    
    // Find current video or fallback to default
    val currentVideo = remember(videoId, allVideos) {
        allVideos.firstOrNull { it.id == videoId || it.youtubeVideoId == videoId }
            ?: VideoDataProvider.defaultVideos.firstOrNull { it.id == videoId || it.youtubeVideoId == videoId }
            ?: VideoDataProvider.defaultVideos.first()
    }

    val relatedVideos = remember(currentVideo, allVideos) {
        val pool = if (allVideos.isNotEmpty()) allVideos else VideoDataProvider.defaultVideos
        pool.filter { it.subjectType == currentVideo.subjectType && it.id != currentVideo.id }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = currentVideo.topicName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = currentVideo.subjectType.titleEnglish,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("video_player_back_btn")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { openYouTubeVideo(context, currentVideo.youtubeVideoId) },
                        modifier = Modifier.testTag("open_youtube_action")
                    ) {
                        Icon(Icons.Default.OpenInNew, contentDescription = "Open in YouTube App", tint = Color(0xFFFF0000))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Slate900
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayBg)
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Embedded YouTube Player
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {
                    EmbeddedYouTubePlayer(
                        videoId = currentVideo.youtubeVideoId,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("youtube_embedded_player")
                    )
                }
            }

            // Video Details Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = Indigo50,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = currentVideo.subjectType.titleEnglish,
                                    color = Indigo600,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Surface(
                                color = Slate100,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(12.dp), tint = Slate600)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = currentVideo.duration,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Slate600,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentVideo.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = Slate500)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentVideo.instructor,
                                style = MaterialTheme.typography.bodySmall,
                                color = Slate600,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        if (currentVideo.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = currentVideo.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Slate700
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Watch on YouTube button
                        OutlinedButton(
                            onClick = { openYouTubeVideo(context, currentVideo.youtubeVideoId) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("watch_on_youtube_btn"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFCC0000)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFCCCC))
                        ) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Watch on YouTube (Official App)", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Learning Flow: Step-by-Step Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.School, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SSC GD 5-Step Learning Pathway",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Step Pathway visual
                        val steps = listOf(
                            "1. Learn Topic",
                            "2. Watch Video",
                            "3. Practice MCQs",
                            "4. Take Test",
                            "5. See Result"
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            steps.forEachIndexed { index, step ->
                                val isActive = index == 1 // Watching video right now
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(if (isActive) GoldAccent else if (index < 1) IndiaGreen else Slate700),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${index + 1}",
                                            color = if (isActive) Slate900 else Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = step.substringAfter(". "),
                                        color = if (isActive) GoldAccent else Slate300,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = androidx.compose.ui.unit.TextUnit(10f, androidx.compose.ui.unit.TextUnitType.Sp),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Next Actions Grid
            item {
                Text(
                    text = "Next Steps for Topic Mastery",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate800,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Action 1: Read Notes
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                currentVideo.linkedChapterId?.let { chId ->
                                    onNavigateToChapter?.invoke(chId)
                                } ?: onNavigateToPractice?.invoke()
                            }
                            .testTag("action_read_notes"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Indigo600, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Study Notes", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Slate900)
                            Text("Formulas & Concepts", style = MaterialTheme.typography.labelSmall, color = Slate500)
                        }
                    }

                    // Action 2: Practice MCQs
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToPractice?.invoke() }
                            .testTag("action_practice_mcqs"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Icon(Icons.Default.EditNote, contentDescription = null, tint = IndiaGreen, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Practice MCQs", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Slate900)
                            Text("Test Understanding", style = MaterialTheme.typography.labelSmall, color = Slate500)
                        }
                    }

                    // Action 3: Ask AI Tutor
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAskAiTutor?.invoke(currentVideo.topicName) }
                            .testTag("action_ask_ai"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Ask AI Tutor", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = Slate900)
                            Text("Clear Doubts", style = MaterialTheme.typography.labelSmall, color = Slate500)
                        }
                    }
                }
            }

            // Related Subject Videos
            if (relatedVideos.isNotEmpty()) {
                item {
                    Text(
                        text = "More ${currentVideo.subjectType.titleEnglish} Video Lectures",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
                    )
                }

                items(relatedVideos) { video ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable {
                                onSelectVideo?.invoke(video.id)
                            }
                            .testTag("related_video_${video.id}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Thumbnail
                            Box(
                                modifier = Modifier
                                    .size(width = 100.dp, height = 62.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black)
                            ) {
                                AsyncImage(
                                    model = VideoDataProvider.getThumbnailUrl(video.youtubeVideoId),
                                    contentDescription = video.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = video.topicName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Indigo600,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = video.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Slate900,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = video.duration,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Slate500
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
