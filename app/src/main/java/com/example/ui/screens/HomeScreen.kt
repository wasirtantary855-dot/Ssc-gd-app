package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Chapter
import com.example.data.model.SubjectType
import com.example.data.model.UserProgress
import com.example.data.viewmodel.BookViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    onNavigateToSubject: (SubjectType) -> Unit,
    onNavigateToChapter: (String) -> Unit,
    onNavigateToMockTest: (Int) -> Unit,
    onNavigateToRevision: () -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onNavigateToStrategy: () -> Unit,
    onNavigateToVideos: () -> Unit = {},
    onWatchVideo: (String) -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val userProgress by viewModel.userProgress.collectAsState()
    val allVideos by viewModel.allVideos.collectAsState()
    val selectedExam by viewModel.selectedExam.collectAsState()
    val allChapters = viewModel.allChapters

    // ... inside component ...
    val completedCount = userProgress.count { it.isCompleted }
    val progressPercent = if (allChapters.isNotEmpty()) ((completedCount.toFloat() / allChapters.size) * 100).toInt() else 0

    val featuredVideos = remember(allVideos) {
        val pool = if (allVideos.isNotEmpty()) allVideos else com.example.data.provider.VideoDataProvider.defaultVideos
        pool.take(6)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header in Clean Minimal Style
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${selectedExam.title.uppercase()} 2026-2027",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Indigo600,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Exam Prep Guide ",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Slate800
                    )
                    Text(
                        text = "• English Edition",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal,
                        color = Slate400
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Indigo50)
                    .clickable(onClick = onNavigateToAiTutor)
                    .testTag("home_ai_tutor_btn"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Doubt Solver",
                    tint = Indigo600,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Divider(color = Slate100, thickness = 1.dp)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Study Progress Summary Card (Clean Minimalism HTML style)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .border(1.dp, Slate100, RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Continue Study",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Slate500,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Number System",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800
                                )
                            }
                            Surface(
                                color = Emerald100,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "DAY 12",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Emerald700,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        LinearProgressIndicator(
                            progress = { if (allChapters.isNotEmpty()) completedCount.toFloat() / allChapters.size else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = Indigo500,
                            trackColor = Slate100
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress: $progressPercent%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Slate400
                            )
                            Text(
                                text = "$completedCount / ${allChapters.size} Chapters Completed",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Slate400
                            )
                        }
                    }
                }
            }

            // Hero Book Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_ssc_gd_hero_banner_1787763014249),
                            contentDescription = "SSC GD Hero Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.82f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Surface(
                                color = Indigo600,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${selectedExam.title} 2026-2027 Complete Guide",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Reasoning • GK • Maths • English Language",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Search Input Field
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .testTag("home_search_input"),
                    placeholder = { Text("Search chapters, topics, formulas or questions...", color = Slate400) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Slate400) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Slate400)
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Indigo600,
                        unfocusedBorderColor = Slate200
                    ),
                    singleLine = true
                )
            }

            // Quick Shortcut Chips
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        FilterChip(
                            selected = false,
                            onClick = onNavigateToVideos,
                            label = { Text("Video Classes (20+)", fontWeight = FontWeight.Bold) },
                            leadingIcon = { Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color(0xFFCC0000)) },
                            modifier = Modifier.testTag("video_classes_chip"),
                            shape = RoundedCornerShape(20.dp),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = false, borderColor = Slate200)
                        )
                    }
                    item {
                        FilterChip(
                            selected = false,
                            onClick = onNavigateToRevision,
                            label = { Text("Quick Revision Notes", fontWeight = FontWeight.SemiBold) },
                            leadingIcon = { Icon(Icons.Default.Bolt, contentDescription = null, tint = Amber500) },
                            modifier = Modifier.testTag("quick_revision_chip"),
                            shape = RoundedCornerShape(20.dp),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = false, borderColor = Slate200)
                        )
                    }
                    item {
                        FilterChip(
                            selected = false,
                            onClick = { onNavigateToMockTest(1) },
                            label = { Text("20 CBT Mock Tests", fontWeight = FontWeight.SemiBold) },
                            leadingIcon = { Icon(Icons.Default.Quiz, contentDescription = null, tint = Indigo600) },
                            modifier = Modifier.testTag("mock_test_chip"),
                            shape = RoundedCornerShape(20.dp),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = false, borderColor = Slate200)
                        )
                    }
                    item {
                        FilterChip(
                            selected = false,
                            onClick = onNavigateToStrategy,
                            label = { Text("Exam Strategy & Tips", fontWeight = FontWeight.SemiBold) },
                            leadingIcon = { Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Emerald600) },
                            modifier = Modifier.testTag("exam_strategy_chip"),
                            shape = RoundedCornerShape(20.dp),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = false, borderColor = Slate200)
                        )
                    }
                }
            }

            // Featured Video Classes Section
            if (searchQuery.isBlank() && featuredVideos.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.PlayCircleFilled, contentDescription = null, tint = Color(0xFFCC0000), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Video Learning Classes",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800
                                )
                            }
                            TextButton(onClick = onNavigateToVideos) {
                                Text("View All (${allVideos.size.coerceAtLeast(20)})", style = MaterialTheme.typography.labelMedium, color = Indigo600, fontWeight = FontWeight.Bold)
                            }
                        }

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(featuredVideos) { video ->
                                Card(
                                    modifier = Modifier
                                        .width(220.dp)
                                        .clickable { onWatchVideo(video.id) }
                                        .testTag("home_featured_video_${video.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate200)
                                ) {
                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(115.dp)
                                                .background(Color.Black)
                                        ) {
                                            coil.compose.AsyncImage(
                                                model = com.example.data.provider.VideoDataProvider.getThumbnailUrl(video.youtubeVideoId),
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
                                                Surface(
                                                    shape = CircleShape,
                                                    color = Color.Black.copy(alpha = 0.7f),
                                                    modifier = Modifier.size(36.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Icon(
                                                            Icons.Default.PlayArrow,
                                                            contentDescription = null,
                                                            tint = Color.White,
                                                            modifier = Modifier.size(22.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            Surface(
                                                color = Color.Black.copy(alpha = 0.8f),
                                                shape = RoundedCornerShape(4.dp),
                                                modifier = Modifier
                                                    .align(Alignment.BottomEnd)
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    text = video.duration,
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontSize = 10.sp,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Column(modifier = Modifier.padding(10.dp)) {
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
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Subject Cards Grid (Matching HTML Clean Minimalism layout: Math, Reasoning, GK, Language)
            if (searchQuery.isBlank()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Subject Modules",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Mathematics (Indigo 600)
                            CleanSubjectGridCard(
                                title = "Elementary Maths",
                                subtitle = "Mathematics",
                                iconSymbol = "Σ",
                                bgColor = Indigo600,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToSubject(SubjectType.MATHEMATICS) }
                            )

                            // Reasoning (Amber 500)
                            CleanSubjectGridCard(
                                title = "Reasoning Ability",
                                subtitle = "Intelligence & Logic",
                                iconSymbol = "?",
                                bgColor = Amber500,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToSubject(SubjectType.REASONING) }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // General GK (Emerald 600)
                            CleanSubjectGridCard(
                                title = "General Knowledge",
                                subtitle = "GK & Awareness",
                                iconSymbol = "G",
                                bgColor = Emerald600,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToSubject(SubjectType.GENERAL_KNOWLEDGE) }
                            )

                            // Language (Rose 500)
                            CleanSubjectGridCard(
                                title = "English Language",
                                subtitle = "Grammar & Vocab",
                                iconSymbol = "E",
                                bgColor = Rose500,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToSubject(SubjectType.ENGLISH_LANGUAGE) }
                            )
                        }
                    }
                }

                // Table of Contents List Section
                item {
                    Text(
                        text = "Subject Index & Chapters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(SubjectType.values()) { subject ->
                    val chaptersInSubject = viewModel.getChaptersForSubject(subject)
                    SubjectCategoryCard(
                        subject = subject,
                        chapterCount = chaptersInSubject.size,
                        onClick = { onNavigateToSubject(subject) }
                    )
                }
            } else {
                // Search Results
                val filteredChapters = allChapters.filter {
                    it.titleHindi.contains(searchQuery, ignoreCase = true) ||
                            it.titleEnglish.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true) ||
                            it.conceptExplanation.contains(searchQuery, ignoreCase = true)
                }

                item {
                    Text(
                        text = "Search Results (${filteredChapters.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate800,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(filteredChapters) { chapter ->
                    ChapterListItem(
                        chapter = chapter,
                        isCompleted = userProgress.any { it.chapterId == chapter.id && it.isCompleted },
                        onClick = { onNavigateToChapter(chapter.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CleanSubjectGridCard(
    title: String,
    subtitle: String,
    iconSymbol: String,
    bgColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(125.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.22f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iconSymbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Column {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.75f),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SubjectCategoryCard(
    subject: SubjectType,
    chapterCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .border(1.dp, Slate100, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("subject_card_${subject.name}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = when (subject) {
                    SubjectType.INTRO -> SaffronOrange
                    SubjectType.REASONING -> Amber500
                    SubjectType.GENERAL_KNOWLEDGE -> Emerald600
                    SubjectType.CURRENT_AFFAIRS -> Indigo500
                    SubjectType.MATHEMATICS -> Indigo600
                    SubjectType.HINDI_LANGUAGE -> Rose500
                    SubjectType.ENGLISH_LANGUAGE -> Color(0xFF8B5CF6)
                    SubjectType.PRACTICE_SETS -> Emerald600
                    SubjectType.MOCK_TESTS -> Indigo600
                    SubjectType.REVISION -> Amber500
                    else -> Indigo600
                },
                shape = CircleShape,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (subject) {
                            SubjectType.INTRO -> Icons.Default.Info
                            SubjectType.REASONING -> Icons.Default.Psychology
                            SubjectType.GENERAL_KNOWLEDGE -> Icons.Default.Public
                            SubjectType.CURRENT_AFFAIRS -> Icons.Default.Newspaper
                            SubjectType.MATHEMATICS -> Icons.Default.Calculate
                            SubjectType.HINDI_LANGUAGE -> Icons.Default.Translate
                            SubjectType.ENGLISH_LANGUAGE -> Icons.Default.Abc
                            SubjectType.PRACTICE_SETS -> Icons.Default.Assignment
                            SubjectType.MOCK_TESTS -> Icons.Default.Quiz
                            SubjectType.REVISION -> Icons.Default.Bolt
                            else -> Icons.Default.Book
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.titleEnglish,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate800
                )
                Text(
                    text = "$chapterCount Chapters • Complete Guide",
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = Slate400
            )
        }
    }
}

@Composable
fun ChapterListItem(
    chapter: Chapter,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(1.dp, Slate100, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("chapter_item_${chapter.id}"),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isCompleted) Icons.Outlined.CheckCircle else Icons.Default.Article,
                contentDescription = null,
                tint = if (isCompleted) Emerald600 else Indigo600,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapter.titleEnglish,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Slate800,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = chapter.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                color = Slate100,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "${chapter.estimatedReadTimeMinutes} min",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Slate500,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

